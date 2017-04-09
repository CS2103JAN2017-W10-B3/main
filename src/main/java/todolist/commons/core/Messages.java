package todolist.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command! \n" + "List of keywords available: \n"
            + "add: add a task;  select: select tasks;  done: complete tasks  edit: update tasks \n"
            + "delete: delete tasks or tasks' parmameters; help: show user guide \n"
            + "find: search for tasks with specific keywords; list: list all tasks or tasks of a specific period \n "
            + "import : import data from a file; changedir: change data storage path to a new path"
            + "save: designate a path to save data;  undo: undo a command;  exit: exit the app; joke: ???";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_NO_TASK_SELECTED = "Invalid command format! No task has been selected! \n";

}
