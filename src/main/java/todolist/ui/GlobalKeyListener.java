package todolist.ui;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javafx.application.Platform;

//@@author A0110791M
/*
 *  Listener class that waits for the Ctrl+T keystroke to activate the ToDoList
 */
public class GlobalKeyListener implements NativeKeyListener {

    private static Boolean isCtrlPressed = false, isTPressed = false;
    private MainWindow mainWindow;

    public GlobalKeyListener(MainWindow window) {
        mainWindow = window;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            isCtrlPressed = true;
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_T) {
            isTPressed = true;
        }

        if (isCtrlPressed && isTPressed) {
            toggleOpenCloseWindow();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            isCtrlPressed = false;
        } else if (e.getKeyCode() == NativeKeyEvent.VC_T) {
            isTPressed = false;
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // Not used
    }

    private void toggleOpenCloseWindow() {
        Platform.runLater(new OpenCloseWindowRunnable(mainWindow, "toggle"));
    }

}
//@@
