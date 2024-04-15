package com.xd.hufei.services.notDistributed.impl;

import com.xd.hufei.services.notDistributed.RangeSearchService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RangeSearchServiceImpl implements RangeSearchService {
    @Override
    public String saveFile(MultipartFile file, String path) {

        return null;
    }

    @Override
    public String sendInit2C() {
        return null;
    }

    @Override
    public String sendQuery2C() {
        return null;
    }

    @Override
    public String sendFree2C() {
        return null;
    }

    @Override
    public String sendEnd2C() {
        return null;
    }
}
