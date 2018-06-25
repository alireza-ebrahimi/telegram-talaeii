package org.telegram.ui.Components;

import android.content.Context;
import android.util.SparseArray;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.support.widget.GridLayoutManager;

public class ExtendedGridLayoutManager extends GridLayoutManager {
    private int calculatedWidth;
    private SparseArray<Integer> itemSpans = new SparseArray();
    private SparseArray<Integer> itemsToRow = new SparseArray();
    private ArrayList<ArrayList<Integer>> rows;

    public ExtendedGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    private void prepareLayout(float viewPortAvailableSize) {
        int a;
        this.itemSpans.clear();
        this.itemsToRow.clear();
        int preferredRowSize = AndroidUtilities.dp(100.0f);
        float totalItemSize = 0.0f;
        int itemsCount = getFlowItemCount();
        int[] weights = new int[itemsCount];
        for (a = 0; a < itemsCount; a++) {
            Size size = sizeForItem(a);
            totalItemSize += (size.width / size.height) * ((float) preferredRowSize);
            weights[a] = Math.round((size.width / size.height) * 100.0f);
        }
        this.rows = getLinearPartitionForSequence(weights, Math.max(Math.round(totalItemSize / viewPortAvailableSize), 1));
        int i = 0;
        a = 0;
        while (a < this.rows.size()) {
            int j;
            ArrayList<Integer> row = (ArrayList) this.rows.get(a);
            float summedRatios = 0.0f;
            int n = i + row.size();
            for (j = i; j < n; j++) {
                Size preferredSize = sizeForItem(j);
                summedRatios += preferredSize.width / preferredSize.height;
            }
            float rowSize = viewPortAvailableSize;
            if (this.rows.size() == 1 && a == this.rows.size() - 1) {
                if (row.size() < 2) {
                    rowSize = (float) Math.floor((double) (viewPortAvailableSize / 3.0f));
                } else if (row.size() < 3) {
                    rowSize = (float) Math.floor((double) ((2.0f * viewPortAvailableSize) / 3.0f));
                }
            }
            int spanLeft = getSpanCount();
            j = i;
            n = i + row.size();
            while (j < n) {
                int itemSpan;
                preferredSize = sizeForItem(j);
                int width = Math.round((rowSize / summedRatios) * (preferredSize.width / preferredSize.height));
                if (itemsCount < 3 || j != n - 1) {
                    itemSpan = (int) ((((float) width) / viewPortAvailableSize) * ((float) getSpanCount()));
                    spanLeft -= itemSpan;
                } else {
                    this.itemsToRow.put(j, Integer.valueOf(a));
                    itemSpan = spanLeft;
                }
                this.itemSpans.put(j, Integer.valueOf(itemSpan));
                j++;
            }
            i += row.size();
            a++;
        }
    }

    private int[] getLinearPartitionTable(int[] sequence, int numPartitions) {
        int i;
        int j;
        int n = sequence.length;
        int[] tmpTable = new int[(n * numPartitions)];
        int[] solution = new int[((n - 1) * (numPartitions - 1))];
        for (i = 0; i < n; i++) {
            int i2;
            int i3 = i * numPartitions;
            int i4 = sequence[i];
            if (i != 0) {
                i2 = tmpTable[(i - 1) * numPartitions];
            } else {
                i2 = 0;
            }
            tmpTable[i3] = i2 + i4;
        }
        for (j = 0; j < numPartitions; j++) {
            tmpTable[j] = sequence[0];
        }
        for (i = 1; i < n; i++) {
            for (j = 1; j < numPartitions; j++) {
                int currentMin = 0;
                int minX = Integer.MAX_VALUE;
                for (int x = 0; x < i; x++) {
                    int cost = Math.max(tmpTable[(x * numPartitions) + (j - 1)], tmpTable[i * numPartitions] - tmpTable[x * numPartitions]);
                    if (x == 0 || cost < currentMin) {
                        currentMin = cost;
                        minX = x;
                    }
                }
                tmpTable[(i * numPartitions) + j] = currentMin;
                solution[((i - 1) * (numPartitions - 1)) + (j - 1)] = minX;
            }
        }
        return solution;
    }

    private ArrayList<ArrayList<Integer>> getLinearPartitionForSequence(int[] sequence, int numberOfPartitions) {
        int n = sequence.length;
        int k = numberOfPartitions;
        if (k <= 0) {
            return new ArrayList();
        }
        int i;
        if (k >= n || n == 1) {
            ArrayList<ArrayList<Integer>> partition = new ArrayList(sequence.length);
            for (int valueOf : sequence) {
                ArrayList<Integer> arrayList = new ArrayList(1);
                arrayList.add(Integer.valueOf(valueOf));
                partition.add(arrayList);
            }
            return partition;
        }
        ArrayList<Integer> currentAnswer;
        int range;
        int[] solution = getLinearPartitionTable(sequence, numberOfPartitions);
        int solutionRowSize = numberOfPartitions - 1;
        n--;
        ArrayList<ArrayList<Integer>> answer = new ArrayList();
        for (k -= 2; k >= 0; k--) {
            if (n < 1) {
                answer.add(0, new ArrayList());
            } else {
                currentAnswer = new ArrayList();
                range = n + 1;
                for (i = solution[((n - 1) * solutionRowSize) + k] + 1; i < range; i++) {
                    currentAnswer.add(Integer.valueOf(sequence[i]));
                }
                answer.add(0, currentAnswer);
                n = solution[((n - 1) * solutionRowSize) + k];
            }
        }
        currentAnswer = new ArrayList();
        range = n + 1;
        for (i = 0; i < range; i++) {
            currentAnswer.add(Integer.valueOf(sequence[i]));
        }
        answer.add(0, currentAnswer);
        return answer;
    }

    private Size sizeForItem(int i) {
        Size size = getSizeForItem(i);
        if (size.width == 0.0f) {
            size.width = 100.0f;
        }
        if (size.height == 0.0f) {
            size.height = 100.0f;
        }
        float aspect = size.width / size.height;
        if (aspect > 4.0f || aspect < 0.2f) {
            float max = Math.max(size.width, size.height);
            size.width = max;
            size.height = max;
        }
        return size;
    }

    protected Size getSizeForItem(int i) {
        return new Size(100.0f, 100.0f);
    }

    private void checkLayout() {
        if (this.itemSpans.size() != getFlowItemCount() || this.calculatedWidth != getWidth()) {
            this.calculatedWidth = getWidth();
            prepareLayout((float) getWidth());
        }
    }

    public int getSpanSizeForItem(int i) {
        checkLayout();
        return ((Integer) this.itemSpans.get(i)).intValue();
    }

    public int getRowsCount(int width) {
        if (this.rows == null) {
            prepareLayout((float) width);
        }
        return this.rows != null ? this.rows.size() : 0;
    }

    public boolean isLastInRow(int i) {
        checkLayout();
        return this.itemsToRow.get(i) != null;
    }

    public boolean isFirstRow(int i) {
        checkLayout();
        return (this.rows == null || this.rows.isEmpty() || i >= ((ArrayList) this.rows.get(0)).size()) ? false : true;
    }

    protected int getFlowItemCount() {
        return getItemCount();
    }
}
