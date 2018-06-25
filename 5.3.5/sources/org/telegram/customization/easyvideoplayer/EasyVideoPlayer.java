package org.telegram.customization.easyvideoplayer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.PorterDuff.Mode;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.ir.talaeii.R;
import org.telegram.messenger.C0906R;

public class EasyVideoPlayer extends FrameLayout implements IUserMethods, SurfaceTextureListener, OnPreparedListener, OnBufferingUpdateListener, OnCompletionListener, OnVideoSizeChangedListener, OnErrorListener, OnClickListener, OnSeekBarChangeListener {
    public static final int LEFT_ACTION_NONE = 0;
    public static final int LEFT_ACTION_RESTART = 1;
    public static final int LEFT_ACTION_RETRY = 2;
    public static final int RIGHT_ACTION_CUSTOM_LABEL = 5;
    public static final int RIGHT_ACTION_NONE = 3;
    public static final int RIGHT_ACTION_SUBMIT = 4;
    private static final int UPDATE_INTERVAL = 100;
    private boolean mAutoFullscreen = false;
    private boolean mAutoPlay;
    private CharSequence mBottomLabelText;
    private ImageButton mBtnPlayPause;
    public ImageButton mBtnRestart;
    public TextView mBtnRetry;
    private TextView mBtnSubmit;
    private EasyVideoCallback mCallback;
    private View mClickFrame;
    private boolean mControlsDisabled;
    private View mControlsFrame;
    private CharSequence mCustomLabelText;
    private Handler mHandler;
    private boolean mHideControlsOnPlay = true;
    private int mInitialPosition = -1;
    private int mInitialTextureHeight;
    private int mInitialTextureWidth;
    private boolean mIsPrepared;
    private TextView mLabelBottom;
    private TextView mLabelCustom;
    private TextView mLabelDuration;
    private TextView mLabelPosition;
    private int mLeftAction = 1;
    private boolean mLoop = false;
    private Drawable mPauseDrawable;
    private Drawable mPlayDrawable;
    private MediaPlayer mPlayer;
    private EasyVideoProgressCallback mProgressCallback;
    private View mProgressFrame;
    private Drawable mRestartDrawable;
    private CharSequence mRetryText;
    private int mRightAction = 3;
    private SeekBar mSeeker;
    private Uri mSource;
    private CharSequence mSubmitText;
    private Surface mSurface;
    private boolean mSurfaceAvailable;
    private TextureView mTextureView;
    private int mThemeColor = 0;
    private final Runnable mUpdateCounters = new C08851();
    private boolean mWasPlaying;

    /* renamed from: org.telegram.customization.easyvideoplayer.EasyVideoPlayer$1 */
    class C08851 implements Runnable {
        C08851() {
        }

        public void run() {
            if (EasyVideoPlayer.this.mHandler != null && EasyVideoPlayer.this.mIsPrepared && EasyVideoPlayer.this.mSeeker != null && EasyVideoPlayer.this.mPlayer != null) {
                int pos = EasyVideoPlayer.this.mPlayer.getCurrentPosition();
                int dur = EasyVideoPlayer.this.mPlayer.getDuration();
                if (pos > dur) {
                    pos = dur;
                }
                EasyVideoPlayer.this.mLabelPosition.setText(Util.getDurationString((long) pos, false));
                EasyVideoPlayer.this.mLabelDuration.setText(Util.getDurationString((long) (dur - pos), true));
                EasyVideoPlayer.this.mSeeker.setProgress(pos);
                EasyVideoPlayer.this.mSeeker.setMax(dur);
                if (EasyVideoPlayer.this.mProgressCallback != null) {
                    EasyVideoPlayer.this.mProgressCallback.onVideoProgressUpdate(pos, dur);
                }
                if (EasyVideoPlayer.this.mHandler != null) {
                    EasyVideoPlayer.this.mHandler.postDelayed(this, 100);
                }
            }
        }
    }

    /* renamed from: org.telegram.customization.easyvideoplayer.EasyVideoPlayer$2 */
    class C08862 extends AnimatorListenerAdapter {
        C08862() {
        }

        public void onAnimationEnd(Animator animation) {
            if (EasyVideoPlayer.this.mAutoFullscreen) {
                EasyVideoPlayer.this.setFullscreen(false);
            }
        }
    }

    /* renamed from: org.telegram.customization.easyvideoplayer.EasyVideoPlayer$3 */
    class C08873 extends AnimatorListenerAdapter {
        C08873() {
        }

        public void onAnimationEnd(Animator animation) {
            EasyVideoPlayer.this.setFullscreen(true);
            if (EasyVideoPlayer.this.mControlsFrame != null) {
                EasyVideoPlayer.this.mControlsFrame.setVisibility(4);
            }
        }
    }

    /* renamed from: org.telegram.customization.easyvideoplayer.EasyVideoPlayer$4 */
    class C08884 implements OnClickListener {
        C08884() {
        }

        public void onClick(View view) {
            EasyVideoPlayer.this.toggleControls();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LeftAction {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RightAction {
    }

    public EasyVideoPlayer(Context context) {
        super(context);
        init(context, null);
    }

    public EasyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EasyVideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setBackgroundColor(-16777216);
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, C0906R.styleable.EasyVideoPlayer, 0, 0);
            try {
                String source = a.getString(0);
                if (!(source == null || source.trim().isEmpty())) {
                    this.mSource = Uri.parse(source);
                }
                this.mLeftAction = a.getInteger(1, 1);
                this.mRightAction = a.getInteger(2, 3);
                this.mCustomLabelText = a.getText(3);
                this.mRetryText = a.getText(4);
                this.mSubmitText = a.getText(5);
                this.mBottomLabelText = a.getText(6);
                int restartDrawableResId = a.getResourceId(7, -1);
                int playDrawableResId = a.getResourceId(8, -1);
                int pauseDrawableResId = a.getResourceId(9, -1);
                if (restartDrawableResId != -1) {
                    this.mRestartDrawable = AppCompatResources.getDrawable(context, restartDrawableResId);
                }
                if (playDrawableResId != -1) {
                    this.mPlayDrawable = AppCompatResources.getDrawable(context, playDrawableResId);
                }
                if (pauseDrawableResId != -1) {
                    this.mPauseDrawable = AppCompatResources.getDrawable(context, pauseDrawableResId);
                }
                this.mHideControlsOnPlay = a.getBoolean(10, true);
                this.mAutoPlay = a.getBoolean(11, false);
                this.mControlsDisabled = a.getBoolean(12, false);
                this.mThemeColor = a.getColor(13, Util.resolveColor(context, R.attr.colorPrimary));
                this.mAutoFullscreen = a.getBoolean(14, false);
                this.mLoop = a.getBoolean(15, false);
            } finally {
                a.recycle();
            }
        } else {
            this.mLeftAction = 1;
            this.mRightAction = 3;
            this.mHideControlsOnPlay = true;
            this.mAutoPlay = false;
            this.mControlsDisabled = false;
            this.mThemeColor = Util.resolveColor(context, R.attr.colorPrimary);
            this.mAutoFullscreen = false;
            this.mLoop = false;
        }
        if (this.mRetryText == null) {
            this.mRetryText = context.getResources().getText(R.string.evp_retry);
        }
        if (this.mSubmitText == null) {
            this.mSubmitText = context.getResources().getText(R.string.evp_submit);
        }
        if (this.mRestartDrawable == null) {
            this.mRestartDrawable = AppCompatResources.getDrawable(context, R.drawable.evp_action_restart);
        }
        if (this.mPlayDrawable == null) {
            this.mPlayDrawable = AppCompatResources.getDrawable(context, R.drawable.evp_action_play);
        }
        if (this.mPauseDrawable == null) {
            this.mPauseDrawable = AppCompatResources.getDrawable(context, R.drawable.evp_action_pause);
        }
    }

    public void setSource(@NonNull Uri source) {
        boolean hadSource = this.mSource != null;
        if (hadSource) {
            stop();
        }
        this.mSource = source;
        if (this.mPlayer == null) {
            return;
        }
        if (hadSource) {
            sourceChanged();
        } else {
            prepare();
        }
    }

    public void setCallback(@NonNull EasyVideoCallback callback) {
    }

    public void setProgressCallback(@NonNull EasyVideoProgressCallback callback) {
        this.mProgressCallback = callback;
    }

    public void setLeftAction(int action) {
        if (action < 0 || action > 2) {
            throw new IllegalArgumentException("Invalid left action specified.");
        }
        this.mLeftAction = action;
        invalidateActions();
    }

    public void setRightAction(int action) {
        if (action < 3 || action > 5) {
            throw new IllegalArgumentException("Invalid right action specified.");
        }
        this.mRightAction = action;
        invalidateActions();
    }

    public void setCustomLabelText(@Nullable CharSequence text) {
        this.mCustomLabelText = text;
        this.mLabelCustom.setText(text);
        setRightAction(5);
    }

    public void setCustomLabelTextRes(@StringRes int textRes) {
        setCustomLabelText(getResources().getText(textRes));
    }

    public void setBottomLabelText(@Nullable CharSequence text) {
        this.mBottomLabelText = text;
        this.mLabelBottom.setText(text);
        if (text == null || text.toString().trim().length() == 0) {
            this.mLabelBottom.setVisibility(8);
        } else {
            this.mLabelBottom.setVisibility(0);
        }
    }

    public void setBottomLabelTextRes(@StringRes int textRes) {
        setBottomLabelText(getResources().getText(textRes));
    }

    public void setRetryText(@Nullable CharSequence text) {
        this.mRetryText = text;
        this.mBtnRetry.setText(text);
    }

    public void setRetryTextRes(@StringRes int res) {
        setRetryText(getResources().getText(res));
    }

    public void setSubmitText(@Nullable CharSequence text) {
        this.mSubmitText = text;
        this.mBtnSubmit.setText(text);
    }

    public void setSubmitTextRes(@StringRes int res) {
        setSubmitText(getResources().getText(res));
    }

    public void setRestartDrawable(@NonNull Drawable drawable) {
        this.mRestartDrawable = drawable;
        this.mBtnRestart.setImageDrawable(drawable);
    }

    public void setRestartDrawableRes(@DrawableRes int res) {
        setRestartDrawable(AppCompatResources.getDrawable(getContext(), res));
    }

    public void setPlayDrawable(@NonNull Drawable drawable) {
        this.mPlayDrawable = drawable;
        if (!isPlaying()) {
            this.mBtnPlayPause.setImageDrawable(drawable);
        }
    }

    public void setPlayDrawableRes(@DrawableRes int res) {
        setPlayDrawable(AppCompatResources.getDrawable(getContext(), res));
    }

    public void setPauseDrawable(@NonNull Drawable drawable) {
        this.mPauseDrawable = drawable;
        if (isPlaying()) {
            this.mBtnPlayPause.setImageDrawable(drawable);
        }
    }

    public void setPauseDrawableRes(@DrawableRes int res) {
        setPauseDrawable(AppCompatResources.getDrawable(getContext(), res));
    }

    public void setThemeColor(@ColorInt int color) {
        this.mThemeColor = color;
        invalidateThemeColors();
    }

    public void setThemeColorRes(@ColorRes int colorRes) {
        setThemeColor(ContextCompat.getColor(getContext(), colorRes));
    }

    public void setHideControlsOnPlay(boolean hide) {
        this.mHideControlsOnPlay = hide;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
    }

    public void setInitialPosition(@IntRange(from = 0, to = 2147483647L) int pos) {
        this.mInitialPosition = pos;
    }

    private void sourceChanged() {
        setControlsEnabled(false);
        this.mSeeker.setProgress(0);
        this.mSeeker.setEnabled(false);
        this.mPlayer.reset();
        if (this.mCallback != null) {
            this.mCallback.onPreparing(this);
        }
        try {
            setSourceInternal();
        } catch (IOException e) {
            throwError(e);
        }
    }

    private void setSourceInternal() throws IOException {
        if (this.mSource.getScheme() != null && (this.mSource.getScheme().equals("http") || this.mSource.getScheme().equals("https"))) {
            LOG("Loading web URI: " + this.mSource.toString(), new Object[0]);
            this.mPlayer.setDataSource(this.mSource.toString());
        } else if (this.mSource.getScheme() != null && this.mSource.getScheme().equals("file") && this.mSource.getPath().contains("/android_assets/")) {
            LOG("Loading assets URI: " + this.mSource.toString(), new Object[0]);
            afd = getContext().getAssets().openFd(this.mSource.toString().replace("file:///android_assets/", ""));
            this.mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
        } else if (this.mSource.getScheme() == null || !this.mSource.getScheme().equals("asset")) {
            LOG("Loading local URI: " + this.mSource.toString(), new Object[0]);
            this.mPlayer.setDataSource(getContext(), this.mSource);
        } else {
            LOG("Loading assets URI: " + this.mSource.toString(), new Object[0]);
            afd = getContext().getAssets().openFd(this.mSource.toString().replace("asset://", ""));
            this.mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
        }
        this.mPlayer.prepareAsync();
    }

    private void prepare() {
        if (this.mSurfaceAvailable && this.mSource != null && this.mPlayer != null && !this.mIsPrepared) {
            if (this.mCallback != null) {
                this.mCallback.onPreparing(this);
            }
            try {
                this.mPlayer.setSurface(this.mSurface);
                setSourceInternal();
            } catch (IOException e) {
                throwError(e);
            }
        }
    }

    private void setControlsEnabled(boolean enabled) {
        float f = 1.0f;
        if (this.mSeeker != null) {
            float f2;
            this.mSeeker.setEnabled(enabled);
            this.mBtnPlayPause.setEnabled(enabled);
            this.mBtnSubmit.setEnabled(enabled);
            this.mBtnRestart.setEnabled(enabled);
            this.mBtnRetry.setEnabled(enabled);
            ImageButton imageButton = this.mBtnPlayPause;
            if (enabled) {
                f2 = 1.0f;
            } else {
                f2 = 0.4f;
            }
            imageButton.setAlpha(f2);
            TextView textView = this.mBtnSubmit;
            if (enabled) {
                f2 = 1.0f;
            } else {
                f2 = 0.4f;
            }
            textView.setAlpha(f2);
            ImageButton imageButton2 = this.mBtnRestart;
            if (!enabled) {
                f = 0.4f;
            }
            imageButton2.setAlpha(f);
            this.mClickFrame.setEnabled(enabled);
        }
    }

    public void showControls() {
        if (!this.mControlsDisabled && !isControlsShown() && this.mSeeker != null) {
            this.mControlsFrame.animate().cancel();
            this.mControlsFrame.setAlpha(0.0f);
            this.mControlsFrame.setVisibility(0);
            this.mControlsFrame.animate().alpha(1.0f).setInterpolator(new DecelerateInterpolator()).setListener(new C08862()).start();
        }
    }

    public void hideControls() {
        if (!this.mControlsDisabled && isControlsShown() && this.mSeeker != null) {
            this.mControlsFrame.animate().cancel();
            this.mControlsFrame.setAlpha(1.0f);
            this.mControlsFrame.setVisibility(0);
            this.mControlsFrame.animate().alpha(0.0f).setInterpolator(new DecelerateInterpolator()).setListener(new C08873()).start();
        }
    }

    @CheckResult
    public boolean isControlsShown() {
        return (this.mControlsDisabled || this.mControlsFrame == null || this.mControlsFrame.getAlpha() <= 0.5f) ? false : true;
    }

    public void toggleControls() {
        if (!this.mControlsDisabled) {
            if (isControlsShown()) {
                hideControls();
            } else {
                showControls();
            }
        }
    }

    public void enableControls(boolean andShow) {
        this.mControlsDisabled = false;
        if (andShow) {
            showControls();
        }
        this.mClickFrame.setOnClickListener(new C08884());
        this.mClickFrame.setClickable(true);
    }

    public void disableControls() {
        this.mControlsDisabled = true;
        this.mControlsFrame.setVisibility(8);
        this.mClickFrame.setOnClickListener(null);
        this.mClickFrame.setClickable(false);
    }

    @CheckResult
    public boolean isPrepared() {
        return this.mPlayer != null && this.mIsPrepared;
    }

    @CheckResult
    public boolean isPlaying() {
        return this.mPlayer != null && this.mPlayer.isPlaying();
    }

    @CheckResult
    public int getCurrentPosition() {
        if (this.mPlayer == null) {
            return -1;
        }
        return this.mPlayer.getCurrentPosition();
    }

    @CheckResult
    public int getDuration() {
        if (this.mPlayer == null) {
            return -1;
        }
        return this.mPlayer.getDuration();
    }

    public void start() {
        if (this.mPlayer != null) {
            this.mPlayer.start();
            if (this.mCallback != null) {
                this.mCallback.onStarted(this);
            }
            if (this.mHandler == null) {
                this.mHandler = new Handler();
            }
            this.mHandler.post(this.mUpdateCounters);
            this.mBtnPlayPause.setImageDrawable(this.mPauseDrawable);
        }
    }

    public void seekTo(@IntRange(from = 0, to = 2147483647L) int pos) {
        if (this.mPlayer != null) {
            this.mPlayer.seekTo(pos);
        }
    }

    public void setVolume(@FloatRange(from = 0.0d, to = 1.0d) float leftVolume, @FloatRange(from = 0.0d, to = 1.0d) float rightVolume) {
        if (this.mPlayer == null || !this.mIsPrepared) {
            throw new IllegalStateException("You cannot use setVolume(float, float) until the player is prepared.");
        }
        this.mPlayer.setVolume(leftVolume, rightVolume);
    }

    public void pause() {
        if (this.mPlayer != null && isPlaying()) {
            this.mPlayer.pause();
            if (this.mCallback != null) {
                this.mCallback.onPaused(this);
            }
            if (this.mHandler != null) {
                this.mHandler.removeCallbacks(this.mUpdateCounters);
                this.mBtnPlayPause.setImageDrawable(this.mPlayDrawable);
            }
        }
    }

    public void stop() {
        if (this.mPlayer != null) {
            try {
                this.mPlayer.stop();
            } catch (Throwable th) {
            }
            if (this.mHandler != null) {
                this.mHandler.removeCallbacks(this.mUpdateCounters);
                this.mBtnPlayPause.setImageDrawable(this.mPauseDrawable);
            }
        }
    }

    public void reset() {
        if (this.mPlayer != null) {
            this.mIsPrepared = false;
            this.mPlayer.reset();
            this.mIsPrepared = false;
        }
    }

    public void release() {
        this.mIsPrepared = false;
        if (this.mPlayer != null) {
            try {
                this.mPlayer.release();
            } catch (Throwable th) {
            }
            this.mPlayer = null;
        }
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mUpdateCounters);
            this.mHandler = null;
        }
        LOG("Released player and Handler", new Object[0]);
    }

    public void setAutoFullscreen(boolean autoFullscreen) {
        this.mAutoFullscreen = autoFullscreen;
    }

    public void setLoop(boolean loop) {
        this.mLoop = loop;
        if (this.mPlayer != null) {
            this.mPlayer.setLooping(loop);
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        LOG("Surface texture available: %dx%d", Integer.valueOf(width), Integer.valueOf(height));
        this.mInitialTextureWidth = width;
        this.mInitialTextureHeight = height;
        this.mSurfaceAvailable = true;
        this.mSurface = new Surface(surfaceTexture);
        if (this.mIsPrepared) {
            this.mPlayer.setSurface(this.mSurface);
        } else {
            prepare();
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
        LOG("Surface texture changed: %dx%d", Integer.valueOf(width), Integer.valueOf(height));
        adjustAspectRatio(width, height, this.mPlayer.getVideoWidth(), this.mPlayer.getVideoHeight());
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        LOG("Surface texture destroyed", new Object[0]);
        this.mSurfaceAvailable = false;
        this.mSurface = null;
        return false;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        LOG("onPrepared()", new Object[0]);
        this.mProgressFrame.setVisibility(4);
        this.mIsPrepared = true;
        if (this.mCallback != null) {
            this.mCallback.onPrepared(this);
        }
        this.mLabelPosition.setText(Util.getDurationString(0, false));
        this.mLabelDuration.setText(Util.getDurationString((long) mediaPlayer.getDuration(), false));
        this.mSeeker.setProgress(0);
        this.mSeeker.setMax(mediaPlayer.getDuration());
        setControlsEnabled(true);
        if (this.mAutoPlay) {
            if (!this.mControlsDisabled && this.mHideControlsOnPlay) {
                hideControls();
            }
            start();
            if (this.mInitialPosition > 0) {
                seekTo(this.mInitialPosition);
                this.mInitialPosition = -1;
                return;
            }
            return;
        }
        this.mPlayer.start();
        this.mPlayer.pause();
    }

    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        LOG("Buffering: %d%%", Integer.valueOf(percent));
        if (this.mCallback != null) {
            this.mCallback.onBuffering(percent);
        }
        if (this.mSeeker == null) {
            return;
        }
        if (percent == 100) {
            this.mSeeker.setSecondaryProgress(0);
        } else {
            this.mSeeker.setSecondaryProgress(this.mSeeker.getMax() * (percent / 100));
        }
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        LOG("onCompletion()", new Object[0]);
        if (this.mLoop) {
            this.mBtnPlayPause.setImageDrawable(this.mPlayDrawable);
            if (this.mHandler != null) {
                this.mHandler.removeCallbacks(this.mUpdateCounters);
            }
            this.mSeeker.setProgress(this.mSeeker.getMax());
            showControls();
        }
        if (this.mCallback != null) {
            this.mCallback.onCompletion(this);
            if (this.mLoop) {
                this.mCallback.onStarted(this);
            }
        }
    }

    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
        LOG("Video size changed: %dx%d", Integer.valueOf(width), Integer.valueOf(height));
        adjustAspectRatio(this.mInitialTextureWidth, this.mInitialTextureHeight, width, height);
    }

    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        if (what != -38) {
            String errorMsg = "Preparation/playback error (" + what + "): ";
            switch (what) {
                case -1010:
                    errorMsg = errorMsg + "Unsupported";
                    break;
                case -1007:
                    errorMsg = errorMsg + "Malformed";
                    break;
                case -1004:
                    errorMsg = errorMsg + "I/O error";
                    break;
                case FetchConst.ERROR_SERVER_ERROR /*-110*/:
                    errorMsg = errorMsg + "Timed out";
                    break;
                case 100:
                    errorMsg = errorMsg + "Server died";
                    break;
                case 200:
                    errorMsg = errorMsg + "Not valid for progressive playback";
                    break;
                default:
                    errorMsg = errorMsg + "Unknown error";
                    break;
            }
            throwError(new Exception(errorMsg));
        }
        return false;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            setKeepScreenOn(true);
            this.mHandler = new Handler();
            this.mPlayer = new MediaPlayer();
            this.mPlayer.setOnPreparedListener(this);
            this.mPlayer.setOnBufferingUpdateListener(this);
            this.mPlayer.setOnCompletionListener(this);
            this.mPlayer.setOnVideoSizeChangedListener(this);
            this.mPlayer.setOnErrorListener(this);
            this.mPlayer.setAudioStreamType(3);
            this.mPlayer.setLooping(this.mLoop);
            LayoutParams textureLp = new LayoutParams(-1, -1);
            this.mTextureView = new TextureView(getContext());
            addView(this.mTextureView, textureLp);
            this.mTextureView.setSurfaceTextureListener(this);
            LayoutInflater li = LayoutInflater.from(getContext());
            this.mProgressFrame = li.inflate(R.layout.evp_include_progress, this, false);
            addView(this.mProgressFrame);
            this.mClickFrame = new FrameLayout(getContext());
            ((FrameLayout) this.mClickFrame).setForeground(Util.resolveDrawable(getContext(), R.attr.selectableItemBackground));
            addView(this.mClickFrame, new ViewGroup.LayoutParams(-1, -1));
            this.mControlsFrame = li.inflate(R.layout.evp_include_controls, this, false);
            LayoutParams controlsLp = new LayoutParams(-1, -2);
            controlsLp.gravity = 80;
            addView(this.mControlsFrame, controlsLp);
            final EasyVideoPlayer easyVideoPlayer = this;
            if (this.mControlsDisabled) {
                this.mClickFrame.setOnClickListener(null);
                this.mControlsFrame.setVisibility(8);
            } else {
                this.mClickFrame.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        EasyVideoPlayer.this.toggleControls();
                        try {
                            EasyVideoPlayer.this.mCallback.onClickVideoFrame(easyVideoPlayer);
                        } catch (Exception e) {
                        }
                    }
                });
            }
            this.mSeeker = (SeekBar) this.mControlsFrame.findViewById(R.id.seeker);
            this.mSeeker.setOnSeekBarChangeListener(this);
            this.mLabelPosition = (TextView) this.mControlsFrame.findViewById(R.id.position);
            this.mLabelPosition.setText(Util.getDurationString(0, false));
            this.mLabelDuration = (TextView) this.mControlsFrame.findViewById(R.id.duration);
            this.mLabelDuration.setText(Util.getDurationString(0, true));
            this.mBtnRestart = (ImageButton) this.mControlsFrame.findViewById(R.id.btnRestart);
            this.mBtnRestart.setOnClickListener(this);
            this.mBtnRestart.setImageDrawable(this.mRestartDrawable);
            this.mBtnRetry = (TextView) this.mControlsFrame.findViewById(R.id.btnRetry);
            this.mBtnRetry.setOnClickListener(this);
            this.mBtnRetry.setText(this.mRetryText);
            this.mBtnPlayPause = (ImageButton) this.mControlsFrame.findViewById(R.id.btnPlayPause);
            this.mBtnPlayPause.setOnClickListener(this);
            this.mBtnPlayPause.setImageDrawable(this.mPlayDrawable);
            this.mBtnSubmit = (TextView) this.mControlsFrame.findViewById(R.id.btnSubmit);
            this.mBtnSubmit.setOnClickListener(this);
            this.mBtnSubmit.setText(this.mSubmitText);
            this.mLabelCustom = (TextView) this.mControlsFrame.findViewById(R.id.labelCustom);
            this.mLabelCustom.setText(this.mCustomLabelText);
            this.mLabelBottom = (TextView) this.mControlsFrame.findViewById(R.id.labelBottom);
            setBottomLabelText(this.mBottomLabelText);
            invalidateThemeColors();
            setControlsEnabled(false);
            invalidateActions();
            prepare();
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnPlayPause) {
            if (this.mPlayer.isPlaying()) {
                pause();
                return;
            }
            if (this.mHideControlsOnPlay && !this.mControlsDisabled) {
                hideControls();
            }
            start();
        } else if (view.getId() == R.id.btnRestart) {
            seekTo(0);
            if (!isPlaying()) {
                start();
            }
        } else if (view.getId() == R.id.btnRetry) {
            if (this.mCallback != null) {
                this.mCallback.onRetry(this, this.mSource);
            }
        } else if (view.getId() == R.id.btnSubmit && this.mCallback != null) {
            this.mCallback.onSubmit(this, this.mSource);
        }
    }

    public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
        if (fromUser) {
            seekTo(value);
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        this.mWasPlaying = isPlaying();
        if (this.mWasPlaying) {
            this.mPlayer.pause();
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        if (this.mWasPlaying) {
            this.mPlayer.start();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LOG("Detached from window", new Object[0]);
        release();
        this.mSeeker = null;
        this.mLabelPosition = null;
        this.mLabelDuration = null;
        this.mBtnPlayPause = null;
        this.mBtnRestart = null;
        this.mBtnSubmit = null;
        this.mControlsFrame = null;
        this.mClickFrame = null;
        this.mProgressFrame = null;
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mUpdateCounters);
            this.mHandler = null;
        }
    }

    private static void LOG(String message, Object... args) {
        if (args != null) {
            try {
                message = String.format(message, args);
            } catch (Exception e) {
                return;
            }
        }
        Log.d("EasyVideoPlayer", message);
    }

    private void invalidateActions() {
        switch (this.mLeftAction) {
            case 0:
                this.mBtnRetry.setVisibility(8);
                this.mBtnRestart.setVisibility(8);
                break;
            case 1:
                this.mBtnRetry.setVisibility(8);
                this.mBtnRestart.setVisibility(0);
                break;
            case 2:
                this.mBtnRetry.setVisibility(0);
                this.mBtnRestart.setVisibility(8);
                break;
        }
        switch (this.mRightAction) {
            case 3:
                this.mBtnSubmit.setVisibility(8);
                this.mLabelCustom.setVisibility(8);
                return;
            case 4:
                this.mBtnSubmit.setVisibility(0);
                this.mLabelCustom.setVisibility(8);
                return;
            case 5:
                this.mBtnSubmit.setVisibility(8);
                this.mLabelCustom.setVisibility(0);
                return;
            default:
                return;
        }
    }

    private void adjustAspectRatio(int viewWidth, int viewHeight, int videoWidth, int videoHeight) {
        int newWidth;
        int newHeight;
        double aspectRatio = ((double) videoHeight) / ((double) videoWidth);
        if (viewHeight > ((int) (((double) viewWidth) * aspectRatio))) {
            newWidth = viewWidth;
            newHeight = (int) (((double) viewWidth) * aspectRatio);
        } else {
            newWidth = (int) (((double) viewHeight) / aspectRatio);
            newHeight = viewHeight;
        }
        int xoff = (viewWidth - newWidth) / 2;
        int yoff = (viewHeight - newHeight) / 2;
        Matrix txform = new Matrix();
        this.mTextureView.getTransform(txform);
        txform.setScale(((float) newWidth) / ((float) viewWidth), ((float) newHeight) / ((float) viewHeight));
        txform.postTranslate((float) xoff, (float) yoff);
        this.mTextureView.setTransform(txform);
    }

    private void throwError(Exception e) {
        if (this.mCallback != null) {
            this.mCallback.onError(this, e);
            return;
        }
        throw new RuntimeException(e);
    }

    private static void setTint(@NonNull SeekBar seekBar, @ColorInt int color) {
        ColorStateList s1 = ColorStateList.valueOf(color);
        if (VERSION.SDK_INT >= 21) {
            seekBar.setThumbTintList(s1);
            seekBar.setProgressTintList(s1);
            seekBar.setSecondaryProgressTintList(s1);
        } else if (VERSION.SDK_INT > 10) {
            Drawable progressDrawable = DrawableCompat.wrap(seekBar.getProgressDrawable());
            seekBar.setProgressDrawable(progressDrawable);
            DrawableCompat.setTintList(progressDrawable, s1);
            if (VERSION.SDK_INT >= 16) {
                Drawable thumbDrawable = DrawableCompat.wrap(seekBar.getThumb());
                DrawableCompat.setTintList(thumbDrawable, s1);
                seekBar.setThumb(thumbDrawable);
            }
        } else {
            Mode mode = Mode.SRC_IN;
            if (VERSION.SDK_INT <= 10) {
                mode = Mode.MULTIPLY;
            }
            if (seekBar.getIndeterminateDrawable() != null) {
                seekBar.getIndeterminateDrawable().setColorFilter(color, mode);
            }
            if (seekBar.getProgressDrawable() != null) {
                seekBar.getProgressDrawable().setColorFilter(color, mode);
            }
        }
    }

    private Drawable tintDrawable(@NonNull Drawable d, @ColorInt int color) {
        d = DrawableCompat.wrap(d.mutate());
        DrawableCompat.setTint(d, color);
        return d;
    }

    private void tintSelector(@NonNull View view, @ColorInt int color) {
        if (VERSION.SDK_INT >= 21 && (view.getBackground() instanceof RippleDrawable)) {
            ((RippleDrawable) view.getBackground()).setColor(ColorStateList.valueOf(Util.adjustAlpha(color, 0.3f)));
        }
    }

    private void invalidateThemeColors() {
        int labelColor = Util.isColorDark(this.mThemeColor) ? -1 : -16777216;
        this.mControlsFrame.setBackgroundColor(Util.adjustAlpha(this.mThemeColor, 0.8f));
        tintSelector(this.mBtnRestart, labelColor);
        tintSelector(this.mBtnPlayPause, labelColor);
        this.mLabelDuration.setTextColor(labelColor);
        this.mLabelPosition.setTextColor(labelColor);
        setTint(this.mSeeker, labelColor);
        this.mBtnRetry.setTextColor(labelColor);
        tintSelector(this.mBtnRetry, labelColor);
        this.mBtnSubmit.setTextColor(labelColor);
        tintSelector(this.mBtnSubmit, labelColor);
        this.mLabelCustom.setTextColor(labelColor);
        this.mLabelBottom.setTextColor(labelColor);
        this.mPlayDrawable = tintDrawable(this.mPlayDrawable.mutate(), labelColor);
        this.mRestartDrawable = tintDrawable(this.mRestartDrawable.mutate(), labelColor);
        this.mPauseDrawable = tintDrawable(this.mPauseDrawable.mutate(), labelColor);
    }

    @TargetApi(14)
    private void setFullscreen(boolean fullscreen) {
        boolean z = true;
        if (VERSION.SDK_INT >= 14 && this.mAutoFullscreen) {
            int flags = !fullscreen ? 0 : 1;
            View view = this.mControlsFrame;
            if (fullscreen) {
                z = false;
            }
            ViewCompat.setFitsSystemWindows(view, z);
            if (VERSION.SDK_INT >= 19) {
                flags |= 1792;
                if (fullscreen) {
                    flags |= 2054;
                }
            }
            this.mClickFrame.setSystemUiVisibility(flags);
        }
    }
}
