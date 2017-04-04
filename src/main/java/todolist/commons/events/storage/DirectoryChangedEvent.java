package todolist.commons.events.storage;

import todolist.commons.events.BaseEvent;

//@@author A0110791M
/** Indicates a change in directory is requested by user */
public class DirectoryChangedEvent extends BaseEvent {

    public final String targetDirectory;

    public DirectoryChangedEvent (String targetDirectoryFilePath) {
        targetDirectory = targetDirectoryFilePath;
    }

    @Override
    public String toString() {
        return targetDirectory;
    }

}
//@@
