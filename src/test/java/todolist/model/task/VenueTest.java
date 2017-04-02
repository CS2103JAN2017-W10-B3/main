package todolist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VenueTest {

    @Test
    public void isValidVenue() {
        // invalid phone numbers
        assertFalse(Venue.isValidVenue("")); // empty string

        // valid phone numbers
        assertTrue(Venue.isValidVenue("computing")); // alphabets only
        assertTrue(Venue.isValidVenue("12345")); // numbers only
        assertTrue(Venue.isValidVenue("school of computing")); // alphanumeric
                                                         // characters
        assertTrue(Venue.isValidVenue("Computing")); // with capital letters
        assertTrue(Venue.isValidVenue("The 2nd cubicle from the left on the 3rd rack at Basement 1 of COM1")); // long
                                                                          // names
    }
}
