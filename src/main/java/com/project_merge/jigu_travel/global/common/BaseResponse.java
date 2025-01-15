package com.project_merge.jigu_travel.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    @Builder.Default
    private Integer code = 200;

    @Builder.Default
    private String message = "SUCCESS";

    private T data;
}