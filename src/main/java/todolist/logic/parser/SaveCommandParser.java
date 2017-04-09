package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.util.regex.Matcher;

import todolist.commons.core.Config;
import todolist.logic.commands.Command;
import todolist.logic.commands.IncorrectCommand;
import todolist.logic.commands.SaveCommand;


/**
 * Parses arguments in the context of the save command.
 *
 * @param args
 *            full command args string
 * @return the prepared command
 *
 *
 */
public class SaveCommandParser {

    public SaveCommandParser() {
    }

    public Command parse(String args) {
        String filePath = args.trim();
        //@@author A0110791M
        if (filePath.length() == 0) {
            return new SaveCommand(Config.getToDoListFilePath());
        }

        final Matcher matcher = ChangeDirectoryCommandParser.FILE_PATH_PATTERN.matcher(filePath);
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

        File file = new File(filePath);
        if (file.isDirectory()) {
            filePath = filePath.concat("\\").concat(Config.DEFAULT_TODOLIST_FILENAME);
        } else if (!filePath.endsWith(".xml")) {
            filePath = filePath.concat(".xml");
        }

        return new SaveCommand(filePath);
    }
}
