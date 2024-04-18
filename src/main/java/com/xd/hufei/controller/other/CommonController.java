package com.xd.hufei.controller.other;

import com.xd.hufei.dto.other.DesensitizedData;
import com.xd.hufei.services.other.CommonService;
import com.xd.hufei.utils.PathResolveUtils;
import com.xd.hufei.utils.StatusUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
@Api("统一处理的controller类")
@RequestMapping("/common")
public class CommonController {

    @Autowired
    CommonService commonService;

    @Autowired
    PathResolveUtils pathResolveUtils;

    @ApiOperation("负责脱敏与数据加密")
    @PostMapping("/desensitization/eTPSS")
    public ResponseEntity<String> processingETPSS(@ApiParam("未做隐私增强的mysql数据库的表") @RequestParam("Address")
                                                  String table,@ApiParam("数据库的url") @RequestParam("url")String url){
        try{
            // 动态的切换数据源
            if(commonService.switchDataSources(url) == StatusUtils.ERROR){
                throw new Exception("动态切换数据源错误");
            }
            // 进行读取数据源
            List<DesensitizedData> bigIntegers = commonService.readTableGetList(table);
            if(bigIntegers == null){
                throw new Exception("从" + table + "读取数据失败");
            }
            // 写文件到指定位置
            if(commonService.saveFile(pathResolveUtils.pathETPSSData,bigIntegers) == StatusUtils.ERROR){
                throw new Exception("写文件到指定位置错误");
            }
            // 执行得到结果文件
            if(commonService.execETPSS(pathResolveUtils.pathETPSSCmd) == StatusUtils.ERROR){
                throw new Exception("执行eTPSS失败");
            }
            // 切换本地数据库
            commonService.switchDataSources("default");
            // 读取结果文件，写入
            String createTableName = commonService.readFileAndWriteTable(pathResolveUtils.pathETPSSRes);
            if(createTableName == null){
                throw new Exception("创建表写入内容错误");
            }
            return ResponseEntity.ok(createTableName);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }

}
