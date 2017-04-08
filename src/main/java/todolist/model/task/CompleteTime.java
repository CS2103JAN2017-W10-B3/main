package todolist.model.task;

import java.time.LocalDateTime;

import todolist.commons.exceptions.IllegalValueException;
import todolist.commons.util.StringUtil;

//@@author A0122017Y
public class CompleteTime implements Time {

    LocalDateTime completeTime;
    
    /**
     * CompleteTime will be initiated with the current time at completing the task,
     * since the complete time is only for sorting purpose, less API is created.
     */
    public CompleteTime(LocalDateTime time) {
        this.completeTime = time;
    }
    
    public CompleteTime(String completeTimeArg) throws IllegalValueException {
        assert completeTimeArg != null;
        completeTimeArg = completeTimeArg.trim();
        try {
            this.completeTime = StringUtil.parseStringToTime(completeTimeArg);
        } catch (IllegalValueException e) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
    }
    
    /**
     * Obtain the LocalDateTime value of the complete time
     */
    @Override
    public LocalDateTime getTimeValue() {
        return this.completeTime;
    }

    @Override
    public int compareTo(Time time) {
        return this.completeTime.compareTo(time.getTimeValue());
    }
    
    @Override
    public String toString() {
        return this.completeTime.format(StringUtil.DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompleteTime // instanceof handles nulls
                        && this.completeTime.equals(((CompleteTime) other).completeTime)); // state check
    }
    
    @Override
    public boolean isBefore(Time time) {
        return this.getTimeValue().isBefore(time.getTimeValue());
    }

    @Override
    public boolean isAfter(Time time) {
        return this.getTimeValue().isAfter(time.getTimeValue());
    }

}
