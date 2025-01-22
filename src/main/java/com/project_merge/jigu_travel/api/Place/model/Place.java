package com.project_merge.jigu_travel.api.Place.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId; // 장소 ID (Primary Key)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceType type; // 장소 유형 (ENUM: 맛집, 오락_체험, 힐링, 역사_문화, 쇼핑)

    @Column(nullable = false, length = 255)
    private String name; // 장소 이름

    @Column(length = 20)
    private String tel; // 연락처

    @Column(nullable = false)
    private double latitude; // 위도

    @Column(nullable = false)
    private double longitude; // 경도

    @Column(nullable = false, length = 50)
    private String address; // 주소

    private LocalDateTime opened; // 개방 시간
    private LocalDateTime closed; // 마감 시간

    @Column(nullable = false)
    private boolean deleted = false; // 폐업 여부 (기본값: false)

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 일자

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now(); // 수정 일자
}
