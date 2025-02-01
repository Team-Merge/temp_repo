package com.project_merge.jigu_travel.api.Place.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId; // 장소 ID (Primary Key)

    @Column(nullable = false, length = 500) // List<String>을 String으로 저장하기 위해 길이 늘림
    private String types;  // 그냥 List<String>을 ","로 합쳐서 저장

    @Column(nullable = false, length = 255)
    private String name; // 장소 이름

    @Column(length = 20)
    private String tel; // 연락처

    @Column(nullable = false)
    private double latitude; // 위도

    @Column(nullable = false)
    private double longitude; // 경도

    @Column(nullable = false, length = 100)
    private String address; // 주소

    @Column(nullable = false)
    private boolean deleted = false; // 폐업 여부 (기본값: false)

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 일자

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now(); // 수정 일자

    // 생성자
    public Place(List<String> types, String name, String tel, double latitude, double longitude, String address) {
        this.types = String.join(",", types); // 🚀 List<String> → "힐링_여행,쇼핑_여행" 형태로 변환 후 저장
        this.name = name;
        this.tel = tel;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // List<String>로 변환해서 가져오기
    public List<String> getTypeList() {
        if (types == null || types.isEmpty()) return new ArrayList<>();
        return List.of(types.split(","));
    }

    @Override
    public String toString() {
        return "Place{" +
                "placeId=" + placeId +
                ", types='" + types + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                '}';
    }
}
