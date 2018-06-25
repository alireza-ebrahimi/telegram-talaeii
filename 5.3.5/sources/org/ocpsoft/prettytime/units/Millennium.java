package org.ocpsoft.prettytime.units;

import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

public class Millennium extends ResourcesTimeUnit implements TimeUnit {
    public Millennium() {
        setMillisPerUnit(31556926000000L);
    }

    protected String getResourceKeyPrefix() {
        return "Millennium";
    }
}
