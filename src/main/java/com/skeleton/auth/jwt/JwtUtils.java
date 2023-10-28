package com.skeleton.auth.jwt;

import com.skeleton.auth.enums.TokenType;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    // 키는 환경변수로 설정
    @Value("${jwt.access.secret}")
    private String ACCESS_SECRET_KEY;
    @Value("${jwt.refresh.secret")
    private String REFRESH_SECRET_KEY;
    private Map<String, String> secretKeys = new HashMap<>();

    @PostConstruct
    public void init() {
        secretKeys.put("ACCESS", ACCESS_SECRET_KEY);
        secretKeys.put("REFRESH", REFRESH_SECRET_KEY);
    }

    // 토큰 검증
    public boolean validateAccessToken(String token, TokenType tokenType) {
        Jwts.parser().setSigningKey(secretKeys.get(tokenType.name()))
                .parseClaimsJws(getAccessToken(token));
        return true;
    }

    // TODO: 후에 권한 추가시 Authorities 추가
    public String generateToken(
            Authentication authentication,
            TokenType tokenType) {

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("tokenType", tokenType.name())
                .setIssuer("quantum-jump")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenType.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, secretKeys.get(tokenType.name()))
                .compact();
    }

    public Claims parseClaim(String token, TokenType tokenType) {
        return Jwts.parser()
                .setSigningKey(secretKeys.get(tokenType.name()))
                .parseClaimsJws(getAccessToken(token))
                .getBody();
    }

    // 헤더에 Bearer {JWT} 가 들어감
    private String getAccessToken(String token) {
        return token.split(" ")[1].trim();
    }
}