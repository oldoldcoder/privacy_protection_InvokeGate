package com.xd.hufei.dto.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigInteger;

@Data
@ToString
public class TableColumn {
    private String columnName;
    private String dataType;
    private Object characterMaximumLength;
    public TableColumn(String columnName, String dataType,BigInteger characterMaximumLength){
        this.columnName = columnName;
        this.dataType = dataType;
        this.characterMaximumLength = characterMaximumLength;
    }


    public TableColumn(String columnName, String dataType,Long characterMaximumLength){
        this.columnName = columnName;
        this.dataType = dataType;
        this.characterMaximumLength = characterMaximumLength;
    }

    public TableColumn(String columnName, String dataType,Integer characterMaximumLength){
        this.columnName = columnName;
        this.dataType = dataType;
        this.characterMaximumLength = characterMaximumLength;
    }
    public TableColumn(String columnName, String dataType,Object characterMaximumLength){
        this.columnName = columnName;
        this.dataType = dataType;
        this.characterMaximumLength = characterMaximumLength;
    }
}
