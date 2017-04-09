package guitests;

import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import todolist.commons.core.LogsCenter;
import todolist.commons.core.Messages;
import todolist.logic.commands.AddCommand;
import todolist.model.task.ReadOnlyTask.Category;
import todolist.testutil.TestTask;
import todolist.testutil.TestUtil;

//@@author A0110791M
public class AddCommandGuiTest extends ToDoListGuiTest {

    private final Logger logger = LogsCenter.getLogger(AddCommandGuiTest.class);
    @Test
    public void addEvents() {
        //add one Task
        TestTask[] currentList = td.getTypicalEventTasks();
        TestTask taskToAdd = td.ma3269Quiz;
        assertAddSuccess(Category.EVENT, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another Task
        taskToAdd = td.laundry;
        assertAddSuccess(Category.EVENT, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate Task
        commandBox.runCommand(td.ma3269Quiz.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(Category.EVENT, currentList));

        //add to empty list
        commandBox.runCommand("clear");
        TestTask task2ToAdd = td.cs2103Tutorial;
        currentList = new TestTask[0];
        assertAddSuccess(Category.EVENT, task2ToAdd, currentList);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void addDeadlines() {
        //add one Task
        TestTask[] currentList = td.getTypicalDeadlineTasks();
        TestTask taskToAdd = td.applyInternship;
        logger.info(taskToAdd.getTitle().toString());
        assertAddSuccess(Category.DEADLINE, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another Task
        taskToAdd = td.cs2010PS6;
        assertAddSuccess(Category.DEADLINE, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate Task
        commandBox.runCommand(td.cs2010PS6.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(Category.DEADLINE, currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(Category.DEADLINE, td.applyInternship);
    }

    @Test
    public void addFloats() {
        //add one Task
        TestTask[] currentList = td.getTypicalFloatingTasks();
        TestTask taskToAdd = td.cleanMyRoom;
        assertAddSuccess(Category.FLOAT, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another Task
        taskToAdd = td.chaseAfterDebts;
        assertAddSuccess(Category.FLOAT, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate Task
        commandBox.runCommand(td.chaseAfterDebts.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(Category.FLOAT, currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(Category.FLOAT, td.cleanMyRoom);
    }

//    @Test
//    public void addAll() {
//        //add one Task
//        TestTask[] currentList = td.getTypicalFloatingTasks();
//        TestTask taskToAdd = td.cleanMyRoom;
//        assertAddSuccess(null, taskToAdd, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//
//        //add another Task
//        taskToAdd = td.chaseAfterDebts;
//        assertAddSuccess(null, taskToAdd, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//
//        //add duplicate Task
//        commandBox.runCommand(td.chaseAfterDebts.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));
//
//        //add to empty list
//        commandBox.runCommand("clear");
//        assertAddSuccess(null, td.cleanMyRoom);
//    }

    private void assertAddSuccess(Category taskType, TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskType, taskToAdd.getTitle().toString());
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous Tasks plus the new Task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(taskType, expectedList));
    }

}
