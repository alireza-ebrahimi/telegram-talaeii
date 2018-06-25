package com.crashlytics.android.core.internal.models;

public class SessionEventData {
    public final BinaryImageData[] binaryImages;
    public final CustomAttributeData[] customAttributes;
    public final DeviceData deviceData;
    public final SignalData signal;
    public final ThreadData[] threads;
    public final long timestamp;

    public SessionEventData(long timestamp, SignalData signal, ThreadData[] threads, BinaryImageData[] binaryImages, CustomAttributeData[] customAttributes, DeviceData deviceData) {
        this.timestamp = timestamp;
        this.signal = signal;
        this.threads = threads;
        this.binaryImages = binaryImages;
        this.customAttributes = customAttributes;
        this.deviceData = deviceData;
    }
}
