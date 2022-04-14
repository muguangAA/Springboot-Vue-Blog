package com.muguang.manager.controller;

import com.muguang.core.entity.ResponseResult;
import com.muguang.core.exception.MessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class exceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MessageException.class)
    public ResponseResult<Object> handleMessageException(MessageException e){
        return new ResponseResult<>(400, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return new ResponseResult<>(401, "请求体错误");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseResult<Object> handleBadCredentialsException(BadCredentialsException e){
        return new ResponseResult<>(401, "用户名或密码错误");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseResult<Object> handleAuthenticationInternalAuthenticationServiceException(InternalAuthenticationServiceException e){
        return new ResponseResult<>(401, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseResult<Object> handleAuthenticationException(AuthenticationException e){
        return new ResponseResult<>(401, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseResult<Object> handle400(RuntimeException e){
        return new ResponseResult<>(500, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseResult<Object> handle400(Exception e){
        return new ResponseResult<>(500, e.getMessage());
    }

}
