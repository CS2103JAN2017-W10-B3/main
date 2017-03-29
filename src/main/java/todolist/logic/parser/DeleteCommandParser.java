package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import todolist.logic.commands.Command;
import todolist.logic.commands.DeleteCommand;
import todolist.logic.commands.IncorrectCommand;
import todolist.model.task.TaskIndex;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {
    // @@ A0143648Y
    private static Optional<TaskIndex> index;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
        Optional<TaskIndex> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            index = DeleteCommandParser.index;
            if (!index.isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        }
        return new DeleteCommand(index.get());
    }

    public static void setIndex(TaskIndex index) {
        DeleteCommandParser.index = Optional.of(index);
    }
}
