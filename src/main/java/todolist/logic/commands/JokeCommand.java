package todolist.logic.commands;

import todolist.commons.util.JokesUtil;
import todolist.logic.commands.exceptions.CommandException;
//@@author A0122017Y
public class JokeCommand extends Command {

    public static final String COMMAND_WORD = "joke";

    private static final String[] JOKES = JokesUtil.JOKES;

    private static final String MESSAGE_SUCCESS = "You must be laughing :D";

    private int randomGenerator() {
        return (int) Math.floor(Math.random()*JOKES.length);
    }

    private String jokeGenerator() {
        return JOKES[randomGenerator()] + "\n" + MESSAGE_SUCCESS;
    }

    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(jokeGenerator());
    }

}
