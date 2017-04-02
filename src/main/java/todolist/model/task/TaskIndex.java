package todolist.model.task;

//@@A0122017Y
import javafx.util.Pair;
import todolist.model.task.ReadOnlyTask.Category;

public class TaskIndex {
    
    private Character taskChar;
    private Integer taskNumber;

    public TaskIndex(Pair<Character, Integer> pair) {
        taskNumber = pair.getValue();
        taskChar = pair.getKey();
    }

    public TaskIndex(Character chr, Integer num) {
        taskNumber = num;
        taskChar = chr;
    }

    public Character getTaskChar() {
        return taskChar;
    }

    public Integer getTaskNumber() {
        return taskNumber;
    }

    public Category getTaskCate() {
        switch (taskChar) {
        case Task.DEADLINE_CHAR:
            return Category.DEADLINE;

        case Task.EVENT_CHAR:
            return Category.EVENT;

        case Task.FLOAT_CHAR:
            return Category.FLOAT;
        }
        return null;
    }
    
    public Pair<Character, Integer> toPair() {
        return new Pair(taskChar, taskNumber);
    }
    
    @Override
    public String toString() {
        return taskChar.toString() + taskNumber.toString();
    }
    
}
