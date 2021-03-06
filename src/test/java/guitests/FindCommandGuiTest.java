package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import todolist.commons.core.Messages;
import todolist.model.task.ReadOnlyTask.Category;
import todolist.testutil.TestTask;

public class FindCommandGuiTest extends ToDoListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find CS2105"); // no results
        assertFindResult("find DBS", td.dbsInterview); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete e2");
        assertFindResult("find Joe", td.hangOutJoe);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(Category.EVENT, expectedHits));
    }
}
