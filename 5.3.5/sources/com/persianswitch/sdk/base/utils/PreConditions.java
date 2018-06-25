package com.persianswitch.sdk.base.utils;

public final class PreConditions {
    public static void checkNotNull(Object reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
    }

    public static void checkNotNull(Object reference, String message) {
        if (reference == null) {
            throw new NullPointerException();
        }
    }
}
