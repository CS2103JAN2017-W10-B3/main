package todolist.ui;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;

/**
 * Replaces TextField to provide auto-complete functionality
 */
public class AutoCompleteTextField extends TextField {

    private TextFieldAutoCompleter textFieldAutoCompleter;
    private ContextMenu suggestionsList;

    public AutoCompleteTextField() {
        super();
        suggestionsList = new ContextMenu();
        textFieldAutoCompleter = new TextFieldAutoCompleter(this, suggestionsList);
        textProperty().addListener(textFieldAutoCompleter);
        focusedProperty().addListener(new FocusListener(this, suggestionsList));
    }

}
