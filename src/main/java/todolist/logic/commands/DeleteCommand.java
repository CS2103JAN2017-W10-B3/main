package todolist.logic.commands;

import java.util.ArrayList;

import todolist.commons.core.Messages;
import todolist.commons.core.UnmodifiableObservableList;
import todolist.logic.commands.exceptions.CommandException;
import todolist.model.ReadOnlyToDoList;
import todolist.model.ToDoList;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.TaskIndex;
import todolist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address
 * book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing. \n"
            + "Parameters: TYPE (d, e or f) + INDEX (must be a positive integer) \n" + "Example: " + COMMAND_WORD + " e1 \n";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final TaskIndex targetIndex;

    private ReadOnlyToDoList originalToDoList;
    private CommandResult commandResultToUndo;

    public DeleteCommand(TaskIndex targetIndex) {
        this.targetIndex = targetIndex;
    }


    // @@ A0143648Y
    @Override
    public CommandResult execute() throws CommandException {
        originalToDoList = new ToDoList(model.getToDoList());

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getListFromChar(targetIndex.getTaskChar());

        if (lastShownList.size() < targetIndex.getTaskNumber()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex.getTaskNumber() - 1);

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        commandResultToUndo = new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        updateUndoLists();

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
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
