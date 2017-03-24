package w10b3.todolist.logic.parser;

import static w10b3.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static w10b3.todolist.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static w10b3.todolist.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static w10b3.todolist.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static w10b3.todolist.logic.parser.CliSyntax.PREFIX_TAG;
import static w10b3.todolist.logic.parser.CliSyntax.PREFIX_URGENCYLEVEL;
import static w10b3.todolist.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.NoSuchElementException;

import w10b3.todolist.commons.exceptions.IllegalValueException;
import w10b3.todolist.logic.commands.AddCommand;
import w10b3.todolist.logic.commands.Command;
import w10b3.todolist.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_VENUE, PREFIX_STARTTIME, PREFIX_ENDTIME,
                        PREFIX_URGENCYLEVEL, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_VENUE),
                    argsTokenizer.getValue(PREFIX_STARTTIME),
                    argsTokenizer.getValue(PREFIX_ENDTIME),
                    argsTokenizer.getValue(PREFIX_URGENCYLEVEL),
                    argsTokenizer.getValue(PREFIX_DESCRIPTION),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
                    );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}