package core.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class ChecklistTest {

    @Test
    @DisplayName("Test Checklist")
    public void testChecklist() {
        // Tests constructor
        Checklist checklist = new Checklist();
        assertEquals("", checklist.getTitle(), "Default title of a new checklist should be \"\"");

        // Tests getChecklistLines() and order of checklists
        checklist.addChecklistLine();
        checklist.addChecklistLine();
        checklist.setChecklistChecked(0, true);
        assertTrue(!checklist.getChecklistLines().get(0).isChecked()
                && checklist.getChecklistLines().get(1).isChecked(),
                "Checklistline that is checked should appear at the bottom.");

        // Tests setChecklistLine() and setListLines()
        ArrayList<ChecklistLine> lines = new ArrayList<>(Arrays.asList(new ChecklistLine(), new ChecklistLine()));
        checklist.setlistLines(lines);
        checklist.setChecklistline(0, "test");
        assertEquals(checklist.getChecklistLines().get(0).getLine(), "test",
                "Checklistline text should be the new text that was set.");

        // Tests removeLine() and isEmpty()
        checklist.setTitle("test");
        assertFalse(checklist.isEmpty(), "Checklist should not be empty.");
        checklist.setTitle("");
        assertFalse(checklist.isEmpty(),
                "Even if the title is \"\", the checklist still contains a line, so isEmpty should return false.");
        checklist.removeChecklistLine(1);
        checklist.removeChecklistLine(0);
        assertTrue(checklist.isEmpty(), "Checklis should now be empty.");
    }
}
