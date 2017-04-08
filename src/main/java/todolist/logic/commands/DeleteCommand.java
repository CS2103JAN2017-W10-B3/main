package todolist.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import todolist.commons.core.EventsCenter;
import todolist.commons.core.Messages;
import todolist.commons.core.UnmodifiableObservableList;
import todolist.commons.events.ui.SelectMultipleTargetEvent;
import todolist.logic.commands.exceptions.CommandException;
import todolist.model.ReadOnlyToDoList;
import todolist.model.ToDoList;
import todolist.model.tag.UniqueTagList;
import todolist.model.task.Description;
import todolist.model.task.EndTime;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.StartTime;
import todolist.model.task.Task;
import todolist.model.task.TaskIndex;
import todolist.model.task.Title;
import todolist.model.task.UniqueTaskList;
import todolist.model.task.UniqueTaskList.TaskNotFoundException;
import todolist.model.task.UrgencyLevel;
import todolist.model.task.Venue;

// @@ A0143648Y
/**
 * Deletes a person identified using it's last displayed index from the address
 * book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing. \n"
            + "Parameters: TYPE (d, e or f) + INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " e1 \n";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Tasks deleted/updated: ";
    public static final String MESSAGE_DUPLICATE_TASK = "This delete command produces "
            + "duplicate tasks in your to-do list. \n"
            + "Please check the parameters you want to delete.";

    // @@ A0143648Y
    private final ArrayList<TaskIndex> filteredTaskListIndexes;
    private final DeleteTaskDescriptor deleteTaskDescriptor;
    private String messageSuccessful;

    private ReadOnlyToDoList originalToDoList;
    private CommandResult commandResultToUndo;

    public DeleteCommand(ArrayList<TaskIndex> filteredTaskListIndexes, DeleteTaskDescriptor deleteTaskDescriptor) {
        this.filteredTaskListIndexes = filteredTaskListIndexes;
        this.deleteTaskDescriptor = deleteTaskDescriptor;
    }

    @Override
    public CommandResult execute() throws CommandException {
        originalToDoList = new ToDoList(model.getToDoList());
        ArrayList<Task> listOfUpdatedTasks = new ArrayList<Task>();

        if (filteredTaskListIndexes.isEmpty()) {
            filteredTaskListIndexes.addAll(model.getSelectedIndexes());
            if (filteredTaskListIndexes.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_NO_TASK_SELECTED);
            }
        }
        ArrayList<ReadOnlyTask> tasksToDelete = getTasksToDelete();

        assert tasksToDelete != null;

        if (deleteTaskDescriptor.ifDeleteWholeTask()) {
            for (int count = 0; count < tasksToDelete.size(); count++) {
                try {
                    model.deleteTask(tasksToDelete.get(count));
                } catch (TaskNotFoundException tnfe) {
                    assert false : "The target task cannot be missing";
                }
            }
        } else {
            for (int count = 0; count < tasksToDelete.size(); count++) {
                try {
                    Task updatedTask = createDeletedTask(tasksToDelete.get(count));
                    model.updateTask(tasksToDelete.get(count), updatedTask);
                    listOfUpdatedTasks.add(updatedTask);
                } catch (UniqueTaskList.DuplicateTaskException dpe) {
                    throw new CommandException(MESSAGE_DUPLICATE_TASK);
                }
            }
            updateFilteredTaskListIndexes(listOfUpdatedTasks);

            assert !filteredTaskListIndexes.isEmpty();

            EventsCenter.getInstance().post(new SelectMultipleTargetEvent(filteredTaskListIndexes));
        }

        commandResultToUndo = new CommandResult(MESSAGE_DELETE_TASK_SUCCESS + messageSuccessful);

        updateUndoLists();

        return new CommandResult(
                MESSAGE_DELETE_TASK_SUCCESS + messageSuccessful);
    }

    private ArrayList<ReadOnlyTask> getTasksToDelete() throws CommandException {
        ArrayList<ReadOnlyTask> tasksToDelete = new ArrayList<ReadOnlyTask>();
        for (int count = 0; count < filteredTaskListIndexes.size(); count++) {
            List<ReadOnlyTask> lastShownList = model.getListFromChar(filteredTaskListIndexes.get(count).getTaskChar());
            int filteredTaskListIndex = filteredTaskListIndexes.get(count).getTaskNumber() - 1;
            if (lastShownList.size() < filteredTaskListIndex + 1) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            messageSuccessful = messageSuccessful + "["
                    + lastShownList.get(filteredTaskListIndex).getTitle().toString() + "] ";

            tasksToDelete.add(lastShownList.get(filteredTaskListIndex));
        }
        return tasksToDelete;
    }

    private void updateFilteredTaskListIndexes(ArrayList<Task> listOfEditedTasks) {
        filteredTaskListIndexes.clear();
        for (int count = 0; count < listOfEditedTasks.size(); count++) {
            UnmodifiableObservableList<ReadOnlyTask> listOfTask = model
                    .getListFromChar(listOfEditedTasks.get(count).getTaskChar());
            filteredTaskListIndexes.add(new TaskIndex(listOfEditedTasks.get(count).getTaskChar(),
                    listOfTask.indexOf(listOfEditedTasks.get(count)) + 1));
        }
    }

    private Task createDeletedTask(ReadOnlyTask taskToDelete) {
        assert taskToDelete != null;

        Title title = taskToDelete.getTitle();
        Venue updatedVenue = updateVenue(taskToDelete);
        StartTime updatedStartTime = updateStartTime(taskToDelete);
        EndTime updatedEndTime = updateEndTime(taskToDelete);
        UrgencyLevel updatedUrgencyLevel = updateUrgencyLevel(taskToDelete);
        Description updatedDescription = updateDescription(taskToDelete);
        UniqueTagList updatedTags = updateTags(taskToDelete);
        return new Task(title, updatedVenue, updatedStartTime, updatedEndTime, updatedUrgencyLevel, updatedDescription,
                updatedTags);

    }

    private Venue updateVenue(ReadOnlyTask taskToDelete) {
        if (!deleteTaskDescriptor.ifVenueDeleted && taskToDelete.getVenue().isPresent()) {
            return taskToDelete.getVenue().get();
        } else {
            return null;
        }
    }

    private StartTime updateStartTime(ReadOnlyTask taskToDelete) {
        if (!deleteTaskDescriptor.ifStartTimeDeleted && taskToDelete.getStartTime().isPresent()) {
            return taskToDelete.getStartTime().get();
        } else {
            return null;
        }
    }

    private EndTime updateEndTime(ReadOnlyTask taskToDelete) {
        if (!deleteTaskDescriptor.ifEndTimeDeleted && taskToDelete.getEndTime().isPresent()) {
            return taskToDelete.getEndTime().get();
        } else {
            return null;
        }
    }

    private Description updateDescription(ReadOnlyTask taskToDelete) {
        if (!deleteTaskDescriptor.ifDescriptionDeleted && taskToDelete.getDescription().isPresent()) {
            return taskToDelete.getDescription().get();
        } else {
            return null;
        }
    }

    private UrgencyLevel updateUrgencyLevel(ReadOnlyTask taskToDelete) {
        if (!deleteTaskDescriptor.ifUrgencyDeleted && taskToDelete.getUrgencyLevel().isPresent()) {
            return taskToDelete.getUrgencyLevel().get();
        } else {
            return null;
        }
    }

    private UniqueTagList updateTags(ReadOnlyTask taskToDelete) {
        if (!deleteTaskDescriptor.ifTagsDeleted) {
            return taskToDelete.getTags();
        } else {
            return new UniqueTagList(new HashSet<>(Collections.emptyList()));
        }

    }

    @Override
    public void updateUndoLists() {
        if (previousToDoLists == null) {
            previousToDoLists = new ArrayList<ReadOnlyToDoList>(UNDO_HISTORY_SIZE);
            previousCommandResults = new ArrayList<CommandResult>(UNDO_HISTORY_SIZE);
        }
        if (previousToDoLists.size() >= UNDO_HISTORY_SIZE) {
            previousToDoLists.remove(ITEM_TO_BE_REMOVED_FROM_HISTORY);
            previousCommandResults.remove(ITEM_TO_BE_REMOVED_FROM_HISTORY);
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        } else {
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        }
    }

    public String getTasksToString(ArrayList<ReadOnlyTask> tasks) {
        StringBuilder sb = new StringBuilder();
        for (ReadOnlyTask task : tasks) {
            sb.append("\n");
            sb.append(task.toString());
        }
        return sb.toString();

    }

    public static class DeleteTaskDescriptor {
        private boolean ifVenueDeleted;
        private boolean ifStartTimeDeleted;
        private boolean ifEndTimeDeleted;
        private boolean ifUrgencyDeleted;
        private boolean ifDescriptionDeleted;
        private boolean ifTagsDeleted;

        public boolean getVenue() {
            return this.ifVenueDeleted;
        }

        public boolean getStartTime() {
            return this.ifStartTimeDeleted;
        }

        public boolean getEndTime() {
            return this.ifEndTimeDeleted;
        }

        public boolean getUrgency() {
            return this.ifUrgencyDeleted;
        }

        public boolean getDescription() {
            return this.ifDescriptionDeleted;
        }

        public boolean getTags() {
            return this.ifTagsDeleted;
        }

        public void setVenue(boolean ifVenueDeleted) {
            this.ifVenueDeleted = ifVenueDeleted;
        }

        public void setStartTime(boolean ifStartTimeDeleted) {
            this.ifStartTimeDeleted = ifStartTimeDeleted;
        }

        public void setEndTime(boolean ifEndTimeDeleted) {
            this.ifEndTimeDeleted = ifEndTimeDeleted;
        }

        public void setUrgency(boolean ifUrgencyDeleted) {
            this.ifUrgencyDeleted = ifUrgencyDeleted;
        }

        public void setDescription(boolean ifDescriptionDeleted) {
            this.ifDescriptionDeleted = ifDescriptionDeleted;
        }

        public void setTags(boolean ifTagsDeleted) {
            this.ifTagsDeleted = ifTagsDeleted;
        }

        public boolean ifDeleteWholeTask() {
            return !(ifVenueDeleted || ifStartTimeDeleted || ifEndTimeDeleted
                || ifUrgencyDeleted || ifTagsDeleted || ifDescriptionDeleted);
        }

    }

}
