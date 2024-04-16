package com.xd.hufei.services.other.impl;


import com.xd.hufei.dto.other.DesensitizedData;
import com.xd.hufei.mapper.other.CommonMapper;
import com.xd.hufei.services.other.CommonService;
import com.xd.hufei.utils.DataBaseUrlParser;
import com.xd.hufei.utils.DynamicDataSource;
import com.xd.hufei.utils.PathResolveUtils;
import com.xd.hufei.utils.StatusUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {


    @Autowired
    private DynamicDataSource dynamicDataSource;

    @Autowired
    private CommonMapper commonMapper;
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
                return StatusUtils.SUCCESS;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return StatusUtils.ERROR;
    }

    @Override
    public List<BigInteger> readTableGetList(String tableName) {
        List<BigInteger> res = commonMapper.getData2desensitization(tableName);
        // 等待处理
        return res;
    }

    @Override
    public int saveFile(String path, /*TODO data暂时没有写入，不了解格式*/List<BigInteger> data) {
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
            // 向文件写入内容
            bufferedWriter.write("Hello, world!\n");
            bufferedWriter.write("This is a test.\n");
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();

            log.info("文件写入成功");
            return StatusUtils.SUCCESS;
        } catch (IOException e) {
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
            commonMapper.createTable(tableName,null);
            commonMapper.insertNewData(dataList,tableName);
            return tableName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
