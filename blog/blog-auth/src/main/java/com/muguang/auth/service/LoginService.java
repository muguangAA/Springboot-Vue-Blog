package com.muguang.auth.service;

import com.muguang.core.entity.ResponseResult;
import com.muguang.core.entity.User;

import java.util.Map;

public interface LoginService {

    ResponseResult<Map<String, Object>> login(User user);
}
