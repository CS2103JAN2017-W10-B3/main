package todolist.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.Platform;

//@@author A0110791M
public class TrayIconListener implements ActionListener {

    private MainWindow mainWindow;

    public TrayIconListener(MainWindow window) {
        mainWindow = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand() == null ? "null" : e.getActionCommand();
        // action can only be these 4 types
        // assert (action == "Show" || action == "null" || action == "Hide" || action == "Exit");
        switch (action) {
        case "Show":
            openWindow();
            break;
        case "null":
            openWindow();
            break;
        case "Hide":
            closeWindow();
            break;
        case "Exit":
            exit();
            break;
        default:
            break;
        }
    }

    private void openWindow() {
        Platform.runLater(new OpenCloseWindowRunnable(mainWindow, "open"));
    }

    private void closeWindow() {
        Platform.runLater(new OpenCloseWindowRunnable(mainWindow, "close"));
    }

    private void exit() {
        Platform.runLater(new OpenCloseWindowRunnable(mainWindow, "exit"));
    }
}
//@@

