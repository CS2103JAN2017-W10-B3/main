package todolist.logic.commands;

import java.util.logging.Logger;

import todolist.commons.core.LogsCenter;
import todolist.logic.commands.exceptions.CommandException;

//@@author A0143648Y
/**
 * Undoes the most recent modification to the ToDoList
 *
 * @author Jia Yilin
 *
 */
public class UndoCommand extends Command {
    private final Logger logger = LogsCenter.getLogger(UndoCommand.class);

    public static final String MESSAGE_UNDO_FAILURE = "No more operations to undo";
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Only the last three commands can be recovered. \n"
            + "Example: " + COMMAND_WORD + "\n";
    public static final String MESSAGE_UNDO_SUCCESS = "The following command has been undone: ";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            logger.info("-----------[SYSTEM UNDO COMMAND]");
            model.resetData(UndoableCommand.previousToDoLists.get(UndoableCommand.previousToDoLists.size() - 1));
            UndoableCommand.previousToDoLists.remove(UndoableCommand.previousToDoLists.size() - 1);
            String feedbackToUser = new String(UndoableCommand.previousCommandResults
                    .get(UndoableCommand.previousCommandResults.size() - 1).feedbackToUser);
            UndoableCommand.previousCommandResults.remove(UndoableCommand.previousCommandResults.size() - 1);
            return new CommandResult(MESSAGE_UNDO_SUCCESS + feedbackToUser);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_UNDO_FAILURE);
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_UNDO_FAILURE);
        }

    }
}
