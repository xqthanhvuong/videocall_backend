package com.example.cognito.demo.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    public String getUserId() {
        // Lấy thông tin của JWT từ Authentication đã được Spring Security xử lý
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Nếu authentication có đối tượng Jwt (là đối tượng chứa thông tin JWT)
        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getSubject();  // Trả về userId từ phần "subject"
        }

        return null;  // Nếu không tìm thấy thông tin JWT
    }
}
