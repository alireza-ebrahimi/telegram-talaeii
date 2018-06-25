package android.support.v7.app;

import android.annotation.TargetApi;
import android.app.UiModeManager;
import android.content.Context;
import android.support.v7.app.C0780i.C0774a;
import android.view.ActionMode;
import android.view.Window;
import android.view.Window.Callback;

@TargetApi(23)
/* renamed from: android.support.v7.app.j */
class C0781j extends C0780i {
    /* renamed from: t */
    private final UiModeManager f1803t;

    /* renamed from: android.support.v7.app.j$a */
    class C0775a extends C0774a {
        /* renamed from: d */
        final /* synthetic */ C0781j f1771d;

        C0775a(C0781j c0781j, Callback callback) {
            this.f1771d = c0781j;
            super(c0781j, callback);
        }

        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            return null;
        }

        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
            if (this.f1771d.mo653o()) {
                switch (i) {
                    case 0:
                        return m3704a(callback);
                }
            }
            return super.onWindowStartingActionMode(callback, i);
        }
    }

    C0781j(Context context, Window window, C0145d c0145d) {
        super(context, window, c0145d);
        this.f1803t = (UiModeManager) context.getSystemService("uimode");
    }

    /* renamed from: a */
    Callback mo651a(Callback callback) {
        return new C0775a(this, callback);
    }

    /* renamed from: d */
    int mo652d(int i) {
        return (i == 0 && this.f1803t.getNightMode() == 0) ? -1 : super.mo652d(i);
    }
}
