package todolist.logic.commands;


import todolist.logic.parser.CommandSyntax;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    //@@ author: A0138628W
    private final String commandType;

    private final CommandSyntax commandSyntax;

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " add \n";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public HelpCommand(CommandSyntax commandSyntax) {
        commandType = "";
        this.commandSyntax = commandSyntax;
    }

    public HelpCommand(String commandType, CommandSyntax commandSyntax) {
        assert !commandType.isEmpty();
        this.commandType = commandType;
        this.commandSyntax = commandSyntax;
    }

    @Override
    public CommandResult execute() {
        if (commandType.isEmpty()) {
            return new CommandResult(getAllCommandUsageMessage());
        } else {
            return new CommandResult(getSpecificCommandUsageMessage(commandType));
        }
    }

    private String getSpecificCommandUsageMessage(String command) {
        assert !command.isEmpty();
        return commandSyntax.getAvailableCommands().get(command);
    }

    private String getAllCommandUsageMessage() {
        StringBuilder sb = new StringBuilder();
        commandSyntax.getAvailableCommands().forEach((k, v) -> sb.append(v).append("\n"));
        return sb.toString();
    }
}
