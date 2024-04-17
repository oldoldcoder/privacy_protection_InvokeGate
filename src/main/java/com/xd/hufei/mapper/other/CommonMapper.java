package com.xd.hufei.mapper.other;

import com.xd.hufei.dto.other.DesensitizedData;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommonMapper {
    List<DesensitizedData> getData2desensitization(@Param("tableName") String tableName);
    int createTable(@Param("tableName") String tableName,@Param("parameters") Map<String, String> parameters );
    int insertNewData(@Param("List") List<DesensitizedData> data, @Param("tableName") String tableName);
}
