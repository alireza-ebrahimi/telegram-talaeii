package com.crashlytics.android.core.internal.models;

public class SignalData {
    public final String code;
    public final long faultAddress;
    public final String name;

    public SignalData(String name, String code, long faultAddress) {
        this.name = name;
        this.code = code;
        this.faultAddress = faultAddress;
    }
}
