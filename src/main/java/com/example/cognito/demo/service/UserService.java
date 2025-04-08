package com.example.cognito.demo.service;

import com.example.cognito.demo.entity.User;
import com.example.cognito.demo.exception.BadException;
import com.example.cognito.demo.exception.ErrorCode;
import com.example.cognito.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {

    UserRepository userRepository;

    // Đăng ký người dùng
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // Tìm người dùng theo email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new BadException(ErrorCode.USER_NOT_EXISTED));
    }

    public User getUserByCognitoUserId(String cognitoUserId) {
        return userRepository.findByCognitoUserId(cognitoUserId).orElse(null);
    }


}

