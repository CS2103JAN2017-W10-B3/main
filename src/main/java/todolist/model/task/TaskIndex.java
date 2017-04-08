package todolist.model.task;

public class TaskIndex {

    public static final int MIN_INDEX = 0;

    private Character taskChar;
    private Integer taskNumber;

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
