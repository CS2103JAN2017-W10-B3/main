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

    public static String showingHelpMessage  = "Opened help window.";

    public HelpCommand(CommandSyntax commandSyntax) {
        commandType = "";
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
    public CommandResult execute() {
        if (commandType.isEmpty()) {
            return new CommandResult(commandSyntax.getAllCommandUsageMessage());
        } else {
            return new CommandResult(commandSyntax.getSpecificCommandUsageMessage(commandType));
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
