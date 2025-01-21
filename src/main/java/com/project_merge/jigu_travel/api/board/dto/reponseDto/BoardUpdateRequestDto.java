package com.project_merge.jigu_travel.api.board.dto.reponseDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BoardUpdateRequestDto {
    private Long boardId;
    private String title;
    private String content;
}