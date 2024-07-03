package com.xd.hufei.listener;

import com.xd.hufei.Library.SSQLibrary;
import com.xd.hufei.Library.SkylineLibrary;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.File;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("销毁的sessionID：" + se.getSession().getId());
        SkylineLibrary.SkylineInterface skyline = SkylineLibrary.SkylineInterface.INSTANCE;
        SSQLibrary.SSQInterface ssq = SSQLibrary.SSQInterface.INSTANCE;
        // 获取所有属性名的枚举
        Enumeration<String> attributeNames = se.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String attributeName =  attributeNames.nextElement();
            Map<String,Object> session_data = (Map) se.getSession().getAttribute(attributeName);
            if(session_data != null){
                // 不同的存储进行不同的清空
                switch (attributeName){
                    case "skyline":
                        skyline.free_algo((SkylineLibrary.Structures.skyline_data) session_data.get("data"), (SkylineLibrary.Structures.rtree) session_data.get("rtree"));
                        // 设置为空，情况内容
                        se.getSession().setAttribute("skyline",null);
                        break;
                    case "ssq":
                        // 调用algo清空C申请的内存，不然内存泄露
                        ssq.free_algo((SSQLibrary.Structures.SSQ_data) session_data.get("data"),
                                (SSQLibrary.Structures.kd_tree) session_data.get("tree"),
                                (SSQLibrary.Structures.SSQ_data) session_data.get("kArr"));
                        // 设置为空，情况内容
                        se.getSession().setAttribute("ssq",null);
                        break;
                    default:
                        log.error("无效的session存储，key：" + attributeName);
                        break;
                }
            }
        }
    }
}
