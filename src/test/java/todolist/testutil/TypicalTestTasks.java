package todolist.testutil;

import java.util.Arrays;

import todolist.commons.exceptions.IllegalValueException;
import todolist.model.ToDoList;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.Task;
import todolist.model.task.UniqueTaskList;

//@@author A0110791M
/**
 * Utility class to generate test tasks for testing.
 */
public class TypicalTestTasks {

    //events
    public TestTask cs2103Tutorial, dbsInterview, hangOutJoe, statsSoc, tuitionPartTime,
        stringsRehearsal, dinnerAuntie, ma3269Quiz, laundry;
    //deadlines
    public TestTask cs2103Submission, cs2103Demo, fypPresentation, fypFinalSubmission, applyInternship, cs2010PS6;
    //floats
    public TestTask planGradTrip, learnJava, buyGroceries, goGym, cleanMyRoom, chaseAfterDebts;

    public TypicalTestTasks() {
        try {
            //events
            cs2103Tutorial = new TaskBuilder().withTitle("CS2103 Tutorial").withVenue("COM1-B103")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withTags("lesson").withUrgencyLevel("3").withDescription("I love you")
                    .withCompleteStatus("false").build();
            dbsInterview = new TaskBuilder().withTitle("DBS Internship interview").withVenue("Raffles Place")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withTags("interview", "internship", "important").withUrgencyLevel("3")
                    .withDescription("I love you").withCompleteStatus("false").build();
            hangOutJoe = new TaskBuilder().withTitle("Hang out with Joe").withVenue("313 Somerset")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("1").withDescription("I love you")
                    .withCompleteStatus("false").build();
            statsSoc = new TaskBuilder().withTitle("Statistics society meeting").withVenue("S16 04-30")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("2").withDescription("I love you")
                    .withCompleteStatus("false").build();
            tuitionPartTime = new TaskBuilder().withTitle("Tuition part-time job")
                    .withVenue("Jun Wei's house at Jurong Ease Avenue 1")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("1").withDescription("I love you")
                    .withCompleteStatus("false").build();
            stringsRehearsal = new TaskBuilder().withTitle("Strings ensemble rehearsal").withVenue("UCC hall")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("2").withDescription("I love you")
                    .withCompleteStatus("false").build();
            dinnerAuntie = new TaskBuilder().withTitle("Dinner with auntie").withVenue("Home")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("2").withDescription("I love you")
                    .withCompleteStatus("false").build();

            // Manually added
            ma3269Quiz = new TaskBuilder().withTitle("MA3269 Quiz").withVenue("LT26")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("1").withDescription("I love you")
                    .withCompleteStatus("false").build();
            laundry = new TaskBuilder().withTitle("Do laundry").withVenue("Hostel")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("1").withDescription("I love you")
                    .withCompleteStatus("false").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(ToDoList ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalEventTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }

        for (TestTask task : new TypicalTestTasks().getTypicalDeadlineTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }

        for (TestTask task : new TypicalTestTasks().getTypicalFloatingTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }


    public TestTask[] getTypicalEventTasks() {
        TestTask[] testTasks = new TestTask[]{cs2103Tutorial, dbsInterview, hangOutJoe, statsSoc,
                                              tuitionPartTime, stringsRehearsal, dinnerAuntie};
        Arrays.sort(testTasks, ReadOnlyTask.getEventComparator());
        return testTasks;
    }

    public TestTask[] getTypicalDeadlineTasks() {
        TestTask[] testTasks = new TestTask[]{cs2103Submission, cs2103Demo, fypPresentation,
                                              fypFinalSubmission};
        Arrays.sort(testTasks, ReadOnlyTask.getDeadlineComparator());
        return testTasks;
    }

    public TestTask[] getTypicalFloatingTasks() {
        TestTask[] testTasks = new TestTask[]{buyGroceries, goGym, learnJava, planGradTrip};
        Arrays.sort(testTasks, ReadOnlyTask.getFloatingComparator());
        return testTasks;
    }

    public ToDoList getTypicalTaskList() {
        ToDoList ab = new ToDoList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}
