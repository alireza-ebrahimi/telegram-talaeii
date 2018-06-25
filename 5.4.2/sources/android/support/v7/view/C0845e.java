package android.support.v7.view;

import android.content.Context;
import android.support.v7.view.C0814b.C0797a;
import android.support.v7.view.menu.C0873h;
import android.support.v7.view.menu.C0873h.C0777a;
import android.support.v7.widget.ActionBarContextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.lang.ref.WeakReference;

/* renamed from: android.support.v7.view.e */
public class C0845e extends C0814b implements C0777a {
    /* renamed from: a */
    private Context f1996a;
    /* renamed from: b */
    private ActionBarContextView f1997b;
    /* renamed from: c */
    private C0797a f1998c;
    /* renamed from: d */
    private WeakReference<View> f1999d;
    /* renamed from: e */
    private boolean f2000e;
    /* renamed from: f */
    private boolean f2001f;
    /* renamed from: g */
    private C0873h f2002g;

    public C0845e(Context context, ActionBarContextView actionBarContextView, C0797a c0797a, boolean z) {
        this.f1996a = context;
        this.f1997b = actionBarContextView;
        this.f1998c = c0797a;
        this.f2002g = new C0873h(actionBarContextView.getContext()).m4216a(1);
        this.f2002g.mo757a((C0777a) this);
        this.f2001f = z;
    }

    /* renamed from: a */
    public MenuInflater mo679a() {
        return new C0850g(this.f1997b.getContext());
    }

    /* renamed from: a */
    public void mo680a(int i) {
        mo686b(this.f1996a.getString(i));
    }

    /* renamed from: a */
    public void mo634a(C0873h c0873h) {
        mo688d();
        this.f1997b.mo766a();
    }

    /* renamed from: a */
    public void mo681a(View view) {
        this.f1997b.setCustomView(view);
        this.f1999d = view != null ? new WeakReference(view) : null;
    }

    /* renamed from: a */
    public void mo682a(CharSequence charSequence) {
        this.f1997b.setSubtitle(charSequence);
    }

    /* renamed from: a */
    public void mo683a(boolean z) {
        super.mo683a(z);
        this.f1997b.setTitleOptional(z);
    }

    /* renamed from: a */
    public boolean mo638a(C0873h c0873h, MenuItem menuItem) {
        return this.f1998c.mo661a((C0814b) this, menuItem);
    }

    /* renamed from: b */
    public Menu mo684b() {
        return this.f2002g;
    }

    /* renamed from: b */
    public void mo685b(int i) {
        mo682a(this.f1996a.getString(i));
    }

    /* renamed from: b */
    public void mo686b(CharSequence charSequence) {
        this.f1997b.setTitle(charSequence);
    }

    /* renamed from: c */
    public void mo687c() {
        if (!this.f2000e) {
            this.f2000e = true;
            this.f1997b.sendAccessibilityEvent(32);
            this.f1998c.mo659a(this);
        }
    }

    /* renamed from: d */
    public void mo688d() {
        this.f1998c.mo662b(this, this.f2002g);
    }

    /* renamed from: f */
    public CharSequence mo689f() {
        return this.f1997b.getTitle();
    }

    /* renamed from: g */
    public CharSequence mo690g() {
        return this.f1997b.getSubtitle();
    }

    /* renamed from: h */
    public boolean mo691h() {
        return this.f1997b.m4370d();
    }

    /* renamed from: i */
    public View mo692i() {
        return this.f1999d != null ? (View) this.f1999d.get() : null;
    }
}
