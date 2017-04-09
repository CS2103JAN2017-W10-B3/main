package todolist.logic;

import java.util.Collections;

import org.junit.Test;

import todolist.logic.commands.ClearCommand;
import todolist.model.ToDoList;
import todolist.model.task.Task;

public class ClearCommandTest extends LogicManagerTest {

    @Test
    //execute clear command and check the result
    public void executeClear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateEventTask(1));
        model.addTask(helper.generateEventTask(2));
        model.addTask(helper.generateEventTask(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS,
                new ToDoList(), Collections.emptyList(), Task.ALL_CHAR);
    }

}
