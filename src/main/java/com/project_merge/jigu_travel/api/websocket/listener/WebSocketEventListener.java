package com.project_merge.jigu_travel.api.websocket.listener;

import com.project_merge.jigu_travel.api.websocket.service.SocketServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;


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
