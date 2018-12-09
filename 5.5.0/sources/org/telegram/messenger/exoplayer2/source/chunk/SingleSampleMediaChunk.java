package org.telegram.messenger.exoplayer2.source.chunk;

import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.DefaultExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.util.Util;

public final class SingleSampleMediaChunk extends BaseMediaChunk {
    private volatile int bytesLoaded;
    private volatile boolean loadCanceled;
    private volatile boolean loadCompleted;
    private final Format sampleFormat;
    private final int trackType;

    public SingleSampleMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, Object obj, long j, long j2, int i2, int i3, Format format2) {
        super(dataSource, dataSpec, format, i, obj, j, j2, i2);
        this.trackType = i3;
        this.sampleFormat = format2;
    }

    public long bytesLoaded() {
        return (long) this.bytesLoaded;
    }

    public void cancelLoad() {
        this.loadCanceled = true;
    }

    public boolean isLoadCanceled() {
        return this.loadCanceled;
    }

    public boolean isLoadCompleted() {
        return this.loadCompleted;
    }

    public void load() {
        try {
            long open = this.dataSource.open(this.dataSpec.subrange((long) this.bytesLoaded));
            if (open != -1) {
                open += (long) this.bytesLoaded;
            }
            ExtractorInput defaultExtractorInput = new DefaultExtractorInput(this.dataSource, (long) this.bytesLoaded, open);
            BaseMediaChunkOutput output = getOutput();
            output.setSampleOffsetUs(0);
            TrackOutput track = output.track(0, this.trackType);
            track.format(this.sampleFormat);
            for (int i = 0; i != -1; i = track.sampleData(defaultExtractorInput, Integer.MAX_VALUE, true)) {
                this.bytesLoaded = i + this.bytesLoaded;
            }
            track.sampleMetadata(this.startTimeUs, 1, this.bytesLoaded, 0, null);
            this.loadCompleted = true;
        } finally {
            Util.closeQuietly(this.dataSource);
        }
    }
}
