package com.skeleton.user.service;

import com.skeleton.common.exception.CustomException;
import com.skeleton.common.exception.ErrorCode;
import com.skeleton.user.dto.UserJoinRequest;
import com.skeleton.user.entity.User;
import com.skeleton.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long saveUser(UserJoinRequest request) {

        checkDuplicateEmail(request);

        User user = User.builder()
                .account(request.getAccount())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    private void checkDuplicateEmail(UserJoinRequest request) {
        userRepository.findByAccount(request.getAccount())
                .ifPresent(user -> {
                    throw new CustomException(ErrorCode.USER_ALREADY_EXIST);
                });
    }
}
