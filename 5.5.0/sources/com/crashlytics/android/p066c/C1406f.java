package com.crashlytics.android.p066c;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.concurrent.CountDownLatch;
import p033b.p034a.p035a.p036a.p037a.p045g.C1213o;

/* renamed from: com.crashlytics.android.c.f */
class C1406f {
    /* renamed from: a */
    private final C1405b f4254a;
    /* renamed from: b */
    private final Builder f4255b;

    /* renamed from: com.crashlytics.android.c.f$a */
    interface C1404a {
        /* renamed from: a */
        void mo1165a(boolean z);
    }

    /* renamed from: com.crashlytics.android.c.f$b */
    private static class C1405b {
        /* renamed from: a */
        private boolean f4252a;
        /* renamed from: b */
        private final CountDownLatch f4253b;

        private C1405b() {
            this.f4252a = false;
            this.f4253b = new CountDownLatch(1);
        }

        /* renamed from: a */
        void m7098a(boolean z) {
            this.f4252a = z;
            this.f4253b.countDown();
        }

        /* renamed from: a */
        boolean m7099a() {
            return this.f4252a;
        }

        /* renamed from: b */
        void m7100b() {
            try {
                this.f4253b.await();
            } catch (InterruptedException e) {
            }
        }
    }

    private C1406f(Builder builder, C1405b c1405b) {
        this.f4254a = c1405b;
        this.f4255b = builder;
    }

    /* renamed from: a */
    private static int m7101a(float f, int i) {
        return (int) (((float) i) * f);
    }

    /* renamed from: a */
    private static ScrollView m7102a(Activity activity, String str) {
        float f = activity.getResources().getDisplayMetrics().density;
        int a = C1406f.m7101a(f, 5);
        View textView = new TextView(activity);
        textView.setAutoLinkMask(15);
        textView.setText(str);
        textView.setTextAppearance(activity, 16973892);
        textView.setPadding(a, a, a, a);
        textView.setFocusable(false);
        ScrollView scrollView = new ScrollView(activity);
        scrollView.setPadding(C1406f.m7101a(f, 14), C1406f.m7101a(f, 2), C1406f.m7101a(f, 10), C1406f.m7101a(f, 12));
        scrollView.addView(textView);
        return scrollView;
    }

    /* renamed from: a */
    public static C1406f m7103a(Activity activity, C1213o c1213o, final C1404a c1404a) {
        final C1405b c1405b = new C1405b();
        C1456r c1456r = new C1456r(activity, c1213o);
        Builder builder = new Builder(activity);
        View a = C1406f.m7102a(activity, c1456r.m7253b());
        builder.setView(a).setTitle(c1456r.m7252a()).setCancelable(false).setNeutralButton(c1456r.m7254c(), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                c1405b.m7098a(true);
                dialogInterface.dismiss();
            }
        });
        if (c1213o.f3500d) {
            builder.setNegativeButton(c1456r.m7256e(), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    c1405b.m7098a(false);
                    dialogInterface.dismiss();
                }
            });
        }
        if (c1213o.f3502f) {
            builder.setPositiveButton(c1456r.m7255d(), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    c1404a.mo1165a(true);
                    c1405b.m7098a(true);
                    dialogInterface.dismiss();
                }
            });
        }
        return new C1406f(builder, c1405b);
    }

    /* renamed from: a */
    public void m7104a() {
        this.f4255b.show();
    }

    /* renamed from: b */
    public void m7105b() {
        this.f4254a.m7100b();
    }

    /* renamed from: c */
    public boolean m7106c() {
        return this.f4254a.m7099a();
    }
}
