package org.telegram.messenger.exoplayer2.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SlidingPercentile {
    private static final Comparator<Sample> INDEX_COMPARATOR = new C35411();
    private static final int MAX_RECYCLED_SAMPLES = 5;
    private static final int SORT_ORDER_BY_INDEX = 1;
    private static final int SORT_ORDER_BY_VALUE = 0;
    private static final int SORT_ORDER_NONE = -1;
    private static final Comparator<Sample> VALUE_COMPARATOR = new C35422();
    private int currentSortOrder = -1;
    private final int maxWeight;
    private int nextSampleIndex;
    private int recycledSampleCount;
    private final Sample[] recycledSamples = new Sample[5];
    private final ArrayList<Sample> samples = new ArrayList();
    private int totalWeight;

    /* renamed from: org.telegram.messenger.exoplayer2.util.SlidingPercentile$1 */
    static class C35411 implements Comparator<Sample> {
        C35411() {
        }

        public int compare(Sample sample, Sample sample2) {
            return sample.index - sample2.index;
        }
    }

    /* renamed from: org.telegram.messenger.exoplayer2.util.SlidingPercentile$2 */
    static class C35422 implements Comparator<Sample> {
        C35422() {
        }

        public int compare(Sample sample, Sample sample2) {
            return sample.value < sample2.value ? -1 : sample2.value < sample.value ? 1 : 0;
        }
    }

    private static class Sample {
        public int index;
        public float value;
        public int weight;

        private Sample() {
        }
    }

    public SlidingPercentile(int i) {
        this.maxWeight = i;
    }

    private void ensureSortedByIndex() {
        if (this.currentSortOrder != 1) {
            Collections.sort(this.samples, INDEX_COMPARATOR);
            this.currentSortOrder = 1;
        }
    }

    private void ensureSortedByValue() {
        if (this.currentSortOrder != 0) {
            Collections.sort(this.samples, VALUE_COMPARATOR);
            this.currentSortOrder = 0;
        }
    }

    public void addSample(int i, float f) {
        int i2;
        Sample sample;
        ensureSortedByIndex();
        if (this.recycledSampleCount > 0) {
            Sample[] sampleArr = this.recycledSamples;
            i2 = this.recycledSampleCount - 1;
            this.recycledSampleCount = i2;
            sample = sampleArr[i2];
        } else {
            sample = new Sample();
        }
        i2 = this.nextSampleIndex;
        this.nextSampleIndex = i2 + 1;
        sample.index = i2;
        sample.weight = i;
        sample.value = f;
        this.samples.add(sample);
        this.totalWeight += i;
        while (this.totalWeight > this.maxWeight) {
            i2 = this.totalWeight - this.maxWeight;
            sample = (Sample) this.samples.get(0);
            if (sample.weight <= i2) {
                this.totalWeight -= sample.weight;
                this.samples.remove(0);
                if (this.recycledSampleCount < 5) {
                    Sample[] sampleArr2 = this.recycledSamples;
                    int i3 = this.recycledSampleCount;
                    this.recycledSampleCount = i3 + 1;
                    sampleArr2[i3] = sample;
                }
            } else {
                sample.weight -= i2;
                this.totalWeight -= i2;
            }
        }
    }

    public float getPercentile(float f) {
        ensureSortedByValue();
        float f2 = f * ((float) this.totalWeight);
        int i = 0;
        for (int i2 = 0; i2 < this.samples.size(); i2++) {
            Sample sample = (Sample) this.samples.get(i2);
            i += sample.weight;
            if (((float) i) >= f2) {
                return sample.value;
            }
        }
        return this.samples.isEmpty() ? Float.NaN : ((Sample) this.samples.get(this.samples.size() - 1)).value;
    }
}
