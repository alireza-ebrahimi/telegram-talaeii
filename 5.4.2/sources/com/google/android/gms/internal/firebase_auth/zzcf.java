package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;

final class zzcf extends zzcd {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzmw;
    private int zzmx;
    private int zzmy;
    private int zzmz;
    private int zzna;

    private zzcf(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzna = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i + i2;
        this.pos = i;
        this.zzmy = this.pos;
        this.zzmw = z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zzcu() {
        /*
        r5 = this;
        r0 = r5.pos;
        r1 = r5.limit;
        if (r1 == r0) goto L_0x006c;
    L_0x0006:
        r3 = r5.buffer;
        r2 = r0 + 1;
        r0 = r3[r0];
        if (r0 < 0) goto L_0x0011;
    L_0x000e:
        r5.pos = r2;
    L_0x0010:
        return r0;
    L_0x0011:
        r1 = r5.limit;
        r1 = r1 - r2;
        r4 = 9;
        if (r1 < r4) goto L_0x006c;
    L_0x0018:
        r1 = r2 + 1;
        r2 = r3[r2];
        r2 = r2 << 7;
        r0 = r0 ^ r2;
        if (r0 >= 0) goto L_0x0026;
    L_0x0021:
        r0 = r0 ^ -128;
    L_0x0023:
        r5.pos = r1;
        goto L_0x0010;
    L_0x0026:
        r2 = r1 + 1;
        r1 = r3[r1];
        r1 = r1 << 14;
        r0 = r0 ^ r1;
        if (r0 < 0) goto L_0x0033;
    L_0x002f:
        r0 = r0 ^ 16256;
        r1 = r2;
        goto L_0x0023;
    L_0x0033:
        r1 = r2 + 1;
        r2 = r3[r2];
        r2 = r2 << 21;
        r0 = r0 ^ r2;
        if (r0 >= 0) goto L_0x0041;
    L_0x003c:
        r2 = -2080896; // 0xffffffffffe03f80 float:NaN double:NaN;
        r0 = r0 ^ r2;
        goto L_0x0023;
    L_0x0041:
        r2 = r1 + 1;
        r1 = r3[r1];
        r4 = r1 << 28;
        r0 = r0 ^ r4;
        r4 = 266354560; // 0xfe03f80 float:2.2112565E-29 double:1.315966377E-315;
        r0 = r0 ^ r4;
        if (r1 >= 0) goto L_0x0072;
    L_0x004e:
        r1 = r2 + 1;
        r2 = r3[r2];
        if (r2 >= 0) goto L_0x0023;
    L_0x0054:
        r2 = r1 + 1;
        r1 = r3[r1];
        if (r1 >= 0) goto L_0x0072;
    L_0x005a:
        r1 = r2 + 1;
        r2 = r3[r2];
        if (r2 >= 0) goto L_0x0023;
    L_0x0060:
        r2 = r1 + 1;
        r1 = r3[r1];
        if (r1 >= 0) goto L_0x0072;
    L_0x0066:
        r1 = r2 + 1;
        r2 = r3[r2];
        if (r2 >= 0) goto L_0x0023;
    L_0x006c:
        r0 = r5.zzcr();
        r0 = (int) r0;
        goto L_0x0010;
    L_0x0072:
        r1 = r2;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzcf.zzcu():int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final long zzcv() {
        /*
        r10 = this;
        r8 = 0;
        r0 = r10.pos;
        r1 = r10.limit;
        if (r1 == r0) goto L_0x00b4;
    L_0x0008:
        r4 = r10.buffer;
        r1 = r0 + 1;
        r0 = r4[r0];
        if (r0 < 0) goto L_0x0014;
    L_0x0010:
        r10.pos = r1;
        r0 = (long) r0;
    L_0x0013:
        return r0;
    L_0x0014:
        r2 = r10.limit;
        r2 = r2 - r1;
        r3 = 9;
        if (r2 < r3) goto L_0x00b4;
    L_0x001b:
        r2 = r1 + 1;
        r1 = r4[r1];
        r1 = r1 << 7;
        r0 = r0 ^ r1;
        if (r0 >= 0) goto L_0x002a;
    L_0x0024:
        r0 = r0 ^ -128;
        r0 = (long) r0;
    L_0x0027:
        r10.pos = r2;
        goto L_0x0013;
    L_0x002a:
        r3 = r2 + 1;
        r1 = r4[r2];
        r1 = r1 << 14;
        r0 = r0 ^ r1;
        if (r0 < 0) goto L_0x0038;
    L_0x0033:
        r0 = r0 ^ 16256;
        r0 = (long) r0;
        r2 = r3;
        goto L_0x0027;
    L_0x0038:
        r2 = r3 + 1;
        r1 = r4[r3];
        r1 = r1 << 21;
        r0 = r0 ^ r1;
        if (r0 >= 0) goto L_0x0047;
    L_0x0041:
        r1 = -2080896; // 0xffffffffffe03f80 float:NaN double:NaN;
        r0 = r0 ^ r1;
        r0 = (long) r0;
        goto L_0x0027;
    L_0x0047:
        r0 = (long) r0;
        r3 = r2 + 1;
        r2 = r4[r2];
        r6 = (long) r2;
        r2 = 28;
        r6 = r6 << r2;
        r0 = r0 ^ r6;
        r2 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r2 < 0) goto L_0x005b;
    L_0x0055:
        r4 = 266354560; // 0xfe03f80 float:2.2112565E-29 double:1.315966377E-315;
        r0 = r0 ^ r4;
        r2 = r3;
        goto L_0x0027;
    L_0x005b:
        r2 = r3 + 1;
        r3 = r4[r3];
        r6 = (long) r3;
        r3 = 35;
        r6 = r6 << r3;
        r0 = r0 ^ r6;
        r3 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r3 >= 0) goto L_0x006f;
    L_0x0068:
        r4 = -34093383808; // 0xfffffff80fe03f80 float:2.2112565E-29 double:NaN;
        r0 = r0 ^ r4;
        goto L_0x0027;
    L_0x006f:
        r3 = r2 + 1;
        r2 = r4[r2];
        r6 = (long) r2;
        r2 = 42;
        r6 = r6 << r2;
        r0 = r0 ^ r6;
        r2 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r2 < 0) goto L_0x0084;
    L_0x007c:
        r4 = 4363953127296; // 0x3f80fe03f80 float:2.2112565E-29 double:2.1560793202584E-311;
        r0 = r0 ^ r4;
        r2 = r3;
        goto L_0x0027;
    L_0x0084:
        r2 = r3 + 1;
        r3 = r4[r3];
        r6 = (long) r3;
        r3 = 49;
        r6 = r6 << r3;
        r0 = r0 ^ r6;
        r3 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r3 >= 0) goto L_0x0098;
    L_0x0091:
        r4 = -558586000294016; // 0xfffe03f80fe03f80 float:2.2112565E-29 double:NaN;
        r0 = r0 ^ r4;
        goto L_0x0027;
    L_0x0098:
        r3 = r2 + 1;
        r2 = r4[r2];
        r6 = (long) r2;
        r2 = 56;
        r6 = r6 << r2;
        r0 = r0 ^ r6;
        r6 = 71499008037633920; // 0xfe03f80fe03f80 float:2.2112565E-29 double:6.838959413692434E-304;
        r0 = r0 ^ r6;
        r2 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r2 >= 0) goto L_0x00ba;
    L_0x00ab:
        r2 = r3 + 1;
        r3 = r4[r3];
        r4 = (long) r3;
        r3 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
        if (r3 >= 0) goto L_0x0027;
    L_0x00b4:
        r0 = r10.zzcr();
        goto L_0x0013;
    L_0x00ba:
        r2 = r3;
        goto L_0x0027;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzcf.zzcv():long");
    }

    private final int zzcw() {
        int i = this.pos;
        if (this.limit - i < 4) {
            throw zzdh.zzee();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8)) | ((bArr[i + 2] & 255) << 16));
    }

    private final long zzcx() {
        int i = this.pos;
        if (this.limit - i < 8) {
            throw zzdh.zzee();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((((((((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8)) | ((((long) bArr[i + 2]) & 255) << 16)) | ((((long) bArr[i + 3]) & 255) << 24)) | ((((long) bArr[i + 4]) & 255) << 32)) | ((((long) bArr[i + 5]) & 255) << 40)) | ((((long) bArr[i + 6]) & 255) << 48));
    }

    private final void zzcy() {
        this.limit += this.zzmx;
        int i = this.limit - this.zzmy;
        if (i > this.zzna) {
            this.zzmx = i - this.zzna;
            this.limit -= this.zzmx;
            return;
        }
        this.zzmx = 0;
    }

    private final byte zzcz() {
        if (this.pos == this.limit) {
            throw zzdh.zzee();
        }
        byte[] bArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        return bArr[i];
    }

    public final double readDouble() {
        return Double.longBitsToDouble(zzcx());
    }

    public final float readFloat() {
        return Float.intBitsToFloat(zzcw());
    }

    public final String readString() {
        int zzcu = zzcu();
        if (zzcu > 0 && zzcu <= this.limit - this.pos) {
            String str = new String(this.buffer, this.pos, zzcu, zzdd.UTF_8);
            this.pos = zzcu + this.pos;
            return str;
        } else if (zzcu == 0) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        } else {
            if (zzcu < 0) {
                throw zzdh.zzef();
            }
            throw zzdh.zzee();
        }
    }

    public final <T extends zzeh> T zza(zzer<T> zzer, zzco zzco) {
        int zzcu = zzcu();
        if (this.zzmq >= this.zzmr) {
            throw zzdh.zzej();
        }
        int zzp = zzp(zzcu);
        this.zzmq++;
        zzeh zzeh = (zzeh) zzer.zza(this, zzco);
        zzm(0);
        this.zzmq--;
        zzq(zzp);
        return zzeh;
    }

    public final int zzcc() {
        if (zzcs()) {
            this.zzmz = 0;
            return 0;
        }
        this.zzmz = zzcu();
        if ((this.zzmz >>> 3) != 0) {
            return this.zzmz;
        }
        throw new zzdh("Protocol message contained an invalid tag (zero).");
    }

    public final long zzcd() {
        return zzcv();
    }

    public final long zzce() {
        return zzcv();
    }

    public final int zzcf() {
        return zzcu();
    }

    public final long zzcg() {
        return zzcx();
    }

    public final int zzch() {
        return zzcw();
    }

    public final boolean zzci() {
        return zzcv() != 0;
    }

    public final String zzcj() {
        int zzcu = zzcu();
        if (zzcu <= 0 || zzcu > this.limit - this.pos) {
            if (zzcu == 0) {
                return TtmlNode.ANONYMOUS_REGION_ID;
            }
            if (zzcu <= 0) {
                throw zzdh.zzef();
            }
            throw zzdh.zzee();
        } else if (zzfx.zzf(this.buffer, this.pos, this.pos + zzcu)) {
            int i = this.pos;
            this.pos += zzcu;
            return new String(this.buffer, i, zzcu, zzdd.UTF_8);
        } else {
            throw new zzdh("Protocol message had invalid UTF-8.");
        }
    }

    public final zzbu zzck() {
        int zzcu = zzcu();
        if (zzcu > 0 && zzcu <= this.limit - this.pos) {
            zzbu zzb = zzbu.zzb(this.buffer, this.pos, zzcu);
            this.pos = zzcu + this.pos;
            return zzb;
        } else if (zzcu == 0) {
            return zzbu.zzmi;
        } else {
            byte[] copyOfRange;
            if (zzcu > 0 && zzcu <= this.limit - this.pos) {
                int i = this.pos;
                this.pos = zzcu + this.pos;
                copyOfRange = Arrays.copyOfRange(this.buffer, i, this.pos);
            } else if (zzcu > 0) {
                throw zzdh.zzee();
            } else if (zzcu == 0) {
                copyOfRange = zzdd.EMPTY_BYTE_ARRAY;
            } else {
                throw zzdh.zzef();
            }
            return zzbu.zza(copyOfRange);
        }
    }

    public final int zzcl() {
        return zzcu();
    }

    public final int zzcm() {
        return zzcu();
    }

    public final int zzcn() {
        return zzcw();
    }

    public final long zzco() {
        return zzcx();
    }

    public final int zzcp() {
        int zzcu = zzcu();
        return (-(zzcu & 1)) ^ (zzcu >>> 1);
    }

    public final long zzcq() {
        long zzcv = zzcv();
        return (-(zzcv & 1)) ^ (zzcv >>> 1);
    }

    final long zzcr() {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzcz = zzcz();
            j |= ((long) (zzcz & 127)) << i;
            if ((zzcz & 128) == 0) {
                return j;
            }
        }
        throw zzdh.zzeg();
    }

    public final boolean zzcs() {
        return this.pos == this.limit;
    }

    public final int zzct() {
        return this.pos - this.zzmy;
    }

    public final void zzm(int i) {
        if (this.zzmz != i) {
            throw zzdh.zzeh();
        }
    }

    public final boolean zzn(int i) {
        int i2 = 0;
        switch (i & 7) {
            case 0:
                if (this.limit - this.pos >= 10) {
                    while (i2 < 10) {
                        byte[] bArr = this.buffer;
                        int i3 = this.pos;
                        this.pos = i3 + 1;
                        if (bArr[i3] >= (byte) 0) {
                            return true;
                        }
                        i2++;
                    }
                    throw zzdh.zzeg();
                }
                while (i2 < 10) {
                    if (zzcz() >= (byte) 0) {
                        return true;
                    }
                    i2++;
                }
                throw zzdh.zzeg();
            case 1:
                zzr(8);
                return true;
            case 2:
                zzr(zzcu());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzr(4);
                return true;
            default:
                throw zzdh.zzei();
        }
        do {
            i2 = zzcc();
            if (i2 != 0) {
            }
            zzm(((i >>> 3) << 3) | 4);
            return true;
        } while (zzn(i2));
        zzm(((i >>> 3) << 3) | 4);
        return true;
    }

    public final int zzp(int i) {
        if (i < 0) {
            throw zzdh.zzef();
        }
        int zzct = zzct() + i;
        int i2 = this.zzna;
        if (zzct > i2) {
            throw zzdh.zzee();
        }
        this.zzna = zzct;
        zzcy();
        return i2;
    }

    public final void zzq(int i) {
        this.zzna = i;
        zzcy();
    }

    public final void zzr(int i) {
        if (i >= 0 && i <= this.limit - this.pos) {
            this.pos += i;
        } else if (i < 0) {
            throw zzdh.zzef();
        } else {
            throw zzdh.zzee();
        }
    }
}
