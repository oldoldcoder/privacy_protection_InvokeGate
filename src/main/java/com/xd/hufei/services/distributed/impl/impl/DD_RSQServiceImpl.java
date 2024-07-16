package com.xd.hufei.services.distributed.impl.impl;

import com.xd.hufei.Library.DD_RSQLibrary;
import com.xd.hufei.services.distributed.impl.DD_RSQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
@Slf4j
public class DD_RSQServiceImpl implements DD_RSQService {

    @Override
    public Map<Object, Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception {
        DD_RSQLibrary.DD_RSQInterface instance = DD_RSQLibrary.DD_RSQInterface.INSTANCE;
        return null;

    }

    @Override
    public Resource queryAlgo(MultipartFile file, Map<Object, Object> params, HttpServletRequest request) throws Exception {
        DD_RSQLibrary.DD_RSQInterface instance = DD_RSQLibrary.DD_RSQInterface.INSTANCE;
        return null;
    }
}
