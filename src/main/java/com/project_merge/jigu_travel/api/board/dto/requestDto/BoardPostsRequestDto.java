package com.project_merge.jigu_travel.api.board.dto.requestDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BoardPostsRequestDto {
    private String title;
    private String content;
}
