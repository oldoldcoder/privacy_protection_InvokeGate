package com.xd.hufei.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// 存放文件解析的路径
@Component
public class PathResolveUtils {
    // 配置路径属性值
    @Value("${path.distributed.drs}")
    public  String DRS_PATH;

    @Value("${path.distributed.drsq}")
    public  String DRSQ_PATH;

    @Value("${path.notDistributed.range_search}")
    public  String RANGE_SEARCH_PATH;

    @Value("${path.notDistributed.rsq}")
    public  String RSQ_PATH;

    @Value("${path.notDistributed.skyline}")
    public  String SKYLINE_PATH;

    @Value("${path.notDistributed.skq}")
    public  String SKQ_PATH;

    @Value("${path.notDistributed.ssq}")
    public  String SSQ_PATH;
}
