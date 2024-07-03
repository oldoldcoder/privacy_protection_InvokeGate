package com.xd.hufei.controller.notDistributed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/ND/range_search")
@Api("非分布式-kdtree范围查询查询算法的controller")
@Slf4j
public class RangeSearchController {


    @ApiOperation("非分布式-range_search数据文件上传，同时初始化构建")
    @PostMapping("/init")
    public ResponseEntity<String> init(@ApiParam(value = "上传的文件,格式为n d\n后续为d行" +
            "n列",required = true) @RequestParam("file")MultipartFile file){
        return null;
    }
    @ApiOperation("非分布式-range_search查询文件上传,同时查询")
    @PostMapping("/query")
    public ResponseEntity<String> query(@ApiParam(value = "上传的文件,具体为查询的参数"
            ,required = true) @RequestParam("file")MultipartFile file){

        return null;
    }

    @ApiOperation("非分布式-range_search停止程序，销毁内容")
    @PostMapping("/stop")
    public ResponseEntity<String> stop(){
        return null;
    }



}
