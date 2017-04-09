package todolist.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ParserUtilTest {

    @Test
    public void executeIsMultipleValidIndex() {
        assertTrue(ParserUtil.isMultipleValidIndex("d1-3"));
        assertTrue(ParserUtil.isMultipleValidIndex("e21-23"));
        assertTrue(ParserUtil.isMultipleValidIndex("f12-13"));
        assertTrue(ParserUtil.isMultipleValidIndex("c5-33"));
        assertTrue(ParserUtil.isMultipleValidIndex("1-3"));

        assertFalse(ParserUtil.isMultipleValidIndex("d1"));
        assertFalse(ParserUtil.isMultipleValidIndex("b1-3"));
        assertFalse(ParserUtil.isMultipleValidIndex("1"));
        assertFalse(ParserUtil.isMultipleValidIndex("d-1"));
        assertFalse(ParserUtil.isMultipleValidIndex("not number"));

    }
}
