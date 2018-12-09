package com.google.android.gms.common.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class InputMethodUtils {
    private InputMethodUtils() {
    }

    public static boolean hideSoftInput(Context context, View view) {
        InputMethodManager zzf = zzf(context);
        if (zzf == null) {
            return false;
        }
        zzf.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return true;
    }

    public static boolean isAcceptingText(Context context) {
        InputMethodManager zzf = zzf(context);
        return zzf != null ? zzf.isAcceptingText() : false;
    }

    public static void restart(Context context, View view) {
        InputMethodManager zzf = zzf(context);
        if (zzf != null) {
            zzf.restartInput(view);
        }
    }

    public static boolean showSoftInput(Context context, View view) {
        InputMethodManager zzf = zzf(context);
        if (zzf == null) {
            return false;
        }
        zzf.showSoftInput(view, 0);
        return true;
    }

    private static InputMethodManager zzf(Context context) {
        return (InputMethodManager) context.getSystemService("input_method");
    }
}
