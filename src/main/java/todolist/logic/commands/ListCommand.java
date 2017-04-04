package todolist.logic.commands;

import todolist.commons.core.EventsCenter;
import todolist.commons.events.ui.ClearAllSelectionsEvent;

/**
 * Lists all tasks in the to-do list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        model.clearSelectedIndexes();
        EventsCenter.getInstance().post(new ClearAllSelectionsEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
