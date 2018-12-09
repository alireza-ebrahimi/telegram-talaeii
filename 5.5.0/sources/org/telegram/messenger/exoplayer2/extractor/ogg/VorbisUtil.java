package org.telegram.messenger.exoplayer2.extractor.ogg;

import android.util.Log;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

final class VorbisUtil {
    private static final String TAG = "VorbisUtil";

    public static final class CodeBook {
        public final int dimensions;
        public final int entries;
        public final boolean isOrdered;
        public final long[] lengthMap;
        public final int lookupType;

        public CodeBook(int i, int i2, long[] jArr, int i3, boolean z) {
            this.dimensions = i;
            this.entries = i2;
            this.lengthMap = jArr;
            this.lookupType = i3;
            this.isOrdered = z;
        }
    }

    public static final class CommentHeader {
        public final String[] comments;
        public final int length;
        public final String vendor;

        public CommentHeader(String str, String[] strArr, int i) {
            this.vendor = str;
            this.comments = strArr;
            this.length = i;
        }
    }

    public static final class Mode {
        public final boolean blockFlag;
        public final int mapping;
        public final int transformType;
        public final int windowType;

        public Mode(boolean z, int i, int i2, int i3) {
            this.blockFlag = z;
            this.windowType = i;
            this.transformType = i2;
            this.mapping = i3;
        }
    }

    public static final class VorbisIdHeader {
        public final int bitrateMax;
        public final int bitrateMin;
        public final int bitrateNominal;
        public final int blockSize0;
        public final int blockSize1;
        public final int channels;
        public final byte[] data;
        public final boolean framingFlag;
        public final long sampleRate;
        public final long version;

        public VorbisIdHeader(long j, int i, long j2, int i2, int i3, int i4, int i5, int i6, boolean z, byte[] bArr) {
            this.version = j;
            this.channels = i;
            this.sampleRate = j2;
            this.bitrateMax = i2;
            this.bitrateNominal = i3;
            this.bitrateMin = i4;
            this.blockSize0 = i5;
            this.blockSize1 = i6;
            this.framingFlag = z;
            this.data = bArr;
        }

        public int getApproximateBitrate() {
            return this.bitrateNominal == 0 ? (this.bitrateMin + this.bitrateMax) / 2 : this.bitrateNominal;
        }
    }

    VorbisUtil() {
    }

    public static int iLog(int i) {
        int i2 = 0;
        while (i > 0) {
            i2++;
            i >>>= 1;
        }
        return i2;
    }

    private static long mapType1QuantValues(long j, long j2) {
        return (long) Math.floor(Math.pow((double) j, 1.0d / ((double) j2)));
    }

    private static CodeBook readBook(VorbisBitArray vorbisBitArray) {
        int i = 0;
        if (vorbisBitArray.readBits(24) != 5653314) {
            throw new ParserException("expected code book to start with [0x56, 0x43, 0x42] at " + vorbisBitArray.getPosition());
        }
        int i2;
        int readBits = vorbisBitArray.readBits(16);
        int readBits2 = vorbisBitArray.readBits(24);
        long[] jArr = new long[readBits2];
        boolean readBit = vorbisBitArray.readBit();
        if (readBit) {
            int readBits3 = vorbisBitArray.readBits(5) + 1;
            i2 = 0;
            while (i2 < jArr.length) {
                int readBits4 = vorbisBitArray.readBits(iLog(readBits2 - i2));
                int i3 = 0;
                while (i3 < readBits4 && i2 < jArr.length) {
                    jArr[i2] = (long) readBits3;
                    i3++;
                    i2++;
                }
                readBits3++;
            }
        } else {
            boolean readBit2 = vorbisBitArray.readBit();
            while (i < jArr.length) {
                if (!readBit2) {
                    jArr[i] = (long) (vorbisBitArray.readBits(5) + 1);
                } else if (vorbisBitArray.readBit()) {
                    jArr[i] = (long) (vorbisBitArray.readBits(5) + 1);
                } else {
                    jArr[i] = 0;
                }
                i++;
            }
        }
        i2 = vorbisBitArray.readBits(4);
        if (i2 > 2) {
            throw new ParserException("lookup type greater than 2 not decodable: " + i2);
        }
        if (i2 == 1 || i2 == 2) {
            vorbisBitArray.skipBits(32);
            vorbisBitArray.skipBits(32);
            i = vorbisBitArray.readBits(4) + 1;
            vorbisBitArray.skipBits(1);
            long mapType1QuantValues = i2 == 1 ? readBits != 0 ? mapType1QuantValues((long) readBits2, (long) readBits) : 0 : (long) (readBits2 * readBits);
            vorbisBitArray.skipBits((int) (mapType1QuantValues * ((long) i)));
        }
        return new CodeBook(readBits, readBits2, jArr, i2, readBit);
    }

    private static void readFloors(VorbisBitArray vorbisBitArray) {
        int readBits = vorbisBitArray.readBits(6) + 1;
        for (int i = 0; i < readBits; i++) {
            int readBits2 = vorbisBitArray.readBits(16);
            int readBits3;
            switch (readBits2) {
                case 0:
                    vorbisBitArray.skipBits(8);
                    vorbisBitArray.skipBits(16);
                    vorbisBitArray.skipBits(16);
                    vorbisBitArray.skipBits(6);
                    vorbisBitArray.skipBits(8);
                    readBits3 = vorbisBitArray.readBits(4) + 1;
                    for (readBits2 = 0; readBits2 < readBits3; readBits2++) {
                        vorbisBitArray.skipBits(8);
                    }
                    break;
                case 1:
                    int readBits4;
                    int readBits5 = vorbisBitArray.readBits(5);
                    readBits2 = -1;
                    int[] iArr = new int[readBits5];
                    for (readBits3 = 0; readBits3 < readBits5; readBits3++) {
                        iArr[readBits3] = vorbisBitArray.readBits(4);
                        if (iArr[readBits3] > readBits2) {
                            readBits2 = iArr[readBits3];
                        }
                    }
                    int[] iArr2 = new int[(readBits2 + 1)];
                    for (readBits2 = 0; readBits2 < iArr2.length; readBits2++) {
                        iArr2[readBits2] = vorbisBitArray.readBits(3) + 1;
                        readBits4 = vorbisBitArray.readBits(2);
                        if (readBits4 > 0) {
                            vorbisBitArray.skipBits(8);
                        }
                        for (readBits3 = 0; readBits3 < (1 << readBits4); readBits3++) {
                            vorbisBitArray.skipBits(8);
                        }
                    }
                    vorbisBitArray.skipBits(2);
                    int readBits6 = vorbisBitArray.readBits(4);
                    readBits2 = 0;
                    readBits4 = 0;
                    for (readBits3 = 0; readBits3 < readBits5; readBits3++) {
                        readBits4 += iArr2[iArr[readBits3]];
                        while (readBits2 < readBits4) {
                            vorbisBitArray.skipBits(readBits6);
                            readBits2++;
                        }
                    }
                    break;
                default:
                    throw new ParserException("floor type greater than 1 not decodable: " + readBits2);
            }
        }
    }

    private static void readMappings(int i, VorbisBitArray vorbisBitArray) {
        int readBits = vorbisBitArray.readBits(6) + 1;
        for (int i2 = 0; i2 < readBits; i2++) {
            int readBits2 = vorbisBitArray.readBits(16);
            switch (readBits2) {
                case 0:
                    int i3;
                    readBits2 = vorbisBitArray.readBit() ? vorbisBitArray.readBits(4) + 1 : 1;
                    if (vorbisBitArray.readBit()) {
                        int readBits3 = vorbisBitArray.readBits(8) + 1;
                        for (i3 = 0; i3 < readBits3; i3++) {
                            vorbisBitArray.skipBits(iLog(i - 1));
                            vorbisBitArray.skipBits(iLog(i - 1));
                        }
                    }
                    if (vorbisBitArray.readBits(2) == 0) {
                        if (readBits2 > 1) {
                            for (i3 = 0; i3 < i; i3++) {
                                vorbisBitArray.skipBits(4);
                            }
                        }
                        for (i3 = 0; i3 < readBits2; i3++) {
                            vorbisBitArray.skipBits(8);
                            vorbisBitArray.skipBits(8);
                            vorbisBitArray.skipBits(8);
                        }
                        break;
                    }
                    throw new ParserException("to reserved bits must be zero after mapping coupling steps");
                default:
                    Log.e(TAG, "mapping type other than 0 not supported: " + readBits2);
                    break;
            }
        }
    }

    private static Mode[] readModes(VorbisBitArray vorbisBitArray) {
        int readBits = vorbisBitArray.readBits(6) + 1;
        Mode[] modeArr = new Mode[readBits];
        for (int i = 0; i < readBits; i++) {
            modeArr[i] = new Mode(vorbisBitArray.readBit(), vorbisBitArray.readBits(16), vorbisBitArray.readBits(16), vorbisBitArray.readBits(8));
        }
        return modeArr;
    }

    private static void readResidues(VorbisBitArray vorbisBitArray) {
        int readBits = vorbisBitArray.readBits(6) + 1;
        for (int i = 0; i < readBits; i++) {
            if (vorbisBitArray.readBits(16) > 2) {
                throw new ParserException("residueType greater than 2 is not decodable");
            }
            int i2;
            vorbisBitArray.skipBits(24);
            vorbisBitArray.skipBits(24);
            vorbisBitArray.skipBits(24);
            int readBits2 = vorbisBitArray.readBits(6) + 1;
            vorbisBitArray.skipBits(8);
            int[] iArr = new int[readBits2];
            for (i2 = 0; i2 < readBits2; i2++) {
                iArr[i2] = ((vorbisBitArray.readBit() ? vorbisBitArray.readBits(5) : 0) * 8) + vorbisBitArray.readBits(3);
            }
            for (i2 = 0; i2 < readBits2; i2++) {
                for (int i3 = 0; i3 < 8; i3++) {
                    if ((iArr[i2] & (1 << i3)) != 0) {
                        vorbisBitArray.skipBits(8);
                    }
                }
            }
        }
    }

    public static CommentHeader readVorbisCommentHeader(ParsableByteArray parsableByteArray) {
        int i = 0;
        verifyVorbisHeaderCapturePattern(3, parsableByteArray, false);
        String readString = parsableByteArray.readString((int) parsableByteArray.readLittleEndianUnsignedInt());
        int length = readString.length() + 11;
        long readLittleEndianUnsignedInt = parsableByteArray.readLittleEndianUnsignedInt();
        String[] strArr = new String[((int) readLittleEndianUnsignedInt)];
        length += 4;
        while (((long) i) < readLittleEndianUnsignedInt) {
            length += 4;
            strArr[i] = parsableByteArray.readString((int) parsableByteArray.readLittleEndianUnsignedInt());
            length += strArr[i].length();
            i++;
        }
        if ((parsableByteArray.readUnsignedByte() & 1) != 0) {
            return new CommentHeader(readString, strArr, length + 1);
        }
        throw new ParserException("framing bit expected to be set");
    }

    public static VorbisIdHeader readVorbisIdentificationHeader(ParsableByteArray parsableByteArray) {
        verifyVorbisHeaderCapturePattern(1, parsableByteArray, false);
        long readLittleEndianUnsignedInt = parsableByteArray.readLittleEndianUnsignedInt();
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        long readLittleEndianUnsignedInt2 = parsableByteArray.readLittleEndianUnsignedInt();
        int readLittleEndianInt = parsableByteArray.readLittleEndianInt();
        int readLittleEndianInt2 = parsableByteArray.readLittleEndianInt();
        int readLittleEndianInt3 = parsableByteArray.readLittleEndianInt();
        int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
        return new VorbisIdHeader(readLittleEndianUnsignedInt, readUnsignedByte, readLittleEndianUnsignedInt2, readLittleEndianInt, readLittleEndianInt2, readLittleEndianInt3, (int) Math.pow(2.0d, (double) (readUnsignedByte2 & 15)), (int) Math.pow(2.0d, (double) ((readUnsignedByte2 & PsExtractor.VIDEO_STREAM_MASK) >> 4)), (parsableByteArray.readUnsignedByte() & 1) > 0, Arrays.copyOf(parsableByteArray.data, parsableByteArray.limit()));
    }

    public static Mode[] readVorbisModes(ParsableByteArray parsableByteArray, int i) {
        int i2;
        int i3 = 0;
        verifyVorbisHeaderCapturePattern(5, parsableByteArray, false);
        int readUnsignedByte = parsableByteArray.readUnsignedByte() + 1;
        VorbisBitArray vorbisBitArray = new VorbisBitArray(parsableByteArray.data);
        vorbisBitArray.skipBits(parsableByteArray.getPosition() * 8);
        for (i2 = 0; i2 < readUnsignedByte; i2++) {
            readBook(vorbisBitArray);
        }
        i2 = vorbisBitArray.readBits(6) + 1;
        while (i3 < i2) {
            if (vorbisBitArray.readBits(16) != 0) {
                throw new ParserException("placeholder of time domain transforms not zeroed out");
            }
            i3++;
        }
        readFloors(vorbisBitArray);
        readResidues(vorbisBitArray);
        readMappings(i, vorbisBitArray);
        Mode[] readModes = readModes(vorbisBitArray);
        if (vorbisBitArray.readBit()) {
            return readModes;
        }
        throw new ParserException("framing bit after modes not set as expected");
    }

    public static boolean verifyVorbisHeaderCapturePattern(int i, ParsableByteArray parsableByteArray, boolean z) {
        if (parsableByteArray.bytesLeft() < 7) {
            if (z) {
                return false;
            }
            throw new ParserException("too short header: " + parsableByteArray.bytesLeft());
        } else if (parsableByteArray.readUnsignedByte() != i) {
            if (z) {
                return false;
            }
            throw new ParserException("expected header type " + Integer.toHexString(i));
        } else if (parsableByteArray.readUnsignedByte() == 118 && parsableByteArray.readUnsignedByte() == 111 && parsableByteArray.readUnsignedByte() == 114 && parsableByteArray.readUnsignedByte() == 98 && parsableByteArray.readUnsignedByte() == 105 && parsableByteArray.readUnsignedByte() == 115) {
            return true;
        } else {
            if (z) {
                return false;
            }
            throw new ParserException("expected characters 'vorbis'");
        }
    }
}
