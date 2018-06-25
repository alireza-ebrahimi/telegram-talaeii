package org.ocpsoft.prettytime.units;

import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

public class Year extends ResourcesTimeUnit implements TimeUnit {
    public Year() {
        setMillisPerUnit(31556925960L);
    }

    protected String getResourceKeyPrefix() {
        return "Year";
    }
}
