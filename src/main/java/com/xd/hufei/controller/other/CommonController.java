package com.xd.hufei.controller.other;


import com.xd.hufei.dto.other.TableColumn;
import com.xd.hufei.services.other.CommonService;
import com.xd.hufei.utils.ETPSSConstant;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@Api("统一处理的controller类")
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Autowired
    private CommonService commonService;

    @ApiOperation("负责脱敏与数据加密")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "返回创建成功表的信息，处理数据位置：" +
                    "mysql://root:T3stPassw0rd1@10.10.55.25:3306/test + newTable", response = String.class),

            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/desensitization/eTPSS")
    public ResponseEntity<String> processingETPSS(@ApiParam(value = "未做隐私增强的mysql数据库的表",name = "Address",
            defaultValue = "null",example = "testTableName")
                                                      @RequestParam("Address")
                                                  String table,@ApiParam(value = "数据库的url",name = "url"
    ,defaultValue = "null",example = "mysql://root:T3stPassw0rd1@10.10.55.25:3306/test(用户名和密码不要包含特殊字符)"
    ) @RequestParam("url")String url){
        try{
            // 动态的切换数据源
            if(commonService.switchDataSources(url) == ETPSSConstant.ERROR){
                throw new IllegalArgumentException("动态切换数据源错误");
            }
            // 进行读取数据源
            List<Map<String,Object>> data = commonService.readTableGetList(table);
            if(data == null){
                throw new IllegalArgumentException("从" + table + "读取数据失败");
            }
            log.info("tableName:"+table+"-读取数据成功");
            // 处理数据，获取表的结构
            commonService.ToDataDesensitization(data);
            log.info("tableName:"+table+"-数据处理成功");
            // 分离出来dataBaseName
            String dataBaseName = url.substring(url.lastIndexOf('/') + 1);
            List<TableColumn> tableStructure = commonService.getTableStructure(dataBaseName, table);
            log.info("tableName:"+table+"-获取了表的结构");
            // 切换本地数据库
            commonService.switchDataSources("default");

            String newTable = commonService.writeTable(table, tableStructure, data);
            if(newTable == null){
                throw new IOException("创建表写入内容错误");
            }
            log.info("tableName:"+newTable+"-创建表写入数据成功");
            return ResponseEntity.ok(newTable);
        }catch (Exception e){
            e.printStackTrace();
            if(e instanceof IllegalArgumentException){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("传入参数错误");
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("写入数据到服务器错误");
            }
        }
    }

}
