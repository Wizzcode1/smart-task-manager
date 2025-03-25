package com.example.smarttaskmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.smarttaskmanager.dto.TaskRequest;
import com.example.smarttaskmanager.dto.TaskResponse;
import com.example.smarttaskmanager.kafka.TaskCreatedEvent;
import com.example.smarttaskmanager.kafka.TaskEventProducer;
import com.example.smarttaskmanager.mapper.TaskMapper;
import com.example.smarttaskmanager.model.Task;
import com.example.smarttaskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskEventProducer taskEventProducer;

    public TaskResponse createTask(TaskRequest request) {
        Task task = taskMapper.toEntity(request);
        Task saved = taskRepository.save(task);

        taskEventProducer.sendTaskCreated(TaskCreatedEvent.builder()
                .taskId(saved.getId())
                .title(saved.getTitle())
                .userId(saved.getUserId())
                .dueDate(saved.getDueDate())
                .build());

        return taskMapper.toResponse(saved);
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(String id) {
        return taskRepository.findById(id)
                .map(taskMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    public TaskResponse updateTask(String id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
        task.setTags(request.getTags());

        Task updated = taskRepository.save(task);
        return taskMapper.toResponse(updated);
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }
}