package todolist.logic.commands;

import todolist.logic.commands.exceptions.CommandException;
import todolist.logic.parser.CommandSyntax;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    //@@ author: A0138628W
    private final String commandType;
    private final CommandSyntax commandSyntax;
    private static String showingHelpMessage;

    public static final String COMMAND_WORD = "help";
    public static final String MESSAGE_INVALID_COMMAND_WORD = "Help message not available for "
            + "this command word!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " add \n";

    public HelpCommand(CommandSyntax commandSyntax) {
        this.commandType = "";
        this.commandSyntax = commandSyntax;
        showingHelpMessage = commandSyntax.getAllCommandUsageMessage();
    }

    public HelpCommand(String commandType, CommandSyntax commandSyntax) {
        assert !commandType.isEmpty();
        this.commandType = commandType;
        this.commandSyntax = commandSyntax;
        showingHelpMessage = commandSyntax.getAllCommandUsageMessage();
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (commandType.isEmpty()) {
            return new CommandResult(commandSyntax.getAllCommandUsageMessage());
        } else {
            return new CommandResult(getCommandUsageMessage(commandType));
        }
    }

    public static String getHelpMessage() {
        return showingHelpMessage;
    }

    private String getCommandUsageMessage(String command) throws CommandException {
        assert !command.isEmpty();
        try {
            return commandSyntax.getSpecificCommandUsageMessage(command);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_INVALID_COMMAND_WORD);
        }
    }
}
