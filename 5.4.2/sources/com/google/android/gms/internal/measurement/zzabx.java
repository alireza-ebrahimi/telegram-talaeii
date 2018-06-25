package com.google.android.gms.internal.measurement;

import org.telegram.tgnet.ConnectionsManager;

public final class zzabx {
    private final byte[] buffer;
    private int zzbrn = 64;
    private int zzbro = ConnectionsManager.FileTypeFile;
    private int zzbrs;
    private int zzbru = Integer.MAX_VALUE;
    private final int zzbwz;
    private final int zzbxa;
    private int zzbxb;
    private int zzbxc;
    private int zzbxd;
    private int zzbxe;

    private zzabx(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.zzbwz = i;
        int i3 = i + i2;
        this.zzbxb = i3;
        this.zzbxa = i3;
        this.zzbxc = i;
    }

    public static zzabx zza(byte[] bArr, int i, int i2) {
        return new zzabx(bArr, 0, i2);
    }

    private final void zzan(int i) {
        if (i < 0) {
            throw zzacf.zzvr();
        } else if (this.zzbxc + i > this.zzbru) {
            zzan(this.zzbru - this.zzbxc);
            throw zzacf.zzvq();
        } else if (i <= this.zzbxb - this.zzbxc) {
            this.zzbxc += i;
        } else {
            throw zzacf.zzvq();
        }
    }

    public static zzabx zzi(byte[] bArr) {
        return zza(bArr, 0, bArr.length);
    }

    private final void zztj() {
        this.zzbxb += this.zzbrs;
        int i = this.zzbxb;
        if (i > this.zzbru) {
            this.zzbrs = i - this.zzbru;
            this.zzbxb -= this.zzbrs;
            return;
        }
        this.zzbrs = 0;
    }

    private final byte zzvm() {
        if (this.zzbxc == this.zzbxb) {
            throw zzacf.zzvq();
        }
        byte[] bArr = this.buffer;
        int i = this.zzbxc;
        this.zzbxc = i + 1;
        return bArr[i];
    }

    public final int getPosition() {
        return this.zzbxc - this.zzbwz;
    }

    public final String readString() {
        int zzvh = zzvh();
        if (zzvh < 0) {
            throw zzacf.zzvr();
        } else if (zzvh > this.zzbxb - this.zzbxc) {
            throw zzacf.zzvq();
        } else {
            String str = new String(this.buffer, this.zzbxc, zzvh, zzace.UTF_8);
            this.zzbxc = zzvh + this.zzbxc;
            return str;
        }
    }

    public final void zza(zzacg zzacg) {
        int zzvh = zzvh();
        if (this.zzbxe >= this.zzbrn) {
            throw zzacf.zzvt();
        }
        zzvh = zzaf(zzvh);
        this.zzbxe++;
        zzacg.zzb(this);
        zzaj(0);
        this.zzbxe--;
        zzal(zzvh);
    }

    public final void zza(zzacg zzacg, int i) {
        if (this.zzbxe >= this.zzbrn) {
            throw zzacf.zzvt();
        }
        this.zzbxe++;
        zzacg.zzb(this);
        zzaj((i << 3) | 4);
        this.zzbxe--;
    }

    public final int zzaf(int i) {
        if (i < 0) {
            throw zzacf.zzvr();
        }
        int i2 = this.zzbxc + i;
        int i3 = this.zzbru;
        if (i2 > i3) {
            throw zzacf.zzvq();
        }
        this.zzbru = i2;
        zztj();
        return i3;
    }

    public final void zzaj(int i) {
        if (this.zzbxd != i) {
            throw new zzacf("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final boolean zzak(int i) {
        switch (i & 7) {
            case 0:
                zzvh();
                return true;
            case 1:
                zzvk();
                return true;
            case 2:
                zzan(zzvh());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzvj();
                return true;
            default:
                throw new zzacf("Protocol message tag had invalid wire type.");
        }
        int zzvf;
        do {
            zzvf = zzvf();
            if (zzvf != 0) {
            }
            zzaj(((i >>> 3) << 3) | 4);
            return true;
        } while (zzak(zzvf));
        zzaj(((i >>> 3) << 3) | 4);
        return true;
    }

    public final void zzal(int i) {
        this.zzbru = i;
        zztj();
    }

    public final void zzam(int i) {
        zzd(i, this.zzbxd);
    }

    public final byte[] zzc(int i, int i2) {
        if (i2 == 0) {
            return zzacj.zzbyc;
        }
        Object obj = new byte[i2];
        System.arraycopy(this.buffer, this.zzbwz + i, obj, 0, i2);
        return obj;
    }

    final void zzd(int i, int i2) {
        if (i > this.zzbxc - this.zzbwz) {
            throw new IllegalArgumentException("Position " + i + " is beyond current " + (this.zzbxc - this.zzbwz));
        } else if (i < 0) {
            throw new IllegalArgumentException("Bad position " + i);
        } else {
            this.zzbxc = this.zzbwz + i;
            this.zzbxd = i2;
        }
    }

    public final int zzvf() {
        if (this.zzbxc == this.zzbxb) {
            this.zzbxd = 0;
            return 0;
        }
        this.zzbxd = zzvh();
        if (this.zzbxd != 0) {
            return this.zzbxd;
        }
        throw new zzacf("Protocol message contained an invalid tag (zero).");
    }

    public final boolean zzvg() {
        return zzvh() != 0;
    }

    public final int zzvh() {
        byte zzvm = zzvm();
        if (zzvm >= (byte) 0) {
            return zzvm;
        }
        int i = zzvm & 127;
        byte zzvm2 = zzvm();
        if (zzvm2 >= (byte) 0) {
            return i | (zzvm2 << 7);
        }
        i |= (zzvm2 & 127) << 7;
        zzvm2 = zzvm();
        if (zzvm2 >= (byte) 0) {
            return i | (zzvm2 << 14);
        }
        i |= (zzvm2 & 127) << 14;
        zzvm2 = zzvm();
        if (zzvm2 >= (byte) 0) {
            return i | (zzvm2 << 21);
        }
        i |= (zzvm2 & 127) << 21;
        zzvm2 = zzvm();
        i |= zzvm2 << 28;
        if (zzvm2 >= (byte) 0) {
            return i;
        }
        for (int i2 = 0; i2 < 5; i2++) {
            if (zzvm() >= (byte) 0) {
                return i;
            }
        }
        throw zzacf.zzvs();
    }

    public final long zzvi() {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzvm = zzvm();
            j |= ((long) (zzvm & 127)) << i;
            if ((zzvm & 128) == 0) {
                return j;
            }
        }
        throw zzacf.zzvs();
    }

    public final int zzvj() {
        return (((zzvm() & 255) | ((zzvm() & 255) << 8)) | ((zzvm() & 255) << 16)) | ((zzvm() & 255) << 24);
    }

    public final long zzvk() {
        byte zzvm = zzvm();
        byte zzvm2 = zzvm();
        return ((((((((((long) zzvm2) & 255) << 8) | (((long) zzvm) & 255)) | ((((long) zzvm()) & 255) << 16)) | ((((long) zzvm()) & 255) << 24)) | ((((long) zzvm()) & 255) << 32)) | ((((long) zzvm()) & 255) << 40)) | ((((long) zzvm()) & 255) << 48)) | ((((long) zzvm()) & 255) << 56);
    }

    public final int zzvl() {
        if (this.zzbru == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzbru - this.zzbxc;
    }
}
