package com.google.android.gms.internal.measurement;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

public final class zzaby {
    private final ByteBuffer zzbxf;

    private zzaby(ByteBuffer byteBuffer) {
        this.zzbxf = byteBuffer;
        this.zzbxf.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzaby(byte[] bArr, int i, int i2) {
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

    private final void zzan(long j) {
        while ((-128 & j) != 0) {
            zzap((((int) j) & 127) | 128);
            j >>>= 7;
        }
        zzap((int) j);
    }

    public static int zzao(int i) {
        return i >= 0 ? zzas(i) : 10;
    }

    public static int zzao(long j) {
        return (-128 & j) == 0 ? 1 : (-16384 & j) == 0 ? 2 : (-2097152 & j) == 0 ? 3 : (-268435456 & j) == 0 ? 4 : (-34359738368L & j) == 0 ? 5 : (-4398046511104L & j) == 0 ? 6 : (-562949953421312L & j) == 0 ? 7 : (-72057594037927936L & j) == 0 ? 8 : (Long.MIN_VALUE & j) == 0 ? 9 : 10;
    }

    private final void zzap(int i) {
        byte b = (byte) i;
        if (this.zzbxf.hasRemaining()) {
            this.zzbxf.put(b);
            return;
        }
        throw new zzabz(this.zzbxf.position(), this.zzbxf.limit());
    }

    public static int zzaq(int i) {
        return zzas(i << 3);
    }

    public static int zzas(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (-268435456 & i) == 0 ? 4 : 5;
    }

    public static int zzb(int i, zzacg zzacg) {
        int zzaq = zzaq(i);
        int zzvv = zzacg.zzvv();
        return zzaq + (zzvv + zzas(zzvv));
    }

    public static zzaby zzb(byte[] bArr, int i, int i2) {
        return new zzaby(bArr, 0, i2);
    }

    public static int zzc(int i, long j) {
        return zzaq(i) + zzao(j);
    }

    public static int zzc(int i, String str) {
        return zzaq(i) + zzfk(str);
    }

    public static int zzf(int i, int i2) {
        return zzaq(i) + zzao(i2);
    }

    public static int zzfk(String str) {
        int zza = zza(str);
        return zza + zzas(zza);
    }

    public static zzaby zzj(byte[] bArr) {
        return zzb(bArr, 0, bArr.length);
    }

    public final void zza(int i, double d) {
        zzg(i, 1);
        long doubleToLongBits = Double.doubleToLongBits(d);
        if (this.zzbxf.remaining() < 8) {
            throw new zzabz(this.zzbxf.position(), this.zzbxf.limit());
        }
        this.zzbxf.putLong(doubleToLongBits);
    }

    public final void zza(int i, float f) {
        zzg(i, 5);
        int floatToIntBits = Float.floatToIntBits(f);
        if (this.zzbxf.remaining() < 4) {
            throw new zzabz(this.zzbxf.position(), this.zzbxf.limit());
        }
        this.zzbxf.putInt(floatToIntBits);
    }

    public final void zza(int i, long j) {
        zzg(i, 0);
        zzan(j);
    }

    public final void zza(int i, zzacg zzacg) {
        zzg(i, 2);
        zzb(zzacg);
    }

    public final void zza(int i, boolean z) {
        int i2 = 0;
        zzg(i, 0);
        if (z) {
            i2 = 1;
        }
        byte b = (byte) i2;
        if (this.zzbxf.hasRemaining()) {
            this.zzbxf.put(b);
            return;
        }
        throw new zzabz(this.zzbxf.position(), this.zzbxf.limit());
    }

    public final void zzar(int i) {
        while ((i & -128) != 0) {
            zzap((i & 127) | 128);
            i >>>= 7;
        }
        zzap(i);
    }

    public final void zzb(int i, long j) {
        zzg(i, 0);
        zzan(j);
    }

    public final void zzb(int i, String str) {
        zzg(i, 2);
        try {
            int zzas = zzas(str.length());
            if (zzas == zzas(str.length() * 3)) {
                int position = this.zzbxf.position();
                if (this.zzbxf.remaining() < zzas) {
                    throw new zzabz(zzas + position, this.zzbxf.limit());
                }
                this.zzbxf.position(position + zzas);
                zza((CharSequence) str, this.zzbxf);
                int position2 = this.zzbxf.position();
                this.zzbxf.position(position);
                zzar((position2 - position) - zzas);
                this.zzbxf.position(position2);
                return;
            }
            zzar(zza(str));
            zza((CharSequence) str, this.zzbxf);
        } catch (Throwable e) {
            zzabz zzabz = new zzabz(this.zzbxf.position(), this.zzbxf.limit());
            zzabz.initCause(e);
            throw zzabz;
        }
    }

    public final void zzb(zzacg zzacg) {
        zzar(zzacg.zzvu());
        zzacg.zza(this);
    }

    public final void zze(int i, int i2) {
        zzg(i, 0);
        if (i2 >= 0) {
            zzar(i2);
        } else {
            zzan((long) i2);
        }
    }

    public final void zzg(int i, int i2) {
        zzar((i << 3) | i2);
    }

    public final void zzk(byte[] bArr) {
        int length = bArr.length;
        if (this.zzbxf.remaining() >= length) {
            this.zzbxf.put(bArr, 0, length);
            return;
        }
        throw new zzabz(this.zzbxf.position(), this.zzbxf.limit());
    }

    public final void zzvn() {
        if (this.zzbxf.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", new Object[]{Integer.valueOf(this.zzbxf.remaining())}));
        }
    }
}
