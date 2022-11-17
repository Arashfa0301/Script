package core.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ChecklistTest {

    @Test
    @Disabled
    @DisplayName("Test checklist")
    public void testCecklist() {
        Checklist checklist = new Checklist();
        assertTrue(checklist.isEmpty());
        checklist.addChecklistLine();
        checklist.getChecklistLines().get(0).setLine("test");
        assertFalse(checklist.isEmpty());
        assertEquals("test", checklist.getChecklistLines().get(0).getLine());
        checklist.addChecklistLine();
        checklist.getChecklistLines().get(1).setLine("testing");
        assertEquals(Arrays.asList("test", "testing"), Arrays.asList(checklist.getChecklistLines().get(0).getLine(),
                checklist.getChecklistLines().get(1).getLine()));

        assertFalse(checklist.getChecklistLines().get(0).getChecked());
<<<<<<< HEAD
        checklist.getChecklistLines().get(0).setChecked(true);
=======
        checklist.getChecklistLines().get(0).checked(true);
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
        assertTrue(checklist.getChecklistLines().get(0).getChecked());
        Checklist checklist2 = new Checklist();
        checklist2.setTitle("title");
        assertFalse(checklist2.isEmpty());
    }
}
