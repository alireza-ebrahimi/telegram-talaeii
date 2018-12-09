package org.telegram.messenger.exoplayer2.source.hls;

import android.os.Handler;
import android.text.TextUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.FormatHolder;
import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.SeekMap;
import org.telegram.messenger.exoplayer2.source.AdaptiveMediaSourceEventListener.EventDispatcher;
import org.telegram.messenger.exoplayer2.source.SampleQueue;
import org.telegram.messenger.exoplayer2.source.SampleQueue.UpstreamFormatChangedListener;
import org.telegram.messenger.exoplayer2.source.SampleStream;
import org.telegram.messenger.exoplayer2.source.SequenceableLoader;
import org.telegram.messenger.exoplayer2.source.TrackGroup;
import org.telegram.messenger.exoplayer2.source.TrackGroupArray;
import org.telegram.messenger.exoplayer2.source.chunk.Chunk;
import org.telegram.messenger.exoplayer2.source.hls.HlsChunkSource.HlsChunkHolder;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsMasterPlaylist.HlsUrl;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.upstream.Loader;
import org.telegram.messenger.exoplayer2.upstream.Loader.ReleaseCallback;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.Util;

final class HlsSampleStreamWrapper implements ExtractorOutput, UpstreamFormatChangedListener, SequenceableLoader, org.telegram.messenger.exoplayer2.upstream.Loader.Callback<Chunk>, ReleaseCallback {
    private static final int PRIMARY_TYPE_AUDIO = 2;
    private static final int PRIMARY_TYPE_NONE = 0;
    private static final int PRIMARY_TYPE_TEXT = 1;
    private static final int PRIMARY_TYPE_VIDEO = 3;
    private final Allocator allocator;
    private final Callback callback;
    private final HlsChunkSource chunkSource;
    private Format downstreamTrackFormat;
    private int enabledTrackCount;
    private final EventDispatcher eventDispatcher;
    private final Handler handler = new Handler();
    private boolean haveAudioVideoTrackGroups;
    private long lastSeekPositionUs;
    private final Loader loader = new Loader("Loader:HlsSampleStreamWrapper");
    private boolean loadingFinished;
    private final Runnable maybeFinishPrepareRunnable = new C35311();
    private final LinkedList<HlsMediaChunk> mediaChunks = new LinkedList();
    private final int minLoadableRetryCount;
    private final Format muxedAudioFormat;
    private final HlsChunkHolder nextChunkHolder = new HlsChunkHolder();
    private long pendingResetPositionUs;
    private boolean pendingResetUpstreamFormats;
    private boolean prepared;
    private int primaryTrackGroupIndex;
    private boolean released;
    private int[] sampleQueueTrackIds = new int[0];
    private SampleQueue[] sampleQueues = new SampleQueue[0];
    private boolean sampleQueuesBuilt;
    private boolean seenFirstTrackSelection;
    private boolean[] trackGroupEnabledStates;
    private boolean[] trackGroupIsAudioVideoFlags;
    private TrackGroupArray trackGroups;
    private final int trackType;
    private int upstreamChunkUid;

    public interface Callback extends org.telegram.messenger.exoplayer2.source.SequenceableLoader.Callback<HlsSampleStreamWrapper> {
        void onPlaylistRefreshRequired(HlsUrl hlsUrl);

        void onPrepared();
    }

    /* renamed from: org.telegram.messenger.exoplayer2.source.hls.HlsSampleStreamWrapper$1 */
    class C35311 implements Runnable {
        C35311() {
        }

        public void run() {
            HlsSampleStreamWrapper.this.maybeFinishPrepare();
        }
    }

    public HlsSampleStreamWrapper(int i, Callback callback, HlsChunkSource hlsChunkSource, Allocator allocator, long j, Format format, int i2, EventDispatcher eventDispatcher) {
        this.trackType = i;
        this.callback = callback;
        this.chunkSource = hlsChunkSource;
        this.allocator = allocator;
        this.muxedAudioFormat = format;
        this.minLoadableRetryCount = i2;
        this.eventDispatcher = eventDispatcher;
        this.lastSeekPositionUs = j;
        this.pendingResetPositionUs = j;
    }

    private void buildTracks() {
        int length = this.sampleQueues.length;
        int i = 0;
        int i2 = -1;
        int i3 = 0;
        while (i < length) {
            String str = this.sampleQueues[i].getUpstreamFormat().sampleMimeType;
            int i4 = MimeTypes.isVideo(str) ? 3 : MimeTypes.isAudio(str) ? 2 : MimeTypes.isText(str) ? 1 : 0;
            if (i4 > i3) {
                i2 = i;
            } else if (i4 != i3 || i2 == -1) {
                i4 = i3;
            } else {
                i2 = -1;
                i4 = i3;
            }
            i++;
            i3 = i4;
        }
        TrackGroup trackGroup = this.chunkSource.getTrackGroup();
        int i5 = trackGroup.length;
        this.primaryTrackGroupIndex = -1;
        this.trackGroupEnabledStates = new boolean[length];
        this.trackGroupIsAudioVideoFlags = new boolean[length];
        TrackGroup[] trackGroupArr = new TrackGroup[length];
        for (int i6 = 0; i6 < length; i6++) {
            Format upstreamFormat = this.sampleQueues[i6].getUpstreamFormat();
            String str2 = upstreamFormat.sampleMimeType;
            boolean z = MimeTypes.isVideo(str2) || MimeTypes.isAudio(str2);
            this.trackGroupIsAudioVideoFlags[i6] = z;
            this.haveAudioVideoTrackGroups = z | this.haveAudioVideoTrackGroups;
            if (i6 == i2) {
                Format[] formatArr = new Format[i5];
                for (i = 0; i < i5; i++) {
                    formatArr[i] = deriveFormat(trackGroup.getFormat(i), upstreamFormat);
                }
                trackGroupArr[i6] = new TrackGroup(formatArr);
                this.primaryTrackGroupIndex = i6;
            } else {
                Format format = (i3 == 3 && MimeTypes.isAudio(upstreamFormat.sampleMimeType)) ? this.muxedAudioFormat : null;
                trackGroupArr[i6] = new TrackGroup(deriveFormat(format, upstreamFormat));
            }
        }
        this.trackGroups = new TrackGroupArray(trackGroupArr);
    }

    private static Format deriveFormat(Format format, Format format2) {
        if (format == null) {
            return format2;
        }
        String str = null;
        int trackType = MimeTypes.getTrackType(format2.sampleMimeType);
        if (trackType == 1) {
            str = getAudioCodecs(format.codecs);
        } else if (trackType == 2) {
            str = getVideoCodecs(format.codecs);
        }
        return format2.copyWithContainerInfo(format.id, str, format.bitrate, format.width, format.height, format.selectionFlags, format.language);
    }

    private boolean finishedReadingChunk(HlsMediaChunk hlsMediaChunk) {
        int i = hlsMediaChunk.uid;
        int i2 = 0;
        while (i2 < this.sampleQueues.length) {
            if (this.trackGroupEnabledStates[i2] && this.sampleQueues[i2].peekSourceId() == i) {
                return false;
            }
            i2++;
        }
        return true;
    }

    private static String getAudioCodecs(String str) {
        return getCodecsOfType(str, 1);
    }

    private static String getCodecsOfType(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split("(\\s*,\\s*)|(\\s*$)");
        StringBuilder stringBuilder = new StringBuilder();
        for (String str2 : split) {
            if (i == MimeTypes.getTrackTypeOfCodec(str2)) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(str2);
            }
        }
        return stringBuilder.length() > 0 ? stringBuilder.toString() : null;
    }

    private static String getVideoCodecs(String str) {
        return getCodecsOfType(str, 2);
    }

    private boolean isMediaChunk(Chunk chunk) {
        return chunk instanceof HlsMediaChunk;
    }

    private boolean isPendingReset() {
        return this.pendingResetPositionUs != C3446C.TIME_UNSET;
    }

    private void maybeFinishPrepare() {
        if (!this.released && !this.prepared && this.sampleQueuesBuilt) {
            SampleQueue[] sampleQueueArr = this.sampleQueues;
            int length = sampleQueueArr.length;
            int i = 0;
            while (i < length) {
                if (sampleQueueArr[i].getUpstreamFormat() != null) {
                    i++;
                } else {
                    return;
                }
            }
            buildTracks();
            this.prepared = true;
            this.callback.onPrepared();
        }
    }

    private void resetSampleQueues() {
        for (SampleQueue reset : this.sampleQueues) {
            reset.reset(this.pendingResetUpstreamFormats);
        }
        this.pendingResetUpstreamFormats = false;
    }

    private boolean seekInsideBufferUs(long j) {
        int length = this.sampleQueues.length;
        int i = 0;
        while (i < length) {
            SampleQueue sampleQueue = this.sampleQueues[i];
            sampleQueue.rewind();
            if (!sampleQueue.advanceTo(j, true, false) && (this.trackGroupIsAudioVideoFlags[i] || !this.haveAudioVideoTrackGroups)) {
                return false;
            }
            sampleQueue.discardToRead();
            i++;
        }
        return true;
    }

    private void setTrackGroupEnabledState(int i, boolean z) {
        int i2 = 1;
        Assertions.checkState(this.trackGroupEnabledStates[i] != z);
        this.trackGroupEnabledStates[i] = z;
        int i3 = this.enabledTrackCount;
        if (!z) {
            i2 = -1;
        }
        this.enabledTrackCount = i3 + i2;
    }

    public boolean continueLoading(long j) {
        if (this.loadingFinished || this.loader.isLoading()) {
            return false;
        }
        HlsChunkSource hlsChunkSource = this.chunkSource;
        HlsMediaChunk hlsMediaChunk = this.mediaChunks.isEmpty() ? null : (HlsMediaChunk) this.mediaChunks.getLast();
        if (this.pendingResetPositionUs != C3446C.TIME_UNSET) {
            j = this.pendingResetPositionUs;
        }
        hlsChunkSource.getNextChunk(hlsMediaChunk, j, this.nextChunkHolder);
        boolean z = this.nextChunkHolder.endOfStream;
        Chunk chunk = this.nextChunkHolder.chunk;
        HlsUrl hlsUrl = this.nextChunkHolder.playlist;
        this.nextChunkHolder.clear();
        if (z) {
            this.pendingResetPositionUs = C3446C.TIME_UNSET;
            this.loadingFinished = true;
            return true;
        } else if (chunk == null) {
            if (hlsUrl != null) {
                this.callback.onPlaylistRefreshRequired(hlsUrl);
            }
            return false;
        } else {
            if (isMediaChunk(chunk)) {
                this.pendingResetPositionUs = C3446C.TIME_UNSET;
                hlsMediaChunk = (HlsMediaChunk) chunk;
                hlsMediaChunk.init(this);
                this.mediaChunks.add(hlsMediaChunk);
            }
            this.eventDispatcher.loadStarted(chunk.dataSpec, chunk.type, this.trackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, this.loader.startLoading(chunk, this, this.minLoadableRetryCount));
            return true;
        }
    }

    public void continuePreparing() {
        if (!this.prepared) {
            continueLoading(this.lastSeekPositionUs);
        }
    }

    public void discardBuffer(long j) {
        int length = this.sampleQueues.length;
        for (int i = 0; i < length; i++) {
            this.sampleQueues[i].discardTo(j, false, this.trackGroupEnabledStates[i]);
        }
    }

    public void endTracks() {
        this.sampleQueuesBuilt = true;
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    public long getBufferedPositionUs() {
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        long j = this.lastSeekPositionUs;
        HlsMediaChunk hlsMediaChunk = (HlsMediaChunk) this.mediaChunks.getLast();
        if (!hlsMediaChunk.isLoadCompleted()) {
            hlsMediaChunk = this.mediaChunks.size() > 1 ? (HlsMediaChunk) this.mediaChunks.get(this.mediaChunks.size() - 2) : null;
        }
        long max = hlsMediaChunk != null ? Math.max(j, hlsMediaChunk.endTimeUs) : j;
        SampleQueue[] sampleQueueArr = this.sampleQueues;
        int length = sampleQueueArr.length;
        int i = 0;
        while (i < length) {
            long max2 = Math.max(max, sampleQueueArr[i].getLargestQueuedTimestampUs());
            i++;
            max = max2;
        }
        return max;
    }

    public long getNextLoadPositionUs() {
        return isPendingReset() ? this.pendingResetPositionUs : this.loadingFinished ? Long.MIN_VALUE : ((HlsMediaChunk) this.mediaChunks.getLast()).endTimeUs;
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public void init(int i, boolean z) {
        int i2 = 0;
        this.upstreamChunkUid = i;
        for (SampleQueue sourceId : this.sampleQueues) {
            sourceId.sourceId(i);
        }
        if (z) {
            SampleQueue[] sampleQueueArr = this.sampleQueues;
            int length = sampleQueueArr.length;
            while (i2 < length) {
                sampleQueueArr[i2].splice();
                i2++;
            }
        }
    }

    boolean isReady(int i) {
        return this.loadingFinished || (!isPendingReset() && this.sampleQueues[i].hasNextSample());
    }

    void maybeThrowError() {
        this.loader.maybeThrowError();
        this.chunkSource.maybeThrowError();
    }

    public void maybeThrowPrepareError() {
        maybeThrowError();
    }

    public void onLoadCanceled(Chunk chunk, long j, long j2, boolean z) {
        this.eventDispatcher.loadCanceled(chunk.dataSpec, chunk.type, this.trackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, j, j2, chunk.bytesLoaded());
        if (!z) {
            resetSampleQueues();
            if (this.enabledTrackCount > 0) {
                this.callback.onContinueLoadingRequested(this);
            }
        }
    }

    public void onLoadCompleted(Chunk chunk, long j, long j2) {
        this.chunkSource.onChunkLoadCompleted(chunk);
        this.eventDispatcher.loadCompleted(chunk.dataSpec, chunk.type, this.trackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, j, j2, chunk.bytesLoaded());
        if (this.prepared) {
            this.callback.onContinueLoadingRequested(this);
            return;
        }
        continueLoading(this.lastSeekPositionUs);
    }

    public int onLoadError(Chunk chunk, long j, long j2, IOException iOException) {
        long bytesLoaded = chunk.bytesLoaded();
        boolean isMediaChunk = isMediaChunk(chunk);
        boolean z = !isMediaChunk || bytesLoaded == 0;
        boolean z2 = false;
        if (this.chunkSource.onChunkLoadError(chunk, z, iOException)) {
            if (isMediaChunk) {
                Assertions.checkState(((HlsMediaChunk) this.mediaChunks.removeLast()) == chunk);
                if (this.mediaChunks.isEmpty()) {
                    this.pendingResetPositionUs = this.lastSeekPositionUs;
                }
            }
            z2 = true;
        }
        this.eventDispatcher.loadError(chunk.dataSpec, chunk.type, this.trackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, j, j2, chunk.bytesLoaded(), iOException, z2);
        if (!z2) {
            return 0;
        }
        if (this.prepared) {
            this.callback.onContinueLoadingRequested(this);
        } else {
            continueLoading(this.lastSeekPositionUs);
        }
        return 2;
    }

    public void onLoaderReleased() {
        resetSampleQueues();
    }

    public void onPlaylistBlacklisted(HlsUrl hlsUrl, long j) {
        this.chunkSource.onPlaylistBlacklisted(hlsUrl, j);
    }

    public void onUpstreamFormatChanged(Format format) {
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    public void prepareSingleTrack(Format format) {
        track(0, -1).format(format);
        this.sampleQueuesBuilt = true;
        maybeFinishPrepare();
    }

    int readData(int i, FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
        if (isPendingReset()) {
            return -3;
        }
        if (!this.mediaChunks.isEmpty()) {
            while (this.mediaChunks.size() > 1 && finishedReadingChunk((HlsMediaChunk) this.mediaChunks.getFirst())) {
                this.mediaChunks.removeFirst();
            }
            HlsMediaChunk hlsMediaChunk = (HlsMediaChunk) this.mediaChunks.getFirst();
            Format format = hlsMediaChunk.trackFormat;
            if (!format.equals(this.downstreamTrackFormat)) {
                this.eventDispatcher.downstreamFormatChanged(this.trackType, format, hlsMediaChunk.trackSelectionReason, hlsMediaChunk.trackSelectionData, hlsMediaChunk.startTimeUs);
            }
            this.downstreamTrackFormat = format;
        }
        return this.sampleQueues[i].read(formatHolder, decoderInputBuffer, z, this.loadingFinished, this.lastSeekPositionUs);
    }

    public void release() {
        boolean release = this.loader.release(this);
        if (this.prepared && !release) {
            for (SampleQueue discardToEnd : this.sampleQueues) {
                discardToEnd.discardToEnd();
            }
        }
        this.handler.removeCallbacksAndMessages(null);
        this.released = true;
    }

    public void seekMap(SeekMap seekMap) {
    }

    public boolean seekToUs(long j, boolean z) {
        this.lastSeekPositionUs = j;
        if (!z && !isPendingReset() && seekInsideBufferUs(j)) {
            return false;
        }
        this.pendingResetPositionUs = j;
        this.loadingFinished = false;
        this.mediaChunks.clear();
        if (this.loader.isLoading()) {
            this.loader.cancelLoading();
        } else {
            resetSampleQueues();
        }
        return true;
    }

    public boolean selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j, boolean z) {
        Assertions.checkState(this.prepared);
        int i = this.enabledTrackCount;
        int i2 = 0;
        while (i2 < trackSelectionArr.length) {
            if (sampleStreamArr[i2] != null && (trackSelectionArr[i2] == null || !zArr[i2])) {
                setTrackGroupEnabledState(((HlsSampleStream) sampleStreamArr[i2]).group, false);
                sampleStreamArr[i2] = null;
            }
            i2++;
        }
        boolean z2 = z || (this.seenFirstTrackSelection ? i == 0 : j != this.lastSeekPositionUs);
        TrackSelection trackSelection = this.chunkSource.getTrackSelection();
        boolean z3 = z2;
        int i3 = 0;
        TrackSelection trackSelection2 = trackSelection;
        while (i3 < trackSelectionArr.length) {
            if (sampleStreamArr[i3] == null && trackSelectionArr[i3] != null) {
                TrackSelection trackSelection3 = trackSelectionArr[i3];
                int indexOf = this.trackGroups.indexOf(trackSelection3.getTrackGroup());
                setTrackGroupEnabledState(indexOf, true);
                if (indexOf == this.primaryTrackGroupIndex) {
                    this.chunkSource.selectTracks(trackSelection3);
                    trackSelection2 = trackSelection3;
                }
                sampleStreamArr[i3] = new HlsSampleStream(this, indexOf);
                zArr2[i3] = true;
                if (!z3) {
                    SampleQueue sampleQueue = this.sampleQueues[indexOf];
                    sampleQueue.rewind();
                    z3 = (sampleQueue.advanceTo(j, true, true) || sampleQueue.getReadIndex() == 0) ? false : true;
                }
            }
            i3++;
        }
        if (this.enabledTrackCount == 0) {
            this.chunkSource.reset();
            this.downstreamTrackFormat = null;
            this.mediaChunks.clear();
            if (this.loader.isLoading()) {
                for (SampleQueue discardToEnd : this.sampleQueues) {
                    discardToEnd.discardToEnd();
                }
                this.loader.cancelLoading();
            } else {
                resetSampleQueues();
            }
        } else {
            if (!(this.mediaChunks.isEmpty() || Util.areEqual(trackSelection2, trackSelection))) {
                Object obj;
                if (this.seenFirstTrackSelection) {
                    obj = 1;
                } else {
                    trackSelection2.updateSelectedTrack(0);
                    obj = trackSelection2.getSelectedIndexInTrackGroup() != this.chunkSource.getTrackGroup().indexOf(((HlsMediaChunk) this.mediaChunks.getLast()).trackFormat) ? 1 : null;
                }
                if (obj != null) {
                    z = true;
                    z3 = true;
                    this.pendingResetUpstreamFormats = true;
                }
            }
            if (z3) {
                seekToUs(j, z);
                for (i3 = 0; i3 < sampleStreamArr.length; i3++) {
                    if (sampleStreamArr[i3] != null) {
                        zArr2[i3] = true;
                    }
                }
            }
        }
        this.seenFirstTrackSelection = true;
        return z3;
    }

    public void setIsTimestampMaster(boolean z) {
        this.chunkSource.setIsTimestampMaster(z);
    }

    void skipData(int i, long j) {
        SampleQueue sampleQueue = this.sampleQueues[i];
        if (!this.loadingFinished || j <= sampleQueue.getLargestQueuedTimestampUs()) {
            sampleQueue.advanceTo(j, true, true);
        } else {
            sampleQueue.advanceToEnd();
        }
    }

    public SampleQueue track(int i, int i2) {
        int length = this.sampleQueues.length;
        for (int i3 = 0; i3 < length; i3++) {
            if (this.sampleQueueTrackIds[i3] == i) {
                return this.sampleQueues[i3];
            }
        }
        SampleQueue sampleQueue = new SampleQueue(this.allocator);
        sampleQueue.setUpstreamFormatChangeListener(this);
        this.sampleQueueTrackIds = Arrays.copyOf(this.sampleQueueTrackIds, length + 1);
        this.sampleQueueTrackIds[length] = i;
        this.sampleQueues = (SampleQueue[]) Arrays.copyOf(this.sampleQueues, length + 1);
        this.sampleQueues[length] = sampleQueue;
        return sampleQueue;
    }
}
