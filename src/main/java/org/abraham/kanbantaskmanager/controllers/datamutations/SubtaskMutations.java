package org.abraham.kanbantaskmanager.controllers.datamutations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.abraham.kanbantaskmanager.dtos.SubTaskResponse;
import org.abraham.kanbantaskmanager.service.SubtaskService;

@DgsComponent
public class SubtaskMutations {

    private final SubtaskService subtaskService;

    public SubtaskMutations(SubtaskService subtaskService) {
        this.subtaskService = subtaskService;
    }

    @DgsMutation
    public SubTaskResponse toggleSubtaskStatus(@InputArgument String id){
        return subtaskService.toggleSubtaskStatus(Long.parseLong(id));
    }
}
