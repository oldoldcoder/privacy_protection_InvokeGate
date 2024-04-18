package com.xd.hufei.controller.notDistributed;

import com.xd.hufei.controller.BaseController;
import com.xd.hufei.services.notDistributed.RangeSearchService;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/ND/range_search")
@Api("非分布式-kdtree查询算法的controller")
@Slf4j
public class RangeSearchController extends BaseController {
    // 对应端口
    @Value("${port.notDistributed.range_search.port}")
    private String PORT;
    // 对应地址
    @Value("${port.notDistributed.range_search.address}")
    private String ADDRESS;


    @Autowired
    RangeSearchService service;
    @Autowired
    PathResolveUtils pathResolveUtils;

    @PostConstruct
    // 负责bean初始化的时候建立长连接
    public void init(){
        // 初始化内容
        this.init(PORT,ADDRESS);
    }

    // 负责bean销毁的时候释放长连接
    @PreDestroy
    private void shutdown() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                this.in.close();
                this.out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(" 关闭Socket连接失败");
        }
    }

    @ApiOperation("非分布式-range_search数据文件上传，同时初始化构建")
    @PostMapping("/init")
    public ResponseEntity<String> init(@ApiParam(value = "上传的文件,格式为n d\n后续为d行" +
            "n列",required = true) @RequestParam("file")MultipartFile file){
        try {
            // 保存date数据
            //service.saveFile(file,pathResolveUtils.pathRangeSearchData);
            // 发送构建命令
            this.out.print("init");
            String resp = this.in.readLine();
            log.info("接受到C程序返回消息：" + resp);
            if(!resp.contains("ok")){
                throw new Exception("c程序init失败");
            }
            // 假设成功处理后返回一个成功消息
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            // 如果发生异常，则返回一个错误消息
            String errorMessage = "错误发生:" + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
    @ApiOperation("非分布式-range_search查询文件上传,同时查询")
    @PostMapping("/query")
    public ResponseEntity<String> query(@ApiParam(value = "上传的文件,具体为查询的参数"
            ,required = true) @RequestParam("file")MultipartFile file){
        try {
            // 保存date数据
            //service.saveFile(file,pathResolveUtils.pathRangeSearchQueryFile);
            // 发送构建命令
            this.out.print("query");
            String resp = this.in.readLine();
            log.info("接受到C程序返回消息：" + resp);
            if(!resp.contains("ok")){
                throw new Exception("c程序init失败");
            }
            // 假设成功处理后返回一个成功消息
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            // 如果发生异常，则返回一个错误消息
            String errorMessage = "错误发生:" + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @ApiOperation("非分布式-range_search停止程序，销毁内容")
    @PostMapping("/stop")
    public ResponseEntity<String> stop(){
        try {
            this.out.print("stop");
            String resp = this.in.readLine();
            log.info("接受到C程序返回消息：" + resp);
            if(!resp.contains("stopped")){
                throw new Exception("c程序init失败");
            }
            // 假设成功处理后返回一个成功消息
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            // 如果发生异常，则返回一个错误消息
            String errorMessage = "错误发生:" + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }



}
