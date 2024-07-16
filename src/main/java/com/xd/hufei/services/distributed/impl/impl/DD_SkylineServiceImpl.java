package com.xd.hufei.services.distributed.impl.impl;

import com.xd.hufei.Library.DD_SKQLibrary;
import com.xd.hufei.Library.DD_SkylineLibrary;
import com.xd.hufei.Library.DRQLibrary;
import com.xd.hufei.services.distributed.impl.DD_SkylineService;
import com.xd.hufei.utils.ETPSSConstant;
import com.xd.hufei.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DD_SkylineServiceImpl implements DD_SkylineService {

    @Override
    public Map<Object, Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception {
        DD_SkylineLibrary.DD_SKYLINEInterface instance = DD_SkylineLibrary.DD_SKYLINEInterface.INSTANCE;

        // 删除之前session的数据
        log.info("检测session之前的数据");
        HttpSession session = request.getSession();
        Map<String,Object> session_data = (Map<String, Object>) session.getAttribute("dd_skyline");
        if(session_data != null){
            // 调用algo清空C申请的内存，不然内存泄露
            instance.free_algo((DD_SkylineLibrary.Structures.DrqDataSet) session_data.get("data"));
            // 设置为空，情况内容
            session.setAttribute("dd_skyline",null);
        }

        // 保存文件到SSQ
        Path filePath = ToolUtils.saveFile(file,"dd_skyline");

        Map<Object, Object> result = ToolUtils.fillResultMap(filePath);

        DD_SkylineLibrary.Structures.DrqDataSet data = new DD_SkylineLibrary.Structures.DrqDataSet();


        // 初始化算法
        int initResult = instance.init_algo(data,filePath.toString());
        if (initResult !=  ETPSSConstant.SUCCESS) {
            System.err.println("Failed to initialize algorithm");
            throw new Exception("dd_skyline初始化构建失败");
        }else{
            log.info("初始化构建成功");
        }
        Map<String,Object> sessionData = new HashMap<>();
        sessionData.put("data",data);
        // 数据存放到session之中去
        session.setAttribute("dd_skyline",sessionData);
        log.info("sessionId:" + session.getId());
        log.info("存储的内容是：" + session.getAttribute("dd_skyline"));
        return result;

    }

    @Override
    public Resource queryAlgo(MultipartFile file, Map<Object, Object> params, HttpServletRequest request) throws Exception {
        DD_SkylineLibrary.DD_SKYLINEInterface instance = DD_SkylineLibrary.DD_SKYLINEInterface.INSTANCE;

        // 获取session存放的数据
        HttpSession session = request.getSession();
        Map<String,Object> session_data = (Map<String, Object>) session.getAttribute("dd_skyline");
        if(session_data == null) {
            throw new Exception("session存放数据失效，重新上传");
        }
        Path filePath = ToolUtils.saveQueryFile(file,params,"dd_skyline");

        // 读取这个文件，处理为两个部分，然后分别保存
        String separator = "**";
        String[] filePaths = splitFile(filePath, separator);

        if (filePaths != null) {
            System.out.println("分割后文件的位置1：" + filePaths[0]);
            System.out.println("分割后文件的位置1：" + filePaths[1]);
        }

        // 执行查询算法
        assert filePaths != null;
        int result = instance.query_algo((DD_SkylineLibrary.Structures.DrqDataSet) session_data.get("data"),
                filePaths[0],
                filePaths[1],
                filePath.getParent().resolve("search_res.txt").toString());

        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("dd_skq查询失败");
        }else{
            log.info("dd_skq查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }

    private static String[] splitFile(Path filePath, String separator) throws IOException {
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
        Files.write(filePath1, parts[0].getBytes(), StandardOpenOption.CREATE);
        Files.write(filePath2, parts[1].getBytes(), StandardOpenOption.CREATE);

        // 返回新文件路径
        return new String[]{filePath1.toString(), filePath2.toString()};
    }
}
