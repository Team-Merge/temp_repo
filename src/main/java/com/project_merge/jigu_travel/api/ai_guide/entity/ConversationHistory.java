package com.project_merge.jigu_travel.api.ai_guide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "conversation_history")
public class ConversationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id")  // 언더스코어 있는 컬럼 매핑
    private Integer conversationId;

    @Column(name = "user_id", nullable = false)  // 언더스코어 있는 컬럼 매핑
    private UUID userId;

    @Column(name = "conversation_question", nullable = false, length = 1000)
    private String conversationQuestion;

    @Column(name = "conversation_answer", nullable = true, length = 1000)
    private String conversationAnswer;

    @Column(name = "conversation_latitude", nullable = false)
    private Double conversationLatitude;

    @Column(name = "conversation_longitude", nullable = false)
    private Double conversationLongitude;

    @Column(name = "conversation_datetime", nullable = false)
    private LocalDateTime conversationDatetime;
}
