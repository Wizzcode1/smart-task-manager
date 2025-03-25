package com.example.smarttaskmanager.scheduler;

import com.example.smarttaskmanager.kafka.TaskReminderEvent;
import com.example.smarttaskmanager.kafka.TaskEventProducer;
import com.example.smarttaskmanager.model.Task;
import com.example.smarttaskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskDeadlineScheduler {

    private final TaskRepository taskRepository;
    private final TaskEventProducer taskEventProducer;

    @Scheduled(cron = "0 0 8 * * *") // everyday at 08:00
    public void notifyTasksDueTomorrow() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        List<Task> tasks = taskRepository.findAll().stream()
                .filter(task -> task.getDueDate() != null &&
                        task.getDueDate().toLocalDate().isEqual(tomorrow))
                .toList();

        log.info("🔔 Found {} tasks due tomorrow", tasks.size());

        tasks.forEach(task -> {
            TaskReminderEvent event = TaskReminderEvent.builder()
                    .taskId(task.getId())
                    .title(task.getTitle())
                    .userId(task.getUserId())
                    .dueDate(task.getDueDate())
                    .build();

            taskEventProducer.sendReminder(event);
        });
    }
}