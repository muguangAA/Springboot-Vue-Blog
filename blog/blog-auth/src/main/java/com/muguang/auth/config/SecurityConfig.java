package com.muguang.auth.config;

import com.muguang.core.filter.CorsFilter;
import com.muguang.auth.security.AuthenticationEntryPointImpl;
import com.muguang.auth.security.JwtAuthenticateTokenFilter;
import com.muguang.auth.security.LogoutSuccessHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import javax.annotation.Resource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Resource
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Resource
    private JwtAuthenticateTokenFilter jwtAuthenticateTokenFilter;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private CorsFilter corsFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //登出处理
        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);

        //认证失败处理器
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and();

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/logout").authenticated()
                .antMatchers("/guest/**").permitAll()
                .anyRequest().authenticated()
                .and()
                // 添加JWT filter
                .addFilterBefore(jwtAuthenticateTokenFilter, UsernamePasswordAuthenticationFilter.class)
                //跨域处理
                .addFilterBefore(corsFilter, JwtAuthenticateTokenFilter.class)
                .addFilterBefore(corsFilter, LogoutFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //访问静态文件需要加的，否则返回时会加上不必要的信息
        web.ignoring().antMatchers("/selectImage/**","/index.html","/static/**", "/storage/**");
    }

}
