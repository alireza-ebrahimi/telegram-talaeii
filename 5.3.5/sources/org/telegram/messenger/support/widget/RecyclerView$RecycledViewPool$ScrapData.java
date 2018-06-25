package org.telegram.messenger.support.widget;

import java.util.ArrayList;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;

class RecyclerView$RecycledViewPool$ScrapData {
    long mBindRunningAverageNs = 0;
    long mCreateRunningAverageNs = 0;
    int mMaxScrap = 20;
    ArrayList<ViewHolder> mScrapHeap = new ArrayList();

    RecyclerView$RecycledViewPool$ScrapData() {
    }
}
