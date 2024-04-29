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

    @Value("${path.notDistributed.rsq.data}")
    public String pathRSQData;
    @Value("${path.notDistributed.rsq.res}")
    public String pathRSQRes;
    @Value("${path.notDistributed.rsq.queryFile}")
    public String pathRSQQueryFile;

    @Value("${path.notDistributed.skyline.data}")
    public String pathSkyLineData;
    @Value("${path.notDistributed.skyline.res}")
    public String pathSkyLineRes;
    @Value("${path.notDistributed.skyline.queryFile}")
    public String pathSkyLineQueryFile;

    @Value("${path.notDistributed.skq.data}")
    public String pathSKQData;
    @Value("${path.notDistributed.skq.res}")
    public String pathSKQRes;
    @Value("${path.notDistributed.skq.queryFile}")
    public String pathSKQQueryFile;

    @Value("${path.notDistributed.ssq.data}")
    public String pathSSQData;
    @Value("${path.notDistributed.ssq.res}")
    public String pathSSQRes;
    @Value("${path.notDistributed.ssq.queryFile}")
    public String pathSSQQueryFile;

}
