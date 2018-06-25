package org.telegram.customization.fetch;

interface FetchConst {
    public static final int DEFAULT_DOWNLOADS_LIMIT = 1;
    public static final int DEFAULT_EMPTY_VALUE = -1;
    public static final long DEFAULT_ON_UPDATE_INTERVAL = 2000;
    public static final int ENQUEUE_ERROR_ID = -1;
    public static final int ERROR_BAD_REQUEST = -116;
    public static final int ERROR_CONNECTION_TIMEOUT = -104;
    public static final int ERROR_ENQUEUE_ERROR = -117;
    public static final int ERROR_FILE_ALREADY_CREATED = -112;
    public static final int ERROR_FILE_NOT_CREATED = -102;
    public static final int ERROR_FILE_NOT_FOUND = -111;
    public static final int ERROR_HTTP_NOT_FOUND = -106;
    public static final int ERROR_ILLEGAL_STATE = -109;
    public static final int ERROR_NO_STORAGE_SPACE = -108;
    public static final int ERROR_REQUEST_ALREADY_EXIST = -113;
    public static final int ERROR_SERVER_ERROR = -110;
    public static final int ERROR_UNKNOWN = -101;
    public static final int ERROR_UNKNOWN_HOST = -105;
    public static final int ERROR_WRITE_PERMISSION_DENIED = -107;
    public static final int MAX_DOWNLOADS_LIMIT = 7;
    public static final int NETWORK_ALL = 200;
    public static final int NETWORK_WIFI = 201;
    public static final int NO_ERROR = -1;
    public static final int PRIORITY_HIGH = 601;
    public static final int PRIORITY_NORMAL = 600;
    public static final int STATUS_DONE = 903;
    public static final int STATUS_DOWNLOADING = 901;
    public static final int STATUS_ERROR = 904;
    public static final int STATUS_NOT_QUEUED = -900;
    public static final int STATUS_PAUSED = 902;
    public static final int STATUS_QUEUED = 900;
    public static final int STATUS_REMOVED = 905;
}
