package com.xd.hufei.services.other.impl;


import ch.qos.logback.classic.db.names.TableName;
import com.xd.hufei.dto.other.TableColumn;
import com.xd.hufei.mapper.other.CommonMapper;
import com.xd.hufei.services.other.AlgoEtpssService;
import com.xd.hufei.services.other.CommonService;
import com.xd.hufei.utils.DataBaseUrlParser;
import com.xd.hufei.utils.DynamicDataSource;
import com.xd.hufei.utils.PathResolveUtils;
import com.xd.hufei.utils.StatusUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Autowired
    private DynamicDataSource dynamicDataSource;

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    AlgoEtpssService etpssService;

    // 创建线程池
    private final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(8, 16, 100,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(),new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public int switchDataSources(String url) {
        try{
            // 切换默认数据源
            if(url.equals("default")){
                DynamicDataSource.threadLocal.set("default");
            }else{
                String name = "new";
                // 删除之前的数据源
                dynamicDataSource.removeDataSource(name);
                DataBaseUrlParser.DatabaseInfo info = DataBaseUrlParser.parseDatabaseUrl(url);
                // 每次新指向数据源的姓名
                DynamicDataSource.threadLocal.set(name);
                // 创建新的数据源
                assert info != null;
                dynamicDataSource.createDataSource(name,info.getHost(),info.getPort() + "",info.getDatabase(),info.getUsername(),info.getPassword());
                log.info("连接：" + info.toString());
                return StatusUtils.SUCCESS;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return StatusUtils.ERROR;
    }

    public List<Map<String,Object>> readTableGetList(String tableName){
        // 读取库里的数量
        Integer count = commonMapper.selectCount(tableName);
        List<Map<String,Object>> res = null;
        final Object lock = new Object();
        if(count > 10000){
            //分批次读取
            res = new ArrayList<>(count);
            int batchSize = 10000;
            int numBatches = (int) Math.ceil((double) count / batchSize);

            List<Future<List<Map<String, Object>>>> futures = new ArrayList<>();

            for (int i = 0; i < numBatches; i++) {
                final int batchStart = i * batchSize;
                final int batchEnd = Math.min(batchStart + batchSize, count);
                futures.add(threadPool.submit(() -> {
                    DynamicDataSource.threadLocal.set("new");
                    List<Map<String, Object>> ret = commonMapper.getDataFromTable(tableName, batchStart, batchSize);
                    log.info(batchStart + "--" + batchEnd + "--" + "size:" + ret.size());
                    return ret;
                }));

            }
            for (Future<List<Map<String, Object>>> future : futures) {
                try {
                    List<Map<String, Object>> maps = future.get();
                    res.addAll(maps);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            res = commonMapper.getDataFromTable(tableName,0,count);
        }
        return res;
    }
    // 读取结果文件，写入,返回的表名
    public String writeTable(String tableName,List<TableColumn> columns,List<Map<String,Object>> data){
        String newTable = tableName + System.currentTimeMillis();
        try {
            // 创建临时表
            commonMapper.createCopyTable(newTable,columns);
            // 如果数据量小于1000则自己插入
            if(data.size() <= 10000){
                commonMapper.insertNewDataFromList(data,newTable);
            }else{
                int size = data.size();
                int batchSize = 10000;
                int numBatches = (int) Math.ceil((double) size / batchSize);
                CountDownLatch latch = new CountDownLatch(numBatches); // 用于等待所有线程执行完成

                for (int i = 0; i < numBatches; i++) {
                    final int batchStart = i * batchSize;
                    final int batchEnd = Math.min(batchStart + batchSize, size);
                    threadPool.submit(() -> {
                        DynamicDataSource.threadLocal.set("default");
                        try {
                            for (int j = batchStart; j < batchEnd; ++j) {
                                commonMapper.insertNewDataOne(data.get(j), newTable);
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        finally {
                            latch.countDown();
                        }
                    });
                }
                latch.await();
            }
            return newTable;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TableColumn> getTableStructure(String dataBaseName, String tableName) {
        return commonMapper.getTableStructure(dataBaseName, tableName);
    }
    @Override
    public void ToDataDesensitization(List<Map<String, Object>> data) {

        ConsumerData consumerData = new ConsumerData(etpssService);
        if(data.size() <= 10000){
            // 进行脱敏处理
            data.forEach(consumerData);
        }else{
            int size = data.size();
            int batchSize = 10000;
            int numBatches = (int) Math.ceil((double) size / batchSize);
            CountDownLatch latch = new CountDownLatch(numBatches); // 用于等待所有线程执行完成

            for (int i = 0; i < numBatches; i++) {
                final int batchStart = i * batchSize;
                final int batchEnd = Math.min(batchStart + batchSize, size);
                threadPool.submit(() -> {
                    try {
                        for(int j = batchStart; j < batchEnd ; ++j){
                            Map<String, Object> map = data.get(j);
                            consumerData.accept(map);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        latch.countDown();
                    }
                });
            }

            try {
                latch.await();
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }
    @PreDestroy
    public void shutdown(){
        threadPool.shutdown();
    }

    public static class ConsumerData implements Consumer<Map<String,Object>> {

        AlgoEtpssService etpssService;
        public ConsumerData(AlgoEtpssService service){
            etpssService = service;
        }
        @Override
        public void accept(Map<String, Object> map) {
            for(String key : map.keySet()){
                if(key.contains("id")){
                    continue;
                }
                String val = map.get(key).toString();
                // 判断val是否是数字能够eTPSS处理的类型
                if(val.matches("\\d+")){
                    AlgoEtpssService.Etpss one = etpssService.Share(val);

                    // 恢复原数据看看怎么样
                    String s = etpssService.Recover(one).toString();
                    if(!s.equals(val)){
                        log.error("eTPSS加密失败");
                    }
                    // 处理之后的数值
                    String str = etpssService.Object2String(one);
                    // 覆盖数值
                    map.put(key,str);
                }
            }
        }
    }

    /*@Override
    public int saveFile(String path, List<DesensitizedData> data) {
        // 保存文件在某处
        try {
            File file = new File(path);

            // 如果文件不存在，则创建文件
            if (!file.exists() && file.createNewFile()) {
                log.info("创建新的文件");
            }
            // 创建 FileWriter 对象，指定文件路径
            FileWriter fileWriter = new FileWriter(path);
            // 创建 BufferedWriter 对象，用于写入文件
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data.size() + "\n");
            data.forEach((a)->{
                try {
                    bufferedWriter.write(a.getOriginalValue().toString() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();

            log.info("文件写入成功");
            return StatusUtils.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StatusUtils.ERROR;
    }

    @Override
    public int execETPSS(String execPath) {
        try {
            // 执行 C 程序
            Process process = Runtime.getRuntime().exec(execPath);
            // 等待程序执行完毕，最多等待 10 秒钟
            if (!process.waitFor(10, TimeUnit.MINUTES)) {
                // 如果超时，则销毁进程
                process.destroy();
                log.error("eTPSS程序执行时间过长");
            }
            // 获取程序的退出码
            int exitCode = process.exitValue();
            if(exitCode != StatusUtils.SUCCESS){
                return StatusUtils.ERROR;
            }
            log.info("eTPSS程序执行返回码:"+exitCode);
            return StatusUtils.SUCCESS;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            log.error("eTPSS程序执行错误");
        }
        return StatusUtils.ERROR;
    }

    @Override
    public String readFileAndWriteTable(String path) {

        File file = new File(path);
        // 如果文件不存在，则创建文件
        if (!file.exists()) {
            log.error("没有找到eTPSS执行结果文件");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // 读取第一行，获取行数
            int n = Integer.parseInt(reader.readLine());
            // 创建 List 存储数据
            List<DesensitizedData> dataList = new ArrayList<>();

            // 逐行读取数据
            for (int i = 0; i < n; i++) {
                DesensitizedData data1 = new DesensitizedData();
                String line = reader.readLine();
                String[] s = line.split(" ");
                if(s.length != 3){
                    throw new IOException("结果文件格式有错误");
                }
                for(int j = 0 ; j < s.length ; ++j){
                    BigInteger bigInteger = new BigInteger(s[j]);
                    switch (j){
                        case 0:
                            data1.setCs1(bigInteger);
                            break;
                        case 1:
                            data1.setCs2(bigInteger);
                            break;
                        case 2:
                            data1.setCs3(bigInteger);
                            break;
                    }
                }
                dataList.add(data1);
            }
            String tableName = "temp1" + System.currentTimeMillis();

            // 对于结果数据写入库里
            commonMapper.createTable(tableName);
            commonMapper.insertNewData(dataList,tableName);
            return tableName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
