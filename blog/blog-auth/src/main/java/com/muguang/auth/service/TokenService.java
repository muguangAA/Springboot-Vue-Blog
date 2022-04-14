package com.muguang.auth.service;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.muguang.auth.security.LoginUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * @author luoba
 */
@Service
public class TokenService {

    //过期时间
    @Value("${tokenUtils.expireTime}")
    private int expireTime;

    //签名加密秘钥
    @Value("${tokenUtils.secret}")
    private String secret;

    @Resource
    private RedisService redisService;

    @Value("${redis.userKey}")
    private String redisUserKey;

    public void refreshToken(LoginUser loginUser) {
        Integer id = loginUser.getUser().getId();

        String newToken = createToken(id);
        loginUser.setToken(newToken);

        String key = redisUserKey + id;
        redisService.setLoginUser(key, loginUser);
    }

    //创建token，用withClaim放入username做自定义数据，用secret做加密签名
    public String createToken(Integer id){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, expireTime);
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("id", id)
                .withExpiresAt(calendar.getTime());
        return builder.sign(Algorithm.HMAC256(secret));
    }

    //根据secret来验证token是否是正确的，如果发生异常就是验证失败，所以直接返回false
    public boolean verifyToken(String token){
        if (!StrUtil.isEmpty(token) && !expired(token)) {
            try {
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
                jwtVerifier.verify(token);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    //用JWT的方法来获取到我们自定义的数据
    public Integer getId(String token){
        try {
            return getDecodedJWT(token).getClaim("id").asInt();
        }catch (JWTDecodeException e){
            throw new InternalAuthenticationServiceException("token非法");
        }
    }

    public DecodedJWT getDecodedJWT(String token){
        return JWT.decode(token);
    }

    //验证token是否过期的方法
    public boolean expired(String token){
        try {
            return getDecodedJWT(token).getExpiresAt().getTime() < System.currentTimeMillis();
        }catch (JWTDecodeException e){
            return false;
        }
    }

    public String getUserKey(String token) {
        Integer id = getId(token);
        return redisUserKey + id;
    }

    public String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
