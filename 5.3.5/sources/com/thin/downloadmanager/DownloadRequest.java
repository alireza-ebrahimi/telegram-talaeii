package com.thin.downloadmanager;

import android.net.Uri;
import java.util.HashMap;

public class DownloadRequest implements Comparable<DownloadRequest> {
    private boolean mCancelled = false;
    private HashMap<String, String> mCustomHeader;
    private boolean mDeleteDestinationFileOnFailure = true;
    private Uri mDestinationURI;
    private Object mDownloadContext;
    private int mDownloadId;
    private DownloadStatusListener mDownloadListener;
    private int mDownloadState;
    private DownloadStatusListenerV1 mDownloadStatusListenerV1;
    private Priority mPriority = Priority.NORMAL;
    private DownloadRequestQueue mRequestQueue;
    private RetryPolicy mRetryPolicy;
    private Uri mUri;

    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    public DownloadRequest(Uri uri) {
        if (uri == null) {
            throw new NullPointerException();
        }
        String scheme = uri.getScheme();
        if (scheme == null || !(scheme.equals("http") || scheme.equals("https"))) {
            throw new IllegalArgumentException("Can only download HTTP/HTTPS URIs: " + uri);
        }
        this.mCustomHeader = new HashMap();
        this.mDownloadState = 1;
        this.mUri = uri;
    }

    public Priority getPriority() {
        return this.mPriority;
    }

    public DownloadRequest setPriority(Priority priority) {
        this.mPriority = priority;
        return this;
    }

    public DownloadRequest addCustomHeader(String key, String value) {
        this.mCustomHeader.put(key, value);
        return this;
    }

    void setDownloadRequestQueue(DownloadRequestQueue downloadQueue) {
        this.mRequestQueue = downloadQueue;
    }

    public RetryPolicy getRetryPolicy() {
        return this.mRetryPolicy == null ? new DefaultRetryPolicy() : this.mRetryPolicy;
    }

    public DownloadRequest setRetryPolicy(RetryPolicy mRetryPolicy) {
        this.mRetryPolicy = mRetryPolicy;
        return this;
    }

    public final int getDownloadId() {
        return this.mDownloadId;
    }

    final void setDownloadId(int downloadId) {
        this.mDownloadId = downloadId;
    }

    int getDownloadState() {
        return this.mDownloadState;
    }

    void setDownloadState(int mDownloadState) {
        this.mDownloadState = mDownloadState;
    }

    DownloadStatusListener getDownloadListener() {
        return this.mDownloadListener;
    }

    @Deprecated
    public DownloadRequest setDownloadListener(DownloadStatusListener downloadListener) {
        this.mDownloadListener = downloadListener;
        return this;
    }

    DownloadStatusListenerV1 getStatusListener() {
        return this.mDownloadStatusListenerV1;
    }

    public DownloadRequest setStatusListener(DownloadStatusListenerV1 downloadStatusListenerV1) {
        this.mDownloadStatusListenerV1 = downloadStatusListenerV1;
        return this;
    }

    public Object getDownloadContext() {
        return this.mDownloadContext;
    }

    public DownloadRequest setDownloadContext(Object downloadContext) {
        this.mDownloadContext = downloadContext;
        return this;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public DownloadRequest setUri(Uri mUri) {
        this.mUri = mUri;
        return this;
    }

    public Uri getDestinationURI() {
        return this.mDestinationURI;
    }

    public DownloadRequest setDestinationURI(Uri destinationURI) {
        this.mDestinationURI = destinationURI;
        return this;
    }

    public boolean getDeleteDestinationFileOnFailure() {
        return this.mDeleteDestinationFileOnFailure;
    }

    public DownloadRequest setDeleteDestinationFileOnFailure(boolean deleteOnFailure) {
        this.mDeleteDestinationFileOnFailure = deleteOnFailure;
        return this;
    }

    public void cancel() {
        this.mCancelled = true;
    }

    public boolean isCancelled() {
        return this.mCancelled;
    }

    HashMap<String, String> getCustomHeaders() {
        return this.mCustomHeader;
    }

    void finish() {
        this.mRequestQueue.finish(this);
    }

    public int compareTo(DownloadRequest other) {
        Priority left = getPriority();
        Priority right = other.getPriority();
        if (left == right) {
            return this.mDownloadId - other.mDownloadId;
        }
        return right.ordinal() - left.ordinal();
    }
}
