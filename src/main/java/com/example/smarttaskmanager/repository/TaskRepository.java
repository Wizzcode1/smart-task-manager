package com.example.smarttaskmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.smarttaskmanager.model.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
}
