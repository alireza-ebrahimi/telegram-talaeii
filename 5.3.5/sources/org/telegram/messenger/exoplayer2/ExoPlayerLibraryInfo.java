package org.telegram.messenger.exoplayer2;

import java.util.HashSet;

public final class ExoPlayerLibraryInfo {
    public static final boolean ASSERTIONS_ENABLED = true;
    public static final String TAG = "ExoPlayer";
    public static final boolean TRACE_ENABLED = true;
    public static final String VERSION = "2.5.4";
    public static final int VERSION_INT = 2005004;
    public static final String VERSION_SLASHY = "ExoPlayerLib/2.5.4";
    private static final HashSet<String> registeredModules = new HashSet();
    private static String registeredModulesString = "goog.exo.core";

    private ExoPlayerLibraryInfo() {
    }

    public static synchronized String registeredModules() {
        String str;
        synchronized (ExoPlayerLibraryInfo.class) {
            str = registeredModulesString;
        }
        return str;
    }

    public static synchronized void registerModule(String name) {
        synchronized (ExoPlayerLibraryInfo.class) {
            if (registeredModules.add(name)) {
                registeredModulesString += ", " + name;
            }
        }
    }
}
