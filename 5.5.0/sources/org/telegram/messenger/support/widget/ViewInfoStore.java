package org.telegram.messenger.support.widget;

import android.support.v4.p022f.C0464a;
import android.support.v4.p022f.C0470f;
import android.support.v4.p022f.C0481j.C0478a;
import android.support.v4.p022f.C0481j.C0479b;
import org.telegram.messenger.support.widget.RecyclerView.ItemAnimator.ItemHolderInfo;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;

class ViewInfoStore {
    private static final boolean DEBUG = false;
    final C0464a<ViewHolder, InfoRecord> mLayoutHolderMap = new C0464a();
    final C0470f<ViewHolder> mOldChangedHolders = new C0470f();

    interface ProcessCallback {
        void processAppeared(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        void processDisappeared(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        void processPersistent(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        void unused(ViewHolder viewHolder);
    }

    static class InfoRecord {
        static final int FLAG_APPEAR = 2;
        static final int FLAG_APPEAR_AND_DISAPPEAR = 3;
        static final int FLAG_APPEAR_PRE_AND_POST = 14;
        static final int FLAG_DISAPPEARED = 1;
        static final int FLAG_POST = 8;
        static final int FLAG_PRE = 4;
        static final int FLAG_PRE_AND_POST = 12;
        static C0478a<InfoRecord> sPool = new C0479b(20);
        int flags;
        ItemHolderInfo postInfo;
        ItemHolderInfo preInfo;

        private InfoRecord() {
        }

        static void drainCache() {
            do {
            } while (sPool.mo332a() != null);
        }

        static InfoRecord obtain() {
            InfoRecord infoRecord = (InfoRecord) sPool.mo332a();
            return infoRecord == null ? new InfoRecord() : infoRecord;
        }

        static void recycle(InfoRecord infoRecord) {
            infoRecord.flags = 0;
            infoRecord.preInfo = null;
            infoRecord.postInfo = null;
            sPool.mo333a(infoRecord);
        }
    }

    ViewInfoStore() {
    }

    private ItemHolderInfo popFromLayoutStep(ViewHolder viewHolder, int i) {
        ItemHolderInfo itemHolderInfo = null;
        int a = this.mLayoutHolderMap.m1980a((Object) viewHolder);
        if (a >= 0) {
            InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.m1986c(a);
            if (!(infoRecord == null || (infoRecord.flags & i) == 0)) {
                infoRecord.flags &= i ^ -1;
                if (i == 4) {
                    itemHolderInfo = infoRecord.preInfo;
                } else if (i == 8) {
                    itemHolderInfo = infoRecord.postInfo;
                } else {
                    throw new IllegalArgumentException("Must provide flag PRE or POST");
                }
                if ((infoRecord.flags & 12) == 0) {
                    this.mLayoutHolderMap.m1987d(a);
                    InfoRecord.recycle(infoRecord);
                }
            }
        }
        return itemHolderInfo;
    }

    void addToAppearedInPreLayoutHolders(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            infoRecord = InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecord);
        }
        infoRecord.flags |= 2;
        infoRecord.preInfo = itemHolderInfo;
    }

    void addToDisappearedInLayout(ViewHolder viewHolder) {
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            infoRecord = InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecord);
        }
        r0.flags |= 1;
    }

    void addToOldChangeHolders(long j, ViewHolder viewHolder) {
        this.mOldChangedHolders.m2024b(j, viewHolder);
    }

    void addToPostLayout(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            infoRecord = InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecord);
        }
        infoRecord.postInfo = itemHolderInfo;
        infoRecord.flags |= 8;
    }

    void addToPreLayout(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            infoRecord = InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecord);
        }
        infoRecord.preInfo = itemHolderInfo;
        infoRecord.flags |= 4;
    }

    void clear() {
        this.mLayoutHolderMap.clear();
        this.mOldChangedHolders.m2026c();
    }

    ViewHolder getFromOldChangeHolders(long j) {
        return (ViewHolder) this.mOldChangedHolders.m2018a(j);
    }

    boolean isDisappearing(ViewHolder viewHolder) {
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(viewHolder);
        return (infoRecord == null || (infoRecord.flags & 1) == 0) ? false : true;
    }

    boolean isInPreLayout(ViewHolder viewHolder) {
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(viewHolder);
        return (infoRecord == null || (infoRecord.flags & 4) == 0) ? false : true;
    }

    void onDetach() {
        InfoRecord.drainCache();
    }

    public void onViewDetached(ViewHolder viewHolder) {
        removeFromDisappearedInLayout(viewHolder);
    }

    ItemHolderInfo popFromPostLayout(ViewHolder viewHolder) {
        return popFromLayoutStep(viewHolder, 8);
    }

    ItemHolderInfo popFromPreLayout(ViewHolder viewHolder) {
        return popFromLayoutStep(viewHolder, 4);
    }

    void process(ProcessCallback processCallback) {
        for (int size = this.mLayoutHolderMap.size() - 1; size >= 0; size--) {
            ViewHolder viewHolder = (ViewHolder) this.mLayoutHolderMap.m1985b(size);
            InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.m1987d(size);
            if ((infoRecord.flags & 3) == 3) {
                processCallback.unused(viewHolder);
            } else if ((infoRecord.flags & 1) != 0) {
                if (infoRecord.preInfo == null) {
                    processCallback.unused(viewHolder);
                } else {
                    processCallback.processDisappeared(viewHolder, infoRecord.preInfo, infoRecord.postInfo);
                }
            } else if ((infoRecord.flags & 14) == 14) {
                processCallback.processAppeared(viewHolder, infoRecord.preInfo, infoRecord.postInfo);
            } else if ((infoRecord.flags & 12) == 12) {
                processCallback.processPersistent(viewHolder, infoRecord.preInfo, infoRecord.postInfo);
            } else if ((infoRecord.flags & 4) != 0) {
                processCallback.processDisappeared(viewHolder, infoRecord.preInfo, null);
            } else if ((infoRecord.flags & 8) != 0) {
                processCallback.processAppeared(viewHolder, infoRecord.preInfo, infoRecord.postInfo);
            } else if ((infoRecord.flags & 2) != 0) {
            }
            InfoRecord.recycle(infoRecord);
        }
    }

    void removeFromDisappearedInLayout(ViewHolder viewHolder) {
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord != null) {
            infoRecord.flags &= -2;
        }
    }

    void removeViewHolder(ViewHolder viewHolder) {
        for (int b = this.mOldChangedHolders.m2021b() - 1; b >= 0; b--) {
            if (viewHolder == this.mOldChangedHolders.m2025c(b)) {
                this.mOldChangedHolders.m2020a(b);
                break;
            }
        }
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.remove(viewHolder);
        if (infoRecord != null) {
            InfoRecord.recycle(infoRecord);
        }
    }
}
