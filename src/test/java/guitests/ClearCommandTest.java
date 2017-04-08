package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import todolist.model.task.ReadOnlyTask.Category;

public class ClearCommandTest extends ToDoListGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(Category.EVENT, td.getTypicalEventTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.ma3269Quiz.getAddCommand());
        assertTrue(taskListPanel.isListMatching(Category.EVENT, td.ma3269Quiz));
        commandBox.runCommand("delete e1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("To-do list has been cleared!");
    }
}
