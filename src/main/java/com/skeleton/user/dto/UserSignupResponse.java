package com.skeleton.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSignupResponse {

    private String verifyCode;

    @Builder
    public UserSignupResponse(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}

