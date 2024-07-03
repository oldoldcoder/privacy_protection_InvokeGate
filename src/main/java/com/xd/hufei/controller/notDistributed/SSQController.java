package com.xd.hufei.controller.notDistributed;


import com.xd.hufei.services.notDistributed.SSQService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/ND/ssq")
@Api("非分布式-相似性查询查询算法的controller内容")
@Slf4j
public class SSQController {

    @Autowired
    SSQService ssqService;

    @ApiOperation("非分布式-ssq数据文件上传，同时初始化构建")
    @PostMapping("/initData")
    public ResponseEntity<Object> init(@ApiParam(value = "上传的文件,格式为n d\n后续为d行" +
            "n列",required = true) @RequestParam("file")MultipartFile file, HttpServletRequest request){

        try {
            Map<Object, Object> resultMap = ssqService.initAlgo(file, request);
            return ResponseEntity.ok(resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
    @ApiOperation("非分布式-ssq查询文件上传,同时查询")
    @PostMapping("/query")
    public ResponseEntity<Object> query(@ApiParam(value = "查询的文件，按照标准格式上传，适合复杂的格式"
            ,required = false)
                                            @RequestParam(value = "file", required = false) MultipartFile file,
                                        @ApiParam(value = "查询参数，按照简单格式上传，适合简单的格式"
                                                ,required = false)
                                            @RequestParam(value = "params", required = false) Map<Object, Object> params,
                                        HttpServletRequest request){
        try {
            Resource resource = ssqService.queryAlgo(file,params, request);
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
