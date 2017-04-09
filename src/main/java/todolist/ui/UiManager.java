package todolist.ui;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import todolist.MainApp;
import todolist.commons.core.ComponentManager;
import todolist.commons.core.Config;
import todolist.commons.core.LogsCenter;
import todolist.commons.events.storage.DataSavingExceptionEvent;
import todolist.commons.events.ui.ClearAllSelectionsEvent;
import todolist.commons.events.ui.JumpToListRequestEvent;
import todolist.commons.events.ui.SelectMultipleTargetEvent;
import todolist.commons.events.ui.ShowHelpRequestEvent;
import todolist.commons.events.ui.TaskPanelSelectionChangedEvent;
import todolist.commons.util.StringUtil;
import todolist.logic.Logic;
import todolist.model.UserPrefs;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";
    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;
    private TrayIcon trayIcon;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        // Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));
        createTrayIcon(primaryStage);

        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic);
            mainWindow.show(); // This should be called before creating other UI
                               // parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    public void createTrayIcon(final Stage stage) {
        if (SystemTray.isSupported()) {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    //hide(stage);
                    stage.hide();
                }
            });
            // create a action listener to listen for default action executed on the tray icon
            final ActionListener closeListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            };

            ActionListener showListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.show();
                        }
                    });
                }
            };

            // create a popup menu
            PopupMenu popup = new PopupMenu();

            MenuItem showItem = new MenuItem("Show ToDoList");
            showItem.addActionListener(showListener);
            popup.add(showItem);

            MenuItem closeItem = new MenuItem("Exit");
            closeItem.addActionListener(closeListener);
            popup.add(closeItem);

            // construct a TrayIcon
            try {
                trayIcon = new TrayIcon(ImageIO.read(new File(ICON_APPLICATION)), "Title", popup);
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
            // set the TrayIcon properties
            //trayIcon.addActionListener(showListener);
            // ...
            // add the tray image
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
            // ...
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
        // mainWindow.releaseResources();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
            String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    // ==================== Event Handling Code
    // ===============================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        char listType = event.targetIndex.getTaskChar();
        if (listType == 'e' || listType == 'E') {
            mainWindow.getEventListPanel().scrollTo(event.targetIndex.getTaskNumber());
            mainWindow.getFloatListPanel().clearSelection();
            mainWindow.getTaskListPanel().clearSelection();
            mainWindow.getCompleteListPanel().clearSelection();
        } else if (listType == 'f' || listType == 'F') {
            mainWindow.getFloatListPanel().scrollTo(event.targetIndex.getTaskNumber());
            mainWindow.getEventListPanel().clearSelection();
            mainWindow.getTaskListPanel().clearSelection();
            mainWindow.getCompleteListPanel().clearSelection();
        } else if (listType == 'd' || listType == 'D') {
            mainWindow.getTaskListPanel().scrollTo(event.targetIndex.getTaskNumber());
            mainWindow.getFloatListPanel().clearSelection();
            mainWindow.getEventListPanel().clearSelection();
            mainWindow.getCompleteListPanel().clearSelection();
        } else if (listType == 'c' || listType == 'C') {
            mainWindow.getCompleteListPanel().scrollTo(event.targetIndex.getTaskNumber());
            mainWindow.getFloatListPanel().clearSelection();
            mainWindow.getTaskListPanel().clearSelection();
            mainWindow.getEventListPanel().clearSelection();
        }
    }

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        // mainWindow.loadTaskPage(event.getNewSelection());
    }

    // @@ A0143648Y
    @Subscribe
    private void handleSelectMultipleTargetEvent(SelectMultipleTargetEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        for (int count = event.targetIndexes.size() - 1; count >= 0; count--) {
            char listType = event.targetIndexes.get(count).getTaskChar();
            if (count == event.targetIndexes.size() - 1) {
                mainWindow.getEventListPanel().clearSelection();
                mainWindow.getFloatListPanel().clearSelection();
                mainWindow.getTaskListPanel().clearSelection();
                mainWindow.getCompleteListPanel().clearSelection();
            }
            if (listType == 'e' || listType == 'E') {
                mainWindow.getEventListPanel().selectTheTarget(event.targetIndexes.get(count).getTaskNumber() - 1);
            } else if (listType == 'f' || listType == 'F') {
                mainWindow.getFloatListPanel().selectTheTarget(event.targetIndexes.get(count).getTaskNumber() - 1);
            } else if (listType == 'd' || listType == 'D') {
                mainWindow.getTaskListPanel().selectTheTarget(event.targetIndexes.get(count).getTaskNumber() - 1);
            } else if (listType == 'c' || listType == 'C') {
                mainWindow.getCompleteListPanel().selectTheTarget(event.targetIndexes.get(count).getTaskNumber() - 1);
            }
        }
    }

    @Subscribe
    private void handleClearAllSelectionsEvent(ClearAllSelectionsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getEventListPanel().clearSelection();
        mainWindow.getFloatListPanel().clearSelection();
        mainWindow.getTaskListPanel().clearSelection();
        mainWindow.getCompleteListPanel().clearSelection();
    }
}
