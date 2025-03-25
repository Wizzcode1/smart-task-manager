package com.example.smarttaskmanager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    private String id;

    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private String userId;
    private LocalDateTime dueDate;
    private List<String> tags;
    private List<StatusChange> changeHistory;
}