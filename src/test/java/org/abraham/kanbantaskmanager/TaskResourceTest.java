package org.abraham.kanbantaskmanager;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.abraham.kanbantaskmanager.dtos.RestDtos.CreateTaskRequest;
import org.abraham.kanbantaskmanager.dtos.RestDtos.EditTaskColumnRequest;
import org.abraham.kanbantaskmanager.dtos.RestDtos.EditTaskRequest;
import org.abraham.kanbantaskmanager.dtos.RestDtos.TaskResponse;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskResourceTest {
    private final MockMvcTester mockMvc;
    @Autowired

    private ObjectMapper objectMapper;

    TaskResourceTest(@Autowired WebApplicationContext wac) {
        this.mockMvc = MockMvcTester.from(wac)
                .withHttpMessageConverters(
                        List.of(wac.getBean(AbstractJackson2HttpMessageConverter.class))
                );
    }

    @Test
    public void shouldSuccessfullyCreateTask() throws JsonProcessingException {
        var subtasks = Set.of("Subtask 1", "Subtask 2", "Subtask 3");
        var status = TaskStatusAndColumnName.DONE;
        var request = getCreateTaskRequest(2L,"Task One", "Description", status, subtasks );

        var json = objectMapper.writeValueAsString(request);

        var result = mockMvc.post()
                .uri("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(201);


        assertTaskResponse(result,status, subtasks.size() );
    }

    private static void assertTaskResponse(MvcTestResult result,TaskStatusAndColumnName status, int size) {
        assertThat(result)
                .bodyJson()
                .convertTo(InstanceOfAssertFactories.type(TaskResponse.class))
                .satisfies(taskResponse -> {
                    assertThat(taskResponse.getId()).isNotNull();
                    assertThat(taskResponse.getSubtasks()).hasSize(size);
                    assertThat(taskResponse.getStatus()).isEqualTo(status);
                    taskResponse.getSubtasks().forEach(task -> {
                        assertThat(task.getId()).isNotNull();
                        assertThat(task.getTitle()).isNotNull();
                        assertThat(task.getIsCompleted()).isFalse();
                    });
                });
    }

    @Test
    public void shouldFailDueToInputValidation() throws JsonProcessingException {
        var subtasks = Set.of("Subtask 1", "Subtask 2", "Subtask 3");
        var request = getCreateTaskRequest(null,null, "Description", null,subtasks);
        var json = objectMapper.writeValueAsString(request);
        var result = mockMvc.post()
                .uri("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
        assertThat(result.getResponse().getStatus()).isEqualTo(400);

        assertThat(result)
            .bodyJson()
            .extractingPath("$.boardId").isEqualTo("boardId is required");

        assertThat(result)
                .bodyJson()
                .extractingPath("$.title").isEqualTo("title is required");

        assertThat(result)
                .bodyJson()
                .extractingPath("$.status").isEqualTo("status is required");
    }

    @Test
    public void shouldFailDueToUniqueKeyConstraintViolation() throws JsonProcessingException {
        var subtasks = Set.of("Subtask 1", "Subtask 2", "Subtask 3");
        var request = getCreateTaskRequest(3L,"Task One", "Description", TaskStatusAndColumnName.DONE,subtasks);
        var json = objectMapper.writeValueAsString(request);

        mockMvc.post()
                .uri("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        var result = mockMvc.post()
                .uri("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(500);
        assertThat(result)
        .bodyJson()
                .extractingPath("$.message").isNotNull();
    }

    @Test
    public void shouldSuccessfullyUpdateTask() throws JsonProcessingException {
        var subtasks = Set.of("Subtask 1", "Subtask 2", "Subtask 3");
        var status = TaskStatusAndColumnName.DOING;
        var request = getEditTaskRequest("Update title", "Update Description",status,subtasks);

        var json = objectMapper.writeValueAsString(request);

        var result = mockMvc.put()
                .uri("/tasks/{task_id}",5L )
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertTaskResponse(result,status, subtasks.size() );
    }

    @Test
    public void updateTaskShouldFailDueToInvalidTaskId() throws JsonProcessingException {
        var taskId = -5L;
        var subtasks = Set.of("Subtask 1", "Subtask 2", "Subtask 3");
        var status = TaskStatusAndColumnName.TODO;

        var request = getEditTaskRequest("Update title", "Update Description",status,subtasks);

        var json = objectMapper.writeValueAsString(request);
        var result = mockMvc.put()
                .uri("/tasks/{task_id}",taskId )
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result)
        .bodyJson()
                .extractingPath("$.message").isEqualTo( "Task with ID -5 Not Found!");
    }

    @Test
    public void shouldSuccessfullyUpdateSubtaskStatus() throws JsonProcessingException {
        var result = mockMvc.put()
                .uri("/tasks/subtasks/{task_id}",5L )
                .exchange();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    public void updateSubtaskShouldFailDueToInvalidSubtaskId() throws JsonProcessingException {
        var taskId = -5L;
        var result = mockMvc.put()
                .uri("/tasks/subtasks/{task_id}",taskId )
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result)
                .bodyJson()
                .extractingPath("$.message").isEqualTo( "SubTask with ID -5 Not Found!");
    }

    @Test
    public void shouldSuccessfullyUpdateTaskStatus() throws JsonProcessingException {
        var taskId = 5L;
        var status = TaskStatusAndColumnName.DOING;
        var request = new EditTaskColumnRequest();
        request.setStatus(status);
        var json = objectMapper.writeValueAsString(request);

        var result = mockMvc.put()
                .uri("/tasks/{task_id}/update-column",taskId )
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);

        result = mockMvc.get()
                .uri("/tasks/{task_id}",taskId )
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
       assertThat(result)
               .bodyJson()
               .convertTo(InstanceOfAssertFactories.type(TaskResponse.class))
               .satisfies(taskResponse -> {
                   assertThat(taskResponse.getStatus()).isEqualTo(status);
               });
    }

    @Test
    public void updateTaskColumnShouldFailDueToInvalidTaskId() throws JsonProcessingException {
        var taskId = -5L;
        var request = new EditTaskColumnRequest();
        request.setStatus(TaskStatusAndColumnName.DOING);
        var json = objectMapper.writeValueAsString(request);

        var result = mockMvc.put()
                .uri("/tasks/{task_id}/update-column",taskId )
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result)
        .bodyJson()
                .extractingPath("$.message").isEqualTo( "Task with ID -5 Not Found!");

    }

    @Test
    public void updateTaskColumnShouldFailDueToRequestValidation() throws JsonProcessingException {
        var taskId = 5L;
        var request = new EditTaskColumnRequest();

        var json = objectMapper.writeValueAsString(request);
        var result = mockMvc.put()
                .uri("/tasks/{task_id}/update-column",taskId )
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result)
        .bodyJson()
                .extractingPath("$.status").isEqualTo( "status is required");
    }


    @Test
    public void shouldSuccessfullyDeleteTask()  {
        var taskId = 3L;
        var result =  mockMvc.delete()
                .uri("/tasks/{task_id}",taskId )
                .exchange();
        assertThat(result.getResponse().getStatus()).isEqualTo(204);

        result = mockMvc.get()
                .uri("/tasks/{task_id}",taskId )
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result)
        .bodyJson()
                .extractingPath("$.message").isEqualTo( "Task with ID 3 Not Found!");
    }


    private static EditTaskRequest getEditTaskRequest(String title, String description, TaskStatusAndColumnName status, Set<String> subtasks) {
        var request = new EditTaskRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setStatus(status);
        request.getSubtasks().addAll(subtasks);
        return request;
    }


    private static CreateTaskRequest getCreateTaskRequest(Long boardId,String title, String description, TaskStatusAndColumnName status ,Set<String> subtasks) {
        var request = new CreateTaskRequest();
        request.setBoardId(boardId);
        request.setTitle(title);
        request.setDescription(description);
        request.setStatus(status);
        request.getSubtasks().addAll(subtasks);
        return request;
    }
}
