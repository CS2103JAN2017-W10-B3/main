package todolist.logic.commands;

import java.util.ArrayList;
import java.util.List;

import todolist.commons.core.EventsCenter;
import todolist.commons.core.Messages;
import todolist.commons.events.ui.SelectMultipleTargetEvent;
import todolist.logic.commands.exceptions.CommandException;
import todolist.model.ReadOnlyToDoList;
import todolist.model.ToDoList;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.TaskIndex;

//@@author A0122017Y

/**
 * Selects a task identified using it's last displayed index from the address
 * book.
 */
public class CompleteCommand extends UndoableCommand {

    private final ArrayList<TaskIndex> filteredTaskListIndexes;

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the task identified by the index number used in the last task listing.\n"
            + "Parameters: CHAR(d, e or f) + INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " e1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed Task: ";

    private ReadOnlyToDoList originalToDoList;
    private CommandResult commandResultToUndo;

    public CompleteCommand(ArrayList<TaskIndex> filteredTaskListIndexes) {
        this.filteredTaskListIndexes = filteredTaskListIndexes;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (filteredTaskListIndexes.isEmpty()) {
            filteredTaskListIndexes.addAll(model.getSelectedIndexes());
            if (filteredTaskListIndexes.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_NO_TASK_SELECTED);
            }
        }
        originalToDoList = new ToDoList(model.getToDoList());
        ArrayList<ReadOnlyTask> tasksToComplete = new ArrayList<ReadOnlyTask>();
        ArrayList<TaskIndex> selectedIndexes = new ArrayList<TaskIndex>();
        String messageSuccessful = new String("");
        for (int count = 0; count < filteredTaskListIndexes.size(); count++) {
            List<ReadOnlyTask> lastShownList = model.getListFromChar(filteredTaskListIndexes.get(count).getTaskChar());
            int filteredTaskListIndex = filteredTaskListIndexes.get(count).getTaskNumber() - 1;

            if (lastShownList.size() < filteredTaskListIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            tasksToComplete.add(lastShownList.get(filteredTaskListIndex));
            messageSuccessful = messageSuccessful +" " + lastShownList.get(filteredTaskListIndex).getTitle().toString();
        }

        for (int count = 0; count < tasksToComplete.size(); count++) {
            model.completeTask(tasksToComplete.get(count));
        }

        commandResultToUndo = new CommandResult(MESSAGE_COMPLETE_TASK_SUCCESS);
        updateUndoLists();

        List<ReadOnlyTask> completedList = model.getCompletedList();
        for (int count = 0; count < tasksToComplete.size(); count++) {
            selectedIndexes.add(new TaskIndex('c', completedList.indexOf(tasksToComplete.get(count)) + 1));
        }
        model.updateSelectedIndexes(selectedIndexes);
        EventsCenter.getInstance().post(new SelectMultipleTargetEvent(selectedIndexes));

        return new CommandResult(MESSAGE_COMPLETE_TASK_SUCCESS + messageSuccessful);
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
