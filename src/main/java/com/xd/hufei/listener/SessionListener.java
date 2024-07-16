package com.xd.hufei.listener;

import com.xd.hufei.Library.*;
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
        RSQLibrary.RSQInterface rsq = RSQLibrary.RSQInterface.INSTANCE;
        RangeSearchLibrary.RangeSearchInterface rangeSearch = RangeSearchLibrary.RangeSearchInterface.INSTANCE;
        SKQLibrary.SKQInterface skq = SKQLibrary.SKQInterface.INSTANCE;

        /*-----------------------下面是分布式的内容-------------------------*/
        DD_SkylineLibrary.DD_SKYLINEInterface ddSkyline = DD_SkylineLibrary.DD_SKYLINEInterface.INSTANCE;
        DD_SKQLibrary.DD_SKQInterface ddSkq = DD_SKQLibrary.DD_SKQInterface.INSTANCE;
        DRQLibrary.DRQInterface drq = DRQLibrary.DRQInterface.INSTANCE;


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
                    case "rsq":
                        rsq.free_algo(( RSQLibrary.Structures.RSQ_data) session_data.get("data"),
                                ( RSQLibrary.Structures.mr_tree) session_data.get("tree"));
                        // 设置为空，情况内容
                        se.getSession().setAttribute("rsq",null);
                        break;
                    case "range_search":
                        rangeSearch.free_algo((RangeSearchLibrary.Structures.PtreeB_data) session_data.get("data"),
                                (RangeSearchLibrary.Structures.kd_tree) session_data.get("tree"));
                        se.getSession().setAttribute("range_search",null);
                        break;
                    case "skq":
                        // 调用algo清空C申请的内存，不然内存泄露
                        skq.free_algo((SKQLibrary.Structures.DataOwner) session_data.get("data"));
                        // 重新调用init_constant
                        skq.init_constant();
                        // 设置为空，情况内容
                        se.getSession().setAttribute("skq",null);
                        break;
                    case "dd_skyline":
                        ddSkyline.free_algo((DD_SkylineLibrary.Structures.DrqDataSet) session_data.get("data"));
                        se.getSession().setAttribute("dd_skyline",null);
                        break;
                    case "dd_skq":
                        ddSkq.free_algo((DD_SKQLibrary.Structures.DD_SKQDataSet) session_data.get("data"));
                        se.getSession().setAttribute("dd_skq",null);
                        break;
                    case "dd_drq":
                        drq.free_algo((DRQLibrary.Structures.DrqDataSet) session_data.get("data"));
                        se.getSession().setAttribute("dd_drq",null);
                        break;
                    case"a":

                        break;
                    case "b":

                        break;

                    default:
                        log.error("不能删除的session存储key，key：" + attributeName);
                        break;
                }
            }
        }
    }
}
