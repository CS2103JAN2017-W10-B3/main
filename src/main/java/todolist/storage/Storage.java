package todolist.storage;

import java.io.IOException;
import java.util.Optional;

import todolist.commons.events.model.ToDoListChangedEvent;
import todolist.commons.events.storage.DataSavingExceptionEvent;
import todolist.commons.events.storage.DirectoryChangedEvent;
import todolist.commons.exceptions.DataConversionException;
import todolist.model.ReadOnlyToDoList;
import todolist.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ToDoListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getToDoListFilePath();

    @Override
    Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, IOException;

    @Override
    void saveToDoList(ReadOnlyToDoList toDoList) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleToDoListChangedEvent(ToDoListChangedEvent abce);

    //@@author A0110791M
    /**
     * Attempts to change the present working directory to what the user specifies
     * @param targetDirectory   is the file path
     */
    void handleDirectoryChangedEvent(DirectoryChangedEvent dce);
}
