package p033b.p034a.p035a.p036a.p037a.p045g;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;

/* renamed from: b.a.a.a.a.g.n */
public class C1212n {
    /* renamed from: a */
    public final String f3493a;
    /* renamed from: b */
    public final int f3494b;
    /* renamed from: c */
    public final int f3495c;
    /* renamed from: d */
    public final int f3496d;

    public C1212n(String str, int i, int i2, int i3) {
        this.f3493a = str;
        this.f3494b = i;
        this.f3495c = i2;
        this.f3496d = i3;
    }

    /* renamed from: a */
    public static C1212n m6359a(Context context, String str) {
        if (str != null) {
            try {
                int l = C1110i.m6033l(context);
                C1230c.m6414h().mo1062a("Fabric", "App icon resource ID is " + l);
                Options options = new Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(context.getResources(), l, options);
                return new C1212n(str, l, options.outWidth, options.outHeight);
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("Fabric", "Failed to load icon", e);
            }
        }
        return null;
    }
}
