package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import org.telegram.customization.compression.p160a.C2672a;
import org.telegram.customization.compression.p160a.C2675c;

final class LZ4JavaSafeFastDecompressor extends LZ4FastDecompressor {
    public static final LZ4FastDecompressor INSTANCE = new LZ4JavaSafeFastDecompressor();

    LZ4JavaSafeFastDecompressor() {
    }

    public int decompress(ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, int i3) {
        if (byteBuffer.hasArray() && byteBuffer2.hasArray()) {
            return decompress(byteBuffer.array(), i + byteBuffer.arrayOffset(), byteBuffer2.array(), i2 + byteBuffer2.arrayOffset(), i3);
        }
        ByteBuffer a = C2672a.m12560a(byteBuffer);
        ByteBuffer a2 = C2672a.m12560a(byteBuffer2);
        C2672a.m12561a(a, i);
        C2672a.m12562a(a2, i2, i3);
        if (i3 != 0) {
            int i4;
            int i5;
            int i6 = i2 + i3;
            int i7 = i2;
            int i8 = i;
            while (true) {
                int b = C2672a.m12564b(a, i8) & 255;
                int i9 = i8 + 1;
                i8 = b >>> 4;
                if (i8 == 15) {
                    byte b2;
                    while (true) {
                        i4 = i9 + 1;
                        b2 = C2672a.m12564b(a, i9);
                        if (b2 != (byte) -1) {
                            break;
                        }
                        i8 += 255;
                        i9 = i4;
                    }
                    i8 += b2 & 255;
                } else {
                    i4 = i9;
                }
                i5 = i7 + i8;
                if (i5 > i6 - 8) {
                    break;
                }
                LZ4ByteBufferUtils.wildArraycopy(a, i4, a2, i7, i8);
                i7 = i4 + i8;
                i8 = i7 + 2;
                i4 = i5 - C2672a.m12571e(a, i7);
                if (i4 < i2) {
                    throw new LZ4Exception("Malformed input at " + i8);
                }
                i7 = b & 15;
                if (i7 == 15) {
                    byte b3;
                    while (true) {
                        i9 = i8 + 1;
                        b3 = C2672a.m12564b(a, i8);
                        if (b3 != (byte) -1) {
                            break;
                        }
                        i7 += 255;
                        i8 = i9;
                    }
                    i7 += b3 & 255;
                    i8 = i9;
                }
                i9 = i7 + 4;
                i7 = i5 + i9;
                if (i7 <= i6 - 8) {
                    LZ4ByteBufferUtils.wildIncrementalCopy(a2, i4, i5, i7);
                } else if (i7 > i6) {
                    throw new LZ4Exception("Malformed input at " + i8);
                } else {
                    LZ4ByteBufferUtils.safeIncrementalCopy(a2, i4, i5, i9);
                }
            }
            if (i5 != i6) {
                throw new LZ4Exception("Malformed input at " + i4);
            }
            LZ4ByteBufferUtils.safeArraycopy(a, i4, a2, i7, i8);
            return (i4 + i8) - i;
        } else if (C2672a.m12564b(a, i) == (byte) 0) {
            return 1;
        } else {
            throw new LZ4Exception("Malformed input at " + i);
        }
    }

    public int decompress(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        C2675c.m12580a(bArr, i);
        C2675c.m12581a(bArr2, i2, i3);
        if (i3 != 0) {
            int i4;
            int i5;
            int i6 = i2 + i3;
            int i7 = i2;
            int i8 = i;
            while (true) {
                int b = C2675c.m12584b(bArr, i8) & 255;
                int i9 = i8 + 1;
                i8 = b >>> 4;
                if (i8 == 15) {
                    byte b2;
                    while (true) {
                        i4 = i9 + 1;
                        b2 = C2675c.m12584b(bArr, i9);
                        if (b2 != (byte) -1) {
                            break;
                        }
                        i8 += 255;
                        i9 = i4;
                    }
                    i8 += b2 & 255;
                } else {
                    i4 = i9;
                }
                i5 = i7 + i8;
                if (i5 > i6 - 8) {
                    break;
                }
                LZ4SafeUtils.wildArraycopy(bArr, i4, bArr2, i7, i8);
                i7 = i4 + i8;
                i8 = i7 + 2;
                i4 = i5 - C2675c.m12590f(bArr, i7);
                if (i4 < i2) {
                    throw new LZ4Exception("Malformed input at " + i8);
                }
                i7 = b & 15;
                if (i7 == 15) {
                    byte b3;
                    while (true) {
                        i9 = i8 + 1;
                        b3 = C2675c.m12584b(bArr, i8);
                        if (b3 != (byte) -1) {
                            break;
                        }
                        i7 += 255;
                        i8 = i9;
                    }
                    i7 += b3 & 255;
                    i8 = i9;
                }
                i9 = i7 + 4;
                i7 = i5 + i9;
                if (i7 <= i6 - 8) {
                    LZ4SafeUtils.wildIncrementalCopy(bArr2, i4, i5, i7);
                } else if (i7 > i6) {
                    throw new LZ4Exception("Malformed input at " + i8);
                } else {
                    LZ4SafeUtils.safeIncrementalCopy(bArr2, i4, i5, i9);
                }
            }
            if (i5 != i6) {
                throw new LZ4Exception("Malformed input at " + i4);
            }
            LZ4SafeUtils.safeArraycopy(bArr, i4, bArr2, i7, i8);
            return (i4 + i8) - i;
        } else if (C2675c.m12584b(bArr, i) == (byte) 0) {
            return 1;
        } else {
            throw new LZ4Exception("Malformed input at " + i);
        }
    }
}
