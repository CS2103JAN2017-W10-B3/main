package todolist.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import todolist.commons.exceptions.IllegalValueException;
import todolist.commons.util.StringUtil;
import todolist.model.tag.Tag;
import todolist.model.tag.UniqueTagList;
import todolist.model.task.Description;
import todolist.model.task.EndTime;
import todolist.model.task.StartTime;
import todolist.model.task.Task;
import todolist.model.task.TaskIndex;
import todolist.model.task.Title;
import todolist.model.task.UrgencyLevel;
import todolist.model.task.Venue;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    private static final String INDEX_RANGE_SYMBOL = "-";

  //@@author A0143648Y
    /**
     * Returns the specified indexes in the {@code command} if it is a valid
     * index Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<ArrayList<TaskIndex>> parseIndex(String command) {
        final Matcher matcher = INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String[] indexes = matcher.group("targetIndex").split(" ");
        for (String index : indexes) {
            if (!isValidIndex(index)) {
                return Optional.empty();
            }
        }
        return parseCorrectIndex(indexes);

    }

    /**
     * Returns the specified indexes in the {@code indexes}
     * {@code indexes} should be passed through isValidIndex first before passed into this method
     */
    public static Optional<ArrayList<TaskIndex>> parseCorrectIndex(String[] indexes) {
        assert indexes != null;

        ArrayList<TaskIndex> editedIndexes = new ArrayList<TaskIndex>();
        for (String index : indexes) {
            if (!index.contains(INDEX_RANGE_SYMBOL)) {
                editedIndexes.add(parseCorrectSingleIndex(index));
            } else {
                editedIndexes.addAll(parseCorrectMultipleIndex(index));
            }
        }

        return Optional.of(editedIndexes);

    }

    /**
     * Returns the specified single index in the {@code indexes}
     */
    public static TaskIndex parseCorrectSingleIndex(String index) {
        assert isSingleValidIndex(index);

        Character taskType;
        int taskNumber;
        if (StringUtil.isUnsignedInteger(index)) {
            taskType = Task.DEADLINE_CHAR;
            taskNumber = Integer.parseInt(index);
        } else {
            taskType = index.charAt(0);
            taskNumber = Integer.parseInt(index.substring(1));
        }

        return new TaskIndex(taskType, taskNumber);

    }

    /**
     * Returns the specified multiple index in the {@code indexes}
     */
    public static ArrayList<TaskIndex> parseCorrectMultipleIndex(String index) {
        assert isMultipleValidIndex(index);

        String[] indexes = index.split(INDEX_RANGE_SYMBOL);
        if (StringUtil.isUnsignedInteger(indexes[0])) {
            return Integer.parseInt(indexes[0]) <= Integer.parseInt(indexes[1])
                    ? generateListOfIndexes(Task.DEADLINE_CHAR, Integer.parseInt(indexes[0]),
                            Integer.parseInt(indexes[1]))
                    : generateListOfIndexes(Task.DEADLINE_CHAR, Integer.parseInt(indexes[1]),
                            Integer.parseInt(indexes[0]));
        } else {
            if (StringUtil.isUnsignedInteger(indexes[1])) {
                return Integer.parseInt(indexes[0].substring(1)) <= Integer.parseInt(indexes[1])
                        ? generateListOfIndexes(indexes[0].charAt(0), Integer.parseInt(indexes[0].substring(1)),
                                Integer.parseInt(indexes[1]))
                        : generateListOfIndexes(indexes[0].charAt(0), Integer.parseInt(indexes[1]),
                                Integer.parseInt(indexes[0].substring(1)));
            } else {
                return Integer.parseInt(indexes[0].substring(1)) <= Integer.parseInt(indexes[1].substring(1))
                        ? generateListOfIndexes(indexes[0].charAt(0), Integer.parseInt(indexes[0].substring(1)),
                                Integer.parseInt(indexes[1].substring(1)))
                        : generateListOfIndexes(indexes[0].charAt(0), Integer.parseInt(indexes[1].substring(1)),
                                Integer.parseInt(indexes[0].substring(1)));

            }
        }

    }

    /**
     * Returns a list of TaskIndex generated from {@code taskType} starting from {@code firstTaskNumber}
     * to {@code} lastTaskNumber
     */
    public static ArrayList<TaskIndex> generateListOfIndexes(Character taskType, int firstTaskNumber,
            int lastTaskNumber) {

        ArrayList<TaskIndex> indexes = new ArrayList<TaskIndex>();
        for (; firstTaskNumber <= lastTaskNumber; firstTaskNumber++) {
            indexes.add(parseCorrectSingleIndex(taskType.toString() + firstTaskNumber));
        }
        return indexes;

    }

    /**
     * Returns if {@code index} is a valid index input
     */
    public static boolean isValidIndex(String index) {
        if (!index.contains(INDEX_RANGE_SYMBOL)) {
            return isSingleValidIndex(index);
        } else if (!index.isEmpty() && !index.startsWith(INDEX_RANGE_SYMBOL)) {
            return isMultipleValidIndex(index);
        } else {
            return false;
        }
    }

    /**
     * Returns if {@code index} is a valid single index input
     */
    public static boolean isSingleValidIndex(String index) {
        if (StringUtil.isUnsignedInteger(index)) {
            return true;
        } else {
            char taskType = index.charAt(0);
            if (taskType != Task.DEADLINE_CHAR && taskType != Task.EVENT_CHAR &&
                    taskType != Task.FLOAT_CHAR && taskType != Task.COMPLETE_CHAR) {
                return false;
            } else {
                if (StringUtil.isUnsignedInteger(index.substring(1))) {
                    return true;
                } else {
                    return false;
                }
            }
        }

    }

    /**
     * Returns if {@code index} is a valid multiple index input
     */
    public static boolean isMultipleValidIndex(String index) {
        String[] splitIndex = index.split(INDEX_RANGE_SYMBOL);
        if (!isSingleValidIndex(splitIndex[0])) {
            return false;
        } else {
            if (splitIndex.length != 2) {
                return false;
            } else {
                if (!isSingleValidIndex(splitIndex[1])) {
                    return false;
                } else {
                    if (StringUtil.isUnsignedInteger(splitIndex[1])) {
                        return true;
                    } else {
                        if (StringUtil.isUnsignedInteger(splitIndex[0])) {
                            return false;
                        } else {
                            if (splitIndex[0].charAt(0) != splitIndex[1].charAt(0)) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                }
            }
        }
    }

    //@@
    /**
     * Returns a new Set populated by all elements in the given list of strings
     * Returns an empty set if the given {@code Optional} is empty, or if the
     * list contained in the {@code Optional} is empty
     */
    public static Set<String> toSet(Optional<List<String>> list) {
        List<String> elements = list.orElse(Collections.emptyList());
        return new HashSet<>(elements);
    }

    /**
     * Splits a preamble string into ordered fields.
     *
     * @return A list of size {@code numFields} where the ith element is the ith
     *         field value if specified in the input, {@code Optional.empty()}
     *         otherwise.
     */
    public static List<Optional<String>> splitPreamble(String preamble, int numFields) {
        return Arrays.stream(Arrays.copyOf(preamble.split("\\s+", numFields), numFields)).map(Optional::ofNullable)
                .collect(Collectors.toList());
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Title>} if
     * {@code name} is present.
     */
    public static Optional<Title> parseTitle(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new Title(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Venue>}
     * if {@code phone} is present.
     */
    public static Optional<Venue> parseVenue(Optional<String> venue) throws IllegalValueException {
        assert venue != null;
        return venue.isPresent() ? Optional.of(new Venue(venue.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an
     * {@code Optional<EndTime>} if {@code address} is present.
     */
    public static Optional<EndTime> parseEndTime(Optional<String> address) throws IllegalValueException {
        assert address != null;
        return address.isPresent() ? Optional.of(new EndTime(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an
     * {@code Optional<StartTime>} if {@code email} is present.
     */
    public static Optional<StartTime> parseStartTime(Optional<String> startTime) throws IllegalValueException {
        assert startTime != null;
        return startTime.isPresent() ? Optional.of(new StartTime(startTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an
     * {@code Optional<StartTime>} if {@code email} is present.
     */
    public static Optional<UrgencyLevel> parseUrgencyLevel(Optional<String> urgencyLevel) throws IllegalValueException {
        assert urgencyLevel != null;
        return urgencyLevel.isPresent() ? Optional.of(new UrgencyLevel(urgencyLevel.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an
     * {@code Optional<StartTime>} if {@code email} is present.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
        assert description != null;
        return description.isPresent() ? Optional.of(new Description(description.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
     */
    public static UniqueTagList parseTags(Collection<String> tags) throws IllegalValueException {
        assert tags != null;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagTitle : tags) {
            tagSet.add(new Tag(tagTitle));
        }
        return new UniqueTagList(tagSet);
    }
}
