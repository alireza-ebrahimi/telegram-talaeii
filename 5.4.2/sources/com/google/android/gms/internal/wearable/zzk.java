package com.google.android.gms.internal.wearable;

import org.telegram.tgnet.ConnectionsManager;

public final class zzk {
    private final byte[] buffer;
    private final int zzgr;
    private final int zzgs;
    private int zzgt;
    private int zzgu;
    private int zzgv;
    private int zzgw;
    private int zzgx = Integer.MAX_VALUE;
    private int zzgy;
    private int zzgz = 64;
    private int zzha = ConnectionsManager.FileTypeFile;

    private zzk(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.zzgr = i;
        int i3 = i + i2;
        this.zzgt = i3;
        this.zzgs = i3;
        this.zzgv = i;
    }

    public static zzk zza(byte[] bArr, int i, int i2) {
        return new zzk(bArr, 0, i2);
    }

    private final void zzh(int i) {
        if (i < 0) {
            throw zzs.zzv();
        } else if (this.zzgv + i > this.zzgx) {
            zzh(this.zzgx - this.zzgv);
            throw zzs.zzu();
        } else if (i <= this.zzgt - this.zzgv) {
            this.zzgv += i;
        } else {
            throw zzs.zzu();
        }
    }

    private final void zzo() {
        this.zzgt += this.zzgu;
        int i = this.zzgt;
        if (i > this.zzgx) {
            this.zzgu = i - this.zzgx;
            this.zzgt -= this.zzgu;
            return;
        }
        this.zzgu = 0;
    }

    private final byte zzq() {
        if (this.zzgv == this.zzgt) {
            throw zzs.zzu();
        }
        byte[] bArr = this.buffer;
        int i = this.zzgv;
        this.zzgv = i + 1;
        return bArr[i];
    }

    public final int getPosition() {
        return this.zzgv - this.zzgr;
    }

    public final byte[] readBytes() {
        int zzk = zzk();
        if (zzk < 0) {
            throw zzs.zzv();
        } else if (zzk == 0) {
            return zzw.zzhy;
        } else {
            if (zzk > this.zzgt - this.zzgv) {
                throw zzs.zzu();
            }
            Object obj = new byte[zzk];
            System.arraycopy(this.buffer, this.zzgv, obj, 0, zzk);
            this.zzgv = zzk + this.zzgv;
            return obj;
        }
    }

    public final String readString() {
        int zzk = zzk();
        if (zzk < 0) {
            throw zzs.zzv();
        } else if (zzk > this.zzgt - this.zzgv) {
            throw zzs.zzu();
        } else {
            String str = new String(this.buffer, this.zzgv, zzk, zzr.UTF_8);
            this.zzgv = zzk + this.zzgv;
            return str;
        }
    }

    public final void zza(zzt zzt) {
        int zzk = zzk();
        if (this.zzgy >= this.zzgz) {
            throw new zzs("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
        }
        zzk = zze(zzk);
        this.zzgy++;
        zzt.zza(this);
        zzc(0);
        this.zzgy--;
        zzf(zzk);
    }

    public final byte[] zzb(int i, int i2) {
        if (i2 == 0) {
            return zzw.zzhy;
        }
        Object obj = new byte[i2];
        System.arraycopy(this.buffer, this.zzgr + i, obj, 0, i2);
        return obj;
    }

    public final void zzc(int i) {
        if (this.zzgw != i) {
            throw new zzs("Protocol message end-group tag did not match expected tag.");
        }
    }

    final void zzc(int i, int i2) {
        if (i > this.zzgv - this.zzgr) {
            throw new IllegalArgumentException("Position " + i + " is beyond current " + (this.zzgv - this.zzgr));
        } else if (i < 0) {
            throw new IllegalArgumentException("Bad position " + i);
        } else {
            this.zzgv = this.zzgr + i;
            this.zzgw = i2;
        }
    }

    public final boolean zzd(int i) {
        switch (i & 7) {
            case 0:
                zzk();
                return true;
            case 1:
                zzn();
                return true;
            case 2:
                zzh(zzk());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzm();
                return true;
            default:
                throw new zzs("Protocol message tag had invalid wire type.");
        }
        int zzj;
        do {
            zzj = zzj();
            if (zzj != 0) {
            }
            zzc(((i >>> 3) << 3) | 4);
            return true;
        } while (zzd(zzj));
        zzc(((i >>> 3) << 3) | 4);
        return true;
    }

    public final int zze(int i) {
        if (i < 0) {
            throw zzs.zzv();
        }
        int i2 = this.zzgv + i;
        int i3 = this.zzgx;
        if (i2 > i3) {
            throw zzs.zzu();
        }
        this.zzgx = i2;
        zzo();
        return i3;
    }

    public final void zzf(int i) {
        this.zzgx = i;
        zzo();
    }

    public final void zzg(int i) {
        zzc(i, this.zzgw);
    }

    public final int zzj() {
        if (this.zzgv == this.zzgt) {
            this.zzgw = 0;
            return 0;
        }
        this.zzgw = zzk();
        if (this.zzgw != 0) {
            return this.zzgw;
        }
        throw new zzs("Protocol message contained an invalid tag (zero).");
    }

    public final int zzk() {
        byte zzq = zzq();
        if (zzq >= (byte) 0) {
            return zzq;
        }
        int i = zzq & 127;
        byte zzq2 = zzq();
        if (zzq2 >= (byte) 0) {
            return i | (zzq2 << 7);
        }
        i |= (zzq2 & 127) << 7;
        zzq2 = zzq();
        if (zzq2 >= (byte) 0) {
            return i | (zzq2 << 14);
        }
        i |= (zzq2 & 127) << 14;
        zzq2 = zzq();
        if (zzq2 >= (byte) 0) {
            return i | (zzq2 << 21);
        }
        i |= (zzq2 & 127) << 21;
        zzq2 = zzq();
        i |= zzq2 << 28;
        if (zzq2 >= (byte) 0) {
            return i;
        }
        for (int i2 = 0; i2 < 5; i2++) {
            if (zzq() >= (byte) 0) {
                return i;
            }
        }
        throw zzs.zzw();
    }

    public final long zzl() {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzq = zzq();
            j |= ((long) (zzq & 127)) << i;
            if ((zzq & 128) == 0) {
                return j;
            }
        }
        throw zzs.zzw();
    }

    public final int zzm() {
        return (((zzq() & 255) | ((zzq() & 255) << 8)) | ((zzq() & 255) << 16)) | ((zzq() & 255) << 24);
    }

    public final long zzn() {
        byte zzq = zzq();
        byte zzq2 = zzq();
        return ((((((((((long) zzq2) & 255) << 8) | (((long) zzq) & 255)) | ((((long) zzq()) & 255) << 16)) | ((((long) zzq()) & 255) << 24)) | ((((long) zzq()) & 255) << 32)) | ((((long) zzq()) & 255) << 40)) | ((((long) zzq()) & 255) << 48)) | ((((long) zzq()) & 255) << 56);
    }

    public final int zzp() {
        if (this.zzgx == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzgx - this.zzgv;
    }
}
