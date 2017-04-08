package todolist.model.util;

import todolist.commons.exceptions.IllegalValueException;
import todolist.model.ReadOnlyToDoList;
import todolist.model.ToDoList;
import todolist.model.tag.UniqueTagList;
import todolist.model.task.Description;
import todolist.model.task.EndTime;
import todolist.model.task.StartTime;
import todolist.model.task.Task;
import todolist.model.task.Title;
import todolist.model.task.UniqueTaskList.DuplicateTaskException;
import todolist.model.task.UrgencyLevel;
import todolist.model.task.Venue;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                //event task: 18
                new Task(new Title("CS2103 Tutorial"), new Venue("COM1-B103"),
                        new StartTime("April 19 9:00"), new EndTime("April 19 10:00"),
                        new UrgencyLevel("3"), new Description("Deadline of V0.6"), new UniqueTagList()),

                new Task(new Title("CS2100 Quiz"), new Venue("SR1"),
                        new StartTime("April 11 9:00"), new EndTime("April 11 10:00"),
                        new UrgencyLevel("3"), null, new UniqueTagList("exams")),

                new Task(new Title("GET1028 Quiz"), new Venue("LT9"),
                        new StartTime("April 12 9:00"), new EndTime("April 12 10:00"),
                        new UrgencyLevel("3"), new Description("10% of grade"), new UniqueTagList("exams")),

                new Task(new Title("FIN3101 Final"), new Venue("LT16"),
                        new StartTime("April 12 19:00"), new EndTime("April 12 21:00"),
                        new UrgencyLevel("3"), new Description("30% of grade"), new UniqueTagList("exams")),

                new Task(new Title("Charlie's birthday"), null,
                        new StartTime("April 13 19:00"), new EndTime("April 13 23:00"),
                        null, null, new UniqueTagList("friends")),

                new Task(new Title("BBQ Party"), new Venue("East coast park"),
                        new StartTime("April 14 9:00"), new EndTime("April 14 10:00"),
                        null, null, new UniqueTagList()),

                new Task(new Title("Dinner with Betty"), null,
                        new StartTime("April 15 21:00"), new EndTime("April 15 23:00"),
                        new UrgencyLevel("2"), new Description("Hang out for the first time"), new UniqueTagList()),

                new Task(new Title("Recruitment talk with Dell"), new Venue("MPSH"),
                        new StartTime("April 16 9:00"), new EndTime("April 16 10:00"),
                        new UrgencyLevel("2"), null, new UniqueTagList()),

                new Task(new Title("Project group meeting"), new Venue("COM1 basement"),
                        new StartTime("April 17 9:00"), new EndTime("April 17 10:00"),
                        null, null, new UniqueTagList()),

                new Task(new Title("CIP with Library"), null,
                        new StartTime("April 18 9:00"), new EndTime("April 18 12:00"),
                        null, null, new UniqueTagList()),

                new Task(new Title("Fire drill with PGP"), null,
                        new StartTime("April 20 3:00"), new EndTime("April 20 4:00"),
                        null, null, new UniqueTagList()),

                new Task(new Title("CCA meeting"), new Venue("COM1-B103"),
                        new StartTime("April 21 9:00"), new EndTime("April 21 10:00"),
                        null, new Description("Last meeting"), new UniqueTagList("CCA")),

                new Task(new Title("CS1010S Staff meeting"), new Venue("COM2-0226"),
                        new StartTime("April 22 9:00"), new EndTime("April 22 11:00"),
                        new UrgencyLevel("3"), null, new UniqueTagList("work")),

                new Task(new Title("Piano concert rehearsal"), new Venue("UCC hall"),
                        new StartTime("April 23 9:00"), new EndTime("April 23 22:00"),
                        new UrgencyLevel("3"), null, new UniqueTagList()),

                new Task(new Title("ST2132 exam"), new Venue("MPSH3"),
                        new StartTime("April 24 9:00"), new EndTime("April 24 11:00"),
                        new UrgencyLevel("3"), null, new UniqueTagList("exams")),

                new Task(new Title("CS1010S help session"), new Venue("COM1 SR2"),
                        new StartTime("April 24 15:00"), new EndTime("April 24 16:00"),
                        null, null, new UniqueTagList("job")),

                new Task(new Title("Chinese Drama at RJC"), new Venue("RJ auditorium"),
                        new StartTime("April 24 19:00"), new EndTime("April 24 21:00"),
                        null, null, new UniqueTagList("performances")),

                new Task(new Title("String conert at Esplanade"), null,
                        new StartTime("April 25 19:00"), new EndTime("April 25 20:00"),
                        null, null, new UniqueTagList()),

                //deadline tasks: 16
                new Task(new Title("CS2100 Report"), null,
                        null, new EndTime("April 12 23:59"),
                        null, new Description("I love CS2100"), new UniqueTagList("Assignment")),

                new Task(new Title("LAJ1201 Essay"), null,
                        null, new EndTime("April 13 10:00"),
                        new UrgencyLevel("2"), null, new UniqueTagList("Assignment")),

                new Task(new Title("GET1006 Essay"), null,
                        null, new EndTime("April 14 23:59"),
                        new UrgencyLevel("2"), null, new UniqueTagList("Assignment")),

                new Task(new Title("Submit claim form to PGP office"), null,
                        null, new EndTime("April 15 10:00"),
                        new UrgencyLevel("2"), new Description("Claim money"), new UniqueTagList()),

                new Task(new Title("Case report"), null,
                        null, new EndTime("April 16 11:00"),
                        null, null, new UniqueTagList("Assignment")),

                new Task(new Title("Reply Amazon's offer"), null,
                        null, new EndTime("April 17 15:00"),
                        new UrgencyLevel("2"), null, new UniqueTagList("job")),

                new Task(new Title("fix bug in web app"), null,
                        null, new EndTime("April 18 18:00"),
                        new UrgencyLevel("2"), null, new UniqueTagList("job")),

                new Task(new Title("finish marking assignments"), null,
                        null, new EndTime("April 19 10:00"),
                        new UrgencyLevel("2"), null, new UniqueTagList("job")),

                new Task(new Title("Clear all pending comments"), null,
                        null, new EndTime("April 20 18:00"),
                        null, null, new UniqueTagList("job")),

                new Task(new Title("Fill up module survey"), null,
                        null, new EndTime("April 21 22:00"),
                        null, null, new UniqueTagList("school")),

                new Task(new Title("Internship report"), null,
                        null, new EndTime("April 22 10:00"),
                        null, null, new UniqueTagList("job")),

                new Task(new Title("Java assignment"), null,
                        null, new EndTime("April 23 15:00"),
                        null, null, new UniqueTagList()),

                new Task(new Title("Japanese language school signing up"), null,
                        null, new EndTime("April 24 19:00"),
                        new UrgencyLevel("2"), null, new UniqueTagList()),

                new Task(new Title("SEP module mapping submission"), null,
                        null, new EndTime("April 24 16:00"),
                        null, null, new UniqueTagList("school")),

                new Task(new Title("Design project initial idea"), null,
                        null, new EndTime("April 25 17:35"),
                        new UrgencyLevel("1"), null, new UniqueTagList()),

                new Task(new Title("Investment Society year book article submission"), null,
                        null, new EndTime("April 26 20:00"),
                        null, null, new UniqueTagList()),

                //floating tasks:
                new Task(new Title("Do laundry"), null, null, null,
                        null, null, new UniqueTagList()),

                new Task(new Title("Do Tutorials"), null, null, null,
                        new UrgencyLevel("2"), null, new UniqueTagList()),

                new Task(new Title("Call grandma"), null, null, null, null,
                        new Description("Interview her for project about aging population"), new UniqueTagList()),

                new Task(new Title("buy new book"), new Venue("popular bookstore at Clementi mall"), null, null,
                        null, null, new UniqueTagList()),

                new Task(new Title("Buy Radwimps concert tickets"), new Venue("online"),
                        new StartTime("April 13"), null, null, null, new UniqueTagList("ticket")),

                new Task(new Title("Get new light bulb changed"), null, null, null,
                        null, null, new UniqueTagList()),

                new Task(new Title("Buy a new belt"), new Venue("Uniqlo at Kent Ridge"), null, null,
                        null, null, new UniqueTagList()),

                new Task(new Title("Eat an apple"), null, null, null,
                        null, null, new UniqueTagList()),

                new Task(new Title("Go eat SAKURA sushi"), null, null, null,
                        null, null, new UniqueTagList()),

                new Task(new Title("Try new outlets of Sushi express"), null, null, null,
                        null, new Description("Opening soon at Kent Ridge"), new UniqueTagList()),

                new Task(new Title("Fix bugs in to-do list"), null, null, null,
                        new UrgencyLevel("3"), null, new UniqueTagList()),

                new Task(new Title("Write test codes"), null, null, null,
                        new UrgencyLevel("3"), null, new UniqueTagList()),

                new Task(new Title("Clear GUI tests"), null, null, null,
                        new UrgencyLevel("3"), null, new UniqueTagList()),

                new Task(new Title("Print new notes"), null, new StartTime("April 20 10:00"), null,
                        new UrgencyLevel("2"), null, new UniqueTagList()),

                new Task(new Title("Ride bicycle"), new Venue("Westcoast Park"), null, null,
                        null, null, new UniqueTagList()),

                new Task(new Title("Visit Uncle"), null, null, null,
                        new UrgencyLevel("1"), new Description("Get Ang pau"), new UniqueTagList()),

                };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyToDoList getSampleToDoList() {
        try {
            ToDoList sampleAB = new ToDoList();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
