package com.crashlytics.android.core.internal.models;

public class DeviceData {
    public final long availableInternalStorage;
    public final long availablePhysicalMemory;
    public final int batteryCapacity;
    public final int batteryVelocity;
    public final int orientation;
    public final boolean proximity;
    public final long totalInternalStorage;
    public final long totalPhysicalMemory;

    public DeviceData(int orientation, long totalPhysicalMemory, long totalInternalStorage, long availablePhysicalMemory, long availableInternalStorage, int batteryCapacity, int batteryVelocity, boolean proximity) {
        this.orientation = orientation;
        this.totalPhysicalMemory = totalPhysicalMemory;
        this.totalInternalStorage = totalInternalStorage;
        this.availablePhysicalMemory = availablePhysicalMemory;
        this.availableInternalStorage = availableInternalStorage;
        this.batteryCapacity = batteryCapacity;
        this.batteryVelocity = batteryVelocity;
        this.proximity = proximity;
    }
}
