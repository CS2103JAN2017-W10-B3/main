package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import todolist.commons.core.Config;
import todolist.logic.commands.ChangeDirectoryCommand;
import todolist.testutil.TestTask;

//@@author A0110791M
public class ChangeDirectoryCommandTest extends ToDoListGuiTest {

    public static final String VALID_NEW_FILE_PATH = "testdata/todolist.xml";
    public static final String INVALID_NEW_FILE_PATH = "!@#$%^&*().xml";

    @Test
    public void changeValidDirectory() throws InterruptedException {
        TestTask[] expectedList = td.getTypicalEventTasks();
        int initialNumberOfTasks = taskListPanel.getNumberOfTasks();
        String currentFilePath = Config.getToDoListFilePath();

        //change directory to valid file path
        commandBox.runCommand(getChangeDirCommand(VALID_NEW_FILE_PATH));
        assertSuccessMessage(currentFilePath, VALID_NEW_FILE_PATH);
        assertEquals(VALID_NEW_FILE_PATH, Config.getToDoListFilePath());

        //check data is preserved
        int updatedNumberOfTasks = taskListPanel.getNumberOfTasks();
        assertEquals(initialNumberOfTasks, updatedNumberOfTasks);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    @Test
    public void changeInvalidDirectory() {
        TestTask[] expectedList = td.getTypicalEventTasks();
        int initialNumberOfTasks = taskListPanel.getNumberOfTasks();
        String currentFilePath = Config.getToDoListFilePath();

        //change directory to invalid file path
        commandBox.runCommand(getChangeDirCommand(INVALID_NEW_FILE_PATH));
        assertFailureMessage(INVALID_NEW_FILE_PATH);
        assertEquals(currentFilePath, Config.getToDoListFilePath());

        //check data is still preserved
        int updatedNumberOfTasks = taskListPanel.getNumberOfTasks();
        assertEquals(initialNumberOfTasks, updatedNumberOfTasks);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    private String getChangeDirCommand(String filePath) {
        return "changedir ".concat(filePath);
    }

    private void assertSuccessMessage(String currentFilePath, String newFilePath) throws InterruptedException {
        String expected = String.format(ChangeDirectoryCommand.MESSAGE_SUCCESS, currentFilePath, newFilePath);
        assertResultMessage(expected);
    }

    private void assertFailureMessage(String invalidFilePath) {
        String expected = String.format(ChangeDirectoryCommand.MESSAGE_FAILURE, invalidFilePath);
        assertResultMessage(expected);
    }
}
