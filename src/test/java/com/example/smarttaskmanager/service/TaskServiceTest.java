package com.example.smarttaskmanager.service;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import com.example.smarttaskmanager.dto.TaskRequest;
import com.example.smarttaskmanager.kafka.TaskEventProducer;
import com.example.smarttaskmanager.mapper.TaskMapper;
import com.example.smarttaskmanager.mapper.TaskMapperImpl;
import com.example.smarttaskmanager.model.Priority;
import com.example.smarttaskmanager.model.Task;
import com.example.smarttaskmanager.repository.TaskRepository;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private AiTaskAssistantService aiService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskEventProducer taskEventProducer;

    @Spy
    private TaskMapper taskMapper = new TaskMapperImpl();

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldSuggestPriorityIfMissing() {
        // given
        TaskRequest request = new TaskRequest();
        request.setDescription("Fix bug");
        when(aiService.suggestPriority("Fix bug")).thenReturn("HIGH");
        when(taskRepository.save(any())).thenReturn(
                Task.builder()
                        .id("1")
                        .title("Title")
                        .description("Fix bug")
                        .priority(Priority.HIGH)
                        .userId("userId")
                        .dueDate(LocalDateTime.now())
                        .build());
        doNothing().when(taskEventProducer).sendTaskCreated(any());

        // when
        taskService.createTask(request);

        // then
        verify(aiService).suggestPriority("Fix bug");
    }
}