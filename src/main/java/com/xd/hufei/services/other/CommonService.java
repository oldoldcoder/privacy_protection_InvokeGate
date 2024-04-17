package com.xd.hufei.services.other;

import com.xd.hufei.dto.other.DesensitizedData;

import java.math.BigInteger;
import java.util.List;

/**
 * @author heqi
 * @time 2024/04/15
 * @desc 公共服务类
 * */
public interface CommonService {

    // 动态的切换数据源
    public int switchDataSources(String url);
    // 进行读取数据源
    public List<DesensitizedData> readTableGetList(String tableName);
    // 写文件到指定位置
    public int saveFile(String path,List<DesensitizedData> data);
    // 执行得到结果文件
    public int execETPSS(String execPath);
    // 读取结果文件，写入,返回的表名
    public String readFileAndWriteTable(String path);
}
