package com.taskmaster.controller;

import com.taskmaster.dto.TaskDto;
import com.taskmaster.model.Task;
import com.taskmaster.model.User;
import com.taskmaster.repository.UserRepository;
import com.taskmaster.service.ClaudeAiService;
import com.taskmaster.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ClaudeAiService claudeAiService;

    @Autowired
    private UserRepository userRepository;

    private String getUserId(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }

    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskDto.CreateTaskRequest request,
                                         Authentication auth) {
        try {
            Task task = taskService.createTask(request, getUserId(auth));
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(Authentication auth) {
        return ResponseEntity.ok(taskService.getAllTasksByUser(getUserId(auth)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id, Authentication auth) {
        try {
            Task task = taskService.getTaskById(id, getUserId(auth));
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id,
                                         @RequestBody TaskDto.UpdateTaskRequest request,
                                         Authentication auth) {
        try {
            Task task = taskService.updateTask(id, request, getUserId(auth));
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id, Authentication auth) {
        try {
            taskService.deleteTask(id, getUserId(auth));
            return ResponseEntity.ok(Map.of("message", "Task deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // AI Feature: Generate task description using Claude
    @PostMapping("/ai/generate-description")
    public ResponseEntity<?> generateDescription(@Valid @RequestBody TaskDto.AiGenerateRequest request,
                                                  Authentication auth) {
        try {
            String description = claudeAiService.generateTaskDescription(request.getTitle());
            return ResponseEntity.ok(Map.of("description", description));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
