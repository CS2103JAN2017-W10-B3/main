package todolist.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import todolist.commons.core.Config;
import todolist.commons.core.GuiSettings;
import todolist.commons.events.ui.ExitAppRequestEvent;
import todolist.commons.util.FxViewUtil;
import todolist.logic.Logic;
import todolist.logic.parser.CommandSyntax;
import todolist.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow.fxml";

    private Stage primaryStage;
    private Logic logic;

    // @@author A0138628W

    // Independent Ui parts residing in this Ui container
    private Scroll scroll;
    // @@

    private TaskListPanel deadlineListPanel, eventListPanel, floatingListPanel, completeListPanel;

    private ResultDisplay resultDisplay;

    @FXML
    private AnchorPane eventListPlaceholder;

    @FXML
    private AnchorPane floatingListPlaceholder;

    @FXML
    private AnchorPane completeListPanelPlaceholder;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    // @@ author: A0138628W
    @FXML
    private MenuItem resultUp;

    @FXML
    private MenuItem resultDown;

    @FXML
    private MenuItem resultLeft;

    @FXML
    private MenuItem resultRight;

    @FXML
    private MenuItem eventUp;

    @FXML
    private MenuItem eventDown;

    @FXML
    private MenuItem floatingUp;

    @FXML
    private MenuItem floatingDown;

    @FXML
    private MenuItem deadlineUp;

    @FXML
    private MenuItem deadlineDown;

    @FXML
    private MenuItem completeUp;

    @FXML
    private MenuItem completeDown;

    @FXML
    private AnchorPane deadlineListPanelPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;

    @FXML
    private AnchorPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMaxSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);

        setAccelerators();
        setScroll();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        // @@ author: A0138628W
        setAccelerator(resultUp, KeyCombination.valueOf("CTRL+SHIFT+R"));
        setAccelerator(resultDown, KeyCombination.valueOf("CTRL+ALT+R"));
        setAccelerator(resultLeft, KeyCombination.valueOf("SHIFT+ALT+L"));
        setAccelerator(resultRight, KeyCombination.valueOf("SHIFT+ALT+R"));
        setAccelerator(eventUp, KeyCombination.valueOf("CTRL+SHIFT+E"));
        setAccelerator(eventDown, KeyCombination.valueOf("CTRL+ALT+E"));
        setAccelerator(floatingUp, KeyCombination.valueOf("CTRL+SHIFT+F"));
        setAccelerator(floatingDown, KeyCombination.valueOf("CTRL+ALT+F"));
        setAccelerator(deadlineUp, KeyCombination.valueOf("CTRL+SHIFT+D"));
        setAccelerator(deadlineDown, KeyCombination.valueOf("CTRL+ALT+D"));
        setAccelerator(completeUp, KeyCombination.valueOf("CTRL+SHIFT+C"));
        setAccelerator(completeDown, KeyCombination.valueOf("CTRL+ALT+C"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination
     *            the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea)
         * will
         * consume function-key events. Because CommandBox contains a TextField,
         * and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1)
         * will
         * not work when the focus is in them because the key event is consumed
         * by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and
         * open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    void fillInnerParts() {
        // @@author A0138628W
        deadlineListPanel = new DeadlineListPanel(getDeadlineListPlaceholder(), logic.getFilteredDeadlineList());
        eventListPanel = new EventListPanel(getEventListPlaceholder(), logic.getFilteredEventList());
        floatingListPanel = new FloatingListPanel(getFloatListPlaceholder(), logic.getFilteredFloatList());
        completeListPanel = new CompletedListPanel(getCompleteListPlaceholder(), logic.getFilteredCompleteList());
        resultDisplay = new ResultDisplay(getResultDisplayPlaceholder());
        new StatusBarFooter(getStatusbarPlaceholder(), Config.getToDoListFilePath());
        new CommandBox(getCommandBoxPlaceholder(), logic);
    }

    private AnchorPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    private AnchorPane getStatusbarPlaceholder() {
        return statusbarPlaceholder;
    }

    private AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    private AnchorPane getDeadlineListPlaceholder() {
        return deadlineListPanelPlaceholder;
    }

    private AnchorPane getEventListPlaceholder() {
        return eventListPlaceholder;
    }

    private AnchorPane getFloatListPlaceholder() {
        return floatingListPlaceholder;
    }

    private AnchorPane getCompleteListPlaceholder() {
        return completeListPanelPlaceholder;
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     *
     * @param iconSource
     *            e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    // @@author A0138628W
    private void setWindowMaxSize() {
        primaryStage.setMaximized(true);
    }

    private void setScroll() {
        scroll = new Scroll();
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    // @@ author: A0138628W
    @FXML
    public void handleHelp() {
        CommandSyntax commandSyntax = new CommandSyntax();
        resultDisplay.setDisplayAreaMessage(commandSyntax.getAllCommandUsageMessage());
    }

    @FXML
    public void handleResultUp() {
        scroll.getTextVerticalScrollbar(resultDisplay.getResultDisplayArea());
        if (scroll.isAvailable()) {
            if (scroll.getCurrentValue() != scroll.getMin()) {
                scroll.scrollDecrease();
            }
        }
    }

    @FXML
    public void handleResultDown() {
        scroll.getTextVerticalScrollbar(resultDisplay.getResultDisplayArea());
        if (scroll.isAvailable()) {
            if (scroll.getCurrentValue() != scroll.getMax()) {
                scroll.scrollIncrease();
            }
        }
    }

    @FXML
    public void handleResultLeft() {
        scroll.getTextHorizontalScrollbar(resultDisplay.getResultDisplayArea());
        if (scroll.isAvailable()) {
            if (scroll.getCurrentValue() != scroll.getMin()) {
                scroll.scrollDecrease();
            }
        }
    }

    @FXML
    public void handleResultRight() {
        scroll.getTextHorizontalScrollbar(resultDisplay.getResultDisplayArea());
        if (scroll.isAvailable()) {
            if (scroll.getCurrentValue() != scroll.getMax()) {
                scroll.scrollIncrease();
            }
        }
    }

    @FXML
    public void handleEventUp() {
        scroll.getListVerticalScrollbar(eventListPanel.getListView());
        if (scroll.isAvailable()) {
            if (scroll.getCurrentValue() != scroll.getMin()) {
                scroll.scrollDecrease();
            }
        }
    }

    @FXML
    public void handleEventDown() {
        scroll.getListVerticalScrollbar(eventListPanel.getListView());
        if (scroll.isAvailable()) {
            if (scroll.getCurrentValue() != scroll.getMax()) {
                scroll.scrollIncrease();
            }
        }
    }

    @FXML
    public void handleFloatingUp() {
        scroll.getListVerticalScrollbar(floatingListPanel.getListView());
        if (scroll.isAvailable()) {
            if (scroll.getCurrentValue() != scroll.getMin()) {
                scroll.scrollDecrease();
            }
        }
    }

    @FXML
    public void handleFloatingDown() {
        scroll.getListVerticalScrollbar(floatingListPanel.getListView());
        if (scroll.isAvailable()) {
            if (scroll.getCurrentValue() != scroll.getMax()) {
                scroll.scrollIncrease();
            }
        }
    }

    @FXML
    public void handleDeadlineUp() {
        scroll.getListVerticalScrollbar(deadlineListPanel.getListView());
        if (scroll.isAvailable()) {
            if (scroll.getCurrentValue() != scroll.getMin()) {
                scroll.scrollDecrease();
            }
        }
    }

    @FXML
    public void handleDeadlineDown() {
        scroll.getListVerticalScrollbar(deadlineListPanel.getListView());
        if (scroll.isAvailable()) {
            if (scroll.getCurrentValue() != scroll.getMax()) {
                scroll.scrollIncrease();
            }
        }
    }

    @FXML
    public void handleCompleteUp() {
        scroll.getListVerticalScrollbar(completeListPanel.getListView());
        if (scroll.isAvailable()) {
            if (scroll.getCurrentValue() != scroll.getMin()) {
                scroll.scrollDecrease();
            }
        }
    }

    @FXML
    public void handleCompleteDown() {
        scroll.getListVerticalScrollbar(completeListPanel.getListView());
        if (scroll.isAvailable()) {
            if (scroll.getCurrentValue() != scroll.getMax()) {
                scroll.scrollIncrease();
            }
        }
    }
    // @@

    // @@author A0110791M
    /*
     * Toggles the app window to show or hide (used by the hotkey)
     */
    void toggle() {
        if (primaryStage.isShowing()) {
            hide();
        } else {
            show();
        }
    }

    void show() {
        if (!primaryStage.isShowing()) {
            primaryStage.show();
        }
    }


    void hide() {
        if (primaryStage.isShowing()) {
            primaryStage.hide();
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    public void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public TaskListPanel getTaskListPanel() {
        return this.deadlineListPanel;
    }

    // @@ author: A0138628W
    public TaskListPanel getEventListPanel() {
        return this.eventListPanel;
    }

    public TaskListPanel getFloatListPanel() {
        return this.floatingListPanel;
    }

    public TaskListPanel getCompleteListPanel() {
        return this.completeListPanel;
    }

}
