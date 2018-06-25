package org.ocpsoft.prettytime.impl;

import org.ocpsoft.prettytime.TimeUnit;

public abstract class ResourcesTimeUnit implements TimeUnit {
    private long maxQuantity = 0;
    private long millisPerUnit = 1;

    protected abstract String getResourceKeyPrefix();

    protected String getResourceBundleName() {
        return "org.ocpsoft.prettytime.i18n.Resources";
    }

    public long getMaxQuantity() {
        return this.maxQuantity;
    }

    public void setMaxQuantity(long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public long getMillisPerUnit() {
        return this.millisPerUnit;
    }

    public void setMillisPerUnit(long millisPerUnit) {
        this.millisPerUnit = millisPerUnit;
    }

    public boolean isPrecise() {
        return true;
    }

    public String toString() {
        return getResourceKeyPrefix();
    }

    public int hashCode() {
        return ((((int) (this.maxQuantity ^ (this.maxQuantity >>> 32))) + 31) * 31) + ((int) (this.millisPerUnit ^ (this.millisPerUnit >>> 32)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ResourcesTimeUnit other = (ResourcesTimeUnit) obj;
        if (this.maxQuantity != other.maxQuantity) {
            return false;
        }
        if (this.millisPerUnit != other.millisPerUnit) {
            return false;
        }
        return true;
    }
}
