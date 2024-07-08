package com.xd.hufei.services.sccomputing;

import org.springframework.core.io.Resource;

import java.nio.file.Path;

/**
 * @author  heqi
 * @date 08/07/2024
 * @desc: 安全协同计算的Service模块
 * */

public interface ScComputingService {

    // 计算均值的函数
    Resource avg(Path filePath) throws Exception;

    // 数据比较
    Resource compare(Path filePath) throws Exception;

    // 相等性测试
    Resource equal(Path filePath) throws Exception;

    // min 最小值
    Resource min_max(Path filePath)throws Exception ;

    // 包含性关系
    Resource include(Path filePath)throws Exception;

    // 范围相交
    Resource intersect(Path filePath)throws Exception;

    // 求内积
    Resource inner_product(Path filePath)throws Exception;

    // 求欧式距离
    Resource distance(Path filePath)throws Exception;

    // 将数据分箱
    Resource split(Path filePath)throws Exception;

    // 计算频率
    Resource frequency(Path filePath)throws Exception;
}
