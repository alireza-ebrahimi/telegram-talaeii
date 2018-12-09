package utils.volley;

import android.os.Process;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import utils.volley.Cache.Entry;

public class CacheDispatcher extends Thread {
    private static final boolean DEBUG = VolleyLog.DEBUG;
    private final Cache mCache;
    private final BlockingQueue<Request<?>> mCacheQueue;
    private final ResponseDelivery mDelivery;
    private final BlockingQueue<Request<?>> mNetworkQueue;
    private volatile boolean mQuit = false;
    private final WaitingRequestManager mWaitingRequestManager;

    private static class WaitingRequestManager implements Request$NetworkRequestCompleteListener {
        private final CacheDispatcher mCacheDispatcher;
        private final Map<String, List<Request<?>>> mWaitingRequests = new HashMap();

        WaitingRequestManager(CacheDispatcher cacheDispatcher) {
            this.mCacheDispatcher = cacheDispatcher;
        }

        private synchronized boolean maybeAddToWaitingRequests(Request<?> request) {
            boolean z = false;
            synchronized (this) {
                String cacheKey = request.getCacheKey();
                if (this.mWaitingRequests.containsKey(cacheKey)) {
                    List list = (List) this.mWaitingRequests.get(cacheKey);
                    if (list == null) {
                        list = new ArrayList();
                    }
                    request.addMarker("waiting-for-response");
                    list.add(request);
                    this.mWaitingRequests.put(cacheKey, list);
                    if (VolleyLog.DEBUG) {
                        VolleyLog.m14406d("Request for cacheKey=%s is in flight, putting on hold.", cacheKey);
                    }
                    z = true;
                } else {
                    this.mWaitingRequests.put(cacheKey, null);
                    request.setNetworkRequestCompleteListener(this);
                    if (VolleyLog.DEBUG) {
                        VolleyLog.m14406d("new request, sending to network %s", cacheKey);
                    }
                }
            }
            return z;
        }

        public synchronized void onNoUsableResponseReceived(Request<?> request) {
            String cacheKey = request.getCacheKey();
            List list = (List) this.mWaitingRequests.remove(cacheKey);
            if (!(list == null || list.isEmpty())) {
                if (VolleyLog.DEBUG) {
                    VolleyLog.m14409v("%d waiting requests for cacheKey=%s; resend to network", Integer.valueOf(list.size()), cacheKey);
                }
                Request request2 = (Request) list.remove(0);
                this.mWaitingRequests.put(cacheKey, list);
                request2.setNetworkRequestCompleteListener(this);
                try {
                    this.mCacheDispatcher.mNetworkQueue.put(request2);
                } catch (InterruptedException e) {
                    VolleyLog.m14407e("Couldn't add request to queue. %s", e.toString());
                    Thread.currentThread().interrupt();
                    this.mCacheDispatcher.quit();
                }
            }
        }

        public void onResponseReceived(Request<?> request, Response<?> response) {
            if (response.cacheEntry == null || response.cacheEntry.isExpired()) {
                onNoUsableResponseReceived(request);
                return;
            }
            String cacheKey = request.getCacheKey();
            synchronized (this) {
                List<Request> list = (List) this.mWaitingRequests.remove(cacheKey);
            }
            if (list != null) {
                if (VolleyLog.DEBUG) {
                    VolleyLog.m14409v("Releasing %d waiting requests for cacheKey=%s.", Integer.valueOf(list.size()), cacheKey);
                }
                for (Request postResponse : list) {
                    this.mCacheDispatcher.mDelivery.postResponse(postResponse, response);
                }
            }
        }
    }

    public CacheDispatcher(BlockingQueue<Request<?>> blockingQueue, BlockingQueue<Request<?>> blockingQueue2, Cache cache, ResponseDelivery responseDelivery) {
        this.mCacheQueue = blockingQueue;
        this.mNetworkQueue = blockingQueue2;
        this.mCache = cache;
        this.mDelivery = responseDelivery;
        this.mWaitingRequestManager = new WaitingRequestManager(this);
    }

    private void processRequest() {
        processRequest((Request) this.mCacheQueue.take());
    }

    void processRequest(final Request<?> request) {
        request.addMarker("cache-queue-take");
        if (request.isCanceled()) {
            request.finish("cache-discard-canceled");
            return;
        }
        Entry entry = this.mCache.get(request.getCacheKey());
        if (entry == null) {
            request.addMarker("cache-miss");
            if (!this.mWaitingRequestManager.maybeAddToWaitingRequests(request)) {
                this.mNetworkQueue.put(request);
            }
        } else if (entry.isExpired()) {
            request.addMarker("cache-hit-expired");
            request.setCacheEntry(entry);
            if (!this.mWaitingRequestManager.maybeAddToWaitingRequests(request)) {
                this.mNetworkQueue.put(request);
            }
        } else {
            request.addMarker("cache-hit");
            Response parseNetworkResponse = request.parseNetworkResponse(new NetworkResponse(entry.data, entry.responseHeaders));
            request.addMarker("cache-hit-parsed");
            if (entry.refreshNeeded()) {
                request.addMarker("cache-hit-refresh-needed");
                request.setCacheEntry(entry);
                parseNetworkResponse.intermediate = true;
                if (this.mWaitingRequestManager.maybeAddToWaitingRequests(request)) {
                    this.mDelivery.postResponse(request, parseNetworkResponse);
                    return;
                } else {
                    this.mDelivery.postResponse(request, parseNetworkResponse, new Runnable() {
                        public void run() {
                            try {
                                CacheDispatcher.this.mNetworkQueue.put(request);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    });
                    return;
                }
            }
            this.mDelivery.postResponse(request, parseNetworkResponse);
        }
    }

    public void quit() {
        this.mQuit = true;
        interrupt();
    }

    public void run() {
        if (DEBUG) {
            VolleyLog.m14409v("start new dispatcher", new Object[0]);
        }
        Process.setThreadPriority(10);
        this.mCache.initialize();
        while (true) {
            try {
                processRequest();
            } catch (InterruptedException e) {
                if (this.mQuit) {
                    return;
                }
            }
        }
    }
}
