package android.support.v4.widget;

import android.os.Build.VERSION;
import android.widget.ListView;

/* renamed from: android.support.v4.widget.o */
public final class C0711o {
    /* renamed from: a */
    public static void m3473a(ListView listView, int i) {
        if (VERSION.SDK_INT >= 19) {
            C0713q.m3475a(listView, i);
        } else {
            C0712p.m3474a(listView, i);
        }
    }
}
