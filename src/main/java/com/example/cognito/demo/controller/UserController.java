package com.example.cognito.demo.controller;

import com.example.cognito.demo.dto.response.JsonResponse;
import com.example.cognito.demo.entity.User;
import com.example.cognito.demo.service.UserService;
import com.example.cognito.demo.utils.JwtTokenProvider;
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
        String cognitoUserName = jwtTokenProvider.getUserName();


        User existingUser = userService.getUserByUserName(cognitoUserName);

        if (existingUser == null) {
            User newUser = new User();
            newUser.setUsername(cognitoUserName);
            return JsonResponse.success(userService.registerUser(newUser));
        }

        return JsonResponse.success(existingUser); // Nếu người dùng đã có trong DB, trả về thông tin người dùng
    }
}
