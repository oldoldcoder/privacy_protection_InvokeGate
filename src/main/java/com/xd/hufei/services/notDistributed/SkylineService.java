package com.xd.hufei.services.notDistributed;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author heqi
 * @date 01/07/2024
 * */

public interface SkylineService {

    // 保存文件，同时预训练，给出分析结果值 *上传新文件训练应该销毁之前的数据*
    Map<Object,Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception;
    // 查询，返回对应文件到前端
    Resource queryAlgo(Map<Object,Object>params,HttpServletRequest request) throws Exception;
}
