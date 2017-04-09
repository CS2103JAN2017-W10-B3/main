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

    // events
    public TestTask cs2103Tutorial, dbsInterview, hangOutJoe, statsSoc, tuitionPartTime, stringsRehearsal, dinnerAuntie,
            ma3269Quiz, laundry;
    // deadlines
    public TestTask cs2103Submission, cs2103Demo, fypPresentation, fypFinalSubmission, applyInternship, cs2010PS6;
    // floats
    public TestTask planGradTrip, learnJava, buyGroceries, goGym, cleanMyRoom, chaseAfterDebts;

    public TypicalTestTasks() {
        try {
            // events
            cs2103Tutorial = new TaskBuilder().withTitle("CS2103 Tutorial").withVenue("COM1-B103")
                    .withStartTime("May 30, 10:00").withEndTime("May 30, 11:00").withTags("lesson")
                    .withUrgencyLevel("3").withDescription("I love you").withCompleteStatus("false").build();
            dbsInterview = new TaskBuilder().withTitle("DBS Internship interview").withVenue("Raffles Place")
                    .withStartTime("May 31, 9:30").withEndTime("May 31, 11:30")
                    .withTags("interview", "internship", "important").withUrgencyLevel("3")
                    .withDescription("I love you").withCompleteStatus("false").build();
            hangOutJoe = new TaskBuilder().withTitle("Hang out with Joe").withVenue("313 Somerset")
                    .withStartTime("June 1, 17:00").withEndTime("June 1, 21:00").withUrgencyLevel("2")
                    .withDescription("I love you").withCompleteStatus("false").build();
            statsSoc = new TaskBuilder().withTitle("Statistics society meeting").withVenue("S16 04-30")
                    .withStartTime("June 2, 19:00").withEndTime("June 2, 21:00").withUrgencyLevel("2")
                    .withDescription("I love you").withCompleteStatus("false").build();
            tuitionPartTime = new TaskBuilder().withTitle("Tuition part-time job")
                    .withVenue("Jun Wei's house at Jurong Ease Avenue 1").withStartTime("June 3, 19:00")
                    .withEndTime("June 3, 21:00").withUrgencyLevel("2").withDescription("I love you")
                    .withCompleteStatus("false").build();
            stringsRehearsal = new TaskBuilder().withTitle("Strings ensemble rehearsal").withVenue("UCC hall")
                    .withStartTime("June 4, 9:00").withEndTime("June 4, 18:00").withUrgencyLevel("1")
                    .withDescription("I love you").withCompleteStatus("false").build();
            dinnerAuntie = new TaskBuilder().withTitle("Dinner with auntie").withVenue("Home")
                    .withStartTime("June 5, 19:00").withEndTime("June 5, 20:00").withUrgencyLevel("")
                    .withDescription("I love you").withCompleteStatus("false").build();
            // Manually added
            ma3269Quiz = new TaskBuilder().withTitle("MA3269 Quiz").withVenue("LT26").withStartTime("June 6, 12:00")
                    .withEndTime("June 6, 13:00").withUrgencyLevel("1").withDescription("I love you")
                    .withCompleteStatus("false").build();
            laundry = new TaskBuilder().withTitle("Do laundry").withVenue("Hostel").withStartTime("now")
                    .withEndTime("1 hour later chores").withUrgencyLevel("1").withDescription("I love you")
                    .withCompleteStatus("false").build();

            // deadlines
            cs2103Submission = new TaskBuilder().withTitle("CS2103 Tutorial").withVenue("COM1-B103")
                    .withEndTime("June 1, 11:00").withTags("lesson").withUrgencyLevel("3")
                    .withDescription("Submit jar file + all the documents.").withCompleteStatus("false").build();
            cs2103Demo = new TaskBuilder().withTitle("CS2103 Project Demo").withVenue("COM1-B103")
                    .withEndTime("June 2, 10:00").withTags("lesson").withDescription("12min presentation")
                    .withCompleteStatus("false").build();
            fypPresentation = new TaskBuilder().withTitle("FYP Presentation").withVenue("EA-06-06")
                    .withEndTime("June 3, 13:00").withTags("FYP")
                    .withDescription("FINAL PRESENTATION").withCompleteStatus("false").build();
            fypFinalSubmission = new TaskBuilder().withTitle("FYP Online Submission").withVenue("Online")
                    .withEndTime("June 4, 12:00").withTags("FYP")
                    .withDescription("Submit to online portal.").withCompleteStatus("false").build();

            // Manually added
            applyInternship = new TaskBuilder().withTitle("Apply for Internship").withUrgencyLevel("2")
                    .withVenue("Online").withEndTime("June 5, 12:00").withTags("internship")
                    .withDescription("Strict deadline!").withCompleteStatus("false").build();
            cs2010PS6 = new TaskBuilder().withTitle("CS2010 PS6").withVenue("Online").withEndTime("17 April 12:00")
                    .withTags("cs2010").withDescription("Save 1 day to do this.").withCompleteStatus("false").build();

            // floats
            planGradTrip = new TaskBuilder().withTitle("Plan for Grad Trip").withTags("gradtrip").withVenue("Online")
                    .withUrgencyLevel("1").withDescription("Decide when and where.").withCompleteStatus("false")
                    .build();
            learnJava = new TaskBuilder().withTitle("Learn Java").withTags("java").withVenue("Online")
                    .withUrgencyLevel("1").withDescription("Refer to photos gallery on phone for resources.")
                    .withCompleteStatus("false").build();
            buyGroceries = new TaskBuilder().withTitle("Buy groceries").withTags("shopping").withVenue("NTUC")
                    .withUrgencyLevel("2").withDescription("Buy cheese, pepper and milk.").withCompleteStatus("false")
                    .build();
            goGym = new TaskBuilder().withTitle("Go gym").withTags("gym").withVenue("Gym").withUrgencyLevel("1")
                    .withDescription("Push, pull, legs!").withCompleteStatus("false").build();
            // Manually added
            cleanMyRoom = new TaskBuilder().withTitle("Clean my room").withTags("chores").withVenue("my room")
                    .withDescription("Sweep").withCompleteStatus("false").build();
            chaseAfterDebts = new TaskBuilder().withTitle("Chase after debts").withTags("random").withVenue("Online")
                    .withDescription("Johan still owes me $1000.").withCompleteStatus("false").build();

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
        TestTask[] testTasks = new TestTask[] { cs2103Tutorial, dbsInterview, hangOutJoe, statsSoc, tuitionPartTime,
            stringsRehearsal, dinnerAuntie };
        Arrays.sort(testTasks, ReadOnlyTask.getEventComparator());
        return testTasks;
    }

    public TestTask[] getTypicalDeadlineTasks() {
        TestTask[] testTasks = new TestTask[] { cs2103Submission, cs2103Demo, fypPresentation, fypFinalSubmission };
        Arrays.sort(testTasks, ReadOnlyTask.getDeadlineComparator());
        return testTasks;
    }

    public TestTask[] getTypicalFloatingTasks() {
        TestTask[] testTasks = new TestTask[] { buyGroceries, goGym, learnJava, planGradTrip };
        Arrays.sort(testTasks, ReadOnlyTask.getFloatingComparator());
        return testTasks;
    }

    public ToDoList getTypicalTaskList() {
        ToDoList ab = new ToDoList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}
