package org.telegram.messenger.exoplayer2.util;

import android.support.v4.media.TransportMediator;
import android.util.Log;
import java.nio.ByteBuffer;
import java.util.Arrays;

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

    public static int unescapeStream(byte[] data, int limit) {
        synchronized (scratchEscapePositionsLock) {
            int position = 0;
            int scratchEscapeCount = 0;
            while (position < limit) {
                try {
                    position = findNextUnescapeIndex(data, position, limit);
                    if (position < limit) {
                        if (scratchEscapePositions.length <= scratchEscapeCount) {
                            scratchEscapePositions = Arrays.copyOf(scratchEscapePositions, scratchEscapePositions.length * 2);
                        }
                        int scratchEscapeCount2 = scratchEscapeCount + 1;
                        try {
                            scratchEscapePositions[scratchEscapeCount] = position;
                            position += 3;
                            scratchEscapeCount = scratchEscapeCount2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    scratchEscapeCount2 = scratchEscapeCount;
                }
            }
            int unescapedLength = limit - scratchEscapeCount;
            int escapedPosition = 0;
            int unescapedPosition = 0;
            for (int i = 0; i < scratchEscapeCount; i++) {
                int copyLength = scratchEscapePositions[i] - escapedPosition;
                System.arraycopy(data, escapedPosition, data, unescapedPosition, copyLength);
                unescapedPosition += copyLength;
                int i2 = unescapedPosition + 1;
                data[unescapedPosition] = (byte) 0;
                unescapedPosition = i2 + 1;
                data[i2] = (byte) 0;
                escapedPosition += copyLength + 3;
            }
            System.arraycopy(data, escapedPosition, data, unescapedPosition, unescapedLength - unescapedPosition);
            return unescapedLength;
        }
    }

    public static void discardToSps(ByteBuffer data) {
        int length = data.position();
        int consecutiveZeros = 0;
        int offset = 0;
        while (offset + 1 < length) {
            int value = data.get(offset) & 255;
            if (consecutiveZeros == 3) {
                if (value == 1 && (data.get(offset + 1) & 31) == 7) {
                    ByteBuffer offsetData = data.duplicate();
                    offsetData.position(offset - 3);
                    offsetData.limit(length);
                    data.position(0);
                    data.put(offsetData);
                    return;
                }
            } else if (value == 0) {
                consecutiveZeros++;
            }
            if (value != 0) {
                consecutiveZeros = 0;
            }
            offset++;
        }
        data.clear();
    }

    public static boolean isNalUnitSei(String mimeType, byte nalUnitHeaderFirstByte) {
        return ("video/avc".equals(mimeType) && (nalUnitHeaderFirstByte & 31) == 6) || (MimeTypes.VIDEO_H265.equals(mimeType) && ((nalUnitHeaderFirstByte & TransportMediator.KEYCODE_MEDIA_PLAY) >> 1) == 39);
    }

    public static int getNalUnitType(byte[] data, int offset) {
        return data[offset + 3] & 31;
    }

    public static int getH265NalUnitType(byte[] data, int offset) {
        return (data[offset + 3] & TransportMediator.KEYCODE_MEDIA_PLAY) >> 1;
    }

    public static NalUnitUtil$SpsData parseSpsNalUnit(byte[] nalData, int nalOffset, int nalLimit) {
        int i;
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(nalData, nalOffset, nalLimit);
        parsableNalUnitBitArray.skipBits(8);
        int profileIdc = parsableNalUnitBitArray.readBits(8);
        parsableNalUnitBitArray.skipBits(16);
        int seqParameterSetId = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int chromaFormatIdc = 1;
        boolean separateColorPlaneFlag = false;
        if (profileIdc == 100 || profileIdc == 110 || profileIdc == 122 || profileIdc == 244 || profileIdc == 44 || profileIdc == 83 || profileIdc == 86 || profileIdc == 118 || profileIdc == 128 || profileIdc == 138) {
            chromaFormatIdc = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            if (chromaFormatIdc == 3) {
                separateColorPlaneFlag = parsableNalUnitBitArray.readBit();
            }
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.skipBit();
            if (parsableNalUnitBitArray.readBit()) {
                int limit = chromaFormatIdc != 3 ? 8 : 12;
                i = 0;
                while (i < limit) {
                    if (parsableNalUnitBitArray.readBit()) {
                        skipScalingList(parsableNalUnitBitArray, i < 6 ? 16 : 64);
                    }
                    i++;
                }
            }
        }
        int frameNumLength = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 4;
        int picOrderCntType = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int picOrderCntLsbLength = 0;
        boolean deltaPicOrderAlwaysZeroFlag = false;
        if (picOrderCntType == 0) {
            picOrderCntLsbLength = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 4;
        } else if (picOrderCntType == 1) {
            deltaPicOrderAlwaysZeroFlag = parsableNalUnitBitArray.readBit();
            parsableNalUnitBitArray.readSignedExpGolombCodedInt();
            parsableNalUnitBitArray.readSignedExpGolombCodedInt();
            long numRefFramesInPicOrderCntCycle = (long) parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            for (i = 0; ((long) i) < numRefFramesInPicOrderCntCycle; i++) {
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            }
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.skipBit();
        int picWidthInMbs = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
        int picHeightInMapUnits = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
        boolean frameMbsOnlyFlag = parsableNalUnitBitArray.readBit();
        int frameHeightInMbs = (2 - (frameMbsOnlyFlag ? 1 : 0)) * picHeightInMapUnits;
        if (!frameMbsOnlyFlag) {
            parsableNalUnitBitArray.skipBit();
        }
        parsableNalUnitBitArray.skipBit();
        int frameWidth = picWidthInMbs * 16;
        int frameHeight = frameHeightInMbs * 16;
        if (parsableNalUnitBitArray.readBit()) {
            int cropUnitX;
            int cropUnitY;
            int frameCropLeftOffset = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int frameCropRightOffset = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int frameCropTopOffset = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int frameCropBottomOffset = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            if (chromaFormatIdc == 0) {
                cropUnitX = 1;
                cropUnitY = 2 - (frameMbsOnlyFlag ? 1 : 0);
            } else {
                cropUnitX = chromaFormatIdc == 3 ? 1 : 2;
                cropUnitY = (chromaFormatIdc == 1 ? 2 : 1) * (2 - (frameMbsOnlyFlag ? 1 : 0));
            }
            frameWidth -= (frameCropLeftOffset + frameCropRightOffset) * cropUnitX;
            frameHeight -= (frameCropTopOffset + frameCropBottomOffset) * cropUnitY;
        }
        float pixelWidthHeightRatio = 1.0f;
        if (parsableNalUnitBitArray.readBit() && parsableNalUnitBitArray.readBit()) {
            int aspectRatioIdc = parsableNalUnitBitArray.readBits(8);
            if (aspectRatioIdc == 255) {
                int sarWidth = parsableNalUnitBitArray.readBits(16);
                int sarHeight = parsableNalUnitBitArray.readBits(16);
                if (!(sarWidth == 0 || sarHeight == 0)) {
                    pixelWidthHeightRatio = ((float) sarWidth) / ((float) sarHeight);
                }
            } else if (aspectRatioIdc < ASPECT_RATIO_IDC_VALUES.length) {
                pixelWidthHeightRatio = ASPECT_RATIO_IDC_VALUES[aspectRatioIdc];
            } else {
                Log.w(TAG, "Unexpected aspect_ratio_idc value: " + aspectRatioIdc);
            }
        }
        return new NalUnitUtil$SpsData(seqParameterSetId, frameWidth, frameHeight, pixelWidthHeightRatio, separateColorPlaneFlag, frameMbsOnlyFlag, frameNumLength, picOrderCntType, picOrderCntLsbLength, deltaPicOrderAlwaysZeroFlag);
    }

    public static NalUnitUtil$PpsData parsePpsNalUnit(byte[] nalData, int nalOffset, int nalLimit) {
        ParsableNalUnitBitArray data = new ParsableNalUnitBitArray(nalData, nalOffset, nalLimit);
        data.skipBits(8);
        int picParameterSetId = data.readUnsignedExpGolombCodedInt();
        int seqParameterSetId = data.readUnsignedExpGolombCodedInt();
        data.skipBit();
        return new NalUnitUtil$PpsData(picParameterSetId, seqParameterSetId, data.readBit());
    }

    public static int findNalUnit(byte[] data, int startOffset, int endOffset, boolean[] prefixFlags) {
        boolean z;
        boolean z2 = true;
        int length = endOffset - startOffset;
        if (length >= 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        if (length == 0) {
            return endOffset;
        }
        if (prefixFlags != null) {
            if (prefixFlags[0]) {
                clearPrefixFlags(prefixFlags);
                return startOffset - 3;
            } else if (length > 1 && prefixFlags[1] && data[startOffset] == (byte) 1) {
                clearPrefixFlags(prefixFlags);
                return startOffset - 2;
            } else if (length > 2 && prefixFlags[2] && data[startOffset] == (byte) 0 && data[startOffset + 1] == (byte) 1) {
                clearPrefixFlags(prefixFlags);
                return startOffset - 1;
            }
        }
        int limit = endOffset - 1;
        int i = startOffset + 2;
        while (i < limit) {
            if ((data[i] & 254) == 0) {
                if (data[i - 2] == (byte) 0 && data[i - 1] == (byte) 0 && data[i] == (byte) 1) {
                    if (prefixFlags != null) {
                        clearPrefixFlags(prefixFlags);
                    }
                    return i - 2;
                }
                i -= 2;
            }
            i += 3;
        }
        if (prefixFlags == null) {
            return endOffset;
        }
        z = length > 2 ? data[endOffset + -3] == (byte) 0 && data[endOffset - 2] == (byte) 0 && data[endOffset - 1] == (byte) 1 : length == 2 ? prefixFlags[2] && data[endOffset - 2] == (byte) 0 && data[endOffset - 1] == (byte) 1 : prefixFlags[1] && data[endOffset - 1] == (byte) 1;
        prefixFlags[0] = z;
        z = length > 1 ? data[endOffset + -2] == (byte) 0 && data[endOffset - 1] == (byte) 0 : prefixFlags[2] && data[endOffset - 1] == (byte) 0;
        prefixFlags[1] = z;
        if (data[endOffset - 1] != (byte) 0) {
            z2 = false;
        }
        prefixFlags[2] = z2;
        return endOffset;
    }

    public static void clearPrefixFlags(boolean[] prefixFlags) {
        prefixFlags[0] = false;
        prefixFlags[1] = false;
        prefixFlags[2] = false;
    }

    private static int findNextUnescapeIndex(byte[] bytes, int offset, int limit) {
        int i = offset;
        while (i < limit - 2) {
            if (bytes[i] == (byte) 0 && bytes[i + 1] == (byte) 0 && bytes[i + 2] == (byte) 3) {
                return i;
            }
            i++;
        }
        return limit;
    }

    private static void skipScalingList(ParsableNalUnitBitArray bitArray, int size) {
        int lastScale = 8;
        int nextScale = 8;
        for (int i = 0; i < size; i++) {
            if (nextScale != 0) {
                nextScale = ((lastScale + bitArray.readSignedExpGolombCodedInt()) + 256) % 256;
            }
            if (nextScale != 0) {
                lastScale = nextScale;
            }
        }
    }

    private NalUnitUtil() {
    }
}
