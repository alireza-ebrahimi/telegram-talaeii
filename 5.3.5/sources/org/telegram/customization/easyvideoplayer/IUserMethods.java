package org.telegram.customization.easyvideoplayer;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

interface IUserMethods {
    void disableControls();

    void enableControls(boolean z);

    @CheckResult
    int getCurrentPosition();

    @CheckResult
    int getDuration();

    void hideControls();

    @CheckResult
    boolean isControlsShown();

    @CheckResult
    boolean isPlaying();

    @CheckResult
    boolean isPrepared();

    void pause();

    void release();

    void reset();

    void seekTo(@IntRange(from = 0, to = 2147483647L) int i);

    void setAutoFullscreen(boolean z);

    void setAutoPlay(boolean z);

    void setBottomLabelText(@Nullable CharSequence charSequence);

    void setBottomLabelTextRes(@StringRes int i);

    void setCallback(@NonNull EasyVideoCallback easyVideoCallback);

    void setCustomLabelText(@Nullable CharSequence charSequence);

    void setCustomLabelTextRes(@StringRes int i);

    void setHideControlsOnPlay(boolean z);

    void setInitialPosition(@IntRange(from = 0, to = 2147483647L) int i);

    void setLeftAction(int i);

    void setLoop(boolean z);

    void setPauseDrawable(@NonNull Drawable drawable);

    void setPauseDrawableRes(@DrawableRes int i);

    void setPlayDrawable(@NonNull Drawable drawable);

    void setPlayDrawableRes(@DrawableRes int i);

    void setProgressCallback(@NonNull EasyVideoProgressCallback easyVideoProgressCallback);

    void setRestartDrawable(@NonNull Drawable drawable);

    void setRestartDrawableRes(@DrawableRes int i);

    void setRetryText(@Nullable CharSequence charSequence);

    void setRetryTextRes(@StringRes int i);

    void setRightAction(int i);

    void setSource(@NonNull Uri uri);

    void setSubmitText(@Nullable CharSequence charSequence);

    void setSubmitTextRes(@StringRes int i);

    void setThemeColor(@ColorInt int i);

    void setThemeColorRes(@ColorRes int i);

    void setVolume(@FloatRange(from = 0.0d, to = 1.0d) float f, @FloatRange(from = 0.0d, to = 1.0d) float f2);

    void showControls();

    void start();

    void stop();

    void toggleControls();
}
