package org.telegram.messenger.exoplayer2.source.chunk;

import android.util.SparseArray;
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

        public BindingTrackOutput(int i, int i2, Format format) {
            this.id = i;
            this.type = i2;
            this.manifestFormat = format;
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

        public int sampleData(ExtractorInput extractorInput, int i, boolean z) {
            return this.trackOutput.sampleData(extractorInput, i, z);
        }

        public void sampleData(ParsableByteArray parsableByteArray, int i) {
            this.trackOutput.sampleData(parsableByteArray, i);
        }

        public void sampleMetadata(long j, int i, int i2, int i3, CryptoData cryptoData) {
            this.trackOutput.sampleMetadata(j, i, i2, i3, cryptoData);
        }
    }

    public ChunkExtractorWrapper(Extractor extractor, Format format) {
        this.extractor = extractor;
        this.manifestFormat = format;
    }

    public void endTracks() {
        Format[] formatArr = new Format[this.bindingTrackOutputs.size()];
        for (int i = 0; i < this.bindingTrackOutputs.size(); i++) {
            formatArr[i] = ((BindingTrackOutput) this.bindingTrackOutputs.valueAt(i)).sampleFormat;
        }
        this.sampleFormats = formatArr;
    }

    public Format[] getSampleFormats() {
        return this.sampleFormats;
    }

    public SeekMap getSeekMap() {
        return this.seekMap;
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

    public void seekMap(SeekMap seekMap) {
        this.seekMap = seekMap;
    }

    public TrackOutput track(int i, int i2) {
        BindingTrackOutput bindingTrackOutput = (BindingTrackOutput) this.bindingTrackOutputs.get(i);
        if (bindingTrackOutput != null) {
            return bindingTrackOutput;
        }
        Assertions.checkState(this.sampleFormats == null);
        TrackOutput bindingTrackOutput2 = new BindingTrackOutput(i, i2, this.manifestFormat);
        bindingTrackOutput2.bind(this.trackOutputProvider);
        this.bindingTrackOutputs.put(i, bindingTrackOutput2);
        return bindingTrackOutput2;
    }
}
