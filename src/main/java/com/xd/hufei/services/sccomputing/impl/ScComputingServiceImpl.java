package com.xd.hufei.services.sccomputing.impl;

import com.xd.hufei.Library.ScComputingLibrary;
import com.xd.hufei.services.sccomputing.ScComputingService;
import com.xd.hufei.utils.ETPSSConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
@Slf4j
public class ScComputingServiceImpl implements ScComputingService {

    private static final ScComputingLibrary.ScComputingInterface instance = ScComputingLibrary.ScComputingInterface.INSTANCE;

    @Override
    public Resource avg(Path filePath) throws Exception{
        int result = instance.deal("avg",filePath.toString(),filePath.getParent().resolve("search_res.txt").toString());
        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("安全协同计算AVG查询失败");
        }else{
            log.info("安全协同计算AVG查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }

    @Override
    public Resource compare(Path filePath) throws Exception{
        int result = instance.deal("compare",filePath.toString(),filePath.getParent().resolve("search_res.txt").toString());
        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("安全协同计算compare查询失败");
        }else{
            log.info("安全协同计算compare查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }

    @Override
    public Resource equal(Path filePath) throws Exception{
        int result = instance.deal("equal",filePath.toString(),filePath.getParent().resolve("search_res.txt").toString());
        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("安全协同计算equal查询失败");
        }else{
            log.info("安全协同计算equal查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }

    @Override
    public Resource min_max(Path filePath) throws Exception{
        int result = instance.deal("min_max",filePath.toString(),filePath.getParent().resolve("search_res.txt").toString());
        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("安全协同计算min_max查询失败");
        }else{
            log.info("安全协同计算min_max查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }


    @Override
    public Resource include(Path filePath) throws Exception{
        int result = instance.deal("include",filePath.toString(),filePath.getParent().resolve("search_res.txt").toString());
        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("安全协同计算include查询失败");
        }else{
            log.info("安全协同计算include查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }

    @Override
    public Resource intersect(Path filePath) throws Exception{
        int result = instance.deal("intersect",filePath.toString(),filePath.getParent().resolve("search_res.txt").toString());
        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("安全协同计算intersect查询失败");
        }else{
            log.info("安全协同计算intersect查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }

    @Override
    public Resource inner_product(Path filePath) throws Exception{
        int result = instance.deal("inner_product",filePath.toString(),filePath.getParent().resolve("search_res.txt").toString());
        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("安全协同计算inner_product查询失败");
        }else{
            log.info("安全协同计算inner_product查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }

    @Override
    public Resource distance(Path filePath) throws Exception{
        int result = instance.deal("distance",filePath.toString(),filePath.getParent().resolve("search_res.txt").toString());
        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("安全协同计算distance查询失败");
        }else{
            log.info("安全协同计算distance查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }

    @Override
    public Resource split(Path filePath) throws Exception{
        int result = instance.deal("split",filePath.toString(),filePath.getParent().resolve("search_res.txt").toString());
        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("安全协同计算split查询失败");
        }else{
            log.info("安全协同计算split查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }

    @Override
    public Resource frequency(Path filePath) throws Exception{
        int result = instance.deal("frequency",filePath.toString(),filePath.getParent().resolve("search_res.txt").toString());
        if (result != ETPSSConstant.SUCCESS) {
            throw new Exception("安全协同计算frequency查询失败");
        }else{
            log.info("安全协同计算frequency查询成功，文件路径为：" + filePath.getParent().resolve("search_res.txt"));
        }

        return new UrlResource(filePath.getParent().resolve("search_res.txt").toUri());
    }
}
