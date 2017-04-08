package todolist.logic;

import java.util.List;

import org.junit.Test;

import todolist.commons.core.Messages;
import todolist.logic.commands.DeleteCommand;
import todolist.model.ToDoList;
import todolist.model.task.Task;

public class DeleteCommandTest extends LogicManagerTest {

    public static final int INDEX_FIRST_TASK = 1;
    public static final String LINE_BREAK = "\n";

    @Test
    public void executeDeleteInvalidArgsFormatErrorMessageShown() throws Exception {
        String expectedMessageNoIndex = Messages.MESSAGE_NO_TASK_SELECTED;
        String expectedMessageWrongIndex =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertNoIndexSelectedBehaviorForCommand("delete", expectedMessageNoIndex);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessageWrongIndex);
    }

    @Test
    public void executeDeleteRemovesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateEventTaskList(3);

        ToDoList expectedAB = helper.generateToDoList(threeTasks);
        expectedAB.removeTask(threeTasks.get(INDEX_FIRST_TASK));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete e2",
                DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS + "[" + threeTasks.get(INDEX_FIRST_TASK).getTitle() + "] ",
                expectedAB,
                expectedAB.getFilteredEvents(), Task.EVENT_CHAR);
    }

}
