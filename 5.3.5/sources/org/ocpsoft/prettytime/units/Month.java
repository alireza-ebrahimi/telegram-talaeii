package org.ocpsoft.prettytime.units;

import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

public class Month extends ResourcesTimeUnit implements TimeUnit {
    public Month() {
        setMillisPerUnit(2629743830L);
    }

    protected String getResourceKeyPrefix() {
        return "Month";
    }
}
