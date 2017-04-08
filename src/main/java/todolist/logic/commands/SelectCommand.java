package todolist.logic.commands;

import java.util.ArrayList;

import todolist.commons.core.EventsCenter;
import todolist.commons.core.Messages;
import todolist.commons.core.UnmodifiableObservableList;
import todolist.commons.events.ui.SelectMultipleTargetEvent;
import todolist.logic.commands.exceptions.CommandException;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.TaskIndex;

//@@ author A0143648Y
/**
 * Selects a task identified using it's last displayed index from the address
 * book.
 */
public class SelectCommand extends Command {

    private final ArrayList<TaskIndex> targetIndexes;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: TYPE (d, e or f) + INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " d1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Tasks have been succussfully selected!\n";

    public SelectCommand(ArrayList<TaskIndex> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute() throws CommandException {
        for (int count = 0; count < targetIndexes.size(); count++) {

            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model
                    .getListFromChar(targetIndexes.get(count).getTaskChar());

            int listIndex = targetIndexes.get(count).getTaskNumber();

            if (lastShownList.size() < listIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
        }

        EventsCenter.getInstance().post(new SelectMultipleTargetEvent(targetIndexes));
        model.updateSelectedIndexes(targetIndexes);

        String selectCommandResult = MESSAGE_SELECT_TASK_SUCCESS;
        return new CommandResult(selectCommandResult);

    }

}
