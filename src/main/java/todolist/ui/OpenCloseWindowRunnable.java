package todolist.ui;

//@@author A0110791M
/*
 *  Runnable class that shows/hides/exits the app between threads.
 */
public class OpenCloseWindowRunnable implements Runnable {

    private MainWindow mainWindow;
    private String command;

    public OpenCloseWindowRunnable(MainWindow window, String commandString) {
        mainWindow = window;
        command = commandString;
    }

    @Override
    public void run() {
        switch (command) {
        case "open":
            mainWindow.show();
            break;
        case "close":
            mainWindow.hide();
            break;
        case "toggle":
            mainWindow.toggle();
            break;
        case "exit":
            mainWindow.handleExit();
        default:
            break;
        }
    }

}
// @@
