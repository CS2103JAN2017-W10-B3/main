package todolist.logic;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import todolist.logic.commands.Command;
import todolist.logic.commands.FindCommand;
import todolist.model.ToDoList;
import todolist.model.task.Task;

public class FindCommandTest extends LogicManagerTest {
    
    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInTitles() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateEventTaskWithTitle("bla bla KEY bla");
        Task pTarget2 = helper.generateEventTaskWithTitle("bla KEY bla bceofeia");
        Task p1 = helper.generateEventTaskWithTitle("KE Y");
        Task p2 = helper.generateEventTaskWithTitle("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        ToDoList expectedAB = helper.generateToDoList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList, Task.EVENT_CHAR);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateEventTaskWithTitle("bla bla KEY bla");
        Task p2 = helper.generateEventTaskWithTitle("bla KEY bla bceofeia");
        Task p3 = helper.generateEventTaskWithTitle("key key");
        Task p4 = helper.generateEventTaskWithTitle("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        ToDoList expectedAB = helper.generateToDoList(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList, Task.EVENT_CHAR);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateEventTaskWithTitle("bla bla KEY bla");
        Task pTarget2 = helper.generateEventTaskWithTitle("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateEventTaskWithTitle("key key");
        Task p1 = helper.generateEventTaskWithTitle("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        ToDoList expectedAB = helper.generateToDoList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList, Task.EVENT_CHAR);
    }
    
}
