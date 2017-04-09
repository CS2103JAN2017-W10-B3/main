package todolist.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import todolist.model.task.ReadOnlyTask;

//@@author A0110791M
public class EventListPanel extends TaskListPanel {

    private static final String FXML = "EventListPanel.fxml";

    @FXML
    private ListView<ReadOnlyTask> eventListView;

    public EventListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        taskListView = eventListView;
        setConnections(taskList);
        addToPlaceholder(taskListPlaceholder);
        Platform.runLater(() -> {
            taskListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);});
    }

}
