package com.project_merge.jigu_travel.api.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 '/sub' 경로로 브로드캐스트하도록 메시지 브로커를 활성화
        registry.enableSimpleBroker("/sub");
        // 클라이언트가 메시지를 서버에 전송할 때 '/'을 프리픽스로 사용하도록 설정
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // '/service-websocket' 경로를 STOMP 엔드포인트로 등록
        registry.addEndpoint("/stomp-ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
        registry.addEndpoint("/stomp-ws")
                .setAllowedOriginPatterns("*");
    }

}
