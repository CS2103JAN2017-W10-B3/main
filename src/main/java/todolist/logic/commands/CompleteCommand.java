package todolist.logic.commands;

import java.util.ArrayList;

import javafx.util.Pair;
import todolist.commons.core.Messages;
import todolist.commons.core.UnmodifiableObservableList;
import todolist.logic.commands.exceptions.CommandException;
import todolist.model.ReadOnlyToDoList;
import todolist.model.ToDoList;
import todolist.model.task.ReadOnlyTask;

//@@author A0122017Y

/**
 * Selects a task identified using it's last displayed index from the address book.
 */
public class CompleteCommand extends UndoableCommand {

    public final Pair<Character, Integer> targetIndex;

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the task identified by the index number used in the last task listing.\n"
            + "Parameters: CHAR(d, e or f) + INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " e1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed Task: %1$s";
    
    private ReadOnlyToDoList originalToDoList;
    private CommandResult commandResultToUndo;
    
    public CompleteCommand(Pair<Character, Integer> targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    @Override
    public CommandResult execute() throws CommandException {
        originalToDoList = new ToDoList(model.getToDoList());

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getListFromChar(targetIndex.getKey());

        if (lastShownList.size() < targetIndex.getValue()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex.getValue() - 1);

        model.completeTask(taskToComplete);

        commandResultToUndo = new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
        updateUndoLists();

        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
    }

    @Override
    public void updateUndoLists() {
        if (previousToDoLists == null) {
            previousToDoLists = new ArrayList<ReadOnlyToDoList>(3);
            previousCommandResults = new ArrayList<CommandResult>(3);
        }
        if (previousToDoLists.size() >= 3) {
            previousToDoLists.remove(0);
            previousCommandResults.remove(0);
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        } else {
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        }
    }
    
}
