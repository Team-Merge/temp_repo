package com.project_merge.jigu_travel.api.ai_classification.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_interest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interestId;  // 관심사 ID (Auto Increment)

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;  // 회원 ID (UUID)

    @Column(nullable = false, length = 30)
    private String interest;  // 관심사 1

    @Column(nullable = false, length = 30)
    private String interest2; // 관심사 2

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt; // 생성일

    @UpdateTimestamp
    private LocalDateTime updatedAt; // 수정일

}
