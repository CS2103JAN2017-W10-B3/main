package todolist.logic.commands;

import java.util.ArrayList;
import java.util.logging.Logger;

import todolist.commons.core.LogsCenter;
import todolist.model.ReadOnlyToDoList;
import todolist.model.ToDoList;
//@@author A0143648Y
/**
 * Clears the todolist.
 */
public class ClearCommand extends UndoableCommand {

    private Logger logger = LogsCenter.getLogger(AddCommand.class.getName());

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all tasks in ToDoList. \n"
            + "Example: " + COMMAND_WORD + "\n";
    public static final String MESSAGE_SUCCESS = "To-do list has been cleared!";
    private ReadOnlyToDoList originalToDoList;
    private CommandResult commandResultToUndo;

    @Override
    public CommandResult execute() {
        assert model != null;
        originalToDoList = new ToDoList(model.getToDoList());
        model.resetData(new ToDoList());
        commandResultToUndo = new CommandResult(MESSAGE_SUCCESS);

        logger.info("-------[Executing ClearCommand]");

        updateUndoLists();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Update {@code previousToDoLists} with the todolist before last edition
     * and {@code previousCommand} with the command just executed
     */
    @Override
    public void updateUndoLists() {
        if (previousToDoLists == null) {
            previousToDoLists = new ArrayList<ReadOnlyToDoList>(UNDO_HISTORY_SIZE);
            previousCommandResults = new ArrayList<CommandResult>(UNDO_HISTORY_SIZE);
        }
        if (previousToDoLists.size() >= UNDO_HISTORY_SIZE) {
            previousToDoLists.remove(ITEM_TO_BE_REMOVED_FROM_HISTORY);
            previousCommandResults.remove(ITEM_TO_BE_REMOVED_FROM_HISTORY);
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        } else {
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        }
    }

}
