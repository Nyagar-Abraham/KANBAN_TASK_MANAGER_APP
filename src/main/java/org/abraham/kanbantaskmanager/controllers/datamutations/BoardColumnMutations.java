package org.abraham.kanbantaskmanager.controllers.datamutations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.abraham.kanbantaskmanager.dtos.requests.UpdateColumnPositionInput;
import org.abraham.kanbantaskmanager.dtos.responses.UpdateColumnPositionResponse;
import org.abraham.kanbantaskmanager.service.BoardColumnService;
import org.springframework.http.HttpStatus;

@DgsComponent
public class BoardColumnMutations {
    private final BoardColumnService boardColumnService;

    public BoardColumnMutations(BoardColumnService boardColumnService) {
        this.boardColumnService = boardColumnService;
    }

    @DgsMutation
    public UpdateColumnPositionResponse updateBoardColumnPosition(@InputArgument UpdateColumnPositionInput input) {
        var response = new UpdateColumnPositionResponse();

        try {
            boardColumnService.updateColumnPosition(input);
            response.setMessage("Successfully updated column position");
            response.setCode(HttpStatus.OK.value());
            response.setSuccess(true);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
        return response;
    }
}
