package com.xd.hufei.services.distributed.impl.impl;

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
import java.nio.file.Path;
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
        return null;
    }
}
