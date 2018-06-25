package com.google.android.gms.internal;

final class zzcjk implements Runnable {
    private /* synthetic */ String val$message;
    private /* synthetic */ int zzjkr;
    private /* synthetic */ Object zzjks;
    private /* synthetic */ Object zzjkt;
    private /* synthetic */ Object zzjku;
    private /* synthetic */ zzcjj zzjkv;

    zzcjk(zzcjj zzcjj, int i, String str, Object obj, Object obj2, Object obj3) {
        this.zzjkv = zzcjj;
        this.zzjkr = i;
        this.val$message = str;
        this.zzjks = obj;
        this.zzjkt = obj2;
        this.zzjku = obj3;
    }

    public final void run() {
        zzcli zzayq = this.zzjkv.zzjev.zzayq();
        if (zzayq.isInitialized()) {
            if (this.zzjkv.zzjkg == '\u0000') {
                if (this.zzjkv.zzayr().zzzu()) {
                    this.zzjkv.zzjkg = 'C';
                } else {
                    this.zzjkv.zzjkg = 'c';
                }
            }
            if (this.zzjkv.zzjft < 0) {
                this.zzjkv.zzjft = 12211;
            }
            char charAt = "01VDIWEA?".charAt(this.zzjkr);
            char zza = this.zzjkv.zzjkg;
            long zzb = this.zzjkv.zzjft;
            String zza2 = zzcjj.zza(true, this.val$message, this.zzjks, this.zzjkt, this.zzjku);
            String stringBuilder = new StringBuilder(String.valueOf(zza2).length() + 24).append("2").append(charAt).append(zza).append(zzb).append(":").append(zza2).toString();
            if (stringBuilder.length() > 1024) {
                stringBuilder = this.val$message.substring(0, 1024);
            }
            zzayq.zzjlm.zzf(stringBuilder, 1);
            return;
        }
        this.zzjkv.zzm(6, "Persisted config not initialized. Not logging error/warn");
    }
}
