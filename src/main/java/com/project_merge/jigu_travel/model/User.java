package com.project_merge.jigu_travel.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // 테이블 이름을 "users"로 설정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role; // ROLE_USER or ROLE_ADMIN
}