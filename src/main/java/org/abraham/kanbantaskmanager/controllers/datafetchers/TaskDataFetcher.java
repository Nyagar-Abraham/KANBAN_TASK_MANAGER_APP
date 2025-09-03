package org.abraham.kanbantaskmanager.controllers.datafetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.abraham.kanbantaskmanager.dtos.TaskResponse;
import org.abraham.kanbantaskmanager.service.TaskService;

import java.util.List;

@DgsComponent
public class TaskDataFetcher {
    private final TaskService taskService;

    public TaskDataFetcher(TaskService taskService) {
        this.taskService = taskService;
    }

    @DgsQuery
    public List<TaskResponse> getTasks() {
        return taskService.getTasks();
    }

    @DgsQuery
    public TaskResponse getTaskById(@InputArgument String id) {
     return taskService.getTaskById(Long.parseLong(id));
    }

    @DgsQuery
    public List<TaskResponse> getTasksByColumnIdAndBoardId(
            @InputArgument String columnId,
            @InputArgument String boardId
    ) {
      return taskService.getTasksByBoardAndBoardColumnId( Long.parseLong(boardId),Long.parseLong(columnId));
    }
}
