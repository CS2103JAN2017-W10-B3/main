package todolist.logic.commands;

import java.util.Set;

/**
 * Lists all persons in the address book to the user.
 */
public class ListTaskUnderTagCommand extends Command {

    public static final String COMMAND_WORD = "list#";
    public static final String MESSAGE_SUCCESS = "Tasks under the tags are listed!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all tasks under the tag. \n"
            + "Parameters: TagName (Can be multiple) \n"
            + "Example: " + COMMAND_WORD + " family friend school \n";

    private Set<String> keywordSet;

    public ListTaskUnderTagCommand(Set<String> keywordSet) {
        this.keywordSet = keywordSet;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListToShowWithTag(keywordSet);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
