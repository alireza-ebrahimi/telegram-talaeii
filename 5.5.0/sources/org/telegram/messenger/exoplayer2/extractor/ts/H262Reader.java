package org.telegram.messenger.exoplayer2.extractor.ts;

import android.util.Pair;
import java.util.Arrays;
import java.util.Collections;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.extractor.ts.TsPayloadReader.TrackIdGenerator;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class H262Reader implements ElementaryStreamReader {
    private static final double[] FRAME_RATE_VALUES = new double[]{23.976023976023978d, 24.0d, 25.0d, 29.97002997002997d, 30.0d, 50.0d, 59.94005994005994d, 60.0d};
    private static final int START_EXTENSION = 181;
    private static final int START_GROUP = 184;
    private static final int START_PICTURE = 0;
    private static final int START_SEQUENCE_HEADER = 179;
    private final CsdBuffer csdBuffer = new CsdBuffer(128);
    private String formatId;
    private long frameDurationUs;
    private boolean hasOutputFormat;
    private TrackOutput output;
    private long pesTimeUs;
    private final boolean[] prefixFlags = new boolean[4];
    private boolean sampleHasPicture;
    private boolean sampleIsKeyframe;
    private long samplePosition;
    private long sampleTimeUs;
    private boolean startedFirstSample;
    private long totalBytesWritten;

    private static final class CsdBuffer {
        private static final byte[] START_CODE = new byte[]{(byte) 0, (byte) 0, (byte) 1};
        public byte[] data;
        private boolean isFilling;
        public int length;
        public int sequenceExtensionPosition;

        public CsdBuffer(int i) {
            this.data = new byte[i];
        }

        public void onData(byte[] bArr, int i, int i2) {
            if (this.isFilling) {
                int i3 = i2 - i;
                if (this.data.length < this.length + i3) {
                    this.data = Arrays.copyOf(this.data, (this.length + i3) * 2);
                }
                System.arraycopy(bArr, i, this.data, this.length, i3);
                this.length = i3 + this.length;
            }
        }

        public boolean onStartCode(int i, int i2) {
            if (this.isFilling) {
                this.length -= i2;
                if (this.sequenceExtensionPosition == 0 && i == H262Reader.START_EXTENSION) {
                    this.sequenceExtensionPosition = this.length;
                } else {
                    this.isFilling = false;
                    return true;
                }
            } else if (i == H262Reader.START_SEQUENCE_HEADER) {
                this.isFilling = true;
            }
            onData(START_CODE, 0, START_CODE.length);
            return false;
        }

        public void reset() {
            this.isFilling = false;
            this.length = 0;
            this.sequenceExtensionPosition = 0;
        }
    }

    private static Pair<Format, Long> parseCsdBuffer(CsdBuffer csdBuffer, String str) {
        Object copyOf = Arrays.copyOf(csdBuffer.data, csdBuffer.length);
        int i = copyOf[5] & 255;
        int i2 = i >> 4;
        i2 |= (copyOf[4] & 255) << 4;
        int i3 = ((i & 15) << 8) | (copyOf[6] & 255);
        float f = 1.0f;
        switch ((copyOf[7] & PsExtractor.VIDEO_STREAM_MASK) >> 4) {
            case 2:
                f = ((float) (i3 * 4)) / ((float) (i2 * 3));
                break;
            case 3:
                f = ((float) (i3 * 16)) / ((float) (i2 * 9));
                break;
            case 4:
                f = ((float) (i3 * 121)) / ((float) (i2 * 100));
                break;
        }
        Format createVideoSampleFormat = Format.createVideoSampleFormat(str, MimeTypes.VIDEO_MPEG2, null, -1, -1, i2, i3, -1.0f, Collections.singletonList(copyOf), -1, f, null);
        long j = 0;
        int i4 = (copyOf[7] & 15) - 1;
        if (i4 >= 0 && i4 < FRAME_RATE_VALUES.length) {
            double d = FRAME_RATE_VALUES[i4];
            i4 = csdBuffer.sequenceExtensionPosition;
            int i5 = (copyOf[i4 + 9] & 96) >> 5;
            i4 = copyOf[i4 + 9] & 31;
            if (i5 != i4) {
                d *= (((double) i5) + 1.0d) / ((double) (i4 + 1));
            }
            j = (long) (1000000.0d / d);
        }
        return Pair.create(createVideoSampleFormat, Long.valueOf(j));
    }

    public void consume(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        byte[] bArr = parsableByteArray.data;
        this.totalBytesWritten += (long) parsableByteArray.bytesLeft();
        this.output.sampleData(parsableByteArray, parsableByteArray.bytesLeft());
        while (true) {
            int findNalUnit = NalUnitUtil.findNalUnit(bArr, position, limit, this.prefixFlags);
            if (findNalUnit == limit) {
                break;
            }
            int i = parsableByteArray.data[findNalUnit + 3] & 255;
            if (!this.hasOutputFormat) {
                int i2 = findNalUnit - position;
                if (i2 > 0) {
                    this.csdBuffer.onData(bArr, position, findNalUnit);
                }
                if (this.csdBuffer.onStartCode(i, i2 < 0 ? -i2 : 0)) {
                    Pair parseCsdBuffer = parseCsdBuffer(this.csdBuffer, this.formatId);
                    this.output.format((Format) parseCsdBuffer.first);
                    this.frameDurationUs = ((Long) parseCsdBuffer.second).longValue();
                    this.hasOutputFormat = true;
                }
            }
            if (i == 0 || i == START_SEQUENCE_HEADER) {
                int i3 = limit - findNalUnit;
                if (this.startedFirstSample && this.sampleHasPicture && this.hasOutputFormat) {
                    this.output.sampleMetadata(this.sampleTimeUs, this.sampleIsKeyframe ? 1 : 0, ((int) (this.totalBytesWritten - this.samplePosition)) - i3, i3, null);
                }
                if (!this.startedFirstSample || this.sampleHasPicture) {
                    this.samplePosition = this.totalBytesWritten - ((long) i3);
                    long j = this.pesTimeUs != C3446C.TIME_UNSET ? this.pesTimeUs : this.startedFirstSample ? this.sampleTimeUs + this.frameDurationUs : 0;
                    this.sampleTimeUs = j;
                    this.sampleIsKeyframe = false;
                    this.pesTimeUs = C3446C.TIME_UNSET;
                    this.startedFirstSample = true;
                }
                this.sampleHasPicture = i == 0;
            } else if (i == START_GROUP) {
                this.sampleIsKeyframe = true;
            }
            position = findNalUnit + 3;
        }
        if (!this.hasOutputFormat) {
            this.csdBuffer.onData(bArr, position, limit);
        }
    }

    public void createTracks(ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.formatId = trackIdGenerator.getFormatId();
        this.output = extractorOutput.track(trackIdGenerator.getTrackId(), 2);
    }

    public void packetFinished() {
    }

    public void packetStarted(long j, boolean z) {
        this.pesTimeUs = j;
    }

    public void seek() {
        NalUnitUtil.clearPrefixFlags(this.prefixFlags);
        this.csdBuffer.reset();
        this.totalBytesWritten = 0;
        this.startedFirstSample = false;
    }
}
