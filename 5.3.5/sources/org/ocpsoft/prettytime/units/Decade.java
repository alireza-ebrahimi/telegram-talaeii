package org.ocpsoft.prettytime.units;

import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

public class Decade extends ResourcesTimeUnit implements TimeUnit {
    public Decade() {
        setMillisPerUnit(315569259747L);
    }

    protected String getResourceKeyPrefix() {
        return "Decade";
    }
}
