package todolist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import todolist.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label venue;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    //@@ author: A0138628W
    @FXML
    private Circle urgencyLevel;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;

    private ReadOnlyTask task;

    public TaskCard(ReadOnlyTask task, String displayedIndex) {
        super(FXML);
        this.task = task;
        title.setText(task.getTitle().toString());
        id.setText(displayedIndex + ". ");
        initialiseVenue();
        initialiseStartTime();
        initialiseEndTime();
        initialiseUrgencyLevel();
        initialiseDescription();
        initTags(task);
    }

    private void initialiseVenue() {
        if (task.getVenue().isPresent()
                && !task.getVenue().get().toString().isEmpty()) {
            venue.setManaged(true);
            venue.setText(task.getVenueString().trim());
        } else {
            venue.setManaged(false);
        }
    }

    private void initialiseStartTime() {
        startTime.setText(task.getStartTimeString().trim());
        if (task.getStartTime().isPresent()) {
            startTime.setManaged(true);
        } else {
            startTime.setManaged(false);
        }
    }

    private void initialiseEndTime() {
        endTime.setText(task.getEndTimeString().trim());
        if (task.getEndTime().isPresent()) {
            endTime.setManaged(true);
        } else {
            endTime.setManaged(false);
        }
    }

    //@@ author: A0138628W
    private void initialiseUrgencyLevel() {
        if (task.getUrgencyLevel().isPresent()) {
            urgencyLevel.setVisible(true);
            int level = task.getUrgencyLevelInt();
            if (level == 1) {
                urgencyLevel.setFill(Color.YELLOW);
            } else if (level == 2) {
                urgencyLevel.setFill(Color.ORANGE);
            } else if (level == 3) {
                urgencyLevel.setFill(Color.RED);
            } else {
                urgencyLevel.setVisible(false);
            }
        } else {
            urgencyLevel.setVisible(false);
        }
    }

    private void initialiseDescription() {
        if (task.getDescription().isPresent()
                && !task.getDescription().get().toString().isEmpty()) {
            description.setManaged(true);
            description.setText(task.getDescriptionString().trim());
        } else {
            description.setManaged(false);
        }
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
    //@@
}
