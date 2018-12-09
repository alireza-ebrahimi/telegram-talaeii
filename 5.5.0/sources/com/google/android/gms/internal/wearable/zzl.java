package com.google.android.gms.internal.wearable;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

public final class zzl {
    private final ByteBuffer zzhb;

    private zzl(ByteBuffer byteBuffer) {
        this.zzhb = byteBuffer;
        this.zzhb.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzl(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    private static int zza(CharSequence charSequence) {
        int i = 0;
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < '') {
            i2++;
        }
        int i3 = length;
        while (i2 < length) {
            char charAt = charSequence.charAt(i2);
            if (charAt < 'ࠀ') {
                i3 += (127 - charAt) >>> 31;
                i2++;
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char charAt2 = charSequence.charAt(i2);
                    if (charAt2 < 'ࠀ') {
                        i += (127 - charAt2) >>> 31;
                    } else {
                        i += 2;
                        if ('?' <= charAt2 && charAt2 <= '?') {
                            if (Character.codePointAt(charSequence, i2) < C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) {
                                throw new IllegalArgumentException("Unpaired surrogate at index " + i2);
                            }
                            i2++;
                        }
                    }
                    i2++;
                }
                i2 = i3 + i;
                if (i2 < length) {
                    return i2;
                }
                throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (((long) i2) + 4294967296L));
            }
        }
        i2 = i3;
        if (i2 < length) {
            return i2;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (((long) i2) + 4294967296L));
    }

    private final void zza(long j) {
        while ((-128 & j) != 0) {
            zzj((((int) j) & 127) | 128);
            j >>>= 7;
        }
        zzj((int) j);
    }

    private static void zza(CharSequence charSequence, ByteBuffer byteBuffer) {
        int i = 0;
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else if (byteBuffer.hasArray()) {
            try {
                byte[] array = byteBuffer.array();
                r1 = byteBuffer.arrayOffset() + byteBuffer.position();
                r2 = byteBuffer.remaining();
                int length = charSequence.length();
                int i2 = r1 + r2;
                while (i < length && i + r1 < i2) {
                    r2 = charSequence.charAt(i);
                    if (r2 >= '') {
                        break;
                    }
                    array[r1 + i] = (byte) r2;
                    i++;
                }
                if (i == length) {
                    i = r1 + length;
                } else {
                    r2 = r1 + i;
                    while (i < length) {
                        char charAt = charSequence.charAt(i);
                        if (charAt < '' && r2 < i2) {
                            r1 = r2 + 1;
                            array[r2] = (byte) charAt;
                        } else if (charAt < 'ࠀ' && r2 <= i2 - 2) {
                            r7 = r2 + 1;
                            array[r2] = (byte) ((charAt >>> 6) | 960);
                            r1 = r7 + 1;
                            array[r7] = (byte) ((charAt & 63) | 128);
                        } else if ((charAt < '?' || '?' < charAt) && r2 <= i2 - 3) {
                            r1 = r2 + 1;
                            array[r2] = (byte) ((charAt >>> 12) | 480);
                            r2 = r1 + 1;
                            array[r1] = (byte) (((charAt >>> 6) & 63) | 128);
                            r1 = r2 + 1;
                            array[r2] = (byte) ((charAt & 63) | 128);
                        } else if (r2 <= i2 - 4) {
                            if (i + 1 != charSequence.length()) {
                                i++;
                                char charAt2 = charSequence.charAt(i);
                                if (Character.isSurrogatePair(charAt, charAt2)) {
                                    int toCodePoint = Character.toCodePoint(charAt, charAt2);
                                    r1 = r2 + 1;
                                    array[r2] = (byte) ((toCodePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK);
                                    r2 = r1 + 1;
                                    array[r1] = (byte) (((toCodePoint >>> 12) & 63) | 128);
                                    r7 = r2 + 1;
                                    array[r2] = (byte) (((toCodePoint >>> 6) & 63) | 128);
                                    r1 = r7 + 1;
                                    array[r7] = (byte) ((toCodePoint & 63) | 128);
                                }
                            }
                            throw new IllegalArgumentException("Unpaired surrogate at index " + (i - 1));
                        } else {
                            throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt + " at index " + r2);
                        }
                        i++;
                        r2 = r1;
                    }
                    i = r2;
                }
                byteBuffer.position(i - byteBuffer.arrayOffset());
            } catch (Throwable e) {
                BufferOverflowException bufferOverflowException = new BufferOverflowException();
                bufferOverflowException.initCause(e);
                throw bufferOverflowException;
            }
        } else {
            r1 = charSequence.length();
            while (i < r1) {
                r2 = charSequence.charAt(i);
                if (r2 < '') {
                    byteBuffer.put((byte) r2);
                } else if (r2 < 'ࠀ') {
                    byteBuffer.put((byte) ((r2 >>> 6) | 960));
                    byteBuffer.put((byte) ((r2 & 63) | 128));
                } else if (r2 < '?' || '?' < r2) {
                    byteBuffer.put((byte) ((r2 >>> 12) | 480));
                    byteBuffer.put((byte) (((r2 >>> 6) & 63) | 128));
                    byteBuffer.put((byte) ((r2 & 63) | 128));
                } else {
                    if (i + 1 != charSequence.length()) {
                        i++;
                        char charAt3 = charSequence.charAt(i);
                        if (Character.isSurrogatePair(r2, charAt3)) {
                            r2 = Character.toCodePoint(r2, charAt3);
                            byteBuffer.put((byte) ((r2 >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                            byteBuffer.put((byte) (((r2 >>> 12) & 63) | 128));
                            byteBuffer.put((byte) (((r2 >>> 6) & 63) | 128));
                            byteBuffer.put((byte) ((r2 & 63) | 128));
                        }
                    }
                    throw new IllegalArgumentException("Unpaired surrogate at index " + (i - 1));
                }
                i++;
            }
        }
    }

    public static int zzb(int i, long j) {
        int zzk = zzk(i);
        int i2 = (-128 & j) == 0 ? 1 : (-16384 & j) == 0 ? 2 : (-2097152 & j) == 0 ? 3 : (-268435456 & j) == 0 ? 4 : (-34359738368L & j) == 0 ? 5 : (-4398046511104L & j) == 0 ? 6 : (-562949953421312L & j) == 0 ? 7 : (-72057594037927936L & j) == 0 ? 8 : (Long.MIN_VALUE & j) == 0 ? 9 : 10;
        return i2 + zzk;
    }

    public static int zzb(int i, zzt zzt) {
        int zzk = zzk(i);
        int zzx = zzt.zzx();
        return zzk + (zzx + zzm(zzx));
    }

    public static int zzb(int i, String str) {
        return zzk(i) + zzg(str);
    }

    public static zzl zzb(byte[] bArr) {
        return zzb(bArr, 0, bArr.length);
    }

    public static zzl zzb(byte[] bArr, int i, int i2) {
        return new zzl(bArr, 0, i2);
    }

    public static int zze(int i, int i2) {
        return zzk(i) + zzi(i2);
    }

    public static int zzg(String str) {
        int zza = zza((CharSequence) str);
        return zza + zzm(zza);
    }

    public static int zzi(int i) {
        return i >= 0 ? zzm(i) : 10;
    }

    private final void zzj(int i) {
        byte b = (byte) i;
        if (this.zzhb.hasRemaining()) {
            this.zzhb.put(b);
            return;
        }
        throw new zzm(this.zzhb.position(), this.zzhb.limit());
    }

    public static int zzk(int i) {
        return zzm(i << 3);
    }

    public static int zzm(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (-268435456 & i) == 0 ? 4 : 5;
    }

    public static int zzn(int i) {
        return (i << 1) ^ (i >> 31);
    }

    public final void zza(byte b) {
        if (this.zzhb.hasRemaining()) {
            this.zzhb.put(b);
            return;
        }
        throw new zzm(this.zzhb.position(), this.zzhb.limit());
    }

    public final void zza(int i, float f) {
        zzf(i, 5);
        int floatToIntBits = Float.floatToIntBits(f);
        if (this.zzhb.remaining() < 4) {
            throw new zzm(this.zzhb.position(), this.zzhb.limit());
        }
        this.zzhb.putInt(floatToIntBits);
    }

    public final void zza(int i, long j) {
        zzf(i, 0);
        zza(j);
    }

    public final void zza(int i, zzt zzt) {
        zzf(i, 2);
        if (zzt.zzhl < 0) {
            zzt.zzx();
        }
        zzl(zzt.zzhl);
        zzt.zza(this);
    }

    public final void zza(int i, String str) {
        zzf(i, 2);
        try {
            int zzm = zzm(str.length());
            if (zzm == zzm(str.length() * 3)) {
                int position = this.zzhb.position();
                if (this.zzhb.remaining() < zzm) {
                    throw new zzm(zzm + position, this.zzhb.limit());
                }
                this.zzhb.position(position + zzm);
                zza((CharSequence) str, this.zzhb);
                int position2 = this.zzhb.position();
                this.zzhb.position(position);
                zzl((position2 - position) - zzm);
                this.zzhb.position(position2);
                return;
            }
            zzl(zza((CharSequence) str));
            zza((CharSequence) str, this.zzhb);
        } catch (Throwable e) {
            zzm zzm2 = new zzm(this.zzhb.position(), this.zzhb.limit());
            zzm2.initCause(e);
            throw zzm2;
        }
    }

    public final void zzb(long j) {
        if (this.zzhb.remaining() < 8) {
            throw new zzm(this.zzhb.position(), this.zzhb.limit());
        }
        this.zzhb.putLong(j);
    }

    public final void zzc(byte[] bArr) {
        int length = bArr.length;
        if (this.zzhb.remaining() >= length) {
            this.zzhb.put(bArr, 0, length);
            return;
        }
        throw new zzm(this.zzhb.position(), this.zzhb.limit());
    }

    public final void zzd(int i, int i2) {
        zzf(i, 0);
        if (i2 >= 0) {
            zzl(i2);
        } else {
            zza((long) i2);
        }
    }

    public final void zzf(int i, int i2) {
        zzl((i << 3) | i2);
    }

    public final void zzl(int i) {
        while ((i & -128) != 0) {
            zzj((i & 127) | 128);
            i >>>= 7;
        }
        zzj(i);
    }

    public final void zzr() {
        if (this.zzhb.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", new Object[]{Integer.valueOf(this.zzhb.remaining())}));
        }
    }
}
