package com.demo.DressMeUp.global.common;

import lombok.Getter;

/*
200번대 : 성공적인 응답
400번대 : 클라이언트 응답 오류
500번대 : 서버 오류
 */
@Getter
public enum BaseResponseStatus {
    SUCCESS(true, 200, "요청에 성공했습니다"),
    SUCCESS_TO_SIGNUP(true, 200, "회원가입에 성공했습니다"),
    NICKNAME_ALREADY_EXISTS(true, 400, "해당 닉네임은 사용 중 입니다."),
    FAILED_TO_SIGNUP(true, 400, "회원가입에 실패했습니다.");

    private final boolean isSuccess;

    private final int code;

    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
