package org.telegram.customization.util.view.jakerecycler;

import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.View;

public class RecycleBin {
    private int[] activeViewTypes = new int[0];
    private View[] activeViews = new View[0];
    private SparseArray<View> currentScrapViews;
    private SparseArray<View>[] scrapViews;
    private int viewTypeCount;

    static View retrieveFromScrap(SparseArray<View> scrapViews, int position) {
        int size = scrapViews.size();
        if (size <= 0) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            int fromPosition = scrapViews.keyAt(i);
            View view = (View) scrapViews.get(fromPosition);
            if (fromPosition == position) {
                scrapViews.remove(fromPosition);
                return view;
            }
        }
        int index = size - 1;
        View r = (View) scrapViews.valueAt(index);
        scrapViews.remove(scrapViews.keyAt(index));
        return r;
    }

    public void setViewTypeCount(int viewTypeCount) {
        if (viewTypeCount < 1) {
            throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
        }
        SparseArray<View>[] scrapViews = new SparseArray[viewTypeCount];
        for (int i = 0; i < viewTypeCount; i++) {
            scrapViews[i] = new SparseArray();
        }
        this.viewTypeCount = viewTypeCount;
        this.currentScrapViews = scrapViews[0];
        this.scrapViews = scrapViews;
    }

    protected boolean shouldRecycleViewType(int viewType) {
        return viewType >= 0;
    }

    View getScrapView(int position, int viewType) {
        if (this.viewTypeCount == 1) {
            return retrieveFromScrap(this.currentScrapViews, position);
        }
        if (viewType < 0 || viewType >= this.scrapViews.length) {
            return null;
        }
        return retrieveFromScrap(this.scrapViews[viewType], position);
    }

    void addScrapView(View scrap, int position, int viewType) {
        if (this.viewTypeCount == 1) {
            this.currentScrapViews.put(position, scrap);
        } else {
            this.scrapViews[viewType].put(position, scrap);
        }
        if (VERSION.SDK_INT >= 14) {
            scrap.setAccessibilityDelegate(null);
        }
    }

    void scrapActiveViews() {
        boolean multipleScraps = true;
        View[] activeViews = this.activeViews;
        int[] activeViewTypes = this.activeViewTypes;
        if (this.viewTypeCount <= 1) {
            multipleScraps = false;
        }
        SparseArray<View> scrapViews = this.currentScrapViews;
        for (int i = activeViews.length - 1; i >= 0; i--) {
            View victim = activeViews[i];
            if (victim != null) {
                int whichScrap = activeViewTypes[i];
                activeViews[i] = null;
                activeViewTypes[i] = -1;
                if (shouldRecycleViewType(whichScrap)) {
                    if (multipleScraps) {
                        scrapViews = this.scrapViews[whichScrap];
                    }
                    scrapViews.put(i, victim);
                    if (VERSION.SDK_INT >= 14) {
                        victim.setAccessibilityDelegate(null);
                    }
                }
            }
        }
        pruneScrapViews();
    }

    private void pruneScrapViews() {
        int maxViews = this.activeViews.length;
        int viewTypeCount = this.viewTypeCount;
        SparseArray<View>[] scrapViews = this.scrapViews;
        for (int i = 0; i < viewTypeCount; i++) {
            SparseArray<View> scrapPile = scrapViews[i];
            int size = scrapPile.size();
            int extras = size - maxViews;
            int j = 0;
            int size2 = size - 1;
            while (j < extras) {
                size = size2 - 1;
                scrapPile.remove(scrapPile.keyAt(size2));
                j++;
                size2 = size;
            }
        }
    }
}
