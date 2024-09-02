package com.xd.hufei.services.notDistributed.impl;

import com.xd.hufei.Library.SSQLibrary;
import com.xd.hufei.Library.SkylineLibrary;
import com.xd.hufei.services.notDistributed.SSQService;
import com.xd.hufei.utils.ETPSSConstant;
import com.xd.hufei.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@Service
@Slf4j
public class SSQServiceImpl implements SSQService {

    @Override
    public Map<Object, Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception {

        SSQLibrary.SSQInterface instance = SSQLibrary.SSQInterface.INSTANCE;

        // 保存文件到SSQ
        Path filePath = ToolUtils.saveFile(file,"ssq");

        Map<Object, Object> result = ToolUtils.fillResultMap(filePath);

        // 初始化算法
        int initResult = instance.init_algo(filePath.toString());
        if (initResult !=  ETPSSConstant.SUCCESS) {
            System.err.println("Failed to initialize algorithm");
            throw new Exception("SSQ初始化构建失败");
        }else{
            log.info("初始化构建成功");
        }
        return result;
    }

    @Override
    public Resource queryAlgo(MultipartFile file, Map<Object, Object> params, HttpServletRequest request) throws Exception {

        SSQLibrary.SSQInterface instance = SSQLibrary.SSQInterface.INSTANCE;

        Path filePath = ToolUtils.saveQueryFile(file,params,"ssq");
        // 执行查询算法
        int result = instance.query_algo(filePath.toString(),
                filePath.getParent().resolve("search_res.txt").toString());

        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("SSQ查询失败");
        }else{
            log.info("SSQ查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }
}
