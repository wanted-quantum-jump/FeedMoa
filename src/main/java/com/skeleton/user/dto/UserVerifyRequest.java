package com.skeleton.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserVerifyRequest {

    @NotBlank(message = "계정은 필수값입니다.")
    private String account;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    private String password;

    @NotBlank(message = "인증코드는 필수값입니다.")
    private String verifyCode;
}
