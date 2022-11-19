package core.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        User user = new User("user", "password", "first", "last");
        assertEquals("user", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("first", user.getFirstName());
        assertEquals("last", user.getLastName());

        assertThrows(IllegalArgumentException.class, () -> {
            new User("", "", "", "");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new User(" ", " ", " ", " ");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new User("\\\\/ ", "dnoijsd", "sdofh", "dailf");
        });
    }

    @Test
    @DisplayName("Test set password")
    public void testSetPassword() {
        User user = new User("test", "1", "test", "test");
        assertEquals("1", user.getPassword());
        user.setPassword("2");
        assertEquals("2", user.getPassword());
    }
}
