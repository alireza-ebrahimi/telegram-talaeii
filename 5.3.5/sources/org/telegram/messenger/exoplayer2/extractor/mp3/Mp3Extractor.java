package org.telegram.messenger.exoplayer2.extractor.mp3;

import java.io.EOFException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorsFactory;
import org.telegram.messenger.exoplayer2.extractor.GaplessInfoHolder;
import org.telegram.messenger.exoplayer2.extractor.MpegAudioHeader;
import org.telegram.messenger.exoplayer2.extractor.PositionHolder;
import org.telegram.messenger.exoplayer2.extractor.SeekMap;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.metadata.Metadata;
import org.telegram.messenger.exoplayer2.metadata.id3.Id3Decoder;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.Util;

public final class Mp3Extractor implements Extractor {
    public static final ExtractorsFactory FACTORY = new C09131();
    public static final int FLAG_DISABLE_ID3_METADATA = 2;
    public static final int FLAG_ENABLE_CONSTANT_BITRATE_SEEKING = 1;
    private static final int MAX_SNIFF_BYTES = 16384;
    private static final int MAX_SYNC_BYTES = 131072;
    private static final int MPEG_AUDIO_HEADER_MASK = -128000;
    private static final int SCRATCH_LENGTH = 10;
    private static final int SEEK_HEADER_INFO = Util.getIntegerCodeForString("Info");
    private static final int SEEK_HEADER_UNSET = 0;
    private static final int SEEK_HEADER_VBRI = Util.getIntegerCodeForString("VBRI");
    private static final int SEEK_HEADER_XING = Util.getIntegerCodeForString("Xing");
    private long basisTimeUs;
    private ExtractorOutput extractorOutput;
    private final int flags;
    private final long forcedFirstSampleTimestampUs;
    private final GaplessInfoHolder gaplessInfoHolder;
    private Metadata metadata;
    private int sampleBytesRemaining;
    private long samplesRead;
    private final ParsableByteArray scratch;
    private Seeker seeker;
    private final MpegAudioHeader synchronizedHeader;
    private int synchronizedHeaderData;
    private TrackOutput trackOutput;

    interface Seeker extends SeekMap {
        long getTimeUs(long j);
    }

    /* renamed from: org.telegram.messenger.exoplayer2.extractor.mp3.Mp3Extractor$1 */
    static class C09131 implements ExtractorsFactory {
        C09131() {
        }

        public Extractor[] createExtractors() {
            return new Extractor[]{new Mp3Extractor()};
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public Mp3Extractor() {
        this(0);
    }

    public Mp3Extractor(int flags) {
        this(flags, C0907C.TIME_UNSET);
    }

    public Mp3Extractor(int flags, long forcedFirstSampleTimestampUs) {
        this.flags = flags;
        this.forcedFirstSampleTimestampUs = forcedFirstSampleTimestampUs;
        this.scratch = new ParsableByteArray(10);
        this.synchronizedHeader = new MpegAudioHeader();
        this.gaplessInfoHolder = new GaplessInfoHolder();
        this.basisTimeUs = C0907C.TIME_UNSET;
    }

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        return synchronize(input, true);
    }

    public void init(ExtractorOutput output) {
        this.extractorOutput = output;
        this.trackOutput = this.extractorOutput.track(0, 1);
        this.extractorOutput.endTracks();
    }

    public void seek(long position, long timeUs) {
        this.synchronizedHeaderData = 0;
        this.basisTimeUs = C0907C.TIME_UNSET;
        this.samplesRead = 0;
        this.sampleBytesRemaining = 0;
    }

    public void release() {
    }

    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        if (this.synchronizedHeaderData == 0) {
            try {
                synchronize(input, false);
            } catch (EOFException e) {
                return -1;
            }
        }
        if (this.seeker == null) {
            this.seeker = maybeReadSeekFrame(input);
            if (this.seeker == null || !(this.seeker.isSeekable() || (this.flags & 1) == 0)) {
                this.seeker = getConstantBitrateSeeker(input);
            }
            this.extractorOutput.seekMap(this.seeker);
            this.trackOutput.format(Format.createAudioSampleFormat(null, this.synchronizedHeader.mimeType, null, -1, 4096, this.synchronizedHeader.channels, this.synchronizedHeader.sampleRate, -1, this.gaplessInfoHolder.encoderDelay, this.gaplessInfoHolder.encoderPadding, null, null, 0, null, (this.flags & 2) != 0 ? null : this.metadata));
        }
        return readSample(input);
    }

    private int readSample(ExtractorInput extractorInput) throws IOException, InterruptedException {
        if (this.sampleBytesRemaining == 0) {
            extractorInput.resetPeekPosition();
            if (!extractorInput.peekFully(this.scratch.data, 0, 4, true)) {
                return -1;
            }
            this.scratch.setPosition(0);
            int sampleHeaderData = this.scratch.readInt();
            if (!headersMatch(sampleHeaderData, (long) this.synchronizedHeaderData) || MpegAudioHeader.getFrameSize(sampleHeaderData) == -1) {
                extractorInput.skipFully(1);
                this.synchronizedHeaderData = 0;
                return 0;
            }
            MpegAudioHeader.populateHeader(sampleHeaderData, this.synchronizedHeader);
            if (this.basisTimeUs == C0907C.TIME_UNSET) {
                this.basisTimeUs = this.seeker.getTimeUs(extractorInput.getPosition());
                if (this.forcedFirstSampleTimestampUs != C0907C.TIME_UNSET) {
                    this.basisTimeUs += this.forcedFirstSampleTimestampUs - this.seeker.getTimeUs(0);
                }
            }
            this.sampleBytesRemaining = this.synchronizedHeader.frameSize;
        }
        int bytesAppended = this.trackOutput.sampleData(extractorInput, this.sampleBytesRemaining, true);
        if (bytesAppended == -1) {
            return -1;
        }
        this.sampleBytesRemaining -= bytesAppended;
        if (this.sampleBytesRemaining > 0) {
            return 0;
        }
        this.trackOutput.sampleMetadata(this.basisTimeUs + ((this.samplesRead * C0907C.MICROS_PER_SECOND) / ((long) this.synchronizedHeader.sampleRate)), 1, this.synchronizedHeader.frameSize, 0, null);
        this.samplesRead += (long) this.synchronizedHeader.samplesPerFrame;
        this.sampleBytesRemaining = 0;
        return 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean synchronize(org.telegram.messenger.exoplayer2.extractor.ExtractorInput r13, boolean r14) throws java.io.IOException, java.lang.InterruptedException {
        /*
        r12 = this;
        r7 = 0;
        r0 = 0;
        r3 = 0;
        r5 = 0;
        if (r14 == 0) goto L_0x003c;
    L_0x0006:
        r4 = 16384; // 0x4000 float:2.2959E-41 double:8.0948E-320;
    L_0x0008:
        r13.resetPeekPosition();
        r8 = r13.getPosition();
        r10 = 0;
        r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r8 != 0) goto L_0x0022;
    L_0x0015:
        r12.peekId3Data(r13);
        r8 = r13.getPeekPosition();
        r3 = (int) r8;
        if (r14 != 0) goto L_0x0022;
    L_0x001f:
        r13.skipFully(r3);
    L_0x0022:
        r8 = r12.scratch;
        r9 = r8.data;
        r10 = 0;
        r11 = 4;
        if (r7 <= 0) goto L_0x003f;
    L_0x002a:
        r8 = 1;
    L_0x002b:
        r8 = r13.peekFully(r9, r10, r11, r8);
        if (r8 != 0) goto L_0x0041;
    L_0x0031:
        if (r14 == 0) goto L_0x0098;
    L_0x0033:
        r8 = r3 + r5;
        r13.skipFully(r8);
    L_0x0038:
        r12.synchronizedHeaderData = r0;
        r8 = 1;
    L_0x003b:
        return r8;
    L_0x003c:
        r4 = 131072; // 0x20000 float:1.83671E-40 double:6.47582E-319;
        goto L_0x0008;
    L_0x003f:
        r8 = 0;
        goto L_0x002b;
    L_0x0041:
        r8 = r12.scratch;
        r9 = 0;
        r8.setPosition(r9);
        r8 = r12.scratch;
        r2 = r8.readInt();
        if (r0 == 0) goto L_0x0056;
    L_0x004f:
        r8 = (long) r0;
        r8 = headersMatch(r2, r8);
        if (r8 == 0) goto L_0x005d;
    L_0x0056:
        r1 = org.telegram.messenger.exoplayer2.extractor.MpegAudioHeader.getFrameSize(r2);
        r8 = -1;
        if (r1 != r8) goto L_0x0083;
    L_0x005d:
        r6 = r5 + 1;
        if (r5 != r4) goto L_0x006f;
    L_0x0061:
        if (r14 != 0) goto L_0x006c;
    L_0x0063:
        r8 = new org.telegram.messenger.exoplayer2.ParserException;
        r9 = "Searched too many bytes.";
        r8.<init>(r9);
        throw r8;
    L_0x006c:
        r8 = 0;
        r5 = r6;
        goto L_0x003b;
    L_0x006f:
        r7 = 0;
        r0 = 0;
        if (r14 == 0) goto L_0x007d;
    L_0x0073:
        r13.resetPeekPosition();
        r8 = r3 + r6;
        r13.advancePeekPosition(r8);
        r5 = r6;
        goto L_0x0022;
    L_0x007d:
        r8 = 1;
        r13.skipFully(r8);
        r5 = r6;
        goto L_0x0022;
    L_0x0083:
        r7 = r7 + 1;
        r8 = 1;
        if (r7 != r8) goto L_0x0094;
    L_0x0088:
        r8 = r12.synchronizedHeader;
        org.telegram.messenger.exoplayer2.extractor.MpegAudioHeader.populateHeader(r2, r8);
        r0 = r2;
    L_0x008e:
        r8 = r1 + -4;
        r13.advancePeekPosition(r8);
        goto L_0x0022;
    L_0x0094:
        r8 = 4;
        if (r7 != r8) goto L_0x008e;
    L_0x0097:
        goto L_0x0031;
    L_0x0098:
        r13.resetPeekPosition();
        goto L_0x0038;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.extractor.mp3.Mp3Extractor.synchronize(org.telegram.messenger.exoplayer2.extractor.ExtractorInput, boolean):boolean");
    }

    private void peekId3Data(ExtractorInput input) throws IOException, InterruptedException {
        int peekedId3Bytes = 0;
        while (true) {
            input.peekFully(this.scratch.data, 0, 10);
            this.scratch.setPosition(0);
            if (this.scratch.readUnsignedInt24() != Id3Decoder.ID3_TAG) {
                input.resetPeekPosition();
                input.advancePeekPosition(peekedId3Bytes);
                return;
            }
            this.scratch.skipBytes(3);
            int framesLength = this.scratch.readSynchSafeInt();
            int tagLength = framesLength + 10;
            if (this.metadata == null) {
                byte[] id3Data = new byte[tagLength];
                System.arraycopy(this.scratch.data, 0, id3Data, 0, 10);
                input.peekFully(id3Data, 10, framesLength);
                this.metadata = new Id3Decoder((this.flags & 2) != 0 ? GaplessInfoHolder.GAPLESS_INFO_ID3_FRAME_PREDICATE : null).decode(id3Data, tagLength);
                if (this.metadata != null) {
                    this.gaplessInfoHolder.setFromMetadata(this.metadata);
                }
            } else {
                input.advancePeekPosition(framesLength);
            }
            peekedId3Bytes += tagLength;
        }
    }

    private Seeker maybeReadSeekFrame(ExtractorInput input) throws IOException, InterruptedException {
        Seeker seeker;
        int xingBase = 21;
        ParsableByteArray frame = new ParsableByteArray(this.synchronizedHeader.frameSize);
        input.peekFully(frame.data, 0, this.synchronizedHeader.frameSize);
        if ((this.synchronizedHeader.version & 1) != 0) {
            if (this.synchronizedHeader.channels != 1) {
                xingBase = 36;
            }
        } else if (this.synchronizedHeader.channels == 1) {
            xingBase = 13;
        }
        int seekHeader = getSeekFrameHeader(frame, xingBase);
        if (seekHeader == SEEK_HEADER_XING || seekHeader == SEEK_HEADER_INFO) {
            seeker = XingSeeker.create(this.synchronizedHeader, frame, input.getPosition(), input.getLength());
            if (!(seeker == null || this.gaplessInfoHolder.hasGaplessInfo())) {
                input.resetPeekPosition();
                input.advancePeekPosition(xingBase + 141);
                input.peekFully(this.scratch.data, 0, 3);
                this.scratch.setPosition(0);
                this.gaplessInfoHolder.setFromXingHeaderValue(this.scratch.readUnsignedInt24());
            }
            input.skipFully(this.synchronizedHeader.frameSize);
            if (!(seeker == null || seeker.isSeekable() || seekHeader != SEEK_HEADER_INFO)) {
                return getConstantBitrateSeeker(input);
            }
        } else if (seekHeader == SEEK_HEADER_VBRI) {
            seeker = VbriSeeker.create(this.synchronizedHeader, frame, input.getPosition(), input.getLength());
            input.skipFully(this.synchronizedHeader.frameSize);
        } else {
            seeker = null;
            input.resetPeekPosition();
        }
        return seeker;
    }

    private Seeker getConstantBitrateSeeker(ExtractorInput input) throws IOException, InterruptedException {
        input.peekFully(this.scratch.data, 0, 4);
        this.scratch.setPosition(0);
        MpegAudioHeader.populateHeader(this.scratch.readInt(), this.synchronizedHeader);
        return new ConstantBitrateSeeker(input.getPosition(), this.synchronizedHeader.bitrate, input.getLength());
    }

    private static boolean headersMatch(int headerA, long headerB) {
        return ((long) (MPEG_AUDIO_HEADER_MASK & headerA)) == (-128000 & headerB);
    }

    private static int getSeekFrameHeader(ParsableByteArray frame, int xingBase) {
        if (frame.limit() >= xingBase + 4) {
            frame.setPosition(xingBase);
            int headerData = frame.readInt();
            if (headerData == SEEK_HEADER_XING || headerData == SEEK_HEADER_INFO) {
                return headerData;
            }
        }
        if (frame.limit() >= 40) {
            frame.setPosition(36);
            if (frame.readInt() == SEEK_HEADER_VBRI) {
                return SEEK_HEADER_VBRI;
            }
        }
        return 0;
    }
}
