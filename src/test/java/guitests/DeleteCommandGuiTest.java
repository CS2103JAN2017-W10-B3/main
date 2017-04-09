package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import todolist.model.task.ReadOnlyTask.Category;
import todolist.testutil.TestTask;
import todolist.testutil.TestUtil;

//@@author A0143648Y
public class DeleteCommandGuiTest extends ToDoListGuiTest {

    @Test
    public void delete() {

        TestTask[] currentEventList = td.getTypicalEventTasks();
        TestTask[] currentDeadlineList = td.getTypicalDeadlineTasks();
        TestTask[] currentFloatingList = td.getTypicalFloatingTasks();

        // delete the first in the eventlist
        String toDelete = "e1";
        currentEventList = TestUtil.removeTaskFromList(currentEventList, 1);
        assertDeleteSuccess(toDelete, currentEventList, currentDeadlineList, currentFloatingList);

        // delete the last in the eventlist
        toDelete = "e" + currentEventList.length;
        currentEventList = TestUtil.removeTaskFromList(currentEventList, currentEventList.length);
        assertDeleteSuccess(toDelete, currentEventList, currentDeadlineList, currentFloatingList);

        // invalid index
        commandBox.runCommand("delete " + "e" + currentEventList.length + 1);
        assertResultMessage("The task index provided is invalid");

        // delete the first in the deadlinelist
        toDelete = "d1";
        currentDeadlineList = TestUtil.removeTaskFromList(currentDeadlineList, 1);
        assertDeleteSuccess(toDelete, currentEventList, currentDeadlineList, currentFloatingList);

        // invalid index
        commandBox.runCommand("delete " + "d" + currentDeadlineList.length + 1);
        assertResultMessage("The task index provided is invalid");

        // delete the first in the Floatinglist
        toDelete = "f1";
        currentFloatingList = TestUtil.removeTaskFromList(currentFloatingList, 1);
        assertDeleteSuccess(toDelete, currentEventList, currentDeadlineList, currentFloatingList);

        // invalid index
        commandBox.runCommand("delete " + "f" + currentFloatingList.length + 1);
        assertResultMessage("The task index provided is invalid");

        toDelete = "e1-2";
        currentEventList = TestUtil.removeTaskFromList(currentEventList, 1, 2);
        assertDeleteSuccess(toDelete, currentEventList, currentDeadlineList, currentFloatingList);

        toDelete = "d1-2";
        currentDeadlineList = TestUtil.removeTaskFromList(currentDeadlineList, 1, 2);
        assertDeleteSuccess(toDelete, currentEventList, currentDeadlineList, currentFloatingList);

        toDelete = "f1-2";
        currentFloatingList = TestUtil.removeTaskFromList(currentFloatingList, 1, 2);
        assertDeleteSuccess(toDelete, currentEventList, currentDeadlineList, currentFloatingList);

        toDelete = "e1 f1 d1";
        currentEventList = TestUtil.removeTaskFromList(currentEventList, 1);
        currentDeadlineList = TestUtil.removeTaskFromList(currentDeadlineList, 1);
        currentFloatingList = TestUtil.removeTaskFromList(currentFloatingList, 1);
        assertDeleteSuccess(toDelete, currentEventList, currentDeadlineList, currentFloatingList);

    }

    /**
     * Runs the delete command to delete the tasks at specified indexes and
     * confirms the result is correct.
     */
    private void assertDeleteSuccess(String toDelete, TestTask[] expectedEventRemainder,
            TestTask[] expectedDeadlineRemainder, TestTask[] expectedFloatingRemainder) {

        commandBox.runCommand("delete " + toDelete);

        assertTrue(taskListPanel.isListMatching(Category.EVENT, expectedEventRemainder));
        assertTrue(taskListPanel.isListMatching(Category.FLOAT, expectedFloatingRemainder));
        assertTrue(taskListPanel.isListMatching(Category.DEADLINE, expectedDeadlineRemainder));

    }

}
