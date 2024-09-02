package com.xd.hufei.services.notDistributed.impl;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import com.xd.hufei.Library.SSQLibrary;
import com.xd.hufei.Library.SkylineLibrary;
import com.xd.hufei.services.notDistributed.SkylineService;
import com.xd.hufei.utils.ETPSSConstant;
import com.xd.hufei.utils.ToolUtils;
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
        log.info("检测session之前的数据");
        HttpSession session = request.getSession();

        // 保存文件
        Path filePath = ToolUtils.saveFile(file,"skyline");

        log.info("保存文件成功...");
        Map<Object, Object> result = ToolUtils.fillResultMap(filePath);

        if(skyline.init_algo(filePath.toString()) != ETPSSConstant.SUCCESS){
            throw new Exception("初始化数据以及树结构失败");
        }else{
            log.info("初始化数据以及树结构成功");
        }
        log.info("sessionId:" + session.getId());
        return result;
    }

    @Override
    public Resource queryAlgo(MultipartFile file,Map<Object, Object> params,HttpServletRequest request) throws Exception{

        SkylineLibrary.SkylineInterface skyline = SkylineLibrary.SkylineInterface.INSTANCE;

        Path filePath = ToolUtils.saveQueryFile(file,params,"skyline");
        // 执行查询算法
        int result = skyline.query_algo(
                filePath.toString(),
                filePath.getParent().resolve("search_res.txt").toString());

        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("SSQ查询失败");
        }else{
            log.info("SSQ查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());

    }


}
