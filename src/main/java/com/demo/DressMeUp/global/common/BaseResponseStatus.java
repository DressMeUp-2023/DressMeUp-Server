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
    FAILED_TO_SIGNUP(true, 400, "회원가입에 실패했습니다."),
    NO_USER_FOUND(true, 400, "해당 유저는 존재하지 않습니다."),
    FAILED_TO_UPLOAD_CLOTHES(true, 400, "옷 업로드에 실패했습니다."),
    NOT_COMPLETED_DRESSING(true, 400, "옷을 다 입혀주세요."),
    NO_CLOTHES_TYPE(true, 400, "옷 타입을 제대로 지정해주세요."),
    DRESS_UP_COMPLETED(true, 200, "dress-up 저장을 완료했습니다."),
    FAILED_TO_DRESS_UP(false, 500, "dress-up 저장에 실패하였습니다."),
    NO_PERMISSION(false, 403, "권한이 없습니다"),
    DELETE_LIKE(true, 200, "좋아요를 취소했습니다."),
    ADD_LIKE(true, 200, "좋아요를 눌렀습니다."),
    NO_ALBUM_FOUND(false, 400, "해당 이미지를 찾을 수 없습니다.");

    private final boolean isSuccess;

    private final int code;

    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
