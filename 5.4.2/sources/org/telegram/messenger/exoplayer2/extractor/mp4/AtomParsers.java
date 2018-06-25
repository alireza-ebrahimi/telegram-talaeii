package org.telegram.messenger.exoplayer2.extractor.mp4;

import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.audio.Ac3Util;
import org.telegram.messenger.exoplayer2.drm.DrmInitData;
import org.telegram.messenger.exoplayer2.extractor.GaplessInfoHolder;
import org.telegram.messenger.exoplayer2.extractor.mp4.FixedSampleSizeRechunker.Results;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.metadata.Metadata;
import org.telegram.messenger.exoplayer2.metadata.Metadata.Entry;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.CodecSpecificDataUtil;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.Util;
import org.telegram.messenger.exoplayer2.video.AvcConfig;
import org.telegram.messenger.exoplayer2.video.HevcConfig;

final class AtomParsers {
    private static final String TAG = "AtomParsers";
    private static final int TYPE_cenc = Util.getIntegerCodeForString(C3446C.CENC_TYPE_cenc);
    private static final int TYPE_clcp = Util.getIntegerCodeForString("clcp");
    private static final int TYPE_meta = Util.getIntegerCodeForString("meta");
    private static final int TYPE_sbtl = Util.getIntegerCodeForString("sbtl");
    private static final int TYPE_soun = Util.getIntegerCodeForString("soun");
    private static final int TYPE_subt = Util.getIntegerCodeForString("subt");
    private static final int TYPE_text = Util.getIntegerCodeForString(MimeTypes.BASE_TYPE_TEXT);
    private static final int TYPE_vide = Util.getIntegerCodeForString("vide");

    private static final class ChunkIterator {
        private final ParsableByteArray chunkOffsets;
        private final boolean chunkOffsetsAreLongs;
        public int index;
        public final int length;
        private int nextSamplesPerChunkChangeIndex;
        public int numSamples;
        public long offset;
        private int remainingSamplesPerChunkChanges;
        private final ParsableByteArray stsc;

        public ChunkIterator(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, boolean z) {
            boolean z2 = true;
            this.stsc = parsableByteArray;
            this.chunkOffsets = parsableByteArray2;
            this.chunkOffsetsAreLongs = z;
            parsableByteArray2.setPosition(12);
            this.length = parsableByteArray2.readUnsignedIntToInt();
            parsableByteArray.setPosition(12);
            this.remainingSamplesPerChunkChanges = parsableByteArray.readUnsignedIntToInt();
            if (parsableByteArray.readInt() != 1) {
                z2 = false;
            }
            Assertions.checkState(z2, "first_chunk must be 1");
            this.index = -1;
        }

        public boolean moveNext() {
            int i = this.index + 1;
            this.index = i;
            if (i == this.length) {
                return false;
            }
            this.offset = this.chunkOffsetsAreLongs ? this.chunkOffsets.readUnsignedLongToLong() : this.chunkOffsets.readUnsignedInt();
            if (this.index == this.nextSamplesPerChunkChangeIndex) {
                this.numSamples = this.stsc.readUnsignedIntToInt();
                this.stsc.skipBytes(4);
                i = this.remainingSamplesPerChunkChanges - 1;
                this.remainingSamplesPerChunkChanges = i;
                this.nextSamplesPerChunkChangeIndex = i > 0 ? this.stsc.readUnsignedIntToInt() - 1 : -1;
            }
            return true;
        }
    }

    private interface SampleSizeBox {
        int getSampleCount();

        boolean isFixedSampleSize();

        int readNextSampleSize();
    }

    private static final class StsdData {
        public static final int STSD_HEADER_SIZE = 8;
        public Format format;
        public int nalUnitLengthFieldLength;
        public int requiredSampleTransformation = 0;
        public final TrackEncryptionBox[] trackEncryptionBoxes;

        public StsdData(int i) {
            this.trackEncryptionBoxes = new TrackEncryptionBox[i];
        }
    }

    static final class StszSampleSizeBox implements SampleSizeBox {
        private final ParsableByteArray data;
        private final int fixedSampleSize = this.data.readUnsignedIntToInt();
        private final int sampleCount = this.data.readUnsignedIntToInt();

        public StszSampleSizeBox(LeafAtom leafAtom) {
            this.data = leafAtom.data;
            this.data.setPosition(12);
        }

        public int getSampleCount() {
            return this.sampleCount;
        }

        public boolean isFixedSampleSize() {
            return this.fixedSampleSize != 0;
        }

        public int readNextSampleSize() {
            return this.fixedSampleSize == 0 ? this.data.readUnsignedIntToInt() : this.fixedSampleSize;
        }
    }

    static final class Stz2SampleSizeBox implements SampleSizeBox {
        private int currentByte;
        private final ParsableByteArray data;
        private final int fieldSize = (this.data.readUnsignedIntToInt() & 255);
        private final int sampleCount = this.data.readUnsignedIntToInt();
        private int sampleIndex;

        public Stz2SampleSizeBox(LeafAtom leafAtom) {
            this.data = leafAtom.data;
            this.data.setPosition(12);
        }

        public int getSampleCount() {
            return this.sampleCount;
        }

        public boolean isFixedSampleSize() {
            return false;
        }

        public int readNextSampleSize() {
            if (this.fieldSize == 8) {
                return this.data.readUnsignedByte();
            }
            if (this.fieldSize == 16) {
                return this.data.readUnsignedShort();
            }
            int i = this.sampleIndex;
            this.sampleIndex = i + 1;
            if (i % 2 != 0) {
                return this.currentByte & 15;
            }
            this.currentByte = this.data.readUnsignedByte();
            return (this.currentByte & PsExtractor.VIDEO_STREAM_MASK) >> 4;
        }
    }

    private static final class TkhdData {
        private final long duration;
        private final int id;
        private final int rotationDegrees;

        public TkhdData(int i, long j, int i2) {
            this.id = i;
            this.duration = j;
            this.rotationDegrees = i2;
        }
    }

    private AtomParsers() {
    }

    private static int findEsdsPosition(ParsableByteArray parsableByteArray, int i, int i2) {
        int position = parsableByteArray.getPosition();
        while (position - i < i2) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
            if (parsableByteArray.readInt() == Atom.TYPE_esds) {
                return position;
            }
            position += readInt;
        }
        return -1;
    }

    private static void parseAudioSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, boolean z, DrmInitData drmInitData, StsdData stsdData, int i5) {
        int i6;
        int readUnsignedShort;
        DrmInitData drmInitData2;
        parsableByteArray.setPosition((i2 + 8) + 8);
        if (z) {
            int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
            parsableByteArray.skipBytes(6);
            i6 = readUnsignedShort2;
        } else {
            parsableByteArray.skipBytes(8);
            i6 = 0;
        }
        if (i6 == 0 || i6 == 1) {
            readUnsignedShort = parsableByteArray.readUnsignedShort();
            parsableByteArray.skipBytes(6);
            readUnsignedShort2 = parsableByteArray.readUnsignedFixedPoint1616();
            if (i6 == 1) {
                parsableByteArray.skipBytes(16);
                i6 = readUnsignedShort;
                readUnsignedShort = readUnsignedShort2;
            } else {
                i6 = readUnsignedShort;
                readUnsignedShort = readUnsignedShort2;
            }
        } else if (i6 == 2) {
            parsableByteArray.skipBytes(16);
            readUnsignedShort2 = (int) Math.round(parsableByteArray.readDouble());
            readUnsignedShort = parsableByteArray.readUnsignedIntToInt();
            parsableByteArray.skipBytes(20);
            i6 = readUnsignedShort;
            readUnsignedShort = readUnsignedShort2;
        } else {
            return;
        }
        int position = parsableByteArray.getPosition();
        if (i == Atom.TYPE_enca) {
            DrmInitData copyWithSchemeType;
            Pair parseSampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray, i2, i3);
            if (parseSampleEntryEncryptionData != null) {
                i = ((Integer) parseSampleEntryEncryptionData.first).intValue();
                copyWithSchemeType = drmInitData == null ? null : drmInitData.copyWithSchemeType(((TrackEncryptionBox) parseSampleEntryEncryptionData.second).schemeType);
                stsdData.trackEncryptionBoxes[i5] = (TrackEncryptionBox) parseSampleEntryEncryptionData.second;
            } else {
                copyWithSchemeType = drmInitData;
            }
            parsableByteArray.setPosition(position);
            drmInitData2 = copyWithSchemeType;
        } else {
            drmInitData2 = drmInitData;
        }
        String str2 = null;
        if (i == Atom.TYPE_ac_3) {
            str2 = MimeTypes.AUDIO_AC3;
        } else if (i == Atom.TYPE_ec_3) {
            str2 = MimeTypes.AUDIO_E_AC3;
        } else if (i == Atom.TYPE_dtsc) {
            str2 = MimeTypes.AUDIO_DTS;
        } else if (i == Atom.TYPE_dtsh || i == Atom.TYPE_dtsl) {
            str2 = MimeTypes.AUDIO_DTS_HD;
        } else if (i == Atom.TYPE_dtse) {
            str2 = MimeTypes.AUDIO_DTS_EXPRESS;
        } else if (i == Atom.TYPE_samr) {
            str2 = MimeTypes.AUDIO_AMR_NB;
        } else if (i == Atom.TYPE_sawb) {
            str2 = MimeTypes.AUDIO_AMR_WB;
        } else if (i == Atom.TYPE_lpcm || i == Atom.TYPE_sowt) {
            str2 = MimeTypes.AUDIO_RAW;
        } else if (i == Atom.TYPE__mp3) {
            str2 = MimeTypes.AUDIO_MPEG;
        } else if (i == Atom.TYPE_alac) {
            str2 = MimeTypes.AUDIO_ALAC;
        }
        Object obj = null;
        int i7 = readUnsignedShort;
        int i8 = i6;
        String str3 = str2;
        while (position - i2 < i3) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
            readUnsignedShort2 = parsableByteArray.readInt();
            if (readUnsignedShort2 == Atom.TYPE_esds || (z && readUnsignedShort2 == Atom.TYPE_wave)) {
                Object obj2;
                readUnsignedShort2 = readUnsignedShort2 == Atom.TYPE_esds ? position : findEsdsPosition(parsableByteArray, position, readInt);
                if (readUnsignedShort2 != -1) {
                    Pair parseEsdsFromParent = parseEsdsFromParent(parsableByteArray, readUnsignedShort2);
                    str3 = (String) parseEsdsFromParent.first;
                    obj2 = (byte[]) parseEsdsFromParent.second;
                    if (MimeTypes.AUDIO_AAC.equals(str3)) {
                        Pair parseAacAudioSpecificConfig = CodecSpecificDataUtil.parseAacAudioSpecificConfig(obj2);
                        i7 = ((Integer) parseAacAudioSpecificConfig.first).intValue();
                        i8 = ((Integer) parseAacAudioSpecificConfig.second).intValue();
                    }
                } else {
                    obj2 = obj;
                }
                obj = obj2;
            } else if (readUnsignedShort2 == Atom.TYPE_dac3) {
                parsableByteArray.setPosition(position + 8);
                stsdData.format = Ac3Util.parseAc3AnnexFFormat(parsableByteArray, Integer.toString(i4), str, drmInitData2);
            } else if (readUnsignedShort2 == Atom.TYPE_dec3) {
                parsableByteArray.setPosition(position + 8);
                stsdData.format = Ac3Util.parseEAc3AnnexFFormat(parsableByteArray, Integer.toString(i4), str, drmInitData2);
            } else if (readUnsignedShort2 == Atom.TYPE_ddts) {
                stsdData.format = Format.createAudioSampleFormat(Integer.toString(i4), str3, null, -1, -1, i8, i7, null, drmInitData2, 0, str);
            } else if (readUnsignedShort2 == Atom.TYPE_alac) {
                obj = new byte[readInt];
                parsableByteArray.setPosition(position);
                parsableByteArray.readBytes(obj, 0, readInt);
            }
            position += readInt;
        }
        if (stsdData.format == null && str3 != null) {
            stsdData.format = Format.createAudioSampleFormat(Integer.toString(i4), str3, null, -1, -1, i8, i7, MimeTypes.AUDIO_RAW.equals(str3) ? 2 : -1, obj == null ? null : Collections.singletonList(obj), drmInitData2, 0, str);
        }
    }

    static Pair<Integer, TrackEncryptionBox> parseCommonEncryptionSinfFromParent(ParsableByteArray parsableByteArray, int i, int i2) {
        boolean z = true;
        int i3 = i + 8;
        Object obj = null;
        String str = null;
        int i4 = 0;
        int i5 = -1;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == Atom.TYPE_frma) {
                obj = Integer.valueOf(parsableByteArray.readInt());
            } else if (readInt2 == Atom.TYPE_schm) {
                parsableByteArray.skipBytes(4);
                str = parsableByteArray.readString(4);
            } else if (readInt2 == Atom.TYPE_schi) {
                i4 = readInt;
                i5 = i3;
            }
            i3 += readInt;
        }
        if (!C3446C.CENC_TYPE_cenc.equals(str) && !C3446C.CENC_TYPE_cbc1.equals(str) && !C3446C.CENC_TYPE_cens.equals(str) && !C3446C.CENC_TYPE_cbcs.equals(str)) {
            return null;
        }
        Assertions.checkArgument(obj != null, "frma atom is mandatory");
        Assertions.checkArgument(i5 != -1, "schi atom is mandatory");
        TrackEncryptionBox parseSchiFromParent = parseSchiFromParent(parsableByteArray, i5, i4, str);
        if (parseSchiFromParent == null) {
            z = false;
        }
        Assertions.checkArgument(z, "tenc atom is mandatory");
        return Pair.create(obj, parseSchiFromParent);
    }

    private static Pair<long[], long[]> parseEdts(ContainerAtom containerAtom) {
        if (containerAtom != null) {
            LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_elst);
            if (leafAtomOfType != null) {
                ParsableByteArray parsableByteArray = leafAtomOfType.data;
                parsableByteArray.setPosition(8);
                int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
                int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
                Object obj = new long[readUnsignedIntToInt];
                Object obj2 = new long[readUnsignedIntToInt];
                for (int i = 0; i < readUnsignedIntToInt; i++) {
                    obj[i] = parseFullAtomVersion == 1 ? parsableByteArray.readUnsignedLongToLong() : parsableByteArray.readUnsignedInt();
                    obj2[i] = parseFullAtomVersion == 1 ? parsableByteArray.readLong() : (long) parsableByteArray.readInt();
                    if (parsableByteArray.readShort() != (short) 1) {
                        throw new IllegalArgumentException("Unsupported media rate.");
                    }
                    parsableByteArray.skipBytes(2);
                }
                return Pair.create(obj, obj2);
            }
        }
        return Pair.create(null, null);
    }

    private static Pair<String, byte[]> parseEsdsFromParent(ParsableByteArray parsableByteArray, int i) {
        Object obj = null;
        parsableByteArray.setPosition((i + 8) + 4);
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        parsableByteArray.skipBytes(2);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if ((readUnsignedByte & 128) != 0) {
            parsableByteArray.skipBytes(2);
        }
        if ((readUnsignedByte & 64) != 0) {
            parsableByteArray.skipBytes(parsableByteArray.readUnsignedShort());
        }
        if ((readUnsignedByte & 32) != 0) {
            parsableByteArray.skipBytes(2);
        }
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        switch (parsableByteArray.readUnsignedByte()) {
            case 32:
                obj = MimeTypes.VIDEO_MP4V;
                break;
            case 33:
                obj = "video/avc";
                break;
            case 35:
                obj = MimeTypes.VIDEO_H265;
                break;
            case 64:
            case 102:
            case 103:
            case 104:
                obj = MimeTypes.AUDIO_AAC;
                break;
            case 96:
            case 97:
                obj = MimeTypes.VIDEO_MPEG2;
                break;
            case 107:
                return Pair.create(MimeTypes.AUDIO_MPEG, null);
            case 165:
                obj = MimeTypes.AUDIO_AC3;
                break;
            case 166:
                obj = MimeTypes.AUDIO_E_AC3;
                break;
            case 169:
            case 172:
                return Pair.create(MimeTypes.AUDIO_DTS, null);
            case 170:
            case 171:
                return Pair.create(MimeTypes.AUDIO_DTS_HD, null);
        }
        parsableByteArray.skipBytes(12);
        parsableByteArray.skipBytes(1);
        readUnsignedByte = parseExpandableClassSize(parsableByteArray);
        Object obj2 = new byte[readUnsignedByte];
        parsableByteArray.readBytes(obj2, 0, readUnsignedByte);
        return Pair.create(obj, obj2);
    }

    private static int parseExpandableClassSize(ParsableByteArray parsableByteArray) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i = readUnsignedByte & 127;
        while ((readUnsignedByte & 128) == 128) {
            readUnsignedByte = parsableByteArray.readUnsignedByte();
            i = (i << 7) | (readUnsignedByte & 127);
        }
        return i;
    }

    private static int parseHdlr(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(16);
        int readInt = parsableByteArray.readInt();
        return readInt == TYPE_soun ? 1 : readInt == TYPE_vide ? 2 : (readInt == TYPE_text || readInt == TYPE_sbtl || readInt == TYPE_subt || readInt == TYPE_clcp) ? 3 : readInt == TYPE_meta ? 4 : -1;
    }

    private static Metadata parseIlst(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(8);
        List arrayList = new ArrayList();
        while (parsableByteArray.getPosition() < i) {
            Entry parseIlstElement = MetadataUtil.parseIlstElement(parsableByteArray);
            if (parseIlstElement != null) {
                arrayList.add(parseIlstElement);
            }
        }
        return arrayList.isEmpty() ? null : new Metadata(arrayList);
    }

    private static Pair<Long, String> parseMdhd(ParsableByteArray parsableByteArray) {
        int i = 8;
        parsableByteArray.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(parseFullAtomVersion == 0 ? 8 : 16);
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        if (parseFullAtomVersion == 0) {
            i = 4;
        }
        parsableByteArray.skipBytes(i);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        return Pair.create(Long.valueOf(readUnsignedInt), TtmlNode.ANONYMOUS_REGION_ID + ((char) (((readUnsignedShort >> 10) & 31) + 96)) + ((char) (((readUnsignedShort >> 5) & 31) + 96)) + ((char) ((readUnsignedShort & 31) + 96)));
    }

    private static Metadata parseMetaAtom(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(12);
        while (parsableByteArray.getPosition() < i) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == Atom.TYPE_ilst) {
                parsableByteArray.setPosition(position);
                return parseIlst(parsableByteArray, position + readInt);
            }
            parsableByteArray.skipBytes(readInt - 8);
        }
        return null;
    }

    private static long parseMvhd(ParsableByteArray parsableByteArray) {
        int i = 8;
        parsableByteArray.setPosition(8);
        if (Atom.parseFullAtomVersion(parsableByteArray.readInt()) != 0) {
            i = 16;
        }
        parsableByteArray.skipBytes(i);
        return parsableByteArray.readUnsignedInt();
    }

    private static float parsePaspFromParent(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition(i + 8);
        return ((float) parsableByteArray.readUnsignedIntToInt()) / ((float) parsableByteArray.readUnsignedIntToInt());
    }

    private static byte[] parseProjFromParent(ParsableByteArray parsableByteArray, int i, int i2) {
        int i3 = i + 8;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == Atom.TYPE_proj) {
                return Arrays.copyOfRange(parsableByteArray.data, i3, readInt + i3);
            }
            i3 += readInt;
        }
        return null;
    }

    private static Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData(ParsableByteArray parsableByteArray, int i, int i2) {
        int position = parsableByteArray.getPosition();
        while (position - i < i2) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
            if (parsableByteArray.readInt() == Atom.TYPE_sinf) {
                Pair<Integer, TrackEncryptionBox> parseCommonEncryptionSinfFromParent = parseCommonEncryptionSinfFromParent(parsableByteArray, position, readInt);
                if (parseCommonEncryptionSinfFromParent != null) {
                    return parseCommonEncryptionSinfFromParent;
                }
            }
            position += readInt;
        }
        return null;
    }

    private static TrackEncryptionBox parseSchiFromParent(ParsableByteArray parsableByteArray, int i, int i2, String str) {
        byte[] bArr = null;
        boolean z = true;
        int i3 = i + 8;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == Atom.TYPE_tenc) {
                int i4;
                int i5;
                i3 = Atom.parseFullAtomVersion(parsableByteArray.readInt());
                parsableByteArray.skipBytes(1);
                if (i3 == 0) {
                    parsableByteArray.skipBytes(1);
                    i4 = 0;
                    i5 = 0;
                } else {
                    i3 = parsableByteArray.readUnsignedByte();
                    i5 = (i3 & PsExtractor.VIDEO_STREAM_MASK) >> 4;
                    i4 = i3 & 15;
                }
                if (parsableByteArray.readUnsignedByte() != 1) {
                    z = false;
                }
                readInt = parsableByteArray.readUnsignedByte();
                byte[] bArr2 = new byte[16];
                parsableByteArray.readBytes(bArr2, 0, bArr2.length);
                if (z && readInt == 0) {
                    i3 = parsableByteArray.readUnsignedByte();
                    bArr = new byte[i3];
                    parsableByteArray.readBytes(bArr, 0, i3);
                }
                return new TrackEncryptionBox(z, str, readInt, bArr2, i5, i4, bArr);
            }
            i3 += readInt;
        }
        return null;
    }

    public static TrackSampleTable parseStbl(Track track, ContainerAtom containerAtom, GaplessInfoHolder gaplessInfoHolder) {
        SampleSizeBox stszSampleSizeBox;
        LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_stsz);
        if (leafAtomOfType != null) {
            stszSampleSizeBox = new StszSampleSizeBox(leafAtomOfType);
        } else {
            leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_stz2);
            if (leafAtomOfType == null) {
                throw new ParserException("Track has no sample table size information");
            }
            stszSampleSizeBox = new Stz2SampleSizeBox(leafAtomOfType);
        }
        int sampleCount = stszSampleSizeBox.getSampleCount();
        if (sampleCount == 0) {
            return new TrackSampleTable(new long[0], new int[0], 0, new long[0], new int[0]);
        }
        int readUnsignedIntToInt;
        int i;
        int i2;
        Object obj;
        int i3;
        long j;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        long[] jArr;
        int i9;
        Object obj2;
        Object obj3;
        long j2;
        boolean z = false;
        leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_stco);
        if (leafAtomOfType == null) {
            z = true;
            leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_co64);
        }
        ParsableByteArray parsableByteArray = leafAtomOfType.data;
        ParsableByteArray parsableByteArray2 = containerAtom.getLeafAtomOfType(Atom.TYPE_stsc).data;
        ParsableByteArray parsableByteArray3 = containerAtom.getLeafAtomOfType(Atom.TYPE_stts).data;
        leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_stss);
        ParsableByteArray parsableByteArray4 = leafAtomOfType != null ? leafAtomOfType.data : null;
        LeafAtom leafAtomOfType2 = containerAtom.getLeafAtomOfType(Atom.TYPE_ctts);
        ParsableByteArray parsableByteArray5 = leafAtomOfType2 != null ? leafAtomOfType2.data : null;
        ChunkIterator chunkIterator = new ChunkIterator(parsableByteArray2, parsableByteArray, z);
        parsableByteArray3.setPosition(12);
        int readUnsignedIntToInt2 = parsableByteArray3.readUnsignedIntToInt() - 1;
        int readUnsignedIntToInt3 = parsableByteArray3.readUnsignedIntToInt();
        int readUnsignedIntToInt4 = parsableByteArray3.readUnsignedIntToInt();
        int i10 = 0;
        if (parsableByteArray5 != null) {
            parsableByteArray5.setPosition(12);
            i10 = parsableByteArray5.readUnsignedIntToInt();
        }
        if (parsableByteArray4 != null) {
            parsableByteArray4.setPosition(12);
            readUnsignedIntToInt = parsableByteArray4.readUnsignedIntToInt();
            if (readUnsignedIntToInt > 0) {
                i = readUnsignedIntToInt;
                readUnsignedIntToInt = parsableByteArray4.readUnsignedIntToInt() - 1;
                parsableByteArray = parsableByteArray4;
                i2 = i;
            } else {
                i = readUnsignedIntToInt;
                readUnsignedIntToInt = -1;
                parsableByteArray = null;
                i2 = i;
            }
        } else {
            readUnsignedIntToInt = -1;
            parsableByteArray = parsableByteArray4;
            i2 = 0;
        }
        Object obj4 = (stszSampleSizeBox.isFixedSampleSize() && MimeTypes.AUDIO_RAW.equals(track.format.sampleMimeType) && readUnsignedIntToInt2 == 0 && i10 == 0 && i2 == 0) ? 1 : null;
        if (obj4 == null) {
            Object obj5 = new long[sampleCount];
            obj = new int[sampleCount];
            long[] jArr2 = new long[sampleCount];
            Object obj6 = new int[sampleCount];
            long j3 = 0;
            int i11 = 0;
            i3 = readUnsignedIntToInt3;
            int i12 = 0;
            int i13 = i2;
            i2 = 0;
            i = readUnsignedIntToInt;
            readUnsignedIntToInt = i10;
            j = 0;
            i4 = 0;
            i5 = 0;
            i6 = readUnsignedIntToInt4;
            i7 = readUnsignedIntToInt2;
            readUnsignedIntToInt4 = i;
            while (i11 < sampleCount) {
                long j4 = j3;
                i8 = i5;
                while (i8 == 0) {
                    Assertions.checkState(chunkIterator.moveNext());
                    j4 = chunkIterator.offset;
                    i8 = chunkIterator.numSamples;
                }
                if (parsableByteArray5 != null) {
                    while (i12 == 0 && readUnsignedIntToInt > 0) {
                        i12 = parsableByteArray5.readUnsignedIntToInt();
                        i2 = parsableByteArray5.readInt();
                        readUnsignedIntToInt--;
                    }
                    i12--;
                }
                obj5[i11] = j4;
                obj[i11] = stszSampleSizeBox.readNextSampleSize();
                if (obj[i11] > i4) {
                    i4 = obj[i11];
                }
                jArr2[i11] = ((long) i2) + j;
                obj6[i11] = parsableByteArray == null ? 1 : 0;
                if (i11 == readUnsignedIntToInt4) {
                    obj6[i11] = 1;
                    i5 = i13 - 1;
                    if (i5 > 0) {
                        readUnsignedIntToInt4 = parsableByteArray.readUnsignedIntToInt() - 1;
                        i13 = i5;
                    } else {
                        i13 = i5;
                    }
                }
                long j5 = ((long) i6) + j;
                i5 = i3 - 1;
                if (i5 != 0 || i7 <= 0) {
                    i = i6;
                    i6 = i5;
                    i5 = i;
                } else {
                    i6 = parsableByteArray3.readUnsignedIntToInt();
                    i5 = parsableByteArray3.readUnsignedIntToInt();
                    i7--;
                }
                j4 += (long) obj[i11];
                i10 = i8 - 1;
                i11++;
                j3 = j4;
                i3 = i6;
                i6 = i5;
                i5 = i10;
                j = j5;
            }
            Assertions.checkArgument(i12 == 0);
            while (readUnsignedIntToInt > 0) {
                Assertions.checkArgument(parsableByteArray5.readUnsignedIntToInt() == 0);
                parsableByteArray5.readInt();
                readUnsignedIntToInt--;
            }
            if (!(i13 == 0 && i3 == 0 && i5 == 0 && i7 == 0)) {
                Log.w(TAG, "Inconsistent stbl box for track " + track.id + ": remainingSynchronizationSamples " + i13 + ", remainingSamplesAtTimestampDelta " + i3 + ", remainingSamplesInChunk " + i5 + ", remainingTimestampDeltaChanges " + i7);
            }
            obj4 = obj6;
            jArr = jArr2;
            i9 = i4;
            obj2 = obj;
            obj3 = obj5;
            j2 = j;
        } else {
            long[] jArr3 = new long[chunkIterator.length];
            int[] iArr = new int[chunkIterator.length];
            while (chunkIterator.moveNext()) {
                jArr3[chunkIterator.index] = chunkIterator.offset;
                iArr[chunkIterator.index] = chunkIterator.numSamples;
            }
            Results rechunk = FixedSampleSizeRechunker.rechunk(stszSampleSizeBox.readNextSampleSize(), jArr3, iArr, (long) readUnsignedIntToInt4);
            obj3 = rechunk.offsets;
            obj2 = rechunk.sizes;
            i9 = rechunk.maximumSize;
            jArr = rechunk.timestamps;
            obj4 = rechunk.flags;
            j2 = 0;
        }
        if (track.editListDurations == null || gaplessInfoHolder.hasGaplessInfo()) {
            Util.scaleLargeTimestampsInPlace(jArr, C3446C.MICROS_PER_SECOND, track.timescale);
            return new TrackSampleTable(obj3, obj2, i9, jArr, obj4);
        }
        long scaleLargeTimestamp;
        if (track.editListDurations.length == 1 && track.type == 1 && jArr.length >= 2) {
            long j6 = track.editListMediaTimes[0];
            scaleLargeTimestamp = Util.scaleLargeTimestamp(track.editListDurations[0], track.timescale, track.movieTimescale) + j6;
            if (jArr[0] <= j6 && j6 < jArr[1] && jArr[jArr.length - 1] < scaleLargeTimestamp && scaleLargeTimestamp <= j2) {
                j2 -= scaleLargeTimestamp;
                j6 = Util.scaleLargeTimestamp(j6 - jArr[0], (long) track.format.sampleRate, track.timescale);
                scaleLargeTimestamp = Util.scaleLargeTimestamp(j2, (long) track.format.sampleRate, track.timescale);
                if (!(j6 == 0 && scaleLargeTimestamp == 0) && j6 <= 2147483647L && scaleLargeTimestamp <= 2147483647L) {
                    gaplessInfoHolder.encoderDelay = (int) j6;
                    gaplessInfoHolder.encoderPadding = (int) scaleLargeTimestamp;
                    Util.scaleLargeTimestampsInPlace(jArr, C3446C.MICROS_PER_SECOND, track.timescale);
                    return new TrackSampleTable(obj3, obj2, i9, jArr, obj4);
                }
            }
        }
        int i14;
        if (track.editListDurations.length == 1 && track.editListDurations[0] == 0) {
            for (i14 = 0; i14 < jArr.length; i14++) {
                jArr[i14] = Util.scaleLargeTimestamp(jArr[i14] - track.editListMediaTimes[0], C3446C.MICROS_PER_SECOND, track.timescale);
            }
            return new TrackSampleTable(obj3, obj2, i9, jArr, obj4);
        }
        long j7;
        int binarySearchCeil;
        boolean z2 = track.type == 1;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        while (i15 < track.editListDurations.length) {
            j7 = track.editListMediaTimes[i15];
            if (j7 != -1) {
                scaleLargeTimestamp = Util.scaleLargeTimestamp(track.editListDurations[i15], track.timescale, track.movieTimescale);
                binarySearchCeil = Util.binarySearchCeil(jArr, j7, true, true);
                i6 = Util.binarySearchCeil(jArr, scaleLargeTimestamp + j7, z2, false);
                i7 = i18 + (i6 - binarySearchCeil);
                i5 = i16 | (i17 != binarySearchCeil ? 1 : 0);
            } else {
                i5 = i16;
                i6 = i17;
                i7 = i18;
            }
            i15++;
            i16 = i5;
            i17 = i6;
            i18 = i7;
        }
        i3 = i16 | (i18 != sampleCount ? 1 : 0);
        Object obj7 = i3 != 0 ? new long[i18] : obj3;
        Object obj8 = i3 != 0 ? new int[i18] : obj2;
        binarySearchCeil = i3 != 0 ? 0 : i9;
        obj = i3 != 0 ? new int[i18] : obj4;
        long[] jArr4 = new long[i18];
        i18 = 0;
        i15 = 0;
        j7 = 0;
        i16 = binarySearchCeil;
        while (i18 < track.editListDurations.length) {
            long j8 = track.editListMediaTimes[i18];
            scaleLargeTimestamp = track.editListDurations[i18];
            if (j8 != -1) {
                j = j8 + Util.scaleLargeTimestamp(scaleLargeTimestamp, track.timescale, track.movieTimescale);
                i6 = Util.binarySearchCeil(jArr, j8, true, true);
                int binarySearchCeil2 = Util.binarySearchCeil(jArr, j, z2, false);
                if (i3 != 0) {
                    i7 = binarySearchCeil2 - i6;
                    System.arraycopy(obj3, i6, obj7, i15, i7);
                    System.arraycopy(obj2, i6, obj8, i15, i7);
                    System.arraycopy(obj4, i6, obj, i15, i7);
                }
                readUnsignedIntToInt3 = i6;
                int i19 = i15;
                i8 = i16;
                while (readUnsignedIntToInt3 < binarySearchCeil2) {
                    jArr4[i19] = Util.scaleLargeTimestamp(jArr[readUnsignedIntToInt3] - j8, C3446C.MICROS_PER_SECOND, track.timescale) + Util.scaleLargeTimestamp(j7, C3446C.MICROS_PER_SECOND, track.movieTimescale);
                    i6 = (i3 == 0 || obj8[i19] <= i8) ? i8 : obj2[readUnsignedIntToInt3];
                    i19++;
                    readUnsignedIntToInt3++;
                    i8 = i6;
                }
                i6 = i19;
                i7 = i8;
            } else {
                i6 = i15;
                i7 = i16;
            }
            i18++;
            i15 = i6;
            j7 += scaleLargeTimestamp;
            i16 = i7;
        }
        i4 = 0;
        for (i14 = 0; i14 < obj.length && i4 == 0; i14++) {
            i4 |= (obj[i14] & 1) != 0 ? 1 : 0;
        }
        if (i4 != 0) {
            return new TrackSampleTable(obj7, obj8, i16, jArr4, obj);
        }
        Log.w(TAG, "Ignoring edit list: Edited sample sequence does not contain a sync sample.");
        Util.scaleLargeTimestampsInPlace(jArr, C3446C.MICROS_PER_SECOND, track.timescale);
        return new TrackSampleTable(obj3, obj2, i9, jArr, obj4);
    }

    private static StsdData parseStsd(ParsableByteArray parsableByteArray, int i, int i2, String str, DrmInitData drmInitData, boolean z) {
        parsableByteArray.setPosition(12);
        int readInt = parsableByteArray.readInt();
        StsdData stsdData = new StsdData(readInt);
        for (int i3 = 0; i3 < readInt; i3++) {
            int position = parsableByteArray.getPosition();
            int readInt2 = parsableByteArray.readInt();
            Assertions.checkArgument(readInt2 > 0, "childAtomSize should be positive");
            int readInt3 = parsableByteArray.readInt();
            if (readInt3 == Atom.TYPE_avc1 || readInt3 == Atom.TYPE_avc3 || readInt3 == Atom.TYPE_encv || readInt3 == Atom.TYPE_mp4v || readInt3 == Atom.TYPE_hvc1 || readInt3 == Atom.TYPE_hev1 || readInt3 == Atom.TYPE_s263 || readInt3 == Atom.TYPE_vp08 || readInt3 == Atom.TYPE_vp09) {
                parseVideoSampleEntry(parsableByteArray, readInt3, position, readInt2, i, i2, drmInitData, stsdData, i3);
            } else if (readInt3 == Atom.TYPE_mp4a || readInt3 == Atom.TYPE_enca || readInt3 == Atom.TYPE_ac_3 || readInt3 == Atom.TYPE_ec_3 || readInt3 == Atom.TYPE_dtsc || readInt3 == Atom.TYPE_dtse || readInt3 == Atom.TYPE_dtsh || readInt3 == Atom.TYPE_dtsl || readInt3 == Atom.TYPE_samr || readInt3 == Atom.TYPE_sawb || readInt3 == Atom.TYPE_lpcm || readInt3 == Atom.TYPE_sowt || readInt3 == Atom.TYPE__mp3 || readInt3 == Atom.TYPE_alac) {
                parseAudioSampleEntry(parsableByteArray, readInt3, position, readInt2, i, str, z, drmInitData, stsdData, i3);
            } else if (readInt3 == Atom.TYPE_TTML || readInt3 == Atom.TYPE_tx3g || readInt3 == Atom.TYPE_wvtt || readInt3 == Atom.TYPE_stpp || readInt3 == Atom.TYPE_c608) {
                parseTextSampleEntry(parsableByteArray, readInt3, position, readInt2, i, str, stsdData);
            } else if (readInt3 == Atom.TYPE_camm) {
                stsdData.format = Format.createSampleFormat(Integer.toString(i), MimeTypes.APPLICATION_CAMERA_MOTION, null, -1, null);
            }
            parsableByteArray.setPosition(position + readInt2);
        }
        return stsdData;
    }

    private static void parseTextSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, StsdData stsdData) {
        String str2;
        parsableByteArray.setPosition((i2 + 8) + 8);
        List list = null;
        long j = Long.MAX_VALUE;
        if (i == Atom.TYPE_TTML) {
            str2 = MimeTypes.APPLICATION_TTML;
        } else if (i == Atom.TYPE_tx3g) {
            str2 = MimeTypes.APPLICATION_TX3G;
            int i5 = (i3 - 8) - 8;
            Object obj = new byte[i5];
            parsableByteArray.readBytes(obj, 0, i5);
            list = Collections.singletonList(obj);
        } else if (i == Atom.TYPE_wvtt) {
            str2 = MimeTypes.APPLICATION_MP4VTT;
        } else if (i == Atom.TYPE_stpp) {
            str2 = MimeTypes.APPLICATION_TTML;
            j = 0;
        } else if (i == Atom.TYPE_c608) {
            str2 = MimeTypes.APPLICATION_MP4CEA608;
            stsdData.requiredSampleTransformation = 1;
        } else {
            throw new IllegalStateException();
        }
        stsdData.format = Format.createTextSampleFormat(Integer.toString(i4), str2, null, -1, 0, str, -1, null, j, list);
    }

    private static TkhdData parseTkhd(ParsableByteArray parsableByteArray) {
        long j;
        int i = 8;
        parsableByteArray.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(parseFullAtomVersion == 0 ? 8 : 16);
        int readInt = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        Object obj = 1;
        int position = parsableByteArray.getPosition();
        if (parseFullAtomVersion == 0) {
            i = 4;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (parsableByteArray.data[position + i2] != (byte) -1) {
                obj = null;
                break;
            }
        }
        if (obj != null) {
            parsableByteArray.skipBytes(i);
            j = C3446C.TIME_UNSET;
        } else {
            j = parseFullAtomVersion == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
            if (j == 0) {
                j = C3446C.TIME_UNSET;
            }
        }
        parsableByteArray.skipBytes(16);
        int readInt2 = parsableByteArray.readInt();
        int readInt3 = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int readInt4 = parsableByteArray.readInt();
        int readInt5 = parsableByteArray.readInt();
        readInt2 = (readInt2 == 0 && readInt3 == C3446C.DEFAULT_BUFFER_SEGMENT_SIZE && readInt4 == (-65536) && readInt5 == 0) ? 90 : (readInt2 == 0 && readInt3 == (-65536) && readInt4 == C3446C.DEFAULT_BUFFER_SEGMENT_SIZE && readInt5 == 0) ? 270 : (readInt2 == (-65536) && readInt3 == 0 && readInt4 == 0 && readInt5 == (-65536)) ? 180 : 0;
        return new TkhdData(readInt, j, readInt2);
    }

    public static Track parseTrak(ContainerAtom containerAtom, LeafAtom leafAtom, long j, DrmInitData drmInitData, boolean z, boolean z2) {
        ContainerAtom containerAtomOfType = containerAtom.getContainerAtomOfType(Atom.TYPE_mdia);
        int parseHdlr = parseHdlr(containerAtomOfType.getLeafAtomOfType(Atom.TYPE_hdlr).data);
        if (parseHdlr == -1) {
            return null;
        }
        TkhdData parseTkhd = parseTkhd(containerAtom.getLeafAtomOfType(Atom.TYPE_tkhd).data);
        long access$000 = j == C3446C.TIME_UNSET ? parseTkhd.duration : j;
        long parseMvhd = parseMvhd(leafAtom.data);
        long scaleLargeTimestamp = access$000 == C3446C.TIME_UNSET ? C3446C.TIME_UNSET : Util.scaleLargeTimestamp(access$000, C3446C.MICROS_PER_SECOND, parseMvhd);
        ContainerAtom containerAtomOfType2 = containerAtomOfType.getContainerAtomOfType(Atom.TYPE_minf).getContainerAtomOfType(Atom.TYPE_stbl);
        Pair parseMdhd = parseMdhd(containerAtomOfType.getLeafAtomOfType(Atom.TYPE_mdhd).data);
        StsdData parseStsd = parseStsd(containerAtomOfType2.getLeafAtomOfType(Atom.TYPE_stsd).data, parseTkhd.id, parseTkhd.rotationDegrees, (String) parseMdhd.second, drmInitData, z2);
        long[] jArr = null;
        long[] jArr2 = null;
        if (!z) {
            Pair parseEdts = parseEdts(containerAtom.getContainerAtomOfType(Atom.TYPE_edts));
            jArr2 = (long[]) parseEdts.second;
            jArr = (long[]) parseEdts.first;
        }
        if (parseStsd.format == null) {
            return null;
        }
        return new Track(parseTkhd.id, parseHdlr, ((Long) parseMdhd.first).longValue(), parseMvhd, scaleLargeTimestamp, parseStsd.format, parseStsd.requiredSampleTransformation, parseStsd.trackEncryptionBoxes, parseStsd.nalUnitLengthFieldLength, jArr, jArr2);
    }

    public static Metadata parseUdta(LeafAtom leafAtom, boolean z) {
        if (z) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtom.data;
        parsableByteArray.setPosition(8);
        while (parsableByteArray.bytesLeft() >= 8) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == Atom.TYPE_meta) {
                parsableByteArray.setPosition(position);
                return parseMetaAtom(parsableByteArray, position + readInt);
            }
            parsableByteArray.skipBytes(readInt - 8);
        }
        return null;
    }

    private static void parseVideoSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, int i5, DrmInitData drmInitData, StsdData stsdData, int i6) {
        DrmInitData drmInitData2;
        parsableByteArray.setPosition((i2 + 8) + 8);
        parsableByteArray.skipBytes(16);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
        float f = 1.0f;
        parsableByteArray.skipBytes(50);
        int position = parsableByteArray.getPosition();
        if (i == Atom.TYPE_encv) {
            DrmInitData copyWithSchemeType;
            Pair parseSampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray, i2, i3);
            if (parseSampleEntryEncryptionData != null) {
                i = ((Integer) parseSampleEntryEncryptionData.first).intValue();
                copyWithSchemeType = drmInitData == null ? null : drmInitData.copyWithSchemeType(((TrackEncryptionBox) parseSampleEntryEncryptionData.second).schemeType);
                stsdData.trackEncryptionBoxes[i6] = (TrackEncryptionBox) parseSampleEntryEncryptionData.second;
            } else {
                copyWithSchemeType = drmInitData;
            }
            parsableByteArray.setPosition(position);
            drmInitData2 = copyWithSchemeType;
        } else {
            drmInitData2 = drmInitData;
        }
        List list = null;
        String str = null;
        byte[] bArr = null;
        int i7 = -1;
        int i8 = position;
        Object obj = null;
        int i9 = i8;
        while (i9 - i2 < i3) {
            parsableByteArray.setPosition(i9);
            int position2 = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (readInt != 0 || parsableByteArray.getPosition() - i2 != i3) {
                Object obj2;
                Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
                int readInt2 = parsableByteArray.readInt();
                if (readInt2 == Atom.TYPE_avcC) {
                    Assertions.checkState(str == null);
                    str = "video/avc";
                    parsableByteArray.setPosition(position2 + 8);
                    AvcConfig parse = AvcConfig.parse(parsableByteArray);
                    list = parse.initializationData;
                    stsdData.nalUnitLengthFieldLength = parse.nalUnitLengthFieldLength;
                    if (obj == null) {
                        f = parse.pixelWidthAspectRatio;
                    }
                    obj2 = obj;
                } else if (readInt2 == Atom.TYPE_hvcC) {
                    Assertions.checkState(str == null);
                    str = MimeTypes.VIDEO_H265;
                    parsableByteArray.setPosition(position2 + 8);
                    HevcConfig parse2 = HevcConfig.parse(parsableByteArray);
                    list = parse2.initializationData;
                    stsdData.nalUnitLengthFieldLength = parse2.nalUnitLengthFieldLength;
                    obj2 = obj;
                } else if (readInt2 == Atom.TYPE_vpcC) {
                    Assertions.checkState(str == null);
                    str = i == Atom.TYPE_vp08 ? MimeTypes.VIDEO_VP8 : MimeTypes.VIDEO_VP9;
                    obj2 = obj;
                } else if (readInt2 == Atom.TYPE_d263) {
                    Assertions.checkState(str == null);
                    str = MimeTypes.VIDEO_H263;
                    obj2 = obj;
                } else if (readInt2 == Atom.TYPE_esds) {
                    Assertions.checkState(str == null);
                    Pair parseEsdsFromParent = parseEsdsFromParent(parsableByteArray, position2);
                    String str2 = (String) parseEsdsFromParent.first;
                    list = Collections.singletonList(parseEsdsFromParent.second);
                    str = str2;
                    obj2 = obj;
                } else if (readInt2 == Atom.TYPE_pasp) {
                    f = parsePaspFromParent(parsableByteArray, position2);
                    obj2 = 1;
                } else if (readInt2 == Atom.TYPE_sv3d) {
                    bArr = parseProjFromParent(parsableByteArray, position2, readInt);
                    obj2 = obj;
                } else {
                    if (readInt2 == Atom.TYPE_st3d) {
                        readInt2 = parsableByteArray.readUnsignedByte();
                        parsableByteArray.skipBytes(3);
                        if (readInt2 == 0) {
                            switch (parsableByteArray.readUnsignedByte()) {
                                case 0:
                                    i7 = 0;
                                    obj2 = obj;
                                    continue;
                                case 1:
                                    i7 = 1;
                                    obj2 = obj;
                                    continue;
                                case 2:
                                    i7 = 2;
                                    obj2 = obj;
                                    continue;
                                case 3:
                                    i7 = 3;
                                    obj2 = obj;
                                    continue;
                            }
                        }
                    }
                    obj2 = obj;
                }
                i9 += readInt;
                obj = obj2;
            } else if (str == null) {
                stsdData.format = Format.createVideoSampleFormat(Integer.toString(i4), str, null, -1, -1, readUnsignedShort, readUnsignedShort2, -1.0f, list, i5, f, bArr, i7, null, drmInitData2);
            }
        }
        if (str == null) {
            stsdData.format = Format.createVideoSampleFormat(Integer.toString(i4), str, null, -1, -1, readUnsignedShort, readUnsignedShort2, -1.0f, list, i5, f, bArr, i7, null, drmInitData2);
        }
    }
}
