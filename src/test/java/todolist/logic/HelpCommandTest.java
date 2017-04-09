package todolist.logic;

import java.util.Collections;

import org.junit.Test;

import todolist.commons.core.Messages;
import todolist.logic.commands.AddCommand;
import todolist.logic.commands.ChangeDirectoryCommand;
import todolist.logic.commands.ClearCommand;
import todolist.logic.commands.CompleteCommand;
import todolist.logic.commands.DeleteCommand;
import todolist.logic.commands.EditCommand;
import todolist.logic.commands.FindCommand;
import todolist.logic.commands.HelpCommand;
import todolist.logic.commands.ImportCommand;
import todolist.logic.commands.JokeCommand;
import todolist.logic.commands.ListCommand;
import todolist.logic.commands.SaveCommand;
import todolist.logic.commands.SelectCommand;
import todolist.logic.commands.UndoCommand;
import todolist.logic.parser.CommandSyntax;
import todolist.model.ToDoList;
import todolist.model.task.Task;

//@@author A0122017Y
public class HelpCommandTest extends LogicManagerTest {

    CommandSyntax syntax = new CommandSyntax();
    @Test
    public void executeHelp() {
        assertCommandSuccess("help", syntax.getAllCommandUsageMessage(),
                new ToDoList(), Collections.emptyList(), Task.ALL_CHAR);
    }

    @Test
    public void executeHelpForCommands() {
        assertHelpForCommands(AddCommand.COMMAND_WORD);
        assertHelpForCommands(ChangeDirectoryCommand.COMMAND_WORD);
        assertHelpForCommands(ClearCommand.COMMAND_WORD);
        assertHelpForCommands(CompleteCommand.COMMAND_WORD);
        assertHelpForCommands(DeleteCommand.COMMAND_WORD);
        assertHelpForCommands(EditCommand.COMMAND_WORD);
        assertHelpForCommands(FindCommand.COMMAND_WORD);
        assertHelpForCommands(HelpCommand.COMMAND_WORD);
        assertHelpForCommands(ImportCommand.COMMAND_WORD);
        assertHelpForCommands(ListCommand.COMMAND_WORD);
        assertHelpForCommands(SaveCommand.COMMAND_WORD);
        assertHelpForCommands(UndoCommand.COMMAND_WORD);
        assertHelpForCommands(SelectCommand.COMMAND_WORD);

        assertFailedHelpForCommands(JokeCommand.COMMAND_WORD);
        assertFailedHelpForCommands("huh?");

    }

    public void assertHelpForCommands(String command) {
        String input = "help " + command;
        String expectedOutput = syntax.getSpecificCommandUsageMessage(command);
        assertCommandSuccess(input, expectedOutput, new ToDoList(),
                Collections.emptyList(), Task.ALL_CHAR);
    }

    public void assertFailedHelpForCommands(String command) {
        String input = "help " + command;
        String expectedOutput = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE);
        assertCommandFailure(input, expectedOutput);
    }

}
