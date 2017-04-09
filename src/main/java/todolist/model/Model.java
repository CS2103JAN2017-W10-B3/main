package todolist.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import todolist.commons.core.UnmodifiableObservableList;
import todolist.commons.exceptions.DataConversionException;
import todolist.model.task.EndTime;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.StartTime;
import todolist.model.task.Task;
import todolist.model.task.TaskIndex;
import todolist.model.task.UniqueTaskList;
import todolist.model.task.UniqueTaskList.DuplicateTaskException;
import todolist.model.util.Status;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyToDoList newData);

    /** Returns the ToDoList */
    ReadOnlyToDoList getToDoList();

    /** Deletes the given Task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given Task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    //@@author A0110791M
    /** Imports all tasks from given file location
     * @throws IOException
     * @throws DataConversionException */
    void importTasks(String filePath) throws DataConversionException, IOException;
    //@@

    /**
     * Updates the Task located at {@code filteredTaskListIndex} with
     * {@code editedTask}.
     *
     * @throws DuplicateTaskException
     *             if updating the Task's details causes the Task to be
     *             equivalent to another existing Task in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code filteredTaskListIndex} < 0 or >= the size of the
     *             filtered list.
     */
    void updateTask(ReadOnlyTask taskToEdit, ReadOnlyTask editedTask) throws UniqueTaskList.DuplicateTaskException;

  //@@author A0143648Y
    /**
     * Returns the filtered Deadline Task list as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList();

    /**
     * Returns the filtered Event list as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList();

    /**
     * Returns the filtered Floating Task list as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatList();

    /**
     * Returns the filtered Task list containing all tasks as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getAllTaskList();

    /**
     * Returns the correct type of Task list from
     * {@code type}}
     */
    UnmodifiableObservableList<ReadOnlyTask> getListFromChar(Character type);

    /**
     * Updates current Task Indexes being selected from {@code indexes}
     */
    void updateSelectedIndexes(ArrayList<TaskIndex> indexes);

    /**
     * Updates current Task Indexes being selected from {@code index}
     */
    void updateSelectedIndexes(TaskIndex index);

    /**
     * Clear all current Task Indexes being selected
     */
    void clearSelectedIndexes();

    /**
     * Returns a list of current selected Task Indexes
     */
    ArrayList<TaskIndex> getSelectedIndexes();

    //@@
    /** Updates the filter of the filtered Task list to show all Tasks */
    void updateFilteredListToShowAll();

    int getSumTaskListed();

    /**
     * Updates the filter of the filtered Task list to filter by the given
     * keywords
     */
    void updateFilteredTaskList(Set<String> keywords);

    //@@ author A0122017Y
    /**
     * Updates the filter of the filtered Task list to filter by the given
     * start and end time values, or a given day
     */
    void updateFilteredTaskList(Optional<StartTime> startTime, Optional<EndTime> endTime, Optional<StartTime> today);

    /**
     * Updates the filter of the filtered Task list to filter by the given
     * status enum
     */
    void updateFilteredTaskListToShowWithStatus(Status status);

    /**
     * Mark a task to be completed
     */
    void completeTask(ReadOnlyTask taskToComplete);

    /**
     * Obtain the list of completed tasks
     */
    UnmodifiableObservableList<ReadOnlyTask> getCompletedList();


    //@@

    //@@author A0110791M
    void changeDirectory(String filePath) throws IOException;

}
