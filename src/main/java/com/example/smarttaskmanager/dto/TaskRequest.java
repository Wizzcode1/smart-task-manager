package com.example.smarttaskmanager.dto;

import com.example.smarttaskmanager.model.Priority;
import com.example.smarttaskmanager.model.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequest {
    @NotBlank
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private String userId;
    private LocalDateTime dueDate;
    private List<String> tags;
}