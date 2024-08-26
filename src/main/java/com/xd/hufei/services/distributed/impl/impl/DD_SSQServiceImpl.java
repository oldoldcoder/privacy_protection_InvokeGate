package com.xd.hufei.services.distributed.impl.impl;

import com.xd.hufei.Library.DD_SSQLibrary;
import com.xd.hufei.services.distributed.impl.DD_RSQService;
import com.xd.hufei.services.distributed.impl.DD_SSQService;
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
public class DD_SSQServiceImpl implements DD_SSQService {

    @Override
    public Map<Object, Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception {
        DD_SSQLibrary.DD_SSQInterface instance = DD_SSQLibrary.DD_SSQInterface.INSTANCE;
        if(instance.cleanupRawData() != 0){
            throw new Exception("double free error");
        }
        Path filePath = ToolUtils.saveFile(file,"dd_ssq");

        Map<Object, Object> result = ToolUtils.fillResultMap(filePath,1);

        int initResult = instance.dealData(filePath.toString());

        if (initResult !=  ETPSSConstant.SUCCESS) {
            System.err.println("Failed to initialize algorithm");
            throw new Exception("dd_ssq初始化构建失败");
        }else{
            log.info("初始化构建成功");
        }

        return result;
    }

    @Override
    public Resource queryAlgo(MultipartFile file, Map<Object, Object> params, HttpServletRequest request) throws Exception {
        DD_SSQLibrary.DD_SSQInterface instance = DD_SSQLibrary.DD_SSQInterface.INSTANCE;
        // 保存文件
        Path filePath = ToolUtils.saveQueryFile(file,params,"dd_ssq");
        // 执行查询算法
        int result = instance.secureCollaborationQ(
                filePath.toString(),
                filePath.getParent().resolve("search_res.txt").toString());

        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("dd_ssq查询失败");
        }else{
            log.info("dd_ssq查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());

    }
}
