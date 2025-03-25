package com.example.smarttaskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}