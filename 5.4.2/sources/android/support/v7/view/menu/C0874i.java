package android.support.v7.view.menu;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.IBinder;
import android.support.v7.app.C0767b;
import android.support.v7.app.C0767b.C0766a;
import android.support.v7.p025a.C0748a.C0744g;
import android.support.v7.view.menu.C0859o.C0794a;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

/* renamed from: android.support.v7.view.menu.i */
class C0874i implements OnClickListener, OnDismissListener, OnKeyListener, C0794a {
    /* renamed from: a */
    C0871f f2200a;
    /* renamed from: b */
    private C0873h f2201b;
    /* renamed from: c */
    private C0767b f2202c;
    /* renamed from: d */
    private C0794a f2203d;

    public C0874i(C0873h c0873h) {
        this.f2201b = c0873h;
    }

    /* renamed from: a */
    public void m4262a() {
        if (this.f2202c != null) {
            this.f2202c.dismiss();
        }
    }

    /* renamed from: a */
    public void m4263a(IBinder iBinder) {
        C0873h c0873h = this.f2201b;
        C0766a c0766a = new C0766a(c0873h.m4247e());
        this.f2200a = new C0871f(c0766a.m3629a(), C0744g.abc_list_menu_item_layout);
        this.f2200a.mo721a((C0794a) this);
        this.f2201b.m4226a(this.f2200a);
        c0766a.m3634a(this.f2200a.m4194a(), (OnClickListener) this);
        View o = c0873h.m4258o();
        if (o != null) {
            c0766a.m3633a(o);
        } else {
            c0766a.m3632a(c0873h.m4257n()).m3635a(c0873h.m4256m());
        }
        c0766a.m3631a((OnKeyListener) this);
        this.f2202c = c0766a.m3639b();
        this.f2202c.setOnDismissListener(this);
        LayoutParams attributes = this.f2202c.getWindow().getAttributes();
        attributes.type = 1003;
        if (iBinder != null) {
            attributes.token = iBinder;
        }
        attributes.flags |= 131072;
        this.f2202c.show();
    }

    /* renamed from: a */
    public void mo657a(C0873h c0873h, boolean z) {
        if (z || c0873h == this.f2201b) {
            m4262a();
        }
        if (this.f2203d != null) {
            this.f2203d.mo657a(c0873h, z);
        }
    }

    /* renamed from: a */
    public boolean mo658a(C0873h c0873h) {
        return this.f2203d != null ? this.f2203d.mo658a(c0873h) : false;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.f2201b.m4232a((C0876j) this.f2200a.m4194a().getItem(i), 0);
    }

    public void onDismiss(DialogInterface dialogInterface) {
        this.f2200a.mo720a(this.f2201b, true);
    }

    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 82 || i == 4) {
            Window window;
            View decorView;
            DispatcherState keyDispatcherState;
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                window = this.f2202c.getWindow();
                if (window != null) {
                    decorView = window.getDecorView();
                    if (decorView != null) {
                        keyDispatcherState = decorView.getKeyDispatcherState();
                        if (keyDispatcherState != null) {
                            keyDispatcherState.startTracking(keyEvent, this);
                            return true;
                        }
                    }
                }
            } else if (keyEvent.getAction() == 1 && !keyEvent.isCanceled()) {
                window = this.f2202c.getWindow();
                if (window != null) {
                    decorView = window.getDecorView();
                    if (decorView != null) {
                        keyDispatcherState = decorView.getKeyDispatcherState();
                        if (keyDispatcherState != null && keyDispatcherState.isTracking(keyEvent)) {
                            this.f2201b.m4230a(true);
                            dialogInterface.dismiss();
                            return true;
                        }
                    }
                }
            }
        }
        return this.f2201b.performShortcut(i, keyEvent, 0);
    }
}
