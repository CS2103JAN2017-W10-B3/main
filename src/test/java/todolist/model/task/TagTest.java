package todolist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import todolist.model.tag.Tag;

//@@author A0122017Y
public class TagTest {

    @Test
    public void isValidTag() {
        // invalid name
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName(" ")); // spaces only
        assertFalse(Tag.isValidTagName("^")); // only non-alphanumeric characters
        assertFalse(Tag.isValidTagName("????")); //several non-alphanumeric characters
        assertFalse(Tag.isValidTagName("a b c d e")); //white spaces in between
        assertFalse(Tag.isValidTagName("My-SQL_Testing")); //word containing symbols

        // valid name
        assertTrue(Tag.isValidTagName("debugging")); // alphabets only
        assertTrue(Tag.isValidTagName("12345")); // numbers only
        assertTrue(Tag.isValidTagName("Cs2103t")); // alphanumeric characters
        assertTrue(Tag.isValidTagName("verylongstringtobeaddedasatagandlikeyalikeyouseehowlongitis")); // long string

    }

}
