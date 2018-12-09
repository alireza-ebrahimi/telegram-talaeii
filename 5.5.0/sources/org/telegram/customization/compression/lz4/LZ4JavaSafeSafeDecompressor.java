package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import org.telegram.customization.compression.p160a.C2672a;
import org.telegram.customization.compression.p160a.C2675c;

final class LZ4JavaSafeSafeDecompressor extends LZ4SafeDecompressor {
    public static final LZ4SafeDecompressor INSTANCE = new LZ4JavaSafeSafeDecompressor();

    LZ4JavaSafeSafeDecompressor() {
    }

    public int decompress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        if (byteBuffer.hasArray() && byteBuffer2.hasArray()) {
            return decompress(byteBuffer.array(), i + byteBuffer.arrayOffset(), i2, byteBuffer2.array(), i3 + byteBuffer2.arrayOffset(), i4);
        }
        ByteBuffer a = C2672a.m12560a(byteBuffer);
        ByteBuffer a2 = C2672a.m12560a(byteBuffer2);
        C2672a.m12562a(a, i, i2);
        C2672a.m12562a(a2, i3, i4);
        if (i4 != 0) {
            int i5;
            int i6;
            int i7;
            int i8 = i + i2;
            int i9 = i3 + i4;
            int i10 = i3;
            while (true) {
                int i11;
                int b = C2672a.m12564b(a, i) & 255;
                i5 = i + 1;
                i6 = b >>> 4;
                if (i6 == 15) {
                    i7 = i6;
                    i6 = (byte) -1;
                    i11 = i5;
                    while (i11 < i8) {
                        i5 = i11 + 1;
                        i6 = C2672a.m12564b(a, i11);
                        if (i6 != -1) {
                            break;
                        }
                        i7 += 255;
                        i11 = i5;
                    }
                    i5 = i11;
                    i6 = (i6 & 255) + i7;
                }
                i7 = i10 + i6;
                if (i7 <= i9 - 8 && i5 + i6 <= i8 - 8) {
                    LZ4ByteBufferUtils.wildArraycopy(a, i5, a2, i10, i6);
                    i10 = i5 + i6;
                    i = i10 + 2;
                    int e = i7 - C2672a.m12571e(a, i10);
                    if (e < i3) {
                        throw new LZ4Exception("Malformed input at " + i);
                    }
                    i10 = b & 15;
                    if (i10 == 15) {
                        i11 = i10;
                        i5 = i;
                        i10 = (byte) -1;
                        while (i5 < i8) {
                            i6 = i5 + 1;
                            i10 = C2672a.m12564b(a, i5);
                            if (i10 != -1) {
                                break;
                            }
                            i11 += 255;
                            i5 = i6;
                        }
                        i6 = i5;
                        i10 = (i10 & 255) + i11;
                        i = i6;
                    }
                    i6 = i10 + 4;
                    i10 = i7 + i6;
                    if (i10 <= i9 - 8) {
                        LZ4ByteBufferUtils.wildIncrementalCopy(a2, e, i7, i10);
                    } else if (i10 > i9) {
                        throw new LZ4Exception("Malformed input at " + i);
                    } else {
                        LZ4ByteBufferUtils.safeIncrementalCopy(a2, e, i7, i6);
                    }
                } else if (i7 > i9) {
                    throw new LZ4Exception();
                } else if (i5 + i6 == i8) {
                    throw new LZ4Exception("Malformed input at " + i5);
                } else {
                    LZ4ByteBufferUtils.safeArraycopy(a, i5, a2, i10, i6);
                    i10 = i5 + i6;
                    return i7 - i3;
                }
            }
            if (i7 > i9) {
                throw new LZ4Exception();
            } else if (i5 + i6 == i8) {
                LZ4ByteBufferUtils.safeArraycopy(a, i5, a2, i10, i6);
                i10 = i5 + i6;
                return i7 - i3;
            } else {
                throw new LZ4Exception("Malformed input at " + i5);
            }
        } else if (i2 == 1 && C2672a.m12564b(a, i) == (byte) 0) {
            return 0;
        } else {
            throw new LZ4Exception("Output buffer too small");
        }
    }

    public int decompress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        C2675c.m12581a(bArr, i, i2);
        C2675c.m12581a(bArr2, i3, i4);
        if (i4 != 0) {
            int i5;
            int i6;
            int i7;
            int i8 = i + i2;
            int i9 = i3 + i4;
            int i10 = i3;
            while (true) {
                int i11;
                int b = C2675c.m12584b(bArr, i) & 255;
                i5 = i + 1;
                i6 = b >>> 4;
                if (i6 == 15) {
                    i7 = i6;
                    i6 = (byte) -1;
                    i11 = i5;
                    while (i11 < i8) {
                        i5 = i11 + 1;
                        i6 = C2675c.m12584b(bArr, i11);
                        if (i6 != -1) {
                            break;
                        }
                        i7 += 255;
                        i11 = i5;
                    }
                    i5 = i11;
                    i6 = (i6 & 255) + i7;
                }
                i7 = i10 + i6;
                if (i7 <= i9 - 8 && i5 + i6 <= i8 - 8) {
                    LZ4SafeUtils.wildArraycopy(bArr, i5, bArr2, i10, i6);
                    i10 = i5 + i6;
                    i = i10 + 2;
                    int f = i7 - C2675c.m12590f(bArr, i10);
                    if (f < i3) {
                        throw new LZ4Exception("Malformed input at " + i);
                    }
                    i10 = b & 15;
                    if (i10 == 15) {
                        i11 = i10;
                        i5 = i;
                        i10 = (byte) -1;
                        while (i5 < i8) {
                            i6 = i5 + 1;
                            i10 = C2675c.m12584b(bArr, i5);
                            if (i10 != -1) {
                                break;
                            }
                            i11 += 255;
                            i5 = i6;
                        }
                        i6 = i5;
                        i10 = (i10 & 255) + i11;
                        i = i6;
                    }
                    i6 = i10 + 4;
                    i10 = i7 + i6;
                    if (i10 <= i9 - 8) {
                        LZ4SafeUtils.wildIncrementalCopy(bArr2, f, i7, i10);
                    } else if (i10 > i9) {
                        throw new LZ4Exception("Malformed input at " + i);
                    } else {
                        LZ4SafeUtils.safeIncrementalCopy(bArr2, f, i7, i6);
                    }
                } else if (i7 > i9) {
                    throw new LZ4Exception();
                } else if (i5 + i6 == i8) {
                    throw new LZ4Exception("Malformed input at " + i5);
                } else {
                    LZ4SafeUtils.safeArraycopy(bArr, i5, bArr2, i10, i6);
                    i10 = i5 + i6;
                    return i7 - i3;
                }
            }
            if (i7 > i9) {
                throw new LZ4Exception();
            } else if (i5 + i6 == i8) {
                LZ4SafeUtils.safeArraycopy(bArr, i5, bArr2, i10, i6);
                i10 = i5 + i6;
                return i7 - i3;
            } else {
                throw new LZ4Exception("Malformed input at " + i5);
            }
        } else if (i2 == 1 && C2675c.m12584b(bArr, i) == (byte) 0) {
            return 0;
        } else {
            throw new LZ4Exception("Output buffer too small");
        }
    }
}
