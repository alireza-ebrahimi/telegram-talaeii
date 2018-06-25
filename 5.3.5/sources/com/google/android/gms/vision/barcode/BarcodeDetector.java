package com.google.android.gms.vision.barcode;

import android.content.Context;
import android.util.SparseArray;
import com.google.android.gms.internal.zzdkv;
import com.google.android.gms.internal.zzdkx;
import com.google.android.gms.internal.zzdld;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;

public final class BarcodeDetector extends Detector<Barcode> {
    private final zzdkx zzlgo;

    public static class Builder {
        private Context mContext;
        private zzdkv zzlgp = new zzdkv();

        public Builder(Context context) {
            this.mContext = context;
        }

        public BarcodeDetector build() {
            return new BarcodeDetector(new zzdkx(this.mContext, this.zzlgp));
        }

        public Builder setBarcodeFormats(int i) {
            this.zzlgp.zzlgq = i;
            return this;
        }
    }

    private BarcodeDetector() {
        throw new IllegalStateException("Default constructor called");
    }

    private BarcodeDetector(zzdkx zzdkx) {
        this.zzlgo = zzdkx;
    }

    public final SparseArray<Barcode> detect(Frame frame) {
        if (frame == null) {
            throw new IllegalArgumentException("No frame supplied.");
        }
        Barcode[] zza;
        zzdld zzc = zzdld.zzc(frame);
        if (frame.getBitmap() != null) {
            zza = this.zzlgo.zza(frame.getBitmap(), zzc);
            if (zza == null) {
                throw new IllegalArgumentException("Internal barcode detector error; check logcat output.");
            }
        }
        zza = this.zzlgo.zza(frame.getGrayscaleImageData(), zzc);
        SparseArray<Barcode> sparseArray = new SparseArray(zza.length);
        for (Barcode barcode : zza) {
            sparseArray.append(barcode.rawValue.hashCode(), barcode);
        }
        return sparseArray;
    }

    public final boolean isOperational() {
        return this.zzlgo.isOperational();
    }

    public final void release() {
        super.release();
        this.zzlgo.zzbln();
    }
}
