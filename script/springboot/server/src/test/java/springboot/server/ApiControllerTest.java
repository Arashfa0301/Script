package springboot.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.main.User;
import data.DataHandler;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import java.io.IOException;
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
    public void setupTestFile() throws Exception {
        register();

    }

    @AfterEach
    public void cleanUp() throws IOException {
        datahandler.removeUser(_TestUserUsername);
    }

    @Test
    @Disabled
    public void testRegisterUser() throws Exception {
        User user = datahandler.getUser(_TestUserUsername);
        assertEquals(_TestUserUsername, user.getUsername());
        assertEquals(_TestUserPassword, user.getPassword());
        try {
            register();
        } catch (ResponseStatusException ex) {
            // Expecting to get a 409 CONFLICT from creating the same user twice
            assertEquals(409, ex.getRawStatusCode());
        }

    }

    @Test
    @Disabled
    public void testGetUser() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(getUrl("user", _TestUserUsername))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + Base64.getEncoder()
                                .encodeToString((_TestUserUsername + ":" + _TestUserPassword)
                                        .getBytes(StandardCharsets.UTF_8))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString() + "\n\n\n\n\n\n\n\n\n\n");
        assertEquals(_TestUserUsername,
                objectMapper.readValue(result.getResponse().getContentAsString(), User.class).getUsername());
    }

    private void register() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(getUrl("auth", "register"))
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

    private static String getUrl(String... portions) {
        StringBuilder url = new StringBuilder();
        new ArrayList<>(Arrays.asList(portions)).stream().forEach(portion -> url.append(("/")).append(portion));
        return url.toString();

    }

}
