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
public class EditCommandGuiTest extends ToDoListGuiTest {

    TestTask[] currentEventList = td.getTypicalEventTasks();
    TestTask[] currentDeadlineList = td.getTypicalDeadlineTasks();
    TestTask[] currentFloatingList = td.getTypicalFloatingTasks();
    ArrayList<TestTask> updatedTasks = new ArrayList<TestTask>();
    String toEdit = "";

    @Test
    public void edit_multiple_floating_starttime_success() throws Exception {
        toEdit = "f3-4 /from June 3, 11:00";
        commandBox.runCommand("edit " + toEdit);
        TestTask learnJava = new TaskBuilder().withTitle("Learn Java").withTags("java").withVenue("Online")
                .withStartTime("June 3, 11:00").withUrgencyLevel("1")
                .withDescription("Refer to photos gallery on phone for resources.").withCompleteStatus("false").build();
        TestTask planGradTrip = new TaskBuilder().withTitle("Plan for Grad Trip").withTags("gradtrip")
                .withVenue("Online").withStartTime("June 3, 11:00").withUrgencyLevel("1")
                .withDescription("Decide when and where.").withCompleteStatus("false").build();
        updatedTasks.clear();
        updatedTasks.add(learnJava);
        updatedTasks.add(planGradTrip);
        currentFloatingList = TestUtil.removeTaskFromList(currentFloatingList, 3, 4);
        currentFloatingList = TestUtil.addTasksToList(currentFloatingList, learnJava, planGradTrip);
        assertCardMatching(Category.FLOAT, updatedTasks);
        assertEditSuccess(currentEventList, currentDeadlineList, currentFloatingList);
    }

    @Test
    public void edit_single_event_endtime_success() throws Exception {
        toEdit = "e2 /to May 31, 12:30";
        commandBox.runCommand("edit " + toEdit);
        TestTask dbsInterview = new TaskBuilder().withTitle("DBS Internship interview").withVenue("Raffles Place")
                .withStartTime("May 31, 9:30").withEndTime("May 31, 12:30")
                .withTags("interview", "internship", "important").withUrgencyLevel("3").withDescription("I love you")
                .withCompleteStatus("false").build();
        updatedTasks.clear();
        updatedTasks.add(dbsInterview);
        currentEventList = TestUtil.removeTaskFromList(currentEventList, 2);
        currentEventList = TestUtil.addTasksToList(currentEventList, dbsInterview);
        assertCardMatching(Category.EVENT, updatedTasks);
        assertEditSuccess(currentEventList, currentDeadlineList, currentFloatingList);
    }

    @Test
    public void edit_single_floating_urgencylevel_success() throws Exception {
        toEdit = "f1 /level 1";
        commandBox.runCommand("edit " + toEdit);
        TestTask buyGroceries = new TaskBuilder().withTitle("Buy groceries").withTags("shopping").withVenue("NTUC")
                .withUrgencyLevel("1").withDescription("Buy cheese, pepper and milk.").withCompleteStatus("false")
                .build();
        updatedTasks.clear();
        updatedTasks.add(buyGroceries);
        currentFloatingList = TestUtil.removeTaskFromList(currentFloatingList, 1);
        currentFloatingList = TestUtil.addTasksToList(currentFloatingList, buyGroceries);
        assertCardMatching(Category.FLOAT, updatedTasks);
        assertEditSuccess(currentEventList, currentDeadlineList, currentFloatingList);
    }

    @Test
    public void edit_single_event_title_success() throws Exception {
        toEdit = "e1 /title GER1000 Tutorial";
        commandBox.runCommand("edit " + toEdit);
        TestTask cs2103Tutorial = new TaskBuilder().withTitle("GER1000 Tutorial").withVenue("COM1-B103")
                .withStartTime("May 30, 10:00").withEndTime("May 30, 11:00").withTags("lesson").withUrgencyLevel("3")
                .withDescription("I love you").withCompleteStatus("false").build();
        updatedTasks.clear();
        updatedTasks.add(cs2103Tutorial);
        currentEventList[0] = cs2103Tutorial;
        assertCardMatching(Category.EVENT, updatedTasks);
        assertEditSuccess(currentEventList, currentDeadlineList, currentFloatingList);
    }

    @Test
    public void edit_single_deadline_venue_success() throws Exception {
        toEdit = "d1 /venue FASS-B103";
        commandBox.runCommand("edit " + toEdit);
        TestTask cs2103Submission = new TaskBuilder().withTitle("CS2103 Tutorial").withVenue("FASS-B103")
                .withEndTime("June 1, 11:00").withTags("lesson").withUrgencyLevel("3")
                .withDescription("Submit jar file + all the documents.").withCompleteStatus("false").build();
        updatedTasks.clear();
        updatedTasks.add(cs2103Submission);
        currentDeadlineList[0] = cs2103Submission;
        assertCardMatching(Category.DEADLINE, updatedTasks);
        assertEditSuccess(currentEventList, currentDeadlineList, currentFloatingList);
    }

    @Test
    public void edit_single_floating_tag_success() throws Exception {
        toEdit = "f1 /description Buy oranges";
        commandBox.runCommand("edit " + toEdit);
        TestTask buyGroceries = new TaskBuilder().withTitle("Buy groceries").withTags("shopping").withVenue("NTUC")
                .withUrgencyLevel("2").withDescription("Buy oranges").withCompleteStatus("false").build();
        updatedTasks.clear();
        updatedTasks.add(buyGroceries);
        currentFloatingList[0] = buyGroceries;
        assertCardMatching(Category.FLOAT, updatedTasks);
        assertEditSuccess(currentEventList, currentDeadlineList, currentFloatingList);
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
    private void assertEditSuccess(TestTask[] expectedEventRemainder, TestTask[] expectedDeadlineRemainder,
            TestTask[] expectedFloatingRemainder) {
        assertTrue(taskListPanel.isListMatching(Category.EVENT, expectedEventRemainder));
        assertTrue(taskListPanel.isListMatching(Category.FLOAT, expectedFloatingRemainder));
        assertTrue(taskListPanel.isListMatching(Category.DEADLINE, expectedDeadlineRemainder));

    }

}
