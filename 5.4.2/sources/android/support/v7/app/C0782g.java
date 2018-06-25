package android.support.v7.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v7.app.C0778k.C0800d;
import android.support.v7.app.C0781j.C0775a;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.Window;
import android.view.Window.Callback;
import java.util.List;

@TargetApi(24)
/* renamed from: android.support.v7.app.g */
class C0782g extends C0781j {

    /* renamed from: android.support.v7.app.g$a */
    class C0776a extends C0775a {
        /* renamed from: b */
        final /* synthetic */ C0782g f1772b;

        C0776a(C0782g c0782g, Callback callback) {
            this.f1772b = c0782g;
            super(c0782g, callback);
        }

        public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
            C0800d a = this.f1772b.m3723a(0, true);
            if (a == null || a.f1839j == null) {
                super.onProvideKeyboardShortcuts(list, menu, i);
            } else {
                super.onProvideKeyboardShortcuts(list, a.f1839j, i);
            }
        }
    }

    C0782g(Context context, Window window, C0145d c0145d) {
        super(context, window, c0145d);
    }

    /* renamed from: a */
    Callback mo651a(Callback callback) {
        return new C0776a(this, callback);
    }
}
