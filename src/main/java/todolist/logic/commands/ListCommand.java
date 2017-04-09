package todolist.logic.commands;

import todolist.commons.core.EventsCenter;
import todolist.commons.events.ui.ClearAllSelectionsEvent;

/**
 * Lists all tasks in the to-do list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_USAGE = "list all tasks, or happens in specified time intervals!\n"
            + "list: list all tasks"
            + "list [/from START] or [/on START]: list all tasks starts after the time point\n"
            + "Notice that by doing so, all deadline tasks and floating task without a start time will be listed\n"
            + "list [/to END] or [/by END]: list all tasks ends before the time point";

  //@@author A0143648Y
    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        model.clearSelectedIndexes();
        EventsCenter.getInstance().post(new ClearAllSelectionsEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
