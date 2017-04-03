package todolist.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import todolist.commons.core.ComponentManager;
import todolist.commons.core.LogsCenter;
import todolist.commons.core.UnmodifiableObservableList;
import todolist.commons.events.model.ToDoListChangedEvent;
import todolist.commons.events.storage.DirectoryChangedEvent;
import todolist.commons.util.CollectionUtil;
import todolist.model.tag.Tag;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.Task;
import todolist.model.task.UniqueTaskList;
import todolist.model.task.UniqueTaskList.TaskNotFoundException;
import todolist.model.util.Status;

/**
 * Represents the in-memory model of the to-do list data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private static final Status INCOMPLETE_STATUS = Status.INCOMPLETE;
    private static final Status COMPLETE_STATUS = Status.COMPLETED;

    // @@author A0143648Y
    private final ToDoList todoList;
    private FilteredList<ReadOnlyTask> filteredFloats;
    private FilteredList<ReadOnlyTask> filteredDeadlines;
    private FilteredList<ReadOnlyTask> filteredEvents;

    private FilteredList<ReadOnlyTask> completedTasks;

    /**
     * Initializes a ModelManager with the given ToDoList and userPrefs.
     */
    public ModelManager(ReadOnlyToDoList todoList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(todoList, userPrefs);

        logger.fine("Initializing with to-do list: " + todoList + " and user prefs " + userPrefs);

        this.todoList = new ToDoList(todoList);
        syncTypeOfTasks();
    }

    // @@
    public ModelManager() {
        this(new ToDoList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyToDoList newData) {
        todoList.resetData(newData);
        indicateToDoListChanged();
    }

    @Override
    public ReadOnlyToDoList getToDoList() {
        return todoList;
    }

    @Override
    public String getTagListToString() {
        return todoList.getTagListToString();
    }

    /** Raises an event to indicate the model has changed */
    private void indicateToDoListChanged() {
        raise(new ToDoListChangedEvent(todoList));
    }

    //@@author A0110791M
    /** Raises an event to indicate the user requests a new directory */
    @Override
    public void indicateDirectoryChanged(String directoryPath) {
        raise(new DirectoryChangedEvent(directoryPath));
    }
    //@@

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        todoList.removeTask(target);
        syncTypeOfTasks();
        updateFilteredTaskListToShowWithStatus(INCOMPLETE_STATUS);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        todoList.addTask(task);
        syncTypeOfTasks();
        updateFilteredTaskListToShowWithStatus(INCOMPLETE_STATUS);
        indicateToDoListChanged();
    }

    // @@author A0143648Y
    @Override
    public void updateTask(ReadOnlyTask taskToEdit, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert taskToEdit != null;
        assert editedTask != null;
        todoList.updateTask(taskToEdit, editedTask);
        syncTypeOfTasks();
        updateFilteredTaskListToShowWithStatus(INCOMPLETE_STATUS);
        indicateToDoListChanged();
    }

    //@@author A0122017Y
    private void syncTypeOfTasks() {
        filteredDeadlines = new FilteredList<>(this.todoList.getFilteredDeadlines());
        filteredFloats = new FilteredList<>(this.todoList.getFilteredFloats());
        filteredEvents = new FilteredList<>(this.todoList.getFilteredEvents());
        completedTasks = new FilteredList<>(this.todoList.getCompletedTasks());

    }

    @Override
    public void completeTask(ReadOnlyTask taskToComplete) {
        todoList.completeTask(taskToComplete);
        updateFilteredTaskListToShowWithStatus(INCOMPLETE_STATUS);
        indicateToDoListChanged();
    }

    // =========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList() {
        SortedList<ReadOnlyTask> sortedDeadlines = new SortedList<>(filteredDeadlines);
        sortedDeadlines.setComparator(ReadOnlyTask.getDeadlineComparator());
        return new UnmodifiableObservableList<>(sortedDeadlines);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList() {
        SortedList<ReadOnlyTask> sortedEvents = new SortedList<>(filteredEvents);
        sortedEvents.setComparator(ReadOnlyTask.getEventComparator());
        return new UnmodifiableObservableList<>(sortedEvents);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatList() {
        SortedList<ReadOnlyTask> sortedFloats = new SortedList<>(filteredFloats);
        sortedFloats.setComparator(ReadOnlyTask.getFloatingComparator());
        return new UnmodifiableObservableList<>(sortedFloats);
    }

    public UnmodifiableObservableList<ReadOnlyTask> getAllTaskList() {
        return new UnmodifiableObservableList<>(todoList.getTaskList());
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getCompletedList() {
        SortedList<ReadOnlyTask> sortedComplete = new SortedList<>(completedTasks);
        sortedComplete.setComparator(ReadOnlyTask.getCompleteComparator());
        return new UnmodifiableObservableList<>(sortedComplete);
    }
    //@@author A0143648Y
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getListFromChar(Character type) {
        switch (type) {

        case Task.DEADLINE_CHAR:
            return getFilteredDeadlineList();

        case Task.EVENT_CHAR:
            return getFilteredEventList();

        case Task.FLOAT_CHAR:
            return getFilteredFloatList();
        }
        return getAllTaskList();
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredDeadlines.setPredicate(null);
        filteredFloats.setPredicate(null);
        filteredEvents.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredDeadlines.setPredicate(expression::satisfies);
        filteredFloats.setPredicate(expression::satisfies);
        filteredEvents.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredTaskListToShowWithStatus(Status status) {
        if (status == Status.ALL) {
            updateFilteredListToShowAll();
        } else {
            updateFilteredTaskList(new PredicateExpression(new StatusQualifier(status)));
        }
    }

    @Override
    public void updateFilteredTaskListToShowWithTag(Set<String> keywordSet) {
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(keywordSet)));

    }


    // ========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);

        @Override
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);

        @Override
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        // @@author A0143648Y
        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> hasContainedKeyword(task.getTitle().toString(), keyword)
                            || hasContainedKeyword(task.getStartTimeString(), keyword)
                            || hasContainedKeyword(task.getEndTimeString(), keyword)
                            || hasContainedKeyword(task.getDescriptionString(), keyword)
                            || hasContainedKeyword(task.getVenueString(), keyword))
                    .findAny().isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    //@@author A0122017Y
    private class StatusQualifier implements Qualifier {

        Boolean status;

        StatusQualifier(Status status) {
            switch(status) {
            case COMPLETED:
                this.status = true;
                break;
            case INCOMPLETE:
                this.status = false;
                break;
            default:
                this.status = false;
            }
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.isTaskCompleted().equals(status);
        }

        @Override
        public String toString() {
            return (status ? "completed" : "not yet completed");
        }

    }
    private class TagQualifier implements Qualifier {

        Set<String> tags;
        Boolean status;

        TagQualifier(Set<String> tags) {
            this.tags = tags;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            for (Tag tag:task.getTags()) {
                if (this.tags.contains(tag.tagName)) {
                    status = true;
                }
            }
            return status;
        }

        @Override
        public String toString() {
            return (status ? "contains tag!" : "not containing tag!");
        }

    }
    //@@
    private boolean hasContainedKeyword(String searchMe, String findMe) {
        searchMe = searchMe.toLowerCase();
        findMe = findMe.toLowerCase();
        int searchMeLength = searchMe.length();
        int findMeLength = findMe.length();
        boolean foundIt = false;
        for (int i = 0; i <= (searchMeLength - findMeLength); i++) {
            if (searchMe.regionMatches(true, i, findMe, 0, findMeLength)) {
                foundIt = true;
                break;
            }
        }
        return foundIt;
    }

}
