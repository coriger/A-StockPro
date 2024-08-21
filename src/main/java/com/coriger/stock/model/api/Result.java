package com.coriger.stock.model.api;

import lombok.Data;

/**
 * 接口响应对象
 */
@Data
public class Result {

    private String code;
    private String msg;
    private Object data;

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
