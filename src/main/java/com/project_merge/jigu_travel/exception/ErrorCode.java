package com.project_merge.jigu_travel.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD("비밀번호가 일치하지 않습니다."),
    DUPLICATE_LOGIN_ID("이미 존재하는 아이디입니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다.");

    private final String message;
}
