package todolist.logic.parser;

import java.util.HashMap;

import todolist.logic.commands.AddCommand;
import todolist.logic.commands.ChangeDirectoryCommand;
import todolist.logic.commands.ClearCommand;
import todolist.logic.commands.CompleteCommand;
import todolist.logic.commands.DeleteCommand;
import todolist.logic.commands.EditCommand;
import todolist.logic.commands.ExitCommand;
import todolist.logic.commands.FindCommand;
import todolist.logic.commands.HelpCommand;
import todolist.logic.commands.ImportCommand;
import todolist.logic.commands.ListCommand;
import todolist.logic.commands.SaveCommand;
import todolist.logic.commands.SelectCommand;
import todolist.logic.commands.UndoCommand;

public class CommandSyntax {

    private final HashMap<String, String> availableCommands;

    public CommandSyntax() {
        availableCommands = new HashMap<String, String>();
        fillCommands();
    }

    private void fillCommands() {
        availableCommands.put(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE);
        availableCommands.put(ChangeDirectoryCommand.COMMAND_WORD, ChangeDirectoryCommand.MESSAGE_USAGE);
        availableCommands.put(ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_USAGE);
        availableCommands.put(CompleteCommand.COMMAND_WORD, CompleteCommand.MESSAGE_USAGE);
        availableCommands.put(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE);
        availableCommands.put(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE);
        availableCommands.put(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_USAGE);
        availableCommands.put(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE);
        availableCommands.put(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE);
        availableCommands.put(ImportCommand.COMMAND_WORD, ImportCommand.MESSAGE_USAGE);
        availableCommands.put(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_USAGE);
        //availableCommands.put(ListTagCommand.COMMAND_WORD, ListTagCommand.MESSAGE_USAGE);
        //availableCommands.put(ListTaskUnderTagCommand.COMMAND_WORD, ListTaskUnderTagCommand.MESSAGE_USAGE);
        availableCommands.put(SaveCommand.COMMAND_WORD, SaveCommand.MESSAGE_USAGE);
        availableCommands.put(SelectCommand.COMMAND_WORD, SelectCommand.MESSAGE_USAGE);
        availableCommands.put(UndoCommand.COMMAND_WORD, UndoCommand.MESSAGE_USAGE);
    }

    public String getSpecificCommandUsageMessage(String command) {
        assert !command.isEmpty();
        return availableCommands.get(command);
    }

    public String getAllCommandUsageMessage() {
        StringBuilder sb = new StringBuilder();
        availableCommands.forEach((k, v) -> sb.append(v).append("\n"));
        return sb.toString();
    }

}
