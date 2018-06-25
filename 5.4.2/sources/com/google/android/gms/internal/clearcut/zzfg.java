package com.google.android.gms.internal.clearcut;

import java.nio.ByteBuffer;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

abstract class zzfg {
    zzfg() {
    }

    static void zzc(CharSequence charSequence, ByteBuffer byteBuffer) {
        int length = charSequence.length();
        int position = byteBuffer.position();
        int i = 0;
        while (i < length) {
            char charAt;
            try {
                charAt = charSequence.charAt(i);
                if (charAt >= '') {
                    break;
                }
                byteBuffer.put(position + i, (byte) charAt);
                i++;
            } catch (IndexOutOfBoundsException e) {
            }
        }
        if (i == length) {
            byteBuffer.position(position + i);
            return;
        }
        position += i;
        while (i < length) {
            char charAt2 = charSequence.charAt(i);
            if (charAt2 < '') {
                byteBuffer.put(position, (byte) charAt2);
            } else if (charAt2 < 'ࠀ') {
                r2 = position + 1;
                byteBuffer.put(position, (byte) ((charAt2 >>> 6) | PsExtractor.AUDIO_STREAM));
                byteBuffer.put(r2, (byte) ((charAt2 & 63) | 128));
                position = r2;
            } else if (charAt2 < '?' || '?' < charAt2) {
                r2 = position + 1;
                byteBuffer.put(position, (byte) ((charAt2 >>> 12) | 224));
                position = r2 + 1;
                byteBuffer.put(r2, (byte) (((charAt2 >>> 6) & 63) | 128));
                byteBuffer.put(position, (byte) ((charAt2 & 63) | 128));
            } else {
                if (i + 1 != length) {
                    i++;
                    charAt = charSequence.charAt(i);
                    if (Character.isSurrogatePair(charAt2, charAt)) {
                        int toCodePoint = Character.toCodePoint(charAt2, charAt);
                        int i2 = position + 1;
                        try {
                            byteBuffer.put(position, (byte) ((toCodePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                            r2 = i2 + 1;
                        } catch (IndexOutOfBoundsException e2) {
                            position = i2;
                        }
                        try {
                            byteBuffer.put(i2, (byte) (((toCodePoint >>> 12) & 63) | 128));
                            position = r2 + 1;
                            byteBuffer.put(r2, (byte) (((toCodePoint >>> 6) & 63) | 128));
                            byteBuffer.put(position, (byte) ((toCodePoint & 63) | 128));
                        } catch (IndexOutOfBoundsException e3) {
                            position = r2;
                        }
                    }
                }
                throw new zzfi(i, length);
            }
            i++;
            position++;
        }
        byteBuffer.position(position);
        return;
        throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i) + " at index " + (Math.max(i, (position - byteBuffer.position()) + 1) + byteBuffer.position()));
    }

    abstract int zzb(int i, byte[] bArr, int i2, int i3);

    abstract int zzb(CharSequence charSequence, byte[] bArr, int i, int i2);

    abstract void zzb(CharSequence charSequence, ByteBuffer byteBuffer);

    final boolean zze(byte[] bArr, int i, int i2) {
        return zzb(0, bArr, i, i2) == 0;
    }
}
