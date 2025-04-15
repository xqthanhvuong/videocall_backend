package com.example.cognito.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cognito.demo.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String cognitoUserName);
}
