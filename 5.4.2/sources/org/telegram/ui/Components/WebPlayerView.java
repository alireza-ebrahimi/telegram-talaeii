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
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.wearable.WearableStatusCodes;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
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
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.exoplayer2.ui.AspectRatioFrameLayout;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.tgnet.TLRPC.Photo;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate;

public class WebPlayerView extends ViewGroup implements OnAudioFocusChangeListener, VideoPlayerDelegate {
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
    class C46431 implements Runnable {
        C46431() {
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
    class C46462 implements SurfaceTextureListener {

        /* renamed from: org.telegram.ui.Components.WebPlayerView$2$1 */
        class C46451 implements OnPreDrawListener {

            /* renamed from: org.telegram.ui.Components.WebPlayerView$2$1$1 */
            class C46441 implements Runnable {
                C46441() {
                }

                public void run() {
                    WebPlayerView.this.delegate.onInlineSurfaceTextureReady();
                }
            }

            C46451() {
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
                AndroidUtilities.runOnUIThread(new C46441());
                WebPlayerView.this.waitingForFirstTextureUpload = 0;
                return true;
            }
        }

        C46462() {
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            if (!WebPlayerView.this.changingTextureView) {
                return true;
            }
            if (WebPlayerView.this.switchingInlineMode) {
                WebPlayerView.this.waitingForFirstTextureUpload = 2;
            }
            WebPlayerView.this.textureView.setSurfaceTexture(surfaceTexture);
            WebPlayerView.this.textureView.setVisibility(0);
            WebPlayerView.this.changingTextureView = false;
            return false;
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            if (WebPlayerView.this.waitingForFirstTextureUpload == 1) {
                WebPlayerView.this.changedTextureView.getViewTreeObserver().addOnPreDrawListener(new C46451());
                WebPlayerView.this.changedTextureView.invalidate();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.WebPlayerView$3 */
    class C46473 implements Runnable {
        C46473() {
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
                } catch (Throwable th) {
                    if (WebPlayerView.this.currentBitmap != null) {
                        WebPlayerView.this.currentBitmap.recycle();
                        WebPlayerView.this.currentBitmap = null;
                    }
                    FileLog.e(th);
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
            viewGroup = (ViewGroup) WebPlayerView.this.textureView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(WebPlayerView.this.textureView);
            }
            WebPlayerView.this.controlsView.show(false, false);
        }
    }

    public interface CallJavaResultInterface {
        void jsCallFinished(String str);
    }

    /* renamed from: org.telegram.ui.Components.WebPlayerView$5 */
    class C46495 implements CallJavaResultInterface {
        C46495() {
        }

        public void jsCallFinished(String str) {
            if (WebPlayerView.this.currentTask != null && !WebPlayerView.this.currentTask.isCancelled() && (WebPlayerView.this.currentTask instanceof YoutubeVideoTask)) {
                ((YoutubeVideoTask) WebPlayerView.this.currentTask).onInterfaceResult(str);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.WebPlayerView$6 */
    class C46506 implements OnClickListener {
        C46506() {
        }

        public void onClick(View view) {
            if (WebPlayerView.this.initied && !WebPlayerView.this.changingTextureView && !WebPlayerView.this.switchingInlineMode && WebPlayerView.this.firstFrameRendered) {
                WebPlayerView.this.inFullscreen = !WebPlayerView.this.inFullscreen;
                WebPlayerView.this.updateFullscreenState(true);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.WebPlayerView$7 */
    class C46517 implements OnClickListener {
        C46517() {
        }

        public void onClick(View view) {
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
    class C46528 implements OnClickListener {
        C46528() {
        }

        public void onClick(View view) {
            if (WebPlayerView.this.textureView != null && WebPlayerView.this.delegate.checkInlinePermissons() && !WebPlayerView.this.changingTextureView && !WebPlayerView.this.switchingInlineMode && WebPlayerView.this.firstFrameRendered) {
                WebPlayerView.this.switchingInlineMode = true;
                if (WebPlayerView.this.isInline) {
                    ViewGroup viewGroup = (ViewGroup) WebPlayerView.this.aspectRatioFrameLayout.getParent();
                    if (viewGroup != WebPlayerView.this) {
                        if (viewGroup != null) {
                            viewGroup.removeView(WebPlayerView.this.aspectRatioFrameLayout);
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
                    viewGroup = (ViewGroup) WebPlayerView.this.controlsView.getParent();
                    if (viewGroup != WebPlayerView.this) {
                        if (viewGroup != null) {
                            viewGroup.removeView(WebPlayerView.this.controlsView);
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
    class C46539 implements OnClickListener {
        C46539() {
        }

        public void onClick(View view) {
            if (WebPlayerView.this.delegate != null) {
                WebPlayerView.this.delegate.onSharePressed();
            }
        }
    }

    private class AparatVideoTask extends AsyncTask<Void, Void, String> {
        private boolean canRetry = true;
        private String[] results = new String[2];
        private String videoId;

        public AparatVideoTask(String str) {
            this.videoId = str;
        }

        protected String doInBackground(Void... voidArr) {
            CharSequence downloadUrlContent = WebPlayerView.this.downloadUrlContent(this, String.format(Locale.US, "http://www.aparat.com/video/video/embed/vt/frame/showvideo/yes/videohash/%s", new Object[]{this.videoId}));
            if (isCancelled()) {
                return null;
            }
            try {
                Matcher matcher = WebPlayerView.aparatFileListPattern.matcher(downloadUrlContent);
                if (matcher.find()) {
                    JSONArray jSONArray = new JSONArray(matcher.group(1));
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONArray jSONArray2 = jSONArray.getJSONArray(i);
                        if (jSONArray2.length() != 0) {
                            JSONObject jSONObject = jSONArray2.getJSONObject(0);
                            if (jSONObject.has("file")) {
                                this.results[0] = jSONObject.getString("file");
                                this.results[1] = "other";
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            return !isCancelled() ? this.results[0] : null;
        }

        protected void onPostExecute(String str) {
            if (str != null) {
                WebPlayerView.this.initied = true;
                WebPlayerView.this.playVideoUrl = str;
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
        private Runnable hideRunnable = new C46541();
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
        class C46541 implements Runnable {
            C46541() {
            }

            public void run() {
                ControlsView.this.show(false, true);
            }
        }

        /* renamed from: org.telegram.ui.Components.WebPlayerView$ControlsView$2 */
        class C46552 extends AnimatorListenerAdapter {
            C46552() {
            }

            public void onAnimationEnd(Animator animator) {
                ControlsView.this.currentAnimation = null;
            }
        }

        /* renamed from: org.telegram.ui.Components.WebPlayerView$ControlsView$3 */
        class C46563 extends AnimatorListenerAdapter {
            C46563() {
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

        private void checkNeedHide() {
            AndroidUtilities.cancelRunOnUIThread(this.hideRunnable);
            if (this.isVisible && WebPlayerView.this.videoPlayer.isPlaying()) {
                AndroidUtilities.runOnUIThread(this.hideRunnable, 3000);
            }
        }

        protected void onDraw(Canvas canvas) {
            int i = 6;
            int i2 = 0;
            if (WebPlayerView.this.drawImage) {
                if (WebPlayerView.this.firstFrameRendered && WebPlayerView.this.currentAlpha != BitmapDescriptorFactory.HUE_RED) {
                    long currentTimeMillis = System.currentTimeMillis();
                    long access$4900 = currentTimeMillis - WebPlayerView.this.lastUpdateTime;
                    WebPlayerView.this.lastUpdateTime = currentTimeMillis;
                    WebPlayerView.this.currentAlpha = WebPlayerView.this.currentAlpha - (((float) access$4900) / 150.0f);
                    if (WebPlayerView.this.currentAlpha < BitmapDescriptorFactory.HUE_RED) {
                        WebPlayerView.this.currentAlpha = BitmapDescriptorFactory.HUE_RED;
                    }
                    invalidate();
                }
                this.imageReceiver.setAlpha(WebPlayerView.this.currentAlpha);
                this.imageReceiver.draw(canvas);
            }
            if (WebPlayerView.this.videoPlayer.isPlayerPrepared() && !WebPlayerView.this.isStream) {
                int measuredWidth = getMeasuredWidth();
                int measuredHeight = getMeasuredHeight();
                if (!WebPlayerView.this.isInline) {
                    if (this.durationLayout != null) {
                        canvas.save();
                        canvas.translate((float) ((measuredWidth - AndroidUtilities.dp(58.0f)) - this.durationWidth), (float) (measuredHeight - AndroidUtilities.dp((float) ((WebPlayerView.this.inFullscreen ? 6 : 10) + 29))));
                        this.durationLayout.draw(canvas);
                        canvas.restore();
                    }
                    if (this.progressLayout != null) {
                        canvas.save();
                        float dp = (float) AndroidUtilities.dp(18.0f);
                        if (!WebPlayerView.this.inFullscreen) {
                            i = 10;
                        }
                        canvas.translate(dp, (float) (measuredHeight - AndroidUtilities.dp((float) (i + 29))));
                        this.progressLayout.draw(canvas);
                        canvas.restore();
                    }
                }
                if (this.duration != 0) {
                    int dp2;
                    int i3;
                    int i4;
                    int dp3;
                    if (WebPlayerView.this.isInline) {
                        dp2 = measuredHeight - AndroidUtilities.dp(7.0f);
                        i3 = measuredWidth;
                        i4 = 0;
                        dp3 = measuredHeight - AndroidUtilities.dp(3.0f);
                    } else if (WebPlayerView.this.inFullscreen) {
                        dp2 = measuredHeight - AndroidUtilities.dp(28.0f);
                        i3 = (measuredWidth - AndroidUtilities.dp(76.0f)) - this.durationWidth;
                        i4 = AndroidUtilities.dp(36.0f) + this.durationWidth;
                        dp3 = measuredHeight - AndroidUtilities.dp(29.0f);
                    } else {
                        dp2 = measuredHeight - AndroidUtilities.dp(12.0f);
                        i3 = measuredWidth;
                        i4 = 0;
                        dp3 = measuredHeight - AndroidUtilities.dp(13.0f);
                    }
                    if (WebPlayerView.this.inFullscreen) {
                        canvas.drawRect((float) i4, (float) dp3, (float) i3, (float) (AndroidUtilities.dp(3.0f) + dp3), this.progressInnerPaint);
                    }
                    int i5 = this.progressPressed ? this.currentProgressX : ((int) (((float) (i3 - i4)) * (((float) this.progress) / ((float) this.duration)))) + i4;
                    if (!(this.bufferedPercentage == 0 || this.duration == 0)) {
                        int i6 = (((i3 - i4) / this.duration) * this.bufferedPosition) + i4;
                        if (i5 < i6) {
                            i2 = i6 - i5;
                        }
                        canvas.drawRect((float) (i6 - i2), (float) dp3, ((float) i6) + (((float) ((i3 - i6) * this.bufferedPercentage)) / 100.0f), (float) (AndroidUtilities.dp(3.0f) + dp3), WebPlayerView.this.inFullscreen ? this.progressBufferedPaint : this.progressInnerPaint);
                    }
                    canvas.drawRect((float) i4, (float) dp3, (float) i5, (float) (AndroidUtilities.dp(3.0f) + dp3), this.progressPaint);
                    if (!WebPlayerView.this.isInline) {
                        canvas.drawCircle((float) i5, (float) dp2, (float) AndroidUtilities.dp(this.progressPressed ? 7.0f : 5.0f), this.progressPaint);
                    }
                }
            }
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return super.onInterceptTouchEvent(motionEvent);
            }
            if (this.isVisible) {
                onTouchEvent(motionEvent);
                return this.progressPressed;
            }
            show(true, true);
            return true;
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            int dp;
            int measuredWidth;
            int measuredHeight;
            if (WebPlayerView.this.inFullscreen) {
                dp = AndroidUtilities.dp(36.0f) + this.durationWidth;
                measuredWidth = (getMeasuredWidth() - AndroidUtilities.dp(76.0f)) - this.durationWidth;
                measuredHeight = getMeasuredHeight() - AndroidUtilities.dp(28.0f);
            } else {
                measuredWidth = getMeasuredWidth();
                measuredHeight = getMeasuredHeight() - AndroidUtilities.dp(12.0f);
                dp = 0;
            }
            int i = (this.duration != 0 ? (int) (((float) (measuredWidth - dp)) * (((float) this.progress) / ((float) this.duration))) : 0) + dp;
            if (motionEvent.getAction() == 0) {
                if (!this.isVisible || WebPlayerView.this.isInline || WebPlayerView.this.isStream) {
                    show(true, true);
                } else if (this.duration != 0) {
                    measuredWidth = (int) motionEvent.getX();
                    dp = (int) motionEvent.getY();
                    if (measuredWidth >= i - AndroidUtilities.dp(10.0f) && measuredWidth <= AndroidUtilities.dp(10.0f) + i && dp >= r0 - AndroidUtilities.dp(10.0f) && dp <= r0 + AndroidUtilities.dp(10.0f)) {
                        this.progressPressed = true;
                        this.lastProgressX = measuredWidth;
                        this.currentProgressX = i;
                        getParent().requestDisallowInterceptTouchEvent(true);
                        invalidate();
                    }
                }
                AndroidUtilities.cancelRunOnUIThread(this.hideRunnable);
            } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                if (WebPlayerView.this.initied && WebPlayerView.this.videoPlayer.isPlaying()) {
                    AndroidUtilities.runOnUIThread(this.hideRunnable, 3000);
                }
                if (this.progressPressed) {
                    this.progressPressed = false;
                    if (WebPlayerView.this.initied) {
                        this.progress = (int) (((float) this.duration) * (((float) (this.currentProgressX - dp)) / ((float) (measuredWidth - dp))));
                        WebPlayerView.this.videoPlayer.seekTo(((long) this.progress) * 1000);
                    }
                }
            } else if (motionEvent.getAction() == 2 && this.progressPressed) {
                measuredHeight = (int) motionEvent.getX();
                this.currentProgressX -= this.lastProgressX - measuredHeight;
                this.lastProgressX = measuredHeight;
                if (this.currentProgressX < dp) {
                    this.currentProgressX = dp;
                } else if (this.currentProgressX > measuredWidth) {
                    this.currentProgressX = measuredWidth;
                }
                setProgress((int) (((float) (this.duration * 1000)) * (((float) (this.currentProgressX - dp)) / ((float) (measuredWidth - dp)))));
                invalidate();
            }
            super.onTouchEvent(motionEvent);
            return true;
        }

        public void requestDisallowInterceptTouchEvent(boolean z) {
            super.requestDisallowInterceptTouchEvent(z);
            checkNeedHide();
        }

        public void setBufferedProgress(int i, int i2) {
            this.bufferedPosition = i;
            this.bufferedPercentage = i2;
            invalidate();
        }

        public void setDuration(int i) {
            if (this.duration != i && i >= 0 && !WebPlayerView.this.isStream) {
                this.duration = i;
                this.durationLayout = new StaticLayout(String.format(Locale.US, "%d:%02d", new Object[]{Integer.valueOf(this.duration / 60), Integer.valueOf(this.duration % 60)}), this.textPaint, AndroidUtilities.dp(1000.0f), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                if (this.durationLayout.getLineCount() > 0) {
                    this.durationWidth = (int) Math.ceil((double) this.durationLayout.getLineWidth(0));
                }
                invalidate();
            }
        }

        public void setProgress(int i) {
            if (!this.progressPressed && i >= 0 && !WebPlayerView.this.isStream) {
                this.progress = i;
                this.progressLayout = new StaticLayout(String.format(Locale.US, "%d:%02d", new Object[]{Integer.valueOf(this.progress / 60), Integer.valueOf(this.progress % 60)}), this.textPaint, AndroidUtilities.dp(1000.0f), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                invalidate();
            }
        }

        public void show(boolean z, boolean z2) {
            if (this.isVisible != z) {
                this.isVisible = z;
                if (this.currentAnimation != null) {
                    this.currentAnimation.cancel();
                }
                AnimatorSet animatorSet;
                Animator[] animatorArr;
                if (this.isVisible) {
                    if (z2) {
                        this.currentAnimation = new AnimatorSet();
                        animatorSet = this.currentAnimation;
                        animatorArr = new Animator[1];
                        animatorArr[0] = ObjectAnimator.ofFloat(this, "alpha", new float[]{1.0f});
                        animatorSet.playTogether(animatorArr);
                        this.currentAnimation.setDuration(150);
                        this.currentAnimation.addListener(new C46552());
                        this.currentAnimation.start();
                    } else {
                        setAlpha(1.0f);
                    }
                } else if (z2) {
                    this.currentAnimation = new AnimatorSet();
                    animatorSet = this.currentAnimation;
                    animatorArr = new Animator[1];
                    animatorArr[0] = ObjectAnimator.ofFloat(this, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorSet.playTogether(animatorArr);
                    this.currentAnimation.setDuration(150);
                    this.currentAnimation.addListener(new C46563());
                    this.currentAnimation.start();
                } else {
                    setAlpha(BitmapDescriptorFactory.HUE_RED);
                }
                checkNeedHide();
            }
        }
    }

    private class CoubVideoTask extends AsyncTask<Void, Void, String> {
        private boolean canRetry = true;
        private String[] results = new String[4];
        private String videoId;

        public CoubVideoTask(String str) {
            this.videoId = str;
        }

        private String decodeUrl(String str) {
            StringBuilder stringBuilder = new StringBuilder(str);
            for (int i = 0; i < stringBuilder.length(); i++) {
                char charAt = stringBuilder.charAt(i);
                char toLowerCase = Character.toLowerCase(charAt);
                if (charAt == toLowerCase) {
                    toLowerCase = Character.toUpperCase(charAt);
                }
                stringBuilder.setCharAt(i, toLowerCase);
            }
            try {
                return new String(Base64.decode(stringBuilder.toString(), 0), C3446C.UTF8_NAME);
            } catch (Exception e) {
                return null;
            }
        }

        protected String doInBackground(Void... voidArr) {
            String downloadUrlContent = WebPlayerView.this.downloadUrlContent(this, String.format(Locale.US, "https://coub.com/api/v2/coubs/%s.json", new Object[]{this.videoId}));
            if (isCancelled()) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(downloadUrlContent).getJSONObject("file_versions").getJSONObject("mobile");
                String decodeUrl = decodeUrl(jSONObject.getString("gifv"));
                downloadUrlContent = jSONObject.getJSONArray(MimeTypes.BASE_TYPE_AUDIO).getString(0);
                if (!(decodeUrl == null || downloadUrlContent == null)) {
                    this.results[0] = decodeUrl;
                    this.results[1] = "other";
                    this.results[2] = downloadUrlContent;
                    this.results[3] = "other";
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            return !isCancelled() ? this.results[0] : null;
        }

        protected void onPostExecute(String str) {
            if (str != null) {
                WebPlayerView.this.initied = true;
                WebPlayerView.this.playVideoUrl = str;
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

        public JSExtractor(String str) {
            this.jsCode = str;
        }

        private void buildFunction(String[] strArr, String str) {
            HashMap hashMap = new HashMap();
            for (Object put : strArr) {
                hashMap.put(put, TtmlNode.ANONYMOUS_REGION_ID);
            }
            String[] split = str.split(";");
            boolean[] zArr = new boolean[1];
            int i = 0;
            while (i < split.length) {
                interpretStatement(split[i], hashMap, zArr, 100);
                if (!zArr[0]) {
                    i++;
                } else {
                    return;
                }
            }
        }

        private String extractFunction(String str) {
            try {
                String quote = Pattern.quote(str);
                Matcher matcher = Pattern.compile(String.format(Locale.US, "(?x)(?:function\\s+%s|[{;,]\\s*%s\\s*=\\s*function|var\\s+%s\\s*=\\s*function)\\s*\\(([^)]*)\\)\\s*\\{([^}]+)\\}", new Object[]{quote, quote, quote})).matcher(this.jsCode);
                if (matcher.find()) {
                    String group = matcher.group();
                    if (!this.codeLines.contains(group)) {
                        this.codeLines.add(group + ";");
                    }
                    buildFunction(matcher.group(1).split(","), matcher.group(2));
                }
            } catch (Throwable e) {
                this.codeLines.clear();
                FileLog.e(e);
            }
            return TextUtils.join(TtmlNode.ANONYMOUS_REGION_ID, this.codeLines);
        }

        private HashMap<String, Object> extractObject(String str) {
            Matcher matcher;
            String str2 = "(?:[a-zA-Z$0-9]+|\"[a-zA-Z$0-9]+\"|'[a-zA-Z$0-9]+')";
            HashMap<String, Object> hashMap = new HashMap();
            Matcher matcher2 = Pattern.compile(String.format(Locale.US, "(?:var\\s+)?%s\\s*=\\s*\\{\\s*((%s\\s*:\\s*function\\(.*?\\)\\s*\\{.*?\\}(?:,\\s*)?)*)\\}\\s*;", new Object[]{Pattern.quote(str), str2})).matcher(this.jsCode);
            CharSequence charSequence = null;
            while (matcher2.find()) {
                String group = matcher2.group();
                charSequence = matcher2.group(2);
                if (!TextUtils.isEmpty(charSequence)) {
                    if (!this.codeLines.contains(group)) {
                        this.codeLines.add(matcher2.group());
                    }
                    matcher = Pattern.compile(String.format("(%s)\\s*:\\s*function\\(([a-z,]+)\\)\\{([^}]+)\\}", new Object[]{str2})).matcher(charSequence);
                    while (matcher.find()) {
                        buildFunction(matcher.group(2).split(","), matcher.group(3));
                    }
                    return hashMap;
                }
            }
            matcher = Pattern.compile(String.format("(%s)\\s*:\\s*function\\(([a-z,]+)\\)\\{([^}]+)\\}", new Object[]{str2})).matcher(charSequence);
            while (matcher.find()) {
                buildFunction(matcher.group(2).split(","), matcher.group(3));
            }
            return hashMap;
        }

        private void interpretExpression(String str, HashMap<String, String> hashMap, int i) {
            int i2;
            CharSequence charSequence;
            String trim = str.trim();
            if (!TextUtils.isEmpty(trim)) {
                Matcher matcher;
                CharSequence trim2;
                int i3;
                Object obj;
                if (trim.charAt(0) == '(') {
                    int i4;
                    i2 = 0;
                    matcher = WebPlayerView.exprParensPattern.matcher(trim);
                    while (matcher.find()) {
                        if (matcher.group(0).indexOf(48) == 40) {
                            i2++;
                        } else {
                            i2--;
                            if (i2 == 0) {
                                interpretExpression(trim.substring(1, matcher.start()), hashMap, i);
                                trim2 = trim.substring(matcher.end()).trim();
                                if (!TextUtils.isEmpty(trim2)) {
                                    i4 = i2;
                                    charSequence = trim2;
                                    i3 = i4;
                                    if (i3 != 0) {
                                        throw new Exception(String.format("Premature end of parens in %s", new Object[]{charSequence}));
                                    }
                                }
                                return;
                            }
                        }
                    }
                    i4 = i2;
                    obj = trim;
                    i3 = i4;
                    if (i3 != 0) {
                        throw new Exception(String.format("Premature end of parens in %s", new Object[]{charSequence}));
                    }
                }
                charSequence = trim;
                for (String str2 : this.assign_operators) {
                    matcher = Pattern.compile(String.format(Locale.US, "(?x)(%s)(?:\\[([^\\]]+?)\\])?\\s*%s(.*)$", new Object[]{WebPlayerView.exprName, Pattern.quote(str2)})).matcher(charSequence);
                    if (matcher.find()) {
                        interpretExpression(matcher.group(3), hashMap, i - 1);
                        obj = matcher.group(2);
                        if (TextUtils.isEmpty(obj)) {
                            hashMap.put(matcher.group(1), TtmlNode.ANONYMOUS_REGION_ID);
                            return;
                        } else {
                            interpretExpression(obj, hashMap, i);
                            return;
                        }
                    }
                }
                try {
                    Integer.parseInt(charSequence);
                } catch (Exception e) {
                    if (!Pattern.compile(String.format(Locale.US, "(?!if|return|true|false)(%s)$", new Object[]{WebPlayerView.exprName})).matcher(charSequence).find()) {
                        if (charSequence.charAt(0) != '\"' || charSequence.charAt(charSequence.length() - 1) != '\"') {
                            try {
                                new JSONObject(charSequence).toString();
                            } catch (Exception e2) {
                                Matcher matcher2 = Pattern.compile(String.format(Locale.US, "(%s)\\[(.+)\\]$", new Object[]{WebPlayerView.exprName})).matcher(charSequence);
                                if (matcher2.find()) {
                                    matcher2.group(1);
                                    interpretExpression(matcher2.group(2), hashMap, i - 1);
                                    return;
                                }
                                Matcher matcher3 = Pattern.compile(String.format(Locale.US, "(%s)(?:\\.([^(]+)|\\[([^]]+)\\])\\s*(?:\\(+([^()]*)\\))?$", new Object[]{WebPlayerView.exprName})).matcher(charSequence);
                                if (matcher3.find()) {
                                    String group = matcher3.group(1);
                                    CharSequence group2 = matcher3.group(2);
                                    trim = matcher3.group(3);
                                    if (!TextUtils.isEmpty(group2)) {
                                        trim2 = group2;
                                    }
                                    trim.replace("\"", TtmlNode.ANONYMOUS_REGION_ID);
                                    trim = matcher3.group(4);
                                    if (hashMap.get(group) == null) {
                                        extractObject(group);
                                    }
                                    if (trim == null) {
                                        return;
                                    }
                                    if (charSequence.charAt(charSequence.length() - 1) != ')') {
                                        throw new Exception("last char not ')'");
                                    } else if (trim.length() != 0) {
                                        String[] split = trim.split(",");
                                        for (String str22 : split) {
                                            interpretExpression(str22, hashMap, i);
                                        }
                                        return;
                                    } else {
                                        return;
                                    }
                                }
                                matcher2 = Pattern.compile(String.format(Locale.US, "(%s)\\[(.+)\\]$", new Object[]{WebPlayerView.exprName})).matcher(charSequence);
                                if (matcher2.find()) {
                                    hashMap.get(matcher2.group(1));
                                    interpretExpression(matcher2.group(2), hashMap, i - 1);
                                    return;
                                }
                                for (String str222 : this.operators) {
                                    matcher3 = Pattern.compile(String.format(Locale.US, "(.+?)%s(.+)", new Object[]{Pattern.quote(str222)})).matcher(charSequence);
                                    if (matcher3.find()) {
                                        boolean[] zArr = new boolean[1];
                                        interpretStatement(matcher3.group(1), hashMap, zArr, i - 1);
                                        if (zArr[0]) {
                                            throw new Exception(String.format("Premature left-side return of %s in %s", new Object[]{str222, charSequence}));
                                        }
                                        interpretStatement(matcher3.group(2), hashMap, zArr, i - 1);
                                        if (zArr[0]) {
                                            throw new Exception(String.format("Premature right-side return of %s in %s", new Object[]{str222, charSequence}));
                                        }
                                    }
                                }
                                matcher2 = Pattern.compile(String.format(Locale.US, "^(%s)\\(([a-zA-Z0-9_$,]*)\\)$", new Object[]{WebPlayerView.exprName})).matcher(charSequence);
                                if (matcher2.find()) {
                                    extractFunction(matcher2.group(1));
                                }
                                throw new Exception(String.format("Unsupported JS expression %s", new Object[]{charSequence}));
                            }
                        }
                    }
                }
            }
        }

        private void interpretStatement(String str, HashMap<String, String> hashMap, boolean[] zArr, int i) {
            if (i < 0) {
                throw new Exception("recursion limit reached");
            }
            zArr[0] = false;
            String trim = str.trim();
            Matcher matcher = WebPlayerView.stmtVarPattern.matcher(trim);
            if (matcher.find()) {
                trim = trim.substring(matcher.group(0).length());
            } else {
                matcher = WebPlayerView.stmtReturnPattern.matcher(trim);
                if (matcher.find()) {
                    trim = trim.substring(matcher.group(0).length());
                    zArr[0] = true;
                }
            }
            interpretExpression(trim, hashMap, i);
        }
    }

    private class TwitchClipVideoTask extends AsyncTask<Void, Void, String> {
        private boolean canRetry = true;
        private String currentUrl;
        private String[] results = new String[2];
        private String videoId;

        public TwitchClipVideoTask(String str, String str2) {
            this.videoId = str2;
            this.currentUrl = str;
        }

        protected String doInBackground(Void... voidArr) {
            CharSequence downloadUrlContent = WebPlayerView.this.downloadUrlContent(this, this.currentUrl, null, false);
            if (isCancelled()) {
                return null;
            }
            try {
                Matcher matcher = WebPlayerView.twitchClipFilePattern.matcher(downloadUrlContent);
                if (matcher.find()) {
                    this.results[0] = new JSONObject(matcher.group(1)).getJSONArray("quality_options").getJSONObject(0).getString(C1797b.SOURCE);
                    this.results[1] = "other";
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            return !isCancelled() ? this.results[0] : null;
        }

        protected void onPostExecute(String str) {
            if (str != null) {
                WebPlayerView.this.initied = true;
                WebPlayerView.this.playVideoUrl = str;
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

        public TwitchStreamVideoTask(String str, String str2) {
            this.videoId = str2;
            this.currentUrl = str;
        }

        protected String doInBackground(Void... voidArr) {
            HashMap hashMap = new HashMap();
            hashMap.put("Client-ID", "jzkbprff40iqj646a697cyrvl0zt2m6");
            int indexOf = this.videoId.indexOf(38);
            if (indexOf > 0) {
                this.videoId = this.videoId.substring(0, indexOf);
            }
            String downloadUrlContent = WebPlayerView.this.downloadUrlContent(this, String.format(Locale.US, "https://api.twitch.tv/kraken/streams/%s?stream_type=all", new Object[]{this.videoId}), hashMap, false);
            if (isCancelled()) {
                return null;
            }
            try {
                new JSONObject(downloadUrlContent).getJSONObject("stream");
                JSONObject jSONObject = new JSONObject(WebPlayerView.this.downloadUrlContent(this, String.format(Locale.US, "https://api.twitch.tv/api/channels/%s/access_token", new Object[]{this.videoId}), hashMap, false));
                String encode = URLEncoder.encode(jSONObject.getString("sig"), C3446C.UTF8_NAME);
                downloadUrlContent = URLEncoder.encode(jSONObject.getString("token"), C3446C.UTF8_NAME);
                URLEncoder.encode("https://youtube.googleapis.com/v/" + this.videoId, C3446C.UTF8_NAME);
                encode = "allow_source=true&allow_audio_only=true&allow_spectre=true&player=twitchweb&segment_preference=4&p=" + ((int) (Math.random() * 1.0E7d)) + "&sig=" + encode + "&token=" + downloadUrlContent;
                this.results[0] = String.format(Locale.US, "https://usher.ttvnw.net/api/channel/hls/%s.m3u8?%s", new Object[]{this.videoId, encode});
                this.results[1] = "hls";
            } catch (Throwable e) {
                FileLog.e(e);
            }
            return !isCancelled() ? this.results[0] : null;
        }

        protected void onPostExecute(String str) {
            if (str != null) {
                WebPlayerView.this.initied = true;
                WebPlayerView.this.playVideoUrl = str;
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

        public VimeoVideoTask(String str) {
            this.videoId = str;
        }

        protected String doInBackground(Void... voidArr) {
            String downloadUrlContent = WebPlayerView.this.downloadUrlContent(this, String.format(Locale.US, "https://player.vimeo.com/video/%s/config", new Object[]{this.videoId}));
            if (isCancelled()) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(downloadUrlContent).getJSONObject("request").getJSONObject("files");
                if (jSONObject.has("hls")) {
                    jSONObject = jSONObject.getJSONObject("hls");
                    try {
                        this.results[0] = jSONObject.getString(ImagesContract.URL);
                    } catch (Exception e) {
                        this.results[0] = jSONObject.getJSONObject("cdns").getJSONObject(jSONObject.getString("default_cdn")).getString(ImagesContract.URL);
                    }
                    this.results[1] = "hls";
                    return isCancelled() ? this.results[0] : null;
                } else {
                    if (jSONObject.has("progressive")) {
                        this.results[1] = "other";
                        this.results[0] = jSONObject.getJSONArray("progressive").getJSONObject(0).getString(ImagesContract.URL);
                    }
                    if (isCancelled()) {
                    }
                }
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
        }

        protected void onPostExecute(String str) {
            if (str != null) {
                WebPlayerView.this.initied = true;
                WebPlayerView.this.playVideoUrl = str;
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

        public YoutubeVideoTask(String str) {
            this.videoId = str;
        }

        private void onInterfaceResult(String str) {
            this.result[0] = this.result[0].replace(this.sig, "/signature/" + str);
            this.semaphore.release();
        }

        protected String[] doInBackground(Void... voidArr) {
            Throwable th;
            CharSequence downloadUrlContent = WebPlayerView.this.downloadUrlContent(this, "https://www.youtube.com/embed/" + this.videoId);
            if (isCancelled()) {
                return null;
            }
            String str;
            int indexOf;
            String str2 = "video_id=" + this.videoId + "&ps=default&gl=US&hl=en";
            try {
                str = str2 + "&eurl=" + URLEncoder.encode("https://youtube.googleapis.com/v/" + this.videoId, C3446C.UTF8_NAME);
            } catch (Throwable e) {
                FileLog.e(e);
                str = str2;
            }
            if (downloadUrlContent != null) {
                Matcher matcher = WebPlayerView.stsPattern.matcher(downloadUrlContent);
                str = matcher.find() ? str + "&sts=" + downloadUrlContent.substring(matcher.start() + 6, matcher.end()) : str + "&sts=";
            }
            this.result[1] = "dash";
            Object obj = null;
            String[] strArr = new String[]{TtmlNode.ANONYMOUS_REGION_ID, "&el=info", "&el=embedded", "&el=detailpage", "&el=vevo"};
            for (String str3 : strArr) {
                String str32;
                String downloadUrlContent2 = WebPlayerView.this.downloadUrlContent(this, "https://www.youtube.com/get_video_info?" + str + str32);
                if (isCancelled()) {
                    return null;
                }
                Object obj2;
                Object obj3;
                String str4;
                str32 = null;
                if (downloadUrlContent2 != null) {
                    String[] split = downloadUrlContent2.split("&");
                    obj2 = null;
                    obj3 = null;
                    int i = 0;
                    Object obj4 = obj;
                    str4 = null;
                    while (i < split.length) {
                        Object obj5;
                        String[] split2;
                        if (split[i].startsWith("dashmpd")) {
                            obj3 = 1;
                            split2 = split[i].split("=");
                            if (split2.length == 2) {
                                try {
                                    this.result[0] = URLDecoder.decode(split2[1], C3446C.UTF8_NAME);
                                } catch (Throwable e2) {
                                    FileLog.e(e2);
                                }
                            }
                            obj5 = obj2;
                            downloadUrlContent2 = str4;
                            obj = obj5;
                        } else if (split[i].startsWith("use_cipher_signature")) {
                            split2 = split[i].split("=");
                            if (split2.length == 2 && split2[1].toLowerCase().equals("true")) {
                                obj4 = 1;
                            }
                            obj5 = obj2;
                            downloadUrlContent2 = str4;
                            obj = obj5;
                        } else if (split[i].startsWith("hlsvp")) {
                            split2 = split[i].split("=");
                            if (split2.length == 2) {
                                try {
                                    str4 = URLDecoder.decode(split2[1], C3446C.UTF8_NAME);
                                } catch (Throwable e22) {
                                    FileLog.e(e22);
                                }
                            }
                            obj5 = obj2;
                            downloadUrlContent2 = str4;
                            obj = obj5;
                        } else {
                            if (split[i].startsWith("livestream")) {
                                split2 = split[i].split("=");
                                if (split2.length == 2 && split2[1].toLowerCase().equals("1")) {
                                    downloadUrlContent2 = str4;
                                    int i2 = 1;
                                }
                            }
                            obj5 = obj2;
                            downloadUrlContent2 = str4;
                            obj = obj5;
                        }
                        i++;
                        obj5 = obj;
                        str4 = downloadUrlContent2;
                        obj2 = obj5;
                    }
                    String str5 = str4;
                    obj = obj4;
                    str32 = str5;
                } else {
                    obj2 = null;
                    obj3 = null;
                }
                if (obj2 != null) {
                    if (str32 == null || r3 != null || str32.contains("/s/")) {
                        return null;
                    }
                    this.result[0] = str32;
                    this.result[1] = "hls";
                }
                if (obj3 != null) {
                    break;
                }
            }
            if (this.result[0] != null && ((r3 != null || this.result[0].contains("/s/")) && downloadUrlContent != null)) {
                indexOf = this.result[0].indexOf("/s/");
                int indexOf2 = this.result[0].indexOf(47, indexOf + 10);
                if (indexOf != -1) {
                    if (indexOf2 == -1) {
                        indexOf2 = this.result[0].length();
                    }
                    this.sig = this.result[0].substring(indexOf, indexOf2);
                    str2 = null;
                    Matcher matcher2 = WebPlayerView.jsPattern.matcher(downloadUrlContent);
                    if (matcher2.find()) {
                        try {
                            Object nextValue = new JSONTokener(matcher2.group(1)).nextValue();
                            str2 = nextValue instanceof String ? (String) nextValue : null;
                        } catch (Throwable e3) {
                            FileLog.e(e3);
                        }
                    }
                    if (str2 != null) {
                        matcher2 = WebPlayerView.playerIdPattern.matcher(str2);
                        str = matcher2.find() ? matcher2.group(1) + matcher2.group(2) : null;
                        downloadUrlContent2 = null;
                        str4 = null;
                        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("youtubecode", 0);
                        if (str != null) {
                            downloadUrlContent2 = sharedPreferences.getString(str, null);
                            str4 = sharedPreferences.getString(str + "n", null);
                        }
                        if (downloadUrlContent2 == null) {
                            if (str2.startsWith("//")) {
                                str2 = "https:" + str2;
                            } else if (str2.startsWith("/")) {
                                str2 = "https://www.youtube.com" + str2;
                            }
                            CharSequence downloadUrlContent3 = WebPlayerView.this.downloadUrlContent(this, str2);
                            if (isCancelled()) {
                                return null;
                            }
                            if (downloadUrlContent3 != null) {
                                matcher = WebPlayerView.sigPattern.matcher(downloadUrlContent3);
                                if (matcher.find()) {
                                    str2 = matcher.group(1);
                                } else {
                                    matcher = WebPlayerView.sigPattern2.matcher(downloadUrlContent3);
                                    str2 = matcher.find() ? matcher.group(1) : str4;
                                }
                                if (str2 != null) {
                                    try {
                                        str4 = new JSExtractor(downloadUrlContent3).extractFunction(str2);
                                        try {
                                            if (!(TextUtils.isEmpty(str4) || str == null)) {
                                                sharedPreferences.edit().putString(str, str4).putString(str + "n", str2).commit();
                                            }
                                            str = str2;
                                            str2 = str4;
                                        } catch (Throwable e32) {
                                            Throwable th2 = e32;
                                            str = str4;
                                            th = th2;
                                            FileLog.e(th);
                                            str5 = str2;
                                            str2 = str;
                                            str = str5;
                                            if (!TextUtils.isEmpty(str2)) {
                                                str = VERSION.SDK_INT < 21 ? str2 + "window." + WebPlayerView.this.interfaceName + ".returnResultToJava(" + str + "('" + this.sig.substring(3) + "'));" : str2 + str + "('" + this.sig.substring(3) + "');";
                                                try {
                                                    AndroidUtilities.runOnUIThread(new Runnable() {

                                                        /* renamed from: org.telegram.ui.Components.WebPlayerView$YoutubeVideoTask$1$1 */
                                                        class C46571 implements ValueCallback<String> {
                                                            C46571() {
                                                            }

                                                            public void onReceiveValue(String str) {
                                                                YoutubeVideoTask.this.result[0] = YoutubeVideoTask.this.result[0].replace(YoutubeVideoTask.this.sig, "/signature/" + str.substring(1, str.length() - 1));
                                                                YoutubeVideoTask.this.semaphore.release();
                                                            }
                                                        }

                                                        public void run() {
                                                            if (VERSION.SDK_INT >= 21) {
                                                                WebPlayerView.this.webView.evaluateJavascript(str, new C46571());
                                                                return;
                                                            }
                                                            try {
                                                                WebPlayerView.this.webView.loadUrl("data:text/html;charset=utf-8;base64," + Base64.encodeToString(("<script>" + str + "</script>").getBytes(C3446C.UTF8_NAME), 0));
                                                            } catch (Throwable e) {
                                                                FileLog.e(e);
                                                            }
                                                        }
                                                    });
                                                    this.semaphore.acquire();
                                                    obj = null;
                                                } catch (Throwable e322) {
                                                    FileLog.e(e322);
                                                }
                                                if (!isCancelled()) {
                                                }
                                            }
                                            i2 = 1;
                                            if (isCancelled()) {
                                            }
                                        }
                                    } catch (Throwable e3222) {
                                        th = e3222;
                                        str = downloadUrlContent2;
                                        FileLog.e(th);
                                        str5 = str2;
                                        str2 = str;
                                        str = str5;
                                        if (TextUtils.isEmpty(str2)) {
                                            if (VERSION.SDK_INT < 21) {
                                            }
                                            AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                                            this.semaphore.acquire();
                                            obj = null;
                                            if (isCancelled()) {
                                            }
                                        }
                                        i2 = 1;
                                        if (isCancelled()) {
                                        }
                                    }
                                }
                                str = str2;
                                str2 = downloadUrlContent2;
                                if (TextUtils.isEmpty(str2)) {
                                    if (VERSION.SDK_INT < 21) {
                                    }
                                    AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                                    this.semaphore.acquire();
                                    obj = null;
                                }
                            }
                        }
                        str = str4;
                        str2 = downloadUrlContent2;
                        if (TextUtils.isEmpty(str2)) {
                            if (VERSION.SDK_INT < 21) {
                            }
                            AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                            this.semaphore.acquire();
                            obj = null;
                        }
                    }
                }
                i2 = 1;
            }
            return (isCancelled() || obj != null) ? null : this.result;
        }

        protected void onPostExecute(String[] strArr) {
            if (strArr != null) {
                WebPlayerView.this.initied = true;
                WebPlayerView.this.playVideoUrl = strArr[0];
                WebPlayerView.this.playVideoType = strArr[1];
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
        private function() {
        }

        public abstract Object run(Object[] objArr);
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    public WebPlayerView(Context context, boolean z, boolean z2, WebPlayerViewDelegate webPlayerViewDelegate) {
        super(context);
        int i = lastContainerId;
        lastContainerId = i + 1;
        this.fragment_container_id = i;
        this.allowInlineAnimation = VERSION.SDK_INT >= 21;
        this.backgroundPaint = new Paint();
        this.progressRunnable = new C46431();
        this.surfaceTextureListener = new C46462();
        this.switchToInlineRunnable = new C46473();
        setWillNotDraw(false);
        this.delegate = webPlayerViewDelegate;
        this.backgroundPaint.setColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        this.aspectRatioFrameLayout = new AspectRatioFrameLayout(context) {
            protected void onMeasure(int i, int i2) {
                super.onMeasure(i, i2);
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
        this.webView.addJavascriptInterface(new WebPlayerView$JavaScriptInterface(this, new C46495()), this.interfaceName);
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        this.textureViewContainer = this.delegate.getTextureViewContainer();
        this.textureView = new TextureView(context);
        this.textureView.setPivotX(BitmapDescriptorFactory.HUE_RED);
        this.textureView.setPivotY(BitmapDescriptorFactory.HUE_RED);
        if (this.textureViewContainer != null) {
            this.textureViewContainer.addView(this.textureView);
        } else {
            this.aspectRatioFrameLayout.addView(this.textureView, LayoutHelper.createFrame(-1, -1, 17));
        }
        if (this.allowInlineAnimation && this.textureViewContainer != null) {
            this.textureImageView = new ImageView(context);
            this.textureImageView.setBackgroundColor(-65536);
            this.textureImageView.setPivotX(BitmapDescriptorFactory.HUE_RED);
            this.textureImageView.setPivotY(BitmapDescriptorFactory.HUE_RED);
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
        this.controlsView.addView(this.fullscreenButton, LayoutHelper.createFrame(56, 56.0f, 85, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 5.0f));
        this.fullscreenButton.setOnClickListener(new C46506());
        this.playButton = new ImageView(context);
        this.playButton.setScaleType(ScaleType.CENTER);
        this.controlsView.addView(this.playButton, LayoutHelper.createFrame(48, 48, 17));
        this.playButton.setOnClickListener(new C46517());
        if (z) {
            this.inlineButton = new ImageView(context);
            this.inlineButton.setScaleType(ScaleType.CENTER);
            this.controlsView.addView(this.inlineButton, LayoutHelper.createFrame(56, 48, 53));
            this.inlineButton.setOnClickListener(new C46528());
        }
        if (z2) {
            this.shareButton = new ImageView(context);
            this.shareButton.setScaleType(ScaleType.CENTER);
            this.shareButton.setImageResource(R.drawable.ic_share_video);
            this.controlsView.addView(this.shareButton, LayoutHelper.createFrame(56, 48, 53));
            this.shareButton.setOnClickListener(new C46539());
        }
        updatePlayButton();
        updateFullscreenButton();
        updateInlineButton();
        updateShareButton();
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

    private View getControlView() {
        return this.controlsView;
    }

    private View getProgressView() {
        return this.progressView;
    }

    private void onInitFailed() {
        if (this.controlsView.getParent() != this) {
            this.controlsView.setVisibility(8);
        }
        this.delegate.onInitFailed();
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
            if (this.videoPlayer.getDuration() != C3446C.TIME_UNSET) {
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

    private void showProgress(boolean z, boolean z2) {
        float f = 1.0f;
        if (z2) {
            if (this.progressAnimation != null) {
                this.progressAnimation.cancel();
            }
            this.progressAnimation = new AnimatorSet();
            AnimatorSet animatorSet = this.progressAnimation;
            Animator[] animatorArr = new Animator[1];
            RadialProgressView radialProgressView = this.progressView;
            String str = "alpha";
            float[] fArr = new float[1];
            if (!z) {
                f = BitmapDescriptorFactory.HUE_RED;
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
        if (!z) {
            f = BitmapDescriptorFactory.HUE_RED;
        }
        radialProgressView2.setAlpha(f);
    }

    private void updateFullscreenButton() {
        if (!this.videoPlayer.isPlayerPrepared() || this.isInline) {
            this.fullscreenButton.setVisibility(8);
            return;
        }
        this.fullscreenButton.setVisibility(0);
        if (this.inFullscreen) {
            this.fullscreenButton.setImageResource(R.drawable.ic_outfullscreen);
            this.fullscreenButton.setLayoutParams(LayoutHelper.createFrame(56, 56.0f, 85, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 1.0f));
            return;
        }
        this.fullscreenButton.setImageResource(R.drawable.ic_gofullscreen);
        this.fullscreenButton.setLayoutParams(LayoutHelper.createFrame(56, 56.0f, 85, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 5.0f));
    }

    private void updateFullscreenState(boolean z) {
        if (this.textureView != null) {
            updateFullscreenButton();
            ViewGroup viewGroup;
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
                    viewGroup = (ViewGroup) this.controlsView.getParent();
                    if (viewGroup != this) {
                        if (viewGroup != null) {
                            viewGroup.removeView(this.controlsView);
                        }
                        if (this.textureViewContainer != null) {
                            this.textureViewContainer.addView(this.controlsView);
                        } else {
                            addView(this.controlsView, 1);
                        }
                    }
                }
                this.changedTextureView = this.delegate.onSwitchToFullscreen(this.controlsView, this.inFullscreen, this.aspectRatioFrameLayout.getAspectRatio(), this.aspectRatioFrameLayout.getVideoRotation(), z);
                this.changedTextureView.setVisibility(4);
                if (this.inFullscreen && this.changedTextureView != null) {
                    viewGroup = (ViewGroup) this.textureView.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(this.textureView);
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
                viewGroup = (ViewGroup) this.aspectRatioFrameLayout.getParent();
                if (viewGroup != this) {
                    if (viewGroup != null) {
                        viewGroup.removeView(this.aspectRatioFrameLayout);
                    }
                    addView(this.aspectRatioFrameLayout, 0);
                }
            }
            this.delegate.onSwitchToFullscreen(this.controlsView, this.inFullscreen, this.aspectRatioFrameLayout.getAspectRatio(), this.aspectRatioFrameLayout.getVideoRotation(), z);
        }
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

    private void updateShareButton() {
        if (this.shareButton != null) {
            ImageView imageView = this.shareButton;
            int i = (this.isInline || !this.videoPlayer.isPlayerPrepared()) ? 8 : 0;
            imageView.setVisibility(i);
        }
    }

    public void destroy() {
        this.videoPlayer.releasePlayer();
        if (this.currentTask != null) {
            this.currentTask.cancel(true);
            this.currentTask = null;
        }
        this.webView.stopLoading();
    }

    protected String downloadUrlContent(AsyncTask asyncTask, String str) {
        return downloadUrlContent(asyncTask, str, null, true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected java.lang.String downloadUrlContent(android.os.AsyncTask r13, java.lang.String r14, java.util.HashMap<java.lang.String, java.lang.String> r15, boolean r16) {
        /*
        r12 = this;
        r5 = 1;
        r6 = 0;
        r4 = 0;
        r1 = new java.net.URL;	 Catch:{ Throwable -> 0x01cc }
        r1.<init>(r14);	 Catch:{ Throwable -> 0x01cc }
        r3 = r1.openConnection();	 Catch:{ Throwable -> 0x01cc }
        r1 = "User-Agent";
        r2 = "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)";
        r3.addRequestProperty(r1, r2);	 Catch:{ Throwable -> 0x0061 }
        if (r16 == 0) goto L_0x0020;
    L_0x0017:
        r1 = "Accept-Encoding";
        r2 = "gzip, deflate";
        r3.addRequestProperty(r1, r2);	 Catch:{ Throwable -> 0x0061 }
    L_0x0020:
        r1 = "Accept-Language";
        r2 = "en-us,en;q=0.5";
        r3.addRequestProperty(r1, r2);	 Catch:{ Throwable -> 0x0061 }
        r1 = "Accept";
        r2 = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
        r3.addRequestProperty(r1, r2);	 Catch:{ Throwable -> 0x0061 }
        r1 = "Accept-Charset";
        r2 = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
        r3.addRequestProperty(r1, r2);	 Catch:{ Throwable -> 0x0061 }
        if (r15 == 0) goto L_0x00a8;
    L_0x003d:
        r1 = r15.entrySet();	 Catch:{ Throwable -> 0x0061 }
        r7 = r1.iterator();	 Catch:{ Throwable -> 0x0061 }
    L_0x0045:
        r1 = r7.hasNext();	 Catch:{ Throwable -> 0x0061 }
        if (r1 == 0) goto L_0x00a8;
    L_0x004b:
        r1 = r7.next();	 Catch:{ Throwable -> 0x0061 }
        r1 = (java.util.Map.Entry) r1;	 Catch:{ Throwable -> 0x0061 }
        r2 = r1.getKey();	 Catch:{ Throwable -> 0x0061 }
        r2 = (java.lang.String) r2;	 Catch:{ Throwable -> 0x0061 }
        r1 = r1.getValue();	 Catch:{ Throwable -> 0x0061 }
        r1 = (java.lang.String) r1;	 Catch:{ Throwable -> 0x0061 }
        r3.addRequestProperty(r2, r1);	 Catch:{ Throwable -> 0x0061 }
        goto L_0x0045;
    L_0x0061:
        r1 = move-exception;
        r2 = r1;
    L_0x0063:
        r1 = r2 instanceof java.net.SocketTimeoutException;
        if (r1 == 0) goto L_0x0159;
    L_0x0067:
        r1 = org.telegram.tgnet.ConnectionsManager.isNetworkOnline();
        if (r1 == 0) goto L_0x01d9;
    L_0x006d:
        r1 = r4;
    L_0x006e:
        org.telegram.messenger.FileLog.e(r2);
        r2 = r1;
        r1 = r3;
        r3 = r6;
    L_0x0074:
        if (r2 == 0) goto L_0x01d6;
    L_0x0076:
        if (r1 == 0) goto L_0x008e;
    L_0x0078:
        r2 = r1 instanceof java.net.HttpURLConnection;	 Catch:{ Exception -> 0x0181 }
        if (r2 == 0) goto L_0x008e;
    L_0x007c:
        r1 = (java.net.HttpURLConnection) r1;	 Catch:{ Exception -> 0x0181 }
        r1 = r1.getResponseCode();	 Catch:{ Exception -> 0x0181 }
        r2 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r1 == r2) goto L_0x008e;
    L_0x0086:
        r2 = 202; // 0xca float:2.83E-43 double:1.0E-321;
        if (r1 == r2) goto L_0x008e;
    L_0x008a:
        r2 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        if (r1 == r2) goto L_0x008e;
    L_0x008e:
        if (r3 == 0) goto L_0x01d3;
    L_0x0090:
        r1 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r7 = new byte[r1];	 Catch:{ Throwable -> 0x01c5 }
        r1 = r6;
    L_0x0096:
        r2 = r13.isCancelled();	 Catch:{ Throwable -> 0x01b2 }
        if (r2 == 0) goto L_0x0187;
    L_0x009c:
        if (r3 == 0) goto L_0x00a1;
    L_0x009e:
        r3.close();	 Catch:{ Throwable -> 0x01bc }
    L_0x00a1:
        if (r4 == 0) goto L_0x01c2;
    L_0x00a3:
        r1 = r1.toString();
    L_0x00a7:
        return r1;
    L_0x00a8:
        r1 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r3.setConnectTimeout(r1);	 Catch:{ Throwable -> 0x0061 }
        r1 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r3.setReadTimeout(r1);	 Catch:{ Throwable -> 0x0061 }
        r1 = r3 instanceof java.net.HttpURLConnection;	 Catch:{ Throwable -> 0x0061 }
        if (r1 == 0) goto L_0x0140;
    L_0x00b6:
        r0 = r3;
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Throwable -> 0x0061 }
        r1 = r0;
        r2 = 1;
        r1.setInstanceFollowRedirects(r2);	 Catch:{ Throwable -> 0x0061 }
        r2 = r1.getResponseCode();	 Catch:{ Throwable -> 0x0061 }
        r7 = 302; // 0x12e float:4.23E-43 double:1.49E-321;
        if (r2 == r7) goto L_0x00ce;
    L_0x00c6:
        r7 = 301; // 0x12d float:4.22E-43 double:1.487E-321;
        if (r2 == r7) goto L_0x00ce;
    L_0x00ca:
        r7 = 303; // 0x12f float:4.25E-43 double:1.497E-321;
        if (r2 != r7) goto L_0x0140;
    L_0x00ce:
        r2 = "Location";
        r2 = r1.getHeaderField(r2);	 Catch:{ Throwable -> 0x0061 }
        r7 = "Set-Cookie";
        r1 = r1.getHeaderField(r7);	 Catch:{ Throwable -> 0x0061 }
        r7 = new java.net.URL;	 Catch:{ Throwable -> 0x0061 }
        r7.<init>(r2);	 Catch:{ Throwable -> 0x0061 }
        r3 = r7.openConnection();	 Catch:{ Throwable -> 0x0061 }
        r2 = "Cookie";
        r3.setRequestProperty(r2, r1);	 Catch:{ Throwable -> 0x0061 }
        r1 = "User-Agent";
        r2 = "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)";
        r3.addRequestProperty(r1, r2);	 Catch:{ Throwable -> 0x0061 }
        if (r16 == 0) goto L_0x00ff;
    L_0x00f6:
        r1 = "Accept-Encoding";
        r2 = "gzip, deflate";
        r3.addRequestProperty(r1, r2);	 Catch:{ Throwable -> 0x0061 }
    L_0x00ff:
        r1 = "Accept-Language";
        r2 = "en-us,en;q=0.5";
        r3.addRequestProperty(r1, r2);	 Catch:{ Throwable -> 0x0061 }
        r1 = "Accept";
        r2 = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
        r3.addRequestProperty(r1, r2);	 Catch:{ Throwable -> 0x0061 }
        r1 = "Accept-Charset";
        r2 = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
        r3.addRequestProperty(r1, r2);	 Catch:{ Throwable -> 0x0061 }
        if (r15 == 0) goto L_0x0140;
    L_0x011c:
        r1 = r15.entrySet();	 Catch:{ Throwable -> 0x0061 }
        r7 = r1.iterator();	 Catch:{ Throwable -> 0x0061 }
    L_0x0124:
        r1 = r7.hasNext();	 Catch:{ Throwable -> 0x0061 }
        if (r1 == 0) goto L_0x0140;
    L_0x012a:
        r1 = r7.next();	 Catch:{ Throwable -> 0x0061 }
        r1 = (java.util.Map.Entry) r1;	 Catch:{ Throwable -> 0x0061 }
        r2 = r1.getKey();	 Catch:{ Throwable -> 0x0061 }
        r2 = (java.lang.String) r2;	 Catch:{ Throwable -> 0x0061 }
        r1 = r1.getValue();	 Catch:{ Throwable -> 0x0061 }
        r1 = (java.lang.String) r1;	 Catch:{ Throwable -> 0x0061 }
        r3.addRequestProperty(r2, r1);	 Catch:{ Throwable -> 0x0061 }
        goto L_0x0124;
    L_0x0140:
        r3.connect();	 Catch:{ Throwable -> 0x0061 }
        if (r16 == 0) goto L_0x0154;
    L_0x0145:
        r1 = new java.util.zip.GZIPInputStream;	 Catch:{ Throwable -> 0x0061 }
        r2 = r3.getInputStream();	 Catch:{ Throwable -> 0x0061 }
        r1.<init>(r2);	 Catch:{ Throwable -> 0x0061 }
    L_0x014e:
        r2 = r5;
        r11 = r1;
        r1 = r3;
        r3 = r11;
        goto L_0x0074;
    L_0x0154:
        r1 = r3.getInputStream();	 Catch:{ Throwable -> 0x0061 }
        goto L_0x014e;
    L_0x0159:
        r1 = r2 instanceof java.net.UnknownHostException;
        if (r1 == 0) goto L_0x0160;
    L_0x015d:
        r1 = r4;
        goto L_0x006e;
    L_0x0160:
        r1 = r2 instanceof java.net.SocketException;
        if (r1 == 0) goto L_0x017a;
    L_0x0164:
        r1 = r2.getMessage();
        if (r1 == 0) goto L_0x01d9;
    L_0x016a:
        r1 = r2.getMessage();
        r7 = "ECONNRESET";
        r1 = r1.contains(r7);
        if (r1 == 0) goto L_0x01d9;
    L_0x0177:
        r1 = r4;
        goto L_0x006e;
    L_0x017a:
        r1 = r2 instanceof java.io.FileNotFoundException;
        if (r1 == 0) goto L_0x01d9;
    L_0x017e:
        r1 = r4;
        goto L_0x006e;
    L_0x0181:
        r1 = move-exception;
        org.telegram.messenger.FileLog.e(r1);
        goto L_0x008e;
    L_0x0187:
        r8 = r3.read(r7);	 Catch:{ Exception -> 0x01ca }
        if (r8 <= 0) goto L_0x01a3;
    L_0x018d:
        if (r1 != 0) goto L_0x01d1;
    L_0x018f:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01ca }
        r2.<init>();	 Catch:{ Exception -> 0x01ca }
    L_0x0194:
        r1 = new java.lang.String;	 Catch:{ Exception -> 0x01a9, Throwable -> 0x01c8 }
        r9 = 0;
        r10 = "UTF-8";
        r1.<init>(r7, r9, r8, r10);	 Catch:{ Exception -> 0x01a9, Throwable -> 0x01c8 }
        r2.append(r1);	 Catch:{ Exception -> 0x01a9, Throwable -> 0x01c8 }
        r1 = r2;
        goto L_0x0096;
    L_0x01a3:
        r2 = -1;
        if (r8 != r2) goto L_0x009c;
    L_0x01a6:
        r4 = r5;
        goto L_0x009c;
    L_0x01a9:
        r1 = move-exception;
        r11 = r1;
        r1 = r2;
        r2 = r11;
    L_0x01ad:
        org.telegram.messenger.FileLog.e(r2);	 Catch:{ Throwable -> 0x01b2 }
        goto L_0x009c;
    L_0x01b2:
        r2 = move-exception;
        r11 = r2;
        r2 = r1;
        r1 = r11;
    L_0x01b6:
        org.telegram.messenger.FileLog.e(r1);
        r1 = r2;
        goto L_0x009c;
    L_0x01bc:
        r2 = move-exception;
        org.telegram.messenger.FileLog.e(r2);
        goto L_0x00a1;
    L_0x01c2:
        r1 = r6;
        goto L_0x00a7;
    L_0x01c5:
        r1 = move-exception;
        r2 = r6;
        goto L_0x01b6;
    L_0x01c8:
        r1 = move-exception;
        goto L_0x01b6;
    L_0x01ca:
        r2 = move-exception;
        goto L_0x01ad;
    L_0x01cc:
        r1 = move-exception;
        r2 = r1;
        r3 = r6;
        goto L_0x0063;
    L_0x01d1:
        r2 = r1;
        goto L_0x0194;
    L_0x01d3:
        r1 = r6;
        goto L_0x009c;
    L_0x01d6:
        r1 = r6;
        goto L_0x00a1;
    L_0x01d9:
        r1 = r5;
        goto L_0x006e;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.WebPlayerView.downloadUrlContent(android.os.AsyncTask, java.lang.String, java.util.HashMap, boolean):java.lang.String");
    }

    public void enterFullscreen() {
        if (!this.inFullscreen) {
            this.inFullscreen = true;
            updateInlineButton();
            updateFullscreenState(false);
        }
    }

    public void exitFullscreen() {
        if (this.inFullscreen) {
            this.inFullscreen = false;
            updateInlineButton();
            updateFullscreenState(false);
        }
    }

    public View getAspectRatioView() {
        return this.aspectRatioFrameLayout;
    }

    public View getControlsView() {
        return this.controlsView;
    }

    public ImageView getTextureImageView() {
        return this.textureImageView;
    }

    public TextureView getTextureView() {
        return this.textureView;
    }

    public String getYoutubeId() {
        return this.currentYoutubeId;
    }

    public boolean isInFullscreen() {
        return this.inFullscreen;
    }

    public boolean isInitied() {
        return this.initied;
    }

    public boolean isInline() {
        return this.isInline || this.switchingInlineMode;
    }

    public boolean loadVideo(String str, Photo photo, String str2, boolean z) {
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10 = null;
        String str11 = null;
        String str12 = null;
        String str13 = null;
        String str14 = null;
        this.seekToTime = -1;
        if (str != null) {
            if (str.endsWith(".mp4")) {
                str3 = null;
                str4 = str;
                str5 = null;
                str6 = null;
                str7 = null;
                str8 = null;
                str9 = null;
            } else {
                String queryParameter;
                Matcher matcher;
                if (str2 != null) {
                    try {
                        queryParameter = Uri.parse(str2).getQueryParameter("t");
                        if (queryParameter != null) {
                            if (queryParameter.contains("m")) {
                                String[] split = queryParameter.split("m");
                                this.seekToTime = Utilities.parseInt(split[1]).intValue() + (Utilities.parseInt(split[0]).intValue() * 60);
                            } else {
                                this.seekToTime = Utilities.parseInt(queryParameter).intValue();
                            }
                        }
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                }
                try {
                    matcher = youtubeIdRegex.matcher(str);
                    queryParameter = null;
                    if (matcher.find()) {
                        queryParameter = matcher.group(1);
                    }
                    if (queryParameter == null) {
                        queryParameter = null;
                    }
                    str10 = queryParameter;
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
                if (str10 == null) {
                    try {
                        matcher = vimeoIdRegex.matcher(str);
                        queryParameter = null;
                        if (matcher.find()) {
                            queryParameter = matcher.group(3);
                        }
                        if (queryParameter == null) {
                            queryParameter = null;
                        }
                        str11 = queryParameter;
                    } catch (Throwable e22) {
                        FileLog.e(e22);
                    }
                }
                if (str11 == null) {
                    try {
                        matcher = aparatIdRegex.matcher(str);
                        queryParameter = null;
                        if (matcher.find()) {
                            queryParameter = matcher.group(1);
                        }
                        if (queryParameter == null) {
                            queryParameter = null;
                        }
                        str14 = queryParameter;
                    } catch (Throwable e222) {
                        FileLog.e(e222);
                    }
                }
                if (str14 == null) {
                    try {
                        matcher = twitchClipIdRegex.matcher(str);
                        queryParameter = null;
                        if (matcher.find()) {
                            queryParameter = matcher.group(1);
                        }
                        if (queryParameter == null) {
                            queryParameter = null;
                        }
                        str12 = queryParameter;
                    } catch (Throwable e2222) {
                        FileLog.e(e2222);
                    }
                }
                if (str12 == null) {
                    try {
                        matcher = twitchStreamIdRegex.matcher(str);
                        queryParameter = null;
                        if (matcher.find()) {
                            queryParameter = matcher.group(1);
                        }
                        if (queryParameter == null) {
                            queryParameter = null;
                        }
                        str13 = queryParameter;
                    } catch (Throwable e22222) {
                        FileLog.e(e22222);
                    }
                }
                if (str13 == null) {
                    try {
                        matcher = coubIdRegex.matcher(str);
                        queryParameter = null;
                        if (matcher.find()) {
                            queryParameter = matcher.group(1);
                        }
                        if (queryParameter == null) {
                            queryParameter = null;
                        }
                        str3 = str14;
                        str4 = null;
                        str5 = str13;
                        str6 = str12;
                        str7 = queryParameter;
                        str8 = str11;
                        str9 = str10;
                    } catch (Throwable e222222) {
                        FileLog.e(e222222);
                    }
                }
                str3 = str14;
                str4 = null;
                str5 = str13;
                str6 = str12;
                str7 = null;
                str8 = str11;
                str9 = str10;
            }
        } else {
            str3 = null;
            str4 = null;
            str5 = null;
            str6 = null;
            str7 = null;
            str8 = null;
            str9 = null;
        }
        this.initied = false;
        this.isCompleted = false;
        this.isAutoplay = z;
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
        if (photo != null) {
            PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(photo.sizes, 80, true);
            if (closestPhotoSizeWithSize != null) {
                this.controlsView.imageReceiver.setImage(null, null, photo != null ? closestPhotoSizeWithSize.location : null, photo != null ? "80_80_b" : null, 0, null, 1);
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
        if (!(str9 == null || BuildVars.DEBUG_PRIVATE_VERSION)) {
            this.currentYoutubeId = str9;
            str9 = null;
        }
        if (str4 != null) {
            this.initied = true;
            this.playVideoUrl = str4;
            this.playVideoType = "other";
            if (this.isAutoplay) {
                preparePlayer();
            }
            showProgress(false, false);
            this.controlsView.show(true, true);
        } else {
            AsyncTask youtubeVideoTask;
            if (str9 != null) {
                youtubeVideoTask = new YoutubeVideoTask(str9);
                youtubeVideoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                this.currentTask = youtubeVideoTask;
            } else if (str8 != null) {
                youtubeVideoTask = new VimeoVideoTask(str8);
                youtubeVideoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                this.currentTask = youtubeVideoTask;
            } else if (str7 != null) {
                youtubeVideoTask = new CoubVideoTask(str7);
                youtubeVideoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                this.currentTask = youtubeVideoTask;
                this.isStream = true;
            } else if (str3 != null) {
                youtubeVideoTask = new AparatVideoTask(str3);
                youtubeVideoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                this.currentTask = youtubeVideoTask;
            } else if (str6 != null) {
                youtubeVideoTask = new TwitchClipVideoTask(str, str6);
                youtubeVideoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                this.currentTask = youtubeVideoTask;
            } else if (str5 != null) {
                youtubeVideoTask = new TwitchStreamVideoTask(str, str5);
                youtubeVideoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                this.currentTask = youtubeVideoTask;
                this.isStream = true;
            }
            this.controlsView.show(false, false);
            showProgress(true, false);
        }
        if (str9 == null && str8 == null && str7 == null && str3 == null && str4 == null && str6 == null && str5 == null) {
            this.controlsView.setVisibility(8);
            return false;
        }
        this.controlsView.setVisibility(0);
        return true;
    }

    public void onAudioFocusChange(int i) {
        if (i == -1) {
            if (this.videoPlayer.isPlaying()) {
                this.videoPlayer.pause();
                updatePlayButton();
            }
            this.hasAudioFocus = false;
            this.audioFocus = 0;
        } else if (i == 1) {
            this.audioFocus = 2;
            if (this.resumeAudioOnFocusGain) {
                this.resumeAudioOnFocusGain = false;
                this.videoPlayer.play();
            }
        } else if (i == -3) {
            this.audioFocus = 1;
        } else if (i == -2) {
            this.audioFocus = 0;
            if (this.videoPlayer.isPlaying()) {
                this.resumeAudioOnFocusGain = true;
                this.videoPlayer.pause();
                updatePlayButton();
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getMeasuredWidth(), (float) (getMeasuredHeight() - AndroidUtilities.dp(10.0f)), this.backgroundPaint);
    }

    public void onError(Exception exception) {
        FileLog.e(exception);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = ((i3 - i) - this.aspectRatioFrameLayout.getMeasuredWidth()) / 2;
        int dp = (((i4 - i2) - AndroidUtilities.dp(10.0f)) - this.aspectRatioFrameLayout.getMeasuredHeight()) / 2;
        this.aspectRatioFrameLayout.layout(measuredWidth, dp, this.aspectRatioFrameLayout.getMeasuredWidth() + measuredWidth, this.aspectRatioFrameLayout.getMeasuredHeight() + dp);
        if (this.controlsView.getParent() == this) {
            this.controlsView.layout(0, 0, this.controlsView.getMeasuredWidth(), this.controlsView.getMeasuredHeight());
        }
        measuredWidth = ((i3 - i) - this.progressView.getMeasuredWidth()) / 2;
        dp = ((i4 - i2) - this.progressView.getMeasuredHeight()) / 2;
        this.progressView.layout(measuredWidth, dp, this.progressView.getMeasuredWidth() + measuredWidth, this.progressView.getMeasuredHeight() + dp);
        this.controlsView.imageReceiver.setImageCoords(0, 0, getMeasuredWidth(), getMeasuredHeight() - AndroidUtilities.dp(10.0f));
    }

    protected void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        this.aspectRatioFrameLayout.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(size2 - AndroidUtilities.dp(10.0f), 1073741824));
        if (this.controlsView.getParent() == this) {
            this.controlsView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(size2, 1073741824));
        }
        this.progressView.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(44.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(44.0f), 1073741824));
        setMeasuredDimension(size, size2);
    }

    public void onRenderedFirstFrame() {
        this.firstFrameRendered = true;
        this.lastUpdateTime = System.currentTimeMillis();
        this.controlsView.invalidate();
    }

    public void onStateChanged(boolean z, int i) {
        if (i != 2) {
            if (this.videoPlayer.getDuration() != C3446C.TIME_UNSET) {
                this.controlsView.setDuration((int) (this.videoPlayer.getDuration() / 1000));
            } else {
                this.controlsView.setDuration(0);
            }
        }
        if (i == 4 || i == 1 || !this.videoPlayer.isPlaying()) {
            this.delegate.onPlayStateChanged(this, false);
        } else {
            this.delegate.onPlayStateChanged(this, true);
        }
        if (this.videoPlayer.isPlaying() && i != 4) {
            updatePlayButton();
        } else if (i == 4) {
            this.isCompleted = true;
            this.videoPlayer.pause();
            this.videoPlayer.seekTo(0);
            updatePlayButton();
            this.controlsView.show(true, true);
        }
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

    public void onVideoSizeChanged(int i, int i2, int i3, float f) {
        if (this.aspectRatioFrameLayout != null) {
            if (i3 == 90 || i3 == 270) {
                int i4 = i;
                i = i2;
                i2 = i4;
            }
            float f2 = i2 == 0 ? 1.0f : (((float) i) * f) / ((float) i2);
            this.aspectRatioFrameLayout.setAspectRatio(f2, i3);
            if (this.inFullscreen) {
                this.delegate.onVideoSizeChanged(f2, i3);
            }
        }
    }

    public void pause() {
        this.videoPlayer.pause();
        updatePlayButton();
        this.controlsView.show(true, true);
    }

    public void updateTextureImageView() {
        if (this.textureImageView != null) {
            try {
                this.currentBitmap = Bitmaps.createBitmap(this.textureView.getWidth(), this.textureView.getHeight(), Config.ARGB_8888);
                this.changedTextureView.getBitmap(this.currentBitmap);
            } catch (Throwable th) {
                if (this.currentBitmap != null) {
                    this.currentBitmap.recycle();
                    this.currentBitmap = null;
                }
                FileLog.e(th);
            }
            if (this.currentBitmap != null) {
                this.textureImageView.setVisibility(0);
                this.textureImageView.setImageBitmap(this.currentBitmap);
                return;
            }
            this.textureImageView.setImageDrawable(null);
        }
    }
}
