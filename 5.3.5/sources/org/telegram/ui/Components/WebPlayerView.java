package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.support.v4.internal.view.SupportMenu;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.google.android.gms.wearable.WearableStatusCodes;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.thin.downloadmanager.BuildConfig;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ir.talaeii.R;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.Bitmaps;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.ui.AspectRatioFrameLayout;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.tgnet.TLRPC$Photo;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate;

public class WebPlayerView extends ViewGroup implements VideoPlayerDelegate, OnAudioFocusChangeListener {
    private static final int AUDIO_FOCUSED = 2;
    private static final int AUDIO_NO_FOCUS_CAN_DUCK = 1;
    private static final int AUDIO_NO_FOCUS_NO_DUCK = 0;
    private static final Pattern aparatFileListPattern = Pattern.compile("fileList\\s*=\\s*JSON\\.parse\\('([^']+)'\\)");
    private static final Pattern aparatIdRegex = Pattern.compile("^https?://(?:www\\.)?aparat\\.com/(?:v/|video/video/embed/videohash/)([a-zA-Z0-9]+)");
    private static final Pattern coubIdRegex = Pattern.compile("(?:coub:|https?://(?:coub\\.com/(?:view|embed|coubs)/|c-cdn\\.coub\\.com/fb-player\\.swf\\?.*\\bcoub(?:ID|id)=))([\\da-z]+)");
    private static final String exprName = "[a-zA-Z_$][a-zA-Z_$0-9]*";
    private static final Pattern exprParensPattern = Pattern.compile("[()]");
    private static final Pattern jsPattern = Pattern.compile("\"assets\":.+?\"js\":\\s*(\"[^\"]+\")");
    private static int lastContainerId = WearableStatusCodes.DUPLICATE_LISTENER;
    private static final Pattern playerIdPattern = Pattern.compile(".*?-([a-zA-Z0-9_-]+)(?:/watch_as3|/html5player(?:-new)?|(?:/[a-z]{2}_[A-Z]{2})?/base)?\\.([a-z]+)$");
    private static final Pattern sigPattern = Pattern.compile("\\.sig\\|\\|([a-zA-Z0-9$]+)\\(");
    private static final Pattern sigPattern2 = Pattern.compile("[\"']signature[\"']\\s*,\\s*([a-zA-Z0-9$]+)\\(");
    private static final Pattern stmtReturnPattern = Pattern.compile("return(?:\\s+|$)");
    private static final Pattern stmtVarPattern = Pattern.compile("var\\s");
    private static final Pattern stsPattern = Pattern.compile("\"sts\"\\s*:\\s*(\\d+)");
    private static final Pattern twitchClipFilePattern = Pattern.compile("clipInfo\\s*=\\s*(\\{[^']+\\});");
    private static final Pattern twitchClipIdRegex = Pattern.compile("https?://clips\\.twitch\\.tv/(?:[^/]+/)*([^/?#&]+)");
    private static final Pattern twitchStreamIdRegex = Pattern.compile("https?://(?:(?:www\\.)?twitch\\.tv/|player\\.twitch\\.tv/\\?.*?\\bchannel=)([^/#?]+)");
    private static final Pattern vimeoIdRegex = Pattern.compile("https?://(?:(?:www|(player))\\.)?vimeo(pro)?\\.com/(?!(?:channels|album)/[^/?#]+/?(?:$|[?#])|[^/]+/review/|ondemand/)(?:.*?/)?(?:(?:play_redirect_hls|moogaloop\\.swf)\\?clip_id=)?(?:videos?/)?([0-9]+)(?:/[\\da-f]+)?/?(?:[?&].*)?(?:[#].*)?$");
    private static final Pattern youtubeIdRegex = Pattern.compile("(?:youtube(?:-nocookie)?\\.com/(?:[^/\\n\\s]+/\\S+/|(?:v|e(?:mbed)?)/|\\S*?[?&]v=)|youtu\\.be/)([a-zA-Z0-9_-]{11})");
    private boolean allowInlineAnimation;
    private AspectRatioFrameLayout aspectRatioFrameLayout;
    private int audioFocus;
    private Paint backgroundPaint;
    private TextureView changedTextureView;
    private boolean changingTextureView;
    private ControlsView controlsView;
    private float currentAlpha;
    private Bitmap currentBitmap;
    private AsyncTask currentTask;
    private String currentYoutubeId;
    private WebPlayerViewDelegate delegate;
    private boolean drawImage;
    private boolean firstFrameRendered;
    private int fragment_container_id;
    private ImageView fullscreenButton;
    private boolean hasAudioFocus;
    private boolean inFullscreen;
    private boolean initFailed;
    private boolean initied;
    private ImageView inlineButton;
    private String interfaceName;
    private boolean isAutoplay;
    private boolean isCompleted;
    private boolean isInline;
    private boolean isLoading;
    private boolean isStream;
    private long lastUpdateTime;
    private String playAudioType;
    private String playAudioUrl;
    private ImageView playButton;
    private String playVideoType;
    private String playVideoUrl;
    private AnimatorSet progressAnimation;
    private Runnable progressRunnable;
    private RadialProgressView progressView;
    private boolean resumeAudioOnFocusGain;
    private int seekToTime;
    private ImageView shareButton;
    private SurfaceTextureListener surfaceTextureListener;
    private Runnable switchToInlineRunnable;
    private boolean switchingInlineMode;
    private ImageView textureImageView;
    private TextureView textureView;
    private ViewGroup textureViewContainer;
    private VideoPlayer videoPlayer;
    private int waitingForFirstTextureUpload;
    private WebView webView;

    public interface WebPlayerViewDelegate {
        boolean checkInlinePermissons();

        ViewGroup getTextureViewContainer();

        void onInitFailed();

        void onInlineSurfaceTextureReady();

        void onPlayStateChanged(WebPlayerView webPlayerView, boolean z);

        void onSharePressed();

        TextureView onSwitchInlineMode(View view, boolean z, float f, int i, boolean z2);

        TextureView onSwitchToFullscreen(View view, boolean z, float f, int i, boolean z2);

        void onVideoSizeChanged(float f, int i);

        void prepareToSwitchInlineMode(boolean z, Runnable runnable, float f, boolean z2);
    }

    /* renamed from: org.telegram.ui.Components.WebPlayerView$1 */
    class C28051 implements Runnable {
        C28051() {
        }

        public void run() {
            if (WebPlayerView.this.videoPlayer != null && WebPlayerView.this.videoPlayer.isPlaying()) {
                WebPlayerView.this.controlsView.setProgress((int) (WebPlayerView.this.videoPlayer.getCurrentPosition() / 1000));
                WebPlayerView.this.controlsView.setBufferedProgress((int) (WebPlayerView.this.videoPlayer.getBufferedPosition() / 1000), WebPlayerView.this.videoPlayer.getBufferedPercentage());
                AndroidUtilities.runOnUIThread(WebPlayerView.this.progressRunnable, 1000);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.WebPlayerView$2 */
    class C28082 implements SurfaceTextureListener {

        /* renamed from: org.telegram.ui.Components.WebPlayerView$2$1 */
        class C28071 implements OnPreDrawListener {

            /* renamed from: org.telegram.ui.Components.WebPlayerView$2$1$1 */
            class C28061 implements Runnable {
                C28061() {
                }

                public void run() {
                    WebPlayerView.this.delegate.onInlineSurfaceTextureReady();
                }
            }

            C28071() {
            }

            public boolean onPreDraw() {
                WebPlayerView.this.changedTextureView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (WebPlayerView.this.textureImageView != null) {
                    WebPlayerView.this.textureImageView.setVisibility(4);
                    WebPlayerView.this.textureImageView.setImageDrawable(null);
                    if (WebPlayerView.this.currentBitmap != null) {
                        WebPlayerView.this.currentBitmap.recycle();
                        WebPlayerView.this.currentBitmap = null;
                    }
                }
                AndroidUtilities.runOnUIThread(new C28061());
                WebPlayerView.this.waitingForFirstTextureUpload = 0;
                return true;
            }
        }

        C28082() {
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            if (!WebPlayerView.this.changingTextureView) {
                return true;
            }
            if (WebPlayerView.this.switchingInlineMode) {
                WebPlayerView.this.waitingForFirstTextureUpload = 2;
            }
            WebPlayerView.this.textureView.setSurfaceTexture(surface);
            WebPlayerView.this.textureView.setVisibility(0);
            WebPlayerView.this.changingTextureView = false;
            return false;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            if (WebPlayerView.this.waitingForFirstTextureUpload == 1) {
                WebPlayerView.this.changedTextureView.getViewTreeObserver().addOnPreDrawListener(new C28071());
                WebPlayerView.this.changedTextureView.invalidate();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.WebPlayerView$3 */
    class C28093 implements Runnable {
        C28093() {
        }

        public void run() {
            WebPlayerView.this.switchingInlineMode = false;
            if (WebPlayerView.this.currentBitmap != null) {
                WebPlayerView.this.currentBitmap.recycle();
                WebPlayerView.this.currentBitmap = null;
            }
            WebPlayerView.this.changingTextureView = true;
            if (WebPlayerView.this.textureImageView != null) {
                try {
                    WebPlayerView.this.currentBitmap = Bitmaps.createBitmap(WebPlayerView.this.textureView.getWidth(), WebPlayerView.this.textureView.getHeight(), Config.ARGB_8888);
                    WebPlayerView.this.textureView.getBitmap(WebPlayerView.this.currentBitmap);
                } catch (Throwable e) {
                    if (WebPlayerView.this.currentBitmap != null) {
                        WebPlayerView.this.currentBitmap.recycle();
                        WebPlayerView.this.currentBitmap = null;
                    }
                    FileLog.e(e);
                }
                if (WebPlayerView.this.currentBitmap != null) {
                    WebPlayerView.this.textureImageView.setVisibility(0);
                    WebPlayerView.this.textureImageView.setImageBitmap(WebPlayerView.this.currentBitmap);
                } else {
                    WebPlayerView.this.textureImageView.setImageDrawable(null);
                }
            }
            WebPlayerView.this.isInline = true;
            WebPlayerView.this.updatePlayButton();
            WebPlayerView.this.updateShareButton();
            WebPlayerView.this.updateFullscreenButton();
            WebPlayerView.this.updateInlineButton();
            ViewGroup viewGroup = (ViewGroup) WebPlayerView.this.controlsView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(WebPlayerView.this.controlsView);
            }
            WebPlayerView.this.changedTextureView = WebPlayerView.this.delegate.onSwitchInlineMode(WebPlayerView.this.controlsView, WebPlayerView.this.isInline, WebPlayerView.this.aspectRatioFrameLayout.getAspectRatio(), WebPlayerView.this.aspectRatioFrameLayout.getVideoRotation(), WebPlayerView.this.allowInlineAnimation);
            WebPlayerView.this.changedTextureView.setVisibility(4);
            ViewGroup parent = (ViewGroup) WebPlayerView.this.textureView.getParent();
            if (parent != null) {
                parent.removeView(WebPlayerView.this.textureView);
            }
            WebPlayerView.this.controlsView.show(false, false);
        }
    }

    public interface CallJavaResultInterface {
        void jsCallFinished(String str);
    }

    /* renamed from: org.telegram.ui.Components.WebPlayerView$5 */
    class C28115 implements CallJavaResultInterface {
        C28115() {
        }

        public void jsCallFinished(String value) {
            if (WebPlayerView.this.currentTask != null && !WebPlayerView.this.currentTask.isCancelled() && (WebPlayerView.this.currentTask instanceof YoutubeVideoTask)) {
                ((YoutubeVideoTask) WebPlayerView.this.currentTask).onInterfaceResult(value);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.WebPlayerView$6 */
    class C28126 implements OnClickListener {
        C28126() {
        }

        public void onClick(View v) {
            if (WebPlayerView.this.initied && !WebPlayerView.this.changingTextureView && !WebPlayerView.this.switchingInlineMode && WebPlayerView.this.firstFrameRendered) {
                WebPlayerView.this.inFullscreen = !WebPlayerView.this.inFullscreen;
                WebPlayerView.this.updateFullscreenState(true);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.WebPlayerView$7 */
    class C28137 implements OnClickListener {
        C28137() {
        }

        public void onClick(View v) {
            if (WebPlayerView.this.initied && WebPlayerView.this.playVideoUrl != null) {
                if (!WebPlayerView.this.videoPlayer.isPlayerPrepared()) {
                    WebPlayerView.this.preparePlayer();
                }
                if (WebPlayerView.this.videoPlayer.isPlaying()) {
                    WebPlayerView.this.videoPlayer.pause();
                } else {
                    WebPlayerView.this.isCompleted = false;
                    WebPlayerView.this.videoPlayer.play();
                }
                WebPlayerView.this.updatePlayButton();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.WebPlayerView$8 */
    class C28148 implements OnClickListener {
        C28148() {
        }

        public void onClick(View v) {
            if (WebPlayerView.this.textureView != null && WebPlayerView.this.delegate.checkInlinePermissons() && !WebPlayerView.this.changingTextureView && !WebPlayerView.this.switchingInlineMode && WebPlayerView.this.firstFrameRendered) {
                WebPlayerView.this.switchingInlineMode = true;
                if (WebPlayerView.this.isInline) {
                    ViewGroup parent = (ViewGroup) WebPlayerView.this.aspectRatioFrameLayout.getParent();
                    if (parent != WebPlayerView.this) {
                        if (parent != null) {
                            parent.removeView(WebPlayerView.this.aspectRatioFrameLayout);
                        }
                        WebPlayerView.this.addView(WebPlayerView.this.aspectRatioFrameLayout, 0, LayoutHelper.createFrame(-1, -1, 17));
                        WebPlayerView.this.aspectRatioFrameLayout.measure(MeasureSpec.makeMeasureSpec(WebPlayerView.this.getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(WebPlayerView.this.getMeasuredHeight() - AndroidUtilities.dp(10.0f), 1073741824));
                    }
                    if (WebPlayerView.this.currentBitmap != null) {
                        WebPlayerView.this.currentBitmap.recycle();
                        WebPlayerView.this.currentBitmap = null;
                    }
                    WebPlayerView.this.changingTextureView = true;
                    WebPlayerView.this.isInline = false;
                    WebPlayerView.this.updatePlayButton();
                    WebPlayerView.this.updateShareButton();
                    WebPlayerView.this.updateFullscreenButton();
                    WebPlayerView.this.updateInlineButton();
                    WebPlayerView.this.textureView.setVisibility(4);
                    if (WebPlayerView.this.textureViewContainer != null) {
                        WebPlayerView.this.textureViewContainer.addView(WebPlayerView.this.textureView);
                    } else {
                        WebPlayerView.this.aspectRatioFrameLayout.addView(WebPlayerView.this.textureView);
                    }
                    parent = (ViewGroup) WebPlayerView.this.controlsView.getParent();
                    if (parent != WebPlayerView.this) {
                        if (parent != null) {
                            parent.removeView(WebPlayerView.this.controlsView);
                        }
                        if (WebPlayerView.this.textureViewContainer != null) {
                            WebPlayerView.this.textureViewContainer.addView(WebPlayerView.this.controlsView);
                        } else {
                            WebPlayerView.this.addView(WebPlayerView.this.controlsView, 1);
                        }
                    }
                    WebPlayerView.this.controlsView.show(false, false);
                    WebPlayerView.this.delegate.prepareToSwitchInlineMode(false, null, WebPlayerView.this.aspectRatioFrameLayout.getAspectRatio(), WebPlayerView.this.allowInlineAnimation);
                    return;
                }
                WebPlayerView.this.inFullscreen = false;
                WebPlayerView.this.delegate.prepareToSwitchInlineMode(true, WebPlayerView.this.switchToInlineRunnable, WebPlayerView.this.aspectRatioFrameLayout.getAspectRatio(), WebPlayerView.this.allowInlineAnimation);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.WebPlayerView$9 */
    class C28159 implements OnClickListener {
        C28159() {
        }

        public void onClick(View v) {
            if (WebPlayerView.this.delegate != null) {
                WebPlayerView.this.delegate.onSharePressed();
            }
        }
    }

    private class AparatVideoTask extends AsyncTask<Void, Void, String> {
        private boolean canRetry = true;
        private String[] results = new String[2];
        private String videoId;

        public AparatVideoTask(String vid) {
            this.videoId = vid;
        }

        protected String doInBackground(Void... voids) {
            String playerCode = WebPlayerView.this.downloadUrlContent(this, String.format(Locale.US, "http://www.aparat.com/video/video/embed/vt/frame/showvideo/yes/videohash/%s", new Object[]{this.videoId}));
            if (isCancelled()) {
                return null;
            }
            try {
                Matcher filelist = WebPlayerView.aparatFileListPattern.matcher(playerCode);
                if (filelist.find()) {
                    JSONArray json = new JSONArray(filelist.group(1));
                    for (int a = 0; a < json.length(); a++) {
                        JSONArray array = json.getJSONArray(a);
                        if (array.length() != 0) {
                            JSONObject object = array.getJSONObject(0);
                            if (object.has("file")) {
                                this.results[0] = object.getString("file");
                                this.results[1] = "other";
                            }
                        }
                    }
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            return isCancelled() ? null : this.results[0];
        }

        protected void onPostExecute(String result) {
            if (result != null) {
                WebPlayerView.this.initied = true;
                WebPlayerView.this.playVideoUrl = result;
                WebPlayerView.this.playVideoType = this.results[1];
                if (WebPlayerView.this.isAutoplay) {
                    WebPlayerView.this.preparePlayer();
                }
                WebPlayerView.this.showProgress(false, true);
                WebPlayerView.this.controlsView.show(true, true);
            } else if (!isCancelled()) {
                WebPlayerView.this.onInitFailed();
            }
        }
    }

    private class ControlsView extends FrameLayout {
        private int bufferedPercentage;
        private int bufferedPosition;
        private AnimatorSet currentAnimation;
        private int currentProgressX;
        private int duration;
        private StaticLayout durationLayout;
        private int durationWidth;
        private Runnable hideRunnable = new C28161();
        private ImageReceiver imageReceiver;
        private boolean isVisible = true;
        private int lastProgressX;
        private int progress;
        private Paint progressBufferedPaint;
        private Paint progressInnerPaint;
        private StaticLayout progressLayout;
        private Paint progressPaint;
        private boolean progressPressed;
        private TextPaint textPaint;

        /* renamed from: org.telegram.ui.Components.WebPlayerView$ControlsView$1 */
        class C28161 implements Runnable {
            C28161() {
            }

            public void run() {
                ControlsView.this.show(false, true);
            }
        }

        /* renamed from: org.telegram.ui.Components.WebPlayerView$ControlsView$2 */
        class C28172 extends AnimatorListenerAdapter {
            C28172() {
            }

            public void onAnimationEnd(Animator animator) {
                ControlsView.this.currentAnimation = null;
            }
        }

        /* renamed from: org.telegram.ui.Components.WebPlayerView$ControlsView$3 */
        class C28183 extends AnimatorListenerAdapter {
            C28183() {
            }

            public void onAnimationEnd(Animator animator) {
                ControlsView.this.currentAnimation = null;
            }
        }

        public ControlsView(Context context) {
            super(context);
            setWillNotDraw(false);
            this.textPaint = new TextPaint(1);
            this.textPaint.setColor(-1);
            this.textPaint.setTextSize((float) AndroidUtilities.dp(12.0f));
            this.progressPaint = new Paint(1);
            this.progressPaint.setColor(-15095832);
            this.progressInnerPaint = new Paint();
            this.progressInnerPaint.setColor(-6975081);
            this.progressBufferedPaint = new Paint(1);
            this.progressBufferedPaint.setColor(-1);
            this.imageReceiver = new ImageReceiver(this);
        }

        public void setDuration(int value) {
            if (this.duration != value && value >= 0 && !WebPlayerView.this.isStream) {
                this.duration = value;
                this.durationLayout = new StaticLayout(String.format(Locale.US, "%d:%02d", new Object[]{Integer.valueOf(this.duration / 60), Integer.valueOf(this.duration % 60)}), this.textPaint, AndroidUtilities.dp(1000.0f), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                if (this.durationLayout.getLineCount() > 0) {
                    this.durationWidth = (int) Math.ceil((double) this.durationLayout.getLineWidth(0));
                }
                invalidate();
            }
        }

        public void setBufferedProgress(int position, int percentage) {
            this.bufferedPosition = position;
            this.bufferedPercentage = percentage;
            invalidate();
        }

        public void setProgress(int value) {
            if (!this.progressPressed && value >= 0 && !WebPlayerView.this.isStream) {
                this.progress = value;
                this.progressLayout = new StaticLayout(String.format(Locale.US, "%d:%02d", new Object[]{Integer.valueOf(this.progress / 60), Integer.valueOf(this.progress % 60)}), this.textPaint, AndroidUtilities.dp(1000.0f), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                invalidate();
            }
        }

        public void show(boolean value, boolean animated) {
            if (this.isVisible != value) {
                this.isVisible = value;
                if (this.currentAnimation != null) {
                    this.currentAnimation.cancel();
                }
                AnimatorSet animatorSet;
                Animator[] animatorArr;
                if (this.isVisible) {
                    if (animated) {
                        this.currentAnimation = new AnimatorSet();
                        animatorSet = this.currentAnimation;
                        animatorArr = new Animator[1];
                        animatorArr[0] = ObjectAnimator.ofFloat(this, "alpha", new float[]{1.0f});
                        animatorSet.playTogether(animatorArr);
                        this.currentAnimation.setDuration(150);
                        this.currentAnimation.addListener(new C28172());
                        this.currentAnimation.start();
                    } else {
                        setAlpha(1.0f);
                    }
                } else if (animated) {
                    this.currentAnimation = new AnimatorSet();
                    animatorSet = this.currentAnimation;
                    animatorArr = new Animator[1];
                    animatorArr[0] = ObjectAnimator.ofFloat(this, "alpha", new float[]{0.0f});
                    animatorSet.playTogether(animatorArr);
                    this.currentAnimation.setDuration(150);
                    this.currentAnimation.addListener(new C28183());
                    this.currentAnimation.start();
                } else {
                    setAlpha(0.0f);
                }
                checkNeedHide();
            }
        }

        private void checkNeedHide() {
            AndroidUtilities.cancelRunOnUIThread(this.hideRunnable);
            if (this.isVisible && WebPlayerView.this.videoPlayer.isPlaying()) {
                AndroidUtilities.runOnUIThread(this.hideRunnable, 3000);
            }
        }

        public boolean onInterceptTouchEvent(MotionEvent ev) {
            if (ev.getAction() != 0) {
                return super.onInterceptTouchEvent(ev);
            }
            if (this.isVisible) {
                onTouchEvent(ev);
                return this.progressPressed;
            }
            show(true, true);
            return true;
        }

        public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            super.requestDisallowInterceptTouchEvent(disallowIntercept);
            checkNeedHide();
        }

        public boolean onTouchEvent(MotionEvent event) {
            int progressLineX;
            int progressLineEndX;
            int i;
            int progressY;
            if (WebPlayerView.this.inFullscreen) {
                progressLineX = AndroidUtilities.dp(36.0f) + this.durationWidth;
                progressLineEndX = (getMeasuredWidth() - AndroidUtilities.dp(76.0f)) - this.durationWidth;
                progressY = getMeasuredHeight() - AndroidUtilities.dp(28.0f);
            } else {
                progressLineX = 0;
                progressLineEndX = getMeasuredWidth();
                progressY = getMeasuredHeight() - AndroidUtilities.dp(12.0f);
            }
            if (this.duration != 0) {
                i = (int) (((float) (progressLineEndX - progressLineX)) * (((float) this.progress) / ((float) this.duration)));
            } else {
                i = 0;
            }
            int progressX = progressLineX + i;
            int x;
            if (event.getAction() == 0) {
                if (!this.isVisible || WebPlayerView.this.isInline || WebPlayerView.this.isStream) {
                    show(true, true);
                } else if (this.duration != 0) {
                    x = (int) event.getX();
                    int y = (int) event.getY();
                    if (x >= progressX - AndroidUtilities.dp(10.0f) && x <= AndroidUtilities.dp(10.0f) + progressX && y >= progressY - AndroidUtilities.dp(10.0f) && y <= AndroidUtilities.dp(10.0f) + progressY) {
                        this.progressPressed = true;
                        this.lastProgressX = x;
                        this.currentProgressX = progressX;
                        getParent().requestDisallowInterceptTouchEvent(true);
                        invalidate();
                    }
                }
                AndroidUtilities.cancelRunOnUIThread(this.hideRunnable);
            } else if (event.getAction() == 1 || event.getAction() == 3) {
                if (WebPlayerView.this.initied && WebPlayerView.this.videoPlayer.isPlaying()) {
                    AndroidUtilities.runOnUIThread(this.hideRunnable, 3000);
                }
                if (this.progressPressed) {
                    this.progressPressed = false;
                    if (WebPlayerView.this.initied) {
                        this.progress = (int) (((float) this.duration) * (((float) (this.currentProgressX - progressLineX)) / ((float) (progressLineEndX - progressLineX))));
                        WebPlayerView.this.videoPlayer.seekTo(((long) this.progress) * 1000);
                    }
                }
            } else if (event.getAction() == 2 && this.progressPressed) {
                x = (int) event.getX();
                this.currentProgressX -= this.lastProgressX - x;
                this.lastProgressX = x;
                if (this.currentProgressX < progressLineX) {
                    this.currentProgressX = progressLineX;
                } else if (this.currentProgressX > progressLineEndX) {
                    this.currentProgressX = progressLineEndX;
                }
                setProgress((int) (((float) (this.duration * 1000)) * (((float) (this.currentProgressX - progressLineX)) / ((float) (progressLineEndX - progressLineX)))));
                invalidate();
            }
            super.onTouchEvent(event);
            return true;
        }

        protected void onDraw(Canvas canvas) {
            if (WebPlayerView.this.drawImage) {
                if (WebPlayerView.this.firstFrameRendered && WebPlayerView.this.currentAlpha != 0.0f) {
                    long newTime = System.currentTimeMillis();
                    long dt = newTime - WebPlayerView.this.lastUpdateTime;
                    WebPlayerView.this.lastUpdateTime = newTime;
                    WebPlayerView.this.currentAlpha = WebPlayerView.this.currentAlpha - (((float) dt) / 150.0f);
                    if (WebPlayerView.this.currentAlpha < 0.0f) {
                        WebPlayerView.this.currentAlpha = 0.0f;
                    }
                    invalidate();
                }
                this.imageReceiver.setAlpha(WebPlayerView.this.currentAlpha);
                this.imageReceiver.draw(canvas);
            }
            if (WebPlayerView.this.videoPlayer.isPlayerPrepared() && !WebPlayerView.this.isStream) {
                int width = getMeasuredWidth();
                int height = getMeasuredHeight();
                if (!WebPlayerView.this.isInline) {
                    if (this.durationLayout != null) {
                        canvas.save();
                        canvas.translate((float) ((width - AndroidUtilities.dp(58.0f)) - this.durationWidth), (float) (height - AndroidUtilities.dp((float) ((WebPlayerView.this.inFullscreen ? 6 : 10) + 29))));
                        this.durationLayout.draw(canvas);
                        canvas.restore();
                    }
                    if (this.progressLayout != null) {
                        canvas.save();
                        canvas.translate((float) AndroidUtilities.dp(18.0f), (float) (height - AndroidUtilities.dp((float) ((WebPlayerView.this.inFullscreen ? 6 : 10) + 29))));
                        this.progressLayout.draw(canvas);
                        canvas.restore();
                    }
                }
                if (this.duration != 0) {
                    int progressLineY;
                    int progressLineX;
                    int progressLineEndX;
                    int cy;
                    int progressX;
                    if (WebPlayerView.this.isInline) {
                        progressLineY = height - AndroidUtilities.dp(3.0f);
                        progressLineX = 0;
                        progressLineEndX = width;
                        cy = height - AndroidUtilities.dp(7.0f);
                    } else if (WebPlayerView.this.inFullscreen) {
                        progressLineY = height - AndroidUtilities.dp(29.0f);
                        progressLineX = AndroidUtilities.dp(36.0f) + this.durationWidth;
                        progressLineEndX = (width - AndroidUtilities.dp(76.0f)) - this.durationWidth;
                        cy = height - AndroidUtilities.dp(28.0f);
                    } else {
                        progressLineY = height - AndroidUtilities.dp(13.0f);
                        progressLineX = 0;
                        progressLineEndX = width;
                        cy = height - AndroidUtilities.dp(12.0f);
                    }
                    if (WebPlayerView.this.inFullscreen) {
                        canvas.drawRect((float) progressLineX, (float) progressLineY, (float) progressLineEndX, (float) (AndroidUtilities.dp(3.0f) + progressLineY), this.progressInnerPaint);
                    }
                    if (this.progressPressed) {
                        progressX = this.currentProgressX;
                    } else {
                        progressX = progressLineX + ((int) (((float) (progressLineEndX - progressLineX)) * (((float) this.progress) / ((float) this.duration))));
                    }
                    if (!(this.bufferedPercentage == 0 || this.duration == 0)) {
                        int start = progressLineX + (this.bufferedPosition * ((progressLineEndX - progressLineX) / this.duration));
                        int additional = 0;
                        if (progressX < start) {
                            additional = start - progressX;
                        }
                        canvas.drawRect((float) (start - additional), (float) progressLineY, (((float) ((progressLineEndX - start) * this.bufferedPercentage)) / 100.0f) + ((float) start), (float) (AndroidUtilities.dp(3.0f) + progressLineY), WebPlayerView.this.inFullscreen ? this.progressBufferedPaint : this.progressInnerPaint);
                    }
                    canvas.drawRect((float) progressLineX, (float) progressLineY, (float) progressX, (float) (AndroidUtilities.dp(3.0f) + progressLineY), this.progressPaint);
                    if (!WebPlayerView.this.isInline) {
                        canvas.drawCircle((float) progressX, (float) cy, (float) AndroidUtilities.dp(this.progressPressed ? 7.0f : 5.0f), this.progressPaint);
                    }
                }
            }
        }
    }

    private class CoubVideoTask extends AsyncTask<Void, Void, String> {
        private boolean canRetry = true;
        private String[] results = new String[4];
        private String videoId;

        public CoubVideoTask(String vid) {
            this.videoId = vid;
        }

        private String decodeUrl(String input) {
            StringBuilder source = new StringBuilder(input);
            for (int a = 0; a < source.length(); a++) {
                char c = source.charAt(a);
                char lower = Character.toLowerCase(c);
                if (c == lower) {
                    lower = Character.toUpperCase(c);
                }
                source.setCharAt(a, lower);
            }
            try {
                return new String(Base64.decode(source.toString(), 0), "UTF-8");
            } catch (Exception e) {
                return null;
            }
        }

        protected String doInBackground(Void... voids) {
            String playerCode = WebPlayerView.this.downloadUrlContent(this, String.format(Locale.US, "https://coub.com/api/v2/coubs/%s.json", new Object[]{this.videoId}));
            if (isCancelled()) {
                return null;
            }
            try {
                JSONObject json = new JSONObject(playerCode).getJSONObject("file_versions").getJSONObject("mobile");
                String video = decodeUrl(json.getString("gifv"));
                String audio = json.getJSONArray(MimeTypes.BASE_TYPE_AUDIO).getString(0);
                if (!(video == null || audio == null)) {
                    this.results[0] = video;
                    this.results[1] = "other";
                    this.results[2] = audio;
                    this.results[3] = "other";
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (isCancelled()) {
                return null;
            }
            return this.results[0];
        }

        protected void onPostExecute(String result) {
            if (result != null) {
                WebPlayerView.this.initied = true;
                WebPlayerView.this.playVideoUrl = result;
                WebPlayerView.this.playVideoType = this.results[1];
                WebPlayerView.this.playAudioUrl = this.results[2];
                WebPlayerView.this.playAudioType = this.results[3];
                if (WebPlayerView.this.isAutoplay) {
                    WebPlayerView.this.preparePlayer();
                }
                WebPlayerView.this.showProgress(false, true);
                WebPlayerView.this.controlsView.show(true, true);
            } else if (!isCancelled()) {
                WebPlayerView.this.onInitFailed();
            }
        }
    }

    private class JSExtractor {
        private String[] assign_operators = new String[]{"|=", "^=", "&=", ">>=", "<<=", "-=", "+=", "%=", "/=", "*=", "="};
        ArrayList<String> codeLines = new ArrayList();
        private String jsCode;
        private String[] operators = new String[]{"|", "^", "&", ">>", "<<", "-", "+", "%", "/", "*"};

        public JSExtractor(String js) {
            this.jsCode = js;
        }

        private void interpretExpression(String expr, HashMap<String, String> localVars, int allowRecursion) throws Exception {
            expr = expr.trim();
            if (!TextUtils.isEmpty(expr)) {
                Matcher matcher;
                if (expr.charAt(0) == '(') {
                    int parens_count = 0;
                    matcher = WebPlayerView.exprParensPattern.matcher(expr);
                    while (matcher.find()) {
                        if (matcher.group(0).indexOf(48) == 40) {
                            parens_count++;
                        } else {
                            parens_count--;
                            if (parens_count == 0) {
                                interpretExpression(expr.substring(1, matcher.start()), localVars, allowRecursion);
                                String remaining_expr = expr.substring(matcher.end()).trim();
                                if (!TextUtils.isEmpty(remaining_expr)) {
                                    expr = remaining_expr;
                                    if (parens_count != 0) {
                                        throw new Exception(String.format("Premature end of parens in %s", new Object[]{expr}));
                                    }
                                }
                                return;
                            }
                        }
                    }
                    if (parens_count != 0) {
                        throw new Exception(String.format("Premature end of parens in %s", new Object[]{expr}));
                    }
                }
                for (String func : this.assign_operators) {
                    matcher = Pattern.compile(String.format(Locale.US, "(?x)(%s)(?:\\[([^\\]]+?)\\])?\\s*%s(.*)$", new Object[]{WebPlayerView.exprName, Pattern.quote(func)})).matcher(expr);
                    if (matcher.find()) {
                        interpretExpression(matcher.group(3), localVars, allowRecursion - 1);
                        String index = matcher.group(2);
                        if (TextUtils.isEmpty(index)) {
                            localVars.put(matcher.group(1), "");
                            return;
                        }
                        interpretExpression(index, localVars, allowRecursion);
                        return;
                    }
                }
                try {
                    Integer.parseInt(expr);
                } catch (Exception e) {
                    if (!Pattern.compile(String.format(Locale.US, "(?!if|return|true|false)(%s)$", new Object[]{WebPlayerView.exprName})).matcher(expr).find()) {
                        if (expr.charAt(0) != '\"' || expr.charAt(expr.length() - 1) != '\"') {
                            try {
                                new JSONObject(expr).toString();
                            } catch (Exception e2) {
                                matcher = Pattern.compile(String.format(Locale.US, "(%s)\\[(.+)\\]$", new Object[]{WebPlayerView.exprName})).matcher(expr);
                                if (matcher.find()) {
                                    String val = matcher.group(1);
                                    interpretExpression(matcher.group(2), localVars, allowRecursion - 1);
                                    return;
                                }
                                matcher = Pattern.compile(String.format(Locale.US, "(%s)(?:\\.([^(]+)|\\[([^]]+)\\])\\s*(?:\\(+([^()]*)\\))?$", new Object[]{WebPlayerView.exprName})).matcher(expr);
                                if (matcher.find()) {
                                    String variable = matcher.group(1);
                                    String m1 = matcher.group(2);
                                    String m2 = matcher.group(3);
                                    if (!TextUtils.isEmpty(m1)) {
                                        m2 = m1;
                                    }
                                    String member = m2.replace("\"", "");
                                    String arg_str = matcher.group(4);
                                    if (localVars.get(variable) == null) {
                                        extractObject(variable);
                                    }
                                    if (arg_str == null) {
                                        return;
                                    }
                                    if (expr.charAt(expr.length() - 1) != ')') {
                                        throw new Exception("last char not ')'");
                                    } else if (arg_str.length() != 0) {
                                        String[] args = arg_str.split(",");
                                        for (String interpretExpression : args) {
                                            interpretExpression(interpretExpression, localVars, allowRecursion);
                                        }
                                        return;
                                    } else {
                                        return;
                                    }
                                }
                                matcher = Pattern.compile(String.format(Locale.US, "(%s)\\[(.+)\\]$", new Object[]{WebPlayerView.exprName})).matcher(expr);
                                if (matcher.find()) {
                                    Object val2 = localVars.get(matcher.group(1));
                                    interpretExpression(matcher.group(2), localVars, allowRecursion - 1);
                                    return;
                                }
                                for (String func2 : this.operators) {
                                    matcher = Pattern.compile(String.format(Locale.US, "(.+?)%s(.+)", new Object[]{Pattern.quote(func2)})).matcher(expr);
                                    if (matcher.find()) {
                                        boolean[] abort = new boolean[1];
                                        interpretStatement(matcher.group(1), localVars, abort, allowRecursion - 1);
                                        if (abort[0]) {
                                            throw new Exception(String.format("Premature left-side return of %s in %s", new Object[]{func2, expr}));
                                        }
                                        interpretStatement(matcher.group(2), localVars, abort, allowRecursion - 1);
                                        if (abort[0]) {
                                            throw new Exception(String.format("Premature right-side return of %s in %s", new Object[]{func2, expr}));
                                        }
                                    }
                                }
                                matcher = Pattern.compile(String.format(Locale.US, "^(%s)\\(([a-zA-Z0-9_$,]*)\\)$", new Object[]{WebPlayerView.exprName})).matcher(expr);
                                if (matcher.find()) {
                                    extractFunction(matcher.group(1));
                                }
                                throw new Exception(String.format("Unsupported JS expression %s", new Object[]{expr}));
                            }
                        }
                    }
                }
            }
        }

        private void interpretStatement(String stmt, HashMap<String, String> localVars, boolean[] abort, int allowRecursion) throws Exception {
            if (allowRecursion < 0) {
                throw new Exception("recursion limit reached");
            }
            String expr;
            abort[0] = false;
            stmt = stmt.trim();
            Matcher matcher = WebPlayerView.stmtVarPattern.matcher(stmt);
            if (matcher.find()) {
                expr = stmt.substring(matcher.group(0).length());
            } else {
                matcher = WebPlayerView.stmtReturnPattern.matcher(stmt);
                if (matcher.find()) {
                    expr = stmt.substring(matcher.group(0).length());
                    abort[0] = true;
                } else {
                    expr = stmt;
                }
            }
            interpretExpression(expr, localVars, allowRecursion);
        }

        private HashMap<String, Object> extractObject(String objname) throws Exception {
            String funcName = "(?:[a-zA-Z$0-9]+|\"[a-zA-Z$0-9]+\"|'[a-zA-Z$0-9]+')";
            HashMap<String, Object> obj = new HashMap();
            Matcher matcher = Pattern.compile(String.format(Locale.US, "(?:var\\s+)?%s\\s*=\\s*\\{\\s*((%s\\s*:\\s*function\\(.*?\\)\\s*\\{.*?\\}(?:,\\s*)?)*)\\}\\s*;", new Object[]{Pattern.quote(objname), funcName})).matcher(this.jsCode);
            String fields = null;
            while (matcher.find()) {
                String code = matcher.group();
                fields = matcher.group(2);
                if (!TextUtils.isEmpty(fields)) {
                    if (!this.codeLines.contains(code)) {
                        this.codeLines.add(matcher.group());
                    }
                    matcher = Pattern.compile(String.format("(%s)\\s*:\\s*function\\(([a-z,]+)\\)\\{([^}]+)\\}", new Object[]{funcName})).matcher(fields);
                    while (matcher.find()) {
                        buildFunction(matcher.group(2).split(","), matcher.group(3));
                    }
                    return obj;
                }
            }
            matcher = Pattern.compile(String.format("(%s)\\s*:\\s*function\\(([a-z,]+)\\)\\{([^}]+)\\}", new Object[]{funcName})).matcher(fields);
            while (matcher.find()) {
                buildFunction(matcher.group(2).split(","), matcher.group(3));
            }
            return obj;
        }

        private void buildFunction(String[] argNames, String funcCode) throws Exception {
            HashMap<String, String> localVars = new HashMap();
            for (Object put : argNames) {
                localVars.put(put, "");
            }
            String[] stmts = funcCode.split(";");
            boolean[] abort = new boolean[1];
            int a = 0;
            while (a < stmts.length) {
                interpretStatement(stmts[a], localVars, abort, 100);
                if (!abort[0]) {
                    a++;
                } else {
                    return;
                }
            }
        }

        private String extractFunction(String funcName) throws Exception {
            try {
                String quote = Pattern.quote(funcName);
                Matcher matcher = Pattern.compile(String.format(Locale.US, "(?x)(?:function\\s+%s|[{;,]\\s*%s\\s*=\\s*function|var\\s+%s\\s*=\\s*function)\\s*\\(([^)]*)\\)\\s*\\{([^}]+)\\}", new Object[]{quote, quote, quote})).matcher(this.jsCode);
                if (matcher.find()) {
                    String group = matcher.group();
                    if (!this.codeLines.contains(group)) {
                        this.codeLines.add(group + ";");
                    }
                    buildFunction(matcher.group(1).split(","), matcher.group(2));
                }
            } catch (Exception e) {
                this.codeLines.clear();
                FileLog.e(e);
            }
            return TextUtils.join("", this.codeLines);
        }
    }

    private class TwitchClipVideoTask extends AsyncTask<Void, Void, String> {
        private boolean canRetry = true;
        private String currentUrl;
        private String[] results = new String[2];
        private String videoId;

        public TwitchClipVideoTask(String url, String vid) {
            this.videoId = vid;
            this.currentUrl = url;
        }

        protected String doInBackground(Void... voids) {
            String playerCode = WebPlayerView.this.downloadUrlContent(this, this.currentUrl, null, false);
            if (isCancelled()) {
                return null;
            }
            try {
                Matcher filelist = WebPlayerView.twitchClipFilePattern.matcher(playerCode);
                if (filelist.find()) {
                    this.results[0] = new JSONObject(filelist.group(1)).getJSONArray("quality_options").getJSONObject(0).getString(Param.SOURCE);
                    this.results[1] = "other";
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (isCancelled()) {
                return null;
            }
            return this.results[0];
        }

        protected void onPostExecute(String result) {
            if (result != null) {
                WebPlayerView.this.initied = true;
                WebPlayerView.this.playVideoUrl = result;
                WebPlayerView.this.playVideoType = this.results[1];
                if (WebPlayerView.this.isAutoplay) {
                    WebPlayerView.this.preparePlayer();
                }
                WebPlayerView.this.showProgress(false, true);
                WebPlayerView.this.controlsView.show(true, true);
            } else if (!isCancelled()) {
                WebPlayerView.this.onInitFailed();
            }
        }
    }

    private class TwitchStreamVideoTask extends AsyncTask<Void, Void, String> {
        private boolean canRetry = true;
        private String currentUrl;
        private String[] results = new String[2];
        private String videoId;

        public TwitchStreamVideoTask(String url, String vid) {
            this.videoId = vid;
            this.currentUrl = url;
        }

        protected String doInBackground(Void... voids) {
            HashMap<String, String> headers = new HashMap();
            headers.put("Client-ID", "jzkbprff40iqj646a697cyrvl0zt2m6");
            int idx = this.videoId.indexOf(38);
            if (idx > 0) {
                this.videoId = this.videoId.substring(0, idx);
            }
            String streamCode = WebPlayerView.this.downloadUrlContent(this, String.format(Locale.US, "https://api.twitch.tv/kraken/streams/%s?stream_type=all", new Object[]{this.videoId}), headers, false);
            if (isCancelled()) {
                return null;
            }
            try {
                JSONObject stream = new JSONObject(streamCode).getJSONObject("stream");
                JSONObject accessToken = new JSONObject(WebPlayerView.this.downloadUrlContent(this, String.format(Locale.US, "https://api.twitch.tv/api/channels/%s/access_token", new Object[]{this.videoId}), headers, false));
                String sig = URLEncoder.encode(accessToken.getString("sig"), "UTF-8");
                String token = URLEncoder.encode(accessToken.getString("token"), "UTF-8");
                URLEncoder.encode("https://youtube.googleapis.com/v/" + this.videoId, "UTF-8");
                String params = "allow_source=true&allow_audio_only=true&allow_spectre=true&player=twitchweb&segment_preference=4&p=" + ((int) (Math.random() * 1.0E7d)) + "&" + "sig=" + sig + "&" + "token=" + token;
                this.results[0] = String.format(Locale.US, "https://usher.ttvnw.net/api/channel/hls/%s.m3u8?%s", new Object[]{this.videoId, params});
                this.results[1] = "hls";
            } catch (Exception e) {
                FileLog.e(e);
            }
            return isCancelled() ? null : this.results[0];
        }

        protected void onPostExecute(String result) {
            if (result != null) {
                WebPlayerView.this.initied = true;
                WebPlayerView.this.playVideoUrl = result;
                WebPlayerView.this.playVideoType = this.results[1];
                if (WebPlayerView.this.isAutoplay) {
                    WebPlayerView.this.preparePlayer();
                }
                WebPlayerView.this.showProgress(false, true);
                WebPlayerView.this.controlsView.show(true, true);
            } else if (!isCancelled()) {
                WebPlayerView.this.onInitFailed();
            }
        }
    }

    private class VimeoVideoTask extends AsyncTask<Void, Void, String> {
        private boolean canRetry = true;
        private String[] results = new String[2];
        private String videoId;

        public VimeoVideoTask(String vid) {
            this.videoId = vid;
        }

        protected String doInBackground(Void... voids) {
            String playerCode = WebPlayerView.this.downloadUrlContent(this, String.format(Locale.US, "https://player.vimeo.com/video/%s/config", new Object[]{this.videoId}));
            if (isCancelled()) {
                return null;
            }
            try {
                JSONObject files = new JSONObject(playerCode).getJSONObject("request").getJSONObject("files");
                if (files.has("hls")) {
                    JSONObject hls = files.getJSONObject("hls");
                    try {
                        this.results[0] = hls.getString("url");
                    } catch (Exception e) {
                        this.results[0] = hls.getJSONObject("cdns").getJSONObject(hls.getString("default_cdn")).getString("url");
                    }
                    this.results[1] = "hls";
                    if (isCancelled()) {
                        return this.results[0];
                    }
                    return null;
                }
                if (files.has("progressive")) {
                    this.results[1] = "other";
                    this.results[0] = files.getJSONArray("progressive").getJSONObject(0).getString("url");
                }
                if (isCancelled()) {
                    return this.results[0];
                }
                return null;
            } catch (Exception e2) {
                FileLog.e(e2);
            }
        }

        protected void onPostExecute(String result) {
            if (result != null) {
                WebPlayerView.this.initied = true;
                WebPlayerView.this.playVideoUrl = result;
                WebPlayerView.this.playVideoType = this.results[1];
                if (WebPlayerView.this.isAutoplay) {
                    WebPlayerView.this.preparePlayer();
                }
                WebPlayerView.this.showProgress(false, true);
                WebPlayerView.this.controlsView.show(true, true);
            } else if (!isCancelled()) {
                WebPlayerView.this.onInitFailed();
            }
        }
    }

    private class YoutubeVideoTask extends AsyncTask<Void, Void, String[]> {
        private boolean canRetry = true;
        private String[] result = new String[2];
        private Semaphore semaphore = new Semaphore(0);
        private String sig;
        private String videoId;

        public YoutubeVideoTask(String vid) {
            this.videoId = vid;
        }

        protected String[] doInBackground(Void... voids) {
            String embedCode = WebPlayerView.this.downloadUrlContent(this, "https://www.youtube.com/embed/" + this.videoId);
            if (isCancelled()) {
                return null;
            }
            Matcher matcher;
            String params = "video_id=" + this.videoId + "&ps=default&gl=US&hl=en";
            try {
                params = params + "&eurl=" + URLEncoder.encode("https://youtube.googleapis.com/v/" + this.videoId, "UTF-8");
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (embedCode != null) {
                matcher = WebPlayerView.stsPattern.matcher(embedCode);
                if (matcher.find()) {
                    params = params + "&sts=" + embedCode.substring(matcher.start() + 6, matcher.end());
                } else {
                    params = params + "&sts=";
                }
            }
            this.result[1] = "dash";
            boolean encrypted = false;
            String[] extra = new String[]{"", "&el=info", "&el=embedded", "&el=detailpage", "&el=vevo"};
            for (String str : extra) {
                String videoInfo = WebPlayerView.this.downloadUrlContent(this, "https://www.youtube.com/get_video_info?" + params + str);
                if (isCancelled()) {
                    return null;
                }
                boolean exists = false;
                String hls = null;
                boolean isLive = false;
                if (videoInfo != null) {
                    String[] args = videoInfo.split("&");
                    for (int a = 0; a < args.length; a++) {
                        String[] args2;
                        if (args[a].startsWith("dashmpd")) {
                            exists = true;
                            args2 = args[a].split("=");
                            if (args2.length == 2) {
                                try {
                                    this.result[0] = URLDecoder.decode(args2[1], "UTF-8");
                                } catch (Exception e2) {
                                    FileLog.e(e2);
                                }
                            }
                        } else if (args[a].startsWith("use_cipher_signature")) {
                            args2 = args[a].split("=");
                            if (args2.length == 2 && args2[1].toLowerCase().equals("true")) {
                                encrypted = true;
                            }
                        } else if (args[a].startsWith("hlsvp")) {
                            args2 = args[a].split("=");
                            if (args2.length == 2) {
                                try {
                                    hls = URLDecoder.decode(args2[1], "UTF-8");
                                } catch (Exception e22) {
                                    FileLog.e(e22);
                                }
                            }
                        } else if (args[a].startsWith("livestream")) {
                            args2 = args[a].split("=");
                            if (args2.length == 2 && args2[1].toLowerCase().equals(BuildConfig.VERSION_NAME)) {
                                isLive = true;
                            }
                        }
                    }
                }
                if (isLive) {
                    if (hls == null || encrypted || hls.contains("/s/")) {
                        return null;
                    }
                    this.result[0] = hls;
                    this.result[1] = "hls";
                }
                if (exists) {
                    break;
                }
            }
            if (this.result[0] != null && ((encrypted || this.result[0].contains("/s/")) && embedCode != null)) {
                encrypted = true;
                int index = this.result[0].indexOf("/s/");
                int index2 = this.result[0].indexOf(47, index + 10);
                if (index != -1) {
                    if (index2 == -1) {
                        index2 = this.result[0].length();
                    }
                    this.sig = this.result[0].substring(index, index2);
                    String jsUrl = null;
                    matcher = WebPlayerView.jsPattern.matcher(embedCode);
                    if (matcher.find()) {
                        try {
                            Object value = new JSONTokener(matcher.group(1)).nextValue();
                            if (value instanceof String) {
                                jsUrl = (String) value;
                            }
                        } catch (Exception e222) {
                            FileLog.e(e222);
                        }
                    }
                    if (jsUrl != null) {
                        String playerId;
                        matcher = WebPlayerView.playerIdPattern.matcher(jsUrl);
                        if (matcher.find()) {
                            playerId = matcher.group(1) + matcher.group(2);
                        } else {
                            playerId = null;
                        }
                        String functionCode = null;
                        String functionName = null;
                        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("youtubecode", 0);
                        if (playerId != null) {
                            functionCode = preferences.getString(playerId, null);
                            functionName = preferences.getString(playerId + "n", null);
                        }
                        if (functionCode == null) {
                            if (jsUrl.startsWith("//")) {
                                jsUrl = "https:" + jsUrl;
                            } else if (jsUrl.startsWith("/")) {
                                jsUrl = "https://www.youtube.com" + jsUrl;
                            }
                            String jsCode = WebPlayerView.this.downloadUrlContent(this, jsUrl);
                            if (isCancelled()) {
                                return null;
                            }
                            if (jsCode != null) {
                                matcher = WebPlayerView.sigPattern.matcher(jsCode);
                                if (matcher.find()) {
                                    functionName = matcher.group(1);
                                } else {
                                    matcher = WebPlayerView.sigPattern2.matcher(jsCode);
                                    if (matcher.find()) {
                                        functionName = matcher.group(1);
                                    }
                                }
                                if (functionName != null) {
                                    try {
                                        functionCode = new JSExtractor(jsCode).extractFunction(functionName);
                                        if (!(TextUtils.isEmpty(functionCode) || playerId == null)) {
                                            preferences.edit().putString(playerId, functionCode).putString(playerId + "n", functionName).commit();
                                        }
                                    } catch (Exception e2222) {
                                        FileLog.e(e2222);
                                    }
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(functionCode)) {
                            if (VERSION.SDK_INT >= 21) {
                                functionCode = functionCode + functionName + "('" + this.sig.substring(3) + "');";
                            } else {
                                functionCode = functionCode + "window." + WebPlayerView.this.interfaceName + ".returnResultToJava(" + functionName + "('" + this.sig.substring(3) + "'));";
                            }
                            final String functionCodeFinal = functionCode;
                            try {
                                AndroidUtilities.runOnUIThread(new Runnable() {

                                    /* renamed from: org.telegram.ui.Components.WebPlayerView$YoutubeVideoTask$1$1 */
                                    class C28191 implements ValueCallback<String> {
                                        C28191() {
                                        }

                                        public void onReceiveValue(String value) {
                                            YoutubeVideoTask.this.result[0] = YoutubeVideoTask.this.result[0].replace(YoutubeVideoTask.this.sig, "/signature/" + value.substring(1, value.length() - 1));
                                            YoutubeVideoTask.this.semaphore.release();
                                        }
                                    }

                                    public void run() {
                                        if (VERSION.SDK_INT >= 21) {
                                            WebPlayerView.this.webView.evaluateJavascript(functionCodeFinal, new C28191());
                                            return;
                                        }
                                        try {
                                            WebPlayerView.this.webView.loadUrl("data:text/html;charset=utf-8;base64," + Base64.encodeToString(("<script>" + functionCodeFinal + "</script>").getBytes("UTF-8"), 0));
                                        } catch (Exception e) {
                                            FileLog.e(e);
                                        }
                                    }
                                });
                                this.semaphore.acquire();
                                encrypted = false;
                            } catch (Exception e22222) {
                                FileLog.e(e22222);
                            }
                        }
                    }
                }
            }
            if (isCancelled() || encrypted) {
                return null;
            }
            return this.result;
        }

        private void onInterfaceResult(String value) {
            this.result[0] = this.result[0].replace(this.sig, "/signature/" + value);
            this.semaphore.release();
        }

        protected void onPostExecute(String[] result) {
            if (result != null) {
                WebPlayerView.this.initied = true;
                WebPlayerView.this.playVideoUrl = result[0];
                WebPlayerView.this.playVideoType = result[1];
                if (WebPlayerView.this.playVideoType.equals("hls")) {
                    WebPlayerView.this.isStream = true;
                }
                if (WebPlayerView.this.isAutoplay) {
                    WebPlayerView.this.preparePlayer();
                }
                WebPlayerView.this.showProgress(false, true);
                WebPlayerView.this.controlsView.show(true, true);
            } else if (!isCancelled()) {
                WebPlayerView.this.onInitFailed();
            }
        }
    }

    private abstract class function {
        public abstract Object run(Object[] objArr);

        private function() {
        }
    }

    protected String downloadUrlContent(AsyncTask parentTask, String url) {
        return downloadUrlContent(parentTask, url, null, true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected java.lang.String downloadUrlContent(android.os.AsyncTask r25, java.lang.String r26, java.util.HashMap<java.lang.String, java.lang.String> r27, boolean r28) {
        /*
        r24 = this;
        r4 = 1;
        r13 = 0;
        r8 = 0;
        r18 = 0;
        r12 = 0;
        r9 = new java.net.URL;	 Catch:{ Throwable -> 0x007e }
        r0 = r26;
        r9.<init>(r0);	 Catch:{ Throwable -> 0x007e }
        r12 = r9.openConnection();	 Catch:{ Throwable -> 0x007e }
        r21 = "User-Agent";
        r22 = "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)";
        r0 = r21;
        r1 = r22;
        r12.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x007e }
        if (r28 == 0) goto L_0x002d;
    L_0x0020:
        r21 = "Accept-Encoding";
        r22 = "gzip, deflate";
        r0 = r21;
        r1 = r22;
        r12.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x007e }
    L_0x002d:
        r21 = "Accept-Language";
        r22 = "en-us,en;q=0.5";
        r0 = r21;
        r1 = r22;
        r12.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x007e }
        r21 = "Accept";
        r22 = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
        r0 = r21;
        r1 = r22;
        r12.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x007e }
        r21 = "Accept-Charset";
        r22 = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
        r0 = r21;
        r1 = r22;
        r12.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x007e }
        if (r27 == 0) goto L_0x00d0;
    L_0x0056:
        r21 = r27.entrySet();	 Catch:{ Throwable -> 0x007e }
        r23 = r21.iterator();	 Catch:{ Throwable -> 0x007e }
    L_0x005e:
        r21 = r23.hasNext();	 Catch:{ Throwable -> 0x007e }
        if (r21 == 0) goto L_0x00d0;
    L_0x0064:
        r11 = r23.next();	 Catch:{ Throwable -> 0x007e }
        r11 = (java.util.Map.Entry) r11;	 Catch:{ Throwable -> 0x007e }
        r21 = r11.getKey();	 Catch:{ Throwable -> 0x007e }
        r21 = (java.lang.String) r21;	 Catch:{ Throwable -> 0x007e }
        r22 = r11.getValue();	 Catch:{ Throwable -> 0x007e }
        r22 = (java.lang.String) r22;	 Catch:{ Throwable -> 0x007e }
        r0 = r21;
        r1 = r22;
        r12.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x007e }
        goto L_0x005e;
    L_0x007e:
        r10 = move-exception;
        r0 = r10 instanceof java.net.SocketTimeoutException;
        r21 = r0;
        if (r21 == 0) goto L_0x01b6;
    L_0x0085:
        r21 = org.telegram.tgnet.ConnectionsManager.isNetworkOnline();
        if (r21 == 0) goto L_0x008c;
    L_0x008b:
        r4 = 0;
    L_0x008c:
        org.telegram.messenger.FileLog.e(r10);
    L_0x008f:
        if (r4 == 0) goto L_0x00c9;
    L_0x0091:
        if (r12 == 0) goto L_0x00b1;
    L_0x0093:
        r0 = r12 instanceof java.net.HttpURLConnection;	 Catch:{ Exception -> 0x01e4 }
        r21 = r0;
        if (r21 == 0) goto L_0x00b1;
    L_0x0099:
        r12 = (java.net.HttpURLConnection) r12;	 Catch:{ Exception -> 0x01e4 }
        r5 = r12.getResponseCode();	 Catch:{ Exception -> 0x01e4 }
        r21 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0 = r21;
        if (r5 == r0) goto L_0x00b1;
    L_0x00a5:
        r21 = 202; // 0xca float:2.83E-43 double:1.0E-321;
        r0 = r21;
        if (r5 == r0) goto L_0x00b1;
    L_0x00ab:
        r21 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        r0 = r21;
        if (r5 == r0) goto L_0x00b1;
    L_0x00b1:
        if (r13 == 0) goto L_0x00c4;
    L_0x00b3:
        r21 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r0 = r21;
        r7 = new byte[r0];	 Catch:{ Throwable -> 0x022d }
        r19 = r18;
    L_0x00bc:
        r21 = r25.isCancelled();	 Catch:{ Throwable -> 0x023d }
        if (r21 == 0) goto L_0x01ea;
    L_0x00c2:
        r18 = r19;
    L_0x00c4:
        if (r13 == 0) goto L_0x00c9;
    L_0x00c6:
        r13.close();	 Catch:{ Throwable -> 0x0233 }
    L_0x00c9:
        if (r8 == 0) goto L_0x0239;
    L_0x00cb:
        r21 = r18.toString();
    L_0x00cf:
        return r21;
    L_0x00d0:
        r21 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = r21;
        r12.setConnectTimeout(r0);	 Catch:{ Throwable -> 0x007e }
        r21 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = r21;
        r12.setReadTimeout(r0);	 Catch:{ Throwable -> 0x007e }
        r0 = r12 instanceof java.net.HttpURLConnection;	 Catch:{ Throwable -> 0x007e }
        r21 = r0;
        if (r21 == 0) goto L_0x019d;
    L_0x00e4:
        r0 = r12;
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Throwable -> 0x007e }
        r15 = r0;
        r21 = 1;
        r0 = r21;
        r15.setInstanceFollowRedirects(r0);	 Catch:{ Throwable -> 0x007e }
        r20 = r15.getResponseCode();	 Catch:{ Throwable -> 0x007e }
        r21 = 302; // 0x12e float:4.23E-43 double:1.49E-321;
        r0 = r20;
        r1 = r21;
        if (r0 == r1) goto L_0x010b;
    L_0x00fb:
        r21 = 301; // 0x12d float:4.22E-43 double:1.487E-321;
        r0 = r20;
        r1 = r21;
        if (r0 == r1) goto L_0x010b;
    L_0x0103:
        r21 = 303; // 0x12f float:4.25E-43 double:1.497E-321;
        r0 = r20;
        r1 = r21;
        if (r0 != r1) goto L_0x019d;
    L_0x010b:
        r21 = "Location";
        r0 = r21;
        r16 = r15.getHeaderField(r0);	 Catch:{ Throwable -> 0x007e }
        r21 = "Set-Cookie";
        r0 = r21;
        r6 = r15.getHeaderField(r0);	 Catch:{ Throwable -> 0x007e }
        r9 = new java.net.URL;	 Catch:{ Throwable -> 0x007e }
        r0 = r16;
        r9.<init>(r0);	 Catch:{ Throwable -> 0x007e }
        r12 = r9.openConnection();	 Catch:{ Throwable -> 0x007e }
        r21 = "Cookie";
        r0 = r21;
        r12.setRequestProperty(r0, r6);	 Catch:{ Throwable -> 0x007e }
        r21 = "User-Agent";
        r22 = "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)";
        r0 = r21;
        r1 = r22;
        r12.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x007e }
        if (r28 == 0) goto L_0x014c;
    L_0x013f:
        r21 = "Accept-Encoding";
        r22 = "gzip, deflate";
        r0 = r21;
        r1 = r22;
        r12.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x007e }
    L_0x014c:
        r21 = "Accept-Language";
        r22 = "en-us,en;q=0.5";
        r0 = r21;
        r1 = r22;
        r12.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x007e }
        r21 = "Accept";
        r22 = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
        r0 = r21;
        r1 = r22;
        r12.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x007e }
        r21 = "Accept-Charset";
        r22 = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
        r0 = r21;
        r1 = r22;
        r12.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x007e }
        if (r27 == 0) goto L_0x019d;
    L_0x0175:
        r21 = r27.entrySet();	 Catch:{ Throwable -> 0x007e }
        r23 = r21.iterator();	 Catch:{ Throwable -> 0x007e }
    L_0x017d:
        r21 = r23.hasNext();	 Catch:{ Throwable -> 0x007e }
        if (r21 == 0) goto L_0x019d;
    L_0x0183:
        r11 = r23.next();	 Catch:{ Throwable -> 0x007e }
        r11 = (java.util.Map.Entry) r11;	 Catch:{ Throwable -> 0x007e }
        r21 = r11.getKey();	 Catch:{ Throwable -> 0x007e }
        r21 = (java.lang.String) r21;	 Catch:{ Throwable -> 0x007e }
        r22 = r11.getValue();	 Catch:{ Throwable -> 0x007e }
        r22 = (java.lang.String) r22;	 Catch:{ Throwable -> 0x007e }
        r0 = r21;
        r1 = r22;
        r12.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x007e }
        goto L_0x017d;
    L_0x019d:
        r12.connect();	 Catch:{ Throwable -> 0x007e }
        if (r28 == 0) goto L_0x01b0;
    L_0x01a2:
        r14 = new java.util.zip.GZIPInputStream;	 Catch:{ Throwable -> 0x007e }
        r21 = r12.getInputStream();	 Catch:{ Throwable -> 0x007e }
        r0 = r21;
        r14.<init>(r0);	 Catch:{ Throwable -> 0x007e }
        r13 = r14;
        goto L_0x008f;
    L_0x01b0:
        r13 = r12.getInputStream();	 Catch:{ Throwable -> 0x007e }
        goto L_0x008f;
    L_0x01b6:
        r0 = r10 instanceof java.net.UnknownHostException;
        r21 = r0;
        if (r21 == 0) goto L_0x01bf;
    L_0x01bc:
        r4 = 0;
        goto L_0x008c;
    L_0x01bf:
        r0 = r10 instanceof java.net.SocketException;
        r21 = r0;
        if (r21 == 0) goto L_0x01db;
    L_0x01c5:
        r21 = r10.getMessage();
        if (r21 == 0) goto L_0x008c;
    L_0x01cb:
        r21 = r10.getMessage();
        r22 = "ECONNRESET";
        r21 = r21.contains(r22);
        if (r21 == 0) goto L_0x008c;
    L_0x01d8:
        r4 = 0;
        goto L_0x008c;
    L_0x01db:
        r0 = r10 instanceof java.io.FileNotFoundException;
        r21 = r0;
        if (r21 == 0) goto L_0x008c;
    L_0x01e1:
        r4 = 0;
        goto L_0x008c;
    L_0x01e4:
        r10 = move-exception;
        org.telegram.messenger.FileLog.e(r10);
        goto L_0x00b1;
    L_0x01ea:
        r17 = r13.read(r7);	 Catch:{ Exception -> 0x0225 }
        if (r17 <= 0) goto L_0x0214;
    L_0x01f0:
        if (r19 != 0) goto L_0x0243;
    L_0x01f2:
        r18 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0225 }
        r18.<init>();	 Catch:{ Exception -> 0x0225 }
    L_0x01f7:
        r21 = new java.lang.String;	 Catch:{ Exception -> 0x0241 }
        r22 = 0;
        r23 = "UTF-8";
        r0 = r21;
        r1 = r22;
        r2 = r17;
        r3 = r23;
        r0.<init>(r7, r1, r2, r3);	 Catch:{ Exception -> 0x0241 }
        r0 = r18;
        r1 = r21;
        r0.append(r1);	 Catch:{ Exception -> 0x0241 }
        r19 = r18;
        goto L_0x00bc;
    L_0x0214:
        r21 = -1;
        r0 = r17;
        r1 = r21;
        if (r0 != r1) goto L_0x0221;
    L_0x021c:
        r8 = 1;
        r18 = r19;
        goto L_0x00c4;
    L_0x0221:
        r18 = r19;
        goto L_0x00c4;
    L_0x0225:
        r10 = move-exception;
        r18 = r19;
    L_0x0228:
        org.telegram.messenger.FileLog.e(r10);	 Catch:{ Throwable -> 0x022d }
        goto L_0x00c4;
    L_0x022d:
        r10 = move-exception;
    L_0x022e:
        org.telegram.messenger.FileLog.e(r10);
        goto L_0x00c4;
    L_0x0233:
        r10 = move-exception;
        org.telegram.messenger.FileLog.e(r10);
        goto L_0x00c9;
    L_0x0239:
        r21 = 0;
        goto L_0x00cf;
    L_0x023d:
        r10 = move-exception;
        r18 = r19;
        goto L_0x022e;
    L_0x0241:
        r10 = move-exception;
        goto L_0x0228;
    L_0x0243:
        r18 = r19;
        goto L_0x01f7;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.WebPlayerView.downloadUrlContent(android.os.AsyncTask, java.lang.String, java.util.HashMap, boolean):java.lang.String");
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    public WebPlayerView(Context context, boolean allowInline, boolean allowShare, WebPlayerViewDelegate webPlayerViewDelegate) {
        boolean z;
        super(context);
        int i = lastContainerId;
        lastContainerId = i + 1;
        this.fragment_container_id = i;
        if (VERSION.SDK_INT >= 21) {
            z = true;
        } else {
            z = false;
        }
        this.allowInlineAnimation = z;
        this.backgroundPaint = new Paint();
        this.progressRunnable = new C28051();
        this.surfaceTextureListener = new C28082();
        this.switchToInlineRunnable = new C28093();
        setWillNotDraw(false);
        this.delegate = webPlayerViewDelegate;
        this.backgroundPaint.setColor(-16777216);
        this.aspectRatioFrameLayout = new AspectRatioFrameLayout(context) {
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                if (WebPlayerView.this.textureViewContainer != null) {
                    LayoutParams layoutParams = WebPlayerView.this.textureView.getLayoutParams();
                    layoutParams.width = getMeasuredWidth();
                    layoutParams.height = getMeasuredHeight();
                    if (WebPlayerView.this.textureImageView != null) {
                        layoutParams = WebPlayerView.this.textureImageView.getLayoutParams();
                        layoutParams.width = getMeasuredWidth();
                        layoutParams.height = getMeasuredHeight();
                    }
                }
            }
        };
        addView(this.aspectRatioFrameLayout, LayoutHelper.createFrame(-1, -1, 17));
        this.interfaceName = "JavaScriptInterface";
        this.webView = new WebView(context);
        this.webView.addJavascriptInterface(new WebPlayerView$JavaScriptInterface(this, new C28115()), this.interfaceName);
        WebSettings webSettings = this.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        this.textureViewContainer = this.delegate.getTextureViewContainer();
        this.textureView = new TextureView(context);
        this.textureView.setPivotX(0.0f);
        this.textureView.setPivotY(0.0f);
        if (this.textureViewContainer != null) {
            this.textureViewContainer.addView(this.textureView);
        } else {
            this.aspectRatioFrameLayout.addView(this.textureView, LayoutHelper.createFrame(-1, -1, 17));
        }
        if (this.allowInlineAnimation && this.textureViewContainer != null) {
            this.textureImageView = new ImageView(context);
            this.textureImageView.setBackgroundColor(SupportMenu.CATEGORY_MASK);
            this.textureImageView.setPivotX(0.0f);
            this.textureImageView.setPivotY(0.0f);
            this.textureImageView.setVisibility(4);
            this.textureViewContainer.addView(this.textureImageView);
        }
        this.videoPlayer = new VideoPlayer();
        this.videoPlayer.setDelegate(this);
        this.videoPlayer.setTextureView(this.textureView);
        this.controlsView = new ControlsView(context);
        if (this.textureViewContainer != null) {
            this.textureViewContainer.addView(this.controlsView);
        } else {
            addView(this.controlsView, LayoutHelper.createFrame(-1, -1.0f));
        }
        this.progressView = new RadialProgressView(context);
        this.progressView.setProgressColor(-1);
        addView(this.progressView, LayoutHelper.createFrame(48, 48, 17));
        this.fullscreenButton = new ImageView(context);
        this.fullscreenButton.setScaleType(ScaleType.CENTER);
        this.controlsView.addView(this.fullscreenButton, LayoutHelper.createFrame(56, 56.0f, 85, 0.0f, 0.0f, 0.0f, 5.0f));
        this.fullscreenButton.setOnClickListener(new C28126());
        this.playButton = new ImageView(context);
        this.playButton.setScaleType(ScaleType.CENTER);
        this.controlsView.addView(this.playButton, LayoutHelper.createFrame(48, 48, 17));
        this.playButton.setOnClickListener(new C28137());
        if (allowInline) {
            this.inlineButton = new ImageView(context);
            this.inlineButton.setScaleType(ScaleType.CENTER);
            this.controlsView.addView(this.inlineButton, LayoutHelper.createFrame(56, 48, 53));
            this.inlineButton.setOnClickListener(new C28148());
        }
        if (allowShare) {
            this.shareButton = new ImageView(context);
            this.shareButton.setScaleType(ScaleType.CENTER);
            this.shareButton.setImageResource(R.drawable.ic_share_video);
            this.controlsView.addView(this.shareButton, LayoutHelper.createFrame(56, 48, 53));
            this.shareButton.setOnClickListener(new C28159());
        }
        updatePlayButton();
        updateFullscreenButton();
        updateInlineButton();
        updateShareButton();
    }

    private void onInitFailed() {
        if (this.controlsView.getParent() != this) {
            this.controlsView.setVisibility(8);
        }
        this.delegate.onInitFailed();
    }

    public void updateTextureImageView() {
        if (this.textureImageView != null) {
            try {
                this.currentBitmap = Bitmaps.createBitmap(this.textureView.getWidth(), this.textureView.getHeight(), Config.ARGB_8888);
                this.changedTextureView.getBitmap(this.currentBitmap);
            } catch (Throwable e) {
                if (this.currentBitmap != null) {
                    this.currentBitmap.recycle();
                    this.currentBitmap = null;
                }
                FileLog.e(e);
            }
            if (this.currentBitmap != null) {
                this.textureImageView.setVisibility(0);
                this.textureImageView.setImageBitmap(this.currentBitmap);
                return;
            }
            this.textureImageView.setImageDrawable(null);
        }
    }

    public String getYoutubeId() {
        return this.currentYoutubeId;
    }

    public void onStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState != 2) {
            if (this.videoPlayer.getDuration() != C0907C.TIME_UNSET) {
                this.controlsView.setDuration((int) (this.videoPlayer.getDuration() / 1000));
            } else {
                this.controlsView.setDuration(0);
            }
        }
        if (playbackState == 4 || playbackState == 1 || !this.videoPlayer.isPlaying()) {
            this.delegate.onPlayStateChanged(this, false);
        } else {
            this.delegate.onPlayStateChanged(this, true);
        }
        if (this.videoPlayer.isPlaying() && playbackState != 4) {
            updatePlayButton();
        } else if (playbackState == 4) {
            this.isCompleted = true;
            this.videoPlayer.pause();
            this.videoPlayer.seekTo(0);
            updatePlayButton();
            this.controlsView.show(true, true);
        }
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0.0f, 0.0f, (float) getMeasuredWidth(), (float) (getMeasuredHeight() - AndroidUtilities.dp(10.0f)), this.backgroundPaint);
    }

    public void onError(Exception e) {
        FileLog.e(e);
    }

    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        if (this.aspectRatioFrameLayout != null) {
            if (unappliedRotationDegrees == 90 || unappliedRotationDegrees == 270) {
                int temp = width;
                width = height;
                height = temp;
            }
            float ratio = height == 0 ? 1.0f : (((float) width) * pixelWidthHeightRatio) / ((float) height);
            this.aspectRatioFrameLayout.setAspectRatio(ratio, unappliedRotationDegrees);
            if (this.inFullscreen) {
                this.delegate.onVideoSizeChanged(ratio, unappliedRotationDegrees);
            }
        }
    }

    public void onRenderedFirstFrame() {
        this.firstFrameRendered = true;
        this.lastUpdateTime = System.currentTimeMillis();
        this.controlsView.invalidate();
    }

    public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
        if (this.changingTextureView) {
            this.changingTextureView = false;
            if (this.inFullscreen || this.isInline) {
                if (this.isInline) {
                    this.waitingForFirstTextureUpload = 1;
                }
                this.changedTextureView.setSurfaceTexture(surfaceTexture);
                this.changedTextureView.setSurfaceTextureListener(this.surfaceTextureListener);
                this.changedTextureView.setVisibility(0);
                return true;
            }
        }
        return false;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        if (this.waitingForFirstTextureUpload == 2) {
            if (this.textureImageView != null) {
                this.textureImageView.setVisibility(4);
                this.textureImageView.setImageDrawable(null);
                if (this.currentBitmap != null) {
                    this.currentBitmap.recycle();
                    this.currentBitmap = null;
                }
            }
            this.switchingInlineMode = false;
            this.delegate.onSwitchInlineMode(this.controlsView, false, this.aspectRatioFrameLayout.getAspectRatio(), this.aspectRatioFrameLayout.getVideoRotation(), this.allowInlineAnimation);
            this.waitingForFirstTextureUpload = 0;
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int x = ((r - l) - this.aspectRatioFrameLayout.getMeasuredWidth()) / 2;
        int y = (((b - t) - AndroidUtilities.dp(10.0f)) - this.aspectRatioFrameLayout.getMeasuredHeight()) / 2;
        this.aspectRatioFrameLayout.layout(x, y, this.aspectRatioFrameLayout.getMeasuredWidth() + x, this.aspectRatioFrameLayout.getMeasuredHeight() + y);
        if (this.controlsView.getParent() == this) {
            this.controlsView.layout(0, 0, this.controlsView.getMeasuredWidth(), this.controlsView.getMeasuredHeight());
        }
        x = ((r - l) - this.progressView.getMeasuredWidth()) / 2;
        y = ((b - t) - this.progressView.getMeasuredHeight()) / 2;
        this.progressView.layout(x, y, this.progressView.getMeasuredWidth() + x, this.progressView.getMeasuredHeight() + y);
        this.controlsView.imageReceiver.setImageCoords(0, 0, getMeasuredWidth(), getMeasuredHeight() - AndroidUtilities.dp(10.0f));
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        this.aspectRatioFrameLayout.measure(MeasureSpec.makeMeasureSpec(width, 1073741824), MeasureSpec.makeMeasureSpec(height - AndroidUtilities.dp(10.0f), 1073741824));
        if (this.controlsView.getParent() == this) {
            this.controlsView.measure(MeasureSpec.makeMeasureSpec(width, 1073741824), MeasureSpec.makeMeasureSpec(height, 1073741824));
        }
        this.progressView.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(44.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(44.0f), 1073741824));
        setMeasuredDimension(width, height);
    }

    private void updatePlayButton() {
        this.controlsView.checkNeedHide();
        AndroidUtilities.cancelRunOnUIThread(this.progressRunnable);
        if (this.videoPlayer.isPlaying()) {
            this.playButton.setImageResource(this.isInline ? R.drawable.ic_pauseinline : R.drawable.ic_pause);
            AndroidUtilities.runOnUIThread(this.progressRunnable, 500);
            checkAudioFocus();
        } else if (this.isCompleted) {
            this.playButton.setImageResource(this.isInline ? R.drawable.ic_againinline : R.drawable.ic_again);
        } else {
            this.playButton.setImageResource(this.isInline ? R.drawable.ic_playinline : R.drawable.ic_play);
        }
    }

    private void checkAudioFocus() {
        if (!this.hasAudioFocus) {
            AudioManager audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
            this.hasAudioFocus = true;
            if (audioManager.requestAudioFocus(this, 3, 1) == 1) {
                this.audioFocus = 2;
            }
        }
    }

    public void onAudioFocusChange(int focusChange) {
        if (focusChange == -1) {
            if (this.videoPlayer.isPlaying()) {
                this.videoPlayer.pause();
                updatePlayButton();
            }
            this.hasAudioFocus = false;
            this.audioFocus = 0;
        } else if (focusChange == 1) {
            this.audioFocus = 2;
            if (this.resumeAudioOnFocusGain) {
                this.resumeAudioOnFocusGain = false;
                this.videoPlayer.play();
            }
        } else if (focusChange == -3) {
            this.audioFocus = 1;
        } else if (focusChange == -2) {
            this.audioFocus = 0;
            if (this.videoPlayer.isPlaying()) {
                this.resumeAudioOnFocusGain = true;
                this.videoPlayer.pause();
                updatePlayButton();
            }
        }
    }

    private void updateFullscreenButton() {
        if (!this.videoPlayer.isPlayerPrepared() || this.isInline) {
            this.fullscreenButton.setVisibility(8);
            return;
        }
        this.fullscreenButton.setVisibility(0);
        if (this.inFullscreen) {
            this.fullscreenButton.setImageResource(R.drawable.ic_outfullscreen);
            this.fullscreenButton.setLayoutParams(LayoutHelper.createFrame(56, 56.0f, 85, 0.0f, 0.0f, 0.0f, 1.0f));
            return;
        }
        this.fullscreenButton.setImageResource(R.drawable.ic_gofullscreen);
        this.fullscreenButton.setLayoutParams(LayoutHelper.createFrame(56, 56.0f, 85, 0.0f, 0.0f, 0.0f, 5.0f));
    }

    private void updateShareButton() {
        if (this.shareButton != null) {
            ImageView imageView = this.shareButton;
            int i = (this.isInline || !this.videoPlayer.isPlayerPrepared()) ? 8 : 0;
            imageView.setVisibility(i);
        }
    }

    private View getControlView() {
        return this.controlsView;
    }

    private View getProgressView() {
        return this.progressView;
    }

    private void updateInlineButton() {
        if (this.inlineButton != null) {
            this.inlineButton.setImageResource(this.isInline ? R.drawable.ic_goinline : R.drawable.ic_outinline);
            this.inlineButton.setVisibility(this.videoPlayer.isPlayerPrepared() ? 0 : 8);
            if (this.isInline) {
                this.inlineButton.setLayoutParams(LayoutHelper.createFrame(40, 40, 53));
            } else {
                this.inlineButton.setLayoutParams(LayoutHelper.createFrame(56, 50, 53));
            }
        }
    }

    private void preparePlayer() {
        if (this.playVideoUrl != null) {
            if (this.playVideoUrl == null || this.playAudioUrl == null) {
                this.videoPlayer.preparePlayer(Uri.parse(this.playVideoUrl), this.playVideoType);
            } else {
                this.videoPlayer.preparePlayerLoop(Uri.parse(this.playVideoUrl), this.playVideoType, Uri.parse(this.playAudioUrl), this.playAudioType);
            }
            this.videoPlayer.setPlayWhenReady(this.isAutoplay);
            this.isLoading = false;
            if (this.videoPlayer.getDuration() != C0907C.TIME_UNSET) {
                this.controlsView.setDuration((int) (this.videoPlayer.getDuration() / 1000));
            } else {
                this.controlsView.setDuration(0);
            }
            updateFullscreenButton();
            updateShareButton();
            updateInlineButton();
            this.controlsView.invalidate();
            if (this.seekToTime != -1) {
                this.videoPlayer.seekTo((long) (this.seekToTime * 1000));
            }
        }
    }

    public void pause() {
        this.videoPlayer.pause();
        updatePlayButton();
        this.controlsView.show(true, true);
    }

    private void updateFullscreenState(boolean byButton) {
        if (this.textureView != null) {
            updateFullscreenButton();
            ViewGroup viewGroup;
            ViewGroup parent;
            if (this.textureViewContainer == null) {
                this.changingTextureView = true;
                if (!this.inFullscreen) {
                    if (this.textureViewContainer != null) {
                        this.textureViewContainer.addView(this.textureView);
                    } else {
                        this.aspectRatioFrameLayout.addView(this.textureView);
                    }
                }
                if (this.inFullscreen) {
                    viewGroup = (ViewGroup) this.controlsView.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(this.controlsView);
                    }
                } else {
                    parent = (ViewGroup) this.controlsView.getParent();
                    if (parent != this) {
                        if (parent != null) {
                            parent.removeView(this.controlsView);
                        }
                        if (this.textureViewContainer != null) {
                            this.textureViewContainer.addView(this.controlsView);
                        } else {
                            addView(this.controlsView, 1);
                        }
                    }
                }
                this.changedTextureView = this.delegate.onSwitchToFullscreen(this.controlsView, this.inFullscreen, this.aspectRatioFrameLayout.getAspectRatio(), this.aspectRatioFrameLayout.getVideoRotation(), byButton);
                this.changedTextureView.setVisibility(4);
                if (this.inFullscreen && this.changedTextureView != null) {
                    parent = (ViewGroup) this.textureView.getParent();
                    if (parent != null) {
                        parent.removeView(this.textureView);
                    }
                }
                this.controlsView.checkNeedHide();
                return;
            }
            if (this.inFullscreen) {
                viewGroup = (ViewGroup) this.aspectRatioFrameLayout.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(this.aspectRatioFrameLayout);
                }
            } else {
                parent = (ViewGroup) this.aspectRatioFrameLayout.getParent();
                if (parent != this) {
                    if (parent != null) {
                        parent.removeView(this.aspectRatioFrameLayout);
                    }
                    addView(this.aspectRatioFrameLayout, 0);
                }
            }
            this.delegate.onSwitchToFullscreen(this.controlsView, this.inFullscreen, this.aspectRatioFrameLayout.getAspectRatio(), this.aspectRatioFrameLayout.getVideoRotation(), byButton);
        }
    }

    public void exitFullscreen() {
        if (this.inFullscreen) {
            this.inFullscreen = false;
            updateInlineButton();
            updateFullscreenState(false);
        }
    }

    public boolean isInitied() {
        return this.initied;
    }

    public boolean isInline() {
        return this.isInline || this.switchingInlineMode;
    }

    public void enterFullscreen() {
        if (!this.inFullscreen) {
            this.inFullscreen = true;
            updateInlineButton();
            updateFullscreenState(false);
        }
    }

    public boolean isInFullscreen() {
        return this.inFullscreen;
    }

    public boolean loadVideo(String url, TLRPC$Photo thumb, String originalUrl, boolean autoplay) {
        String youtubeId = null;
        String vimeoId = null;
        String coubId = null;
        String twitchClipId = null;
        String twitchStreamId = null;
        String mp4File = null;
        String aparatId = null;
        this.seekToTime = -1;
        if (url != null) {
            if (url.endsWith(".mp4")) {
                mp4File = url;
            } else {
                Matcher matcher;
                String id;
                if (originalUrl != null) {
                    try {
                        String t = Uri.parse(originalUrl).getQueryParameter("t");
                        if (t != null) {
                            if (t.contains("m")) {
                                String[] args = t.split("m");
                                this.seekToTime = (Utilities.parseInt(args[0]).intValue() * 60) + Utilities.parseInt(args[1]).intValue();
                            } else {
                                this.seekToTime = Utilities.parseInt(t).intValue();
                            }
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
                try {
                    matcher = youtubeIdRegex.matcher(url);
                    id = null;
                    if (matcher.find()) {
                        id = matcher.group(1);
                    }
                    if (id != null) {
                        youtubeId = id;
                    }
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
                if (youtubeId == null) {
                    try {
                        matcher = vimeoIdRegex.matcher(url);
                        id = null;
                        if (matcher.find()) {
                            id = matcher.group(3);
                        }
                        if (id != null) {
                            vimeoId = id;
                        }
                    } catch (Exception e22) {
                        FileLog.e(e22);
                    }
                }
                if (vimeoId == null) {
                    try {
                        matcher = aparatIdRegex.matcher(url);
                        id = null;
                        if (matcher.find()) {
                            id = matcher.group(1);
                        }
                        if (id != null) {
                            aparatId = id;
                        }
                    } catch (Exception e222) {
                        FileLog.e(e222);
                    }
                }
                if (aparatId == null) {
                    try {
                        matcher = twitchClipIdRegex.matcher(url);
                        id = null;
                        if (matcher.find()) {
                            id = matcher.group(1);
                        }
                        if (id != null) {
                            twitchClipId = id;
                        }
                    } catch (Exception e2222) {
                        FileLog.e(e2222);
                    }
                }
                if (twitchClipId == null) {
                    try {
                        matcher = twitchStreamIdRegex.matcher(url);
                        id = null;
                        if (matcher.find()) {
                            id = matcher.group(1);
                        }
                        if (id != null) {
                            twitchStreamId = id;
                        }
                    } catch (Exception e22222) {
                        FileLog.e(e22222);
                    }
                }
                if (twitchStreamId == null) {
                    try {
                        matcher = coubIdRegex.matcher(url);
                        id = null;
                        if (matcher.find()) {
                            id = matcher.group(1);
                        }
                        if (id != null) {
                            coubId = id;
                        }
                    } catch (Exception e222222) {
                        FileLog.e(e222222);
                    }
                }
            }
        }
        this.initied = false;
        this.isCompleted = false;
        this.isAutoplay = autoplay;
        this.playVideoUrl = null;
        this.playAudioUrl = null;
        destroy();
        this.firstFrameRendered = false;
        this.currentAlpha = 1.0f;
        if (this.currentTask != null) {
            this.currentTask.cancel(true);
            this.currentTask = null;
        }
        updateFullscreenButton();
        updateShareButton();
        updateInlineButton();
        updatePlayButton();
        if (thumb != null) {
            TLRPC$PhotoSize photoSize = FileLoader.getClosestPhotoSizeWithSize(thumb.sizes, 80, true);
            if (photoSize != null) {
                this.controlsView.imageReceiver.setImage(null, null, thumb != null ? photoSize.location : null, thumb != null ? "80_80_b" : null, 0, null, 1);
                this.drawImage = true;
            }
        } else {
            this.drawImage = false;
        }
        if (this.progressAnimation != null) {
            this.progressAnimation.cancel();
            this.progressAnimation = null;
        }
        this.isLoading = true;
        this.controlsView.setProgress(0);
        if (!(youtubeId == null || BuildVars.DEBUG_PRIVATE_VERSION)) {
            this.currentYoutubeId = youtubeId;
            youtubeId = null;
        }
        if (mp4File != null) {
            this.initied = true;
            this.playVideoUrl = mp4File;
            this.playVideoType = "other";
            if (this.isAutoplay) {
                preparePlayer();
            }
            showProgress(false, false);
            this.controlsView.show(true, true);
        } else {
            AsyncTask youtubeVideoTask;
            if (youtubeId != null) {
                youtubeVideoTask = new YoutubeVideoTask(youtubeId);
                youtubeVideoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                this.currentTask = youtubeVideoTask;
            } else if (vimeoId != null) {
                youtubeVideoTask = new VimeoVideoTask(vimeoId);
                youtubeVideoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                this.currentTask = youtubeVideoTask;
            } else if (coubId != null) {
                youtubeVideoTask = new CoubVideoTask(coubId);
                youtubeVideoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                this.currentTask = youtubeVideoTask;
                this.isStream = true;
            } else if (aparatId != null) {
                youtubeVideoTask = new AparatVideoTask(aparatId);
                youtubeVideoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                this.currentTask = youtubeVideoTask;
            } else if (twitchClipId != null) {
                youtubeVideoTask = new TwitchClipVideoTask(url, twitchClipId);
                youtubeVideoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                this.currentTask = youtubeVideoTask;
            } else if (twitchStreamId != null) {
                youtubeVideoTask = new TwitchStreamVideoTask(url, twitchStreamId);
                youtubeVideoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                this.currentTask = youtubeVideoTask;
                this.isStream = true;
            }
            this.controlsView.show(false, false);
            showProgress(true, false);
        }
        if (youtubeId == null && vimeoId == null && coubId == null && aparatId == null && mp4File == null && twitchClipId == null && twitchStreamId == null) {
            this.controlsView.setVisibility(8);
            return false;
        }
        this.controlsView.setVisibility(0);
        return true;
    }

    public View getAspectRatioView() {
        return this.aspectRatioFrameLayout;
    }

    public TextureView getTextureView() {
        return this.textureView;
    }

    public ImageView getTextureImageView() {
        return this.textureImageView;
    }

    public View getControlsView() {
        return this.controlsView;
    }

    public void destroy() {
        this.videoPlayer.releasePlayer();
        if (this.currentTask != null) {
            this.currentTask.cancel(true);
            this.currentTask = null;
        }
        this.webView.stopLoading();
    }

    private void showProgress(boolean show, boolean animated) {
        float f = 1.0f;
        if (animated) {
            if (this.progressAnimation != null) {
                this.progressAnimation.cancel();
            }
            this.progressAnimation = new AnimatorSet();
            AnimatorSet animatorSet = this.progressAnimation;
            Animator[] animatorArr = new Animator[1];
            RadialProgressView radialProgressView = this.progressView;
            String str = "alpha";
            float[] fArr = new float[1];
            if (!show) {
                f = 0.0f;
            }
            fArr[0] = f;
            animatorArr[0] = ObjectAnimator.ofFloat(radialProgressView, str, fArr);
            animatorSet.playTogether(animatorArr);
            this.progressAnimation.setDuration(150);
            this.progressAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    WebPlayerView.this.progressAnimation = null;
                }
            });
            this.progressAnimation.start();
            return;
        }
        RadialProgressView radialProgressView2 = this.progressView;
        if (!show) {
            f = 0.0f;
        }
        radialProgressView2.setAlpha(f);
    }
}
