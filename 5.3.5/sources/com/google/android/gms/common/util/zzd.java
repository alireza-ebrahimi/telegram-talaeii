package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbih;

@Hide
public final class zzd {
    public static int zzt(Context context, String str) {
        PackageInfo zzu = zzu(context, str);
        if (zzu == null || zzu.applicationInfo == null) {
            return -1;
        }
        Bundle bundle = zzu.applicationInfo.metaData;
        return bundle != null ? bundle.getInt("com.google.android.gms.version", -1) : -1;
    }

    @Nullable
    private static PackageInfo zzu(Context context, String str) {
        try {
            return zzbih.zzdd(context).getPackageInfo(str, 128);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static boolean zzv(Context context, String str) {
        "com.google.android.gms".equals(str);
        try {
            return (zzbih.zzdd(context).getApplicationInfo(str, 0).flags & 2097152) != 0;
        } catch (NameNotFoundException e) {
            return false;
        }
    }
}
