package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import todolist.model.task.ReadOnlyTask;
import todolist.model.task.ReadOnlyTask.Category;

public class SelectCommandGuiTest extends ToDoListGuiTest {


    @Test
    public void selectTaskNonEmptyList() {

        assertSelectionInvalid(10); // invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(1); // first Task in the list
        int taskCount = td.getTypicalEventTasks().length;
        assertSelectionSuccess(taskCount); // last Task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(middleIndex); // a Task in the middle of the list

        assertSelectionInvalid(taskCount + 1); // invalid index
        assertTaskSelected(middleIndex); // assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectTaskEmptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select e" + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select e" + index);
        assertResultMessage("Tasks have been succussfully selected!\n");
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks(Category.EVENT).size(), 1);
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks(Category.EVENT).get(0);
        assertEquals(taskListPanel.getTask(Category.EVENT, index - 1), selectedTask);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks(Category.EVENT).size(), 0);
    }

}
