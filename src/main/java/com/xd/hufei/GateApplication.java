package com.xd.hufei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class GateApplication implements ApplicationRunner, ExitCodeGenerator {

    @Value("${cmd}")
    private String[] cmd;

    private Process[] proc;
    public static void main(String[] args) {
        SpringApplication.run(GateApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        proc = new Process[cmd.length];
//        int i = 0 ;
//        for(String s : cmd){
//           proc[i++] = Runtime.getRuntime().exec(cmd);
//        }
    }

    @Override
    public int getExitCode() {
//        for(Process process : proc){
//            if (process != null && process.isAlive()) {
//                // 发送 SIGTERM 信号，请求进程正常关闭
//                process.destroy();
//
//                try {
//                    // 等待进程正常关闭，最多等待 5 秒
//                    if (!process.waitFor(60, TimeUnit.SECONDS)) {
//                        // 如果进程没有在 5 秒内正常关闭，强制终止它
//                        process.destroyForcibly();
//                    }
//                } catch (InterruptedException e) {
//                    // 处理异常
//                    e.printStackTrace();
//                }
//            }
//        }
        return 0;
    }
}
