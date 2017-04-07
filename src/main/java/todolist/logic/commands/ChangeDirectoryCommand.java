package todolist.logic.commands;

import java.io.IOException;

import todolist.commons.core.Config;
import todolist.logic.commands.exceptions.CommandException;

//@@author A0110791M
public class ChangeDirectoryCommand extends Command {

    public static final String COMMAND_WORD = "changedir";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves the data to location specified by file path and sets it as current file path.\n"
            + "General usage: \"changedir [file path]\" "
            + "where the file path should be an xml file or a directory (if directory then default file name is used)"
            + "\nExample: changedir C:\\data\\mytodolist or changedir C:\\data\\ \n";

    public static final String MESSAGE_SUCCESS = "Directory successfully changed from: %s to: %s";
    public static final String MESSAGE_FAILURE = "Error encountered, please check file path: %s";

    private String filePath;

    public ChangeDirectoryCommand(String targetFilePath) {
        filePath = targetFilePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            String currentFilePath = Config.getToDoListFilePath();
            model.changeDirectory(filePath);
            return new CommandResult(String.format(MESSAGE_SUCCESS, currentFilePath, filePath));
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_FAILURE, filePath));
        }
    }

}
// @@
