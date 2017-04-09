package todolist.logic;

import org.junit.Test;

import todolist.commons.core.Messages;
import todolist.logic.commands.ImportCommand;
import todolist.logic.commands.SaveCommand;
import todolist.model.ToDoList;
import todolist.model.task.Task;
//@@A0122017Y
public class SaveCommandTest extends LogicManagerTest {

    @Test
    public void executeSave() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        ToDoList expected = helper.generateToDoList(5);
        model.resetData(expected);

        // save to a wrong file path
        String wrongSaveCommand = "save thisisobviouslynotadirectory";
        String expectedWrongOutput = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                SaveCommand.MESSAGE_USAGE);
        assertCommandFailure(wrongSaveCommand, expectedWrongOutput);

        // save to the default file path
        String expectedResult = String.format(SaveCommand.MESSAGE_SUCCESS,
                SaveCommand.getToDoListFilePath());
        assertCommandSuccess("save", expectedResult, expected, expected.getTaskList(), Task.ALL_CHAR);
        //model.resetData(new ToDoList());

        //import from the default file path
        String importCommand = "import " + SaveCommand.getToDoListFilePath();
        String expectedImportSuccess = ImportCommand.MESSAGE_SUCCESS
                .concat(SaveCommand.getToDoListFilePath());
        //import from the wrong file path
        assertCommandFailure("import thisisanotherobviouslywrongdirectory", "File does not exist.");
        //import from the correct file path
        assertCommandSuccess(importCommand, expectedImportSuccess,
                expected, expected.getTaskList(), Task.ALL_CHAR);
    }

}
