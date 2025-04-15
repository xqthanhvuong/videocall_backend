package com.example.cognito.demo.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

public class VideoCallHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessionMap = new HashMap<>();
    private final Map<String, Set<String>> roomToPeersMap = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessionMap.put(session.getId(), session);
        System.out.println("✅ Connected: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String peerId = session.getId();
        sessionMap.remove(peerId);
        roomToPeersMap.values().forEach(peers -> peers.remove(peerId));
        System.out.println("❎ Disconnected: " + peerId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> data = objectMapper.readValue(message.getPayload(), Map.class);
        String type = (String) data.get("type");

        switch (type) {
            case "createRoom" -> {
                String roomId = session.getId();  // Use socket ID as roomId
                roomToPeersMap.put(roomId, new HashSet<>(Set.of(session.getId())));
                Map<String, Object> response = new HashMap<>();
                response.put("type", "roomCreated");
                response.put("roomId", roomId);
                response.put("peerId", session.getId());  // send back peerId too
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
                System.out.println("🛠️ Room created by: " + session.getId());
            }

            case "joinRoom" -> {
                String roomId = (String) data.get("roomId");
                String peerId = session.getId();
                roomToPeersMap.computeIfAbsent(roomId, k -> new HashSet<>()).add(peerId);

                // Inform others in room
                for (String otherPeerId : roomToPeersMap.get(roomId)) {
                    if (!otherPeerId.equals(peerId)) {
                        WebSocketSession otherSession = sessionMap.get(otherPeerId);
                        if (otherSession != null && otherSession.isOpen()) {
                            Map<String, Object> peerJoined = new HashMap<>();
                            peerJoined.put("type", "peerJoined");
                            peerJoined.put("peerId", peerId);
                            otherSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(peerJoined)));
                        }
                    }
                }

                // Confirm join
                Map<String, Object> joinedRoom = new HashMap<>();
                joinedRoom.put("type", "joinedRoom");
                joinedRoom.put("peerId", peerId);
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(joinedRoom)));
                System.out.println("👥 " + peerId + " joined room " + roomId);
            }

            case "signal" -> {
                String targetPeerId = (String) data.get("peerId");
                WebSocketSession targetSession = sessionMap.get(targetPeerId);
                if (targetSession != null && targetSession.isOpen()) {
                    targetSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(data)));
                    System.out.println("📡 Relayed signal to " + targetPeerId);
                }
            }

            default -> System.out.println("⚠️ Unknown message type: " + type);
        }
    }
}
