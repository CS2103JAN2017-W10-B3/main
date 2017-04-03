package todolist.model;

import java.io.IOException;
import java.util.Set;

import todolist.commons.core.UnmodifiableObservableList;
import todolist.commons.exceptions.DataConversionException;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.Task;
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
     * Returns the filtered Task list as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList();

    UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList();

    UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatList();

    UnmodifiableObservableList<ReadOnlyTask> getListFromChar(Character type);

    // @@
    /** Updates the filter of the filtered Task list to show all Tasks */
    void updateFilteredListToShowAll();

    String getTagListToString();

    /**
     * Updates the filter of the filtered Task list to filter by the given
     * keywords
     */
    void updateFilteredTaskList(Set<String> keywords);

    // @@ author A0122017Y
    void updateFilteredTaskListToShowWithStatus(Status status);

    void updateFilteredTaskListToShowWithTag(Set<String> keywordSet);

    void completeTask(ReadOnlyTask taskToComplete);


    UnmodifiableObservableList<ReadOnlyTask> getCompletedList();

    //@@author A0110791M
    void changeDirectory(String filePath) throws IOException;

}
