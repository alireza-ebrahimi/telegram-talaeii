package com.google.android.gms.vision;

import com.google.android.gms.vision.Detector.Detections;

public class Tracker<T> {
    public void onDone() {
    }

    public void onMissing(Detections<T> detections) {
    }

    public void onNewItem(int i, T t) {
    }

    public void onUpdate(Detections<T> detections, T t) {
    }
}
