package todolist.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static todolist.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static todolist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import todolist.commons.core.EventsCenter;
import todolist.commons.events.model.ToDoListChangedEvent;
import todolist.commons.events.ui.JumpToListRequestEvent;
import todolist.commons.events.ui.ShowHelpRequestEvent;
import todolist.commons.exceptions.IllegalValueException;
import todolist.logic.commands.ClearCommand;
import todolist.logic.commands.CommandResult;
import todolist.logic.commands.ExitCommand;
import todolist.logic.commands.FindCommand;
import todolist.logic.commands.HelpCommand;
import todolist.logic.commands.SelectCommand;
import todolist.logic.commands.exceptions.CommandException;
import todolist.model.Model;
import todolist.model.ModelManager;
import todolist.model.ReadOnlyToDoList;
import todolist.model.ToDoList;
import todolist.model.tag.Tag;
import todolist.model.tag.UniqueTagList;
import todolist.model.task.Description;
import todolist.model.task.EndTime;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.StartTime;
import todolist.model.task.Task;
import todolist.model.task.Title;
import todolist.model.task.UrgencyLevel;
import todolist.model.task.Venue;
import todolist.storage.StorageManager;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    protected Model model;
    protected Logic logic;

    // These are for checking the correctness of the events raised
    protected ReadOnlyToDoList latestSavedToDoList;
    protected boolean helpShown;
    protected int targetedJumpIndex;

    @Subscribe
    protected void handleLocalModelChangedEvent(ToDoListChangedEvent abce) {
        latestSavedToDoList = new ToDoList(abce.data);
    }

    @Subscribe
    protected void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    protected void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex.getTaskNumber();
    }

    @Before
    public void setUp() {
        model = new ModelManager();
        String tempToDoListFile = saveFolder.getRoot().getPath() + "TempToDoList.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempToDoListFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedToDoList = new ToDoList(model.getToDoList()); // last saved
                                                                 // assumed to
                                                                 // be up to
                                                                 // date
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void tearDown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() {
        String invalidCommand = "       ";
        assertCommandFailure(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command, confirms that a CommandException is not thrown and
     * that the result message is correct.
     * Also confirms that both the 'address book' and the 'last shown list' are
     * as specified.
     *
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyToDoList,
     *      List)
     */
    protected void assertCommandSuccess(String inputCommand, String expectedMessage,
            ReadOnlyToDoList expectedToDoList,
            List<? extends ReadOnlyTask> expectedShownList, Character taskChar) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedToDoList, expectedShownList, taskChar);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that
     * the result message is correct.
     * Both the 'address book' and the 'last shown list' are verified to be
     * unchanged.
     *
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyToDoList,
     *      List)
     */
    protected void assertCommandFailure(String inputCommand, String expectedMessage) {
        ToDoList expectedToDoList = new ToDoList(model.getToDoList());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getAllTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedToDoList, expectedShownList, Task.ALL_CHAR);
    }

    /**
     * Executes the command, confirms that the result message is correct
     * and that a CommandException is thrown if expected
     * and also confirms that the following three parts of the LogicManager
     * object's state are as expected:<br>
     * - the internal address book data are same as those in the
     * {@code expectedToDoList} <br>
     * - the backing list shown by UI matches the {@code shownList} <br>
     * - {@code expectedToDoList} was saved to the storage file. <br>
     */
    protected void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
            ReadOnlyToDoList expectedToDoList,
            List<? extends ReadOnlyTask> expectedShownList, Character taskChar) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, e.getMessage());
        }
        
        // Confirm the state of data given by model is as expected
        assertEquals(expectedShownList, model.getListFromChar(taskChar));
        
        // Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedToDoList, model.getToDoList());
        assertEquals(expectedToDoList, latestSavedToDoList);
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new ToDoList(), Collections.emptyList(), Task.ALL_CHAR);
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new ToDoList(), Collections.emptyList(), Task.ALL_CHAR);
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new ToDoList(), Collections.emptyList(), Task.ALL_CHAR);
    }



    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        ToDoList expectedAB = helper.generateToDoList(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare to-do list state
        helper.addToModel(model, 2);

//        assertCommandSuccess("list",
//                ListCommand.MESSAGE_SUCCESS,
//                expectedAB,
//                expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command
     * targeting a single task in the shown list, using visible index.
     *
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list
     *            based on visible index.
     */
    protected void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
            throws Exception {
        assertCommandFailure(commandWord, expectedMessage); // index missing
        assertCommandFailure(commandWord + " +1", expectedMessage); // index should be unsigned
        assertCommandFailure(commandWord + " -1", expectedMessage); // index should be unsigned
        assertCommandFailure(commandWord + " 0", expectedMessage); // index cannot be 0
        assertCommandFailure(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command
     * targeting a single task in the shown list, using visible index.
     *
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list
     *            based on visible index.
     */
    protected void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new ToDoList());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandFailure(commandWord + " 3", expectedMessage);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        ToDoList expectedAB = helper.generateToDoList(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("select f2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList(), Task.ALL_CHAR);
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredDeadlineList().get(1), threeTasks.get(1));
    }

    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInTitles() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithTitle("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithTitle("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithTitle("KE Y");
        Task p2 = helper.generateTaskWithTitle("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        ToDoList expectedAB = helper.generateToDoList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

//        assertCommandSuccess("find KEY",
//                Command.getMessageForTaskListShownSummary(expectedList.size()),
//                expectedAB,
//                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithTitle("bla bla KEY bla");
        Task p2 = helper.generateTaskWithTitle("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithTitle("key key");
        Task p4 = helper.generateTaskWithTitle("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        ToDoList expectedAB = helper.generateToDoList(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

//        //assertCommandSuccess("find KEY",
//                Command.getMessageForTaskListShownSummary(expectedList.size()),
//                expectedAB,
//                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithTitle("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithTitle("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithTitle("key key");
        Task p1 = helper.generateTaskWithTitle("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        ToDoList expectedAB = helper.generateToDoList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

//        //assertCommandSuccess("find key rAnDoM",
//                Command.getMessageForTaskListShownSummary(expectedList.size()),
//                expectedAB,
//                expectedList);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {
        
        Title name;
        Venue privateVenue;
        StartTime privateStartTime;
        EndTime privateEndTime;
        UrgencyLevel privateUrgencyLevel;
        Description privateDescription;
        UniqueTagList tags;
        
        public TestDataHelper() throws IllegalValueException {
            name = new Title("CS2103");
            privateVenue = new Venue("COM1 B103");
            privateStartTime = new StartTime("Today 11am");
            privateEndTime = new EndTime("Today 12pm");
            privateUrgencyLevel = new UrgencyLevel("3");
            privateDescription = new Description("I love 2103!!!");
            tags = new UniqueTagList(new Tag("tag1"), new Tag("longertag2"));
        }

        Task cs2103() throws Exception {
            return new Task(name, privateVenue, null, privateEndTime,
                    privateUrgencyLevel, privateDescription, tags);
        }
        
        Task cs2103Deadline() throws Exception {
            name = new Title("CS2103 Deadline");
            return new Task(name, privateVenue, null, privateEndTime,
                    privateUrgencyLevel, privateDescription, tags);
        }
        
        Task cs2103Event() throws Exception {
            name = new Title("CS2103 Event");
            return new Task(name, privateVenue, privateStartTime, privateEndTime,
                    privateUrgencyLevel, privateDescription, tags);
        }
        
        Task cs2103Float() throws Exception {
            name = new Title("CS2103 Float");
            return new Task(name, privateVenue, null, null,
                    privateUrgencyLevel, privateDescription, tags);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the
         * returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed
         *            used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    new Title("Task " + seed),
                    new Venue("" + Math.abs(seed)),
                    new StartTime("0000"),
                    new EndTime("1200" + seed),
                    new UrgencyLevel("3"),
                    new Description("I love 2103!!"),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))));
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getTitle().toString());
            if (p.getVenue().isPresent()) {
                cmd.append(" /venue ").append(p.getVenue().get());
            }
            if (p.getStartTime().isPresent()) {
                cmd.append(" /from ").append(p.getStartTime().get());
            }
            if (p.getEndTime().isPresent()) {
                cmd.append(" /to ").append(p.getEndTime().get());
            }
            if (p.getUrgencyLevel().isPresent()) {
                cmd.append(" /level ").append(p.getUrgencyLevel().get());
            }
            if (p.getDescription().isPresent()) {
                cmd.append(" /description ").append(p.getDescription().get());
            }
            UniqueTagList tags = p.getTags();
            for (Tag t : tags) {
                cmd.append(" #").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an ToDoList with auto-generated tasks.
         */
        ToDoList generateToDoList(int numGenerated) throws Exception {
            ToDoList todoList = new ToDoList();
            addToToDoList(todoList, numGenerated);
            return todoList;
        }

        /**
         * Generates an ToDoList based on the list of Tasks given.
         */
        ToDoList generateToDoList(List<Task> tasks) throws Exception {
            ToDoList todoList = new ToDoList();
            addToToDoList(todoList, tasks);
            return todoList;
        }

        /**
         * Adds auto-generated Task objects to the given ToDoList
         *
         * @param toDoList
         *            The ToDoList to which the Tasks will be added
         */
        void addToToDoList(ToDoList toDoList, int numGenerated) throws Exception {
            addToToDoList(toDoList, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given ToDoList
         */
        void addToToDoList(ToDoList toDoList, List<Task> tasksToAdd) throws Exception {
            for (Task t : tasksToAdd) {
                toDoList.addTask(t);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         *
         * @param model
         *            The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task p : tasksToAdd) {
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some
         * dummy values.
         */
        Task generateTaskWithTitle(String name) throws Exception {
            return new Task(
                    new Title(name),
                    new Venue("location"),
                    new StartTime("Today"),
                    new EndTime("Tomorrow"),
                    new UrgencyLevel("3"),
                    new Description("I love 2103!!"),
                    new UniqueTagList(new Tag("tag")));
        }
    }
}
