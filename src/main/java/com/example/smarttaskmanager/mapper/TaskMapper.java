package com.example.smarttaskmanager.mapper;

import org.mapstruct.Mapper;

import com.example.smarttaskmanager.dto.TaskRequest;
import com.example.smarttaskmanager.dto.TaskResponse;
import com.example.smarttaskmanager.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(TaskRequest request);
    TaskResponse toResponse(Task task);

}