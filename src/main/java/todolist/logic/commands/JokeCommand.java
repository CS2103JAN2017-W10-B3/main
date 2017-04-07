package todolist.logic.commands;

import todolist.commons.util.JokesUtil;
import todolist.logic.commands.exceptions.CommandException;

public class JokeCommand extends Command {
    
    public static final String COMMAND_WORD = "joke";
    
    private final String[] JOKES = JokesUtil.JOKES;
    
    private int randomGenerator() {
        return (int) Math.floor(Math.random()*JOKES.length);
    }
    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(JOKES[randomGenerator()]);
    }
    
    
}
