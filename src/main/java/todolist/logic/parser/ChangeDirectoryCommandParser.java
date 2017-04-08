package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import todolist.commons.core.Config;
import todolist.logic.commands.ChangeDirectoryCommand;
import todolist.logic.commands.Command;
import todolist.logic.commands.IncorrectCommand;

//@@author A0110791M
public class ChangeDirectoryCommandParser {

    public static final Pattern FILE_PATH_PATTERN = Pattern.compile("[:*?\"<>|]+");

    public ChangeDirectoryCommandParser() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Provides flexibility for user to either specify folder or file path
     * directly
     */
    public Command parse(String args) {
        String commandString = args.trim();
        if (commandString.length() == 0) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeDirectoryCommand.MESSAGE_USAGE));
        }

        String filePath;
        final Matcher matcher = FILE_PATH_PATTERN.matcher(commandString);
        if (matcher.matches()) {
            return new IncorrectCommand(
                    String.format(ChangeDirectoryCommand.MESSAGE_FAILURE, commandString));
        }

        File file = new File(commandString);
        if (file.isDirectory()) {
            filePath = commandString.concat("\\").concat(Config.DEFAULT_TODOLIST_FILENAME);
        } else if (!commandString.endsWith(".xml")) {
            filePath = commandString.concat(".xml");
        } else {
            filePath = commandString;
        }

        return new ChangeDirectoryCommand(filePath);
    }
}
// @@
