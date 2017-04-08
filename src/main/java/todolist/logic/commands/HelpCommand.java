package todolist.logic.commands;

import todolist.commons.core.EventsCenter;
import todolist.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    //@@ author: A0138628W
    private final String commandType;

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " add";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public HelpCommand() {
        commandType = "";
    }

    public HelpCommand(String commandType) {
        assert !commandType.isEmpty();
        this.commandType = commandType;
    }

    @Override
    public CommandResult execute() {
        if (commandType.isEmpty()) {
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
        } else {
            return new CommandResult(getCommandUsageMessage(commandType));
        }
    }

    private String getCommandUsageMessage(String command) {
        assert !command.isEmpty();
        if (command.equals(AddCommand.COMMAND_WORD)) {
            return AddCommand.MESSAGE_USAGE;
        } else if (command.equals(EditCommand.COMMAND_WORD)) {
            return EditCommand.MESSAGE_USAGE;
        } else if (command.equals(SelectCommand.COMMAND_WORD)) {
            return SelectCommand.MESSAGE_USAGE;
        } else if (command.equals(DeleteCommand.COMMAND_WORD)) {
            return DeleteCommand.MESSAGE_USAGE;
        } else if (command.equals(ClearCommand.COMMAND_WORD)) {
            return ClearCommand.MESSAGE_USAGE;
        } else if (command.equals(CompleteCommand.COMMAND_WORD)) {
            return CompleteCommand.MESSAGE_USAGE;
        } else if (command.equals(FindCommand.COMMAND_WORD)) {
            return FindCommand.MESSAGE_USAGE;
        } else if (command.equals(ListCommand.COMMAND_WORD)) {
            return ListCommand.MESSAGE_USAGE;
        } else if (command.equals(ExitCommand.COMMAND_WORD)) {
            return ExitCommand.MESSAGE_USAGE;
        } else if (command.equals(UndoCommand.COMMAND_WORD)) {
            return UndoCommand.MESSAGE_USAGE;
        } else if (command.equals(SaveCommand.COMMAND_WORD)) {
            return SaveCommand.MESSAGE_USAGE;
        } else {
            return MESSAGE_USAGE;
        }
    }
}
