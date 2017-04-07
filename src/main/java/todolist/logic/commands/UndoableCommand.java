package todolist.logic.commands;

import java.util.List;

import todolist.model.ReadOnlyToDoList;

//@@author A0143648Y
public abstract class UndoableCommand extends Command {
    protected static final int UNDO_HISTORY_SIZE = 3;
    protected static final int ITEM_TO_BE_REMOVED_FROM_HISTORY = 0;

    protected static List<ReadOnlyToDoList> previousToDoLists;
    protected static List<CommandResult> previousCommandResults;

    public abstract void updateUndoLists();
}
