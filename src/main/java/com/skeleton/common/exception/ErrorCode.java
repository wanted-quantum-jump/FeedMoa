package com.skeleton.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 예시)
    // RECRUITMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "R001", "해당하는 채용공고가 없습니다."),
  
    //User
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "U001", "이미 계정이 존재합니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U002", "해당 계정을 찾을 수 없습니다."),
    USER_VERIFY_PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "U003", "회원 비밀번호가 일치하지 않습니다."),

    //Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "해당하는 게시물이 없습니다."),
    SNS_POST_NOT_FOUND(HttpStatus.NOT_FOUND,"P002" ,"해당하는 SNS 게시글이 없습니다."),

    //Auth
    LOGIN_DATA_IS_NULL(HttpStatus.BAD_REQUEST,"A001","계정과 패스워드 값을 입력해야 합니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED,"A002","계정 또는 패스워드가 틀렸습니다."),
    JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST,"A003","Json 파싱 에러."),
    MISS_MATCH_INPUT(HttpStatus.BAD_REQUEST,"A004","잘못된 데이터 타입 입니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED,"A005","잘못된 토큰 입니다."),
    NEED_AUTHORIZATION_HEADER(HttpStatus.BAD_REQUEST,"A006","Authorization 헤더가 필요합니다.")
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