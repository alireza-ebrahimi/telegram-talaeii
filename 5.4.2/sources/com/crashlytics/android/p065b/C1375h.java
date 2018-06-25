package com.crashlytics.android.p065b;

import android.content.Context;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import p033b.p034a.p035a.p036a.p037a.p038a.C1095d;

/* renamed from: com.crashlytics.android.b.h */
public class C1375h implements C1095d<String> {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    public java.lang.String m6954a(android.content.Context r10) {
        /*
        r9 = this;
        r4 = java.lang.System.nanoTime();
        r0 = "";
        r1 = 0;
        r2 = "io.crash.air";
        r1 = r9.m6956a(r10, r2);	 Catch:{ NameNotFoundException -> 0x0059, FileNotFoundException -> 0x007c, IOException -> 0x00a3, all -> 0x00cb }
        r0 = r9.m6955a(r1);	 Catch:{ NameNotFoundException -> 0x0059, FileNotFoundException -> 0x00ec, IOException -> 0x00e7 }
        if (r1 == 0) goto L_0x0018;
    L_0x0015:
        r1.close();	 Catch:{ IOException -> 0x004a }
    L_0x0018:
        r2 = java.lang.System.nanoTime();
        r2 = r2 - r4;
        r2 = (double) r2;
        r4 = 4696837146684686336; // 0x412e848000000000 float:0.0 double:1000000.0;
        r2 = r2 / r4;
        r1 = p033b.p034a.p035a.p036a.C1230c.m6414h();
        r4 = "Beta";
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "Beta device token load took ";
        r5 = r5.append(r6);
        r2 = r5.append(r2);
        r3 = "ms";
        r2 = r2.append(r3);
        r2 = r2.toString();
        r1.mo1062a(r4, r2);
        return r0;
    L_0x004a:
        r1 = move-exception;
        r2 = p033b.p034a.p035a.p036a.C1230c.m6414h();
        r3 = "Beta";
        r6 = "Failed to close the APK file";
        r2.mo1070e(r3, r6, r1);
        goto L_0x0018;
    L_0x0059:
        r2 = move-exception;
        r2 = p033b.p034a.p035a.p036a.C1230c.m6414h();	 Catch:{ all -> 0x00e2 }
        r3 = "Beta";
        r6 = "Beta by Crashlytics app is not installed";
        r2.mo1062a(r3, r6);	 Catch:{ all -> 0x00e2 }
        if (r1 == 0) goto L_0x0018;
    L_0x0069:
        r1.close();	 Catch:{ IOException -> 0x006d }
        goto L_0x0018;
    L_0x006d:
        r1 = move-exception;
        r2 = p033b.p034a.p035a.p036a.C1230c.m6414h();
        r3 = "Beta";
        r6 = "Failed to close the APK file";
        r2.mo1070e(r3, r6, r1);
        goto L_0x0018;
    L_0x007c:
        r2 = move-exception;
        r8 = r2;
        r2 = r1;
        r1 = r8;
    L_0x0080:
        r3 = p033b.p034a.p035a.p036a.C1230c.m6414h();	 Catch:{ all -> 0x00e5 }
        r6 = "Beta";
        r7 = "Failed to find the APK file";
        r3.mo1070e(r6, r7, r1);	 Catch:{ all -> 0x00e5 }
        if (r2 == 0) goto L_0x0018;
    L_0x008f:
        r2.close();	 Catch:{ IOException -> 0x0093 }
        goto L_0x0018;
    L_0x0093:
        r1 = move-exception;
        r2 = p033b.p034a.p035a.p036a.C1230c.m6414h();
        r3 = "Beta";
        r6 = "Failed to close the APK file";
        r2.mo1070e(r3, r6, r1);
        goto L_0x0018;
    L_0x00a3:
        r2 = move-exception;
        r8 = r2;
        r2 = r1;
        r1 = r8;
    L_0x00a7:
        r3 = p033b.p034a.p035a.p036a.C1230c.m6414h();	 Catch:{ all -> 0x00e5 }
        r6 = "Beta";
        r7 = "Failed to read the APK file";
        r3.mo1070e(r6, r7, r1);	 Catch:{ all -> 0x00e5 }
        if (r2 == 0) goto L_0x0018;
    L_0x00b6:
        r2.close();	 Catch:{ IOException -> 0x00bb }
        goto L_0x0018;
    L_0x00bb:
        r1 = move-exception;
        r2 = p033b.p034a.p035a.p036a.C1230c.m6414h();
        r3 = "Beta";
        r6 = "Failed to close the APK file";
        r2.mo1070e(r3, r6, r1);
        goto L_0x0018;
    L_0x00cb:
        r0 = move-exception;
        r2 = r1;
    L_0x00cd:
        if (r2 == 0) goto L_0x00d2;
    L_0x00cf:
        r2.close();	 Catch:{ IOException -> 0x00d3 }
    L_0x00d2:
        throw r0;
    L_0x00d3:
        r1 = move-exception;
        r2 = p033b.p034a.p035a.p036a.C1230c.m6414h();
        r3 = "Beta";
        r4 = "Failed to close the APK file";
        r2.mo1070e(r3, r4, r1);
        goto L_0x00d2;
    L_0x00e2:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00cd;
    L_0x00e5:
        r0 = move-exception;
        goto L_0x00cd;
    L_0x00e7:
        r2 = move-exception;
        r8 = r2;
        r2 = r1;
        r1 = r8;
        goto L_0x00a7;
    L_0x00ec:
        r2 = move-exception;
        r8 = r2;
        r2 = r1;
        r1 = r8;
        goto L_0x0080;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.b.h.a(android.content.Context):java.lang.String");
    }

    /* renamed from: a */
    String m6955a(ZipInputStream zipInputStream) {
        ZipEntry nextEntry = zipInputStream.getNextEntry();
        if (nextEntry != null) {
            String name = nextEntry.getName();
            if (name.startsWith("assets/com.crashlytics.android.beta/dirfactor-device-token=")) {
                return name.substring("assets/com.crashlytics.android.beta/dirfactor-device-token=".length(), name.length() - 1);
            }
        }
        return TtmlNode.ANONYMOUS_REGION_ID;
    }

    /* renamed from: a */
    ZipInputStream m6956a(Context context, String str) {
        return new ZipInputStream(new FileInputStream(context.getPackageManager().getApplicationInfo(str, 0).sourceDir));
    }

    /* renamed from: b */
    public /* synthetic */ Object mo1022b(Context context) {
        return m6954a(context);
    }
}
