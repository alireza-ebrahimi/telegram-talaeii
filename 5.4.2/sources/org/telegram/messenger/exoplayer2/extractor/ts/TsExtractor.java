package org.telegram.messenger.exoplayer2.extractor.ts;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorsFactory;
import org.telegram.messenger.exoplayer2.extractor.SeekMap.Unseekable;
import org.telegram.messenger.exoplayer2.extractor.ts.TsPayloadReader.DvbSubtitleInfo;
import org.telegram.messenger.exoplayer2.extractor.ts.TsPayloadReader.EsInfo;
import org.telegram.messenger.exoplayer2.extractor.ts.TsPayloadReader.Factory;
import org.telegram.messenger.exoplayer2.extractor.ts.TsPayloadReader.TrackIdGenerator;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.ParsableBitArray;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.TimestampAdjuster;
import org.telegram.messenger.exoplayer2.util.Util;

public final class TsExtractor implements Extractor {
    private static final long AC3_FORMAT_IDENTIFIER = ((long) Util.getIntegerCodeForString("AC-3"));
    private static final int BUFFER_SIZE = 9400;
    private static final long E_AC3_FORMAT_IDENTIFIER = ((long) Util.getIntegerCodeForString("EAC3"));
    public static final ExtractorsFactory FACTORY = new C34921();
    private static final long HEVC_FORMAT_IDENTIFIER = ((long) Util.getIntegerCodeForString("HEVC"));
    private static final int MAX_PID_PLUS_ONE = 8192;
    public static final int MODE_HLS = 2;
    public static final int MODE_MULTI_PMT = 0;
    public static final int MODE_SINGLE_PMT = 1;
    private static final int SNIFF_TS_PACKET_COUNT = 5;
    private static final int TS_PACKET_SIZE = 188;
    private static final int TS_PAT_PID = 0;
    public static final int TS_STREAM_TYPE_AAC = 15;
    public static final int TS_STREAM_TYPE_AC3 = 129;
    public static final int TS_STREAM_TYPE_DTS = 138;
    public static final int TS_STREAM_TYPE_DVBSUBS = 89;
    public static final int TS_STREAM_TYPE_E_AC3 = 135;
    public static final int TS_STREAM_TYPE_H262 = 2;
    public static final int TS_STREAM_TYPE_H264 = 27;
    public static final int TS_STREAM_TYPE_H265 = 36;
    public static final int TS_STREAM_TYPE_HDMV_DTS = 130;
    public static final int TS_STREAM_TYPE_ID3 = 21;
    public static final int TS_STREAM_TYPE_MPA = 3;
    public static final int TS_STREAM_TYPE_MPA_LSF = 4;
    public static final int TS_STREAM_TYPE_SPLICE_INFO = 134;
    private static final int TS_SYNC_BYTE = 71;
    private final SparseIntArray continuityCounters;
    private TsPayloadReader id3Reader;
    private final int mode;
    private ExtractorOutput output;
    private final Factory payloadReaderFactory;
    private int remainingPmts;
    private final List<TimestampAdjuster> timestampAdjusters;
    private final SparseBooleanArray trackIds;
    private boolean tracksEnded;
    private final ParsableByteArray tsPacketBuffer;
    private final SparseArray<TsPayloadReader> tsPayloadReaders;

    /* renamed from: org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor$1 */
    static class C34921 implements ExtractorsFactory {
        C34921() {
        }

        public Extractor[] createExtractors() {
            return new Extractor[]{new TsExtractor()};
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    private class PatReader implements SectionPayloadReader {
        private final ParsableBitArray patScratch = new ParsableBitArray(new byte[4]);

        public void consume(ParsableByteArray parsableByteArray) {
            if (parsableByteArray.readUnsignedByte() == 0) {
                parsableByteArray.skipBytes(7);
                int bytesLeft = parsableByteArray.bytesLeft() / 4;
                for (int i = 0; i < bytesLeft; i++) {
                    parsableByteArray.readBytes(this.patScratch, 4);
                    int readBits = this.patScratch.readBits(16);
                    this.patScratch.skipBits(3);
                    if (readBits == 0) {
                        this.patScratch.skipBits(13);
                    } else {
                        readBits = this.patScratch.readBits(13);
                        TsExtractor.this.tsPayloadReaders.put(readBits, new SectionReader(new PmtReader(readBits)));
                        TsExtractor.this.remainingPmts = TsExtractor.this.remainingPmts + 1;
                    }
                }
                if (TsExtractor.this.mode != 2) {
                    TsExtractor.this.tsPayloadReaders.remove(0);
                }
            }
        }

        public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator) {
        }
    }

    private class PmtReader implements SectionPayloadReader {
        private static final int TS_PMT_DESC_AC3 = 106;
        private static final int TS_PMT_DESC_DTS = 123;
        private static final int TS_PMT_DESC_DVBSUBS = 89;
        private static final int TS_PMT_DESC_EAC3 = 122;
        private static final int TS_PMT_DESC_ISO639_LANG = 10;
        private static final int TS_PMT_DESC_REGISTRATION = 5;
        private final int pid;
        private final ParsableBitArray pmtScratch = new ParsableBitArray(new byte[5]);
        private final SparseIntArray trackIdToPidScratch = new SparseIntArray();
        private final SparseArray<TsPayloadReader> trackIdToReaderScratch = new SparseArray();

        public PmtReader(int i) {
            this.pid = i;
        }

        private EsInfo readEsInfo(ParsableByteArray parsableByteArray, int i) {
            int position = parsableByteArray.getPosition();
            int i2 = position + i;
            int i3 = -1;
            String str = null;
            List list = null;
            while (parsableByteArray.getPosition() < i2) {
                int readUnsignedByte = parsableByteArray.readUnsignedByte();
                int readUnsignedByte2 = parsableByteArray.readUnsignedByte() + parsableByteArray.getPosition();
                if (readUnsignedByte == 5) {
                    long readUnsignedInt = parsableByteArray.readUnsignedInt();
                    if (readUnsignedInt == TsExtractor.AC3_FORMAT_IDENTIFIER) {
                        i3 = 129;
                    } else if (readUnsignedInt == TsExtractor.E_AC3_FORMAT_IDENTIFIER) {
                        i3 = TsExtractor.TS_STREAM_TYPE_E_AC3;
                    } else if (readUnsignedInt == TsExtractor.HEVC_FORMAT_IDENTIFIER) {
                        i3 = 36;
                    }
                } else if (readUnsignedByte == 106) {
                    i3 = 129;
                } else if (readUnsignedByte == TS_PMT_DESC_EAC3) {
                    i3 = TsExtractor.TS_STREAM_TYPE_E_AC3;
                } else if (readUnsignedByte == TS_PMT_DESC_DTS) {
                    i3 = TsExtractor.TS_STREAM_TYPE_DTS;
                } else if (readUnsignedByte == 10) {
                    str = parsableByteArray.readString(3).trim();
                } else if (readUnsignedByte == 89) {
                    i3 = 89;
                    list = new ArrayList();
                    while (parsableByteArray.getPosition() < readUnsignedByte2) {
                        String trim = parsableByteArray.readString(3).trim();
                        int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
                        byte[] bArr = new byte[4];
                        parsableByteArray.readBytes(bArr, 0, 4);
                        list.add(new DvbSubtitleInfo(trim, readUnsignedByte3, bArr));
                    }
                }
                parsableByteArray.skipBytes(readUnsignedByte2 - parsableByteArray.getPosition());
            }
            parsableByteArray.setPosition(i2);
            return new EsInfo(i3, str, list, Arrays.copyOfRange(parsableByteArray.data, position, i2));
        }

        public void consume(ParsableByteArray parsableByteArray) {
            if (parsableByteArray.readUnsignedByte() == 2) {
                TimestampAdjuster timestampAdjuster;
                int readBits;
                int readBits2;
                if (TsExtractor.this.mode == 1 || TsExtractor.this.mode == 2 || TsExtractor.this.remainingPmts == 1) {
                    timestampAdjuster = (TimestampAdjuster) TsExtractor.this.timestampAdjusters.get(0);
                } else {
                    timestampAdjuster = new TimestampAdjuster(((TimestampAdjuster) TsExtractor.this.timestampAdjusters.get(0)).getFirstSampleTimestampUs());
                    TsExtractor.this.timestampAdjusters.add(timestampAdjuster);
                }
                parsableByteArray.skipBytes(2);
                int readUnsignedShort = parsableByteArray.readUnsignedShort();
                parsableByteArray.skipBytes(5);
                parsableByteArray.readBytes(this.pmtScratch, 2);
                this.pmtScratch.skipBits(4);
                parsableByteArray.skipBytes(this.pmtScratch.readBits(12));
                if (TsExtractor.this.mode == 2 && TsExtractor.this.id3Reader == null) {
                    TsExtractor.this.id3Reader = TsExtractor.this.payloadReaderFactory.createPayloadReader(21, new EsInfo(21, null, null, new byte[0]));
                    TsExtractor.this.id3Reader.init(timestampAdjuster, TsExtractor.this.output, new TrackIdGenerator(readUnsignedShort, 21, 8192));
                }
                this.trackIdToReaderScratch.clear();
                this.trackIdToPidScratch.clear();
                int bytesLeft = parsableByteArray.bytesLeft();
                while (bytesLeft > 0) {
                    parsableByteArray.readBytes(this.pmtScratch, 5);
                    int readBits3 = this.pmtScratch.readBits(8);
                    this.pmtScratch.skipBits(3);
                    readBits = this.pmtScratch.readBits(13);
                    this.pmtScratch.skipBits(4);
                    readBits2 = this.pmtScratch.readBits(12);
                    EsInfo readEsInfo = readEsInfo(parsableByteArray, readBits2);
                    if (readBits3 == 6) {
                        readBits3 = readEsInfo.streamType;
                    }
                    readBits2 = bytesLeft - (readBits2 + 5);
                    bytesLeft = TsExtractor.this.mode == 2 ? readBits3 : readBits;
                    if (TsExtractor.this.trackIds.get(bytesLeft)) {
                        bytesLeft = readBits2;
                    } else {
                        Object access$400 = (TsExtractor.this.mode == 2 && readBits3 == 21) ? TsExtractor.this.id3Reader : TsExtractor.this.payloadReaderFactory.createPayloadReader(readBits3, readEsInfo);
                        if (TsExtractor.this.mode != 2 || readBits < this.trackIdToPidScratch.get(bytesLeft, 8192)) {
                            this.trackIdToPidScratch.put(bytesLeft, readBits);
                            this.trackIdToReaderScratch.put(bytesLeft, access$400);
                        }
                        bytesLeft = readBits2;
                    }
                }
                readBits = this.trackIdToPidScratch.size();
                for (bytesLeft = 0; bytesLeft < readBits; bytesLeft++) {
                    readBits2 = this.trackIdToPidScratch.keyAt(bytesLeft);
                    TsExtractor.this.trackIds.put(readBits2, true);
                    TsPayloadReader tsPayloadReader = (TsPayloadReader) this.trackIdToReaderScratch.valueAt(bytesLeft);
                    if (tsPayloadReader != null) {
                        if (tsPayloadReader != TsExtractor.this.id3Reader) {
                            tsPayloadReader.init(timestampAdjuster, TsExtractor.this.output, new TrackIdGenerator(readUnsignedShort, readBits2, 8192));
                        }
                        TsExtractor.this.tsPayloadReaders.put(this.trackIdToPidScratch.valueAt(bytesLeft), tsPayloadReader);
                    }
                }
                if (TsExtractor.this.mode != 2) {
                    TsExtractor.this.tsPayloadReaders.remove(this.pid);
                    TsExtractor.this.remainingPmts = TsExtractor.this.mode == 1 ? 0 : TsExtractor.this.remainingPmts - 1;
                    if (TsExtractor.this.remainingPmts == 0) {
                        TsExtractor.this.output.endTracks();
                        TsExtractor.this.tracksEnded = true;
                    }
                } else if (!TsExtractor.this.tracksEnded) {
                    TsExtractor.this.output.endTracks();
                    TsExtractor.this.remainingPmts = 0;
                    TsExtractor.this.tracksEnded = true;
                }
            }
        }

        public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator) {
        }
    }

    public TsExtractor() {
        this(0);
    }

    public TsExtractor(int i) {
        this(1, i);
    }

    public TsExtractor(int i, int i2) {
        this(i, new TimestampAdjuster(0), new DefaultTsPayloadReaderFactory(i2));
    }

    public TsExtractor(int i, TimestampAdjuster timestampAdjuster, Factory factory) {
        this.payloadReaderFactory = (Factory) Assertions.checkNotNull(factory);
        this.mode = i;
        if (i == 1 || i == 2) {
            this.timestampAdjusters = Collections.singletonList(timestampAdjuster);
        } else {
            this.timestampAdjusters = new ArrayList();
            this.timestampAdjusters.add(timestampAdjuster);
        }
        this.tsPacketBuffer = new ParsableByteArray((int) BUFFER_SIZE);
        this.trackIds = new SparseBooleanArray();
        this.tsPayloadReaders = new SparseArray();
        this.continuityCounters = new SparseIntArray();
        resetPayloadReaders();
    }

    private void resetPayloadReaders() {
        this.trackIds.clear();
        this.tsPayloadReaders.clear();
        SparseArray createInitialPayloadReaders = this.payloadReaderFactory.createInitialPayloadReaders();
        int size = createInitialPayloadReaders.size();
        for (int i = 0; i < size; i++) {
            this.tsPayloadReaders.put(createInitialPayloadReaders.keyAt(i), createInitialPayloadReaders.valueAt(i));
        }
        this.tsPayloadReaders.put(0, new SectionReader(new PatReader()));
        this.id3Reader = null;
    }

    public void init(ExtractorOutput extractorOutput) {
        this.output = extractorOutput;
        extractorOutput.seekMap(new Unseekable(C3446C.TIME_UNSET));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(org.telegram.messenger.exoplayer2.extractor.ExtractorInput r12, org.telegram.messenger.exoplayer2.extractor.PositionHolder r13) {
        /*
        r11 = this;
        r7 = 188; // 0xbc float:2.63E-43 double:9.3E-322;
        r0 = -1;
        r1 = 1;
        r2 = 0;
        r3 = r11.tsPacketBuffer;
        r3 = r3.data;
        r4 = r11.tsPacketBuffer;
        r4 = r4.getPosition();
        r4 = 9400 - r4;
        if (r4 >= r7) goto L_0x0029;
    L_0x0013:
        r4 = r11.tsPacketBuffer;
        r4 = r4.bytesLeft();
        if (r4 <= 0) goto L_0x0024;
    L_0x001b:
        r5 = r11.tsPacketBuffer;
        r5 = r5.getPosition();
        java.lang.System.arraycopy(r3, r5, r3, r2, r4);
    L_0x0024:
        r5 = r11.tsPacketBuffer;
        r5.reset(r3, r4);
    L_0x0029:
        r4 = r11.tsPacketBuffer;
        r4 = r4.bytesLeft();
        if (r4 >= r7) goto L_0x0048;
    L_0x0031:
        r4 = r11.tsPacketBuffer;
        r4 = r4.limit();
        r5 = 9400 - r4;
        r5 = r12.read(r3, r4, r5);
        if (r5 != r0) goto L_0x0041;
    L_0x003f:
        r2 = r0;
    L_0x0040:
        return r2;
    L_0x0041:
        r6 = r11.tsPacketBuffer;
        r4 = r4 + r5;
        r6.setLimit(r4);
        goto L_0x0029;
    L_0x0048:
        r0 = r11.tsPacketBuffer;
        r5 = r0.limit();
        r0 = r11.tsPacketBuffer;
        r0 = r0.getPosition();
    L_0x0054:
        if (r0 >= r5) goto L_0x005f;
    L_0x0056:
        r4 = r3[r0];
        r6 = 71;
        if (r4 == r6) goto L_0x005f;
    L_0x005c:
        r0 = r0 + 1;
        goto L_0x0054;
    L_0x005f:
        r3 = r11.tsPacketBuffer;
        r3.setPosition(r0);
        r6 = r0 + 188;
        if (r6 > r5) goto L_0x0040;
    L_0x0068:
        r0 = r11.tsPacketBuffer;
        r7 = r0.readInt();
        r0 = 8388608; // 0x800000 float:1.17549435E-38 double:4.144523E-317;
        r0 = r0 & r7;
        if (r0 == 0) goto L_0x0079;
    L_0x0073:
        r0 = r11.tsPacketBuffer;
        r0.setPosition(r6);
        goto L_0x0040;
    L_0x0079:
        r0 = 4194304; // 0x400000 float:5.877472E-39 double:2.0722615E-317;
        r0 = r0 & r7;
        if (r0 == 0) goto L_0x00ad;
    L_0x007e:
        r4 = r1;
    L_0x007f:
        r0 = 2096896; // 0x1fff00 float:2.938377E-39 double:1.0360043E-317;
        r0 = r0 & r7;
        r8 = r0 >> 8;
        r0 = r7 & 32;
        if (r0 == 0) goto L_0x00af;
    L_0x0089:
        r3 = r1;
    L_0x008a:
        r0 = r7 & 16;
        if (r0 == 0) goto L_0x00b1;
    L_0x008e:
        r0 = r1;
    L_0x008f:
        r9 = r11.mode;
        r10 = 2;
        if (r9 == r10) goto L_0x00ed;
    L_0x0094:
        r7 = r7 & 15;
        r9 = r11.continuityCounters;
        r10 = r7 + -1;
        r9 = r9.get(r8, r10);
        r10 = r11.continuityCounters;
        r10.put(r8, r7);
        if (r9 != r7) goto L_0x00b3;
    L_0x00a5:
        if (r0 == 0) goto L_0x00ed;
    L_0x00a7:
        r0 = r11.tsPacketBuffer;
        r0.setPosition(r6);
        goto L_0x0040;
    L_0x00ad:
        r4 = r2;
        goto L_0x007f;
    L_0x00af:
        r3 = r2;
        goto L_0x008a;
    L_0x00b1:
        r0 = r2;
        goto L_0x008f;
    L_0x00b3:
        r9 = r9 + 1;
        r9 = r9 & 15;
        if (r7 == r9) goto L_0x00ed;
    L_0x00b9:
        if (r3 == 0) goto L_0x00c6;
    L_0x00bb:
        r3 = r11.tsPacketBuffer;
        r3 = r3.readUnsignedByte();
        r7 = r11.tsPacketBuffer;
        r7.skipBytes(r3);
    L_0x00c6:
        if (r0 == 0) goto L_0x00e6;
    L_0x00c8:
        r0 = r11.tsPayloadReaders;
        r0 = r0.get(r8);
        r0 = (org.telegram.messenger.exoplayer2.extractor.ts.TsPayloadReader) r0;
        if (r0 == 0) goto L_0x00e6;
    L_0x00d2:
        if (r1 == 0) goto L_0x00d7;
    L_0x00d4:
        r0.seek();
    L_0x00d7:
        r1 = r11.tsPacketBuffer;
        r1.setLimit(r6);
        r1 = r11.tsPacketBuffer;
        r0.consume(r1, r4);
        r0 = r11.tsPacketBuffer;
        r0.setLimit(r5);
    L_0x00e6:
        r0 = r11.tsPacketBuffer;
        r0.setPosition(r6);
        goto L_0x0040;
    L_0x00ed:
        r1 = r2;
        goto L_0x00b9;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor.read(org.telegram.messenger.exoplayer2.extractor.ExtractorInput, org.telegram.messenger.exoplayer2.extractor.PositionHolder):int");
    }

    public void release() {
    }

    public void seek(long j, long j2) {
        int size = this.timestampAdjusters.size();
        for (int i = 0; i < size; i++) {
            ((TimestampAdjuster) this.timestampAdjusters.get(i)).reset();
        }
        this.tsPacketBuffer.reset();
        this.continuityCounters.clear();
        resetPayloadReaders();
    }

    public boolean sniff(ExtractorInput extractorInput) {
        byte[] bArr = this.tsPacketBuffer.data;
        extractorInput.peekFully(bArr, 0, 940);
        int i = 0;
        while (i < TS_PACKET_SIZE) {
            int i2 = 0;
            while (i2 != 5) {
                if (bArr[(i2 * TS_PACKET_SIZE) + i] != (byte) 71) {
                    i++;
                } else {
                    i2++;
                }
            }
            extractorInput.skipFully(i);
            return true;
        }
        return false;
    }
}
