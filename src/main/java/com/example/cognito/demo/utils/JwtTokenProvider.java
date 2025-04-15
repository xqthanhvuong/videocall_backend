package com.example.cognito.demo.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    public String getUserName() {
        // Lấy thông tin của JWT từ Authentication đã được Spring Security xử lý
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Nếu authentication có đối tượng Jwt (là đối tượng chứa thông tin JWT)
        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();

            // Lấy username từ phần claim trong JWT (không phải sub)
            Object username = jwt.getClaims().get("username");

            if (username != null) {
                return username.toString();  // Trả về username
            }
        }

        return null;  // Nếu không tìm thấy thông tin JWT hoặc không có username
    }

}
