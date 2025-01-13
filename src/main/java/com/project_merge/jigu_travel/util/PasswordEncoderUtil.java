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