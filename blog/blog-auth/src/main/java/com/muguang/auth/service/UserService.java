package com.muguang.auth.service;

import com.muguang.auth.security.LoginUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class UserService {

    @Resource
    private TokenService tokenService;

    @Resource
    private RedisService redisService;

    public LoginUser getLoginUser(HttpServletRequest request) {
        //从redis中获取用户信息
        String token = tokenService.getToken(request);
        if (tokenService.verifyToken(token)) {
            String key = tokenService.getUserKey(token);
            LoginUser loginUser = redisService.getLoginUser(key);
            if (Objects.isNull(loginUser)) {
                return null;
            }
            return loginUser;
        }
        return null;
    }

}
