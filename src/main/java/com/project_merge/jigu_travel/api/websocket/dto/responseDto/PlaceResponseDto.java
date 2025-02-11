package com.project_merge.jigu_travel.api.websocket.dto.responseDto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PlaceResponseDto {
    private Integer placeId;
    private List<String> types;
    private String name;
    private String tel;
    private Double latitude;
    private Double longitude;
    private String address;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
