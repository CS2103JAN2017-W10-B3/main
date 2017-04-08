package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.util.regex.Matcher;

import todolist.logic.commands.Command;
import todolist.logic.commands.ImportCommand;
import todolist.logic.commands.IncorrectCommand;

//@@author A0110791M
public class ImportCommandParser {

    public ImportCommandParser() {
    }

    public Command parse(String sourceFilePath) {
        String filePath = sourceFilePath.trim();
        if (filePath.length() == 0) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        final Matcher matcher = ChangeDirectoryCommandParser.FILE_PATH_PATTERN.matcher(filePath);
        if (matcher.matches()) {
            return new IncorrectCommand(
                    String.format(ImportCommand.MESSAGE_FAILURE, filePath));
        }

        if (!filePath.endsWith(".xml")) {
            filePath = filePath.concat(".xml");
        }
        if (new File(filePath).exists()) {
            return new ImportCommand(filePath);
        } else {
            return new IncorrectCommand("File does not exist.");
        }
    }

}
//@@
