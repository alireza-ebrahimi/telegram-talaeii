package com.google.android.gms.internal.measurement;

import java.io.PrintStream;

public final class zzxf {
    private static final zzxg zzboi;
    private static final int zzboj;

    static final class zza extends zzxg {
        zza() {
        }

        public final void zza(Throwable th, PrintStream printStream) {
            th.printStackTrace(printStream);
        }
    }

    static {
        Integer zzsm;
        zzxg zzxk;
        Throwable th;
        PrintStream printStream;
        String name;
        try {
            zzsm = zzsm();
            if (zzsm != null) {
                try {
                    if (zzsm.intValue() >= 19) {
                        zzxk = new zzxk();
                        zzboi = zzxk;
                        zzboj = zzsm != null ? 1 : zzsm.intValue();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    printStream = System.err;
                    name = zza.class.getName();
                    printStream.println(new StringBuilder(String.valueOf(name).length() + 132).append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ").append(name).append("will be used. The error is: ").toString());
                    th.printStackTrace(System.err);
                    zzxk = new zza();
                    zzboi = zzxk;
                    if (zzsm != null) {
                    }
                    zzboj = zzsm != null ? 1 : zzsm.intValue();
                }
            }
            zzxk = (!Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ? 1 : null) != null ? new zzxj() : new zza();
        } catch (Throwable th3) {
            Throwable th4 = th3;
            zzsm = null;
            th = th4;
            printStream = System.err;
            name = zza.class.getName();
            printStream.println(new StringBuilder(String.valueOf(name).length() + 132).append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ").append(name).append("will be used. The error is: ").toString());
            th.printStackTrace(System.err);
            zzxk = new zza();
            zzboi = zzxk;
            if (zzsm != null) {
            }
            zzboj = zzsm != null ? 1 : zzsm.intValue();
        }
        zzboi = zzxk;
        if (zzsm != null) {
        }
        zzboj = zzsm != null ? 1 : zzsm.intValue();
    }

    public static void zza(Throwable th, PrintStream printStream) {
        zzboi.zza(th, printStream);
    }

    private static Integer zzsm() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }
}
