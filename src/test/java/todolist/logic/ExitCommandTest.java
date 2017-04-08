package todolist.logic;

import java.util.Collections;

import org.junit.Test;

import todolist.logic.commands.ExitCommand;
import todolist.model.ToDoList;
import todolist.model.task.Task;

//@@author A0122017Y
public class ExitCommandTest extends LogicManagerTest {

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new ToDoList(), Collections.emptyList(), Task.ALL_CHAR);
    }

}
