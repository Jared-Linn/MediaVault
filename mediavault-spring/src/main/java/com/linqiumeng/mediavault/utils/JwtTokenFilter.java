package com.linqiumeng.mediavault.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.linqiumeng.mediavault.service.UserDetailsServiceImpl;
import com.linqiumeng.mediavault.service.impl.UserService;
import com.linqiumeng.mediavault.utils.jwt.JwtAuthenticationEntryPoint;
import com.linqiumeng.mediavault.utils.jwt.JwtTokenProvider;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT过滤器，用于验证请求中的JWT
 * 用于检查请求头中是否存在有效的JWT，并验证其有效性。
 * 如果JWT有效，则继续处理请求；否则，返回401错误。
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.SECRET}")
    private String SECRET; // 替换为实际的密钥


    /**
     * 执行过滤器的主要方法
     * 检查请求头中的JWT，并验证其有效性
     * 如果JWT有效，则设置认证信息并继续处理请求
     *
     * @param request  HttpServletRequest对象，用于获取请求头
     * @param response HttpServletResponse对象，用于返回错误信息
     * @param chain    过滤链，用于继续处理请求
     * @throws ServletException 如果发生Servlet异常
     * @throws IOException      如果发生IO异常
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain
    ) throws ServletException, IOException {
        // 获取请求头中的Authorization信息
        String header = request.getHeader("Authorization");
        System.out.println("Authorization header: " + header);

        // 检查请求头是否为空
        if (header == null || header.isEmpty()) {
            throw new ServletException("Authorization information is missing");
        }

        // 检查请求头是否以"Bearer "开头
        if (header != null && header.startsWith("Bearer ")) {
            // 提取JWT
            String token = header.substring(7);
            System.out.println("Extracted token: " + token);
            try {
                // 验证JWT的有效性
                String username = JWT.require(Algorithm.HMAC256(SECRET))
                        .build()
                        .verify(token)
                        .getSubject();
                System.out.println("Verified username: " + username);

                // 如果JWT有效且当前认证信息为空，则设置认证信息
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // 加载用户详细信息
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    // 创建认证令牌
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    // 设置认证详情
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 将认证信息设置到SecurityContextHolder中
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("Authentication set to SecurityContextHolder");
                }
            } catch (JWTVerificationException e) {
                throw new JWTVerificationException("JWT verification failed: " + e.getMessage());
            }
        } else {
            throw new ServletException("Invalid Authorization format");
        }

        // 继续处理请求
        chain.doFilter(request, response);
    }

}
