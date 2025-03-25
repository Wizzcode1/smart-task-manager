package com.example.smarttaskmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

import com.example.smarttaskmanager.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
