package utils.volley.toolbox;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import utils.volley.Request;
import utils.volley.RequestQueue;
import utils.volley.Response$ErrorListener;
import utils.volley.Response$Listener;
import utils.volley.VolleyError;

public class ImageLoader {
    private int mBatchResponseDelayMs = 100;
    private final HashMap<String, BatchedImageRequest> mBatchedResponses = new HashMap();
    private final ImageCache mCache;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final HashMap<String, BatchedImageRequest> mInFlightRequests = new HashMap();
    private final RequestQueue mRequestQueue;
    private Runnable mRunnable;

    public interface ImageListener extends Response$ErrorListener {
        void onResponse(ImageContainer imageContainer, boolean z);
    }

    /* renamed from: utils.volley.toolbox.ImageLoader$4 */
    class C53674 implements Runnable {
        C53674() {
        }

        public void run() {
            for (BatchedImageRequest batchedImageRequest : ImageLoader.this.mBatchedResponses.values()) {
                for (ImageContainer imageContainer : batchedImageRequest.mContainers) {
                    if (imageContainer.mListener != null) {
                        if (batchedImageRequest.getError() == null) {
                            imageContainer.mBitmap = batchedImageRequest.mResponseBitmap;
                            imageContainer.mListener.onResponse(imageContainer, false);
                        } else {
                            imageContainer.mListener.onErrorResponse(batchedImageRequest.getError());
                        }
                    }
                }
            }
            ImageLoader.this.mBatchedResponses.clear();
            ImageLoader.this.mRunnable = null;
        }
    }

    private static class BatchedImageRequest {
        private final List<ImageContainer> mContainers = new ArrayList();
        private VolleyError mError;
        private final Request<?> mRequest;
        private Bitmap mResponseBitmap;

        public BatchedImageRequest(Request<?> request, ImageContainer imageContainer) {
            this.mRequest = request;
            this.mContainers.add(imageContainer);
        }

        public void addContainer(ImageContainer imageContainer) {
            this.mContainers.add(imageContainer);
        }

        public VolleyError getError() {
            return this.mError;
        }

        public boolean removeContainerAndCancelIfNecessary(ImageContainer imageContainer) {
            this.mContainers.remove(imageContainer);
            if (this.mContainers.size() != 0) {
                return false;
            }
            this.mRequest.cancel();
            return true;
        }

        public void setError(VolleyError volleyError) {
            this.mError = volleyError;
        }
    }

    public interface ImageCache {
        Bitmap getBitmap(String str);

        void putBitmap(String str, Bitmap bitmap);
    }

    public class ImageContainer {
        private Bitmap mBitmap;
        private final String mCacheKey;
        private final ImageListener mListener;
        private final String mRequestUrl;

        public ImageContainer(Bitmap bitmap, String str, String str2, ImageListener imageListener) {
            this.mBitmap = bitmap;
            this.mRequestUrl = str;
            this.mCacheKey = str2;
            this.mListener = imageListener;
        }

        public void cancelRequest() {
            if (this.mListener != null) {
                BatchedImageRequest batchedImageRequest = (BatchedImageRequest) ImageLoader.this.mInFlightRequests.get(this.mCacheKey);
                if (batchedImageRequest == null) {
                    batchedImageRequest = (BatchedImageRequest) ImageLoader.this.mBatchedResponses.get(this.mCacheKey);
                    if (batchedImageRequest != null) {
                        batchedImageRequest.removeContainerAndCancelIfNecessary(this);
                        if (batchedImageRequest.mContainers.size() == 0) {
                            ImageLoader.this.mBatchedResponses.remove(this.mCacheKey);
                        }
                    }
                } else if (batchedImageRequest.removeContainerAndCancelIfNecessary(this)) {
                    ImageLoader.this.mInFlightRequests.remove(this.mCacheKey);
                }
            }
        }

        public Bitmap getBitmap() {
            return this.mBitmap;
        }

        public String getRequestUrl() {
            return this.mRequestUrl;
        }
    }

    public ImageLoader(RequestQueue requestQueue, ImageCache imageCache) {
        this.mRequestQueue = requestQueue;
        this.mCache = imageCache;
    }

    private void batchResponse(String str, BatchedImageRequest batchedImageRequest) {
        this.mBatchedResponses.put(str, batchedImageRequest);
        if (this.mRunnable == null) {
            this.mRunnable = new C53674();
            this.mHandler.postDelayed(this.mRunnable, (long) this.mBatchResponseDelayMs);
        }
    }

    private static String getCacheKey(String str, int i, int i2, ScaleType scaleType) {
        return new StringBuilder(str.length() + 12).append("#W").append(i).append("#H").append(i2).append("#S").append(scaleType.ordinal()).append(str).toString();
    }

    public static ImageListener getImageListener(final ImageView imageView, final int i, final int i2) {
        return new ImageListener() {
            public void onErrorResponse(VolleyError volleyError) {
                if (i2 != 0) {
                    imageView.setImageResource(i2);
                }
            }

            public void onResponse(ImageContainer imageContainer, boolean z) {
                if (imageContainer.getBitmap() != null) {
                    imageView.setImageBitmap(imageContainer.getBitmap());
                } else if (i != 0) {
                    imageView.setImageResource(i);
                }
            }
        };
    }

    private void throwIfNotOnMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("ImageLoader must be invoked from the main thread.");
        }
    }

    public ImageContainer get(String str, ImageListener imageListener) {
        return get(str, imageListener, 0, 0);
    }

    public ImageContainer get(String str, ImageListener imageListener, int i, int i2) {
        return get(str, imageListener, i, i2, ScaleType.CENTER_INSIDE);
    }

    public ImageContainer get(String str, ImageListener imageListener, int i, int i2, ScaleType scaleType) {
        throwIfNotOnMainThread();
        String cacheKey = getCacheKey(str, i, i2, scaleType);
        Bitmap bitmap = this.mCache.getBitmap(cacheKey);
        if (bitmap != null) {
            ImageContainer imageContainer = new ImageContainer(bitmap, str, null, null);
            imageListener.onResponse(imageContainer, true);
            return imageContainer;
        }
        imageContainer = new ImageContainer(null, str, cacheKey, imageListener);
        imageListener.onResponse(imageContainer, true);
        BatchedImageRequest batchedImageRequest = (BatchedImageRequest) this.mInFlightRequests.get(cacheKey);
        if (batchedImageRequest != null) {
            batchedImageRequest.addContainer(imageContainer);
            return imageContainer;
        }
        Request makeImageRequest = makeImageRequest(str, i, i2, scaleType, cacheKey);
        this.mRequestQueue.add(makeImageRequest);
        this.mInFlightRequests.put(cacheKey, new BatchedImageRequest(makeImageRequest, imageContainer));
        return imageContainer;
    }

    public boolean isCached(String str, int i, int i2) {
        return isCached(str, i, i2, ScaleType.CENTER_INSIDE);
    }

    public boolean isCached(String str, int i, int i2, ScaleType scaleType) {
        throwIfNotOnMainThread();
        return this.mCache.getBitmap(getCacheKey(str, i, i2, scaleType)) != null;
    }

    protected Request<Bitmap> makeImageRequest(String str, int i, int i2, ScaleType scaleType, final String str2) {
        return new ImageRequest(str, new Response$Listener<Bitmap>() {
            public void onResponse(Bitmap bitmap) {
                ImageLoader.this.onGetImageSuccess(str2, bitmap);
            }
        }, i, i2, scaleType, Config.RGB_565, new Response$ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                ImageLoader.this.onGetImageError(str2, volleyError);
            }
        });
    }

    protected void onGetImageError(String str, VolleyError volleyError) {
        BatchedImageRequest batchedImageRequest = (BatchedImageRequest) this.mInFlightRequests.remove(str);
        if (batchedImageRequest != null) {
            batchedImageRequest.setError(volleyError);
            batchResponse(str, batchedImageRequest);
        }
    }

    protected void onGetImageSuccess(String str, Bitmap bitmap) {
        this.mCache.putBitmap(str, bitmap);
        BatchedImageRequest batchedImageRequest = (BatchedImageRequest) this.mInFlightRequests.remove(str);
        if (batchedImageRequest != null) {
            batchedImageRequest.mResponseBitmap = bitmap;
            batchResponse(str, batchedImageRequest);
        }
    }

    public void setBatchedResponseDelay(int i) {
        this.mBatchResponseDelayMs = i;
    }
}
