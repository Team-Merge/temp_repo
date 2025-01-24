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
    private Integer conversation_id;

    @Column(nullable = false)
    private UUID user_id;


    @Column(nullable = false,length = 1000)
    private String conversation_question;

    @Column(nullable = true,length = 1000)
    private String conversation_answer;

    @Column(nullable = false)
    private Double conversation_latitude;

    @Column(nullable = false)
    private Double conversation_longitude;

    @Column(nullable = false)
    private LocalDateTime conversation_datetime;


}
