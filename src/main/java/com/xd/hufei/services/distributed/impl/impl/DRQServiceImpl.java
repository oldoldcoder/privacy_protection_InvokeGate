package com.xd.hufei.services.distributed.impl.impl;

import com.xd.hufei.Library.DRQLibrary;
import com.xd.hufei.Library.RangeSearchLibrary;
import com.xd.hufei.services.distributed.impl.DRQService;
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
public class DRQServiceImpl implements DRQService {

    @Override
    public Map<Object, Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception {
        DRQLibrary.DRQInterface instance = DRQLibrary.DRQInterface.INSTANCE;

        // 删除之前session的数据
        log.info("检测session之前的数据");
        HttpSession session = request.getSession();
        Map<String,Object> session_data = (Map<String, Object>) session.getAttribute("dd_drq");
        if(session_data != null){
            // 调用algo清空C申请的内存，不然内存泄露
            instance.free_algo((DRQLibrary.Structures.DrqDataSet) session_data.get("data"));
            // 设置为空，情况内容
            session.setAttribute("dd_drq",null);
        }

        // 保存文件到SSQ
        Path filePath = ToolUtils.saveFile(file,"dd_drq");

        Map<Object, Object> result = ToolUtils.fillResultMap(filePath);

        DRQLibrary.Structures.DrqDataSet data = new DRQLibrary.Structures.DrqDataSet();


        // 初始化算法
        int initResult = instance.init_algo(data,filePath.toString());
        if (initResult !=  ETPSSConstant.SUCCESS) {
            System.err.println("Failed to initialize algorithm");
            throw new Exception("dd_drq初始化构建失败");
        }else{
            log.info("初始化构建成功");
        }
        Map<String,Object> sessionData = new HashMap<>();
        sessionData.put("data",data);
        // 数据存放到session之中去
        session.setAttribute("dd_drq",sessionData);
        log.info("sessionId:" + session.getId());
        log.info("存储的内容是：" + session.getAttribute("dd_drq"));
        return result;

    }

    @Override
    public Resource queryAlgo(MultipartFile file, Map<Object, Object> params, HttpServletRequest request) throws Exception {
        DRQLibrary.DRQInterface instance = DRQLibrary.DRQInterface.INSTANCE;
        // 获取session存放的数据
        HttpSession session = request.getSession();
        Map<String,Object> session_data = (Map<String, Object>) session.getAttribute("dd_drq");
        if(session_data == null) {
            throw new Exception("session存放数据失效，重新上传");
        }
        Path filePath = ToolUtils.saveQueryFile(file,params,"dd_drq");
        // 执行查询算法
        int result = instance.query_algo((DRQLibrary.Structures.DrqDataSet) session_data.get("data"),
                filePath.toString(),
                filePath.getParent().resolve("search_res.txt").toString());

        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("dd_drq查询失败");
        }else{
            log.info("dd_drq查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }
}