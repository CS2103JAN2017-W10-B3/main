package todolist.logic.commands;

import java.io.IOException;
import java.util.logging.Logger;

import todolist.commons.core.LogsCenter;
import todolist.commons.exceptions.DataConversionException;
import todolist.logic.commands.exceptions.CommandException;

//@@author A0110791M
public class ImportCommand extends Command {

    private Logger logger = LogsCenter.getLogger(ImportCommand.class.getName());

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports tasks from the data file specified.\n"
            + "General usage: \"import [file path]\" "
            + "where the file should be an xml file and the path can be relative or absolute addressing\n"
            + "Example: import C:\\data\\mytodolist \n";

    public static final String MESSAGE_SUCCESS = "Successful import from: ";
    public static final String MESSAGE_FAILURE = "Error: please check file format and file path.\n" + MESSAGE_USAGE;

    private String sourceFilePath;

    public ImportCommand(String source) {
        sourceFilePath = source;
    }

    @Override
    public CommandResult execute() throws CommandException {
        logger.info("Importing data from: " + this.sourceFilePath);

        try {
            model.importTasks(sourceFilePath);
            logger.info("Data imported");
            return new CommandResult(MESSAGE_SUCCESS.concat(sourceFilePath));
        } catch (IOException | DataConversionException e) {
            logger.info("Error reading from filepath.");
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

}
// @@
