package org.abraham.kanbantaskmanager.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.abraham.kanbantaskmanager.dtos.BoardResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateBoardResponse extends CustomResponse {
    private BoardResponse board;
}


