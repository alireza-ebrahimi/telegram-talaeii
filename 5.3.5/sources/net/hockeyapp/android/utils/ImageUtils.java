package net.hockeyapp.android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 0;

    public static int determineOrientation(File file) throws IOException {
        Throwable th;
        InputStream input = null;
        try {
            InputStream input2 = new FileInputStream(file);
            try {
                int determineOrientation = determineOrientation(input2);
                if (input2 != null) {
                    input2.close();
                }
                return determineOrientation;
            } catch (Throwable th2) {
                th = th2;
                input = input2;
                if (input != null) {
                    input.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (input != null) {
                input.close();
            }
            throw th;
        }
    }

    public static int determineOrientation(Context context, Uri uri) throws IOException {
        InputStream input = null;
        try {
            input = context.getContentResolver().openInputStream(uri);
            int determineOrientation = determineOrientation(input);
            return determineOrientation;
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    public static int determineOrientation(InputStream input) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, options);
        if (options.outWidth == -1 || options.outHeight == -1) {
            return 0;
        }
        if (((float) options.outWidth) / ((float) options.outHeight) <= 1.0f) {
            return 0;
        }
        return 1;
    }

    public static Bitmap decodeSampledBitmap(File file, int reqWidth, int reqHeight) throws IOException {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public static Bitmap decodeSampledBitmap(Context context, Uri imageUri, int reqWidth, int reqHeight) throws IOException {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri), null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri), null, options);
    }

    private static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
