package com.crashlytics.android.core;

class MiddleOutStrategy implements StackTraceTrimmingStrategy {
    private final int trimmedSize;

    public MiddleOutStrategy(int trimmedSize) {
        this.trimmedSize = trimmedSize;
    }

    public StackTraceElement[] getTrimmedStackTrace(StackTraceElement[] stacktrace) {
        if (stacktrace.length <= this.trimmedSize) {
            return stacktrace;
        }
        int backHalf = this.trimmedSize / 2;
        int frontHalf = this.trimmedSize - backHalf;
        StackTraceElement[] trimmed = new StackTraceElement[this.trimmedSize];
        System.arraycopy(stacktrace, 0, trimmed, 0, frontHalf);
        System.arraycopy(stacktrace, stacktrace.length - backHalf, trimmed, frontHalf, backHalf);
        return trimmed;
    }
}
