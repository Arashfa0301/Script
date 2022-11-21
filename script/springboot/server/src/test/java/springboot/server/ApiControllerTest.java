package springboot.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.main.Board;
import core.main.Checklist;
import core.main.Note;
import core.main.User;
import data.DataHandler;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { ApiController.class, ApiService.class, SecurityConfig.class,
        ApiControllerTest.class })
@WebMvcTest
public class ApiControllerTest {
    public static String _TestUserUsername = "arashfa";
    public static String _TestUserPassword = "123";
    private static final String TEST_FILE_NAME = "users";
    private static final DataHandler datahandler = new DataHandler(TEST_FILE_NAME);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        registerTestUser();
    }

    @AfterEach
    public void cleanUp() throws Exception {
        deleteTestUser();
    }

    @Test
    public void testRegisterUser() throws Exception {

        User user = datahandler.getUser(_TestUserUsername);
        assertEquals(_TestUserUsername, user.getUsername());
        assertEquals(_TestUserPassword, user.getPassword());
        try {
            registerTestUser();
        } catch (ResponseStatusException ex) {
            // Expecting to get a 409 CONFLICT from creating the same user twice
            assertEquals(409, ex.getRawStatusCode());
        }

    }

    @Test
    public void testGetUser() throws Exception {

        assertEquals(_TestUserUsername,
                getTestUser().getUsername());
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders
                            .get(getUrl("user", _TestUserUsername))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Basic " + Base64.getEncoder()
                                    .encodeToString(("invalid_user" + ":" + _TestUserPassword)
                                            .getBytes(StandardCharsets.UTF_8))))

                    .andReturn();

        } catch (ResponseStatusException ex) {
            // Expecting to get a 401 CONFLICT from creating the same user twice
            assertEquals(401, ex.getRawStatusCode());
            assertEquals("You are not authorized to access this resource", ex.getReason());
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .delete(getUrl("user", _TestUserUsername, "delete")).accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + Base64.getEncoder()
                            .encodeToString(("invalid_user" + ":" + _TestUserPassword)
                                    .getBytes(StandardCharsets.UTF_8))))

                    .andReturn();
        } catch (ResponseStatusException ex) {
            // Expecting to get a 401 CONFLICT from creating the same user twice
            assertEquals(401, ex.getRawStatusCode());
            assertEquals("You are not authorized to delete this user", ex.getReason());
        }
    }

    @Test
    public void testCreateBoard() throws Exception {

        addTestBoard();
        assertEquals("test_board", getTestUser().getBoards().get(0).getName());

    }

    @Test
    public void testRemoveBoard() throws Exception {

        addTestBoard();
        assertEquals(1, getTestUser().getBoards().size());
        mockMvc.perform(
                MockMvcRequestBuilders.get(getUrl("boards", "remove", "test_board")).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + Base64.getEncoder()
                                .encodeToString((_TestUserUsername + ":" + _TestUserPassword)
                                        .getBytes(StandardCharsets.UTF_8))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(0, getTestUser().getBoards().size());

    }

    @Test
    public void testRenameBoard() throws Exception {

        addTestBoard();
        assertEquals("test_board", getTestUser().getBoards().get(0).getName());
        mockMvc.perform(
                MockMvcRequestBuilders.post(getUrl("boards", "rename", "test_board", "new_test_board"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + Base64.getEncoder()
                                .encodeToString((_TestUserUsername + ":" + _TestUserPassword)
                                        .getBytes(StandardCharsets.UTF_8))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals("new_test_board", getTestUser().getBoards().get(0).getName());

    }

    @Test
    public void testPutBoards() throws Exception {

        addTestBoard();
        assertEquals(0, getTestUser().getBoards().get(0).getNotes().size());
        assertEquals(0, getTestUser().getBoards().get(0).getChecklists().size());
        assertEquals("", getTestUser().getBoards().get(0).getBoardDescription());
        Board testBoard = new Board("test_board", "test_description", new ArrayList<>(), new ArrayList<>());
        testBoard.addNote(new Note());
        testBoard.addChecklist(new Checklist());
        testBoard.addChecklist(new Checklist());
        String boardJson;
        try {
            boardJson = objectMapper.writeValueAsString(testBoard);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        mockMvc.perform(
                MockMvcRequestBuilders.put(getUrl("board", "test_board"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + Base64.getEncoder()
                                .encodeToString((_TestUserUsername + ":" + _TestUserPassword)
                                        .getBytes(StandardCharsets.UTF_8)))
                        .content(boardJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        assertEquals(1, getTestUser().getBoards().get(0).getNotes().size());
        assertEquals(2, getTestUser().getBoards().get(0).getChecklists().size());
        assertEquals("test_description", getTestUser().getBoards().get(0).getBoardDescription());

    }

    private User getTestUser() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(getUrl("user", _TestUserUsername))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + Base64.getEncoder()
                                .encodeToString((_TestUserUsername + ":" + _TestUserPassword)
                                        .getBytes(StandardCharsets.UTF_8))))
                .andDo((resultHandler) -> {
                    if (resultHandler.getResponse().getStatus() != 200) {
                        throw new ResponseStatusException(resultHandler.getResponse().getStatus(), "", null);
                    }
                })
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        return objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
    }

    private void registerTestUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(getUrl("auth", "register/"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"" + "arash" + "\",\"lastName\":\"" + "farzaneh"
                                + "\",\"username\":\"" + _TestUserUsername + "\",\"password\":\""
                                + _TestUserPassword
                                + "\"}"))
                .andDo((resultHandler) -> {
                    if (resultHandler.getResponse().getStatus() != 201) {
                        throw new ResponseStatusException(resultHandler.getResponse().getStatus(), "", null);
                    }
                })
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
    }

    private void deleteTestUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(getUrl("user", _TestUserUsername, "delete")).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + Base64.getEncoder()
                        .encodeToString((_TestUserUsername + ":" + _TestUserPassword)
                                .getBytes(StandardCharsets.UTF_8))))
                .andDo((resultHandler) -> {
                    if (resultHandler.getResponse().getStatus() != 200) {
                        throw new ResponseStatusException(resultHandler.getResponse().getStatus(), "", null);
                    }
                })
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    private void addTestBoard() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(getUrl("boards", "create", "test_board")).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + Base64.getEncoder()
                                .encodeToString((_TestUserUsername + ":" + _TestUserPassword)
                                        .getBytes(StandardCharsets.UTF_8))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    private static String getUrl(String... portions) {
        StringBuilder url = new StringBuilder();
        new ArrayList<>(Arrays.asList(portions)).stream().forEach(portion -> url.append(("/")).append(portion));
        return url.toString();

    }

}
