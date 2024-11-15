package com.linqiumeng.mediavault.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.SECRET}")
    private String SECRET;

    @Value("${jwt.EXPIRATION_TIME}")
    private long EXPIRATION_TIME;


    /**
     * 生成包含用户ID的JWT
     * @param username 用户名
     * @param userId 用户ID
     * @return 生成的JWT
     */
    public String generateTokenWithUserId(String username, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return JWT.create()
                .withSubject(username)
                .withClaim("userId", userId)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 从JWT中提取用户名
     * @param token JWT
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    /**
     * 验证JWT的有效性
     * @param token JWT
     * @return 是否有效
     */
    public String validateToken(String token) throws JWTVerificationException {
        return getUsernameFromToken(token);
    }
}
