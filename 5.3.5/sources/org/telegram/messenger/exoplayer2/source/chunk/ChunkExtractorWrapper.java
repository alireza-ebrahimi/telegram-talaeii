package org.telegram.messenger.exoplayer2.source.chunk;

import android.util.SparseArray;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.DummyTrackOutput;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.SeekMap;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput.CryptoData;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class ChunkExtractorWrapper implements ExtractorOutput {
    private final SparseArray<BindingTrackOutput> bindingTrackOutputs = new SparseArray();
    public final Extractor extractor;
    private boolean extractorInitialized;
    private final Format manifestFormat;
    private Format[] sampleFormats;
    private SeekMap seekMap;
    private TrackOutputProvider trackOutputProvider;

    public interface TrackOutputProvider {
        TrackOutput track(int i, int i2);
    }

    private static final class BindingTrackOutput implements TrackOutput {
        private final int id;
        private final Format manifestFormat;
        public Format sampleFormat;
        private TrackOutput trackOutput;
        private final int type;

        public BindingTrackOutput(int id, int type, Format manifestFormat) {
            this.id = id;
            this.type = type;
            this.manifestFormat = manifestFormat;
        }

        public void bind(TrackOutputProvider trackOutputProvider) {
            if (trackOutputProvider == null) {
                this.trackOutput = new DummyTrackOutput();
                return;
            }
            this.trackOutput = trackOutputProvider.track(this.id, this.type);
            if (this.trackOutput != null) {
                this.trackOutput.format(this.sampleFormat);
            }
        }

        public void format(Format format) {
            this.sampleFormat = format.copyWithManifestFormatInfo(this.manifestFormat);
            this.trackOutput.format(this.sampleFormat);
        }

        public int sampleData(ExtractorInput input, int length, boolean allowEndOfInput) throws IOException, InterruptedException {
            return this.trackOutput.sampleData(input, length, allowEndOfInput);
        }

        public void sampleData(ParsableByteArray data, int length) {
            this.trackOutput.sampleData(data, length);
        }

        public void sampleMetadata(long timeUs, int flags, int size, int offset, CryptoData cryptoData) {
            this.trackOutput.sampleMetadata(timeUs, flags, size, offset, cryptoData);
        }
    }

    public ChunkExtractorWrapper(Extractor extractor, Format manifestFormat) {
        this.extractor = extractor;
        this.manifestFormat = manifestFormat;
    }

    public SeekMap getSeekMap() {
        return this.seekMap;
    }

    public Format[] getSampleFormats() {
        return this.sampleFormats;
    }

    public void init(TrackOutputProvider trackOutputProvider) {
        this.trackOutputProvider = trackOutputProvider;
        if (this.extractorInitialized) {
            this.extractor.seek(0, 0);
            for (int i = 0; i < this.bindingTrackOutputs.size(); i++) {
                ((BindingTrackOutput) this.bindingTrackOutputs.valueAt(i)).bind(trackOutputProvider);
            }
            return;
        }
        this.extractor.init(this);
        this.extractorInitialized = true;
    }

    public TrackOutput track(int id, int type) {
        BindingTrackOutput bindingTrackOutput = (BindingTrackOutput) this.bindingTrackOutputs.get(id);
        if (bindingTrackOutput != null) {
            return bindingTrackOutput;
        }
        Assertions.checkState(this.sampleFormats == null);
        bindingTrackOutput = new BindingTrackOutput(id, type, this.manifestFormat);
        bindingTrackOutput.bind(this.trackOutputProvider);
        this.bindingTrackOutputs.put(id, bindingTrackOutput);
        return bindingTrackOutput;
    }

    public void endTracks() {
        Format[] sampleFormats = new Format[this.bindingTrackOutputs.size()];
        for (int i = 0; i < this.bindingTrackOutputs.size(); i++) {
            sampleFormats[i] = ((BindingTrackOutput) this.bindingTrackOutputs.valueAt(i)).sampleFormat;
        }
        this.sampleFormats = sampleFormats;
    }

    public void seekMap(SeekMap seekMap) {
        this.seekMap = seekMap;
    }
}
