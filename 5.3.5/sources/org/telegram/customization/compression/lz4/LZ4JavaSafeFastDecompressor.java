package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import org.telegram.customization.compression.util.ByteBufferUtils;
import org.telegram.customization.compression.util.SafeUtils;

final class LZ4JavaSafeFastDecompressor extends LZ4FastDecompressor {
    public static final LZ4FastDecompressor INSTANCE = new LZ4JavaSafeFastDecompressor();

    LZ4JavaSafeFastDecompressor() {
    }

    public int decompress(byte[] src, int srcOff, byte[] dest, int destOff, int destLen) {
        SafeUtils.checkRange(src, srcOff);
        SafeUtils.checkRange(dest, destOff, destLen);
        if (destLen != 0) {
            int literalLen;
            int literalCopyEnd;
            int destEnd = destOff + destLen;
            int sOff = srcOff;
            int dOff = destOff;
            while (true) {
                int sOff2;
                byte len;
                int token = SafeUtils.readByte(src, sOff) & 255;
                sOff++;
                literalLen = token >>> 4;
                if (literalLen == 15) {
                    while (true) {
                        sOff2 = sOff + 1;
                        len = SafeUtils.readByte(src, sOff);
                        if (len != (byte) -1) {
                            break;
                        }
                        literalLen += 255;
                        sOff = sOff2;
                    }
                    literalLen += len & 255;
                    sOff = sOff2;
                }
                literalCopyEnd = dOff + literalLen;
                if (literalCopyEnd > destEnd - 8) {
                    break;
                }
                LZ4SafeUtils.wildArraycopy(src, sOff, dest, dOff, literalLen);
                sOff += literalLen;
                dOff = literalCopyEnd;
                int matchDec = SafeUtils.readShortLE(src, sOff);
                sOff += 2;
                int matchOff = dOff - matchDec;
                if (matchOff < destOff) {
                    throw new LZ4Exception("Malformed input at " + sOff);
                }
                int matchLen = token & 15;
                if (matchLen == 15) {
                    while (true) {
                        sOff2 = sOff + 1;
                        len = SafeUtils.readByte(src, sOff);
                        if (len != (byte) -1) {
                            break;
                        }
                        matchLen += 255;
                        sOff = sOff2;
                    }
                    matchLen += len & 255;
                    sOff = sOff2;
                }
                matchLen += 4;
                int matchCopyEnd = dOff + matchLen;
                if (matchCopyEnd <= destEnd - 8) {
                    LZ4SafeUtils.wildIncrementalCopy(dest, matchOff, dOff, matchCopyEnd);
                } else if (matchCopyEnd > destEnd) {
                    throw new LZ4Exception("Malformed input at " + sOff);
                } else {
                    LZ4SafeUtils.safeIncrementalCopy(dest, matchOff, dOff, matchLen);
                }
                dOff = matchCopyEnd;
            }
            if (literalCopyEnd != destEnd) {
                throw new LZ4Exception("Malformed input at " + sOff);
            }
            LZ4SafeUtils.safeArraycopy(src, sOff, dest, dOff, literalLen);
            dOff = literalCopyEnd;
            return (sOff + literalLen) - srcOff;
        } else if (SafeUtils.readByte(src, srcOff) == (byte) 0) {
            return 1;
        } else {
            throw new LZ4Exception("Malformed input at " + srcOff);
        }
    }

    public int decompress(ByteBuffer src, int srcOff, ByteBuffer dest, int destOff, int destLen) {
        if (src.hasArray() && dest.hasArray()) {
            return decompress(src.array(), srcOff + src.arrayOffset(), dest.array(), destOff + dest.arrayOffset(), destLen);
        }
        src = ByteBufferUtils.inNativeByteOrder(src);
        dest = ByteBufferUtils.inNativeByteOrder(dest);
        ByteBufferUtils.checkRange(src, srcOff);
        ByteBufferUtils.checkRange(dest, destOff, destLen);
        if (destLen != 0) {
            int literalLen;
            int literalCopyEnd;
            int destEnd = destOff + destLen;
            int sOff = srcOff;
            int dOff = destOff;
            while (true) {
                int sOff2;
                byte len;
                int token = ByteBufferUtils.readByte(src, sOff) & 255;
                sOff++;
                literalLen = token >>> 4;
                if (literalLen == 15) {
                    while (true) {
                        sOff2 = sOff + 1;
                        len = ByteBufferUtils.readByte(src, sOff);
                        if (len != (byte) -1) {
                            break;
                        }
                        literalLen += 255;
                        sOff = sOff2;
                    }
                    literalLen += len & 255;
                    sOff = sOff2;
                }
                literalCopyEnd = dOff + literalLen;
                if (literalCopyEnd > destEnd - 8) {
                    break;
                }
                LZ4ByteBufferUtils.wildArraycopy(src, sOff, dest, dOff, literalLen);
                sOff += literalLen;
                dOff = literalCopyEnd;
                sOff += 2;
                int matchOff = dOff - ByteBufferUtils.readShortLE(src, sOff);
                if (matchOff < destOff) {
                    throw new LZ4Exception("Malformed input at " + sOff);
                }
                int matchLen = token & 15;
                if (matchLen == 15) {
                    while (true) {
                        sOff2 = sOff + 1;
                        len = ByteBufferUtils.readByte(src, sOff);
                        if (len != (byte) -1) {
                            break;
                        }
                        matchLen += 255;
                        sOff = sOff2;
                    }
                    matchLen += len & 255;
                    sOff = sOff2;
                }
                matchLen += 4;
                int matchCopyEnd = dOff + matchLen;
                if (matchCopyEnd <= destEnd - 8) {
                    LZ4ByteBufferUtils.wildIncrementalCopy(dest, matchOff, dOff, matchCopyEnd);
                } else if (matchCopyEnd > destEnd) {
                    throw new LZ4Exception("Malformed input at " + sOff);
                } else {
                    LZ4ByteBufferUtils.safeIncrementalCopy(dest, matchOff, dOff, matchLen);
                }
                dOff = matchCopyEnd;
            }
            if (literalCopyEnd != destEnd) {
                throw new LZ4Exception("Malformed input at " + sOff);
            }
            LZ4ByteBufferUtils.safeArraycopy(src, sOff, dest, dOff, literalLen);
            dOff = literalCopyEnd;
            return (sOff + literalLen) - srcOff;
        } else if (ByteBufferUtils.readByte(src, srcOff) == (byte) 0) {
            return 1;
        } else {
            throw new LZ4Exception("Malformed input at " + srcOff);
        }
    }
}
