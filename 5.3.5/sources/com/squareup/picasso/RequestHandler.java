package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.NetworkInfo;
import com.squareup.picasso.Picasso.LoadedFrom;
import java.io.IOException;
import java.io.InputStream;

public abstract class RequestHandler {

    public static final class Result {
        private final Bitmap bitmap;
        private final int exifOrientation;
        private final LoadedFrom loadedFrom;
        private final InputStream stream;

        public Result(Bitmap bitmap, LoadedFrom loadedFrom) {
            this((Bitmap) Utils.checkNotNull(bitmap, "bitmap == null"), null, loadedFrom, 0);
        }

        public Result(InputStream stream, LoadedFrom loadedFrom) {
            this(null, (InputStream) Utils.checkNotNull(stream, "stream == null"), loadedFrom, 0);
        }

        Result(Bitmap bitmap, InputStream stream, LoadedFrom loadedFrom, int exifOrientation) {
            int i;
            int i2 = 1;
            if (bitmap != null) {
                i = 1;
            } else {
                i = 0;
            }
            if (stream == null) {
                i2 = 0;
            }
            if ((i2 ^ i) == 0) {
                throw new AssertionError();
            }
            this.bitmap = bitmap;
            this.stream = stream;
            this.loadedFrom = (LoadedFrom) Utils.checkNotNull(loadedFrom, "loadedFrom == null");
            this.exifOrientation = exifOrientation;
        }

        public Bitmap getBitmap() {
            return this.bitmap;
        }

        public InputStream getStream() {
            return this.stream;
        }

        public LoadedFrom getLoadedFrom() {
            return this.loadedFrom;
        }

        int getExifOrientation() {
            return this.exifOrientation;
        }
    }

    public abstract boolean canHandleRequest(Request request);

    public abstract Result load(Request request, int i) throws IOException;

    int getRetryCount() {
        return 0;
    }

    boolean shouldRetry(boolean airplaneMode, NetworkInfo info) {
        return false;
    }

    boolean supportsReplay() {
        return false;
    }

    static Options createBitmapOptions(Request data) {
        boolean justBounds = data.hasSize();
        boolean hasConfig = data.config != null;
        Options options = null;
        if (justBounds || hasConfig) {
            options = new Options();
            options.inJustDecodeBounds = justBounds;
            if (hasConfig) {
                options.inPreferredConfig = data.config;
            }
        }
        return options;
    }

    static boolean requiresInSampleSize(Options options) {
        return options != null && options.inJustDecodeBounds;
    }

    static void calculateInSampleSize(int reqWidth, int reqHeight, Options options, Request request) {
        calculateInSampleSize(reqWidth, reqHeight, options.outWidth, options.outHeight, options, request);
    }

    static void calculateInSampleSize(int reqWidth, int reqHeight, int width, int height, Options options, Request request) {
        int sampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (reqHeight == 0) {
                sampleSize = (int) Math.floor((double) (((float) width) / ((float) reqWidth)));
            } else if (reqWidth == 0) {
                sampleSize = (int) Math.floor((double) (((float) height) / ((float) reqHeight)));
            } else {
                int heightRatio = (int) Math.floor((double) (((float) height) / ((float) reqHeight)));
                int widthRatio = (int) Math.floor((double) (((float) width) / ((float) reqWidth)));
                if (request.centerInside) {
                    sampleSize = Math.max(heightRatio, widthRatio);
                } else {
                    sampleSize = Math.min(heightRatio, widthRatio);
                }
            }
        }
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
    }
}
