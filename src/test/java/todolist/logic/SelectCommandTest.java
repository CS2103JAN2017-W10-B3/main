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
    public void executeSelectInvalidArgsFormatErrorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    //Check if the error message is generated if index given is not found in the list
    public void executeSelectIndexNotFoundErrorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    //Check if the message shown is correct if index given is valid
    public void executeSelectJumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateEventTaskList(3);

        ToDoList expectedAB = helper.generateToDoList(threeTasks);
        helper.addToModel(model, threeTasks);
        Task toSelect = threeTasks.get(1);

        assertCommandSuccess("e2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, "e2", toSelect.toString()),
                expectedAB,
                expectedAB.getFilteredEvents(), Task.EVENT_CHAR);
        assertEquals(model.getFilteredEventList().get(1), threeTasks.get(1));
    }

    @Test
    //Check if the message shown is correct if index given is valid
    public void executeMultipleSelectJumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateEventTaskList(5);

        ToDoList expectedAB = helper.generateToDoList(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("e1-3",
                SelectCommand.MESSAGE_SELECT_TASK_SUCCESS,
                expectedAB,
                expectedAB.getFilteredEvents(), Task.EVENT_CHAR);
    }

    @Test
    //Check if the message shown is correct if interval given is inverted
    public void executeMultipleSelectJumpsToCorrectDeadline() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateDeadlineTaskList(5);

        ToDoList expectedAB = helper.generateToDoList(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("4-2",
                SelectCommand.MESSAGE_SELECT_TASK_SUCCESS,
                expectedAB,
                expectedAB.getFilteredDeadlines(), Task.DEADLINE_CHAR);
    }

    @Test
    //Check if the message shown is correct if index given is in the format of jumps
    public void executeMultipleIntervalSelectFloat() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateFloatTaskList(6);

        ToDoList expectedAB = helper.generateToDoList(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("f5-2 f1-3",
                SelectCommand.MESSAGE_SELECT_TASK_SUCCESS,
                expectedAB,
                expectedAB.getFilteredFloats(), Task.FLOAT_CHAR);
    }

}
