package todolist.logic;

import java.util.ArrayList;

import todolist.logic.commands.ClearCommand;
import todolist.logic.commands.ImportCommand;
import todolist.logic.commands.SaveCommand;
import todolist.model.ToDoList;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.Task;

public class SaveCommandTest extends LogicManagerTest {

    public void executeSave() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        ToDoList expected = helper.generateToDoList(5);
        model.resetData(expected);

        // save to a wrong file path
        String wrongSaveCommand = "save thisisobviouslynotadirectory";
        assertCommandFailure(wrongSaveCommand , SaveCommand.MESSAGE_INVALID_PATH);

        // save to the default file path
        String expectedResult = String.format(SaveCommand.MESSAGE_SUCCESS,
                SaveCommand.getUserPrefsFilePath());
        assertCommandSuccess("save", expectedResult, expected, expected.getTaskList(), Task.ALL_CHAR);
        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS,
                new ToDoList(), new ArrayList<ReadOnlyTask>(), Task.ALL_CHAR);

        //import from the default file path
        String importCommand = "import " + SaveCommand.getUserPrefsFilePath();
        String expectedImportSuccess = ImportCommand.MESSAGE_SUCCESS
                .concat(SaveCommand.getUserPrefsFilePath());
        //import from the wrong file path
        assertCommandFailure("import thisisanotherobviouslywrongdirectory", ImportCommand.MESSAGE_FAILURE);
        //import from the correct file path
        assertCommandSuccess(importCommand, expectedImportSuccess,
                expected, expected.getTaskList(), Task.ALL_CHAR);
    }

}
