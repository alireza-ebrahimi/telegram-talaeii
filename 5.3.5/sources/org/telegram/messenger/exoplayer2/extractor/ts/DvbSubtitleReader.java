package org.telegram.messenger.exoplayer2.extractor.ts;

import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class DvbSubtitleReader implements ElementaryStreamReader {
    private int bytesToCheck;
    private final TrackOutput[] outputs;
    private int sampleBytesWritten;
    private long sampleTimeUs;
    private final List<TsPayloadReader$DvbSubtitleInfo> subtitleInfos;
    private boolean writingSample;

    public DvbSubtitleReader(List<TsPayloadReader$DvbSubtitleInfo> subtitleInfos) {
        this.subtitleInfos = subtitleInfos;
        this.outputs = new TrackOutput[subtitleInfos.size()];
    }

    public void seek() {
        this.writingSample = false;
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader$TrackIdGenerator idGenerator) {
        for (int i = 0; i < this.outputs.length; i++) {
            TsPayloadReader$DvbSubtitleInfo subtitleInfo = (TsPayloadReader$DvbSubtitleInfo) this.subtitleInfos.get(i);
            idGenerator.generateNewId();
            TrackOutput output = extractorOutput.track(idGenerator.getTrackId(), 3);
            output.format(Format.createImageSampleFormat(idGenerator.getFormatId(), MimeTypes.APPLICATION_DVBSUBS, null, -1, Collections.singletonList(subtitleInfo.initializationData), subtitleInfo.language, null));
            this.outputs[i] = output;
        }
    }

    public void packetStarted(long pesTimeUs, boolean dataAlignmentIndicator) {
        if (dataAlignmentIndicator) {
            this.writingSample = true;
            this.sampleTimeUs = pesTimeUs;
            this.sampleBytesWritten = 0;
            this.bytesToCheck = 2;
        }
    }

    public void packetFinished() {
        if (this.writingSample) {
            for (TrackOutput output : this.outputs) {
                output.sampleMetadata(this.sampleTimeUs, 1, this.sampleBytesWritten, 0, null);
            }
            this.writingSample = false;
        }
    }

    public void consume(ParsableByteArray data) {
        int i = 0;
        if (!this.writingSample) {
            return;
        }
        if (this.bytesToCheck == 2 && !checkNextByte(data, 32)) {
            return;
        }
        if (this.bytesToCheck != 1 || checkNextByte(data, 0)) {
            int dataPosition = data.getPosition();
            int bytesAvailable = data.bytesLeft();
            TrackOutput[] trackOutputArr = this.outputs;
            int length = trackOutputArr.length;
            while (i < length) {
                TrackOutput output = trackOutputArr[i];
                data.setPosition(dataPosition);
                output.sampleData(data, bytesAvailable);
                i++;
            }
            this.sampleBytesWritten += bytesAvailable;
        }
    }

    private boolean checkNextByte(ParsableByteArray data, int expectedValue) {
        if (data.bytesLeft() == 0) {
            return false;
        }
        if (data.readUnsignedByte() != expectedValue) {
            this.writingSample = false;
        }
        this.bytesToCheck--;
        return this.writingSample;
    }
}
