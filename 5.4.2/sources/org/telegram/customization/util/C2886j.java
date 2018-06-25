package org.telegram.customization.util;

import android.content.Context;
import java.io.InputStream;

/* renamed from: org.telegram.customization.util.j */
public class C2886j {
    /* renamed from: a */
    public static String m13401a(Context context, String str) {
        try {
            InputStream open = context.getAssets().open("data/" + str);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
