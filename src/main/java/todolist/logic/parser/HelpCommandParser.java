//@@ author: A0138628W
package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import todolist.logic.commands.Command;
import todolist.logic.commands.HelpCommand;
import todolist.logic.commands.IncorrectCommand;

public class HelpCommandParser {

    private CommandSyntax commands;

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns an HelpCommand object for execution.
     */
    public Command parse(String args) {
        commands = new CommandSyntax();
        if (args.isEmpty()) { //just help
            return new HelpCommand(commands);
        } else {
            String command = args.trim();
            if (commands.getSpecificCommandUsageMessage(command) != null) {
                return new HelpCommand(command, commands);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT
                        , HelpCommand.MESSAGE_USAGE));
            }
        }
    }

}
