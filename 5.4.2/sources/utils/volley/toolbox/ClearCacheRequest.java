package utils.volley.toolbox;

import android.os.Handler;
import android.os.Looper;
import utils.volley.Cache;
import utils.volley.NetworkResponse;
import utils.volley.Request;
import utils.volley.Request$Priority;
import utils.volley.Response;

public class ClearCacheRequest extends Request<Object> {
    private final Cache mCache;
    private final Runnable mCallback;

    public ClearCacheRequest(Cache cache, Runnable runnable) {
        super(0, null, null);
        this.mCache = cache;
        this.mCallback = runnable;
    }

    protected void deliverResponse(Object obj) {
    }

    public Request$Priority getPriority() {
        return Request$Priority.IMMEDIATE;
    }

    public boolean isCanceled() {
        this.mCache.clear();
        if (this.mCallback != null) {
            new Handler(Looper.getMainLooper()).postAtFrontOfQueue(this.mCallback);
        }
        return true;
    }

    protected Response<Object> parseNetworkResponse(NetworkResponse networkResponse) {
        return null;
    }
}
