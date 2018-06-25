package org.ocpsoft.prettytime.units;

import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

public class JustNow extends ResourcesTimeUnit implements TimeUnit {
    public JustNow() {
        setMaxQuantity(60000);
    }

    protected String getResourceKeyPrefix() {
        return "JustNow";
    }

    public boolean isPrecise() {
        return false;
    }
}
