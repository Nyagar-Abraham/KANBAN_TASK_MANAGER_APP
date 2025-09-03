package org.abraham.kanbantaskmanager.dtos.requests;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskPositionInput {
    @NotNull(message = "sourceId is required")
    private Long sourceId;
    @NotNull(message = "destinationId is required")
    private Long destinationId;
    @NotNull(message = "sourcePosition is required")
    private Integer sourcePosition;
    @NotNull(message = "destinationPosition is required")
    private Integer destinationPosition;
}
