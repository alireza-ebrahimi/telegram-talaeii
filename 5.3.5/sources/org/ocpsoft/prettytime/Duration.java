package org.ocpsoft.prettytime;

public interface Duration {
    long getDelta();

    long getQuantity();

    long getQuantityRounded(int i);

    TimeUnit getUnit();

    boolean isInFuture();

    boolean isInPast();
}
