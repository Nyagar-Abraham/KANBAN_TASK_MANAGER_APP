package org.abraham.kanbantaskmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abraham.kanbantaskmanager.Exceptions.EntityNotFoundException;
import org.abraham.kanbantaskmanager.dtos.TaskResponse;
import org.abraham.kanbantaskmanager.dtos.requests.CreateSubtaskRequest;
import org.abraham.kanbantaskmanager.dtos.requests.CreateTaskRequest;
import org.abraham.kanbantaskmanager.dtos.requests.UpdateTaskInput;
import org.abraham.kanbantaskmanager.dtos.requests.UpdateTaskPositionInput;
import org.abraham.kanbantaskmanager.entities.Board;
import org.abraham.kanbantaskmanager.entities.BoardColumn;
import org.abraham.kanbantaskmanager.entities.SubTask;
import org.abraham.kanbantaskmanager.entities.Task;
import org.abraham.kanbantaskmanager.mappers.SubTaskMapper;
import org.abraham.kanbantaskmanager.mappers.TaskMapper;
import org.abraham.kanbantaskmanager.repository.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TaskService {
    private final BoardColumnRepository boardColumnRepository;
    private final SubTaskRepository subTaskRepository;
    private final TaskRepository taskRepository;
    private final BoardRepository boardRepository;
    private final SubTaskMapper subTaskMapper;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    private static void addSubTasks(Set<CreateSubtaskRequest> subtaskRequest, Task task) {

        subtaskRequest.forEach(subtask -> {
//           var assignedUser = userRepository.findById(subtask.getAssignedTo());

            var newSubtask = new SubTask();
            newSubtask.setTitle(subtask.getTitle());
            newSubtask.setDescription(subtask.getDescription());
//           newSubtask.setAssignedTo(subtask.getAssignedTo());
            newSubtask.setDueDate(subtask.getDueDate());
            task.addSubTask(newSubtask);
        });
    }

    private static void addAndRemoveSubtasks(Set<String> updatedSubtasksTitles, Task dbTask) {
        var currentSubtasksTitles = dbTask.getSubtasks().stream().map(SubTask::getTitle).collect(Collectors.toSet());
        var currentSubTasks = new HashSet<>(dbTask.getSubtasks());

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

    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {
        var board = boardRepository.findById(request.getBoardId()).orElseThrow(() -> new EntityNotFoundException("Board", "Id", request.getBoardId().toString()));
        var assignedUser = userRepository.findById(request.getAssignedTo()).orElseThrow(() -> new EntityNotFoundException("AssignedUser", "Id", request.getAssignedTo().toString()));
        var creator = userRepository.findById(request.getCreatedBy()).orElseThrow(() -> new EntityNotFoundException("CreatedBy", "Id", request.getCreatedBy().toString()));

        var task = new Task();
        task.setCreatedBy(creator);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setAssignedTo(assignedUser);
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());
        task.setTags(request.getTags());
        task.setEstimatedHours(request.getEstimatedHours());

        addSubTasks(request.getSubtasks(), task);
        addTaskToOrCreateColumnIfNotExist(request, task, board);

        task.addBoard(board);
        boardRepository.save(board);
        var persisted = taskRepository.findByTitleAndBoardId(task.getTitle(), board.getId())
                .orElseThrow(() -> new EntityNotFoundException("Task", "title", task.getTitle()));
        return taskMapper.taskToTaskResponse(persisted);
    }

    public List<TaskResponse> getTasks() {
        return taskRepository.findAll()
                .stream().map(taskMapper::taskToTaskResponse).collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long taskId) {
        var task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task", "Id", taskId.toString()));
        return taskMapper.taskToTaskResponse(task);
    }

    public List<TaskResponse> getTasksByBoardAndBoardColumnId(Long boardId, Long columnId) {
        return taskRepository.findByBoardIdAndBoardColumnIdOrderByPosition(boardId, columnId).stream().map(taskMapper::taskToTaskResponse).collect(Collectors.toList());
    }

    public TaskResponse updateTaskTitle(Long task_id, String title) {
        var task = taskRepository.findById(task_id).orElseThrow(() -> new EntityNotFoundException("Task", "Id", task_id.toString()));
        task.setTitle(title);
        taskRepository.save(task);
        return taskMapper.taskToTaskResponse(task);
    }

    public TaskResponse updateTaskDescription(Long taskId, String description) {
        var task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task", "Id", taskId.toString()));
        task.setDescription(description);
        taskRepository.save(task);
        return taskMapper.taskToTaskResponse(task);
    }

    public TaskResponse updateTaskSubtasks(Long taskId, Set<String> subtasks) {
        var task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task", "Id", taskId.toString()));
        addAndRemoveSubtasks(subtasks, task);
        taskRepository.save(task);
        return taskMapper.taskToTaskResponse(task);
    }

    @Transactional
    public void updateTaskPosition(UpdateTaskPositionInput input) {
        var source = taskRepository.findById(input.getSourceId()).orElseThrow(() -> new EntityNotFoundException("Task", "Id", input.getSourceId().toString()));
        var destination = taskRepository.findById(input.getDestinationId()).orElseThrow(() -> new EntityNotFoundException("Task", "Id", input.getDestinationId().toString()));

        source.setPosition(input.getDestinationPosition());
        destination.setPosition(input.getSourcePosition());

        taskRepository.save(source);
        taskRepository.save(destination);
    }

    @Transactional
    public TaskResponse updateTask(UpdateTaskInput request, Long taskId) {
        var dbTask = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task", "Id", taskId.toString()));
        if (!Objects.isNull(request.getTitle())) dbTask.setTitle(request.getTitle());
        if (!Objects.isNull(request.getDescription())) dbTask.setDescription(request.getDescription());
        if (!Objects.isNull(request.getPriority())) dbTask.setPriority(request.getPriority());
        if (!Objects.isNull(request.getTags())) dbTask.setTags(request.getTags());
        if (!Objects.isNull(request.getEstimatedHours())) dbTask.setEstimatedHours(request.getEstimatedHours());
        if (!Objects.isNull(request.getDueDate())) dbTask.setDueDate(request.getDueDate());
        if (!Objects.isNull(request.getAssignedTo()) && !Objects.equals(dbTask.getAssignedTo().getId(), request.getAssignedTo())) {
            var assignedUser = userRepository.findById(request.getAssignedTo()).orElseThrow(() -> new EntityNotFoundException("User", "Id", request.getAssignedTo().toString()));
            dbTask.setAssignedTo(assignedUser);
        }
        if (!Objects.isNull(request.getSubtasks()))
            addAndRemoveSubtasks(request.getSubtasks().stream().map(CreateSubtaskRequest::getTitle).collect(Collectors.toSet()), dbTask);

        if (!Objects.isNull(request.getStatus()))
//        switchTaskColumn(, dbTask);

            taskRepository.save(dbTask);
        return taskMapper.taskToTaskResponse(dbTask);
    }

    @Transactional
    public TaskResponse updateTaskColumn(Long taskId, Long columnId) {
        var task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task", "Id", taskId.toString()));
        switchTaskColumn(columnId, task);
        return taskMapper.taskToTaskResponse(task);
    }

    private void addTaskToOrCreateColumnIfNotExist(CreateTaskRequest request, Task task, Board board) {
        BoardColumn column = boardColumnRepository.findByName(request.getStatus()).orElse(null);
        if (column != null) {
            column.addTask(task);
        } else {
            column = new BoardColumn();
            column.setName(request.getStatus());
            board.addColumn(column);
            column.addTask(task);
        }
    }

    public void deleteTask(Long taskId) {
        taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task", "Id", taskId.toString()));
        taskRepository.deleteById(taskId);
    }

    private void switchTaskColumn(Long columnId, Task dbTask) {
        var currentColumn = dbTask.getBoardColumn();
        if (!Objects.equals(currentColumn.getId(), columnId)) {
            var newColumn = boardColumnRepository.findById(columnId).orElseThrow(() -> new EntityNotFoundException("Column", "Id", columnId.toString()));
            dbTask.setStatus(newColumn.getName());
            currentColumn.removeTask(dbTask);
            newColumn.addTask(dbTask);
            boardColumnRepository.save(currentColumn);
            boardColumnRepository.save(newColumn);
        }
    }

    public int getSubtaskCount(Long id) {
        return subTaskRepository.countByTaskId(id);
    }
}
