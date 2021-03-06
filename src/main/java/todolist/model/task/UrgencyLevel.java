package todolist.model.task;
import todolist.commons.exceptions.IllegalValueException;

//@@author A0122017Y
public class UrgencyLevel implements Comparable<UrgencyLevel> {

    /**
     * Represents a Task's urgency level in the to-do list.
     * Guarantees: immutable; is valid as declared in {@link #isValidUrgencyLevel(String)}
     */

    public static final String MESSAGE_URGENCYLEVEL_CONSTRAINTS = "Task urgency levels should "
            + "only contain numbers between 0 to 3";
    public static final String URGENCYLEVEL_VALIDATION_REGEX = "[0-3]";

    private final String value;
    private final int intValue;

    /**
     * Validates given urgency level.
     *
     * @throws IllegalValueException if given urgency level string is invalid.
     */
    public UrgencyLevel(String urgencyLevel) throws IllegalValueException {
        assert urgencyLevel != null;
        if (urgencyLevel.isEmpty()) {
            this.value = urgencyLevel;
            this.intValue = 0;
        } else {
            String trimmedUrgencyLevel = urgencyLevel.trim();
            if (!isValidUrgencyLevel(trimmedUrgencyLevel)) {
                throw new IllegalValueException(MESSAGE_URGENCYLEVEL_CONSTRAINTS);
            }
            this.value = trimmedUrgencyLevel;
            this.intValue = Integer.parseInt(value);
        }
    }

    /**
     * Returns true if a given string is a valid task urgency level.
     */
    public static boolean isValidUrgencyLevel(String test) {
        return test.matches(URGENCYLEVEL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    public int getIntValue() {
        return intValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UrgencyLevel // instanceof handles nulls
                        && this.value.equals(((UrgencyLevel) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(UrgencyLevel urgencyLevel) {
        return this.intValue - urgencyLevel.getIntValue();
    }

}
