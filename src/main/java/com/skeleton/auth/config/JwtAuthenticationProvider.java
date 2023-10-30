package com.skeleton.auth.config;

import com.skeleton.common.exception.CustomException;
import com.skeleton.common.exception.ErrorCode;
import com.skeleton.user.crypto.PasswordEncoder;
import com.skeleton.user.entity.User;
import com.skeleton.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    // TODO: 유저 repository 구현이 끝나면 수정
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String account = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        // TODO : 유저 레포지토리에서 받아와서 유저의 이름과 패스어드 검증
        // 유저 계정이 없을 시 로그인 실패 에러
        User user = userRepository.findByAccount(account).orElseThrow(
                () -> new UsernameNotFoundException("Invalid account.")
        );
        // 비밀번호 비교
        if (passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    account, password, authentication.getAuthorities()
            );
        } else {
            // 비밀번호 틀림
            throw new BadCredentialsException("Invalid password.") {
            };
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
