package com.xd.hufei.services.distributed.impl.impl;

import com.xd.hufei.Library.DD_SSQLibrary;
import com.xd.hufei.services.distributed.impl.DD_RSQService;
import com.xd.hufei.services.distributed.impl.DD_SSQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
@Slf4j
public class DD_SSQServiceImpl implements DD_SSQService {

    @Override
    public Map<Object, Object> initAlgo(MultipartFile file, HttpServletRequest request) throws Exception {
        DD_SSQLibrary.DD_SSQInterface instance = DD_SSQLibrary.DD_SSQInterface.INSTANCE;

        return null;
    }

    @Override
    public Resource queryAlgo(MultipartFile file, Map<Object, Object> params, HttpServletRequest request) throws Exception {
        DD_SSQLibrary.DD_SSQInterface instance = DD_SSQLibrary.DD_SSQInterface.INSTANCE;

        return null;
    }
}
