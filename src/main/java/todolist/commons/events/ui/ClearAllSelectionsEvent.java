package todolist.commons.events.ui;

import todolist.commons.events.BaseEvent;

/**
 * Indicates a request to clear all selections
 */
public class ClearAllSelectionsEvent extends BaseEvent {

    public ClearAllSelectionsEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
