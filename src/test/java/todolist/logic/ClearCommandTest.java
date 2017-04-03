package todolist.logic;

import java.util.Collections;

import org.junit.Test;

import todolist.logic.commands.ClearCommand;
import todolist.model.ToDoList;
import todolist.model.task.Task;

//@@author A0122017Y
public class ClearCommandTest extends LogicManagerTest {
    
    @Test
    //execute clear command and check the result
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new ToDoList(), Collections.emptyList(), Task.ALL_CHAR);
    }

}
