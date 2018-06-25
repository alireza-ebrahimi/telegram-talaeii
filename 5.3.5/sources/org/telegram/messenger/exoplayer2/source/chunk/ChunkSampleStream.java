package org.telegram.messenger.exoplayer2.source.chunk;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.telegram.messenger.exoplayer2.C0907C;
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

        public EmbeddedSampleStream(ChunkSampleStream<T> parent, SampleQueue sampleQueue, int index) {
            this.parent = parent;
            this.sampleQueue = sampleQueue;
            this.index = index;
        }

        public boolean isReady() {
            return ChunkSampleStream.this.loadingFinished || (!ChunkSampleStream.this.isPendingReset() && this.sampleQueue.hasNextSample());
        }

        public void skipData(long positionUs) {
            if (!ChunkSampleStream.this.loadingFinished || positionUs <= this.sampleQueue.getLargestQueuedTimestampUs()) {
                this.sampleQueue.advanceTo(positionUs, true, true);
            } else {
                this.sampleQueue.advanceToEnd();
            }
        }

        public void maybeThrowError() throws IOException {
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean formatRequired) {
            if (ChunkSampleStream.this.isPendingReset()) {
                return -3;
            }
            return this.sampleQueue.read(formatHolder, buffer, formatRequired, ChunkSampleStream.this.loadingFinished, ChunkSampleStream.this.lastSeekPositionUs);
        }

        public void release() {
            Assertions.checkState(ChunkSampleStream.this.embeddedTracksSelected[this.index]);
            ChunkSampleStream.this.embeddedTracksSelected[this.index] = false;
        }
    }

    public ChunkSampleStream(int primaryTrackType, int[] embeddedTrackTypes, T chunkSource, SequenceableLoader.Callback<ChunkSampleStream<T>> callback, Allocator allocator, long positionUs, int minLoadableRetryCount, EventDispatcher eventDispatcher) {
        this.primaryTrackType = primaryTrackType;
        this.embeddedTrackTypes = embeddedTrackTypes;
        this.chunkSource = chunkSource;
        this.callback = callback;
        this.eventDispatcher = eventDispatcher;
        this.minLoadableRetryCount = minLoadableRetryCount;
        int embeddedTrackCount = embeddedTrackTypes == null ? 0 : embeddedTrackTypes.length;
        this.embeddedSampleQueues = new SampleQueue[embeddedTrackCount];
        this.embeddedTracksSelected = new boolean[embeddedTrackCount];
        int[] trackTypes = new int[(embeddedTrackCount + 1)];
        SampleQueue[] sampleQueues = new SampleQueue[(embeddedTrackCount + 1)];
        this.primarySampleQueue = new SampleQueue(allocator);
        trackTypes[0] = primaryTrackType;
        sampleQueues[0] = this.primarySampleQueue;
        for (int i = 0; i < embeddedTrackCount; i++) {
            SampleQueue sampleQueue = new SampleQueue(allocator);
            this.embeddedSampleQueues[i] = sampleQueue;
            sampleQueues[i + 1] = sampleQueue;
            trackTypes[i + 1] = embeddedTrackTypes[i];
        }
        this.mediaChunkOutput = new BaseMediaChunkOutput(trackTypes, sampleQueues);
        this.pendingResetPositionUs = positionUs;
        this.lastSeekPositionUs = positionUs;
    }

    public void discardEmbeddedTracksTo(long positionUs) {
        for (int i = 0; i < this.embeddedSampleQueues.length; i++) {
            this.embeddedSampleQueues[i].discardTo(positionUs, true, this.embeddedTracksSelected[i]);
        }
    }

    public EmbeddedSampleStream selectEmbeddedTrack(long positionUs, int trackType) {
        for (int i = 0; i < this.embeddedSampleQueues.length; i++) {
            if (this.embeddedTrackTypes[i] == trackType) {
                Assertions.checkState(!this.embeddedTracksSelected[i]);
                this.embeddedTracksSelected[i] = true;
                this.embeddedSampleQueues[i].rewind();
                this.embeddedSampleQueues[i].advanceTo(positionUs, true, true);
                return new EmbeddedSampleStream(this, this.embeddedSampleQueues[i], i);
            }
        }
        throw new IllegalStateException();
    }

    public T getChunkSource() {
        return this.chunkSource;
    }

    public long getBufferedPositionUs() {
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        long bufferedPositionUs = this.lastSeekPositionUs;
        BaseMediaChunk lastMediaChunk = (BaseMediaChunk) this.mediaChunks.getLast();
        BaseMediaChunk lastCompletedMediaChunk = lastMediaChunk.isLoadCompleted() ? lastMediaChunk : this.mediaChunks.size() > 1 ? (BaseMediaChunk) this.mediaChunks.get(this.mediaChunks.size() - 2) : null;
        if (lastCompletedMediaChunk != null) {
            bufferedPositionUs = Math.max(bufferedPositionUs, lastCompletedMediaChunk.endTimeUs);
        }
        return Math.max(bufferedPositionUs, this.primarySampleQueue.getLargestQueuedTimestampUs());
    }

    public void seekToUs(long positionUs) {
        boolean seekInsideBuffer;
        SampleQueue[] sampleQueueArr;
        int length;
        int i;
        int i2 = 0;
        this.lastSeekPositionUs = positionUs;
        if (!isPendingReset()) {
            boolean z;
            SampleQueue sampleQueue = this.primarySampleQueue;
            if (positionUs < getNextLoadPositionUs()) {
                z = true;
            } else {
                z = false;
            }
            if (sampleQueue.advanceTo(positionUs, true, z)) {
                seekInsideBuffer = true;
                if (seekInsideBuffer) {
                    this.pendingResetPositionUs = positionUs;
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
                for (SampleQueue embeddedSampleQueue : this.embeddedSampleQueues) {
                    embeddedSampleQueue.rewind();
                    embeddedSampleQueue.discardTo(positionUs, true, false);
                }
            }
        }
        seekInsideBuffer = false;
        if (seekInsideBuffer) {
            this.pendingResetPositionUs = positionUs;
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
        for (i = 0; i < r6; i++) {
            embeddedSampleQueue.rewind();
            embeddedSampleQueue.discardTo(positionUs, true, false);
        }
    }

    public void release() {
        if (!this.loader.release(this)) {
            this.primarySampleQueue.discardToEnd();
            for (SampleQueue embeddedSampleQueue : this.embeddedSampleQueues) {
                embeddedSampleQueue.discardToEnd();
            }
        }
    }

    public void onLoaderReleased() {
        this.primarySampleQueue.reset();
        for (SampleQueue embeddedSampleQueue : this.embeddedSampleQueues) {
            embeddedSampleQueue.reset();
        }
    }

    public boolean isReady() {
        return this.loadingFinished || (!isPendingReset() && this.primarySampleQueue.hasNextSample());
    }

    public void maybeThrowError() throws IOException {
        this.loader.maybeThrowError();
        if (!this.loader.isLoading()) {
            this.chunkSource.maybeThrowError();
        }
    }

    public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean formatRequired) {
        if (isPendingReset()) {
            return -3;
        }
        discardDownstreamMediaChunks(this.primarySampleQueue.getReadIndex());
        int result = this.primarySampleQueue.read(formatHolder, buffer, formatRequired, this.loadingFinished, this.lastSeekPositionUs);
        if (result != -4) {
            return result;
        }
        this.primarySampleQueue.discardToRead();
        return result;
    }

    public void skipData(long positionUs) {
        if (!this.loadingFinished || positionUs <= this.primarySampleQueue.getLargestQueuedTimestampUs()) {
            this.primarySampleQueue.advanceTo(positionUs, true, true);
        } else {
            this.primarySampleQueue.advanceToEnd();
        }
        this.primarySampleQueue.discardToRead();
    }

    public void onLoadCompleted(Chunk loadable, long elapsedRealtimeMs, long loadDurationMs) {
        this.chunkSource.onChunkLoadCompleted(loadable);
        this.eventDispatcher.loadCompleted(loadable.dataSpec, loadable.type, this.primaryTrackType, loadable.trackFormat, loadable.trackSelectionReason, loadable.trackSelectionData, loadable.startTimeUs, loadable.endTimeUs, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        this.callback.onContinueLoadingRequested(this);
    }

    public void onLoadCanceled(Chunk loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
        this.eventDispatcher.loadCanceled(loadable.dataSpec, loadable.type, this.primaryTrackType, loadable.trackFormat, loadable.trackSelectionReason, loadable.trackSelectionData, loadable.startTimeUs, loadable.endTimeUs, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        if (!released) {
            this.primarySampleQueue.reset();
            for (SampleQueue embeddedSampleQueue : this.embeddedSampleQueues) {
                embeddedSampleQueue.reset();
            }
            this.callback.onContinueLoadingRequested(this);
        }
    }

    public int onLoadError(Chunk loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error) {
        long bytesLoaded = loadable.bytesLoaded();
        boolean isMediaChunk = isMediaChunk(loadable);
        boolean cancelable = !isMediaChunk || bytesLoaded == 0 || this.mediaChunks.size() > 1;
        boolean canceled = false;
        if (this.chunkSource.onChunkLoadError(loadable, cancelable, error)) {
            canceled = true;
            if (isMediaChunk) {
                Chunk removed = (BaseMediaChunk) this.mediaChunks.removeLast();
                Assertions.checkState(removed == loadable);
                this.primarySampleQueue.discardUpstreamSamples(removed.getFirstSampleIndex(0));
                for (int i = 0; i < this.embeddedSampleQueues.length; i++) {
                    this.embeddedSampleQueues[i].discardUpstreamSamples(removed.getFirstSampleIndex(i + 1));
                }
                if (this.mediaChunks.isEmpty()) {
                    this.pendingResetPositionUs = this.lastSeekPositionUs;
                }
            }
        }
        this.eventDispatcher.loadError(loadable.dataSpec, loadable.type, this.primaryTrackType, loadable.trackFormat, loadable.trackSelectionReason, loadable.trackSelectionData, loadable.startTimeUs, loadable.endTimeUs, elapsedRealtimeMs, loadDurationMs, bytesLoaded, error, canceled);
        if (!canceled) {
            return 0;
        }
        this.callback.onContinueLoadingRequested(this);
        return 2;
    }

    public boolean continueLoading(long positionUs) {
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
        if (this.pendingResetPositionUs != C0907C.TIME_UNSET) {
            positionUs = this.pendingResetPositionUs;
        }
        chunkSource.getNextChunk(mediaChunk, positionUs, this.nextChunkHolder);
        boolean endOfStream = this.nextChunkHolder.endOfStream;
        Chunk loadable = this.nextChunkHolder.chunk;
        this.nextChunkHolder.clear();
        if (endOfStream) {
            this.pendingResetPositionUs = C0907C.TIME_UNSET;
            this.loadingFinished = true;
            return true;
        } else if (loadable == null) {
            return false;
        } else {
            if (isMediaChunk(loadable)) {
                this.pendingResetPositionUs = C0907C.TIME_UNSET;
                BaseMediaChunk mediaChunk2 = (BaseMediaChunk) loadable;
                mediaChunk2.init(this.mediaChunkOutput);
                this.mediaChunks.add(mediaChunk2);
            }
            this.eventDispatcher.loadStarted(loadable.dataSpec, loadable.type, this.primaryTrackType, loadable.trackFormat, loadable.trackSelectionReason, loadable.trackSelectionData, loadable.startTimeUs, loadable.endTimeUs, this.loader.startLoading(loadable, this, this.minLoadableRetryCount));
            return true;
        }
    }

    public long getNextLoadPositionUs() {
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        return this.loadingFinished ? Long.MIN_VALUE : ((BaseMediaChunk) this.mediaChunks.getLast()).endTimeUs;
    }

    private void maybeDiscardUpstream(long positionUs) {
        discardUpstreamMediaChunks(Math.max(1, this.chunkSource.getPreferredQueueSize(positionUs, this.readOnlyMediaChunks)));
    }

    private boolean isMediaChunk(Chunk chunk) {
        return chunk instanceof BaseMediaChunk;
    }

    boolean isPendingReset() {
        return this.pendingResetPositionUs != C0907C.TIME_UNSET;
    }

    private void discardDownstreamMediaChunks(int primaryStreamReadIndex) {
        if (!this.mediaChunks.isEmpty()) {
            while (this.mediaChunks.size() > 1 && ((BaseMediaChunk) this.mediaChunks.get(1)).getFirstSampleIndex(0) <= primaryStreamReadIndex) {
                this.mediaChunks.removeFirst();
            }
            BaseMediaChunk currentChunk = (BaseMediaChunk) this.mediaChunks.getFirst();
            Format trackFormat = currentChunk.trackFormat;
            if (!trackFormat.equals(this.primaryDownstreamTrackFormat)) {
                this.eventDispatcher.downstreamFormatChanged(this.primaryTrackType, trackFormat, currentChunk.trackSelectionReason, currentChunk.trackSelectionData, currentChunk.startTimeUs);
            }
            this.primaryDownstreamTrackFormat = trackFormat;
        }
    }

    private boolean discardUpstreamMediaChunks(int queueLength) {
        if (this.mediaChunks.size() <= queueLength) {
            return false;
        }
        BaseMediaChunk removed;
        long startTimeUs;
        long endTimeUs = ((BaseMediaChunk) this.mediaChunks.getLast()).endTimeUs;
        do {
            removed = (BaseMediaChunk) this.mediaChunks.removeLast();
            startTimeUs = removed.startTimeUs;
        } while (this.mediaChunks.size() > queueLength);
        this.primarySampleQueue.discardUpstreamSamples(removed.getFirstSampleIndex(0));
        for (int i = 0; i < this.embeddedSampleQueues.length; i++) {
            this.embeddedSampleQueues[i].discardUpstreamSamples(removed.getFirstSampleIndex(i + 1));
        }
        this.loadingFinished = false;
        this.eventDispatcher.upstreamDiscarded(this.primaryTrackType, startTimeUs, endTimeUs);
        return true;
    }
}
