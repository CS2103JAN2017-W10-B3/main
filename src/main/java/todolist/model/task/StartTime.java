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

    //@@author A0122017Y
    /**
     * Check if the underlying time value is before or equal to the input
     * By default, time values on the same day are treated as equal
     */
    @Override
    public boolean isBefore(Time time) {
        return isSameDay(time) || !this.getTimeValue().isAfter(time.getTimeValue());
    }

    /**
     * Check if the underlying time value is after or equal to the input
     * By default, time values on the same day are treated as equal
     */
    @Override
    public boolean isAfter(Time time) {
        return isSameDay(time) || !this.getTimeValue().isBefore(time.getTimeValue());
    }

    /**
     * Check if the underlying time value is happening on the same day as the input
     */
    @Override
    public boolean isSameDay(Time time) {
        return startTime.getDayOfMonth() == time.getTimeValue().getDayOfMonth() &&
                startTime.getMonthValue() == time.getTimeValue().getMonthValue() &&
                startTime.getYear() == time.getTimeValue().getYear();
    }

}
