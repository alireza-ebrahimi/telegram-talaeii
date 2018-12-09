package org.telegram.messenger.exoplayer2.extractor.mp3;

import java.io.EOFException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.telegram.messenger.exoplayer2.C3446C;
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
    public static final ExtractorsFactory FACTORY = new C34811();
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
    static class C34811 implements ExtractorsFactory {
        C34811() {
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

    public Mp3Extractor(int i) {
        this(i, C3446C.TIME_UNSET);
    }

    public Mp3Extractor(int i, long j) {
        this.flags = i;
        this.forcedFirstSampleTimestampUs = j;
        this.scratch = new ParsableByteArray(10);
        this.synchronizedHeader = new MpegAudioHeader();
        this.gaplessInfoHolder = new GaplessInfoHolder();
        this.basisTimeUs = C3446C.TIME_UNSET;
    }

    private Seeker getConstantBitrateSeeker(ExtractorInput extractorInput) {
        extractorInput.peekFully(this.scratch.data, 0, 4);
        this.scratch.setPosition(0);
        MpegAudioHeader.populateHeader(this.scratch.readInt(), this.synchronizedHeader);
        return new ConstantBitrateSeeker(extractorInput.getPosition(), this.synchronizedHeader.bitrate, extractorInput.getLength());
    }

    private static int getSeekFrameHeader(ParsableByteArray parsableByteArray, int i) {
        if (parsableByteArray.limit() >= i + 4) {
            parsableByteArray.setPosition(i);
            int readInt = parsableByteArray.readInt();
            if (readInt == SEEK_HEADER_XING || readInt == SEEK_HEADER_INFO) {
                return readInt;
            }
        }
        if (parsableByteArray.limit() >= 40) {
            parsableByteArray.setPosition(36);
            if (parsableByteArray.readInt() == SEEK_HEADER_VBRI) {
                return SEEK_HEADER_VBRI;
            }
        }
        return 0;
    }

    private static boolean headersMatch(int i, long j) {
        return ((long) (MPEG_AUDIO_HEADER_MASK & i)) == (-128000 & j);
    }

    private Seeker maybeReadSeekFrame(ExtractorInput extractorInput) {
        ParsableByteArray parsableByteArray = new ParsableByteArray(this.synchronizedHeader.frameSize);
        extractorInput.peekFully(parsableByteArray.data, 0, this.synchronizedHeader.frameSize);
        int i = (this.synchronizedHeader.version & 1) != 0 ? this.synchronizedHeader.channels != 1 ? 36 : 21 : this.synchronizedHeader.channels != 1 ? 21 : 13;
        int seekFrameHeader = getSeekFrameHeader(parsableByteArray, i);
        Seeker create;
        if (seekFrameHeader == SEEK_HEADER_XING || seekFrameHeader == SEEK_HEADER_INFO) {
            create = XingSeeker.create(this.synchronizedHeader, parsableByteArray, extractorInput.getPosition(), extractorInput.getLength());
            if (!(create == null || this.gaplessInfoHolder.hasGaplessInfo())) {
                extractorInput.resetPeekPosition();
                extractorInput.advancePeekPosition(i + 141);
                extractorInput.peekFully(this.scratch.data, 0, 3);
                this.scratch.setPosition(0);
                this.gaplessInfoHolder.setFromXingHeaderValue(this.scratch.readUnsignedInt24());
            }
            extractorInput.skipFully(this.synchronizedHeader.frameSize);
            return (create == null || create.isSeekable() || seekFrameHeader != SEEK_HEADER_INFO) ? create : getConstantBitrateSeeker(extractorInput);
        } else if (seekFrameHeader == SEEK_HEADER_VBRI) {
            create = VbriSeeker.create(this.synchronizedHeader, parsableByteArray, extractorInput.getPosition(), extractorInput.getLength());
            extractorInput.skipFully(this.synchronizedHeader.frameSize);
            return create;
        } else {
            extractorInput.resetPeekPosition();
            return null;
        }
    }

    private void peekId3Data(ExtractorInput extractorInput) {
        int i = 0;
        while (true) {
            extractorInput.peekFully(this.scratch.data, 0, 10);
            this.scratch.setPosition(0);
            if (this.scratch.readUnsignedInt24() != Id3Decoder.ID3_TAG) {
                extractorInput.resetPeekPosition();
                extractorInput.advancePeekPosition(i);
                return;
            }
            this.scratch.skipBytes(3);
            int readSynchSafeInt = this.scratch.readSynchSafeInt();
            int i2 = readSynchSafeInt + 10;
            if (this.metadata == null) {
                Object obj = new byte[i2];
                System.arraycopy(this.scratch.data, 0, obj, 0, 10);
                extractorInput.peekFully(obj, 10, readSynchSafeInt);
                this.metadata = new Id3Decoder((this.flags & 2) != 0 ? GaplessInfoHolder.GAPLESS_INFO_ID3_FRAME_PREDICATE : null).decode(obj, i2);
                if (this.metadata != null) {
                    this.gaplessInfoHolder.setFromMetadata(this.metadata);
                }
            } else {
                extractorInput.advancePeekPosition(readSynchSafeInt);
            }
            i += i2;
        }
    }

    private int readSample(ExtractorInput extractorInput) {
        int readInt;
        if (this.sampleBytesRemaining == 0) {
            extractorInput.resetPeekPosition();
            if (!extractorInput.peekFully(this.scratch.data, 0, 4, true)) {
                return -1;
            }
            this.scratch.setPosition(0);
            readInt = this.scratch.readInt();
            if (!headersMatch(readInt, (long) this.synchronizedHeaderData) || MpegAudioHeader.getFrameSize(readInt) == -1) {
                extractorInput.skipFully(1);
                this.synchronizedHeaderData = 0;
                return 0;
            }
            MpegAudioHeader.populateHeader(readInt, this.synchronizedHeader);
            if (this.basisTimeUs == C3446C.TIME_UNSET) {
                this.basisTimeUs = this.seeker.getTimeUs(extractorInput.getPosition());
                if (this.forcedFirstSampleTimestampUs != C3446C.TIME_UNSET) {
                    long timeUs = this.seeker.getTimeUs(0);
                    this.basisTimeUs = (this.forcedFirstSampleTimestampUs - timeUs) + this.basisTimeUs;
                }
            }
            this.sampleBytesRemaining = this.synchronizedHeader.frameSize;
        }
        readInt = this.trackOutput.sampleData(extractorInput, this.sampleBytesRemaining, true);
        if (readInt == -1) {
            return -1;
        }
        this.sampleBytesRemaining -= readInt;
        if (this.sampleBytesRemaining > 0) {
            return 0;
        }
        this.trackOutput.sampleMetadata(((this.samplesRead * C3446C.MICROS_PER_SECOND) / ((long) this.synchronizedHeader.sampleRate)) + this.basisTimeUs, 1, this.synchronizedHeader.frameSize, 0, null);
        this.samplesRead += (long) this.synchronizedHeader.samplesPerFrame;
        this.sampleBytesRemaining = 0;
        return 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean synchronize(org.telegram.messenger.exoplayer2.extractor.ExtractorInput r12, boolean r13) {
        /*
        r11 = this;
        r10 = 4;
        r7 = 1;
        r2 = 0;
        if (r13 == 0) goto L_0x003d;
    L_0x0005:
        r0 = 16384; // 0x4000 float:2.2959E-41 double:8.0948E-320;
    L_0x0007:
        r12.resetPeekPosition();
        r4 = r12.getPosition();
        r8 = 0;
        r1 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
        if (r1 != 0) goto L_0x0098;
    L_0x0014:
        r11.peekId3Data(r12);
        r4 = r12.getPeekPosition();
        r1 = (int) r4;
        if (r13 != 0) goto L_0x0021;
    L_0x001e:
        r12.skipFully(r1);
    L_0x0021:
        r3 = r1;
        r4 = r2;
        r5 = r2;
        r1 = r2;
    L_0x0025:
        r6 = r11.scratch;
        r8 = r6.data;
        if (r5 <= 0) goto L_0x0040;
    L_0x002b:
        r6 = r7;
    L_0x002c:
        r6 = r12.peekFully(r8, r2, r10, r6);
        if (r6 != 0) goto L_0x0042;
    L_0x0032:
        if (r13 == 0) goto L_0x0094;
    L_0x0034:
        r0 = r3 + r1;
        r12.skipFully(r0);
    L_0x0039:
        r11.synchronizedHeaderData = r4;
        r2 = r7;
    L_0x003c:
        return r2;
    L_0x003d:
        r0 = 131072; // 0x20000 float:1.83671E-40 double:6.47582E-319;
        goto L_0x0007;
    L_0x0040:
        r6 = r2;
        goto L_0x002c;
    L_0x0042:
        r6 = r11.scratch;
        r6.setPosition(r2);
        r6 = r11.scratch;
        r6 = r6.readInt();
        if (r4 == 0) goto L_0x0056;
    L_0x004f:
        r8 = (long) r4;
        r8 = headersMatch(r6, r8);
        if (r8 == 0) goto L_0x005d;
    L_0x0056:
        r8 = org.telegram.messenger.exoplayer2.extractor.MpegAudioHeader.getFrameSize(r6);
        r9 = -1;
        if (r8 != r9) goto L_0x0081;
    L_0x005d:
        r4 = r1 + 1;
        if (r1 != r0) goto L_0x006c;
    L_0x0061:
        if (r13 != 0) goto L_0x003c;
    L_0x0063:
        r0 = new org.telegram.messenger.exoplayer2.ParserException;
        r1 = "Searched too many bytes.";
        r0.<init>(r1);
        throw r0;
    L_0x006c:
        if (r13 == 0) goto L_0x007a;
    L_0x006e:
        r12.resetPeekPosition();
        r1 = r3 + r4;
        r12.advancePeekPosition(r1);
        r1 = r4;
        r5 = r2;
        r4 = r2;
        goto L_0x0025;
    L_0x007a:
        r12.skipFully(r7);
        r1 = r4;
        r5 = r2;
        r4 = r2;
        goto L_0x0025;
    L_0x0081:
        r5 = r5 + 1;
        if (r5 != r7) goto L_0x0091;
    L_0x0085:
        r4 = r11.synchronizedHeader;
        org.telegram.messenger.exoplayer2.extractor.MpegAudioHeader.populateHeader(r6, r4);
        r4 = r6;
    L_0x008b:
        r6 = r8 + -4;
        r12.advancePeekPosition(r6);
        goto L_0x0025;
    L_0x0091:
        if (r5 != r10) goto L_0x008b;
    L_0x0093:
        goto L_0x0032;
    L_0x0094:
        r12.resetPeekPosition();
        goto L_0x0039;
    L_0x0098:
        r1 = r2;
        r3 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x0025;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.extractor.mp3.Mp3Extractor.synchronize(org.telegram.messenger.exoplayer2.extractor.ExtractorInput, boolean):boolean");
    }

    public void init(ExtractorOutput extractorOutput) {
        this.extractorOutput = extractorOutput;
        this.trackOutput = this.extractorOutput.track(0, 1);
        this.extractorOutput.endTracks();
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) {
        if (this.synchronizedHeaderData == 0) {
            try {
                synchronize(extractorInput, false);
            } catch (EOFException e) {
                return -1;
            }
        }
        if (this.seeker == null) {
            this.seeker = maybeReadSeekFrame(extractorInput);
            if (this.seeker == null || !(this.seeker.isSeekable() || (this.flags & 1) == 0)) {
                this.seeker = getConstantBitrateSeeker(extractorInput);
            }
            this.extractorOutput.seekMap(this.seeker);
            this.trackOutput.format(Format.createAudioSampleFormat(null, this.synchronizedHeader.mimeType, null, -1, 4096, this.synchronizedHeader.channels, this.synchronizedHeader.sampleRate, -1, this.gaplessInfoHolder.encoderDelay, this.gaplessInfoHolder.encoderPadding, null, null, 0, null, (this.flags & 2) != 0 ? null : this.metadata));
        }
        return readSample(extractorInput);
    }

    public void release() {
    }

    public void seek(long j, long j2) {
        this.synchronizedHeaderData = 0;
        this.basisTimeUs = C3446C.TIME_UNSET;
        this.samplesRead = 0;
        this.sampleBytesRemaining = 0;
    }

    public boolean sniff(ExtractorInput extractorInput) {
        return synchronize(extractorInput, true);
    }
}
