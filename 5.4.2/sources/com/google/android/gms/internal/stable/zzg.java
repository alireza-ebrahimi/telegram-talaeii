package com.google.android.gms.internal.stable;

import android.content.ContentResolver;
import android.net.Uri;
import com.google.android.gms.internal.stable.zze.zza;

public final class zzg extends zza {
    private static final Uri CONTENT_URI = Uri.parse("content://com.google.settings/partner");

    public static int getInt(ContentResolver contentResolver, String str, int i) {
        int i2 = -1;
        String string = getString(contentResolver, str);
        if (string != null) {
            try {
                i2 = Integer.parseInt(string);
            } catch (NumberFormatException e) {
            }
        }
        return i2;
    }

    private static String getString(ContentResolver contentResolver, String str) {
        return zza.zza(contentResolver, CONTENT_URI, str);
    }

    public static String zza(ContentResolver contentResolver, String str, String str2) {
        String string = getString(contentResolver, str);
        return string == null ? str2 : string;
    }
}
