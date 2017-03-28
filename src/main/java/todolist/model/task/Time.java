package todolist.model.task;

import java.time.LocalDateTime;

public interface Time extends Comparable<Time>{

    public static final String TIME_VALIDATION_REGEX = ".+";

    static Boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    LocalDateTime getTimeValue();
    
    @Override
    int compareTo(Time time);
}
