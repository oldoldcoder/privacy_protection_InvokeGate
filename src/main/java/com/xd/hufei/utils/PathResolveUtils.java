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
    @Value("${path.eTPSS.cmd}")
    public  String pathETPSSCmd;

    @Value("${path.eTPSS.data}")
    public String pathETPSSData;

    @Value("${path.eTPSS.res}")
    public String pathETPSSRes;

    @Value("${path.notDistributed.range_search.data}")
    public String pathRangeSearchData;

    @Value("${path.notDistributed.range_search.res}")
    public String pathRangeSearchRes;
    @Value("${path.notDistributed.range_search.queryFile}")
    public String pathRangeSearchQueryFile;


}
