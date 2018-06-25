package org.telegram.messenger.exoplayer2.source;

import android.util.Pair;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.Timeline$Period;
import org.telegram.messenger.exoplayer2.Timeline.Window;

abstract class AbstractConcatenatedTimeline extends Timeline {
    private final int childCount;

    protected abstract int getChildIndexByChildUid(Object obj);

    protected abstract int getChildIndexByPeriodIndex(int i);

    protected abstract int getChildIndexByWindowIndex(int i);

    protected abstract Object getChildUidByChildIndex(int i);

    protected abstract int getFirstPeriodIndexByChildIndex(int i);

    protected abstract int getFirstWindowIndexByChildIndex(int i);

    protected abstract Timeline getTimelineByChildIndex(int i);

    public AbstractConcatenatedTimeline(int childCount) {
        this.childCount = childCount;
    }

    public int getNextWindowIndex(int windowIndex, int repeatMode) {
        int i;
        int childIndex = getChildIndexByWindowIndex(windowIndex);
        int firstWindowIndexInChild = getFirstWindowIndexByChildIndex(childIndex);
        Timeline timelineByChildIndex = getTimelineByChildIndex(childIndex);
        int i2 = windowIndex - firstWindowIndexInChild;
        if (repeatMode == 2) {
            i = 0;
        } else {
            i = repeatMode;
        }
        int nextWindowIndexInChild = timelineByChildIndex.getNextWindowIndex(i2, i);
        if (nextWindowIndexInChild != -1) {
            return firstWindowIndexInChild + nextWindowIndexInChild;
        }
        int nextChildIndex = childIndex + 1;
        if (nextChildIndex < this.childCount) {
            return getFirstWindowIndexByChildIndex(nextChildIndex);
        }
        if (repeatMode != 2) {
            return -1;
        }
        return 0;
    }

    public int getPreviousWindowIndex(int windowIndex, int repeatMode) {
        int i;
        int childIndex = getChildIndexByWindowIndex(windowIndex);
        int firstWindowIndexInChild = getFirstWindowIndexByChildIndex(childIndex);
        Timeline timelineByChildIndex = getTimelineByChildIndex(childIndex);
        int i2 = windowIndex - firstWindowIndexInChild;
        if (repeatMode == 2) {
            i = 0;
        } else {
            i = repeatMode;
        }
        int previousWindowIndexInChild = timelineByChildIndex.getPreviousWindowIndex(i2, i);
        if (previousWindowIndexInChild != -1) {
            return firstWindowIndexInChild + previousWindowIndexInChild;
        }
        if (firstWindowIndexInChild > 0) {
            return firstWindowIndexInChild - 1;
        }
        return repeatMode == 2 ? getWindowCount() - 1 : -1;
    }

    public final Window getWindow(int windowIndex, Window window, boolean setIds, long defaultPositionProjectionUs) {
        int childIndex = getChildIndexByWindowIndex(windowIndex);
        int firstWindowIndexInChild = getFirstWindowIndexByChildIndex(childIndex);
        int firstPeriodIndexInChild = getFirstPeriodIndexByChildIndex(childIndex);
        getTimelineByChildIndex(childIndex).getWindow(windowIndex - firstWindowIndexInChild, window, setIds, defaultPositionProjectionUs);
        window.firstPeriodIndex += firstPeriodIndexInChild;
        window.lastPeriodIndex += firstPeriodIndexInChild;
        return window;
    }

    public final Timeline$Period getPeriod(int periodIndex, Timeline$Period period, boolean setIds) {
        int childIndex = getChildIndexByPeriodIndex(periodIndex);
        int firstWindowIndexInChild = getFirstWindowIndexByChildIndex(childIndex);
        getTimelineByChildIndex(childIndex).getPeriod(periodIndex - getFirstPeriodIndexByChildIndex(childIndex), period, setIds);
        period.windowIndex += firstWindowIndexInChild;
        if (setIds) {
            period.uid = Pair.create(getChildUidByChildIndex(childIndex), period.uid);
        }
        return period;
    }

    public final int getIndexOfPeriod(Object uid) {
        if (!(uid instanceof Pair)) {
            return -1;
        }
        Pair<?, ?> childUidAndPeriodUid = (Pair) uid;
        Object childUid = childUidAndPeriodUid.first;
        Object periodUid = childUidAndPeriodUid.second;
        int childIndex = getChildIndexByChildUid(childUid);
        if (childIndex == -1) {
            return -1;
        }
        int periodIndexInChild = getTimelineByChildIndex(childIndex).getIndexOfPeriod(periodUid);
        if (periodIndexInChild != -1) {
            return getFirstPeriodIndexByChildIndex(childIndex) + periodIndexInChild;
        }
        return -1;
    }
}
