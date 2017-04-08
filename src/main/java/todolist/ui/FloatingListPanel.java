package todolist.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import todolist.model.task.ReadOnlyTask;

//@@author A0110791M
public class FloatingListPanel extends TaskListPanel {

    private static final String FXML = "FloatingListPanel.fxml";

    @FXML
    private ListView<ReadOnlyTask> floatingListView;

    public FloatingListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        taskListView = floatingListView;
        setConnections(taskList);
        addToPlaceholder(taskListPlaceholder);
    }

}
