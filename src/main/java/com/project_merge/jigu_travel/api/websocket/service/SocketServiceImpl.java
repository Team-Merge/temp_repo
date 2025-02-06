package com.project_merge.jigu_travel.api.websocket.service;

import com.project_merge.jigu_travel.api.Place.service.PlaceServiceImpl;
import com.project_merge.jigu_travel.api.websocket.dto.requestDto.LocationRequestDto;
import com.project_merge.jigu_travel.api.websocket.dto.responseDto.PlaceResponseDto;
import com.project_merge.jigu_travel.api.websocket.dto.responseDto.SubscribeInitResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SocketServiceImpl implements SocketService{

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private PlaceServiceImpl placeServiceImpl;

    @Override
    public void sendMessage(String accessToken, LocationRequestDto locationRequestDto) {
        String destination = getDestination(locationRequestDto.getServiceUUID());

        List<String> types = locationRequestDto.getInterests();

        if (types == null || types.isEmpty()) {
            types = new ArrayList<>();
            types.add("힐링_여행");
            types.add("쇼핑_여행");
        }

        List<PlaceResponseDto> result = placeServiceImpl.findNearbyPlace(locationRequestDto.getLatitude(), locationRequestDto.getLongitude(), 1, types);
        messagingTemplate.convertAndSend(destination, result);
    }

    @Override
    public SubscribeInitResponseDto createUUID(String accessToken) {
        return SubscribeInitResponseDto.builder()
                .serviceUUID(UUID.randomUUID())
                .build();
    }

    private String getDestination(UUID serviceUUID) {
        return "/sub/" + serviceUUID;
    }
}
