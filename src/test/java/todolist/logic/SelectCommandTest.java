package todolist.logic;

import static org.junit.Assert.assertEquals;
import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import todolist.logic.commands.SelectCommand;
import todolist.model.ToDoList;
import todolist.model.task.Task;

//@@author A0122017Y
public class SelectCommandTest extends LogicManagerTest {

    @Test
    //Check if the error message is generated if no indexes is given
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    //Check if the error message is generated if index given is not found in the list
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    //Check if the message shown is correct if index given is valid
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        ToDoList expectedAB = helper.generateToDoList(threeTasks);
        helper.addToModel(model, threeTasks);
        Task toSelect = threeTasks.get(1);

        assertCommandSuccess("e2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, "e2", toSelect.toString()),
                expectedAB,
                expectedAB.getFilteredEvents(), Task.EVENT_CHAR);
        assertEquals(model.getFilteredEventList().get(1), threeTasks.get(1));
    }

}
