package com.thin.downloadmanager;

import android.os.Handler;
import android.os.Looper;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadRequestQueue {
    private Set<DownloadRequest> mCurrentRequests = new HashSet();
    private CallBackDelivery mDelivery;
    private DownloadDispatcher[] mDownloadDispatchers;
    private PriorityBlockingQueue<DownloadRequest> mDownloadQueue = new PriorityBlockingQueue();
    private AtomicInteger mSequenceGenerator = new AtomicInteger();

    class CallBackDelivery {
        private final Executor mCallBackExecutor;

        public CallBackDelivery(final Handler handler) {
            this.mCallBackExecutor = new Executor(DownloadRequestQueue.this) {
                public void execute(Runnable command) {
                    handler.post(command);
                }
            };
        }

        public void postDownloadComplete(final DownloadRequest request) {
            this.mCallBackExecutor.execute(new Runnable() {
                public void run() {
                    if (request.getDownloadListener() != null) {
                        request.getDownloadListener().onDownloadComplete(request.getDownloadId());
                    }
                    if (request.getStatusListener() != null) {
                        request.getStatusListener().onDownloadComplete(request);
                    }
                }
            });
        }

        public void postDownloadFailed(final DownloadRequest request, final int errorCode, final String errorMsg) {
            this.mCallBackExecutor.execute(new Runnable() {
                public void run() {
                    if (request.getDownloadListener() != null) {
                        request.getDownloadListener().onDownloadFailed(request.getDownloadId(), errorCode, errorMsg);
                    }
                    if (request.getStatusListener() != null) {
                        request.getStatusListener().onDownloadFailed(request, errorCode, errorMsg);
                    }
                }
            });
        }

        public void postProgressUpdate(DownloadRequest request, long totalBytes, long downloadedBytes, int progress) {
            final DownloadRequest downloadRequest = request;
            final long j = totalBytes;
            final long j2 = downloadedBytes;
            final int i = progress;
            this.mCallBackExecutor.execute(new Runnable() {
                public void run() {
                    if (downloadRequest.getDownloadListener() != null) {
                        downloadRequest.getDownloadListener().onProgress(downloadRequest.getDownloadId(), j, j2, i);
                    }
                    if (downloadRequest.getStatusListener() != null) {
                        downloadRequest.getStatusListener().onProgress(downloadRequest, j, j2, i);
                    }
                }
            });
        }
    }

    public DownloadRequestQueue() {
        initialize(new Handler(Looper.getMainLooper()));
    }

    public DownloadRequestQueue(int threadPoolSize) {
        initialize(new Handler(Looper.getMainLooper()), threadPoolSize);
    }

    public DownloadRequestQueue(Handler callbackHandler) throws InvalidParameterException {
        if (callbackHandler == null) {
            throw new InvalidParameterException("callbackHandler must not be null");
        }
        initialize(callbackHandler);
    }

    public void start() {
        stop();
        for (int i = 0; i < this.mDownloadDispatchers.length; i++) {
            DownloadDispatcher downloadDispatcher = new DownloadDispatcher(this.mDownloadQueue, this.mDelivery);
            this.mDownloadDispatchers[i] = downloadDispatcher;
            downloadDispatcher.start();
        }
    }

    int add(DownloadRequest request) {
        int downloadId = getDownloadId();
        request.setDownloadRequestQueue(this);
        synchronized (this.mCurrentRequests) {
            this.mCurrentRequests.add(request);
        }
        request.setDownloadId(downloadId);
        this.mDownloadQueue.add(request);
        return downloadId;
    }

    int query(int downloadId) {
        synchronized (this.mCurrentRequests) {
            for (DownloadRequest request : this.mCurrentRequests) {
                if (request.getDownloadId() == downloadId) {
                    int downloadState = request.getDownloadState();
                    return downloadState;
                }
            }
            return 64;
        }
    }

    void cancelAll() {
        synchronized (this.mCurrentRequests) {
            for (DownloadRequest request : this.mCurrentRequests) {
                request.cancel();
            }
            this.mCurrentRequests.clear();
        }
    }

    int cancel(int downloadId) {
        synchronized (this.mCurrentRequests) {
            for (DownloadRequest request : this.mCurrentRequests) {
                if (request.getDownloadId() == downloadId) {
                    request.cancel();
                    return 1;
                }
            }
            return 0;
        }
    }

    void finish(DownloadRequest request) {
        if (this.mCurrentRequests != null) {
            synchronized (this.mCurrentRequests) {
                this.mCurrentRequests.remove(request);
            }
        }
    }

    void release() {
        if (this.mCurrentRequests != null) {
            synchronized (this.mCurrentRequests) {
                this.mCurrentRequests.clear();
                this.mCurrentRequests = null;
            }
        }
        if (this.mDownloadQueue != null) {
            this.mDownloadQueue = null;
        }
        if (this.mDownloadDispatchers != null) {
            stop();
            for (int i = 0; i < this.mDownloadDispatchers.length; i++) {
                this.mDownloadDispatchers[i] = null;
            }
            this.mDownloadDispatchers = null;
        }
    }

    private void initialize(Handler callbackHandler) {
        this.mDownloadDispatchers = new DownloadDispatcher[Runtime.getRuntime().availableProcessors()];
        this.mDelivery = new CallBackDelivery(callbackHandler);
    }

    private void initialize(Handler callbackHandler, int threadPoolSize) {
        this.mDownloadDispatchers = new DownloadDispatcher[threadPoolSize];
        this.mDelivery = new CallBackDelivery(callbackHandler);
    }

    private void stop() {
        for (int i = 0; i < this.mDownloadDispatchers.length; i++) {
            if (this.mDownloadDispatchers[i] != null) {
                this.mDownloadDispatchers[i].quit();
            }
        }
    }

    private int getDownloadId() {
        return this.mSequenceGenerator.incrementAndGet();
    }
}
