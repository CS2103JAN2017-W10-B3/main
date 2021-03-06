package todolist.logic;

import java.util.Collections;

import org.junit.Test;

import todolist.logic.commands.ExitCommand;
import todolist.model.ToDoList;
import todolist.model.task.Task;

public class ExitCommandTest extends LogicManagerTest {

    @Test
    public void executeExit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new ToDoList(), Collections.emptyList(), Task.ALL_CHAR);
    }

}
