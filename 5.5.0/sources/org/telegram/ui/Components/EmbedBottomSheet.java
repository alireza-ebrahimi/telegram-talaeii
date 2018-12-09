package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.TextureView;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.HashMap;
import java.util.Map;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BringAppForegroundService;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.BottomSheet.BottomSheetDelegate;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.WebPlayerView.WebPlayerViewDelegate;

public class EmbedBottomSheet extends BottomSheet {
    @SuppressLint({"StaticFieldLeak"})
    private static EmbedBottomSheet instance;
    private boolean animationInProgress;
    private FrameLayout containerLayout;
    private TextView copyTextButton;
    private View customView;
    private CustomViewCallback customViewCallback;
    private String embedUrl;
    private FrameLayout fullscreenVideoContainer;
    private boolean fullscreenedByButton;
    private boolean hasDescription;
    private int height;
    private LinearLayout imageButtonsContainer;
    private boolean isYouTube;
    private int lastOrientation;
    private OnShowListener onShowListener;
    private TextView openInButton;
    private String openUrl;
    private OrientationEventListener orientationEventListener;
    private Activity parentActivity;
    private ImageView pipButton;
    private PipVideoView pipVideoView;
    private int[] position;
    private int prevOrientation;
    private RadialProgressView progressBar;
    private View progressBarBlackBackground;
    private WebPlayerView videoView;
    private int waitingForDraw;
    private boolean wasInLandscape;
    private WebView webView;
    private int width;
    private final String youtubeFrame;
    private ImageView youtubeLogoImage;

    /* renamed from: org.telegram.ui.Components.EmbedBottomSheet$1 */
    class C43951 implements OnShowListener {

        /* renamed from: org.telegram.ui.Components.EmbedBottomSheet$1$1 */
        class C43931 implements OnPreDrawListener {
            C43931() {
            }

            public boolean onPreDraw() {
                EmbedBottomSheet.this.videoView.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        }

        C43951() {
        }

        public void onShow(DialogInterface dialogInterface) {
            if (EmbedBottomSheet.this.pipVideoView != null && EmbedBottomSheet.this.videoView.isInline()) {
                EmbedBottomSheet.this.videoView.getViewTreeObserver().addOnPreDrawListener(new C43931());
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.EmbedBottomSheet$2 */
    class C43962 implements OnTouchListener {
        C43962() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.Components.EmbedBottomSheet$4 */
    class C43984 implements OnTouchListener {
        C43984() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.Components.EmbedBottomSheet$6 */
    class C44006 extends WebChromeClient {
        C44006() {
        }

        public void onHideCustomView() {
            super.onHideCustomView();
            if (EmbedBottomSheet.this.customView != null) {
                EmbedBottomSheet.this.getSheetContainer().setVisibility(0);
                EmbedBottomSheet.this.fullscreenVideoContainer.setVisibility(4);
                EmbedBottomSheet.this.fullscreenVideoContainer.removeView(EmbedBottomSheet.this.customView);
                if (!(EmbedBottomSheet.this.customViewCallback == null || EmbedBottomSheet.this.customViewCallback.getClass().getName().contains(".chromium."))) {
                    EmbedBottomSheet.this.customViewCallback.onCustomViewHidden();
                }
                EmbedBottomSheet.this.customView = null;
            }
        }

        public void onShowCustomView(View view, int i, CustomViewCallback customViewCallback) {
            onShowCustomView(view, customViewCallback);
        }

        public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
            if (EmbedBottomSheet.this.customView == null && EmbedBottomSheet.this.pipVideoView == null) {
                EmbedBottomSheet.this.exitFromPip();
                EmbedBottomSheet.this.customView = view;
                EmbedBottomSheet.this.getSheetContainer().setVisibility(4);
                EmbedBottomSheet.this.fullscreenVideoContainer.setVisibility(0);
                EmbedBottomSheet.this.fullscreenVideoContainer.addView(view, LayoutHelper.createFrame(-1, -1.0f));
                EmbedBottomSheet.this.customViewCallback = customViewCallback;
                return;
            }
            customViewCallback.onCustomViewHidden();
        }
    }

    /* renamed from: org.telegram.ui.Components.EmbedBottomSheet$7 */
    class C44017 extends WebViewClient {
        C44017() {
        }

        public void onLoadResource(WebView webView, String str) {
            super.onLoadResource(webView, str);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (!EmbedBottomSheet.this.isYouTube || VERSION.SDK_INT < 17) {
                EmbedBottomSheet.this.progressBar.setVisibility(4);
                EmbedBottomSheet.this.progressBarBlackBackground.setVisibility(4);
                EmbedBottomSheet.this.pipButton.setEnabled(true);
                EmbedBottomSheet.this.pipButton.setAlpha(1.0f);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.EmbedBottomSheet$8 */
    class C44028 implements OnClickListener {
        C44028() {
        }

        public void onClick(View view) {
            if (EmbedBottomSheet.this.youtubeLogoImage.getAlpha() != BitmapDescriptorFactory.HUE_RED) {
                EmbedBottomSheet.this.openInButton.callOnClick();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.EmbedBottomSheet$9 */
    class C44059 implements WebPlayerViewDelegate {

        /* renamed from: org.telegram.ui.Components.EmbedBottomSheet$9$2 */
        class C44042 extends AnimatorListenerAdapter {
            C44042() {
            }

            public void onAnimationEnd(Animator animator) {
                EmbedBottomSheet.this.animationInProgress = false;
            }
        }

        C44059() {
        }

        public boolean checkInlinePermissons() {
            return EmbedBottomSheet.this.checkInlinePermissions();
        }

        public ViewGroup getTextureViewContainer() {
            return EmbedBottomSheet.this.container;
        }

        public void onInitFailed() {
            EmbedBottomSheet.this.webView.setVisibility(0);
            EmbedBottomSheet.this.imageButtonsContainer.setVisibility(0);
            EmbedBottomSheet.this.copyTextButton.setVisibility(4);
            EmbedBottomSheet.this.webView.setKeepScreenOn(true);
            EmbedBottomSheet.this.videoView.setVisibility(4);
            EmbedBottomSheet.this.videoView.getControlsView().setVisibility(4);
            EmbedBottomSheet.this.videoView.getTextureView().setVisibility(4);
            if (EmbedBottomSheet.this.videoView.getTextureImageView() != null) {
                EmbedBottomSheet.this.videoView.getTextureImageView().setVisibility(4);
            }
            EmbedBottomSheet.this.videoView.loadVideo(null, null, null, false);
            Map hashMap = new HashMap();
            hashMap.put("Referer", "http://youtube.com");
            try {
                EmbedBottomSheet.this.webView.loadUrl(EmbedBottomSheet.this.embedUrl, hashMap);
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }

        public void onInlineSurfaceTextureReady() {
            if (EmbedBottomSheet.this.videoView.isInline()) {
                EmbedBottomSheet.this.dismissInternal();
            }
        }

        public void onPlayStateChanged(WebPlayerView webPlayerView, boolean z) {
            if (z) {
                try {
                    EmbedBottomSheet.this.parentActivity.getWindow().addFlags(128);
                    return;
                } catch (Throwable e) {
                    FileLog.e(e);
                    return;
                }
            }
            try {
                EmbedBottomSheet.this.parentActivity.getWindow().clearFlags(128);
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
        }

        public void onSharePressed() {
        }

        public TextureView onSwitchInlineMode(View view, boolean z, float f, int i, boolean z2) {
            if (z) {
                view.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                EmbedBottomSheet.this.pipVideoView = new PipVideoView();
                return EmbedBottomSheet.this.pipVideoView.show(EmbedBottomSheet.this.parentActivity, EmbedBottomSheet.this, view, f, i, null);
            }
            if (z2) {
                EmbedBottomSheet.this.animationInProgress = true;
                EmbedBottomSheet.this.videoView.getAspectRatioView().getLocationInWindow(EmbedBottomSheet.this.position);
                int[] access$3900 = EmbedBottomSheet.this.position;
                access$3900[0] = access$3900[0] - EmbedBottomSheet.this.getLeftInset();
                access$3900 = EmbedBottomSheet.this.position;
                access$3900[1] = (int) (((float) access$3900[1]) - EmbedBottomSheet.this.containerView.getTranslationY());
                TextureView textureView = EmbedBottomSheet.this.videoView.getTextureView();
                ImageView textureImageView = EmbedBottomSheet.this.videoView.getTextureImageView();
                AnimatorSet animatorSet = new AnimatorSet();
                Animator[] animatorArr = new Animator[10];
                animatorArr[0] = ObjectAnimator.ofFloat(textureImageView, "scaleX", new float[]{1.0f});
                animatorArr[1] = ObjectAnimator.ofFloat(textureImageView, "scaleY", new float[]{1.0f});
                animatorArr[2] = ObjectAnimator.ofFloat(textureImageView, "translationX", new float[]{(float) EmbedBottomSheet.this.position[0]});
                animatorArr[3] = ObjectAnimator.ofFloat(textureImageView, "translationY", new float[]{(float) EmbedBottomSheet.this.position[1]});
                animatorArr[4] = ObjectAnimator.ofFloat(textureView, "scaleX", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(textureView, "scaleY", new float[]{1.0f});
                animatorArr[6] = ObjectAnimator.ofFloat(textureView, "translationX", new float[]{(float) EmbedBottomSheet.this.position[0]});
                animatorArr[7] = ObjectAnimator.ofFloat(textureView, "translationY", new float[]{(float) EmbedBottomSheet.this.position[1]});
                animatorArr[8] = ObjectAnimator.ofFloat(EmbedBottomSheet.this.containerView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[9] = ObjectAnimator.ofInt(EmbedBottomSheet.this.backDrawable, "alpha", new int[]{51});
                animatorSet.playTogether(animatorArr);
                animatorSet.setInterpolator(new DecelerateInterpolator());
                animatorSet.setDuration(250);
                animatorSet.addListener(new C44042());
                animatorSet.start();
            } else {
                EmbedBottomSheet.this.containerView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
            }
            return null;
        }

        public TextureView onSwitchToFullscreen(View view, boolean z, float f, int i, boolean z2) {
            if (z) {
                EmbedBottomSheet.this.fullscreenVideoContainer.setVisibility(0);
                EmbedBottomSheet.this.fullscreenVideoContainer.setAlpha(1.0f);
                EmbedBottomSheet.this.fullscreenVideoContainer.addView(EmbedBottomSheet.this.videoView.getAspectRatioView());
                EmbedBottomSheet.this.wasInLandscape = false;
                EmbedBottomSheet.this.fullscreenedByButton = z2;
                if (EmbedBottomSheet.this.parentActivity != null) {
                    try {
                        EmbedBottomSheet.this.prevOrientation = EmbedBottomSheet.this.parentActivity.getRequestedOrientation();
                        if (z2) {
                            if (((WindowManager) EmbedBottomSheet.this.parentActivity.getSystemService("window")).getDefaultDisplay().getRotation() == 3) {
                                EmbedBottomSheet.this.parentActivity.setRequestedOrientation(8);
                            } else {
                                EmbedBottomSheet.this.parentActivity.setRequestedOrientation(0);
                            }
                        }
                        EmbedBottomSheet.this.containerView.setSystemUiVisibility(1028);
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                }
            } else {
                EmbedBottomSheet.this.fullscreenVideoContainer.setVisibility(4);
                EmbedBottomSheet.this.fullscreenedByButton = false;
                if (EmbedBottomSheet.this.parentActivity != null) {
                    try {
                        EmbedBottomSheet.this.containerView.setSystemUiVisibility(0);
                        EmbedBottomSheet.this.parentActivity.setRequestedOrientation(EmbedBottomSheet.this.prevOrientation);
                    } catch (Throwable e2) {
                        FileLog.e(e2);
                    }
                }
            }
            return null;
        }

        public void onVideoSizeChanged(float f, int i) {
        }

        public void prepareToSwitchInlineMode(boolean z, final Runnable runnable, float f, boolean z2) {
            if (z) {
                if (EmbedBottomSheet.this.parentActivity != null) {
                    try {
                        EmbedBottomSheet.this.containerView.setSystemUiVisibility(0);
                        if (EmbedBottomSheet.this.prevOrientation != -2) {
                            EmbedBottomSheet.this.parentActivity.setRequestedOrientation(EmbedBottomSheet.this.prevOrientation);
                        }
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                }
                if (EmbedBottomSheet.this.fullscreenVideoContainer.getVisibility() == 0) {
                    EmbedBottomSheet.this.containerView.setTranslationY((float) (EmbedBottomSheet.this.containerView.getMeasuredHeight() + AndroidUtilities.dp(10.0f)));
                    EmbedBottomSheet.this.backDrawable.setAlpha(0);
                }
                EmbedBottomSheet.this.setOnShowListener(null);
                if (z2) {
                    TextureView textureView = EmbedBottomSheet.this.videoView.getTextureView();
                    View controlsView = EmbedBottomSheet.this.videoView.getControlsView();
                    ImageView textureImageView = EmbedBottomSheet.this.videoView.getTextureImageView();
                    Rect pipRect = PipVideoView.getPipRect(f);
                    float width = pipRect.width / ((float) textureView.getWidth());
                    if (VERSION.SDK_INT >= 21) {
                        pipRect.f10187y += (float) AndroidUtilities.statusBarHeight;
                    }
                    AnimatorSet animatorSet = new AnimatorSet();
                    r6 = new Animator[12];
                    r6[0] = ObjectAnimator.ofFloat(textureImageView, "scaleX", new float[]{width});
                    r6[1] = ObjectAnimator.ofFloat(textureImageView, "scaleY", new float[]{width});
                    r6[2] = ObjectAnimator.ofFloat(textureImageView, "translationX", new float[]{pipRect.f10186x});
                    r6[3] = ObjectAnimator.ofFloat(textureImageView, "translationY", new float[]{pipRect.f10187y});
                    r6[4] = ObjectAnimator.ofFloat(textureView, "scaleX", new float[]{width});
                    r6[5] = ObjectAnimator.ofFloat(textureView, "scaleY", new float[]{width});
                    r6[6] = ObjectAnimator.ofFloat(textureView, "translationX", new float[]{pipRect.f10186x});
                    r6[7] = ObjectAnimator.ofFloat(textureView, "translationY", new float[]{pipRect.f10187y});
                    r6[8] = ObjectAnimator.ofFloat(EmbedBottomSheet.this.containerView, "translationY", new float[]{(float) (EmbedBottomSheet.this.containerView.getMeasuredHeight() + AndroidUtilities.dp(10.0f))});
                    r6[9] = ObjectAnimator.ofInt(EmbedBottomSheet.this.backDrawable, "alpha", new int[]{0});
                    r6[10] = ObjectAnimator.ofFloat(EmbedBottomSheet.this.fullscreenVideoContainer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    r6[11] = ObjectAnimator.ofFloat(controlsView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorSet.playTogether(r6);
                    animatorSet.setInterpolator(new DecelerateInterpolator());
                    animatorSet.setDuration(250);
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            if (EmbedBottomSheet.this.fullscreenVideoContainer.getVisibility() == 0) {
                                EmbedBottomSheet.this.fullscreenVideoContainer.setAlpha(1.0f);
                                EmbedBottomSheet.this.fullscreenVideoContainer.setVisibility(4);
                            }
                            runnable.run();
                        }
                    });
                    animatorSet.start();
                    return;
                }
                if (EmbedBottomSheet.this.fullscreenVideoContainer.getVisibility() == 0) {
                    EmbedBottomSheet.this.fullscreenVideoContainer.setAlpha(1.0f);
                    EmbedBottomSheet.this.fullscreenVideoContainer.setVisibility(4);
                }
                runnable.run();
                EmbedBottomSheet.this.dismissInternal();
                return;
            }
            if (ApplicationLoader.mainInterfacePaused) {
                EmbedBottomSheet.this.parentActivity.startService(new Intent(ApplicationLoader.applicationContext, BringAppForegroundService.class));
            }
            if (z2) {
                EmbedBottomSheet.this.setOnShowListener(EmbedBottomSheet.this.onShowListener);
                Rect pipRect2 = PipVideoView.getPipRect(f);
                TextureView textureView2 = EmbedBottomSheet.this.videoView.getTextureView();
                textureImageView = EmbedBottomSheet.this.videoView.getTextureImageView();
                float f2 = pipRect2.width / ((float) textureView2.getLayoutParams().width);
                if (VERSION.SDK_INT >= 21) {
                    pipRect2.f10187y += (float) AndroidUtilities.statusBarHeight;
                }
                textureImageView.setScaleX(f2);
                textureImageView.setScaleY(f2);
                textureImageView.setTranslationX(pipRect2.f10186x);
                textureImageView.setTranslationY(pipRect2.f10187y);
                textureView2.setScaleX(f2);
                textureView2.setScaleY(f2);
                textureView2.setTranslationX(pipRect2.f10186x);
                textureView2.setTranslationY(pipRect2.f10187y);
            } else {
                EmbedBottomSheet.this.pipVideoView.close();
                EmbedBottomSheet.this.pipVideoView = null;
            }
            EmbedBottomSheet.this.setShowWithoutAnimation(true);
            EmbedBottomSheet.this.show();
            if (z2) {
                EmbedBottomSheet.this.waitingForDraw = 4;
                EmbedBottomSheet.this.backDrawable.setAlpha(1);
                EmbedBottomSheet.this.containerView.setTranslationY((float) (EmbedBottomSheet.this.containerView.getMeasuredHeight() + AndroidUtilities.dp(10.0f)));
            }
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private EmbedBottomSheet(Context context, String str, String str2, String str3, String str4, int i, int i2) {
        View textView;
        super(context, false);
        this.position = new int[2];
        this.lastOrientation = -1;
        this.prevOrientation = -2;
        this.youtubeFrame = "<!DOCTYPE html><html><head><style>body { margin: 0; width:100%%; height:100%%;  background-color:#000; }html { width:100%%; height:100%%; background-color:#000; }.embed-container iframe,.embed-container object,   .embed-container embed {       position: absolute;       top: 0;       left: 0;       width: 100%% !important;       height: 100%% !important;   }   </style></head><body>   <div class=\"embed-container\">       <div id=\"player\"></div>   </div>   <script src=\"https://www.youtube.com/iframe_api\"></script>   <script>   var player;   var observer;   var videoEl;   var playing;   var posted = false;   YT.ready(function() {       player = new YT.Player(\"player\", {                              \"width\" : \"100%%\",                              \"events\" : {                              \"onReady\" : \"onReady\",                              \"onError\" : \"onError\",                              },                              \"videoId\" : \"%1$s\",                              \"height\" : \"100%%\",                              \"playerVars\" : {                              \"start\" : %2$d,                              \"rel\" : 0,                              \"showinfo\" : 0,                              \"modestbranding\" : 1,                              \"iv_load_policy\" : 3,                              \"autohide\" : 1,                              \"autoplay\" : 1,                              \"cc_load_policy\" : 1,                              \"playsinline\" : 1,                              \"controls\" : 1                              }                            });        player.setSize(window.innerWidth, window.innerHeight);    });    function hideControls() {        playing = !videoEl.paused;       videoEl.controls = 0;       observer.observe(videoEl, {attributes: true});    }    function showControls() {        playing = !videoEl.paused;       observer.disconnect();       videoEl.controls = 1;    }    function onError(event) {       if (!posted) {            if (window.YoutubeProxy !== undefined) {                   YoutubeProxy.postEvent(\"loaded\", null);             }            posted = true;       }    }    function onReady(event) {       player.playVideo();       videoEl = player.getIframe().contentDocument.getElementsByTagName('video')[0];\n       videoEl.addEventListener(\"canplay\", function() {            if (playing) {               videoEl.play();            }       }, true);       videoEl.addEventListener(\"timeupdate\", function() {            if (!posted && videoEl.currentTime > 0) {               if (window.YoutubeProxy !== undefined) {                   YoutubeProxy.postEvent(\"loaded\", null);                }               posted = true;           }       }, true);       observer = new MutationObserver(function() {\n          if (videoEl.controls) {\n               videoEl.controls = 0;\n          }       });\n    }    window.onresize = function() {        player.setSize(window.innerWidth, window.innerHeight);    }    </script></body></html>";
        this.onShowListener = new C43951();
        this.fullWidth = true;
        setApplyTopPadding(false);
        setApplyBottomPadding(false);
        if (context instanceof Activity) {
            this.parentActivity = (Activity) context;
        }
        this.embedUrl = str4;
        boolean z = str2 != null && str2.length() > 0;
        this.hasDescription = z;
        this.openUrl = str3;
        this.width = i;
        this.height = i2;
        if (this.width == 0 || this.height == 0) {
            this.width = AndroidUtilities.displaySize.x;
            this.height = AndroidUtilities.displaySize.y / 2;
        }
        this.fullscreenVideoContainer = new FrameLayout(context);
        this.fullscreenVideoContainer.setBackgroundColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        if (VERSION.SDK_INT >= 21) {
            this.fullscreenVideoContainer.setFitsSystemWindows(true);
        }
        this.container.addView(this.fullscreenVideoContainer, LayoutHelper.createFrame(-1, -1.0f));
        this.fullscreenVideoContainer.setVisibility(4);
        this.fullscreenVideoContainer.setOnTouchListener(new C43962());
        this.containerLayout = new FrameLayout(context) {
            protected void onDetachedFromWindow() {
                super.onDetachedFromWindow();
                try {
                    if ((EmbedBottomSheet.this.pipVideoView == null || EmbedBottomSheet.this.webView.getVisibility() != 0) && EmbedBottomSheet.this.webView.getParent() != null) {
                        removeView(EmbedBottomSheet.this.webView);
                        EmbedBottomSheet.this.webView.stopLoading();
                        EmbedBottomSheet.this.webView.loadUrl("about:blank");
                        EmbedBottomSheet.this.webView.destroy();
                    }
                    if (!EmbedBottomSheet.this.videoView.isInline() && EmbedBottomSheet.this.pipVideoView == null) {
                        if (EmbedBottomSheet.instance == EmbedBottomSheet.this) {
                            EmbedBottomSheet.instance = null;
                        }
                        EmbedBottomSheet.this.videoView.destroy();
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }

            protected void onMeasure(int i, int i2) {
                super.onMeasure(i, MeasureSpec.makeMeasureSpec((AndroidUtilities.dp((float) ((EmbedBottomSheet.this.hasDescription ? 22 : 0) + 84)) + ((int) Math.min(((float) EmbedBottomSheet.this.height) / (((float) EmbedBottomSheet.this.width) / ((float) MeasureSpec.getSize(i))), (float) (AndroidUtilities.displaySize.y / 2)))) + 1, 1073741824));
            }
        };
        this.containerLayout.setOnTouchListener(new C43984());
        setCustomView(this.containerLayout);
        this.webView = new WebView(context) {
            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (EmbedBottomSheet.this.isYouTube && motionEvent.getAction() == 0) {
                    EmbedBottomSheet.this.showOrHideYoutubeLogo(true);
                }
                return super.onTouchEvent(motionEvent);
            }
        };
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setDomStorageEnabled(true);
        if (VERSION.SDK_INT >= 17) {
            this.webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
        if (VERSION.SDK_INT >= 21) {
            this.webView.getSettings().setMixedContentMode(0);
            CookieManager.getInstance().setAcceptThirdPartyCookies(this.webView, true);
        }
        this.webView.setWebChromeClient(new C44006());
        this.webView.setWebViewClient(new C44017());
        this.containerLayout.addView(this.webView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) ((this.hasDescription ? 22 : 0) + 84)));
        this.youtubeLogoImage = new ImageView(context);
        this.youtubeLogoImage.setVisibility(8);
        this.containerLayout.addView(this.youtubeLogoImage, LayoutHelper.createFrame(66, 28.0f, 53, BitmapDescriptorFactory.HUE_RED, 8.0f, 8.0f, BitmapDescriptorFactory.HUE_RED));
        this.youtubeLogoImage.setOnClickListener(new C44028());
        this.videoView = new WebPlayerView(context, true, false, new C44059());
        this.videoView.setVisibility(4);
        this.containerLayout.addView(this.videoView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) (((this.hasDescription ? 22 : 0) + 84) - 10)));
        this.progressBarBlackBackground = new View(context);
        this.progressBarBlackBackground.setBackgroundColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        this.progressBarBlackBackground.setVisibility(4);
        this.containerLayout.addView(this.progressBarBlackBackground, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) ((this.hasDescription ? 22 : 0) + 84)));
        this.progressBar = new RadialProgressView(context);
        this.progressBar.setVisibility(4);
        this.containerLayout.addView(this.progressBar, LayoutHelper.createFrame(-2, -2.0f, 17, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) (((this.hasDescription ? 22 : 0) + 84) / 2)));
        if (this.hasDescription) {
            textView = new TextView(context);
            textView.setTextSize(1, 16.0f);
            textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
            textView.setText(str2);
            textView.setSingleLine(true);
            textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            textView.setEllipsize(TruncateAt.END);
            textView.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
            this.containerLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 77.0f));
        }
        textView = new TextView(context);
        textView.setTextSize(1, 14.0f);
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextGray));
        textView.setText(str);
        textView.setSingleLine(true);
        textView.setEllipsize(TruncateAt.END);
        textView.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
        this.containerLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 57.0f));
        View view = new View(context);
        view.setBackgroundColor(Theme.getColor(Theme.key_dialogGrayLine));
        this.containerLayout.addView(view, new LayoutParams(-1, 1, 83));
        ((LayoutParams) view.getLayoutParams()).bottomMargin = AndroidUtilities.dp(48.0f);
        view = new FrameLayout(context);
        view.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
        this.containerLayout.addView(view, LayoutHelper.createFrame(-1, 48, 83));
        textView = new LinearLayout(context);
        textView.setOrientation(0);
        textView.setWeightSum(1.0f);
        view.addView(textView, LayoutHelper.createFrame(-2, -1, 53));
        View textView2 = new TextView(context);
        textView2.setTextSize(1, 14.0f);
        textView2.setTextColor(Theme.getColor(Theme.key_dialogTextBlue4));
        textView2.setGravity(17);
        textView2.setSingleLine(true);
        textView2.setEllipsize(TruncateAt.END);
        textView2.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 0));
        textView2.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
        textView2.setText(LocaleController.getString("Close", R.string.Close).toUpperCase());
        textView2.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        view.addView(textView2, LayoutHelper.createLinear(-2, -1, 51));
        textView2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                EmbedBottomSheet.this.dismiss();
            }
        });
        this.imageButtonsContainer = new LinearLayout(context);
        this.imageButtonsContainer.setVisibility(4);
        view.addView(this.imageButtonsContainer, LayoutHelper.createFrame(-2, -1, 17));
        this.pipButton = new ImageView(context);
        this.pipButton.setScaleType(ScaleType.CENTER);
        this.pipButton.setImageResource(R.drawable.video_pip);
        this.pipButton.setEnabled(false);
        this.pipButton.setAlpha(0.5f);
        this.pipButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogTextBlue4), Mode.MULTIPLY));
        this.pipButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 0));
        this.imageButtonsContainer.addView(this.pipButton, LayoutHelper.createFrame(48, 48.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 4.0f, BitmapDescriptorFactory.HUE_RED));
        this.pipButton.setOnClickListener(new OnClickListener() {

            /* renamed from: org.telegram.ui.Components.EmbedBottomSheet$11$1 */
            class C43941 extends AnimatorListenerAdapter {
                C43941() {
                }

                public void onAnimationEnd(Animator animator) {
                    EmbedBottomSheet.this.animationInProgress = false;
                }
            }

            public void onClick(View view) {
                if (EmbedBottomSheet.this.checkInlinePermissions() && EmbedBottomSheet.this.progressBar.getVisibility() != 0) {
                    EmbedBottomSheet.this.pipVideoView = new PipVideoView();
                    PipVideoView access$400 = EmbedBottomSheet.this.pipVideoView;
                    Activity access$2200 = EmbedBottomSheet.this.parentActivity;
                    EmbedBottomSheet embedBottomSheet = EmbedBottomSheet.this;
                    float access$800 = (EmbedBottomSheet.this.width == 0 || EmbedBottomSheet.this.height == 0) ? 1.0f : ((float) EmbedBottomSheet.this.width) / ((float) EmbedBottomSheet.this.height);
                    access$400.show(access$2200, embedBottomSheet, null, access$800, 0, EmbedBottomSheet.this.webView);
                    if (EmbedBottomSheet.this.isYouTube) {
                        EmbedBottomSheet.this.runJsCode("hideControls();");
                    }
                    EmbedBottomSheet.this.containerView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                    EmbedBottomSheet.this.dismissInternal();
                }
            }
        });
        OnClickListener anonymousClass12 = new OnClickListener() {
            public void onClick(View view) {
                try {
                    ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", EmbedBottomSheet.this.openUrl));
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                Toast.makeText(EmbedBottomSheet.this.getContext(), LocaleController.getString("LinkCopied", R.string.LinkCopied), 0).show();
                EmbedBottomSheet.this.dismiss();
            }
        };
        textView2 = new ImageView(context);
        textView2.setScaleType(ScaleType.CENTER);
        textView2.setImageResource(R.drawable.video_copy);
        textView2.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogTextBlue4), Mode.MULTIPLY));
        textView2.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 0));
        this.imageButtonsContainer.addView(textView2, LayoutHelper.createFrame(48, 48, 51));
        textView2.setOnClickListener(anonymousClass12);
        this.copyTextButton = new TextView(context);
        this.copyTextButton.setTextSize(1, 14.0f);
        this.copyTextButton.setTextColor(Theme.getColor(Theme.key_dialogTextBlue4));
        this.copyTextButton.setGravity(17);
        this.copyTextButton.setSingleLine(true);
        this.copyTextButton.setEllipsize(TruncateAt.END);
        this.copyTextButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 0));
        this.copyTextButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
        this.copyTextButton.setText(LocaleController.getString("Copy", R.string.Copy).toUpperCase());
        this.copyTextButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textView.addView(this.copyTextButton, LayoutHelper.createFrame(-2, -1, 51));
        this.copyTextButton.setOnClickListener(anonymousClass12);
        this.openInButton = new TextView(context);
        this.openInButton.setTextSize(1, 14.0f);
        this.openInButton.setTextColor(Theme.getColor(Theme.key_dialogTextBlue4));
        this.openInButton.setGravity(17);
        this.openInButton.setSingleLine(true);
        this.openInButton.setEllipsize(TruncateAt.END);
        this.openInButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 0));
        this.openInButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
        this.openInButton.setText(LocaleController.getString("OpenInBrowser", R.string.OpenInBrowser).toUpperCase());
        this.openInButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textView.addView(this.openInButton, LayoutHelper.createFrame(-2, -1, 51));
        this.openInButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Browser.openUrl(EmbedBottomSheet.this.parentActivity, EmbedBottomSheet.this.openUrl);
                EmbedBottomSheet.this.dismiss();
            }
        });
        setDelegate(new BottomSheetDelegate() {
            public boolean canDismiss() {
                if (EmbedBottomSheet.this.videoView.isInFullscreen()) {
                    EmbedBottomSheet.this.videoView.exitFullscreen();
                    return false;
                }
                try {
                    EmbedBottomSheet.this.parentActivity.getWindow().clearFlags(128);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                return true;
            }

            public void onOpenAnimationEnd() {
                int i = 0;
                if (EmbedBottomSheet.this.videoView.loadVideo(EmbedBottomSheet.this.embedUrl, null, EmbedBottomSheet.this.openUrl, true)) {
                    EmbedBottomSheet.this.progressBar.setVisibility(4);
                    EmbedBottomSheet.this.webView.setVisibility(4);
                    EmbedBottomSheet.this.videoView.setVisibility(i);
                    return;
                }
                EmbedBottomSheet.this.progressBar.setVisibility(i);
                EmbedBottomSheet.this.webView.setVisibility(i);
                EmbedBottomSheet.this.imageButtonsContainer.setVisibility(i);
                EmbedBottomSheet.this.copyTextButton.setVisibility(4);
                EmbedBottomSheet.this.webView.setKeepScreenOn(true);
                EmbedBottomSheet.this.videoView.setVisibility(4);
                EmbedBottomSheet.this.videoView.getControlsView().setVisibility(4);
                EmbedBottomSheet.this.videoView.getTextureView().setVisibility(4);
                if (EmbedBottomSheet.this.videoView.getTextureImageView() != null) {
                    EmbedBottomSheet.this.videoView.getTextureImageView().setVisibility(4);
                }
                EmbedBottomSheet.this.videoView.loadVideo(null, null, null, i);
                Map hashMap = new HashMap();
                hashMap.put("Referer", "http://youtube.com");
                try {
                    if (EmbedBottomSheet.this.videoView.getYoutubeId() != null) {
                        int i2;
                        EmbedBottomSheet.this.progressBarBlackBackground.setVisibility(0);
                        EmbedBottomSheet.this.youtubeLogoImage.setVisibility(0);
                        EmbedBottomSheet.this.youtubeLogoImage.setImageResource(R.drawable.ytlogo);
                        EmbedBottomSheet.this.isYouTube = true;
                        if (VERSION.SDK_INT >= 17) {
                            EmbedBottomSheet.this.webView.addJavascriptInterface(new EmbedBottomSheet$YoutubeProxy(EmbedBottomSheet.this, null), "YoutubeProxy");
                        }
                        if (EmbedBottomSheet.this.openUrl != null) {
                            try {
                                String queryParameter = Uri.parse(EmbedBottomSheet.this.openUrl).getQueryParameter("t");
                                if (queryParameter != null) {
                                    if (queryParameter.contains("m")) {
                                        String[] split = queryParameter.split("m");
                                        i = Utilities.parseInt(split[1]).intValue() + (Utilities.parseInt(split[0]).intValue() * 60);
                                    } else {
                                        i = Utilities.parseInt(queryParameter).intValue();
                                    }
                                }
                                i2 = i;
                            } catch (Throwable e) {
                                FileLog.e(e);
                            }
                            EmbedBottomSheet.this.webView.loadDataWithBaseURL("https://www.youtube.com", String.format("<!DOCTYPE html><html><head><style>body { margin: 0; width:100%%; height:100%%;  background-color:#000; }html { width:100%%; height:100%%; background-color:#000; }.embed-container iframe,.embed-container object,   .embed-container embed {       position: absolute;       top: 0;       left: 0;       width: 100%% !important;       height: 100%% !important;   }   </style></head><body>   <div class=\"embed-container\">       <div id=\"player\"></div>   </div>   <script src=\"https://www.youtube.com/iframe_api\"></script>   <script>   var player;   var observer;   var videoEl;   var playing;   var posted = false;   YT.ready(function() {       player = new YT.Player(\"player\", {                              \"width\" : \"100%%\",                              \"events\" : {                              \"onReady\" : \"onReady\",                              \"onError\" : \"onError\",                              },                              \"videoId\" : \"%1$s\",                              \"height\" : \"100%%\",                              \"playerVars\" : {                              \"start\" : %2$d,                              \"rel\" : 0,                              \"showinfo\" : 0,                              \"modestbranding\" : 1,                              \"iv_load_policy\" : 3,                              \"autohide\" : 1,                              \"autoplay\" : 1,                              \"cc_load_policy\" : 1,                              \"playsinline\" : 1,                              \"controls\" : 1                              }                            });        player.setSize(window.innerWidth, window.innerHeight);    });    function hideControls() {        playing = !videoEl.paused;       videoEl.controls = 0;       observer.observe(videoEl, {attributes: true});    }    function showControls() {        playing = !videoEl.paused;       observer.disconnect();       videoEl.controls = 1;    }    function onError(event) {       if (!posted) {            if (window.YoutubeProxy !== undefined) {                   YoutubeProxy.postEvent(\"loaded\", null);             }            posted = true;       }    }    function onReady(event) {       player.playVideo();       videoEl = player.getIframe().contentDocument.getElementsByTagName('video')[0];\n       videoEl.addEventListener(\"canplay\", function() {            if (playing) {               videoEl.play();            }       }, true);       videoEl.addEventListener(\"timeupdate\", function() {            if (!posted && videoEl.currentTime > 0) {               if (window.YoutubeProxy !== undefined) {                   YoutubeProxy.postEvent(\"loaded\", null);                }               posted = true;           }       }, true);       observer = new MutationObserver(function() {\n          if (videoEl.controls) {\n               videoEl.controls = 0;\n          }       });\n    }    window.onresize = function() {        player.setSize(window.innerWidth, window.innerHeight);    }    </script></body></html>", new Object[]{r3, Integer.valueOf(i2)}), "text/html", C3446C.UTF8_NAME, "http://youtube.com");
                            return;
                        }
                        i2 = i;
                        EmbedBottomSheet.this.webView.loadDataWithBaseURL("https://www.youtube.com", String.format("<!DOCTYPE html><html><head><style>body { margin: 0; width:100%%; height:100%%;  background-color:#000; }html { width:100%%; height:100%%; background-color:#000; }.embed-container iframe,.embed-container object,   .embed-container embed {       position: absolute;       top: 0;       left: 0;       width: 100%% !important;       height: 100%% !important;   }   </style></head><body>   <div class=\"embed-container\">       <div id=\"player\"></div>   </div>   <script src=\"https://www.youtube.com/iframe_api\"></script>   <script>   var player;   var observer;   var videoEl;   var playing;   var posted = false;   YT.ready(function() {       player = new YT.Player(\"player\", {                              \"width\" : \"100%%\",                              \"events\" : {                              \"onReady\" : \"onReady\",                              \"onError\" : \"onError\",                              },                              \"videoId\" : \"%1$s\",                              \"height\" : \"100%%\",                              \"playerVars\" : {                              \"start\" : %2$d,                              \"rel\" : 0,                              \"showinfo\" : 0,                              \"modestbranding\" : 1,                              \"iv_load_policy\" : 3,                              \"autohide\" : 1,                              \"autoplay\" : 1,                              \"cc_load_policy\" : 1,                              \"playsinline\" : 1,                              \"controls\" : 1                              }                            });        player.setSize(window.innerWidth, window.innerHeight);    });    function hideControls() {        playing = !videoEl.paused;       videoEl.controls = 0;       observer.observe(videoEl, {attributes: true});    }    function showControls() {        playing = !videoEl.paused;       observer.disconnect();       videoEl.controls = 1;    }    function onError(event) {       if (!posted) {            if (window.YoutubeProxy !== undefined) {                   YoutubeProxy.postEvent(\"loaded\", null);             }            posted = true;       }    }    function onReady(event) {       player.playVideo();       videoEl = player.getIframe().contentDocument.getElementsByTagName('video')[0];\n       videoEl.addEventListener(\"canplay\", function() {            if (playing) {               videoEl.play();            }       }, true);       videoEl.addEventListener(\"timeupdate\", function() {            if (!posted && videoEl.currentTime > 0) {               if (window.YoutubeProxy !== undefined) {                   YoutubeProxy.postEvent(\"loaded\", null);                }               posted = true;           }       }, true);       observer = new MutationObserver(function() {\n          if (videoEl.controls) {\n               videoEl.controls = 0;\n          }       });\n    }    window.onresize = function() {        player.setSize(window.innerWidth, window.innerHeight);    }    </script></body></html>", new Object[]{r3, Integer.valueOf(i2)}), "text/html", C3446C.UTF8_NAME, "http://youtube.com");
                        return;
                    }
                    EmbedBottomSheet.this.webView.loadUrl(EmbedBottomSheet.this.embedUrl, hashMap);
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
            }
        });
        this.orientationEventListener = new OrientationEventListener(ApplicationLoader.applicationContext) {
            public void onOrientationChanged(int i) {
                if (EmbedBottomSheet.this.orientationEventListener == null || EmbedBottomSheet.this.videoView.getVisibility() != 0 || EmbedBottomSheet.this.parentActivity == null || !EmbedBottomSheet.this.videoView.isInFullscreen() || !EmbedBottomSheet.this.fullscreenedByButton) {
                    return;
                }
                if (i >= PsExtractor.VIDEO_STREAM_MASK && i <= 300) {
                    EmbedBottomSheet.this.wasInLandscape = true;
                } else if (!EmbedBottomSheet.this.wasInLandscape) {
                } else {
                    if (i >= 330 || i <= 30) {
                        EmbedBottomSheet.this.parentActivity.setRequestedOrientation(EmbedBottomSheet.this.prevOrientation);
                        EmbedBottomSheet.this.fullscreenedByButton = false;
                        EmbedBottomSheet.this.wasInLandscape = false;
                    }
                }
            }
        };
        if (this.orientationEventListener.canDetectOrientation()) {
            this.orientationEventListener.enable();
        } else {
            this.orientationEventListener.disable();
            this.orientationEventListener = null;
        }
        instance = this;
    }

    public static EmbedBottomSheet getInstance() {
        return instance;
    }

    private void runJsCode(String str) {
        if (VERSION.SDK_INT >= 21) {
            this.webView.evaluateJavascript(str, null);
            return;
        }
        try {
            this.webView.loadUrl("javascript:" + str);
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    public static void show(Context context, String str, String str2, String str3, String str4, int i, int i2) {
        if (instance != null) {
            instance.destroy();
        }
        new EmbedBottomSheet(context, str, str2, str3, str4, i, i2).show();
    }

    private void showOrHideYoutubeLogo(final boolean z) {
        this.youtubeLogoImage.animate().alpha(z ? 1.0f : BitmapDescriptorFactory.HUE_RED).setDuration(200).setStartDelay(z ? 0 : 2900).setInterpolator(new DecelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                if (z) {
                    EmbedBottomSheet.this.showOrHideYoutubeLogo(false);
                }
            }
        }).start();
    }

    protected boolean canDismissWithSwipe() {
        return (this.videoView.getVisibility() == 0 && this.videoView.isInFullscreen()) ? false : true;
    }

    public boolean checkInlinePermissions() {
        if (this.parentActivity == null) {
            return false;
        }
        if (VERSION.SDK_INT < 23 || Settings.canDrawOverlays(this.parentActivity)) {
            return true;
        }
        new Builder(this.parentActivity).setTitle(LocaleController.getString("AppName", R.string.AppName)).setMessage(LocaleController.getString("PermissionDrawAboveOtherApps", R.string.PermissionDrawAboveOtherApps)).setPositiveButton(LocaleController.getString("PermissionOpenSettings", R.string.PermissionOpenSettings), new DialogInterface.OnClickListener() {
            @TargetApi(23)
            public void onClick(DialogInterface dialogInterface, int i) {
                if (EmbedBottomSheet.this.parentActivity != null) {
                    EmbedBottomSheet.this.parentActivity.startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + EmbedBottomSheet.this.parentActivity.getPackageName())));
                }
            }
        }).show();
        return false;
    }

    public void destroy() {
        if (this.webView != null && this.webView.getVisibility() == 0) {
            this.containerLayout.removeView(this.webView);
            this.webView.stopLoading();
            this.webView.loadUrl("about:blank");
            this.webView.destroy();
        }
        if (this.pipVideoView != null) {
            this.pipVideoView.close();
            this.pipVideoView = null;
        }
        if (this.videoView != null) {
            this.videoView.destroy();
        }
        instance = null;
        dismissInternal();
    }

    public void exitFromPip() {
        if (this.webView != null && this.pipVideoView != null) {
            if (ApplicationLoader.mainInterfacePaused) {
                this.parentActivity.startService(new Intent(ApplicationLoader.applicationContext, BringAppForegroundService.class));
            }
            if (this.isYouTube) {
                runJsCode("showControls();");
            }
            ViewGroup viewGroup = (ViewGroup) this.webView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.webView);
            }
            this.containerLayout.addView(this.webView, 0, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) ((this.hasDescription ? 22 : 0) + 84)));
            setShowWithoutAnimation(true);
            show();
            this.pipVideoView.close();
            this.pipVideoView = null;
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (this.videoView.getVisibility() == 0 && this.videoView.isInitied() && !this.videoView.isInline()) {
            if (configuration.orientation == 2) {
                if (!this.videoView.isInFullscreen()) {
                    this.videoView.enterFullscreen();
                }
            } else if (this.videoView.isInFullscreen()) {
                this.videoView.exitFullscreen();
            }
        }
        if (this.pipVideoView != null) {
            this.pipVideoView.onConfigurationChanged();
        }
    }

    public void onContainerDraw(Canvas canvas) {
        if (this.waitingForDraw != 0) {
            this.waitingForDraw--;
            if (this.waitingForDraw == 0) {
                this.videoView.updateTextureImageView();
                this.pipVideoView.close();
                this.pipVideoView = null;
                return;
            }
            this.container.invalidate();
        }
    }

    protected void onContainerTranslationYChanged(float f) {
        updateTextureViewPosition();
    }

    protected boolean onCustomLayout(View view, int i, int i2, int i3, int i4) {
        if (view == this.videoView.getControlsView()) {
            updateTextureViewPosition();
        }
        return false;
    }

    protected boolean onCustomMeasure(View view, int i, int i2) {
        if (view == this.videoView.getControlsView()) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = this.videoView.getMeasuredWidth();
            layoutParams.height = (this.videoView.isInFullscreen() ? 0 : AndroidUtilities.dp(10.0f)) + this.videoView.getAspectRatioView().getMeasuredHeight();
        }
        return false;
    }

    public void pause() {
        if (this.videoView != null && this.videoView.isInitied()) {
            this.videoView.pause();
        }
    }

    public void updateTextureViewPosition() {
        View textureImageView;
        this.videoView.getAspectRatioView().getLocationInWindow(this.position);
        int[] iArr = this.position;
        iArr[0] = iArr[0] - getLeftInset();
        if (!(this.videoView.isInline() || this.animationInProgress)) {
            TextureView textureView = this.videoView.getTextureView();
            textureView.setTranslationX((float) this.position[0]);
            textureView.setTranslationY((float) this.position[1]);
            textureImageView = this.videoView.getTextureImageView();
            if (textureImageView != null) {
                textureImageView.setTranslationX((float) this.position[0]);
                textureImageView.setTranslationY((float) this.position[1]);
            }
        }
        textureImageView = this.videoView.getControlsView();
        if (textureImageView.getParent() == this.container) {
            textureImageView.setTranslationY((float) this.position[1]);
        } else {
            textureImageView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        }
    }
}
