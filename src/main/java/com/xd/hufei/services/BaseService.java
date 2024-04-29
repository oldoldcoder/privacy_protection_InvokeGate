package com.xd.hufei.services;


import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface BaseService {

    // 向对应位置写入文件:保存目标文件
    public String saveFile(MultipartFile file,String path);

    // 向C程序通知进行初始化工作
    public String sendInit2C(PrintWriter out, BufferedReader in) throws Exception;

    // 向C程序通知进行一次查询工作
    public String sendQuery2C(PrintWriter out, BufferedReader in) throws Exception;

    // 向C程序通知结束运行
    public String sendEnd2C(PrintWriter out, BufferedReader in) throws Exception;
}
