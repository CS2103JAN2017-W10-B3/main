package todolist.logic;

import static org.junit.Assert.assertEquals;
import static todolist.commons.core.Messages.MESSAGE_NO_TASK_SELECTED;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import todolist.logic.commands.CompleteCommand;
import todolist.model.ToDoList;
import todolist.model.task.Task;

//@@author A0122017Y
public class CompleteCommandTest extends LogicManagerTest {

    @Test
    public void executeAddInvalidArgsFormat() {
        String expectedMessage = MESSAGE_NO_TASK_SELECTED;
        assertCommandFailure("done", expectedMessage);
    }

    @Test
    //Check if the message shown is correct if index given is valid
    public void executeCompleteEventTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateEventTaskList(3);

        ToDoList expectedAB = helper.generateToDoList(threeTasks);
        helper.addToModel(model, threeTasks);
        Task toComplete = threeTasks.get(1);

        assertCommandSuccess("done e2",
                CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS + toComplete.getTitleFormattedString(),
                expectedAB,
                expectedAB.getCompletedTasks(), Task.COMPLETE_CHAR);
        assertEquals(model.getCompletedList().get(0), toComplete);
    }

    @Test
    //Check if the message shown is correct if index given is valid
    public void executeCompleteMultipleTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> eightTasks = helper.generateEventTaskList(8);

        ToDoList expectedAB = helper.generateToDoList(eightTasks);
        helper.addToModel(model, eightTasks);
        List<Task> toComplete = new ArrayList<Task>();
        toComplete.add(eightTasks.get(1));
        toComplete.add(eightTasks.get(2));
        toComplete.add(eightTasks.get(3));

        assertCommandSuccess("done e2-4",
                CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS + messageBuilder(toComplete),
                expectedAB,
                expectedAB.getCompletedTasks(), Task.COMPLETE_CHAR);
    }

    @Test
    //Check if the message shown is correct if index given is in inverted sequence
    public void executeCompleteMultipleTasksInverted() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> eightTasks = helper.generateEventTaskList(8);

        ToDoList expectedAB = helper.generateToDoList(eightTasks);
        helper.addToModel(model, eightTasks);
        List<Task> toComplete = new ArrayList<Task>();
        toComplete.add(eightTasks.get(1));
        toComplete.add(eightTasks.get(2));
        toComplete.add(eightTasks.get(3));

        assertCommandSuccess("done e4-2",
                CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS + messageBuilder(toComplete),
                expectedAB,
                expectedAB.getCompletedTasks(), Task.COMPLETE_CHAR);
    }

    @Test
    //Check if the message shown is correct if index given is valid
    public void executeCompleteMultipleTasksWithSingleIndex() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> eightTasks = helper.generateDeadlineTaskList(8);

        ToDoList expectedAB = helper.generateToDoList(eightTasks);
        helper.addToModel(model, eightTasks);
        List<Task> toComplete = new ArrayList<Task>();
        toComplete.add(eightTasks.get(1));
        toComplete.add(eightTasks.get(2));
        toComplete.add(eightTasks.get(3));

        assertCommandSuccess("done 2-4",
                CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS + messageBuilder(toComplete),
                expectedAB,
                expectedAB.getCompletedTasks(), Task.COMPLETE_CHAR);

        toComplete = new ArrayList<Task>();
        toComplete.add(eightTasks.get(4));
        toComplete.add(eightTasks.get(5));
        toComplete.add(eightTasks.get(6));
        assertCommandSuccess("done 4-2",
                CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS + messageBuilder(toComplete),
                expectedAB,
                expectedAB.getCompletedTasks(), Task.COMPLETE_CHAR);

    }

    private String messageBuilder(List<Task> toComplete) {
        StringBuilder sb = new StringBuilder();
        for (Task task : toComplete) {
            sb.append(task.getTitleFormattedString());
        }
        return sb.toString();
    }

    @Test
    //Check if the message shown is correct if index given is valid
    public void executeCompleteMixedList() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(helper.generateEventTask(1));
        tasks.add(helper.generateDeadlineTask(1));
        tasks.add(helper.generateFloatingTask(1));

        ToDoList expectedAB = helper.generateToDoList(tasks);
        helper.addToModel(model, tasks);
        Task toComplete = tasks.get(1);

        assertCommandSuccess("done 1",
                CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS + toComplete.getTitleFormattedString(),
                expectedAB,
                expectedAB.getCompletedTasks(), Task.COMPLETE_CHAR);
        assertEquals(model.getCompletedList().get(0), toComplete);

        toComplete = tasks.get(2);
        assertCommandSuccess("done f1",
            CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS + toComplete.getTitleFormattedString(),
            expectedAB,
            expectedAB.getCompletedTasks(), Task.COMPLETE_CHAR);
        assertEquals(model.getCompletedList().get(1), toComplete);

        toComplete = tasks.get(0);
        assertCommandSuccess("done e1",
            CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS + toComplete.getTitleFormattedString(),
            expectedAB,
            expectedAB.getCompletedTasks(), Task.COMPLETE_CHAR);
        assertEquals(model.getCompletedList().get(0), toComplete);
    }
}
