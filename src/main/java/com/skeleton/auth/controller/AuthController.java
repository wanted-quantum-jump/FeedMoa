package com.skeleton.auth.controller;

import com.skeleton.auth.enums.TokenType;
import com.skeleton.auth.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<String> login(Authentication authentication) {
        return ResponseEntity.ok().body(jwtUtils.generateToken(authentication, TokenType.REFRESH));
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(Authentication authentication) {
        return ResponseEntity.ok().body(jwtUtils.generateToken(authentication, TokenType.ACCESS));
    }
}
