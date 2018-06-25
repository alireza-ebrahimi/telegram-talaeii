package com.google.android.gms.vision;

import android.util.SparseArray;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.vision.Frame.Metadata;

public abstract class Detector<T> {
    private final Object zzlft = new Object();
    private Processor<T> zzlfu;

    public static class Detections<T> {
        private final SparseArray<T> zzlfv;
        private final Metadata zzlfw;
        private final boolean zzlfx;

        @Hide
        public Detections(SparseArray<T> sparseArray, Metadata metadata, boolean z) {
            this.zzlfv = sparseArray;
            this.zzlfw = metadata;
            this.zzlfx = z;
        }

        public boolean detectorIsOperational() {
            return this.zzlfx;
        }

        public SparseArray<T> getDetectedItems() {
            return this.zzlfv;
        }

        public Metadata getFrameMetadata() {
            return this.zzlfw;
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
        metadata.zzblk();
        Detections detections = new Detections(detect(frame), metadata, isOperational());
        synchronized (this.zzlft) {
            if (this.zzlfu == null) {
                throw new IllegalStateException("Detector processor must first be set with setProcessor in order to receive detection results.");
            }
            this.zzlfu.receiveDetections(detections);
        }
    }

    public void release() {
        synchronized (this.zzlft) {
            if (this.zzlfu != null) {
                this.zzlfu.release();
                this.zzlfu = null;
            }
        }
    }

    public boolean setFocus(int i) {
        return true;
    }

    public void setProcessor(Processor<T> processor) {
        synchronized (this.zzlft) {
            if (this.zzlfu != null) {
                this.zzlfu.release();
            }
            this.zzlfu = processor;
        }
    }
}
