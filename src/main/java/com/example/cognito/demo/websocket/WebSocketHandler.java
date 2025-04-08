package com.example.cognito.demo.websocket;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, org.springframework.web.socket.TextMessage message) {
        // Xử lý tín hiệu WebRTC
        System.out.println("Received message: " + message.getPayload());
        try {
            session.sendMessage(new org.springframework.web.socket.TextMessage("Message received"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
