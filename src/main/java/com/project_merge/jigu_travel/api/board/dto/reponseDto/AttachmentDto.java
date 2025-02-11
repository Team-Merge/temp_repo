package com.project_merge.jigu_travel.api.board.dto.reponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttachmentDto {
    private Long fileId;
    private String fileName;
    private Long fileSize;

}
