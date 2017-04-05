package todolist.logic.commands;

//@@author A0122017Y
/**
 * Lists all persons in the address book to the user.
 */
public class ListTagCommand extends Command {

    public static final String COMMAND_WORD = "listtags";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all tags in ToDoList. \n"
            + "Example: " + COMMAND_WORD + "\n";

    @Override
    public CommandResult execute() {
        return new CommandResult(model.getTagListToString());
    }
}
