package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import todolist.logic.commands.ChangeDirectoryCommand;
import todolist.logic.commands.Command;
import todolist.logic.commands.IncorrectCommand;
import todolist.logic.commands.SaveCommand;

public class ChangeDirectoryCommandParser {

    private static final Pattern SAVE_ARGS_FORMAT = Pattern.compile("(?<arguments>.*)");

    public ChangeDirectoryCommandParser() {
        // TODO Auto-generated constructor stub
    }

    public Command parse(String args) {
        String commandString = args.trim();
        final Matcher matcher = SAVE_ARGS_FORMAT.matcher(commandString);

        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

        return new ChangeDirectoryCommand(commandString);
    }
}
