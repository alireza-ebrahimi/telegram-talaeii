package com.google.android.gms.internal.clearcut;

import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class zzbn extends zzba {
    private static final Logger logger = Logger.getLogger(zzbn.class.getName());
    private static final boolean zzfy = zzfd.zzed();
    zzbp zzfz = this;

    static class zza extends zzbn {
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
            zzb(j);
        }

        public final void zza(int i, zzbb zzbb) {
            zzb(i, 2);
            zza(zzbb);
        }

        public final void zza(int i, zzdo zzdo) {
            zzb(i, 2);
            zzb(zzdo);
        }

        final void zza(int i, zzdo zzdo, zzef zzef) {
            zzb(i, 2);
            zzas zzas = (zzas) zzdo;
            int zzs = zzas.zzs();
            if (zzs == -1) {
                zzs = zzef.zzm(zzas);
                zzas.zzf(zzs);
            }
            zzo(zzs);
            zzef.zza(zzdo, this.zzfz);
        }

        public final void zza(int i, String str) {
            zzb(i, 2);
            zzg(str);
        }

        public final void zza(zzbb zzbb) {
            zzo(zzbb.size());
            zzbb.zza((zzba) this);
        }

        final void zza(zzdo zzdo, zzef zzef) {
            zzas zzas = (zzas) zzdo;
            int zzs = zzas.zzs();
            if (zzs == -1) {
                zzs = zzef.zzm(zzas);
                zzas.zzf(zzs);
            }
            zzo(zzs);
            zzef.zza(zzdo, this.zzfz);
        }

        public final void zza(byte[] bArr, int i, int i2) {
            write(bArr, i, i2);
        }

        public final int zzag() {
            return this.limit - this.position;
        }

        public final int zzai() {
            return this.position - this.offset;
        }

        public final void zzb(int i, int i2) {
            zzo((i << 3) | i2);
        }

        public final void zzb(int i, zzbb zzbb) {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzbb);
            zzb(1, 4);
        }

        public final void zzb(int i, zzdo zzdo) {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzdo);
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

        public final void zzb(long j) {
            byte[] bArr;
            int i;
            if (!zzbn.zzfy || zzag() < 10) {
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
                zzfd.zza(bArr, (long) i, (byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            bArr = this.buffer;
            i = this.position;
            this.position = i + 1;
            zzfd.zza(bArr, (long) i, (byte) ((int) j));
        }

        public final void zzb(zzdo zzdo) {
            zzo(zzdo.zzas());
            zzdo.zzb(this);
        }

        public final void zzc(int i, int i2) {
            zzb(i, 0);
            zzn(i2);
        }

        public final void zzc(int i, long j) {
            zzb(i, 1);
            zzd(j);
        }

        public final void zzd(int i, int i2) {
            zzb(i, 0);
            zzo(i2);
        }

        public final void zzd(long j) {
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

        public final void zzd(byte[] bArr, int i, int i2) {
            zzo(i2);
            write(bArr, 0, i2);
        }

        public final void zzf(int i, int i2) {
            zzb(i, 5);
            zzq(i2);
        }

        public final void zzg(String str) {
            int i = this.position;
            try {
                int zzt = zzbn.zzt(str.length() * 3);
                int zzt2 = zzbn.zzt(str.length());
                if (zzt2 == zzt) {
                    this.position = i + zzt2;
                    zzt = zzff.zza(str, this.buffer, this.position, zzag());
                    this.position = i;
                    zzo((zzt - i) - zzt2);
                    this.position = zzt;
                    return;
                }
                zzo(zzff.zza(str));
                this.position = zzff.zza(str, this.buffer, this.position, zzag());
            } catch (zzfi e) {
                this.position = i;
                zza(str, e);
            } catch (Throwable e2) {
                throw new zzc(e2);
            }
        }

        public final void zzn(int i) {
            if (i >= 0) {
                zzo(i);
            } else {
                zzb((long) i);
            }
        }

        public final void zzo(int i) {
            byte[] bArr;
            int i2;
            if (!zzbn.zzfy || zzag() < 10) {
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
                zzfd.zza(bArr, (long) i2, (byte) ((i & 127) | 128));
                i >>>= 7;
            }
            bArr = this.buffer;
            i2 = this.position;
            this.position = i2 + 1;
            zzfd.zza(bArr, (long) i2, (byte) i);
        }

        public final void zzq(int i) {
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
        private final ByteBuffer zzga;
        private int zzgb;

        zzb(ByteBuffer byteBuffer) {
            super(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            this.zzga = byteBuffer;
            this.zzgb = byteBuffer.position();
        }

        public final void flush() {
            this.zzga.position(this.zzgb + zzai());
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

    static final class zzd extends zzbn {
        private final int zzgb;
        private final ByteBuffer zzgc;
        private final ByteBuffer zzgd;

        zzd(ByteBuffer byteBuffer) {
            super();
            this.zzgc = byteBuffer;
            this.zzgd = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.zzgb = byteBuffer.position();
        }

        private final void zzi(String str) {
            try {
                zzff.zza(str, this.zzgd);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void flush() {
            this.zzgc.position(this.zzgd.position());
        }

        public final void write(byte[] bArr, int i, int i2) {
            try {
                this.zzgd.put(bArr, i, i2);
            } catch (Throwable e) {
                throw new zzc(e);
            } catch (Throwable e2) {
                throw new zzc(e2);
            }
        }

        public final void zza(byte b) {
            try {
                this.zzgd.put(b);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void zza(int i, long j) {
            zzb(i, 0);
            zzb(j);
        }

        public final void zza(int i, zzbb zzbb) {
            zzb(i, 2);
            zza(zzbb);
        }

        public final void zza(int i, zzdo zzdo) {
            zzb(i, 2);
            zzb(zzdo);
        }

        final void zza(int i, zzdo zzdo, zzef zzef) {
            zzb(i, 2);
            zza(zzdo, zzef);
        }

        public final void zza(int i, String str) {
            zzb(i, 2);
            zzg(str);
        }

        public final void zza(zzbb zzbb) {
            zzo(zzbb.size());
            zzbb.zza((zzba) this);
        }

        final void zza(zzdo zzdo, zzef zzef) {
            zzas zzas = (zzas) zzdo;
            int zzs = zzas.zzs();
            if (zzs == -1) {
                zzs = zzef.zzm(zzas);
                zzas.zzf(zzs);
            }
            zzo(zzs);
            zzef.zza(zzdo, this.zzfz);
        }

        public final void zza(byte[] bArr, int i, int i2) {
            write(bArr, i, i2);
        }

        public final int zzag() {
            return this.zzgd.remaining();
        }

        public final void zzb(int i, int i2) {
            zzo((i << 3) | i2);
        }

        public final void zzb(int i, zzbb zzbb) {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzbb);
            zzb(1, 4);
        }

        public final void zzb(int i, zzdo zzdo) {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzdo);
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

        public final void zzb(long j) {
            while ((-128 & j) != 0) {
                this.zzgd.put((byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            try {
                this.zzgd.put((byte) ((int) j));
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void zzb(zzdo zzdo) {
            zzo(zzdo.zzas());
            zzdo.zzb(this);
        }

        public final void zzc(int i, int i2) {
            zzb(i, 0);
            zzn(i2);
        }

        public final void zzc(int i, long j) {
            zzb(i, 1);
            zzd(j);
        }

        public final void zzd(int i, int i2) {
            zzb(i, 0);
            zzo(i2);
        }

        public final void zzd(long j) {
            try {
                this.zzgd.putLong(j);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void zzd(byte[] bArr, int i, int i2) {
            zzo(i2);
            write(bArr, 0, i2);
        }

        public final void zzf(int i, int i2) {
            zzb(i, 5);
            zzq(i2);
        }

        public final void zzg(String str) {
            int position = this.zzgd.position();
            try {
                int zzt = zzbn.zzt(str.length() * 3);
                int zzt2 = zzbn.zzt(str.length());
                if (zzt2 == zzt) {
                    zzt = this.zzgd.position() + zzt2;
                    this.zzgd.position(zzt);
                    zzi(str);
                    zzt2 = this.zzgd.position();
                    this.zzgd.position(position);
                    zzo(zzt2 - zzt);
                    this.zzgd.position(zzt2);
                    return;
                }
                zzo(zzff.zza(str));
                zzi(str);
            } catch (zzfi e) {
                this.zzgd.position(position);
                zza(str, e);
            } catch (Throwable e2) {
                throw new zzc(e2);
            }
        }

        public final void zzn(int i) {
            if (i >= 0) {
                zzo(i);
            } else {
                zzb((long) i);
            }
        }

        public final void zzo(int i) {
            while ((i & -128) != 0) {
                this.zzgd.put((byte) ((i & 127) | 128));
                i >>>= 7;
            }
            try {
                this.zzgd.put((byte) i);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void zzq(int i) {
            try {
                this.zzgd.putInt(i);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }
    }

    static final class zze extends zzbn {
        private final ByteBuffer zzgc;
        private final ByteBuffer zzgd;
        private final long zzge;
        private final long zzgf;
        private final long zzgg;
        private final long zzgh = (this.zzgg - 10);
        private long zzgi = this.zzgf;

        zze(ByteBuffer byteBuffer) {
            super();
            this.zzgc = byteBuffer;
            this.zzgd = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.zzge = zzfd.zzb(byteBuffer);
            this.zzgf = this.zzge + ((long) byteBuffer.position());
            this.zzgg = this.zzge + ((long) byteBuffer.limit());
        }

        private final void zzk(long j) {
            this.zzgd.position((int) (j - this.zzge));
        }

        public final void flush() {
            this.zzgc.position((int) (this.zzgi - this.zzge));
        }

        public final void write(byte[] bArr, int i, int i2) {
            if (bArr != null && i >= 0 && i2 >= 0 && bArr.length - i2 >= i && this.zzgg - ((long) i2) >= this.zzgi) {
                zzfd.zza(bArr, (long) i, this.zzgi, (long) i2);
                this.zzgi += (long) i2;
            } else if (bArr == null) {
                throw new NullPointerException(C1797b.VALUE);
            } else {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.zzgi), Long.valueOf(this.zzgg), Integer.valueOf(i2)}));
            }
        }

        public final void zza(byte b) {
            if (this.zzgi >= this.zzgg) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.zzgi), Long.valueOf(this.zzgg), Integer.valueOf(1)}));
            }
            long j = this.zzgi;
            this.zzgi = 1 + j;
            zzfd.zza(j, b);
        }

        public final void zza(int i, long j) {
            zzb(i, 0);
            zzb(j);
        }

        public final void zza(int i, zzbb zzbb) {
            zzb(i, 2);
            zza(zzbb);
        }

        public final void zza(int i, zzdo zzdo) {
            zzb(i, 2);
            zzb(zzdo);
        }

        final void zza(int i, zzdo zzdo, zzef zzef) {
            zzb(i, 2);
            zza(zzdo, zzef);
        }

        public final void zza(int i, String str) {
            zzb(i, 2);
            zzg(str);
        }

        public final void zza(zzbb zzbb) {
            zzo(zzbb.size());
            zzbb.zza((zzba) this);
        }

        final void zza(zzdo zzdo, zzef zzef) {
            zzas zzas = (zzas) zzdo;
            int zzs = zzas.zzs();
            if (zzs == -1) {
                zzs = zzef.zzm(zzas);
                zzas.zzf(zzs);
            }
            zzo(zzs);
            zzef.zza(zzdo, this.zzfz);
        }

        public final void zza(byte[] bArr, int i, int i2) {
            write(bArr, i, i2);
        }

        public final int zzag() {
            return (int) (this.zzgg - this.zzgi);
        }

        public final void zzb(int i, int i2) {
            zzo((i << 3) | i2);
        }

        public final void zzb(int i, zzbb zzbb) {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzbb);
            zzb(1, 4);
        }

        public final void zzb(int i, zzdo zzdo) {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzdo);
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

        public final void zzb(long j) {
            long j2;
            if (this.zzgi <= this.zzgh) {
                while ((j & -128) != 0) {
                    j2 = this.zzgi;
                    this.zzgi = j2 + 1;
                    zzfd.zza(j2, (byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                }
                j2 = this.zzgi;
                this.zzgi = j2 + 1;
                zzfd.zza(j2, (byte) ((int) j));
                return;
            }
            while (this.zzgi < this.zzgg) {
                if ((j & -128) == 0) {
                    j2 = this.zzgi;
                    this.zzgi = j2 + 1;
                    zzfd.zza(j2, (byte) ((int) j));
                    return;
                }
                j2 = this.zzgi;
                this.zzgi = j2 + 1;
                zzfd.zza(j2, (byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.zzgi), Long.valueOf(this.zzgg), Integer.valueOf(1)}));
        }

        public final void zzb(zzdo zzdo) {
            zzo(zzdo.zzas());
            zzdo.zzb(this);
        }

        public final void zzc(int i, int i2) {
            zzb(i, 0);
            zzn(i2);
        }

        public final void zzc(int i, long j) {
            zzb(i, 1);
            zzd(j);
        }

        public final void zzd(int i, int i2) {
            zzb(i, 0);
            zzo(i2);
        }

        public final void zzd(long j) {
            this.zzgd.putLong((int) (this.zzgi - this.zzge), j);
            this.zzgi += 8;
        }

        public final void zzd(byte[] bArr, int i, int i2) {
            zzo(i2);
            write(bArr, 0, i2);
        }

        public final void zzf(int i, int i2) {
            zzb(i, 5);
            zzq(i2);
        }

        public final void zzg(String str) {
            long j = this.zzgi;
            try {
                int zzt = zzbn.zzt(str.length() * 3);
                int zzt2 = zzbn.zzt(str.length());
                if (zzt2 == zzt) {
                    zzt = ((int) (this.zzgi - this.zzge)) + zzt2;
                    this.zzgd.position(zzt);
                    zzff.zza(str, this.zzgd);
                    zzt = this.zzgd.position() - zzt;
                    zzo(zzt);
                    this.zzgi = ((long) zzt) + this.zzgi;
                    return;
                }
                zzt = zzff.zza(str);
                zzo(zzt);
                zzk(this.zzgi);
                zzff.zza(str, this.zzgd);
                this.zzgi = ((long) zzt) + this.zzgi;
            } catch (zzfi e) {
                this.zzgi = j;
                zzk(this.zzgi);
                zza(str, e);
            } catch (Throwable e2) {
                throw new zzc(e2);
            } catch (Throwable e22) {
                throw new zzc(e22);
            }
        }

        public final void zzn(int i) {
            if (i >= 0) {
                zzo(i);
            } else {
                zzb((long) i);
            }
        }

        public final void zzo(int i) {
            long j;
            if (this.zzgi <= this.zzgh) {
                while ((i & -128) != 0) {
                    j = this.zzgi;
                    this.zzgi = j + 1;
                    zzfd.zza(j, (byte) ((i & 127) | 128));
                    i >>>= 7;
                }
                j = this.zzgi;
                this.zzgi = j + 1;
                zzfd.zza(j, (byte) i);
                return;
            }
            while (this.zzgi < this.zzgg) {
                if ((i & -128) == 0) {
                    j = this.zzgi;
                    this.zzgi = j + 1;
                    zzfd.zza(j, (byte) i);
                    return;
                }
                j = this.zzgi;
                this.zzgi = j + 1;
                zzfd.zza(j, (byte) ((i & 127) | 128));
                i >>>= 7;
            }
            throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.zzgi), Long.valueOf(this.zzgg), Integer.valueOf(1)}));
        }

        public final void zzq(int i) {
            this.zzgd.putInt((int) (this.zzgi - this.zzge), i);
            this.zzgi += 4;
        }
    }

    private zzbn() {
    }

    public static int zza(int i, zzcv zzcv) {
        int zzr = zzr(i);
        int zzas = zzcv.zzas();
        return zzr + (zzas + zzt(zzas));
    }

    public static int zza(zzcv zzcv) {
        int zzas = zzcv.zzas();
        return zzas + zzt(zzas);
    }

    public static zzbn zza(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return new zzb(byteBuffer);
        }
        if (byteBuffer.isDirect() && !byteBuffer.isReadOnly()) {
            return zzfd.zzee() ? new zze(byteBuffer) : new zzd(byteBuffer);
        } else {
            throw new IllegalArgumentException("ByteBuffer is read-only");
        }
    }

    public static int zzb(double d) {
        return 8;
    }

    public static int zzb(float f) {
        return 4;
    }

    public static int zzb(int i, double d) {
        return zzr(i) + 8;
    }

    public static int zzb(int i, float f) {
        return zzr(i) + 4;
    }

    public static int zzb(int i, zzcv zzcv) {
        return ((zzr(1) << 1) + zzh(2, i)) + zza(3, zzcv);
    }

    static int zzb(int i, zzdo zzdo, zzef zzef) {
        return zzr(i) + zzb(zzdo, zzef);
    }

    public static int zzb(int i, String str) {
        return zzr(i) + zzh(str);
    }

    public static int zzb(zzbb zzbb) {
        int size = zzbb.size();
        return size + zzt(size);
    }

    static int zzb(zzdo zzdo, zzef zzef) {
        zzas zzas = (zzas) zzdo;
        int zzs = zzas.zzs();
        if (zzs == -1) {
            zzs = zzef.zzm(zzas);
            zzas.zzf(zzs);
        }
        return zzs + zzt(zzs);
    }

    public static int zzb(boolean z) {
        return 1;
    }

    public static int zzc(int i, zzbb zzbb) {
        int zzr = zzr(i);
        int size = zzbb.size();
        return zzr + (size + zzt(size));
    }

    public static int zzc(int i, zzdo zzdo) {
        return zzr(i) + zzc(zzdo);
    }

    @Deprecated
    static int zzc(int i, zzdo zzdo, zzef zzef) {
        int zzr = zzr(i) << 1;
        zzas zzas = (zzas) zzdo;
        int zzs = zzas.zzs();
        if (zzs == -1) {
            zzs = zzef.zzm(zzas);
            zzas.zzf(zzs);
        }
        return zzs + zzr;
    }

    public static int zzc(int i, boolean z) {
        return zzr(i) + 1;
    }

    public static int zzc(zzdo zzdo) {
        int zzas = zzdo.zzas();
        return zzas + zzt(zzas);
    }

    public static zzbn zzc(byte[] bArr) {
        return new zza(bArr, 0, bArr.length);
    }

    public static int zzd(int i, long j) {
        return zzr(i) + zzf(j);
    }

    public static int zzd(int i, zzbb zzbb) {
        return ((zzr(1) << 1) + zzh(2, i)) + zzc(3, zzbb);
    }

    public static int zzd(int i, zzdo zzdo) {
        return ((zzr(1) << 1) + zzh(2, i)) + zzc(3, zzdo);
    }

    @Deprecated
    public static int zzd(zzdo zzdo) {
        return zzdo.zzas();
    }

    public static int zzd(byte[] bArr) {
        int length = bArr.length;
        return length + zzt(length);
    }

    public static int zze(int i, long j) {
        return zzr(i) + zzf(j);
    }

    public static int zze(long j) {
        return zzf(j);
    }

    public static int zzf(int i, long j) {
        return zzr(i) + zzf(zzj(j));
    }

    public static int zzf(long j) {
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

    public static int zzg(int i, int i2) {
        return zzr(i) + zzs(i2);
    }

    public static int zzg(int i, long j) {
        return zzr(i) + 8;
    }

    public static int zzg(long j) {
        return zzf(zzj(j));
    }

    public static int zzh(int i, int i2) {
        return zzr(i) + zzt(i2);
    }

    public static int zzh(int i, long j) {
        return zzr(i) + 8;
    }

    public static int zzh(long j) {
        return 8;
    }

    public static int zzh(String str) {
        int zza;
        try {
            zza = zzff.zza(str);
        } catch (zzfi e) {
            zza = str.getBytes(zzci.UTF_8).length;
        }
        return zza + zzt(zza);
    }

    public static int zzi(int i, int i2) {
        return zzr(i) + zzt(zzy(i2));
    }

    public static int zzi(long j) {
        return 8;
    }

    public static int zzj(int i, int i2) {
        return zzr(i) + 4;
    }

    private static long zzj(long j) {
        return (j << 1) ^ (j >> 63);
    }

    public static int zzk(int i, int i2) {
        return zzr(i) + 4;
    }

    public static int zzl(int i, int i2) {
        return zzr(i) + zzs(i2);
    }

    public static int zzr(int i) {
        return zzt(i << 3);
    }

    public static int zzs(int i) {
        return i >= 0 ? zzt(i) : 10;
    }

    public static int zzt(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (-268435456 & i) == 0 ? 4 : 5;
    }

    public static int zzu(int i) {
        return zzt(zzy(i));
    }

    public static int zzv(int i) {
        return 4;
    }

    public static int zzw(int i) {
        return 4;
    }

    public static int zzx(int i) {
        return zzs(i);
    }

    private static int zzy(int i) {
        return (i << 1) ^ (i >> 31);
    }

    @Deprecated
    public static int zzz(int i) {
        return zzt(i);
    }

    public abstract void flush();

    public abstract void write(byte[] bArr, int i, int i2);

    public abstract void zza(byte b);

    public final void zza(double d) {
        zzd(Double.doubleToRawLongBits(d));
    }

    public final void zza(float f) {
        zzq(Float.floatToRawIntBits(f));
    }

    public final void zza(int i, double d) {
        zzc(i, Double.doubleToRawLongBits(d));
    }

    public final void zza(int i, float f) {
        zzf(i, Float.floatToRawIntBits(f));
    }

    public abstract void zza(int i, long j);

    public abstract void zza(int i, zzbb zzbb);

    public abstract void zza(int i, zzdo zzdo);

    abstract void zza(int i, zzdo zzdo, zzef zzef);

    public abstract void zza(int i, String str);

    public abstract void zza(zzbb zzbb);

    abstract void zza(zzdo zzdo, zzef zzef);

    final void zza(String str, zzfi zzfi) {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", zzfi);
        byte[] bytes = str.getBytes(zzci.UTF_8);
        try {
            zzo(bytes.length);
            zza(bytes, 0, bytes.length);
        } catch (Throwable e) {
            throw new zzc(e);
        } catch (zzc e2) {
            throw e2;
        }
    }

    public final void zza(boolean z) {
        zza((byte) (z ? 1 : 0));
    }

    public abstract int zzag();

    public abstract void zzb(int i, int i2);

    public final void zzb(int i, long j) {
        zza(i, zzj(j));
    }

    public abstract void zzb(int i, zzbb zzbb);

    public abstract void zzb(int i, zzdo zzdo);

    public abstract void zzb(int i, boolean z);

    public abstract void zzb(long j);

    public abstract void zzb(zzdo zzdo);

    public abstract void zzc(int i, int i2);

    public abstract void zzc(int i, long j);

    public final void zzc(long j) {
        zzb(zzj(j));
    }

    public abstract void zzd(int i, int i2);

    public abstract void zzd(long j);

    abstract void zzd(byte[] bArr, int i, int i2);

    public final void zze(int i, int i2) {
        zzd(i, zzy(i2));
    }

    public abstract void zzf(int i, int i2);

    public abstract void zzg(String str);

    public abstract void zzn(int i);

    public abstract void zzo(int i);

    public final void zzp(int i) {
        zzo(zzy(i));
    }

    public abstract void zzq(int i);
}
