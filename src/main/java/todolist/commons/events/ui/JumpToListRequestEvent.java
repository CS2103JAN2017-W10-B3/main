package todolist.commons.events.ui;

import todolist.commons.events.BaseEvent;
import todolist.model.task.TaskIndex;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final TaskIndex targetIndex;

    public JumpToListRequestEvent(TaskIndex targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
