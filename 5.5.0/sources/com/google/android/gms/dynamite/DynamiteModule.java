package com.google.android.gms.dynamite;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.IDynamiteLoaderV2.Stub;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.annotation.concurrent.GuardedBy;

public final class DynamiteModule {
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION = new zzd();
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING = new zze();
    public static final VersionPolicy PREFER_HIGHEST_OR_REMOTE_VERSION = new zzf();
    public static final VersionPolicy PREFER_HIGHEST_OR_REMOTE_VERSION_NO_FORCE_STAGING = new zzg();
    public static final VersionPolicy PREFER_LOCAL = new zzc();
    public static final VersionPolicy PREFER_REMOTE = new zzb();
    @GuardedBy("DynamiteModule.class")
    private static Boolean zzabr;
    @GuardedBy("DynamiteModule.class")
    private static IDynamiteLoader zzabs;
    @GuardedBy("DynamiteModule.class")
    private static IDynamiteLoaderV2 zzabt;
    @GuardedBy("DynamiteModule.class")
    private static String zzabu;
    private static final ThreadLocal<zza> zzabv = new ThreadLocal();
    private static final IVersions zzabw = new zza();
    private final Context zzabx;

    @DynamiteApi
    public static class DynamiteLoaderClassLoader {
        @GuardedBy("DynamiteLoaderClassLoader.class")
        public static ClassLoader sClassLoader;
    }

    public static class LoadingException extends Exception {
        private LoadingException(String str) {
            super(str);
        }

        private LoadingException(String str, Throwable th) {
            super(str, th);
        }
    }

    public interface VersionPolicy {

        public interface IVersions {
            int getLocalVersion(Context context, String str);

            int getRemoteVersion(Context context, String str, boolean z);
        }

        public static class SelectionResult {
            public int localVersion = 0;
            public int remoteVersion = 0;
            public int selection = 0;
        }

        SelectionResult selectModule(Context context, String str, IVersions iVersions);
    }

    private static class zza {
        public Cursor zzaby;

        private zza() {
        }
    }

    private static class zzb implements IVersions {
        private final int zzabz;
        private final int zzaca = 0;

        public zzb(int i, int i2) {
            this.zzabz = i;
        }

        public final int getLocalVersion(Context context, String str) {
            return this.zzabz;
        }

        public final int getRemoteVersion(Context context, String str, boolean z) {
            return 0;
        }
    }

    private DynamiteModule(Context context) {
        this.zzabx = (Context) Preconditions.checkNotNull(context);
    }

    public static int getLocalVersion(Context context, String str) {
        String valueOf;
        try {
            Class loadClass = context.getApplicationContext().getClassLoader().loadClass(new StringBuilder(String.valueOf(str).length() + 61).append("com.google.android.gms.dynamite.descriptors.").append(str).append(".ModuleDescriptor").toString());
            Field declaredField = loadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = loadClass.getDeclaredField("MODULE_VERSION");
            if (declaredField.get(null).equals(str)) {
                return declaredField2.getInt(null);
            }
            valueOf = String.valueOf(declaredField.get(null));
            Log.e("DynamiteModule", new StringBuilder((String.valueOf(valueOf).length() + 51) + String.valueOf(str).length()).append("Module descriptor id '").append(valueOf).append("' didn't match expected id '").append(str).append("'").toString());
            return 0;
        } catch (ClassNotFoundException e) {
            Log.w("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 45).append("Local module descriptor class for ").append(str).append(" not found.").toString());
            return 0;
        } catch (Exception e2) {
            valueOf = "DynamiteModule";
            String str2 = "Failed to load module descriptor class: ";
            String valueOf2 = String.valueOf(e2.getMessage());
            Log.e(valueOf, valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
            return 0;
        }
    }

    public static Uri getQueryUri(String str, boolean z) {
        String str2 = z ? ProviderConstants.API_PATH_FORCE_STAGING : ProviderConstants.API_PATH;
        return Uri.parse(new StringBuilder((String.valueOf(str2).length() + 42) + String.valueOf(str).length()).append("content://com.google.android.gms.chimera/").append(str2).append("/").append(str).toString());
    }

    public static int getRemoteVersion(Context context, String str) {
        return getRemoteVersion(context, str, false);
    }

    public static int getRemoteVersion(Context context, String str, boolean z) {
        Object e;
        synchronized (DynamiteModule.class) {
            Boolean bool = zzabr;
            if (bool == null) {
                try {
                    Class loadClass = context.getApplicationContext().getClassLoader().loadClass(DynamiteLoaderClassLoader.class.getName());
                    Field declaredField = loadClass.getDeclaredField("sClassLoader");
                    synchronized (loadClass) {
                        ClassLoader classLoader = (ClassLoader) declaredField.get(null);
                        if (classLoader != null) {
                            if (classLoader == ClassLoader.getSystemClassLoader()) {
                                bool = Boolean.FALSE;
                            } else {
                                try {
                                    zza(classLoader);
                                } catch (LoadingException e2) {
                                }
                                bool = Boolean.TRUE;
                            }
                        } else if ("com.google.android.gms".equals(context.getApplicationContext().getPackageName())) {
                            declaredField.set(null, ClassLoader.getSystemClassLoader());
                            bool = Boolean.FALSE;
                        } else {
                            try {
                                int zzb = zzb(context, str, z);
                                if (zzabu == null || zzabu.isEmpty()) {
                                    return zzb;
                                }
                                ClassLoader zzh = new zzh(zzabu, ClassLoader.getSystemClassLoader());
                                zza(zzh);
                                declaredField.set(null, zzh);
                                zzabr = Boolean.TRUE;
                                return zzb;
                            } catch (LoadingException e3) {
                                declaredField.set(null, ClassLoader.getSystemClassLoader());
                                bool = Boolean.FALSE;
                                zzabr = bool;
                                if (!bool.booleanValue()) {
                                    try {
                                    } catch (LoadingException e4) {
                                        String str2 = "DynamiteModule";
                                        String str3 = "Failed to retrieve remote module version: ";
                                        String valueOf = String.valueOf(e4.getMessage());
                                        Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                                        return 0;
                                    }
                                }
                            }
                        }
                    }
                } catch (ClassNotFoundException e5) {
                    e = e5;
                } catch (IllegalAccessException e6) {
                    e = e6;
                } catch (NoSuchFieldException e7) {
                    e = e7;
                }
            }
        }
        valueOf = String.valueOf(e);
        Log.w("DynamiteModule", new StringBuilder(String.valueOf(valueOf).length() + 30).append("Failed to load module via V2: ").append(valueOf).toString());
        bool = Boolean.FALSE;
        zzabr = bool;
        return !bool.booleanValue() ? zza(context, str, z) : zzb(context, str, z);
    }

    @VisibleForTesting
    public static synchronized Boolean getUseV2ForTesting() {
        Boolean bool;
        synchronized (DynamiteModule.class) {
            bool = zzabr;
        }
        return bool;
    }

    public static DynamiteModule load(Context context, VersionPolicy versionPolicy, String str) {
        SelectionResult selectModule;
        zza zza = (zza) zzabv.get();
        zza zza2 = new zza();
        zzabv.set(zza2);
        DynamiteModule zzd;
        try {
            selectModule = versionPolicy.selectModule(context, str, zzabw);
            Log.i("DynamiteModule", new StringBuilder((String.valueOf(str).length() + 68) + String.valueOf(str).length()).append("Considering local module ").append(str).append(":").append(selectModule.localVersion).append(" and remote module ").append(str).append(":").append(selectModule.remoteVersion).toString());
            if (selectModule.selection == 0 || ((selectModule.selection == -1 && selectModule.localVersion == 0) || (selectModule.selection == 1 && selectModule.remoteVersion == 0))) {
                throw new LoadingException("No acceptable module found. Local version is " + selectModule.localVersion + " and remote version is " + selectModule.remoteVersion + ".");
            } else if (selectModule.selection == -1) {
                zzd = zzd(context, str);
                if (zza2.zzaby != null) {
                    zza2.zzaby.close();
                }
                zzabv.set(zza);
                return zzd;
            } else if (selectModule.selection == 1) {
                zzd = zza(context, str, selectModule.remoteVersion);
                if (zza2.zzaby != null) {
                    zza2.zzaby.close();
                }
                zzabv.set(zza);
                return zzd;
            } else {
                throw new LoadingException("VersionPolicy returned invalid code:" + selectModule.selection);
            }
        } catch (Throwable e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to load remote module: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            if (selectModule.localVersion == 0 || versionPolicy.selectModule(context, str, new zzb(selectModule.localVersion, 0)).selection != -1) {
                throw new LoadingException("Remote load failed. No local fallback found.", e);
            }
            zzd = zzd(context, str);
            if (zza2.zzaby != null) {
                zza2.zzaby.close();
            }
            zzabv.set(zza);
            return zzd;
        } catch (Throwable th) {
            if (zza2.zzaby != null) {
                zza2.zzaby.close();
            }
            zzabv.set(zza);
        }
    }

    public static Cursor queryForDynamiteModule(Context context, String str, boolean z) {
        return context.getContentResolver().query(getQueryUri(str, z), null, null, null, null);
    }

    @VisibleForTesting
    public static synchronized void resetInternalStateForTesting() {
        synchronized (DynamiteModule.class) {
            zzabs = null;
            zzabt = null;
            zzabu = null;
            zzabr = null;
            synchronized (DynamiteLoaderClassLoader.class) {
                DynamiteLoaderClassLoader.sClassLoader = null;
            }
        }
    }

    @VisibleForTesting
    public static synchronized void setUseV2ForTesting(Boolean bool) {
        synchronized (DynamiteModule.class) {
            zzabr = bool;
        }
    }

    private static int zza(Context context, String str, boolean z) {
        IDynamiteLoader zzg = zzg(context);
        if (zzg == null) {
            return 0;
        }
        try {
            return zzg.getModuleVersion2(ObjectWrapper.wrap(context), str, z);
        } catch (RemoteException e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to retrieve remote module version: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return 0;
        }
    }

    private static Context zza(Context context, String str, int i, Cursor cursor, IDynamiteLoaderV2 iDynamiteLoaderV2) {
        try {
            return (Context) ObjectWrapper.unwrap(iDynamiteLoaderV2.loadModule2(ObjectWrapper.wrap(context), str, i, ObjectWrapper.wrap(cursor)));
        } catch (Exception e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to load DynamiteLoader: ";
            String valueOf = String.valueOf(e.toString());
            Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return null;
        }
    }

    private static DynamiteModule zza(Context context, String str, int i) {
        synchronized (DynamiteModule.class) {
            Boolean bool = zzabr;
        }
        if (bool != null) {
            return bool.booleanValue() ? zzc(context, str, i) : zzb(context, str, i);
        } else {
            throw new LoadingException("Failed to determine which loading route to use.");
        }
    }

    @GuardedBy("DynamiteModule.class")
    private static void zza(ClassLoader classLoader) {
        Throwable e;
        try {
            zzabt = Stub.asInterface((IBinder) classLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]));
        } catch (ClassNotFoundException e2) {
            e = e2;
            throw new LoadingException("Failed to instantiate dynamite loader", e);
        } catch (IllegalAccessException e3) {
            e = e3;
            throw new LoadingException("Failed to instantiate dynamite loader", e);
        } catch (InstantiationException e4) {
            e = e4;
            throw new LoadingException("Failed to instantiate dynamite loader", e);
        } catch (InvocationTargetException e5) {
            e = e5;
            throw new LoadingException("Failed to instantiate dynamite loader", e);
        } catch (NoSuchMethodException e6) {
            e = e6;
            throw new LoadingException("Failed to instantiate dynamite loader", e);
        }
    }

    private static int zzb(Context context, String str, boolean z) {
        Cursor queryForDynamiteModule;
        Throwable e;
        try {
            queryForDynamiteModule = queryForDynamiteModule(context, str, z);
            if (queryForDynamiteModule != null) {
                try {
                    if (queryForDynamiteModule.moveToFirst()) {
                        int i = queryForDynamiteModule.getInt(0);
                        if (i > 0) {
                            synchronized (DynamiteModule.class) {
                                zzabu = queryForDynamiteModule.getString(2);
                            }
                            zza zza = (zza) zzabv.get();
                            if (zza != null && zza.zzaby == null) {
                                zza.zzaby = queryForDynamiteModule;
                                queryForDynamiteModule = null;
                            }
                        }
                        if (queryForDynamiteModule != null) {
                            queryForDynamiteModule.close();
                        }
                        return i;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            }
            Log.w("DynamiteModule", "Failed to retrieve remote module version.");
            throw new LoadingException("Failed to connect to dynamite module ContentResolver.");
        } catch (Exception e3) {
            e = e3;
            queryForDynamiteModule = null;
            try {
                if (e instanceof LoadingException) {
                    throw e;
                }
                throw new LoadingException("V2 version check failed", e);
            } catch (Throwable th) {
                e = th;
                if (queryForDynamiteModule != null) {
                    queryForDynamiteModule.close();
                }
                throw e;
            }
        } catch (Throwable th2) {
            e = th2;
            queryForDynamiteModule = null;
            if (queryForDynamiteModule != null) {
                queryForDynamiteModule.close();
            }
            throw e;
        }
    }

    private static DynamiteModule zzb(Context context, String str, int i) {
        Log.i("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 51).append("Selected remote version of ").append(str).append(", version >= ").append(i).toString());
        IDynamiteLoader zzg = zzg(context);
        if (zzg == null) {
            throw new LoadingException("Failed to create IDynamiteLoader.");
        }
        try {
            IObjectWrapper createModuleContext = zzg.createModuleContext(ObjectWrapper.wrap(context), str, i);
            if (ObjectWrapper.unwrap(createModuleContext) != null) {
                return new DynamiteModule((Context) ObjectWrapper.unwrap(createModuleContext));
            }
            throw new LoadingException("Failed to load remote module.");
        } catch (Throwable e) {
            throw new LoadingException("Failed to load remote module.", e);
        }
    }

    private static DynamiteModule zzc(Context context, String str, int i) {
        Log.i("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 51).append("Selected remote version of ").append(str).append(", version >= ").append(i).toString());
        synchronized (DynamiteModule.class) {
            IDynamiteLoaderV2 iDynamiteLoaderV2 = zzabt;
        }
        if (iDynamiteLoaderV2 == null) {
            throw new LoadingException("DynamiteLoaderV2 was not cached.");
        }
        zza zza = (zza) zzabv.get();
        if (zza == null || zza.zzaby == null) {
            throw new LoadingException("No result cursor");
        }
        Context zza2 = zza(context.getApplicationContext(), str, i, zza.zzaby, iDynamiteLoaderV2);
        if (zza2 != null) {
            return new DynamiteModule(zza2);
        }
        throw new LoadingException("Failed to get module context");
    }

    private static DynamiteModule zzd(Context context, String str) {
        String str2 = "DynamiteModule";
        String str3 = "Selected local version of ";
        String valueOf = String.valueOf(str);
        Log.i(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        return new DynamiteModule(context.getApplicationContext());
    }

    private static IDynamiteLoader zzg(Context context) {
        synchronized (DynamiteModule.class) {
            IDynamiteLoader iDynamiteLoader;
            if (zzabs != null) {
                iDynamiteLoader = zzabs;
                return iDynamiteLoader;
            } else if (GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(context) != 0) {
                return null;
            } else {
                try {
                    iDynamiteLoader = IDynamiteLoader.Stub.asInterface((IBinder) context.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance());
                    if (iDynamiteLoader != null) {
                        zzabs = iDynamiteLoader;
                        return iDynamiteLoader;
                    }
                } catch (Exception e) {
                    String str = "DynamiteModule";
                    String str2 = "Failed to load IDynamiteLoader from GmsCore: ";
                    String valueOf = String.valueOf(e.getMessage());
                    Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                    return null;
                }
            }
        }
    }

    public final Context getModuleContext() {
        return this.zzabx;
    }

    public final IBinder instantiate(String str) {
        Throwable e;
        String str2;
        String valueOf;
        try {
            return (IBinder) this.zzabx.getClassLoader().loadClass(str).newInstance();
        } catch (ClassNotFoundException e2) {
            e = e2;
            str2 = "Failed to instantiate module class: ";
            valueOf = String.valueOf(str);
            throw new LoadingException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), e);
        } catch (InstantiationException e3) {
            e = e3;
            str2 = "Failed to instantiate module class: ";
            valueOf = String.valueOf(str);
            if (valueOf.length() != 0) {
            }
            throw new LoadingException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), e);
        } catch (IllegalAccessException e4) {
            e = e4;
            str2 = "Failed to instantiate module class: ";
            valueOf = String.valueOf(str);
            if (valueOf.length() != 0) {
            }
            throw new LoadingException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), e);
        }
    }
}
