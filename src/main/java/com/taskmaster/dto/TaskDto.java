package com.taskmaster.dto;

import com.taskmaster.model.Task;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class TaskDto {

    public static class CreateTaskRequest {
        @NotBlank(message = "Title is required")
        private String title;
        private String description;
        private Task.TaskPriority priority = Task.TaskPriority.MEDIUM;
        private LocalDateTime dueDate;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public Task.TaskPriority getPriority() { return priority; }
        public void setPriority(Task.TaskPriority priority) { this.priority = priority; }

        public LocalDateTime getDueDate() { return dueDate; }
        public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    }

    public static class UpdateTaskRequest {
        private String title;
        private String description;
        private Task.TaskStatus status;
        private Task.TaskPriority priority;
        private LocalDateTime dueDate;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public Task.TaskStatus getStatus() { return status; }
        public void setStatus(Task.TaskStatus status) { this.status = status; }

        public Task.TaskPriority getPriority() { return priority; }
        public void setPriority(Task.TaskPriority priority) { this.priority = priority; }

        public LocalDateTime getDueDate() { return dueDate; }
        public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    }

    public static class AiGenerateRequest {
        @NotBlank(message = "Title is required")
        private String title;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
    }
}