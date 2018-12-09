package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.GuardedBy;
import android.support.v4.content.C0235a;
import android.support.v4.p022f.C0464a;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/* renamed from: com.google.firebase.iid.r */
final class C1946r {
    /* renamed from: a */
    private final SharedPreferences f5749a;
    /* renamed from: b */
    private final Context f5750b;
    /* renamed from: c */
    private final am f5751c;
    @GuardedBy("this")
    /* renamed from: d */
    private final Map<String, an> f5752d;

    public C1946r(Context context) {
        this(context, new am());
    }

    private C1946r(Context context, am amVar) {
        this.f5752d = new C0464a();
        this.f5750b = context;
        this.f5749a = context.getSharedPreferences("com.google.android.gms.appid", 0);
        this.f5751c = amVar;
        File file = new File(C0235a.m1074b(this.f5750b), "com.google.android.gms.appid-no-backup");
        if (!file.exists()) {
            try {
                if (file.createNewFile() && !m8877c()) {
                    Log.i("FirebaseInstanceId", "App restored, clearing state");
                    m8883b();
                    FirebaseInstanceId.m8755a().m8779h();
                }
            } catch (IOException e) {
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    String str = "FirebaseInstanceId";
                    String str2 = "Error creating file in no backup dir: ";
                    String valueOf = String.valueOf(e.getMessage());
                    Log.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
            }
        }
    }

    /* renamed from: a */
    static String m8875a(String str, String str2) {
        return new StringBuilder((String.valueOf(str).length() + 3) + String.valueOf(str2).length()).append(str).append("|S|").append(str2).toString();
    }

    /* renamed from: b */
    private static String m8876b(String str, String str2, String str3) {
        return new StringBuilder(((String.valueOf(str).length() + 4) + String.valueOf(str2).length()) + String.valueOf(str3).length()).append(str).append("|T|").append(str2).append("|").append(str3).toString();
    }

    /* renamed from: c */
    private final synchronized boolean m8877c() {
        return this.f5749a.getAll().isEmpty();
    }

    /* renamed from: a */
    public final synchronized C1947s m8878a(String str, String str2, String str3) {
        return C1947s.m8885a(this.f5749a.getString(C1946r.m8876b(str, str2, str3), null));
    }

    /* renamed from: a */
    public final synchronized String m8879a() {
        return this.f5749a.getString("topic_operaion_queue", TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: a */
    public final synchronized void m8880a(String str) {
        this.f5749a.edit().putString("topic_operaion_queue", str).apply();
    }

    /* renamed from: a */
    public final synchronized void m8881a(String str, String str2, String str3, String str4, String str5) {
        String a = C1947s.m8886a(str4, str5, System.currentTimeMillis());
        if (a != null) {
            Editor edit = this.f5749a.edit();
            edit.putString(C1946r.m8876b(str, str2, str3), a);
            edit.commit();
        }
    }

    /* renamed from: b */
    public final synchronized an m8882b(String str) {
        an anVar;
        anVar = (an) this.f5752d.get(str);
        if (anVar == null) {
            try {
                anVar = this.f5751c.m8818a(this.f5750b, str);
            } catch (ao e) {
                Log.w("FirebaseInstanceId", "Stored data is corrupt, generating new identity");
                FirebaseInstanceId.m8755a().m8779h();
                anVar = this.f5751c.m8819b(this.f5750b, str);
            }
            this.f5752d.put(str, anVar);
        }
        return anVar;
    }

    /* renamed from: b */
    public final synchronized void m8883b() {
        this.f5752d.clear();
        am.m8808a(this.f5750b);
        this.f5749a.edit().clear().commit();
    }

    /* renamed from: c */
    public final synchronized void m8884c(String str) {
        String concat = String.valueOf(str).concat("|T|");
        Editor edit = this.f5749a.edit();
        for (String str2 : this.f5749a.getAll().keySet()) {
            if (str2.startsWith(concat)) {
                edit.remove(str2);
            }
        }
        edit.commit();
    }
}
