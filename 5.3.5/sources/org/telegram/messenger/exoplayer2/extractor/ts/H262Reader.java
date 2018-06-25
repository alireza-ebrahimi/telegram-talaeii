package org.telegram.messenger.exoplayer2.extractor.ts;

import android.util.Pair;
import java.util.Arrays;
import java.util.Collections;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class H262Reader implements ElementaryStreamReader {
    private static final double[] FRAME_RATE_VALUES = new double[]{23.976023976023978d, 24.0d, 25.0d, 29.97002997002997d, 30.0d, 50.0d, 59.94005994005994d, 60.0d};
    private static final int START_EXTENSION = 181;
    private static final int START_GROUP = 184;
    private static final int START_PICTURE = 0;
    private static final int START_SEQUENCE_HEADER = 179;
    private final H262Reader$CsdBuffer csdBuffer = new H262Reader$CsdBuffer(128);
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

    public void seek() {
        NalUnitUtil.clearPrefixFlags(this.prefixFlags);
        this.csdBuffer.reset();
        this.totalBytesWritten = 0;
        this.startedFirstSample = false;
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader$TrackIdGenerator idGenerator) {
        idGenerator.generateNewId();
        this.formatId = idGenerator.getFormatId();
        this.output = extractorOutput.track(idGenerator.getTrackId(), 2);
    }

    public void packetStarted(long pesTimeUs, boolean dataAlignmentIndicator) {
        this.pesTimeUs = pesTimeUs;
    }

    public void consume(ParsableByteArray data) {
        int offset = data.getPosition();
        int limit = data.limit();
        byte[] dataArray = data.data;
        this.totalBytesWritten += (long) data.bytesLeft();
        this.output.sampleData(data, data.bytesLeft());
        while (true) {
            int startCodeOffset = NalUnitUtil.findNalUnit(dataArray, offset, limit, this.prefixFlags);
            if (startCodeOffset == limit) {
                break;
            }
            int startCodeValue = data.data[startCodeOffset + 3] & 255;
            if (!this.hasOutputFormat) {
                int lengthToStartCode = startCodeOffset - offset;
                if (lengthToStartCode > 0) {
                    this.csdBuffer.onData(dataArray, offset, startCodeOffset);
                }
                if (this.csdBuffer.onStartCode(startCodeValue, lengthToStartCode < 0 ? -lengthToStartCode : 0)) {
                    Pair<Format, Long> result = parseCsdBuffer(this.csdBuffer, this.formatId);
                    this.output.format((Format) result.first);
                    this.frameDurationUs = ((Long) result.second).longValue();
                    this.hasOutputFormat = true;
                }
            }
            if (startCodeValue == 0 || startCodeValue == START_SEQUENCE_HEADER) {
                int bytesWrittenPastStartCode = limit - startCodeOffset;
                if (this.startedFirstSample && this.sampleHasPicture && this.hasOutputFormat) {
                    this.output.sampleMetadata(this.sampleTimeUs, this.sampleIsKeyframe ? 1 : 0, ((int) (this.totalBytesWritten - this.samplePosition)) - bytesWrittenPastStartCode, bytesWrittenPastStartCode, null);
                }
                if (!this.startedFirstSample || this.sampleHasPicture) {
                    this.samplePosition = this.totalBytesWritten - ((long) bytesWrittenPastStartCode);
                    long j = this.pesTimeUs != C0907C.TIME_UNSET ? this.pesTimeUs : this.startedFirstSample ? this.sampleTimeUs + this.frameDurationUs : 0;
                    this.sampleTimeUs = j;
                    this.sampleIsKeyframe = false;
                    this.pesTimeUs = C0907C.TIME_UNSET;
                    this.startedFirstSample = true;
                }
                this.sampleHasPicture = startCodeValue == 0;
            } else if (startCodeValue == START_GROUP) {
                this.sampleIsKeyframe = true;
            }
            offset = startCodeOffset + 3;
        }
        if (!this.hasOutputFormat) {
            this.csdBuffer.onData(dataArray, offset, limit);
        }
    }

    public void packetFinished() {
    }

    private static Pair<Format, Long> parseCsdBuffer(H262Reader$CsdBuffer csdBuffer, String formatId) {
        byte[] csdData = Arrays.copyOf(csdBuffer.data, csdBuffer.length);
        int secondByte = csdData[5] & 255;
        int width = ((csdData[4] & 255) << 4) | (secondByte >> 4);
        int height = ((secondByte & 15) << 8) | (csdData[6] & 255);
        float pixelWidthHeightRatio = 1.0f;
        switch ((csdData[7] & PsExtractor.VIDEO_STREAM_MASK) >> 4) {
            case 2:
                pixelWidthHeightRatio = ((float) (height * 4)) / ((float) (width * 3));
                break;
            case 3:
                pixelWidthHeightRatio = ((float) (height * 16)) / ((float) (width * 9));
                break;
            case 4:
                pixelWidthHeightRatio = ((float) (height * 121)) / ((float) (width * 100));
                break;
        }
        Format format = Format.createVideoSampleFormat(formatId, MimeTypes.VIDEO_MPEG2, null, -1, -1, width, height, -1.0f, Collections.singletonList(csdData), -1, pixelWidthHeightRatio, null);
        long frameDurationUs = 0;
        int frameRateCodeMinusOne = (csdData[7] & 15) - 1;
        if (frameRateCodeMinusOne >= 0 && frameRateCodeMinusOne < FRAME_RATE_VALUES.length) {
            double frameRate = FRAME_RATE_VALUES[frameRateCodeMinusOne];
            int sequenceExtensionPosition = csdBuffer.sequenceExtensionPosition;
            int frameRateExtensionN = (csdData[sequenceExtensionPosition + 9] & 96) >> 5;
            int frameRateExtensionD = csdData[sequenceExtensionPosition + 9] & 31;
            if (frameRateExtensionN != frameRateExtensionD) {
                frameRate *= (((double) frameRateExtensionN) + 1.0d) / ((double) (frameRateExtensionD + 1));
            }
            frameDurationUs = (long) (1000000.0d / frameRate);
        }
        return Pair.create(format, Long.valueOf(frameDurationUs));
    }
}
