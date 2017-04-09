package guitests.guihandles;

import java.util.Arrays;

import guitests.GuiRobot;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import todolist.TestApp;

/**
 * Provides a handle to the main menu of the app.
 */
public class MainMenuHandle extends GuiHandle {
    public MainMenuHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public GuiHandle clickOn(String... menuText) {
        Arrays.stream(menuText).forEach((menuItem) -> guiRobot.clickOn(menuItem));
        return this;
    }

    public void useF1Accelerator() {
        guiRobot.push(KeyCode.F1);
        guiRobot.sleep(500);
    }

    public void useCtrlShiftRAccelerator() {
        guiRobot.push(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        guiRobot.sleep(500);
    }

    public void useCtrlAltRAccelerator() {
        guiRobot.push(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN));
        guiRobot.sleep(500);
    }

    public void useShiftAltLAccelerator() {
        guiRobot.push(new KeyCodeCombination(KeyCode.L, KeyCombination.SHIFT_DOWN, KeyCombination.ALT_DOWN));
        guiRobot.sleep(500);
    }

    public void useShiftAltRAccelerator() {
        guiRobot.push(new KeyCodeCombination(KeyCode.R, KeyCombination.SHIFT_DOWN, KeyCombination.ALT_DOWN));
        guiRobot.sleep(500);
    }

    public void useCtrlShiftEAccelerator() {
        guiRobot.push(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        guiRobot.sleep(500);
    }

    public void useCtrlAltEAccelerator() {
        guiRobot.push(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN));
        guiRobot.sleep(500);
    }

    public void useCtrlShiftFAccelerator() {
        guiRobot.push(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        guiRobot.sleep(500);
    }

    public void useCtrlAltFAccelerator() {
        guiRobot.push(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN));
        guiRobot.sleep(500);
    }

    public void useCtrlShiftDAccelerator() {
        guiRobot.push(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        guiRobot.sleep(500);
    }

    public void useCtrlAltDAccelerator() {
        guiRobot.push(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN));
        guiRobot.sleep(500);
    }

    public void useCtrlShiftCAccelerator() {
        guiRobot.push(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        guiRobot.sleep(500);
    }

    public void useCtrlAltCAccelerator() {
        guiRobot.push(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN));
        guiRobot.sleep(500);
    }
}
