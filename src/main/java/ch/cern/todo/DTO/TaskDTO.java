package ch.cern.todo.DTO;

import ch.cern.todo.entity.Task;

import java.time.LocalDateTime;

public class TaskDTO {
    private String taskName;
    private String taskDescription;
    private LocalDateTime deadline;
    private Long categoryId;
    private Long userId;

    // Default constructor
    public TaskDTO() {
    }

    // Constructor
    public TaskDTO(Task task) {
        this.taskName = task.getTaskName();
        this.taskDescription = task.getTaskDescription();
        this.deadline = task.getDeadline();
        this.categoryId = task.getCategory() != null ? task.getCategory().getCategoryId() : null;
    }

    // Getters and Setters
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

