package com.google.android.gms.internal.vision;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public final class zzm {
    public static Bitmap zzb(Bitmap bitmap, zzk zzk) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (zzk.rotation != 0) {
            int i;
            Matrix matrix = new Matrix();
            switch (zzk.rotation) {
                case 0:
                    i = 0;
                    break;
                case 1:
                    i = 90;
                    break;
                case 2:
                    i = 180;
                    break;
                case 3:
                    i = 270;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported rotation degree.");
            }
            matrix.postRotate((float) i);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        }
        if (zzk.rotation == 1 || zzk.rotation == 3) {
            zzk.width = height;
            zzk.height = width;
        }
        return bitmap;
    }
}
