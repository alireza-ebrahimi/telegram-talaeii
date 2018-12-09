package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;
import java.util.HashMap;

/* renamed from: android.support.v4.content.l */
public final class C0424l {
    /* renamed from: f */
    private static final Object f1179f = new Object();
    /* renamed from: g */
    private static C0424l f1180g;
    /* renamed from: a */
    private final Context f1181a;
    /* renamed from: b */
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> f1182b = new HashMap();
    /* renamed from: c */
    private final HashMap<String, ArrayList<C0423b>> f1183c = new HashMap();
    /* renamed from: d */
    private final ArrayList<C0422a> f1184d = new ArrayList();
    /* renamed from: e */
    private final Handler f1185e;

    /* renamed from: android.support.v4.content.l$a */
    private static class C0422a {
        /* renamed from: a */
        final Intent f1174a;
        /* renamed from: b */
        final ArrayList<C0423b> f1175b;

        C0422a(Intent intent, ArrayList<C0423b> arrayList) {
            this.f1174a = intent;
            this.f1175b = arrayList;
        }
    }

    /* renamed from: android.support.v4.content.l$b */
    private static class C0423b {
        /* renamed from: a */
        final IntentFilter f1176a;
        /* renamed from: b */
        final BroadcastReceiver f1177b;
        /* renamed from: c */
        boolean f1178c;

        C0423b(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.f1176a = intentFilter;
            this.f1177b = broadcastReceiver;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("Receiver{");
            stringBuilder.append(this.f1177b);
            stringBuilder.append(" filter=");
            stringBuilder.append(this.f1176a);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    private C0424l(Context context) {
        this.f1181a = context;
        this.f1185e = new Handler(this, context.getMainLooper()) {
            /* renamed from: a */
            final /* synthetic */ C0424l f1173a;

            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        this.f1173a.m1900a();
                        return;
                    default:
                        super.handleMessage(message);
                        return;
                }
            }
        };
    }

    /* renamed from: a */
    public static C0424l m1899a(Context context) {
        C0424l c0424l;
        synchronized (f1179f) {
            if (f1180g == null) {
                f1180g = new C0424l(context.getApplicationContext());
            }
            c0424l = f1180g;
        }
        return c0424l;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    private void m1900a() {
        /*
        r8 = this;
        r2 = 0;
    L_0x0001:
        r1 = r8.f1182b;
        monitor-enter(r1);
        r0 = r8.f1184d;	 Catch:{ all -> 0x003f }
        r0 = r0.size();	 Catch:{ all -> 0x003f }
        if (r0 > 0) goto L_0x000e;
    L_0x000c:
        monitor-exit(r1);	 Catch:{ all -> 0x003f }
        return;
    L_0x000e:
        r4 = new android.support.v4.content.C0424l.C0422a[r0];	 Catch:{ all -> 0x003f }
        r0 = r8.f1184d;	 Catch:{ all -> 0x003f }
        r0.toArray(r4);	 Catch:{ all -> 0x003f }
        r0 = r8.f1184d;	 Catch:{ all -> 0x003f }
        r0.clear();	 Catch:{ all -> 0x003f }
        monitor-exit(r1);	 Catch:{ all -> 0x003f }
        r3 = r2;
    L_0x001c:
        r0 = r4.length;
        if (r3 >= r0) goto L_0x0001;
    L_0x001f:
        r5 = r4[r3];
        r1 = r2;
    L_0x0022:
        r0 = r5.f1175b;
        r0 = r0.size();
        if (r1 >= r0) goto L_0x0042;
    L_0x002a:
        r0 = r5.f1175b;
        r0 = r0.get(r1);
        r0 = (android.support.v4.content.C0424l.C0423b) r0;
        r0 = r0.f1177b;
        r6 = r8.f1181a;
        r7 = r5.f1174a;
        r0.onReceive(r6, r7);
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x0022;
    L_0x003f:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x003f }
        throw r0;
    L_0x0042:
        r0 = r3 + 1;
        r3 = r0;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.l.a():void");
    }

    /* renamed from: a */
    public void m1902a(BroadcastReceiver broadcastReceiver) {
        synchronized (this.f1182b) {
            ArrayList arrayList = (ArrayList) this.f1182b.remove(broadcastReceiver);
            if (arrayList == null) {
                return;
            }
            for (int i = 0; i < arrayList.size(); i++) {
                IntentFilter intentFilter = (IntentFilter) arrayList.get(i);
                for (int i2 = 0; i2 < intentFilter.countActions(); i2++) {
                    String action = intentFilter.getAction(i2);
                    ArrayList arrayList2 = (ArrayList) this.f1183c.get(action);
                    if (arrayList2 != null) {
                        int i3 = 0;
                        while (i3 < arrayList2.size()) {
                            int i4;
                            if (((C0423b) arrayList2.get(i3)).f1177b == broadcastReceiver) {
                                arrayList2.remove(i3);
                                i4 = i3 - 1;
                            } else {
                                i4 = i3;
                            }
                            i3 = i4 + 1;
                        }
                        if (arrayList2.size() <= 0) {
                            this.f1183c.remove(action);
                        }
                    }
                }
            }
        }
    }

    /* renamed from: a */
    public void m1903a(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        synchronized (this.f1182b) {
            C0423b c0423b = new C0423b(intentFilter, broadcastReceiver);
            ArrayList arrayList = (ArrayList) this.f1182b.get(broadcastReceiver);
            if (arrayList == null) {
                arrayList = new ArrayList(1);
                this.f1182b.put(broadcastReceiver, arrayList);
            }
            arrayList.add(intentFilter);
            for (int i = 0; i < intentFilter.countActions(); i++) {
                String action = intentFilter.getAction(i);
                arrayList = (ArrayList) this.f1183c.get(action);
                if (arrayList == null) {
                    arrayList = new ArrayList(1);
                    this.f1183c.put(action, arrayList);
                }
                arrayList.add(c0423b);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    public boolean m1904a(android.content.Intent r17) {
        /*
        r16 = this;
        r0 = r16;
        r13 = r0.f1182b;
        monitor-enter(r13);
        r2 = r17.getAction();	 Catch:{ all -> 0x0110 }
        r0 = r16;
        r1 = r0.f1181a;	 Catch:{ all -> 0x0110 }
        r1 = r1.getContentResolver();	 Catch:{ all -> 0x0110 }
        r0 = r17;
        r3 = r0.resolveTypeIfNeeded(r1);	 Catch:{ all -> 0x0110 }
        r5 = r17.getData();	 Catch:{ all -> 0x0110 }
        r4 = r17.getScheme();	 Catch:{ all -> 0x0110 }
        r6 = r17.getCategories();	 Catch:{ all -> 0x0110 }
        r1 = r17.getFlags();	 Catch:{ all -> 0x0110 }
        r1 = r1 & 8;
        if (r1 == 0) goto L_0x00d3;
    L_0x002b:
        r1 = 1;
        r12 = r1;
    L_0x002d:
        if (r12 == 0) goto L_0x0061;
    L_0x002f:
        r1 = "LocalBroadcastManager";
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0110 }
        r7.<init>();	 Catch:{ all -> 0x0110 }
        r8 = "Resolving type ";
        r7 = r7.append(r8);	 Catch:{ all -> 0x0110 }
        r7 = r7.append(r3);	 Catch:{ all -> 0x0110 }
        r8 = " scheme ";
        r7 = r7.append(r8);	 Catch:{ all -> 0x0110 }
        r7 = r7.append(r4);	 Catch:{ all -> 0x0110 }
        r8 = " of intent ";
        r7 = r7.append(r8);	 Catch:{ all -> 0x0110 }
        r0 = r17;
        r7 = r7.append(r0);	 Catch:{ all -> 0x0110 }
        r7 = r7.toString();	 Catch:{ all -> 0x0110 }
        android.util.Log.v(r1, r7);	 Catch:{ all -> 0x0110 }
    L_0x0061:
        r0 = r16;
        r1 = r0.f1183c;	 Catch:{ all -> 0x0110 }
        r7 = r17.getAction();	 Catch:{ all -> 0x0110 }
        r1 = r1.get(r7);	 Catch:{ all -> 0x0110 }
        r0 = r1;
        r0 = (java.util.ArrayList) r0;	 Catch:{ all -> 0x0110 }
        r8 = r0;
        if (r8 == 0) goto L_0x0182;
    L_0x0073:
        if (r12 == 0) goto L_0x008f;
    L_0x0075:
        r1 = "LocalBroadcastManager";
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0110 }
        r7.<init>();	 Catch:{ all -> 0x0110 }
        r9 = "Action list: ";
        r7 = r7.append(r9);	 Catch:{ all -> 0x0110 }
        r7 = r7.append(r8);	 Catch:{ all -> 0x0110 }
        r7 = r7.toString();	 Catch:{ all -> 0x0110 }
        android.util.Log.v(r1, r7);	 Catch:{ all -> 0x0110 }
    L_0x008f:
        r10 = 0;
        r1 = 0;
        r11 = r1;
    L_0x0092:
        r1 = r8.size();	 Catch:{ all -> 0x0110 }
        if (r11 >= r1) goto L_0x0147;
    L_0x0098:
        r1 = r8.get(r11);	 Catch:{ all -> 0x0110 }
        r0 = r1;
        r0 = (android.support.v4.content.C0424l.C0423b) r0;	 Catch:{ all -> 0x0110 }
        r9 = r0;
        if (r12 == 0) goto L_0x00be;
    L_0x00a2:
        r1 = "LocalBroadcastManager";
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0110 }
        r7.<init>();	 Catch:{ all -> 0x0110 }
        r14 = "Matching against filter ";
        r7 = r7.append(r14);	 Catch:{ all -> 0x0110 }
        r14 = r9.f1176a;	 Catch:{ all -> 0x0110 }
        r7 = r7.append(r14);	 Catch:{ all -> 0x0110 }
        r7 = r7.toString();	 Catch:{ all -> 0x0110 }
        android.util.Log.v(r1, r7);	 Catch:{ all -> 0x0110 }
    L_0x00be:
        r1 = r9.f1178c;	 Catch:{ all -> 0x0110 }
        if (r1 == 0) goto L_0x00d7;
    L_0x00c2:
        if (r12 == 0) goto L_0x0135;
    L_0x00c4:
        r1 = "LocalBroadcastManager";
        r7 = "  Filter's target already added";
        android.util.Log.v(r1, r7);	 Catch:{ all -> 0x0110 }
        r1 = r10;
    L_0x00ce:
        r7 = r11 + 1;
        r11 = r7;
        r10 = r1;
        goto L_0x0092;
    L_0x00d3:
        r1 = 0;
        r12 = r1;
        goto L_0x002d;
    L_0x00d7:
        r1 = r9.f1176a;	 Catch:{ all -> 0x0110 }
        r7 = "LocalBroadcastManager";
        r1 = r1.match(r2, r3, r4, r5, r6, r7);	 Catch:{ all -> 0x0110 }
        if (r1 < 0) goto L_0x0113;
    L_0x00e2:
        if (r12 == 0) goto L_0x0102;
    L_0x00e4:
        r7 = "LocalBroadcastManager";
        r14 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0110 }
        r14.<init>();	 Catch:{ all -> 0x0110 }
        r15 = "  Filter matched!  match=0x";
        r14 = r14.append(r15);	 Catch:{ all -> 0x0110 }
        r1 = java.lang.Integer.toHexString(r1);	 Catch:{ all -> 0x0110 }
        r1 = r14.append(r1);	 Catch:{ all -> 0x0110 }
        r1 = r1.toString();	 Catch:{ all -> 0x0110 }
        android.util.Log.v(r7, r1);	 Catch:{ all -> 0x0110 }
    L_0x0102:
        if (r10 != 0) goto L_0x0185;
    L_0x0104:
        r1 = new java.util.ArrayList;	 Catch:{ all -> 0x0110 }
        r1.<init>();	 Catch:{ all -> 0x0110 }
    L_0x0109:
        r1.add(r9);	 Catch:{ all -> 0x0110 }
        r7 = 1;
        r9.f1178c = r7;	 Catch:{ all -> 0x0110 }
        goto L_0x00ce;
    L_0x0110:
        r1 = move-exception;
        monitor-exit(r13);	 Catch:{ all -> 0x0110 }
        throw r1;
    L_0x0113:
        if (r12 == 0) goto L_0x0135;
    L_0x0115:
        switch(r1) {
            case -4: goto L_0x013b;
            case -3: goto L_0x0137;
            case -2: goto L_0x013f;
            case -1: goto L_0x0143;
            default: goto L_0x0118;
        };
    L_0x0118:
        r1 = "unknown reason";
    L_0x011b:
        r7 = "LocalBroadcastManager";
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0110 }
        r9.<init>();	 Catch:{ all -> 0x0110 }
        r14 = "  Filter did not match: ";
        r9 = r9.append(r14);	 Catch:{ all -> 0x0110 }
        r1 = r9.append(r1);	 Catch:{ all -> 0x0110 }
        r1 = r1.toString();	 Catch:{ all -> 0x0110 }
        android.util.Log.v(r7, r1);	 Catch:{ all -> 0x0110 }
    L_0x0135:
        r1 = r10;
        goto L_0x00ce;
    L_0x0137:
        r1 = "action";
        goto L_0x011b;
    L_0x013b:
        r1 = "category";
        goto L_0x011b;
    L_0x013f:
        r1 = "data";
        goto L_0x011b;
    L_0x0143:
        r1 = "type";
        goto L_0x011b;
    L_0x0147:
        if (r10 == 0) goto L_0x0182;
    L_0x0149:
        r1 = 0;
        r2 = r1;
    L_0x014b:
        r1 = r10.size();	 Catch:{ all -> 0x0110 }
        if (r2 >= r1) goto L_0x015e;
    L_0x0151:
        r1 = r10.get(r2);	 Catch:{ all -> 0x0110 }
        r1 = (android.support.v4.content.C0424l.C0423b) r1;	 Catch:{ all -> 0x0110 }
        r3 = 0;
        r1.f1178c = r3;	 Catch:{ all -> 0x0110 }
        r1 = r2 + 1;
        r2 = r1;
        goto L_0x014b;
    L_0x015e:
        r0 = r16;
        r1 = r0.f1184d;	 Catch:{ all -> 0x0110 }
        r2 = new android.support.v4.content.l$a;	 Catch:{ all -> 0x0110 }
        r0 = r17;
        r2.<init>(r0, r10);	 Catch:{ all -> 0x0110 }
        r1.add(r2);	 Catch:{ all -> 0x0110 }
        r0 = r16;
        r1 = r0.f1185e;	 Catch:{ all -> 0x0110 }
        r2 = 1;
        r1 = r1.hasMessages(r2);	 Catch:{ all -> 0x0110 }
        if (r1 != 0) goto L_0x017f;
    L_0x0177:
        r0 = r16;
        r1 = r0.f1185e;	 Catch:{ all -> 0x0110 }
        r2 = 1;
        r1.sendEmptyMessage(r2);	 Catch:{ all -> 0x0110 }
    L_0x017f:
        r1 = 1;
        monitor-exit(r13);	 Catch:{ all -> 0x0110 }
    L_0x0181:
        return r1;
    L_0x0182:
        monitor-exit(r13);	 Catch:{ all -> 0x0110 }
        r1 = 0;
        goto L_0x0181;
    L_0x0185:
        r1 = r10;
        goto L_0x0109;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.l.a(android.content.Intent):boolean");
    }
}
