package com.crashlytics.android.p066c;

import android.app.Activity;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.crashlytics.android.c.h.AnonymousClass11;
import com.crashlytics.android.c.h.AnonymousClass13;
import com.crashlytics.android.c.h.AnonymousClass15;
import com.crashlytics.android.p064a.C1333b;
import com.crashlytics.android.p064a.C1347q;
import com.crashlytics.android.p064a.C1348k;
import com.crashlytics.android.p066c.C1400e;
import com.crashlytics.android.p066c.C1406f.C1404a;
import com.crashlytics.android.p066c.C1438h;
import com.crashlytics.android.p066c.C1438h.11.C14101;
import com.crashlytics.android.p066c.C1438h.13.C14131;
import com.crashlytics.android.p066c.C1438h.15.C14141;
import com.crashlytics.android.p066c.C1449m.C1417a;
import com.crashlytics.android.p066c.C1460u.C1429a;
import com.crashlytics.android.p066c.af.C1389d;
import com.crashlytics.android.p066c.af.C1390a;
import com.crashlytics.android.p066c.af.C1391b;
import com.crashlytics.android.p066c.af.C1392c;
import com.crashlytics.android.p066c.ag;
import com.crashlytics.android.p066c.p067a.p068a.C1381d;
import com.google.android.gms.dynamite.ProviderConstants;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.AppMeasurement.Event;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.Flushable;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1111j.C1112a;
import p033b.p034a.p035a.p036a.p037a.p039b.C1114l;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;
import p033b.p034a.p035a.p036a.p037a.p044f.C1192a;
import p033b.p034a.p035a.p036a.p037a.p045g.C1213o;
import p033b.p034a.p035a.p036a.p037a.p045g.C1214p;
import p033b.p034a.p035a.p036a.p037a.p045g.C1217q;
import p033b.p034a.p035a.p036a.p037a.p045g.C1219t;

/* renamed from: com.crashlytics.android.c.h */
class C1438h {
    /* renamed from: a */
    static final FilenameFilter f4334a = new C1415c("BeginSession") {
        public boolean accept(File file, String str) {
            return super.accept(file, str) && str.endsWith(".cls");
        }
    };
    /* renamed from: b */
    static final FilenameFilter f4335b = new FilenameFilter() {
        public boolean accept(File file, String str) {
            return str.length() == ".cls".length() + 35 && str.endsWith(".cls");
        }
    };
    /* renamed from: c */
    static final FileFilter f4336c = new FileFilter() {
        public boolean accept(File file) {
            return file.isDirectory() && file.getName().length() == 35;
        }
    };
    /* renamed from: d */
    static final Comparator<File> f4337d = new Comparator<File>() {
        /* renamed from: a */
        public int m7118a(File file, File file2) {
            return file2.getName().compareTo(file.getName());
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m7118a((File) obj, (File) obj2);
        }
    };
    /* renamed from: e */
    static final Comparator<File> f4338e = new Comparator<File>() {
        /* renamed from: a */
        public int m7119a(File file, File file2) {
            return file.getName().compareTo(file2.getName());
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m7119a((File) obj, (File) obj2);
        }
    };
    /* renamed from: f */
    private static final Pattern f4339f = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
    /* renamed from: g */
    private static final Map<String, String> f4340g = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", "1");
    /* renamed from: h */
    private static final String[] f4341h = new String[]{"SessionUser", "SessionApp", "SessionOS", "SessionDevice"};
    /* renamed from: i */
    private final AtomicInteger f4342i = new AtomicInteger(0);
    /* renamed from: j */
    private final C1446i f4343j;
    /* renamed from: k */
    private final C1409g f4344k;
    /* renamed from: l */
    private final C1177e f4345l;
    /* renamed from: m */
    private final C1122p f4346m;
    /* renamed from: n */
    private final ab f4347n;
    /* renamed from: o */
    private final C1192a f4348o;
    /* renamed from: p */
    private final C1386a f4349p;
    /* renamed from: q */
    private final C1430f f4350q;
    /* renamed from: r */
    private final C1460u f4351r;
    /* renamed from: s */
    private final C1392c f4352s;
    /* renamed from: t */
    private final C1391b f4353t;
    /* renamed from: u */
    private final C1455q f4354u;
    /* renamed from: v */
    private final ai f4355v;
    /* renamed from: w */
    private final String f4356w;
    /* renamed from: x */
    private final C1347q f4357x;
    /* renamed from: y */
    private final boolean f4358y;
    /* renamed from: z */
    private C1449m f4359z;

    /* renamed from: com.crashlytics.android.c.h$d */
    private interface C1411d {
        /* renamed from: a */
        void mo1160a(FileOutputStream fileOutputStream);
    }

    /* renamed from: com.crashlytics.android.c.h$b */
    private interface C1412b {
        /* renamed from: a */
        void mo1161a(C1400e c1400e);
    }

    /* renamed from: com.crashlytics.android.c.h$c */
    static class C1415c implements FilenameFilter {
        /* renamed from: a */
        private final String f4292a;

        public C1415c(String str) {
            this.f4292a = str;
        }

        public boolean accept(File file, String str) {
            return str.contains(this.f4292a) && !str.endsWith(".cls_temp");
        }
    }

    /* renamed from: com.crashlytics.android.c.h$2 */
    class C14182 implements Callable<Void> {
        /* renamed from: a */
        final /* synthetic */ C1438h f4298a;

        C14182(C1438h c1438h) {
            this.f4298a = c1438h;
        }

        /* renamed from: a */
        public Void m7123a() {
            this.f4298a.m7186m();
            return null;
        }

        public /* synthetic */ Object call() {
            return m7123a();
        }
    }

    /* renamed from: com.crashlytics.android.c.h$4 */
    class C14204 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C1438h f4301a;

        C14204(C1438h c1438h) {
            this.f4301a = c1438h;
        }

        public void run() {
            this.f4301a.m7196a(this.f4301a.m7165a(new C1428e()));
        }
    }

    /* renamed from: com.crashlytics.android.c.h$a */
    private static class C1427a implements FilenameFilter {
        private C1427a() {
        }

        public boolean accept(File file, String str) {
            return !C1438h.f4335b.accept(file, str) && C1438h.f4339f.matcher(str).matches();
        }
    }

    /* renamed from: com.crashlytics.android.c.h$e */
    static class C1428e implements FilenameFilter {
        C1428e() {
        }

        public boolean accept(File file, String str) {
            return C1398d.f4239a.accept(file, str) || str.contains("SessionMissingBinaryImages");
        }
    }

    /* renamed from: com.crashlytics.android.c.h$f */
    private static final class C1430f implements C1429a {
        /* renamed from: a */
        private final C1192a f4321a;

        public C1430f(C1192a c1192a) {
            this.f4321a = c1192a;
        }

        /* renamed from: a */
        public File mo1164a() {
            File file = new File(this.f4321a.mo1050a(), "log-files");
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
    }

    /* renamed from: com.crashlytics.android.c.h$g */
    private static final class C1433g implements C1389d {
        /* renamed from: a */
        private final C1237i f4325a;
        /* renamed from: b */
        private final ab f4326b;
        /* renamed from: c */
        private final C1213o f4327c;

        /* renamed from: com.crashlytics.android.c.h$g$1 */
        class C14311 implements C1404a {
            /* renamed from: a */
            final /* synthetic */ C1433g f4322a;

            C14311(C1433g c1433g) {
                this.f4322a = c1433g;
            }

            /* renamed from: a */
            public void mo1165a(boolean z) {
                this.f4322a.f4326b.m6965a(z);
            }
        }

        public C1433g(C1237i c1237i, ab abVar, C1213o c1213o) {
            this.f4325a = c1237i;
            this.f4326b = abVar;
            this.f4327c = c1213o;
        }

        /* renamed from: a */
        public boolean mo1153a() {
            Activity b = this.f4325a.m6453r().m6421b();
            if (b == null || b.isFinishing()) {
                return true;
            }
            final C1406f a = C1406f.m7103a(b, this.f4327c, new C14311(this));
            b.runOnUiThread(new Runnable(this) {
                /* renamed from: b */
                final /* synthetic */ C1433g f4324b;

                public void run() {
                    a.m7104a();
                }
            });
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Waiting for user opt-in.");
            a.m7105b();
            return a.m7106c();
        }
    }

    /* renamed from: com.crashlytics.android.c.h$h */
    private final class C1434h implements C1392c {
        /* renamed from: a */
        final /* synthetic */ C1438h f4328a;

        private C1434h(C1438h c1438h) {
            this.f4328a = c1438h;
        }

        /* renamed from: a */
        public File[] mo1166a() {
            return this.f4328a.m7199b();
        }

        /* renamed from: b */
        public File[] mo1167b() {
            return this.f4328a.m7206i().listFiles();
        }
    }

    /* renamed from: com.crashlytics.android.c.h$i */
    private final class C1435i implements C1391b {
        /* renamed from: a */
        final /* synthetic */ C1438h f4329a;

        private C1435i(C1438h c1438h) {
            this.f4329a = c1438h;
        }

        /* renamed from: a */
        public boolean mo1168a() {
            return this.f4329a.m7202e();
        }
    }

    /* renamed from: com.crashlytics.android.c.h$j */
    private static final class C1436j implements Runnable {
        /* renamed from: a */
        private final Context f4330a;
        /* renamed from: b */
        private final ae f4331b;
        /* renamed from: c */
        private final af f4332c;

        public C1436j(Context context, ae aeVar, af afVar) {
            this.f4330a = context;
            this.f4331b = aeVar;
            this.f4332c = afVar;
        }

        public void run() {
            if (C1110i.m6035n(this.f4330a)) {
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Attempting to send crash report at time of crash...");
                this.f4332c.m6997a(this.f4331b);
            }
        }
    }

    /* renamed from: com.crashlytics.android.c.h$k */
    static class C1437k implements FilenameFilter {
        /* renamed from: a */
        private final String f4333a;

        public C1437k(String str) {
            this.f4333a = str;
        }

        public boolean accept(File file, String str) {
            return (str.equals(new StringBuilder().append(this.f4333a).append(".cls").toString()) || !str.contains(this.f4333a) || str.endsWith(".cls_temp")) ? false : true;
        }
    }

    C1438h(C1446i c1446i, C1409g c1409g, C1177e c1177e, C1122p c1122p, ab abVar, C1192a c1192a, C1386a c1386a, ak akVar, boolean z) {
        this.f4343j = c1446i;
        this.f4344k = c1409g;
        this.f4345l = c1177e;
        this.f4346m = c1122p;
        this.f4347n = abVar;
        this.f4348o = c1192a;
        this.f4349p = c1386a;
        this.f4356w = akVar.mo1175a();
        this.f4358y = z;
        Context q = c1446i.m6452q();
        this.f4350q = new C1430f(c1192a);
        this.f4351r = new C1460u(q, this.f4350q);
        this.f4352s = new C1434h();
        this.f4353t = new C1435i();
        this.f4354u = new C1455q(q);
        this.f4355v = new C1463x(1024, new ad(10));
        this.f4357x = C1348k.m6865a(q);
    }

    /* renamed from: a */
    static String m7138a(File file) {
        return file.getName().substring(0, 35);
    }

    /* renamed from: a */
    private void m7139a(long j) {
        if (m7189p()) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Skipping logging Crashlytics event to Firebase, FirebaseCrash exists");
        } else if (!this.f4358y) {
        } else {
            if (this.f4357x != null) {
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Logging Crashlytics event to Firebase");
                Bundle bundle = new Bundle();
                bundle.putInt("_r", 1);
                bundle.putInt(Param.FATAL, 1);
                bundle.putLong(Param.TIMESTAMP, j);
                this.f4357x.mo1134a("clx", Event.APP_EXCEPTION, bundle);
                return;
            }
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Skipping logging Crashlytics event to Firebase, no Firebase Analytics");
        }
    }

    /* renamed from: a */
    private void m7140a(C1214p c1214p, boolean z) {
        int i = z ? 1 : 0;
        m7167b(i + 8);
        File[] n = m7187n();
        if (n.length <= i) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "No open sessions to be closed.");
            return;
        }
        m7180f(C1438h.m7138a(n[i]));
        if (c1214p == null) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Unable to close session. Settings are not loaded.");
        } else {
            m7159a(n, i, c1214p.f3506c);
        }
    }

    /* renamed from: a */
    private void m7141a(C1398d c1398d) {
        if (c1398d != null) {
            try {
                c1398d.m7049a();
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("CrashlyticsCore", "Error closing session file stream in the presence of an exception", e);
            }
        }
    }

    /* renamed from: a */
    private static void m7142a(C1400e c1400e, File file) {
        Closeable fileInputStream;
        Throwable th;
        if (file.exists()) {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    C1438h.m7151a((InputStream) fileInputStream, c1400e, (int) file.length());
                    C1110i.m6011a(fileInputStream, "Failed to close file input stream.");
                    return;
                } catch (Throwable th2) {
                    th = th2;
                    C1110i.m6011a(fileInputStream, "Failed to close file input stream.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = null;
                C1110i.m6011a(fileInputStream, "Failed to close file input stream.");
                throw th;
            }
        }
        C1230c.m6414h().mo1070e("CrashlyticsCore", "Tried to include a file that doesn't exist: " + file.getName(), null);
    }

    /* renamed from: a */
    private void m7143a(C1400e c1400e, String str) {
        for (String str2 : f4341h) {
            File[] a = m7165a(new C1415c(str + str2 + ".cls"));
            if (a.length == 0) {
                C1230c.m6414h().mo1070e("CrashlyticsCore", "Can't find " + str2 + " data for session ID " + str, null);
            } else {
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Collecting " + str2 + " data for session ID " + str);
                C1438h.m7142a(c1400e, a[0]);
            }
        }
    }

    /* renamed from: a */
    private void m7144a(C1400e c1400e, Date date, Thread thread, Throwable th, String str, boolean z) {
        Thread[] threadArr;
        Map treeMap;
        aj ajVar = new aj(th, this.f4355v);
        Context q = this.f4343j.m6452q();
        long time = date.getTime() / 1000;
        Float c = C1110i.m6021c(q);
        int a = C1110i.m5995a(q, this.f4354u.m7247a());
        boolean d = C1110i.m6024d(q);
        int i = q.getResources().getConfiguration().orientation;
        long b = C1110i.m6015b() - C1110i.m6016b(q);
        long c2 = C1110i.m6020c(Environment.getDataDirectory().getPath());
        RunningAppProcessInfo a2 = C1110i.m5997a(q.getPackageName(), q);
        List linkedList = new LinkedList();
        StackTraceElement[] stackTraceElementArr = ajVar.f4223c;
        String str2 = this.f4349p.f4192b;
        String c3 = this.f4346m.m6061c();
        if (z) {
            Map allStackTraces = Thread.getAllStackTraces();
            threadArr = new Thread[allStackTraces.size()];
            int i2 = 0;
            for (Entry entry : allStackTraces.entrySet()) {
                threadArr[i2] = (Thread) entry.getKey();
                linkedList.add(this.f4355v.mo1152a((StackTraceElement[]) entry.getValue()));
                i2++;
            }
        } else {
            threadArr = new Thread[0];
        }
        if (C1110i.m6014a(q, "com.crashlytics.CollectCustomKeys", true)) {
            Map f = this.f4343j.m7224f();
            treeMap = (f == null || f.size() <= 1) ? f : new TreeMap(f);
        } else {
            treeMap = new TreeMap();
        }
        ag.m7016a(c1400e, time, str, ajVar, thread, stackTraceElementArr, threadArr, linkedList, treeMap, this.f4351r, a2, i, c3, str2, c, a, d, b, c2);
    }

    /* renamed from: a */
    private static void m7145a(C1400e c1400e, File[] fileArr, String str) {
        Arrays.sort(fileArr, C1110i.f3265a);
        for (File name : fileArr) {
            try {
                C1230c.m6414h().mo1062a("CrashlyticsCore", String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", new Object[]{str, name.getName()}));
                C1438h.m7142a(c1400e, name);
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("CrashlyticsCore", "Error writting non-fatal to session.", e);
            }
        }
    }

    /* renamed from: a */
    private void m7149a(File file, String str, int i) {
        C1230c.m6414h().mo1062a("CrashlyticsCore", "Collecting session parts for ID " + str);
        File[] a = m7165a(new C1415c(str + "SessionCrash"));
        boolean z = a != null && a.length > 0;
        C1230c.m6414h().mo1062a("CrashlyticsCore", String.format(Locale.US, "Session %s has fatal exception: %s", new Object[]{str, Boolean.valueOf(z)}));
        File[] a2 = m7165a(new C1415c(str + "SessionEvent"));
        boolean z2 = a2 != null && a2.length > 0;
        C1230c.m6414h().mo1062a("CrashlyticsCore", String.format(Locale.US, "Session %s has non-fatal exceptions: %s", new Object[]{str, Boolean.valueOf(z2)}));
        if (z || z2) {
            m7150a(file, str, m7166a(str, a2, i), z ? a[0] : null);
        } else {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "No events present for session ID " + str);
        }
        C1230c.m6414h().mo1062a("CrashlyticsCore", "Removing session part files for ID " + str);
        m7152a(str);
    }

    /* renamed from: a */
    private void m7150a(File file, String str, File[] fileArr, File file2) {
        Closeable c1398d;
        Throwable e;
        boolean z = file2 != null;
        File g = z ? m7204g() : m7205h();
        if (!g.exists()) {
            g.mkdirs();
        }
        try {
            c1398d = new C1398d(g, str);
            try {
                Flushable a = C1400e.m7050a((OutputStream) c1398d);
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Collecting SessionStart data for session ID " + str);
                C1438h.m7142a((C1400e) a, file);
                a.m7077a(4, new Date().getTime() / 1000);
                a.m7079a(5, z);
                a.m7076a(11, 1);
                a.m7087b(12, 3);
                m7143a((C1400e) a, str);
                C1438h.m7145a((C1400e) a, fileArr, str);
                if (z) {
                    C1438h.m7142a((C1400e) a, file2);
                }
                C1110i.m6012a(a, "Error flushing session file stream");
                C1110i.m6011a(c1398d, "Failed to close CLS file");
            } catch (Exception e2) {
                e = e2;
                try {
                    C1230c.m6414h().mo1070e("CrashlyticsCore", "Failed to write session file for session ID: " + str, e);
                    C1110i.m6012a(null, "Error flushing session file stream");
                    m7141a((C1398d) c1398d);
                } catch (Throwable th) {
                    e = th;
                    C1110i.m6012a(null, "Error flushing session file stream");
                    C1110i.m6011a(c1398d, "Failed to close CLS file");
                    throw e;
                }
            }
        } catch (Exception e3) {
            e = e3;
            c1398d = null;
            C1230c.m6414h().mo1070e("CrashlyticsCore", "Failed to write session file for session ID: " + str, e);
            C1110i.m6012a(null, "Error flushing session file stream");
            m7141a((C1398d) c1398d);
        } catch (Throwable th2) {
            e = th2;
            c1398d = null;
            C1110i.m6012a(null, "Error flushing session file stream");
            C1110i.m6011a(c1398d, "Failed to close CLS file");
            throw e;
        }
    }

    /* renamed from: a */
    private static void m7151a(InputStream inputStream, C1400e c1400e, int i) {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < bArr.length) {
            int read = inputStream.read(bArr, i2, bArr.length - i2);
            if (read < 0) {
                break;
            }
            i2 += read;
        }
        c1400e.m7084a(bArr);
    }

    /* renamed from: a */
    private void m7152a(String str) {
        for (File delete : m7173b(str)) {
            delete.delete();
        }
    }

    /* renamed from: a */
    private void m7153a(String str, int i) {
        am.m7037a(m7203f(), new C1415c(str + "SessionEvent"), i, f4338e);
    }

    /* renamed from: a */
    private static void m7154a(String str, String str2) {
        C1333b c1333b = (C1333b) C1230c.m6405a(C1333b.class);
        if (c1333b == null) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Answers is not available");
        } else {
            c1333b.m6820a(new C1112a(str, str2));
        }
    }

    /* renamed from: a */
    private void m7155a(String str, String str2, C1412b c1412b) {
        Throwable th;
        Flushable flushable = null;
        Closeable c1398d;
        try {
            c1398d = new C1398d(m7203f(), str + str2);
            try {
                flushable = C1400e.m7050a((OutputStream) c1398d);
                c1412b.mo1161a(flushable);
                C1110i.m6012a(flushable, "Failed to flush to session " + str2 + " file.");
                C1110i.m6011a(c1398d, "Failed to close session " + str2 + " file.");
            } catch (Throwable th2) {
                th = th2;
                C1110i.m6012a(flushable, "Failed to flush to session " + str2 + " file.");
                C1110i.m6011a(c1398d, "Failed to close session " + str2 + " file.");
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            c1398d = null;
            C1110i.m6012a(flushable, "Failed to flush to session " + str2 + " file.");
            C1110i.m6011a(c1398d, "Failed to close session " + str2 + " file.");
            throw th;
        }
    }

    /* renamed from: a */
    private void m7156a(String str, String str2, C1411d c1411d) {
        Throwable th;
        Closeable fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(new File(m7203f(), str + str2));
            try {
                c1411d.mo1160a(fileOutputStream);
                C1110i.m6011a(fileOutputStream, "Failed to close " + str2 + " file.");
            } catch (Throwable th2) {
                th = th2;
                C1110i.m6011a(fileOutputStream, "Failed to close " + str2 + " file.");
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            C1110i.m6011a(fileOutputStream, "Failed to close " + str2 + " file.");
            throw th;
        }
    }

    /* renamed from: a */
    private void m7157a(String str, Date date) {
        final String format = String.format(Locale.US, "Crashlytics Android SDK/%s", new Object[]{this.f4343j.mo1080a()});
        final long time = date.getTime() / 1000;
        final String str2 = str;
        m7155a(str, "BeginSession", new C1412b(this) {
            /* renamed from: d */
            final /* synthetic */ C1438h f4309d;

            /* renamed from: a */
            public void mo1161a(C1400e c1400e) {
                ag.m7022a(c1400e, str2, format, time);
            }
        });
        str2 = str;
        m7156a(str, "BeginSession.json", new C1411d(this) {
            /* renamed from: d */
            final /* synthetic */ C1438h f4314d;

            /* renamed from: com.crashlytics.android.c.h$8$1 */
            class C14241 extends HashMap<String, Object> {
                /* renamed from: a */
                final /* synthetic */ C14258 f4310a;

                C14241(C14258 c14258) {
                    this.f4310a = c14258;
                    put("session_id", str2);
                    put("generator", format);
                    put("started_at_seconds", Long.valueOf(time));
                }
            }

            /* renamed from: a */
            public void mo1160a(FileOutputStream fileOutputStream) {
                fileOutputStream.write(new JSONObject(new C14241(this)).toString().getBytes());
            }
        });
    }

    /* renamed from: a */
    private void m7158a(Date date, Thread thread, Throwable th) {
        Throwable e;
        Closeable closeable;
        Flushable flushable = null;
        try {
            String k = m7184k();
            if (k == null) {
                C1230c.m6414h().mo1070e("CrashlyticsCore", "Tried to write a fatal exception while no session was open.", null);
                C1110i.m6012a(null, "Failed to flush to session begin file.");
                C1110i.m6011a(null, "Failed to close fatal exception file output stream.");
                return;
            }
            C1438h.m7154a(k, th.getClass().getName());
            m7139a(date.getTime());
            Closeable c1398d = new C1398d(m7203f(), k + "SessionCrash");
            try {
                flushable = C1400e.m7050a((OutputStream) c1398d);
                m7144a(flushable, date, thread, th, AppMeasurement.CRASH_ORIGIN, true);
                C1110i.m6012a(flushable, "Failed to flush to session begin file.");
                C1110i.m6011a(c1398d, "Failed to close fatal exception file output stream.");
            } catch (Exception e2) {
                e = e2;
                closeable = c1398d;
                try {
                    C1230c.m6414h().mo1070e("CrashlyticsCore", "An error occurred in the fatal exception logger", e);
                    C1110i.m6012a(flushable, "Failed to flush to session begin file.");
                    C1110i.m6011a(closeable, "Failed to close fatal exception file output stream.");
                } catch (Throwable th2) {
                    e = th2;
                    C1110i.m6012a(flushable, "Failed to flush to session begin file.");
                    C1110i.m6011a(closeable, "Failed to close fatal exception file output stream.");
                    throw e;
                }
            } catch (Throwable th3) {
                e = th3;
                closeable = c1398d;
                C1110i.m6012a(flushable, "Failed to flush to session begin file.");
                C1110i.m6011a(closeable, "Failed to close fatal exception file output stream.");
                throw e;
            }
        } catch (Exception e3) {
            e = e3;
            closeable = null;
            C1230c.m6414h().mo1070e("CrashlyticsCore", "An error occurred in the fatal exception logger", e);
            C1110i.m6012a(flushable, "Failed to flush to session begin file.");
            C1110i.m6011a(closeable, "Failed to close fatal exception file output stream.");
        } catch (Throwable th4) {
            e = th4;
            closeable = null;
            C1110i.m6012a(flushable, "Failed to flush to session begin file.");
            C1110i.m6011a(closeable, "Failed to close fatal exception file output stream.");
            throw e;
        }
    }

    /* renamed from: a */
    private void m7159a(File[] fileArr, int i, int i2) {
        C1230c.m6414h().mo1062a("CrashlyticsCore", "Closing open sessions.");
        while (i < fileArr.length) {
            File file = fileArr[i];
            String a = C1438h.m7138a(file);
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Closing session: " + a);
            m7149a(file, a, i2);
            i++;
        }
    }

    /* renamed from: a */
    private void m7160a(File[] fileArr, Set<String> set) {
        for (File file : fileArr) {
            String name = file.getName();
            Matcher matcher = f4339f.matcher(name);
            if (!matcher.matches()) {
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Deleting unknown file: " + name);
                file.delete();
            } else if (!set.contains(matcher.group(1))) {
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Trimming session file: " + name);
                file.delete();
            }
        }
    }

    /* renamed from: a */
    private boolean m7161a(C1219t c1219t) {
        return (c1219t == null || !c1219t.f3523d.f3489a || this.f4347n.m6966a()) ? false : true;
    }

    /* renamed from: a */
    private File[] m7164a(File file, FilenameFilter filenameFilter) {
        return m7174b(file.listFiles(filenameFilter));
    }

    /* renamed from: a */
    private File[] m7165a(FilenameFilter filenameFilter) {
        return m7164a(m7203f(), filenameFilter);
    }

    /* renamed from: a */
    private File[] m7166a(String str, File[] fileArr, int i) {
        if (fileArr.length <= i) {
            return fileArr;
        }
        C1230c.m6414h().mo1062a("CrashlyticsCore", String.format(Locale.US, "Trimming down to %d logged exceptions.", new Object[]{Integer.valueOf(i)}));
        m7153a(str, i);
        return m7165a(new C1415c(str + "SessionEvent"));
    }

    /* renamed from: b */
    private void m7167b(int i) {
        Set hashSet = new HashSet();
        File[] n = m7187n();
        int min = Math.min(i, n.length);
        for (int i2 = 0; i2 < min; i2++) {
            hashSet.add(C1438h.m7138a(n[i2]));
        }
        this.f4351r.m7271a(hashSet);
        m7160a(m7165a(new C1427a()), hashSet);
    }

    /* renamed from: b */
    private void m7168b(C1219t c1219t) {
        if (c1219t == null) {
            C1230c.m6414h().mo1067d("CrashlyticsCore", "Cannot send reports. Settings are unavailable.");
            return;
        }
        Context q = this.f4343j.m6452q();
        af afVar = new af(this.f4349p.f4191a, m7182h(c1219t.f3520a.f3475d), this.f4352s, this.f4353t);
        for (File ahVar : m7199b()) {
            this.f4344k.m7108a(new C1436j(q, new ah(ahVar, f4340g), afVar));
        }
    }

    /* renamed from: b */
    private void m7169b(C1381d c1381d) {
        Closeable c1398d;
        Throwable e;
        Object obj = 1;
        Flushable flushable = null;
        try {
            String l = m7185l();
            if (l == null) {
                C1230c.m6414h().mo1070e("CrashlyticsCore", "Tried to write a native crash while no session was open.", null);
                C1110i.m6012a(null, "Failed to flush to session begin file.");
                C1110i.m6011a(null, "Failed to close fatal exception file output stream.");
                return;
            }
            C1438h.m7154a(l, String.format(Locale.US, "<native-crash [%s (%s)]>", new Object[]{c1381d.f4175b.f4181b, c1381d.f4175b.f4180a}));
            if (c1381d.f4177d == null || c1381d.f4177d.length <= 0) {
                obj = null;
            }
            c1398d = new C1398d(m7203f(), l + (obj != null ? "SessionCrash" : "SessionMissingBinaryImages"));
            try {
                flushable = C1400e.m7050a((OutputStream) c1398d);
                C1478z.m7315a(c1381d, new C1460u(this.f4343j.m6452q(), this.f4350q, l), new C1462w(m7203f()).m7280b(l), flushable);
                C1110i.m6012a(flushable, "Failed to flush to session begin file.");
                C1110i.m6011a(c1398d, "Failed to close fatal exception file output stream.");
            } catch (Exception e2) {
                e = e2;
                try {
                    C1230c.m6414h().mo1070e("CrashlyticsCore", "An error occurred in the native crash logger", e);
                    C1110i.m6012a(flushable, "Failed to flush to session begin file.");
                    C1110i.m6011a(c1398d, "Failed to close fatal exception file output stream.");
                } catch (Throwable th) {
                    e = th;
                    C1110i.m6012a(flushable, "Failed to flush to session begin file.");
                    C1110i.m6011a(c1398d, "Failed to close fatal exception file output stream.");
                    throw e;
                }
            }
        } catch (Exception e3) {
            e = e3;
            c1398d = null;
            C1230c.m6414h().mo1070e("CrashlyticsCore", "An error occurred in the native crash logger", e);
            C1110i.m6012a(flushable, "Failed to flush to session begin file.");
            C1110i.m6011a(c1398d, "Failed to close fatal exception file output stream.");
        } catch (Throwable th2) {
            e = th2;
            c1398d = null;
            C1110i.m6012a(flushable, "Failed to flush to session begin file.");
            C1110i.m6011a(c1398d, "Failed to close fatal exception file output stream.");
            throw e;
        }
    }

    /* renamed from: b */
    private File[] m7172b(File file) {
        return m7174b(file.listFiles());
    }

    /* renamed from: b */
    private File[] m7173b(String str) {
        return m7165a(new C1437k(str));
    }

    /* renamed from: b */
    private File[] m7174b(File[] fileArr) {
        return fileArr == null ? new File[0] : fileArr;
    }

    /* renamed from: c */
    private void m7176c(String str) {
        final String c = this.f4346m.m6061c();
        final String str2 = this.f4349p.f4195e;
        final String str3 = this.f4349p.f4196f;
        final String b = this.f4346m.m6060b();
        final int a = C1114l.m6039a(this.f4349p.f4193c).m6040a();
        m7155a(str, "SessionApp", new C1412b(this) {
            /* renamed from: f */
            final /* synthetic */ C1438h f4320f;

            /* renamed from: a */
            public void mo1161a(C1400e c1400e) {
                ag.m7024a(c1400e, c, this.f4320f.f4349p.f4191a, str2, str3, b, a, this.f4320f.f4356w);
            }
        });
        m7156a(str, "SessionApp.json", new C1411d(this) {
            /* renamed from: f */
            final /* synthetic */ C1438h f4267f;

            /* renamed from: com.crashlytics.android.c.h$11$1 */
            class C14101 extends HashMap<String, Object> {
                /* renamed from: a */
                final /* synthetic */ AnonymousClass11 f4261a;

                C14101(AnonymousClass11 anonymousClass11) {
                    this.f4261a = anonymousClass11;
                    put("app_identifier", c);
                    put("api_key", this.f4261a.f4267f.f4349p.f4191a);
                    put("version_code", str2);
                    put("version_name", str3);
                    put("install_uuid", b);
                    put("delivery_mechanism", Integer.valueOf(a));
                    put("unity_version", TextUtils.isEmpty(this.f4261a.f4267f.f4356w) ? TtmlNode.ANONYMOUS_REGION_ID : this.f4261a.f4267f.f4356w);
                }
            }

            /* renamed from: a */
            public void mo1160a(FileOutputStream fileOutputStream) {
                fileOutputStream.write(new JSONObject(new C14101(this)).toString().getBytes());
            }
        });
    }

    /* renamed from: d */
    private void m7178d(String str) {
        final boolean g = C1110i.m6028g(this.f4343j.m6452q());
        m7155a(str, "SessionOS", new C1412b(this) {
            /* renamed from: b */
            final /* synthetic */ C1438h f4269b;

            /* renamed from: a */
            public void mo1161a(C1400e c1400e) {
                ag.m7025a(c1400e, VERSION.RELEASE, VERSION.CODENAME, g);
            }
        });
        m7156a(str, "SessionOS.json", new C1411d(this) {
            /* renamed from: b */
            final /* synthetic */ C1438h f4272b;

            /* renamed from: com.crashlytics.android.c.h$13$1 */
            class C14131 extends HashMap<String, Object> {
                /* renamed from: a */
                final /* synthetic */ AnonymousClass13 f4270a;

                C14131(AnonymousClass13 anonymousClass13) {
                    this.f4270a = anonymousClass13;
                    put(ProviderConstants.API_COLNAME_FEATURE_VERSION, VERSION.RELEASE);
                    put("build_version", VERSION.CODENAME);
                    put("is_rooted", Boolean.valueOf(g));
                }
            }

            /* renamed from: a */
            public void mo1160a(FileOutputStream fileOutputStream) {
                fileOutputStream.write(new JSONObject(new C14131(this)).toString().getBytes());
            }
        });
    }

    /* renamed from: e */
    private void m7179e(String str) {
        Context q = this.f4343j.m6452q();
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        final int a = C1110i.m5993a();
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        final long b = C1110i.m6015b();
        final long blockCount = ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        final boolean f = C1110i.m6027f(q);
        final Map h = this.f4346m.m6066h();
        final int h2 = C1110i.m6029h(q);
        m7155a(str, "SessionDevice", new C1412b(this) {
            /* renamed from: h */
            final /* synthetic */ C1438h f4280h;

            /* renamed from: a */
            public void mo1161a(C1400e c1400e) {
                ag.m7015a(c1400e, a, Build.MODEL, availableProcessors, b, blockCount, f, h, h2, Build.MANUFACTURER, Build.PRODUCT);
            }
        });
        m7156a(str, "SessionDevice.json", new C1411d(this) {
            /* renamed from: h */
            final /* synthetic */ C1438h f4289h;

            /* renamed from: com.crashlytics.android.c.h$15$1 */
            class C14141 extends HashMap<String, Object> {
                /* renamed from: a */
                final /* synthetic */ AnonymousClass15 f4281a;

                C14141(AnonymousClass15 anonymousClass15) {
                    this.f4281a = anonymousClass15;
                    put("arch", Integer.valueOf(a));
                    put("build_model", Build.MODEL);
                    put("available_processors", Integer.valueOf(availableProcessors));
                    put("total_ram", Long.valueOf(b));
                    put("disk_space", Long.valueOf(blockCount));
                    put("is_emulator", Boolean.valueOf(f));
                    put("ids", h);
                    put("state", Integer.valueOf(h2));
                    put("build_manufacturer", Build.MANUFACTURER);
                    put("build_product", Build.PRODUCT);
                }
            }

            /* renamed from: a */
            public void mo1160a(FileOutputStream fileOutputStream) {
                fileOutputStream.write(new JSONObject(new C14141(this)).toString().getBytes());
            }
        });
    }

    /* renamed from: f */
    private void m7180f(String str) {
        final al g = m7181g(str);
        m7155a(str, "SessionUser", new C1412b(this) {
            /* renamed from: b */
            final /* synthetic */ C1438h f4291b;

            /* renamed from: a */
            public void mo1161a(C1400e c1400e) {
                ag.m7023a(c1400e, g.f4226b, g.f4227c, g.f4228d);
            }
        });
    }

    /* renamed from: g */
    private al m7181g(String str) {
        return m7202e() ? new al(this.f4343j.m7225g(), this.f4343j.m7227i(), this.f4343j.m7226h()) : new C1462w(m7203f()).m7279a(str);
    }

    /* renamed from: h */
    private C1451o m7182h(String str) {
        return new C1452p(this.f4343j, C1110i.m6017b(this.f4343j.m6452q(), "com.crashlytics.ApiEndpoint"), str, this.f4345l);
    }

    /* renamed from: k */
    private String m7184k() {
        File[] n = m7187n();
        return n.length > 0 ? C1438h.m7138a(n[0]) : null;
    }

    /* renamed from: l */
    private String m7185l() {
        File[] n = m7187n();
        return n.length > 1 ? C1438h.m7138a(n[1]) : null;
    }

    /* renamed from: m */
    private void m7186m() {
        Date date = new Date();
        String c1396c = new C1396c(this.f4346m).toString();
        C1230c.m6414h().mo1062a("CrashlyticsCore", "Opening a new session with ID " + c1396c);
        m7157a(c1396c, date);
        m7176c(c1396c);
        m7178d(c1396c);
        m7179e(c1396c);
        this.f4351r.m7270a(c1396c);
    }

    /* renamed from: n */
    private File[] m7187n() {
        File[] c = m7200c();
        Arrays.sort(c, f4337d);
        return c;
    }

    /* renamed from: o */
    private void m7188o() {
        File i = m7206i();
        if (i.exists()) {
            File[] a = m7164a(i, new C1428e());
            Arrays.sort(a, Collections.reverseOrder());
            Set hashSet = new HashSet();
            for (int i2 = 0; i2 < a.length && hashSet.size() < 4; i2++) {
                hashSet.add(C1438h.m7138a(a[i2]));
            }
            m7160a(m7172b(i), hashSet);
        }
    }

    /* renamed from: p */
    private boolean m7189p() {
        try {
            Class.forName("com.google.firebase.crash.FirebaseCrash");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /* renamed from: a */
    void m7190a() {
        this.f4344k.m7109b(new C14182(this));
    }

    /* renamed from: a */
    void m7191a(float f, C1219t c1219t) {
        if (c1219t == null) {
            C1230c.m6414h().mo1067d("CrashlyticsCore", "Could not send reports. Settings are not available.");
            return;
        }
        new af(this.f4349p.f4191a, m7182h(c1219t.f3520a.f3475d), this.f4352s, this.f4353t).m6996a(f, m7161a(c1219t) ? new C1433g(this.f4343j, this.f4347n, c1219t.f3522c) : new C1390a());
    }

    /* renamed from: a */
    void m7192a(int i) {
        int a = i - am.m7036a(m7204g(), i, f4338e);
        am.m7037a(m7203f(), f4335b, a - am.m7036a(m7205h(), a, f4338e), f4338e);
    }

    /* renamed from: a */
    void m7193a(final C1381d c1381d) {
        this.f4344k.m7109b(new Callable<Void>(this) {
            /* renamed from: b */
            final /* synthetic */ C1438h f4305b;

            /* renamed from: a */
            public Void m7125a() {
                if (!this.f4305b.m7202e()) {
                    this.f4305b.m7169b(c1381d);
                }
                return null;
            }

            public /* synthetic */ Object call() {
                return m7125a();
            }
        });
    }

    /* renamed from: a */
    void m7194a(UncaughtExceptionHandler uncaughtExceptionHandler) {
        m7190a();
        this.f4359z = new C1449m(new C1417a(this) {
            /* renamed from: a */
            final /* synthetic */ C1438h f4293a;

            {
                this.f4293a = r1;
            }

            /* renamed from: a */
            public void mo1163a(Thread thread, Throwable th) {
                this.f4293a.m7195a(thread, th);
            }
        }, uncaughtExceptionHandler);
        Thread.setDefaultUncaughtExceptionHandler(this.f4359z);
    }

    /* renamed from: a */
    synchronized void m7195a(final Thread thread, final Throwable th) {
        C1230c.m6414h().mo1062a("CrashlyticsCore", "Crashlytics is handling uncaught exception \"" + th + "\" from thread " + thread.getName());
        this.f4354u.m7248b();
        final Date date = new Date();
        this.f4344k.m7107a(new Callable<Void>(this) {
            /* renamed from: d */
            final /* synthetic */ C1438h f4297d;

            /* renamed from: a */
            public Void m7122a() {
                this.f4297d.f4343j.m7232n();
                this.f4297d.m7158a(date, thread, th);
                C1219t b = C1217q.m6361a().m6364b();
                C1214p c1214p = b != null ? b.f3521b : null;
                this.f4297d.m7198b(c1214p);
                this.f4297d.m7186m();
                if (c1214p != null) {
                    this.f4297d.m7192a(c1214p.f3510g);
                }
                if (!this.f4297d.m7161a(b)) {
                    this.f4297d.m7168b(b);
                }
                return null;
            }

            public /* synthetic */ Object call() {
                return m7122a();
            }
        });
    }

    /* renamed from: a */
    void m7196a(File[] fileArr) {
        int length;
        File file;
        int i = 0;
        final Set hashSet = new HashSet();
        for (File file2 : fileArr) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Found invalid session part file: " + file2);
            hashSet.add(C1438h.m7138a(file2));
        }
        if (!hashSet.isEmpty()) {
            File i2 = m7206i();
            if (!i2.exists()) {
                i2.mkdir();
            }
            File[] a = m7165a(new FilenameFilter(this) {
                /* renamed from: b */
                final /* synthetic */ C1438h f4303b;

                public boolean accept(File file, String str) {
                    return str.length() < 35 ? false : hashSet.contains(str.substring(0, 35));
                }
            });
            length = a.length;
            while (i < length) {
                file2 = a[i];
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Moving session file: " + file2);
                if (!file2.renameTo(new File(i2, file2.getName()))) {
                    C1230c.m6414h().mo1062a("CrashlyticsCore", "Could not move session file. Deleting " + file2);
                    file2.delete();
                }
                i++;
            }
            m7188o();
        }
    }

    /* renamed from: a */
    boolean m7197a(final C1214p c1214p) {
        return ((Boolean) this.f4344k.m7107a(new Callable<Boolean>(this) {
            /* renamed from: b */
            final /* synthetic */ C1438h f4300b;

            /* renamed from: a */
            public Boolean m7124a() {
                if (this.f4300b.m7202e()) {
                    C1230c.m6414h().mo1062a("CrashlyticsCore", "Skipping session finalization because a crash has already occurred.");
                    return Boolean.FALSE;
                }
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Finalizing previously open sessions.");
                this.f4300b.m7140a(c1214p, true);
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Closed all previously open sessions");
                return Boolean.TRUE;
            }

            public /* synthetic */ Object call() {
                return m7124a();
            }
        })).booleanValue();
    }

    /* renamed from: b */
    void m7198b(C1214p c1214p) {
        m7140a(c1214p, false);
    }

    /* renamed from: b */
    File[] m7199b() {
        List linkedList = new LinkedList();
        Collections.addAll(linkedList, m7164a(m7204g(), f4335b));
        Collections.addAll(linkedList, m7164a(m7205h(), f4335b));
        Collections.addAll(linkedList, m7164a(m7203f(), f4335b));
        return (File[]) linkedList.toArray(new File[linkedList.size()]);
    }

    /* renamed from: c */
    File[] m7200c() {
        return m7165a(f4334a);
    }

    /* renamed from: d */
    void m7201d() {
        this.f4344k.m7108a(new C14204(this));
    }

    /* renamed from: e */
    boolean m7202e() {
        return this.f4359z != null && this.f4359z.m7241a();
    }

    /* renamed from: f */
    File m7203f() {
        return this.f4348o.mo1050a();
    }

    /* renamed from: g */
    File m7204g() {
        return new File(m7203f(), "fatal-sessions");
    }

    /* renamed from: h */
    File m7205h() {
        return new File(m7203f(), "nonfatal-sessions");
    }

    /* renamed from: i */
    File m7206i() {
        return new File(m7203f(), "invalidClsFiles");
    }
}
