package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

final class zzfhe extends zzfhb {
    private final byte[] buffer;
    private int pos;
    private int zzpoh;
    private int zzpoj;
    private int zzpok;
    private final InputStream zzpol;
    private int zzpom;
    private int zzpon;
    private zzfhf zzpoo;

    private zzfhe(InputStream inputStream, int i) {
        super();
        this.zzpok = Integer.MAX_VALUE;
        this.zzpoo = null;
        zzfhz.zzc(inputStream, "input");
        this.zzpol = inputStream;
        this.buffer = new byte[i];
        this.zzpom = 0;
        this.pos = 0;
        this.zzpon = 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final long zzcyr() throws java.io.IOException {
        /*
        r10 = this;
        r8 = 0;
        r0 = r10.pos;
        r1 = r10.zzpom;
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
        r2 = r10.zzpom;
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
        r0 = r10.zzcyn();
        goto L_0x0013;
    L_0x00ba:
        r2 = r3;
        goto L_0x0027;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfhe.zzcyr():long");
    }

    private final int zzcys() throws IOException {
        int i = this.pos;
        if (this.zzpom - i < 4) {
            zzlm(4);
            i = this.pos;
        }
        byte[] bArr = this.buffer;
        this.pos = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8)) | ((bArr[i + 2] & 255) << 16));
    }

    private final long zzcyt() throws IOException {
        int i = this.pos;
        if (this.zzpom - i < 8) {
            zzlm(8);
            i = this.pos;
        }
        byte[] bArr = this.buffer;
        this.pos = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((((((((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8)) | ((((long) bArr[i + 2]) & 255) << 16)) | ((((long) bArr[i + 3]) & 255) << 24)) | ((((long) bArr[i + 4]) & 255) << 32)) | ((((long) bArr[i + 5]) & 255) << 40)) | ((((long) bArr[i + 6]) & 255) << 48));
    }

    private final void zzcyu() {
        this.zzpom += this.zzpoh;
        int i = this.zzpon + this.zzpom;
        if (i > this.zzpok) {
            this.zzpoh = i - this.zzpok;
            this.zzpom -= this.zzpoh;
            return;
        }
        this.zzpoh = 0;
    }

    private final byte zzcyv() throws IOException {
        if (this.pos == this.zzpom) {
            zzlm(1);
        }
        byte[] bArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        return bArr[i];
    }

    private final void zzlm(int i) throws IOException {
        if (!zzln(i)) {
            if (i > (this.zzpoe - this.zzpon) - this.pos) {
                throw zzfie.zzdal();
            }
            throw zzfie.zzdae();
        }
    }

    private final boolean zzln(int i) throws IOException {
        while (this.pos + i > this.zzpom) {
            if (i > (this.zzpoe - this.zzpon) - this.pos || (this.zzpon + this.pos) + i > this.zzpok) {
                return false;
            }
            int i2 = this.pos;
            if (i2 > 0) {
                if (this.zzpom > i2) {
                    System.arraycopy(this.buffer, i2, this.buffer, 0, this.zzpom - i2);
                }
                this.zzpon += i2;
                this.zzpom -= i2;
                this.pos = 0;
            }
            i2 = this.zzpol.read(this.buffer, this.zzpom, Math.min(this.buffer.length - this.zzpom, (this.zzpoe - this.zzpon) - this.zzpom));
            if (i2 == 0 || i2 < -1 || i2 > this.buffer.length) {
                throw new IllegalStateException("InputStream#read(byte[]) returned invalid result: " + i2 + "\nThe InputStream implementation is buggy.");
            } else if (i2 <= 0) {
                return false;
            } else {
                this.zzpom = i2 + this.zzpom;
                zzcyu();
                if (this.zzpom >= i) {
                    return true;
                }
            }
        }
        throw new IllegalStateException("refillBuffer() called when " + i + " bytes were already available in buffer");
    }

    private final byte[] zzlo(int i) throws IOException {
        byte[] zzlp = zzlp(i);
        if (zzlp != null) {
            return zzlp;
        }
        int i2 = this.pos;
        int i3 = this.zzpom - this.pos;
        this.zzpon += this.zzpom;
        this.pos = 0;
        this.zzpom = 0;
        List<byte[]> zzlq = zzlq(i - i3);
        Object obj = new byte[i];
        System.arraycopy(this.buffer, i2, obj, 0, i3);
        i2 = i3;
        for (byte[] zzlp2 : zzlq) {
            System.arraycopy(zzlp2, 0, obj, i2, zzlp2.length);
            i2 = zzlp2.length + i2;
        }
        return obj;
    }

    private final byte[] zzlp(int i) throws IOException {
        if (i == 0) {
            return zzfhz.EMPTY_BYTE_ARRAY;
        }
        if (i < 0) {
            throw zzfie.zzdaf();
        }
        int i2 = (this.zzpon + this.pos) + i;
        if (i2 - this.zzpoe > 0) {
            throw zzfie.zzdal();
        } else if (i2 > this.zzpok) {
            zzlk((this.zzpok - this.zzpon) - this.pos);
            throw zzfie.zzdae();
        } else {
            i2 = this.zzpom - this.pos;
            int i3 = i - i2;
            if (i3 >= 4096 && i3 > this.zzpol.available()) {
                return null;
            }
            Object obj = new byte[i];
            System.arraycopy(this.buffer, this.pos, obj, 0, i2);
            this.zzpon += this.zzpom;
            this.pos = 0;
            this.zzpom = 0;
            while (i2 < obj.length) {
                int read = this.zzpol.read(obj, i2, i - i2);
                if (read == -1) {
                    throw zzfie.zzdae();
                }
                this.zzpon += read;
                i2 += read;
            }
            return obj;
        }
    }

    private final List<byte[]> zzlq(int i) throws IOException {
        List<byte[]> arrayList = new ArrayList();
        while (i > 0) {
            Object obj = new byte[Math.min(i, 4096)];
            int i2 = 0;
            while (i2 < obj.length) {
                int read = this.zzpol.read(obj, i2, obj.length - i2);
                if (read == -1) {
                    throw zzfie.zzdae();
                }
                this.zzpon += read;
                i2 += read;
            }
            i -= obj.length;
            arrayList.add(obj);
        }
        return arrayList;
    }

    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(zzcyt());
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(zzcys());
    }

    public final String readString() throws IOException {
        int zzcym = zzcym();
        String str;
        if (zzcym > 0 && zzcym <= this.zzpom - this.pos) {
            str = new String(this.buffer, this.pos, zzcym, zzfhz.UTF_8);
            this.pos = zzcym + this.pos;
            return str;
        } else if (zzcym == 0) {
            return "";
        } else {
            if (zzcym > this.zzpom) {
                return new String(zzlo(zzcym), zzfhz.UTF_8);
            }
            zzlm(zzcym);
            str = new String(this.buffer, this.pos, zzcym, zzfhz.UTF_8);
            this.pos = zzcym + this.pos;
            return str;
        }
    }

    public final <T extends zzfhu<T, ?>> T zza(T t, zzfhm zzfhm) throws IOException {
        int zzcym = zzcym();
        if (this.zzpoc >= this.zzpod) {
            throw zzfie.zzdak();
        }
        zzcym = zzli(zzcym);
        this.zzpoc++;
        T zza = zzfhu.zza((zzfhu) t, (zzfhb) this, zzfhm);
        zzlf(0);
        this.zzpoc--;
        zzlj(zzcym);
        return zza;
    }

    public final void zza(zzfjd zzfjd, zzfhm zzfhm) throws IOException {
        int zzcym = zzcym();
        if (this.zzpoc >= this.zzpod) {
            throw zzfie.zzdak();
        }
        zzcym = zzli(zzcym);
        this.zzpoc++;
        zzfjd.zzb(this, zzfhm);
        zzlf(0);
        this.zzpoc--;
        zzlj(zzcym);
    }

    public final int zzcxx() throws IOException {
        if (zzcyp()) {
            this.zzpoj = 0;
            return 0;
        }
        this.zzpoj = zzcym();
        if ((this.zzpoj >>> 3) != 0) {
            return this.zzpoj;
        }
        throw zzfie.zzdah();
    }

    public final long zzcxy() throws IOException {
        return zzcyr();
    }

    public final long zzcxz() throws IOException {
        return zzcyr();
    }

    public final int zzcya() throws IOException {
        return zzcym();
    }

    public final long zzcyb() throws IOException {
        return zzcyt();
    }

    public final int zzcyc() throws IOException {
        return zzcys();
    }

    public final boolean zzcyd() throws IOException {
        return zzcyr() != 0;
    }

    public final String zzcye() throws IOException {
        byte[] bArr;
        int zzcym = zzcym();
        int i = this.pos;
        if (zzcym <= this.zzpom - i && zzcym > 0) {
            bArr = this.buffer;
            this.pos = i + zzcym;
        } else if (zzcym == 0) {
            return "";
        } else {
            if (zzcym <= this.zzpom) {
                zzlm(zzcym);
                byte[] bArr2 = this.buffer;
                this.pos = zzcym;
                bArr = bArr2;
                i = 0;
            } else {
                bArr = zzlo(zzcym);
                i = 0;
            }
        }
        if (zzfks.zzl(bArr, i, i + zzcym)) {
            return new String(bArr, i, zzcym, zzfhz.UTF_8);
        }
        throw zzfie.zzdam();
    }

    public final zzfgs zzcyf() throws IOException {
        int zzcym = zzcym();
        if (zzcym <= this.zzpom - this.pos && zzcym > 0) {
            zzfgs zzf = zzfgs.zzf(this.buffer, this.pos, zzcym);
            this.pos = zzcym + this.pos;
            return zzf;
        } else if (zzcym == 0) {
            return zzfgs.zzpnw;
        } else {
            byte[] zzlp = zzlp(zzcym);
            if (zzlp != null) {
                return zzfgs.zzba(zzlp);
            }
            int i = this.pos;
            int i2 = this.zzpom - this.pos;
            this.zzpon += this.zzpom;
            this.pos = 0;
            this.zzpom = 0;
            List<byte[]> zzlq = zzlq(zzcym - i2);
            Iterable arrayList = new ArrayList(zzlq.size() + 1);
            arrayList.add(zzfgs.zzf(this.buffer, i, i2));
            for (byte[] zzlp2 : zzlq) {
                arrayList.add(zzfgs.zzba(zzlp2));
            }
            return zzfgs.zzg(arrayList);
        }
    }

    public final int zzcyg() throws IOException {
        return zzcym();
    }

    public final int zzcyh() throws IOException {
        return zzcym();
    }

    public final int zzcyi() throws IOException {
        return zzcys();
    }

    public final long zzcyj() throws IOException {
        return zzcyt();
    }

    public final int zzcyk() throws IOException {
        return zzfhb.zzll(zzcym());
    }

    public final long zzcyl() throws IOException {
        return zzfhb.zzct(zzcyr());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int zzcym() throws java.io.IOException {
        /*
        r5 = this;
        r0 = r5.pos;
        r1 = r5.zzpom;
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
        r1 = r5.zzpom;
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
        r0 = r5.zzcyn();
        r0 = (int) r0;
        goto L_0x0010;
    L_0x0072:
        r1 = r2;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfhe.zzcym():int");
    }

    final long zzcyn() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzcyv = zzcyv();
            j |= ((long) (zzcyv & 127)) << i;
            if ((zzcyv & 128) == 0) {
                return j;
            }
        }
        throw zzfie.zzdag();
    }

    public final int zzcyo() {
        if (this.zzpok == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzpok - (this.zzpon + this.pos);
    }

    public final boolean zzcyp() throws IOException {
        return this.pos == this.zzpom && !zzln(1);
    }

    public final int zzcyq() {
        return this.zzpon + this.pos;
    }

    public final void zzlf(int i) throws zzfie {
        if (this.zzpoj != i) {
            throw zzfie.zzdai();
        }
    }

    public final boolean zzlg(int i) throws IOException {
        int i2 = 0;
        switch (i & 7) {
            case 0:
                if (this.zzpom - this.pos >= 10) {
                    while (i2 < 10) {
                        byte[] bArr = this.buffer;
                        int i3 = this.pos;
                        this.pos = i3 + 1;
                        if (bArr[i3] >= (byte) 0) {
                            return true;
                        }
                        i2++;
                    }
                    throw zzfie.zzdag();
                }
                while (i2 < 10) {
                    if (zzcyv() >= (byte) 0) {
                        return true;
                    }
                    i2++;
                }
                throw zzfie.zzdag();
            case 1:
                zzlk(8);
                return true;
            case 2:
                zzlk(zzcym());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzlk(4);
                return true;
            default:
                throw zzfie.zzdaj();
        }
        do {
            i2 = zzcxx();
            if (i2 != 0) {
            }
            zzlf(((i >>> 3) << 3) | 4);
            return true;
        } while (zzlg(i2));
        zzlf(((i >>> 3) << 3) | 4);
        return true;
    }

    public final int zzli(int i) throws zzfie {
        if (i < 0) {
            throw zzfie.zzdaf();
        }
        int i2 = (this.zzpon + this.pos) + i;
        int i3 = this.zzpok;
        if (i2 > i3) {
            throw zzfie.zzdae();
        }
        this.zzpok = i2;
        zzcyu();
        return i3;
    }

    public final void zzlj(int i) {
        this.zzpok = i;
        zzcyu();
    }

    public final void zzlk(int i) throws IOException {
        if (i <= this.zzpom - this.pos && i >= 0) {
            this.pos += i;
        } else if (i < 0) {
            throw zzfie.zzdaf();
        } else if ((this.zzpon + this.pos) + i > this.zzpok) {
            zzlk((this.zzpok - this.zzpon) - this.pos);
            throw zzfie.zzdae();
        } else {
            int i2 = this.zzpom - this.pos;
            this.pos = this.zzpom;
            zzlm(1);
            while (i - i2 > this.zzpom) {
                i2 += this.zzpom;
                this.pos = this.zzpom;
                zzlm(1);
            }
            this.pos = i - i2;
        }
    }
}
