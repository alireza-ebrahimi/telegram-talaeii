package org.ocpsoft.prettytime.units;

import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

public class Century extends ResourcesTimeUnit implements TimeUnit {
    public Century() {
        setMillisPerUnit(3155692597470L);
    }

    protected String getResourceKeyPrefix() {
        return "Century";
    }
}
