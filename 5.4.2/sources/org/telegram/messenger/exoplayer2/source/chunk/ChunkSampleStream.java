package org.telegram.messenger.exoplayer2.source.chunk;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.FormatHolder;
import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;
import org.telegram.messenger.exoplayer2.source.AdaptiveMediaSourceEventListener.EventDispatcher;
import org.telegram.messenger.exoplayer2.source.SampleQueue;
import org.telegram.messenger.exoplayer2.source.SampleStream;
import org.telegram.messenger.exoplayer2.source.SequenceableLoader;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.upstream.Loader;
import org.telegram.messenger.exoplayer2.upstream.Loader.Callback;
import org.telegram.messenger.exoplayer2.upstream.Loader.ReleaseCallback;
import org.telegram.messenger.exoplayer2.util.Assertions;

public class ChunkSampleStream<T extends ChunkSource> implements SampleStream, SequenceableLoader, Callback<Chunk>, ReleaseCallback {
    private final SequenceableLoader.Callback<ChunkSampleStream<T>> callback;
    private final T chunkSource;
    private final SampleQueue[] embeddedSampleQueues;
    private final int[] embeddedTrackTypes;
    private final boolean[] embeddedTracksSelected;
    private final EventDispatcher eventDispatcher;
    long lastSeekPositionUs;
    private final Loader loader = new Loader("Loader:ChunkSampleStream");
    boolean loadingFinished;
    private final BaseMediaChunkOutput mediaChunkOutput;
    private final LinkedList<BaseMediaChunk> mediaChunks = new LinkedList();
    private final int minLoadableRetryCount;
    private final ChunkHolder nextChunkHolder = new ChunkHolder();
    private long pendingResetPositionUs;
    private Format primaryDownstreamTrackFormat;
    private final SampleQueue primarySampleQueue;
    private final int primaryTrackType;
    private final List<BaseMediaChunk> readOnlyMediaChunks = Collections.unmodifiableList(this.mediaChunks);

    public final class EmbeddedSampleStream implements SampleStream {
        private final int index;
        public final ChunkSampleStream<T> parent;
        private final SampleQueue sampleQueue;

        public EmbeddedSampleStream(ChunkSampleStream<T> chunkSampleStream, SampleQueue sampleQueue, int i) {
            this.parent = chunkSampleStream;
            this.sampleQueue = sampleQueue;
            this.index = i;
        }

        public boolean isReady() {
            return ChunkSampleStream.this.loadingFinished || (!ChunkSampleStream.this.isPendingReset() && this.sampleQueue.hasNextSample());
        }

        public void maybeThrowError() {
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
            if (ChunkSampleStream.this.isPendingReset()) {
                return -3;
            }
            return this.sampleQueue.read(formatHolder, decoderInputBuffer, z, ChunkSampleStream.this.loadingFinished, ChunkSampleStream.this.lastSeekPositionUs);
        }

        public void release() {
            Assertions.checkState(ChunkSampleStream.this.embeddedTracksSelected[this.index]);
            ChunkSampleStream.this.embeddedTracksSelected[this.index] = false;
        }

        public void skipData(long j) {
            if (!ChunkSampleStream.this.loadingFinished || j <= this.sampleQueue.getLargestQueuedTimestampUs()) {
                this.sampleQueue.advanceTo(j, true, true);
            } else {
                this.sampleQueue.advanceToEnd();
            }
        }
    }

    public ChunkSampleStream(int i, int[] iArr, T t, SequenceableLoader.Callback<ChunkSampleStream<T>> callback, Allocator allocator, long j, int i2, EventDispatcher eventDispatcher) {
        int i3 = 0;
        this.primaryTrackType = i;
        this.embeddedTrackTypes = iArr;
        this.chunkSource = t;
        this.callback = callback;
        this.eventDispatcher = eventDispatcher;
        this.minLoadableRetryCount = i2;
        int length = iArr == null ? 0 : iArr.length;
        this.embeddedSampleQueues = new SampleQueue[length];
        this.embeddedTracksSelected = new boolean[length];
        int[] iArr2 = new int[(length + 1)];
        SampleQueue[] sampleQueueArr = new SampleQueue[(length + 1)];
        this.primarySampleQueue = new SampleQueue(allocator);
        iArr2[0] = i;
        sampleQueueArr[0] = this.primarySampleQueue;
        while (i3 < length) {
            SampleQueue sampleQueue = new SampleQueue(allocator);
            this.embeddedSampleQueues[i3] = sampleQueue;
            sampleQueueArr[i3 + 1] = sampleQueue;
            iArr2[i3 + 1] = iArr[i3];
            i3++;
        }
        this.mediaChunkOutput = new BaseMediaChunkOutput(iArr2, sampleQueueArr);
        this.pendingResetPositionUs = j;
        this.lastSeekPositionUs = j;
    }

    private void discardDownstreamMediaChunks(int i) {
        if (!this.mediaChunks.isEmpty()) {
            while (this.mediaChunks.size() > 1 && ((BaseMediaChunk) this.mediaChunks.get(1)).getFirstSampleIndex(0) <= i) {
                this.mediaChunks.removeFirst();
            }
            BaseMediaChunk baseMediaChunk = (BaseMediaChunk) this.mediaChunks.getFirst();
            Format format = baseMediaChunk.trackFormat;
            if (!format.equals(this.primaryDownstreamTrackFormat)) {
                this.eventDispatcher.downstreamFormatChanged(this.primaryTrackType, format, baseMediaChunk.trackSelectionReason, baseMediaChunk.trackSelectionData, baseMediaChunk.startTimeUs);
            }
            this.primaryDownstreamTrackFormat = format;
        }
    }

    private boolean discardUpstreamMediaChunks(int i) {
        if (this.mediaChunks.size() <= i) {
            return false;
        }
        BaseMediaChunk baseMediaChunk;
        long j;
        long j2 = ((BaseMediaChunk) this.mediaChunks.getLast()).endTimeUs;
        do {
            baseMediaChunk = (BaseMediaChunk) this.mediaChunks.removeLast();
            j = baseMediaChunk.startTimeUs;
        } while (this.mediaChunks.size() > i);
        this.primarySampleQueue.discardUpstreamSamples(baseMediaChunk.getFirstSampleIndex(0));
        for (int i2 = 0; i2 < this.embeddedSampleQueues.length; i2++) {
            this.embeddedSampleQueues[i2].discardUpstreamSamples(baseMediaChunk.getFirstSampleIndex(i2 + 1));
        }
        this.loadingFinished = false;
        this.eventDispatcher.upstreamDiscarded(this.primaryTrackType, j, j2);
        return true;
    }

    private boolean isMediaChunk(Chunk chunk) {
        return chunk instanceof BaseMediaChunk;
    }

    private void maybeDiscardUpstream(long j) {
        discardUpstreamMediaChunks(Math.max(1, this.chunkSource.getPreferredQueueSize(j, this.readOnlyMediaChunks)));
    }

    public boolean continueLoading(long j) {
        if (this.loadingFinished || this.loader.isLoading()) {
            return false;
        }
        MediaChunk mediaChunk;
        ChunkSource chunkSource = this.chunkSource;
        if (this.mediaChunks.isEmpty()) {
            mediaChunk = null;
        } else {
            BaseMediaChunk baseMediaChunk = (BaseMediaChunk) this.mediaChunks.getLast();
        }
        if (this.pendingResetPositionUs != C3446C.TIME_UNSET) {
            j = this.pendingResetPositionUs;
        }
        chunkSource.getNextChunk(mediaChunk, j, this.nextChunkHolder);
        boolean z = this.nextChunkHolder.endOfStream;
        Chunk chunk = this.nextChunkHolder.chunk;
        this.nextChunkHolder.clear();
        if (z) {
            this.pendingResetPositionUs = C3446C.TIME_UNSET;
            this.loadingFinished = true;
            return true;
        } else if (chunk == null) {
            return false;
        } else {
            if (isMediaChunk(chunk)) {
                this.pendingResetPositionUs = C3446C.TIME_UNSET;
                baseMediaChunk = (BaseMediaChunk) chunk;
                baseMediaChunk.init(this.mediaChunkOutput);
                this.mediaChunks.add(baseMediaChunk);
            }
            this.eventDispatcher.loadStarted(chunk.dataSpec, chunk.type, this.primaryTrackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, this.loader.startLoading(chunk, this, this.minLoadableRetryCount));
            return true;
        }
    }

    public void discardEmbeddedTracksTo(long j) {
        for (int i = 0; i < this.embeddedSampleQueues.length; i++) {
            this.embeddedSampleQueues[i].discardTo(j, true, this.embeddedTracksSelected[i]);
        }
    }

    public long getBufferedPositionUs() {
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        long j = this.lastSeekPositionUs;
        BaseMediaChunk baseMediaChunk = (BaseMediaChunk) this.mediaChunks.getLast();
        if (!baseMediaChunk.isLoadCompleted()) {
            baseMediaChunk = this.mediaChunks.size() > 1 ? (BaseMediaChunk) this.mediaChunks.get(this.mediaChunks.size() - 2) : null;
        }
        return Math.max(baseMediaChunk != null ? Math.max(j, baseMediaChunk.endTimeUs) : j, this.primarySampleQueue.getLargestQueuedTimestampUs());
    }

    public T getChunkSource() {
        return this.chunkSource;
    }

    public long getNextLoadPositionUs() {
        return isPendingReset() ? this.pendingResetPositionUs : this.loadingFinished ? Long.MIN_VALUE : ((BaseMediaChunk) this.mediaChunks.getLast()).endTimeUs;
    }

    boolean isPendingReset() {
        return this.pendingResetPositionUs != C3446C.TIME_UNSET;
    }

    public boolean isReady() {
        return this.loadingFinished || (!isPendingReset() && this.primarySampleQueue.hasNextSample());
    }

    public void maybeThrowError() {
        this.loader.maybeThrowError();
        if (!this.loader.isLoading()) {
            this.chunkSource.maybeThrowError();
        }
    }

    public void onLoadCanceled(Chunk chunk, long j, long j2, boolean z) {
        this.eventDispatcher.loadCanceled(chunk.dataSpec, chunk.type, this.primaryTrackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, j, j2, chunk.bytesLoaded());
        if (!z) {
            this.primarySampleQueue.reset();
            for (SampleQueue reset : this.embeddedSampleQueues) {
                reset.reset();
            }
            this.callback.onContinueLoadingRequested(this);
        }
    }

    public void onLoadCompleted(Chunk chunk, long j, long j2) {
        this.chunkSource.onChunkLoadCompleted(chunk);
        this.eventDispatcher.loadCompleted(chunk.dataSpec, chunk.type, this.primaryTrackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, j, j2, chunk.bytesLoaded());
        this.callback.onContinueLoadingRequested(this);
    }

    public int onLoadError(Chunk chunk, long j, long j2, IOException iOException) {
        long bytesLoaded = chunk.bytesLoaded();
        boolean isMediaChunk = isMediaChunk(chunk);
        boolean z = !isMediaChunk || bytesLoaded == 0 || this.mediaChunks.size() > 1;
        boolean z2 = false;
        if (this.chunkSource.onChunkLoadError(chunk, z, iOException)) {
            z2 = true;
            if (isMediaChunk) {
                Chunk chunk2 = (BaseMediaChunk) this.mediaChunks.removeLast();
                Assertions.checkState(chunk2 == chunk);
                this.primarySampleQueue.discardUpstreamSamples(chunk2.getFirstSampleIndex(0));
                for (int i = 0; i < this.embeddedSampleQueues.length; i++) {
                    this.embeddedSampleQueues[i].discardUpstreamSamples(chunk2.getFirstSampleIndex(i + 1));
                }
                if (this.mediaChunks.isEmpty()) {
                    this.pendingResetPositionUs = this.lastSeekPositionUs;
                }
            }
        }
        this.eventDispatcher.loadError(chunk.dataSpec, chunk.type, this.primaryTrackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, j, j2, bytesLoaded, iOException, z2);
        if (!z2) {
            return 0;
        }
        this.callback.onContinueLoadingRequested(this);
        return 2;
    }

    public void onLoaderReleased() {
        this.primarySampleQueue.reset();
        for (SampleQueue reset : this.embeddedSampleQueues) {
            reset.reset();
        }
    }

    public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
        if (isPendingReset()) {
            return -3;
        }
        discardDownstreamMediaChunks(this.primarySampleQueue.getReadIndex());
        int read = this.primarySampleQueue.read(formatHolder, decoderInputBuffer, z, this.loadingFinished, this.lastSeekPositionUs);
        if (read != -4) {
            return read;
        }
        this.primarySampleQueue.discardToRead();
        return read;
    }

    public void release() {
        if (!this.loader.release(this)) {
            this.primarySampleQueue.discardToEnd();
            for (SampleQueue discardToEnd : this.embeddedSampleQueues) {
                discardToEnd.discardToEnd();
            }
        }
    }

    public void seekToUs(long j) {
        boolean z;
        SampleQueue[] sampleQueueArr;
        int length;
        int i;
        int i2 = 0;
        this.lastSeekPositionUs = j;
        if (!isPendingReset()) {
            if (this.primarySampleQueue.advanceTo(j, true, j < getNextLoadPositionUs())) {
                z = true;
                if (z) {
                    this.pendingResetPositionUs = j;
                    this.loadingFinished = false;
                    this.mediaChunks.clear();
                    if (this.loader.isLoading()) {
                        this.primarySampleQueue.reset();
                        sampleQueueArr = this.embeddedSampleQueues;
                        length = sampleQueueArr.length;
                        while (i2 < length) {
                            sampleQueueArr[i2].reset();
                            i2++;
                        }
                        return;
                    }
                    this.loader.cancelLoading();
                    return;
                }
                discardDownstreamMediaChunks(this.primarySampleQueue.getReadIndex());
                this.primarySampleQueue.discardToRead();
                for (SampleQueue sampleQueue : this.embeddedSampleQueues) {
                    sampleQueue.rewind();
                    sampleQueue.discardTo(j, true, false);
                }
            }
        }
        z = false;
        if (z) {
            this.pendingResetPositionUs = j;
            this.loadingFinished = false;
            this.mediaChunks.clear();
            if (this.loader.isLoading()) {
                this.primarySampleQueue.reset();
                sampleQueueArr = this.embeddedSampleQueues;
                length = sampleQueueArr.length;
                while (i2 < length) {
                    sampleQueueArr[i2].reset();
                    i2++;
                }
                return;
            }
            this.loader.cancelLoading();
            return;
        }
        discardDownstreamMediaChunks(this.primarySampleQueue.getReadIndex());
        this.primarySampleQueue.discardToRead();
        for (i = 0; i < r4; i++) {
            sampleQueue.rewind();
            sampleQueue.discardTo(j, true, false);
        }
    }

    public EmbeddedSampleStream selectEmbeddedTrack(long j, int i) {
        boolean z = false;
        for (int i2 = 0; i2 < this.embeddedSampleQueues.length; i2++) {
            if (this.embeddedTrackTypes[i2] == i) {
                if (!this.embeddedTracksSelected[i2]) {
                    z = true;
                }
                Assertions.checkState(z);
                this.embeddedTracksSelected[i2] = true;
                this.embeddedSampleQueues[i2].rewind();
                this.embeddedSampleQueues[i2].advanceTo(j, true, true);
                return new EmbeddedSampleStream(this, this.embeddedSampleQueues[i2], i2);
            }
        }
        throw new IllegalStateException();
    }

    public void skipData(long j) {
        if (!this.loadingFinished || j <= this.primarySampleQueue.getLargestQueuedTimestampUs()) {
            this.primarySampleQueue.advanceTo(j, true, true);
        } else {
            this.primarySampleQueue.advanceToEnd();
        }
        this.primarySampleQueue.discardToRead();
    }
}
