package ch.cern.todo.service;

import ch.cern.todo.DTO.TaskDTO;
import ch.cern.todo.entity.Category;
import ch.cern.todo.entity.User;
import ch.cern.todo.entity.Task;
import ch.cern.todo.repository.TaskRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public TaskService(TaskRepository taskRepository, UserService userService, CategoryService categoryService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public Task createTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTaskName(taskDTO.getTaskName());
        task.setTaskDescription(taskDTO.getTaskDescription());
        task.setDeadline(taskDTO.getDeadline());
        Category category = categoryService.getCategoryById(taskDTO.getCategoryId());
        User user = userService.getUserById(taskDTO.getUserId());

        task.setCategory(category);
        task.setUser(user);

        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {

        taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.deleteById(taskId);
    }

    public void updateTask(Long taskId, TaskDTO taskDetailsDTO) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Update the task details
        existingTask.setTaskName(taskDetailsDTO.getTaskName());
        existingTask.setTaskDescription(taskDetailsDTO.getTaskDescription());
        existingTask.setDeadline(taskDetailsDTO.getDeadline());
        existingTask.setCategory(categoryService.getCategoryById(taskDetailsDTO.getCategoryId()));
        User user = userService.getUserById(taskDetailsDTO.getUserId());
        existingTask.setUser(user);

        taskRepository.save(existingTask);
    }

    // Search for user, name, description, deadline and category in any combination
    public List<TaskDTO> searchTasks(Long userId, String name, String description, LocalDateTime deadline, Long categoryId) {

        List<Task> tasks = taskRepository.findByFilters(userId, name, description, deadline, categoryId);
        System.out.println("Tasks list:" + tasks);
        return tasks.stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());
    }

    public List<Task> getUserTasks() {
        String currentUsername = getCurrentUsername();
        return taskRepository.findByUser_Username(currentUsername);
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
