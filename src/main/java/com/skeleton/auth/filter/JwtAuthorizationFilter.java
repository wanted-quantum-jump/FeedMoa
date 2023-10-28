package com.skeleton.auth.filter;

import com.skeleton.auth.enums.TokenType;
import com.skeleton.auth.jwt.JwtUtils;
import com.skeleton.common.exception.CustomException;
import com.skeleton.common.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");

            // 헤더가 없을 시
            if (token == null) {
                handlerExceptionResolver.resolveException(
                        request, response, null, new CustomException(ErrorCode.NEED_AUTHORIZATION_HEADER)
                );
                return;
            }

            // 토큰 타입마다 키가 달라 구분하기 위함.
            // refresh 토큰은 /api/refresh 에만 사용 가능하다.
            TokenType tokenType;
            if (request.getRequestURI().equals("/api/refresh")) {
                tokenType = TokenType.REFRESH;
            } else {
                tokenType = TokenType.ACCESS;
            }

            if (jwtUtils.validateAccessToken(token, tokenType)) {
                Claims claims = jwtUtils.parseClaim(token, tokenType);

                String account = claims.getSubject();
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                account,
                                null,
                                AuthorityUtils.NO_AUTHORITIES
                        );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            }
        } catch (JwtException e) {
            // 만료, 형식 등 여러 에러가 있지만 하나로 통합하였습니다. - JWT 검증 에러
            handlerExceptionResolver.resolveException(
                    request, response, null, new CustomException(ErrorCode.INVALID_JWT)
            );
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        /**
         * token 이 필요한 url
         * 필요
         * refresh - access 토큰을 받기 위해 refresh 토큰이 필요하다.
         * 이후 모든 API 요청 Header 에 JWT 가 항시 포함되며, JWT 유효성을 검증합니다. <- 요구사항
         *
         * 필요 x
         * login - 로그인은 계정, 패스워드를 입력하여 refresh 토큰을 받는다.
         * signup - 회원가입은 토큰이 필요없다.
         */
        return (!request.getServletPath().equals("/api/refresh") && !request.getServletPath().startsWith("/api/posts/"));
    }
}
