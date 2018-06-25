package org.telegram.customization.fetch;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.telegram.customization.fetch.exception.DownloadInterruptedException;
import org.telegram.customization.fetch.request.Header;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;

final class FetchRunnable implements Runnable {
    private static final String ACTION_DONE = "com.tonyodev.fetch.action_done";
    private static final String EXTRA_ID = "com.tonyodev.fetch.extra_id";
    private final LocalBroadcastManager broadcastManager;
    private final Context context;
    private final DatabaseHelper databaseHelper;
    private long downloadedBytes;
    private final String filePath;
    private long fileSize;
    private final List<Header> headers;
    private HttpURLConnection httpURLConnection;
    private final long id;
    private BufferedInputStream input;
    private volatile boolean interrupted = false;
    private final boolean loggingEnabled;
    private final long onUpdateInterval;
    private RandomAccessFile output;
    private int progress;
    private final String url;

    @NonNull
    static IntentFilter getDoneFilter() {
        return new IntentFilter(ACTION_DONE);
    }

    FetchRunnable(@NonNull Context context, long id, @NonNull String url, @NonNull String filePath, @NonNull List<Header> headers, long fileSize, boolean loggingEnabled, long onUpdateInterval) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        } else if (url == null) {
            throw new NullPointerException("Url cannot be null");
        } else if (filePath == null) {
            throw new NullPointerException("FilePath cannot be null");
        } else {
            if (headers == null) {
                this.headers = new ArrayList();
            } else {
                this.headers = headers;
            }
            this.id = id;
            this.url = url;
            this.filePath = filePath;
            this.fileSize = fileSize;
            this.context = context.getApplicationContext();
            this.broadcastManager = LocalBroadcastManager.getInstance(this.context);
            this.databaseHelper = DatabaseHelper.getInstance(this.context);
            this.loggingEnabled = loggingEnabled;
            this.onUpdateInterval = onUpdateInterval;
            this.databaseHelper.setLoggingEnabled(loggingEnabled);
        }
    }

    public void run() {
        try {
            setHttpConnectionPrefs();
            Utils.createFileOrThrow(this.filePath);
            this.downloadedBytes = Utils.getFileSize(this.filePath);
            this.progress = Utils.getProgress(this.downloadedBytes, this.fileSize);
            this.databaseHelper.updateFileBytes(this.id, this.downloadedBytes, this.fileSize);
            this.httpURLConnection.setRequestProperty("Range", "bytes=" + this.downloadedBytes + "-");
            if (isInterrupted()) {
                throw new DownloadInterruptedException("DIE", -118);
            }
            this.httpURLConnection.connect();
            int responseCode = this.httpURLConnection.getResponseCode();
            if (!isResponseOk(responseCode)) {
                throw new IllegalStateException("SSRV:" + responseCode);
            } else if (isInterrupted()) {
                throw new DownloadInterruptedException("DIE", -118);
            } else {
                if (this.fileSize < 1) {
                    setContentLength();
                    this.databaseHelper.updateFileBytes(this.id, this.downloadedBytes, this.fileSize);
                    this.progress = Utils.getProgress(this.downloadedBytes, this.fileSize);
                }
                this.output = new RandomAccessFile(this.filePath, "rw");
                if (responseCode == 206) {
                    this.output.seek(this.downloadedBytes);
                } else {
                    this.output.seek(0);
                }
                this.input = new BufferedInputStream(this.httpURLConnection.getInputStream());
                writeToFileAndPost();
                this.databaseHelper.updateFileBytes(this.id, this.downloadedBytes, this.fileSize);
                if (isInterrupted()) {
                    throw new DownloadInterruptedException("DIE", -118);
                }
                if (this.downloadedBytes >= this.fileSize && !isInterrupted()) {
                    if (this.fileSize < 1) {
                        this.fileSize = Utils.getFileSize(this.filePath);
                        this.databaseHelper.updateFileBytes(this.id, this.downloadedBytes, this.fileSize);
                        this.progress = Utils.getProgress(this.downloadedBytes, this.fileSize);
                    } else {
                        this.progress = Utils.getProgress(this.downloadedBytes, this.fileSize);
                    }
                    if (this.databaseHelper.updateStatus(this.id, FetchConst.STATUS_DONE, -1)) {
                        Utils.sendEventUpdate(this.broadcastManager, this.id, FetchConst.STATUS_DONE, this.progress, this.downloadedBytes, this.fileSize, -1);
                    }
                }
                release();
                broadcastDone();
            }
        } catch (Exception exception) {
            if (this.loggingEnabled) {
                exception.printStackTrace();
            }
            int error = ErrorUtils.getCode(exception.getMessage());
            if (canRetry(error)) {
                if (this.databaseHelper.updateStatus(this.id, FetchConst.STATUS_QUEUED, -1)) {
                    Utils.sendEventUpdate(this.broadcastManager, this.id, FetchConst.STATUS_QUEUED, this.progress, this.downloadedBytes, this.fileSize, -1);
                }
            } else if (this.databaseHelper.updateStatus(this.id, FetchConst.STATUS_ERROR, error)) {
                Utils.sendEventUpdate(this.broadcastManager, this.id, FetchConst.STATUS_ERROR, this.progress, this.downloadedBytes, this.fileSize, error);
            }
            release();
            broadcastDone();
        } catch (Throwable th) {
            release();
            broadcastDone();
        }
    }

    private void setHttpConnectionPrefs() throws IOException {
        this.httpURLConnection = (HttpURLConnection) new URL(this.url).openConnection();
        this.httpURLConnection.setRequestMethod(HttpRequest.METHOD_GET);
        this.httpURLConnection.setReadTimeout(BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT);
        this.httpURLConnection.setConnectTimeout(DefaultLoadControl.DEFAULT_MIN_BUFFER_MS);
        this.httpURLConnection.setUseCaches(false);
        this.httpURLConnection.setDefaultUseCaches(false);
        this.httpURLConnection.setInstanceFollowRedirects(true);
        this.httpURLConnection.setDoInput(true);
        for (Header header : this.headers) {
            this.httpURLConnection.addRequestProperty(header.getHeader(), header.getValue());
        }
    }

    private boolean isResponseOk(int responseCode) {
        switch (responseCode) {
            case 200:
            case 202:
            case 206:
                return true;
            default:
                return false;
        }
    }

    private void setContentLength() {
        try {
            this.fileSize = this.downloadedBytes + Long.valueOf(this.httpURLConnection.getHeaderField(HttpRequest.HEADER_CONTENT_LENGTH)).longValue();
        } catch (Exception e) {
            this.fileSize = -1;
        }
    }

    private void writeToFileAndPost() throws IOException {
        byte[] buffer = new byte[1024];
        long startTime = System.nanoTime();
        while (true) {
            int read = this.input.read(buffer, 0, 1024);
            if (read != -1 && !isInterrupted()) {
                this.output.write(buffer, 0, read);
                this.downloadedBytes += (long) read;
                if (Utils.hasIntervalElapsed(startTime, System.nanoTime(), this.onUpdateInterval) && !isInterrupted()) {
                    this.progress = Utils.getProgress(this.downloadedBytes, this.fileSize);
                    Utils.sendEventUpdate(this.broadcastManager, this.id, FetchConst.STATUS_DOWNLOADING, this.progress, this.downloadedBytes, this.fileSize, -1);
                    this.databaseHelper.updateFileBytes(this.id, this.downloadedBytes, this.fileSize);
                    startTime = System.nanoTime();
                }
            } else {
                return;
            }
        }
    }

    private boolean canRetry(int error) {
        if (!Utils.isNetworkAvailable(this.context)) {
            return true;
        }
        switch (error) {
            case -118:
            case FetchConst.ERROR_CONNECTION_TIMEOUT /*-104*/:
            case -103:
                return true;
            default:
                return false;
        }
    }

    private void release() {
        try {
            if (this.input != null) {
                this.input.close();
            }
        } catch (IOException e) {
            if (this.loggingEnabled) {
                e.printStackTrace();
            }
        }
        try {
            if (this.output != null) {
                this.output.close();
            }
        } catch (IOException e2) {
            if (this.loggingEnabled) {
                e2.printStackTrace();
            }
        }
        if (this.httpURLConnection != null) {
            this.httpURLConnection.disconnect();
        }
    }

    private void broadcastDone() {
        Intent intent = new Intent(ACTION_DONE);
        intent.putExtra("com.tonyodev.fetch.extra_id", this.id);
        this.broadcastManager.sendBroadcast(intent);
    }

    private boolean isInterrupted() {
        return this.interrupted;
    }

    synchronized void interrupt() {
        this.interrupted = true;
    }

    synchronized long getId() {
        return this.id;
    }

    static long getIdFromIntent(Intent intent) {
        if (intent == null) {
            return -1;
        }
        return intent.getLongExtra("com.tonyodev.fetch.extra_id", -1);
    }
}
