package org.ocpsoft.prettytime.units;

import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

public class Day extends ResourcesTimeUnit implements TimeUnit {
    public Day() {
        setMillisPerUnit(86400000);
    }

    protected String getResourceKeyPrefix() {
        return "Day";
    }
}
