package com.xd.hufei.dto.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class TableColumn {
    private String columnName;
    private String dataType;
    private Integer characterMaximumLength;
}
