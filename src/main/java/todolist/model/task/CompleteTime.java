package todolist.model.task;

import java.time.LocalDateTime;

public class CompleteTime implements Time {

    LocalDateTime completeTime;

    public CompleteTime(LocalDateTime time) {
        this.completeTime = time;
    }

    @Override
    public LocalDateTime getTimeValue() {
        return this.completeTime;
    }

    @Override
    public int compareTo(Time time) {
        return this.completeTime.compareTo(time.getTimeValue());
    }

}
