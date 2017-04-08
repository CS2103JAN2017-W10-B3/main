package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import todolist.ui.Scroll;

public class ScrollTest extends ToDoListGuiTest {
    //@@author: A0138628W
    /**
     * Scroll for testing
     * It should test when there is no scroll bar and when there is
     */
    private Scroll scrollTest;

    //Scroll is null
    @Test
    public void scrollbar_isnull() {
        scrollTest = new Scroll();
        assertNull(scrollTest.getScrollBar());
        assertFalse(scrollTest.isAvailable());
    }

    @Test(expected=NullPointerException.class)
    public void testGetCurrentValue() {
        scrollTest = new Scroll();
        scrollTest.getCurrentValue();
    }

    @Test(expected=NullPointerException.class)
    public void testGetMax() {
        scrollTest = new Scroll();
        scrollTest.getMax();
    }

    @Test(expected=NullPointerException.class)
    public void testGetMin() {
        scrollTest = new Scroll();
        scrollTest.getMin();
    }

    @Test(expected=NullPointerException.class)
    public void testScrollIncrease() {
        scrollTest = new Scroll();
        scrollTest.scrollIncrease();
    }

    @Test(expected=NullPointerException.class)
    public void testScrollDecrease() {
        scrollTest = new Scroll();
        scrollTest.scrollDecrease();
    }

    //Scroll test with scroll bar

    @Test
    public void testGettingOfScrollBarWithList() {
        scrollTest = new Scroll();
        scrollTest.getListVerticalScrollbar(taskListPanel.getListView());
        assertNotNull(scrollTest.getScrollBar());
        assertTrue(scrollTest.isAvailable());
    }

    @Test
    public void testGettingOfScrollBarWithText() {
        scrollTest = new Scroll();
        scrollTest.getTextVerticalScrollbar(resultDisplay.getResultDisplay());
        assertNotNull(scrollTest.getScrollBar());
        assertTrue(scrollTest.isAvailable());
        scrollTest.getTextHorizontalScrollbar(resultDisplay.getResultDisplay());
        assertNotNull(scrollTest.getScrollBar());
        assertTrue(scrollTest.isAvailable());
    }

    @Test
    public void testScrollingIncrease() {
        scrollTest = new Scroll();
        scrollTest.getListVerticalScrollbar(taskListPanel.getListView());
        double oldValue = scrollTest.getCurrentValue();
        scrollTest.scrollIncrease();
        double newValue = scrollTest.getCurrentValue();
        assertTrue(oldValue <= newValue);
    }

    @Test
    public void testScrollingDecrease() {
        scrollTest = new Scroll();
        scrollTest.getListVerticalScrollbar(taskListPanel.getListView());
        double oldValue = scrollTest.getCurrentValue();
        scrollTest.scrollDecrease();
        double newValue = scrollTest.getCurrentValue();
        assertTrue(oldValue >= newValue);
    }

}
