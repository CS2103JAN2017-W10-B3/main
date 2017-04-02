package todolist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import todolist.commons.exceptions.IllegalValueException;

public class TimeTest {

    @Test
    public void isValidTime() {
        // blank EndTime
        assertFalse(Time.isValidTime("")); // empty string
        
        

        // valid Time
        assertTrue(Time.isValidTime("March Fifteenth")); //alphabets
        assertTrue(Time.isValidTime("20170315")); // number
        assertTrue(Time.isValidTime("By end of March")); // alphabets with capital
        assertTrue(Time.isValidTime("March 15 2017")); // numeric and alphabet
                                                           // and domain name
        assertTrue(Time.isValidTime("March 15, 2017")); // mixture of
                                                               // alphanumeric
                                                               // and comma
                                                               // characters
    }
    
    @Test
    public void isValidDuration() throws IllegalValueException {
        StartTime start1 = new StartTime("Today");
        StartTime start3 = new StartTime("Tomorrow");
        
        EndTime end1 = new EndTime("Tomorrow");
        EndTime end2 = new EndTime("Today");
        EndTime end3 = new EndTime("Two weeks later");
        
        assertTrue(start1.isValidDuration(end1));
        assertFalse(start3.isValidDuration(end2));
    }
    
}
