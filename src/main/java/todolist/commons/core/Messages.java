package todolist.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command! \n"
            + "List of keywords available: \n"
            + "add: add a task;  select: select a task;  done: complete a task  edit: update a task \n"
            + "save: designate a path to save data;  undo: undo a command;  exit: exit the app";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";

}
