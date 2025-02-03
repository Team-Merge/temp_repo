package com.project_merge.jigu_travel.api.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY) // 게시글과 다대일 관계
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false)
    private String fileName; // 원본 파일명

    @Column(nullable = false)
    private String filePath; // 저장된 파일 경로

    @Column(nullable = false)
    private String fileType; // MIME 타입 (예: image/png, application/pdf)

    @Column(nullable = false)
    private Long fileSize; // 파일 크기 (바이트 단위)
}
