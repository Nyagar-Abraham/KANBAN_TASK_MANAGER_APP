package org.abraham.kanbantaskmanager.graphqlcontroller;

import org.abraham.kanbantaskmanager.dtos.CreateTaskRequest;
import org.abraham.kanbantaskmanager.dtos.SubTaskResponse;
import org.abraham.kanbantaskmanager.dtos.TaskResponse;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;
import org.abraham.kanbantaskmanager.service.TaskService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

@Controller
public class GraphqlTaskController {

    private final TaskService taskService;

    public GraphqlTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @QueryMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getTasks();
    }

    @QueryMapping
    public TaskResponse getTaskById(@Argument String task_id) {
        return taskService.getTaskById(Long.parseLong(task_id));
    }

    @MutationMapping
    public TaskResponse createTask(@Argument("task") CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @MutationMapping
    public TaskResponse updateTaskTitle(@Argument String task_id, @Argument String title) {
        return taskService.updateTaskTitle(Long.parseLong(task_id),title);
    }

    @MutationMapping
    public TaskResponse updateTaskDescription(@Argument String task_id, @Argument String description) {
        return taskService.updateTaskDescription(Long.parseLong(task_id),description);
    }

    @MutationMapping
    public TaskResponse updateTaskColumn(@Argument String task_id, @Argument TaskStatusAndColumnName column) {
        return taskService.updateTaskColumn(Long.parseLong(task_id),column);
    }

    @MutationMapping
    public TaskResponse updateTaskSubtasks(@Argument String task_id, @Argument Set<String> subtasks) {
        return taskService.updateTaskSubtasks(Long.parseLong(task_id),subtasks);
    }

    @MutationMapping
    public SubTaskResponse toggleSubtaskStatus(@Argument String subtask_id) {
        return taskService.toggleSubtaskStatus(Long.parseLong(subtask_id));
    }

    @MutationMapping
    public String deleteTaskById(@Argument String task_id) {
        taskService.deleteTask(Long.parseLong(task_id));
        return task_id;
    }

}
