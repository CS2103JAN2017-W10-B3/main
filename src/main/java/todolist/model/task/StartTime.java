package todolist.model.task;

import java.time.LocalDateTime;

import todolist.commons.exceptions.IllegalValueException;
import todolist.commons.util.StringUtil;

/**
 * Represents a Task's start time in the to-do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime implements Time {

    public static final String STARTTIME_VALIDATION_REGEX = ".+";

    private LocalDateTime startTime;

    public StartTime(String startTimeArg) throws IllegalValueException {
        assert startTimeArg != null;
        startTimeArg = startTimeArg.trim();
        try {
            this.startTime = StringUtil.parseStringToTime(startTimeArg);
        } catch (IllegalValueException e) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid task start time.
     */

    @Override
    public LocalDateTime getTimeValue() {
        return this.startTime;
    }

    @Override
    public String toString() {
        return this.startTime.format(StringUtil.DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                        && this.startTime.equals(((StartTime) other).startTime)); // state check
    }

    @Override
    public int hashCode() {
        return startTime.toString().hashCode();
    }

    @Override
    public int compareTo(Time time) {
        return this.getTimeValue().compareTo(time.getTimeValue());
    }

    public Boolean isValidDuration(EndTime endTime) {
        return this.getTimeValue().isBefore(endTime.getTimeValue());
    }

}
