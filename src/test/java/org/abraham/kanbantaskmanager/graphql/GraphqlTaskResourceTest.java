package org.abraham.kanbantaskmanager.graphql;

import org.abraham.kanbantaskmanager.dtos.RestDtos.CreateTaskRequest;
import org.abraham.kanbantaskmanager.dtos.RestDtos.SubTaskResponse;
import org.abraham.kanbantaskmanager.dtos.RestDtos.TaskResponse;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.*;

@SpringBootTest
public class GraphqlTaskResourceTest {
    private static final List<TaskStatusAndColumnName> StatusList = List.of(TaskStatusAndColumnName.TODO,TaskStatusAndColumnName.DONE,TaskStatusAndColumnName.DOING);
    HttpGraphQlTester tester;

    GraphqlTaskResourceTest(){
        WebTestClient webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:8085/graphql")
                .build();
        tester = HttpGraphQlTester.create(webTestClient);
    }

    @Test
    public void shouldSuccessfullyGetAllTasks(){
        tester.documentName("TaskDocuments")
                .operationName("getAllTasks")
                .execute()
                .path("getAllTasks").entityList(TaskResponse.class)
                .satisfies(response -> {
                    Assertions.assertThat(response.size()).isEqualTo(2);
                    response.forEach(GraphqlTaskResourceTest::assertTaskResponse);
                });
    }

    @Test
    public void shouldSuccessfullyGetTaskById(){
        tester.documentName("TaskDocuments")
                .operationName("getTaskById")
                .execute()
                .path("getTaskById").entity(TaskResponse.class)
                .satisfies(GraphqlTaskResourceTest::assertTaskResponse);
    }

    @Test
    public void shouldSuccessfullyCreateTask(){
        var subtasks = Set.of("Subtask1", "Subtask2", "Subtask3");
        var status = TaskStatusAndColumnName.DOING;
        var newTask = new CreateTaskRequest();
        newTask.setBoardId(5L);
        newTask.setTitle("New Task");
        newTask.setDescription("New Description");
        newTask.setStatus(status);
        newTask.setSubtasks(subtasks);

        tester.documentName("TaskDocuments")
                .operationName("createTask")
                .variable("task",newTask)
                .execute()
                .path("createTask").entity(TaskResponse.class)
                .satisfies(GraphqlTaskResourceTest::assertTaskResponse);
    }

    @Test
    public void shouldSuccessfullyUpdateTaskTitle(){
        tester.documentName("TaskDocuments")
                .operationName("updateTaskTitle")
                .variables(Map.of(
                        "task_id", 2,
                        "title", "Updated Task Title"
                ))
                .execute()
                .path("updateTaskTitle").entity(TaskResponse.class)
                .satisfies(response -> {
                    Assertions.assertThat(response.getId()).isEqualTo(2);
                    Assertions.assertThat(response.getTitle()).isEqualTo("Updated Task Title");
                });
    }

    @Test
    public void shouldSuccessfullyUpdateTaskDescription(){
        tester.documentName("TaskDocuments")
                .operationName("updateTaskDescription")
                .variables(Map.of(
                        "task_id", 2,
                        "description", "Updated Task Description"
                ))
                .execute()
                .path("updateTaskDescription").entity(TaskResponse.class)
                .satisfies(response -> {
                    Assertions.assertThat(response.getId()).isEqualTo(2);
                    Assertions.assertThat(response.getDescription()).isEqualTo("Updated Task Description");
                });
    }

    @Test
    public void shouldSuccessfullyUpdateTaskColumn(){
        var newColumn = TaskStatusAndColumnName.DOING;
        tester.documentName("TaskDocuments")
                .operationName("updateTaskColumn")
                .variables(Map.of(
                        "task_id",2,
                        "column", newColumn
                ))
                .execute()
                .path("updateTaskColumn").entity(TaskResponse.class)
                .satisfies(response -> {
                    Assertions.assertThat(response.getId()).isEqualTo(2);
                    Assertions.assertThat(response.getStatus()).isEqualTo(newColumn);
                });
    }

    @Test
    public void shouldSuccessfullyUpdateTaskSubtasks(){
        var newSubtasks = Set.of("Updated Subtask1", "Updated Subtask2", "Updated Subtask3");
        tester.documentName("TaskDocuments")
                .operationName("updateTaskSubtasks")
                .variables(Map.of(
                        "task_id", 2,
                        "subtasks", newSubtasks
                ))
                .execute()
                .path("updateTaskSubtasks").entity(TaskResponse.class)
                .satisfies(response -> {
                    Assertions.assertThat(response.getId()).isEqualTo(2);
                    response.getSubtasks().forEach(subtask -> {
                        Assertions.assertThat(subtask.getId()).isNotNull();
                        Assertions.assertThat(subtask.getTitle()).isNotBlank();
                    });
                });
    }

    @Test
    public void shouldSuccessfullyToggleSubtaskStatus(){
        tester.documentName("TaskDocuments")
                .operationName("toggleSubtaskStatus")
                .variables(Map.of(
                        "subtask_id", 11
                ))
                .execute()
                .path("toggleSubtaskStatus").entity(SubTaskResponse.class)
                .satisfies(response -> {
                    Assertions.assertThat(response.getId()).isEqualTo(11);
                    Assertions.assertThat(response.getIsCompleted()).isInstanceOf(Boolean.class);
                });

    }


    private static void assertTaskResponse(TaskResponse response) {
        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getTitle()).isNotBlank();
        Assertions.assertThat(response.getDescription()).isNotNull();
        Assertions.assertThat(response.getStatus()).isIn(StatusList);
        response.getSubtasks().forEach(taskSubtaskResponse -> {
            Assertions.assertThat(taskSubtaskResponse.getId()).isNotNull();
            Assertions.assertThat(taskSubtaskResponse.getTitle()).isNotBlank();
            Assertions.assertThat(taskSubtaskResponse.getIsCompleted()).isFalse();
        });
    }

}
