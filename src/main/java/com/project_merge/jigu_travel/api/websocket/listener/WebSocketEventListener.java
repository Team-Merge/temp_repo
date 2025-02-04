package com.project_merge.jigu_travel.api.websocket.listener;

import com.project_merge.jigu_travel.api.websocket.service.SocketServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;
import java.util.Map;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private SocketServiceImpl socketServiceImpl;

    // 소켓 연결 요청
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web-socket connection");
        MessageHeaderAccessor accessor = NativeMessageHeaderAccessor.getAccessor(event.getMessage(),
                SimpMessageHeaderAccessor.class);
//        GenericMessage<?> generic = (GenericMessage<?>) accessor.getHeader("simpConnectMessage");
//        logger.info("generic = {}", generic);
//        Map<String, Object> nativeHeaders = (Map<String, Object>) generic.getHeaders().get("nativeHeaders");
//        logger.info("nativeHeader = {}", nativeHeaders);
//        String accessToken = ((List<String>) nativeHeaders.get("accessToken")).get(0);
//        logger.info("accessToken = {}", accessToken);

        // 서버 관리
        //
    }

    // 구독 요청
    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        logger.info("Received a new web-socket subscribe");
    }

    // 연결 해제
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headerAccessor.getSessionId();

        logger.info("[Disconnected] web-socket session id : {}", sessionId);
    }

}
