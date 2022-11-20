package springboot.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import core.main.User;
import org.assertj.core.util.Arrays;
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

import java.util.ArrayList;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { ApiController.class, ApiControllerTest.class })
@WebMvcTest
public class ApiControllerTest {

    // private static final String TEST_FILE_NAME = "test_users";
    // private DataHandler dataHandler = new DataHandler(TEST_FILE_NAME);
    Gson gson = new Gson();
    // private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setupTestFile() throws Exception {
        User user = new User("arashfa", "123", "Arash", "Farzaneh");
        user.addBoard("NTNU");
        // register(user);

    }
    /*
     * @AfterEach
     * public void cleanTestFile() throws Exception {
     * dataHandler.clearSavedData();
     * }
     */

    @Test
    @Disabled
    public void testGetUser() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(getUrl("users", "arashfa"))
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertEquals("arashfa", gson.fromJson(result.getResponse().toString(), User.class).getUsername());

    }

    // private void register(User user) throws Exception {
    // String json;
    // try {
    // json = objectMapper.writeValueAsString(user);
    // } catch (Exception e) {
    // fail(e.getMessage());
    // return;
    // }
    // mockMvc.perform(
    // MockMvcRequestBuilders.post(getUrl("auth",
    // "register")).accept(MediaType.APPLICATION_JSON)
    // .contentType(MediaType.APPLICATION_JSON).content(json))
    // .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    // }

    private static String getUrl(String... portions) {
        StringBuilder url = new StringBuilder();

        new ArrayList<>(Arrays.asList(portions)).stream().forEach(portion -> url.append(("/")).append(portion));
        return url.toString();

    }
}
