package com.p077f.p078a.p095c;

import android.content.Context;
import android.os.Environment;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import java.io.File;
import java.io.IOException;

/* renamed from: com.f.a.c.e */
public final class C1605e {
    /* renamed from: a */
    public static File m7946a(Context context) {
        return C1605e.m7948a(context, true);
    }

    /* renamed from: a */
    public static File m7947a(Context context, String str) {
        File a = C1605e.m7946a(context);
        File file = new File(a, str);
        return (file.exists() || file.mkdir()) ? file : a;
    }

    /* renamed from: a */
    public static File m7948a(Context context, boolean z) {
        Object externalStorageState;
        File file = null;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) {
            externalStorageState = TtmlNode.ANONYMOUS_REGION_ID;
        } catch (IncompatibleClassChangeError e2) {
            externalStorageState = TtmlNode.ANONYMOUS_REGION_ID;
        }
        if (z && "mounted".equals(r1) && C1605e.m7951d(context)) {
            file = C1605e.m7950c(context);
        }
        if (file == null) {
            file = context.getCacheDir();
        }
        if (file != null) {
            return file;
        }
        C1602c.m7941c("Can't define system cache directory! '%s' will be used.", "/data/data/" + context.getPackageName() + "/cache/");
        return new File("/data/data/" + context.getPackageName() + "/cache/");
    }

    /* renamed from: b */
    public static File m7949b(Context context) {
        return C1605e.m7947a(context, "uil-images");
    }

    /* renamed from: c */
    private static File m7950c(Context context) {
        File file = new File(new File(new File(new File(Environment.getExternalStorageDirectory(), "Android"), DataBufferSafeParcelable.DATA_FIELD), context.getPackageName()), "cache");
        if (file.exists()) {
            return file;
        }
        if (file.mkdirs()) {
            try {
                new File(file, ".nomedia").createNewFile();
                return file;
            } catch (IOException e) {
                C1602c.m7939b("Can't create \".nomedia\" file in application external cache directory", new Object[0]);
                return file;
            }
        }
        C1602c.m7941c("Unable to create external cache directory", new Object[0]);
        return null;
    }

    /* renamed from: d */
    private static boolean m7951d(Context context) {
        return context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0;
    }
}
