package org.abraham.kanbantaskmanager.service;

import org.abraham.kanbantaskmanager.Exceptions.EntityNotFoundException;
import org.abraham.kanbantaskmanager.dtos.SubTaskResponse;
import org.abraham.kanbantaskmanager.mappers.SubTaskMapper;
import org.abraham.kanbantaskmanager.repository.SubTaskRepository;
import org.springframework.stereotype.Service;

@Service
public class SubtaskService {
    private final SubTaskRepository subTaskRepository;
    private final SubTaskMapper subTaskMapper;

    public SubtaskService(SubTaskRepository subTaskRepository, SubTaskMapper subTaskMapper) {
        this.subTaskRepository = subTaskRepository;
        this.subTaskMapper = subTaskMapper;
    }

    public SubTaskResponse toggleSubtaskStatus(Long subTaskId){
        var subtask = subTaskRepository.findById(subTaskId).orElseThrow(() -> new EntityNotFoundException("SubTask", "Id", subTaskId.toString()));
        subtask.setIsCompleted(!subtask.getIsCompleted());
        subTaskRepository.save(subtask);
        return  subTaskMapper.subTaskToSubTaskResponse(subtask);
    }
}
