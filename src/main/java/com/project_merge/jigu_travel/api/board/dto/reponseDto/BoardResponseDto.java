package com.project_merge.jigu_travel.api.board.dto.reponseDto;

import lombok.*;

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
}