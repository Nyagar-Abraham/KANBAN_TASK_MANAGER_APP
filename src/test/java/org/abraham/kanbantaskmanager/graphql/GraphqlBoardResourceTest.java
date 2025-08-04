package org.abraham.kanbantaskmanager.graphql;


import org.abraham.kanbantaskmanager.dtos.BoardResponse;
import org.abraham.kanbantaskmanager.dtos.CreateBoardRequest;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;
import java.util.Set;

@SpringBootTest
public class GraphqlBoardResourceTest {
    private static final Set<TaskStatusAndColumnName> taskStatusAndColumnNames = Set.of(TaskStatusAndColumnName.TODO,TaskStatusAndColumnName.DOING,TaskStatusAndColumnName.DONE);
    HttpGraphQlTester tester;


    GraphqlBoardResourceTest(){
       WebTestClient webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:8085/graphql")
                .build();
       tester = HttpGraphQlTester.create(webTestClient);
    }

    @Test
    public void shouldSuccessfullyGetAllBoards() {
        tester.documentName("BoardDocuments")
                .operationName("getAllBoards")
                .execute()
                .path("getAllBoards").entityList(BoardResponse.class)
                .satisfies(boardResponse -> {
                    Assertions.assertThat(boardResponse.size()).isEqualTo(2);
                    boardResponse.forEach(GraphqlBoardResourceTest::assertBoardResponse);
                });
    }

    @Test
    public void shouldSuccessfullyGetBoardById() {
        tester.documentName("BoardDocuments")
                .operationName("getBoardById")
                .variable("board_id",2)
                .execute()
                .path("getBoardById").entity(BoardResponse.class)
                .satisfies(GraphqlBoardResourceTest::assertBoardResponse);
    }

    @Test
    public void shouldSuccessfullyCreateBoard() {
        var columnsSet = Set.of(TaskStatusAndColumnName.DONE,TaskStatusAndColumnName.DOING);
        var request =new CreateBoardRequest();
        request.setName("TestBoard One");
        request.setColumns(columnsSet);

        tester.documentName("BoardDocuments")
                .operationName("createBoard")
                .variable("board",request)
                .execute()
                .path("createBoard").entity(BoardResponse.class)
                .satisfies(GraphqlBoardResourceTest::assertBoardResponse);
    }

    @Test
    public void shouldSuccessfullyUpdateBoardName() {
        tester.documentName("BoardDocuments")
                .operationName("updateBoardName")
                .variables(Map.of(
                        "board_id", 2,
                        "name", "updated TestBoard"
                ))
                .execute()
                .path("updateBoardName").entity(BoardResponse.class)
                .satisfies(boardResponse -> {
                    Assertions.assertThat(boardResponse.getId()).isEqualTo(2);
                    Assertions.assertThat(boardResponse.getName()).isEqualTo("updated TestBoard");
                });
    }

    @Test
    public void updateBoardColumns() {
        var newColumns = Set.of(TaskStatusAndColumnName.DONE,TaskStatusAndColumnName.DOING);
        tester.documentName("BoardDocuments")
                .operationName("updateBoardColumns")
                .variables(Map.of(
                        "board_id", 2,
                        "columns", newColumns
                ))
                .execute()
                .path("updateBoardColumns").entity(BoardResponse.class)
                .satisfies(boardResponse -> {
                    Assertions.assertThat(boardResponse.getId()).isEqualTo(2);
                    Assertions.assertThat(boardResponse.getColumns().size()).isEqualTo(newColumns.size());
                    boardResponse.getColumns().forEach(boardColumn -> {
                        Assertions.assertThat(boardColumn.getId()).isNotNull();
                        Assertions.assertThat(boardColumn.getName()).isIn(newColumns);
                    });
                });
    }

    @Test
    public void shouldSuccessfullyDeleteBoardById(){
        tester.documentName("BoardDocuments")
                .operationName("deleteBoardById")
                .variable("board_id",7)
                .execute()
                .path("deleteBoardById").entity(String.class).isEqualTo("7");
    }




    private static void assertBoardResponse(BoardResponse boardResponse) {
        Assertions.assertThat(boardResponse.getId()).isNotNull();
        Assertions.assertThat(boardResponse.getName()).isNotBlank();
        Assertions.assertThat(boardResponse.getColumns()).isNotNull();
        boardResponse.getColumns().forEach(boardColumnResponse -> {
            Assertions.assertThat(boardColumnResponse.getId()).isNotNull();
            Assertions.assertThat(boardColumnResponse.getName()).isIn(taskStatusAndColumnNames);
        });
    }
}
