package com.xd.hufei;

import com.xd.hufei.listener.SessionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class GateApplication {

    public static void main(String[] args) {
        // 获取操作系统名称
        String osName = System.getProperty("os.name").toLowerCase();

        // 如果是在Linux系统下，设置 jna.library.path 属性
        /*在 Linux 上，JNA 会在库名称前加上lib，例如，它会尝试加载libdd_cComputing.so。
        在 Windows 上，JNA 会在库名称后加上.dll，例如，它会尝试加载dd_cComputing.dll。*/
        if (osName.contains("linux")) {
            System.setProperty("jna.library.path", "/root/project4/lib");
        }
        SpringApplication.run(GateApplication.class, args);
    }
    // 注册监听session失效清理数据的监听器
    @Bean
    public ServletListenerRegistrationBean<SessionListener> sessionListener() {
        return new ServletListenerRegistrationBean<>(new SessionListener());
    }
}
