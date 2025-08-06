package org.abraham.kanbantaskmanager.service;

import lombok.AllArgsConstructor;
import org.abraham.kanbantaskmanager.repository.BoardColumnRepository;
import org.abraham.kanbantaskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BoardColumnService {
    private final TaskRepository taskRepository;
    private BoardColumnRepository boardColumnRepository;

    public int getTaskCount(Long column_id) {
       return taskRepository.countByBoardColumnId(column_id);
    }
}
