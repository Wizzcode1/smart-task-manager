package com.example.smarttaskmanager.service;

import com.example.smarttaskmanager.dto.AuthResponse;
import com.example.smarttaskmanager.dto.UserRequest;
import com.example.smarttaskmanager.model.Role;
import com.example.smarttaskmanager.model.User;
import com.example.smarttaskmanager.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;
    @Mock private AuthenticationManager authenticationManager;

    @InjectMocks private UserService userService;

    @Test
    void shouldRegisterUserAndReturnToken() {
        // given
        UserRequest request = new UserRequest("user", "pass");
        when(passwordEncoder.encode("pass")).thenReturn("hashed");
        when(jwtService.generateToken(any())).thenReturn("mock-token");

        // when
        AuthResponse response = userService.register(request);

        // then
        assertEquals("mock-token", response.getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldLoginAndReturnToken() {
        // given
        UserRequest request = new UserRequest("user", "pass");
        User user = User.builder().username("user").password("hashed").role(Role.USER).build();

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("login-token");

        // when
        AuthResponse response = userService.login(request);

        // then
        assertEquals("login-token", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}