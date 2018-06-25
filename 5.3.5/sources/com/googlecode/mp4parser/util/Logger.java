package com.googlecode.mp4parser.util;

public abstract class Logger {
    public abstract void logDebug(String str);

    public abstract void logError(String str);

    public abstract void logWarn(String str);

    public static Logger getLogger(Class clz) {
        if (System.getProperty("java.vm.name").equalsIgnoreCase("Dalvik")) {
            return new AndroidLogger(clz.getSimpleName());
        }
        return new JuliLogger(clz.getSimpleName());
    }
}
