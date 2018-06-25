package com.google.android.gms.vision;

import android.util.SparseArray;
import com.google.android.gms.vision.Frame.Metadata;
import javax.annotation.concurrent.GuardedBy;

public abstract class Detector<T> {
    private final Object zzac = new Object();
    @GuardedBy("mProcessorLock")
    private Processor<T> zzad;

    public static class Detections<T> {
        private final SparseArray<T> zzae;
        private final Metadata zzaf;
        private final boolean zzag;

        public Detections(SparseArray<T> sparseArray, Metadata metadata, boolean z) {
            this.zzae = sparseArray;
            this.zzaf = metadata;
            this.zzag = z;
        }

        public boolean detectorIsOperational() {
            return this.zzag;
        }

        public SparseArray<T> getDetectedItems() {
            return this.zzae;
        }

        public Metadata getFrameMetadata() {
            return this.zzaf;
        }
    }

    public interface Processor<T> {
        void receiveDetections(Detections<T> detections);

        void release();
    }

    public abstract SparseArray<T> detect(Frame frame);

    public boolean isOperational() {
        return true;
    }

    public void receiveFrame(Frame frame) {
        Metadata metadata = new Metadata(frame.getMetadata());
        metadata.zzd();
        Detections detections = new Detections(detect(frame), metadata, isOperational());
        synchronized (this.zzac) {
            if (this.zzad == null) {
                throw new IllegalStateException("Detector processor must first be set with setProcessor in order to receive detection results.");
            }
            this.zzad.receiveDetections(detections);
        }
    }

    public void release() {
        synchronized (this.zzac) {
            if (this.zzad != null) {
                this.zzad.release();
                this.zzad = null;
            }
        }
    }

    public boolean setFocus(int i) {
        return true;
    }

    public void setProcessor(Processor<T> processor) {
        synchronized (this.zzac) {
            if (this.zzad != null) {
                this.zzad.release();
            }
            this.zzad = processor;
        }
    }
}
