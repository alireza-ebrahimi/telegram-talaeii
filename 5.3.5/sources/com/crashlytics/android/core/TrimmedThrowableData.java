package com.crashlytics.android.core;

class TrimmedThrowableData {
    public final TrimmedThrowableData cause;
    public final String className;
    public final String localizedMessage;
    public final StackTraceElement[] stacktrace;

    public TrimmedThrowableData(Throwable ex, StackTraceTrimmingStrategy trimmingStrategy) {
        this.localizedMessage = ex.getLocalizedMessage();
        this.className = ex.getClass().getName();
        this.stacktrace = trimmingStrategy.getTrimmedStackTrace(ex.getStackTrace());
        Throwable exCause = ex.getCause();
        this.cause = exCause != null ? new TrimmedThrowableData(exCause, trimmingStrategy) : null;
    }
}
