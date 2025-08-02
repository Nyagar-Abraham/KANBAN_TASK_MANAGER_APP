package org.abraham.kanbantaskmanager.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abraham.kanbantaskmanager.Exceptions.EntityNotFoundException;
import org.abraham.kanbantaskmanager.dtos.CreateTaskRequest;
import org.abraham.kanbantaskmanager.dtos.EditTaskColumnRequest;
import org.abraham.kanbantaskmanager.dtos.EditTaskRequest;
import org.abraham.kanbantaskmanager.dtos.TaskResponse;
import org.abraham.kanbantaskmanager.entities.BoardColumn;
import org.abraham.kanbantaskmanager.entities.SubTask;
import org.abraham.kanbantaskmanager.entities.Task;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;
import org.abraham.kanbantaskmanager.mappers.TaskMapper;
import org.abraham.kanbantaskmanager.repository.BoardColumnRepository;
import org.abraham.kanbantaskmanager.repository.BoardRepository;
import org.abraham.kanbantaskmanager.repository.SubTaskRepository;
import org.abraham.kanbantaskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TaskService {
    private final BoardColumnRepository boardColumnRepository;
    private final SubTaskRepository subTaskRepository;
    private TaskRepository taskRepository;
    private BoardRepository boardRepository;

    public void toggleSubtaskStatus(Long subTaskId){
        var subtask = subTaskRepository.findById(subTaskId).orElseThrow(() -> new EntityNotFoundException("SubTask", subTaskId.toString()));
        subtask.setIsCompleted(!subtask.getIsCompleted());
        subTaskRepository.save(subtask);
    }

    public List<TaskResponse> getTasks() {
        return taskRepository.findAll().stream().map(TaskMapper::toDto).collect(Collectors.toList());
    }

    public TaskResponse createTask(CreateTaskRequest request) {
        var board = boardRepository.findById(request.getBoardId()).orElseThrow(() -> new EntityNotFoundException("Board", request.getBoardId().toString()));
        BoardColumn column = boardColumnRepository.findByName(request.getStatus()).orElse(null);

        var task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());

        request.getSubtasks().forEach(subtask -> {
            var newSubtask = new SubTask();
            newSubtask.setTitle(subtask);
            task.addSubTask(newSubtask);
        });

        if (column != null) {
            column.addTask(task);
        } else {
            column = new BoardColumn();
            column.setName(request.getStatus());
            board.addColumn(column);
            column.addTask(task);
        }

        task.addBoard(board);
        boardRepository.save(board);
        var saved_task = taskRepository.save(task);
        return TaskMapper.toDto(saved_task);
    }

    @Transactional
    public TaskResponse updateTask(EditTaskRequest request, Long taskId) {
        var dbTask = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task", taskId.toString()));
        dbTask.setTitle(request.getTitle());
        dbTask.setDescription(request.getDescription());
        dbTask.setStatus(request.getStatus());

        addAndRemoveSubtasks(request.getSubtasks(), dbTask);
        switchTaskColumn(request.getStatus(), dbTask);

        var task = taskRepository.save(dbTask);
        return TaskMapper.toDto(task);
    }

    @Transactional
    public void updateTaskColumn(@Valid EditTaskColumnRequest request, Long taskId) {
        var task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task",taskId.toString()));
        task.setStatus(request.getStatus());
        switchTaskColumn(request.getStatus(),task);

    }

    public void deleteTask(Long taskId){
        taskRepository.deleteById(taskId);
    }


    private static void addAndRemoveSubtasks(Set<String> updatedSubtasksTitles, Task dbTask) {
        var currentSubtasksTitles = dbTask.getSubTasks().stream().map(SubTask::getTitle).collect(Collectors.toSet());
        var currentSubTasks = new HashSet<>(dbTask.getSubTasks());

        for (SubTask subTask : currentSubTasks) {
            if (!updatedSubtasksTitles.contains(subTask.getTitle())) {
                dbTask.removeSubTask(subTask);
            }
        }
        updatedSubtasksTitles.forEach(subtask -> {
            if (!currentSubtasksTitles.contains(subtask)) {
                log.info("Adding subtask {}", subtask);
                var newSubtask = new SubTask();
                newSubtask.setTitle(subtask);
                dbTask.addSubTask(newSubtask);
            }
        });
    }

    private void switchTaskColumn(TaskStatusAndColumnName newColumnName, Task dbTask) {
        var currentColumn = dbTask.getBoardColumn();
        if (!currentColumn.getName().name().equals(newColumnName.name())) {
            var newColumn = boardColumnRepository.findByName(newColumnName).orElseThrow();
            currentColumn.removeTask(dbTask);
            newColumn.addTask(dbTask);
            boardColumnRepository.save(currentColumn);
            boardColumnRepository.save(newColumn);
        }
    }

}
