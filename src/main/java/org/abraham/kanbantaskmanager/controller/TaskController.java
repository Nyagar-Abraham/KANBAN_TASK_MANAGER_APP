package org.abraham.kanbantaskmanager.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.abraham.kanbantaskmanager.dtos.CreateTaskRequest;
import org.abraham.kanbantaskmanager.dtos.CreateTaskResponse;
import org.abraham.kanbantaskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @PostMapping()
    public ResponseEntity<CreateTaskResponse> createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest , UriComponentsBuilder uriBuilder) {
        var task = taskService.createTask(createTaskRequest);
        var uri = uriBuilder.path("/tasks/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).body(task);
    }
}
