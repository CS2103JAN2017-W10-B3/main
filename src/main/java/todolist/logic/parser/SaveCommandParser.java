package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import todolist.commons.core.Config;
import todolist.logic.commands.Command;
import todolist.logic.commands.IncorrectCommand;
import todolist.logic.commands.SaveCommand;

//@@author A0143648Y

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

    public static final Pattern SAVE_ARGS_FORMAT = Pattern.compile("(?<arguments>.*)");

    public SaveCommandParser() {
    }

    public Command parse(String args) {
        String filePath = args.trim();
        final Matcher matcher = SAVE_ARGS_FORMAT.matcher(filePath);
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

        //@@author A0110791M
        if (filePath.endsWith(".xml")) {
            return new SaveCommand(filePath);
        }

        File file = new File(filePath);
        if ((new File(filePath.concat(".xml"))).isFile()) {
            filePath = filePath.concat(".xml");
        } else if (file.isDirectory() || !file.exists()) {
            filePath = filePath.concat(Config.DEFAULT_TODOLIST_FILENAME);
        }

        return new SaveCommand(filePath);
    }
}
