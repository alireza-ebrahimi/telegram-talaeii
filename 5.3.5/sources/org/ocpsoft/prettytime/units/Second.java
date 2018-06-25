package org.ocpsoft.prettytime.units;

import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

public class Second extends ResourcesTimeUnit implements TimeUnit {
    public Second() {
        setMillisPerUnit(1000);
    }

    protected String getResourceKeyPrefix() {
        return "Second";
    }
}
