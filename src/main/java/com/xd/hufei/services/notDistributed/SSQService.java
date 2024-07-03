package com.xd.hufei.services.notDistributed;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author heqi
 * @date 02/07/2024
 * */
public interface SSQService {

    Map<Object,Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception;
    // 查询，返回对应文件到前端
    Resource queryAlgo(MultipartFile file, Map<Object,Object>params, HttpServletRequest request) throws Exception;
}
