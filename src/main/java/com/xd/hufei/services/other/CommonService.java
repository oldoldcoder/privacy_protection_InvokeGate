package com.xd.hufei.services.other;

import com.xd.hufei.dto.other.TableColumn;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author heqi
 * @time 2024/04/15
 * @desc 公共服务类
 * */
public interface CommonService {

    // 动态的切换数据源
    int switchDataSources(String url);
    // 进行读取数据源
    List<Map<String,Object>> readTableGetList(String tableName);
    // 读取结果文件，写入,返回的表名
    String writeTable(String tableName,List<TableColumn> columns,List<Map<String,Object>> data);
    // 获取目标表的结构，同时创建表的结构到我们的这里
    List<TableColumn> getTableStructure(String dataBaseName,String tableName);
    // 处理数据，进行一次脱敏的处理
    void  ToDataDesensitization(List<Map<String,Object>> data);

}
