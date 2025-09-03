package org.abraham.kanbantaskmanager.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.abraham.kanbantaskmanager.dtos.BoardResponse;
import org.abraham.kanbantaskmanager.dtos.TaskResponse;
import org.abraham.kanbantaskmanager.dtos.requests.CustomResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateTaskResponse extends CustomResponse {
    private TaskResponse task;
}
