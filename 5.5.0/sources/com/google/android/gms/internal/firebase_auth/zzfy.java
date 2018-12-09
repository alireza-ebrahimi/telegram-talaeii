package com.google.android.gms.internal.firebase_auth;

import java.nio.ByteBuffer;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

abstract class zzfy {
    zzfy() {
    }

    static void zzc(CharSequence charSequence, ByteBuffer byteBuffer) {
        int i;
        int length = charSequence.length();
        int position = byteBuffer.position();
        int i2 = 0;
        while (i2 < length) {
            char charAt;
            try {
                charAt = charSequence.charAt(i2);
                if (charAt >= '') {
                    break;
                }
                byteBuffer.put(position + i2, (byte) charAt);
                i2++;
            } catch (IndexOutOfBoundsException e) {
            }
        }
        if (i2 == length) {
            byteBuffer.position(position + i2);
            return;
        }
        position += i2;
        while (i2 < length) {
            char charAt2 = charSequence.charAt(i2);
            if (charAt2 < '') {
                byteBuffer.put(position, (byte) charAt2);
            } else if (charAt2 < 'ࠀ') {
                i = position + 1;
                byteBuffer.put(position, (byte) ((charAt2 >>> 6) | PsExtractor.AUDIO_STREAM));
                byteBuffer.put(i, (byte) ((charAt2 & 63) | 128));
                position = i;
            } else if (charAt2 < '?' || '?' < charAt2) {
                i = position + 1;
                byteBuffer.put(position, (byte) ((charAt2 >>> 12) | 224));
                position = i + 1;
                byteBuffer.put(i, (byte) (((charAt2 >>> 6) & 63) | 128));
                byteBuffer.put(position, (byte) ((charAt2 & 63) | 128));
            } else {
                if (i2 + 1 != length) {
                    i2++;
                    charAt = charSequence.charAt(i2);
                    if (Character.isSurrogatePair(charAt2, charAt)) {
                        int toCodePoint = Character.toCodePoint(charAt2, charAt);
                        int i3 = position + 1;
                        try {
                            byteBuffer.put(position, (byte) ((toCodePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                            i = i3 + 1;
                        } catch (IndexOutOfBoundsException e2) {
                            position = i3;
                        }
                        try {
                            byteBuffer.put(i3, (byte) (((toCodePoint >>> 12) & 63) | 128));
                            position = i + 1;
                            byteBuffer.put(i, (byte) (((toCodePoint >>> 6) & 63) | 128));
                            byteBuffer.put(position, (byte) ((toCodePoint & 63) | 128));
                        } catch (IndexOutOfBoundsException e3) {
                            position = i;
                        }
                    }
                }
                throw new zzga(i2, length);
            }
            i2++;
            position++;
        }
        byteBuffer.position(position);
        return;
        throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i2) + " at index " + (Math.max(i2, (position - byteBuffer.position()) + 1) + byteBuffer.position()));
    }

    abstract int zzb(int i, byte[] bArr, int i2, int i3);

    abstract int zzb(CharSequence charSequence, byte[] bArr, int i, int i2);

    abstract void zzb(CharSequence charSequence, ByteBuffer byteBuffer);

    final boolean zzf(byte[] bArr, int i, int i2) {
        return zzb(0, bArr, i, i2) == 0;
    }
}
