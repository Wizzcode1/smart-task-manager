package com.example.smarttaskmanager.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// TODO move to 2nd microservice

@Component
@Slf4j
public class TaskNotificationConsumer {


    @KafkaListener(topics = "task-created", groupId = "task-consumer-group")
    public void handleTaskCreated(TaskCreatedEvent event) {
        log.info("📩 [Kafka] Notifying user '{}' about new task '{}' due on {}",
                event.getUserId(), event.getTitle(), event.getDueDate());
    }

    @KafkaListener(topics = "task-reminder", groupId = "task-consumer-group")
    public void handleTaskReminder(TaskReminderEvent event) {
        log.info("⏰ [Kafka] Reminder for user '{}' – task '{}' is due on {}",
                event.getUserId(), event.getTitle(), event.getDueDate());
    }
}