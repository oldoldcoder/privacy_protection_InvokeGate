package com.xd.hufei.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.PrintWriter;

@Slf4j
public class BaseServiceImpl implements BaseService{
    @Override
    public String saveFile(MultipartFile file, String path) {
        return null;
    }

    @Override
    public String sendInit2C(PrintWriter out, BufferedReader in) throws Exception{
        return sendMsg2C("init",out,in);
    }

    @Override
    public String sendQuery2C(PrintWriter out, BufferedReader in) throws Exception{
        return sendMsg2C("query",out,in);
    }

    @Override
    public String sendEnd2C(PrintWriter out, BufferedReader in) throws Exception{
        return sendMsg2C("stop",out,in);
    }

    private String sendMsg2C(String msg,PrintWriter out, BufferedReader in) throws Exception{
        out.print(msg);
        String resp = in.readLine();
        log.info("接受到C程序返回消息：" + resp);
        if(!resp.contains("ok")){
            throw new Exception("c程序init失败");
        }
        return resp;
    }
}
