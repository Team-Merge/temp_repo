package com.project_merge.jigu_travel.api.board.dto.reponseDto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BoardResponseDto {

    private Long boardId;

    private String userId;

    private String nickname;

    private String title;

    private String content;

    private Long likes;

    private List<AttachmentDto> attachments;

    private LocalDateTime createdAt;


}