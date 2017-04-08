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
                    .withStartTime("Tuesday 10:00").withEndTime("Tuesday 11:00")
                    .withTags("lesson").withUrgencyLevel("3").withDescription("I love you").build();
            dbsInterview = new TaskBuilder().withTitle("DBS Internship interview").withVenue("Raffles Place")
                    .withStartTime("March 31, 9:30").withEndTime("March 31, 11:30")
                    .withTags("interview", "internship", "important").withUrgencyLevel("3")
                    .withDescription("I love you").build();
            hangOutJoe = new TaskBuilder().withTitle("Hang out with Joe").withVenue("313 Somerset")
                    .withStartTime("Saturday 17:00").withEndTime("Saturday 21:00")
                    .withUrgencyLevel("2").withDescription("I love you").build();
            statsSoc = new TaskBuilder().withTitle("Statistics society meeting").withVenue("S16 04-30")
                    .withStartTime("Wednesday 19:00").withEndTime("Wednesday 21:00")
                    .withUrgencyLevel("2").withDescription("I love you").build();
            tuitionPartTime = new TaskBuilder().withTitle("Tuition part-time job")
                    .withVenue("Jun Wei's house at Jurong Ease Avenue 1")
                    .withStartTime("Next Thursday 19:00").withEndTime("Next Thursday 21:00")
                    .withUrgencyLevel("2").withDescription("I love you").build();
            stringsRehearsal = new TaskBuilder().withTitle("Strings ensemble rehearsal").withVenue("UCC hall")
                    .withStartTime("Friday 9:00").withEndTime("Friday 18:00")
                    .withUrgencyLevel("1").withDescription("I love you").build();
            dinnerAuntie = new TaskBuilder().withTitle("Dinner with auntie").withVenue("Home")
                    .withStartTime("Friday 19:00").withEndTime("Friday 20:00")
                    .withUrgencyLevel("").withDescription("I love you").build();
            // Manually added
            ma3269Quiz = new TaskBuilder().withTitle("MA3269 Quiz").withVenue("LT26")
                    .withStartTime("Thursday 12:00").withEndTime("Thursday 14:00")
                    .withUrgencyLevel("1").withDescription("I love you").build();
            laundry = new TaskBuilder().withTitle("Do laundry").withVenue("Hostel")
                    .withStartTime("now").withEndTime("1 hour later chores")
                    .withUrgencyLevel("1").withDescription("I love you").build();

            //deadlines
            cs2103Submission = new TaskBuilder().withTitle("CS2103 Tutorial").withVenue("COM1-B103")
                    .withEndTime("Tuesday 11:00").withTags("lesson").withUrgencyLevel("3")
                    .withDescription("Submit jar file + all the documents.").build();
            cs2103Demo = new TaskBuilder().withTitle("CS2103 Project Demo").withVenue("COM1-B103")
                    .withEndTime("Wednesday 10:00").withTags("lesson").withDescription("12min presentation").build();
            fypPresentation = new TaskBuilder().withTitle("FYP Presentation").withVenue("EA-06-06")
                     .withEndTime("Wednesday 13:00").withTags("FYP").withDescription("FINAL PRESENTATION").build();
            fypFinalSubmission = new TaskBuilder().withTitle("FYP Online Submission").withVenue("Online")
                     .withEndTime("13 May 12:00").withTags("FYP").withDescription("Submit to online portal.").build();
            // Manually added
            applyInternship = new TaskBuilder().withTitle("Apply for Internship").withUrgencyLevel("2").withVenue("Online")
                     .withEndTime("Friday 12:00").withTags("internship").withDescription("Strict deadline!").build();
            cs2010PS6 = new TaskBuilder().withTitle("CS2010 PS6").withVenue("Online")
                     .withEndTime("17 April 12:00").withTags("cs2010").withDescription("Save 1 day to do this.").build();

            //floats
            planGradTrip = new TaskBuilder().withTitle("Plan for Grad Trip").withTags("gradtrip").withVenue("Online")
                    .withDescription("Decide when and where.").build();
            learnJava = new TaskBuilder().withTitle("Learn Java").withTags("java").withVenue("Online")
                    .withDescription("Refer to photos gallery on phone for resources.").build();
            buyGroceries = new TaskBuilder().withTitle("Buy groceries").withTags("shopping").withVenue("NTUC")
                     .withDescription("Buy cheddar and mozarella cheese, pepper and milk.").build();
            goGym = new TaskBuilder().withTitle("Go gym").withTags("gym").withVenue("Gym")
                     .withDescription("Push, pull, legs!").build();
            // Manually added
            cleanMyRoom = new TaskBuilder().withTitle("Clean my room").withTags("chores").withVenue("my room")
                     .withDescription("Sweep").build();
            chaseAfterDebts = new TaskBuilder().withTitle("Chase after debts").withTags("random").withVenue("Online")
                     .withDescription("Johan still owes me $1000.").build();

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
                                              fypFinalSubmission, applyInternship};
        Arrays.sort(testTasks, ReadOnlyTask.getDeadlineComparator());
        return testTasks;
    }

    public TestTask[] getTypicalFloatingTasks() {
        TestTask[] testTasks = new TestTask[]{planGradTrip, learnJava, buyGroceries, goGym, cleanMyRoom};
        Arrays.sort(testTasks, ReadOnlyTask.getFloatingComparator());
        return testTasks;
    }

    public ToDoList getTypicalTaskList() {
        ToDoList ab = new ToDoList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}
