package org.abraham.kanbantaskmanager.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.abraham.kanbantaskmanager.dtos.CreateTaskRequest;
import org.abraham.kanbantaskmanager.dtos.EditTaskColumnRequest;
import org.abraham.kanbantaskmanager.dtos.EditTaskRequest;
import org.abraham.kanbantaskmanager.dtos.TaskResponse;
import org.abraham.kanbantaskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        var tasks = taskService.getTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping()
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request , UriComponentsBuilder uriBuilder) {
        var task = taskService.createTask(request);
        var uri = uriBuilder.path("/tasks/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).body(task);
    }

    @PutMapping("/{task_id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable(name = "task_id") Long taskId, @Valid @RequestBody EditTaskRequest request) {
        var task = taskService.updateTask(request, taskId);
        return ResponseEntity.ok().body(task);
    }

    @PutMapping("/{task_id}/update-column")
    public ResponseEntity<?> updateTaskColumn(@PathVariable(name = "task_id") Long taskId, @Valid @RequestBody EditTaskColumnRequest request) {
       taskService.updateTaskColumn(request, taskId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/subtasks/{id}")
    public ResponseEntity<?> toggleSubtaskStatus(@PathVariable(name = "id") Long subtaskId){
        taskService.toggleSubtaskStatus(subtaskId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{task_id}")
    public ResponseEntity<?> deleteTask(@PathVariable(name = "task_id") Long taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
