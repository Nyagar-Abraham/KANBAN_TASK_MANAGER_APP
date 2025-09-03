package org.abraham.kanbantaskmanager.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
public class UpdateSubtaskRequest {
    @NotNull(message = "title is required")
    private String title;
    private String description;
    private LocalDate dueDate;
    private Long assignedTo;
}
