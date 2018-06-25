package com.google.android.gms.internal.measurement;

final class zzfi implements Runnable {
    private final /* synthetic */ int zzajg;
    private final /* synthetic */ String zzajh;
    private final /* synthetic */ Object zzaji;
    private final /* synthetic */ Object zzajj;
    private final /* synthetic */ Object zzajk;
    private final /* synthetic */ zzfh zzajl;

    zzfi(zzfh zzfh, int i, String str, Object obj, Object obj2, Object obj3) {
        this.zzajl = zzfh;
        this.zzajg = i;
        this.zzajh = str;
        this.zzaji = obj;
        this.zzajj = obj2;
        this.zzajk = obj3;
    }

    public final void run() {
        zzhi zzgg = this.zzajl.zzacw.zzgg();
        if (zzgg.isInitialized()) {
            if (this.zzajl.zzaiv == '\u0000') {
                zzfh zzfh;
                if (this.zzajl.zzgh().zzds()) {
                    zzfh = this.zzajl;
                    this.zzajl.zzgi();
                    zzfh.zzaiv = 'C';
                } else {
                    zzfh = this.zzajl;
                    this.zzajl.zzgi();
                    zzfh.zzaiv = 'c';
                }
            }
            if (this.zzajl.zzadu < 0) {
                this.zzajl.zzadu = 12451;
            }
            char charAt = "01VDIWEA?".charAt(this.zzajg);
            char zza = this.zzajl.zzaiv;
            long zzb = this.zzajl.zzadu;
            String zza2 = zzfh.zza(true, this.zzajh, this.zzaji, this.zzajj, this.zzajk);
            String stringBuilder = new StringBuilder(String.valueOf(zza2).length() + 24).append("2").append(charAt).append(zza).append(zzb).append(":").append(zza2).toString();
            if (stringBuilder.length() > 1024) {
                stringBuilder = this.zzajh.substring(0, 1024);
            }
            zzgg.zzakc.zzc(stringBuilder, 1);
            return;
        }
        this.zzajl.zza(6, "Persisted config not initialized. Not logging error/warn");
    }
}
