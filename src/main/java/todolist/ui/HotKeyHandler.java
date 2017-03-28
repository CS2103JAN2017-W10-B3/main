package todolist.ui;

import java.util.LinkedList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

// @@ A0110791M
public class HotKeyHandler implements EventHandler<KeyEvent> {

    private LinkedList<String> commandsHistory;
    private static final int COMMANDS_HISTORY_SIZE = 10;

    private AutoCompleteTextField textField;
    private ContextMenu commandsHistoryList;

    public HotKeyHandler (AutoCompleteTextField autoCompleteTextField, ContextMenu popupList) {
        commandsHistory = new LinkedList<String>();
        textField = autoCompleteTextField;
        commandsHistoryList = popupList;
    }

    @Override
    public void handle (KeyEvent key) {
        KeyCode keyCode = key.getCode();
        switch (keyCode) {
            case DOWN:
                showCommandsHistory();
                return;
            default:
                return;
        }
    }

    private void showCommandsHistory () {
        commandsHistoryList.getItems().clear();
        commandsHistoryList.getItems().addAll(getCommandsHistoryMenu());
        commandsHistoryList.show(textField, Side.BOTTOM, textField.getCaretPosition(), 0);
    }

    private List<CustomMenuItem> getCommandsHistoryMenu () {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        for (int i = commandsHistory.size()-1; i >= 0; i--) {
            final String oldCommand = commandsHistory.get(i);
            Label oldCommandLabel = new Label(oldCommand);
            CustomMenuItem item = new CustomMenuItem(oldCommandLabel, true);
            item.setOnAction(new AutoCompleteChoiceHandler(oldCommand, "", textField, commandsHistoryList));
            menuItems.add(item);
        }

        return menuItems;
    }

    public void updateCommandHistory (String newCommandString) {
        commandsHistory.add(newCommandString);
        if (commandsHistory.size() > COMMANDS_HISTORY_SIZE) {
            commandsHistory.removeFirst();
        }
    }

}
