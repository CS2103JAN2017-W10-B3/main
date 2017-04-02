package todolist.logic;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import todolist.logic.commands.AddCommand;
import todolist.model.ToDoList;
import todolist.model.tag.Tag;
import todolist.model.task.Task;
import todolist.model.task.Time;
import todolist.model.task.Title;
import todolist.model.task.UrgencyLevel;
import todolist.model.task.Venue;


//@@ author A0122017Y
/*
 * Logic test for Add Command
 */
public class AddCommandTest extends LogicManagerTest{

    @Test
    public void execute_add_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandFailure("add", expectedMessage);
    }

    @Test
    public void execute_add_invalidTaskData() {
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
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.cs2103();
        Task toBeAddedDeadline = helper.cs2103Deadline();
        Task toBeAddedEvent = helper.cs2103Event();
        Task toBeAddedFloat = helper.cs2103Float();
        Task toBeAddedComplete = helper.cs2103Complete();
        ToDoList expectedTDL = new ToDoList();
        expectedTDL.addTask(toBeAdded);

        // execute command and verify result for different types of tasks
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTDL,
                expectedTDL.getTaskList());
        
        assertCommandSuccess(helper.generateAddCommand(toBeAddedDeadline),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAddedDeadline),
                expectedTDL,
                expectedTDL.getFilteredDeadlines());
        
        assertCommandSuccess(helper.generateAddCommand(toBeAddedEvent),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAddedEvent),
                expectedTDL,
                expectedTDL.getFilteredDeadlines());
        
        assertCommandSuccess(helper.generateAddCommand(toBeAddedFloat),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAddedFloat),
                expectedTDL,
                expectedTDL.getFilteredDeadlines());
        
        assertCommandSuccess(helper.generateAddCommand(toBeAddedComplete),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAddedComplete),
                expectedTDL,
                expectedTDL.getFilteredDeadlines());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.cs2103();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal address book

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK);
    }
    
}