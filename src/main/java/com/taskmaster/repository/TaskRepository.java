package com.taskmaster.repository;

import com.taskmaster.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByUserId(String userId);
    List<Task> findByUserIdAndStatus(String userId, Task.TaskStatus status);
    Optional<Task> findByIdAndUserId(String id, String userId);
    void deleteByIdAndUserId(String id, String userId);
}
