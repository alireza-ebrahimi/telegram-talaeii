package org.telegram.messenger.exoplayer2.source;

import android.util.Pair;
import android.util.SparseIntArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.ExoPlayer.ExoPlayerComponent;
import org.telegram.messenger.exoplayer2.ExoPlayer.ExoPlayerMessage;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.Timeline.Period;
import org.telegram.messenger.exoplayer2.Timeline.Window;
import org.telegram.messenger.exoplayer2.source.MediaPeriod.Callback;
import org.telegram.messenger.exoplayer2.source.MediaSource.Listener;
import org.telegram.messenger.exoplayer2.source.MediaSource.MediaPeriodId;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

public final class DynamicConcatenatingMediaSource implements ExoPlayerComponent, MediaSource {
    private static final int MSG_ADD = 0;
    private static final int MSG_ADD_MULTIPLE = 1;
    private static final int MSG_MOVE = 3;
    private static final int MSG_REMOVE = 2;
    private final List<DeferredMediaPeriod> deferredMediaPeriods = new ArrayList(1);
    private Listener listener;
    private final Map<MediaPeriod, MediaSource> mediaSourceByMediaPeriod = new IdentityHashMap();
    private final List<MediaSourceHolder> mediaSourceHolders = new ArrayList();
    private final List<MediaSource> mediaSourcesPublic = new ArrayList();
    private int periodCount;
    private ExoPlayer player;
    private boolean preventListenerNotification;
    private final MediaSourceHolder query = new MediaSourceHolder(null, null, -1, -1, Integer.valueOf(-1));
    private int windowCount;

    private static final class ConcatenatedTimeline extends AbstractConcatenatedTimeline {
        private final SparseIntArray childIndexByUid = new SparseIntArray();
        private final int[] firstPeriodInChildIndices;
        private final int[] firstWindowInChildIndices;
        private final int periodCount;
        private final Timeline[] timelines;
        private final int[] uids;
        private final int windowCount;

        public ConcatenatedTimeline(Collection<MediaSourceHolder> collection, int i, int i2) {
            super(collection.size());
            this.windowCount = i;
            this.periodCount = i2;
            int size = collection.size();
            this.firstPeriodInChildIndices = new int[size];
            this.firstWindowInChildIndices = new int[size];
            this.timelines = new Timeline[size];
            this.uids = new int[size];
            int i3 = 0;
            for (MediaSourceHolder mediaSourceHolder : collection) {
                this.timelines[i3] = mediaSourceHolder.timeline;
                this.firstPeriodInChildIndices[i3] = mediaSourceHolder.firstPeriodIndexInChild;
                this.firstWindowInChildIndices[i3] = mediaSourceHolder.firstWindowIndexInChild;
                this.uids[i3] = ((Integer) mediaSourceHolder.uid).intValue();
                size = i3 + 1;
                this.childIndexByUid.put(this.uids[i3], i3);
                i3 = size;
            }
        }

        protected int getChildIndexByChildUid(Object obj) {
            if (!(obj instanceof Integer)) {
                return -1;
            }
            int i = this.childIndexByUid.get(((Integer) obj).intValue(), -1);
            return i != -1 ? i : -1;
        }

        protected int getChildIndexByPeriodIndex(int i) {
            return Util.binarySearchFloor(this.firstPeriodInChildIndices, i, true, false);
        }

        protected int getChildIndexByWindowIndex(int i) {
            return Util.binarySearchFloor(this.firstWindowInChildIndices, i, true, false);
        }

        protected Object getChildUidByChildIndex(int i) {
            return Integer.valueOf(this.uids[i]);
        }

        protected int getFirstPeriodIndexByChildIndex(int i) {
            return this.firstPeriodInChildIndices[i];
        }

        protected int getFirstWindowIndexByChildIndex(int i) {
            return this.firstWindowInChildIndices[i];
        }

        public int getPeriodCount() {
            return this.periodCount;
        }

        protected Timeline getTimelineByChildIndex(int i) {
            return this.timelines[i];
        }

        public int getWindowCount() {
            return this.windowCount;
        }
    }

    private static final class DeferredMediaPeriod implements MediaPeriod, Callback {
        private final Allocator allocator;
        private Callback callback;
        private final MediaPeriodId id;
        private MediaPeriod mediaPeriod;
        public final MediaSource mediaSource;
        private long preparePositionUs;

        public DeferredMediaPeriod(MediaSource mediaSource, MediaPeriodId mediaPeriodId, Allocator allocator) {
            this.id = mediaPeriodId;
            this.allocator = allocator;
            this.mediaSource = mediaSource;
        }

        public boolean continueLoading(long j) {
            return this.mediaPeriod != null && this.mediaPeriod.continueLoading(j);
        }

        public void createPeriod() {
            this.mediaPeriod = this.mediaSource.createPeriod(this.id, this.allocator);
            if (this.callback != null) {
                this.mediaPeriod.prepare(this, this.preparePositionUs);
            }
        }

        public void discardBuffer(long j) {
            this.mediaPeriod.discardBuffer(j);
        }

        public long getBufferedPositionUs() {
            return this.mediaPeriod.getBufferedPositionUs();
        }

        public long getNextLoadPositionUs() {
            return this.mediaPeriod.getNextLoadPositionUs();
        }

        public TrackGroupArray getTrackGroups() {
            return this.mediaPeriod.getTrackGroups();
        }

        public void maybeThrowPrepareError() {
            if (this.mediaPeriod != null) {
                this.mediaPeriod.maybeThrowPrepareError();
            } else {
                this.mediaSource.maybeThrowSourceInfoRefreshError();
            }
        }

        public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
            this.callback.onContinueLoadingRequested(this);
        }

        public void onPrepared(MediaPeriod mediaPeriod) {
            this.callback.onPrepared(this);
        }

        public void prepare(Callback callback, long j) {
            this.callback = callback;
            this.preparePositionUs = j;
            if (this.mediaPeriod != null) {
                this.mediaPeriod.prepare(this, j);
            }
        }

        public long readDiscontinuity() {
            return this.mediaPeriod.readDiscontinuity();
        }

        public void releasePeriod() {
            if (this.mediaPeriod != null) {
                this.mediaSource.releasePeriod(this.mediaPeriod);
            }
        }

        public long seekToUs(long j) {
            return this.mediaPeriod.seekToUs(j);
        }

        public long selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
            return this.mediaPeriod.selectTracks(trackSelectionArr, zArr, sampleStreamArr, zArr2, j);
        }
    }

    private static final class DeferredTimeline extends Timeline {
        private static final Object DUMMY_ID = new Object();
        private static final Period period = new Period();
        private final Object replacedID;
        private final Timeline timeline;

        public DeferredTimeline() {
            this.timeline = null;
            this.replacedID = null;
        }

        private DeferredTimeline(Timeline timeline, Object obj) {
            this.timeline = timeline;
            this.replacedID = obj;
        }

        public DeferredTimeline cloneWithNewTimeline(Timeline timeline) {
            Object obj = (this.replacedID != null || timeline.getPeriodCount() <= 0) ? this.replacedID : timeline.getPeriod(0, period, true).uid;
            return new DeferredTimeline(timeline, obj);
        }

        public int getIndexOfPeriod(Object obj) {
            if (this.timeline == null) {
                return obj == DUMMY_ID ? 0 : -1;
            } else {
                Timeline timeline = this.timeline;
                if (obj == DUMMY_ID) {
                    obj = this.replacedID;
                }
                return timeline.getIndexOfPeriod(obj);
            }
        }

        public Period getPeriod(int i, Period period, boolean z) {
            Object obj = null;
            if (this.timeline == null) {
                Object obj2 = z ? DUMMY_ID : null;
                if (z) {
                    obj = DUMMY_ID;
                }
                return period.set(obj2, obj, 0, C3446C.TIME_UNSET, C3446C.TIME_UNSET);
            }
            this.timeline.getPeriod(i, period, z);
            if (period.uid != this.replacedID) {
                return period;
            }
            period.uid = DUMMY_ID;
            return period;
        }

        public int getPeriodCount() {
            return this.timeline == null ? 1 : this.timeline.getPeriodCount();
        }

        public Timeline getTimeline() {
            return this.timeline;
        }

        public Window getWindow(int i, Window window, boolean z, long j) {
            if (this.timeline != null) {
                return this.timeline.getWindow(i, window, z, j);
            }
            return window.set(z ? DUMMY_ID : null, C3446C.TIME_UNSET, C3446C.TIME_UNSET, false, true, 0, C3446C.TIME_UNSET, 0, 0, 0);
        }

        public int getWindowCount() {
            return this.timeline == null ? 1 : this.timeline.getWindowCount();
        }
    }

    private static final class MediaSourceHolder implements Comparable<MediaSourceHolder> {
        public int firstPeriodIndexInChild;
        public int firstWindowIndexInChild;
        public boolean isPrepared;
        public final MediaSource mediaSource;
        public DeferredTimeline timeline;
        public final Object uid;

        public MediaSourceHolder(MediaSource mediaSource, DeferredTimeline deferredTimeline, int i, int i2, Object obj) {
            this.mediaSource = mediaSource;
            this.timeline = deferredTimeline;
            this.firstWindowIndexInChild = i;
            this.firstPeriodIndexInChild = i2;
            this.uid = obj;
        }

        public int compareTo(MediaSourceHolder mediaSourceHolder) {
            return this.firstPeriodIndexInChild - mediaSourceHolder.firstPeriodIndexInChild;
        }
    }

    private void addMediaSourceInternal(int i, MediaSource mediaSource) {
        MediaSourceHolder mediaSourceHolder;
        Integer valueOf = Integer.valueOf(System.identityHashCode(mediaSource));
        DeferredTimeline deferredTimeline = new DeferredTimeline();
        if (i > 0) {
            MediaSourceHolder mediaSourceHolder2 = (MediaSourceHolder) this.mediaSourceHolders.get(i - 1);
            mediaSourceHolder = new MediaSourceHolder(mediaSource, deferredTimeline, mediaSourceHolder2.firstWindowIndexInChild + mediaSourceHolder2.timeline.getWindowCount(), mediaSourceHolder2.firstPeriodIndexInChild + mediaSourceHolder2.timeline.getPeriodCount(), valueOf);
        } else {
            mediaSourceHolder = new MediaSourceHolder(mediaSource, deferredTimeline, 0, 0, valueOf);
        }
        correctOffsets(i, deferredTimeline.getWindowCount(), deferredTimeline.getPeriodCount());
        this.mediaSourceHolders.add(i, mediaSourceHolder);
        mediaSourceHolder.mediaSource.prepareSource(this.player, false, new Listener() {
            public void onSourceInfoRefreshed(Timeline timeline, Object obj) {
                DynamicConcatenatingMediaSource.this.updateMediaSourceInternal(mediaSourceHolder, timeline);
            }
        });
    }

    private void addMediaSourcesInternal(int i, Collection<MediaSource> collection) {
        for (MediaSource addMediaSourceInternal : collection) {
            int i2 = i + 1;
            addMediaSourceInternal(i, addMediaSourceInternal);
            i = i2;
        }
    }

    private void correctOffsets(int i, int i2, int i3) {
        this.windowCount += i2;
        this.periodCount += i3;
        while (i < this.mediaSourceHolders.size()) {
            MediaSourceHolder mediaSourceHolder = (MediaSourceHolder) this.mediaSourceHolders.get(i);
            mediaSourceHolder.firstWindowIndexInChild += i2;
            mediaSourceHolder = (MediaSourceHolder) this.mediaSourceHolders.get(i);
            mediaSourceHolder.firstPeriodIndexInChild += i3;
            i++;
        }
    }

    private int findMediaSourceHolderByPeriodIndex(int i) {
        this.query.firstPeriodIndexInChild = i;
        int binarySearch = Collections.binarySearch(this.mediaSourceHolders, this.query);
        return binarySearch >= 0 ? binarySearch : (-binarySearch) - 2;
    }

    private void maybeNotifyListener() {
        if (!this.preventListenerNotification) {
            this.listener.onSourceInfoRefreshed(new ConcatenatedTimeline(this.mediaSourceHolders, this.windowCount, this.periodCount), null);
        }
    }

    private void moveMediaSourceInternal(int i, int i2) {
        int min = Math.min(i, i2);
        int max = Math.max(i, i2);
        int i3 = ((MediaSourceHolder) this.mediaSourceHolders.get(min)).firstWindowIndexInChild;
        int i4 = ((MediaSourceHolder) this.mediaSourceHolders.get(min)).firstPeriodIndexInChild;
        this.mediaSourceHolders.add(i2, this.mediaSourceHolders.remove(i));
        int i5 = i3;
        i3 = i4;
        while (min <= max) {
            MediaSourceHolder mediaSourceHolder = (MediaSourceHolder) this.mediaSourceHolders.get(min);
            mediaSourceHolder.firstWindowIndexInChild = i5;
            mediaSourceHolder.firstPeriodIndexInChild = i3;
            i5 += mediaSourceHolder.timeline.getWindowCount();
            i3 += mediaSourceHolder.timeline.getPeriodCount();
            min++;
        }
    }

    private void removeMediaSourceInternal(int i) {
        MediaSourceHolder mediaSourceHolder = (MediaSourceHolder) this.mediaSourceHolders.get(i);
        this.mediaSourceHolders.remove(i);
        Timeline timeline = mediaSourceHolder.timeline;
        correctOffsets(i, -timeline.getWindowCount(), -timeline.getPeriodCount());
        mediaSourceHolder.mediaSource.releaseSource();
    }

    private void updateMediaSourceInternal(MediaSourceHolder mediaSourceHolder, Timeline timeline) {
        if (mediaSourceHolder == null) {
            throw new IllegalArgumentException();
        }
        DeferredTimeline deferredTimeline = mediaSourceHolder.timeline;
        if (deferredTimeline.getTimeline() != timeline) {
            int windowCount = timeline.getWindowCount() - deferredTimeline.getWindowCount();
            int periodCount = timeline.getPeriodCount() - deferredTimeline.getPeriodCount();
            if (!(windowCount == 0 && periodCount == 0)) {
                correctOffsets(findMediaSourceHolderByPeriodIndex(mediaSourceHolder.firstPeriodIndexInChild) + 1, windowCount, periodCount);
            }
            mediaSourceHolder.timeline = deferredTimeline.cloneWithNewTimeline(timeline);
            if (!mediaSourceHolder.isPrepared) {
                for (windowCount = this.deferredMediaPeriods.size() - 1; windowCount >= 0; windowCount--) {
                    if (((DeferredMediaPeriod) this.deferredMediaPeriods.get(windowCount)).mediaSource == mediaSourceHolder.mediaSource) {
                        ((DeferredMediaPeriod) this.deferredMediaPeriods.get(windowCount)).createPeriod();
                        this.deferredMediaPeriods.remove(windowCount);
                    }
                }
            }
            mediaSourceHolder.isPrepared = true;
            maybeNotifyListener();
        }
    }

    public synchronized void addMediaSource(int i, MediaSource mediaSource) {
        boolean z = true;
        synchronized (this) {
            Assertions.checkNotNull(mediaSource);
            if (this.mediaSourcesPublic.contains(mediaSource)) {
                z = false;
            }
            Assertions.checkArgument(z);
            this.mediaSourcesPublic.add(i, mediaSource);
            if (this.player != null) {
                this.player.sendMessages(new ExoPlayerMessage(this, 0, Pair.create(Integer.valueOf(i), mediaSource)));
            }
        }
    }

    public synchronized void addMediaSource(MediaSource mediaSource) {
        addMediaSource(this.mediaSourcesPublic.size(), mediaSource);
    }

    public synchronized void addMediaSources(int i, Collection<MediaSource> collection) {
        for (MediaSource mediaSource : collection) {
            Assertions.checkNotNull(mediaSource);
            Assertions.checkArgument(!this.mediaSourcesPublic.contains(mediaSource));
        }
        this.mediaSourcesPublic.addAll(i, collection);
        if (!(this.player == null || collection.isEmpty())) {
            this.player.sendMessages(new ExoPlayerMessage(this, 1, Pair.create(Integer.valueOf(i), collection)));
        }
    }

    public synchronized void addMediaSources(Collection<MediaSource> collection) {
        addMediaSources(this.mediaSourcesPublic.size(), collection);
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator) {
        MediaPeriod createPeriod;
        MediaSourceHolder mediaSourceHolder = (MediaSourceHolder) this.mediaSourceHolders.get(findMediaSourceHolderByPeriodIndex(mediaPeriodId.periodIndex));
        MediaPeriodId mediaPeriodId2 = new MediaPeriodId(mediaPeriodId.periodIndex - mediaSourceHolder.firstPeriodIndexInChild);
        if (mediaSourceHolder.isPrepared) {
            createPeriod = mediaSourceHolder.mediaSource.createPeriod(mediaPeriodId2, allocator);
        } else {
            createPeriod = new DeferredMediaPeriod(mediaSourceHolder.mediaSource, mediaPeriodId2, allocator);
            this.deferredMediaPeriods.add((DeferredMediaPeriod) createPeriod);
        }
        this.mediaSourceByMediaPeriod.put(createPeriod, mediaSourceHolder.mediaSource);
        return createPeriod;
    }

    public synchronized MediaSource getMediaSource(int i) {
        return (MediaSource) this.mediaSourcesPublic.get(i);
    }

    public synchronized int getSize() {
        return this.mediaSourcesPublic.size();
    }

    public void handleMessage(int i, Object obj) {
        this.preventListenerNotification = true;
        Pair pair;
        switch (i) {
            case 0:
                pair = (Pair) obj;
                addMediaSourceInternal(((Integer) pair.first).intValue(), (MediaSource) pair.second);
                break;
            case 1:
                pair = (Pair) obj;
                addMediaSourcesInternal(((Integer) pair.first).intValue(), (Collection) pair.second);
                break;
            case 2:
                removeMediaSourceInternal(((Integer) obj).intValue());
                break;
            case 3:
                pair = (Pair) obj;
                moveMediaSourceInternal(((Integer) pair.first).intValue(), ((Integer) pair.second).intValue());
                break;
            default:
                throw new IllegalStateException();
        }
        this.preventListenerNotification = false;
        maybeNotifyListener();
    }

    public void maybeThrowSourceInfoRefreshError() {
        for (MediaSourceHolder mediaSourceHolder : this.mediaSourceHolders) {
            mediaSourceHolder.mediaSource.maybeThrowSourceInfoRefreshError();
        }
    }

    public synchronized void moveMediaSource(int i, int i2) {
        if (i != i2) {
            this.mediaSourcesPublic.add(i2, this.mediaSourcesPublic.remove(i));
            if (this.player != null) {
                this.player.sendMessages(new ExoPlayerMessage(this, 3, Pair.create(Integer.valueOf(i), Integer.valueOf(i2))));
            }
        }
    }

    public synchronized void prepareSource(ExoPlayer exoPlayer, boolean z, Listener listener) {
        this.player = exoPlayer;
        this.listener = listener;
        this.preventListenerNotification = true;
        addMediaSourcesInternal(0, this.mediaSourcesPublic);
        this.preventListenerNotification = false;
        maybeNotifyListener();
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        MediaSource mediaSource = (MediaSource) this.mediaSourceByMediaPeriod.get(mediaPeriod);
        this.mediaSourceByMediaPeriod.remove(mediaPeriod);
        if (mediaPeriod instanceof DeferredMediaPeriod) {
            this.deferredMediaPeriods.remove(mediaPeriod);
            ((DeferredMediaPeriod) mediaPeriod).releasePeriod();
            return;
        }
        mediaSource.releasePeriod(mediaPeriod);
    }

    public void releaseSource() {
        for (MediaSourceHolder mediaSourceHolder : this.mediaSourceHolders) {
            mediaSourceHolder.mediaSource.releaseSource();
        }
    }

    public synchronized void removeMediaSource(int i) {
        this.mediaSourcesPublic.remove(i);
        if (this.player != null) {
            this.player.sendMessages(new ExoPlayerMessage(this, 2, Integer.valueOf(i)));
        }
    }
}
