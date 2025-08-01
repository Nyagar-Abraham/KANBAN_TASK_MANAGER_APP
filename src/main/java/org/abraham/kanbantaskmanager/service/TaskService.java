package org.abraham.kanbantaskmanager.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abraham.kanbantaskmanager.Exceptions.EntityNotFoundException;
import org.abraham.kanbantaskmanager.dtos.CreateTaskRequest;
import org.abraham.kanbantaskmanager.dtos.CreateTaskResponse;
import org.abraham.kanbantaskmanager.entities.BoardColumn;
import org.abraham.kanbantaskmanager.entities.Status;
import org.abraham.kanbantaskmanager.entities.SubTask;
import org.abraham.kanbantaskmanager.entities.Task;
import org.abraham.kanbantaskmanager.mappers.TaskMapper;
import org.abraham.kanbantaskmanager.repository.BoardColumnRepository;
import org.abraham.kanbantaskmanager.repository.BoardRepository;
import org.abraham.kanbantaskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TaskService {
    private final BoardColumnRepository boardColumnRepository;
    private TaskRepository taskRepository;
    private BoardRepository boardRepository;

    public CreateTaskResponse createTask(CreateTaskRequest request) {
        var board = boardRepository.findById(request.getBoardId()).orElseThrow(() -> new EntityNotFoundException("Board", request.getBoardId().toString()));
        BoardColumn column = boardColumnRepository.findByName(request.getStatus().name()).orElse(null);


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
            task.addBoard(board);
        } else {
            column = new BoardColumn();
            column.setName(request.getStatus().name());
            board.addColumn(column);
            column.addTask(task);
            task.addBoard(board);
        }

        boardRepository.save(board);
//        var saved_task = taskRepository.save(task);

        log.info("Created task {}", task);
        return TaskMapper.toDto(task);
    }
}
