package com.example.cognito.demo.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class SocketIOServerConfig {

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(8001); // Port cho Socket.IO server

        // Cấu hình CORS cho Socket.IO
        config.setOrigin("*"); // Cho phép tất cả các nguồn (có thể thay bằng "http://localhost:4200" nếu chỉ muốn chấp nhận từ 1 nguồn)

        // Khởi tạo và trả về SocketIOServer
        SocketIOServer server = new SocketIOServer(config);

        // Start server
        new Thread(() -> {
            try {
                System.out.println("Starting Socket.IO Server...");
                server.start();
                System.out.println("Socket.IO Server started!");
            } catch (Exception e) {
                System.err.println("Error starting Socket.IO Server: " + e.getMessage());
            }
        }).start();

        return server;
    }
}
