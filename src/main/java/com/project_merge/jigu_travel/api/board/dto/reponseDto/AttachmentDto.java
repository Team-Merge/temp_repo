package com.project_merge.jigu_travel.api.board.dto.reponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttachmentDto {
    private Long fileId;  // 파일의 고유 ID
    private String fileName; // 파일명
    private Long fileSize;  // 파일 크기 (바이트)

}
