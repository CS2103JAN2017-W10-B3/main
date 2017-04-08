package todolist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@ author A0122017Y
public class TaskIndexTest {

    @Test
    public void isValidTaskIndex() {
        //invalid task index
        assertFalse(TaskIndex.isValidTaskIndex('a', 4));  //task character not valid
        assertFalse(TaskIndex.isValidTaskIndex('d', -3)); //task number not valid

        //valid task index
        assertTrue(TaskIndex.isValidTaskIndex('d', 3));  //deadline task index
        assertTrue(TaskIndex.isValidTaskIndex('e', 3));  //event task index
        assertTrue(TaskIndex.isValidTaskIndex('f', 3));  //floating task index
        assertTrue(TaskIndex.isValidTaskIndex('e', 1999999));  //task index with very big number
    }

}
