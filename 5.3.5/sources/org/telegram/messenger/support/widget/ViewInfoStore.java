package org.telegram.messenger.support.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import org.telegram.messenger.support.widget.RecyclerView.ItemAnimator.ItemHolderInfo;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;

class ViewInfoStore {
    private static final boolean DEBUG = false;
    @VisibleForTesting
    final ArrayMap<ViewHolder, ViewInfoStore$InfoRecord> mLayoutHolderMap = new ArrayMap();
    @VisibleForTesting
    final LongSparseArray<ViewHolder> mOldChangedHolders = new LongSparseArray();

    interface ProcessCallback {
        void processAppeared(ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        void processDisappeared(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2);

        void processPersistent(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        void unused(ViewHolder viewHolder);
    }

    ViewInfoStore() {
    }

    void clear() {
        this.mLayoutHolderMap.clear();
        this.mOldChangedHolders.clear();
    }

    void addToPreLayout(ViewHolder holder, ItemHolderInfo info) {
        ViewInfoStore$InfoRecord record = (ViewInfoStore$InfoRecord) this.mLayoutHolderMap.get(holder);
        if (record == null) {
            record = ViewInfoStore$InfoRecord.obtain();
            this.mLayoutHolderMap.put(holder, record);
        }
        record.preInfo = info;
        record.flags |= 4;
    }

    boolean isDisappearing(ViewHolder holder) {
        ViewInfoStore$InfoRecord record = (ViewInfoStore$InfoRecord) this.mLayoutHolderMap.get(holder);
        return (record == null || (record.flags & 1) == 0) ? false : true;
    }

    @Nullable
    ItemHolderInfo popFromPreLayout(ViewHolder vh) {
        return popFromLayoutStep(vh, 4);
    }

    @Nullable
    ItemHolderInfo popFromPostLayout(ViewHolder vh) {
        return popFromLayoutStep(vh, 8);
    }

    private ItemHolderInfo popFromLayoutStep(ViewHolder vh, int flag) {
        ItemHolderInfo itemHolderInfo = null;
        int index = this.mLayoutHolderMap.indexOfKey(vh);
        if (index >= 0) {
            ViewInfoStore$InfoRecord record = (ViewInfoStore$InfoRecord) this.mLayoutHolderMap.valueAt(index);
            if (!(record == null || (record.flags & flag) == 0)) {
                record.flags &= flag ^ -1;
                if (flag == 4) {
                    itemHolderInfo = record.preInfo;
                } else if (flag == 8) {
                    itemHolderInfo = record.postInfo;
                } else {
                    throw new IllegalArgumentException("Must provide flag PRE or POST");
                }
                if ((record.flags & 12) == 0) {
                    this.mLayoutHolderMap.removeAt(index);
                    ViewInfoStore$InfoRecord.recycle(record);
                }
            }
        }
        return itemHolderInfo;
    }

    void addToOldChangeHolders(long key, ViewHolder holder) {
        this.mOldChangedHolders.put(key, holder);
    }

    void addToAppearedInPreLayoutHolders(ViewHolder holder, ItemHolderInfo info) {
        ViewInfoStore$InfoRecord record = (ViewInfoStore$InfoRecord) this.mLayoutHolderMap.get(holder);
        if (record == null) {
            record = ViewInfoStore$InfoRecord.obtain();
            this.mLayoutHolderMap.put(holder, record);
        }
        record.flags |= 2;
        record.preInfo = info;
    }

    boolean isInPreLayout(ViewHolder viewHolder) {
        ViewInfoStore$InfoRecord record = (ViewInfoStore$InfoRecord) this.mLayoutHolderMap.get(viewHolder);
        return (record == null || (record.flags & 4) == 0) ? false : true;
    }

    ViewHolder getFromOldChangeHolders(long key) {
        return (ViewHolder) this.mOldChangedHolders.get(key);
    }

    void addToPostLayout(ViewHolder holder, ItemHolderInfo info) {
        ViewInfoStore$InfoRecord record = (ViewInfoStore$InfoRecord) this.mLayoutHolderMap.get(holder);
        if (record == null) {
            record = ViewInfoStore$InfoRecord.obtain();
            this.mLayoutHolderMap.put(holder, record);
        }
        record.postInfo = info;
        record.flags |= 8;
    }

    void addToDisappearedInLayout(ViewHolder holder) {
        ViewInfoStore$InfoRecord record = (ViewInfoStore$InfoRecord) this.mLayoutHolderMap.get(holder);
        if (record == null) {
            record = ViewInfoStore$InfoRecord.obtain();
            this.mLayoutHolderMap.put(holder, record);
        }
        record.flags |= 1;
    }

    void removeFromDisappearedInLayout(ViewHolder holder) {
        ViewInfoStore$InfoRecord record = (ViewInfoStore$InfoRecord) this.mLayoutHolderMap.get(holder);
        if (record != null) {
            record.flags &= -2;
        }
    }

    void process(ProcessCallback callback) {
        for (int index = this.mLayoutHolderMap.size() - 1; index >= 0; index--) {
            ViewHolder viewHolder = (ViewHolder) this.mLayoutHolderMap.keyAt(index);
            ViewInfoStore$InfoRecord record = (ViewInfoStore$InfoRecord) this.mLayoutHolderMap.removeAt(index);
            if ((record.flags & 3) == 3) {
                callback.unused(viewHolder);
            } else if ((record.flags & 1) != 0) {
                if (record.preInfo == null) {
                    callback.unused(viewHolder);
                } else {
                    callback.processDisappeared(viewHolder, record.preInfo, record.postInfo);
                }
            } else if ((record.flags & 14) == 14) {
                callback.processAppeared(viewHolder, record.preInfo, record.postInfo);
            } else if ((record.flags & 12) == 12) {
                callback.processPersistent(viewHolder, record.preInfo, record.postInfo);
            } else if ((record.flags & 4) != 0) {
                callback.processDisappeared(viewHolder, record.preInfo, null);
            } else if ((record.flags & 8) != 0) {
                callback.processAppeared(viewHolder, record.preInfo, record.postInfo);
            } else if ((record.flags & 2) != 0) {
            }
            ViewInfoStore$InfoRecord.recycle(record);
        }
    }

    void removeViewHolder(ViewHolder holder) {
        for (int i = this.mOldChangedHolders.size() - 1; i >= 0; i--) {
            if (holder == this.mOldChangedHolders.valueAt(i)) {
                this.mOldChangedHolders.removeAt(i);
                break;
            }
        }
        ViewInfoStore$InfoRecord info = (ViewInfoStore$InfoRecord) this.mLayoutHolderMap.remove(holder);
        if (info != null) {
            ViewInfoStore$InfoRecord.recycle(info);
        }
    }

    void onDetach() {
        ViewInfoStore$InfoRecord.drainCache();
    }

    public void onViewDetached(ViewHolder viewHolder) {
        removeFromDisappearedInLayout(viewHolder);
    }
}
