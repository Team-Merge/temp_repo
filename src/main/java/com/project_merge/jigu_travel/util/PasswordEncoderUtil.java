package com.project_merge.jigu_travel.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 암호화할 비밀번호
        System.out.println("admin password: " + encoder.encode("1234"));
        System.out.println("user password: " + encoder.encode("1234"));
    }
}

//여러분 이거는 테스트 할려고 제가 그냥 만든겁니다...혹시 혹시라도 테스트 하고 싶으신 분들은 이것만 돌려보시면 됩니다. 격리된 코드에요