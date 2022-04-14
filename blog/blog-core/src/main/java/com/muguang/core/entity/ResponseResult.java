package com.muguang.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseResult<T> {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private T data;

    public ResponseResult(Integer code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
