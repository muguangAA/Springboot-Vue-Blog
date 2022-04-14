package com.muguang.core.utils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtils {

    public static HttpServletResponse toHttp(ServletResponse response){
        return (HttpServletResponse) response;
    }

    public static HttpServletRequest toHttp(ServletRequest request){
        return (HttpServletRequest) request;
    }

    public static void renderString(HttpServletResponse response, String string){
        try {
            response.setStatus(200);
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(string);
        } catch (IOException e) {
            throw new RuntimeException("写入响应失败");
        }
    }
}
