package com.muguang.auth.access;

import com.muguang.auth.security.LoginUser;

public class LoginUserContext {

    private static ThreadLocal<LoginUser> loginUserHolder = new ThreadLocal<>();

    public static void setLoginUser(LoginUser loginUser) {
        loginUserHolder.set(loginUser);
    }

    public static LoginUser getLoginUser() {
        return loginUserHolder.get();
    }
}
