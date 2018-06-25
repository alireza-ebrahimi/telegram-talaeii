package com.google.android.gms.internal.firebase_auth;

import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

public final class zzg {

    public static final class zza extends zzgn<zza> {
        public boolean zzaa;
        private boolean zzab;
        public String[] zzac;
        public String zzj;
        private String zzr;
        private String zzw;
        public String zzx;
        public String[] zzy;
        public boolean zzz;

        public zza() {
            this.zzw = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzx = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzy = zzgw.EMPTY_STRING_ARRAY;
            this.zzz = false;
            this.zzj = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaa = false;
            this.zzab = false;
            this.zzr = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzac = zzgw.EMPTY_STRING_ARRAY;
            this.zzya = -1;
        }

        public final /* synthetic */ zzgt zza(zzgk zzgk) {
            while (true) {
                int zzcc = zzgk.zzcc();
                int zzb;
                Object obj;
                switch (zzcc) {
                    case 0:
                        break;
                    case 10:
                        this.zzw = zzgk.readString();
                        continue;
                    case 18:
                        this.zzx = zzgk.readString();
                        continue;
                    case 26:
                        zzb = zzgw.zzb(zzgk, 26);
                        zzcc = this.zzy == null ? 0 : this.zzy.length;
                        obj = new String[(zzb + zzcc)];
                        if (zzcc != 0) {
                            System.arraycopy(this.zzy, 0, obj, 0, zzcc);
                        }
                        while (zzcc < obj.length - 1) {
                            obj[zzcc] = zzgk.readString();
                            zzgk.zzcc();
                            zzcc++;
                        }
                        obj[zzcc] = zzgk.readString();
                        this.zzy = obj;
                        continue;
                    case 32:
                        this.zzz = zzgk.zzci();
                        continue;
                    case 42:
                        this.zzj = zzgk.readString();
                        continue;
                    case 48:
                        this.zzaa = zzgk.zzci();
                        continue;
                    case 56:
                        this.zzab = zzgk.zzci();
                        continue;
                    case 66:
                        this.zzr = zzgk.readString();
                        continue;
                    case 74:
                        zzb = zzgw.zzb(zzgk, 74);
                        zzcc = this.zzac == null ? 0 : this.zzac.length;
                        obj = new String[(zzb + zzcc)];
                        if (zzcc != 0) {
                            System.arraycopy(this.zzac, 0, obj, 0, zzcc);
                        }
                        while (zzcc < obj.length - 1) {
                            obj[zzcc] = zzgk.readString();
                            zzgk.zzcc();
                            zzcc++;
                        }
                        obj[zzcc] = zzgk.readString();
                        this.zzac = obj;
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
            int i = 0;
            zzgl.zza(1, this.zzw);
            if (!(this.zzx == null || this.zzx.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(2, this.zzx);
            }
            if (this.zzy != null && this.zzy.length > 0) {
                for (String str : this.zzy) {
                    if (str != null) {
                        zzgl.zza(3, str);
                    }
                }
            }
            if (this.zzz) {
                zzgl.zzb(4, this.zzz);
            }
            if (!(this.zzj == null || this.zzj.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(5, this.zzj);
            }
            if (this.zzaa) {
                zzgl.zzb(6, this.zzaa);
            }
            if (this.zzab) {
                zzgl.zzb(7, this.zzab);
            }
            if (!(this.zzr == null || this.zzr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(8, this.zzr);
            }
            if (this.zzac != null && this.zzac.length > 0) {
                while (i < this.zzac.length) {
                    String str2 = this.zzac[i];
                    if (str2 != null) {
                        zzgl.zza(9, str2);
                    }
                    i++;
                }
            }
            super.zza(zzgl);
        }

        protected final int zzb() {
            int i;
            int i2;
            int i3 = 0;
            int zzb = super.zzb() + zzgl.zzb(1, this.zzw);
            if (!(this.zzx == null || this.zzx.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(2, this.zzx);
            }
            if (this.zzy != null && this.zzy.length > 0) {
                i = 0;
                int i4 = 0;
                for (String str : this.zzy) {
                    if (str != null) {
                        i4++;
                        i += zzgl.zzam(str);
                    }
                }
                zzb = (zzb + i) + (i4 * 1);
            }
            if (this.zzz) {
                zzb += zzgl.zzaa(4) + 1;
            }
            if (!(this.zzj == null || this.zzj.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(5, this.zzj);
            }
            if (this.zzaa) {
                zzb += zzgl.zzaa(6) + 1;
            }
            if (this.zzab) {
                zzb += zzgl.zzaa(7) + 1;
            }
            if (!(this.zzr == null || this.zzr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(8, this.zzr);
            }
            if (this.zzac == null || this.zzac.length <= 0) {
                return zzb;
            }
            i2 = 0;
            i = 0;
            while (i3 < this.zzac.length) {
                String str2 = this.zzac[i3];
                if (str2 != null) {
                    i++;
                    i2 += zzgl.zzam(str2);
                }
                i3++;
            }
            return (zzb + i2) + (i * 1);
        }
    }

    public static final class zzb extends zzgn<zzb> {
        public String zzad;
        public String zzaf;
        public String zzah;
        public String zzai;
        public long zzaj;
        public boolean zzak;
        private String zzw;

        public zzb() {
            this.zzw = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzai = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaj = 0;
            this.zzad = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzak = false;
            this.zzya = -1;
        }

        public final /* synthetic */ zzgt zza(zzgk zzgk) {
            while (true) {
                int zzcc = zzgk.zzcc();
                switch (zzcc) {
                    case 0:
                        break;
                    case 10:
                        this.zzw = zzgk.readString();
                        continue;
                    case 18:
                        this.zzaf = zzgk.readString();
                        continue;
                    case 26:
                        this.zzah = zzgk.readString();
                        continue;
                    case 34:
                        this.zzai = zzgk.readString();
                        continue;
                    case 40:
                        this.zzaj = zzgk.zzcv();
                        continue;
                    case 50:
                        this.zzad = zzgk.readString();
                        continue;
                    case 56:
                        this.zzak = zzgk.zzci();
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
            zzgl.zza(1, this.zzw);
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(2, this.zzaf);
            }
            if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(3, this.zzah);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(4, this.zzai);
            }
            if (this.zzaj != 0) {
                zzgl.zzi(5, this.zzaj);
            }
            if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(6, this.zzad);
            }
            if (this.zzak) {
                zzgl.zzb(7, this.zzak);
            }
            super.zza(zzgl);
        }

        protected final int zzb() {
            int zzb = super.zzb() + zzgl.zzb(1, this.zzw);
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(2, this.zzaf);
            }
            if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(3, this.zzah);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(4, this.zzai);
            }
            if (this.zzaj != 0) {
                zzb += zzgl.zzd(5, this.zzaj);
            }
            if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(6, this.zzad);
            }
            return this.zzak ? zzb + (zzgl.zzaa(7) + 1) : zzb;
        }
    }

    public static final class zzc extends zzgn<zzc> {
        public zzu[] zzan;
        private String zzw;

        public zzc() {
            this.zzw = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzan = zzu.zzd();
            this.zzya = -1;
        }

        public final /* synthetic */ zzgt zza(zzgk zzgk) {
            while (true) {
                int zzcc = zzgk.zzcc();
                switch (zzcc) {
                    case 0:
                        break;
                    case 10:
                        this.zzw = zzgk.readString();
                        continue;
                    case 18:
                        int zzb = zzgw.zzb(zzgk, 18);
                        zzcc = this.zzan == null ? 0 : this.zzan.length;
                        Object obj = new zzu[(zzb + zzcc)];
                        if (zzcc != 0) {
                            System.arraycopy(this.zzan, 0, obj, 0, zzcc);
                        }
                        while (zzcc < obj.length - 1) {
                            obj[zzcc] = new zzu();
                            zzgk.zzb(obj[zzcc]);
                            zzgk.zzcc();
                            zzcc++;
                        }
                        obj[zzcc] = new zzu();
                        zzgk.zzb(obj[zzcc]);
                        this.zzan = obj;
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
            zzgl.zza(1, this.zzw);
            if (this.zzan != null && this.zzan.length > 0) {
                for (zzgt zzgt : this.zzan) {
                    if (zzgt != null) {
                        zzgl.zza(2, zzgt);
                    }
                }
            }
            super.zza(zzgl);
        }

        protected final int zzb() {
            int zzb = zzgl.zzb(1, this.zzw) + super.zzb();
            if (this.zzan != null && this.zzan.length > 0) {
                for (zzgt zzgt : this.zzan) {
                    if (zzgt != null) {
                        zzb += zzgl.zzb(2, zzgt);
                    }
                }
            }
            return zzb;
        }
    }

    public static final class zzd extends zzgn<zzd> {
        public String zzah;
        public String zzas;
        public int zzbc;
        private String zzw;

        public zzd() {
            this.zzw = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzas = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzbc = 0;
            this.zzya = -1;
        }

        private final zzd zzc(zzgk zzgk) {
            while (true) {
                int zzcc = zzgk.zzcc();
                switch (zzcc) {
                    case 0:
                        break;
                    case 10:
                        this.zzw = zzgk.readString();
                        continue;
                    case 18:
                        this.zzah = zzgk.readString();
                        continue;
                    case 26:
                        this.zzas = zzgk.readString();
                        continue;
                    case 32:
                        int position = zzgk.getPosition();
                        try {
                            this.zzbc = zzgx.zzbe(zzgk.zzcu());
                            continue;
                        } catch (IllegalArgumentException e) {
                            zzgk.zzay(position);
                            zza(zzgk, zzcc);
                            break;
                        }
                    default:
                        if (!super.zza(zzgk, zzcc)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public final /* synthetic */ zzgt zza(zzgk zzgk) {
            return zzc(zzgk);
        }

        public final void zza(zzgl zzgl) {
            zzgl.zza(1, this.zzw);
            if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(2, this.zzah);
            }
            if (!(this.zzas == null || this.zzas.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(3, this.zzas);
            }
            if (this.zzbc != 0) {
                zzgl.zzc(4, this.zzbc);
            }
            super.zza(zzgl);
        }

        protected final int zzb() {
            int zzb = super.zzb() + zzgl.zzb(1, this.zzw);
            if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(2, this.zzah);
            }
            if (!(this.zzas == null || this.zzas.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(3, this.zzas);
            }
            return this.zzbc != 0 ? zzb + zzgl.zzg(4, this.zzbc) : zzb;
        }
    }

    public static final class zze extends zzgn<zze> {
        private String zzad;
        public String zzaf;
        public String zzah;
        public String zzai;
        public long zzaj;
        private String zzas;
        public String zzbh;
        private String[] zzbj;
        public boolean zzbk;
        public String zzbr;
        public zzt[] zzbx;
        public String zzby;
        private String zzw;

        public zze() {
            this.zzw = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzad = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzbh = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzbj = zzgw.EMPTY_STRING_ARRAY;
            this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzbx = zzt.zzc();
            this.zzas = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzbr = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzai = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaj = 0;
            this.zzby = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzbk = false;
            this.zzya = -1;
        }

        public final /* synthetic */ zzgt zza(zzgk zzgk) {
            while (true) {
                int zzcc = zzgk.zzcc();
                int zzb;
                Object obj;
                switch (zzcc) {
                    case 0:
                        break;
                    case 10:
                        this.zzw = zzgk.readString();
                        continue;
                    case 18:
                        this.zzad = zzgk.readString();
                        continue;
                    case 26:
                        this.zzah = zzgk.readString();
                        continue;
                    case 34:
                        this.zzbh = zzgk.readString();
                        continue;
                    case 42:
                        zzb = zzgw.zzb(zzgk, 42);
                        zzcc = this.zzbj == null ? 0 : this.zzbj.length;
                        obj = new String[(zzb + zzcc)];
                        if (zzcc != 0) {
                            System.arraycopy(this.zzbj, 0, obj, 0, zzcc);
                        }
                        while (zzcc < obj.length - 1) {
                            obj[zzcc] = zzgk.readString();
                            zzgk.zzcc();
                            zzcc++;
                        }
                        obj[zzcc] = zzgk.readString();
                        this.zzbj = obj;
                        continue;
                    case 50:
                        this.zzaf = zzgk.readString();
                        continue;
                    case 58:
                        zzb = zzgw.zzb(zzgk, 58);
                        zzcc = this.zzbx == null ? 0 : this.zzbx.length;
                        obj = new zzt[(zzb + zzcc)];
                        if (zzcc != 0) {
                            System.arraycopy(this.zzbx, 0, obj, 0, zzcc);
                        }
                        while (zzcc < obj.length - 1) {
                            obj[zzcc] = new zzt();
                            zzgk.zzb(obj[zzcc]);
                            zzgk.zzcc();
                            zzcc++;
                        }
                        obj[zzcc] = new zzt();
                        zzgk.zzb(obj[zzcc]);
                        this.zzbx = obj;
                        continue;
                    case 66:
                        this.zzas = zzgk.readString();
                        continue;
                    case 74:
                        this.zzbr = zzgk.readString();
                        continue;
                    case 82:
                        this.zzai = zzgk.readString();
                        continue;
                    case 88:
                        this.zzaj = zzgk.zzcv();
                        continue;
                    case 98:
                        this.zzby = zzgk.readString();
                        continue;
                    case 104:
                        this.zzbk = zzgk.zzci();
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
            int i = 0;
            zzgl.zza(1, this.zzw);
            if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(2, this.zzad);
            }
            if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(3, this.zzah);
            }
            if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(4, this.zzbh);
            }
            if (this.zzbj != null && this.zzbj.length > 0) {
                for (String str : this.zzbj) {
                    if (str != null) {
                        zzgl.zza(5, str);
                    }
                }
            }
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(6, this.zzaf);
            }
            if (this.zzbx != null && this.zzbx.length > 0) {
                while (i < this.zzbx.length) {
                    zzgt zzgt = this.zzbx[i];
                    if (zzgt != null) {
                        zzgl.zza(7, zzgt);
                    }
                    i++;
                }
            }
            if (!(this.zzas == null || this.zzas.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(8, this.zzas);
            }
            if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(9, this.zzbr);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(10, this.zzai);
            }
            if (this.zzaj != 0) {
                zzgl.zzi(11, this.zzaj);
            }
            if (!(this.zzby == null || this.zzby.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(12, this.zzby);
            }
            if (this.zzbk) {
                zzgl.zzb(13, this.zzbk);
            }
            super.zza(zzgl);
        }

        protected final int zzb() {
            int i = 0;
            int zzb = super.zzb() + zzgl.zzb(1, this.zzw);
            if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(2, this.zzad);
            }
            if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(3, this.zzah);
            }
            if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(4, this.zzbh);
            }
            if (this.zzbj != null && this.zzbj.length > 0) {
                int i2 = 0;
                int i3 = 0;
                for (String str : this.zzbj) {
                    if (str != null) {
                        i3++;
                        i2 += zzgl.zzam(str);
                    }
                }
                zzb = (zzb + i2) + (i3 * 1);
            }
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(6, this.zzaf);
            }
            if (this.zzbx != null && this.zzbx.length > 0) {
                while (i < this.zzbx.length) {
                    zzgt zzgt = this.zzbx[i];
                    if (zzgt != null) {
                        zzb += zzgl.zzb(7, zzgt);
                    }
                    i++;
                }
            }
            if (!(this.zzas == null || this.zzas.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(8, this.zzas);
            }
            if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(9, this.zzbr);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(10, this.zzai);
            }
            if (this.zzaj != 0) {
                zzb += zzgl.zzd(11, this.zzaj);
            }
            if (!(this.zzby == null || this.zzby.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(12, this.zzby);
            }
            return this.zzbk ? zzb + (zzgl.zzaa(13) + 1) : zzb;
        }
    }

    public static final class zzf extends zzgn<zzf> {
        private String zzad;
        public String zzaf;
        public String zzah;
        public String zzai;
        public long zzaj;
        public String zzbh;
        private String zzw;

        public zzf() {
            this.zzw = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzbh = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzai = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaj = 0;
            this.zzad = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzya = -1;
        }

        public final /* synthetic */ zzgt zza(zzgk zzgk) {
            while (true) {
                int zzcc = zzgk.zzcc();
                switch (zzcc) {
                    case 0:
                        break;
                    case 10:
                        this.zzw = zzgk.readString();
                        continue;
                    case 18:
                        this.zzaf = zzgk.readString();
                        continue;
                    case 34:
                        this.zzbh = zzgk.readString();
                        continue;
                    case 42:
                        this.zzah = zzgk.readString();
                        continue;
                    case 50:
                        this.zzai = zzgk.readString();
                        continue;
                    case 56:
                        this.zzaj = zzgk.zzcv();
                        continue;
                    case 66:
                        this.zzad = zzgk.readString();
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
            zzgl.zza(1, this.zzw);
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(2, this.zzaf);
            }
            if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(4, this.zzbh);
            }
            if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(5, this.zzah);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(6, this.zzai);
            }
            if (this.zzaj != 0) {
                zzgl.zzi(7, this.zzaj);
            }
            if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(8, this.zzad);
            }
            super.zza(zzgl);
        }

        protected final int zzb() {
            int zzb = super.zzb() + zzgl.zzb(1, this.zzw);
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(2, this.zzaf);
            }
            if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(4, this.zzbh);
            }
            if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(5, this.zzah);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(6, this.zzai);
            }
            if (this.zzaj != 0) {
                zzb += zzgl.zzd(7, this.zzaj);
            }
            return (this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID)) ? zzb : zzb + zzgl.zzb(8, this.zzad);
        }
    }

    public static final class zzg extends zzgn<zzg> {
        public String zzad;
        public String zzaf;
        public String zzah;
        public String zzai;
        public long zzaj;
        public boolean zzak;
        public String zzbh;
        private boolean zzbk;
        public String zzbr;
        private String zzcg;
        private String zzch;
        private String zzci;
        private String zzcj;
        private String zzck;
        private String zzcl;
        private String zzcm;
        private String zzcn;
        private String zzco;
        private String zzcp;
        private String zzcq;
        private boolean zzcr;
        private String zzcs;
        private String[] zzct;
        public boolean zzcu;
        private String zzcv;
        private String zzcw;
        public String zzcx;
        private String zzcy;
        private long zzcz;
        private String zzda;
        public boolean zzdb;
        private String zzdc;
        public String zzdd;
        private String zzde;
        public String zzdf;
        private String zzdg;
        public String zzj;
        private String zzl;
        private String zzn;

        public zzg() {
            this.zzcg = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzj = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzbk = false;
            this.zzch = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzci = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcj = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzck = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcl = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcm = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzbr = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcn = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzco = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzl = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcp = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcq = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzad = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcr = false;
            this.zzbh = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcs = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzn = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzct = zzgw.EMPTY_STRING_ARRAY;
            this.zzcu = false;
            this.zzcv = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcw = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcx = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcy = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcz = 0;
            this.zzda = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzdb = false;
            this.zzdc = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzai = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaj = 0;
            this.zzdd = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzde = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzdf = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzdg = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzak = false;
            this.zzya = -1;
        }

        public final /* synthetic */ zzgt zza(zzgk zzgk) {
            while (true) {
                int zzcc = zzgk.zzcc();
                switch (zzcc) {
                    case 0:
                        break;
                    case 10:
                        this.zzcg = zzgk.readString();
                        continue;
                    case 18:
                        this.zzj = zzgk.readString();
                        continue;
                    case 26:
                        this.zzah = zzgk.readString();
                        continue;
                    case 32:
                        this.zzbk = zzgk.zzci();
                        continue;
                    case 42:
                        this.zzch = zzgk.readString();
                        continue;
                    case 50:
                        this.zzci = zzgk.readString();
                        continue;
                    case 58:
                        this.zzcj = zzgk.readString();
                        continue;
                    case 66:
                        this.zzck = zzgk.readString();
                        continue;
                    case 74:
                        this.zzcl = zzgk.readString();
                        continue;
                    case 82:
                        this.zzcm = zzgk.readString();
                        continue;
                    case 90:
                        this.zzbr = zzgk.readString();
                        continue;
                    case 98:
                        this.zzcn = zzgk.readString();
                        continue;
                    case 106:
                        this.zzco = zzgk.readString();
                        continue;
                    case 114:
                        this.zzl = zzgk.readString();
                        continue;
                    case 122:
                        this.zzcp = zzgk.readString();
                        continue;
                    case TsExtractor.TS_STREAM_TYPE_HDMV_DTS /*130*/:
                        this.zzcq = zzgk.readString();
                        continue;
                    case TsExtractor.TS_STREAM_TYPE_DTS /*138*/:
                        this.zzad = zzgk.readString();
                        continue;
                    case 144:
                        this.zzcr = zzgk.zzci();
                        continue;
                    case 154:
                        this.zzbh = zzgk.readString();
                        continue;
                    case 162:
                        this.zzaf = zzgk.readString();
                        continue;
                    case 170:
                        this.zzcs = zzgk.readString();
                        continue;
                    case 186:
                        this.zzn = zzgk.readString();
                        continue;
                    case 194:
                        int zzb = zzgw.zzb(zzgk, 194);
                        zzcc = this.zzct == null ? 0 : this.zzct.length;
                        Object obj = new String[(zzb + zzcc)];
                        if (zzcc != 0) {
                            System.arraycopy(this.zzct, 0, obj, 0, zzcc);
                        }
                        while (zzcc < obj.length - 1) {
                            obj[zzcc] = zzgk.readString();
                            zzgk.zzcc();
                            zzcc++;
                        }
                        obj[zzcc] = zzgk.readString();
                        this.zzct = obj;
                        continue;
                    case Callback.DEFAULT_DRAG_ANIMATION_DURATION /*200*/:
                        this.zzcu = zzgk.zzci();
                        continue;
                    case 210:
                        this.zzcv = zzgk.readString();
                        continue;
                    case 218:
                        this.zzcw = zzgk.readString();
                        continue;
                    case 226:
                        this.zzcx = zzgk.readString();
                        continue;
                    case 234:
                        this.zzcy = zzgk.readString();
                        continue;
                    case PsExtractor.VIDEO_STREAM_MASK /*240*/:
                        this.zzcz = zzgk.zzcv();
                        continue;
                    case Callback.DEFAULT_SWIPE_ANIMATION_DURATION /*250*/:
                        this.zzda = zzgk.readString();
                        continue;
                    case 256:
                        this.zzdb = zzgk.zzci();
                        continue;
                    case 266:
                        this.zzdc = zzgk.readString();
                        continue;
                    case 274:
                        this.zzai = zzgk.readString();
                        continue;
                    case 280:
                        this.zzaj = zzgk.zzcv();
                        continue;
                    case 290:
                        this.zzdd = zzgk.readString();
                        continue;
                    case 298:
                        this.zzde = zzgk.readString();
                        continue;
                    case 306:
                        this.zzdf = zzgk.readString();
                        continue;
                    case 314:
                        this.zzdg = zzgk.readString();
                        continue;
                    case 320:
                        this.zzak = zzgk.zzci();
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
            if (!(this.zzcg == null || this.zzcg.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(1, this.zzcg);
            }
            if (!(this.zzj == null || this.zzj.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(2, this.zzj);
            }
            if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(3, this.zzah);
            }
            if (this.zzbk) {
                zzgl.zzb(4, this.zzbk);
            }
            if (!(this.zzch == null || this.zzch.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(5, this.zzch);
            }
            if (!(this.zzci == null || this.zzci.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(6, this.zzci);
            }
            if (!(this.zzcj == null || this.zzcj.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(7, this.zzcj);
            }
            if (!(this.zzck == null || this.zzck.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(8, this.zzck);
            }
            if (!(this.zzcl == null || this.zzcl.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(9, this.zzcl);
            }
            if (!(this.zzcm == null || this.zzcm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(10, this.zzcm);
            }
            if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(11, this.zzbr);
            }
            if (!(this.zzcn == null || this.zzcn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(12, this.zzcn);
            }
            if (!(this.zzco == null || this.zzco.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(13, this.zzco);
            }
            if (!(this.zzl == null || this.zzl.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(14, this.zzl);
            }
            if (!(this.zzcp == null || this.zzcp.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(15, this.zzcp);
            }
            if (!(this.zzcq == null || this.zzcq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(16, this.zzcq);
            }
            if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(17, this.zzad);
            }
            if (this.zzcr) {
                zzgl.zzb(18, this.zzcr);
            }
            if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(19, this.zzbh);
            }
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(20, this.zzaf);
            }
            if (!(this.zzcs == null || this.zzcs.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(21, this.zzcs);
            }
            if (!(this.zzn == null || this.zzn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(23, this.zzn);
            }
            if (this.zzct != null && this.zzct.length > 0) {
                for (String str : this.zzct) {
                    if (str != null) {
                        zzgl.zza(24, str);
                    }
                }
            }
            if (this.zzcu) {
                zzgl.zzb(25, this.zzcu);
            }
            if (!(this.zzcv == null || this.zzcv.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(26, this.zzcv);
            }
            if (!(this.zzcw == null || this.zzcw.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(27, this.zzcw);
            }
            if (!(this.zzcx == null || this.zzcx.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(28, this.zzcx);
            }
            if (!(this.zzcy == null || this.zzcy.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(29, this.zzcy);
            }
            if (this.zzcz != 0) {
                zzgl.zzi(30, this.zzcz);
            }
            if (!(this.zzda == null || this.zzda.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(31, this.zzda);
            }
            if (this.zzdb) {
                zzgl.zzb(32, this.zzdb);
            }
            if (!(this.zzdc == null || this.zzdc.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(33, this.zzdc);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(34, this.zzai);
            }
            if (this.zzaj != 0) {
                zzgl.zzi(35, this.zzaj);
            }
            if (!(this.zzdd == null || this.zzdd.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(36, this.zzdd);
            }
            if (!(this.zzde == null || this.zzde.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(37, this.zzde);
            }
            if (!(this.zzdf == null || this.zzdf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(38, this.zzdf);
            }
            if (!(this.zzdg == null || this.zzdg.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(39, this.zzdg);
            }
            if (this.zzak) {
                zzgl.zzb(40, this.zzak);
            }
            super.zza(zzgl);
        }

        protected final int zzb() {
            int i = 0;
            int zzb = super.zzb();
            if (!(this.zzcg == null || this.zzcg.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(1, this.zzcg);
            }
            if (!(this.zzj == null || this.zzj.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(2, this.zzj);
            }
            if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(3, this.zzah);
            }
            if (this.zzbk) {
                zzb += zzgl.zzaa(4) + 1;
            }
            if (!(this.zzch == null || this.zzch.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(5, this.zzch);
            }
            if (!(this.zzci == null || this.zzci.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(6, this.zzci);
            }
            if (!(this.zzcj == null || this.zzcj.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(7, this.zzcj);
            }
            if (!(this.zzck == null || this.zzck.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(8, this.zzck);
            }
            if (!(this.zzcl == null || this.zzcl.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(9, this.zzcl);
            }
            if (!(this.zzcm == null || this.zzcm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(10, this.zzcm);
            }
            if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(11, this.zzbr);
            }
            if (!(this.zzcn == null || this.zzcn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(12, this.zzcn);
            }
            if (!(this.zzco == null || this.zzco.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(13, this.zzco);
            }
            if (!(this.zzl == null || this.zzl.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(14, this.zzl);
            }
            if (!(this.zzcp == null || this.zzcp.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(15, this.zzcp);
            }
            if (!(this.zzcq == null || this.zzcq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(16, this.zzcq);
            }
            if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(17, this.zzad);
            }
            if (this.zzcr) {
                zzb += zzgl.zzaa(18) + 1;
            }
            if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(19, this.zzbh);
            }
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(20, this.zzaf);
            }
            if (!(this.zzcs == null || this.zzcs.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(21, this.zzcs);
            }
            if (!(this.zzn == null || this.zzn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(23, this.zzn);
            }
            if (this.zzct != null && this.zzct.length > 0) {
                int i2 = 0;
                int i3 = 0;
                while (i < this.zzct.length) {
                    String str = this.zzct[i];
                    if (str != null) {
                        i3++;
                        i2 += zzgl.zzam(str);
                    }
                    i++;
                }
                zzb = (zzb + i2) + (i3 * 2);
            }
            if (this.zzcu) {
                zzb += zzgl.zzaa(25) + 1;
            }
            if (!(this.zzcv == null || this.zzcv.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(26, this.zzcv);
            }
            if (!(this.zzcw == null || this.zzcw.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(27, this.zzcw);
            }
            if (!(this.zzcx == null || this.zzcx.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(28, this.zzcx);
            }
            if (!(this.zzcy == null || this.zzcy.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(29, this.zzcy);
            }
            if (this.zzcz != 0) {
                zzb += zzgl.zzd(30, this.zzcz);
            }
            if (!(this.zzda == null || this.zzda.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(31, this.zzda);
            }
            if (this.zzdb) {
                zzb += zzgl.zzaa(32) + 1;
            }
            if (!(this.zzdc == null || this.zzdc.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(33, this.zzdc);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(34, this.zzai);
            }
            if (this.zzaj != 0) {
                zzb += zzgl.zzd(35, this.zzaj);
            }
            if (!(this.zzdd == null || this.zzdd.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(36, this.zzdd);
            }
            if (!(this.zzde == null || this.zzde.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(37, this.zzde);
            }
            if (!(this.zzdf == null || this.zzdf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(38, this.zzdf);
            }
            if (!(this.zzdg == null || this.zzdg.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(39, this.zzdg);
            }
            return this.zzak ? zzb + (zzgl.zzaa(40) + 1) : zzb;
        }
    }

    public static final class zzh extends zzgn<zzh> {
        public String zzaf;
        public String zzai;
        public long zzaj;
        public boolean zzak;
        private String zzw;

        public zzh() {
            this.zzw = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzai = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaj = 0;
            this.zzak = false;
            this.zzya = -1;
        }

        public final /* synthetic */ zzgt zza(zzgk zzgk) {
            while (true) {
                int zzcc = zzgk.zzcc();
                switch (zzcc) {
                    case 0:
                        break;
                    case 10:
                        this.zzw = zzgk.readString();
                        continue;
                    case 18:
                        this.zzaf = zzgk.readString();
                        continue;
                    case 26:
                        this.zzai = zzgk.readString();
                        continue;
                    case 32:
                        this.zzaj = zzgk.zzcv();
                        continue;
                    case 40:
                        this.zzak = zzgk.zzci();
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
            zzgl.zza(1, this.zzw);
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(2, this.zzaf);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(3, this.zzai);
            }
            if (this.zzaj != 0) {
                zzgl.zzi(4, this.zzaj);
            }
            if (this.zzak) {
                zzgl.zzb(5, this.zzak);
            }
            super.zza(zzgl);
        }

        protected final int zzb() {
            int zzb = super.zzb() + zzgl.zzb(1, this.zzw);
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(2, this.zzaf);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(3, this.zzai);
            }
            if (this.zzaj != 0) {
                zzb += zzgl.zzd(4, this.zzaj);
            }
            return this.zzak ? zzb + (zzgl.zzaa(5) + 1) : zzb;
        }
    }

    public static final class zzi extends zzgn<zzi> {
        public String zzad;
        public String zzaf;
        public String zzah;
        public String zzai;
        public long zzaj;
        public String zzbh;
        public String zzbr;
        private String zzcx;
        private long zzcz;
        private String zzda;
        private String zzw;
        private boolean zzz;

        public zzi() {
            this.zzw = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzad = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzbh = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzz = false;
            this.zzbr = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcx = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzcz = 0;
            this.zzda = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzai = TtmlNode.ANONYMOUS_REGION_ID;
            this.zzaj = 0;
            this.zzya = -1;
        }

        public final /* synthetic */ zzgt zza(zzgk zzgk) {
            while (true) {
                int zzcc = zzgk.zzcc();
                switch (zzcc) {
                    case 0:
                        break;
                    case 10:
                        this.zzw = zzgk.readString();
                        continue;
                    case 18:
                        this.zzad = zzgk.readString();
                        continue;
                    case 26:
                        this.zzah = zzgk.readString();
                        continue;
                    case 34:
                        this.zzbh = zzgk.readString();
                        continue;
                    case 42:
                        this.zzaf = zzgk.readString();
                        continue;
                    case 48:
                        this.zzz = zzgk.zzci();
                        continue;
                    case 58:
                        this.zzbr = zzgk.readString();
                        continue;
                    case 66:
                        this.zzcx = zzgk.readString();
                        continue;
                    case 72:
                        this.zzcz = zzgk.zzcv();
                        continue;
                    case 82:
                        this.zzda = zzgk.readString();
                        continue;
                    case 90:
                        this.zzai = zzgk.readString();
                        continue;
                    case 96:
                        this.zzaj = zzgk.zzcv();
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
            zzgl.zza(1, this.zzw);
            if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(2, this.zzad);
            }
            if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(3, this.zzah);
            }
            if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(4, this.zzbh);
            }
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(5, this.zzaf);
            }
            if (this.zzz) {
                zzgl.zzb(6, this.zzz);
            }
            if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(7, this.zzbr);
            }
            if (!(this.zzcx == null || this.zzcx.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(8, this.zzcx);
            }
            if (this.zzcz != 0) {
                zzgl.zzi(9, this.zzcz);
            }
            if (!(this.zzda == null || this.zzda.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(10, this.zzda);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzgl.zza(11, this.zzai);
            }
            if (this.zzaj != 0) {
                zzgl.zzi(12, this.zzaj);
            }
            super.zza(zzgl);
        }

        protected final int zzb() {
            int zzb = super.zzb() + zzgl.zzb(1, this.zzw);
            if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(2, this.zzad);
            }
            if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(3, this.zzah);
            }
            if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(4, this.zzbh);
            }
            if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(5, this.zzaf);
            }
            if (this.zzz) {
                zzb += zzgl.zzaa(6) + 1;
            }
            if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(7, this.zzbr);
            }
            if (!(this.zzcx == null || this.zzcx.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(8, this.zzcx);
            }
            if (this.zzcz != 0) {
                zzb += zzgl.zzd(9, this.zzcz);
            }
            if (!(this.zzda == null || this.zzda.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(10, this.zzda);
            }
            if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                zzb += zzgl.zzb(11, this.zzai);
            }
            return this.zzaj != 0 ? zzb + zzgl.zzd(12, this.zzaj) : zzb;
        }
    }
}
