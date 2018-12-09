package org.telegram.messenger.exoplayer2.extractor.wav;

import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorsFactory;
import org.telegram.messenger.exoplayer2.extractor.PositionHolder;
import org.telegram.messenger.exoplayer2.extractor.SeekMap;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.util.MimeTypes;

public final class WavExtractor implements Extractor, SeekMap {
    public static final ExtractorsFactory FACTORY = new C34931();
    private static final int MAX_INPUT_SIZE = 32768;
    private int bytesPerFrame;
    private ExtractorOutput extractorOutput;
    private int pendingBytes;
    private TrackOutput trackOutput;
    private WavHeader wavHeader;

    /* renamed from: org.telegram.messenger.exoplayer2.extractor.wav.WavExtractor$1 */
    static class C34931 implements ExtractorsFactory {
        C34931() {
        }

        public Extractor[] createExtractors() {
            return new Extractor[]{new WavExtractor()};
        }
    }

    public long getDurationUs() {
        return this.wavHeader.getDurationUs();
    }

    public long getPosition(long j) {
        return this.wavHeader.getPosition(j);
    }

    public void init(ExtractorOutput extractorOutput) {
        this.extractorOutput = extractorOutput;
        this.trackOutput = extractorOutput.track(0, 1);
        this.wavHeader = null;
        extractorOutput.endTracks();
    }

    public boolean isSeekable() {
        return true;
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) {
        if (this.wavHeader == null) {
            this.wavHeader = WavHeaderReader.peek(extractorInput);
            if (this.wavHeader == null) {
                throw new ParserException("Unsupported or unrecognized wav header.");
            }
            this.trackOutput.format(Format.createAudioSampleFormat(null, MimeTypes.AUDIO_RAW, null, this.wavHeader.getBitrate(), 32768, this.wavHeader.getNumChannels(), this.wavHeader.getSampleRateHz(), this.wavHeader.getEncoding(), null, null, 0, null));
            this.bytesPerFrame = this.wavHeader.getBytesPerFrame();
        }
        if (!this.wavHeader.hasDataBounds()) {
            WavHeaderReader.skipToData(extractorInput, this.wavHeader);
            this.extractorOutput.seekMap(this);
        }
        int sampleData = this.trackOutput.sampleData(extractorInput, 32768 - this.pendingBytes, true);
        if (sampleData != -1) {
            this.pendingBytes += sampleData;
        }
        int i = this.pendingBytes / this.bytesPerFrame;
        if (i > 0) {
            long timeUs = this.wavHeader.getTimeUs(extractorInput.getPosition() - ((long) this.pendingBytes));
            int i2 = i * this.bytesPerFrame;
            this.pendingBytes -= i2;
            this.trackOutput.sampleMetadata(timeUs, 1, i2, this.pendingBytes, null);
        }
        return sampleData == -1 ? -1 : 0;
    }

    public void release() {
    }

    public void seek(long j, long j2) {
        this.pendingBytes = 0;
    }

    public boolean sniff(ExtractorInput extractorInput) {
        return WavHeaderReader.peek(extractorInput) != null;
    }
}
