package todolist.logic;

import org.junit.Test;

import todolist.logic.commands.AddCommand;
import todolist.logic.commands.EditCommand;
import todolist.logic.commands.UndoCommand;
import todolist.model.ToDoList;
import todolist.model.task.Task;
import todolist.model.task.Venue;

//@@author A0122017Y
public class UndoCommandTest extends LogicManagerTest {

    @Test
    public void executeUndoCommandFailure() {
        String expectedMessage = UndoCommand.MESSAGE_UNDO_FAILURE;
        AddCommand.resetLists();
        assertCommandFailure("undo", expectedMessage);
    }

    @Test
    public void executeMultipleUndoCommand() throws Exception {
     // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAddedDeadline = helper.cs2103Deadline();
        Task toBeEdited = new Task(toBeAddedDeadline);
        ToDoList expectedTDL = new ToDoList();
        model.resetData(expectedTDL);
        ToDoList twiceUndo = new ToDoList(expectedTDL);
        expectedTDL.addTask(toBeAddedDeadline);
        String feedbackToUser = String.format(AddCommand.MESSAGE_SUCCESS, toBeAddedDeadline);
        AddCommand.resetLists();

        // execute command and verify result for different types of tasks
        assertCommandSuccess(helper.generateAddCommand(toBeAddedDeadline),
                feedbackToUser,
                expectedTDL,
                expectedTDL.getFilteredDeadlines(), Task.DEADLINE_CHAR);

        ToDoList originalTDL = new ToDoList(expectedTDL);
        toBeEdited.setVenue(new Venue("there"));
        expectedTDL.updateTask(toBeAddedDeadline, toBeEdited);
        String editfeedbackToUser = EditCommand.MESSAGE_EDIT_TASK_SUCCESS
                + "[" + toBeEdited.getTitle().toString() + "] ";

        // execute edit command
        assertCommandSuccess("edit d1 /venue there",
                editfeedbackToUser,
                expectedTDL,
                expectedTDL.getFilteredDeadlines(), Task.DEADLINE_CHAR);

        // first time should undo the edit command
        String undofeedbackToUser = UndoCommand.MESSAGE_UNDO_SUCCESS + editfeedbackToUser;
        assertCommandSuccess("undo", undofeedbackToUser, originalTDL,
                originalTDL.getFilteredDeadlines(), Task.DEADLINE_CHAR);

        // second time should undo the add command
        String undoTwiceFeedback = UndoCommand.MESSAGE_UNDO_SUCCESS + feedbackToUser;
        assertCommandSuccess("undo", undoTwiceFeedback, twiceUndo,
                twiceUndo.getFilteredDeadlines(), Task.DEADLINE_CHAR);

        // third time no more command to undo, throw exception
        assertCommandFailure("undo", UndoCommand.MESSAGE_UNDO_FAILURE);

    }

}
