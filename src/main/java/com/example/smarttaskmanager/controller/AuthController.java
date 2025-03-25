package com.example.smarttaskmanager.controller;

import com.example.smarttaskmanager.dto.AuthResponse;
import com.example.smarttaskmanager.dto.UserRequest;
import com.example.smarttaskmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

}
