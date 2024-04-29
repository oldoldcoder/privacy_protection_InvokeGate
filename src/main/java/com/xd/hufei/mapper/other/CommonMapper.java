package com.xd.hufei.mapper.other;

import com.xd.hufei.dto.other.TableColumn;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommonMapper {

    List<Map<String,Object>> getDataFromTable(@Param("tableName") String tableName);
    // 获取表的结构
    List<TableColumn> getTableStructure(@Param("databaseName") String databaseName, @Param("tableName")String tableName);
    // 创建相应的表
    void createCopyTable(@Param("tableName") String tableName,@Param("columns") List<TableColumn> columns);

    int insertNewData(@Param("list") List<Map<String,Object>> data, @Param("tableName") String tableName);
}
