package com.project_merge.jigu_travel.api.websocket.controller;

import com.project_merge.jigu_travel.api.websocket.dto.requestDto.LocationRequestDto;
import com.project_merge.jigu_travel.api.websocket.dto.responseDto.SubscribeInitResponseDto;
import com.project_merge.jigu_travel.api.websocket.service.SocketServiceImpl;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/guide")
public class SocketController {

    private static final Logger logger = LoggerFactory.getLogger(SocketController.class);

    @Autowired
    private SocketServiceImpl socketServiceImpl;

    @GetMapping("/init")
    public ResponseEntity<BaseResponse<SubscribeInitResponseDto>> subscribeInit(@RequestHeader("Authorization") String accessToken) {
        // UserID와 별개의 UUID를 생성해 구독번호로 사용
        SubscribeInitResponseDto subscribeInitResponseDto = socketServiceImpl.createUUID(accessToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.<SubscribeInitResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(subscribeInitResponseDto)
                        .build());
    }

    @MessageMapping("/place")
    public void sendNearPlace(SimpMessageHeaderAccessor headerAccessor, @Payload LocationRequestDto locationRequestDto) {

        String accessToken = headerAccessor.getFirstNativeHeader("Authorization");
        logger.info("Request Location Message. serviceUUID : {}, latitude : {}, longitude : {}, interests={}", locationRequestDto.getServiceUUID(), locationRequestDto.getLatitude(), locationRequestDto.getLongitude(), locationRequestDto.getInterests());

        if(locationRequestDto.getLatitude() == null) throw new IllegalArgumentException();

        socketServiceImpl.sendMessage(accessToken, locationRequestDto);
    }
}
