package com.xd.hufei.controller.notDistributed;

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
import java.io.IOException;
import java.net.Socket;

@RestController
@RequestMapping("/ND/range_search")
@Api("非分布式-kdtree查询算法的controller")
@Slf4j
public class RangeSearchController {
    /*// 对应端口
    @Value("${port.notDistributed.range_search.port}")
    private String PORT;
    // 对应地址
    @Value("${port.notDistributed.range_search.address}")
    private String ADDRESS;

    // 存储数据的后缀
    private static String DATA_FILE = "/RANGE_FILE.txt";
    // 查询数据的后缀
    private static String QUERY_FILE = "/QUERY_FILE.txt";
    // 结果的后缀
    private static String RES_FILE = "/RES_FILE.txt";

    @Autowired
    RangeSearchService service;
    @Autowired
    PathResolveUtils pathResolveUtils;
    private Socket socket;
    // 负责bean初始化的时候建立长连接
    @PostConstruct
    private void init() {
        try {
            socket = new Socket(ADDRESS, Integer.parseInt(PORT));
        }catch (Exception e){
            e.printStackTrace();
            log.error("无法连接到非分布式range_search服务");
        }
    }

    // 负责bean销毁的时候释放长连接
    @PreDestroy
    private void shutdown() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(" 关闭Socket连接失败");
        }
    }

    @ApiOperation("非分布式-range_search数据文件上传")
    @PostMapping("/upload_data_file")
    public ResponseEntity<String> upload_data_file(@ApiParam(value = "上传的文件,格式为n d\n后续为d行" +
            "n列",required = true) @RequestParam("file")MultipartFile file){
        try {
            // 进行socket通信
            String result = service.saveFile(file,pathResolveUtils.RANGE_SEARCH_PATH + DATA_FILE);
            // 假设成功处理后返回一个成功消息
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 如果发生异常，则返回一个错误消息
            String errorMessage = "An error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @ApiOperation("非分布式-range_search查询文件上传")
    @PostMapping("/upload_query_file")
    public ResponseEntity<String> upload_query_file(@ApiParam(value = "上传的文件",required = true) @RequestParam("file")MultipartFile file){
        try {
            // 进行socket通信
            String result = service.saveFile(file,pathResolveUtils.RANGE_SEARCH_PATH + QUERY_FILE);
            // 假设成功处理后返回一个成功消息
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 如果发生异常，则返回一个错误消息
            String errorMessage = "An error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @ApiOperation("非分布式-range_search进行查询")
    @PostMapping("/query")
    public ResponseEntity<String> query(){
        try {
            // 进行socket通信
            String result = service.saveFile(null,pathResolveUtils.RANGE_SEARCH_PATH + QUERY_FILE);
            // 假设成功处理后返回一个成功消息
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 如果发生异常，则返回一个错误消息
            String errorMessage = "An error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }*/

}
