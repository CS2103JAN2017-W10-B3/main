package todolist.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;

public class AutoCompleteChoiceHandler implements EventHandler<ActionEvent> {

    // @@ A0110791M
    private String optionChosen, precedingText;
    private AutoCompleteTextField choiceHandlerHost;
    private ContextMenu popupList;

    public AutoCompleteChoiceHandler (String choiceInput, String currentText, AutoCompleteTextField eventHost, ContextMenu popupList) {
        optionChosen = choiceInput;
        precedingText = getTextBeforeLastSpacing(currentText);
        choiceHandlerHost = eventHost;
        this.popupList = popupList;
    }

    @Override
    public void handle (ActionEvent actionEvent) {
        String output = precedingText.concat(optionChosen);
        choiceHandlerHost.setText(output);
        choiceHandlerHost.positionCaret(output.length());
        popupList.hide();
    }

    private String getTextBeforeLastSpacing (String currentText) {
        int indexOfLastSpacing = currentText.lastIndexOf(' ');
        if (indexOfLastSpacing == -1) {
            return "";
        } else {
            return currentText.substring(0, indexOfLastSpacing+1);
        }
    }
    // @@

}
