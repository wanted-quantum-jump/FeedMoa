package com.skeleton.user.service;

import com.skeleton.common.exception.CustomException;
import com.skeleton.common.exception.ErrorCode;
import com.skeleton.user.crypto.PasswordEncoder;
import com.skeleton.user.dto.UserSignupRequest;
import com.skeleton.user.dto.UserSignupResponse;
import com.skeleton.user.entity.User;
import com.skeleton.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserSignupResponse saveUser(UserSignupRequest request) {

        checkDuplicateEmail(request);

        String encryptedPassword = passwordEncoder.encrypt(request.getPassword());

        User user = User.builder()
                .account(request.getAccount())
                .email(request.getEmail())
                .password(encryptedPassword)
                .build();

        userRepository.save(user);

        UserSignupResponse verifyCode = UserSignupResponse.builder()
                .verifyCode(getRandomCode())
                .build();

        return verifyCode;
    }

    private void checkDuplicateEmail(UserSignupRequest request) {
        userRepository.findByAccount(request.getAccount())
                .ifPresent(user -> {
                    throw new CustomException(ErrorCode.USER_ALREADY_EXIST);
                });
    }

    private String getRandomCode() {
        Random random = new Random();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(10);
            sb.append(randomNumber);
        }
        return sb.toString();
    }
}
