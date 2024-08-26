package com.xd.hufei.services.distributed.impl.impl;

import com.xd.hufei.Library.DD_RSQLibrary;
import com.xd.hufei.Library.DD_SKQLibrary;
import com.xd.hufei.services.distributed.impl.DD_RSQService;
import com.xd.hufei.utils.ETPSSConstant;
import com.xd.hufei.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.Map;

@Service
@Slf4j
public class DD_RSQServiceImpl implements DD_RSQService {

    @Override
    public Map<Object, Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception {
        DD_RSQLibrary.DD_RSQInterface instance = DD_RSQLibrary.DD_RSQInterface.INSTANCE;

        if(instance.freeRawData()!= 0){
            throw new Exception("double free error");
        }

        Path filePath = ToolUtils.saveFile(file,"dd_rsq");
        Map<Object, Object> result = ToolUtils.fillResultMap(filePath,1);
        // 读取这个文件，处理为两个部分，然后分别保存
        String separator = "\\*\\*";
        String[] filePaths = ToolUtils.splitFile(filePath, separator);

        if (filePaths != null) {
           log.info("分割后文件的位置1：" + filePaths[0]);
            System.out.println("分割后文件的位置1：" + filePaths[1]);
        }
        // 初始化算法
        assert filePaths != null;
        int initResult = instance.dealData(filePaths[0],filePaths[1]);
        if (initResult !=  ETPSSConstant.SUCCESS) {
            log.error("Failed to initialize algorithm");
            throw new Exception("dd_rsq初始化构建失败");
        }else{
            log.info("初始化构建成功");
        }
        return result;

    }

    @Override
    public Resource queryAlgo(MultipartFile file, Map<Object, Object> params, HttpServletRequest request) throws Exception {
        DD_RSQLibrary.DD_RSQInterface instance = DD_RSQLibrary.DD_RSQInterface.INSTANCE;

        // 保存文件
        Path filePath = ToolUtils.saveQueryFile(file,params,"dd_rsq");
        // 执行查询算法
        int result = instance.reverseSQ(
                filePath.toString(),
                filePath.getParent().resolve("search_res.txt").toString());

        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("dd_rsq查询失败");
        }else{
            log.info("dd_rsq查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());

    }
}
