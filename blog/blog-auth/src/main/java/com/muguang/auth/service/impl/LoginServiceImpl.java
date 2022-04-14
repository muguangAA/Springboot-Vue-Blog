package com.muguang.auth.service.impl;

import com.muguang.auth.access.LoginUserContext;
import com.muguang.core.entity.ResponseResult;
import com.muguang.core.entity.User;
import com.muguang.auth.security.LoginUser;
import com.muguang.auth.service.LoginService;
import com.muguang.auth.service.RedisService;
import com.muguang.auth.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisService redisService;

    @Override
    public ResponseResult<Map<String, Object>> login(User user) {
        //使用AuthorizationManager.authenticate进行认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过返回信息
        if (Objects.isNull(authenticate)) {
            throw new InternalAuthenticationServiceException("用户认证失败");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        LoginUserContext.setLoginUser(loginUser);
        Integer id = loginUser.getUser().getId();

        //认证通过，生成JWT
        String token = tokenService.createToken(id);
        loginUser.setToken(token);
        //将用户信息存入redis
        String key = tokenService.getUserKey(token);
        redisService.setLoginUser(key, loginUser);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return new ResponseResult<>(200, "登录成功", map);
    }

}
