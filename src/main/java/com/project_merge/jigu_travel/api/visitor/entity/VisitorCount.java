package com.project_merge.jigu_travel.api.visitor.entity;

import com.project_merge.jigu_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "visitor_count")
public class VisitorCount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ip;  // 방문자 IP 저장

    @Column(nullable = false)
    private LocalDate visitDate;  // 방문 날짜 저장

    @Column(nullable = false)
    private int visitCount;  // 방문 횟수 저장 (디폴트 1)
}
