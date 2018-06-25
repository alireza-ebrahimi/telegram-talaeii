package org.telegram.messenger;

public interface MediaController$FileDownloadProgressListener {
    int getObserverTag();

    void onFailedDownload(String str);

    void onProgressDownload(String str, float f);

    void onProgressUpload(String str, float f, boolean z);

    void onSuccessDownload(String str);
}
