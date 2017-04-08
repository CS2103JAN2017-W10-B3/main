package todolist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author A0122017Y
public class UrgencyLevelTest {
    @Test

    public void isValid() {
        // invalid urgency level
        assertFalse(UrgencyLevel.isValidUrgencyLevel("")); // empty string
        assertFalse(UrgencyLevel.isValidUrgencyLevel(" ")); // spaces only
        assertFalse(UrgencyLevel.isValidUrgencyLevel("^")); // only non-alphanumeric character
        assertFalse(UrgencyLevel.isValidUrgencyLevel("aa")); // only alphabetic strings
        assertFalse(UrgencyLevel.isValidUrgencyLevel("1*")); // number with non-alphanumeric character
        assertFalse(UrgencyLevel.isValidUrgencyLevel("4")); // number not in the range 1-3
        assertFalse(UrgencyLevel.isValidUrgencyLevel("0")); // number not in the range 1-3

        // valid urgency level
        assertTrue(UrgencyLevel.isValidUrgencyLevel("1")); // UrgencyLevel at 1
        assertTrue(UrgencyLevel.isValidUrgencyLevel("2")); // UrgencyLevel at 2
        assertTrue(UrgencyLevel.isValidUrgencyLevel("3")); // UrgencyLevel at 3
    }
}
