package com.example.cognito.demo.service;

import com.example.cognito.demo.entity.Room;
import com.example.cognito.demo.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom(String roomName) {
        Room room = new Room();
        room.setRoomName(roomName);
        return roomRepository.save(room);
    }

    public void addUserToRoom(String roomId, String userId) {
        Room room = roomRepository.findById(Long.valueOf(roomId)).orElseThrow(() -> new RuntimeException("Room not found"));
        // Thêm người dùng vào phòng
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
