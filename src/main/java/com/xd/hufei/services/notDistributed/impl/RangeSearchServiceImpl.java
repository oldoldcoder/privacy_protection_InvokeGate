package com.xd.hufei.services.notDistributed.impl;

import com.xd.hufei.Library.RangeSearchLibrary;
import com.xd.hufei.Library.SSQLibrary;
import com.xd.hufei.services.notDistributed.RangeSearchService;
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
public class RangeSearchServiceImpl implements RangeSearchService {

    @Override
    public Map<Object, Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception {
        RangeSearchLibrary.RangeSearchInterface instance =  RangeSearchLibrary.RangeSearchInterface.INSTANCE;

        // 删除之前session的数据
        log.info("检测session之前的数据");
        HttpSession session = request.getSession();
        Map<String,Object> session_data = (Map<String, Object>) session.getAttribute("range_search");
        if(session_data != null){
            // 调用algo清空C申请的内存，不然内存泄露
            instance.free_algo((RangeSearchLibrary.Structures.PtreeB_data) session_data.get("data"),
                    (RangeSearchLibrary.Structures.kd_tree) session_data.get("tree"));
            // 设置为空，情况内容
            session.setAttribute("range_search",null);
        }

        // 保存文件到SSQ
        Path filePath = ToolUtils.saveFile(file,"range_search");

        Map<Object, Object> result = ToolUtils.fillResultMap(filePath);

        RangeSearchLibrary.Structures.PtreeB_data data = new RangeSearchLibrary.Structures.PtreeB_data();

        RangeSearchLibrary.Structures.kd_tree tree = new RangeSearchLibrary.Structures.kd_tree();

        // 初始化算法
        int initResult = instance.init_algo(filePath.toString(), data, tree);
        if (initResult !=  ETPSSConstant.SUCCESS) {
            System.err.println("Failed to initialize algorithm");
            throw new Exception("range_search初始化构建失败");
        }else{
            log.info("初始化构建成功");
        }
        Map<String,Object> sessionData = new HashMap<>();
        sessionData.put("data",data);
        sessionData.put("tree",tree);
        // 数据存放到session之中去
        session.setAttribute("range_search",sessionData);
        log.info("sessionId:" + session.getId());
        log.info("存储的内容是：" + session.getAttribute("range_search"));
        return result;
    }

    @Override
    public Resource queryAlgo(MultipartFile file, Map<Object, Object> params, HttpServletRequest request) throws Exception {
        RangeSearchLibrary.RangeSearchInterface instance = RangeSearchLibrary.RangeSearchInterface.INSTANCE;
        // 获取session存放的数据
        HttpSession session = request.getSession();
        Map<String,Object> session_data = (Map<String, Object>) session.getAttribute("range_search");
        if(session_data == null) {
            throw new Exception("session存放数据失效，重新上传");
        }
        Path filePath = ToolUtils.saveQueryFile(file,params,"range_search");
        // 执行查询算法
        int result = instance.query_algo((RangeSearchLibrary.Structures.PtreeB_data) session_data.get("data"),
                (RangeSearchLibrary.Structures.kd_tree) session_data.get("tree"),
                filePath.toString(),
                filePath.getParent().resolve("search_res.txt").toString());

        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("range_search查询失败");
        }else{
            log.info("range_search查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }
}
