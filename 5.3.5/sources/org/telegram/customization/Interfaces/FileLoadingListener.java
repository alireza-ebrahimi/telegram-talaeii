package org.telegram.customization.Interfaces;

public interface FileLoadingListener {
    void onLoadingCancelled(String str);

    void onLoadingComplete(String str);

    void onLoadingFailed(String str);

    void onLoadingStarted(String str);

    void onProgress(String str, int i);
}
