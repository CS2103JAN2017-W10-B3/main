package todolist.logic.parser;

import static todolist.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static todolist.logic.parser.CliSyntax.PREFIX_BEGINNINGTIME;
import static todolist.logic.parser.CliSyntax.PREFIX_DEADLINETIME;
import static todolist.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static todolist.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static todolist.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;

import todolist.commons.exceptions.IllegalValueException;
import todolist.commons.util.TimeUtil;
import todolist.logic.commands.Command;
import todolist.logic.commands.IncorrectCommand;
import todolist.logic.commands.ListByDurationCommand;
import todolist.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ListCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ListTaskByDateCommand
     * and returns an ListTaskUnderTagCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new ListCommand();
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_STARTTIME, PREFIX_BEGINNINGTIME, PREFIX_ENDTIME,
                        PREFIX_DEADLINETIME, PREFIX_TAG);
        
        Optional<String> startTimeArg = argsTokenizer.getValue(PREFIX_STARTTIME);
        Optional<String> endTimeArg = argsTokenizer.getValue(PREFIX_ENDTIME);    
        Optional<String> beginningTimeArg = argsTokenizer.getValue(PREFIX_BEGINNINGTIME);
        Optional<String> deadlineArg = argsTokenizer.getValue(PREFIX_DEADLINETIME);
        try {
            TimeUtil.checkTimeDuplicated(startTimeArg,beginningTimeArg, endTimeArg,deadlineArg);
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage());
        }
        
        try {
            return new ListByDurationCommand(startTimeArg, beginningTimeArg, endTimeArg, deadlineArg);
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage());
        }
    }

}
