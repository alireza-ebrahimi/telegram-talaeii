package org.telegram.customization.util.view.sva.utils;

import android.util.Log;

public final class LogHelper {
    private static final String CLASS_METHOD_LINE_FORMAT = "%s.%s()_%s";
    private static final String TAG = "JJSearchView";
    public static final boolean mIsDebugMode = true;

    public static void trace(String str) {
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        className = className.substring(className.lastIndexOf(".") + 1);
        Log.i(TAG, String.format(CLASS_METHOD_LINE_FORMAT, new Object[]{className, traceElement.getMethodName(), String.valueOf(traceElement.getLineNumber())}) + "->" + str);
    }

    public static void printStackTrace(Throwable throwable) {
        Log.w(TAG, "", throwable);
    }
}
