package todolist.logic.commands;

import java.util.Optional;

import todolist.commons.exceptions.IllegalValueException;
import todolist.commons.util.TimeUtil;
import todolist.logic.commands.exceptions.CommandException;
import todolist.model.task.EndTime;
import todolist.model.task.StartTime;

//@@author A0122017Y
public class ListByDurationCommand extends Command {

    private Optional<StartTime> startTime;
    private Optional<EndTime> endTime;
    private Optional<StartTime> today;

    public ListByDurationCommand(Optional<String> startTimeArg, Optional<String> beginningTimeArg,
            Optional<String> endTimeArg, Optional<String> deadlineArg) throws IllegalValueException {
        initDuration(startTimeArg, beginningTimeArg, endTimeArg, deadlineArg);
        if (startTime != null && endTime != null) {
            TimeUtil.isValidDuration(startTime.get(), endTime.get());
        }
    }

    private void initDuration(Optional<String> startTimeArg, Optional<String> beginningTimeArg,
            Optional<String> endTimeArg, Optional<String> deadlineArg) throws IllegalValueException {
        initStartTime(startTimeArg, beginningTimeArg);
        initEndTime(endTimeArg, deadlineArg);
    }

    private void initEndTime(Optional<String> endTimeArg, Optional<String> deadlineArg) throws IllegalValueException {
        if (endTimeArg.isPresent()) {
            this.endTime = Optional.of(new EndTime(endTimeArg.get()));
        } else if (deadlineArg.isPresent()) {
            this.endTime = Optional.of(new EndTime(deadlineArg.get()));
        } else {
            this.endTime = null;
        }
        
    }

    private void initStartTime(Optional<String> startTimeArg, Optional<String> beginningTimeArg) throws IllegalValueException {
        if (startTimeArg.isPresent()) {
            this.startTime = Optional.of(new StartTime(startTimeArg.get()));
            this.today = null;
        } else if (beginningTimeArg.isPresent()) {
            this.today = Optional.of(new StartTime(beginningTimeArg.get()));
            this.startTime = null;
        } else {
            this.startTime = null;
            this.today = null;
        }
        
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredTaskList(startTime, endTime, today);
        return new CommandResult(getMessageForTaskListShownSummary(model.getSumTaskListed()));
    }

}
