package org.telegram.messenger.exoplayer2.extractor.ts;

import android.util.SparseArray;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorsFactory;
import org.telegram.messenger.exoplayer2.extractor.PositionHolder;
import org.telegram.messenger.exoplayer2.extractor.SeekMap.Unseekable;
import org.telegram.messenger.exoplayer2.extractor.ts.TsPayloadReader.TrackIdGenerator;
import org.telegram.messenger.exoplayer2.util.ParsableBitArray;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.TimestampAdjuster;

public final class PsExtractor implements Extractor {
    public static final int AUDIO_STREAM = 192;
    public static final int AUDIO_STREAM_MASK = 224;
    public static final ExtractorsFactory FACTORY = new C34911();
    private static final long MAX_SEARCH_LENGTH = 1048576;
    private static final int MAX_STREAM_ID_PLUS_ONE = 256;
    private static final int MPEG_PROGRAM_END_CODE = 441;
    private static final int PACKET_START_CODE_PREFIX = 1;
    private static final int PACK_START_CODE = 442;
    public static final int PRIVATE_STREAM_1 = 189;
    private static final int SYSTEM_HEADER_START_CODE = 443;
    public static final int VIDEO_STREAM = 224;
    public static final int VIDEO_STREAM_MASK = 240;
    private boolean foundAllTracks;
    private boolean foundAudioTrack;
    private boolean foundVideoTrack;
    private ExtractorOutput output;
    private final ParsableByteArray psPacketBuffer;
    private final SparseArray<PesReader> psPayloadReaders;
    private final TimestampAdjuster timestampAdjuster;

    /* renamed from: org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor$1 */
    static class C34911 implements ExtractorsFactory {
        C34911() {
        }

        public Extractor[] createExtractors() {
            return new Extractor[]{new PsExtractor()};
        }
    }

    private static final class PesReader {
        private static final int PES_SCRATCH_SIZE = 64;
        private boolean dtsFlag;
        private int extendedHeaderLength;
        private final ElementaryStreamReader pesPayloadReader;
        private final ParsableBitArray pesScratch = new ParsableBitArray(new byte[64]);
        private boolean ptsFlag;
        private boolean seenFirstDts;
        private long timeUs;
        private final TimestampAdjuster timestampAdjuster;

        public PesReader(ElementaryStreamReader elementaryStreamReader, TimestampAdjuster timestampAdjuster) {
            this.pesPayloadReader = elementaryStreamReader;
            this.timestampAdjuster = timestampAdjuster;
        }

        private void parseHeader() {
            this.pesScratch.skipBits(8);
            this.ptsFlag = this.pesScratch.readBit();
            this.dtsFlag = this.pesScratch.readBit();
            this.pesScratch.skipBits(6);
            this.extendedHeaderLength = this.pesScratch.readBits(8);
        }

        private void parseHeaderExtension() {
            this.timeUs = 0;
            if (this.ptsFlag) {
                this.pesScratch.skipBits(4);
                long readBits = ((long) this.pesScratch.readBits(3)) << 30;
                this.pesScratch.skipBits(1);
                readBits |= (long) (this.pesScratch.readBits(15) << 15);
                this.pesScratch.skipBits(1);
                readBits |= (long) this.pesScratch.readBits(15);
                this.pesScratch.skipBits(1);
                if (!this.seenFirstDts && this.dtsFlag) {
                    this.pesScratch.skipBits(4);
                    long readBits2 = ((long) this.pesScratch.readBits(3)) << 30;
                    this.pesScratch.skipBits(1);
                    readBits2 |= (long) (this.pesScratch.readBits(15) << 15);
                    this.pesScratch.skipBits(1);
                    readBits2 |= (long) this.pesScratch.readBits(15);
                    this.pesScratch.skipBits(1);
                    this.timestampAdjuster.adjustTsTimestamp(readBits2);
                    this.seenFirstDts = true;
                }
                this.timeUs = this.timestampAdjuster.adjustTsTimestamp(readBits);
            }
        }

        public void consume(ParsableByteArray parsableByteArray) {
            parsableByteArray.readBytes(this.pesScratch.data, 0, 3);
            this.pesScratch.setPosition(0);
            parseHeader();
            parsableByteArray.readBytes(this.pesScratch.data, 0, this.extendedHeaderLength);
            this.pesScratch.setPosition(0);
            parseHeaderExtension();
            this.pesPayloadReader.packetStarted(this.timeUs, true);
            this.pesPayloadReader.consume(parsableByteArray);
            this.pesPayloadReader.packetFinished();
        }

        public void seek() {
            this.seenFirstDts = false;
            this.pesPayloadReader.seek();
        }
    }

    public PsExtractor() {
        this(new TimestampAdjuster(0));
    }

    public PsExtractor(TimestampAdjuster timestampAdjuster) {
        this.timestampAdjuster = timestampAdjuster;
        this.psPacketBuffer = new ParsableByteArray(4096);
        this.psPayloadReaders = new SparseArray();
    }

    public void init(ExtractorOutput extractorOutput) {
        this.output = extractorOutput;
        extractorOutput.seekMap(new Unseekable(C3446C.TIME_UNSET));
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) {
        if (!extractorInput.peekFully(this.psPacketBuffer.data, 0, 4, true)) {
            return -1;
        }
        this.psPacketBuffer.setPosition(0);
        int readInt = this.psPacketBuffer.readInt();
        if (readInt == MPEG_PROGRAM_END_CODE) {
            return -1;
        }
        if (readInt == PACK_START_CODE) {
            extractorInput.peekFully(this.psPacketBuffer.data, 0, 10);
            this.psPacketBuffer.setPosition(9);
            extractorInput.skipFully((this.psPacketBuffer.readUnsignedByte() & 7) + 14);
            return 0;
        } else if (readInt == SYSTEM_HEADER_START_CODE) {
            extractorInput.peekFully(this.psPacketBuffer.data, 0, 2);
            this.psPacketBuffer.setPosition(0);
            extractorInput.skipFully(this.psPacketBuffer.readUnsignedShort() + 6);
            return 0;
        } else if (((readInt & -256) >> 8) != 1) {
            extractorInput.skipFully(1);
            return 0;
        } else {
            int i = readInt & 255;
            PesReader pesReader = (PesReader) this.psPayloadReaders.get(i);
            if (!this.foundAllTracks) {
                if (pesReader == null) {
                    ElementaryStreamReader elementaryStreamReader = null;
                    if (!this.foundAudioTrack && i == PRIVATE_STREAM_1) {
                        elementaryStreamReader = new Ac3Reader();
                        this.foundAudioTrack = true;
                    } else if (!this.foundAudioTrack && (i & 224) == AUDIO_STREAM) {
                        elementaryStreamReader = new MpegAudioReader();
                        this.foundAudioTrack = true;
                    } else if (!this.foundVideoTrack && (i & VIDEO_STREAM_MASK) == 224) {
                        elementaryStreamReader = new H262Reader();
                        this.foundVideoTrack = true;
                    }
                    if (elementaryStreamReader != null) {
                        elementaryStreamReader.createTracks(this.output, new TrackIdGenerator(i, 256));
                        pesReader = new PesReader(elementaryStreamReader, this.timestampAdjuster);
                        this.psPayloadReaders.put(i, pesReader);
                    }
                }
                if ((this.foundAudioTrack && this.foundVideoTrack) || extractorInput.getPosition() > MAX_SEARCH_LENGTH) {
                    this.foundAllTracks = true;
                    this.output.endTracks();
                }
            }
            extractorInput.peekFully(this.psPacketBuffer.data, 0, 2);
            this.psPacketBuffer.setPosition(0);
            readInt = this.psPacketBuffer.readUnsignedShort() + 6;
            if (pesReader == null) {
                extractorInput.skipFully(readInt);
            } else {
                this.psPacketBuffer.reset(readInt);
                extractorInput.readFully(this.psPacketBuffer.data, 0, readInt);
                this.psPacketBuffer.setPosition(6);
                pesReader.consume(this.psPacketBuffer);
                this.psPacketBuffer.setLimit(this.psPacketBuffer.capacity());
            }
            return 0;
        }
    }

    public void release() {
    }

    public void seek(long j, long j2) {
        this.timestampAdjuster.reset();
        for (int i = 0; i < this.psPayloadReaders.size(); i++) {
            ((PesReader) this.psPayloadReaders.valueAt(i)).seek();
        }
    }

    public boolean sniff(ExtractorInput extractorInput) {
        boolean z = true;
        byte[] bArr = new byte[14];
        extractorInput.peekFully(bArr, 0, 14);
        if (PACK_START_CODE != (((((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16)) | ((bArr[2] & 255) << 8)) | (bArr[3] & 255)) || (bArr[4] & 196) != 68 || (bArr[6] & 4) != 4 || (bArr[8] & 4) != 4 || (bArr[9] & 1) != 1 || (bArr[12] & 3) != 3) {
            return false;
        }
        extractorInput.advancePeekPosition(bArr[13] & 7);
        extractorInput.peekFully(bArr, 0, 3);
        if (1 != ((bArr[2] & 255) | (((bArr[0] & 255) << 16) | ((bArr[1] & 255) << 8)))) {
            z = false;
        }
        return z;
    }
}
