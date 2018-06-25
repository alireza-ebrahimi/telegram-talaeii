package android.support.v7.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.view.C0771i;
import android.support.v7.view.C0814b;
import android.support.v7.view.C0814b.C0797a;
import android.support.v7.view.C0850g;
import android.support.v7.view.menu.C0873h;
import android.support.v7.widget.bk;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;
import java.lang.Thread.UncaughtExceptionHandler;

@TargetApi(9)
/* renamed from: android.support.v7.app.f */
abstract class C0773f extends C0769e {
    /* renamed from: m */
    private static boolean f1752m = true;
    /* renamed from: n */
    private static final boolean f1753n = (VERSION.SDK_INT < 21);
    /* renamed from: o */
    private static final int[] f1754o = new int[]{16842836};
    /* renamed from: a */
    final Context f1755a;
    /* renamed from: b */
    final Window f1756b;
    /* renamed from: c */
    final Callback f1757c = this.f1756b.getCallback();
    /* renamed from: d */
    final Callback f1758d;
    /* renamed from: e */
    final C0145d f1759e;
    /* renamed from: f */
    C0765a f1760f;
    /* renamed from: g */
    MenuInflater f1761g;
    /* renamed from: h */
    boolean f1762h;
    /* renamed from: i */
    boolean f1763i;
    /* renamed from: j */
    boolean f1764j;
    /* renamed from: k */
    boolean f1765k;
    /* renamed from: l */
    boolean f1766l;
    /* renamed from: p */
    private CharSequence f1767p;
    /* renamed from: q */
    private boolean f1768q;
    /* renamed from: r */
    private boolean f1769r;

    /* renamed from: android.support.v7.app.f$a */
    class C0772a extends C0771i {
        /* renamed from: a */
        final /* synthetic */ C0773f f1751a;

        C0772a(C0773f c0773f, Callback callback) {
            this.f1751a = c0773f;
            super(callback);
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return this.f1751a.mo639a(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return super.dispatchKeyShortcutEvent(keyEvent) || this.f1751a.mo637a(keyEvent.getKeyCode(), keyEvent);
        }

        public void onContentChanged() {
        }

        public boolean onCreatePanelMenu(int i, Menu menu) {
            return (i != 0 || (menu instanceof C0873h)) ? super.onCreatePanelMenu(i, menu) : false;
        }

        public boolean onMenuOpened(int i, Menu menu) {
            super.onMenuOpened(i, menu);
            this.f1751a.mo644b(i, menu);
            return true;
        }

        public void onPanelClosed(int i, Menu menu) {
            super.onPanelClosed(i, menu);
            this.f1751a.mo631a(i, menu);
        }

        public boolean onPreparePanel(int i, View view, Menu menu) {
            C0873h c0873h = menu instanceof C0873h ? (C0873h) menu : null;
            if (i == 0 && c0873h == null) {
                return false;
            }
            if (c0873h != null) {
                c0873h.m4241c(true);
            }
            boolean onPreparePanel = super.onPreparePanel(i, view, menu);
            if (c0873h == null) {
                return onPreparePanel;
            }
            c0873h.m4241c(false);
            return onPreparePanel;
        }
    }

    static {
        if (f1753n && !f1752m) {
            final UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                /* renamed from: a */
                private boolean m3681a(Throwable th) {
                    if (!(th instanceof NotFoundException)) {
                        return false;
                    }
                    String message = th.getMessage();
                    return message != null ? message.contains("drawable") || message.contains("Drawable") : false;
                }

                public void uncaughtException(Thread thread, Throwable th) {
                    if (m3681a(th)) {
                        Throwable notFoundException = new NotFoundException(th.getMessage() + ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
                        notFoundException.initCause(th.getCause());
                        notFoundException.setStackTrace(th.getStackTrace());
                        defaultUncaughtExceptionHandler.uncaughtException(thread, notFoundException);
                        return;
                    }
                    defaultUncaughtExceptionHandler.uncaughtException(thread, th);
                }
            });
        }
    }

    C0773f(Context context, Window window, C0145d c0145d) {
        this.f1755a = context;
        this.f1756b = window;
        this.f1759e = c0145d;
        if (this.f1757c instanceof C0772a) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        this.f1758d = mo651a(this.f1757c);
        this.f1756b.setCallback(this.f1758d);
        bk a = bk.m5653a(context, null, f1754o);
        Drawable b = a.m5661b(0);
        if (b != null) {
            this.f1756b.setBackgroundDrawable(b);
        }
        a.m5658a();
    }

    /* renamed from: a */
    public C0765a mo618a() {
        mo649l();
        return this.f1760f;
    }

    /* renamed from: a */
    abstract C0814b mo629a(C0797a c0797a);

    /* renamed from: a */
    Callback mo651a(Callback callback) {
        return new C0772a(this, callback);
    }

    /* renamed from: a */
    abstract void mo631a(int i, Menu menu);

    /* renamed from: a */
    public final void mo619a(CharSequence charSequence) {
        this.f1767p = charSequence;
        mo643b(charSequence);
    }

    /* renamed from: a */
    abstract boolean mo637a(int i, KeyEvent keyEvent);

    /* renamed from: a */
    abstract boolean mo639a(KeyEvent keyEvent);

    /* renamed from: b */
    public MenuInflater mo620b() {
        if (this.f1761g == null) {
            mo649l();
            this.f1761g = new C0850g(this.f1760f != null ? this.f1760f.mo670c() : this.f1755a);
        }
        return this.f1761g;
    }

    /* renamed from: b */
    abstract void mo643b(CharSequence charSequence);

    /* renamed from: b */
    abstract boolean mo644b(int i, Menu menu);

    /* renamed from: c */
    public void mo621c() {
        this.f1768q = true;
    }

    /* renamed from: c */
    public void mo622c(Bundle bundle) {
    }

    /* renamed from: d */
    public void mo623d() {
        this.f1768q = false;
    }

    /* renamed from: g */
    public void mo624g() {
        this.f1769r = true;
    }

    /* renamed from: i */
    public boolean mo625i() {
        return false;
    }

    /* renamed from: l */
    abstract void mo649l();

    /* renamed from: m */
    final C0765a m3698m() {
        return this.f1760f;
    }

    /* renamed from: n */
    final Context m3699n() {
        Context context = null;
        C0765a a = mo618a();
        if (a != null) {
            context = a.mo670c();
        }
        return context == null ? this.f1755a : context;
    }

    /* renamed from: o */
    public boolean mo653o() {
        return false;
    }

    /* renamed from: p */
    final boolean m3701p() {
        return this.f1769r;
    }

    /* renamed from: q */
    final Callback m3702q() {
        return this.f1756b.getCallback();
    }

    /* renamed from: r */
    final CharSequence m3703r() {
        return this.f1757c instanceof Activity ? ((Activity) this.f1757c).getTitle() : this.f1767p;
    }
}
