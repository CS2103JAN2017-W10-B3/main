package todolist.ui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;

public class TextFieldAutoCompleter implements ChangeListener<String> {

    // @@ A0110791M
    public static final String[] PREFIXSTRINGS = new String[] {"/venue ","/from ","/to ","/level","/description" };
    public static final TreeSet<String> SUGGESTIONS = new TreeSet<String>(Arrays.asList(PREFIXSTRINGS));
    public static final int MAX_SUGGESTIONS_LENGTH = 10;

    private ContextMenu suggestionsList;
    private AutoCompleteTextField textField;

    public TextFieldAutoCompleter(AutoCompleteTextField autoCompleteTextField, ContextMenu suggestionsListMenu) {
        suggestionsList = suggestionsListMenu;
        textField = autoCompleteTextField;
    }

    @Override
    public void changed (ObservableValue<? extends String> observableValue, String prevTextInput,
            String currentTextInput) {
        if (currentTextInput.length() == 0 || currentTextInput.endsWith(" ")) {
            suggestionsList.hide();
        } else {
            showSuggestions(currentTextInput);
        }
    }

    /**
     * Get the suggestions list to pop up.
     *
     * @param currentTextInput  Current string in the text field.
     */
    private void showSuggestions (String currentTextInput) {
        LinkedList<String> searchResults = new LinkedList<String>();
        String currentWord = getLastWord(currentTextInput);
        searchResults.addAll(SUGGESTIONS.subSet(currentWord, currentWord + Character.MAX_VALUE));
        List<CustomMenuItem> menuItems = getSuggestionsMenu(searchResults, currentTextInput);

        suggestionsList.getItems().clear();
        suggestionsList.getItems().addAll(menuItems);
        suggestionsList.show(textField, Side.BOTTOM, textField.getCaretPosition(), 0);
    }

    /**
     *  Get the menu object containing all suggestions for user to choose from.
     *
     *  @param searchResult     List of matching strings.
     *  @param  currentText     Current string in the text field.
     */
    private List<CustomMenuItem> getSuggestionsMenu (List<String> searchResult, String currentText) {
        List<CustomMenuItem> menuItems = new LinkedList<>();

        int count = Math.min(searchResult.size(), MAX_SUGGESTIONS_LENGTH);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(new AutoCompleteChoiceHandler(result, currentText, textField, suggestionsList));
            menuItems.add(item);
        }

        return menuItems;
    }

    private String getLastWord (String currentText) {
        String lastWord = null;
        int indexOfLastSpacing = currentText.lastIndexOf(' ');
        if (indexOfLastSpacing == -1) {
            lastWord = " ";
        }
        else {
            lastWord = currentText.substring(indexOfLastSpacing+1, currentText.length());
        }
        return lastWord;
    }


    // @@
}
