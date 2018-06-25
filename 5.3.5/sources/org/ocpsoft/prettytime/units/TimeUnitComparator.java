package org.ocpsoft.prettytime.units;

import java.io.Serializable;
import java.util.Comparator;
import org.ocpsoft.prettytime.TimeUnit;

public class TimeUnitComparator implements Comparator<TimeUnit>, Serializable {
    private static final long serialVersionUID = 1;

    public int compare(TimeUnit left, TimeUnit right) {
        if (left.getMillisPerUnit() < right.getMillisPerUnit()) {
            return -1;
        }
        if (left.getMillisPerUnit() > right.getMillisPerUnit()) {
            return 1;
        }
        return 0;
    }
}
