package android.support.v7.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.view.C0814b;
import android.support.v7.view.C0814b.C0797a;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/* renamed from: android.support.v7.app.l */
public class C0146l extends Dialog implements C0145d {
    /* renamed from: a */
    private C0769e f454a;

    public C0146l(Context context, int i) {
        super(context, C0146l.m682a(context, i));
        m686b().mo633a(null);
        m686b().mo625i();
    }

    /* renamed from: a */
    private static int m682a(Context context, int i) {
        if (i != 0) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(C0738a.dialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    /* renamed from: a */
    public C0814b mo133a(C0797a c0797a) {
        return null;
    }

    /* renamed from: a */
    public void mo134a(C0814b c0814b) {
    }

    /* renamed from: a */
    public boolean m685a(int i) {
        return m686b().mo645c(i);
    }

    public void addContentView(View view, LayoutParams layoutParams) {
        m686b().mo642b(view, layoutParams);
    }

    /* renamed from: b */
    public C0769e m686b() {
        if (this.f454a == null) {
            this.f454a = C0769e.m3656a((Dialog) this, (C0145d) this);
        }
        return this.f454a;
    }

    /* renamed from: b */
    public void mo135b(C0814b c0814b) {
    }

    public View findViewById(int i) {
        return m686b().mo630a(i);
    }

    public void invalidateOptionsMenu() {
        m686b().mo647f();
    }

    protected void onCreate(Bundle bundle) {
        m686b().mo648h();
        super.onCreate(bundle);
        m686b().mo633a(bundle);
    }

    protected void onStop() {
        super.onStop();
        m686b().mo623d();
    }

    public void setContentView(int i) {
        m686b().mo640b(i);
    }

    public void setContentView(View view) {
        m686b().mo635a(view);
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        m686b().mo636a(view, layoutParams);
    }

    public void setTitle(int i) {
        super.setTitle(i);
        m686b().mo619a(getContext().getString(i));
    }

    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        m686b().mo619a(charSequence);
    }
}
