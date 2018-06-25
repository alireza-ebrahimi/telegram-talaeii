package p033b.p034a.p035a.p036a.p037a.p044f;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import p033b.p034a.p035a.p036a.C1237i;

/* renamed from: b.a.a.a.a.f.d */
public class C1195d implements C1194c {
    /* renamed from: a */
    private final SharedPreferences f3444a;
    /* renamed from: b */
    private final String f3445b;
    /* renamed from: c */
    private final Context f3446c;

    public C1195d(Context context, String str) {
        if (context == null) {
            throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
        }
        this.f3446c = context;
        this.f3445b = str;
        this.f3444a = this.f3446c.getSharedPreferences(this.f3445b, 0);
    }

    @Deprecated
    public C1195d(C1237i c1237i) {
        this(c1237i.m6452q(), c1237i.getClass().getName());
    }

    /* renamed from: a */
    public SharedPreferences mo1051a() {
        return this.f3444a;
    }

    @TargetApi(9)
    /* renamed from: a */
    public boolean mo1052a(Editor editor) {
        if (VERSION.SDK_INT < 9) {
            return editor.commit();
        }
        editor.apply();
        return true;
    }

    /* renamed from: b */
    public Editor mo1053b() {
        return this.f3444a.edit();
    }
}
