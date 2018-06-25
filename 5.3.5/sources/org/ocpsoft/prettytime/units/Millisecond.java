package org.ocpsoft.prettytime.units;

import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

public class Millisecond extends ResourcesTimeUnit implements TimeUnit {
    public Millisecond() {
        setMillisPerUnit(1);
    }

    protected String getResourceKeyPrefix() {
        return "Millisecond";
    }
}
