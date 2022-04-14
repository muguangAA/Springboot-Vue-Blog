package com.muguang.core.exception;

/**
 * @author luoba
 * 自定义异常
 */
public class MessageException extends RuntimeException{

    static final long serialVersionUID = 1L;

    public MessageException(){
        super();
    }

    public MessageException(String message) {
        super(message);
    }

}
