package com.thin.downloadmanager;

@Deprecated
public interface DownloadStatusListener {
    void onDownloadComplete(int i);

    void onDownloadFailed(int i, int i2, String str);

    void onProgress(int i, long j, long j2, int i2);
}
