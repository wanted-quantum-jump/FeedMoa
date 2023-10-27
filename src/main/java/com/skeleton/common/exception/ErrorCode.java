package com.skeleton.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 예시)
    // RECRUITMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "R001", "해당하는 채용공고가 없습니다."),
  
    //User
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "R001", "이미 계정이 존재합니다."),

    //Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "해당하는 게시물이 없습니다."),
    SNS_POST_NOT_FOUND(HttpStatus.NOT_FOUND,"P002" ,"해당하는 SNS 게시글이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}