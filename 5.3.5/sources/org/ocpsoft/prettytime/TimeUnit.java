package org.ocpsoft.prettytime;

public interface TimeUnit {
    long getMaxQuantity();

    long getMillisPerUnit();

    boolean isPrecise();
}
