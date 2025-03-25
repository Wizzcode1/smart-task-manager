package com.example.smarttaskmanager.kafka;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskReminderEvent {
    private String taskId;
    private String title;
    private String userId;
    private LocalDateTime dueDate;
}