package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import java.io.File;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.ui.AspectRatioFrameLayout;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$TL_documentEmpty;
import org.telegram.tgnet.TLRPC$TL_photoEmpty;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.DrawerLayoutContainer;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.Scroller;
import org.telegram.ui.Components.VideoPlayer;
import org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate;
import org.telegram.ui.PhotoViewer.PhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PlaceProviderObject;

public class SecretMediaViewer implements NotificationCenterDelegate, OnGestureListener, OnDoubleTapListener {
    @SuppressLint({"StaticFieldLeak"})
    private static volatile SecretMediaViewer Instance = null;
    private ActionBar actionBar;
    private float animateToClipBottom;
    private float animateToClipHorizontal;
    private float animateToClipTop;
    private float animateToScale;
    private float animateToX;
    private float animateToY;
    private long animationStartTime;
    private float animationValue;
    private AspectRatioFrameLayout aspectRatioFrameLayout;
    private Paint blackPaint = new Paint();
    private boolean canDragDown = true;
    private ImageReceiver centerImage = new ImageReceiver();
    private float clipBottom;
    private float clipHorizontal;
    private float clipTop;
    private long closeTime;
    private boolean closeVideoAfterWatch;
    private FrameLayoutDrawer containerView;
    private int[] coords = new int[2];
    private AnimatorSet currentActionBarAnimation;
    private int currentChannelId;
    private MessageObject currentMessageObject;
    private PhotoViewerProvider currentProvider;
    private int currentRotation;
    private boolean disableShowCheck;
    private boolean discardTap;
    private boolean doubleTap;
    private float dragY;
    private boolean draggingDown;
    private GestureDetector gestureDetector;
    private AnimatorSet imageMoveAnimation;
    private DecelerateInterpolator interpolator = new DecelerateInterpolator(1.5f);
    private boolean invalidCoords;
    private boolean isActionBarVisible = true;
    private boolean isPhotoVisible;
    private boolean isPlaying;
    private boolean isVideo;
    private boolean isVisible;
    private Object lastInsets;
    private float maxX;
    private float maxY;
    private float minX;
    private float minY;
    private float moveStartX;
    private float moveStartY;
    private boolean moving;
    private long openTime;
    private Activity parentActivity;
    private Runnable photoAnimationEndRunnable;
    private int photoAnimationInProgress;
    private PhotoBackgroundDrawable photoBackgroundDrawable = new PhotoBackgroundDrawable(-16777216);
    private long photoTransitionAnimationStartTime;
    private float pinchCenterX;
    private float pinchCenterY;
    private float pinchStartDistance;
    private float pinchStartScale = 1.0f;
    private float pinchStartX;
    private float pinchStartY;
    private float scale = 1.0f;
    private Scroller scroller;
    private SecretDeleteTimer secretDeleteTimer;
    private boolean textureUploaded;
    private float translationX;
    private float translationY;
    private boolean useOvershootForScale;
    private VelocityTracker velocityTracker;
    private float videoCrossfadeAlpha;
    private long videoCrossfadeAlphaLastTime;
    private boolean videoCrossfadeStarted;
    private VideoPlayer videoPlayer;
    private TextureView videoTextureView;
    private boolean videoWatchedOneTime;
    private LayoutParams windowLayoutParams;
    private FrameLayout windowView;
    private boolean zoomAnimation;
    private boolean zooming;

    /* renamed from: org.telegram.ui.SecretMediaViewer$1 */
    class C33201 implements VideoPlayerDelegate {
        C33201() {
        }

        public void onStateChanged(boolean playWhenReady, int playbackState) {
            if (SecretMediaViewer.this.videoPlayer != null && SecretMediaViewer.this.currentMessageObject != null) {
                if (playbackState == 4 || playbackState == 1) {
                    try {
                        SecretMediaViewer.this.parentActivity.getWindow().clearFlags(128);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                } else {
                    try {
                        SecretMediaViewer.this.parentActivity.getWindow().addFlags(128);
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                }
                if (playbackState == 3 && SecretMediaViewer.this.aspectRatioFrameLayout.getVisibility() != 0) {
                    SecretMediaViewer.this.aspectRatioFrameLayout.setVisibility(0);
                }
                if (!SecretMediaViewer.this.videoPlayer.isPlaying() || playbackState == 4) {
                    if (SecretMediaViewer.this.isPlaying) {
                        SecretMediaViewer.this.isPlaying = false;
                        if (playbackState == 4) {
                            SecretMediaViewer.this.videoWatchedOneTime = true;
                            if (SecretMediaViewer.this.closeVideoAfterWatch) {
                                SecretMediaViewer.this.closePhoto(true, true);
                                return;
                            }
                            SecretMediaViewer.this.videoPlayer.seekTo(0);
                            SecretMediaViewer.this.videoPlayer.play();
                        }
                    }
                } else if (!SecretMediaViewer.this.isPlaying) {
                    SecretMediaViewer.this.isPlaying = true;
                }
            }
        }

        public void onError(Exception e) {
            FileLog.e(e);
        }

        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
            if (SecretMediaViewer.this.aspectRatioFrameLayout != null) {
                if (unappliedRotationDegrees == 90 || unappliedRotationDegrees == 270) {
                    int temp = width;
                    width = height;
                    height = temp;
                }
                SecretMediaViewer.this.aspectRatioFrameLayout.setAspectRatio(height == 0 ? 1.0f : (((float) width) * pixelWidthHeightRatio) / ((float) height), unappliedRotationDegrees);
            }
        }

        public void onRenderedFirstFrame() {
            if (!SecretMediaViewer.this.textureUploaded) {
                SecretMediaViewer.this.textureUploaded = true;
                SecretMediaViewer.this.containerView.invalidate();
            }
        }

        public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }
    }

    private class FrameLayoutDrawer extends FrameLayout {
        public FrameLayoutDrawer(Context context) {
            super(context);
            setWillNotDraw(false);
        }

        public boolean onTouchEvent(MotionEvent event) {
            SecretMediaViewer.this.processTouchEvent(event);
            return true;
        }

        protected void onDraw(Canvas canvas) {
            SecretMediaViewer.this.onDraw(canvas);
        }

        protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
            return child != SecretMediaViewer.this.aspectRatioFrameLayout && super.drawChild(canvas, child, drawingTime);
        }
    }

    /* renamed from: org.telegram.ui.SecretMediaViewer$4 */
    class C33234 implements OnApplyWindowInsetsListener {
        C33234() {
        }

        @SuppressLint({"NewApi"})
        public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
            WindowInsets oldInsets = (WindowInsets) SecretMediaViewer.this.lastInsets;
            SecretMediaViewer.this.lastInsets = insets;
            if (oldInsets == null || !oldInsets.toString().equals(insets.toString())) {
                SecretMediaViewer.this.windowView.requestLayout();
            }
            return insets.consumeSystemWindowInsets();
        }
    }

    /* renamed from: org.telegram.ui.SecretMediaViewer$5 */
    class C33245 extends ActionBarMenuOnItemClick {
        C33245() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                SecretMediaViewer.this.closePhoto(true, false);
            }
        }
    }

    /* renamed from: org.telegram.ui.SecretMediaViewer$6 */
    class C33256 implements Runnable {
        C33256() {
        }

        public void run() {
            SecretMediaViewer.this.photoAnimationInProgress = 0;
            SecretMediaViewer.this.imageMoveAnimation = null;
            if (SecretMediaViewer.this.containerView != null) {
                if (VERSION.SDK_INT >= 18) {
                    SecretMediaViewer.this.containerView.setLayerType(0, null);
                }
                SecretMediaViewer.this.containerView.invalidate();
            }
        }
    }

    /* renamed from: org.telegram.ui.SecretMediaViewer$7 */
    class C33267 extends AnimatorListenerAdapter {
        C33267() {
        }

        public void onAnimationEnd(Animator animation) {
            if (SecretMediaViewer.this.photoAnimationEndRunnable != null) {
                SecretMediaViewer.this.photoAnimationEndRunnable.run();
                SecretMediaViewer.this.photoAnimationEndRunnable = null;
            }
        }
    }

    /* renamed from: org.telegram.ui.SecretMediaViewer$9 */
    class C33289 extends AnimatorListenerAdapter {
        C33289() {
        }

        public void onAnimationEnd(Animator animation) {
            if (SecretMediaViewer.this.currentActionBarAnimation != null && SecretMediaViewer.this.currentActionBarAnimation.equals(animation)) {
                SecretMediaViewer.this.actionBar.setVisibility(8);
                SecretMediaViewer.this.currentActionBarAnimation = null;
            }
        }
    }

    private class PhotoBackgroundDrawable extends ColorDrawable {
        private Runnable drawRunnable;
        private int frame;

        public PhotoBackgroundDrawable(int color) {
            super(color);
        }

        public void setAlpha(int alpha) {
            if (SecretMediaViewer.this.parentActivity instanceof LaunchActivity) {
                DrawerLayoutContainer drawerLayoutContainer = ((LaunchActivity) SecretMediaViewer.this.parentActivity).drawerLayoutContainer;
                boolean z = (SecretMediaViewer.this.isPhotoVisible && alpha == 255) ? false : true;
                drawerLayoutContainer.setAllowDrawContent(z);
            }
            super.setAlpha(alpha);
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (getAlpha() != 0) {
                if (this.frame != 2 || this.drawRunnable == null) {
                    invalidateSelf();
                } else {
                    this.drawRunnable.run();
                    this.drawRunnable = null;
                }
                this.frame++;
            }
        }
    }

    private class SecretDeleteTimer extends FrameLayout {
        private Paint afterDeleteProgressPaint;
        private Paint circlePaint;
        private Paint deleteProgressPaint;
        private RectF deleteProgressRect = new RectF();
        private long destroyTime;
        private long destroyTtl;
        private Drawable drawable;
        private ArrayList<Particle> freeParticles = new ArrayList();
        private long lastAnimationTime;
        private Paint particlePaint;
        private ArrayList<Particle> particles = new ArrayList();
        private boolean useVideoProgress;

        private class Particle {
            float alpha;
            float currentTime;
            float lifeTime;
            float velocity;
            float vx;
            float vy;
            /* renamed from: x */
            float f109x;
            /* renamed from: y */
            float f110y;

            private Particle() {
            }
        }

        public SecretDeleteTimer(Context context) {
            super(context);
            setWillNotDraw(false);
            this.particlePaint = new Paint(1);
            this.particlePaint.setStrokeWidth((float) AndroidUtilities.dp(1.5f));
            this.particlePaint.setColor(-1644826);
            this.particlePaint.setStrokeCap(Cap.ROUND);
            this.particlePaint.setStyle(Style.STROKE);
            this.deleteProgressPaint = new Paint(1);
            this.deleteProgressPaint.setColor(-1644826);
            this.afterDeleteProgressPaint = new Paint(1);
            this.afterDeleteProgressPaint.setStyle(Style.STROKE);
            this.afterDeleteProgressPaint.setStrokeCap(Cap.ROUND);
            this.afterDeleteProgressPaint.setColor(-1644826);
            this.afterDeleteProgressPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
            this.circlePaint = new Paint(1);
            this.circlePaint.setColor(2130706432);
            this.drawable = context.getResources().getDrawable(R.drawable.flame_small);
            for (int a = 0; a < 40; a++) {
                this.freeParticles.add(new Particle());
            }
        }

        private void setDestroyTime(long time, long ttl, boolean videoProgress) {
            this.destroyTime = time;
            this.destroyTtl = ttl;
            this.useVideoProgress = videoProgress;
            this.lastAnimationTime = System.currentTimeMillis();
            invalidate();
        }

        private void updateParticles(long dt) {
            int count = this.particles.size();
            int a = 0;
            while (a < count) {
                Particle particle = (Particle) this.particles.get(a);
                if (particle.currentTime >= particle.lifeTime) {
                    if (this.freeParticles.size() < 40) {
                        this.freeParticles.add(particle);
                    }
                    this.particles.remove(a);
                    a--;
                    count--;
                } else {
                    particle.alpha = 1.0f - AndroidUtilities.decelerateInterpolator.getInterpolation(particle.currentTime / particle.lifeTime);
                    particle.f109x += ((particle.vx * particle.velocity) * ((float) dt)) / 500.0f;
                    particle.f110y += ((particle.vy * particle.velocity) * ((float) dt)) / 500.0f;
                    particle.currentTime += (float) dt;
                }
                a++;
            }
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int y = (getMeasuredHeight() / 2) - (AndroidUtilities.dp(28.0f) / 2);
            this.deleteProgressRect.set((float) (getMeasuredWidth() - AndroidUtilities.dp(49.0f)), (float) y, (float) (getMeasuredWidth() - AndroidUtilities.dp(21.0f)), (float) (AndroidUtilities.dp(28.0f) + y));
        }

        @SuppressLint({"DrawAllocation"})
        protected void onDraw(Canvas canvas) {
            if (SecretMediaViewer.this.currentMessageObject != null && SecretMediaViewer.this.currentMessageObject.messageOwner.destroyTime != 0) {
                float progress;
                int a;
                canvas.drawCircle((float) (getMeasuredWidth() - AndroidUtilities.dp(35.0f)), (float) (getMeasuredHeight() / 2), (float) AndroidUtilities.dp(16.0f), this.circlePaint);
                if (!this.useVideoProgress) {
                    progress = ((float) Math.max(0, this.destroyTime - (System.currentTimeMillis() + ((long) (ConnectionsManager.getInstance().getTimeDifference() * 1000))))) / (((float) this.destroyTtl) * 1000.0f);
                } else if (SecretMediaViewer.this.videoPlayer != null) {
                    long duration = SecretMediaViewer.this.videoPlayer.getDuration();
                    long position = SecretMediaViewer.this.videoPlayer.getCurrentPosition();
                    if (duration == C0907C.TIME_UNSET || position == C0907C.TIME_UNSET) {
                        progress = 1.0f;
                    } else {
                        progress = 1.0f - (((float) position) / ((float) duration));
                    }
                } else {
                    progress = 1.0f;
                }
                int x = getMeasuredWidth() - AndroidUtilities.dp(40.0f);
                int y = ((getMeasuredHeight() - AndroidUtilities.dp(14.0f)) / 2) - AndroidUtilities.dp(0.5f);
                this.drawable.setBounds(x, y, AndroidUtilities.dp(10.0f) + x, AndroidUtilities.dp(14.0f) + y);
                this.drawable.draw(canvas);
                float radProgress = -360.0f * progress;
                canvas.drawArc(this.deleteProgressRect, -90.0f, radProgress, false, this.afterDeleteProgressPaint);
                int count = this.particles.size();
                for (a = 0; a < count; a++) {
                    Particle particle = (Particle) this.particles.get(a);
                    this.particlePaint.setAlpha((int) (255.0f * particle.alpha));
                    canvas.drawPoint(particle.f109x, particle.f110y, this.particlePaint);
                }
                double vx = Math.sin(0.017453292519943295d * ((double) (radProgress - 90.0f)));
                double vy = -Math.cos(0.017453292519943295d * ((double) (radProgress - 90.0f)));
                int rad = AndroidUtilities.dp(14.0f);
                float cx = (float) (((-vy) * ((double) rad)) + ((double) this.deleteProgressRect.centerX()));
                float cy = (float) ((((double) rad) * vx) + ((double) this.deleteProgressRect.centerY()));
                for (a = 0; a < 1; a++) {
                    Particle newParticle;
                    if (this.freeParticles.isEmpty()) {
                        SecretDeleteTimer secretDeleteTimer = this;
                        Particle particle2 = new Particle();
                    } else {
                        newParticle = (Particle) this.freeParticles.get(0);
                        this.freeParticles.remove(0);
                    }
                    newParticle.f109x = cx;
                    newParticle.f110y = cy;
                    double angle = 0.017453292519943295d * ((double) (Utilities.random.nextInt(140) - 70));
                    if (angle < 0.0d) {
                        angle += 6.283185307179586d;
                    }
                    newParticle.vx = (float) ((Math.cos(angle) * vx) - (Math.sin(angle) * vy));
                    newParticle.vy = (float) ((Math.sin(angle) * vx) + (Math.cos(angle) * vy));
                    newParticle.alpha = 1.0f;
                    newParticle.currentTime = 0.0f;
                    newParticle.lifeTime = (float) (Utilities.random.nextInt(100) + ChatActivity.scheduleDownloads);
                    newParticle.velocity = 20.0f + (Utilities.random.nextFloat() * 4.0f);
                    this.particles.add(newParticle);
                }
                long newTime = System.currentTimeMillis();
                updateParticles(newTime - this.lastAnimationTime);
                this.lastAnimationTime = newTime;
                invalidate();
            }
        }
    }

    public static SecretMediaViewer getInstance() {
        SecretMediaViewer localInstance = Instance;
        if (localInstance == null) {
            synchronized (PhotoViewer.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        SecretMediaViewer localInstance2 = new SecretMediaViewer();
                        try {
                            Instance = localInstance2;
                            localInstance = localInstance2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            localInstance = localInstance2;
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    throw th2;
                }
            }
        }
        return localInstance;
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.messagesDeleted) {
            if (this.currentMessageObject == null || ((Integer) args[1]).intValue() != 0 || !args[0].contains(Integer.valueOf(this.currentMessageObject.getId()))) {
                return;
            }
            if (!this.isVideo || this.videoWatchedOneTime) {
                closePhoto(true, true);
            } else {
                this.closeVideoAfterWatch = true;
            }
        } else if (id == NotificationCenter.didCreatedNewDeleteTask) {
            if (this.currentMessageObject != null && this.secretDeleteTimer != null) {
                SparseArray<ArrayList<Long>> mids = args[0];
                for (int i = 0; i < mids.size(); i++) {
                    int key = mids.keyAt(i);
                    ArrayList<Long> arr = (ArrayList) mids.get(key);
                    for (int a = 0; a < arr.size(); a++) {
                        long mid = ((Long) arr.get(a)).longValue();
                        if (a == 0) {
                            int channelId = (int) (mid >> 32);
                            if (channelId < 0) {
                                channelId = 0;
                            }
                            if (channelId != this.currentChannelId) {
                                return;
                            }
                        }
                        if (((long) this.currentMessageObject.getId()) == mid) {
                            this.currentMessageObject.messageOwner.destroyTime = key;
                            this.secretDeleteTimer.invalidate();
                            return;
                        }
                    }
                }
            }
        } else if (id == NotificationCenter.updateMessageMedia) {
            if (this.currentMessageObject.getId() != args[0].id) {
                return;
            }
            if (!this.isVideo || this.videoWatchedOneTime) {
                closePhoto(true, true);
            } else {
                this.closeVideoAfterWatch = true;
            }
        }
    }

    private void preparePlayer(File file) {
        if (this.parentActivity != null) {
            releasePlayer();
            if (this.videoTextureView == null) {
                this.aspectRatioFrameLayout = new AspectRatioFrameLayout(this.parentActivity);
                this.aspectRatioFrameLayout.setVisibility(4);
                this.containerView.addView(this.aspectRatioFrameLayout, 0, LayoutHelper.createFrame(-1, -1, 17));
                this.videoTextureView = new TextureView(this.parentActivity);
                this.videoTextureView.setOpaque(false);
                this.aspectRatioFrameLayout.addView(this.videoTextureView, LayoutHelper.createFrame(-1, -1, 17));
            }
            this.textureUploaded = false;
            this.videoCrossfadeStarted = false;
            TextureView textureView = this.videoTextureView;
            this.videoCrossfadeAlpha = 0.0f;
            textureView.setAlpha(0.0f);
            if (this.videoPlayer == null) {
                this.videoPlayer = new VideoPlayer();
                this.videoPlayer.setTextureView(this.videoTextureView);
                this.videoPlayer.setDelegate(new C33201());
            }
            this.videoPlayer.preparePlayer(Uri.fromFile(file), "other");
            this.videoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (this.videoPlayer != null) {
            this.videoPlayer.releasePlayer();
            this.videoPlayer = null;
        }
        try {
            if (this.parentActivity != null) {
                this.parentActivity.getWindow().clearFlags(128);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (this.aspectRatioFrameLayout != null) {
            this.containerView.removeView(this.aspectRatioFrameLayout);
            this.aspectRatioFrameLayout = null;
        }
        if (this.videoTextureView != null) {
            this.videoTextureView = null;
        }
        this.isPlaying = false;
    }

    public void setParentActivity(Activity activity) {
        if (this.parentActivity != activity) {
            this.parentActivity = activity;
            this.scroller = new Scroller(activity);
            this.windowView = new FrameLayout(activity) {
                protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
                    int heightSize = MeasureSpec.getSize(heightMeasureSpec);
                    if (VERSION.SDK_INT >= 21 && SecretMediaViewer.this.lastInsets != null) {
                        WindowInsets insets = (WindowInsets) SecretMediaViewer.this.lastInsets;
                        if (AndroidUtilities.incorrectDisplaySizeFix) {
                            if (heightSize > AndroidUtilities.displaySize.y) {
                                heightSize = AndroidUtilities.displaySize.y;
                            }
                            heightSize += AndroidUtilities.statusBarHeight;
                        }
                        heightSize -= insets.getSystemWindowInsetBottom();
                        widthSize -= insets.getSystemWindowInsetRight();
                    } else if (heightSize > AndroidUtilities.displaySize.y) {
                        heightSize = AndroidUtilities.displaySize.y;
                    }
                    setMeasuredDimension(widthSize, heightSize);
                    if (VERSION.SDK_INT >= 21 && SecretMediaViewer.this.lastInsets != null) {
                        widthSize -= ((WindowInsets) SecretMediaViewer.this.lastInsets).getSystemWindowInsetLeft();
                    }
                    SecretMediaViewer.this.containerView.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(heightSize, 1073741824));
                }

                protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                    int x = 0;
                    if (VERSION.SDK_INT >= 21 && SecretMediaViewer.this.lastInsets != null) {
                        x = 0 + ((WindowInsets) SecretMediaViewer.this.lastInsets).getSystemWindowInsetLeft();
                    }
                    SecretMediaViewer.this.containerView.layout(x, 0, SecretMediaViewer.this.containerView.getMeasuredWidth() + x, SecretMediaViewer.this.containerView.getMeasuredHeight());
                    if (changed) {
                        if (SecretMediaViewer.this.imageMoveAnimation == null) {
                            SecretMediaViewer.this.scale = 1.0f;
                            SecretMediaViewer.this.translationX = 0.0f;
                            SecretMediaViewer.this.translationY = 0.0f;
                        }
                        SecretMediaViewer.this.updateMinMax(SecretMediaViewer.this.scale);
                    }
                }
            };
            this.windowView.setBackgroundDrawable(this.photoBackgroundDrawable);
            this.windowView.setFocusable(true);
            this.windowView.setFocusableInTouchMode(true);
            this.containerView = new FrameLayoutDrawer(activity) {
                protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                    super.onLayout(changed, left, top, right, bottom);
                    if (SecretMediaViewer.this.secretDeleteTimer != null) {
                        int y = ((ActionBar.getCurrentActionBarHeight() - SecretMediaViewer.this.secretDeleteTimer.getMeasuredHeight()) / 2) + (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0);
                        SecretMediaViewer.this.secretDeleteTimer.layout(SecretMediaViewer.this.secretDeleteTimer.getLeft(), y, SecretMediaViewer.this.secretDeleteTimer.getRight(), SecretMediaViewer.this.secretDeleteTimer.getMeasuredHeight() + y);
                    }
                }
            };
            this.containerView.setFocusable(false);
            this.windowView.addView(this.containerView);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.containerView.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.gravity = 51;
            this.containerView.setLayoutParams(layoutParams);
            if (VERSION.SDK_INT >= 21) {
                this.containerView.setFitsSystemWindows(true);
                this.containerView.setOnApplyWindowInsetsListener(new C33234());
                this.containerView.setSystemUiVisibility(1280);
            }
            this.gestureDetector = new GestureDetector(this.containerView.getContext(), this);
            this.gestureDetector.setOnDoubleTapListener(this);
            this.actionBar = new ActionBar(activity);
            this.actionBar.setTitleColor(-1);
            this.actionBar.setSubtitleColor(-1);
            this.actionBar.setBackgroundColor(2130706432);
            this.actionBar.setOccupyStatusBar(VERSION.SDK_INT >= 21);
            this.actionBar.setItemsBackgroundColor(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR, false);
            this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
            this.actionBar.setTitleRightMargin(AndroidUtilities.dp(70.0f));
            this.containerView.addView(this.actionBar, LayoutHelper.createFrame(-1, -2.0f));
            this.actionBar.setActionBarMenuOnItemClick(new C33245());
            this.secretDeleteTimer = new SecretDeleteTimer(activity);
            this.containerView.addView(this.secretDeleteTimer, LayoutHelper.createFrame(119, 48.0f, 53, 0.0f, 0.0f, 0.0f, 0.0f));
            this.windowLayoutParams = new LayoutParams();
            this.windowLayoutParams.height = -1;
            this.windowLayoutParams.format = -3;
            this.windowLayoutParams.width = -1;
            this.windowLayoutParams.gravity = 48;
            this.windowLayoutParams.type = 99;
            if (VERSION.SDK_INT >= 21) {
                this.windowLayoutParams.flags = -2147417848;
            } else {
                this.windowLayoutParams.flags = 8;
            }
            this.centerImage.setParentView(this.containerView);
            this.centerImage.setForceCrossfade(true);
        }
    }

    public void openMedia(MessageObject messageObject, PhotoViewerProvider provider) {
        if (this.parentActivity != null && messageObject != null && messageObject.isSecretPhoto() && provider != null) {
            PlaceProviderObject object = provider.getPlaceForPhoto(messageObject, null, 0);
            if (object != null) {
                this.currentProvider = provider;
                this.openTime = System.currentTimeMillis();
                this.closeTime = 0;
                this.isActionBarVisible = true;
                this.isPhotoVisible = true;
                this.draggingDown = false;
                if (this.aspectRatioFrameLayout != null) {
                    this.aspectRatioFrameLayout.setVisibility(4);
                }
                releasePlayer();
                this.pinchStartDistance = 0.0f;
                this.pinchStartScale = 1.0f;
                this.pinchCenterX = 0.0f;
                this.pinchCenterY = 0.0f;
                this.pinchStartX = 0.0f;
                this.pinchStartY = 0.0f;
                this.moveStartX = 0.0f;
                this.moveStartY = 0.0f;
                this.zooming = false;
                this.moving = false;
                this.doubleTap = false;
                this.invalidCoords = false;
                this.canDragDown = true;
                updateMinMax(this.scale);
                this.photoBackgroundDrawable.setAlpha(0);
                this.containerView.setAlpha(1.0f);
                this.containerView.setVisibility(0);
                this.secretDeleteTimer.setAlpha(1.0f);
                this.isVideo = false;
                this.videoWatchedOneTime = false;
                this.closeVideoAfterWatch = false;
                this.disableShowCheck = true;
                this.centerImage.setManualAlphaAnimator(false);
                Rect drawRegion = object.imageReceiver.getDrawRegion();
                float width = (float) (drawRegion.right - drawRegion.left);
                float height = (float) (drawRegion.bottom - drawRegion.top);
                int viewWidth = AndroidUtilities.displaySize.x;
                int viewHeight = AndroidUtilities.displaySize.y + (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0);
                this.scale = Math.max(width / ((float) viewWidth), height / ((float) viewHeight));
                this.translationX = (((float) (object.viewX + drawRegion.left)) + (width / 2.0f)) - ((float) (viewWidth / 2));
                this.translationY = (((float) (object.viewY + drawRegion.top)) + (height / 2.0f)) - ((float) (viewHeight / 2));
                this.clipHorizontal = (float) Math.abs(drawRegion.left - object.imageReceiver.getImageX());
                int clipVertical = Math.abs(drawRegion.top - object.imageReceiver.getImageY());
                int[] coords2 = new int[2];
                object.parentView.getLocationInWindow(coords2);
                this.clipTop = (float) (((coords2[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight)) - (object.viewY + drawRegion.top)) + object.clipTopAddition);
                if (this.clipTop < 0.0f) {
                    this.clipTop = 0.0f;
                }
                this.clipBottom = (float) (((((int) height) + (object.viewY + drawRegion.top)) - ((object.parentView.getHeight() + coords2[1]) - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight))) + object.clipBottomAddition);
                if (this.clipBottom < 0.0f) {
                    this.clipBottom = 0.0f;
                }
                this.clipTop = Math.max(this.clipTop, (float) clipVertical);
                this.clipBottom = Math.max(this.clipBottom, (float) clipVertical);
                this.animationStartTime = System.currentTimeMillis();
                this.animateToX = 0.0f;
                this.animateToY = 0.0f;
                this.animateToClipBottom = 0.0f;
                this.animateToClipHorizontal = 0.0f;
                this.animateToClipTop = 0.0f;
                this.animateToScale = 1.0f;
                this.zoomAnimation = true;
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesDeleted);
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateMessageMedia);
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.didCreatedNewDeleteTask);
                this.currentChannelId = messageObject.messageOwner.to_id != null ? messageObject.messageOwner.to_id.channel_id : 0;
                toggleActionBar(true, false);
                this.currentMessageObject = messageObject;
                TLRPC$Document document = messageObject.getDocument();
                Bitmap thumb = object.imageReceiver.getThumbBitmap();
                if (document != null) {
                    this.actionBar.setTitle(LocaleController.getString("DisappearingVideo", R.string.DisappearingVideo));
                    File file = new File(messageObject.messageOwner.attachPath);
                    if (file.exists()) {
                        preparePlayer(file);
                    } else {
                        File file2 = FileLoader.getPathToMessage(messageObject.messageOwner);
                        file = new File(file2.getAbsolutePath() + ".enc");
                        if (file.exists()) {
                            file2 = file;
                        }
                        preparePlayer(file2);
                    }
                    this.isVideo = true;
                    this.centerImage.setImage(null, null, thumb != null ? new BitmapDrawable(thumb) : null, -1, null, 2);
                    if (((long) (messageObject.getDuration() * 1000)) > (((long) messageObject.messageOwner.destroyTime) * 1000) - (System.currentTimeMillis() + ((long) (ConnectionsManager.getInstance().getTimeDifference() * 1000)))) {
                        this.secretDeleteTimer.setDestroyTime(-1, -1, true);
                    } else {
                        this.secretDeleteTimer.setDestroyTime(((long) messageObject.messageOwner.destroyTime) * 1000, (long) messageObject.messageOwner.ttl, false);
                    }
                } else {
                    this.actionBar.setTitle(LocaleController.getString("DisappearingPhoto", R.string.DisappearingPhoto));
                    this.centerImage.setImage(FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize()).location, null, thumb != null ? new BitmapDrawable(thumb) : null, -1, null, 2);
                    this.secretDeleteTimer.setDestroyTime(((long) messageObject.messageOwner.destroyTime) * 1000, (long) messageObject.messageOwner.ttl, false);
                }
                try {
                    if (this.windowView.getParent() != null) {
                        ((WindowManager) this.parentActivity.getSystemService("window")).removeView(this.windowView);
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
                ((WindowManager) this.parentActivity.getSystemService("window")).addView(this.windowView, this.windowLayoutParams);
                this.secretDeleteTimer.invalidate();
                this.isVisible = true;
                this.imageMoveAnimation = new AnimatorSet();
                this.imageMoveAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{0.0f, 1.0f}), ObjectAnimator.ofFloat(this.secretDeleteTimer, "alpha", new float[]{0.0f, 1.0f}), ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0, 255}), ObjectAnimator.ofFloat(this.secretDeleteTimer, "alpha", new float[]{0.0f, 1.0f}), ObjectAnimator.ofFloat(this, "animationValue", new float[]{0.0f, 1.0f})});
                this.photoAnimationInProgress = 3;
                this.photoAnimationEndRunnable = new C33256();
                this.imageMoveAnimation.setDuration(250);
                this.imageMoveAnimation.addListener(new C33267());
                this.photoTransitionAnimationStartTime = System.currentTimeMillis();
                if (VERSION.SDK_INT >= 18) {
                    this.containerView.setLayerType(2, null);
                }
                this.imageMoveAnimation.setInterpolator(new DecelerateInterpolator());
                this.photoBackgroundDrawable.frame = 0;
                final PlaceProviderObject placeProviderObject = object;
                this.photoBackgroundDrawable.drawRunnable = new Runnable() {
                    public void run() {
                        SecretMediaViewer.this.disableShowCheck = false;
                        placeProviderObject.imageReceiver.setVisible(false, true);
                    }
                };
                this.imageMoveAnimation.start();
            }
        }
    }

    public boolean isShowingImage(MessageObject object) {
        return (!this.isVisible || this.disableShowCheck || object == null || this.currentMessageObject == null || this.currentMessageObject.getId() != object.getId()) ? false : true;
    }

    private void toggleActionBar(boolean show, boolean animated) {
        float f = 1.0f;
        if (show) {
            this.actionBar.setVisibility(0);
        }
        this.actionBar.setEnabled(show);
        this.isActionBarVisible = show;
        if (animated) {
            ArrayList<Animator> arrayList = new ArrayList();
            ActionBar actionBar = this.actionBar;
            String str = "alpha";
            float[] fArr = new float[1];
            if (!show) {
                f = 0.0f;
            }
            fArr[0] = f;
            arrayList.add(ObjectAnimator.ofFloat(actionBar, str, fArr));
            this.currentActionBarAnimation = new AnimatorSet();
            this.currentActionBarAnimation.playTogether(arrayList);
            if (!show) {
                this.currentActionBarAnimation.addListener(new C33289());
            }
            this.currentActionBarAnimation.setDuration(200);
            this.currentActionBarAnimation.start();
            return;
        }
        actionBar = this.actionBar;
        if (!show) {
            f = 0.0f;
        }
        actionBar.setAlpha(f);
        if (!show) {
            this.actionBar.setVisibility(8);
        }
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void destroyPhotoViewer() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesDeleted);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateMessageMedia);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didCreatedNewDeleteTask);
        this.isVisible = false;
        this.currentProvider = null;
        releasePlayer();
        if (!(this.parentActivity == null || this.windowView == null)) {
            try {
                if (this.windowView.getParent() != null) {
                    ((WindowManager) this.parentActivity.getSystemService("window")).removeViewImmediate(this.windowView);
                }
                this.windowView = null;
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
        Instance = null;
    }

    private void onDraw(Canvas canvas) {
        if (this.isPhotoVisible) {
            float currentScale;
            float currentTranslationY;
            float currentTranslationX;
            float currentClipTop;
            float currentClipBottom;
            float currentClipHorizontal;
            float aty = -1.0f;
            if (this.imageMoveAnimation != null) {
                if (!this.scroller.isFinished()) {
                    this.scroller.abortAnimation();
                }
                if (this.useOvershootForScale) {
                    float av;
                    if (this.animationValue < 0.9f) {
                        av = this.animationValue / 0.9f;
                        currentScale = this.scale + (((this.animateToScale * 1.02f) - this.scale) * av);
                    } else {
                        av = 1.0f;
                        currentScale = this.animateToScale + ((this.animateToScale * 0.01999998f) * (1.0f - ((this.animationValue - 0.9f) / 0.100000024f)));
                    }
                    currentTranslationY = this.translationY + ((this.animateToY - this.translationY) * av);
                    currentTranslationX = this.translationX + ((this.animateToX - this.translationX) * av);
                    currentClipTop = this.clipTop + ((this.animateToClipTop - this.clipTop) * av);
                    currentClipBottom = this.clipBottom + ((this.animateToClipBottom - this.clipBottom) * av);
                    currentClipHorizontal = this.clipHorizontal + ((this.animateToClipHorizontal - this.clipHorizontal) * av);
                } else {
                    currentScale = this.scale + ((this.animateToScale - this.scale) * this.animationValue);
                    currentTranslationY = this.translationY + ((this.animateToY - this.translationY) * this.animationValue);
                    currentTranslationX = this.translationX + ((this.animateToX - this.translationX) * this.animationValue);
                    currentClipTop = this.clipTop + ((this.animateToClipTop - this.clipTop) * this.animationValue);
                    currentClipBottom = this.clipBottom + ((this.animateToClipBottom - this.clipBottom) * this.animationValue);
                    currentClipHorizontal = this.clipHorizontal + ((this.animateToClipHorizontal - this.clipHorizontal) * this.animationValue);
                }
                if (this.animateToScale == 1.0f && this.scale == 1.0f && this.translationX == 0.0f) {
                    aty = currentTranslationY;
                }
                this.containerView.invalidate();
            } else {
                if (this.animationStartTime != 0) {
                    this.translationX = this.animateToX;
                    this.translationY = this.animateToY;
                    this.clipBottom = this.animateToClipBottom;
                    this.clipTop = this.animateToClipTop;
                    this.clipHorizontal = this.animateToClipHorizontal;
                    this.scale = this.animateToScale;
                    this.animationStartTime = 0;
                    updateMinMax(this.scale);
                    this.zoomAnimation = false;
                    this.useOvershootForScale = false;
                }
                if (!this.scroller.isFinished() && this.scroller.computeScrollOffset()) {
                    if (((float) this.scroller.getStartX()) < this.maxX && ((float) this.scroller.getStartX()) > this.minX) {
                        this.translationX = (float) this.scroller.getCurrX();
                    }
                    if (((float) this.scroller.getStartY()) < this.maxY && ((float) this.scroller.getStartY()) > this.minY) {
                        this.translationY = (float) this.scroller.getCurrY();
                    }
                    this.containerView.invalidate();
                }
                currentScale = this.scale;
                currentTranslationY = this.translationY;
                currentTranslationX = this.translationX;
                currentClipTop = this.clipTop;
                currentClipBottom = this.clipBottom;
                currentClipHorizontal = this.clipHorizontal;
                if (!this.moving) {
                    aty = this.translationY;
                }
            }
            float translateX = currentTranslationX;
            float scaleDiff = 0.0f;
            float alpha = 1.0f;
            if (this.photoAnimationInProgress != 3) {
                if (this.scale != 1.0f || aty == -1.0f || this.zoomAnimation) {
                    this.photoBackgroundDrawable.setAlpha(255);
                } else {
                    float maxValue = ((float) getContainerViewHeight()) / 4.0f;
                    this.photoBackgroundDrawable.setAlpha((int) Math.max(127.0f, 255.0f * (1.0f - (Math.min(Math.abs(aty), maxValue) / maxValue))));
                }
                if (!this.zoomAnimation && translateX > this.maxX) {
                    alpha = Math.min(1.0f, (translateX - this.maxX) / ((float) canvas.getWidth()));
                    scaleDiff = alpha * 0.3f;
                    alpha = 1.0f - alpha;
                    translateX = this.maxX;
                }
            }
            boolean drawTextureView = this.aspectRatioFrameLayout != null && this.aspectRatioFrameLayout.getVisibility() == 0;
            canvas.save();
            float sc = currentScale - scaleDiff;
            canvas.translate(((float) (getContainerViewWidth() / 2)) + translateX, ((float) (getContainerViewHeight() / 2)) + currentTranslationY);
            canvas.scale(sc, sc);
            int bitmapWidth = this.centerImage.getBitmapWidth();
            int bitmapHeight = this.centerImage.getBitmapHeight();
            if (drawTextureView && this.textureUploaded && Math.abs((((float) bitmapWidth) / ((float) bitmapHeight)) - (((float) this.videoTextureView.getMeasuredWidth()) / ((float) this.videoTextureView.getMeasuredHeight()))) > 0.01f) {
                bitmapWidth = this.videoTextureView.getMeasuredWidth();
                bitmapHeight = this.videoTextureView.getMeasuredHeight();
            }
            float scale = Math.min(((float) getContainerViewHeight()) / ((float) bitmapHeight), ((float) getContainerViewWidth()) / ((float) bitmapWidth));
            int width = (int) (((float) bitmapWidth) * scale);
            int height = (int) (((float) bitmapHeight) * scale);
            canvas.clipRect(((float) ((-width) / 2)) + (currentClipHorizontal / sc), ((float) ((-height) / 2)) + (currentClipTop / sc), ((float) (width / 2)) - (currentClipHorizontal / sc), ((float) (height / 2)) - (currentClipBottom / sc));
            if (!(drawTextureView && this.textureUploaded && this.videoCrossfadeStarted && this.videoCrossfadeAlpha == 1.0f)) {
                this.centerImage.setAlpha(alpha);
                this.centerImage.setImageCoords((-width) / 2, (-height) / 2, width, height);
                this.centerImage.draw(canvas);
            }
            if (drawTextureView) {
                if (!this.videoCrossfadeStarted && this.textureUploaded) {
                    this.videoCrossfadeStarted = true;
                    this.videoCrossfadeAlpha = 0.0f;
                    this.videoCrossfadeAlphaLastTime = System.currentTimeMillis();
                }
                canvas.translate((float) ((-width) / 2), (float) ((-height) / 2));
                this.videoTextureView.setAlpha(this.videoCrossfadeAlpha * alpha);
                this.aspectRatioFrameLayout.draw(canvas);
                if (this.videoCrossfadeStarted && this.videoCrossfadeAlpha < 1.0f) {
                    long newUpdateTime = System.currentTimeMillis();
                    long dt = newUpdateTime - this.videoCrossfadeAlphaLastTime;
                    this.videoCrossfadeAlphaLastTime = newUpdateTime;
                    this.videoCrossfadeAlpha += ((float) dt) / 200.0f;
                    this.containerView.invalidate();
                    if (this.videoCrossfadeAlpha > 1.0f) {
                        this.videoCrossfadeAlpha = 1.0f;
                    }
                }
            }
            canvas.restore();
        }
    }

    public float getVideoCrossfadeAlpha() {
        return this.videoCrossfadeAlpha;
    }

    public void setVideoCrossfadeAlpha(float value) {
        this.videoCrossfadeAlpha = value;
        this.containerView.invalidate();
    }

    private boolean checkPhotoAnimation() {
        if (this.photoAnimationInProgress != 0 && Math.abs(this.photoTransitionAnimationStartTime - System.currentTimeMillis()) >= 500) {
            if (this.photoAnimationEndRunnable != null) {
                this.photoAnimationEndRunnable.run();
                this.photoAnimationEndRunnable = null;
            }
            this.photoAnimationInProgress = 0;
        }
        if (this.photoAnimationInProgress != 0) {
            return true;
        }
        return false;
    }

    public long getOpenTime() {
        return this.openTime;
    }

    public long getCloseTime() {
        return this.closeTime;
    }

    public MessageObject getCurrentMessageObject() {
        return this.currentMessageObject;
    }

    public void closePhoto(boolean animated, boolean byDelete) {
        if (this.parentActivity != null && this.isPhotoVisible && !checkPhotoAnimation()) {
            PlaceProviderObject object;
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesDeleted);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateMessageMedia);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didCreatedNewDeleteTask);
            this.isActionBarVisible = false;
            if (this.velocityTracker != null) {
                this.velocityTracker.recycle();
                this.velocityTracker = null;
            }
            this.closeTime = System.currentTimeMillis();
            if (this.currentProvider == null || (this.currentMessageObject.messageOwner.media.photo instanceof TLRPC$TL_photoEmpty) || (this.currentMessageObject.messageOwner.media.document instanceof TLRPC$TL_documentEmpty)) {
                object = null;
            } else {
                object = this.currentProvider.getPlaceForPhoto(this.currentMessageObject, null, 0);
            }
            if (this.videoPlayer != null) {
                this.videoPlayer.pause();
            }
            if (animated) {
                this.photoAnimationInProgress = 3;
                this.containerView.invalidate();
                this.imageMoveAnimation = new AnimatorSet();
                if (object == null || object.imageReceiver.getThumbBitmap() == null || byDelete) {
                    float f;
                    int h = AndroidUtilities.displaySize.y + (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0);
                    if (this.translationY >= 0.0f) {
                        f = (float) h;
                    } else {
                        f = (float) (-h);
                    }
                    this.animateToY = f;
                } else {
                    object.imageReceiver.setVisible(false, true);
                    Rect drawRegion = object.imageReceiver.getDrawRegion();
                    float width = (float) (drawRegion.right - drawRegion.left);
                    float height = (float) (drawRegion.bottom - drawRegion.top);
                    int viewWidth = AndroidUtilities.displaySize.x;
                    int viewHeight = AndroidUtilities.displaySize.y + (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0);
                    this.animateToScale = Math.max(width / ((float) viewWidth), height / ((float) viewHeight));
                    this.animateToX = (((float) (object.viewX + drawRegion.left)) + (width / 2.0f)) - ((float) (viewWidth / 2));
                    this.animateToY = (((float) (object.viewY + drawRegion.top)) + (height / 2.0f)) - ((float) (viewHeight / 2));
                    this.animateToClipHorizontal = (float) Math.abs(drawRegion.left - object.imageReceiver.getImageX());
                    int clipVertical = Math.abs(drawRegion.top - object.imageReceiver.getImageY());
                    int[] coords2 = new int[2];
                    object.parentView.getLocationInWindow(coords2);
                    this.animateToClipTop = (float) (((coords2[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight)) - (object.viewY + drawRegion.top)) + object.clipTopAddition);
                    if (this.animateToClipTop < 0.0f) {
                        this.animateToClipTop = 0.0f;
                    }
                    this.animateToClipBottom = (float) (((((int) height) + (object.viewY + drawRegion.top)) - ((object.parentView.getHeight() + coords2[1]) - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight))) + object.clipBottomAddition);
                    if (this.animateToClipBottom < 0.0f) {
                        this.animateToClipBottom = 0.0f;
                    }
                    this.animationStartTime = System.currentTimeMillis();
                    this.animateToClipBottom = Math.max(this.animateToClipBottom, (float) clipVertical);
                    this.animateToClipTop = Math.max(this.animateToClipTop, (float) clipVertical);
                    this.zoomAnimation = true;
                }
                AnimatorSet animatorSet;
                Animator[] animatorArr;
                float[] fArr;
                if (this.isVideo) {
                    this.videoCrossfadeStarted = false;
                    this.textureUploaded = false;
                    animatorSet = this.imageMoveAnimation;
                    animatorArr = new Animator[5];
                    animatorArr[0] = ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0});
                    fArr = new float[2];
                    animatorArr[1] = ObjectAnimator.ofFloat(this, "animationValue", new float[]{0.0f, 1.0f});
                    animatorArr[2] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{0.0f});
                    animatorArr[3] = ObjectAnimator.ofFloat(this.secretDeleteTimer, "alpha", new float[]{0.0f});
                    animatorArr[4] = ObjectAnimator.ofFloat(this, "videoCrossfadeAlpha", new float[]{0.0f});
                    animatorSet.playTogether(animatorArr);
                } else {
                    this.centerImage.setManualAlphaAnimator(true);
                    animatorSet = this.imageMoveAnimation;
                    animatorArr = new Animator[5];
                    animatorArr[0] = ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0});
                    fArr = new float[2];
                    animatorArr[1] = ObjectAnimator.ofFloat(this, "animationValue", new float[]{0.0f, 1.0f});
                    animatorArr[2] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{0.0f});
                    animatorArr[3] = ObjectAnimator.ofFloat(this.secretDeleteTimer, "alpha", new float[]{0.0f});
                    animatorArr[4] = ObjectAnimator.ofFloat(this.centerImage, "currentAlpha", new float[]{0.0f});
                    animatorSet.playTogether(animatorArr);
                }
                this.photoAnimationEndRunnable = new Runnable() {
                    public void run() {
                        SecretMediaViewer.this.imageMoveAnimation = null;
                        SecretMediaViewer.this.photoAnimationInProgress = 0;
                        if (VERSION.SDK_INT >= 18) {
                            SecretMediaViewer.this.containerView.setLayerType(0, null);
                        }
                        SecretMediaViewer.this.containerView.setVisibility(4);
                        SecretMediaViewer.this.onPhotoClosed(object);
                    }
                };
                this.imageMoveAnimation.setInterpolator(new DecelerateInterpolator());
                this.imageMoveAnimation.setDuration(250);
                this.imageMoveAnimation.addListener(new AnimatorListenerAdapter() {

                    /* renamed from: org.telegram.ui.SecretMediaViewer$11$1 */
                    class C33191 implements Runnable {
                        C33191() {
                        }

                        public void run() {
                            if (SecretMediaViewer.this.photoAnimationEndRunnable != null) {
                                SecretMediaViewer.this.photoAnimationEndRunnable.run();
                                SecretMediaViewer.this.photoAnimationEndRunnable = null;
                            }
                        }
                    }

                    public void onAnimationEnd(Animator animation) {
                        if (object != null) {
                            object.imageReceiver.setVisible(true, true);
                        }
                        SecretMediaViewer.this.isVisible = false;
                        AndroidUtilities.runOnUIThread(new C33191());
                    }
                });
                this.photoTransitionAnimationStartTime = System.currentTimeMillis();
                if (VERSION.SDK_INT >= 18) {
                    this.containerView.setLayerType(2, null);
                }
                this.imageMoveAnimation.start();
                return;
            }
            AnimatorSet animatorSet2 = new AnimatorSet();
            Animator[] animatorArr2 = new Animator[4];
            animatorArr2[0] = ObjectAnimator.ofFloat(this.containerView, "scaleX", new float[]{0.9f});
            animatorArr2[1] = ObjectAnimator.ofFloat(this.containerView, "scaleY", new float[]{0.9f});
            animatorArr2[2] = ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0});
            animatorArr2[3] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{0.0f});
            animatorSet2.playTogether(animatorArr2);
            this.photoAnimationInProgress = 2;
            this.photoAnimationEndRunnable = new Runnable() {
                public void run() {
                    if (SecretMediaViewer.this.containerView != null) {
                        if (VERSION.SDK_INT >= 18) {
                            SecretMediaViewer.this.containerView.setLayerType(0, null);
                        }
                        SecretMediaViewer.this.containerView.setVisibility(4);
                        SecretMediaViewer.this.photoAnimationInProgress = 0;
                        SecretMediaViewer.this.onPhotoClosed(object);
                        SecretMediaViewer.this.containerView.setScaleX(1.0f);
                        SecretMediaViewer.this.containerView.setScaleY(1.0f);
                    }
                }
            };
            animatorSet2.setDuration(200);
            animatorSet2.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    if (SecretMediaViewer.this.photoAnimationEndRunnable != null) {
                        SecretMediaViewer.this.photoAnimationEndRunnable.run();
                        SecretMediaViewer.this.photoAnimationEndRunnable = null;
                    }
                }
            });
            this.photoTransitionAnimationStartTime = System.currentTimeMillis();
            if (VERSION.SDK_INT >= 18) {
                this.containerView.setLayerType(2, null);
            }
            animatorSet2.start();
        }
    }

    private void onPhotoClosed(PlaceProviderObject object) {
        this.isVisible = false;
        this.currentProvider = null;
        this.disableShowCheck = false;
        releasePlayer();
        ArrayList<File> filesToDelete = new ArrayList();
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                SecretMediaViewer.this.centerImage.setImageBitmap((Bitmap) null);
                try {
                    if (SecretMediaViewer.this.windowView.getParent() != null) {
                        ((WindowManager) SecretMediaViewer.this.parentActivity.getSystemService("window")).removeView(SecretMediaViewer.this.windowView);
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
                SecretMediaViewer.this.isPhotoVisible = false;
            }
        }, 50);
    }

    private void updateMinMax(float scale) {
        int maxW = ((int) ((((float) this.centerImage.getImageWidth()) * scale) - ((float) getContainerViewWidth()))) / 2;
        int maxH = ((int) ((((float) this.centerImage.getImageHeight()) * scale) - ((float) getContainerViewHeight()))) / 2;
        if (maxW > 0) {
            this.minX = (float) (-maxW);
            this.maxX = (float) maxW;
        } else {
            this.maxX = 0.0f;
            this.minX = 0.0f;
        }
        if (maxH > 0) {
            this.minY = (float) (-maxH);
            this.maxY = (float) maxH;
            return;
        }
        this.maxY = 0.0f;
        this.minY = 0.0f;
    }

    private int getContainerViewWidth() {
        return this.containerView.getWidth();
    }

    private int getContainerViewHeight() {
        return this.containerView.getHeight();
    }

    private boolean processTouchEvent(MotionEvent ev) {
        if (this.photoAnimationInProgress != 0 || this.animationStartTime != 0) {
            return false;
        }
        if (ev.getPointerCount() == 1 && this.gestureDetector.onTouchEvent(ev) && this.doubleTap) {
            this.doubleTap = false;
            this.moving = false;
            this.zooming = false;
            checkMinMax(false);
            return true;
        }
        if (ev.getActionMasked() == 0 || ev.getActionMasked() == 5) {
            this.discardTap = false;
            if (!this.scroller.isFinished()) {
                this.scroller.abortAnimation();
            }
            if (!this.draggingDown) {
                if (ev.getPointerCount() == 2) {
                    this.pinchStartDistance = (float) Math.hypot((double) (ev.getX(1) - ev.getX(0)), (double) (ev.getY(1) - ev.getY(0)));
                    this.pinchStartScale = this.scale;
                    this.pinchCenterX = (ev.getX(0) + ev.getX(1)) / 2.0f;
                    this.pinchCenterY = (ev.getY(0) + ev.getY(1)) / 2.0f;
                    this.pinchStartX = this.translationX;
                    this.pinchStartY = this.translationY;
                    this.zooming = true;
                    this.moving = false;
                    if (this.velocityTracker != null) {
                        this.velocityTracker.clear();
                    }
                } else if (ev.getPointerCount() == 1) {
                    this.moveStartX = ev.getX();
                    float y = ev.getY();
                    this.moveStartY = y;
                    this.dragY = y;
                    this.draggingDown = false;
                    this.canDragDown = true;
                    if (this.velocityTracker != null) {
                        this.velocityTracker.clear();
                    }
                }
            }
        } else if (ev.getActionMasked() == 2) {
            if (ev.getPointerCount() == 2 && !this.draggingDown && this.zooming) {
                this.discardTap = true;
                this.scale = (((float) Math.hypot((double) (ev.getX(1) - ev.getX(0)), (double) (ev.getY(1) - ev.getY(0)))) / this.pinchStartDistance) * this.pinchStartScale;
                this.translationX = (this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - (((this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - this.pinchStartX) * (this.scale / this.pinchStartScale));
                this.translationY = (this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - (((this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - this.pinchStartY) * (this.scale / this.pinchStartScale));
                updateMinMax(this.scale);
                this.containerView.invalidate();
            } else if (ev.getPointerCount() == 1) {
                if (this.velocityTracker != null) {
                    this.velocityTracker.addMovement(ev);
                }
                float dx = Math.abs(ev.getX() - this.moveStartX);
                float dy = Math.abs(ev.getY() - this.dragY);
                if (dx > ((float) AndroidUtilities.dp(3.0f)) || dy > ((float) AndroidUtilities.dp(3.0f))) {
                    this.discardTap = true;
                }
                if (this.canDragDown && !this.draggingDown && this.scale == 1.0f && dy >= ((float) AndroidUtilities.dp(30.0f)) && dy / 2.0f > dx) {
                    this.draggingDown = true;
                    this.moving = false;
                    this.dragY = ev.getY();
                    if (this.isActionBarVisible) {
                        toggleActionBar(false, true);
                    }
                    return true;
                } else if (this.draggingDown) {
                    this.translationY = ev.getY() - this.dragY;
                    this.containerView.invalidate();
                } else if (this.invalidCoords || this.animationStartTime != 0) {
                    this.invalidCoords = false;
                    this.moveStartX = ev.getX();
                    this.moveStartY = ev.getY();
                } else {
                    float moveDx = this.moveStartX - ev.getX();
                    float moveDy = this.moveStartY - ev.getY();
                    if (this.moving || ((this.scale == 1.0f && Math.abs(moveDy) + ((float) AndroidUtilities.dp(12.0f)) < Math.abs(moveDx)) || this.scale != 1.0f)) {
                        if (!this.moving) {
                            moveDx = 0.0f;
                            moveDy = 0.0f;
                            this.moving = true;
                            this.canDragDown = false;
                        }
                        this.moveStartX = ev.getX();
                        this.moveStartY = ev.getY();
                        updateMinMax(this.scale);
                        if (this.translationX < this.minX || this.translationX > this.maxX) {
                            moveDx /= 3.0f;
                        }
                        if (this.maxY == 0.0f && this.minY == 0.0f) {
                            if (this.translationY - moveDy < this.minY) {
                                this.translationY = this.minY;
                                moveDy = 0.0f;
                            } else if (this.translationY - moveDy > this.maxY) {
                                this.translationY = this.maxY;
                                moveDy = 0.0f;
                            }
                        } else if (this.translationY < this.minY || this.translationY > this.maxY) {
                            moveDy /= 3.0f;
                        }
                        this.translationX -= moveDx;
                        if (this.scale != 1.0f) {
                            this.translationY -= moveDy;
                        }
                        this.containerView.invalidate();
                    }
                }
            }
        } else if (ev.getActionMasked() == 3 || ev.getActionMasked() == 1 || ev.getActionMasked() == 6) {
            if (this.zooming) {
                this.invalidCoords = true;
                if (this.scale < 1.0f) {
                    updateMinMax(1.0f);
                    animateTo(1.0f, 0.0f, 0.0f, true);
                } else if (this.scale > 3.0f) {
                    float atx = (this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - (((this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - this.pinchStartX) * (3.0f / this.pinchStartScale));
                    float aty = (this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - (((this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - this.pinchStartY) * (3.0f / this.pinchStartScale));
                    updateMinMax(3.0f);
                    if (atx < this.minX) {
                        atx = this.minX;
                    } else if (atx > this.maxX) {
                        atx = this.maxX;
                    }
                    if (aty < this.minY) {
                        aty = this.minY;
                    } else if (aty > this.maxY) {
                        aty = this.maxY;
                    }
                    animateTo(3.0f, atx, aty, true);
                } else {
                    checkMinMax(true);
                }
                this.zooming = false;
            } else if (this.draggingDown) {
                if (Math.abs(this.dragY - ev.getY()) > ((float) getContainerViewHeight()) / 6.0f) {
                    closePhoto(true, false);
                } else {
                    animateTo(1.0f, 0.0f, 0.0f, false);
                }
                this.draggingDown = false;
            } else if (this.moving) {
                float moveToX = this.translationX;
                float moveToY = this.translationY;
                updateMinMax(this.scale);
                this.moving = false;
                this.canDragDown = true;
                if (this.velocityTracker != null && this.scale == 1.0f) {
                    this.velocityTracker.computeCurrentVelocity(1000);
                }
                if (this.translationX < this.minX) {
                    moveToX = this.minX;
                } else if (this.translationX > this.maxX) {
                    moveToX = this.maxX;
                }
                if (this.translationY < this.minY) {
                    moveToY = this.minY;
                } else if (this.translationY > this.maxY) {
                    moveToY = this.maxY;
                }
                animateTo(this.scale, moveToX, moveToY, false);
            }
        }
        return false;
    }

    private void checkMinMax(boolean zoom) {
        float moveToX = this.translationX;
        float moveToY = this.translationY;
        updateMinMax(this.scale);
        if (this.translationX < this.minX) {
            moveToX = this.minX;
        } else if (this.translationX > this.maxX) {
            moveToX = this.maxX;
        }
        if (this.translationY < this.minY) {
            moveToY = this.minY;
        } else if (this.translationY > this.maxY) {
            moveToY = this.maxY;
        }
        animateTo(this.scale, moveToX, moveToY, zoom);
    }

    private void animateTo(float newScale, float newTx, float newTy, boolean isZoom) {
        animateTo(newScale, newTx, newTy, isZoom, 250);
    }

    private void animateTo(float newScale, float newTx, float newTy, boolean isZoom, int duration) {
        if (this.scale != newScale || this.translationX != newTx || this.translationY != newTy) {
            this.zoomAnimation = isZoom;
            this.animateToScale = newScale;
            this.animateToX = newTx;
            this.animateToY = newTy;
            this.animationStartTime = System.currentTimeMillis();
            this.imageMoveAnimation = new AnimatorSet();
            this.imageMoveAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(this, "animationValue", new float[]{0.0f, 1.0f})});
            this.imageMoveAnimation.setInterpolator(this.interpolator);
            this.imageMoveAnimation.setDuration((long) duration);
            this.imageMoveAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    SecretMediaViewer.this.imageMoveAnimation = null;
                    SecretMediaViewer.this.containerView.invalidate();
                }
            });
            this.imageMoveAnimation.start();
        }
    }

    public void setAnimationValue(float value) {
        this.animationValue = value;
        this.containerView.invalidate();
    }

    public float getAnimationValue() {
        return this.animationValue;
    }

    public boolean onDown(MotionEvent e) {
        return false;
    }

    public void onShowPress(MotionEvent e) {
    }

    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    public void onLongPress(MotionEvent e) {
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (this.scale != 1.0f) {
            this.scroller.abortAnimation();
            this.scroller.fling(Math.round(this.translationX), Math.round(this.translationY), Math.round(velocityX), Math.round(velocityY), (int) this.minX, (int) this.maxX, (int) this.minY, (int) this.maxY);
            this.containerView.postInvalidate();
        }
        return false;
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {
        boolean z = false;
        if (this.discardTap) {
            return false;
        }
        if (!this.isActionBarVisible) {
            z = true;
        }
        toggleActionBar(z, true);
        return true;
    }

    public boolean onDoubleTap(MotionEvent e) {
        if ((this.scale == 1.0f && (this.translationY != 0.0f || this.translationX != 0.0f)) || this.animationStartTime != 0 || this.photoAnimationInProgress != 0) {
            return false;
        }
        if (this.scale == 1.0f) {
            float atx = (e.getX() - ((float) (getContainerViewWidth() / 2))) - (((e.getX() - ((float) (getContainerViewWidth() / 2))) - this.translationX) * (3.0f / this.scale));
            float aty = (e.getY() - ((float) (getContainerViewHeight() / 2))) - (((e.getY() - ((float) (getContainerViewHeight() / 2))) - this.translationY) * (3.0f / this.scale));
            updateMinMax(3.0f);
            if (atx < this.minX) {
                atx = this.minX;
            } else if (atx > this.maxX) {
                atx = this.maxX;
            }
            if (aty < this.minY) {
                aty = this.minY;
            } else if (aty > this.maxY) {
                aty = this.maxY;
            }
            animateTo(3.0f, atx, aty, true);
        } else {
            animateTo(1.0f, 0.0f, 0.0f, true);
        }
        this.doubleTap = true;
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    private boolean scaleToFill() {
        return false;
    }
}
