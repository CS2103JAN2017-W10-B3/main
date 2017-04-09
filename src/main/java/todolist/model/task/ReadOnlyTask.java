package todolist.model.task;

import java.util.Comparator;
import java.util.Optional;

import todolist.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the to-do list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Title getTitle();
    Optional<StartTime> getStartTime();
    Optional<EndTime> getEndTime();
    Optional<Venue> getVenue();
    Optional<Description> getDescription();
    Optional<UrgencyLevel> getUrgencyLevel();
    public enum Category { DEADLINE, EVENT, FLOAT, COMPLETED }

    Category getTaskCategory();
    Character getTaskChar();

    Boolean isTaskCompleted();
    void toggleComplete();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the Task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime())
                && other.getVenue().equals(this.getVenue())
                && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the Task as text, showing all contact details.
     */
    //@@ author:A0122017Y
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append("This is a " + getTaskCategory() + " task; ")
                .append("Title of task is ")
                .append(getTitle() + "; ")
                .append(getVenueString())
                .append(getDescriptionString())
                .append(getEndTimeString())
                .append(getTagString());
        return builder.toString();
    }
    /**
     * Check if venue is present.
     * If null, empty string is returned.
     */
    default String getVenueString() {
        return getVenue().isPresent() ? "At: " + getVenue().get().toString() + "; " : "";
    }

    /**
     * Check if start time is present.
     * If null, empty string is returned.
     */
    default String getStartTimeString() {
        return getStartTime().isPresent() ? "Start at: " + getStartTime().get().toString() + "; " : "";
    }

    /**
     * Check if end time is present.
     * If null, empty string is returned.
     */
    default String getEndTimeString() {
        return getEndTime().isPresent() ? "Done by: " + getEndTime().get().toString() + "; " : "";
    }

    /**
     * Check if urgency level is present.
     * If null, empty string is returned.
     */
    default String getUrgencyLevelString() {
        return getUrgencyLevel().isPresent() ? "Urgency level at: " + getUrgencyLevel().get().toString() + "; " : "";
    }

    /**
     * Check if description is present.
     * If null, empty string is returned.
     */
    default String getDescriptionString() {
        return getDescription().isPresent() ? "Description: " + getDescription().get().toString() + "; " : "";
    }

    /**
     * Obtain the tag string form the UniqueTagList of the task
     */
    default String getTagString() {
        return !getTags().isEmpty() ? "Tags: " + getTags().getTagListToString() + "; " : "";
    }

    //@@ author: A0138628W
    default int getUrgencyLevelInt() {
        return getUrgencyLevel().isPresent() ? getUrgencyLevel().get().getIntValue() : 0;
    }
    //@@

    //====================Comparators for tasks======================
    //@@author A0122017Y
    /**
     * For deadline tasks, first by deadline, then by name
     */
    public static Comparator<ReadOnlyTask> getDeadlineComparator() {
        //first by deadline
        Comparator<ReadOnlyTask> byDeadline = (t1, t2) -> {
            return t1.getEndTime().get().compareTo(t2.getEndTime().get());
        };

        //then by name
        Comparator<ReadOnlyTask> byName = (t1, t2) -> t1.getTitle().compareTo(t2.getTitle());

        return byDeadline.thenComparing(byName);
    }

    /**
     * For event tasks, first by start time, then by urgency level, then by end time, then by name
     */
    public static Comparator<ReadOnlyTask> getEventComparator() {
        //first by start time
        Comparator<ReadOnlyTask> byStartTime = (t1, t2) -> {
            return t1.getStartTime().get().compareTo(t2.getStartTime().get());
        };

        //then by urgency level
        Comparator<ReadOnlyTask> byUrgencyLevel = (t1, t2) -> {
            return t2.getUrgencyLevel().get().compareTo(t1.getUrgencyLevel().get());
        };

        //then by end time
        Comparator<ReadOnlyTask> byEndTime = (t1, t2) -> {
            return t1.getEndTime().get().compareTo(t2.getEndTime().get());
        };

        //then by name
        Comparator<ReadOnlyTask> byName = (t1, t2) -> t1.getTitle().compareTo(t2.getTitle());

        return byStartTime.thenComparing(byUrgencyLevel).thenComparing(byEndTime).thenComparing(byName);
    }

    /**
     * For floating tasks, first by urgency level, then by start time if any, then by name
     */
    public static Comparator<ReadOnlyTask> getFloatingComparator() {
        //first by urgency level
        Comparator<ReadOnlyTask> byUrgencyLevel = (t1, t2) -> {
            if (!t1.getUrgencyLevel().isPresent() && !t2.getUrgencyLevel().isPresent()) {
                return 0;
            } else if (!t1.getUrgencyLevel().isPresent()) {
                return 1;
            } else if (!t2.getUrgencyLevel().isPresent()) {
                return -1;
            }

            //if both having urgency level
            return t2.getUrgencyLevel().get().compareTo(t1.getUrgencyLevel().get());
        };

        //then by name
        Comparator<ReadOnlyTask> byName = (t1, t2) -> t1.getTitle().compareTo(t2.getTitle());

        return byUrgencyLevel.thenComparing(byName);
    }

    public static Comparator<ReadOnlyTask> getCompleteComparator() {
        //first by task type, deadline, first then event, then floating
        Comparator<ReadOnlyTask> byTaskType = (t1, t2) -> {
            return t1.getTaskChar().compareTo(t2.getTaskChar());
        };

        //then by name
        Comparator<ReadOnlyTask> byName = (t1, t2) -> t1.getTitle().compareTo(t2.getTitle());

        return byTaskType.thenComparing(byName);
    }

    String getTitleFormattedString();

    //@@
    //author A0143648Y
    default String getIsCompletedToString() {
        return this.isTaskCompleted().toString();
    }

}
