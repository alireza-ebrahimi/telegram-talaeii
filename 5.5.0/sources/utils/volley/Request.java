package utils.volley;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;
import utils.volley.Cache.Entry;
import utils.volley.VolleyLog.MarkerLog;

public abstract class Request<T> implements Comparable<Request<T>> {
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private Entry mCacheEntry;
    private boolean mCanceled;
    private final int mDefaultTrafficStatsTag;
    private Response$ErrorListener mErrorListener;
    private final MarkerLog mEventLog;
    private final Object mLock;
    private final int mMethod;
    private Request$NetworkRequestCompleteListener mRequestCompleteListener;
    private RequestQueue mRequestQueue;
    private boolean mResponseDelivered;
    private RetryPolicy mRetryPolicy;
    private Integer mSequence;
    private boolean mShouldCache;
    private boolean mShouldRetryServerErrors;
    private Object mTag;
    private final String mUrl;

    public Request(int i, String str, Response$ErrorListener response$ErrorListener) {
        this.mEventLog = MarkerLog.ENABLED ? new MarkerLog() : null;
        this.mLock = new Object();
        this.mShouldCache = true;
        this.mCanceled = false;
        this.mResponseDelivered = false;
        this.mShouldRetryServerErrors = false;
        this.mCacheEntry = null;
        this.mMethod = i;
        this.mUrl = str;
        this.mErrorListener = response$ErrorListener;
        setRetryPolicy(new DefaultRetryPolicy());
        this.mDefaultTrafficStatsTag = findDefaultTrafficStatsTag(str);
    }

    @Deprecated
    public Request(String str, Response$ErrorListener response$ErrorListener) {
        this(-1, str, response$ErrorListener);
    }

    private byte[] encodeParameters(Map<String, String> map, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (Map.Entry entry : map.entrySet()) {
                stringBuilder.append(URLEncoder.encode((String) entry.getKey(), str));
                stringBuilder.append('=');
                stringBuilder.append(URLEncoder.encode((String) entry.getValue(), str));
                stringBuilder.append('&');
            }
            return stringBuilder.toString().getBytes(str);
        } catch (Throwable e) {
            throw new RuntimeException("Encoding not supported: " + str, e);
        }
    }

    private static int findDefaultTrafficStatsTag(String str) {
        if (!TextUtils.isEmpty(str)) {
            Uri parse = Uri.parse(str);
            if (parse != null) {
                String host = parse.getHost();
                if (host != null) {
                    return host.hashCode();
                }
            }
        }
        return 0;
    }

    public void addMarker(String str) {
        if (MarkerLog.ENABLED) {
            this.mEventLog.add(str, Thread.currentThread().getId());
        }
    }

    public void cancel() {
        synchronized (this.mLock) {
            this.mCanceled = true;
            this.mErrorListener = null;
        }
    }

    public int compareTo(Request<T> request) {
        Request$Priority priority = getPriority();
        Request$Priority priority2 = request.getPriority();
        return priority == priority2 ? this.mSequence.intValue() - request.mSequence.intValue() : priority2.ordinal() - priority.ordinal();
    }

    public void deliverError(VolleyError volleyError) {
        synchronized (this.mLock) {
            Response$ErrorListener response$ErrorListener = this.mErrorListener;
        }
        if (response$ErrorListener != null) {
            response$ErrorListener.onErrorResponse(volleyError);
        }
    }

    protected abstract void deliverResponse(T t);

    void finish(String str) {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.finish(this);
        }
        if (MarkerLog.ENABLED) {
            long id = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new Request$1(this, str, id));
                return;
            }
            this.mEventLog.add(str, id);
            this.mEventLog.finish(toString());
        }
    }

    public byte[] getBody() {
        Map params = getParams();
        return (params == null || params.size() <= 0) ? null : encodeParameters(params, getParamsEncoding());
    }

    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    public Entry getCacheEntry() {
        return this.mCacheEntry;
    }

    public String getCacheKey() {
        return getUrl();
    }

    public Response$ErrorListener getErrorListener() {
        Response$ErrorListener response$ErrorListener;
        synchronized (this.mLock) {
            response$ErrorListener = this.mErrorListener;
        }
        return response$ErrorListener;
    }

    public Map<String, String> getHeaders() {
        return Collections.emptyMap();
    }

    public int getMethod() {
        return this.mMethod;
    }

    protected Map<String, String> getParams() {
        return null;
    }

    protected String getParamsEncoding() {
        return "UTF-8";
    }

    @Deprecated
    public byte[] getPostBody() {
        Map postParams = getPostParams();
        return (postParams == null || postParams.size() <= 0) ? null : encodeParameters(postParams, getPostParamsEncoding());
    }

    @Deprecated
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    @Deprecated
    protected Map<String, String> getPostParams() {
        return getParams();
    }

    @Deprecated
    protected String getPostParamsEncoding() {
        return getParamsEncoding();
    }

    public Request$Priority getPriority() {
        return Request$Priority.NORMAL;
    }

    public RetryPolicy getRetryPolicy() {
        return this.mRetryPolicy;
    }

    public final int getSequence() {
        if (this.mSequence != null) {
            return this.mSequence.intValue();
        }
        throw new IllegalStateException("getSequence called before setSequence");
    }

    public Object getTag() {
        return this.mTag;
    }

    public final int getTimeoutMs() {
        return this.mRetryPolicy.getCurrentTimeout();
    }

    public int getTrafficStatsTag() {
        return this.mDefaultTrafficStatsTag;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public boolean hasHadResponseDelivered() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mResponseDelivered;
        }
        return z;
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mCanceled;
        }
        return z;
    }

    public void markDelivered() {
        synchronized (this.mLock) {
            this.mResponseDelivered = true;
        }
    }

    void notifyListenerResponseNotUsable() {
        synchronized (this.mLock) {
            Request$NetworkRequestCompleteListener request$NetworkRequestCompleteListener = this.mRequestCompleteListener;
        }
        if (request$NetworkRequestCompleteListener != null) {
            request$NetworkRequestCompleteListener.onNoUsableResponseReceived(this);
        }
    }

    void notifyListenerResponseReceived(Response<?> response) {
        synchronized (this.mLock) {
            Request$NetworkRequestCompleteListener request$NetworkRequestCompleteListener = this.mRequestCompleteListener;
        }
        if (request$NetworkRequestCompleteListener != null) {
            request$NetworkRequestCompleteListener.onResponseReceived(this, response);
        }
    }

    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return volleyError;
    }

    protected abstract Response<T> parseNetworkResponse(NetworkResponse networkResponse);

    public Request<?> setCacheEntry(Entry entry) {
        this.mCacheEntry = entry;
        return this;
    }

    void setNetworkRequestCompleteListener(Request$NetworkRequestCompleteListener request$NetworkRequestCompleteListener) {
        synchronized (this.mLock) {
            this.mRequestCompleteListener = request$NetworkRequestCompleteListener;
        }
    }

    public Request<?> setRequestQueue(RequestQueue requestQueue) {
        this.mRequestQueue = requestQueue;
        return this;
    }

    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        this.mRetryPolicy = retryPolicy;
        return this;
    }

    public final Request<?> setSequence(int i) {
        this.mSequence = Integer.valueOf(i);
        return this;
    }

    public final Request<?> setShouldCache(boolean z) {
        this.mShouldCache = z;
        return this;
    }

    public final Request<?> setShouldRetryServerErrors(boolean z) {
        this.mShouldRetryServerErrors = z;
        return this;
    }

    public Request<?> setTag(Object obj) {
        this.mTag = obj;
        return this;
    }

    public final boolean shouldCache() {
        return this.mShouldCache;
    }

    public final boolean shouldRetryServerErrors() {
        return this.mShouldRetryServerErrors;
    }

    public String toString() {
        return (isCanceled() ? "[X] " : "[ ] ") + getUrl() + " " + ("0x" + Integer.toHexString(getTrafficStatsTag())) + " " + getPriority() + " " + this.mSequence;
    }
}
