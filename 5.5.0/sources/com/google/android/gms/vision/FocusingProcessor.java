package com.google.android.gms.vision;

import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.vision.Detector.Detections;
import com.google.android.gms.vision.Detector.Processor;

public abstract class FocusingProcessor<T> implements Processor<T> {
    private Tracker<T> zzah;
    private int zzai = 3;
    private boolean zzaj = false;
    private int zzak;
    private int zzal = 0;
    private Detector<T> zzr;

    public FocusingProcessor(Detector<T> detector, Tracker<T> tracker) {
        this.zzr = detector;
        this.zzah = tracker;
    }

    public void receiveDetections(Detections<T> detections) {
        SparseArray detectedItems = detections.getDetectedItems();
        if (detectedItems.size() == 0) {
            if (this.zzal == this.zzai) {
                this.zzah.onDone();
                this.zzaj = false;
            } else {
                this.zzah.onMissing(detections);
            }
            this.zzal++;
            return;
        }
        this.zzal = 0;
        if (this.zzaj) {
            Object obj = detectedItems.get(this.zzak);
            if (obj != null) {
                this.zzah.onUpdate(detections, obj);
                return;
            } else {
                this.zzah.onDone();
                this.zzaj = false;
            }
        }
        int selectFocus = selectFocus(detections);
        Object obj2 = detectedItems.get(selectFocus);
        if (obj2 == null) {
            Log.w("FocusingProcessor", "Invalid focus selected: " + selectFocus);
            return;
        }
        this.zzaj = true;
        this.zzak = selectFocus;
        this.zzr.setFocus(this.zzak);
        this.zzah.onNewItem(this.zzak, obj2);
        this.zzah.onUpdate(detections, obj2);
    }

    public void release() {
        this.zzah.onDone();
    }

    public abstract int selectFocus(Detections<T> detections);

    protected final void zza(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Invalid max gap: " + i);
        }
        this.zzai = i;
    }
}
