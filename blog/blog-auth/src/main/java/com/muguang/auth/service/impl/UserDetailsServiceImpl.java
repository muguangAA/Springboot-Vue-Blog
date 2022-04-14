package com.muguang.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.muguang.core.entity.Permission;
import com.muguang.core.entity.User;
import com.muguang.core.mapper.PermissionMapper;
import com.muguang.core.mapper.UserMapper;
import com.muguang.auth.security.LoginUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (Objects.isNull(dbUser)) {
            throw new RuntimeException("用户名或密码不正确");
        }
        Set<Permission> permissions = permissionMapper.selectPermissionsByUserId(dbUser.getId());
        Set<String> permissionsStrings = new HashSet<>();
        for (Permission permission : permissions) {
            permissionsStrings.add(permission.getPermission());
        }
        return new LoginUser(dbUser, permissionsStrings);
    }

}
