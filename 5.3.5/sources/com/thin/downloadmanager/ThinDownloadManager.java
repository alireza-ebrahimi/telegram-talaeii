package com.thin.downloadmanager;

import android.os.Handler;
import com.thin.downloadmanager.util.Log;
import java.security.InvalidParameterException;

public class ThinDownloadManager implements DownloadManager {
    private DownloadRequestQueue mRequestQueue;

    public ThinDownloadManager() {
        this(true);
    }

    public ThinDownloadManager(boolean loggingEnabled) {
        this.mRequestQueue = new DownloadRequestQueue();
        this.mRequestQueue.start();
        setLoggingEnabled(loggingEnabled);
    }

    public ThinDownloadManager(Handler callbackHandler) throws InvalidParameterException {
        this.mRequestQueue = new DownloadRequestQueue(callbackHandler);
        this.mRequestQueue.start();
        setLoggingEnabled(true);
    }

    public ThinDownloadManager(int threadPoolSize) {
        this.mRequestQueue = new DownloadRequestQueue(threadPoolSize);
        this.mRequestQueue.start();
        setLoggingEnabled(true);
    }

    public int add(DownloadRequest request) throws IllegalArgumentException {
        checkReleased("add(...) called on a released ThinDownloadManager.");
        if (request != null) {
            return this.mRequestQueue.add(request);
        }
        throw new IllegalArgumentException("DownloadRequest cannot be null");
    }

    public int cancel(int downloadId) {
        checkReleased("cancel(...) called on a released ThinDownloadManager.");
        return this.mRequestQueue.cancel(downloadId);
    }

    public void cancelAll() {
        checkReleased("cancelAll() called on a released ThinDownloadManager.");
        this.mRequestQueue.cancelAll();
    }

    public int query(int downloadId) {
        checkReleased("query(...) called on a released ThinDownloadManager.");
        return this.mRequestQueue.query(downloadId);
    }

    public void release() {
        if (!isReleased()) {
            this.mRequestQueue.release();
            this.mRequestQueue = null;
        }
    }

    public boolean isReleased() {
        return this.mRequestQueue == null;
    }

    private void checkReleased(String errorMessage) {
        if (isReleased()) {
            throw new IllegalStateException(errorMessage);
        }
    }

    private static void setLoggingEnabled(boolean enabled) {
        Log.setEnabled(enabled);
    }
}
