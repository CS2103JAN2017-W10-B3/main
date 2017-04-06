package todolist.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author A0122017Y
public class TimeUtilTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void isValidTimeFormat() {
        
        //Test if it matches DD/MM/YYYY HH:MM or DD/MM/YYYY HHam/pm
        assertTrue(TimeUtil.isValidFormat("20/03/2017 06:00"));
        assertTrue(TimeUtil.isValidFormat("3/4/2017 5:30"));
        assertTrue(TimeUtil.isValidFormat("4/3/2017 11pM"));
        assertTrue(TimeUtil.isValidFormat("20/03/2017 6am"));
        assertTrue(TimeUtil.isValidFormat("20/03/2017"));
        assertTrue(TimeUtil.isValidFormat("20/03/2017 6pM"));
        
        assertFalse(TimeUtil.isValidFormat("200/03/2017 06:00"));
        assertFalse(TimeUtil.isValidFormat("20/03/2017 6dm"));
        assertFalse(TimeUtil.isValidFormat("20/03/2017-6dm"));
        
        //Test if it matches DD MM YYYY HH:MM or DD MM YYYY HHam/pm
        assertTrue(TimeUtil.isValidFormat("20 March 2017 06:00"));
        assertTrue(TimeUtil.isValidFormat("20 DECEMBER 2017 06:00"));
        assertTrue(TimeUtil.isValidFormat("20 Mar 2017"));
        assertTrue(TimeUtil.isValidFormat("3 September 2017 4am"));
        
        assertFalse(TimeUtil.isValidFormat("3 Sp 2017 4am"));
        assertFalse(TimeUtil.isValidFormat("3 Septembber 2017 4am"));
        assertFalse(TimeUtil.isValidFormat("312 Septembber 2017 4am"));
        assertFalse(TimeUtil.isValidFormat("3 September 209 4am"));
        assertFalse(TimeUtil.isValidFormat("13 September 2009 5la"));
        assertFalse(TimeUtil.isValidFormat("13 September 2019 13:133"));
        
        //Test if it matches the format DD MM HH:MM
        assertTrue(TimeUtil.isValidFormat("20 March 06:00"));
        assertTrue(TimeUtil.isValidFormat("20 DECEMBER 06:00"));
        assertTrue(TimeUtil.isValidFormat("20 Mar 11am"));
        assertTrue(TimeUtil.isValidFormat("3 September 4am"));
        
        assertFalse(TimeUtil.isValidFormat("3 Sp 2017 4am"));
        assertFalse(TimeUtil.isValidFormat("3 Septembber 2017 4am"));
        assertFalse(TimeUtil.isValidFormat("312 Septembber 4am"));

        
        //Test if it matches the format the format "Day HH:MM"
        assertTrue(TimeUtil.isValidFormat("This Tuesday 06:00"));
        assertTrue(TimeUtil.isValidFormat("This Wednesday 06:00"));
        assertTrue(TimeUtil.isValidFormat("tomorrow 11am"));
        assertTrue(TimeUtil.isValidFormat("Thu 4am"));
        
        //Test if it matches the format the format "two days ago"
        assertTrue(TimeUtil.isValidFormat("Coming tuesday"));
        assertTrue(TimeUtil.isValidFormat("Two days before the day after tomorrow"));
        assertTrue(TimeUtil.isValidFormat("This thursday"));
    }
    
    @Test
    public void isValidTimeValue() {

        //Test isValidMonthDay for DD/MM/YYYY HH:MM or HHam/pm
        assertTrue(TimeUtil.isValidMonthDay("20/03/2017 06:00"));
        assertFalse(TimeUtil.isValidMonthDay("32/03/2017 06:00"));
        
        //Test isValidMonthDay for DD MM YYYY HH:MM or HHam/pm
        assertFalse(TimeUtil.isValidMonthDay("31 Feb 2017"));
        assertFalse(TimeUtil.isValidMonthDay("32 Jan 2017 06:00"));
        
        //Test isValidMonthDay for DD MM HH:MM or HHam/pm
        assertTrue(TimeUtil.isValidMonthDay("27 Feb 06:00"));
        assertFalse(TimeUtil.isValidMonthDay("30 feb 06:00"));
        
    }
}
