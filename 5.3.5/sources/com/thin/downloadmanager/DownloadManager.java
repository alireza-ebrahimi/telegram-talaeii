package com.thin.downloadmanager;

public interface DownloadManager {
    public static final int ERROR_CONNECTION_TIMEOUT_AFTER_RETRIES = 1009;
    public static final int ERROR_DOWNLOAD_CANCELLED = 1008;
    public static final int ERROR_DOWNLOAD_SIZE_UNKNOWN = 1006;
    public static final int ERROR_FILE_ERROR = 1001;
    public static final int ERROR_HTTP_DATA_ERROR = 1004;
    public static final int ERROR_MALFORMED_URI = 1007;
    public static final int ERROR_TOO_MANY_REDIRECTS = 1005;
    public static final int ERROR_UNHANDLED_HTTP_CODE = 1002;
    public static final int STATUS_CONNECTING = 4;
    public static final int STATUS_FAILED = 32;
    public static final int STATUS_NOT_FOUND = 64;
    public static final int STATUS_PENDING = 1;
    public static final int STATUS_RETRYING = 128;
    public static final int STATUS_RUNNING = 8;
    public static final int STATUS_STARTED = 2;
    public static final int STATUS_SUCCESSFUL = 16;

    int add(DownloadRequest downloadRequest);

    int cancel(int i);

    void cancelAll();

    boolean isReleased();

    int query(int i);

    void release();
}
