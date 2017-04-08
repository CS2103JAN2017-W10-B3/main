package guitests;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import todolist.logic.commands.AddCommand;
import todolist.logic.commands.HelpCommand;
import todolist.logic.parser.CommandSyntax;

public class HelpCommandTest extends ToDoListGuiTest {
    //@@author: A0138628W

    @Test
    public void help() {
        CommandSyntax commandSyntaxTest = new CommandSyntax();

        //run help -> show a list of all commands usage
        commandBox.runCommand("help");
        assertResultMessage(commandSyntaxTest.getAllCommandUsageMessage());
        assertResultMessage(HelpCommand.SHOWING_HELP_MESSAGE);

        //run help COMMAND -> specific command usage (use add)
        commandBox.runCommand("help add");
        assertResultMessage(commandSyntaxTest.getSpecificCommandUsageMessage("add"));
        assertResultMessage(AddCommand.MESSAGE_USAGE);

        //run help NotExistingCommand
        commandBox.runCommand("help scroll");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));

        //run with f1 accelerator
        mainMenu.useF1Accelerator();
        assertResultMessage(commandSyntaxTest.getAllCommandUsageMessage());

        //run help clear so that the message would change
        commandBox.runCommand("help clear");

        //clicking on help menu
        mainMenu.clickOn("Help", "F1");
        assertResultMessage(commandSyntaxTest.getAllCommandUsageMessage());
    }
}
