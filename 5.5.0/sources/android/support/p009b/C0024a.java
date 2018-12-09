package android.support.p009b;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.util.Log;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

/* renamed from: android.support.b.a */
public final class C0024a {
    /* renamed from: a */
    private static final String f92a = ("code_cache" + File.separator + "secondary-dexes");
    /* renamed from: b */
    private static final Set<String> f93b = new HashSet();
    /* renamed from: c */
    private static final boolean f94c = C0024a.m80a(System.getProperty("java.vm.version"));

    /* renamed from: android.support.b.a$a */
    private static final class C0021a {
        /* renamed from: a */
        private static Object[] m68a(Object obj, ArrayList<File> arrayList, File file) {
            return (Object[]) C0024a.m84b(obj, "makeDexElements", ArrayList.class, File.class).invoke(obj, new Object[]{arrayList, file});
        }

        /* renamed from: b */
        private static void m69b(ClassLoader classLoader, List<File> list, File file) {
            Object obj = C0024a.m83b(classLoader, "pathList").get(classLoader);
            C0024a.m85b(obj, "dexElements", C0021a.m68a(obj, new ArrayList(list), file));
        }
    }

    /* renamed from: android.support.b.a$b */
    private static final class C0022b {
        /* renamed from: a */
        private static Object[] m71a(Object obj, ArrayList<File> arrayList, File file, ArrayList<IOException> arrayList2) {
            return (Object[]) C0024a.m84b(obj, "makeDexElements", ArrayList.class, File.class, ArrayList.class).invoke(obj, new Object[]{arrayList, file, arrayList2});
        }

        /* renamed from: b */
        private static void m72b(ClassLoader classLoader, List<File> list, File file) {
            Object obj = C0024a.m83b(classLoader, "pathList").get(classLoader);
            ArrayList arrayList = new ArrayList();
            C0024a.m85b(obj, "dexElements", C0022b.m71a(obj, new ArrayList(list), file, arrayList));
            if (arrayList.size() > 0) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Log.w("MultiDex", "Exception in makeDexElement", (IOException) it.next());
                }
                Field a = C0024a.m83b(classLoader, "dexElementsSuppressedExceptions");
                IOException[] iOExceptionArr = (IOException[]) a.get(classLoader);
                if (iOExceptionArr == null) {
                    obj = (IOException[]) arrayList.toArray(new IOException[arrayList.size()]);
                } else {
                    Object obj2 = new IOException[(arrayList.size() + iOExceptionArr.length)];
                    arrayList.toArray(obj2);
                    System.arraycopy(iOExceptionArr, 0, obj2, arrayList.size(), iOExceptionArr.length);
                    obj = obj2;
                }
                a.set(classLoader, obj);
            }
        }
    }

    /* renamed from: android.support.b.a$c */
    private static final class C0023c {
        /* renamed from: b */
        private static void m74b(ClassLoader classLoader, List<File> list) {
            int size = list.size();
            Field a = C0024a.m83b(classLoader, "path");
            StringBuilder stringBuilder = new StringBuilder((String) a.get(classLoader));
            Object[] objArr = new String[size];
            Object[] objArr2 = new File[size];
            Object[] objArr3 = new ZipFile[size];
            Object[] objArr4 = new DexFile[size];
            ListIterator listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                File file = (File) listIterator.next();
                String absolutePath = file.getAbsolutePath();
                stringBuilder.append(':').append(absolutePath);
                int previousIndex = listIterator.previousIndex();
                objArr[previousIndex] = absolutePath;
                objArr2[previousIndex] = file;
                objArr3[previousIndex] = new ZipFile(file);
                objArr4[previousIndex] = DexFile.loadDex(absolutePath, absolutePath + ".dex", 0);
            }
            a.set(classLoader, stringBuilder.toString());
            C0024a.m85b((Object) classLoader, "mPaths", objArr);
            C0024a.m85b((Object) classLoader, "mFiles", objArr2);
            C0024a.m85b((Object) classLoader, "mZips", objArr3);
            C0024a.m85b((Object) classLoader, "mDexs", objArr4);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    public static void m77a(android.content.Context r6) {
        /*
        r4 = 20;
        r3 = 4;
        r0 = "MultiDex";
        r1 = "install";
        android.util.Log.i(r0, r1);
        r0 = f94c;
        if (r0 == 0) goto L_0x001a;
    L_0x0010:
        r0 = "MultiDex";
        r1 = "VM has multidex support, MultiDex support library is disabled.";
        android.util.Log.i(r0, r1);
    L_0x0019:
        return;
    L_0x001a:
        r0 = android.os.Build.VERSION.SDK_INT;
        if (r0 >= r3) goto L_0x004c;
    L_0x001e:
        r0 = new java.lang.RuntimeException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Multi dex installation failed. SDK ";
        r1 = r1.append(r2);
        r2 = android.os.Build.VERSION.SDK_INT;
        r1 = r1.append(r2);
        r2 = " is unsupported. Min SDK version is ";
        r1 = r1.append(r2);
        r1 = r1.append(r3);
        r2 = ".";
        r1 = r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
    L_0x004c:
        r0 = android.support.p009b.C0024a.m82b(r6);	 Catch:{ Exception -> 0x0064 }
        if (r0 == 0) goto L_0x0019;
    L_0x0052:
        r1 = f93b;	 Catch:{ Exception -> 0x0064 }
        monitor-enter(r1);	 Catch:{ Exception -> 0x0064 }
        r2 = r0.sourceDir;	 Catch:{ all -> 0x0061 }
        r3 = f93b;	 Catch:{ all -> 0x0061 }
        r3 = r3.contains(r2);	 Catch:{ all -> 0x0061 }
        if (r3 == 0) goto L_0x0093;
    L_0x005f:
        monitor-exit(r1);	 Catch:{ all -> 0x0061 }
        goto L_0x0019;
    L_0x0061:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0061 }
        throw r0;	 Catch:{ Exception -> 0x0064 }
    L_0x0064:
        r0 = move-exception;
        r1 = "MultiDex";
        r2 = "Multidex installation failure";
        android.util.Log.e(r1, r2, r0);
        r1 = new java.lang.RuntimeException;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Multi dex installation failed (";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r2 = ").";
        r0 = r0.append(r2);
        r0 = r0.toString();
        r1.<init>(r0);
        throw r1;
    L_0x0093:
        r3 = f93b;	 Catch:{ all -> 0x0061 }
        r3.add(r2);	 Catch:{ all -> 0x0061 }
        r2 = android.os.Build.VERSION.SDK_INT;	 Catch:{ all -> 0x0061 }
        if (r2 <= r4) goto L_0x00ec;
    L_0x009c:
        r2 = "MultiDex";
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0061 }
        r3.<init>();	 Catch:{ all -> 0x0061 }
        r4 = "MultiDex is not guaranteed to work in SDK version ";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0061 }
        r4 = android.os.Build.VERSION.SDK_INT;	 Catch:{ all -> 0x0061 }
        r3 = r3.append(r4);	 Catch:{ all -> 0x0061 }
        r4 = ": SDK version higher than ";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0061 }
        r4 = 20;
        r3 = r3.append(r4);	 Catch:{ all -> 0x0061 }
        r4 = " should be backed by ";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0061 }
        r4 = "runtime with built-in multidex capabilty but it's not the ";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0061 }
        r4 = "case here: java.vm.version=\"";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0061 }
        r4 = "java.vm.version";
        r4 = java.lang.System.getProperty(r4);	 Catch:{ all -> 0x0061 }
        r3 = r3.append(r4);	 Catch:{ all -> 0x0061 }
        r4 = "\"";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0061 }
        r3 = r3.toString();	 Catch:{ all -> 0x0061 }
        android.util.Log.w(r2, r3);	 Catch:{ all -> 0x0061 }
    L_0x00ec:
        r2 = r6.getClassLoader();	 Catch:{ RuntimeException -> 0x00fe }
        if (r2 != 0) goto L_0x010b;
    L_0x00f2:
        r0 = "MultiDex";
        r2 = "Context class loader is null. Must be running in test mode. Skip patching.";
        android.util.Log.e(r0, r2);	 Catch:{ all -> 0x0061 }
        monitor-exit(r1);	 Catch:{ all -> 0x0061 }
        goto L_0x0019;
    L_0x00fe:
        r0 = move-exception;
        r2 = "MultiDex";
        r3 = "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.";
        android.util.Log.w(r2, r3, r0);	 Catch:{ all -> 0x0061 }
        monitor-exit(r1);	 Catch:{ all -> 0x0061 }
        goto L_0x0019;
    L_0x010b:
        android.support.p009b.C0024a.m86c(r6);	 Catch:{ Throwable -> 0x0131 }
    L_0x010e:
        r3 = new java.io.File;	 Catch:{ all -> 0x0061 }
        r4 = r0.dataDir;	 Catch:{ all -> 0x0061 }
        r5 = f92a;	 Catch:{ all -> 0x0061 }
        r3.<init>(r4, r5);	 Catch:{ all -> 0x0061 }
        r4 = 0;
        r4 = android.support.p009b.C0026b.m88a(r6, r0, r3, r4);	 Catch:{ all -> 0x0061 }
        r5 = android.support.p009b.C0024a.m81a(r4);	 Catch:{ all -> 0x0061 }
        if (r5 == 0) goto L_0x013c;
    L_0x0122:
        android.support.p009b.C0024a.m78a(r2, r3, r4);	 Catch:{ all -> 0x0061 }
    L_0x0125:
        monitor-exit(r1);	 Catch:{ all -> 0x0061 }
        r0 = "MultiDex";
        r1 = "install done";
        android.util.Log.i(r0, r1);
        goto L_0x0019;
    L_0x0131:
        r3 = move-exception;
        r4 = "MultiDex";
        r5 = "Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning.";
        android.util.Log.w(r4, r5, r3);	 Catch:{ all -> 0x0061 }
        goto L_0x010e;
    L_0x013c:
        r4 = "MultiDex";
        r5 = "Files were not valid zip files.  Forcing a reload.";
        android.util.Log.w(r4, r5);	 Catch:{ all -> 0x0061 }
        r4 = 1;
        r0 = android.support.p009b.C0026b.m88a(r6, r0, r3, r4);	 Catch:{ all -> 0x0061 }
        r4 = android.support.p009b.C0024a.m81a(r0);	 Catch:{ all -> 0x0061 }
        if (r4 == 0) goto L_0x0154;
    L_0x0150:
        android.support.p009b.C0024a.m78a(r2, r3, r0);	 Catch:{ all -> 0x0061 }
        goto L_0x0125;
    L_0x0154:
        r0 = new java.lang.RuntimeException;	 Catch:{ all -> 0x0061 }
        r2 = "Zip files were not valid.";
        r0.<init>(r2);	 Catch:{ all -> 0x0061 }
        throw r0;	 Catch:{ all -> 0x0061 }
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.b.a.a(android.content.Context):void");
    }

    /* renamed from: a */
    private static void m78a(ClassLoader classLoader, File file, List<File> list) {
        if (!list.isEmpty()) {
            if (VERSION.SDK_INT >= 19) {
                C0022b.m72b(classLoader, list, file);
            } else if (VERSION.SDK_INT >= 14) {
                C0021a.m69b(classLoader, list, file);
            } else {
                C0023c.m74b(classLoader, list);
            }
        }
    }

    /* renamed from: a */
    static boolean m80a(String str) {
        boolean z = false;
        if (str != null) {
            Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(str);
            if (matcher.matches()) {
                try {
                    int parseInt = Integer.parseInt(matcher.group(1));
                    int parseInt2 = Integer.parseInt(matcher.group(2));
                    if (parseInt > 2 || (parseInt == 2 && parseInt2 >= 1)) {
                        z = true;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
        Log.i("MultiDex", "VM with version " + str + (z ? " has multidex support" : " does not have multidex support"));
        return z;
    }

    /* renamed from: a */
    private static boolean m81a(List<File> list) {
        for (File a : list) {
            if (!C0026b.m97a(a)) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: b */
    private static ApplicationInfo m82b(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            return (packageManager == null || packageName == null) ? null : packageManager.getApplicationInfo(packageName, 128);
        } catch (Throwable e) {
            Log.w("MultiDex", "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e);
            return null;
        }
    }

    /* renamed from: b */
    private static Field m83b(Object obj, String str) {
        Class cls = obj.getClass();
        while (cls != null) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                return declaredField;
            } catch (NoSuchFieldException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + str + " not found in " + obj.getClass());
    }

    /* renamed from: b */
    private static Method m84b(Object obj, String str, Class<?>... clsArr) {
        Class cls = obj.getClass();
        while (cls != null) {
            try {
                Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
                if (!declaredMethod.isAccessible()) {
                    declaredMethod.setAccessible(true);
                }
                return declaredMethod;
            } catch (NoSuchMethodException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + str + " with parameters " + Arrays.asList(clsArr) + " not found in " + obj.getClass());
    }

    /* renamed from: b */
    private static void m85b(Object obj, String str, Object[] objArr) {
        Field b = C0024a.m83b(obj, str);
        Object[] objArr2 = (Object[]) b.get(obj);
        Object[] objArr3 = (Object[]) Array.newInstance(objArr2.getClass().getComponentType(), objArr2.length + objArr.length);
        System.arraycopy(objArr2, 0, objArr3, 0, objArr2.length);
        System.arraycopy(objArr, 0, objArr3, objArr2.length, objArr.length);
        b.set(obj, objArr3);
    }

    /* renamed from: c */
    private static void m86c(Context context) {
        File file = new File(context.getFilesDir(), "secondary-dexes");
        if (file.isDirectory()) {
            Log.i("MultiDex", "Clearing old secondary dex dir (" + file.getPath() + ").");
            File[] listFiles = file.listFiles();
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
            if (file.delete()) {
                Log.i("MultiDex", "Deleted old secondary dex dir " + file.getPath());
            } else {
                Log.w("MultiDex", "Failed to delete secondary dex dir " + file.getPath());
            }
        }
    }
}
