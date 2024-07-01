package com.xd.hufei.services.notDistributed.impl;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import com.xd.hufei.Library.SkylineLibrary;
import com.xd.hufei.services.notDistributed.SkylineService;
import com.xd.hufei.utils.ETPSSConstant;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.scanner.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@Service
@Slf4j
public class SkylineServiceImpl implements SkylineService {


    // 初始化算法
    @Override
    public Map<Object, Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception {
        // skyline进行训练
        SkylineLibrary.SkylineInterface skyline = SkylineLibrary.SkylineInterface.INSTANCE;

        // 删除之前session的数据
        log.info("检测到session之前的数据，正在删除...");
        HttpSession session = request.getSession();
        Map<String,Object> session_data = (Map<String, Object>) session.getAttribute("skyline");
        if(session_data != null){
            // 调用algo清空C申请的内存，不然内存泄露
            skyline.free_algo((SkylineLibrary.Structures.skyline_data) session_data.get("data"), (SkylineLibrary.Structures.rtree) session_data.get("rtree"));
            // 设置为空，情况内容
            session.setAttribute("skyline",null);
        }
        // 保存文件到具体位置
        Map<Object, Object> result = new HashMap<>();

        // 保存文件到具体位置
        String currentDir = System.getProperty("user.dir");
        String uploadDir = currentDir + "/data/skyline";
        // 原文件名和时间戳
        String fileName = file.getOriginalFilename() + System.currentTimeMillis();
        Path filePath = Paths.get(uploadDir, fileName);
        if (!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        // 保存文件
        file.transferTo(filePath.toFile());
        log.info("保存文件成功...");
        // 读取文件前一行，返回解析结果
        // 假设我们只是读取第一行内容，实际解析逻辑需要根据文件格式和需求来实现
        String firstLine = Files.readAllLines(filePath).get(0);
        String[] split = firstLine.split(" ");

        result.put("totals", split[0]);
        result.put("dim", split[1]);


        SkylineLibrary.Structures.skyline_data data =  new SkylineLibrary.Structures.skyline_data();
        SkylineLibrary.Structures.rtree tree = new  SkylineLibrary.Structures.rtree();
        if(skyline.init_algo(filePath.toString(), data, tree) != ETPSSConstant.SUCCESS){
            throw new Exception("初始化数据以及树结构失败");
        }else{
            log.info("初始化数据以及树结构成功");
        }
        Map<String,Object> sessionData = new HashMap<>();
        sessionData.put("data",data);
        sessionData.put("rtree",tree);

        // 数据存放到session之中去
        session.setAttribute("skyline",sessionData);
        return result;
    }

    @Override
    public Resource queryAlgo(Map<Object, Object> params,HttpServletRequest request) throws Exception{

        SkylineLibrary.SkylineInterface skyline = SkylineLibrary.SkylineInterface.INSTANCE;
        // 获取session存放的数据
        HttpSession session = request.getSession();
        Map<String,Object> session_data = (Map<String, Object>) session.getAttribute("skyline");
        if(session_data == null) {
            throw new Exception("session存放数据失效，重新上传");
        }
        // 保存文件到具体位置
        String currentDir = System.getProperty("user.dir");
        String uploadDir = currentDir + "/data/skyline";
        // 原文件名和时间戳
        String fileName = "queryParam" + System.currentTimeMillis();
        Path filePath = Paths.get(uploadDir, fileName);
        if (!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }
        // 将params保存为文件（不想修改C的接口了）
        saveParamsToFile(params,filePath.toString());
        String resultPathStr = skyline.query_algo((SkylineLibrary.Structures.skyline_data) session_data.get("data"), (SkylineLibrary.Structures.rtree) session_data.get("rtree"), filePath.toString());
        Path resultPath = Paths.get(resultPathStr);

        return new UrlResource(filePath.toUri());

    }

    /**
     * 文件格式默认设置为：
     * number1 number2 number3（分别是不同维度数值，一个查询点）
     * */
    private void saveParamsToFile(Map<Object, Object> params, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Object, Object> entry : params.entrySet()) {
                sb.append(entry.getValue()).append(" ");
            }
            writer.write(sb.toString());
            writer.newLine();
        }
    }
}
