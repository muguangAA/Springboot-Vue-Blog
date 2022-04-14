package com.muguang.auth.security;

import com.muguang.core.entity.ResponseResult;
import com.muguang.auth.service.TokenService;
import com.muguang.core.utils.JsonUtils;
import com.muguang.core.redis.RedisCache;
import com.muguang.core.utils.WebUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 自定义退出处理类 返回成功
 * 
 * @author ruoyi
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler
{

    @Resource
    private TokenService tokenService;

    @Resource
    private RedisCache redisCache;

    /**
     * 退出处理
     * 
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("Authorization");
        String responseMessage = JsonUtils.toJson(new ResponseResult<>(401, "未携带token"));
        if (!Objects.isNull(token)) {
            String key = tokenService.getUserKey(token);
            // 删除用户缓存记录
            if (redisCache.deleteObject(key)) {
                responseMessage = JsonUtils.toJson(new ResponseResult<>(200, "登出成功"));
            } else {
                responseMessage = JsonUtils.toJson(new ResponseResult<>(500, "登出失败"));
            }
        }
        WebUtils.renderString(response, responseMessage);
    }
}