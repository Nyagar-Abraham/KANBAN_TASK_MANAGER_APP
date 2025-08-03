package org.abraham.kanbantaskmanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.abraham.kanbantaskmanager.dtos.BoardResponse;
import org.abraham.kanbantaskmanager.dtos.CreateBoardRequest;
import org.abraham.kanbantaskmanager.dtos.EditBoardRequest;
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
        var request = getCreateBoardRequest("Test Board", columnSet);

        var body = objectMapper.writeValueAsString(request);

        var result = getPostRequest(body, "boards", null);

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

        assertBoardResponse(result, columnSet, name);
    }

    @Test
    public void shouldFailDueToDuplicateBoardName() throws JsonProcessingException {
        var columnSet = Set.of(TaskStatusAndColumnName.DOING, TaskStatusAndColumnName.DONE);
        var request = getCreateBoardRequest(null, columnSet);

        var body = objectMapper.writeValueAsString(request);
        getPostRequest(body, "/boards", null);

        var result = getPostRequest(body, "/boards", null);

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertFailure(result, HttpStatus.BAD_REQUEST, "Board with name Test Board 2 already exists");
    }

    @Test
    public void boardCreationShouldFailDueToRequestValidation() throws JsonProcessingException {
        var columnSet = Set.of(TaskStatusAndColumnName.TODO, TaskStatusAndColumnName.DONE);
        var request = getCreateBoardRequest(null, columnSet);

        var body = objectMapper.writeValueAsString(request);

        var result = getPostRequest(body, "/boards", null);

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertFailure(result, HttpStatus.BAD_REQUEST, "Name is required!");
    }

    //        UPDATE
    @Test
    public void shouldSuccessfullyUpdateBoard() throws JsonProcessingException {
        var name = "Test Update Board";
        var columnSet = Set.of(TaskStatusAndColumnName.DOING, TaskStatusAndColumnName.DONE, TaskStatusAndColumnName.TODO);
        var request = getEditBoardRequest("Test Update Board", columnSet);
        String body = objectMapper.writeValueAsString(request);

        var result = getPutRequest(body, "/boards/{board_id}", 1L);

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertBoardResponse(result, columnSet, name);
    }

    @Test
    public void boardUpdateShouldFailDueToMissingBoardName() throws JsonProcessingException {
        var columnSet = Set.of(TaskStatusAndColumnName.TODO, TaskStatusAndColumnName.DONE);
        var request = getEditBoardRequest(null, columnSet);


        var body = objectMapper.writeValueAsString(request);
        log.info("body={}", body);
        var result = getPutRequest(body, "/boards/{board_id}", 1L);

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertFailure(result, HttpStatus.BAD_REQUEST, "Name is required!");
    }

//        RESULT
    @Test
    public void shouldSuccessfullyDeleteBoard() {
        var result = mockMvc.delete()
                .uri("/boards/{board_id}", 4L)
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());

        result = mockMvc.get()
                .uri("/boards/{board_id}", 4L)
                .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        assertFailure(result, HttpStatus.INTERNAL_SERVER_ERROR,  "No value present");
        assertThat(result)
                .hasFailed()
                .hasStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyJson()
                .extractingPath("$.message").isEqualTo("No value present");
    }

    @Test
    public void shouldFailDueToInvalidBoardId() {
        var result = mockMvc.delete()
                .uri("/boards/{board_id}", -1L)
                .exchange();

        assertThat(result)
        .hasFailed()
                .hasStatus(HttpStatus.BAD_REQUEST);

        assertThat(result)
                .bodyJson()
                .extractingPath("$.message").isEqualTo( "Board with ID -1 Not Found!");
    }


    private MvcTestResult getPutRequest(String body, String uri, Long param) {
        return param != null ? mockMvc.put()
                .uri(uri, param)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .exchange() : mockMvc.put()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
    }

    private MvcTestResult getPostRequest(String body, String uri, String param) {
        return param != null ? mockMvc.post()
                .uri(uri, param)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .exchange() :
                mockMvc.post()
                        .uri(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange();
    }

    private CreateBoardRequest getCreateBoardRequest(String name, Set<TaskStatusAndColumnName> columnNames) {
        var request = new CreateBoardRequest();
        request.setName(name);
        request.setColumns(columnNames);
        return request;
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


    private static EditBoardRequest getEditBoardRequest(String name, Set<TaskStatusAndColumnName> columnNames) {
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

    private static void assertFailure(MvcTestResult result, HttpStatus status, String message) {
        assertThat(result)
                .hasFailed()
                .hasStatus(status)
                .bodyJson()
                .extractingPath("$.name").isEqualTo(message);
    }


}
