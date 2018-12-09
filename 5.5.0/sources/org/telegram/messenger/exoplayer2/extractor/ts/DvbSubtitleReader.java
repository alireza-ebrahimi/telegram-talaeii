package org.telegram.messenger.exoplayer2.extractor.ts;

import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.extractor.ts.TsPayloadReader.DvbSubtitleInfo;
import org.telegram.messenger.exoplayer2.extractor.ts.TsPayloadReader.TrackIdGenerator;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class DvbSubtitleReader implements ElementaryStreamReader {
    private int bytesToCheck;
    private final TrackOutput[] outputs;
    private int sampleBytesWritten;
    private long sampleTimeUs;
    private final List<DvbSubtitleInfo> subtitleInfos;
    private boolean writingSample;

    public DvbSubtitleReader(List<DvbSubtitleInfo> list) {
        this.subtitleInfos = list;
        this.outputs = new TrackOutput[list.size()];
    }

    private boolean checkNextByte(ParsableByteArray parsableByteArray, int i) {
        if (parsableByteArray.bytesLeft() == 0) {
            return false;
        }
        if (parsableByteArray.readUnsignedByte() != i) {
            this.writingSample = false;
        }
        this.bytesToCheck--;
        return this.writingSample;
    }

    public void consume(ParsableByteArray parsableByteArray) {
        int i = 0;
        if (!this.writingSample) {
            return;
        }
        if (this.bytesToCheck == 2 && !checkNextByte(parsableByteArray, 32)) {
            return;
        }
        if (this.bytesToCheck != 1 || checkNextByte(parsableByteArray, 0)) {
            int position = parsableByteArray.getPosition();
            int bytesLeft = parsableByteArray.bytesLeft();
            TrackOutput[] trackOutputArr = this.outputs;
            int length = trackOutputArr.length;
            while (i < length) {
                TrackOutput trackOutput = trackOutputArr[i];
                parsableByteArray.setPosition(position);
                trackOutput.sampleData(parsableByteArray, bytesLeft);
                i++;
            }
            this.sampleBytesWritten += bytesLeft;
        }
    }

    public void createTracks(ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator) {
        for (int i = 0; i < this.outputs.length; i++) {
            DvbSubtitleInfo dvbSubtitleInfo = (DvbSubtitleInfo) this.subtitleInfos.get(i);
            trackIdGenerator.generateNewId();
            TrackOutput track = extractorOutput.track(trackIdGenerator.getTrackId(), 3);
            track.format(Format.createImageSampleFormat(trackIdGenerator.getFormatId(), MimeTypes.APPLICATION_DVBSUBS, null, -1, Collections.singletonList(dvbSubtitleInfo.initializationData), dvbSubtitleInfo.language, null));
            this.outputs[i] = track;
        }
    }

    public void packetFinished() {
        if (this.writingSample) {
            for (TrackOutput sampleMetadata : this.outputs) {
                sampleMetadata.sampleMetadata(this.sampleTimeUs, 1, this.sampleBytesWritten, 0, null);
            }
            this.writingSample = false;
        }
    }

    public void packetStarted(long j, boolean z) {
        if (z) {
            this.writingSample = true;
            this.sampleTimeUs = j;
            this.sampleBytesWritten = 0;
            this.bytesToCheck = 2;
        }
    }

    public void seek() {
        this.writingSample = false;
    }
}
