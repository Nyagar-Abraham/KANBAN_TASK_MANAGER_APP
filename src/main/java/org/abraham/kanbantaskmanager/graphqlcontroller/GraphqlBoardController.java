package org.abraham.kanbantaskmanager.graphqlcontroller;

import org.abraham.kanbantaskmanager.dtos.BoardResponse;
import org.abraham.kanbantaskmanager.dtos.requests.CreateBoardRequest;
import org.abraham.kanbantaskmanager.service.BoardColumnService;
import org.abraham.kanbantaskmanager.service.BoardService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GraphqlBoardController {
    private final BoardService boardService;
    private final BoardColumnService boardColumnService;

    public GraphqlBoardController(BoardService boardService, BoardColumnService boardColumnService) {
        this.boardService = boardService;
        this.boardColumnService = boardColumnService;
    }

//    @QueryMapping
//    public List<BoardResponse> getAllBoards() {
//        return boardService.getBoards();
//    }

//    @QueryMapping
//    public BoardResponse getBoardById(@Argument String board_id) {
//        return boardService.getBoardById(Long.parseLong(board_id));
//    }

//    @SchemaMapping(typeName ="BoardColumnResponse", field = "taskCount")
//    public int getTaskCount(BoardColumnResponse boardResponse) {
//        return boardColumnService.getTaskCount(boardResponse.getId());
//    }
//
//    @MutationMapping
//    public String deleteBoardById(@Argument String board_id) {
//        boardService.deleteBoard(Long.parseLong(board_id));
//        return board_id;
//    }


//    @MutationMapping
//    public BoardResponse createBoard(@Argument("board") CreateBoardRequest request) {
//        return boardService.createBoard(request);
//    }

//    @MutationMapping
//    public BoardResponse updateBoardName(@Argument String board_id, @Argument String name) {
//        return boardService.updateBoardName(Long.parseLong(board_id),name);
//    }
//
//    @MutationMapping
//    public BoardResponse updateBoardColumns(@Argument String board_id, @Argument Set<TaskStatusAndColumnName> columns) {
//        return boardService.updateBoardColumns(Long.parseLong(board_id), columns);
//    }
}
