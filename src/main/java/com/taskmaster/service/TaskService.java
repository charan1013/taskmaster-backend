package com.taskmaster.service;

import com.taskmaster.dto.TaskDto;
import com.taskmaster.model.Task;
import com.taskmaster.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(TaskDto.CreateTaskRequest request, String userId) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUserId(userId);
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setStatus(Task.TaskStatus.TODO);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public List<Task> getAllTasksByUser(String userId) {
        return taskRepository.findByUserId(userId);
    }

    public Task getTaskById(String taskId, String userId) {
        return taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task updateTask(String taskId, TaskDto.UpdateTaskRequest request, String userId) {
        Task task = getTaskById(taskId, userId);

        if (request.getTitle() != null) task.setTitle(request.getTitle());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        if (request.getStatus() != null) task.setStatus(request.getStatus());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        if (request.getDueDate() != null) task.setDueDate(request.getDueDate());

        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public void deleteTask(String taskId, String userId) {
        Task task = getTaskById(taskId, userId);
        taskRepository.delete(task);
    }
}
