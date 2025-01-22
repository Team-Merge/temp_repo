package com.project_merge.jigu_travel.api.websocket.service;

import com.project_merge.jigu_travel.api.websocket.dto.requestDto.LocationRequestDto;
import com.project_merge.jigu_travel.api.websocket.dto.responseDto.SubscribeInitResponseDto;

import java.util.UUID;

public interface SocketService {
    void sendMessage(String accessToken, LocationRequestDto locationRequestDto);
    SubscribeInitResponseDto createUUID(String accessToken);
}
