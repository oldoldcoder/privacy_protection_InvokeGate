package com.xd.hufei.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

// 主要负责了网络特性的
@Slf4j
@RestController
public class BaseController {
    protected PrintWriter out;
    protected BufferedReader in;

    protected Socket socket;

    // 是否启动这一部分的标识
    @Value("${isPrivacyActivated}")
    private boolean isPrivacyActivated;

    // 负责bean销毁的时候释放长连接
    @PreDestroy
    private void shutdown() {
        if(isPrivacyActivated){
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
    }

    public void init(String PORT,String ADDRESS){
        if(isPrivacyActivated) {
            try {
                socket = new Socket(ADDRESS, Integer.parseInt(PORT));
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            } catch (Exception e) {
                e.printStackTrace();
                log.error("无法连接到非分布式range_search服务");
            }
        }
    }

}
