package org.abraham.kanbantaskmanager.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.abraham.kanbantaskmanager.dtos.TaskResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateTaskResponse extends CustomResponse {
    private TaskResponse task;
}
