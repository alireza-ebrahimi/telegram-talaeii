package net.hockeyapp.android.p137e;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/* renamed from: net.hockeyapp.android.e.g */
public class C2405g {
    /* renamed from: a */
    private SharedPreferences f8090a;
    /* renamed from: b */
    private Editor f8091b;
    /* renamed from: c */
    private SharedPreferences f8092c;
    /* renamed from: d */
    private Editor f8093d;

    /* renamed from: net.hockeyapp.android.e.g$a */
    private static class C2404a {
        /* renamed from: a */
        public static final C2405g f8089a = new C2405g();
    }

    private C2405g() {
    }

    /* renamed from: a */
    public static C2405g m11864a() {
        return C2404a.f8089a;
    }

    /* renamed from: a */
    public String m11865a(Context context) {
        if (context == null) {
            return null;
        }
        this.f8090a = context.getSharedPreferences("net.hockeyapp.android.prefs_feedback_token", 0);
        return this.f8090a != null ? this.f8090a.getString("net.hockeyapp.android.prefs_key_feedback_token", null) : null;
    }

    /* renamed from: a */
    public void m11866a(Context context, String str) {
        if (context != null) {
            this.f8090a = context.getSharedPreferences("net.hockeyapp.android.prefs_feedback_token", 0);
            if (this.f8090a != null) {
                this.f8091b = this.f8090a.edit();
                this.f8091b.putString("net.hockeyapp.android.prefs_key_feedback_token", str);
                this.f8091b.apply();
            }
        }
    }

    /* renamed from: a */
    public void m11867a(Context context, String str, String str2, String str3) {
        if (context != null) {
            this.f8092c = context.getSharedPreferences("net.hockeyapp.android.prefs_name_email", 0);
            if (this.f8092c != null) {
                this.f8093d = this.f8092c.edit();
                if (str == null || str2 == null || str3 == null) {
                    this.f8093d.putString("net.hockeyapp.android.prefs_key_name_email", null);
                } else {
                    this.f8093d.putString("net.hockeyapp.android.prefs_key_name_email", String.format("%s|%s|%s", new Object[]{str, str2, str3}));
                }
                this.f8093d.apply();
            }
        }
    }

    /* renamed from: b */
    public String m11868b(Context context) {
        if (context == null) {
            return null;
        }
        this.f8092c = context.getSharedPreferences("net.hockeyapp.android.prefs_name_email", 0);
        return this.f8092c != null ? this.f8092c.getString("net.hockeyapp.android.prefs_key_name_email", null) : null;
    }
}
