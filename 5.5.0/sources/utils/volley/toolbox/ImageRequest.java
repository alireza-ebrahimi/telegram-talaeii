package utils.volley.toolbox;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.widget.ImageView.ScaleType;
import utils.volley.DefaultRetryPolicy;
import utils.volley.NetworkResponse;
import utils.volley.ParseError;
import utils.volley.Request;
import utils.volley.Request$Priority;
import utils.volley.Response;
import utils.volley.Response$ErrorListener;
import utils.volley.Response$Listener;
import utils.volley.VolleyLog;

public class ImageRequest extends Request<Bitmap> {
    public static final float DEFAULT_IMAGE_BACKOFF_MULT = 2.0f;
    public static final int DEFAULT_IMAGE_MAX_RETRIES = 2;
    public static final int DEFAULT_IMAGE_TIMEOUT_MS = 1000;
    private static final Object sDecodeLock = new Object();
    private final Config mDecodeConfig;
    private Response$Listener<Bitmap> mListener;
    private final Object mLock;
    private final int mMaxHeight;
    private final int mMaxWidth;
    private final ScaleType mScaleType;

    @Deprecated
    public ImageRequest(String str, Response$Listener<Bitmap> response$Listener, int i, int i2, Config config, Response$ErrorListener response$ErrorListener) {
        this(str, response$Listener, i, i2, ScaleType.CENTER_INSIDE, config, response$ErrorListener);
    }

    public ImageRequest(String str, Response$Listener<Bitmap> response$Listener, int i, int i2, ScaleType scaleType, Config config, Response$ErrorListener response$ErrorListener) {
        super(0, str, response$ErrorListener);
        this.mLock = new Object();
        setRetryPolicy(new DefaultRetryPolicy(1000, 2, 2.0f));
        this.mListener = response$Listener;
        this.mDecodeConfig = config;
        this.mMaxWidth = i;
        this.mMaxHeight = i2;
        this.mScaleType = scaleType;
    }

    private Response<Bitmap> doParse(NetworkResponse networkResponse) {
        Object decodeByteArray;
        byte[] bArr = networkResponse.data;
        Options options = new Options();
        if (this.mMaxWidth == 0 && this.mMaxHeight == 0) {
            options.inPreferredConfig = this.mDecodeConfig;
            decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        } else {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            int i = options.outWidth;
            int i2 = options.outHeight;
            int resizedDimension = getResizedDimension(this.mMaxWidth, this.mMaxHeight, i, i2, this.mScaleType);
            int resizedDimension2 = getResizedDimension(this.mMaxHeight, this.mMaxWidth, i2, i, this.mScaleType);
            options.inJustDecodeBounds = false;
            options.inSampleSize = findBestSampleSize(i, i2, resizedDimension, resizedDimension2);
            Bitmap decodeByteArray2 = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            if (decodeByteArray2 == null || (decodeByteArray2.getWidth() <= resizedDimension && decodeByteArray2.getHeight() <= resizedDimension2)) {
                Bitmap bitmap = decodeByteArray2;
            } else {
                decodeByteArray = Bitmap.createScaledBitmap(decodeByteArray2, resizedDimension, resizedDimension2, true);
                decodeByteArray2.recycle();
            }
        }
        return decodeByteArray == null ? Response.error(new ParseError(networkResponse)) : Response.success(decodeByteArray, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    static int findBestSampleSize(int i, int i2, int i3, int i4) {
        float f = 1.0f;
        while (((double) (f * 2.0f)) <= Math.min(((double) i) / ((double) i3), ((double) i2) / ((double) i4))) {
            f *= 2.0f;
        }
        return (int) f;
    }

    private static int getResizedDimension(int i, int i2, int i3, int i4, ScaleType scaleType) {
        if (i == 0 && i2 == 0) {
            return i3;
        }
        if (scaleType == ScaleType.FIT_XY) {
            return i == 0 ? i3 : i;
        } else {
            if (i == 0) {
                return (int) ((((double) i2) / ((double) i4)) * ((double) i3));
            }
            if (i2 == 0) {
                return i;
            }
            double d = ((double) i4) / ((double) i3);
            return scaleType == ScaleType.CENTER_CROP ? ((double) i) * d < ((double) i2) ? (int) (((double) i2) / d) : i : ((double) i) * d > ((double) i2) ? (int) (((double) i2) / d) : i;
        }
    }

    public void cancel() {
        super.cancel();
        synchronized (this.mLock) {
            this.mListener = null;
        }
    }

    protected void deliverResponse(Bitmap bitmap) {
        synchronized (this.mLock) {
            Response$Listener response$Listener = this.mListener;
        }
        if (response$Listener != null) {
            response$Listener.onResponse(bitmap);
        }
    }

    public Request$Priority getPriority() {
        return Request$Priority.LOW;
    }

    protected Response<Bitmap> parseNetworkResponse(NetworkResponse networkResponse) {
        Response<Bitmap> doParse;
        synchronized (sDecodeLock) {
            try {
                doParse = doParse(networkResponse);
            } catch (Throwable e) {
                VolleyLog.e("Caught OOM for %d byte image, url=%s", new Object[]{Integer.valueOf(networkResponse.data.length), getUrl()});
                doParse = Response.error(new ParseError(e));
            }
        }
        return doParse;
    }
}
