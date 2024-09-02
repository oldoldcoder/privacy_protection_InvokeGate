package com.xd.hufei.services.notDistributed.impl;

import com.xd.hufei.Library.RSQLibrary;
import com.xd.hufei.Library.SSQLibrary;
import com.xd.hufei.services.notDistributed.RSQService;
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
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RSQServiceImpl implements RSQService {

    @Override
    public Map<Object, Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception {
        RSQLibrary.RSQInterface instance = RSQLibrary.RSQInterface.INSTANCE;

        // 删除之前session的数据
        log.info("检测session之前的数据");
        HttpSession session = request.getSession();

        // 保存文件到SSQ
        Path filePath = ToolUtils.saveFile(file,"rsq");

        Map<Object, Object> result = ToolUtils.fillResultMap(filePath);

        // 初始化算法
        int initResult = instance.init_algo(filePath.toString());
        if (initResult !=  ETPSSConstant.SUCCESS) {
            System.err.println("Failed to initialize algorithm");
            throw new Exception("RSQ初始化构建失败");
        }else{
            log.info("初始化构建成功");
        }
        log.info("sessionId:" + session.getId());
        return result;
    }

    @Override
    public Resource queryAlgo(MultipartFile file, Map<Object, Object> params, HttpServletRequest request) throws Exception {
        RSQLibrary.RSQInterface instance = RSQLibrary.RSQInterface.INSTANCE;

        Path filePath = ToolUtils.saveQueryFile(file,params,"rsq");
        // 执行查询算法
        int result = instance.query_algo(filePath.toString(),
                filePath.getParent().resolve("search_res.txt").toString());

        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("RSQ查询失败");
        }else{
            log.info("RSQ查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }
}
