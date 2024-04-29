package com.xd.hufei.controller.notDistributed;

import com.xd.hufei.controller.BaseController;
import com.xd.hufei.services.notDistributed.RSQService;
import com.xd.hufei.utils.PathResolveUtils;
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
import javax.annotation.PreDestroy;
import java.io.IOException;

@RestController
@RequestMapping("/DN/rsq")
@Api("非分布式-rsq查询算法的controller内容")
@Slf4j
public class RSQController extends BaseController {

    @Value("${port.notDistributed.rsq}")
    private String PORT;

    @Value("${port.address}")
    private String ADDRESS;

    @Autowired
    RSQService service;

    @Autowired
    PathResolveUtils pathResolveUtils;

    @PostConstruct
    // 负责bean初始化的时候建立长连接
    public void init(){
        // 初始化内容
        this.init(PORT,ADDRESS);
    }


    @ApiOperation("非分布式-rsq数据文件上传，同时初始化构建")
    @PostMapping("/init")
    public ResponseEntity<String> init(@ApiParam(value = "上传的文件,格式为n d\n后续为d行" +
            "n列",required = true) @RequestParam("file")MultipartFile file){
        try {
            // 保存date数据
            service.saveFile(file,pathResolveUtils.pathRSQData);
            String resp = service.sendInit2C(this.out,this.in);
            // 假设成功处理后返回一个成功消息
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            // 如果发生异常，则返回一个错误消息
            String errorMessage = "错误发生:" + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
    @ApiOperation("非分布式-rsq查询文件上传,同时查询")
    @PostMapping("/query")
    public ResponseEntity<String> query(@ApiParam(value = "上传的文件,具体为查询的参数"
            ,required = true) @RequestParam("file")MultipartFile file){
        try {
            // 保存date数据
            service.saveFile(file,pathResolveUtils.pathRSQQueryFile);
            String resp = service.sendQuery2C(this.out,this.in);
            //TODO 对于成功之后的文件处理操作

            // 假设成功处理后返回一个成功消息
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            // 如果发生异常，则返回一个错误消息
            String errorMessage = "错误发生:" + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @ApiOperation("非分布式-rsq停止程序，销毁内容")
    @PostMapping("/stop")
    public ResponseEntity<String> stop(){
        try {
            String resp = service.sendEnd2C(this.out,this.in);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            // 如果发生异常，则返回一个错误消息
            String errorMessage = "错误发生:" + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}
