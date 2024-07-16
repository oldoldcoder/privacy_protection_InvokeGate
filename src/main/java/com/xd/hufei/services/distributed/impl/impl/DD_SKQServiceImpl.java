package com.xd.hufei.services.distributed.impl.impl;

import com.xd.hufei.Library.DD_SKQLibrary;
import com.xd.hufei.Library.DRQLibrary;
import com.xd.hufei.services.distributed.impl.DD_SKQService;
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
public class DD_SKQServiceImpl implements DD_SKQService {

    @Override
    public Map<Object, Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception {
        DD_SKQLibrary.DD_SKQInterface instance = DD_SKQLibrary.DD_SKQInterface.INSTANCE;

        // 删除之前session的数据
        log.info("检测session之前的数据");
        HttpSession session = request.getSession();
        Map<String,Object> session_data = (Map<String, Object>) session.getAttribute("dd_skq");
        if(session_data != null){
            // 调用algo清空C申请的内存，不然内存泄露
            instance.free_algo((DD_SKQLibrary.Structures.DD_SKQDataSet) session_data.get("data"));
            // 设置为空，情况内容
            session.setAttribute("dd_skq",null);
        }

        // 保存文件到SSQ
        Path filePath = ToolUtils.saveFile(file,"dd_skq");

        Map<Object, Object> result = ToolUtils.fillResultMap(filePath);

        DD_SKQLibrary.Structures.DD_SKQDataSet data = new DD_SKQLibrary.Structures.DD_SKQDataSet();

        // 初始化算法
        int initResult = instance.init_algo(data,filePath.toString());
        if (initResult !=  ETPSSConstant.SUCCESS) {
            System.err.println("Failed to initialize algorithm");
            throw new Exception("dd_skq初始化构建失败");
        }else{
            log.info("初始化构建成功");
        }
        Map<String,Object> sessionData = new HashMap<>();
        sessionData.put("data",data);
        // 数据存放到session之中去
        session.setAttribute("dd_skq",sessionData);
        log.info("sessionId:" + session.getId());
        log.info("存储的内容是：" + session.getAttribute("dd_skq"));
        return result;

    }

    @Override
    public Resource queryAlgo(MultipartFile file, Map<Object, Object> params, HttpServletRequest request) throws Exception {
        DD_SKQLibrary.DD_SKQInterface instance = DD_SKQLibrary.DD_SKQInterface.INSTANCE;
        // 获取session存放的数据
        HttpSession session = request.getSession();
        Map<String,Object> session_data = (Map<String, Object>) session.getAttribute("dd_skq");
        if(session_data == null) {
            throw new Exception("session存放数据失效，重新上传");
        }
        Path filePath = ToolUtils.saveQueryFile(file,params,"dd_skq");
        // 执行查询算法
        int result = instance.query_algo((DD_SKQLibrary.Structures.DD_SKQDataSet) session_data.get("data"),
                filePath.toString(),
                filePath.getParent().resolve("search_res.txt").toString());

        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("dd_skq查询失败");
        }else{
            log.info("dd_skq查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }
}
