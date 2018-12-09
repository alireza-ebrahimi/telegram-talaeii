package org.telegram.messenger;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import java.io.File;

public class NativeLoader {
    private static final String LIB_NAME = "tmessages.27";
    private static final String LIB_SO_NAME = "libtmessages.27.so";
    private static final int LIB_VERSION = 27;
    private static final String LOCALE_LIB_SO_NAME = "libtmessages.27loc.so";
    private static volatile boolean nativeLoaded = false;
    private String crashPath = TtmlNode.ANONYMOUS_REGION_ID;

    private static File getNativeLibraryDir(Context context) {
        File file;
        if (context != null) {
            try {
                file = new File((String) ApplicationInfo.class.getField("nativeLibraryDir").get(context.getApplicationInfo()));
            } catch (Throwable th) {
                th.printStackTrace();
            }
            if (file == null) {
                file = new File(context.getApplicationInfo().dataDir, "lib");
            }
            return file.isDirectory() ? file : null;
        }
        file = null;
        if (file == null) {
            file = new File(context.getApplicationInfo().dataDir, "lib");
        }
        if (file.isDirectory()) {
        }
    }

    private static native void init(String str, boolean z);

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void initNativeLibs(android.content.Context r6) {
        /*
        r2 = org.telegram.messenger.NativeLoader.class;
        monitor-enter(r2);
        r0 = nativeLoaded;	 Catch:{ all -> 0x00d1 }
        if (r0 == 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r2);
        return;
    L_0x0009:
        net.hockeyapp.android.C2367a.m11720a(r6);	 Catch:{ all -> 0x00d1 }
        r0 = android.os.Build.CPU_ABI;	 Catch:{ Exception -> 0x0122 }
        r1 = "armeabi-v7a";
        r0 = r0.equalsIgnoreCase(r1);	 Catch:{ Exception -> 0x0122 }
        if (r0 == 0) goto L_0x00d4;
    L_0x0017:
        r0 = "armeabi-v7a";
    L_0x001a:
        r1 = "os.arch";
        r1 = java.lang.System.getProperty(r1);	 Catch:{ Throwable -> 0x012b }
        if (r1 == 0) goto L_0x0130;
    L_0x0023:
        r3 = "686";
        r1 = r1.contains(r3);	 Catch:{ Throwable -> 0x012b }
        if (r1 == 0) goto L_0x0130;
    L_0x002c:
        r0 = "x86";
        r1 = r0;
    L_0x0030:
        r0 = getNativeLibraryDir(r6);	 Catch:{ Throwable -> 0x012b }
        if (r0 == 0) goto L_0x005f;
    L_0x0036:
        r3 = new java.io.File;	 Catch:{ Throwable -> 0x012b }
        r4 = "libtmessages.27.so";
        r3.<init>(r0, r4);	 Catch:{ Throwable -> 0x012b }
        r0 = r3.exists();	 Catch:{ Throwable -> 0x012b }
        if (r0 == 0) goto L_0x005f;
    L_0x0044:
        r0 = "load normal lib";
        org.telegram.messenger.FileLog.m13725d(r0);	 Catch:{ Throwable -> 0x012b }
        r0 = "tmessages.27";
        java.lang.System.loadLibrary(r0);	 Catch:{ Error -> 0x005b }
        r0 = net.hockeyapp.android.C2367a.f7955a;	 Catch:{ Error -> 0x005b }
        r3 = org.telegram.messenger.BuildVars.DEBUG_VERSION;	 Catch:{ Error -> 0x005b }
        init(r0, r3);	 Catch:{ Error -> 0x005b }
        r0 = 1;
        nativeLoaded = r0;	 Catch:{ Error -> 0x005b }
        goto L_0x0007;
    L_0x005b:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Throwable -> 0x012b }
    L_0x005f:
        r3 = new java.io.File;	 Catch:{ Throwable -> 0x012b }
        r0 = r6.getFilesDir();	 Catch:{ Throwable -> 0x012b }
        r4 = "lib";
        r3.<init>(r0, r4);	 Catch:{ Throwable -> 0x012b }
        r3.mkdirs();	 Catch:{ Throwable -> 0x012b }
        r4 = new java.io.File;	 Catch:{ Throwable -> 0x012b }
        r0 = "libtmessages.27loc.so";
        r4.<init>(r3, r0);	 Catch:{ Throwable -> 0x012b }
        r0 = r4.exists();	 Catch:{ Throwable -> 0x012b }
        if (r0 == 0) goto L_0x009c;
    L_0x007c:
        r0 = "Load local lib";
        org.telegram.messenger.FileLog.m13725d(r0);	 Catch:{ Error -> 0x0095 }
        r0 = r4.getAbsolutePath();	 Catch:{ Error -> 0x0095 }
        java.lang.System.load(r0);	 Catch:{ Error -> 0x0095 }
        r0 = net.hockeyapp.android.C2367a.f7955a;	 Catch:{ Error -> 0x0095 }
        r5 = org.telegram.messenger.BuildVars.DEBUG_VERSION;	 Catch:{ Error -> 0x0095 }
        init(r0, r5);	 Catch:{ Error -> 0x0095 }
        r0 = 1;
        nativeLoaded = r0;	 Catch:{ Error -> 0x0095 }
        goto L_0x0007;
    L_0x0095:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Throwable -> 0x012b }
        r4.delete();	 Catch:{ Throwable -> 0x012b }
    L_0x009c:
        r0 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x012b }
        r0.<init>();	 Catch:{ Throwable -> 0x012b }
        r5 = "Library not found, arch = ";
        r0 = r0.append(r5);	 Catch:{ Throwable -> 0x012b }
        r0 = r0.append(r1);	 Catch:{ Throwable -> 0x012b }
        r0 = r0.toString();	 Catch:{ Throwable -> 0x012b }
        org.telegram.messenger.FileLog.m13726e(r0);	 Catch:{ Throwable -> 0x012b }
        r0 = loadFromZip(r6, r3, r4, r1);	 Catch:{ Throwable -> 0x012b }
        if (r0 != 0) goto L_0x0007;
    L_0x00b9:
        r0 = "tmessages.27";
        java.lang.System.loadLibrary(r0);	 Catch:{ Error -> 0x00cb }
        r0 = net.hockeyapp.android.C2367a.f7955a;	 Catch:{ Error -> 0x00cb }
        r1 = org.telegram.messenger.BuildVars.DEBUG_VERSION;	 Catch:{ Error -> 0x00cb }
        init(r0, r1);	 Catch:{ Error -> 0x00cb }
        r0 = 1;
        nativeLoaded = r0;	 Catch:{ Error -> 0x00cb }
        goto L_0x0007;
    L_0x00cb:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ all -> 0x00d1 }
        goto L_0x0007;
    L_0x00d1:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
    L_0x00d4:
        r0 = android.os.Build.CPU_ABI;	 Catch:{ Exception -> 0x0122 }
        r1 = "armeabi";
        r0 = r0.equalsIgnoreCase(r1);	 Catch:{ Exception -> 0x0122 }
        if (r0 == 0) goto L_0x00e4;
    L_0x00df:
        r0 = "armeabi";
        goto L_0x001a;
    L_0x00e4:
        r0 = android.os.Build.CPU_ABI;	 Catch:{ Exception -> 0x0122 }
        r1 = "x86";
        r0 = r0.equalsIgnoreCase(r1);	 Catch:{ Exception -> 0x0122 }
        if (r0 == 0) goto L_0x00f4;
    L_0x00ef:
        r0 = "x86";
        goto L_0x001a;
    L_0x00f4:
        r0 = android.os.Build.CPU_ABI;	 Catch:{ Exception -> 0x0122 }
        r1 = "mips";
        r0 = r0.equalsIgnoreCase(r1);	 Catch:{ Exception -> 0x0122 }
        if (r0 == 0) goto L_0x0104;
    L_0x00ff:
        r0 = "mips";
        goto L_0x001a;
    L_0x0104:
        r0 = "armeabi";
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0122 }
        r1.<init>();	 Catch:{ Exception -> 0x0122 }
        r3 = "Unsupported arch: ";
        r1 = r1.append(r3);	 Catch:{ Exception -> 0x0122 }
        r3 = android.os.Build.CPU_ABI;	 Catch:{ Exception -> 0x0122 }
        r1 = r1.append(r3);	 Catch:{ Exception -> 0x0122 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0122 }
        org.telegram.messenger.FileLog.m13726e(r1);	 Catch:{ Exception -> 0x0122 }
        goto L_0x001a;
    L_0x0122:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Throwable -> 0x012b }
        r0 = "armeabi";
        goto L_0x001a;
    L_0x012b:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x00d1 }
        goto L_0x00b9;
    L_0x0130:
        r1 = r0;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NativeLoader.initNativeLibs(android.content.Context):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean loadFromZip(android.content.Context r8, java.io.File r9, java.io.File r10, java.lang.String r11) {
        /*
        r3 = 0;
        r0 = 1;
        r1 = 0;
        r4 = r9.listFiles();	 Catch:{ Exception -> 0x0013 }
        r5 = r4.length;	 Catch:{ Exception -> 0x0013 }
        r2 = r1;
    L_0x0009:
        if (r2 >= r5) goto L_0x0017;
    L_0x000b:
        r6 = r4[r2];	 Catch:{ Exception -> 0x0013 }
        r6.delete();	 Catch:{ Exception -> 0x0013 }
        r2 = r2 + 1;
        goto L_0x0009;
    L_0x0013:
        r2 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r2);
    L_0x0017:
        r4 = new java.util.zip.ZipFile;	 Catch:{ Exception -> 0x0106, all -> 0x00ff }
        r2 = r8.getApplicationInfo();	 Catch:{ Exception -> 0x0106, all -> 0x00ff }
        r2 = r2.sourceDir;	 Catch:{ Exception -> 0x0106, all -> 0x00ff }
        r4.<init>(r2);	 Catch:{ Exception -> 0x0106, all -> 0x00ff }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r2.<init>();	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r5 = "lib/";
        r2 = r2.append(r5);	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r2 = r2.append(r11);	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r5 = "/";
        r2 = r2.append(r5);	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r5 = "libtmessages.27.so";
        r2 = r2.append(r5);	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r2 = r4.getEntry(r2);	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        if (r2 != 0) goto L_0x0084;
    L_0x004a:
        r0 = new java.lang.Exception;	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r2.<init>();	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r5 = "Unable to find file in apk:lib/";
        r2 = r2.append(r5);	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r2 = r2.append(r11);	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r5 = "/";
        r2 = r2.append(r5);	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r5 = "tmessages.27";
        r2 = r2.append(r5);	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r0.<init>(r2);	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        throw r0;	 Catch:{ Exception -> 0x0072, all -> 0x00da }
    L_0x0072:
        r0 = move-exception;
        r2 = r3;
        r3 = r4;
    L_0x0075:
        org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ all -> 0x0102 }
        if (r2 == 0) goto L_0x007d;
    L_0x007a:
        r2.close();	 Catch:{ Exception -> 0x00eb }
    L_0x007d:
        if (r3 == 0) goto L_0x0082;
    L_0x007f:
        r3.close();	 Catch:{ Exception -> 0x00f0 }
    L_0x0082:
        r0 = r1;
    L_0x0083:
        return r0;
    L_0x0084:
        r3 = r4.getInputStream(r2);	 Catch:{ Exception -> 0x0072, all -> 0x00da }
        r2 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x009f, all -> 0x00da }
        r2.<init>(r10);	 Catch:{ Exception -> 0x009f, all -> 0x00da }
        r5 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r5 = new byte[r5];	 Catch:{ Exception -> 0x009f, all -> 0x00da }
    L_0x0091:
        r6 = r3.read(r5);	 Catch:{ Exception -> 0x009f, all -> 0x00da }
        if (r6 <= 0) goto L_0x00a3;
    L_0x0097:
        java.lang.Thread.yield();	 Catch:{ Exception -> 0x009f, all -> 0x00da }
        r7 = 0;
        r2.write(r5, r7, r6);	 Catch:{ Exception -> 0x009f, all -> 0x00da }
        goto L_0x0091;
    L_0x009f:
        r0 = move-exception;
        r2 = r3;
        r3 = r4;
        goto L_0x0075;
    L_0x00a3:
        r2.close();	 Catch:{ Exception -> 0x009f, all -> 0x00da }
        r2 = 1;
        r5 = 0;
        r10.setReadable(r2, r5);	 Catch:{ Exception -> 0x009f, all -> 0x00da }
        r2 = 1;
        r5 = 0;
        r10.setExecutable(r2, r5);	 Catch:{ Exception -> 0x009f, all -> 0x00da }
        r2 = 1;
        r10.setWritable(r2);	 Catch:{ Exception -> 0x009f, all -> 0x00da }
        r2 = r10.getAbsolutePath();	 Catch:{ Error -> 0x00d5 }
        java.lang.System.load(r2);	 Catch:{ Error -> 0x00d5 }
        r2 = net.hockeyapp.android.C2367a.f7955a;	 Catch:{ Error -> 0x00d5 }
        r5 = org.telegram.messenger.BuildVars.DEBUG_VERSION;	 Catch:{ Error -> 0x00d5 }
        init(r2, r5);	 Catch:{ Error -> 0x00d5 }
        r2 = 1;
        nativeLoaded = r2;	 Catch:{ Error -> 0x00d5 }
    L_0x00c5:
        if (r3 == 0) goto L_0x00ca;
    L_0x00c7:
        r3.close();	 Catch:{ Exception -> 0x00e6 }
    L_0x00ca:
        if (r4 == 0) goto L_0x0083;
    L_0x00cc:
        r4.close();	 Catch:{ Exception -> 0x00d0 }
        goto L_0x0083;
    L_0x00d0:
        r1 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r1);
        goto L_0x0083;
    L_0x00d5:
        r2 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r2);	 Catch:{ Exception -> 0x009f, all -> 0x00da }
        goto L_0x00c5;
    L_0x00da:
        r0 = move-exception;
    L_0x00db:
        if (r3 == 0) goto L_0x00e0;
    L_0x00dd:
        r3.close();	 Catch:{ Exception -> 0x00f5 }
    L_0x00e0:
        if (r4 == 0) goto L_0x00e5;
    L_0x00e2:
        r4.close();	 Catch:{ Exception -> 0x00fa }
    L_0x00e5:
        throw r0;
    L_0x00e6:
        r1 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r1);
        goto L_0x00ca;
    L_0x00eb:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);
        goto L_0x007d;
    L_0x00f0:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);
        goto L_0x0082;
    L_0x00f5:
        r1 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r1);
        goto L_0x00e0;
    L_0x00fa:
        r1 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r1);
        goto L_0x00e5;
    L_0x00ff:
        r0 = move-exception;
        r4 = r3;
        goto L_0x00db;
    L_0x0102:
        r0 = move-exception;
        r4 = r3;
        r3 = r2;
        goto L_0x00db;
    L_0x0106:
        r0 = move-exception;
        r2 = r3;
        goto L_0x0075;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NativeLoader.loadFromZip(android.content.Context, java.io.File, java.io.File, java.lang.String):boolean");
    }
}
