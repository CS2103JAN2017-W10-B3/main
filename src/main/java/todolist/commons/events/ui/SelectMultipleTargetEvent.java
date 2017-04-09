package todolist.commons.events.ui;

import java.util.ArrayList;

import todolist.commons.events.BaseEvent;
import todolist.model.task.TaskIndex;
//@@ author A0143648Y
/**
 * Indicates a request to jump to the list of persons
 */
public class SelectMultipleTargetEvent extends BaseEvent {

    public final ArrayList<TaskIndex> targetIndexes;

    public SelectMultipleTargetEvent(ArrayList<TaskIndex> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
