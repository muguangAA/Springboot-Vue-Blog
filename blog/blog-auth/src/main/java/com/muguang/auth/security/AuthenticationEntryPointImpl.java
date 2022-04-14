package com.muguang.auth.security;

import com.muguang.core.entity.ResponseResult;
import com.muguang.core.utils.JsonUtils;
import com.muguang.core.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 *
 * @author ruoyi
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = 1L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        String message = "请求访问：" + request.getRequestURI() + "，认证失败，无法访问系统资源";
        String data = JsonUtils.toJson(new ResponseResult<>(401, message, null));
        WebUtils.renderString(response, data);
    }
}