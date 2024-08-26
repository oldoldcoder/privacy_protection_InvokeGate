package com.xd.hufei.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
                Map<String, Object> value = (Map<String, Object>) entry.getValue();
                sb.append(value.get("inputValue")).append(",");
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
        String fileName = "queryParam" + System.currentTimeMillis() + ".txt";
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
        List<String> strings = Files.readAllLines(filePath);
        String[] arr = strings.get(0).split(" ");
        Map<Object,Object> result = new HashMap<>();
        result.put("totals", arr[0]);
        result.put("dim", arr[1]);
        return result;
    }

    public static Map<Object,Object> fillResultMap(Path filePath,int flag) throws IOException {
        int total = Files.readAllLines(filePath).size();
        Map<Object,Object> result = new HashMap<>();
        result.put("totals", total);
        result.put("dim", "该查询无dim");
        return result;
    }

    // 生成唯一标识
    private String generateUniqueIdentifier() {
        // 实现你的唯一标识符生成逻辑，可以使用 UUID 等方式生成
        return UUID.randomUUID().toString();
    }

    // 按照对应分隔符分割文件
    public static String[] splitFile(Path filePath, String separator) throws IOException {
        // 读取文件内容
        String content = new String(Files.readAllBytes(filePath));

        // 分割内容
        String[] parts = content.split(separator, 2);

        if (parts.length < 2) {
            System.err.println("The file does not contain the separator.");
            return null;
        }

        // 获取文件目录和文件名
        File file = filePath.toFile();
        String directory = file.getParent();
        String fileName = file.getName();
        String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        String extension = fileName.substring(fileName.lastIndexOf('.'));

        // 新文件路径
        Path filePath1 = Paths.get(directory, baseName + "_part1" + extension);
        Path filePath2 = Paths.get(directory, baseName + "_part2" + extension);

        // 写入新文件
        Files.write(filePath1, parts[0].trim().getBytes(), StandardOpenOption.CREATE);
        Files.write(filePath2, parts[1].trim().getBytes(), StandardOpenOption.CREATE);

        // 返回新文件路径
        return new String[]{filePath1.toString(), filePath2.toString()};
    }
}
