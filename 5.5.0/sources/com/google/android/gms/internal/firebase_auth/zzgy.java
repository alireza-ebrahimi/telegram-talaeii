package com.google.android.gms.internal.firebase_auth;

public final class zzgy {

    public static final class zza extends zzgn<zza> {
        private String zzaf;
        public String zzai;
        public long zzaj;
        public String zzdv;
        public String zzjy;
        private String zzkv;
        private long zzym;

        public zza() {
            this.zzdv = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaj = 0;
            this.zzjy = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzai = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzkv = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzym = 0;
            this.zzya = -1;
        }

        public final /* synthetic */ zzgt zza(zzgk zzgk) {
            while (true) {
                int zzcc = zzgk.zzcc();
                switch (zzcc) {
                    case 0:
                        break;
                    case 10:
                        this.zzdv = zzgk.readString();
                        continue;
                    case 16:
                        this.zzaj = zzgk.zzce();
                        continue;
                    case 26:
                        this.zzjy = zzgk.readString();
                        continue;
                    case 34:
                        this.zzai = zzgk.readString();
                        continue;
                    case 42:
                        this.zzaf = zzgk.readString();
                        continue;
                    case 50:
                        this.zzkv = zzgk.readString();
                        continue;
                    case 56:
                        this.zzym = zzgk.zzce();
                        continue;
                    default:
                        if (!super.zza(zzgk, zzcc)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public final void zza(zzgl zzgl) {
            if (!(this.zzdv == null || this.zzdv.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(1, this.zzdv);
            }
            if (this.zzaj != 0) {
                zzgl.zzi(2, this.zzaj);
            }
            if (!(this.zzjy == null || this.zzjy.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(3, this.zzjy);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(4, this.zzai);
            }
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(5, this.zzaf);
            }
            if (!(this.zzkv == null || this.zzkv.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(6, this.zzkv);
            }
            if (this.zzym != 0) {
                zzgl.zzi(7, this.zzym);
            }
            super.zza(zzgl);
        }

        protected final int zzb() {
            int zzb = super.zzb();
            if (!(this.zzdv == null || this.zzdv.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(1, this.zzdv);
            }
            if (this.zzaj != 0) {
                zzb += zzgl.zzd(2, this.zzaj);
            }
            if (!(this.zzjy == null || this.zzjy.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(3, this.zzjy);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(4, this.zzai);
            }
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(5, this.zzaf);
            }
            if (!(this.zzkv == null || this.zzkv.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(6, this.zzkv);
            }
            return this.zzym != 0 ? zzb + zzgl.zzd(7, this.zzym) : zzb;
        }
    }
}
