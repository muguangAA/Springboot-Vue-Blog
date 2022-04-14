package com.muguang.auth.service;

import com.muguang.auth.security.LoginUser;
import com.muguang.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Value("${redis.expireTime}")
    private Integer expireTime;

    @Resource
    private RedisCache redisCache;

    public void setLoginUser(String key, LoginUser loginUser) {
        if (redisCache.exist(key)) {
            redisCache.deleteObject(key);
        }
        redisCache.setCacheObject(key, loginUser, expireTime, TimeUnit.HOURS);
    }

    public LoginUser getLoginUser(String key) {
        return redisCache.getCacheObject(key);
    }

}
