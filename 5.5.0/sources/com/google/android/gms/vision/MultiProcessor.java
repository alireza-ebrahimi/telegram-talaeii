package com.google.android.gms.vision;

import android.util.SparseArray;
import com.google.android.gms.vision.Detector.Detections;
import com.google.android.gms.vision.Detector.Processor;
import java.util.HashSet;
import java.util.Set;

public class MultiProcessor<T> implements Processor<T> {
    private int zzai;
    private Factory<T> zzax;
    private SparseArray<zza> zzay;

    public static class Builder<T> {
        private MultiProcessor<T> zzaz = new MultiProcessor();

        public Builder(Factory<T> factory) {
            if (factory == null) {
                throw new IllegalArgumentException("No factory supplied.");
            }
            this.zzaz.zzax = factory;
        }

        public MultiProcessor<T> build() {
            return this.zzaz;
        }

        public Builder<T> setMaxGapFrames(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Invalid max gap: " + i);
            }
            this.zzaz.zzai = i;
            return this;
        }
    }

    public interface Factory<T> {
        Tracker<T> create(T t);
    }

    private class zza {
        private Tracker<T> zzah;
        private int zzal;

        private zza(MultiProcessor multiProcessor) {
            this.zzal = 0;
        }
    }

    private MultiProcessor() {
        this.zzay = new SparseArray();
        this.zzai = 3;
    }

    public void receiveDetections(Detections<T> detections) {
        int i;
        int i2;
        SparseArray detectedItems = detections.getDetectedItems();
        for (i = 0; i < detectedItems.size(); i++) {
            int keyAt = detectedItems.keyAt(i);
            Object valueAt = detectedItems.valueAt(i);
            if (this.zzay.get(keyAt) == null) {
                zza zza = new zza();
                zza.zzah = this.zzax.create(valueAt);
                zza.zzah.onNewItem(keyAt, valueAt);
                this.zzay.append(keyAt, zza);
            }
        }
        SparseArray detectedItems2 = detections.getDetectedItems();
        Set<Integer> hashSet = new HashSet();
        for (i2 = 0; i2 < this.zzay.size(); i2++) {
            zza zza2;
            int keyAt2 = this.zzay.keyAt(i2);
            if (detectedItems2.get(keyAt2) == null) {
                zza2 = (zza) this.zzay.valueAt(i2);
                zza2.zzal = zza2.zzal + 1;
                if (zza2.zzal >= this.zzai) {
                    zza2.zzah.onDone();
                    hashSet.add(Integer.valueOf(keyAt2));
                } else {
                    zza2.zzah.onMissing(detections);
                }
            }
        }
        for (Integer intValue : hashSet) {
            this.zzay.delete(intValue.intValue());
        }
        detectedItems2 = detections.getDetectedItems();
        for (i2 = 0; i2 < detectedItems2.size(); i2++) {
            i = detectedItems2.keyAt(i2);
            valueAt = detectedItems2.valueAt(i2);
            zza2 = (zza) this.zzay.get(i);
            zza2.zzal = 0;
            zza2.zzah.onUpdate(detections, valueAt);
        }
    }

    public void release() {
        for (int i = 0; i < this.zzay.size(); i++) {
            ((zza) this.zzay.valueAt(i)).zzah.onDone();
        }
        this.zzay.clear();
    }
}
