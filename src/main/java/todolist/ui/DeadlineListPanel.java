package todolist.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import todolist.model.task.ReadOnlyTask;

//@@author A0110791M
public class DeadlineListPanel extends TaskListPanel {

    private static final String FXML = "DeadlineListPanel.fxml";

    @FXML
    private ListView<ReadOnlyTask> deadlineListView;

    public DeadlineListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        taskListView = deadlineListView;
        setConnections(taskList);
        addToPlaceholder(taskListPlaceholder);
        Platform.runLater(() -> {
            taskListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        });
    }

}
