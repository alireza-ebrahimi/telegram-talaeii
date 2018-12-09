package p033b.p034a.p035a.p036a.p037a.p039b;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;

/* renamed from: b.a.a.a.a.b.p */
public class C1122p {
    /* renamed from: e */
    private static final Pattern f3294e = Pattern.compile("[^\\p{Alnum}]");
    /* renamed from: f */
    private static final String f3295f = Pattern.quote("/");
    /* renamed from: a */
    C1100c f3296a;
    /* renamed from: b */
    C1097b f3297b;
    /* renamed from: c */
    boolean f3298c;
    /* renamed from: d */
    C1120o f3299d;
    /* renamed from: g */
    private final ReentrantLock f3300g = new ReentrantLock();
    /* renamed from: h */
    private final C1124q f3301h;
    /* renamed from: i */
    private final boolean f3302i;
    /* renamed from: j */
    private final boolean f3303j;
    /* renamed from: k */
    private final Context f3304k;
    /* renamed from: l */
    private final String f3305l;
    /* renamed from: m */
    private final String f3306m;
    /* renamed from: n */
    private final Collection<C1237i> f3307n;

    /* renamed from: b.a.a.a.a.b.p$a */
    public enum C1121a {
        WIFI_MAC_ADDRESS(1),
        BLUETOOTH_MAC_ADDRESS(2),
        FONT_TOKEN(53),
        ANDROID_ID(100),
        ANDROID_DEVICE_ID(101),
        ANDROID_SERIAL(102),
        ANDROID_ADVERTISING_ID(103);
        
        /* renamed from: h */
        public final int f3293h;

        private C1121a(int i) {
            this.f3293h = i;
        }
    }

    public C1122p(Context context, String str, String str2, Collection<C1237i> collection) {
        if (context == null) {
            throw new IllegalArgumentException("appContext must not be null");
        } else if (str == null) {
            throw new IllegalArgumentException("appIdentifier must not be null");
        } else if (collection == null) {
            throw new IllegalArgumentException("kits must not be null");
        } else {
            this.f3304k = context;
            this.f3305l = str;
            this.f3306m = str2;
            this.f3307n = collection;
            this.f3301h = new C1124q();
            this.f3296a = new C1100c(context);
            this.f3299d = new C1120o();
            this.f3302i = C1110i.m6014a(context, "com.crashlytics.CollectDeviceIdentifiers", true);
            if (!this.f3302i) {
                C1230c.m6414h().mo1062a("Fabric", "Device ID collection disabled for " + context.getPackageName());
            }
            this.f3303j = C1110i.m6014a(context, "com.crashlytics.CollectUserIdentifiers", true);
            if (!this.f3303j) {
                C1230c.m6414h().mo1062a("Fabric", "User information collection disabled for " + context.getPackageName());
            }
        }
    }

    @SuppressLint({"CommitPrefEdits"})
    /* renamed from: a */
    private String m6052a(SharedPreferences sharedPreferences) {
        this.f3300g.lock();
        try {
            String string = sharedPreferences.getString("crashlytics.installation.id", null);
            if (string == null) {
                string = m6053a(UUID.randomUUID().toString());
                sharedPreferences.edit().putString("crashlytics.installation.id", string).commit();
            }
            this.f3300g.unlock();
            return string;
        } catch (Throwable th) {
            this.f3300g.unlock();
        }
    }

    /* renamed from: a */
    private String m6053a(String str) {
        return str == null ? null : f3294e.matcher(str).replaceAll(TtmlNode.ANONYMOUS_REGION_ID).toLowerCase(Locale.US);
    }

    @SuppressLint({"CommitPrefEdits"})
    /* renamed from: a */
    private void m6054a(SharedPreferences sharedPreferences, String str) {
        this.f3300g.lock();
        try {
            if (!TextUtils.isEmpty(str)) {
                Object string = sharedPreferences.getString("crashlytics.advertising.id", null);
                if (TextUtils.isEmpty(string)) {
                    sharedPreferences.edit().putString("crashlytics.advertising.id", str).commit();
                } else if (!string.equals(str)) {
                    sharedPreferences.edit().remove("crashlytics.installation.id").putString("crashlytics.advertising.id", str).commit();
                }
                this.f3300g.unlock();
            }
        } finally {
            this.f3300g.unlock();
        }
    }

    /* renamed from: a */
    private void m6055a(Map<C1121a, String> map, C1121a c1121a, String str) {
        if (str != null) {
            map.put(c1121a, str);
        }
    }

    /* renamed from: b */
    private String m6056b(String str) {
        return str.replaceAll(f3295f, TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: b */
    private void m6057b(SharedPreferences sharedPreferences) {
        C1097b n = m6072n();
        if (n != null) {
            m6054a(sharedPreferences, n.f3242a);
        }
    }

    /* renamed from: o */
    private Boolean m6058o() {
        C1097b n = m6072n();
        return n != null ? Boolean.valueOf(n.f3243b) : null;
    }

    /* renamed from: a */
    public boolean m6059a() {
        return this.f3303j;
    }

    /* renamed from: b */
    public String m6060b() {
        String str = this.f3306m;
        if (str != null) {
            return str;
        }
        SharedPreferences a = C1110i.m5998a(this.f3304k);
        m6057b(a);
        str = a.getString("crashlytics.installation.id", null);
        return str == null ? m6052a(a) : str;
    }

    /* renamed from: c */
    public String m6061c() {
        return this.f3305l;
    }

    /* renamed from: d */
    public String m6062d() {
        return m6063e() + "/" + m6064f();
    }

    /* renamed from: e */
    public String m6063e() {
        return m6056b(VERSION.RELEASE);
    }

    /* renamed from: f */
    public String m6064f() {
        return m6056b(VERSION.INCREMENTAL);
    }

    /* renamed from: g */
    public String m6065g() {
        return String.format(Locale.US, "%s/%s", new Object[]{m6056b(Build.MANUFACTURER), m6056b(Build.MODEL)});
    }

    /* renamed from: h */
    public Map<C1121a, String> m6066h() {
        Map hashMap = new HashMap();
        for (C1237i c1237i : this.f3307n) {
            if (c1237i instanceof C1115m) {
                for (Entry entry : ((C1115m) c1237i).mo1147f().entrySet()) {
                    m6055a(hashMap, (C1121a) entry.getKey(), (String) entry.getValue());
                }
            }
        }
        Object k = m6069k();
        if (TextUtils.isEmpty(k)) {
            m6055a(hashMap, C1121a.ANDROID_ID, m6070l());
        } else {
            m6055a(hashMap, C1121a.ANDROID_ADVERTISING_ID, k);
        }
        return Collections.unmodifiableMap(hashMap);
    }

    /* renamed from: i */
    public String m6067i() {
        return this.f3301h.m6075a(this.f3304k);
    }

    /* renamed from: j */
    public Boolean m6068j() {
        return m6071m() ? m6058o() : null;
    }

    /* renamed from: k */
    public String m6069k() {
        if (!m6071m()) {
            return null;
        }
        C1097b n = m6072n();
        return (n == null || n.f3243b) ? null : n.f3242a;
    }

    /* renamed from: l */
    public String m6070l() {
        boolean equals = Boolean.TRUE.equals(m6058o());
        if (!m6071m() || equals) {
            return null;
        }
        String string = Secure.getString(this.f3304k.getContentResolver(), "android_id");
        return !"9774d56d682e549c".equals(string) ? m6053a(string) : null;
    }

    /* renamed from: m */
    protected boolean m6071m() {
        return this.f3302i && !this.f3299d.m6051b(this.f3304k);
    }

    /* renamed from: n */
    synchronized C1097b m6072n() {
        if (!this.f3298c) {
            this.f3297b = this.f3296a.m5971a();
            this.f3298c = true;
        }
        return this.f3297b;
    }
}
