package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static todolist.logic.parser.CliSyntax.PREFIX_BEGINNINGTIME;
import static todolist.logic.parser.CliSyntax.PREFIX_DEADLINETIME;
import static todolist.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static todolist.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static todolist.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static todolist.logic.parser.CliSyntax.PREFIX_TAG;
import static todolist.logic.parser.CliSyntax.PREFIX_TITLE;
import static todolist.logic.parser.CliSyntax.PREFIX_URGENCYLEVEL;
import static todolist.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import todolist.commons.exceptions.IllegalValueException;
import todolist.logic.commands.Command;
import todolist.logic.commands.EditCommand;
import todolist.logic.commands.EditCommand.EditTaskDescriptor;
import todolist.logic.commands.IncorrectCommand;
import todolist.model.tag.UniqueTagList;
import todolist.model.task.Task;
import todolist.model.task.TaskIndex;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    // @@ A0143648Y
    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        Optional<ArrayList<TaskIndex>> indexes;
        assert args != null;
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_TITLE, PREFIX_VENUE, PREFIX_BEGINNINGTIME, PREFIX_STARTTIME,
                PREFIX_ENDTIME, PREFIX_DEADLINETIME, PREFIX_URGENCYLEVEL, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        String indexesToBeParsed = argsTokenizer.getPreamble().orElse("");
        if (indexesToBeParsed.isEmpty()) {
            indexes = Optional.of(new ArrayList<TaskIndex>());
        }

        else {

            indexes = ParserUtil.parseIndex(indexesToBeParsed);

            if (!indexes.isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
            
            if(hasContainedCompletedTask(indexes)){
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setTitle(ParserUtil.parseTitle(argsTokenizer.getValue(PREFIX_TITLE)));
            editTaskDescriptor.setVenue(ParserUtil.parseVenue(argsTokenizer.getValue(PREFIX_VENUE)));
            if(argsTokenizer.getValue(PREFIX_STARTTIME).isPresent()){
            editTaskDescriptor.setStartTime(ParserUtil.parseStartTime(argsTokenizer.getValue(PREFIX_STARTTIME)));
            }
            else{
            editTaskDescriptor.setStartTime(ParserUtil.parseStartTime(argsTokenizer.getValue(PREFIX_BEGINNINGTIME)));
            }
            if(argsTokenizer.getValue(PREFIX_ENDTIME).isPresent()){
            editTaskDescriptor.setEndTime(ParserUtil.parseEndTime(argsTokenizer.getValue(PREFIX_ENDTIME)));
            }
            else{
            editTaskDescriptor.setEndTime(ParserUtil.parseEndTime(argsTokenizer.getValue(PREFIX_DEADLINETIME)));
            }
            editTaskDescriptor
                    .setUrgencyLevel(ParserUtil.parseUrgencyLevel(argsTokenizer.getValue(PREFIX_URGENCYLEVEL)));
            editTaskDescriptor.setDescription(ParserUtil.parseDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
            editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(indexes.get(), editTaskDescriptor);
    }
    
    private boolean hasContainedCompletedTask(Optional<ArrayList<TaskIndex>> indexes){
        boolean hasContained = false;
        for(TaskIndex index: indexes.get()){
            if(index.getTaskChar() == Task.COMPLETE_CHAR){
                hasContained = true;
            }
        }
        return hasContained;
    }
    // @@

    /**
     * Parses {@code Collection<String> tags} into an
     * {@code Optional<UniqueTagList>} if {@code tags} is non-empty. If
     * {@code tags} contain only one element which is an empty string, it will
     * be parsed into a {@code Optional<UniqueTagList>} containing zero tags.
     */
    private Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
