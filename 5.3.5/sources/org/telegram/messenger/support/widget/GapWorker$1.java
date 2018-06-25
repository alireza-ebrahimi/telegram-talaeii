package org.telegram.messenger.support.widget;

import java.util.Comparator;

class GapWorker$1 implements Comparator<GapWorker$Task> {
    GapWorker$1() {
    }

    public int compare(GapWorker$Task lhs, GapWorker$Task rhs) {
        int i = -1;
        if ((lhs.view == null ? 1 : 0) != (rhs.view == null ? 1 : 0)) {
            return lhs.view == null ? 1 : -1;
        } else {
            if (lhs.immediate != rhs.immediate) {
                if (!lhs.immediate) {
                    i = 1;
                }
                return i;
            }
            int deltaViewVelocity = rhs.viewVelocity - lhs.viewVelocity;
            if (deltaViewVelocity != 0) {
                return deltaViewVelocity;
            }
            int deltaDistanceToItem = lhs.distanceToItem - rhs.distanceToItem;
            return deltaDistanceToItem != 0 ? deltaDistanceToItem : 0;
        }
    }
}
