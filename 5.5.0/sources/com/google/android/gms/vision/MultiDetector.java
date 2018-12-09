package com.google.android.gms.vision;

import android.util.SparseArray;
import com.google.android.gms.vision.Detector.Processor;
import java.util.ArrayList;
import java.util.List;

public class MultiDetector extends Detector<Object> {
    private List<Detector<? extends Object>> zzav;

    public static class Builder {
        private MultiDetector zzaw = new MultiDetector();

        public Builder add(Detector<? extends Object> detector) {
            this.zzaw.zzav.add(detector);
            return this;
        }

        public MultiDetector build() {
            if (this.zzaw.zzav.size() != 0) {
                return this.zzaw;
            }
            throw new RuntimeException("No underlying detectors added to MultiDetector.");
        }
    }

    private MultiDetector() {
        this.zzav = new ArrayList();
    }

    public SparseArray<Object> detect(Frame frame) {
        SparseArray<Object> sparseArray = new SparseArray();
        for (Detector detect : this.zzav) {
            SparseArray detect2 = detect.detect(frame);
            for (int i = 0; i < detect2.size(); i++) {
                int keyAt = detect2.keyAt(i);
                if (sparseArray.get(keyAt) != null) {
                    throw new IllegalStateException("Detection ID overlap for id = " + keyAt + "  This means that one of the detectors is not using global IDs.");
                }
                sparseArray.append(keyAt, detect2.valueAt(i));
            }
        }
        return sparseArray;
    }

    public boolean isOperational() {
        for (Detector isOperational : this.zzav) {
            if (!isOperational.isOperational()) {
                return false;
            }
        }
        return true;
    }

    public void receiveFrame(Frame frame) {
        for (Detector receiveFrame : this.zzav) {
            receiveFrame.receiveFrame(frame);
        }
    }

    public void release() {
        for (Detector release : this.zzav) {
            release.release();
        }
        this.zzav.clear();
    }

    public void setProcessor(Processor<Object> processor) {
        throw new UnsupportedOperationException("MultiDetector.setProcessor is not supported.  You should set a processor instance on each underlying detector instead.");
    }
}
