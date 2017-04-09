package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import todolist.model.task.ReadOnlyTask.Category;
import todolist.testutil.TaskBuilder;
import todolist.testutil.TestTask;
import todolist.testutil.TestUtil;
//@@ author A0143648Y
public class DeleteParameterGuiTest extends ToDoListGuiTest {

    TestTask[] currentEventList = td.getTypicalEventTasks();
    TestTask[] currentDeadlineList = td.getTypicalDeadlineTasks();
    TestTask[] currentFloatingList = td.getTypicalFloatingTasks();
    ArrayList<TestTask> updatedTasks = new ArrayList<TestTask>();
    String toDelete = "";

    @Test
    public void deleteMultipleDeadlineEndtimeSuccess() throws Exception {
        toDelete = "d3-4 /to";
        commandBox.runCommand("delete " + toDelete);
        TestTask fypPresentation = new TaskBuilder().withTitle("FYP Presentation").withVenue("EA-06-06")
                .withTags("FYP").withDescription("FINAL PRESENTATION")
                .withCompleteStatus("false").build();
        TestTask fypFinalSubmission = new TaskBuilder().withTitle("FYP Online Submission").withVenue("Online")
                .withTags("FYP").withDescription("Submit to online portal.")
                .withCompleteStatus("false").build();
        updatedTasks.clear();
        updatedTasks.add(fypPresentation);
        updatedTasks.add(fypFinalSubmission);
        currentDeadlineList = TestUtil.removeTaskFromList(currentDeadlineList, 3, 4);
        currentFloatingList = TestUtil.addTasksToList(currentFloatingList, fypPresentation, fypFinalSubmission);
        assertCardMatching(Category.FLOAT, updatedTasks);
        assertDeleteSuccess(currentEventList, currentDeadlineList, currentFloatingList);
    }

    @Test
    public void deleteSingleEventEndtimeSuccess() throws Exception {
        toDelete = "e2 /to";
        commandBox.runCommand("delete " + toDelete);
        TestTask dbsInterview = new TaskBuilder().withTitle("DBS Internship interview").withVenue("Raffles Place")
                .withStartTime("May 31, 9:30")
                .withTags("interview", "internship", "important").withUrgencyLevel("3")
                .withDescription("I love you").withCompleteStatus("false").build();
        updatedTasks.clear();
        updatedTasks.add(dbsInterview);
        currentEventList = TestUtil.removeTaskFromList(currentEventList, 2);
        currentFloatingList = TestUtil.addTasksToList(currentFloatingList, dbsInterview);
        assertCardMatching(Category.FLOAT, updatedTasks);
        assertDeleteSuccess(currentEventList, currentDeadlineList, currentFloatingList);
    }

    @Test
    public void deleteSingleFloatingUrgencylevelSuccess() throws Exception {
        toDelete = "f1 /level";
        commandBox.runCommand("delete " + toDelete);
        TestTask buyGroceries = new TaskBuilder().withTitle("Buy groceries").withVenue("NTUC")
                .withDescription("Buy cheese, pepper and milk.")
                .withCompleteStatus("false").build();
        updatedTasks.clear();
        updatedTasks.add(buyGroceries);
        currentFloatingList = TestUtil.removeTaskFromList(currentFloatingList, 1);
        currentFloatingList = TestUtil.addTasksToList(currentFloatingList, buyGroceries);
        assertCardMatching(Category.FLOAT, updatedTasks);
        assertDeleteSuccess(currentEventList, currentDeadlineList, currentFloatingList);
    }

    @Test
    public void deleteSingleEventDescriptionSuccess() throws Exception {
        toDelete = "e1 /description";
        commandBox.runCommand("delete " + toDelete);
        TestTask cs2103Tutorial = new TaskBuilder().withTitle("CS2103 Tutorial").withVenue("COM1-B103")
                .withStartTime("May 30, 10:00").withEndTime("May 30, 11:00").withTags("lesson").withUrgencyLevel("3")
                .withCompleteStatus("false").build();
        updatedTasks.clear();
        updatedTasks.add(cs2103Tutorial);
        currentEventList[0] = cs2103Tutorial;
        assertCardMatching(Category.EVENT, updatedTasks);
        assertDeleteSuccess(currentEventList, currentDeadlineList, currentFloatingList);
    }

    @Test
    public void deleteSingleDeadlineVenueSuccess() throws Exception {
        toDelete = "d1 /venue";
        commandBox.runCommand("delete " + toDelete);
        TestTask cs2103Submission = new TaskBuilder().withTitle("CS2103 Tutorial")
                .withEndTime("June 1, 11:00").withTags("lesson").withUrgencyLevel("3")
                .withDescription("Submit jar file + all the documents.").withCompleteStatus("false").build();
        updatedTasks.clear();
        updatedTasks.add(cs2103Submission);
        currentDeadlineList[0] = cs2103Submission;
        assertCardMatching(Category.DEADLINE, updatedTasks);
        assertDeleteSuccess(currentEventList, currentDeadlineList, currentFloatingList);
    }

    @Test
    public void deleteSingleFloatingTagSuccess() throws Exception {
        toDelete = "f1 #";
        commandBox.runCommand("delete " + toDelete);
        TestTask buyGroceries = new TaskBuilder().withTitle("Buy groceries").withVenue("NTUC")
                .withUrgencyLevel("2").withDescription("Buy cheese, pepper and milk.")
                .withCompleteStatus("false").build();
        updatedTasks.clear();
        updatedTasks.add(buyGroceries);
        currentFloatingList[0] = buyGroceries;
        assertCardMatching(Category.FLOAT, updatedTasks);
        assertDeleteSuccess(currentEventList, currentDeadlineList, currentFloatingList);
    }

    private void assertCardMatching(Category category, ArrayList<TestTask> updatedTasks) {

        // confirm the new card contains the right data
        for (TestTask updatedTask : updatedTasks) {
            TaskCardHandle editedCard = taskListPanel.navigateToTask(category, updatedTask.getTitle().toString());
            assertMatching(updatedTask, editedCard);
        }
    }

    /**
     * Runs the delete command to delete the tasks at specified indexes and
     * confirms the result is correct.
     */
    private void assertDeleteSuccess(TestTask[] expectedEventRemainder, TestTask[] expectedDeadlineRemainder,
            TestTask[] expectedFloatingRemainder) {
        assertTrue(taskListPanel.isListMatching(Category.EVENT, expectedEventRemainder));
        assertTrue(taskListPanel.isListMatching(Category.FLOAT, expectedFloatingRemainder));
        assertTrue(taskListPanel.isListMatching(Category.DEADLINE, expectedDeadlineRemainder));

    }

}
