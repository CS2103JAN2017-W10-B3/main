package todolist.model.task;

//@@A0122017Y
import javafx.util.Pair;

public class TaskIndex {
    
    public static final int MIN_INDEX = 0;
    
    private Character taskChar;
    private Integer taskNumber;

    /**
     * Initialize TaskIndex using a Pair as input
     * @param pair
     */
    public TaskIndex(Pair<Character, Integer> pair) {
        taskNumber = pair.getValue();
        taskChar = pair.getKey();
    }
    
    /**
     * Initialize TaskIndex using an integer and a character as input
     * @param pair
     */
    public TaskIndex(Character chr, Integer num) {
        taskNumber = num;
        taskChar = chr;
    }
    
    /**
     * Obtain the task character
     */
    public Character getTaskChar() {
        return taskChar;
    }
    
    /**
     * Obtain the task integer index
     */
    public Integer getTaskNumber() {
        return taskNumber;
    }
    
    /**
     * Generate a new Pair object using the taskChar and taskNumber
     */
    public Pair<Character, Integer> toPair() {
        return new Pair(taskChar, taskNumber);
    }
    
    /**
     * Generate a String representation of the task index
     */
    @Override
    public String toString() {
        return taskChar.toString() + taskNumber.toString();
    }
    
    public static boolean isValidTaskIndex(Character chr, int index) {
        return (chr.equals(Task.DEADLINE_CHAR) ||
                chr.equals(Task.EVENT_CHAR) ||
                chr.equals(Task.FLOAT_CHAR)) && (index > MIN_INDEX);
    }
    
}
