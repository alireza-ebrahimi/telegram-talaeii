package com.google.android.gms.internal.firebase_messaging;

import java.io.PrintStream;

public final class zzh {
    private static final zzi zze;
    private static final int zzf;

    static final class zza extends zzi {
        zza() {
        }

        public final void zza(Throwable th, Throwable th2) {
        }
    }

    static {
        Integer zza;
        zzi zzm;
        Throwable th;
        PrintStream printStream;
        String name;
        try {
            zza = zza();
            if (zza != null) {
                try {
                    if (zza.intValue() >= 19) {
                        zzm = new zzm();
                        zze = zzm;
                        zzf = zza != null ? 1 : zza.intValue();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    printStream = System.err;
                    name = zza.class.getName();
                    printStream.println(new StringBuilder(String.valueOf(name).length() + 132).append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ").append(name).append("will be used. The error is: ").toString());
                    th.printStackTrace(System.err);
                    zzm = new zza();
                    zze = zzm;
                    if (zza != null) {
                    }
                    zzf = zza != null ? 1 : zza.intValue();
                }
            }
            zzm = (!Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ? 1 : null) != null ? new zzl() : new zza();
        } catch (Throwable th3) {
            Throwable th4 = th3;
            zza = null;
            th = th4;
            printStream = System.err;
            name = zza.class.getName();
            printStream.println(new StringBuilder(String.valueOf(name).length() + 132).append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ").append(name).append("will be used. The error is: ").toString());
            th.printStackTrace(System.err);
            zzm = new zza();
            zze = zzm;
            if (zza != null) {
            }
            zzf = zza != null ? 1 : zza.intValue();
        }
        zze = zzm;
        if (zza != null) {
        }
        zzf = zza != null ? 1 : zza.intValue();
    }

    private static Integer zza() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    public static void zza(Throwable th, Throwable th2) {
        zze.zza(th, th2);
    }
}
