//@@ author: A0138628W
package todolist.ui;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import todolist.model.task.ReadOnlyTask;

public class Scroll {

    private ScrollBar result;

    public Scroll() {
        result = null;
    }

    public ScrollBar getScrollBar() {
        return result;
    }

    public boolean isAvailable() {
        if(result != null) {
            return true;
        }
        return false;
    }

    public double getCurrentValue() {
        return result.getValue();
    }

    public double getMax() {
        return result.getMax();
    }

    public double getMin() {
        return result.getMin();
    }

    public void getListVerticalScrollbar(ListView<ReadOnlyTask> list) {
        for (Node n : list.lookupAll(".scroll-bar")) {
            if (n instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    result = bar;
                }
            }
        }
    }

    public void getTextVerticalScrollbar(TextArea area) {
        for (Node n : area.lookupAll(".scroll-bar")) {
            if (n instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    result = bar;
                }
            }
        }
    }

    public void getTextHorizontalScrollbar(TextArea area) {
        for (Node n : area.lookupAll(".scroll-bar")) {
            if (n instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.HORIZONTAL)) {
                    result = bar;
                }
            }
        }
    }

    public void scrollIncrease() {
        result.increment();
    }

    public void scrollDecrease() {
        result.decrement();
    }

}
