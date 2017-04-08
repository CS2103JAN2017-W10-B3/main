package todolist.logic.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import todolist.logic.commands.AddCommand;
import todolist.logic.commands.HelpCommand;

public class CommandSyntaxTest {
    //@@author: A0138628W

    @Test
    public void specificCommandTest() {
        //test with add command
        CommandSyntax commandSyntaxTest = new CommandSyntax();
        String fromCST = commandSyntaxTest.getSpecificCommandUsageMessage("add");
        String fromAddCommand = AddCommand.MESSAGE_USAGE;
        assertEquals(fromCST, fromAddCommand);
    }

    @Test
    public void allCommandTest() {
        CommandSyntax commandSyntaxTest = new CommandSyntax();
        String fromCST = commandSyntaxTest.getAllCommandUsageMessage();
        String fromHelpCommand = new HelpCommand(commandSyntaxTest).SHOWING_HELP_MESSAGE;
        assertEquals(fromCST, fromHelpCommand);
    }
}
