package com.crashlytics.android.p066c;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.p037a.p039b.C1098h;

/* renamed from: com.crashlytics.android.c.af */
class af {
    /* renamed from: a */
    static final Map<String, String> f4208a = Collections.singletonMap("X-CRASHLYTICS-INVALID-SESSION", "1");
    /* renamed from: b */
    private static final short[] f4209b = new short[]{(short) 10, (short) 20, (short) 30, (short) 60, (short) 120, (short) 300};
    /* renamed from: c */
    private final Object f4210c = new Object();
    /* renamed from: d */
    private final C1451o f4211d;
    /* renamed from: e */
    private final String f4212e;
    /* renamed from: f */
    private final C1392c f4213f;
    /* renamed from: g */
    private final C1391b f4214g;
    /* renamed from: h */
    private Thread f4215h;

    /* renamed from: com.crashlytics.android.c.af$d */
    interface C1389d {
        /* renamed from: a */
        boolean mo1153a();
    }

    /* renamed from: com.crashlytics.android.c.af$a */
    static final class C1390a implements C1389d {
        C1390a() {
        }

        /* renamed from: a */
        public boolean mo1153a() {
            return true;
        }
    }

    /* renamed from: com.crashlytics.android.c.af$b */
    interface C1391b {
        /* renamed from: a */
        boolean mo1168a();
    }

    /* renamed from: com.crashlytics.android.c.af$c */
    interface C1392c {
        /* renamed from: a */
        File[] mo1166a();

        /* renamed from: b */
        File[] mo1167b();
    }

    /* renamed from: com.crashlytics.android.c.af$e */
    private class C1393e extends C1098h {
        /* renamed from: a */
        final /* synthetic */ af f4205a;
        /* renamed from: b */
        private final float f4206b;
        /* renamed from: c */
        private final C1389d f4207c;

        C1393e(af afVar, float f, C1389d c1389d) {
            this.f4205a = afVar;
            this.f4206b = f;
            this.f4207c = c1389d;
        }

        /* renamed from: b */
        private void m6990b() {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Starting report processing in " + this.f4206b + " second(s)...");
            if (this.f4206b > BitmapDescriptorFactory.HUE_RED) {
                try {
                    Thread.sleep((long) (this.f4206b * 1000.0f));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            List<ae> a = this.f4205a.m6995a();
            if (!this.f4205a.f4214g.mo1168a()) {
                if (a.isEmpty() || this.f4207c.mo1153a()) {
                    List list = a;
                    int i = 0;
                    while (!r0.isEmpty() && !this.f4205a.f4214g.mo1168a()) {
                        C1230c.m6414h().mo1062a("CrashlyticsCore", "Attempting to send " + r0.size() + " report(s)");
                        for (ae a2 : r0) {
                            this.f4205a.m6997a(a2);
                        }
                        List a3 = this.f4205a.m6995a();
                        if (a3.isEmpty()) {
                            list = a3;
                        } else {
                            int i2 = i + 1;
                            long j = (long) af.f4209b[Math.min(i, af.f4209b.length - 1)];
                            C1230c.m6414h().mo1062a("CrashlyticsCore", "Report submisson: scheduling delayed retry in " + j + " seconds");
                            try {
                                Thread.sleep(j * 1000);
                                i = i2;
                                list = a3;
                            } catch (InterruptedException e2) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }
                    }
                    return;
                }
                C1230c.m6414h().mo1062a("CrashlyticsCore", "User declined to send. Removing " + a.size() + " Report(s).");
                for (ae a22 : a) {
                    a22.mo1159f();
                }
            }
        }

        /* renamed from: a */
        public void mo1020a() {
            try {
                m6990b();
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("CrashlyticsCore", "An unexpected error occurred while attempting to upload crash reports.", e);
            }
            this.f4205a.f4215h = null;
        }
    }

    public af(String str, C1451o c1451o, C1392c c1392c, C1391b c1391b) {
        if (c1451o == null) {
            throw new IllegalArgumentException("createReportCall must not be null.");
        }
        this.f4211d = c1451o;
        this.f4212e = str;
        this.f4213f = c1392c;
        this.f4214g = c1391b;
    }

    /* renamed from: a */
    List<ae> m6995a() {
        String a;
        C1230c.m6414h().mo1062a("CrashlyticsCore", "Checking for crash reports...");
        synchronized (this.f4210c) {
            File[] a2 = this.f4213f.mo1166a();
            File[] b = this.f4213f.mo1167b();
        }
        List<ae> linkedList = new LinkedList();
        if (a2 != null) {
            for (File file : a2) {
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Found crash report " + file.getPath());
                linkedList.add(new ah(file));
            }
        }
        Map hashMap = new HashMap();
        if (b != null) {
            for (File file2 : b) {
                a = C1438h.m7138a(file2);
                if (!hashMap.containsKey(a)) {
                    hashMap.put(a, new LinkedList());
                }
                ((List) hashMap.get(a)).add(file2);
            }
        }
        for (String a3 : hashMap.keySet()) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Found invalid session: " + a3);
            List list = (List) hashMap.get(a3);
            linkedList.add(new C1457t(a3, (File[]) list.toArray(new File[list.size()])));
        }
        if (linkedList.isEmpty()) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "No reports found.");
        }
        return linkedList;
    }

    /* renamed from: a */
    public synchronized void m6996a(float f, C1389d c1389d) {
        if (this.f4215h != null) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Report upload has already been started.");
        } else {
            this.f4215h = new Thread(new C1393e(this, f, c1389d), "Crashlytics Report Uploader");
            this.f4215h.start();
        }
    }

    /* renamed from: a */
    boolean m6997a(ae aeVar) {
        boolean z = false;
        synchronized (this.f4210c) {
            try {
                boolean a = this.f4211d.mo1174a(new C1450n(this.f4212e, aeVar));
                C1230c.m6414h().mo1066c("CrashlyticsCore", "Crashlytics report upload " + (a ? "complete: " : "FAILED: ") + aeVar.mo1155b());
                if (a) {
                    aeVar.mo1159f();
                    z = true;
                }
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("CrashlyticsCore", "Error occurred sending report " + aeVar, e);
            }
        }
        return z;
    }
}
