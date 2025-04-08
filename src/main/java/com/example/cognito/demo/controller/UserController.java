package com.example.cognito.demo.controller;

import com.example.cognito.demo.dto.response.JsonResponse;
import com.example.cognito.demo.entity.User;
import com.example.cognito.demo.service.UserService;
import com.example.cognito.demo.utils.JwtTokenProvider;
import io.swagger.v3.core.util.Json;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {

    UserService userService;
    JwtTokenProvider jwtTokenProvider;

    // Đăng ký người dùng từ JWT token
    @PostMapping("/register")
    public JsonResponse<?> registerUser() {
        String cognitoUserId = jwtTokenProvider.getUserId();


        User existingUser = userService.getUserByCognitoUserId(cognitoUserId);

        if (existingUser == null) {
            User newUser = new User();
            newUser.setCognitoUserId(cognitoUserId);
            newUser.setUsername("username_from_token");  // Bạn có thể lấy thông tin từ token
            newUser.setEmail("email_from_token");  // Tương tự, lấy email từ token
            return JsonResponse.success(userService.registerUser(newUser));
        }

        return JsonResponse.success(existingUser); // Nếu người dùng đã có trong DB, trả về thông tin người dùng
    }
}
