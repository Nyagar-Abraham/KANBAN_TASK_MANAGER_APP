package org.abraham.kanbantaskmanager.controllers.datamutations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.abraham.kanbantaskmanager.dtos.TaskResponse;
import org.abraham.kanbantaskmanager.dtos.requests.CreateTaskRequest;
import org.abraham.kanbantaskmanager.dtos.requests.CreateTaskResponse;
import org.abraham.kanbantaskmanager.dtos.requests.UpdateTaskInput;
import org.abraham.kanbantaskmanager.dtos.requests.UpdateTaskPositionInput;
import org.abraham.kanbantaskmanager.dtos.responses.DeleteTaskResponse;
import org.abraham.kanbantaskmanager.dtos.responses.UpdateTaskPositionResponse;
import org.abraham.kanbantaskmanager.dtos.responses.UpdateTaskResponse;
import org.abraham.kanbantaskmanager.service.TaskService;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.List;


@DgsComponent
public class TaskMutations {
    private final TaskService taskService;

    public TaskMutations(TaskService taskService) {
        this.taskService = taskService;
    }

    @DgsMutation
    public CreateTaskResponse createTask(@InputArgument CreateTaskRequest task) {
        var response = new CreateTaskResponse();

        try{
            var taskResponse = taskService.createTask(task);
            response.setCode(HttpStatus.CREATED.value());
            response.setMessage("success");
            response.setSuccess(true);
            response.setTask(taskResponse);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
      return response;
    }

    @DgsMutation
    public DeleteTaskResponse deleteTaskById(@InputArgument String id) {
        var response = new DeleteTaskResponse();
        try{
            taskService.deleteTask(Long.parseLong(id));
            response.setCode(HttpStatus.OK.value());
            response.setMessage("success");
            response.setSuccess(true);
            response.setTaskId(Integer.parseInt(id));
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }

        return response;
    }

    @DgsMutation
    public TaskResponse updateTaskSubtasks(@InputArgument String id,@InputArgument List<String> subtasks) {
        var subtasksSet = new HashSet<>(subtasks);
        return taskService.updateTaskSubtasks(Long.parseLong(id), subtasksSet);
    }

    @DgsMutation
    public TaskResponse updateTaskColumn(@InputArgument String taskId,@InputArgument String columnId) {
        return taskService.updateTaskColumn(Long.parseLong(taskId), Long.parseLong(columnId));
    }

    @DgsMutation
    public TaskResponse updateTaskTitle(@InputArgument String id,@InputArgument String title) {
        return taskService.updateTaskTitle(Long.parseLong(id), title);
    }

    @DgsMutation
    public TaskResponse updateTaskDescription(@InputArgument String id,@InputArgument String description) {
        return taskService.updateTaskDescription(Long.parseLong(id), description);
    }

    @DgsMutation
    public UpdateTaskPositionResponse updateTaskPosition(@InputArgument UpdateTaskPositionInput input) {
        var response = new UpdateTaskPositionResponse();

        try {
            taskService.updateTaskPosition(input);
            response.setCode(HttpStatus.OK.value());
            response.setMessage("success");
            response.setSuccess(true);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return response;
    }

    @DgsMutation
    public UpdateTaskResponse updateTask(@InputArgument String id, @InputArgument UpdateTaskInput input) {
        var response = new UpdateTaskResponse();

        try {
            var task = taskService.updateTask(input, Long.parseLong(id));
            response.setCode(HttpStatus.OK.value());
            response.setMessage("success");
            response.setSuccess(true);
            response.setTask(task);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return response;
    }
}
