package com.example.smarttaskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.smarttaskmanager.dto.TaskRequest;
import com.example.smarttaskmanager.dto.TaskResponse;
import com.example.smarttaskmanager.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "changeHistory", ignore = true)
    Task toEntity(TaskRequest request);

    TaskResponse toResponse(Task task);

}