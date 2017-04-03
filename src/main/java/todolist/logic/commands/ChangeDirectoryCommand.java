package todolist.logic.commands;

import java.io.IOException;

import todolist.logic.commands.exceptions.CommandException;

//@@author A0110791M
public class ChangeDirectoryCommand extends Command {

    public static final String COMMAND_WORD = "changedir";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loads the data and set data storage location to filePath.\n"
            + "General usage: \"changedir [file path]\" "
            + "where the file path should be an xml file or a directory (if directory then default file name is used) \n"
            + "Example: import C:\\data\\mytodolist or import C:\\data\\ \n";

    public static final String MESSAGE_SUCCESS = "Directory successfully changed to: ";
    public static final String MESSAGE_FAILURE = "Error encountered, please check file path: ";

    private String filePath;

    public ChangeDirectoryCommand(String targetFilePath) {
        filePath = targetFilePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.changeDirectory(filePath);
            return new CommandResult(MESSAGE_SUCCESS.concat(filePath));
        } catch (IOException e) {
            return new CommandResult(MESSAGE_FAILURE.concat(filePath));
        }
    }

}
//@@
