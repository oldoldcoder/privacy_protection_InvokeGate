package com.xd.hufei.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ToolUtils {

    public static Path saveFile(MultipartFile file,String algo) throws IOException {
        // 获取当前目录
        String currentDir = System.getProperty("user.dir");
        // 构建上传目录
        Path uploadPath = Paths.get(currentDir, "data", algo);
        // 原文件名和时间戳
        String fileName = file.getOriginalFilename() + System.currentTimeMillis();
        Path filePath = Paths.get(uploadPath.toString(), fileName);
        if (!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        // 保存文件
        file.transferTo(filePath.toFile());
        return filePath;
    }

    // map转换为file文件
    /**
     * 文件格式默认设置为：
     * number1 number2 number3（分别是不同维度数值，一个查询点）
     * */
    public static void saveParamsToFile(Map<Object, Object> params, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Object, Object> entry : params.entrySet()) {
                sb.append(entry.getValue()).append(" ");
            }
            writer.write(sb.toString());
            writer.newLine();
        }
    }

    public static Path saveQueryFile(MultipartFile file,Map params,String algo) throws Exception {
        // 获取当前目录
        String currentDir = System.getProperty("user.dir");
        // 构建上传目录
        Path uploadPath = Paths.get(currentDir, "data", algo);
        // 原文件名和时间戳
        String fileName = "queryParam" + System.currentTimeMillis();
        Path filePath = Paths.get(uploadPath.toString(), fileName);
        if (!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }
        if((params != null && file != null) || (params == null && file == null)){
            throw new Exception("传递了错误的参数");
        }
        // 如果参数不为空走这个
        if(params != null){
            // 将params保存为文件（不想修改C的接口了）
            ToolUtils.saveParamsToFile(params,filePath.toString());
        }else{
            // 将multipart存放到对应的位置
            file.transferTo(filePath.toFile());
        }
        return filePath;
    }

    public static Map<Object,Object> fillResultMap(Path filePath) throws IOException {
        String firstLine = Files.readAllLines(filePath).get(0);
        String[] split = firstLine.split(" ");
        Map<Object,Object> result = new HashMap<>();
        result.put("totals", split[0]);
        result.put("dim", split[1]);
        return result;
    }
}
