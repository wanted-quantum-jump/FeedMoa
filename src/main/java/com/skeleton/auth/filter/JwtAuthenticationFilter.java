package com.skeleton.auth.filter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skeleton.common.exception.CustomException;
import com.skeleton.common.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, String> jsonMap = objectMapper.readValue(request.getReader(), Map.class);

            String account = jsonMap.get("account");
            String password = jsonMap.get("password");

            if (account != null && password != null) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(account, password);
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                try {
                    Authentication authentication = authenticationManager.authenticate(
                            authenticationToken
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                } catch (AuthenticationException e) {
                    // 계정 또는 패스워드가 틀림
                    SecurityContextHolder.clearContext();
                    handlerExceptionResolver.resolveException(request, response, null, new CustomException(ErrorCode.LOGIN_FAILED));
                }
            } else {
                // 계정, 패스워드 미입력시 에러
                SecurityContextHolder.clearContext();
                handlerExceptionResolver.resolveException(request, response, null, new CustomException(ErrorCode.LOGIN_DATA_IS_NULL));
            }
        } catch (JacksonException e) {
            // JsonParseException, MismatchedInputException 통합 처리
            log.error("JacksonException", e);
            handlerExceptionResolver.resolveException(request, response, null, new CustomException(ErrorCode.JSON_PARSE_ERROR));
        } catch (ClassCastException e) {
            // 스트링 타입에 정수형등 잘못된 타입을 넣었을때
            log.error("ClassCastException", e);
            handlerExceptionResolver.resolveException(request, response, null, new CustomException(ErrorCode.MISS_MATCH_INPUT));
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        /**
         * login 은 계정과 패스워드를 입력하여 refresh 토큰을 받아야함
         */
        return !request.getServletPath().equals("/api/login");
    }
}
