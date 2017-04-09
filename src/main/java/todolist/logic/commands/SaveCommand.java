package todolist.logic.commands;

//@@author A0143648Y

import java.io.IOException;
import java.util.logging.Logger;

import todolist.commons.core.Config;
import todolist.commons.core.LogsCenter;
import todolist.model.ReadOnlyToDoList;
import todolist.storage.Storage;
import todolist.storage.StorageManager;

/**
 * Save command that saves current data file to a new filepath.
 *
 */

public class SaveCommand extends Command {

    private Logger logger = LogsCenter.getLogger(SaveCommand.class.getName());

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves data file to new location specified. "
            + "New folders with the file can be auto-created as long as given directory or file path is valid.\n"
            + "Main directory will be the default save location for any valid but unspecifed file path\n"
            + "Example: " + COMMAND_WORD + " C:/Users/Computing/Desktop/CS2103 \n";

    public static final String MESSAGE_SUCCESS = "Data successfully saved to location: %s";
    public static final String MESSAGE_INVALID_PATH = "Filepath given is invalid. "
            + "Filepath will be reset to old path.\n" + MESSAGE_USAGE;

    // private static Config config;
    private String newStorageFilePath;
    private ReadOnlyToDoList toDoList;
    private static Storage storage;

    public SaveCommand(String newStorageFilePath) {
        this.newStorageFilePath = newStorageFilePath;
        logger.info("Data saved to: " + this.newStorageFilePath);

        setStorage(new StorageManager(Config.getToDoListFilePath(), Config.getUserPrefsFilePath()));
    }

    public static void setStorage(Storage s) {
        storage = s;
    }
    
    public static String getUserPrefsFilePath() {
        return Config.getUserPrefsFilePath();
    }

    @Override
    public CommandResult execute() {

        toDoList = model.getToDoList();

        try {
            storage.saveToDoList(toDoList, newStorageFilePath);
        } catch (IOException e) {
            handleInvalidFilePathException();
            return new CommandResult(MESSAGE_INVALID_PATH);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_SUCCESS, newStorageFilePath));
    }

    private void handleInvalidFilePathException() {
        logger.info("Error writing to filepath. Handling data save exception.");

        try {
            storage.saveToDoList(toDoList, newStorageFilePath);
        } catch (IOException e) {
            logger.severe("Error saving task manager");
        }

    }

}
