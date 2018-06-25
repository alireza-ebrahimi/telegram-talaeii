package com.google.android.gms.vision.text;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.SparseArray;
import com.google.android.gms.internal.vision.zzaa;
import com.google.android.gms.internal.vision.zzk;
import com.google.android.gms.internal.vision.zzm;
import com.google.android.gms.internal.vision.zzt;
import com.google.android.gms.internal.vision.zzv;
import com.google.android.gms.internal.vision.zzz;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.Frame.Metadata;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public final class TextRecognizer extends Detector<TextBlock> {
    private final zzz zzda;

    public static class Builder {
        private Context mContext;
        private zzaa zzdb = new zzaa();

        public Builder(Context context) {
            this.mContext = context;
        }

        public TextRecognizer build() {
            return new TextRecognizer(new zzz(this.mContext, this.zzdb));
        }
    }

    private TextRecognizer() {
        throw new IllegalStateException("Default constructor called");
    }

    private TextRecognizer(zzz zzz) {
        this.zzda = zzz;
    }

    public final SparseArray<TextBlock> detect(Frame frame) {
        zzv zzv = new zzv(new Rect());
        if (frame == null) {
            throw new IllegalArgumentException("No frame supplied.");
        }
        Bitmap bitmap;
        int i;
        zzk zzc = zzk.zzc(frame);
        if (frame.getBitmap() != null) {
            bitmap = frame.getBitmap();
        } else {
            byte[] array;
            Metadata metadata = frame.getMetadata();
            ByteBuffer grayscaleImageData = frame.getGrayscaleImageData();
            int format = metadata.getFormat();
            int i2 = zzc.width;
            int i3 = zzc.height;
            if (grayscaleImageData.hasArray() && grayscaleImageData.arrayOffset() == 0) {
                array = grayscaleImageData.array();
            } else {
                array = new byte[grayscaleImageData.capacity()];
                grayscaleImageData.get(array);
            }
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new YuvImage(array, format, i2, i3, null).compressToJpeg(new Rect(0, 0, i2, i3), 100, byteArrayOutputStream);
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(toByteArray, 0, toByteArray.length);
        }
        Bitmap zzb = zzm.zzb(bitmap, zzc);
        if (!zzv.zzdm.isEmpty()) {
            Rect rect;
            Rect rect2 = zzv.zzdm;
            i2 = frame.getMetadata().getWidth();
            i3 = frame.getMetadata().getHeight();
            switch (zzc.rotation) {
                case 1:
                    rect = new Rect(i3 - rect2.bottom, rect2.left, i3 - rect2.top, rect2.right);
                    break;
                case 2:
                    rect = new Rect(i2 - rect2.right, i3 - rect2.bottom, i2 - rect2.left, i3 - rect2.top);
                    break;
                case 3:
                    rect = new Rect(rect2.top, i2 - rect2.right, rect2.bottom, i2 - rect2.left);
                    break;
                default:
                    rect = rect2;
                    break;
            }
            zzv.zzdm.set(rect);
        }
        zzc.rotation = 0;
        zzt[] zza = this.zzda.zza(zzb, zzc, zzv);
        SparseArray sparseArray = new SparseArray();
        for (zzt zzt : zza) {
            SparseArray sparseArray2 = (SparseArray) sparseArray.get(zzt.zzdk);
            if (sparseArray2 == null) {
                sparseArray2 = new SparseArray();
                sparseArray.append(zzt.zzdk, sparseArray2);
            }
            sparseArray2.append(zzt.zzdl, zzt);
        }
        SparseArray<TextBlock> sparseArray3 = new SparseArray(sparseArray.size());
        for (i = 0; i < sparseArray.size(); i++) {
            sparseArray3.append(sparseArray.keyAt(i), new TextBlock((SparseArray) sparseArray.valueAt(i)));
        }
        return sparseArray3;
    }

    public final boolean isOperational() {
        return this.zzda.isOperational();
    }

    public final void release() {
        super.release();
        this.zzda.zzg();
    }
}
