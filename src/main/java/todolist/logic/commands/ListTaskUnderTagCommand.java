package todolist.logic.commands;

import java.util.Set;

/**
 * Lists all persons in the address book to the user.
 */
public class ListTaskUnderTagCommand extends Command {

    public static final String COMMAND_WORD = "list#";
    public static final String MESSAGE_SUCCESS = "Tasks under the tags are listed!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " + tag name: list all tasks under the tag"
            + "Example: " + COMMAND_WORD + " family friend school";
    
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
