package com.project_merge.jigu_travel.api.websocket.dto.responseDto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SubscribeInitResponseDto {
    private UUID serviceUUID;
}
