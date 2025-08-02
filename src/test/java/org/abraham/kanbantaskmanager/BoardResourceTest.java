package org.abraham.kanbantaskmanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.abraham.kanbantaskmanager.dtos.BoardResponse;
import org.abraham.kanbantaskmanager.dtos.CreateBoardRequest;
import org.abraham.kanbantaskmanager.dtos.EditBoardRequest;
import org.abraham.kanbantaskmanager.entities.Board;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@AutoConfigureMockMvc
//@SpringJUnitWebConfig(KanbanTaskManagerApplication.class)
public class BoardResourceTest {

    private static final Logger log = LoggerFactory.getLogger(BoardResourceTest.class);

    private final MockMvcTester mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    BoardResourceTest(@Autowired WebApplicationContext wac) {
        this.mockMvc = MockMvcTester.from(wac)
                .withHttpMessageConverters(
                        List.of(wac.getBean(AbstractJackson2HttpMessageConverter.class))
                );
    }

//        CREATE

    @Test
    public void shouldSuccessfullyAddABoards() throws JsonProcessingException {
        var name = "Test Board";
        var columnSet = Set.of(TaskStatusAndColumnName.TODO);
      var request = getCreateBoardRequest("Test Board",columnSet);

        var body = objectMapper.writeValueAsString(request);

        var result = mockMvc.post()
                .uri("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

        assertBoardResponse(result, columnSet, name);
    }




    @Test
    public void shouldFailDueToDuplicateBoardName() throws JsonProcessingException {
        var request = new CreateBoardRequest();
        request.setName("Test Board 2");
        request.setColumns(Set.of(TaskStatusAndColumnName.DOING,TaskStatusAndColumnName.DONE));

        var body = objectMapper.writeValueAsString(request);
        mockMvc.post()
                .uri("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        var result = mockMvc.post()
                .uri("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result)
                .hasFailed()
                .hasStatus(HttpStatus.BAD_REQUEST)
                .failure().hasMessage( "board with name Test Board 2 already exists");
    }

    @Test
    public void shouldFailDueToRequestValidation() throws JsonProcessingException {
        var request = new CreateBoardRequest();
        request.setName("Test Board");
        request.setColumns(Set.of(TaskStatusAndColumnName.TODO,TaskStatusAndColumnName.DONE));

        var body = objectMapper.writeValueAsString(request);

        var result = mockMvc.post()
                .uri("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result)
                .hasFailed()
                .hasStatus(HttpStatus.BAD_REQUEST)
                .failure().hasMessageContaining("Name is required!");
    }

//        UPDATE
    @Test
    public void shouldSuccessfullyUpdateBoard() throws JsonProcessingException {
        var name = "Test Update Board";
        var columnSet = Set.of(TaskStatusAndColumnName.DOING,TaskStatusAndColumnName.DONE,TaskStatusAndColumnName.TODO);
        var request = getEditBoardRequest("Test Update Board",columnSet);
        String body = objectMapper.writeValueAsString(request);

        var result = mockMvc.put()
                .uri("/boards/{task_id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertBoardResponse(result, columnSet ,name );
    }

    private CreateBoardRequest getCreateBoardRequest(String name , Set<TaskStatusAndColumnName> columnNames) {
        var request = new CreateBoardRequest();
        request.setName(name);
        request.setColumns(columnNames);
        return request;
    }

    private static EditBoardRequest getEditBoardRequest(String name,Set<TaskStatusAndColumnName> columnNames) {
        var request = new EditBoardRequest();
        request.setName(name);
        request.setColumns(columnNames);
        return request;
    }

    private static void assertBoardResponse(MvcTestResult result, Set<TaskStatusAndColumnName> columnSet, String name) {
        assertThat(result)
                .bodyJson()
                .convertTo(InstanceOfAssertFactories.type(BoardResponse.class))
                .satisfies(boardResponse -> {
                    assertThat(boardResponse.getId()).isNotNull();
                    assertThat(boardResponse.getName()).isEqualTo(name);
                    assertThat(boardResponse.getColumns()).hasSize(columnSet.size());
                    boardResponse.getColumns().forEach(column -> {
                        assertThat(column.getId()).isNotNull();
                        assertThat(column.getName()).isNotNull();
                    });
                });
    }

    @Test
    public void shouldSuccessfullyGetAListOfBoards() {
        MvcTestResult result = mockMvc.get().uri("/boards")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).bodyJson()
                .convertTo(InstanceOfAssertFactories.list(BoardResponse.class))
                .hasSize(1)
                .element(0)
                .satisfies(boardResponse -> {
                    assertThat(boardResponse.getId()).isEqualTo(1);
                    assertThat(boardResponse.getName()).isEqualTo("Platform Launch");
                    assertThat(boardResponse.getColumns()).hasSize(2);
                });
    }


}
