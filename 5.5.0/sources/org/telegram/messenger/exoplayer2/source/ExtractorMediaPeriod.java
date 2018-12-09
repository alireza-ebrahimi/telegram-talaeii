package org.telegram.messenger.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.FormatHolder;
import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;
import org.telegram.messenger.exoplayer2.extractor.DefaultExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.PositionHolder;
import org.telegram.messenger.exoplayer2.extractor.SeekMap;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.source.ExtractorMediaSource.EventListener;
import org.telegram.messenger.exoplayer2.source.SampleQueue.UpstreamFormatChangedListener;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.upstream.Loader;
import org.telegram.messenger.exoplayer2.upstream.Loader.Callback;
import org.telegram.messenger.exoplayer2.upstream.Loader.Loadable;
import org.telegram.messenger.exoplayer2.upstream.Loader.ReleaseCallback;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.ConditionVariable;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.Util;

final class ExtractorMediaPeriod implements ExtractorOutput, MediaPeriod, UpstreamFormatChangedListener, Callback<ExtractingLoadable>, ReleaseCallback {
    private static final long DEFAULT_LAST_SAMPLE_DURATION_US = 10000;
    private final Allocator allocator;
    private MediaPeriod.Callback callback;
    private final long continueLoadingCheckIntervalBytes;
    private final String customCacheKey;
    private final DataSource dataSource;
    private long durationUs;
    private int enabledTrackCount;
    private final Handler eventHandler;
    private final EventListener eventListener;
    private int extractedSamplesCountAtStartOfLoad;
    private final ExtractorHolder extractorHolder;
    private final Handler handler;
    private boolean haveAudioVideoTracks;
    private long lastSeekPositionUs;
    private long length;
    private final Listener listener;
    private final ConditionVariable loadCondition;
    private final Loader loader = new Loader("Loader:ExtractorMediaPeriod");
    private boolean loadingFinished;
    private final Runnable maybeFinishPrepareRunnable;
    private final int minLoadableRetryCount;
    private boolean notifyDiscontinuity;
    private final Runnable onContinueLoadingRequestedRunnable;
    private long pendingResetPositionUs;
    private boolean prepared;
    private boolean released;
    private int[] sampleQueueTrackIds;
    private SampleQueue[] sampleQueues;
    private boolean sampleQueuesBuilt;
    private SeekMap seekMap;
    private boolean seenFirstTrackSelection;
    private boolean[] trackEnabledStates;
    private boolean[] trackIsAudioVideoFlags;
    private TrackGroupArray tracks;
    private final Uri uri;

    /* renamed from: org.telegram.messenger.exoplayer2.source.ExtractorMediaPeriod$1 */
    class C35211 implements Runnable {
        C35211() {
        }

        public void run() {
            ExtractorMediaPeriod.this.maybeFinishPrepare();
        }
    }

    /* renamed from: org.telegram.messenger.exoplayer2.source.ExtractorMediaPeriod$2 */
    class C35222 implements Runnable {
        C35222() {
        }

        public void run() {
            if (!ExtractorMediaPeriod.this.released) {
                ExtractorMediaPeriod.this.callback.onContinueLoadingRequested(ExtractorMediaPeriod.this);
            }
        }
    }

    final class ExtractingLoadable implements Loadable {
        private final DataSource dataSource;
        private final ExtractorHolder extractorHolder;
        private long length = -1;
        private volatile boolean loadCanceled;
        private final ConditionVariable loadCondition;
        private boolean pendingExtractorSeek = true;
        private final PositionHolder positionHolder = new PositionHolder();
        private long seekTimeUs;
        private final Uri uri;

        public ExtractingLoadable(Uri uri, DataSource dataSource, ExtractorHolder extractorHolder, ConditionVariable conditionVariable) {
            this.uri = (Uri) Assertions.checkNotNull(uri);
            this.dataSource = (DataSource) Assertions.checkNotNull(dataSource);
            this.extractorHolder = (ExtractorHolder) Assertions.checkNotNull(extractorHolder);
            this.loadCondition = conditionVariable;
        }

        public void cancelLoad() {
            this.loadCanceled = true;
        }

        public boolean isLoadCanceled() {
            return this.loadCanceled;
        }

        public void load() {
            int read;
            Throwable th;
            ExtractorInput extractorInput;
            Throwable th2;
            int i = 0;
            while (i == 0 && !this.loadCanceled) {
                try {
                    long j = this.positionHolder.position;
                    this.length = this.dataSource.open(new DataSpec(this.uri, j, -1, ExtractorMediaPeriod.this.customCacheKey));
                    if (this.length != -1) {
                        this.length += j;
                    }
                    ExtractorInput defaultExtractorInput = new DefaultExtractorInput(this.dataSource, j, this.length);
                    try {
                        int i2;
                        Extractor selectExtractor = this.extractorHolder.selectExtractor(defaultExtractorInput, this.dataSource.getUri());
                        if (this.pendingExtractorSeek) {
                            selectExtractor.seek(j, this.seekTimeUs);
                            this.pendingExtractorSeek = false;
                        }
                        long j2 = j;
                        int i3 = i;
                        while (i3 == 0) {
                            try {
                                if (this.loadCanceled) {
                                    break;
                                }
                                this.loadCondition.block();
                                read = selectExtractor.read(defaultExtractorInput, this.positionHolder);
                                try {
                                    if (defaultExtractorInput.getPosition() > ExtractorMediaPeriod.this.continueLoadingCheckIntervalBytes + j2) {
                                        j2 = defaultExtractorInput.getPosition();
                                        this.loadCondition.close();
                                        ExtractorMediaPeriod.this.handler.post(ExtractorMediaPeriod.this.onContinueLoadingRequestedRunnable);
                                        i3 = read;
                                    } else {
                                        i3 = read;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    extractorInput = defaultExtractorInput;
                                    th2 = th;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                read = i3;
                                extractorInput = defaultExtractorInput;
                                th2 = th;
                            }
                        }
                        if (i3 == 1) {
                            i2 = 0;
                        } else {
                            if (defaultExtractorInput != null) {
                                this.positionHolder.position = defaultExtractorInput.getPosition();
                            }
                            i2 = i3;
                        }
                        Util.closeQuietly(this.dataSource);
                        i = i2;
                    } catch (Throwable th32) {
                        read = i;
                        ExtractorInput extractorInput2 = defaultExtractorInput;
                        th2 = th32;
                        extractorInput = extractorInput2;
                    }
                } catch (Throwable th5) {
                    th2 = th5;
                    extractorInput = null;
                    read = i;
                }
            }
            return;
            if (!(read == 1 || extractorInput == null)) {
                this.positionHolder.position = extractorInput.getPosition();
            }
            Util.closeQuietly(this.dataSource);
            throw th2;
        }

        public void setLoadPosition(long j, long j2) {
            this.positionHolder.position = j;
            this.seekTimeUs = j2;
            this.pendingExtractorSeek = true;
        }
    }

    private static final class ExtractorHolder {
        private Extractor extractor;
        private final ExtractorOutput extractorOutput;
        private final Extractor[] extractors;

        public ExtractorHolder(Extractor[] extractorArr, ExtractorOutput extractorOutput) {
            this.extractors = extractorArr;
            this.extractorOutput = extractorOutput;
        }

        public void release() {
            if (this.extractor != null) {
                this.extractor.release();
                this.extractor = null;
            }
        }

        public Extractor selectExtractor(ExtractorInput extractorInput, Uri uri) {
            if (this.extractor != null) {
                return this.extractor;
            }
            Extractor[] extractorArr = this.extractors;
            int length = extractorArr.length;
            int i = 0;
            loop0:
            while (i < length) {
                Extractor extractor = extractorArr[i];
                try {
                    if (extractor.sniff(extractorInput)) {
                        this.extractor = extractor;
                        extractorInput.resetPeekPosition();
                        break loop0;
                    }
                    i++;
                } catch (EOFException e) {
                    i++;
                } finally {
                    extractorInput.resetPeekPosition();
                }
            }
            if (this.extractor == null) {
                throw new UnrecognizedInputFormatException("None of the available extractors (" + Util.getCommaDelimitedSimpleClassNames(this.extractors) + ") could read the stream.", uri);
            }
            this.extractor.init(this.extractorOutput);
            return this.extractor;
        }
    }

    interface Listener {
        void onSourceInfoRefreshed(long j, boolean z);
    }

    private final class SampleStreamImpl implements SampleStream {
        private final int track;

        public SampleStreamImpl(int i) {
            this.track = i;
        }

        public boolean isReady() {
            return ExtractorMediaPeriod.this.isReady(this.track);
        }

        public void maybeThrowError() {
            ExtractorMediaPeriod.this.maybeThrowError();
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
            return ExtractorMediaPeriod.this.readData(this.track, formatHolder, decoderInputBuffer, z);
        }

        public void skipData(long j) {
            ExtractorMediaPeriod.this.skipData(this.track, j);
        }
    }

    public ExtractorMediaPeriod(Uri uri, DataSource dataSource, Extractor[] extractorArr, int i, Handler handler, EventListener eventListener, Listener listener, Allocator allocator, String str, int i2) {
        this.uri = uri;
        this.dataSource = dataSource;
        this.minLoadableRetryCount = i;
        this.eventHandler = handler;
        this.eventListener = eventListener;
        this.listener = listener;
        this.allocator = allocator;
        this.customCacheKey = str;
        this.continueLoadingCheckIntervalBytes = (long) i2;
        this.extractorHolder = new ExtractorHolder(extractorArr, this);
        this.loadCondition = new ConditionVariable();
        this.maybeFinishPrepareRunnable = new C35211();
        this.onContinueLoadingRequestedRunnable = new C35222();
        this.handler = new Handler();
        this.sampleQueueTrackIds = new int[0];
        this.sampleQueues = new SampleQueue[0];
        this.pendingResetPositionUs = C3446C.TIME_UNSET;
        this.length = -1;
    }

    private void configureRetry(ExtractingLoadable extractingLoadable) {
        if (this.length != -1) {
            return;
        }
        if (this.seekMap == null || this.seekMap.getDurationUs() == C3446C.TIME_UNSET) {
            this.lastSeekPositionUs = 0;
            this.notifyDiscontinuity = this.prepared;
            for (SampleQueue reset : this.sampleQueues) {
                reset.reset();
            }
            extractingLoadable.setLoadPosition(0, 0);
        }
    }

    private void copyLengthFromLoader(ExtractingLoadable extractingLoadable) {
        if (this.length == -1) {
            this.length = extractingLoadable.length;
        }
    }

    private int getExtractedSamplesCount() {
        int i = 0;
        SampleQueue[] sampleQueueArr = this.sampleQueues;
        int i2 = 0;
        while (i < sampleQueueArr.length) {
            i2 += sampleQueueArr[i].getWriteIndex();
            i++;
        }
        return i2;
    }

    private long getLargestQueuedTimestampUs() {
        long j = Long.MIN_VALUE;
        for (SampleQueue largestQueuedTimestampUs : this.sampleQueues) {
            j = Math.max(j, largestQueuedTimestampUs.getLargestQueuedTimestampUs());
        }
        return j;
    }

    private boolean isLoadableExceptionFatal(IOException iOException) {
        return iOException instanceof UnrecognizedInputFormatException;
    }

    private boolean isPendingReset() {
        return this.pendingResetPositionUs != C3446C.TIME_UNSET;
    }

    private void maybeFinishPrepare() {
        if (!this.released && !this.prepared && this.seekMap != null && this.sampleQueuesBuilt) {
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
            this.loadCondition.close();
            length = this.sampleQueues.length;
            TrackGroup[] trackGroupArr = new TrackGroup[length];
            this.trackIsAudioVideoFlags = new boolean[length];
            this.trackEnabledStates = new boolean[length];
            this.durationUs = this.seekMap.getDurationUs();
            for (int i2 = 0; i2 < length; i2++) {
                trackGroupArr[i2] = new TrackGroup(this.sampleQueues[i2].getUpstreamFormat());
                String str = r0.sampleMimeType;
                boolean z = MimeTypes.isVideo(str) || MimeTypes.isAudio(str);
                this.trackIsAudioVideoFlags[i2] = z;
                this.haveAudioVideoTracks = z | this.haveAudioVideoTracks;
            }
            this.tracks = new TrackGroupArray(trackGroupArr);
            this.prepared = true;
            this.listener.onSourceInfoRefreshed(this.durationUs, this.seekMap.isSeekable());
            this.callback.onPrepared(this);
        }
    }

    private void notifyLoadError(final IOException iOException) {
        if (this.eventHandler != null && this.eventListener != null) {
            this.eventHandler.post(new Runnable() {
                public void run() {
                    ExtractorMediaPeriod.this.eventListener.onLoadError(iOException);
                }
            });
        }
    }

    private boolean seekInsideBufferUs(long j) {
        int length = this.sampleQueues.length;
        int i = 0;
        while (i < length) {
            SampleQueue sampleQueue = this.sampleQueues[i];
            sampleQueue.rewind();
            if (!sampleQueue.advanceTo(j, true, false) && (this.trackIsAudioVideoFlags[i] || !this.haveAudioVideoTracks)) {
                return false;
            }
            sampleQueue.discardToRead();
            i++;
        }
        return true;
    }

    private void startLoading() {
        Loadable extractingLoadable = new ExtractingLoadable(this.uri, this.dataSource, this.extractorHolder, this.loadCondition);
        if (this.prepared) {
            Assertions.checkState(isPendingReset());
            if (this.durationUs == C3446C.TIME_UNSET || this.pendingResetPositionUs < this.durationUs) {
                extractingLoadable.setLoadPosition(this.seekMap.getPosition(this.pendingResetPositionUs), this.pendingResetPositionUs);
                this.pendingResetPositionUs = C3446C.TIME_UNSET;
            } else {
                this.loadingFinished = true;
                this.pendingResetPositionUs = C3446C.TIME_UNSET;
                return;
            }
        }
        this.extractedSamplesCountAtStartOfLoad = getExtractedSamplesCount();
        int i = this.minLoadableRetryCount;
        if (i == -1) {
            i = (this.prepared && this.length == -1 && (this.seekMap == null || this.seekMap.getDurationUs() == C3446C.TIME_UNSET)) ? 6 : 3;
        }
        this.loader.startLoading(extractingLoadable, this, i);
    }

    public boolean continueLoading(long j) {
        if (this.loadingFinished || (this.prepared && this.enabledTrackCount == 0)) {
            return false;
        }
        boolean open = this.loadCondition.open();
        if (this.loader.isLoading()) {
            return open;
        }
        startLoading();
        return true;
    }

    public void discardBuffer(long j) {
        int length = this.sampleQueues.length;
        for (int i = 0; i < length; i++) {
            this.sampleQueues[i].discardTo(j, false, this.trackEnabledStates[i]);
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
        long j;
        if (this.haveAudioVideoTracks) {
            int length = this.sampleQueues.length;
            j = Long.MAX_VALUE;
            for (int i = 0; i < length; i++) {
                if (this.trackIsAudioVideoFlags[i]) {
                    j = Math.min(j, this.sampleQueues[i].getLargestQueuedTimestampUs());
                }
            }
        } else {
            j = getLargestQueuedTimestampUs();
        }
        return j == Long.MIN_VALUE ? this.lastSeekPositionUs : j;
    }

    public long getNextLoadPositionUs() {
        return this.enabledTrackCount == 0 ? Long.MIN_VALUE : getBufferedPositionUs();
    }

    public TrackGroupArray getTrackGroups() {
        return this.tracks;
    }

    boolean isReady(int i) {
        return this.loadingFinished || (!isPendingReset() && this.sampleQueues[i].hasNextSample());
    }

    void maybeThrowError() {
        this.loader.maybeThrowError();
    }

    public void maybeThrowPrepareError() {
        maybeThrowError();
    }

    public void onLoadCanceled(ExtractingLoadable extractingLoadable, long j, long j2, boolean z) {
        if (!z) {
            copyLengthFromLoader(extractingLoadable);
            for (SampleQueue reset : this.sampleQueues) {
                reset.reset();
            }
            if (this.enabledTrackCount > 0) {
                this.callback.onContinueLoadingRequested(this);
            }
        }
    }

    public void onLoadCompleted(ExtractingLoadable extractingLoadable, long j, long j2) {
        copyLengthFromLoader(extractingLoadable);
        this.loadingFinished = true;
        if (this.durationUs == C3446C.TIME_UNSET) {
            long largestQueuedTimestampUs = getLargestQueuedTimestampUs();
            this.durationUs = largestQueuedTimestampUs == Long.MIN_VALUE ? 0 : largestQueuedTimestampUs + DEFAULT_LAST_SAMPLE_DURATION_US;
            this.listener.onSourceInfoRefreshed(this.durationUs, this.seekMap.isSeekable());
        }
        this.callback.onContinueLoadingRequested(this);
    }

    public int onLoadError(ExtractingLoadable extractingLoadable, long j, long j2, IOException iOException) {
        copyLengthFromLoader(extractingLoadable);
        notifyLoadError(iOException);
        if (isLoadableExceptionFatal(iOException)) {
            return 3;
        }
        int i = getExtractedSamplesCount() > this.extractedSamplesCountAtStartOfLoad ? 1 : 0;
        configureRetry(extractingLoadable);
        this.extractedSamplesCountAtStartOfLoad = getExtractedSamplesCount();
        return i == 0 ? 0 : 1;
    }

    public void onLoaderReleased() {
        this.extractorHolder.release();
        for (SampleQueue reset : this.sampleQueues) {
            reset.reset();
        }
    }

    public void onUpstreamFormatChanged(Format format) {
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    public void prepare(MediaPeriod.Callback callback, long j) {
        this.callback = callback;
        this.loadCondition.open();
        startLoading();
    }

    int readData(int i, FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
        if (this.notifyDiscontinuity || isPendingReset()) {
            return -3;
        }
        return this.sampleQueues[i].read(formatHolder, decoderInputBuffer, z, this.loadingFinished, this.lastSeekPositionUs);
    }

    public long readDiscontinuity() {
        if (!this.notifyDiscontinuity) {
            return C3446C.TIME_UNSET;
        }
        this.notifyDiscontinuity = false;
        return this.lastSeekPositionUs;
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
        this.seekMap = seekMap;
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    public long seekToUs(long j) {
        int i = 0;
        if (!this.seekMap.isSeekable()) {
            j = 0;
        }
        this.lastSeekPositionUs = j;
        this.notifyDiscontinuity = false;
        if (isPendingReset() || !seekInsideBufferUs(j)) {
            this.pendingResetPositionUs = j;
            this.loadingFinished = false;
            if (this.loader.isLoading()) {
                this.loader.cancelLoading();
            } else {
                SampleQueue[] sampleQueueArr = this.sampleQueues;
                int length = sampleQueueArr.length;
                while (i < length) {
                    sampleQueueArr[i].reset();
                    i++;
                }
            }
        }
        return j;
    }

    public long selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        int access$300;
        int i = 0;
        Assertions.checkState(this.prepared);
        int i2 = this.enabledTrackCount;
        int i3 = 0;
        while (i3 < trackSelectionArr.length) {
            if (sampleStreamArr[i3] != null && (trackSelectionArr[i3] == null || !zArr[i3])) {
                access$300 = ((SampleStreamImpl) sampleStreamArr[i3]).track;
                Assertions.checkState(this.trackEnabledStates[access$300]);
                this.enabledTrackCount--;
                this.trackEnabledStates[access$300] = false;
                sampleStreamArr[i3] = null;
            }
            i3++;
        }
        boolean z = this.seenFirstTrackSelection ? i2 == 0 : j != 0;
        boolean z2 = z;
        access$300 = 0;
        while (access$300 < trackSelectionArr.length) {
            if (sampleStreamArr[access$300] == null && trackSelectionArr[access$300] != null) {
                TrackSelection trackSelection = trackSelectionArr[access$300];
                Assertions.checkState(trackSelection.length() == 1);
                Assertions.checkState(trackSelection.getIndexInTrackGroup(0) == 0);
                int indexOf = this.tracks.indexOf(trackSelection.getTrackGroup());
                Assertions.checkState(!this.trackEnabledStates[indexOf]);
                this.enabledTrackCount++;
                this.trackEnabledStates[indexOf] = true;
                sampleStreamArr[access$300] = new SampleStreamImpl(indexOf);
                zArr2[access$300] = true;
                if (!z2) {
                    SampleQueue sampleQueue = this.sampleQueues[indexOf];
                    sampleQueue.rewind();
                    z2 = (sampleQueue.advanceTo(j, true, true) || sampleQueue.getReadIndex() == 0) ? false : true;
                }
            }
            access$300++;
        }
        if (this.enabledTrackCount == 0) {
            this.notifyDiscontinuity = false;
            SampleQueue[] sampleQueueArr;
            if (this.loader.isLoading()) {
                sampleQueueArr = this.sampleQueues;
                i3 = sampleQueueArr.length;
                while (i < i3) {
                    sampleQueueArr[i].discardToEnd();
                    i++;
                }
                this.loader.cancelLoading();
            } else {
                sampleQueueArr = this.sampleQueues;
                i3 = sampleQueueArr.length;
                while (i < i3) {
                    sampleQueueArr[i].reset();
                    i++;
                }
            }
        } else if (z2) {
            j = seekToUs(j);
            while (i < sampleStreamArr.length) {
                if (sampleStreamArr[i] != null) {
                    zArr2[i] = true;
                }
                i++;
            }
        }
        this.seenFirstTrackSelection = true;
        return j;
    }

    void skipData(int i, long j) {
        SampleQueue sampleQueue = this.sampleQueues[i];
        if (!this.loadingFinished || j <= sampleQueue.getLargestQueuedTimestampUs()) {
            sampleQueue.advanceTo(j, true, true);
        } else {
            sampleQueue.advanceToEnd();
        }
    }

    public TrackOutput track(int i, int i2) {
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
