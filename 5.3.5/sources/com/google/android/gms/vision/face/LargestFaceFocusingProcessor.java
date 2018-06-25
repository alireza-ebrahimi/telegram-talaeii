package com.google.android.gms.vision.face;

import android.util.SparseArray;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Detector.Detections;
import com.google.android.gms.vision.FocusingProcessor;
import com.google.android.gms.vision.Tracker;

public class LargestFaceFocusingProcessor extends FocusingProcessor<Face> {

    public static class Builder {
        private LargestFaceFocusingProcessor zzlhh;

        public Builder(Detector<Face> detector, Tracker<Face> tracker) {
            this.zzlhh = new LargestFaceFocusingProcessor(detector, tracker);
        }

        public LargestFaceFocusingProcessor build() {
            return this.zzlhh;
        }

        public Builder setMaxGapFrames(int i) {
            this.zzlhh.zzfl(i);
            return this;
        }
    }

    public LargestFaceFocusingProcessor(Detector<Face> detector, Tracker<Face> tracker) {
        super(detector, tracker);
    }

    @Hide
    public int selectFocus(Detections<Face> detections) {
        SparseArray detectedItems = detections.getDetectedItems();
        if (detectedItems.size() == 0) {
            throw new IllegalArgumentException("No faces for selectFocus.");
        }
        int keyAt = detectedItems.keyAt(0);
        float width = ((Face) detectedItems.valueAt(0)).getWidth();
        for (int i = 1; i < detectedItems.size(); i++) {
            int keyAt2 = detectedItems.keyAt(i);
            float width2 = ((Face) detectedItems.valueAt(i)).getWidth();
            if (width2 > width) {
                width = width2;
                keyAt = keyAt2;
            }
        }
        return keyAt;
    }
}
