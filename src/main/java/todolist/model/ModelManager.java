package todolist.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import todolist.commons.core.ComponentManager;
import todolist.commons.core.LogsCenter;
import todolist.commons.core.UnmodifiableObservableList;
import todolist.commons.events.model.ToDoListChangedEvent;
import todolist.commons.events.storage.DirectoryChangedEvent;
import todolist.commons.exceptions.DataConversionException;
import todolist.commons.util.CollectionUtil;
import todolist.commons.util.FileUtil;
import todolist.model.task.EndTime;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.ReadOnlyTask.Category;
import todolist.model.task.StartTime;
import todolist.model.task.Task;
import todolist.model.task.TaskIndex;
import todolist.model.task.Time;
import todolist.model.task.UniqueTaskList;
import todolist.model.task.UniqueTaskList.TaskNotFoundException;
import todolist.model.util.SampleDataUtil;
import todolist.model.util.Status;
import todolist.storage.XmlFileStorage;

/**
 * Represents the in-memory model of the to-do list data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private static final Status INCOMPLETE_STATUS = Status.INCOMPLETE;
    private static final Status COMPLETE_STATUS = Status.COMPLETED;

    private static int taskCount;

    // @@author A0143648Y
    private final ToDoList todoList;
    private FilteredList<ReadOnlyTask> filteredFloats;
    private FilteredList<ReadOnlyTask> filteredDeadlines;
    private FilteredList<ReadOnlyTask> filteredEvents;
    private FilteredList<ReadOnlyTask> completedTasks;
    private ArrayList<TaskIndex> selectedIndexes;

    /**
     * Initializes a ModelManager with the given ToDoList and userPrefs.
     */
    public ModelManager(ReadOnlyToDoList todoList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(todoList, userPrefs);

        logger.fine("Initializing with to-do list: " + todoList + " and user prefs " + userPrefs);

        this.todoList = new ToDoList(todoList);
        this.selectedIndexes = new ArrayList<TaskIndex>();
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

    /** Raises an event to indicate the model has changed */
    private void indicateToDoListChanged() {
        raise(new ToDoListChangedEvent(todoList));
    }

    // @@author A0110791M
    /**
     * Changes the directory to the filePath specified and updates the current
     * todoList to match the destination
     */
    @Override
    public void changeDirectory(String filePath) throws IOException {
        FileUtil.createIfMissing(new File(filePath));
        indicateDirectoryChanged(filePath);
        indicateToDoListChanged();
    }

    /** Raises an event to indicate the user requests a new directory */
    private void indicateDirectoryChanged(String filePath) {
        raise(new DirectoryChangedEvent(filePath));
    }

    /** Imports all tasks from given filePath */
    @Override
    public void importTasks(String filePath) throws DataConversionException, IOException {
        Optional<ReadOnlyToDoList> todoListOptional;
        ReadOnlyToDoList initialData;

        todoListOptional = Optional.of(XmlFileStorage.loadDataFromSaveFile(new File(filePath)));
        if (!todoListOptional.isPresent()) {
            logger.info("Data file not found. Will be starting with a sample ToDoList");
        }
        initialData = todoListOptional.orElseGet(SampleDataUtil::getSampleToDoList);

        addImportedTasks(initialData);

        indicateToDoListChanged();
    }

    private void addImportedTasks(ReadOnlyToDoList importedList) {
        ObservableList<ReadOnlyTask> taskList = importedList.getTaskList();
        for (ReadOnlyTask task : taskList) {
            try {
                addTask(task);
            } catch (UniqueTaskList.DuplicateTaskException e) {
                continue;
            }
        }
    }

    public synchronized void addTask(ReadOnlyTask readOnlyTask) throws UniqueTaskList.DuplicateTaskException {
        Task task;
        task = new Task(readOnlyTask.getTitle(), readOnlyTask.getVenue().orElse(null),
                readOnlyTask.getStartTime().orElse(null), readOnlyTask.getEndTime().orElse(null),
                readOnlyTask.getUrgencyLevel().orElse(null), readOnlyTask.getDescription().orElse(null),
                readOnlyTask.getTags());
        todoList.addTask(task);
    }
    // @@

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        todoList.addTask(task);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        todoList.removeTask(target);
        indicateToDoListChanged();
    }

    // @@author A0143648Y
    @Override
    public void updateTask(ReadOnlyTask taskToEdit, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert taskToEdit != null;
        assert editedTask != null;
        todoList.updateTask(taskToEdit, editedTask);
        indicateToDoListChanged();
    }

    @Override
    public void updateSelectedIndexes(ArrayList<TaskIndex> indexes) {
        this.selectedIndexes = indexes;
    }

    @Override
    public void updateSelectedIndexes(TaskIndex index) {
        this.selectedIndexes.clear();
        this.selectedIndexes.add(index);
    }

    @Override
    public ArrayList<TaskIndex> getSelectedIndexes() {
        return this.selectedIndexes;
    }

    @Override
    public void clearSelectedIndexes() {
        this.selectedIndexes.clear();
    }

    // @@author A0122017Y
    /**
     * Synchronize the task lists with the respective task type.
     */
    private void syncTypeOfTasks() {
        filteredDeadlines = new FilteredList<>(this.todoList.getFilteredDeadlines());
        filteredFloats = new FilteredList<>(this.todoList.getFilteredFloats());
        filteredEvents = new FilteredList<>(this.todoList.getFilteredEvents());
        completedTasks = new FilteredList<>(this.todoList.getCompletedTasks());
        syncSumTaskListed();

    }

    /**
     * Mark a task in the list to completed
     */
    @Override
    public void completeTask(ReadOnlyTask taskToComplete) {
        todoList.completeTask(taskToComplete);
        indicateToDoListChanged();

    }

    // =========== Filtered Task List Accessors
    // =============================================================
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

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getAllTaskList() {
        return new UnmodifiableObservableList<>(todoList.getTaskList());
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getCompletedList() {
        SortedList<ReadOnlyTask> sortedComplete = new SortedList<>(completedTasks);
        sortedComplete.setComparator(ReadOnlyTask.getCompleteComparator());
        return new UnmodifiableObservableList<>(sortedComplete);
    }

    @Override
    public int getSumTaskListed() {
        return taskCount;
    }

    // @@author A0143648Y
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getListFromChar(Character type) {
        switch (type) {

        case Task.COMPLETE_CHAR:
            return getCompletedList();

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
        syncTaskWithTime();
        filteredDeadlines.setPredicate(null);
        filteredFloats.setPredicate(null);
        filteredEvents.setPredicate(null);
        completedTasks.setPredicate(null);
        syncSumTaskListed();
        indicateToDoListChanged();

    }

    private void syncTaskWithTime() {
        todoList.autoComplete();
        indicateToDoListChanged();

    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskList(Optional<StartTime> startTime,
            Optional<EndTime> endTime, Optional<StartTime> today) {
        updateFilteredTaskList(new PredicateExpression(new DurationQualifier(startTime, endTime, today)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredDeadlines.setPredicate(expression::satisfies);
        filteredFloats.setPredicate(expression::satisfies);
        filteredEvents.setPredicate(expression::satisfies);
        completedTasks.setPredicate(expression::satisfies);
        syncSumTaskListed();
        indicateToDoListChanged();

    }

    private void syncSumTaskListed() {
        int deadlineCounts = filteredDeadlines.size();
        int floatCounts = filteredFloats.size();
        int eventCounts = filteredEvents.size();
        int completeCounts = completedTasks.size();
        taskCount = deadlineCounts + floatCounts + eventCounts + completeCounts;

    }

    // @@
    @Override
    public void updateFilteredTaskListToShowWithStatus(Status status) {
        if (status == Status.ALL) {
            updateFilteredListToShowAll();
        } else {
            updateFilteredTaskList(new PredicateExpression(new StatusQualifier(status)));
        }
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

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

    //

    // @@author A0122017Y
    private class StatusQualifier implements Qualifier {

        Boolean status;

        StatusQualifier(Status status) {
            switch (status) {
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

    private class DurationQualifier implements Qualifier {

        private StartTime startTime;
        private StartTime today;
        private EndTime endTime;
        Boolean status;

        DurationQualifier(Optional<StartTime> start, Optional<EndTime> end, Optional<StartTime> day) {
            initStart(start);
            initToday(day);
            initEnd(end);
        }

        public void initStart(Optional<StartTime> start) {
            if (start != null) {
                this.startTime = start.get();
            } else {
                startTime = null;
            }
        }

        public void initToday(Optional<StartTime> today) {
            if (today != null) {
                this.today = today.get();
            } else {
                today = null;
            }
        }

        public void initEnd(Optional<EndTime> end) {
            if (end != null) {
                this.endTime = end.get();
            } else {
                endTime = null;
            }
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (task.getTaskCategory().equals(Category.DEADLINE)) {
                return isDeadlineWithinDuration(task);
            } else if (task.getTaskCategory().equals(Category.EVENT)) {
                return isEventWithinDuration(task);
            } else if (task.getTaskCategory().equals(Category.FLOAT)) {
                return isFloatingWithinDuration(task);
            } else {
                return false;
            }
        }

        private boolean isFloatingWithinDuration(ReadOnlyTask task) {
            if (task.getStartTime().isPresent()) {
                return isTimeInDuration(task.getStartTime().get()) ||
                        isOnTheDay(task.getStartTime().get());
            } else {
                return false;
            }
        }

        private boolean isOnTheDay(Time time) {
            if (today != null) {
                return time.isSameDay(today);
            } else {
                return false;
            }
        }

        private boolean isEventWithinDuration(ReadOnlyTask task) {
            return (isTimeInDuration(task.getStartTime().get()) &&
                    isTimeInDuration(task.getEndTime().get())) ||
                    isOnTheDay(task.getStartTime().get());
        }

        private boolean isDeadlineWithinDuration(ReadOnlyTask task) {
            return isTimeInDuration(task.getEndTime().get()) ||
                    isOnTheDay(task.getEndTime().get());
        }

        private boolean isTimeInDuration(Time time) {
            if (startTime != null && endTime == null) {
                return startTime.isBefore(time);
            } else if (endTime != null && startTime == null) {
                return endTime.isAfter(time);
            } else if (startTime != null && endTime != null) {
                return startTime.isBefore(time) && endTime.isAfter(time);
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return (status ? "within the period!" : "not within the period!");
        }

    }

    // @@
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
