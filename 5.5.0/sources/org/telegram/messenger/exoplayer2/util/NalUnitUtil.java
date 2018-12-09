package org.telegram.messenger.exoplayer2.util;

import android.util.Log;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;

public final class NalUnitUtil {
    public static final float[] ASPECT_RATIO_IDC_VALUES = new float[]{1.0f, 1.0f, 1.0909091f, 0.90909094f, 1.4545455f, 1.2121212f, 2.1818182f, 1.8181819f, 2.909091f, 2.4242425f, 1.6363636f, 1.3636364f, 1.939394f, 1.6161616f, 1.3333334f, 1.5f, 2.0f};
    public static final int EXTENDED_SAR = 255;
    private static final int H264_NAL_UNIT_TYPE_SEI = 6;
    private static final int H264_NAL_UNIT_TYPE_SPS = 7;
    private static final int H265_NAL_UNIT_TYPE_PREFIX_SEI = 39;
    public static final byte[] NAL_START_CODE = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 1};
    private static final String TAG = "NalUnitUtil";
    private static int[] scratchEscapePositions = new int[10];
    private static final Object scratchEscapePositionsLock = new Object();

    public static final class PpsData {
        public final boolean bottomFieldPicOrderInFramePresentFlag;
        public final int picParameterSetId;
        public final int seqParameterSetId;

        public PpsData(int i, int i2, boolean z) {
            this.picParameterSetId = i;
            this.seqParameterSetId = i2;
            this.bottomFieldPicOrderInFramePresentFlag = z;
        }
    }

    public static final class SpsData {
        public final boolean deltaPicOrderAlwaysZeroFlag;
        public final boolean frameMbsOnlyFlag;
        public final int frameNumLength;
        public final int height;
        public final int picOrderCntLsbLength;
        public final int picOrderCountType;
        public final float pixelWidthAspectRatio;
        public final boolean separateColorPlaneFlag;
        public final int seqParameterSetId;
        public final int width;

        public SpsData(int i, int i2, int i3, float f, boolean z, boolean z2, int i4, int i5, int i6, boolean z3) {
            this.seqParameterSetId = i;
            this.width = i2;
            this.height = i3;
            this.pixelWidthAspectRatio = f;
            this.separateColorPlaneFlag = z;
            this.frameMbsOnlyFlag = z2;
            this.frameNumLength = i4;
            this.picOrderCountType = i5;
            this.picOrderCntLsbLength = i6;
            this.deltaPicOrderAlwaysZeroFlag = z3;
        }
    }

    private NalUnitUtil() {
    }

    public static void clearPrefixFlags(boolean[] zArr) {
        zArr[0] = false;
        zArr[1] = false;
        zArr[2] = false;
    }

    public static void discardToSps(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        int i = 0;
        int i2 = 0;
        while (i + 1 < position) {
            int i3 = byteBuffer.get(i) & 255;
            if (i2 == 3) {
                if (i3 == 1 && (byteBuffer.get(i + 1) & 31) == 7) {
                    ByteBuffer duplicate = byteBuffer.duplicate();
                    duplicate.position(i - 3);
                    duplicate.limit(position);
                    byteBuffer.position(0);
                    byteBuffer.put(duplicate);
                    return;
                }
            } else if (i3 == 0) {
                i2++;
            }
            if (i3 != 0) {
                i2 = 0;
            }
            i++;
        }
        byteBuffer.clear();
    }

    public static int findNalUnit(byte[] bArr, int i, int i2, boolean[] zArr) {
        boolean z = true;
        int i3 = i2 - i;
        Assertions.checkState(i3 >= 0);
        if (i3 == 0) {
            return i2;
        }
        if (zArr != null) {
            if (zArr[0]) {
                clearPrefixFlags(zArr);
                return i - 3;
            } else if (i3 > 1 && zArr[1] && bArr[i] == (byte) 1) {
                clearPrefixFlags(zArr);
                return i - 2;
            } else if (i3 > 2 && zArr[2] && bArr[i] == (byte) 0 && bArr[i + 1] == (byte) 1) {
                clearPrefixFlags(zArr);
                return i - 1;
            }
        }
        int i4 = i2 - 1;
        int i5 = i + 2;
        while (i5 < i4) {
            if ((bArr[i5] & 254) == 0) {
                if (bArr[i5 - 2] == (byte) 0 && bArr[i5 - 1] == (byte) 0 && bArr[i5] == (byte) 1) {
                    if (zArr != null) {
                        clearPrefixFlags(zArr);
                    }
                    return i5 - 2;
                }
                i5 -= 2;
            }
            i5 += 3;
        }
        if (zArr == null) {
            return i2;
        }
        boolean z2 = i3 > 2 ? bArr[i2 + -3] == (byte) 0 && bArr[i2 - 2] == (byte) 0 && bArr[i2 - 1] == (byte) 1 : i3 == 2 ? zArr[2] && bArr[i2 - 2] == (byte) 0 && bArr[i2 - 1] == (byte) 1 : zArr[1] && bArr[i2 - 1] == (byte) 1;
        zArr[0] = z2;
        z2 = i3 > 1 ? bArr[i2 + -2] == (byte) 0 && bArr[i2 - 1] == (byte) 0 : zArr[2] && bArr[i2 - 1] == (byte) 0;
        zArr[1] = z2;
        if (bArr[i2 - 1] != (byte) 0) {
            z = false;
        }
        zArr[2] = z;
        return i2;
    }

    private static int findNextUnescapeIndex(byte[] bArr, int i, int i2) {
        int i3 = i;
        while (i3 < i2 - 2) {
            if (bArr[i3] == (byte) 0 && bArr[i3 + 1] == (byte) 0 && bArr[i3 + 2] == (byte) 3) {
                return i3;
            }
            i3++;
        }
        return i2;
    }

    public static int getH265NalUnitType(byte[] bArr, int i) {
        return (bArr[i + 3] & 126) >> 1;
    }

    public static int getNalUnitType(byte[] bArr, int i) {
        return bArr[i + 3] & 31;
    }

    public static boolean isNalUnitSei(String str, byte b) {
        return ("video/avc".equals(str) && (b & 31) == 6) || (MimeTypes.VIDEO_H265.equals(str) && ((b & 126) >> 1) == 39);
    }

    public static PpsData parsePpsNalUnit(byte[] bArr, int i, int i2) {
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(bArr, i, i2);
        parsableNalUnitBitArray.skipBits(8);
        int readUnsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int readUnsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.skipBit();
        return new PpsData(readUnsignedExpGolombCodedInt, readUnsignedExpGolombCodedInt2, parsableNalUnitBitArray.readBit());
    }

    public static SpsData parseSpsNalUnit(byte[] bArr, int i, int i2) {
        int readUnsignedExpGolombCodedInt;
        int i3;
        boolean z;
        int i4;
        float f;
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(bArr, i, i2);
        parsableNalUnitBitArray.skipBits(8);
        int readBits = parsableNalUnitBitArray.readBits(8);
        parsableNalUnitBitArray.skipBits(16);
        int readUnsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        boolean z2 = false;
        if (readBits == 100 || readBits == 110 || readBits == 122 || readBits == 244 || readBits == 44 || readBits == 83 || readBits == 86 || readBits == 118 || readBits == 128 || readBits == TsExtractor.TS_STREAM_TYPE_DTS) {
            readUnsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            if (readUnsignedExpGolombCodedInt == 3) {
                z2 = parsableNalUnitBitArray.readBit();
            }
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.skipBit();
            if (parsableNalUnitBitArray.readBit()) {
                i3 = readUnsignedExpGolombCodedInt != 3 ? 8 : 12;
                int i5 = 0;
                while (i5 < i3) {
                    if (parsableNalUnitBitArray.readBit()) {
                        skipScalingList(parsableNalUnitBitArray, i5 < 6 ? 16 : 64);
                    }
                    i5++;
                }
            }
            z = z2;
            i4 = readUnsignedExpGolombCodedInt;
        } else {
            z = false;
            i4 = 1;
        }
        int readUnsignedExpGolombCodedInt3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 4;
        int readUnsignedExpGolombCodedInt4 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int i6 = 0;
        boolean z3 = false;
        if (readUnsignedExpGolombCodedInt4 == 0) {
            i6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 4;
        } else if (readUnsignedExpGolombCodedInt4 == 1) {
            z3 = parsableNalUnitBitArray.readBit();
            parsableNalUnitBitArray.readSignedExpGolombCodedInt();
            parsableNalUnitBitArray.readSignedExpGolombCodedInt();
            long readUnsignedExpGolombCodedInt5 = (long) parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            for (i3 = 0; ((long) i3) < readUnsignedExpGolombCodedInt5; i3++) {
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            }
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.skipBit();
        readBits = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
        readUnsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
        boolean readBit = parsableNalUnitBitArray.readBit();
        i3 = (2 - (readBit ? 1 : 0)) * readUnsignedExpGolombCodedInt;
        if (!readBit) {
            parsableNalUnitBitArray.skipBit();
        }
        parsableNalUnitBitArray.skipBit();
        readUnsignedExpGolombCodedInt = readBits * 16;
        readBits = i3 * 16;
        if (parsableNalUnitBitArray.readBit()) {
            int readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int readUnsignedExpGolombCodedInt7 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int readUnsignedExpGolombCodedInt8 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int readUnsignedExpGolombCodedInt9 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            if (i4 == 0) {
                i3 = 1;
                i4 = 2 - (readBit ? 1 : 0);
            } else {
                i3 = i4 == 3 ? 1 : 2;
                i4 = (2 - (readBit ? 1 : 0)) * (i4 == 1 ? 2 : 1);
            }
            i3 = readUnsignedExpGolombCodedInt - (i3 * (readUnsignedExpGolombCodedInt6 + readUnsignedExpGolombCodedInt7));
            readBits -= i4 * (readUnsignedExpGolombCodedInt8 + readUnsignedExpGolombCodedInt9);
        } else {
            i3 = readUnsignedExpGolombCodedInt;
        }
        float f2 = 1.0f;
        if (parsableNalUnitBitArray.readBit() && parsableNalUnitBitArray.readBit()) {
            readUnsignedExpGolombCodedInt = parsableNalUnitBitArray.readBits(8);
            if (readUnsignedExpGolombCodedInt == 255) {
                readUnsignedExpGolombCodedInt = parsableNalUnitBitArray.readBits(16);
                int readBits2 = parsableNalUnitBitArray.readBits(16);
                if (!(readUnsignedExpGolombCodedInt == 0 || readBits2 == 0)) {
                    f2 = ((float) readUnsignedExpGolombCodedInt) / ((float) readBits2);
                }
                f = f2;
            } else if (readUnsignedExpGolombCodedInt < ASPECT_RATIO_IDC_VALUES.length) {
                f = ASPECT_RATIO_IDC_VALUES[readUnsignedExpGolombCodedInt];
            } else {
                Log.w(TAG, "Unexpected aspect_ratio_idc value: " + readUnsignedExpGolombCodedInt);
            }
            return new SpsData(readUnsignedExpGolombCodedInt2, i3, readBits, f, z, readBit, readUnsignedExpGolombCodedInt3, readUnsignedExpGolombCodedInt4, i6, z3);
        }
        f = 1.0f;
        return new SpsData(readUnsignedExpGolombCodedInt2, i3, readBits, f, z, readBit, readUnsignedExpGolombCodedInt3, readUnsignedExpGolombCodedInt4, i6, z3);
    }

    private static void skipScalingList(ParsableNalUnitBitArray parsableNalUnitBitArray, int i) {
        int i2 = 8;
        int i3 = 8;
        for (int i4 = 0; i4 < i; i4++) {
            if (i2 != 0) {
                i2 = ((parsableNalUnitBitArray.readSignedExpGolombCodedInt() + i3) + 256) % 256;
            }
            if (i2 != 0) {
                i3 = i2;
            }
        }
    }

    public static int unescapeStream(byte[] bArr, int i) {
        int i2;
        int i3 = 0;
        synchronized (scratchEscapePositionsLock) {
            int findNextUnescapeIndex;
            int i4 = 0;
            int i5 = 0;
            while (i5 < i) {
                findNextUnescapeIndex = findNextUnescapeIndex(bArr, i5, i);
                if (findNextUnescapeIndex < i) {
                    if (scratchEscapePositions.length <= i4) {
                        scratchEscapePositions = Arrays.copyOf(scratchEscapePositions, scratchEscapePositions.length * 2);
                    }
                    i5 = i4 + 1;
                    scratchEscapePositions[i4] = findNextUnescapeIndex;
                    i4 = i5;
                    i5 = findNextUnescapeIndex + 3;
                } else {
                    i5 = findNextUnescapeIndex;
                }
            }
            i2 = i - i4;
            i5 = 0;
            findNextUnescapeIndex = 0;
            while (i3 < i4) {
                int i6 = scratchEscapePositions[i3] - findNextUnescapeIndex;
                System.arraycopy(bArr, findNextUnescapeIndex, bArr, i5, i6);
                i5 += i6;
                int i7 = i5 + 1;
                bArr[i5] = (byte) 0;
                i5 = i7 + 1;
                bArr[i7] = (byte) 0;
                findNextUnescapeIndex += i6 + 3;
                i3++;
            }
            System.arraycopy(bArr, findNextUnescapeIndex, bArr, i5, i2 - i5);
        }
        return i2;
    }
}
