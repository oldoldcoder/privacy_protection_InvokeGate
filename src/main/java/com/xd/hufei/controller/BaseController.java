package com.xd.hufei.controller;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

// 主要负责了网络特性的
@Slf4j
public class BaseController {
    protected PrintWriter out;
    protected BufferedReader in;

    protected Socket socket;

    public void init(String PORT,String ADDRESS){
        try {
            socket = new Socket(ADDRESS, Integer.parseInt(PORT));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


        }catch (Exception e){
            e.printStackTrace();
            log.error("无法连接到非分布式range_search服务");
        }
    }
}
