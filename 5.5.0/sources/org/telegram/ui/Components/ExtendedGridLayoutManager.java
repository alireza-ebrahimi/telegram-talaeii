package org.telegram.ui.Components;

import android.content.Context;
import android.util.SparseArray;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.support.widget.GridLayoutManager;

public class ExtendedGridLayoutManager extends GridLayoutManager {
    private int calculatedWidth;
    private SparseArray<Integer> itemSpans = new SparseArray();
    private SparseArray<Integer> itemsToRow = new SparseArray();
    private ArrayList<ArrayList<Integer>> rows;

    public ExtendedGridLayoutManager(Context context, int i) {
        super(context, i);
    }

    private void checkLayout() {
        if (this.itemSpans.size() != getFlowItemCount() || this.calculatedWidth != getWidth()) {
            this.calculatedWidth = getWidth();
            prepareLayout((float) getWidth());
        }
    }

    private ArrayList<ArrayList<Integer>> getLinearPartitionForSequence(int[] iArr, int i) {
        int i2 = 0;
        int length = iArr.length;
        if (i <= 0) {
            return new ArrayList();
        }
        if (i >= length || length == 1) {
            ArrayList<ArrayList<Integer>> arrayList = new ArrayList(iArr.length);
            while (i2 < iArr.length) {
                ArrayList arrayList2 = new ArrayList(1);
                arrayList2.add(Integer.valueOf(iArr[i2]));
                arrayList.add(arrayList2);
                i2++;
            }
            return arrayList;
        }
        int i3;
        int[] linearPartitionTable = getLinearPartitionTable(iArr, i);
        int i4 = i - 1;
        int i5 = i - 2;
        int i6 = length - 1;
        ArrayList<ArrayList<Integer>> arrayList3 = new ArrayList();
        i5 = i6;
        for (i3 = i5; i3 >= 0; i3--) {
            if (i5 < 1) {
                arrayList3.add(0, new ArrayList());
            } else {
                ArrayList arrayList4 = new ArrayList();
                int i7 = i5 + 1;
                for (i6 = linearPartitionTable[((i5 - 1) * i4) + i3] + 1; i6 < i7; i6++) {
                    arrayList4.add(Integer.valueOf(iArr[i6]));
                }
                arrayList3.add(0, arrayList4);
                i5 = linearPartitionTable[((i5 - 1) * i4) + i3];
            }
        }
        ArrayList arrayList5 = new ArrayList();
        i3 = i5 + 1;
        for (i5 = 0; i5 < i3; i5++) {
            arrayList5.add(Integer.valueOf(iArr[i5]));
        }
        arrayList3.add(0, arrayList5);
        return arrayList3;
    }

    private int[] getLinearPartitionTable(int[] iArr, int i) {
        int i2;
        int length = iArr.length;
        int[] iArr2 = new int[(length * i)];
        int[] iArr3 = new int[((length - 1) * (i - 1))];
        int i3 = 0;
        while (i3 < length) {
            iArr2[i3 * i] = (i3 != 0 ? iArr2[(i3 - 1) * i] : 0) + iArr[i3];
            i3++;
        }
        for (i2 = 0; i2 < i; i2++) {
            iArr2[i2] = iArr[0];
        }
        for (int i4 = 1; i4 < length; i4++) {
            for (int i5 = 1; i5 < i; i5++) {
                i2 = Integer.MAX_VALUE;
                int i6 = 0;
                for (i3 = 0; i3 < i4; i3++) {
                    int max = Math.max(iArr2[(i3 * i) + (i5 - 1)], iArr2[i4 * i] - iArr2[i3 * i]);
                    if (i3 == 0 || max < i6) {
                        i2 = i3;
                        i6 = max;
                    }
                }
                iArr2[(i4 * i) + i5] = i6;
                iArr3[((i4 - 1) * (i - 1)) + (i5 - 1)] = i2;
            }
        }
        return iArr3;
    }

    private void prepareLayout(float f) {
        this.itemSpans.clear();
        this.itemsToRow.clear();
        int dp = AndroidUtilities.dp(100.0f);
        float f2 = BitmapDescriptorFactory.HUE_RED;
        int flowItemCount = getFlowItemCount();
        int[] iArr = new int[flowItemCount];
        for (int i = 0; i < flowItemCount; i++) {
            Size sizeForItem = sizeForItem(i);
            f2 += (sizeForItem.width / sizeForItem.height) * ((float) dp);
            iArr[i] = Math.round((sizeForItem.width / sizeForItem.height) * 100.0f);
        }
        this.rows = getLinearPartitionForSequence(iArr, Math.max(Math.round(f2 / f), 1));
        dp = 0;
        int i2 = 0;
        while (i2 < this.rows.size()) {
            float floor;
            int spanCount;
            int size;
            int i3;
            ArrayList arrayList = (ArrayList) this.rows.get(i2);
            int size2 = dp + arrayList.size();
            float f3 = BitmapDescriptorFactory.HUE_RED;
            int i4 = dp;
            while (i4 < size2) {
                sizeForItem = sizeForItem(i4);
                i4++;
                f3 = (sizeForItem.width / sizeForItem.height) + f3;
            }
            if (this.rows.size() == 1 && i2 == this.rows.size() - 1) {
                if (arrayList.size() < 2) {
                    floor = (float) Math.floor((double) (f / 3.0f));
                } else if (arrayList.size() < 3) {
                    floor = (float) Math.floor((double) ((2.0f * f) / 3.0f));
                }
                spanCount = getSpanCount();
                size = dp + arrayList.size();
                i3 = dp;
                while (i3 < size) {
                    Size sizeForItem2 = sizeForItem(i3);
                    size2 = Math.round((sizeForItem2.width / sizeForItem2.height) * (floor / f3));
                    if (flowItemCount >= 3 || i3 != size - 1) {
                        size2 = (int) ((((float) size2) / f) * ((float) getSpanCount()));
                        int i5 = size2;
                        size2 = spanCount - size2;
                        spanCount = i5;
                    } else {
                        this.itemsToRow.put(i3, Integer.valueOf(i2));
                        size2 = spanCount;
                    }
                    this.itemSpans.put(i3, Integer.valueOf(spanCount));
                    i3++;
                    spanCount = size2;
                }
                dp += arrayList.size();
                i2++;
            }
            floor = f;
            spanCount = getSpanCount();
            size = dp + arrayList.size();
            i3 = dp;
            while (i3 < size) {
                Size sizeForItem22 = sizeForItem(i3);
                size2 = Math.round((sizeForItem22.width / sizeForItem22.height) * (floor / f3));
                if (flowItemCount >= 3) {
                }
                size2 = (int) ((((float) size2) / f) * ((float) getSpanCount()));
                int i52 = size2;
                size2 = spanCount - size2;
                spanCount = i52;
                this.itemSpans.put(i3, Integer.valueOf(spanCount));
                i3++;
                spanCount = size2;
            }
            dp += arrayList.size();
            i2++;
        }
    }

    private Size sizeForItem(int i) {
        Size sizeForItem = getSizeForItem(i);
        if (sizeForItem.width == BitmapDescriptorFactory.HUE_RED) {
            sizeForItem.width = 100.0f;
        }
        if (sizeForItem.height == BitmapDescriptorFactory.HUE_RED) {
            sizeForItem.height = 100.0f;
        }
        float f = sizeForItem.width / sizeForItem.height;
        if (f > 4.0f || f < 0.2f) {
            f = Math.max(sizeForItem.width, sizeForItem.height);
            sizeForItem.width = f;
            sizeForItem.height = f;
        }
        return sizeForItem;
    }

    protected int getFlowItemCount() {
        return getItemCount();
    }

    public int getRowsCount(int i) {
        if (this.rows == null) {
            prepareLayout((float) i);
        }
        return this.rows != null ? this.rows.size() : 0;
    }

    protected Size getSizeForItem(int i) {
        return new Size(100.0f, 100.0f);
    }

    public int getSpanSizeForItem(int i) {
        checkLayout();
        return ((Integer) this.itemSpans.get(i)).intValue();
    }

    public boolean isFirstRow(int i) {
        checkLayout();
        return (this.rows == null || this.rows.isEmpty() || i >= ((ArrayList) this.rows.get(0)).size()) ? false : true;
    }

    public boolean isLastInRow(int i) {
        checkLayout();
        return this.itemsToRow.get(i) != null;
    }

    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
