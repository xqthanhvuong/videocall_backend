package com.example.cognito.demo.controller;

import com.example.cognito.demo.entity.Room;
import com.example.cognito.demo.service.RoomService;
import com.example.cognito.demo.utils.JwtTokenProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/rooms")
public class RoomController {

    RoomService roomService;
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public Room createRoom(@RequestParam String roomName) {
        // Trích xuất cognitoUserId từ token
        String cognitoUserId = jwtTokenProvider.getUserId();

        // Tạo phòng mới và lưu vào cơ sở dữ liệu
        return roomService.createRoom(roomName, cognitoUserId);
    }
}
