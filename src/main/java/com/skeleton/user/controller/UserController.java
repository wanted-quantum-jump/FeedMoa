package com.skeleton.user.controller;

import com.skeleton.user.dto.UserSignupRequest;
import com.skeleton.user.dto.UserSignupResponse;
import com.skeleton.user.dto.UserVerifyRequest;
import com.skeleton.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> joinUser(@Valid @RequestBody UserSignupRequest request) {

        UserSignupResponse verifyCode = userService.saveUser(request);

        return new ResponseEntity<>(verifyCode, HttpStatus.CREATED);
    }

    @PatchMapping("/verify")
    public ResponseEntity<?> verifyUser(@Valid @RequestBody UserVerifyRequest request) {

        userService.verifyUser(request);

        return ResponseEntity.noContent().build();
    }
}
