package todolist.testutil;

import java.util.Optional;

import todolist.commons.exceptions.IllegalValueException;
import todolist.model.ToDoList;
import todolist.model.task.Task;
import todolist.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask cs2103Tutorial, dbsInterview, hangOutJoe, statsSoc, tuitionPartTime,
        stringsRehearsal, dinnerAuntie, ma3269Quiz, laundry;

    public TypicalTestTasks() {
        try {
            cs2103Tutorial = new TaskBuilder().withTitle("CS2103 Tutorial").withVenue("COM1-B103")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withTags("lesson").withUrgencyLevel("3").withDescription("I love you")
                    .withCompleteStatus("false").withCompleteTime(Optional.empty()).build();
            dbsInterview = new TaskBuilder().withTitle("DBS Internship interview").withVenue("Raffles Place")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withTags("interview", "internship", "important").withUrgencyLevel("3")
                    .withDescription("I love you").withCompleteStatus("false").withCompleteTime(Optional.empty()).build();
            hangOutJoe = new TaskBuilder().withTitle("Hang out with Joe").withVenue("313 Somerset")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("1").withDescription("I love you")
                    .withCompleteStatus("false").withCompleteTime(Optional.empty()).build();
            statsSoc = new TaskBuilder().withTitle("Statistics society meeting").withVenue("S16 04-30")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("2").withDescription("I love you")
                    .withCompleteStatus("false").withCompleteTime(Optional.empty()).build();
            tuitionPartTime = new TaskBuilder().withTitle("Tuition part-time job")
                    .withVenue("Jun Wei's house at Jurong Ease Avenue 1")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("1").withDescription("I love you")
                    .withCompleteStatus("false").withCompleteTime(Optional.empty()).build();
            stringsRehearsal = new TaskBuilder().withTitle("Strings ensemble rehearsal").withVenue("UCC hall")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("2").withDescription("I love you")
                    .withCompleteStatus("false").withCompleteTime(Optional.empty()).build();
            dinnerAuntie = new TaskBuilder().withTitle("Dinner with auntie").withVenue("Home")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("2").withDescription("I love you")
                    .withCompleteStatus("false").withCompleteTime(Optional.empty()).build();

            // Manually added
            ma3269Quiz = new TaskBuilder().withTitle("MA3269 Quiz").withVenue("LT26")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("1").withDescription("I love you")
                    .withCompleteStatus("false").withCompleteTime(Optional.empty()).build();
            laundry = new TaskBuilder().withTitle("Do laundry").withVenue("Hostel")
                    .withStartTime("Today").withEndTime("Tomorrow")
                    .withUrgencyLevel("1").withDescription("I love you")
                    .withCompleteStatus("false").withCompleteTime(Optional.empty()).build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadToDoListWithSampleData(ToDoList ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{cs2103Tutorial, dbsInterview, hangOutJoe, statsSoc,
            tuitionPartTime, stringsRehearsal, dinnerAuntie};
    }

    public ToDoList getTypicalToDoList() {
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
