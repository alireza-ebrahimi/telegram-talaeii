package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import org.telegram.customization.compression.util.ByteBufferUtils;
import org.telegram.customization.compression.util.SafeUtils;

final class LZ4JavaSafeSafeDecompressor extends LZ4SafeDecompressor {
    public static final LZ4SafeDecompressor INSTANCE = new LZ4JavaSafeSafeDecompressor();

    LZ4JavaSafeSafeDecompressor() {
    }

    public int decompress(byte[] src, int srcOff, int srcLen, byte[] dest, int destOff, int destLen) {
        SafeUtils.checkRange(src, srcOff, srcLen);
        SafeUtils.checkRange(dest, destOff, destLen);
        if (destLen != 0) {
            int literalLen;
            int literalCopyEnd;
            int srcEnd = srcOff + srcLen;
            int destEnd = destOff + destLen;
            int sOff = srcOff;
            int dOff = destOff;
            while (true) {
                byte len;
                int sOff2;
                int token = SafeUtils.readByte(src, sOff) & 255;
                sOff++;
                literalLen = token >>> 4;
                if (literalLen == 15) {
                    len = (byte) -1;
                    sOff2 = sOff;
                    while (sOff2 < srcEnd) {
                        sOff = sOff2 + 1;
                        len = SafeUtils.readByte(src, sOff2);
                        if (len != (byte) -1) {
                            break;
                        }
                        literalLen += 255;
                        sOff2 = sOff;
                    }
                    sOff = sOff2;
                    literalLen += len & 255;
                }
                literalCopyEnd = dOff + literalLen;
                if (literalCopyEnd <= destEnd - 8 && sOff + literalLen <= srcEnd - 8) {
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
                        len = (byte) -1;
                        sOff2 = sOff;
                        while (sOff2 < srcEnd) {
                            sOff = sOff2 + 1;
                            len = SafeUtils.readByte(src, sOff2);
                            if (len != (byte) -1) {
                                break;
                            }
                            matchLen += 255;
                            sOff2 = sOff;
                        }
                        sOff = sOff2;
                        matchLen += len & 255;
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
                } else if (literalCopyEnd > destEnd) {
                    throw new LZ4Exception();
                } else if (sOff + literalLen == srcEnd) {
                    throw new LZ4Exception("Malformed input at " + sOff);
                } else {
                    LZ4SafeUtils.safeArraycopy(src, sOff, dest, dOff, literalLen);
                    sOff += literalLen;
                    return literalCopyEnd - destOff;
                }
            }
            if (literalCopyEnd > destEnd) {
                throw new LZ4Exception();
            } else if (sOff + literalLen == srcEnd) {
                LZ4SafeUtils.safeArraycopy(src, sOff, dest, dOff, literalLen);
                sOff += literalLen;
                return literalCopyEnd - destOff;
            } else {
                throw new LZ4Exception("Malformed input at " + sOff);
            }
        } else if (srcLen == 1 && SafeUtils.readByte(src, srcOff) == (byte) 0) {
            return 0;
        } else {
            throw new LZ4Exception("Output buffer too small");
        }
    }

    public int decompress(ByteBuffer src, int srcOff, int srcLen, ByteBuffer dest, int destOff, int destLen) {
        if (src.hasArray() && dest.hasArray()) {
            return decompress(src.array(), srcOff + src.arrayOffset(), srcLen, dest.array(), destOff + dest.arrayOffset(), destLen);
        }
        src = ByteBufferUtils.inNativeByteOrder(src);
        dest = ByteBufferUtils.inNativeByteOrder(dest);
        ByteBufferUtils.checkRange(src, srcOff, srcLen);
        ByteBufferUtils.checkRange(dest, destOff, destLen);
        if (destLen != 0) {
            int literalLen;
            int literalCopyEnd;
            int srcEnd = srcOff + srcLen;
            int destEnd = destOff + destLen;
            int sOff = srcOff;
            int dOff = destOff;
            while (true) {
                byte len;
                int sOff2;
                int token = ByteBufferUtils.readByte(src, sOff) & 255;
                sOff++;
                literalLen = token >>> 4;
                if (literalLen == 15) {
                    len = (byte) -1;
                    sOff2 = sOff;
                    while (sOff2 < srcEnd) {
                        sOff = sOff2 + 1;
                        len = ByteBufferUtils.readByte(src, sOff2);
                        if (len != (byte) -1) {
                            break;
                        }
                        literalLen += 255;
                        sOff2 = sOff;
                    }
                    sOff = sOff2;
                    literalLen += len & 255;
                }
                literalCopyEnd = dOff + literalLen;
                if (literalCopyEnd <= destEnd - 8 && sOff + literalLen <= srcEnd - 8) {
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
                        len = (byte) -1;
                        sOff2 = sOff;
                        while (sOff2 < srcEnd) {
                            sOff = sOff2 + 1;
                            len = ByteBufferUtils.readByte(src, sOff2);
                            if (len != (byte) -1) {
                                break;
                            }
                            matchLen += 255;
                            sOff2 = sOff;
                        }
                        sOff = sOff2;
                        matchLen += len & 255;
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
                } else if (literalCopyEnd > destEnd) {
                    throw new LZ4Exception();
                } else if (sOff + literalLen == srcEnd) {
                    throw new LZ4Exception("Malformed input at " + sOff);
                } else {
                    LZ4ByteBufferUtils.safeArraycopy(src, sOff, dest, dOff, literalLen);
                    sOff += literalLen;
                    return literalCopyEnd - destOff;
                }
            }
            if (literalCopyEnd > destEnd) {
                throw new LZ4Exception();
            } else if (sOff + literalLen == srcEnd) {
                LZ4ByteBufferUtils.safeArraycopy(src, sOff, dest, dOff, literalLen);
                sOff += literalLen;
                return literalCopyEnd - destOff;
            } else {
                throw new LZ4Exception("Malformed input at " + sOff);
            }
        } else if (srcLen == 1 && ByteBufferUtils.readByte(src, srcOff) == (byte) 0) {
            return 0;
        } else {
            throw new LZ4Exception("Output buffer too small");
        }
    }
}
