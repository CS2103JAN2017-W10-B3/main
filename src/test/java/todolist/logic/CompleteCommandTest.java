package todolist.logic;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import todolist.logic.commands.CompleteCommand;
import todolist.model.ToDoList;
import todolist.model.task.Task;

public class CompleteCommandTest extends LogicManagerTest {
    @Test
    //Check if the message shown is correct if index given is valid
    public void executeCompleteTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        ToDoList expectedAB = helper.generateToDoList(threeTasks);
        helper.addToModel(model, threeTasks);
        Task toComplete = threeTasks.get(1);

        assertCommandSuccess("done e2",
                CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS + " " + toComplete.getTitle(),
                expectedAB,
                expectedAB.getCompletedTasks(), Task.COMPLETE_CHAR);
        assertEquals(model.getCompletedList().get(0), toComplete);
    }
}
