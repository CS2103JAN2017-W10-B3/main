package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import todolist.commons.core.Messages;
import todolist.logic.commands.AddCommand;
import todolist.testutil.TestTask;
import todolist.testutil.TestUtil;

public class AddCommandTest extends ToDoListGuiTest {

    @Test
    public void addEvents() {
        //add one Task
        TestTask[] currentList = td.getTypicalEventTasks();
        TestTask taskToAdd = td.ma3269Quiz;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another Task
        taskToAdd = td.laundry;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate Task
        commandBox.runCommand(td.ma3269Quiz.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.cs2103Tutorial);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void addDeadlines() {
        //add one Task
        TestTask[] currentList = td.getTypicalDeadlineTasks();
        TestTask taskToAdd = td.applyInternship;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another Task
        taskToAdd = td.cs2010PS6;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate Task
        commandBox.runCommand(td.cs2010PS6.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.applyInternship);
    }

    @Test
    public void addFloats() {
        //add one Task
        TestTask[] currentList = td.getTypicalFloatingTasks();
        TestTask taskToAdd = td.cleanMyRoom;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another Task
        taskToAdd = td.chaseAfterDebts;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate Task
        commandBox.runCommand(td.chaseAfterDebts.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.cleanMyRoom);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getTitle().toString());
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous Tasks plus the new Task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
