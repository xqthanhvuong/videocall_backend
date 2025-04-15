package com.example.cognito.demo.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.example.cognito.demo.entity.Room;
import com.example.cognito.demo.entity.User;
import com.example.cognito.demo.repository.RoomRepository;
import com.example.cognito.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocketIOController {

    @Autowired
    private SocketIOServer server;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void startServer() {
        // Lắng nghe kết nối
        server.addConnectListener(client -> {
            System.out.println("Client connected: " + client.getSessionId());
        });

        // Lắng nghe khi ngắt kết nối
        server.addDisconnectListener(client -> {
            System.out.println("Client disconnected: " + client.getSessionId());
            // Thông báo cho các peer khác khi có người rời đi
            server.getBroadcastOperations().sendEvent("userLeft", client.getSessionId().toString());
        });

        // Lắng nghe sự kiện người dùng tham gia phòng
        server.addEventListener("joinRoom", String.class, (client, data, ackSender) -> {
            String[] dataParts = data.split(":");
            String roomName = dataParts[0]; // Tên phòng
            String username = dataParts[1]; // Username người dùng

            // Tìm kiếm hoặc tạo phòng
            Room room = roomRepository.findByRoomName(roomName);
            if (room == null) {
                room = new Room();
                room.setRoomName(roomName);
                room = roomRepository.save(room);
            }

            // Lưu thông tin người dùng và liên kết với phòng
            User user = new User();
            user.setUsername(username);
            user.setRoom(room);
            userRepository.save(user);

            // Thông báo cho các peer khác về sự kiện người dùng tham gia
            server.getBroadcastOperations().sendEvent("newUserJoined", roomName);

            // Gửi thông tin cho người dùng mới
            client.sendEvent("joinedRoom", "Welcome " + username + " to the room " + roomName);
        });
    }
}
