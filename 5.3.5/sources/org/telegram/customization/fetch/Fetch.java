package org.telegram.customization.fetch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.telegram.customization.fetch.callback.FetchCall;
import org.telegram.customization.fetch.callback.FetchTask;
import org.telegram.customization.fetch.exception.EnqueueException;
import org.telegram.customization.fetch.listener.FetchListener;
import org.telegram.customization.fetch.request.Request;
import org.telegram.customization.fetch.request.RequestInfo;

public final class Fetch implements FetchConst {
    private static final Callback callsCallback = new C08901();
    private static final ConcurrentMap<Request, FetchCallRunnable> callsMap = new ConcurrentHashMap();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final LocalBroadcastManager broadcastManager;
    private final Context context;
    private final DatabaseHelper dbHelper;
    private volatile boolean isReleased = false;
    private final List<FetchListener> listeners = new ArrayList();
    private final BroadcastReceiver networkReceiver = new C08945();
    private final BroadcastReceiver updateReceiver = new C08934();

    /* renamed from: org.telegram.customization.fetch.Fetch$1 */
    static class C08901 implements Callback {
        C08901() {
        }

        public void onDone(Request request) {
            Fetch.callsMap.remove(request);
        }
    }

    /* renamed from: org.telegram.customization.fetch.Fetch$4 */
    class C08934 extends BroadcastReceiver {
        private long downloadedBytes;
        private int error;
        private long fileSize;
        private long id;
        private int progress;
        private int status;

        C08934() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                this.id = intent.getLongExtra(FetchService.EXTRA_ID, -1);
                this.status = intent.getIntExtra(FetchService.EXTRA_STATUS, -1);
                this.progress = intent.getIntExtra(FetchService.EXTRA_PROGRESS, -1);
                this.downloadedBytes = intent.getLongExtra(FetchService.EXTRA_DOWNLOADED_BYTES, -1);
                this.fileSize = intent.getLongExtra(FetchService.EXTRA_FILE_SIZE, -1);
                this.error = intent.getIntExtra(FetchService.EXTRA_ERROR, -1);
                try {
                    for (FetchListener listener : Fetch.this.listeners) {
                        listener.onUpdate(this.id, this.status, this.progress, this.downloadedBytes, this.fileSize, this.error);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /* renamed from: org.telegram.customization.fetch.Fetch$5 */
    class C08945 extends BroadcastReceiver {
        C08945() {
        }

        public void onReceive(Context context, Intent intent) {
            FetchService.processPendingRequests(context);
        }
    }

    public static class Settings {
        private final Context context;
        private final List<Bundle> settings = new ArrayList();

        public Settings(@NonNull Context context) {
            if (context == null) {
                throw new NullPointerException("Context cannot be null");
            }
            this.context = context;
        }

        public Settings enableLogging(boolean enabled) {
            Bundle extras = new Bundle();
            extras.putInt(FetchService.ACTION_TYPE, FetchService.ACTION_LOGGING);
            extras.putBoolean(FetchService.EXTRA_LOGGING_ID, enabled);
            this.settings.add(extras);
            return this;
        }

        public Settings setAllowedNetwork(int networkType) {
            int type = 200;
            if (networkType == FetchConst.NETWORK_WIFI) {
                type = FetchConst.NETWORK_WIFI;
            }
            Bundle extras = new Bundle();
            extras.putInt(FetchService.ACTION_TYPE, FetchService.ACTION_NETWORK);
            extras.putInt(FetchService.EXTRA_NETWORK_ID, type);
            this.settings.add(extras);
            return this;
        }

        public Settings setConcurrentDownloadsLimit(int limit) {
            Bundle extras = new Bundle();
            extras.putInt(FetchService.ACTION_TYPE, FetchService.ACTION_CONCURRENT_DOWNLOADS_LIMIT);
            extras.putInt(FetchService.EXTRA_CONCURRENT_DOWNLOADS_LIMIT, limit);
            this.settings.add(extras);
            return this;
        }

        public Settings setOnUpdateInterval(long intervalMs) {
            Bundle extras = new Bundle();
            extras.putInt(FetchService.ACTION_TYPE, FetchService.ACTION_ON_UPDATE_INTERVAL);
            extras.putLong(FetchService.EXTRA_ON_UPDATE_INTERVAL, intervalMs);
            this.settings.add(extras);
            return this;
        }

        public void apply() {
            for (Bundle setting : this.settings) {
                FetchService.sendToService(this.context, setting);
            }
        }
    }

    private Fetch(Context context) {
        this.context = context.getApplicationContext();
        this.broadcastManager = LocalBroadcastManager.getInstance(this.context);
        this.dbHelper = DatabaseHelper.getInstance(this.context);
        this.dbHelper.setLoggingEnabled(isLoggingEnabled());
        this.broadcastManager.registerReceiver(this.updateReceiver, FetchService.getEventUpdateFilter());
        this.context.registerReceiver(this.networkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        startService(this.context);
    }

    public static void startService(@NonNull Context context) {
        FetchService.processPendingRequests(context);
    }

    public static Fetch getInstance(@NonNull Context context) {
        return newInstance(context);
    }

    public static Fetch newInstance(@NonNull Context context) {
        if (context != null) {
            return new Fetch(context);
        }
        throw new NullPointerException("Context cannot be null");
    }

    public static void call(@NonNull Request request, @NonNull FetchCall<String> fetchCall) {
        if (request == null) {
            throw new NullPointerException("Request cannot be null");
        } else if (fetchCall == null) {
            throw new NullPointerException("FetchCall cannot be null");
        } else if (!callsMap.containsKey(request)) {
            FetchCallRunnable callRunnable = new FetchCallRunnable(request, fetchCall, callsCallback);
            callsMap.put(request, callRunnable);
            new Thread(callRunnable).start();
        }
    }

    public static void cancelCall(@NonNull Request request) {
        if (request != null && callsMap.containsKey(request)) {
            FetchCallRunnable fetchCallRunnable = (FetchCallRunnable) callsMap.get(request);
            if (fetchCallRunnable != null) {
                fetchCallRunnable.interrupt();
            }
        }
    }

    public void release() {
        if (!isReleased()) {
            setReleased(true);
            this.listeners.clear();
            this.broadcastManager.unregisterReceiver(this.updateReceiver);
            this.context.unregisterReceiver(this.networkReceiver);
        }
    }

    public void addFetchListener(@NonNull FetchListener fetchListener) {
        Utils.throwIfNotUsable(this);
        if (fetchListener == null) {
            throw new NullPointerException("fetchListener cannot be null");
        } else if (!this.listeners.contains(fetchListener)) {
            this.listeners.add(fetchListener);
        }
    }

    public void removeFetchListener(@NonNull FetchListener fetchListener) {
        Utils.throwIfNotUsable(this);
        if (fetchListener != null) {
            this.listeners.remove(fetchListener);
        }
    }

    public void removeFetchListeners() {
        Utils.throwIfNotUsable(this);
        this.listeners.clear();
    }

    public long enqueue(@NonNull Request request, long fileSize) {
        Utils.throwIfNotUsable(this);
        if (request == null) {
            throw new NullPointerException("Request cannot be null");
        }
        long id = Utils.generateRequestId();
        try {
            String url = request.getUrl();
            String filePath = request.getFilePath();
            int priority = request.getPriority();
            String headers = Utils.headerListToString(request.getHeaders(), isLoggingEnabled());
            long downloadedBytes = 0;
            File file = Utils.getFile(filePath);
            if (file.exists()) {
                downloadedBytes = file.length();
            }
            if (this.dbHelper.insert(id, url, filePath, FetchConst.STATUS_QUEUED, headers, downloadedBytes, fileSize, priority, -1)) {
                startService(this.context);
                return id;
            }
            throw new EnqueueException("could not insert request", FetchConst.ERROR_ENQUEUE_ERROR);
        } catch (EnqueueException e) {
            if (isLoggingEnabled()) {
                e.printStackTrace();
            }
            return -1;
        }
    }

    @NonNull
    public List<Long> enqueue(@NonNull List<Request> requests) {
        Utils.throwIfNotUsable(this);
        if (requests == null) {
            throw new NullPointerException("Request list cannot be null");
        } else if (requests.size() < 1) {
            return new ArrayList(0);
        } else {
            List<Long> arrayList = new ArrayList(requests.size());
            List<String> statements = new ArrayList();
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.dbHelper.getInsertStatementOpen());
                for (Request request : requests) {
                    long id = -1;
                    if (request != null) {
                        id = Utils.generateRequestId();
                        String url = request.getUrl();
                        String filePath = request.getFilePath();
                        String headers = Utils.headerListToString(request.getHeaders(), isLoggingEnabled());
                        int priority = request.getPriority();
                        long downloadedBytes = 0;
                        File file = Utils.getFile(filePath);
                        if (file.exists()) {
                            downloadedBytes = file.length();
                        }
                        stringBuilder.append(this.dbHelper.getRowInsertStatement(id, url, filePath, FetchConst.STATUS_QUEUED, headers, downloadedBytes, 0, priority, -1)).append(", ");
                    }
                    arrayList.add(Long.valueOf(id));
                }
                stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append(this.dbHelper.getInsertStatementClose());
                if (this.dbHelper.insert(stringBuilder.toString())) {
                    startService(this.context);
                    return arrayList;
                }
                throw new EnqueueException("could not insert requests", FetchConst.ERROR_ENQUEUE_ERROR);
            } catch (EnqueueException e) {
                if (isLoggingEnabled()) {
                    e.printStackTrace();
                }
                arrayList.clear();
                for (int i = 0; i < requests.size(); i++) {
                    arrayList.add(Long.valueOf(-1));
                }
                return arrayList;
            }
        }
    }

    public void remove(long id) {
        Utils.throwIfNotUsable(this);
        Bundle extras = new Bundle();
        extras.putInt(FetchService.ACTION_TYPE, FetchService.ACTION_REMOVE);
        extras.putLong(FetchService.EXTRA_ID, id);
        FetchService.sendToService(this.context, extras);
    }

    public void removeAll() {
        Utils.throwIfNotUsable(this);
        Bundle extras = new Bundle();
        extras.putInt(FetchService.ACTION_TYPE, FetchService.ACTION_REMOVE_ALL);
        FetchService.sendToService(this.context, extras);
    }

    public void removeRequest(long id) {
        Utils.throwIfNotUsable(this);
        Bundle extras = new Bundle();
        extras.putInt(FetchService.ACTION_TYPE, FetchService.ACTION_REMOVE_REQUEST);
        extras.putLong(FetchService.EXTRA_ID, id);
        FetchService.sendToService(this.context, extras);
    }

    public void removeRequests() {
        Utils.throwIfNotUsable(this);
        Bundle extras = new Bundle();
        extras.putInt(FetchService.ACTION_TYPE, FetchService.ACTION_REMOVE_REQUEST_ALL);
        FetchService.sendToService(this.context, extras);
    }

    public void pause(long id) {
        Utils.throwIfNotUsable(this);
        Bundle extras = new Bundle();
        extras.putInt(FetchService.ACTION_TYPE, FetchService.ACTION_PAUSE);
        extras.putLong(FetchService.EXTRA_ID, id);
        FetchService.sendToService(this.context, extras);
    }

    public void resume(long id) {
        Utils.throwIfNotUsable(this);
        Bundle extras = new Bundle();
        extras.putInt(FetchService.ACTION_TYPE, FetchService.ACTION_RESUME);
        extras.putLong(FetchService.EXTRA_ID, id);
        FetchService.sendToService(this.context, extras);
    }

    public void setAllowedNetwork(int networkType) {
        Utils.throwIfNotUsable(this);
        new Settings(this.context).setAllowedNetwork(networkType).apply();
    }

    public void setPriority(long id, int priority) {
        Utils.throwIfNotUsable(this);
        int priorityType = 600;
        if (priority == FetchConst.PRIORITY_HIGH) {
            priorityType = FetchConst.PRIORITY_HIGH;
        }
        Bundle extras = new Bundle();
        extras.putInt(FetchService.ACTION_TYPE, FetchService.ACTION_PRIORITY);
        extras.putLong(FetchService.EXTRA_ID, id);
        extras.putInt(FetchService.EXTRA_PRIORITY, priorityType);
        FetchService.sendToService(this.context, extras);
    }

    public void retry(long id) {
        Utils.throwIfNotUsable(this);
        Bundle extras = new Bundle();
        extras.putInt(FetchService.ACTION_TYPE, FetchService.ACTION_RETRY);
        extras.putLong(FetchService.EXTRA_ID, id);
        FetchService.sendToService(this.context, extras);
    }

    @Nullable
    public synchronized RequestInfo get(long id) {
        Utils.throwIfNotUsable(this);
        return Utils.cursorToRequestInfo(this.dbHelper.get(id), true, isLoggingEnabled());
    }

    @NonNull
    public synchronized List<RequestInfo> get() {
        Utils.throwIfNotUsable(this);
        return Utils.cursorToRequestInfoList(this.dbHelper.get(), true, isLoggingEnabled());
    }

    @NonNull
    public synchronized List<RequestInfo> get(long... ids) {
        List<RequestInfo> arrayList;
        Utils.throwIfNotUsable(this);
        if (ids == null) {
            arrayList = new ArrayList();
        } else {
            arrayList = Utils.cursorToRequestInfoList(this.dbHelper.get(ids), true, isLoggingEnabled());
        }
        return arrayList;
    }

    @NonNull
    public synchronized List<RequestInfo> getByStatus(int status) {
        Utils.throwIfNotUsable(this);
        Utils.throwIfInvalidStatus(status);
        return Utils.cursorToRequestInfoList(this.dbHelper.getByStatus(status), true, isLoggingEnabled());
    }

    @Nullable
    public synchronized RequestInfo get(@NonNull Request request) {
        Utils.throwIfNotUsable(this);
        if (request == null) {
            throw new NullPointerException("Request cannot be null.");
        }
        return Utils.cursorToRequestInfo(this.dbHelper.getByUrlAndFilePath(request.getUrl(), request.getFilePath()), true, isLoggingEnabled());
    }

    @Nullable
    public synchronized File getDownloadedFile(long id) {
        File file;
        Utils.throwIfNotUsable(this);
        RequestInfo requestInfo = Utils.cursorToRequestInfo(this.dbHelper.get(id), true, isLoggingEnabled());
        if (requestInfo == null || requestInfo.getStatus() != FetchConst.STATUS_DONE) {
            file = null;
        } else {
            file = Utils.getFile(requestInfo.getFilePath());
            if (!file.exists()) {
                file = null;
            }
        }
        return file;
    }

    @Nullable
    public synchronized String getFilePath(long id) {
        String str;
        Utils.throwIfNotUsable(this);
        RequestInfo requestInfo = Utils.cursorToRequestInfo(this.dbHelper.get(id), true, isLoggingEnabled());
        if (requestInfo == null) {
            str = null;
        } else {
            str = requestInfo.getFilePath();
        }
        return str;
    }

    public long addCompletedDownload(@NonNull String filePath) {
        Utils.throwIfNotUsable(this);
        if (filePath == null) {
            throw new NullPointerException("File path cannot be null");
        }
        try {
            if (Utils.fileExist(filePath)) {
                long id = Utils.generateRequestId();
                File file = Utils.getFile(filePath);
                String url = Uri.fromFile(file).toString();
                String headers = Utils.headerListToString(null, isLoggingEnabled());
                long fileSize = file.length();
                if (this.dbHelper.insert(id, url, filePath, FetchConst.STATUS_DONE, headers, fileSize, fileSize, 600, -1)) {
                    return id;
                }
                throw new EnqueueException("could not insert request:" + filePath, FetchConst.ERROR_ENQUEUE_ERROR);
            }
            throw new EnqueueException("File does not exist at filePath: " + filePath, FetchConst.ERROR_FILE_NOT_CREATED);
        } catch (EnqueueException e) {
            if (isLoggingEnabled()) {
                e.printStackTrace();
            }
            return -1;
        }
    }

    @NonNull
    public List<Long> addCompletedDownloads(@NonNull List<String> filePaths) {
        Utils.throwIfNotUsable(this);
        if (filePaths == null) {
            throw new NullPointerException("Request list cannot be null");
        } else if (filePaths.size() < 1) {
            return new ArrayList(0);
        } else {
            List<Long> arrayList = new ArrayList(filePaths.size());
            List<String> statements = new ArrayList();
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.dbHelper.getInsertStatementOpen());
                for (String path : filePaths) {
                    long id = -1;
                    if (path != null) {
                        File file = Utils.getFile(path);
                        if (!file.exists()) {
                            break;
                        }
                        id = Utils.generateRequestId();
                        String url = Uri.fromFile(file).toString();
                        String filePath = path;
                        String headers = Utils.headerListToString(null, isLoggingEnabled());
                        long downloadedBytes = file.length();
                        stringBuilder.append(this.dbHelper.getRowInsertStatement(id, url, filePath, FetchConst.STATUS_DONE, headers, downloadedBytes, downloadedBytes, 600, -1)).append(",");
                    }
                    arrayList.add(Long.valueOf(id));
                }
                stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append(this.dbHelper.getInsertStatementClose());
                if (this.dbHelper.insert(stringBuilder.toString())) {
                    return arrayList;
                }
                throw new EnqueueException("could not insert requests", FetchConst.ERROR_ENQUEUE_ERROR);
            } catch (EnqueueException e) {
                if (isLoggingEnabled()) {
                    e.printStackTrace();
                }
                arrayList.clear();
                for (int i = 0; i < filePaths.size(); i++) {
                    arrayList.add(Long.valueOf(-1));
                }
                return arrayList;
            }
        }
    }

    public void runOnBackgroundThread(@NonNull final FetchTask fetchTask) {
        Utils.throwIfNotUsable(this);
        Utils.throwIfFetchTaskNull(fetchTask);
        new Thread(new Runnable() {
            public void run() {
                Fetch fetch = Fetch.newInstance(Fetch.this.context);
                fetchTask.onProcess(fetch);
                fetch.release();
            }
        }).start();
    }

    public void runOnMainThread(@NonNull final FetchTask fetchTask) {
        Utils.throwIfNotUsable(this);
        Utils.throwIfFetchTaskNull(fetchTask);
        mainHandler.post(new Runnable() {
            public void run() {
                Fetch fetch = Fetch.newInstance(Fetch.this.context);
                fetchTask.onProcess(fetch);
                fetch.release();
            }
        });
    }

    public synchronized boolean contains(@NonNull Request request) {
        Utils.throwIfNotUsable(this);
        if (request == null) {
            throw new NullPointerException("Request cannot be null.");
        }
        return Utils.containsRequest(this.dbHelper.getByUrlAndFilePath(request.getUrl(), request.getFilePath()), true);
    }

    public boolean isValid() {
        return !isReleased();
    }

    boolean isReleased() {
        return this.isReleased;
    }

    private void setReleased(boolean released) {
        this.isReleased = released;
    }

    private boolean isLoggingEnabled() {
        return FetchService.isLoggingEnabled(this.context);
    }

    public void enableLogging(boolean enabled) {
        Utils.throwIfNotUsable(this);
        new Settings(this.context).enableLogging(enabled).apply();
    }

    public void setConcurrentDownloadsLimit(int limit) {
        Utils.throwIfNotUsable(this);
        new Settings(this.context).setConcurrentDownloadsLimit(limit).apply();
    }

    public void setOnUpdateInterval(long intervalMs) {
        Utils.throwIfNotUsable(this);
        new Settings(this.context).setOnUpdateInterval(intervalMs).apply();
    }

    public void updateUrlForRequest(long id, @Nullable String url) {
        Utils.throwIfNotUsable(this);
        if (url == null) {
            throw new NullPointerException("Url cannot be null");
        }
        Utils.throwIfInvalidUrl(url);
        Bundle extras = new Bundle();
        extras.putInt(FetchService.ACTION_TYPE, FetchService.ACTION_UPDATE_REQUEST_URL);
        extras.putLong(FetchService.EXTRA_ID, id);
        extras.putString(FetchService.EXTRA_URL, url);
        FetchService.sendToService(this.context, extras);
    }
}
