package com.example.cognito.demo.service;

import com.example.cognito.demo.entity.Room;
import com.example.cognito.demo.entity.User;
import com.example.cognito.demo.repository.RoomRepository;
import com.example.cognito.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RoomService {

    RoomRepository roomRepository;

    UserRepository userRepository;

    public Room createRoom(String roomName, String cognitoUserId) {
        User user = userRepository.findByCognitoUserId(cognitoUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Room room = new Room();
        room.setRoomName(roomName);
        room.setCreatedBy(user);
        return roomRepository.save(room);
    }

    // Lấy danh sách phòng họp của người dùng
    public List<Room> getRoomsByUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return roomRepository.findByCreatedBy(user);
    }
}
