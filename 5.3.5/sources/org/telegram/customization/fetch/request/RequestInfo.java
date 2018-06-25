package org.telegram.customization.fetch.request;

import android.support.annotation.NonNull;
import java.util.List;

public final class RequestInfo {
    private final long downloadedBytes;
    private final int error;
    private final String filePath;
    private final long fileSize;
    private final List<Header> headers;
    private final long id;
    private final int priority;
    private final int progress;
    private final int status;
    private final String url;

    public RequestInfo(long id, int status, @NonNull String url, @NonNull String filePath, int progress, long downloadedBytes, long fileSize, int error, @NonNull List<Header> headers, int priority) {
        if (url == null) {
            throw new NullPointerException("Url cannot be null");
        } else if (filePath == null) {
            throw new NullPointerException("FilePath cannot be null");
        } else if (headers == null) {
            throw new NullPointerException("Headers cannot be null");
        } else {
            this.id = id;
            this.status = status;
            this.url = url;
            this.filePath = filePath;
            this.progress = progress;
            this.downloadedBytes = downloadedBytes;
            this.fileSize = fileSize;
            this.error = error;
            this.headers = headers;
            this.priority = priority;
        }
    }

    public long getId() {
        return this.id;
    }

    public int getStatus() {
        return this.status;
    }

    @NonNull
    public String getUrl() {
        return this.url;
    }

    @NonNull
    public String getFilePath() {
        return this.filePath;
    }

    public int getProgress() {
        return this.progress;
    }

    public long getDownloadedBytes() {
        return this.downloadedBytes;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public int getError() {
        return this.error;
    }

    @NonNull
    public List<Header> getHeaders() {
        return this.headers;
    }

    public int getPriority() {
        return this.priority;
    }

    public String toString() {
        StringBuilder headerBuilder = new StringBuilder();
        for (Header header : this.headers) {
            headerBuilder.append(header.toString()).append(",");
        }
        if (this.headers.size() > 0) {
            headerBuilder.deleteCharAt(headerBuilder.length() - 1);
        }
        return "{id:" + this.id + ",status:" + this.status + ",url:" + this.url + ",filePath:" + this.filePath + ",progress:" + this.progress + ",fileSize:" + this.fileSize + ",error:" + this.error + ",headers:{" + headerBuilder.toString() + "}" + ",priority:" + this.priority + "}";
    }
}
