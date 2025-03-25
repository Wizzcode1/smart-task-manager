package com.example.smarttaskmanager.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskEventProducer {

    private final KafkaTemplate<String, TaskCreatedEvent> taskCreatedTemplate;
    private final KafkaTemplate<String, TaskReminderEvent> taskReminderTemplate;

    public void sendTaskCreated(TaskCreatedEvent event) {
        log.info("Sending TaskCreatedEvent: {}", event);
        taskCreatedTemplate.send("task-created", event);
    }

    public void sendReminder(TaskReminderEvent event) {
        log.info("📨 Sending reminder: {}", event);
        taskReminderTemplate.send("task-reminder", event);
    }
}