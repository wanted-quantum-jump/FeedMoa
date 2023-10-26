package com.skeleton.user.controller;

import com.skeleton.user.dto.UserSignupRequest;
import com.skeleton.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/feedmoa/join")
    public ResponseEntity<?> joinUser(@Valid @RequestBody UserSignupRequest request) {

        Long userId = userService.saveUser(request);

        return ResponseEntity.created(URI.create("/feedmoa/" + userId)).build();
    }
}
