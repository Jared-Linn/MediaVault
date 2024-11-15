package com.linqiumeng.mediavault.config;

import com.linqiumeng.mediavault.utils.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 主要用于配置安全过滤链
 * 禁用CSRF保护：由于API通常是无状态的，CSRF保护通常不需要。
 * 路径权限配置：
 * /public/** 路径下的所有请求都允许匿名访问。
 * 其他所有请求都需要认证。
 * 添加JWT过滤器：在用户名密码认证过滤器之前添加自定义的JWT过滤器，用于处理JWT令牌。
 *
 * @author
 * @date
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    /**
     * 配置安全过滤链
     * 禁用CSRF，设置请求匹配规则，添加JWT过滤器
     *
     * @param http HttpSecurity对象，用于配置安全设置
     * @return SecurityFilterChain对象，表示配置好的安全过滤链
     * @throws Exception 配置过程中可能抛出的异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**", "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
