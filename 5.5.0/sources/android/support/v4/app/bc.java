package android.support.v4.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Parcelable;
import java.util.ArrayList;

public final class bc {
    /* renamed from: a */
    static C0316b f1016a;

    /* renamed from: android.support.v4.app.bc$a */
    public static class C0315a {
        /* renamed from: a */
        private Activity f1010a;
        /* renamed from: b */
        private Intent f1011b = new Intent().setAction("android.intent.action.SEND");
        /* renamed from: c */
        private ArrayList<String> f1012c;
        /* renamed from: d */
        private ArrayList<String> f1013d;
        /* renamed from: e */
        private ArrayList<String> f1014e;
        /* renamed from: f */
        private ArrayList<Uri> f1015f;

        private C0315a(Activity activity) {
            this.f1010a = activity;
            this.f1011b.putExtra("android.support.v4.app.EXTRA_CALLING_PACKAGE", activity.getPackageName());
            this.f1011b.putExtra("android.support.v4.app.EXTRA_CALLING_ACTIVITY", activity.getComponentName());
            this.f1011b.addFlags(524288);
        }

        /* renamed from: a */
        public static C0315a m1416a(Activity activity) {
            return new C0315a(activity);
        }

        /* renamed from: a */
        private void m1417a(String str, ArrayList<String> arrayList) {
            Object stringArrayExtra = this.f1011b.getStringArrayExtra(str);
            int length = stringArrayExtra != null ? stringArrayExtra.length : 0;
            Object obj = new String[(arrayList.size() + length)];
            arrayList.toArray(obj);
            if (stringArrayExtra != null) {
                System.arraycopy(stringArrayExtra, 0, obj, arrayList.size(), length);
            }
            this.f1011b.putExtra(str, obj);
        }

        /* renamed from: a */
        public Intent m1418a() {
            if (this.f1012c != null) {
                m1417a("android.intent.extra.EMAIL", this.f1012c);
                this.f1012c = null;
            }
            if (this.f1013d != null) {
                m1417a("android.intent.extra.CC", this.f1013d);
                this.f1013d = null;
            }
            if (this.f1014e != null) {
                m1417a("android.intent.extra.BCC", this.f1014e);
                this.f1014e = null;
            }
            int i = (this.f1015f == null || this.f1015f.size() <= 1) ? 0 : 1;
            boolean equals = this.f1011b.getAction().equals("android.intent.action.SEND_MULTIPLE");
            if (i == 0 && equals) {
                this.f1011b.setAction("android.intent.action.SEND");
                if (this.f1015f == null || this.f1015f.isEmpty()) {
                    this.f1011b.removeExtra("android.intent.extra.STREAM");
                } else {
                    this.f1011b.putExtra("android.intent.extra.STREAM", (Parcelable) this.f1015f.get(0));
                }
                this.f1015f = null;
            }
            if (!(i == 0 || equals)) {
                this.f1011b.setAction("android.intent.action.SEND_MULTIPLE");
                if (this.f1015f == null || this.f1015f.isEmpty()) {
                    this.f1011b.removeExtra("android.intent.extra.STREAM");
                } else {
                    this.f1011b.putParcelableArrayListExtra("android.intent.extra.STREAM", this.f1015f);
                }
            }
            return this.f1011b;
        }

        /* renamed from: a */
        public C0315a m1419a(CharSequence charSequence) {
            this.f1011b.putExtra("android.intent.extra.TEXT", charSequence);
            return this;
        }

        /* renamed from: a */
        public C0315a m1420a(String str) {
            this.f1011b.setType(str);
            return this;
        }

        /* renamed from: b */
        public C0315a m1421b(String str) {
            this.f1011b.putExtra("android.intent.extra.SUBJECT", str);
            return this;
        }
    }

    /* renamed from: android.support.v4.app.bc$b */
    interface C0316b {
    }

    /* renamed from: android.support.v4.app.bc$c */
    static class C0317c implements C0316b {
        C0317c() {
        }
    }

    /* renamed from: android.support.v4.app.bc$d */
    static class C0318d extends C0317c {
        C0318d() {
        }
    }

    /* renamed from: android.support.v4.app.bc$e */
    static class C0319e extends C0318d {
        C0319e() {
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            f1016a = new C0319e();
        } else if (VERSION.SDK_INT >= 14) {
            f1016a = new C0318d();
        } else {
            f1016a = new C0317c();
        }
    }
}
