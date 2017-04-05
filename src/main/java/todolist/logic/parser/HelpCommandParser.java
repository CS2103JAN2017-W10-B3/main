//@@ author: A0138628W
package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import todolist.logic.commands.AddCommand;
import todolist.logic.commands.ClearCommand;
import todolist.logic.commands.Command;
import todolist.logic.commands.CompleteCommand;
import todolist.logic.commands.DeleteCommand;
import todolist.logic.commands.EditCommand;
import todolist.logic.commands.ExitCommand;
import todolist.logic.commands.FindCommand;
import todolist.logic.commands.HelpCommand;
import todolist.logic.commands.IncorrectCommand;
import todolist.logic.commands.ListCommand;
import todolist.logic.commands.ListTagCommand;
import todolist.logic.commands.ListTaskUnderTagCommand;
import todolist.logic.commands.SaveCommand;
import todolist.logic.commands.SelectCommand;
import todolist.logic.commands.UndoCommand;

public class HelpCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns an HelpCommand object for execution.
     */
    public Command parse(String args) {
        if (args.isEmpty()) { //just help
            return new HelpCommand();
        } else {
            String command = args.trim();
            if (isValidInput(command)) {
                return new HelpCommand(command);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }
        }
    }

    private boolean isValidInput(String input) {
        if (input.equals(AddCommand.COMMAND_WORD)) {
            return true;
        } else if (input.equals(EditCommand.COMMAND_WORD)) {
            return true;
        } else if (input.equals(SelectCommand.COMMAND_WORD)) {
            return true;
        } else if (input.equals(DeleteCommand.COMMAND_WORD)) {
            return true;
        } else if (input.equals(ClearCommand.COMMAND_WORD)) {
            return true;
        } else if (input.equals(CompleteCommand.COMMAND_WORD)) {
            return true;
        } else if (input.equals(FindCommand.COMMAND_WORD)) {
            return true;
        } else if (input.equals(ListCommand.COMMAND_WORD)) {
            return true;
        } else if (input.equals(ListTagCommand.COMMAND_WORD)) {
            return true;
        } else if (input.equals(ListTaskUnderTagCommand.COMMAND_WORD)) {
            return true;
        } else if (input.equals(ExitCommand.COMMAND_WORD)) {
            return true;
        } else if (input.equals(HelpCommand.COMMAND_WORD)) {
            return true;
        } else if (input.equals(UndoCommand.COMMAND_WORD)) {
            return true;
        } else if (input.equals(SaveCommand.COMMAND_WORD)) {
            return true;
        } else {
            return false;
        }
    }

}
