package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;

import todolist.logic.commands.ChangeDirectoryCommand;
import todolist.logic.commands.Command;
import todolist.logic.commands.IncorrectCommand;

public class ChangeDirectoryCommandParser {

    public ChangeDirectoryCommandParser() {
        // TODO Auto-generated constructor stub
    }

    public Command parse(String args) {
        String commandString = args.trim();
        final Matcher matcher = SaveCommandParser.SAVE_ARGS_FORMAT.matcher(commandString);

        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeDirectoryCommand.MESSAGE_USAGE));
        }

        return new ChangeDirectoryCommand(commandString);
    }
}
