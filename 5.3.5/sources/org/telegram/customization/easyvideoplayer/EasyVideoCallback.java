package org.telegram.customization.easyvideoplayer;

import android.net.Uri;

public interface EasyVideoCallback {
    void onBuffering(int i);

    void onClickVideoFrame(EasyVideoPlayer easyVideoPlayer);

    void onCompletion(EasyVideoPlayer easyVideoPlayer);

    void onError(EasyVideoPlayer easyVideoPlayer, Exception exception);

    void onPaused(EasyVideoPlayer easyVideoPlayer);

    void onPrepared(EasyVideoPlayer easyVideoPlayer);

    void onPreparing(EasyVideoPlayer easyVideoPlayer);

    void onRetry(EasyVideoPlayer easyVideoPlayer, Uri uri);

    void onStarted(EasyVideoPlayer easyVideoPlayer);

    void onSubmit(EasyVideoPlayer easyVideoPlayer, Uri uri);
}
