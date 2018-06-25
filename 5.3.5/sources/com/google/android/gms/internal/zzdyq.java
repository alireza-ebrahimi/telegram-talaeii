package com.google.android.gms.internal;

import java.io.PrintStream;
import java.io.PrintWriter;

public final class zzdyq {
    private static zzdyr zzmmf;
    private static int zzmmg;

    static final class zza extends zzdyr {
        zza() {
        }

        public final void zza(Throwable th, PrintStream printStream) {
            th.printStackTrace(printStream);
        }

        public final void zza(Throwable th, PrintWriter printWriter) {
            th.printStackTrace(printWriter);
        }
    }

    static {
        Integer zzbss;
        zzdyr zzdyv;
        Throwable th;
        PrintStream printStream;
        String name;
        try {
            zzbss = zzbss();
            if (zzbss != null) {
                try {
                    if (zzbss.intValue() >= 19) {
                        zzdyv = new zzdyv();
                        zzmmf = zzdyv;
                        zzmmg = zzbss != null ? 1 : zzbss.intValue();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    printStream = System.err;
                    name = zza.class.getName();
                    printStream.println(new StringBuilder(String.valueOf(name).length() + 132).append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ").append(name).append("will be used. The error is: ").toString());
                    th.printStackTrace(System.err);
                    zzdyv = new zza();
                    zzmmf = zzdyv;
                    if (zzbss != null) {
                    }
                    zzmmg = zzbss != null ? 1 : zzbss.intValue();
                }
            }
            zzdyv = (!Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ? 1 : null) != null ? new zzdyu() : new zza();
        } catch (Throwable th3) {
            Throwable th4 = th3;
            zzbss = null;
            th = th4;
            printStream = System.err;
            name = zza.class.getName();
            printStream.println(new StringBuilder(String.valueOf(name).length() + 132).append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ").append(name).append("will be used. The error is: ").toString());
            th.printStackTrace(System.err);
            zzdyv = new zza();
            zzmmf = zzdyv;
            if (zzbss != null) {
            }
            zzmmg = zzbss != null ? 1 : zzbss.intValue();
        }
        zzmmf = zzdyv;
        if (zzbss != null) {
        }
        zzmmg = zzbss != null ? 1 : zzbss.intValue();
    }

    public static void zza(Throwable th, PrintStream printStream) {
        zzmmf.zza(th, printStream);
    }

    public static void zza(Throwable th, PrintWriter printWriter) {
        zzmmf.zza(th, printWriter);
    }

    private static Integer zzbss() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }
}
