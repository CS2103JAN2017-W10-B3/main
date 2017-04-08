package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static todolist.logic.parser.CliSyntax.PREFIX_BEGINNINGTIME;
import static todolist.logic.parser.CliSyntax.PREFIX_DEADLINETIME;
import static todolist.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static todolist.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static todolist.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static todolist.logic.parser.CliSyntax.PREFIX_TAG;
import static todolist.logic.parser.CliSyntax.PREFIX_TITLE;
import static todolist.logic.parser.CliSyntax.PREFIX_URGENCYLEVEL;
import static todolist.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.ArrayList;
import java.util.Optional;

import todolist.logic.commands.Command;
import todolist.logic.commands.DeleteCommand;
import todolist.logic.commands.DeleteCommand.DeleteTaskDescriptor;
import todolist.logic.commands.IncorrectCommand;
import todolist.model.task.TaskIndex;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
// @@ A0143648Y
public class DeleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
        Optional<ArrayList<TaskIndex>> indexes;
        assert args != null;
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_TITLE, PREFIX_VENUE, PREFIX_STARTTIME,
                PREFIX_BEGINNINGTIME, PREFIX_ENDTIME, PREFIX_DEADLINETIME,
                PREFIX_URGENCYLEVEL, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        String indexesToBeParsed = argsTokenizer.getPreamble().orElse("");
        if (indexesToBeParsed.isEmpty()) {
            indexes = Optional.of(new ArrayList<TaskIndex>());
        } else {

            indexes = ParserUtil.parseIndex(indexesToBeParsed);

            if (!indexes.isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

        }

        if (!isValidDeleteArgs(argsTokenizer)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        DeleteTaskDescriptor deleteTaskDescriptor = new DeleteTaskDescriptor();
        deleteTaskDescriptor.setVenue(argsTokenizer.getValue(PREFIX_VENUE).isPresent());
        deleteTaskDescriptor.setStartTime(argsTokenizer.getValue(PREFIX_STARTTIME).isPresent() ||
                argsTokenizer.getValue(PREFIX_BEGINNINGTIME).isPresent());
        deleteTaskDescriptor.setEndTime(argsTokenizer.getValue(PREFIX_ENDTIME).isPresent() ||
                argsTokenizer.getValue(PREFIX_DEADLINETIME).isPresent());
        deleteTaskDescriptor.setUrgency(argsTokenizer.getValue(PREFIX_URGENCYLEVEL).isPresent());
        deleteTaskDescriptor.setDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION).isPresent());
        deleteTaskDescriptor.setTags(argsTokenizer.getAllValues(PREFIX_TAG).isPresent());

        return new DeleteCommand(indexes.get(), deleteTaskDescriptor);

    }

    private boolean isValidDeleteArgs(ArgumentTokenizer argsTokenizer) {
        boolean isValid = true;
        if (argsTokenizer.getValue(PREFIX_TITLE).isPresent()) {
            isValid = false;
        }

        if (argsTokenizer.getValue(PREFIX_VENUE).isPresent()) {
            if (!argsTokenizer.getValue(PREFIX_VENUE).get().isEmpty()) {
                isValid = false;
            }
        }

        if (argsTokenizer.getValue(PREFIX_STARTTIME).isPresent()) {
            if (!argsTokenizer.getValue(PREFIX_STARTTIME).get().isEmpty()) {
                isValid = false;
            }
        }

        if (argsTokenizer.getValue(PREFIX_BEGINNINGTIME).isPresent()) {
            if (!argsTokenizer.getValue(PREFIX_BEGINNINGTIME).get().isEmpty()) {
                isValid = false;
            }
        }

        if (argsTokenizer.getValue(PREFIX_ENDTIME).isPresent()) {
            if (!argsTokenizer.getValue(PREFIX_ENDTIME).get().isEmpty()) {
                isValid = false;
            }
        }

        if (argsTokenizer.getValue(PREFIX_DEADLINETIME).isPresent()) {
            if (!argsTokenizer.getValue(PREFIX_DEADLINETIME).get().isEmpty()) {
                isValid = false;
            }
        }

        if (argsTokenizer.getValue(PREFIX_URGENCYLEVEL).isPresent()) {
            if (!argsTokenizer.getValue(PREFIX_URGENCYLEVEL).get().isEmpty()) {
                isValid = false;
            }
        }

        if (argsTokenizer.getValue(PREFIX_DESCRIPTION).isPresent()) {
            if (!argsTokenizer.getValue(PREFIX_DESCRIPTION).get().isEmpty())
                isValid = false;
        }

        if (argsTokenizer.getAllValues(PREFIX_TAG).isPresent()) {
            if (!argsTokenizer.getAllValues(PREFIX_TAG).get().get(0).isEmpty())
                isValid = false;
        }

        return isValid;
    }
}
