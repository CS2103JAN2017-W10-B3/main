package todolist.logic.commands;

import todolist.logic.commands.exceptions.CommandException;

//@@author A0110791M
public class ChangeDirectoryCommand extends Command {

    public static final String COMMAND_WORD = "changedir";
    public static final String MESSAGE_SUCCESS = "Directory changed to: ";

    private String targetDirectory;

    public ChangeDirectoryCommand(String targetDirectoryPath) {
        targetDirectory = targetDirectoryPath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        //Config.setToDoListFilePath(targetDirectory);
        model.indicateDirectoryChanged(targetDirectory);
        return new CommandResult(MESSAGE_SUCCESS.concat(targetDirectory));
    }

}
//@@
