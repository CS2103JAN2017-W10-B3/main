package todolist.logic;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import todolist.logic.commands.DeleteCommand;
import todolist.model.ToDoList;
import todolist.model.task.Task;

public class DeleteCommandTest extends LogicManagerTest {
    
    public static final int INDEX_FIRST_TASK = 1;
    public static final String LINE_BREAK = "\n";
    
    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        ToDoList expectedAB = helper.generateToDoList(threeTasks);
        expectedAB.removeTask(threeTasks.get(INDEX_FIRST_TASK));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete e2",
                DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS + "[" + threeTasks.get(INDEX_FIRST_TASK).getTitle() + "] ",
                expectedAB,
                expectedAB.getFilteredEvents(), Task.EVENT_CHAR);
    }
    
}
