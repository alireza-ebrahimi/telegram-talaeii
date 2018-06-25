package com.google.android.gms.internal.firebase_auth;

import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class zzci extends zzbt {
    private static final Logger logger = Logger.getLogger(zzci.class.getName());
    private static final boolean zznf = zzfv.zzgc();
    zzck zzng = this;

    static class zza extends zzci {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        zza(byte[] bArr, int i, int i2) {
            super();
            if (bArr == null) {
                throw new NullPointerException("buffer");
            } else if (((i | i2) | (bArr.length - (i + i2))) < 0) {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)}));
            } else {
                this.buffer = bArr;
                this.offset = i;
                this.position = i;
                this.limit = i + i2;
            }
        }

        public void flush() {
        }

        public final void write(byte[] bArr, int i, int i2) {
            try {
                System.arraycopy(bArr, i, this.buffer, this.position, i2);
                this.position += i2;
            } catch (Throwable e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(i2)}), e);
            }
        }

        public final void zza(byte b) {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = b;
            } catch (Throwable e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }

        public final void zza(int i, long j) {
            zzb(i, 0);
            zza(j);
        }

        public final void zza(int i, zzbu zzbu) {
            zzb(i, 2);
            zza(zzbu);
        }

        public final void zza(int i, zzeh zzeh) {
            zzb(i, 2);
            zzb(zzeh);
        }

        final void zza(int i, zzeh zzeh, zzev zzev) {
            zzb(i, 2);
            zzbn zzbn = (zzbn) zzeh;
            int zzbp = zzbn.zzbp();
            if (zzbp == -1) {
                zzbp = zzev.zzo(zzbn);
                zzbn.zzg(zzbp);
            }
            zzx(zzbp);
            zzev.zza(zzeh, this.zzng);
        }

        public final void zza(int i, String str) {
            zzb(i, 2);
            zzal(str);
        }

        public final void zza(long j) {
            byte[] bArr;
            int i;
            if (!zzci.zznf || zzdc() < 10) {
                while ((j & -128) != 0) {
                    try {
                        bArr = this.buffer;
                        i = this.position;
                        this.position = i + 1;
                        bArr[i] = (byte) ((((int) j) & 127) | 128);
                        j >>>= 7;
                    } catch (Throwable e) {
                        throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
                    }
                }
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) j);
                return;
            }
            while ((j & -128) != 0) {
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                zzfv.zza(bArr, (long) i, (byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            bArr = this.buffer;
            i = this.position;
            this.position = i + 1;
            zzfv.zza(bArr, (long) i, (byte) ((int) j));
        }

        public final void zza(zzbu zzbu) {
            zzx(zzbu.size());
            zzbu.zza((zzbt) this);
        }

        final void zza(zzeh zzeh, zzev zzev) {
            zzbn zzbn = (zzbn) zzeh;
            int zzbp = zzbn.zzbp();
            if (zzbp == -1) {
                zzbp = zzev.zzo(zzbn);
                zzbn.zzg(zzbp);
            }
            zzx(zzbp);
            zzev.zza(zzeh, this.zzng);
        }

        public final void zza(byte[] bArr, int i, int i2) {
            write(bArr, i, i2);
        }

        public final void zzal(String str) {
            int i = this.position;
            try {
                int zzac = zzci.zzac(str.length() * 3);
                int zzac2 = zzci.zzac(str.length());
                if (zzac2 == zzac) {
                    this.position = i + zzac2;
                    zzac = zzfx.zza(str, this.buffer, this.position, zzdc());
                    this.position = i;
                    zzx((zzac - i) - zzac2);
                    this.position = zzac;
                    return;
                }
                zzx(zzfx.zza(str));
                this.position = zzfx.zza(str, this.buffer, this.position, zzdc());
            } catch (zzga e) {
                this.position = i;
                zza(str, e);
            } catch (Throwable e2) {
                throw new zzc(e2);
            }
        }

        public final void zzb(int i, int i2) {
            zzx((i << 3) | i2);
        }

        public final void zzb(int i, zzbu zzbu) {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzbu);
            zzb(1, 4);
        }

        public final void zzb(int i, zzeh zzeh) {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzeh);
            zzb(1, 4);
        }

        public final void zzb(int i, boolean z) {
            int i2 = 0;
            zzb(i, 0);
            if (z) {
                i2 = 1;
            }
            zza((byte) i2);
        }

        public final void zzb(zzeh zzeh) {
            zzx(zzeh.zzdq());
            zzeh.zzb(this);
        }

        public final void zzc(int i, int i2) {
            zzb(i, 0);
            zzw(i2);
        }

        public final void zzc(int i, long j) {
            zzb(i, 1);
            zzc(j);
        }

        public final void zzc(long j) {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) j);
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 8));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 16));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 24));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 32));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 40));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 48));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 56));
            } catch (Throwable e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }

        public final void zzd(int i, int i2) {
            zzb(i, 0);
            zzx(i2);
        }

        public final int zzdc() {
            return this.limit - this.position;
        }

        public final int zzde() {
            return this.position - this.offset;
        }

        public final void zze(byte[] bArr, int i, int i2) {
            zzx(i2);
            write(bArr, 0, i2);
        }

        public final void zzf(int i, int i2) {
            zzb(i, 5);
            zzz(i2);
        }

        public final void zzw(int i) {
            if (i >= 0) {
                zzx(i);
            } else {
                zza((long) i);
            }
        }

        public final void zzx(int i) {
            byte[] bArr;
            int i2;
            if (!zzci.zznf || zzdc() < 10) {
                while ((i & -128) != 0) {
                    try {
                        bArr = this.buffer;
                        i2 = this.position;
                        this.position = i2 + 1;
                        bArr[i2] = (byte) ((i & 127) | 128);
                        i >>>= 7;
                    } catch (Throwable e) {
                        throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
                    }
                }
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) i;
                return;
            }
            while ((i & -128) != 0) {
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                zzfv.zza(bArr, (long) i2, (byte) ((i & 127) | 128));
                i >>>= 7;
            }
            bArr = this.buffer;
            i2 = this.position;
            this.position = i2 + 1;
            zzfv.zza(bArr, (long) i2, (byte) i);
        }

        public final void zzz(int i) {
            try {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) i;
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) (i >> 8);
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) (i >> 16);
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = i >> 24;
            } catch (Throwable e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }
    }

    static final class zzb extends zza {
        private final ByteBuffer zznh;
        private int zzni;

        zzb(ByteBuffer byteBuffer) {
            super(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            this.zznh = byteBuffer;
            this.zzni = byteBuffer.position();
        }

        public final void flush() {
            this.zznh.position(this.zzni + zzde());
        }
    }

    public static class zzc extends IOException {
        zzc() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        zzc(String str) {
            String valueOf = String.valueOf("CodedOutputStream was writing to a flat byte array and ran out of space.: ");
            String valueOf2 = String.valueOf(str);
            super(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        }

        zzc(String str, Throwable th) {
            String valueOf = String.valueOf("CodedOutputStream was writing to a flat byte array and ran out of space.: ");
            String valueOf2 = String.valueOf(str);
            super(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), th);
        }

        zzc(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }
    }

    static final class zzd extends zzci {
        private final int zzni;
        private final ByteBuffer zznj;
        private final ByteBuffer zznk;

        zzd(ByteBuffer byteBuffer) {
            super();
            this.zznj = byteBuffer;
            this.zznk = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.zzni = byteBuffer.position();
        }

        private final void zzan(String str) {
            try {
                zzfx.zza(str, this.zznk);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void flush() {
            this.zznj.position(this.zznk.position());
        }

        public final void write(byte[] bArr, int i, int i2) {
            try {
                this.zznk.put(bArr, i, i2);
            } catch (Throwable e) {
                throw new zzc(e);
            } catch (Throwable e2) {
                throw new zzc(e2);
            }
        }

        public final void zza(byte b) {
            try {
                this.zznk.put(b);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void zza(int i, long j) {
            zzb(i, 0);
            zza(j);
        }

        public final void zza(int i, zzbu zzbu) {
            zzb(i, 2);
            zza(zzbu);
        }

        public final void zza(int i, zzeh zzeh) {
            zzb(i, 2);
            zzb(zzeh);
        }

        final void zza(int i, zzeh zzeh, zzev zzev) {
            zzb(i, 2);
            zza(zzeh, zzev);
        }

        public final void zza(int i, String str) {
            zzb(i, 2);
            zzal(str);
        }

        public final void zza(long j) {
            while ((-128 & j) != 0) {
                this.zznk.put((byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            try {
                this.zznk.put((byte) ((int) j));
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void zza(zzbu zzbu) {
            zzx(zzbu.size());
            zzbu.zza((zzbt) this);
        }

        final void zza(zzeh zzeh, zzev zzev) {
            zzbn zzbn = (zzbn) zzeh;
            int zzbp = zzbn.zzbp();
            if (zzbp == -1) {
                zzbp = zzev.zzo(zzbn);
                zzbn.zzg(zzbp);
            }
            zzx(zzbp);
            zzev.zza(zzeh, this.zzng);
        }

        public final void zza(byte[] bArr, int i, int i2) {
            write(bArr, i, i2);
        }

        public final void zzal(String str) {
            int position = this.zznk.position();
            try {
                int zzac = zzci.zzac(str.length() * 3);
                int zzac2 = zzci.zzac(str.length());
                if (zzac2 == zzac) {
                    zzac = this.zznk.position() + zzac2;
                    this.zznk.position(zzac);
                    zzan(str);
                    zzac2 = this.zznk.position();
                    this.zznk.position(position);
                    zzx(zzac2 - zzac);
                    this.zznk.position(zzac2);
                    return;
                }
                zzx(zzfx.zza(str));
                zzan(str);
            } catch (zzga e) {
                this.zznk.position(position);
                zza(str, e);
            } catch (Throwable e2) {
                throw new zzc(e2);
            }
        }

        public final void zzb(int i, int i2) {
            zzx((i << 3) | i2);
        }

        public final void zzb(int i, zzbu zzbu) {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzbu);
            zzb(1, 4);
        }

        public final void zzb(int i, zzeh zzeh) {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzeh);
            zzb(1, 4);
        }

        public final void zzb(int i, boolean z) {
            int i2 = 0;
            zzb(i, 0);
            if (z) {
                i2 = 1;
            }
            zza((byte) i2);
        }

        public final void zzb(zzeh zzeh) {
            zzx(zzeh.zzdq());
            zzeh.zzb(this);
        }

        public final void zzc(int i, int i2) {
            zzb(i, 0);
            zzw(i2);
        }

        public final void zzc(int i, long j) {
            zzb(i, 1);
            zzc(j);
        }

        public final void zzc(long j) {
            try {
                this.zznk.putLong(j);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void zzd(int i, int i2) {
            zzb(i, 0);
            zzx(i2);
        }

        public final int zzdc() {
            return this.zznk.remaining();
        }

        public final void zze(byte[] bArr, int i, int i2) {
            zzx(i2);
            write(bArr, 0, i2);
        }

        public final void zzf(int i, int i2) {
            zzb(i, 5);
            zzz(i2);
        }

        public final void zzw(int i) {
            if (i >= 0) {
                zzx(i);
            } else {
                zza((long) i);
            }
        }

        public final void zzx(int i) {
            while ((i & -128) != 0) {
                this.zznk.put((byte) ((i & 127) | 128));
                i >>>= 7;
            }
            try {
                this.zznk.put((byte) i);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void zzz(int i) {
            try {
                this.zznk.putInt(i);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }
    }

    static final class zze extends zzci {
        private final ByteBuffer zznj;
        private final ByteBuffer zznk;
        private final long zznl;
        private final long zznm;
        private final long zznn;
        private final long zzno = (this.zznn - 10);
        private long zznp = this.zznm;

        zze(ByteBuffer byteBuffer) {
            super();
            this.zznj = byteBuffer;
            this.zznk = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.zznl = zzfv.zzb(byteBuffer);
            this.zznm = this.zznl + ((long) byteBuffer.position());
            this.zznn = this.zznl + ((long) byteBuffer.limit());
        }

        private final void zzj(long j) {
            this.zznk.position((int) (j - this.zznl));
        }

        public final void flush() {
            this.zznj.position((int) (this.zznp - this.zznl));
        }

        public final void write(byte[] bArr, int i, int i2) {
            if (bArr != null && i >= 0 && i2 >= 0 && bArr.length - i2 >= i && this.zznn - ((long) i2) >= this.zznp) {
                zzfv.zza(bArr, (long) i, this.zznp, (long) i2);
                this.zznp += (long) i2;
            } else if (bArr == null) {
                throw new NullPointerException(C1797b.VALUE);
            } else {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.zznp), Long.valueOf(this.zznn), Integer.valueOf(i2)}));
            }
        }

        public final void zza(byte b) {
            if (this.zznp >= this.zznn) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.zznp), Long.valueOf(this.zznn), Integer.valueOf(1)}));
            }
            long j = this.zznp;
            this.zznp = 1 + j;
            zzfv.zza(j, b);
        }

        public final void zza(int i, long j) {
            zzb(i, 0);
            zza(j);
        }

        public final void zza(int i, zzbu zzbu) {
            zzb(i, 2);
            zza(zzbu);
        }

        public final void zza(int i, zzeh zzeh) {
            zzb(i, 2);
            zzb(zzeh);
        }

        final void zza(int i, zzeh zzeh, zzev zzev) {
            zzb(i, 2);
            zza(zzeh, zzev);
        }

        public final void zza(int i, String str) {
            zzb(i, 2);
            zzal(str);
        }

        public final void zza(long j) {
            long j2;
            if (this.zznp <= this.zzno) {
                while ((j & -128) != 0) {
                    j2 = this.zznp;
                    this.zznp = j2 + 1;
                    zzfv.zza(j2, (byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                }
                j2 = this.zznp;
                this.zznp = j2 + 1;
                zzfv.zza(j2, (byte) ((int) j));
                return;
            }
            while (this.zznp < this.zznn) {
                if ((j & -128) == 0) {
                    j2 = this.zznp;
                    this.zznp = j2 + 1;
                    zzfv.zza(j2, (byte) ((int) j));
                    return;
                }
                j2 = this.zznp;
                this.zznp = j2 + 1;
                zzfv.zza(j2, (byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.zznp), Long.valueOf(this.zznn), Integer.valueOf(1)}));
        }

        public final void zza(zzbu zzbu) {
            zzx(zzbu.size());
            zzbu.zza((zzbt) this);
        }

        final void zza(zzeh zzeh, zzev zzev) {
            zzbn zzbn = (zzbn) zzeh;
            int zzbp = zzbn.zzbp();
            if (zzbp == -1) {
                zzbp = zzev.zzo(zzbn);
                zzbn.zzg(zzbp);
            }
            zzx(zzbp);
            zzev.zza(zzeh, this.zzng);
        }

        public final void zza(byte[] bArr, int i, int i2) {
            write(bArr, i, i2);
        }

        public final void zzal(String str) {
            long j = this.zznp;
            try {
                int zzac = zzci.zzac(str.length() * 3);
                int zzac2 = zzci.zzac(str.length());
                if (zzac2 == zzac) {
                    zzac = ((int) (this.zznp - this.zznl)) + zzac2;
                    this.zznk.position(zzac);
                    zzfx.zza(str, this.zznk);
                    zzac = this.zznk.position() - zzac;
                    zzx(zzac);
                    this.zznp = ((long) zzac) + this.zznp;
                    return;
                }
                zzac = zzfx.zza(str);
                zzx(zzac);
                zzj(this.zznp);
                zzfx.zza(str, this.zznk);
                this.zznp = ((long) zzac) + this.zznp;
            } catch (zzga e) {
                this.zznp = j;
                zzj(this.zznp);
                zza(str, e);
            } catch (Throwable e2) {
                throw new zzc(e2);
            } catch (Throwable e22) {
                throw new zzc(e22);
            }
        }

        public final void zzb(int i, int i2) {
            zzx((i << 3) | i2);
        }

        public final void zzb(int i, zzbu zzbu) {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzbu);
            zzb(1, 4);
        }

        public final void zzb(int i, zzeh zzeh) {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzeh);
            zzb(1, 4);
        }

        public final void zzb(int i, boolean z) {
            int i2 = 0;
            zzb(i, 0);
            if (z) {
                i2 = 1;
            }
            zza((byte) i2);
        }

        public final void zzb(zzeh zzeh) {
            zzx(zzeh.zzdq());
            zzeh.zzb(this);
        }

        public final void zzc(int i, int i2) {
            zzb(i, 0);
            zzw(i2);
        }

        public final void zzc(int i, long j) {
            zzb(i, 1);
            zzc(j);
        }

        public final void zzc(long j) {
            this.zznk.putLong((int) (this.zznp - this.zznl), j);
            this.zznp += 8;
        }

        public final void zzd(int i, int i2) {
            zzb(i, 0);
            zzx(i2);
        }

        public final int zzdc() {
            return (int) (this.zznn - this.zznp);
        }

        public final void zze(byte[] bArr, int i, int i2) {
            zzx(i2);
            write(bArr, 0, i2);
        }

        public final void zzf(int i, int i2) {
            zzb(i, 5);
            zzz(i2);
        }

        public final void zzw(int i) {
            if (i >= 0) {
                zzx(i);
            } else {
                zza((long) i);
            }
        }

        public final void zzx(int i) {
            long j;
            if (this.zznp <= this.zzno) {
                while ((i & -128) != 0) {
                    j = this.zznp;
                    this.zznp = j + 1;
                    zzfv.zza(j, (byte) ((i & 127) | 128));
                    i >>>= 7;
                }
                j = this.zznp;
                this.zznp = j + 1;
                zzfv.zza(j, (byte) i);
                return;
            }
            while (this.zznp < this.zznn) {
                if ((i & -128) == 0) {
                    j = this.zznp;
                    this.zznp = j + 1;
                    zzfv.zza(j, (byte) i);
                    return;
                }
                j = this.zznp;
                this.zznp = j + 1;
                zzfv.zza(j, (byte) ((i & 127) | 128));
                i >>>= 7;
            }
            throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.zznp), Long.valueOf(this.zznn), Integer.valueOf(1)}));
        }

        public final void zzz(int i) {
            this.zznk.putInt((int) (this.zznp - this.zznl), i);
            this.zznp += 4;
        }
    }

    private zzci() {
    }

    public static int zza(int i, zzdo zzdo) {
        int zzaa = zzaa(i);
        int zzdq = zzdo.zzdq();
        return zzaa + (zzdq + zzac(zzdq));
    }

    public static int zza(zzdo zzdo) {
        int zzdq = zzdo.zzdq();
        return zzdq + zzac(zzdq);
    }

    public static zzci zza(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return new zzb(byteBuffer);
        }
        if (byteBuffer.isDirect() && !byteBuffer.isReadOnly()) {
            return zzfv.zzgd() ? new zze(byteBuffer) : new zzd(byteBuffer);
        } else {
            throw new IllegalArgumentException("ByteBuffer is read-only");
        }
    }

    public static int zzaa(int i) {
        return zzac(i << 3);
    }

    public static int zzab(int i) {
        return i >= 0 ? zzac(i) : 10;
    }

    public static int zzac(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (-268435456 & i) == 0 ? 4 : 5;
    }

    public static int zzad(int i) {
        return zzac(zzah(i));
    }

    public static int zzae(int i) {
        return 4;
    }

    public static int zzaf(int i) {
        return 4;
    }

    public static int zzag(int i) {
        return zzab(i);
    }

    private static int zzah(int i) {
        return (i << 1) ^ (i >> 31);
    }

    @Deprecated
    public static int zzai(int i) {
        return zzac(i);
    }

    public static int zzam(String str) {
        int zza;
        try {
            zza = zzfx.zza(str);
        } catch (zzga e) {
            zza = str.getBytes(zzdd.UTF_8).length;
        }
        return zza + zzac(zza);
    }

    public static int zzb(double d) {
        return 8;
    }

    public static int zzb(float f) {
        return 4;
    }

    public static int zzb(int i, double d) {
        return zzaa(i) + 8;
    }

    public static int zzb(int i, float f) {
        return zzaa(i) + 4;
    }

    public static int zzb(int i, zzdo zzdo) {
        return ((zzaa(1) << 1) + zzh(2, i)) + zza(3, zzdo);
    }

    static int zzb(int i, zzeh zzeh, zzev zzev) {
        return zzaa(i) + zzb(zzeh, zzev);
    }

    public static int zzb(int i, String str) {
        return zzaa(i) + zzam(str);
    }

    public static int zzb(zzbu zzbu) {
        int size = zzbu.size();
        return size + zzac(size);
    }

    static int zzb(zzeh zzeh, zzev zzev) {
        zzbn zzbn = (zzbn) zzeh;
        int zzbp = zzbn.zzbp();
        if (zzbp == -1) {
            zzbp = zzev.zzo(zzbn);
            zzbn.zzg(zzbp);
        }
        return zzbp + zzac(zzbp);
    }

    public static zzci zzb(byte[] bArr) {
        return new zza(bArr, 0, bArr.length);
    }

    public static int zzc(int i, zzbu zzbu) {
        int zzaa = zzaa(i);
        int size = zzbu.size();
        return zzaa + (size + zzac(size));
    }

    public static int zzc(int i, zzeh zzeh) {
        return zzaa(i) + zzc(zzeh);
    }

    @Deprecated
    static int zzc(int i, zzeh zzeh, zzev zzev) {
        int zzaa = zzaa(i) << 1;
        zzbn zzbn = (zzbn) zzeh;
        int zzbp = zzbn.zzbp();
        if (zzbp == -1) {
            zzbp = zzev.zzo(zzbn);
            zzbn.zzg(zzbp);
        }
        return zzbp + zzaa;
    }

    public static int zzc(int i, boolean z) {
        return zzaa(i) + 1;
    }

    public static int zzc(zzeh zzeh) {
        int zzdq = zzeh.zzdq();
        return zzdq + zzac(zzdq);
    }

    public static int zzc(byte[] bArr) {
        int length = bArr.length;
        return length + zzac(length);
    }

    public static int zzd(int i, long j) {
        return zzaa(i) + zze(j);
    }

    public static int zzd(int i, zzbu zzbu) {
        return ((zzaa(1) << 1) + zzh(2, i)) + zzc(3, zzbu);
    }

    public static int zzd(int i, zzeh zzeh) {
        return ((zzaa(1) << 1) + zzh(2, i)) + zzc(3, zzeh);
    }

    public static int zzd(long j) {
        return zze(j);
    }

    @Deprecated
    public static int zzd(zzeh zzeh) {
        return zzeh.zzdq();
    }

    public static int zze(int i, long j) {
        return zzaa(i) + zze(j);
    }

    public static int zze(long j) {
        if ((-128 & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        long j2;
        int i = 2;
        if ((-34359738368L & j) != 0) {
            i = 6;
            j2 = j >>> 28;
        } else {
            j2 = j;
        }
        if ((-2097152 & j2) != 0) {
            i += 2;
            j2 >>>= 14;
        }
        return (j2 & -16384) != 0 ? i + 1 : i;
    }

    public static int zzf(int i, long j) {
        return zzaa(i) + zze(zzi(j));
    }

    public static int zzf(long j) {
        return zze(zzi(j));
    }

    public static int zzg(int i, int i2) {
        return zzaa(i) + zzab(i2);
    }

    public static int zzg(int i, long j) {
        return zzaa(i) + 8;
    }

    public static int zzg(long j) {
        return 8;
    }

    public static int zzg(boolean z) {
        return 1;
    }

    public static int zzh(int i, int i2) {
        return zzaa(i) + zzac(i2);
    }

    public static int zzh(int i, long j) {
        return zzaa(i) + 8;
    }

    public static int zzh(long j) {
        return 8;
    }

    public static int zzi(int i, int i2) {
        return zzaa(i) + zzac(zzah(i2));
    }

    private static long zzi(long j) {
        return (j << 1) ^ (j >> 63);
    }

    public static int zzj(int i, int i2) {
        return zzaa(i) + 4;
    }

    public static int zzk(int i, int i2) {
        return zzaa(i) + 4;
    }

    public static int zzl(int i, int i2) {
        return zzaa(i) + zzab(i2);
    }

    public abstract void flush();

    public abstract void write(byte[] bArr, int i, int i2);

    public abstract void zza(byte b);

    public final void zza(double d) {
        zzc(Double.doubleToRawLongBits(d));
    }

    public final void zza(float f) {
        zzz(Float.floatToRawIntBits(f));
    }

    public final void zza(int i, double d) {
        zzc(i, Double.doubleToRawLongBits(d));
    }

    public final void zza(int i, float f) {
        zzf(i, Float.floatToRawIntBits(f));
    }

    public abstract void zza(int i, long j);

    public abstract void zza(int i, zzbu zzbu);

    public abstract void zza(int i, zzeh zzeh);

    abstract void zza(int i, zzeh zzeh, zzev zzev);

    public abstract void zza(int i, String str);

    public abstract void zza(long j);

    public abstract void zza(zzbu zzbu);

    abstract void zza(zzeh zzeh, zzev zzev);

    final void zza(String str, zzga zzga) {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", zzga);
        byte[] bytes = str.getBytes(zzdd.UTF_8);
        try {
            zzx(bytes.length);
            zza(bytes, 0, bytes.length);
        } catch (Throwable e) {
            throw new zzc(e);
        } catch (zzc e2) {
            throw e2;
        }
    }

    public abstract void zzal(String str);

    public abstract void zzb(int i, int i2);

    public final void zzb(int i, long j) {
        zza(i, zzi(j));
    }

    public abstract void zzb(int i, zzbu zzbu);

    public abstract void zzb(int i, zzeh zzeh);

    public abstract void zzb(int i, boolean z);

    public final void zzb(long j) {
        zza(zzi(j));
    }

    public abstract void zzb(zzeh zzeh);

    public abstract void zzc(int i, int i2);

    public abstract void zzc(int i, long j);

    public abstract void zzc(long j);

    public abstract void zzd(int i, int i2);

    public abstract int zzdc();

    public final void zze(int i, int i2) {
        zzd(i, zzah(i2));
    }

    abstract void zze(byte[] bArr, int i, int i2);

    public abstract void zzf(int i, int i2);

    public final void zzf(boolean z) {
        zza((byte) (z ? 1 : 0));
    }

    public abstract void zzw(int i);

    public abstract void zzx(int i);

    public final void zzy(int i) {
        zzx(zzah(i));
    }

    public abstract void zzz(int i);
}
