package w10b3.todolist.logic.parser;

import static w10b3.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static w10b3.todolist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import w10b3.todolist.logic.commands.AddCommand;
import w10b3.todolist.logic.commands.ClearCommand;
import w10b3.todolist.logic.commands.Command;
import w10b3.todolist.logic.commands.DeleteCommand;
import w10b3.todolist.logic.commands.EditCommand;
import w10b3.todolist.logic.commands.ExitCommand;
import w10b3.todolist.logic.commands.FindCommand;
import w10b3.todolist.logic.commands.HelpCommand;
import w10b3.todolist.logic.commands.IncorrectCommand;
import w10b3.todolist.logic.commands.ListCommand;
import w10b3.todolist.logic.commands.ListTagCommand;
import w10b3.todolist.logic.commands.SelectCommand;
import w10b3.todolist.logic.commands.UndoCommand;

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
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
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

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListTagCommand.COMMAND_WORD:
            return new ListTagCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}