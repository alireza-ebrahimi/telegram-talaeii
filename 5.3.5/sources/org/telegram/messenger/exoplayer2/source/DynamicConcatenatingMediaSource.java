package org.telegram.messenger.exoplayer2.source;

import android.util.Pair;
import android.util.SparseIntArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.ExoPlaybackException;
import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.ExoPlayer$ExoPlayerMessage;
import org.telegram.messenger.exoplayer2.ExoPlayer.ExoPlayerComponent;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.Timeline$Period;
import org.telegram.messenger.exoplayer2.Timeline.Window;
import org.telegram.messenger.exoplayer2.source.MediaPeriod.Callback;
import org.telegram.messenger.exoplayer2.source.MediaSource.Listener;
import org.telegram.messenger.exoplayer2.source.MediaSource.MediaPeriodId;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

public final class DynamicConcatenatingMediaSource implements MediaSource, ExoPlayerComponent {
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

        public ConcatenatedTimeline(Collection<MediaSourceHolder> mediaSourceHolders, int windowCount, int periodCount) {
            super(mediaSourceHolders.size());
            this.windowCount = windowCount;
            this.periodCount = periodCount;
            int childCount = mediaSourceHolders.size();
            this.firstPeriodInChildIndices = new int[childCount];
            this.firstWindowInChildIndices = new int[childCount];
            this.timelines = new Timeline[childCount];
            this.uids = new int[childCount];
            int index = 0;
            for (MediaSourceHolder mediaSourceHolder : mediaSourceHolders) {
                this.timelines[index] = mediaSourceHolder.timeline;
                this.firstPeriodInChildIndices[index] = mediaSourceHolder.firstPeriodIndexInChild;
                this.firstWindowInChildIndices[index] = mediaSourceHolder.firstWindowIndexInChild;
                this.uids[index] = ((Integer) mediaSourceHolder.uid).intValue();
                int index2 = index + 1;
                this.childIndexByUid.put(this.uids[index], index);
                index = index2;
            }
        }

        protected int getChildIndexByPeriodIndex(int periodIndex) {
            return Util.binarySearchFloor(this.firstPeriodInChildIndices, periodIndex, true, false);
        }

        protected int getChildIndexByWindowIndex(int windowIndex) {
            return Util.binarySearchFloor(this.firstWindowInChildIndices, windowIndex, true, false);
        }

        protected int getChildIndexByChildUid(Object childUid) {
            if (!(childUid instanceof Integer)) {
                return -1;
            }
            int index = this.childIndexByUid.get(((Integer) childUid).intValue(), -1);
            if (index == -1) {
                index = -1;
            }
            return index;
        }

        protected Timeline getTimelineByChildIndex(int childIndex) {
            return this.timelines[childIndex];
        }

        protected int getFirstPeriodIndexByChildIndex(int childIndex) {
            return this.firstPeriodInChildIndices[childIndex];
        }

        protected int getFirstWindowIndexByChildIndex(int childIndex) {
            return this.firstWindowInChildIndices[childIndex];
        }

        protected Object getChildUidByChildIndex(int childIndex) {
            return Integer.valueOf(this.uids[childIndex]);
        }

        public int getWindowCount() {
            return this.windowCount;
        }

        public int getPeriodCount() {
            return this.periodCount;
        }
    }

    private static final class DeferredMediaPeriod implements MediaPeriod, Callback {
        private final Allocator allocator;
        private Callback callback;
        private final MediaPeriodId id;
        private MediaPeriod mediaPeriod;
        public final MediaSource mediaSource;
        private long preparePositionUs;

        public DeferredMediaPeriod(MediaSource mediaSource, MediaPeriodId id, Allocator allocator) {
            this.id = id;
            this.allocator = allocator;
            this.mediaSource = mediaSource;
        }

        public void createPeriod() {
            this.mediaPeriod = this.mediaSource.createPeriod(this.id, this.allocator);
            if (this.callback != null) {
                this.mediaPeriod.prepare(this, this.preparePositionUs);
            }
        }

        public void releasePeriod() {
            if (this.mediaPeriod != null) {
                this.mediaSource.releasePeriod(this.mediaPeriod);
            }
        }

        public void prepare(Callback callback, long preparePositionUs) {
            this.callback = callback;
            this.preparePositionUs = preparePositionUs;
            if (this.mediaPeriod != null) {
                this.mediaPeriod.prepare(this, preparePositionUs);
            }
        }

        public void maybeThrowPrepareError() throws IOException {
            if (this.mediaPeriod != null) {
                this.mediaPeriod.maybeThrowPrepareError();
            } else {
                this.mediaSource.maybeThrowSourceInfoRefreshError();
            }
        }

        public TrackGroupArray getTrackGroups() {
            return this.mediaPeriod.getTrackGroups();
        }

        public long selectTracks(TrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
            return this.mediaPeriod.selectTracks(selections, mayRetainStreamFlags, streams, streamResetFlags, positionUs);
        }

        public void discardBuffer(long positionUs) {
            this.mediaPeriod.discardBuffer(positionUs);
        }

        public long readDiscontinuity() {
            return this.mediaPeriod.readDiscontinuity();
        }

        public long getBufferedPositionUs() {
            return this.mediaPeriod.getBufferedPositionUs();
        }

        public long seekToUs(long positionUs) {
            return this.mediaPeriod.seekToUs(positionUs);
        }

        public long getNextLoadPositionUs() {
            return this.mediaPeriod.getNextLoadPositionUs();
        }

        public boolean continueLoading(long positionUs) {
            return this.mediaPeriod != null && this.mediaPeriod.continueLoading(positionUs);
        }

        public void onContinueLoadingRequested(MediaPeriod source) {
            this.callback.onContinueLoadingRequested(this);
        }

        public void onPrepared(MediaPeriod mediaPeriod) {
            this.callback.onPrepared(this);
        }
    }

    private static final class DeferredTimeline extends Timeline {
        private static final Object DUMMY_ID = new Object();
        private static final Timeline$Period period = new Timeline$Period();
        private final Object replacedID;
        private final Timeline timeline;

        public DeferredTimeline() {
            this.timeline = null;
            this.replacedID = null;
        }

        private DeferredTimeline(Timeline timeline, Object replacedID) {
            this.timeline = timeline;
            this.replacedID = replacedID;
        }

        public DeferredTimeline cloneWithNewTimeline(Timeline timeline) {
            Object obj = (this.replacedID != null || timeline.getPeriodCount() <= 0) ? this.replacedID : timeline.getPeriod(0, period, true).uid;
            return new DeferredTimeline(timeline, obj);
        }

        public Timeline getTimeline() {
            return this.timeline;
        }

        public int getWindowCount() {
            return this.timeline == null ? 1 : this.timeline.getWindowCount();
        }

        public Window getWindow(int windowIndex, Window window, boolean setIds, long defaultPositionProjectionUs) {
            if (this.timeline != null) {
                return this.timeline.getWindow(windowIndex, window, setIds, defaultPositionProjectionUs);
            }
            return window.set(setIds ? DUMMY_ID : null, C0907C.TIME_UNSET, C0907C.TIME_UNSET, false, true, 0, C0907C.TIME_UNSET, 0, 0, 0);
        }

        public int getPeriodCount() {
            return this.timeline == null ? 1 : this.timeline.getPeriodCount();
        }

        public Timeline$Period getPeriod(int periodIndex, Timeline$Period period, boolean setIds) {
            Object obj = null;
            if (this.timeline == null) {
                Object obj2;
                if (setIds) {
                    obj2 = DUMMY_ID;
                } else {
                    obj2 = null;
                }
                if (setIds) {
                    obj = DUMMY_ID;
                }
                return period.set(obj2, obj, 0, C0907C.TIME_UNSET, C0907C.TIME_UNSET);
            }
            this.timeline.getPeriod(periodIndex, period, setIds);
            if (period.uid != this.replacedID) {
                return period;
            }
            period.uid = DUMMY_ID;
            return period;
        }

        public int getIndexOfPeriod(Object uid) {
            if (this.timeline == null) {
                return uid == DUMMY_ID ? 0 : -1;
            } else {
                Timeline timeline = this.timeline;
                if (uid == DUMMY_ID) {
                    uid = this.replacedID;
                }
                return timeline.getIndexOfPeriod(uid);
            }
        }
    }

    private static final class MediaSourceHolder implements Comparable<MediaSourceHolder> {
        public int firstPeriodIndexInChild;
        public int firstWindowIndexInChild;
        public boolean isPrepared;
        public final MediaSource mediaSource;
        public DeferredTimeline timeline;
        public final Object uid;

        public MediaSourceHolder(MediaSource mediaSource, DeferredTimeline timeline, int window, int period, Object uid) {
            this.mediaSource = mediaSource;
            this.timeline = timeline;
            this.firstWindowIndexInChild = window;
            this.firstPeriodIndexInChild = period;
            this.uid = uid;
        }

        public int compareTo(MediaSourceHolder other) {
            return this.firstPeriodIndexInChild - other.firstPeriodIndexInChild;
        }
    }

    public synchronized void addMediaSource(MediaSource mediaSource) {
        addMediaSource(this.mediaSourcesPublic.size(), mediaSource);
    }

    public synchronized void addMediaSource(int index, MediaSource mediaSource) {
        boolean z = true;
        synchronized (this) {
            Assertions.checkNotNull(mediaSource);
            if (this.mediaSourcesPublic.contains(mediaSource)) {
                z = false;
            }
            Assertions.checkArgument(z);
            this.mediaSourcesPublic.add(index, mediaSource);
            if (this.player != null) {
                this.player.sendMessages(new ExoPlayer$ExoPlayerMessage[]{new ExoPlayer$ExoPlayerMessage(this, 0, Pair.create(Integer.valueOf(index), mediaSource))});
            }
        }
    }

    public synchronized void addMediaSources(Collection<MediaSource> mediaSources) {
        addMediaSources(this.mediaSourcesPublic.size(), mediaSources);
    }

    public synchronized void addMediaSources(int index, Collection<MediaSource> mediaSources) {
        for (MediaSource mediaSource : mediaSources) {
            boolean z;
            Assertions.checkNotNull(mediaSource);
            if (this.mediaSourcesPublic.contains(mediaSource)) {
                z = false;
            } else {
                z = true;
            }
            Assertions.checkArgument(z);
        }
        this.mediaSourcesPublic.addAll(index, mediaSources);
        if (!(this.player == null || mediaSources.isEmpty())) {
            this.player.sendMessages(new ExoPlayer$ExoPlayerMessage[]{new ExoPlayer$ExoPlayerMessage(this, 1, Pair.create(Integer.valueOf(index), mediaSources))});
        }
    }

    public synchronized void removeMediaSource(int index) {
        this.mediaSourcesPublic.remove(index);
        if (this.player != null) {
            this.player.sendMessages(new ExoPlayer$ExoPlayerMessage[]{new ExoPlayer$ExoPlayerMessage(this, 2, Integer.valueOf(index))});
        }
    }

    public synchronized void moveMediaSource(int currentIndex, int newIndex) {
        if (currentIndex != newIndex) {
            this.mediaSourcesPublic.add(newIndex, this.mediaSourcesPublic.remove(currentIndex));
            if (this.player != null) {
                this.player.sendMessages(new ExoPlayer$ExoPlayerMessage[]{new ExoPlayer$ExoPlayerMessage(this, 3, Pair.create(Integer.valueOf(currentIndex), Integer.valueOf(newIndex)))});
            }
        }
    }

    public synchronized int getSize() {
        return this.mediaSourcesPublic.size();
    }

    public synchronized MediaSource getMediaSource(int index) {
        return (MediaSource) this.mediaSourcesPublic.get(index);
    }

    public synchronized void prepareSource(ExoPlayer player, boolean isTopLevelSource, Listener listener) {
        this.player = player;
        this.listener = listener;
        this.preventListenerNotification = true;
        addMediaSourcesInternal(0, this.mediaSourcesPublic);
        this.preventListenerNotification = false;
        maybeNotifyListener();
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        for (MediaSourceHolder mediaSourceHolder : this.mediaSourceHolders) {
            mediaSourceHolder.mediaSource.maybeThrowSourceInfoRefreshError();
        }
    }

    public MediaPeriod createPeriod(MediaPeriodId id, Allocator allocator) {
        MediaPeriod mediaPeriod;
        MediaSourceHolder holder = (MediaSourceHolder) this.mediaSourceHolders.get(findMediaSourceHolderByPeriodIndex(id.periodIndex));
        MediaPeriodId idInSource = new MediaPeriodId(id.periodIndex - holder.firstPeriodIndexInChild);
        if (holder.isPrepared) {
            mediaPeriod = holder.mediaSource.createPeriod(idInSource, allocator);
        } else {
            mediaPeriod = new DeferredMediaPeriod(holder.mediaSource, idInSource, allocator);
            this.deferredMediaPeriods.add((DeferredMediaPeriod) mediaPeriod);
        }
        this.mediaSourceByMediaPeriod.put(mediaPeriod, holder.mediaSource);
        return mediaPeriod;
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

    public void handleMessage(int messageType, Object message) throws ExoPlaybackException {
        this.preventListenerNotification = true;
        switch (messageType) {
            case 0:
                Pair<Integer, MediaSource> messageData = (Pair) message;
                addMediaSourceInternal(((Integer) messageData.first).intValue(), (MediaSource) messageData.second);
                break;
            case 1:
                Pair<Integer, Collection<MediaSource>> messageData2 = (Pair) message;
                addMediaSourcesInternal(((Integer) messageData2.first).intValue(), (Collection) messageData2.second);
                break;
            case 2:
                removeMediaSourceInternal(((Integer) message).intValue());
                break;
            case 3:
                Pair<Integer, Integer> messageData3 = (Pair) message;
                moveMediaSourceInternal(((Integer) messageData3.first).intValue(), ((Integer) messageData3.second).intValue());
                break;
            default:
                throw new IllegalStateException();
        }
        this.preventListenerNotification = false;
        maybeNotifyListener();
    }

    private void maybeNotifyListener() {
        if (!this.preventListenerNotification) {
            this.listener.onSourceInfoRefreshed(new ConcatenatedTimeline(this.mediaSourceHolders, this.windowCount, this.periodCount), null);
        }
    }

    private void addMediaSourceInternal(int newIndex, MediaSource newMediaSource) {
        MediaSourceHolder newMediaSourceHolder;
        Integer newUid = Integer.valueOf(System.identityHashCode(newMediaSource));
        DeferredTimeline newTimeline = new DeferredTimeline();
        if (newIndex > 0) {
            MediaSourceHolder previousHolder = (MediaSourceHolder) this.mediaSourceHolders.get(newIndex - 1);
            newMediaSourceHolder = new MediaSourceHolder(newMediaSource, newTimeline, previousHolder.timeline.getWindowCount() + previousHolder.firstWindowIndexInChild, previousHolder.timeline.getPeriodCount() + previousHolder.firstPeriodIndexInChild, newUid);
        } else {
            newMediaSourceHolder = new MediaSourceHolder(newMediaSource, newTimeline, 0, 0, newUid);
        }
        correctOffsets(newIndex, newTimeline.getWindowCount(), newTimeline.getPeriodCount());
        this.mediaSourceHolders.add(newIndex, newMediaSourceHolder);
        newMediaSourceHolder.mediaSource.prepareSource(this.player, false, new Listener() {
            public void onSourceInfoRefreshed(Timeline newTimeline, Object manifest) {
                DynamicConcatenatingMediaSource.this.updateMediaSourceInternal(newMediaSourceHolder, newTimeline);
            }
        });
    }

    private void addMediaSourcesInternal(int index, Collection<MediaSource> mediaSources) {
        for (MediaSource mediaSource : mediaSources) {
            int index2 = index + 1;
            addMediaSourceInternal(index, mediaSource);
            index = index2;
        }
    }

    private void updateMediaSourceInternal(MediaSourceHolder mediaSourceHolder, Timeline timeline) {
        if (mediaSourceHolder == null) {
            throw new IllegalArgumentException();
        }
        DeferredTimeline deferredTimeline = mediaSourceHolder.timeline;
        if (deferredTimeline.getTimeline() != timeline) {
            int windowOffsetUpdate = timeline.getWindowCount() - deferredTimeline.getWindowCount();
            int periodOffsetUpdate = timeline.getPeriodCount() - deferredTimeline.getPeriodCount();
            if (!(windowOffsetUpdate == 0 && periodOffsetUpdate == 0)) {
                correctOffsets(findMediaSourceHolderByPeriodIndex(mediaSourceHolder.firstPeriodIndexInChild) + 1, windowOffsetUpdate, periodOffsetUpdate);
            }
            mediaSourceHolder.timeline = deferredTimeline.cloneWithNewTimeline(timeline);
            if (!mediaSourceHolder.isPrepared) {
                for (int i = this.deferredMediaPeriods.size() - 1; i >= 0; i--) {
                    if (((DeferredMediaPeriod) this.deferredMediaPeriods.get(i)).mediaSource == mediaSourceHolder.mediaSource) {
                        ((DeferredMediaPeriod) this.deferredMediaPeriods.get(i)).createPeriod();
                        this.deferredMediaPeriods.remove(i);
                    }
                }
            }
            mediaSourceHolder.isPrepared = true;
            maybeNotifyListener();
        }
    }

    private void removeMediaSourceInternal(int index) {
        MediaSourceHolder holder = (MediaSourceHolder) this.mediaSourceHolders.get(index);
        this.mediaSourceHolders.remove(index);
        Timeline oldTimeline = holder.timeline;
        correctOffsets(index, -oldTimeline.getWindowCount(), -oldTimeline.getPeriodCount());
        holder.mediaSource.releaseSource();
    }

    private void moveMediaSourceInternal(int currentIndex, int newIndex) {
        int startIndex = Math.min(currentIndex, newIndex);
        int endIndex = Math.max(currentIndex, newIndex);
        int windowOffset = ((MediaSourceHolder) this.mediaSourceHolders.get(startIndex)).firstWindowIndexInChild;
        int periodOffset = ((MediaSourceHolder) this.mediaSourceHolders.get(startIndex)).firstPeriodIndexInChild;
        this.mediaSourceHolders.add(newIndex, this.mediaSourceHolders.remove(currentIndex));
        for (int i = startIndex; i <= endIndex; i++) {
            MediaSourceHolder holder = (MediaSourceHolder) this.mediaSourceHolders.get(i);
            holder.firstWindowIndexInChild = windowOffset;
            holder.firstPeriodIndexInChild = periodOffset;
            windowOffset += holder.timeline.getWindowCount();
            periodOffset += holder.timeline.getPeriodCount();
        }
    }

    private void correctOffsets(int startIndex, int windowOffsetUpdate, int periodOffsetUpdate) {
        this.windowCount += windowOffsetUpdate;
        this.periodCount += periodOffsetUpdate;
        for (int i = startIndex; i < this.mediaSourceHolders.size(); i++) {
            MediaSourceHolder mediaSourceHolder = (MediaSourceHolder) this.mediaSourceHolders.get(i);
            mediaSourceHolder.firstWindowIndexInChild += windowOffsetUpdate;
            mediaSourceHolder = (MediaSourceHolder) this.mediaSourceHolders.get(i);
            mediaSourceHolder.firstPeriodIndexInChild += periodOffsetUpdate;
        }
    }

    private int findMediaSourceHolderByPeriodIndex(int periodIndex) {
        this.query.firstPeriodIndexInChild = periodIndex;
        int index = Collections.binarySearch(this.mediaSourceHolders, this.query);
        return index >= 0 ? index : (-index) - 2;
    }
}
