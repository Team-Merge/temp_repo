package com.project_merge.jigu_travel.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseDto {

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
