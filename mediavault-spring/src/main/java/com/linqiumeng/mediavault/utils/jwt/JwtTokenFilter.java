package com.linqiumeng.mediavault.utils.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.linqiumeng.mediavault.service.User.UserDetailsServiceImpl;
import com.linqiumeng.mediavault.service.User.impl.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    private List<String> whiteList = Arrays.asList("/login", "/register");

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
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {

        String path = request.getServletPath();
        if (whiteList.contains(path)) {
            chain.doFilter(request, response);
            return;
        }
        // 获取请求头中的Authorization信息
        String header = request.getHeader("Authorization");
        System.out.println("Authorization header: " + header);

        // 检查请求头是否为空
        if (header == null || header.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Authorization information is missing");
            return;
        }


        // 检查请求头是否以"Bearer "开头
        if (header.startsWith("Bearer ")) {
            // 提取JWT
            String token = header.substring(7);
            System.out.println("Extracted token: " + token);
            try {
                // 验证JWT的有效性
                if (jwtTokenProvider.validateToken(token)) {
                    System.out.println("id or username is null");
                    return;
                }
                // 从 JWT中提取用户名
                String username = jwtTokenProvider.getUsernameFromToken(token);
                // 从 JWT中提取用户ID
                Long userId = jwtTokenProvider.getUserIdFromToken(token);
                // 检测 id + username 是否存在
                if (userDetailsService.checkUser(username, Math.toIntExact(userId)) == null) {
                    System.out.println("用户或id不存在");
                    throw new ServletException("Authorization information is missing");
                }

                // 如果JWT有效且当前认证信息为空，则设置认证信息
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
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
                System.out.println("User not found: " + e.getMessage());
                throw new JWTVerificationException("JWT verification failed: " + e.getMessage());
            }
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Authorization information is missing");
            return;
        }

        // 继续处理请求
        chain.doFilter(request, response);
    }

}
