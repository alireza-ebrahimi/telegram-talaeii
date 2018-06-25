package com.google.android.gms.dynamite;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.common.zzf;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Hide
public final class DynamiteModule {
    private static Boolean zzhdc;
    private static zzk zzhdd;
    private static zzm zzhde;
    private static String zzhdf;
    private static final ThreadLocal<zza> zzhdg = new ThreadLocal();
    private static final zzi zzhdh = new zza();
    public static final zzd zzhdi = new zzb();
    private static zzd zzhdj = new zzc();
    public static final zzd zzhdk = new zzd();
    public static final zzd zzhdl = new zze();
    public static final zzd zzhdm = new zzf();
    public static final zzd zzhdn = new zzg();
    private final Context zzhdo;

    @DynamiteApi
    public static class DynamiteLoaderClassLoader {
        public static ClassLoader sClassLoader;
    }

    static class zza {
        public Cursor zzhdp;

        private zza() {
        }
    }

    static class zzb implements zzi {
        private final int zzhdq;
        private final int zzhdr = 0;

        public zzb(int i, int i2) {
            this.zzhdq = i;
        }

        public final int zzc(Context context, String str, boolean z) {
            return 0;
        }

        public final int zzx(Context context, String str) {
            return this.zzhdq;
        }
    }

    public static class zzc extends Exception {
        private zzc(String str) {
            super(str);
        }

        private zzc(String str, Throwable th) {
            super(str, th);
        }
    }

    public interface zzd {
        zzj zza(Context context, String str, zzi zzi) throws zzc;
    }

    private DynamiteModule(Context context) {
        this.zzhdo = (Context) zzbq.checkNotNull(context);
    }

    private static Context zza(Context context, String str, int i, Cursor cursor, zzm zzm) {
        try {
            return (Context) zzn.zzy(zzm.zza(zzn.zzz(context), str, i, zzn.zzz(cursor)));
        } catch (Exception e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to load DynamiteLoader: ";
            String valueOf = String.valueOf(e.toString());
            Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return null;
        }
    }

    public static DynamiteModule zza(Context context, zzd zzd, String str) throws zzc {
        zza zza = (zza) zzhdg.get();
        zza zza2 = new zza();
        zzhdg.set(zza2);
        zzj zza3;
        DynamiteModule zzz;
        try {
            zza3 = zzd.zza(context, str, zzhdh);
            Log.i("DynamiteModule", new StringBuilder((String.valueOf(str).length() + 68) + String.valueOf(str).length()).append("Considering local module ").append(str).append(":").append(zza3.zzhds).append(" and remote module ").append(str).append(":").append(zza3.zzhdt).toString());
            if (zza3.zzhdu == 0 || ((zza3.zzhdu == -1 && zza3.zzhds == 0) || (zza3.zzhdu == 1 && zza3.zzhdt == 0))) {
                throw new zzc("No acceptable module found. Local version is " + zza3.zzhds + " and remote version is " + zza3.zzhdt + ".");
            } else if (zza3.zzhdu == -1) {
                zzz = zzz(context, str);
                if (zza2.zzhdp != null) {
                    zza2.zzhdp.close();
                }
                zzhdg.set(zza);
                return zzz;
            } else if (zza3.zzhdu == 1) {
                zzz = zza(context, str, zza3.zzhdt);
                if (zza2.zzhdp != null) {
                    zza2.zzhdp.close();
                }
                zzhdg.set(zza);
                return zzz;
            } else {
                throw new zzc("VersionPolicy returned invalid code:" + zza3.zzhdu);
            }
        } catch (Throwable e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to load remote module: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            if (zza3.zzhds == 0 || zzd.zza(context, str, new zzb(zza3.zzhds, 0)).zzhdu != -1) {
                throw new zzc("Remote load failed. No local fallback found.", e);
            }
            zzz = zzz(context, str);
            if (zza2.zzhdp != null) {
                zza2.zzhdp.close();
            }
            zzhdg.set(zza);
            return zzz;
        } catch (Throwable th) {
            if (zza2.zzhdp != null) {
                zza2.zzhdp.close();
            }
            zzhdg.set(zza);
        }
    }

    private static DynamiteModule zza(Context context, String str, int i) throws zzc {
        synchronized (DynamiteModule.class) {
            Boolean bool = zzhdc;
        }
        if (bool != null) {
            return bool.booleanValue() ? zzc(context, str, i) : zzb(context, str, i);
        } else {
            throw new zzc("Failed to determine which loading route to use.");
        }
    }

    private static void zza(ClassLoader classLoader) throws zzc {
        Throwable e;
        try {
            zzm zzm;
            IBinder iBinder = (IBinder) classLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]);
            if (iBinder == null) {
                zzm = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
                if (queryLocalInterface instanceof zzm) {
                    zzm = (zzm) queryLocalInterface;
                } else {
                    Object zzn = new zzn(iBinder);
                }
            }
            zzhde = zzm;
        } catch (ClassNotFoundException e2) {
            e = e2;
            throw new zzc("Failed to instantiate dynamite loader", e);
        } catch (IllegalAccessException e3) {
            e = e3;
            throw new zzc("Failed to instantiate dynamite loader", e);
        } catch (InstantiationException e4) {
            e = e4;
            throw new zzc("Failed to instantiate dynamite loader", e);
        } catch (InvocationTargetException e5) {
            e = e5;
            throw new zzc("Failed to instantiate dynamite loader", e);
        } catch (NoSuchMethodException e6) {
            e = e6;
            throw new zzc("Failed to instantiate dynamite loader", e);
        }
    }

    private static DynamiteModule zzb(Context context, String str, int i) throws zzc {
        Log.i("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 51).append("Selected remote version of ").append(str).append(", version >= ").append(i).toString());
        zzk zzdh = zzdh(context);
        if (zzdh == null) {
            throw new zzc("Failed to create IDynamiteLoader.");
        }
        try {
            IObjectWrapper zza = zzdh.zza(zzn.zzz(context), str, i);
            if (zzn.zzy(zza) != null) {
                return new DynamiteModule((Context) zzn.zzy(zza));
            }
            throw new zzc("Failed to load remote module.");
        } catch (Throwable e) {
            throw new zzc("Failed to load remote module.", e);
        }
    }

    public static int zzc(Context context, String str, boolean z) {
        Object e;
        synchronized (DynamiteModule.class) {
            Boolean bool = zzhdc;
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
                                } catch (zzc e2) {
                                }
                                bool = Boolean.TRUE;
                            }
                        } else if ("com.google.android.gms".equals(context.getApplicationContext().getPackageName())) {
                            declaredField.set(null, ClassLoader.getSystemClassLoader());
                            bool = Boolean.FALSE;
                        } else {
                            try {
                                int zze = zze(context, str, z);
                                if (zzhdf == null || zzhdf.isEmpty()) {
                                    return zze;
                                }
                                ClassLoader zzh = new zzh(zzhdf, ClassLoader.getSystemClassLoader());
                                zza(zzh);
                                declaredField.set(null, zzh);
                                zzhdc = Boolean.TRUE;
                                return zze;
                            } catch (zzc e3) {
                                declaredField.set(null, ClassLoader.getSystemClassLoader());
                                bool = Boolean.FALSE;
                                zzhdc = bool;
                                if (!bool.booleanValue()) {
                                    try {
                                    } catch (zzc e4) {
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
        zzhdc = bool;
        return !bool.booleanValue() ? zzd(context, str, z) : zze(context, str, z);
    }

    private static DynamiteModule zzc(Context context, String str, int i) throws zzc {
        Log.i("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 51).append("Selected remote version of ").append(str).append(", version >= ").append(i).toString());
        synchronized (DynamiteModule.class) {
            zzm zzm = zzhde;
        }
        if (zzm == null) {
            throw new zzc("DynamiteLoaderV2 was not cached.");
        }
        zza zza = (zza) zzhdg.get();
        if (zza == null || zza.zzhdp == null) {
            throw new zzc("No result cursor");
        }
        Context zza2 = zza(context.getApplicationContext(), str, i, zza.zzhdp, zzm);
        if (zza2 != null) {
            return new DynamiteModule(zza2);
        }
        throw new zzc("Failed to get module context");
    }

    private static int zzd(Context context, String str, boolean z) {
        zzk zzdh = zzdh(context);
        if (zzdh == null) {
            return 0;
        }
        try {
            return zzdh.zza(zzn.zzz(context), str, z);
        } catch (RemoteException e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to retrieve remote module version: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return 0;
        }
    }

    private static zzk zzdh(Context context) {
        synchronized (DynamiteModule.class) {
            zzk zzk;
            if (zzhdd != null) {
                zzk = zzhdd;
                return zzk;
            } else if (zzf.zzahf().isGooglePlayServicesAvailable(context) != 0) {
                return null;
            } else {
                try {
                    IBinder iBinder = (IBinder) context.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance();
                    if (iBinder == null) {
                        zzk = null;
                    } else {
                        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                        if (queryLocalInterface instanceof zzk) {
                            zzk = (zzk) queryLocalInterface;
                        } else {
                            Object zzl = new zzl(iBinder);
                        }
                    }
                    if (zzk != null) {
                        zzhdd = zzk;
                        return zzk;
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

    private static int zze(Context context, String str, boolean z) throws zzc {
        Cursor query;
        Throwable e;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            String str2 = z ? "api_force_staging" : "api";
            query = contentResolver.query(Uri.parse(new StringBuilder((String.valueOf(str2).length() + 42) + String.valueOf(str).length()).append("content://com.google.android.gms.chimera/").append(str2).append("/").append(str).toString()), null, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        int i = query.getInt(0);
                        if (i > 0) {
                            synchronized (DynamiteModule.class) {
                                zzhdf = query.getString(2);
                            }
                            zza zza = (zza) zzhdg.get();
                            if (zza != null && zza.zzhdp == null) {
                                zza.zzhdp = query;
                                query = null;
                            }
                        }
                        if (query != null) {
                            query.close();
                        }
                        return i;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            }
            Log.w("DynamiteModule", "Failed to retrieve remote module version.");
            throw new zzc("Failed to connect to dynamite module ContentResolver.");
        } catch (Exception e3) {
            e = e3;
            query = null;
            try {
                if (e instanceof zzc) {
                    throw e;
                }
                throw new zzc("V2 version check failed", e);
            } catch (Throwable th) {
                e = th;
                if (query != null) {
                    query.close();
                }
                throw e;
            }
        } catch (Throwable th2) {
            e = th2;
            query = null;
            if (query != null) {
                query.close();
            }
            throw e;
        }
    }

    public static int zzx(Context context, String str) {
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

    public static int zzy(Context context, String str) {
        return zzc(context, str, false);
    }

    private static DynamiteModule zzz(Context context, String str) {
        String str2 = "DynamiteModule";
        String str3 = "Selected local version of ";
        String valueOf = String.valueOf(str);
        Log.i(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        return new DynamiteModule(context.getApplicationContext());
    }

    public final Context zzarl() {
        return this.zzhdo;
    }

    public final IBinder zzhk(String str) throws zzc {
        Throwable e;
        String str2;
        String valueOf;
        try {
            return (IBinder) this.zzhdo.getClassLoader().loadClass(str).newInstance();
        } catch (ClassNotFoundException e2) {
            e = e2;
            str2 = "Failed to instantiate module class: ";
            valueOf = String.valueOf(str);
            throw new zzc(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), e);
        } catch (InstantiationException e3) {
            e = e3;
            str2 = "Failed to instantiate module class: ";
            valueOf = String.valueOf(str);
            if (valueOf.length() != 0) {
            }
            throw new zzc(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), e);
        } catch (IllegalAccessException e4) {
            e = e4;
            str2 = "Failed to instantiate module class: ";
            valueOf = String.valueOf(str);
            if (valueOf.length() != 0) {
            }
            throw new zzc(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), e);
        }
    }
}
