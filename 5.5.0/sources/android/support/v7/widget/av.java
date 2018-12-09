package android.support.v7.widget;

import android.content.Context;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.view.menu.C0873h;
import android.support.v7.view.menu.C0873h.C0777a;
import android.support.v7.view.menu.C0885n;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow.OnDismissListener;

public class av {
    /* renamed from: a */
    final C0885n f2936a;
    /* renamed from: b */
    C1032b f2937b;
    /* renamed from: c */
    C1031a f2938c;
    /* renamed from: d */
    private final Context f2939d;
    /* renamed from: e */
    private final C0873h f2940e;
    /* renamed from: f */
    private final View f2941f;

    /* renamed from: android.support.v7.widget.av$1 */
    class C10291 implements C0777a {
        /* renamed from: a */
        final /* synthetic */ av f2934a;

        C10291(av avVar) {
            this.f2934a = avVar;
        }

        /* renamed from: a */
        public void mo634a(C0873h c0873h) {
        }

        /* renamed from: a */
        public boolean mo638a(C0873h c0873h, MenuItem menuItem) {
            return this.f2934a.f2937b != null ? this.f2934a.f2937b.onMenuItemClick(menuItem) : false;
        }
    }

    /* renamed from: android.support.v7.widget.av$2 */
    class C10302 implements OnDismissListener {
        /* renamed from: a */
        final /* synthetic */ av f2935a;

        C10302(av avVar) {
            this.f2935a = avVar;
        }

        public void onDismiss() {
            if (this.f2935a.f2938c != null) {
                this.f2935a.f2938c.m5565a(this.f2935a);
            }
        }
    }

    /* renamed from: android.support.v7.widget.av$a */
    public interface C1031a {
        /* renamed from: a */
        void m5565a(av avVar);
    }

    /* renamed from: android.support.v7.widget.av$b */
    public interface C1032b {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public av(Context context, View view) {
        this(context, view, 0);
    }

    public av(Context context, View view, int i) {
        this(context, view, i, C0738a.popupMenuStyle, 0);
    }

    public av(Context context, View view, int i, int i2, int i3) {
        this.f2939d = context;
        this.f2941f = view;
        this.f2940e = new C0873h(context);
        this.f2940e.mo757a(new C10291(this));
        this.f2936a = new C0885n(context, this.f2940e, view, false, i2, i3);
        this.f2936a.m4313a(i);
        this.f2936a.m4316a(new C10302(this));
    }

    /* renamed from: a */
    public Menu m5566a() {
        return this.f2940e;
    }

    /* renamed from: a */
    public void m5567a(C1032b c1032b) {
        this.f2937b = c1032b;
    }

    /* renamed from: b */
    public void m5568b() {
        this.f2936a.m4312a();
    }
}
