package com.xd.hufei.services.notDistributed;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @desc:火影忍者卡卡西copy的代码罢了
 * */
public interface RSQService {
    Map<Object,Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception;
    // 查询，返回对应文件到前端
    Resource queryAlgo(MultipartFile file, Map<Object,Object>params, HttpServletRequest request) throws Exception;
}
