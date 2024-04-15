package com.xd.hufei.controller.other;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("统一处理的controller类")
@RequestMapping("/common")
public class CommonController {

    @ApiOperation("负责脱敏与数据加密")
    @PostMapping("/eTPSS")
    public ResponseEntity<String> processingETPSS(){
        try{

            
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }

}
