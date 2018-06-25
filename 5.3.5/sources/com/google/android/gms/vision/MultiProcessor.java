package com.google.android.gms.vision;

import android.util.SparseArray;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.vision.Detector.Detections;
import com.google.android.gms.vision.Detector.Processor;
import java.util.HashSet;
import java.util.Set;

public class MultiProcessor<T> implements Processor<T> {
    private int zzlfz;
    private Factory<T> zzlgl;
    private SparseArray<zza> zzlgm;

    public static class Builder<T> {
        private MultiProcessor<T> zzlgn = new MultiProcessor();

        public Builder(Factory<T> factory) {
            if (factory == null) {
                throw new IllegalArgumentException("No factory supplied.");
            }
            this.zzlgn.zzlgl = factory;
        }

        public MultiProcessor<T> build() {
            return this.zzlgn;
        }

        public Builder<T> setMaxGapFrames(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Invalid max gap: " + i);
            }
            this.zzlgn.zzlfz = i;
            return this;
        }
    }

    public interface Factory<T> {
        Tracker<T> create(T t);
    }

    class zza {
        private Tracker<T> zzlfy;
        private int zzlgc;

        private zza(MultiProcessor multiProcessor) {
            this.zzlgc = 0;
        }
    }

    private MultiProcessor() {
        this.zzlgm = new SparseArray();
        this.zzlfz = 3;
    }

    private final void zza(Detections<T> detections) {
        SparseArray detectedItems = detections.getDetectedItems();
        for (int i = 0; i < detectedItems.size(); i++) {
            int keyAt = detectedItems.keyAt(i);
            Object valueAt = detectedItems.valueAt(i);
            zza zza = (zza) this.zzlgm.get(keyAt);
            zza.zzlgc = 0;
            zza.zzlfy.onUpdate(detections, valueAt);
        }
    }

    @Hide
    public void receiveDetections(Detections<T> detections) {
        int i = 0;
        SparseArray detectedItems = detections.getDetectedItems();
        for (int i2 = 0; i2 < detectedItems.size(); i2++) {
            int keyAt = detectedItems.keyAt(i2);
            Object valueAt = detectedItems.valueAt(i2);
            if (this.zzlgm.get(keyAt) == null) {
                zza zza = new zza();
                zza.zzlfy = this.zzlgl.create(valueAt);
                zza.zzlfy.onNewItem(keyAt, valueAt);
                this.zzlgm.append(keyAt, zza);
            }
        }
        detectedItems = detections.getDetectedItems();
        Set<Integer> hashSet = new HashSet();
        while (i < this.zzlgm.size()) {
            int keyAt2 = this.zzlgm.keyAt(i);
            if (detectedItems.get(keyAt2) == null) {
                zza zza2 = (zza) this.zzlgm.valueAt(i);
                zza2.zzlgc = zza2.zzlgc + 1;
                if (zza2.zzlgc >= this.zzlfz) {
                    zza2.zzlfy.onDone();
                    hashSet.add(Integer.valueOf(keyAt2));
                } else {
                    zza2.zzlfy.onMissing(detections);
                }
            }
            i++;
        }
        for (Integer intValue : hashSet) {
            this.zzlgm.delete(intValue.intValue());
        }
        zza(detections);
    }

    @Hide
    public void release() {
        for (int i = 0; i < this.zzlgm.size(); i++) {
            ((zza) this.zzlgm.valueAt(i)).zzlfy.onDone();
        }
        this.zzlgm.clear();
    }
}
