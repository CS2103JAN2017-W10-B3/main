package todolist.ui;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;

// @@ A0110791M
/**
 * Replaces TextField to provide auto-complete/commands history functionality
 */
public class AutoCompleteTextField extends TextField {

    private TextFieldAutoCompleter textFieldAutoCompleter;
    private HotKeyHandler commandHistoryHandler;
    private ContextMenu popupList;

    public AutoCompleteTextField() {
        super();
        popupList = new ContextMenu();
        textFieldAutoCompleter = new TextFieldAutoCompleter(this, popupList);
        textProperty().addListener(textFieldAutoCompleter);

        commandHistoryHandler = new HotKeyHandler(this, popupList);
        setOnKeyPressed(commandHistoryHandler);
    }

    public void addKeyWords () {
        textFieldAutoCompleter.addKeyWords(this.getText());
    }

    public void addCommandHistory () {
        commandHistoryHandler.updateCommandHistory(this.getText());
    }
}
