package com.xd.hufei.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

// 检查工具集
@Component
public class CheckUtils {
    // 检查上传文件是否非空：
    private boolean checkFileFormat(MultipartFile file){
        if(file.isEmpty()){
            return false;
        }
        // 其他检验

        return true;
    }
}
