package ch.cern.todo.controller;

import ch.cern.todo.DTO.TaskDTO;
import ch.cern.todo.entity.Task;
import ch.cern.todo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {

        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        Task createdTask = taskService.createTask(taskDTO);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        System.out.println("Zien we een taskId: " + taskId);
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDetailsDTO) {
        taskService.updateTask(taskId, taskDetailsDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<TaskDTO>> searchTasks(
            @RequestParam(required = false) Long userId, // ToDo get userId from Authentication
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDateTime deadline,
            @RequestParam(required = false) Long categoryId
    ) {

        List<TaskDTO> tasksTDO = taskService.searchTasks(userId, name, description, deadline, categoryId);
        return new ResponseEntity<>(tasksTDO, HttpStatus.OK);
    }
}
