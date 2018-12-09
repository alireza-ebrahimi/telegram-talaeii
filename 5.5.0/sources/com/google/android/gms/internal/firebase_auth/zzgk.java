package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import org.telegram.tgnet.ConnectionsManager;

public final class zzgk {
    private final byte[] buffer;
    private int zzmq;
    private int zzmr = 64;
    private int zzms = ConnectionsManager.FileTypeFile;
    private int zzmx;
    private int zzmz;
    private int zzna = Integer.MAX_VALUE;
    private final int zzxk;
    private final int zzxl;
    private int zzxm;
    private int zzxn;
    private zzcd zzxo;

    private zzgk(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.zzxk = i;
        int i3 = i + i2;
        this.zzxm = i3;
        this.zzxl = i3;
        this.zzxn = i;
    }

    private final void zzcy() {
        this.zzxm += this.zzmx;
        int i = this.zzxm;
        if (i > this.zzna) {
            this.zzmx = i - this.zzna;
            this.zzxm -= this.zzmx;
            return;
        }
        this.zzmx = 0;
    }

    private final byte zzcz() {
        if (this.zzxn == this.zzxm) {
            throw zzgs.zzgp();
        }
        byte[] bArr = this.buffer;
        int i = this.zzxn;
        this.zzxn = i + 1;
        return bArr[i];
    }

    public static zzgk zzi(byte[] bArr, int i, int i2) {
        return new zzgk(bArr, 0, i2);
    }

    private final void zzr(int i) {
        if (i < 0) {
            throw zzgs.zzgq();
        } else if (this.zzxn + i > this.zzna) {
            zzr(this.zzna - this.zzxn);
            throw zzgs.zzgp();
        } else if (i <= this.zzxm - this.zzxn) {
            this.zzxn += i;
        } else {
            throw zzgs.zzgp();
        }
    }

    public final int getPosition() {
        return this.zzxn - this.zzxk;
    }

    public final byte[] readBytes() {
        int zzcu = zzcu();
        if (zzcu < 0) {
            throw zzgs.zzgq();
        } else if (zzcu == 0) {
            return zzgw.zzyk;
        } else {
            if (zzcu > this.zzxm - this.zzxn) {
                throw zzgs.zzgp();
            }
            Object obj = new byte[zzcu];
            System.arraycopy(this.buffer, this.zzxn, obj, 0, zzcu);
            this.zzxn = zzcu + this.zzxn;
            return obj;
        }
    }

    public final String readString() {
        int zzcu = zzcu();
        if (zzcu < 0) {
            throw zzgs.zzgq();
        } else if (zzcu > this.zzxm - this.zzxn) {
            throw zzgs.zzgp();
        } else {
            String str = new String(this.buffer, this.zzxn, zzcu, zzgr.UTF_8);
            this.zzxn = zzcu + this.zzxn;
            return str;
        }
    }

    public final <T extends zzdb<T, ?>> T zza(zzer<T> zzer) {
        try {
            if (this.zzxo == null) {
                this.zzxo = zzcd.zzd(this.buffer, this.zzxk, this.zzxl);
            }
            int zzct = this.zzxo.zzct();
            int i = this.zzxn - this.zzxk;
            if (zzct > i) {
                throw new IOException(String.format("CodedInputStream read ahead of CodedInputByteBufferNano: %s > %s", new Object[]{Integer.valueOf(zzct), Integer.valueOf(i)}));
            }
            this.zzxo.zzr(i - zzct);
            this.zzxo.zzo(this.zzmr - this.zzmq);
            zzdb zzdb = (zzdb) this.zzxo.zza(zzer, zzco.zzdl());
            zzn(this.zzmz);
            return zzdb;
        } catch (Exception e) {
            throw new zzgs(TtmlNode.ANONYMOUS_REGION_ID, e);
        }
    }

    public final void zzay(int i) {
        zzs(i, this.zzmz);
    }

    public final void zzb(zzgt zzgt) {
        int zzcu = zzcu();
        if (this.zzmq >= this.zzmr) {
            throw new zzgs("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
        }
        zzcu = zzp(zzcu);
        this.zzmq++;
        zzgt.zza(this);
        zzm(0);
        this.zzmq--;
        zzq(zzcu);
    }

    public final int zzcc() {
        if (this.zzxn == this.zzxm) {
            this.zzmz = 0;
            return 0;
        }
        this.zzmz = zzcu();
        if (this.zzmz != 0) {
            return this.zzmz;
        }
        throw new zzgs("Protocol message contained an invalid tag (zero).");
    }

    public final long zzce() {
        return zzcv();
    }

    public final boolean zzci() {
        return zzcu() != 0;
    }

    public final int zzcu() {
        byte zzcz = zzcz();
        if (zzcz >= (byte) 0) {
            return zzcz;
        }
        int i = zzcz & 127;
        byte zzcz2 = zzcz();
        if (zzcz2 >= (byte) 0) {
            return i | (zzcz2 << 7);
        }
        i |= (zzcz2 & 127) << 7;
        zzcz2 = zzcz();
        if (zzcz2 >= (byte) 0) {
            return i | (zzcz2 << 14);
        }
        i |= (zzcz2 & 127) << 14;
        zzcz2 = zzcz();
        if (zzcz2 >= (byte) 0) {
            return i | (zzcz2 << 21);
        }
        i |= (zzcz2 & 127) << 21;
        zzcz2 = zzcz();
        i |= zzcz2 << 28;
        if (zzcz2 >= (byte) 0) {
            return i;
        }
        for (int i2 = 0; i2 < 5; i2++) {
            if (zzcz() >= (byte) 0) {
                return i;
            }
        }
        throw zzgs.zzgr();
    }

    public final long zzcv() {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzcz = zzcz();
            j |= ((long) (zzcz & 127)) << i;
            if ((zzcz & 128) == 0) {
                return j;
            }
        }
        throw zzgs.zzgr();
    }

    public final int zzgl() {
        if (this.zzna == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzna - this.zzxn;
    }

    public final void zzm(int i) {
        if (this.zzmz != i) {
            throw new zzgs("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final boolean zzn(int i) {
        switch (i & 7) {
            case 0:
                zzcu();
                return true;
            case 1:
                zzcz();
                zzcz();
                zzcz();
                zzcz();
                zzcz();
                zzcz();
                zzcz();
                zzcz();
                return true;
            case 2:
                zzr(zzcu());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzcz();
                zzcz();
                zzcz();
                zzcz();
                return true;
            default:
                throw new zzgs("Protocol message tag had invalid wire type.");
        }
        int zzcc;
        do {
            zzcc = zzcc();
            if (zzcc != 0) {
            }
            zzm(((i >>> 3) << 3) | 4);
            return true;
        } while (zzn(zzcc));
        zzm(((i >>> 3) << 3) | 4);
        return true;
    }

    public final int zzp(int i) {
        if (i < 0) {
            throw zzgs.zzgq();
        }
        int i2 = this.zzxn + i;
        int i3 = this.zzna;
        if (i2 > i3) {
            throw zzgs.zzgp();
        }
        this.zzna = i2;
        zzcy();
        return i3;
    }

    public final void zzq(int i) {
        this.zzna = i;
        zzcy();
    }

    public final byte[] zzr(int i, int i2) {
        if (i2 == 0) {
            return zzgw.zzyk;
        }
        Object obj = new byte[i2];
        System.arraycopy(this.buffer, this.zzxk + i, obj, 0, i2);
        return obj;
    }

    final void zzs(int i, int i2) {
        if (i > this.zzxn - this.zzxk) {
            throw new IllegalArgumentException("Position " + i + " is beyond current " + (this.zzxn - this.zzxk));
        } else if (i < 0) {
            throw new IllegalArgumentException("Bad position " + i);
        } else {
            this.zzxn = this.zzxk + i;
            this.zzmz = i2;
        }
    }
}
