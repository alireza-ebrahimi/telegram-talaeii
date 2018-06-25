package org.telegram.messenger.support.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class StaggeredGridLayoutManager$LazySpanLookup {
    private static final int MIN_SIZE = 10;
    int[] mData;
    List<FullSpanItem> mFullSpanItems;

    /* renamed from: org.telegram.messenger.support.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem */
    static class FullSpanItem implements Parcelable {
        public static final Creator<FullSpanItem> CREATOR = new C18841();
        int mGapDir;
        int[] mGapPerSpan;
        boolean mHasUnwantedGapAfter;
        int mPosition;

        /* renamed from: org.telegram.messenger.support.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem$1 */
        static class C18841 implements Creator<FullSpanItem> {
            C18841() {
            }

            public FullSpanItem createFromParcel(Parcel in) {
                return new FullSpanItem(in);
            }

            public FullSpanItem[] newArray(int size) {
                return new FullSpanItem[size];
            }
        }

        public FullSpanItem(Parcel in) {
            boolean z = true;
            this.mPosition = in.readInt();
            this.mGapDir = in.readInt();
            if (in.readInt() != 1) {
                z = false;
            }
            this.mHasUnwantedGapAfter = z;
            int spanCount = in.readInt();
            if (spanCount > 0) {
                this.mGapPerSpan = new int[spanCount];
                in.readIntArray(this.mGapPerSpan);
            }
        }

        int getGapForSpan(int spanIndex) {
            return this.mGapPerSpan == null ? 0 : this.mGapPerSpan[spanIndex];
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mPosition);
            dest.writeInt(this.mGapDir);
            dest.writeInt(this.mHasUnwantedGapAfter ? 1 : 0);
            if (this.mGapPerSpan == null || this.mGapPerSpan.length <= 0) {
                dest.writeInt(0);
                return;
            }
            dest.writeInt(this.mGapPerSpan.length);
            dest.writeIntArray(this.mGapPerSpan);
        }

        public String toString() {
            return "FullSpanItem{mPosition=" + this.mPosition + ", mGapDir=" + this.mGapDir + ", mHasUnwantedGapAfter=" + this.mHasUnwantedGapAfter + ", mGapPerSpan=" + Arrays.toString(this.mGapPerSpan) + '}';
        }
    }

    StaggeredGridLayoutManager$LazySpanLookup() {
    }

    int forceInvalidateAfter(int position) {
        if (this.mFullSpanItems != null) {
            for (int i = this.mFullSpanItems.size() - 1; i >= 0; i--) {
                if (((FullSpanItem) this.mFullSpanItems.get(i)).mPosition >= position) {
                    this.mFullSpanItems.remove(i);
                }
            }
        }
        return invalidateAfter(position);
    }

    int invalidateAfter(int position) {
        if (this.mData == null || position >= this.mData.length) {
            return -1;
        }
        int endPosition = invalidateFullSpansAfter(position);
        if (endPosition == -1) {
            Arrays.fill(this.mData, position, this.mData.length, -1);
            return this.mData.length;
        }
        Arrays.fill(this.mData, position, endPosition + 1, -1);
        return endPosition + 1;
    }

    int getSpan(int position) {
        if (this.mData == null || position >= this.mData.length) {
            return -1;
        }
        return this.mData[position];
    }

    void setSpan(int position, StaggeredGridLayoutManager$Span span) {
        ensureSize(position);
        this.mData[position] = span.mIndex;
    }

    int sizeForPosition(int position) {
        int len = this.mData.length;
        while (len <= position) {
            len *= 2;
        }
        return len;
    }

    void ensureSize(int position) {
        if (this.mData == null) {
            this.mData = new int[(Math.max(position, 10) + 1)];
            Arrays.fill(this.mData, -1);
        } else if (position >= this.mData.length) {
            int[] old = this.mData;
            this.mData = new int[sizeForPosition(position)];
            System.arraycopy(old, 0, this.mData, 0, old.length);
            Arrays.fill(this.mData, old.length, this.mData.length, -1);
        }
    }

    void clear() {
        if (this.mData != null) {
            Arrays.fill(this.mData, -1);
        }
        this.mFullSpanItems = null;
    }

    void offsetForRemoval(int positionStart, int itemCount) {
        if (this.mData != null && positionStart < this.mData.length) {
            ensureSize(positionStart + itemCount);
            System.arraycopy(this.mData, positionStart + itemCount, this.mData, positionStart, (this.mData.length - positionStart) - itemCount);
            Arrays.fill(this.mData, this.mData.length - itemCount, this.mData.length, -1);
            offsetFullSpansForRemoval(positionStart, itemCount);
        }
    }

    private void offsetFullSpansForRemoval(int positionStart, int itemCount) {
        if (this.mFullSpanItems != null) {
            int end = positionStart + itemCount;
            for (int i = this.mFullSpanItems.size() - 1; i >= 0; i--) {
                FullSpanItem fsi = (FullSpanItem) this.mFullSpanItems.get(i);
                if (fsi.mPosition >= positionStart) {
                    if (fsi.mPosition < end) {
                        this.mFullSpanItems.remove(i);
                    } else {
                        fsi.mPosition -= itemCount;
                    }
                }
            }
        }
    }

    void offsetForAddition(int positionStart, int itemCount) {
        if (this.mData != null && positionStart < this.mData.length) {
            ensureSize(positionStart + itemCount);
            System.arraycopy(this.mData, positionStart, this.mData, positionStart + itemCount, (this.mData.length - positionStart) - itemCount);
            Arrays.fill(this.mData, positionStart, positionStart + itemCount, -1);
            offsetFullSpansForAddition(positionStart, itemCount);
        }
    }

    private void offsetFullSpansForAddition(int positionStart, int itemCount) {
        if (this.mFullSpanItems != null) {
            for (int i = this.mFullSpanItems.size() - 1; i >= 0; i--) {
                FullSpanItem fsi = (FullSpanItem) this.mFullSpanItems.get(i);
                if (fsi.mPosition >= positionStart) {
                    fsi.mPosition += itemCount;
                }
            }
        }
    }

    private int invalidateFullSpansAfter(int position) {
        if (this.mFullSpanItems == null) {
            return -1;
        }
        FullSpanItem item = getFullSpanItem(position);
        if (item != null) {
            this.mFullSpanItems.remove(item);
        }
        int nextFsiIndex = -1;
        int count = this.mFullSpanItems.size();
        for (int i = 0; i < count; i++) {
            if (((FullSpanItem) this.mFullSpanItems.get(i)).mPosition >= position) {
                nextFsiIndex = i;
                break;
            }
        }
        if (nextFsiIndex == -1) {
            return -1;
        }
        FullSpanItem fsi = (FullSpanItem) this.mFullSpanItems.get(nextFsiIndex);
        this.mFullSpanItems.remove(nextFsiIndex);
        return fsi.mPosition;
    }

    public void addFullSpanItem(FullSpanItem fullSpanItem) {
        if (this.mFullSpanItems == null) {
            this.mFullSpanItems = new ArrayList();
        }
        int size = this.mFullSpanItems.size();
        for (int i = 0; i < size; i++) {
            FullSpanItem other = (FullSpanItem) this.mFullSpanItems.get(i);
            if (other.mPosition == fullSpanItem.mPosition) {
                this.mFullSpanItems.remove(i);
            }
            if (other.mPosition >= fullSpanItem.mPosition) {
                this.mFullSpanItems.add(i, fullSpanItem);
                return;
            }
        }
        this.mFullSpanItems.add(fullSpanItem);
    }

    public FullSpanItem getFullSpanItem(int position) {
        if (this.mFullSpanItems == null) {
            return null;
        }
        for (int i = this.mFullSpanItems.size() - 1; i >= 0; i--) {
            FullSpanItem fsi = (FullSpanItem) this.mFullSpanItems.get(i);
            if (fsi.mPosition == position) {
                return fsi;
            }
        }
        return null;
    }

    public FullSpanItem getFirstFullSpanItemInRange(int minPos, int maxPos, int gapDir, boolean hasUnwantedGapAfter) {
        if (this.mFullSpanItems == null) {
            return null;
        }
        int limit = this.mFullSpanItems.size();
        for (int i = 0; i < limit; i++) {
            FullSpanItem fsi = (FullSpanItem) this.mFullSpanItems.get(i);
            if (fsi.mPosition >= maxPos) {
                return null;
            }
            if (fsi.mPosition >= minPos) {
                if (gapDir == 0 || fsi.mGapDir == gapDir) {
                    return fsi;
                }
                if (hasUnwantedGapAfter && fsi.mHasUnwantedGapAfter) {
                    return fsi;
                }
            }
        }
        return null;
    }
}
