package com.persianswitch.sdk.base.log;

public final class SDKLog {
    private static ILogger currentLogger = new MemoryLogCatLogger();
    private static int maxPriority = 6;

    public static synchronized void setLogger(ILogger logger) {
        synchronized (SDKLog.class) {
            currentLogger = logger;
        }
    }

    public static synchronized void setMaxPriority(int maxPriority) {
        synchronized (SDKLog.class) {
            maxPriority = maxPriority;
        }
    }

    private static boolean isLoggable(int priority) {
        return priority <= maxPriority;
    }

    /* renamed from: i */
    public static void m38i(String tag, String message, Throwable t, Object... args) {
        prepareLog(4, tag, message, t, args);
    }

    /* renamed from: i */
    public static void m39i(String tag, String message, Object... args) {
        prepareLog(4, tag, message, null, args);
    }

    /* renamed from: d */
    public static void m34d(String tag, String message, Throwable t, Object... args) {
        prepareLog(3, tag, message, t, args);
    }

    /* renamed from: d */
    public static void m35d(String tag, String message, Object... args) {
        prepareLog(3, tag, message, null, args);
    }

    /* renamed from: e */
    public static void m36e(String tag, String message, Throwable t, Object... args) {
        prepareLog(6, tag, message, t, args);
    }

    /* renamed from: e */
    public static void m37e(String tag, String message, Object... args) {
        prepareLog(6, tag, message, null, args);
    }

    private static void prepareLog(int priority, String tag, String message, Throwable t, Object... args) {
        if (isLoggable(priority)) {
            try {
                currentLogger.log(priority, tag, message, t, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String collectLog() {
        return currentLogger.collect();
    }

    public static void clearLog() {
        currentLogger.clear();
    }
}
