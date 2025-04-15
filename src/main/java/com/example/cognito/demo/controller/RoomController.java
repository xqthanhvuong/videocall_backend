package com.example.cognito.demo.controller;

import com.example.cognito.demo.entity.Room;
import com.example.cognito.demo.entity.User;
import com.example.cognito.demo.repository.RoomRepository;
import com.example.cognito.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    // Tạo phòng mới
    @PostMapping("/create")
    public Room createRoom(@RequestParam String roomName) {
        Room room = new Room();
        room.setRoomName(roomName);
        return roomRepository.save(room);
    }

    // Người dùng tham gia phòng
    @PostMapping("/join")
    public String joinRoom(@RequestParam String roomId, @RequestParam String username) {
        Room room = roomRepository.findById(Long.parseLong(roomId)).orElse(null);
        if (room == null) {
            return "Room not found!";
        }

        User user = new User();
        user.setUsername(username);
        user.setRoom(room);
        userRepository.save(user);

        return "User joined room: " + room.getRoomName();
    }

    // Lấy thông tin phòng
    @GetMapping("/{roomId}")
    public Room getRoom(@PathVariable String roomId) {
        return roomRepository.findById(Long.parseLong(roomId)).orElse(null);
    }

    // Lấy danh sách người dùng trong phòng
    @GetMapping("/{roomId}/users")
    public List<User> getUsersInRoom(@PathVariable String roomId) {
        Room room = roomRepository.findById(Long.parseLong(roomId)).orElse(null);
        if (room == null) {
            return null;
        }
        return room.getUsers();
    }
}
