package org.ocpsoft.prettytime.units;

import org.apache.commons.lang3.time.DateUtils;
import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

public class Hour extends ResourcesTimeUnit implements TimeUnit {
    public Hour() {
        setMillisPerUnit(DateUtils.MILLIS_PER_HOUR);
    }

    protected String getResourceKeyPrefix() {
        return "Hour";
    }
}
