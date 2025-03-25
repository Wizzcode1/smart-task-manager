package com.example.smarttaskmanager.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusChange {
    private Status status;
    private LocalDateTime timestamp;
}