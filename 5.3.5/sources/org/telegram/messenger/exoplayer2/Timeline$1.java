package org.telegram.messenger.exoplayer2;

import org.telegram.messenger.exoplayer2.Timeline.Window;

class Timeline$1 extends Timeline {
    Timeline$1() {
    }

    public int getWindowCount() {
        return 0;
    }

    public Window getWindow(int windowIndex, Window window, boolean setIds, long defaultPositionProjectionUs) {
        throw new IndexOutOfBoundsException();
    }

    public int getPeriodCount() {
        return 0;
    }

    public Timeline$Period getPeriod(int periodIndex, Timeline$Period period, boolean setIds) {
        throw new IndexOutOfBoundsException();
    }

    public int getIndexOfPeriod(Object uid) {
        return -1;
    }
}
