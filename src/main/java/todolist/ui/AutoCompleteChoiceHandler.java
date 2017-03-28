package todolist.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;

public class AutoCompleteChoiceHandler implements EventHandler<ActionEvent> {

    // @@ A0110791M
    private String optionChosen, precedingText;
    private AutoCompleteTextField choiceHandlerHost;
    private ContextMenu suggestionsList;

    public AutoCompleteChoiceHandler (String choiceInput, String currentText, AutoCompleteTextField eventHost,
            ContextMenu dropDownList) {
        optionChosen = choiceInput;
        precedingText = getTextBeforeLastSpacing(currentText);
        choiceHandlerHost = eventHost;
        suggestionsList = dropDownList;
    }

    @Override
    public void handle (ActionEvent actionEvent) {
        String output = precedingText.concat(optionChosen);
        choiceHandlerHost.setText(output);
        choiceHandlerHost.positionCaret(output.length());
        suggestionsList.hide();
    }

    private String getTextBeforeLastSpacing (String currentText) {
        int indexOfLastSpacing = currentText.lastIndexOf(' ');
        return currentText.substring(0, indexOfLastSpacing);
    }
    // @@

}
