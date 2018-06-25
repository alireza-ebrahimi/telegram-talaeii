package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.p014d.C0432c;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

/* renamed from: android.support.v7.app.e */
public abstract class C0769e {
    /* renamed from: a */
    private static int f1747a = -1;
    /* renamed from: b */
    private static boolean f1748b = false;

    C0769e() {
    }

    /* renamed from: a */
    public static C0769e m3655a(Activity activity, C0145d c0145d) {
        return C0769e.m3657a(activity, activity.getWindow(), c0145d);
    }

    /* renamed from: a */
    public static C0769e m3656a(Dialog dialog, C0145d c0145d) {
        return C0769e.m3657a(dialog.getContext(), dialog.getWindow(), c0145d);
    }

    /* renamed from: a */
    private static C0769e m3657a(Context context, Window window, C0145d c0145d) {
        int i = VERSION.SDK_INT;
        return C0432c.m1912a() ? new C0782g(context, window, c0145d) : i >= 23 ? new C0781j(context, window, c0145d) : i >= 14 ? new C0780i(context, window, c0145d) : i >= 11 ? new C0779h(context, window, c0145d) : new C0778k(context, window, c0145d);
    }

    /* renamed from: a */
    public static void m3658a(boolean z) {
        f1748b = z;
    }

    /* renamed from: j */
    public static int m3659j() {
        return f1747a;
    }

    /* renamed from: k */
    public static boolean m3660k() {
        return f1748b;
    }

    /* renamed from: a */
    public abstract C0765a mo618a();

    /* renamed from: a */
    public abstract View mo630a(int i);

    /* renamed from: a */
    public abstract void mo632a(Configuration configuration);

    /* renamed from: a */
    public abstract void mo633a(Bundle bundle);

    /* renamed from: a */
    public abstract void mo635a(View view);

    /* renamed from: a */
    public abstract void mo636a(View view, LayoutParams layoutParams);

    /* renamed from: a */
    public abstract void mo619a(CharSequence charSequence);

    /* renamed from: b */
    public abstract MenuInflater mo620b();

    /* renamed from: b */
    public abstract void mo640b(int i);

    /* renamed from: b */
    public abstract void mo641b(Bundle bundle);

    /* renamed from: b */
    public abstract void mo642b(View view, LayoutParams layoutParams);

    /* renamed from: c */
    public abstract void mo621c();

    /* renamed from: c */
    public abstract void mo622c(Bundle bundle);

    /* renamed from: c */
    public abstract boolean mo645c(int i);

    /* renamed from: d */
    public abstract void mo623d();

    /* renamed from: e */
    public abstract void mo646e();

    /* renamed from: f */
    public abstract void mo647f();

    /* renamed from: g */
    public abstract void mo624g();

    /* renamed from: h */
    public abstract void mo648h();

    /* renamed from: i */
    public abstract boolean mo625i();
}
