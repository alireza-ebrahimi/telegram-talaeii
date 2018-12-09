package android.support.v7.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.C0236a;
import android.support.v4.app.C0353t;
import android.support.v4.app.ag;
import android.support.v4.app.bg;
import android.support.v4.app.bg.C0320a;
import android.support.v4.view.C0630h;
import android.support.v7.view.C0814b;
import android.support.v7.view.C0814b.C0797a;
import android.support.v7.widget.bm;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/* renamed from: android.support.v7.app.c */
public class C0768c extends C0353t implements C0320a, C0145d {
    /* renamed from: n */
    private C0769e f1743n;
    /* renamed from: o */
    private int f1744o = 0;
    /* renamed from: p */
    private boolean f1745p;
    /* renamed from: q */
    private Resources f1746q;

    /* renamed from: a */
    public Intent mo601a() {
        return ag.m1188a(this);
    }

    /* renamed from: a */
    public C0814b mo133a(C0797a c0797a) {
        return null;
    }

    /* renamed from: a */
    public void m3644a(bg bgVar) {
        bgVar.m1427a((Activity) this);
    }

    /* renamed from: a */
    public void mo134a(C0814b c0814b) {
    }

    /* renamed from: a */
    public boolean m3646a(Intent intent) {
        return ag.m1190a((Activity) this, intent);
    }

    public void addContentView(View view, LayoutParams layoutParams) {
        m3654i().mo642b(view, layoutParams);
    }

    /* renamed from: b */
    public void m3647b(Intent intent) {
        ag.m1193b((Activity) this, intent);
    }

    /* renamed from: b */
    public void m3648b(bg bgVar) {
    }

    /* renamed from: b */
    public void mo135b(C0814b c0814b) {
    }

    /* renamed from: c */
    public void mo602c() {
        m3654i().mo647f();
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (C0630h.m3138b(keyEvent) && keyEvent.getUnicodeChar(keyEvent.getMetaState() & -28673) == 60) {
            int action = keyEvent.getAction();
            if (action == 0) {
                C0765a f = m3651f();
                if (f != null && f.mo669b() && f.mo676g()) {
                    this.f1745p = true;
                    return true;
                }
            } else if (action == 1 && this.f1745p) {
                this.f1745p = false;
                return true;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    /* renamed from: f */
    public C0765a m3651f() {
        return m3654i().mo618a();
    }

    public View findViewById(int i) {
        return m3654i().mo630a(i);
    }

    /* renamed from: g */
    public boolean m3652g() {
        Intent a = mo601a();
        if (a == null) {
            return false;
        }
        if (m3646a(a)) {
            bg a2 = bg.m1426a((Context) this);
            m3644a(a2);
            m3648b(a2);
            a2.m1430a();
            try {
                C0236a.m1077a(this);
            } catch (IllegalStateException e) {
                finish();
            }
        } else {
            m3647b(a);
        }
        return true;
    }

    public MenuInflater getMenuInflater() {
        return m3654i().mo620b();
    }

    public Resources getResources() {
        if (this.f1746q == null && bm.m5716a()) {
            this.f1746q = new bm(this, super.getResources());
        }
        return this.f1746q == null ? super.getResources() : this.f1746q;
    }

    @Deprecated
    /* renamed from: h */
    public void m3653h() {
    }

    /* renamed from: i */
    public C0769e m3654i() {
        if (this.f1743n == null) {
            this.f1743n = C0769e.m3655a((Activity) this, (C0145d) this);
        }
        return this.f1743n;
    }

    public void invalidateOptionsMenu() {
        m3654i().mo647f();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        m3654i().mo632a(configuration);
        if (this.f1746q != null) {
            this.f1746q.updateConfiguration(configuration, super.getResources().getDisplayMetrics());
        }
    }

    public void onContentChanged() {
        m3653h();
    }

    protected void onCreate(Bundle bundle) {
        C0769e i = m3654i();
        i.mo648h();
        i.mo633a(bundle);
        if (i.mo625i() && this.f1744o != 0) {
            if (VERSION.SDK_INT >= 23) {
                onApplyThemeResource(getTheme(), this.f1744o, false);
            } else {
                setTheme(this.f1744o);
            }
        }
        super.onCreate(bundle);
    }

    protected void onDestroy() {
        super.onDestroy();
        m3654i().mo624g();
    }

    public final boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        C0765a f = m3651f();
        return (menuItem.getItemId() != 16908332 || f == null || (f.mo663a() & 4) == 0) ? false : m3652g();
    }

    public boolean onMenuOpened(int i, Menu menu) {
        return super.onMenuOpened(i, menu);
    }

    public void onPanelClosed(int i, Menu menu) {
        super.onPanelClosed(i, menu);
    }

    protected void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        m3654i().mo641b(bundle);
    }

    protected void onPostResume() {
        super.onPostResume();
        m3654i().mo646e();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        m3654i().mo622c(bundle);
    }

    protected void onStart() {
        super.onStart();
        m3654i().mo621c();
    }

    protected void onStop() {
        super.onStop();
        m3654i().mo623d();
    }

    protected void onTitleChanged(CharSequence charSequence, int i) {
        super.onTitleChanged(charSequence, i);
        m3654i().mo619a(charSequence);
    }

    public void setContentView(int i) {
        m3654i().mo640b(i);
    }

    public void setContentView(View view) {
        m3654i().mo635a(view);
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        m3654i().mo636a(view, layoutParams);
    }

    public void setTheme(int i) {
        super.setTheme(i);
        this.f1744o = i;
    }
}
