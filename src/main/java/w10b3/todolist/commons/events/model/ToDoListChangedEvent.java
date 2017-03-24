package w10b3.todolist.commons.events.model;

import w10b3.todolist.commons.events.BaseEvent;
import w10b3.todolist.model.ReadOnlyToDoList;

/** Indicates the ToDoList in the model has changed*/
public class ToDoListChangedEvent extends BaseEvent {

    public final ReadOnlyToDoList data;

    public ToDoListChangedEvent(ReadOnlyToDoList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}