package com.project_merge.jigu_travel.api.websocket.dto.requestDto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LocationRequestDto {
    private UUID serviceUUID;
    private Double latitude;
    private Double longitude;
}
