package com.project_merge.jigu_travel.api.auth.model;

public enum AuthType {
    LOCAL,  // 자체 로그인 (ID/PW)
    GOOGLE, // 구글 로그인
    NAVER,  // 네이버 로그인
    KAKAO   // 카카오 로그인 (추후 추가 가능)
}
