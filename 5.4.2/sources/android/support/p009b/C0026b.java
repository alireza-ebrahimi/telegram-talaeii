package android.support.p009b;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.util.Log;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.telegram.messenger.MessagesController;

/* renamed from: android.support.b.b */
final class C0026b {
    /* renamed from: a */
    private static Method f96a;

    static {
        try {
            f96a = Editor.class.getMethod("apply", new Class[0]);
        } catch (NoSuchMethodException e) {
            f96a = null;
        }
    }

    /* renamed from: a */
    private static SharedPreferences m87a(Context context) {
        return context.getSharedPreferences("multidex.version", VERSION.SDK_INT < 11 ? 0 : 4);
    }

    /* renamed from: a */
    static List<File> m88a(Context context, ApplicationInfo applicationInfo, File file, boolean z) {
        List<File> a;
        Log.i("MultiDex", "MultiDexExtractor.load(" + applicationInfo.sourceDir + ", " + z + ")");
        File file2 = new File(applicationInfo.sourceDir);
        long c = C0026b.m99c(file2);
        if (z || C0026b.m96a(context, file2, c)) {
            Log.i("MultiDex", "Detected that extraction must be performed.");
            a = C0026b.m90a(file2, file);
            C0026b.m91a(context, C0026b.m98b(file2), c, a.size() + 1);
        } else {
            try {
                a = C0026b.m89a(context, file2, file);
            } catch (Throwable e) {
                Log.w("MultiDex", "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", e);
                a = C0026b.m90a(file2, file);
                C0026b.m91a(context, C0026b.m98b(file2), c, a.size() + 1);
            }
        }
        Log.i("MultiDex", "load found " + a.size() + " secondary dex files");
        return a;
    }

    /* renamed from: a */
    private static List<File> m89a(Context context, File file, File file2) {
        Log.i("MultiDex", "loading existing secondary dex files");
        String str = file.getName() + ".classes";
        int i = C0026b.m87a(context).getInt("dex.number", 1);
        List<File> arrayList = new ArrayList(i);
        int i2 = 2;
        while (i2 <= i) {
            File file3 = new File(file2, str + i2 + ".zip");
            if (file3.isFile()) {
                arrayList.add(file3);
                if (C0026b.m97a(file3)) {
                    i2++;
                } else {
                    Log.i("MultiDex", "Invalid zip file: " + file3);
                    throw new IOException("Invalid ZIP file.");
                }
            }
            throw new IOException("Missing extracted secondary dex file '" + file3.getPath() + "'");
        }
        return arrayList;
    }

    /* renamed from: a */
    private static List<File> m90a(File file, File file2) {
        String str = file.getName() + ".classes";
        C0026b.m94a(file2, str);
        List<File> arrayList = new ArrayList();
        ZipFile zipFile = new ZipFile(file);
        try {
            ZipEntry entry = zipFile.getEntry("classes" + 2 + ".dex");
            int i = 2;
            while (entry != null) {
                File file3 = new File(file2, str + i + ".zip");
                arrayList.add(file3);
                Log.i("MultiDex", "Extraction is needed for file " + file3);
                boolean z = false;
                int i2 = 0;
                while (i2 < 3 && !z) {
                    int i3 = i2 + 1;
                    C0026b.m95a(zipFile, entry, file3, str);
                    boolean a = C0026b.m97a(file3);
                    Log.i("MultiDex", "Extraction " + (a ? C1797b.SUCCESS : "failed") + " - length " + file3.getAbsolutePath() + ": " + file3.length());
                    if (!a) {
                        file3.delete();
                        if (file3.exists()) {
                            Log.w("MultiDex", "Failed to delete corrupted secondary dex '" + file3.getPath() + "'");
                            z = a;
                            i2 = i3;
                        }
                    }
                    z = a;
                    i2 = i3;
                }
                if (z) {
                    i2 = i + 1;
                    entry = zipFile.getEntry("classes" + i2 + ".dex");
                    i = i2;
                } else {
                    throw new IOException("Could not create zip file " + file3.getAbsolutePath() + " for secondary dex (" + i + ")");
                }
            }
            return arrayList;
        } finally {
            try {
                zipFile.close();
            } catch (Throwable e) {
                Log.w("MultiDex", "Failed to close resource", e);
            }
        }
    }

    /* renamed from: a */
    private static void m91a(Context context, long j, long j2, int i) {
        Editor edit = C0026b.m87a(context).edit();
        edit.putLong(Param.TIMESTAMP, j);
        edit.putLong("crc", j2);
        edit.putInt("dex.number", i);
        C0026b.m92a(edit);
    }

    /* renamed from: a */
    private static void m92a(Editor editor) {
        if (f96a != null) {
            try {
                f96a.invoke(editor, new Object[0]);
                return;
            } catch (InvocationTargetException e) {
            } catch (IllegalAccessException e2) {
            }
        }
        editor.commit();
    }

    /* renamed from: a */
    private static void m93a(Closeable closeable) {
        try {
            closeable.close();
        } catch (Throwable e) {
            Log.w("MultiDex", "Failed to close resource", e);
        }
    }

    /* renamed from: a */
    private static void m94a(File file, final String str) {
        C0026b.m100d(file.getParentFile());
        C0026b.m100d(file);
        File[] listFiles = file.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return !file.getName().startsWith(str);
            }
        });
        if (listFiles == null) {
            Log.w("MultiDex", "Failed to list secondary dex dir content (" + file.getPath() + ").");
            return;
        }
        for (File file2 : listFiles) {
            Log.i("MultiDex", "Trying to delete old file " + file2.getPath() + " of size " + file2.length());
            if (file2.delete()) {
                Log.i("MultiDex", "Deleted old file " + file2.getPath());
            } else {
                Log.w("MultiDex", "Failed to delete old file " + file2.getPath());
            }
        }
    }

    /* renamed from: a */
    private static void m95a(ZipFile zipFile, ZipEntry zipEntry, File file, String str) {
        Closeable inputStream = zipFile.getInputStream(zipEntry);
        File createTempFile = File.createTempFile(str, ".zip", file.getParentFile());
        Log.i("MultiDex", "Extracting " + createTempFile.getPath());
        ZipOutputStream zipOutputStream;
        try {
            zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(createTempFile)));
            ZipEntry zipEntry2 = new ZipEntry("classes.dex");
            zipEntry2.setTime(zipEntry.getTime());
            zipOutputStream.putNextEntry(zipEntry2);
            byte[] bArr = new byte[MessagesController.UPDATE_MASK_CHAT_ADMINS];
            for (int read = inputStream.read(bArr); read != -1; read = inputStream.read(bArr)) {
                zipOutputStream.write(bArr, 0, read);
            }
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            Log.i("MultiDex", "Renaming to " + file.getPath());
            if (createTempFile.renameTo(file)) {
                C0026b.m93a(inputStream);
                createTempFile.delete();
                return;
            }
            throw new IOException("Failed to rename \"" + createTempFile.getAbsolutePath() + "\" to \"" + file.getAbsolutePath() + "\"");
        } catch (Throwable th) {
            C0026b.m93a(inputStream);
            createTempFile.delete();
        }
    }

    /* renamed from: a */
    private static boolean m96a(Context context, File file, long j) {
        SharedPreferences a = C0026b.m87a(context);
        return (a.getLong(Param.TIMESTAMP, -1) == C0026b.m98b(file) && a.getLong("crc", -1) == j) ? false : true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    static boolean m97a(java.io.File r4) {
        /*
        r0 = new java.util.zip.ZipFile;	 Catch:{ ZipException -> 0x002b, IOException -> 0x0052 }
        r0.<init>(r4);	 Catch:{ ZipException -> 0x002b, IOException -> 0x0052 }
        r0.close();	 Catch:{ IOException -> 0x000a, ZipException -> 0x002b }
        r0 = 1;
    L_0x0009:
        return r0;
    L_0x000a:
        r0 = move-exception;
        r0 = "MultiDex";
        r1 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x002b, IOException -> 0x0052 }
        r1.<init>();	 Catch:{ ZipException -> 0x002b, IOException -> 0x0052 }
        r2 = "Failed to close zip file: ";
        r1 = r1.append(r2);	 Catch:{ ZipException -> 0x002b, IOException -> 0x0052 }
        r2 = r4.getAbsolutePath();	 Catch:{ ZipException -> 0x002b, IOException -> 0x0052 }
        r1 = r1.append(r2);	 Catch:{ ZipException -> 0x002b, IOException -> 0x0052 }
        r1 = r1.toString();	 Catch:{ ZipException -> 0x002b, IOException -> 0x0052 }
        android.util.Log.w(r0, r1);	 Catch:{ ZipException -> 0x002b, IOException -> 0x0052 }
    L_0x0029:
        r0 = 0;
        goto L_0x0009;
    L_0x002b:
        r0 = move-exception;
        r1 = "MultiDex";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "File ";
        r2 = r2.append(r3);
        r3 = r4.getAbsolutePath();
        r2 = r2.append(r3);
        r3 = " is not a valid zip file.";
        r2 = r2.append(r3);
        r2 = r2.toString();
        android.util.Log.w(r1, r2, r0);
        goto L_0x0029;
    L_0x0052:
        r0 = move-exception;
        r1 = "MultiDex";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Got an IOException trying to open zip file: ";
        r2 = r2.append(r3);
        r3 = r4.getAbsolutePath();
        r2 = r2.append(r3);
        r2 = r2.toString();
        android.util.Log.w(r1, r2, r0);
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.b.b.a(java.io.File):boolean");
    }

    /* renamed from: b */
    private static long m98b(File file) {
        long lastModified = file.lastModified();
        return lastModified == -1 ? lastModified - 1 : lastModified;
    }

    /* renamed from: c */
    private static long m99c(File file) {
        long a = C0028c.m101a(file);
        return a == -1 ? a - 1 : a;
    }

    /* renamed from: d */
    private static void m100d(File file) {
        file.mkdir();
        if (!file.isDirectory()) {
            File parentFile = file.getParentFile();
            if (parentFile == null) {
                Log.e("MultiDex", "Failed to create dir " + file.getPath() + ". Parent file is null.");
            } else {
                Log.e("MultiDex", "Failed to create dir " + file.getPath() + ". parent file is a dir " + parentFile.isDirectory() + ", a file " + parentFile.isFile() + ", exists " + parentFile.exists() + ", readable " + parentFile.canRead() + ", writable " + parentFile.canWrite());
            }
            throw new IOException("Failed to create cache directory " + file.getPath());
        }
    }
}
