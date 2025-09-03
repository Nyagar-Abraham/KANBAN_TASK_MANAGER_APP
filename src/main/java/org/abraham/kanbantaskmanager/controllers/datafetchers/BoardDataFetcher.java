package org.abraham.kanbantaskmanager.controllers.datafetchers;


import com.netflix.graphql.dgs.*;
import org.abraham.kanbantaskmanager.dtos.BoardColumnResponse;
import org.abraham.kanbantaskmanager.dtos.BoardResponse;
import org.abraham.kanbantaskmanager.service.BoardColumnService;
import org.abraham.kanbantaskmanager.service.BoardService;

import java.util.List;

@DgsComponent
public class BoardDataFetcher {
    private final BoardService boardService;
    private final BoardColumnService boardColumnService;

    public BoardDataFetcher(BoardService boardService, BoardColumnService boardColumnService) {
        this.boardService = boardService;
        this.boardColumnService = boardColumnService;
    }

    @DgsQuery
    public List<BoardResponse> getBoards() {
        return boardService.getBoards();
    }

    @DgsQuery
    public BoardResponse getBoardById(@InputArgument String id) {
        return boardService.getBoardById(Long.parseLong(id));
    }

//    @DgsData(parentType = "BoardResponse", field = "columns")
//    public List<BoardColumnResponse> getBoardColumns(DgsDataFetchingEnvironment dfe) {
//       BoardResponse board = dfe.getSource();
//       var boardId = board.getId();
//       return boardColumnService.getColumnsByBoardId(boardId);
//    }

    @DgsData(parentType = "BoardColumnResponse", field = "taskCount")
    public int getTaskCount(DgsDataFetchingEnvironment dfe) {
        BoardColumnResponse column = dfe.getSource();
        var columnId = column.getId();
        return boardColumnService.getTaskCount(columnId);
    }
}
