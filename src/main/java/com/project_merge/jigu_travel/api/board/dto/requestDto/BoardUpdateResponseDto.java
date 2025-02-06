package com.project_merge.jigu_travel.api.board.dto.requestDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BoardUpdateResponseDto {
    private String title;
    private String content;
    private String inquiryType;
}
