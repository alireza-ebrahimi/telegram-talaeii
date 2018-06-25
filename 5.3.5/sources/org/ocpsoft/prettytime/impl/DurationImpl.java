package org.ocpsoft.prettytime.impl;

import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.TimeUnit;

public class DurationImpl implements Duration {
    private long delta;
    private long quantity;
    private TimeUnit unit;

    public long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public TimeUnit getUnit() {
        return this.unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public long getDelta() {
        return this.delta;
    }

    public void setDelta(long delta) {
        this.delta = delta;
    }

    public boolean isInPast() {
        return getQuantity() < 0;
    }

    public boolean isInFuture() {
        return !isInPast();
    }

    public long getQuantityRounded(int tolerance) {
        long quantity = Math.abs(getQuantity());
        if (getDelta() == 0 || Math.abs((((double) getDelta()) / ((double) getUnit().getMillisPerUnit())) * 100.0d) <= ((double) tolerance)) {
            return quantity;
        }
        return quantity + 1;
    }

    public String toString() {
        return "DurationImpl [" + this.quantity + " " + this.unit + ", delta=" + this.delta + "]";
    }

    public int hashCode() {
        return ((((((int) (this.delta ^ (this.delta >>> 32))) + 31) * 31) + ((int) (this.quantity ^ (this.quantity >>> 32)))) * 31) + (this.unit == null ? 0 : this.unit.hashCode());
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
        DurationImpl other = (DurationImpl) obj;
        if (this.delta != other.delta) {
            return false;
        }
        if (this.quantity != other.quantity) {
            return false;
        }
        if (this.unit == null) {
            if (other.unit != null) {
                return false;
            }
            return true;
        } else if (this.unit.equals(other.unit)) {
            return true;
        } else {
            return false;
        }
    }
}
