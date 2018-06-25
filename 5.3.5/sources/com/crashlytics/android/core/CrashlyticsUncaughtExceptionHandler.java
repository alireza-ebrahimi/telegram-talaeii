package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.atomic.AtomicBoolean;

class CrashlyticsUncaughtExceptionHandler implements UncaughtExceptionHandler {
    private final CrashListener crashListener;
    private final UncaughtExceptionHandler defaultHandler;
    private final AtomicBoolean isHandlingException = new AtomicBoolean(false);

    interface CrashListener {
        void onUncaughtException(Thread thread, Throwable th);
    }

    public CrashlyticsUncaughtExceptionHandler(CrashListener crashListener, UncaughtExceptionHandler defaultHandler) {
        this.crashListener = crashListener;
        this.defaultHandler = defaultHandler;
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        this.isHandlingException.set(true);
        try {
            this.crashListener.onUncaughtException(thread, ex);
        } catch (Exception e) {
            Fabric.getLogger().mo4384e(CrashlyticsCore.TAG, "An error occurred in the uncaught exception handler", e);
        } finally {
            Fabric.getLogger().mo4381d(CrashlyticsCore.TAG, "Crashlytics completed exception processing. Invoking default exception handler.");
            this.defaultHandler.uncaughtException(thread, ex);
            this.isHandlingException.set(false);
        }
    }

    boolean isHandlingException() {
        return this.isHandlingException.get();
    }
}
