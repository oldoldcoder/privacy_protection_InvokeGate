package com.xd.hufei.controller.notDistributed;


import com.xd.hufei.services.notDistributed.SkylineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/ND/skyline")
@Api("非分布式-skyline查询算法的controller内容")
@Slf4j
public class SkylineController {

    @Autowired
    SkylineService skylineService;

    @ApiOperation("非分布式-skyline数据文件上传，同时初始化构建")
    @PostMapping("/initData")
    public ResponseEntity<Object> init(@ApiParam(value = "上传的文件,格式为n d\n后续为d行" +
            "n列",required = true) @RequestParam("file") MultipartFile file, HttpServletRequest request){
        try {
            Map<Object, Object> resultMap = skylineService.initAlgo(file, request);
            return ResponseEntity.ok(resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
    @ApiOperation("非分布式-skyline查询文件上传,同时查询")
    @PostMapping("/query")
    public ResponseEntity<Object> query(@ApiParam(value = "一次查询的参数，将会保存params为文件"
            ,required = true) @RequestParam Map<Object, Object> params,
                                        HttpServletRequest request){
        try {
            Resource resource = skylineService.queryAlgo(params, request);
            // 文件可达，返回我的文件
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 未找到文件
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found: " + resource.getFilename());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }

}