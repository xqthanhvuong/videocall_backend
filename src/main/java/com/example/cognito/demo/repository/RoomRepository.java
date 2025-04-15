package com.example.cognito.demo.repository;

import com.example.cognito.demo.entity.Room;
import com.example.cognito.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByRoomName(String roomName);
}
