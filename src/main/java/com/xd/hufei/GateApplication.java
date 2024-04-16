package com.xd.hufei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class GateApplication {

    public static void main(String[] args) {
        // 命令行方式让我们的c语言程序启动
        try {
            // Runtime.getRuntime().exec("");
        }catch (Exception e){
            log.error("c程序启动失败，服务不可用，结束运行");
        }
        SpringApplication.run(GateApplication.class, args);
    }

}
