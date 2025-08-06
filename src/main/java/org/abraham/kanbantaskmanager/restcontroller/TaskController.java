//package org.abraham.kanbantaskmanager.restcontroller;
//
//
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.abraham.kanbantaskmanager.dtos.RestDtos.CreateTaskRequest;
//import org.abraham.kanbantaskmanager.dtos.RestDtos.EditTaskColumnRequest;
//import org.abraham.kanbantaskmanager.dtos.RestDtos.EditTaskRequest;
//import org.abraham.kanbantaskmanager.dtos.RestDtos.TaskResponse;
//import org.abraham.kanbantaskmanager.service.TaskService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/tasks")
//@AllArgsConstructor
//public class TaskController {
//
//    private TaskService taskService;
//
//    @GetMapping
//    public ResponseEntity<List<TaskResponse>> getAllTasks(@RequestParam(name = "size") Integer size) {
//        var tasks = taskService.getTasks();
//        return ResponseEntity.ok(tasks);
//    }
//    @GetMapping("/{board_id}/{column_id}")
//    public ResponseEntity<List<TaskResponse>> getTasksByBoardAndBoardColumnId(
//            @PathVariable(name = "board_id") Long boardId,
//            @PathVariable(name = "column_id") Long columnId,
//            @RequestParam(name = "sort", defaultValue = "title")  String sort,
//            @RequestParam(name ="size",defaultValue = "2") Integer size,
//            @RequestParam(name = "page",defaultValue ="1") Integer page
//    ){
//        var tasks = taskService.getTasksByBoardAndBoardColumnId(boardId, columnId,page,size, sort);
//        return ResponseEntity.ok(tasks);
//    }
//
//    @GetMapping("/{task_id}")
//    public ResponseEntity<TaskResponse> getTaskById(@PathVariable(name = "task_id") Long taskId) {
//      var task =  taskService.getTaskById(taskId);
//      return ResponseEntity.ok(task);
//    }
//
//    @PostMapping()
//    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request , UriComponentsBuilder uriBuilder) {
//        var task = taskService.createTask(request);
//        var uri = uriBuilder.path("/tasks/{id}").buildAndExpand(task.getId()).toUri();
//        return ResponseEntity.created(uri).body(task);
//    }
//
//    @PutMapping("/{task_id}")
//    public ResponseEntity<TaskResponse> updateTask(@PathVariable(name = "task_id") Long taskId, @Valid @RequestBody EditTaskRequest request) {
//        var task = taskService.updateTask(request, taskId);
//        return ResponseEntity.ok().body(task);
//    }
//
//    @PutMapping("/{task_id}/update-column")
//    public ResponseEntity<?> updateTaskColumn(@PathVariable(name = "task_id") Long taskId, @Valid @RequestBody EditTaskColumnRequest request) {
//       taskService.updateTaskColumn(request, taskId);
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping("/subtasks/{id}")
//    public ResponseEntity<?> toggleSubtaskStatus(@PathVariable(name = "id") Long subtaskId){
//        taskService.toggleSubtaskStatus(subtaskId);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{task_id}")
//    public ResponseEntity<?> deleteTask(@PathVariable(name = "task_id") Long taskId){
//        taskService.deleteTask(taskId);
//        return ResponseEntity.noContent().build();
//    }
//}
