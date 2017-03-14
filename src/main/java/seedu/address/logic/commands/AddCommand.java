package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.Venue;

/**
 * Adds a Task to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Task to the to-do list. "
            + "Parameters: TITLE v/VENUE s/STARTTIME e/ENDTIME  [t/TAG]...\n" + "Example: " + COMMAND_WORD
            + " CS2103 Tutorial v/COM1-B110 s/March 8,10.00am e/March 8, 11.00am t/lesson t/project";

    public static final String MESSAGE_SUCCESS = "New Task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This Task already exists in the address book";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String title, Optional<String> venue, Optional<String> starttime, Optional<String> endtime,
            Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        Title tempTitle = new Title(title);
        Venue tempVenue = null;
        StartTime tempStartTime = null;
        EndTime tempEndTime = null;

        if (venue.isPresent()) {
            tempVenue = new Venue(venue.get());
        } else {
            tempVenue = new Venue("");
        }
        if (starttime.isPresent()) {
            tempStartTime = new StartTime(starttime.get());
        } else {
            tempStartTime = new StartTime("");
        }
        if (endtime.isPresent()) {
            tempEndTime = new EndTime(endtime.get());
        } else {
            tempEndTime = new EndTime("");
        }

        this.toAdd = new Task(tempTitle, tempVenue, tempStartTime, tempEndTime, new UniqueTagList(tagSet));
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
