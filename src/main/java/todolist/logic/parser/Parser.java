package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static todolist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import todolist.logic.commands.AddCommand;
import todolist.logic.commands.ChangeDirectoryCommand;
import todolist.logic.commands.ClearCommand;
import todolist.logic.commands.Command;
import todolist.logic.commands.CompleteCommand;
import todolist.logic.commands.DeleteCommand;
import todolist.logic.commands.EditCommand;
import todolist.logic.commands.ExitCommand;
import todolist.logic.commands.FindCommand;
import todolist.logic.commands.HelpCommand;
import todolist.logic.commands.ImportCommand;
import todolist.logic.commands.IncorrectCommand;
import todolist.logic.commands.JokeCommand;
import todolist.logic.commands.ListCommand;
import todolist.logic.commands.SaveCommand;
import todolist.logic.commands.SelectCommand;
import todolist.logic.commands.UndoCommand;
import todolist.model.task.TaskIndex;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput
     *            full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case CompleteCommand.COMMAND_WORD:
            return new CompleteCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        case JokeCommand.COMMAND_WORD:
            return new JokeCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(arguments);

        case ChangeDirectoryCommand.COMMAND_WORD:
            return new ChangeDirectoryCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);
            //@@author A0143648Y
        default:
            if (ParserUtil.isValidIndex(commandWord) && arguments.isEmpty()) {
                return new SelectCommandParser().parse(commandWord);
            } else {
                Optional<ArrayList<TaskIndex>> testIfArgumentValid = ParserUtil.parseIndex(arguments.trim());
                if (ParserUtil.isValidIndex(commandWord) && testIfArgumentValid.isPresent()) {
                    return new SelectCommandParser().parse(commandWord + " " + arguments.trim());
                } else {
                    return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
                }
            }
        }
    }

}
