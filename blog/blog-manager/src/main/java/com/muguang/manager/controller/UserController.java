package com.muguang.manager.controller;

import com.muguang.auth.access.LoginUserContext;
import com.muguang.core.entity.ResponseResult;
import com.muguang.core.entity.User;
import com.muguang.auth.security.LoginUser;
import com.muguang.auth.service.TokenService;
import com.muguang.auth.service.impl.LoginServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

    @Resource
    private LoginServiceImpl loginService;

    @Resource
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseResult<Map<String, Object>> login(@RequestBody User user){
        return loginService.login(user);
    }

    @GetMapping("/user/info")
    public ResponseResult<Map<String, Object>> info() {
        LoginUser loginUser = LoginUserContext.getLoginUser();
        Map<String, Object> map = new HashMap<>();
        map.put("username", loginUser.getUser());
        return new ResponseResult<>(200, "请求成功", map);
    }
}
