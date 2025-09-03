package org.abraham.kanbantaskmanager.service;

import lombok.AllArgsConstructor;
import org.abraham.kanbantaskmanager.Exceptions.EntityNotFoundException;
import org.abraham.kanbantaskmanager.dtos.BoardColumnResponse;
import org.abraham.kanbantaskmanager.dtos.requests.UpdateColumnPositionInput;
import org.abraham.kanbantaskmanager.mappers.BoardColumnMapper;
import org.abraham.kanbantaskmanager.repository.BoardColumnRepository;
import org.abraham.kanbantaskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BoardColumnService {
    private final TaskRepository taskRepository;
    private BoardColumnRepository boardColumnRepository;
    private final BoardColumnMapper boardColumnMapper;

    public int getTaskCount(Long columnId) {
       return taskRepository.countByBoardColumnId(columnId);
    }

    public void updateColumnPosition(UpdateColumnPositionInput input) {
      var source =   boardColumnRepository.findById(input.getSourceId()).orElseThrow(() -> new EntityNotFoundException("BoardColumn", "Id", input.getSourceId().toString()));
      var destination = boardColumnRepository.findById(input.getDestinationId()).orElseThrow(() -> new EntityNotFoundException("BoardColumn", "Id", input.getDestinationId().toString()));

      source.setPosition(input.getDestinationPosition());
      destination.setPosition(input.getSourcePosition());

      boardColumnRepository.save(source);
      boardColumnRepository.save(destination);
    }

//    public List<BoardColumnResponse> getColumnsByBoardId(Long boardId) {
//      return boardColumnRepository.findByBoardIdOrderByPosition(boardId).stream().map(boardColumnMapper::boardColumnToBoardColumnResponse).toList();
//    }
}
