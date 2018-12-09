package com.google.android.gms.internal.gcm;

import java.io.PrintStream;

public final class zzf {
    private static final zzg zzdc;
    private static final int zzdd;

    static final class zzd extends zzg {
        zzd() {
        }

        public final void zzd(Throwable th, Throwable th2) {
        }
    }

    static {
        Integer zzy;
        zzg zzk;
        Throwable th;
        PrintStream printStream;
        String name;
        try {
            zzy = zzy();
            if (zzy != null) {
                try {
                    if (zzy.intValue() >= 19) {
                        zzk = new zzk();
                        zzdc = zzk;
                        zzdd = zzy != null ? 1 : zzy.intValue();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    printStream = System.err;
                    name = zzd.class.getName();
                    printStream.println(new StringBuilder(String.valueOf(name).length() + 132).append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ").append(name).append("will be used. The error is: ").toString());
                    th.printStackTrace(System.err);
                    zzk = new zzd();
                    zzdc = zzk;
                    if (zzy != null) {
                    }
                    zzdd = zzy != null ? 1 : zzy.intValue();
                }
            }
            zzk = (!Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ? 1 : null) != null ? new zzj() : new zzd();
        } catch (Throwable th3) {
            Throwable th4 = th3;
            zzy = null;
            th = th4;
            printStream = System.err;
            name = zzd.class.getName();
            printStream.println(new StringBuilder(String.valueOf(name).length() + 132).append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ").append(name).append("will be used. The error is: ").toString());
            th.printStackTrace(System.err);
            zzk = new zzd();
            zzdc = zzk;
            if (zzy != null) {
            }
            zzdd = zzy != null ? 1 : zzy.intValue();
        }
        zzdc = zzk;
        if (zzy != null) {
        }
        zzdd = zzy != null ? 1 : zzy.intValue();
    }

    public static void zzd(Throwable th, Throwable th2) {
        zzdc.zzd(th, th2);
    }

    private static Integer zzy() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }
}
