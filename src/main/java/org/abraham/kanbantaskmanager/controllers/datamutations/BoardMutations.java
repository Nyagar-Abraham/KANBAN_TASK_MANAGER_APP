package org.abraham.kanbantaskmanager.controllers.datamutations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.abraham.kanbantaskmanager.dtos.BoardResponse;
import org.abraham.kanbantaskmanager.dtos.requests.CreateBoardRequest;
import org.abraham.kanbantaskmanager.dtos.requests.CreateBoardResponse;
import org.abraham.kanbantaskmanager.dtos.requests.UpdateBoardInput;
import org.abraham.kanbantaskmanager.dtos.responses.DeleteBoardResponse;
import org.abraham.kanbantaskmanager.dtos.responses.UpdateBoardResponse;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;
import org.abraham.kanbantaskmanager.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DgsComponent
public class BoardMutations {

    private static final Logger log = LoggerFactory.getLogger(BoardMutations.class);
    private final BoardService boardService;

    public BoardMutations(BoardService boardService) {
        this.boardService = boardService;
    }

    @DgsMutation
    public CreateBoardResponse createBoard(@InputArgument CreateBoardRequest board) {
        var response = new CreateBoardResponse();
        try {
            var boardResponse = boardService.createBoard(board);
            response.setCode(HttpStatus.OK.value());
            response.setMessage("success");
            response.setSuccess(true);
            response.setBoard(boardResponse);
        } catch (Exception ex){
           throw new RuntimeException(ex);
        }

        return response;
    }

    @DgsMutation
    public DeleteBoardResponse deleteBoardById(@InputArgument String id) {
        var response = new DeleteBoardResponse();

        try{
            boardService.deleteBoard(Long.parseLong(id));
            response.setCode(HttpStatus.OK.value());
            response.setMessage("success");
            response.setSuccess(true);
            response.setBoardId(Integer.parseInt(id));
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
      return  response;
    }

    @DgsMutation
    public BoardResponse updateBoardColumns(@InputArgument String id, @InputArgument List<TaskStatusAndColumnName> columns) {
        var columsSet = new HashSet<>(columns);
        return boardService.updateBoardColumns(Long.parseLong(id), columsSet);
    }

    @DgsMutation
    public BoardResponse updateBoardName(@InputArgument String id, @InputArgument String name) {
        return boardService.updateBoardName(Long.parseLong(id), name);
    }

    @DgsMutation
    public UpdateBoardResponse updateBoard(@InputArgument String id, @InputArgument UpdateBoardInput input) {
        var response = new UpdateBoardResponse();
        try{
            var board = boardService.updateBoard(input, Long.parseLong(id));
            response.setCode(HttpStatus.OK.value());
            response.setMessage("success");
            response.setSuccess(true);
            response.setBoard(board);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return response;
    }
}
