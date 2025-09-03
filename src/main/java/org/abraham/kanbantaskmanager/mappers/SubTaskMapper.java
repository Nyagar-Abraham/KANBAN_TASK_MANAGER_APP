package org.abraham.kanbantaskmanager.mappers;



import org.abraham.kanbantaskmanager.dtos.SubTaskResponse;
import org.abraham.kanbantaskmanager.entities.SubTask;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubTaskMapper {
    SubTaskResponse subTaskToSubTaskResponse(SubTask subTask);

//    public static SubTaskResponse toDto(SubTask subTask) {
//        var response = new SubTaskResponse();
//        response.setId(subTask.getId());
//        response.setTitle(subTask.getTitle());
//        response.setIsCompleted(subTask.getIsCompleted());
//        return response;
//    }
}
