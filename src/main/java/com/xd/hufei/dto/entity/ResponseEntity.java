package com.xd.hufei.dto.entity;

import java.util.Objects;

public class ResponseEntity {
    // 状态码
    int statusCode;
    // 附带消息
    String msg;
    // 携带的数据
    Object data;
}
