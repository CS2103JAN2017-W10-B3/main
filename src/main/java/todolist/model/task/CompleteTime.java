package todolist.model.task;

import java.time.LocalDateTime;

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

}
