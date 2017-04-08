package todolist.logic.commands;

import java.util.Optional;

import todolist.commons.exceptions.IllegalValueException;
import todolist.commons.util.TimeUtil;
import todolist.logic.commands.exceptions.CommandException;
import todolist.model.task.EndTime;
import todolist.model.task.StartTime;

public class ListByDuration extends Command {
    
    private final Optional<String> startTimeArg;
    private final Optional<String> beginningTimeArg;
    private final Optional<String> endTimeArg;
    private final Optional<String> deadlineArg;
    
    private Optional<StartTime> startTime;
    private Optional<EndTime> endTime;
    
    

    public ListByDuration(Optional<String> startTimeArg, Optional<String> beginningTimeArg,
            Optional<String> endTimeArg, Optional<String> deadlineArg) throws IllegalValueException {
        this.startTimeArg = startTimeArg;
        this.beginningTimeArg = beginningTimeArg;
        this.endTimeArg = endTimeArg;
        this.deadlineArg = deadlineArg;
        initDuration(startTimeArg, beginningTimeArg, endTimeArg, deadlineArg);
        if (startTime.isPresent() && endTime.isPresent()) {
            TimeUtil.isValidDuration(startTime.get(), endTime.get());
        }
    }

    private void initDuration(Optional<String> startTimeArg, Optional<String> beginningTimeArg,
            Optional<String> endTimeArg, Optional<String> deadlineArg) throws IllegalValueException {
        if (startTimeArg.isPresent() && !beginningTimeArg.isPresent()) {
            this.startTime = Optional.of(new StartTime(startTimeArg.get()));
        } else if (!startTimeArg.isPresent() && beginningTimeArg.isPresent()) {
            this.startTime = Optional.of(new StartTime(beginningTimeArg.get()));
        } else {
            this.startTime = Optional.empty();
        }
        
        if (endTimeArg.isPresent() && !deadlineArg.isPresent()) {
            this.endTime = Optional.of(new EndTime(endTimeArg.get()));
        } else if (!endTimeArg.isPresent() && deadlineArg.isPresent()) {
            this.endTime = Optional.of(new EndTime(deadlineArg.get()));
        } else {
            this.endTime = Optional.empty();
        }
        
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredTaskList(startTime, endTime);
        return new CommandResult(getMessageForTaskListShownSummary(model.getSumTaskListed()));
    }

}
