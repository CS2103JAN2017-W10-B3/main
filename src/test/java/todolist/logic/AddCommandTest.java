package todolist.logic;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import todolist.logic.commands.AddCommand;
import todolist.logic.commands.UndoCommand;
import todolist.model.ReadOnlyToDoList;
import todolist.model.ToDoList;
import todolist.model.tag.Tag;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.Task;
import todolist.model.task.Time;
import todolist.model.task.Title;
import todolist.model.task.UrgencyLevel;
import todolist.model.task.Venue;


//@@ author A0122017Y
/*
 * Logic test for Add Command
 */
public class AddCommandTest extends LogicManagerTest {

    @Test
    public void executeAddInvalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandFailure("add", expectedMessage);
    }

    @Test
    public void executeAddInvalidTaskData() {
        //invalid title
        assertCommandFailure("add []\\[;] /venue 12345 /from today /to tomorrow",
                Title.MESSAGE_TITLE_CONSTRAINTS);

        //invalid venue
        assertCommandFailure("add do CS!!! /venue &*))(*)(*) /from today /to tomorrow",
                Venue.MESSAGE_VENUE_CONSTRAINTS);

        //invalid start time
        assertCommandFailure("add Valid Title /venue there /from randomString /to tomorrow",
                Time.MESSAGE_TIME_CONSTRAINTS);

        //invalid end time
        assertCommandFailure("add Valid Title /venue there /from today /to randomString",
                Time.MESSAGE_TIME_CONSTRAINTS);

        //invalid urgency level
        assertCommandFailure("add Valid Title /venue there /level 7",
                UrgencyLevel.MESSAGE_URGENCYLEVEL_CONSTRAINTS);

        //invalid tag
        assertCommandFailure("add Valid Title /venue there /level 2 /description valid #&*",
                Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void executeAddSuccessfulDeadline() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAddedDeadline = helper.cs2103Deadline();
        ToDoList expectedTDL = new ToDoList();
        ToDoList originalTDL = new ToDoList(expectedTDL);
        expectedTDL.addTask(toBeAddedDeadline);
        String feedbackToUser = String.format(AddCommand.MESSAGE_SUCCESS, toBeAddedDeadline);

        // execute command and verify result for different types of tasks
        assertAddCommandSuccess(helper.generateAddCommand(toBeAddedDeadline),
                feedbackToUser,
                expectedTDL,
                expectedTDL.getFilteredDeadlines(), Task.DEADLINE_CHAR);

        // execute undo
        assertCommandSuccess("undo",
                UndoCommand.MESSAGE_UNDO_SUCCESS + feedbackToUser,
                originalTDL, originalTDL.getFilteredDeadlines(), Task.DEADLINE_CHAR);
    }

    @Test
    public void executeAddSuccessfulEvent() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAddedEvent = helper.cs2103Event();
        ToDoList expectedTDL = new ToDoList();
        ToDoList originalTDL = new ToDoList(expectedTDL);
        expectedTDL.addTask(toBeAddedEvent);
        String feedbackToUser = String.format(AddCommand.MESSAGE_SUCCESS, toBeAddedEvent);

        // execute command and verify result for different types of tasks
        assertAddCommandSuccess(helper.generateAddCommand(toBeAddedEvent),
                feedbackToUser,
                expectedTDL,
                expectedTDL.getFilteredEvents(), Task.EVENT_CHAR);

        // execute undo
        assertCommandSuccess("undo",
                UndoCommand.MESSAGE_UNDO_SUCCESS + feedbackToUser,
                originalTDL, originalTDL.getFilteredEvents(), Task.EVENT_CHAR);
    }

    @Test
    public void executeAddSuccessfulAndUndoFloat() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAddedFloat = helper.cs2103Float();
        ToDoList expectedTDL = new ToDoList();
        ToDoList originalTDL = new ToDoList(expectedTDL);
        expectedTDL.addTask(toBeAddedFloat);
        String feedbackToUser = String.format(AddCommand.MESSAGE_SUCCESS, toBeAddedFloat);

        // execute command and verify result for different types of tasks
        assertAddCommandSuccess(helper.generateAddCommand(toBeAddedFloat),
                feedbackToUser,
                expectedTDL,
                expectedTDL.getFilteredFloats(), Task.FLOAT_CHAR);

        assertCommandSuccess("undo",
                UndoCommand.MESSAGE_UNDO_SUCCESS + feedbackToUser,
                originalTDL, originalTDL.getFilteredFloats(), Task.FLOAT_CHAR);
    }

    @Test
    public void executeAddDuplicateNotAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.cs2103();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal address book

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK);
    }

    public void assertAddCommandSuccess(String inputCommand, String expectedMessage,
            ReadOnlyToDoList expectedToDoList,
            List<? extends ReadOnlyTask> expectedShownList, Character taskChar) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedToDoList, expectedShownList, taskChar);
    }

}
