package com.google.android.gms.internal.firebase_auth;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

public final class zzgl {
    private final ByteBuffer zznk;
    private zzci zzxp;
    private int zzxq;

    private zzgl(ByteBuffer byteBuffer) {
        this.zznk = byteBuffer;
        this.zznk.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzgl(byte[] bArr, int i, int i2) {
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

    public static int zzaa(int i) {
        return zzai(i << 3);
    }

    public static int zzab(int i) {
        return i >= 0 ? zzai(i) : 10;
    }

    public static int zzai(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (-268435456 & i) == 0 ? 4 : 5;
    }

    public static int zzam(String str) {
        int zza = zza(str);
        return zza + zzai(zza);
    }

    private final void zzaz(int i) {
        byte b = (byte) i;
        if (this.zznk.hasRemaining()) {
            this.zznk.put(b);
            return;
        }
        throw new zzgm(this.zznk.position(), this.zznk.limit());
    }

    public static int zzb(int i, zzgt zzgt) {
        int zzaa = zzaa(i);
        int zzdq = zzgt.zzdq();
        return zzaa + (zzdq + zzai(zzdq));
    }

    public static int zzb(int i, String str) {
        return zzaa(i) + zzam(str);
    }

    public static int zzb(int i, byte[] bArr) {
        return zzaa(i) + (zzai(bArr.length) + bArr.length);
    }

    private final void zzb(int i, int i2) {
        zzba((i << 3) | i2);
    }

    public static int zzd(int i, long j) {
        return zzaa(i) + zzn(j);
    }

    private static void zzd(CharSequence charSequence, ByteBuffer byteBuffer) {
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

    public static int zze(int i, long j) {
        return zzaa(i) + zzn(j);
    }

    public static zzgl zzf(byte[] bArr) {
        return zzj(bArr, 0, bArr.length);
    }

    public static int zzg(int i, int i2) {
        return zzaa(i) + zzab(i2);
    }

    public static zzgl zzj(byte[] bArr, int i, int i2) {
        return new zzgl(bArr, 0, i2);
    }

    private final void zzm(long j) {
        while ((-128 & j) != 0) {
            zzaz((((int) j) & 127) | 128);
            j >>>= 7;
        }
        zzaz((int) j);
    }

    private static int zzn(long j) {
        return (-128 & j) == 0 ? 1 : (-16384 & j) == 0 ? 2 : (-2097152 & j) == 0 ? 3 : (-268435456 & j) == 0 ? 4 : (-34359738368L & j) == 0 ? 5 : (-4398046511104L & j) == 0 ? 6 : (-562949953421312L & j) == 0 ? 7 : (-72057594037927936L & j) == 0 ? 8 : (Long.MIN_VALUE & j) == 0 ? 9 : 10;
    }

    public final void zza(int i, long j) {
        zzb(i, 0);
        zzm(j);
    }

    public final void zza(int i, zzgt zzgt) {
        zzb(i, 2);
        if (zzgt.zzya < 0) {
            zzgt.zzdq();
        }
        zzba(zzgt.zzya);
        zzgt.zza(this);
    }

    public final void zza(int i, String str) {
        zzb(i, 2);
        try {
            int zzai = zzai(str.length());
            if (zzai == zzai(str.length() * 3)) {
                int position = this.zznk.position();
                if (this.zznk.remaining() < zzai) {
                    throw new zzgm(zzai + position, this.zznk.limit());
                }
                this.zznk.position(position + zzai);
                zzd((CharSequence) str, this.zznk);
                int position2 = this.zznk.position();
                this.zznk.position(position);
                zzba((position2 - position) - zzai);
                this.zznk.position(position2);
                return;
            }
            zzba(zza(str));
            zzd((CharSequence) str, this.zznk);
        } catch (Throwable e) {
            zzgm zzgm = new zzgm(this.zznk.position(), this.zznk.limit());
            zzgm.initCause(e);
            throw zzgm;
        }
    }

    public final void zza(int i, byte[] bArr) {
        zzb(i, 2);
        zzba(bArr.length);
        zzg(bArr);
    }

    public final void zzb(int i, boolean z) {
        int i2 = 0;
        zzb(i, 0);
        if (z) {
            i2 = 1;
        }
        byte b = (byte) i2;
        if (this.zznk.hasRemaining()) {
            this.zznk.put(b);
            return;
        }
        throw new zzgm(this.zznk.position(), this.zznk.limit());
    }

    public final void zzba(int i) {
        while ((i & -128) != 0) {
            zzaz((i & 127) | 128);
            i >>>= 7;
        }
        zzaz(i);
    }

    public final void zzc(int i, int i2) {
        zzb(i, 0);
        if (i2 >= 0) {
            zzba(i2);
        } else {
            zzm((long) i2);
        }
    }

    public final void zze(int i, zzeh zzeh) {
        if (this.zzxp == null) {
            this.zzxp = zzci.zza(this.zznk);
            this.zzxq = this.zznk.position();
        } else if (this.zzxq != this.zznk.position()) {
            this.zzxp.write(this.zznk.array(), this.zzxq, this.zznk.position() - this.zzxq);
            this.zzxq = this.zznk.position();
        }
        zzci zzci = this.zzxp;
        zzci.zza(13, zzeh);
        zzci.flush();
        this.zzxq = this.zznk.position();
    }

    public final void zzg(byte[] bArr) {
        int length = bArr.length;
        if (this.zznk.remaining() >= length) {
            this.zznk.put(bArr, 0, length);
            return;
        }
        throw new zzgm(this.zznk.position(), this.zznk.limit());
    }

    public final void zzgm() {
        if (this.zznk.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", new Object[]{Integer.valueOf(this.zznk.remaining())}));
        }
    }

    public final void zzi(int i, long j) {
        zzb(i, 0);
        zzm(j);
    }
}
