package todolist.commons.events.ui;

import javafx.util.Pair;
import todolist.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final Pair<Character, Integer> targetIndex;

    public JumpToListRequestEvent(Pair<Character, Integer> targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
