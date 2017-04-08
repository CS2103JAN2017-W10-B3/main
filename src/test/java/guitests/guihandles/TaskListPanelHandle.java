package guitests.guihandles;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import todolist.TestApp;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.ReadOnlyTask.Category;
import todolist.model.task.Task;
import todolist.testutil.TestUtil;


//@@author A0110791M
/**
 * Provides a handle for the panel containing the task list.
 */
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String TASK_LIST_VIEW_ID = "#taskListView";
    private static final String EVENT_LIST_VIEW_ID = "#eventListView";
    private static final String DEADLINE_LIST_VIEW_ID = "#deadlineListView";
    private static final String FLOAT_LIST_VIEW_ID = "#floatingListView";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedTasks(Category taskType) {
        ListView<ReadOnlyTask> taskList = getListView(taskType);
        return taskList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyTask> getListView() {
        return getNode(EVENT_LIST_VIEW_ID);
    }

    public ListView<ReadOnlyTask> getListView(Category taskType) {
        ListView<ReadOnlyTask> listView;
        switch (taskType) {
        case EVENT:
            listView = getNode(EVENT_LIST_VIEW_ID);
            break;
        case DEADLINE:
            listView = getNode(DEADLINE_LIST_VIEW_ID);
            break;
        case FLOAT:
            listView = getNode(FLOAT_LIST_VIEW_ID);
            break;
        default:
            listView = new ListView<ReadOnlyTask>();
            break;
        }
        return listView;
    }

    /**
     * Returns true if the list is showing the task details correctly and in
     * correct order.
     *
     * @param tasks
     *            A list of task in the correct order.
     */
    public boolean isListMatching(Category taskType, ReadOnlyTask... tasks) {
        return this.isListMatching(taskType, 0, tasks);
    }

    /**
     * Returns true if the list is showing the task details correctly and in
     * correct order.
     *
     * @param startPosition
     *            The starting position of the sub list.
     * @param tasks
     *
     *            A list of task in the correct order.
     */
    public boolean isListMatching(Category taskType, int startPosition, ReadOnlyTask... tasks)
            throws IllegalArgumentException {
        if (tasks.length + startPosition != getListView(taskType).getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView(taskType).getItems().size() - 1) + " tasks.");
        }
        assertTrue(this.containsInOrder(taskType, startPosition, tasks));
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView(taskType).scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getTaskCardHandle(taskType, startPosition + i), tasks[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView(Category taskType) {
        Point2D point = TestUtil.getScreenMidPoint(getListView(taskType));
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order)
     * at position {@code startPosition}.
     */
    public boolean containsInOrder(Category taskType, int startPosition, ReadOnlyTask... tasks) {
        List<ReadOnlyTask> tasksInList = getListView(taskType).getItems();

        // Return false if the list in panel is too short to contain the given
        // list
        if (startPosition + tasks.length > tasksInList.size()) {
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getTitle().toString().equals(tasks[i].getTitle().toString())) {
                return false;
            }
        }

        return true;
    }

    public TaskCardHandle navigateToTask(Category taskType, String title) {
        guiRobot.sleep(500); // Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getListView(taskType).getItems().stream()
                .filter(p -> p.getTitle().toString().equals(title))
                .findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Title not found: " + title);
        }

        return navigateToTask(taskType, task.get());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public TaskCardHandle navigateToTask(Category taskType, ReadOnlyTask task) {
        int index = getTaskIndex(taskType, task);

        guiRobot.interact(() -> {
            getListView(taskType).scrollTo(index);
            guiRobot.sleep(150);
            getListView(taskType).getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTaskCardHandle(task);
    }

    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in
     * the list.
     */
    public int getTaskIndex(Category taskType, ReadOnlyTask targetTask) {
        List<ReadOnlyTask> tasksInList = getListView(taskType).getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if (tasksInList.get(i).getTitle().equals(targetTask.getTitle())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a task from the list by index
     */
    public ReadOnlyTask getTask(Category taskType, int index) {
        return getListView(taskType).getItems().get(index);
    }

    public TaskCardHandle getTaskCardHandle(Category taskType, int index) {
        return getTaskCardHandle(new Task(getListView(taskType).getItems().get(index)));
    }

    public TaskCardHandle getTaskCardHandle(ReadOnlyTask task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new TaskCardHandle(guiRobot, primaryStage, n).isSameTask(task))
                .findFirst();
        if (taskCardNode.isPresent()) {
            return new TaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTasks(Category taskType) {
        return getListView(taskType).getItems().size();
    }
}
