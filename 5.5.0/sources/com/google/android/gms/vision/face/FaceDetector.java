package com.google.android.gms.vision.face;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.internal.vision.zzk;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.internal.client.zza;
import com.google.android.gms.vision.zzc;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.concurrent.GuardedBy;

public final class FaceDetector extends Detector<Face> {
    public static final int ACCURATE_MODE = 1;
    public static final int ALL_CLASSIFICATIONS = 1;
    public static final int ALL_LANDMARKS = 1;
    public static final int FAST_MODE = 0;
    public static final int NO_CLASSIFICATIONS = 0;
    public static final int NO_LANDMARKS = 0;
    @GuardedBy("mLock")
    private boolean mIsActive;
    private final Object mLock;
    private final zzc zzbm;
    @GuardedBy("mLock")
    private final zza zzbn;

    public static class Builder {
        private final Context mContext;
        private int zzbo = 0;
        private boolean zzbp = false;
        private int zzbq = 0;
        private boolean zzbr = true;
        private int zzbs = 0;
        private float zzbt = -1.0f;

        public Builder(Context context) {
            this.mContext = context;
        }

        public FaceDetector build() {
            com.google.android.gms.vision.face.internal.client.zzc zzc = new com.google.android.gms.vision.face.internal.client.zzc();
            zzc.mode = this.zzbs;
            zzc.zzcd = this.zzbo;
            zzc.zzce = this.zzbq;
            zzc.zzcf = this.zzbp;
            zzc.zzcg = this.zzbr;
            zzc.zzch = this.zzbt;
            return new FaceDetector(new zza(this.mContext, zzc));
        }

        public Builder setClassificationType(int i) {
            if (i == 0 || i == 1) {
                this.zzbq = i;
                return this;
            }
            throw new IllegalArgumentException("Invalid classification type: " + i);
        }

        public Builder setLandmarkType(int i) {
            if (i == 0 || i == 1) {
                this.zzbo = i;
                return this;
            }
            throw new IllegalArgumentException("Invalid landmark type: " + i);
        }

        public Builder setMinFaceSize(float f) {
            if (f < BitmapDescriptorFactory.HUE_RED || f > 1.0f) {
                throw new IllegalArgumentException("Invalid proportional face size: " + f);
            }
            this.zzbt = f;
            return this;
        }

        public Builder setMode(int i) {
            switch (i) {
                case 0:
                case 1:
                    this.zzbs = i;
                    return this;
                default:
                    throw new IllegalArgumentException("Invalid mode: " + i);
            }
        }

        public Builder setProminentFaceOnly(boolean z) {
            this.zzbp = z;
            return this;
        }

        public Builder setTrackingEnabled(boolean z) {
            this.zzbr = z;
            return this;
        }
    }

    private FaceDetector() {
        this.zzbm = new zzc();
        this.mLock = new Object();
        this.mIsActive = true;
        throw new IllegalStateException("Default constructor called");
    }

    private FaceDetector(zza zza) {
        this.zzbm = new zzc();
        this.mLock = new Object();
        this.mIsActive = true;
        this.zzbn = zza;
    }

    public final SparseArray<Face> detect(Frame frame) {
        if (frame == null) {
            throw new IllegalArgumentException("No frame supplied.");
        }
        Face[] zzb;
        ByteBuffer grayscaleImageData = frame.getGrayscaleImageData();
        synchronized (this.mLock) {
            if (this.mIsActive) {
                zzb = this.zzbn.zzb(grayscaleImageData, zzk.zzc(frame));
            } else {
                throw new RuntimeException("Cannot use detector after release()");
            }
        }
        Set hashSet = new HashSet();
        SparseArray<Face> sparseArray = new SparseArray(zzb.length);
        int i = 0;
        for (Face face : zzb) {
            int id = face.getId();
            i = Math.max(i, id);
            if (hashSet.contains(Integer.valueOf(id))) {
                id = i + 1;
                i = id;
            }
            hashSet.add(Integer.valueOf(id));
            sparseArray.append(this.zzbm.zzb(id), face);
        }
        return sparseArray;
    }

    protected final void finalize() {
        try {
            synchronized (this.mLock) {
                if (this.mIsActive) {
                    Log.w("FaceDetector", "FaceDetector was not released with FaceDetector.release()");
                    release();
                }
            }
        } finally {
            super.finalize();
        }
    }

    public final boolean isOperational() {
        return this.zzbn.isOperational();
    }

    public final void release() {
        super.release();
        synchronized (this.mLock) {
            if (this.mIsActive) {
                this.zzbn.zzg();
                this.mIsActive = false;
                return;
            }
        }
    }

    public final boolean setFocus(int i) {
        boolean zzd;
        int zzc = this.zzbm.zzc(i);
        synchronized (this.mLock) {
            if (this.mIsActive) {
                zzd = this.zzbn.zzd(zzc);
            } else {
                throw new RuntimeException("Cannot use detector after release()");
            }
        }
        return zzd;
    }
}
