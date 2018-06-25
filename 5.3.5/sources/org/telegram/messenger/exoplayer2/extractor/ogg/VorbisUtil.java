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

        public CodeBook(int dimensions, int entries, long[] lengthMap, int lookupType, boolean isOrdered) {
            this.dimensions = dimensions;
            this.entries = entries;
            this.lengthMap = lengthMap;
            this.lookupType = lookupType;
            this.isOrdered = isOrdered;
        }
    }

    public static final class CommentHeader {
        public final String[] comments;
        public final int length;
        public final String vendor;

        public CommentHeader(String vendor, String[] comments, int length) {
            this.vendor = vendor;
            this.comments = comments;
            this.length = length;
        }
    }

    public static final class Mode {
        public final boolean blockFlag;
        public final int mapping;
        public final int transformType;
        public final int windowType;

        public Mode(boolean blockFlag, int windowType, int transformType, int mapping) {
            this.blockFlag = blockFlag;
            this.windowType = windowType;
            this.transformType = transformType;
            this.mapping = mapping;
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

        public VorbisIdHeader(long version, int channels, long sampleRate, int bitrateMax, int bitrateNominal, int bitrateMin, int blockSize0, int blockSize1, boolean framingFlag, byte[] data) {
            this.version = version;
            this.channels = channels;
            this.sampleRate = sampleRate;
            this.bitrateMax = bitrateMax;
            this.bitrateNominal = bitrateNominal;
            this.bitrateMin = bitrateMin;
            this.blockSize0 = blockSize0;
            this.blockSize1 = blockSize1;
            this.framingFlag = framingFlag;
            this.data = data;
        }

        public int getApproximateBitrate() {
            return this.bitrateNominal == 0 ? (this.bitrateMin + this.bitrateMax) / 2 : this.bitrateNominal;
        }
    }

    VorbisUtil() {
    }

    public static int iLog(int x) {
        int val = 0;
        while (x > 0) {
            val++;
            x >>>= 1;
        }
        return val;
    }

    public static VorbisIdHeader readVorbisIdentificationHeader(ParsableByteArray headerData) throws ParserException {
        verifyVorbisHeaderCapturePattern(1, headerData, false);
        long version = headerData.readLittleEndianUnsignedInt();
        int channels = headerData.readUnsignedByte();
        long sampleRate = headerData.readLittleEndianUnsignedInt();
        int bitrateMax = headerData.readLittleEndianInt();
        int bitrateNominal = headerData.readLittleEndianInt();
        int bitrateMin = headerData.readLittleEndianInt();
        int blockSize = headerData.readUnsignedByte();
        return new VorbisIdHeader(version, channels, sampleRate, bitrateMax, bitrateNominal, bitrateMin, (int) Math.pow(2.0d, (double) (blockSize & 15)), (int) Math.pow(2.0d, (double) ((blockSize & PsExtractor.VIDEO_STREAM_MASK) >> 4)), (headerData.readUnsignedByte() & 1) > 0, Arrays.copyOf(headerData.data, headerData.limit()));
    }

    public static CommentHeader readVorbisCommentHeader(ParsableByteArray headerData) throws ParserException {
        verifyVorbisHeaderCapturePattern(3, headerData, false);
        int length = 7 + 4;
        String vendor = headerData.readString((int) headerData.readLittleEndianUnsignedInt());
        length = vendor.length() + 11;
        long commentListLen = headerData.readLittleEndianUnsignedInt();
        String[] comments = new String[((int) commentListLen)];
        length += 4;
        for (int i = 0; ((long) i) < commentListLen; i++) {
            length += 4;
            comments[i] = headerData.readString((int) headerData.readLittleEndianUnsignedInt());
            length += comments[i].length();
        }
        if ((headerData.readUnsignedByte() & 1) != 0) {
            return new CommentHeader(vendor, comments, length + 1);
        }
        throw new ParserException("framing bit expected to be set");
    }

    public static boolean verifyVorbisHeaderCapturePattern(int headerType, ParsableByteArray header, boolean quiet) throws ParserException {
        if (header.bytesLeft() < 7) {
            if (quiet) {
                return false;
            }
            throw new ParserException("too short header: " + header.bytesLeft());
        } else if (header.readUnsignedByte() != headerType) {
            if (quiet) {
                return false;
            }
            throw new ParserException("expected header type " + Integer.toHexString(headerType));
        } else if (header.readUnsignedByte() == 118 && header.readUnsignedByte() == 111 && header.readUnsignedByte() == 114 && header.readUnsignedByte() == 98 && header.readUnsignedByte() == 105 && header.readUnsignedByte() == 115) {
            return true;
        } else {
            if (quiet) {
                return false;
            }
            throw new ParserException("expected characters 'vorbis'");
        }
    }

    public static Mode[] readVorbisModes(ParsableByteArray headerData, int channels) throws ParserException {
        int i;
        verifyVorbisHeaderCapturePattern(5, headerData, false);
        int numberOfBooks = headerData.readUnsignedByte() + 1;
        VorbisBitArray bitArray = new VorbisBitArray(headerData.data);
        bitArray.skipBits(headerData.getPosition() * 8);
        for (i = 0; i < numberOfBooks; i++) {
            readBook(bitArray);
        }
        int timeCount = bitArray.readBits(6) + 1;
        for (i = 0; i < timeCount; i++) {
            if (bitArray.readBits(16) != 0) {
                throw new ParserException("placeholder of time domain transforms not zeroed out");
            }
        }
        readFloors(bitArray);
        readResidues(bitArray);
        readMappings(channels, bitArray);
        Mode[] modes = readModes(bitArray);
        if (bitArray.readBit()) {
            return modes;
        }
        throw new ParserException("framing bit after modes not set as expected");
    }

    private static Mode[] readModes(VorbisBitArray bitArray) {
        int modeCount = bitArray.readBits(6) + 1;
        Mode[] modes = new Mode[modeCount];
        for (int i = 0; i < modeCount; i++) {
            modes[i] = new Mode(bitArray.readBit(), bitArray.readBits(16), bitArray.readBits(16), bitArray.readBits(8));
        }
        return modes;
    }

    private static void readMappings(int channels, VorbisBitArray bitArray) throws ParserException {
        int mappingsCount = bitArray.readBits(6) + 1;
        for (int i = 0; i < mappingsCount; i++) {
            int mappingType = bitArray.readBits(16);
            switch (mappingType) {
                case 0:
                    int submaps;
                    int j;
                    if (bitArray.readBit()) {
                        submaps = bitArray.readBits(4) + 1;
                    } else {
                        submaps = 1;
                    }
                    if (bitArray.readBit()) {
                        int couplingSteps = bitArray.readBits(8) + 1;
                        for (j = 0; j < couplingSteps; j++) {
                            bitArray.skipBits(iLog(channels - 1));
                            bitArray.skipBits(iLog(channels - 1));
                        }
                    }
                    if (bitArray.readBits(2) == 0) {
                        if (submaps > 1) {
                            for (j = 0; j < channels; j++) {
                                bitArray.skipBits(4);
                            }
                        }
                        for (j = 0; j < submaps; j++) {
                            bitArray.skipBits(8);
                            bitArray.skipBits(8);
                            bitArray.skipBits(8);
                        }
                        break;
                    }
                    throw new ParserException("to reserved bits must be zero after mapping coupling steps");
                default:
                    Log.e(TAG, "mapping type other than 0 not supported: " + mappingType);
                    break;
            }
        }
    }

    private static void readResidues(VorbisBitArray bitArray) throws ParserException {
        int residueCount = bitArray.readBits(6) + 1;
        for (int i = 0; i < residueCount; i++) {
            if (bitArray.readBits(16) > 2) {
                throw new ParserException("residueType greater than 2 is not decodable");
            }
            int j;
            bitArray.skipBits(24);
            bitArray.skipBits(24);
            bitArray.skipBits(24);
            int classifications = bitArray.readBits(6) + 1;
            bitArray.skipBits(8);
            int[] cascade = new int[classifications];
            for (j = 0; j < classifications; j++) {
                int highBits = 0;
                int lowBits = bitArray.readBits(3);
                if (bitArray.readBit()) {
                    highBits = bitArray.readBits(5);
                }
                cascade[j] = (highBits * 8) + lowBits;
            }
            for (j = 0; j < classifications; j++) {
                for (int k = 0; k < 8; k++) {
                    if ((cascade[j] & (1 << k)) != 0) {
                        bitArray.skipBits(8);
                    }
                }
            }
        }
    }

    private static void readFloors(VorbisBitArray bitArray) throws ParserException {
        int floorCount = bitArray.readBits(6) + 1;
        for (int i = 0; i < floorCount; i++) {
            int floorType = bitArray.readBits(16);
            int j;
            switch (floorType) {
                case 0:
                    bitArray.skipBits(8);
                    bitArray.skipBits(16);
                    bitArray.skipBits(16);
                    bitArray.skipBits(6);
                    bitArray.skipBits(8);
                    int floorNumberOfBooks = bitArray.readBits(4) + 1;
                    for (j = 0; j < floorNumberOfBooks; j++) {
                        bitArray.skipBits(8);
                    }
                    break;
                case 1:
                    int k;
                    int partitions = bitArray.readBits(5);
                    int maximumClass = -1;
                    int[] partitionClassList = new int[partitions];
                    for (j = 0; j < partitions; j++) {
                        partitionClassList[j] = bitArray.readBits(4);
                        if (partitionClassList[j] > maximumClass) {
                            maximumClass = partitionClassList[j];
                        }
                    }
                    int[] classDimensions = new int[(maximumClass + 1)];
                    for (j = 0; j < classDimensions.length; j++) {
                        classDimensions[j] = bitArray.readBits(3) + 1;
                        int classSubclasses = bitArray.readBits(2);
                        if (classSubclasses > 0) {
                            bitArray.skipBits(8);
                        }
                        for (k = 0; k < (1 << classSubclasses); k++) {
                            bitArray.skipBits(8);
                        }
                    }
                    bitArray.skipBits(2);
                    int rangeBits = bitArray.readBits(4);
                    int count = 0;
                    k = 0;
                    for (j = 0; j < partitions; j++) {
                        count += classDimensions[partitionClassList[j]];
                        while (k < count) {
                            bitArray.skipBits(rangeBits);
                            k++;
                        }
                    }
                    break;
                default:
                    throw new ParserException("floor type greater than 1 not decodable: " + floorType);
            }
        }
    }

    private static CodeBook readBook(VorbisBitArray bitArray) throws ParserException {
        if (bitArray.readBits(24) != 5653314) {
            throw new ParserException("expected code book to start with [0x56, 0x43, 0x42] at " + bitArray.getPosition());
        }
        int dimensions = bitArray.readBits(16);
        int entries = bitArray.readBits(24);
        long[] lengthMap = new long[entries];
        boolean isOrdered = bitArray.readBit();
        int i;
        if (isOrdered) {
            int length = bitArray.readBits(5) + 1;
            i = 0;
            while (i < lengthMap.length) {
                int num = bitArray.readBits(iLog(entries - i));
                for (int j = 0; j < num && i < lengthMap.length; j++) {
                    lengthMap[i] = (long) length;
                    i++;
                }
                length++;
            }
        } else {
            boolean isSparse = bitArray.readBit();
            for (i = 0; i < lengthMap.length; i++) {
                if (!isSparse) {
                    lengthMap[i] = (long) (bitArray.readBits(5) + 1);
                } else if (bitArray.readBit()) {
                    lengthMap[i] = (long) (bitArray.readBits(5) + 1);
                } else {
                    lengthMap[i] = 0;
                }
            }
        }
        int lookupType = bitArray.readBits(4);
        if (lookupType > 2) {
            throw new ParserException("lookup type greater than 2 not decodable: " + lookupType);
        }
        if (lookupType == 1 || lookupType == 2) {
            long lookupValuesCount;
            bitArray.skipBits(32);
            bitArray.skipBits(32);
            int valueBits = bitArray.readBits(4) + 1;
            bitArray.skipBits(1);
            if (lookupType != 1) {
                lookupValuesCount = (long) (entries * dimensions);
            } else if (dimensions != 0) {
                lookupValuesCount = mapType1QuantValues((long) entries, (long) dimensions);
            } else {
                lookupValuesCount = 0;
            }
            bitArray.skipBits((int) (((long) valueBits) * lookupValuesCount));
        }
        return new CodeBook(dimensions, entries, lengthMap, lookupType, isOrdered);
    }

    private static long mapType1QuantValues(long entries, long dimension) {
        return (long) Math.floor(Math.pow((double) entries, 1.0d / ((double) dimension)));
    }
}
