package com.heroesOfUbersharik.WebSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WsConfig implements WebSocketMessageBrokerConfigurer {



    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Регистрируем STOMP endpoint на пути /ws
        registry.addEndpoint("/ws").withSockJS(); // Подключение через SockJS для совместимости
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Устанавливаем простую брокеризацию сообщений
        registry.enableSimpleBroker("/main"); // Простой брокер сообщений для "/main"
        registry.setApplicationDestinationPrefixes("/app"); // Префикс для обработки приложением
    }
}