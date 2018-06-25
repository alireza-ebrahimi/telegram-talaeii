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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.ui.AspectRatioFrameLayout;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$TL_documentEmpty;
import org.telegram.tgnet.TLRPC$TL_photoEmpty;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.Message;
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

public class SecretMediaViewer implements OnDoubleTapListener, OnGestureListener, NotificationCenterDelegate {
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
    private PhotoBackgroundDrawable photoBackgroundDrawable = new PhotoBackgroundDrawable(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
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
    class C51591 implements VideoPlayerDelegate {
        C51591() {
        }

        public void onError(Exception exception) {
            FileLog.e(exception);
        }

        public void onRenderedFirstFrame() {
            if (!SecretMediaViewer.this.textureUploaded) {
                SecretMediaViewer.this.textureUploaded = true;
                SecretMediaViewer.this.containerView.invalidate();
            }
        }

        public void onStateChanged(boolean z, int i) {
            if (SecretMediaViewer.this.videoPlayer != null && SecretMediaViewer.this.currentMessageObject != null) {
                if (i == 4 || i == 1) {
                    try {
                        SecretMediaViewer.this.parentActivity.getWindow().clearFlags(128);
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                } else {
                    try {
                        SecretMediaViewer.this.parentActivity.getWindow().addFlags(128);
                    } catch (Throwable e2) {
                        FileLog.e(e2);
                    }
                }
                if (i == 3 && SecretMediaViewer.this.aspectRatioFrameLayout.getVisibility() != 0) {
                    SecretMediaViewer.this.aspectRatioFrameLayout.setVisibility(0);
                }
                if (!SecretMediaViewer.this.videoPlayer.isPlaying() || i == 4) {
                    if (SecretMediaViewer.this.isPlaying) {
                        SecretMediaViewer.this.isPlaying = false;
                        if (i == 4) {
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

        public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        public void onVideoSizeChanged(int i, int i2, int i3, float f) {
            if (SecretMediaViewer.this.aspectRatioFrameLayout != null) {
                if (i3 == 90 || i3 == 270) {
                    int i4 = i;
                    i = i2;
                    i2 = i4;
                }
                SecretMediaViewer.this.aspectRatioFrameLayout.setAspectRatio(i2 == 0 ? 1.0f : (((float) i) * f) / ((float) i2), i3);
            }
        }
    }

    private class FrameLayoutDrawer extends FrameLayout {
        public FrameLayoutDrawer(Context context) {
            super(context);
            setWillNotDraw(false);
        }

        protected boolean drawChild(Canvas canvas, View view, long j) {
            return view != SecretMediaViewer.this.aspectRatioFrameLayout && super.drawChild(canvas, view, j);
        }

        protected void onDraw(Canvas canvas) {
            SecretMediaViewer.this.onDraw(canvas);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            SecretMediaViewer.this.processTouchEvent(motionEvent);
            return true;
        }
    }

    /* renamed from: org.telegram.ui.SecretMediaViewer$4 */
    class C51624 implements OnApplyWindowInsetsListener {
        C51624() {
        }

        @SuppressLint({"NewApi"})
        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            WindowInsets windowInsets2 = (WindowInsets) SecretMediaViewer.this.lastInsets;
            SecretMediaViewer.this.lastInsets = windowInsets;
            if (windowInsets2 == null || !windowInsets2.toString().equals(windowInsets.toString())) {
                SecretMediaViewer.this.windowView.requestLayout();
            }
            return windowInsets.consumeSystemWindowInsets();
        }
    }

    /* renamed from: org.telegram.ui.SecretMediaViewer$5 */
    class C51635 extends ActionBarMenuOnItemClick {
        C51635() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                SecretMediaViewer.this.closePhoto(true, false);
            }
        }
    }

    /* renamed from: org.telegram.ui.SecretMediaViewer$6 */
    class C51646 implements Runnable {
        C51646() {
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
    class C51657 extends AnimatorListenerAdapter {
        C51657() {
        }

        public void onAnimationEnd(Animator animator) {
            if (SecretMediaViewer.this.photoAnimationEndRunnable != null) {
                SecretMediaViewer.this.photoAnimationEndRunnable.run();
                SecretMediaViewer.this.photoAnimationEndRunnable = null;
            }
        }
    }

    /* renamed from: org.telegram.ui.SecretMediaViewer$9 */
    class C51679 extends AnimatorListenerAdapter {
        C51679() {
        }

        public void onAnimationEnd(Animator animator) {
            if (SecretMediaViewer.this.currentActionBarAnimation != null && SecretMediaViewer.this.currentActionBarAnimation.equals(animator)) {
                SecretMediaViewer.this.actionBar.setVisibility(8);
                SecretMediaViewer.this.currentActionBarAnimation = null;
            }
        }
    }

    private class PhotoBackgroundDrawable extends ColorDrawable {
        private Runnable drawRunnable;
        private int frame;

        public PhotoBackgroundDrawable(int i) {
            super(i);
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

        public void setAlpha(int i) {
            if (SecretMediaViewer.this.parentActivity instanceof LaunchActivity) {
                DrawerLayoutContainer drawerLayoutContainer = ((LaunchActivity) SecretMediaViewer.this.parentActivity).drawerLayoutContainer;
                boolean z = (SecretMediaViewer.this.isPhotoVisible && i == 255) ? false : true;
                drawerLayoutContainer.setAllowDrawContent(z);
            }
            super.setAlpha(i);
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
        final /* synthetic */ SecretMediaViewer this$0;
        private boolean useVideoProgress;

        private class Particle {
            float alpha;
            float currentTime;
            float lifeTime;
            float velocity;
            float vx;
            float vy;
            /* renamed from: x */
            float f10200x;
            /* renamed from: y */
            float f10201y;

            private Particle() {
            }
        }

        public SecretDeleteTimer(SecretMediaViewer secretMediaViewer, Context context) {
            int i = 0;
            this.this$0 = secretMediaViewer;
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
            this.circlePaint.setColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
            this.drawable = context.getResources().getDrawable(R.drawable.flame_small);
            while (i < 40) {
                this.freeParticles.add(new Particle());
                i++;
            }
        }

        private void setDestroyTime(long j, long j2, boolean z) {
            this.destroyTime = j;
            this.destroyTtl = j2;
            this.useVideoProgress = z;
            this.lastAnimationTime = System.currentTimeMillis();
            invalidate();
        }

        private void updateParticles(long j) {
            int size = this.particles.size();
            int i = 0;
            while (i < size) {
                int i2;
                Particle particle = (Particle) this.particles.get(i);
                if (particle.currentTime >= particle.lifeTime) {
                    if (this.freeParticles.size() < 40) {
                        this.freeParticles.add(particle);
                    }
                    this.particles.remove(i);
                    i2 = i - 1;
                    i = size - 1;
                } else {
                    particle.alpha = 1.0f - AndroidUtilities.decelerateInterpolator.getInterpolation(particle.currentTime / particle.lifeTime);
                    particle.f10200x += ((particle.vx * particle.velocity) * ((float) j)) / 500.0f;
                    particle.f10201y += ((particle.vy * particle.velocity) * ((float) j)) / 500.0f;
                    particle.currentTime += (float) j;
                    i2 = i;
                    i = size;
                }
                size = i;
                i = i2 + 1;
            }
        }

        @SuppressLint({"DrawAllocation"})
        protected void onDraw(Canvas canvas) {
            if (this.this$0.currentMessageObject != null && this.this$0.currentMessageObject.messageOwner.destroyTime != 0) {
                float max;
                long duration;
                Particle particle;
                canvas.drawCircle((float) (getMeasuredWidth() - AndroidUtilities.dp(35.0f)), (float) (getMeasuredHeight() / 2), (float) AndroidUtilities.dp(16.0f), this.circlePaint);
                if (!this.useVideoProgress) {
                    max = ((float) Math.max(0, this.destroyTime - (System.currentTimeMillis() + ((long) (ConnectionsManager.getInstance().getTimeDifference() * 1000))))) / (((float) this.destroyTtl) * 1000.0f);
                } else if (this.this$0.videoPlayer != null) {
                    duration = this.this$0.videoPlayer.getDuration();
                    long currentPosition = this.this$0.videoPlayer.getCurrentPosition();
                    max = (duration == C3446C.TIME_UNSET || currentPosition == C3446C.TIME_UNSET) ? 1.0f : 1.0f - (((float) currentPosition) / ((float) duration));
                } else {
                    max = 1.0f;
                }
                int measuredWidth = getMeasuredWidth() - AndroidUtilities.dp(40.0f);
                int measuredHeight = ((getMeasuredHeight() - AndroidUtilities.dp(14.0f)) / 2) - AndroidUtilities.dp(0.5f);
                this.drawable.setBounds(measuredWidth, measuredHeight, AndroidUtilities.dp(10.0f) + measuredWidth, AndroidUtilities.dp(14.0f) + measuredHeight);
                this.drawable.draw(canvas);
                float f = -360.0f * max;
                canvas.drawArc(this.deleteProgressRect, -90.0f, f, false, this.afterDeleteProgressPaint);
                measuredHeight = this.particles.size();
                for (measuredWidth = 0; measuredWidth < measuredHeight; measuredWidth++) {
                    particle = (Particle) this.particles.get(measuredWidth);
                    this.particlePaint.setAlpha((int) (255.0f * particle.alpha));
                    canvas.drawPoint(particle.f10200x, particle.f10201y, this.particlePaint);
                }
                double sin = Math.sin(0.017453292519943295d * ((double) (f - 90.0f)));
                double d = -Math.cos(0.017453292519943295d * ((double) (f - 90.0f)));
                int dp = AndroidUtilities.dp(14.0f);
                float centerX = (float) (((-d) * ((double) dp)) + ((double) this.deleteProgressRect.centerX()));
                float centerY = (float) ((((double) dp) * sin) + ((double) this.deleteProgressRect.centerY()));
                for (measuredWidth = 0; measuredWidth < 1; measuredWidth++) {
                    if (this.freeParticles.isEmpty()) {
                        particle = new Particle();
                    } else {
                        particle = (Particle) this.freeParticles.get(0);
                        this.freeParticles.remove(0);
                    }
                    particle.f10200x = centerX;
                    particle.f10201y = centerY;
                    double nextInt = 0.017453292519943295d * ((double) (Utilities.random.nextInt(140) - 70));
                    if (nextInt < 0.0d) {
                        nextInt += 6.283185307179586d;
                    }
                    particle.vx = (float) ((Math.cos(nextInt) * sin) - (Math.sin(nextInt) * d));
                    particle.vy = (float) ((Math.cos(nextInt) * d) + (Math.sin(nextInt) * sin));
                    particle.alpha = 1.0f;
                    particle.currentTime = BitmapDescriptorFactory.HUE_RED;
                    particle.lifeTime = (float) (Utilities.random.nextInt(100) + ChatActivity.scheduleDownloads);
                    particle.velocity = 20.0f + (Utilities.random.nextFloat() * 4.0f);
                    this.particles.add(particle);
                }
                duration = System.currentTimeMillis();
                updateParticles(duration - this.lastAnimationTime);
                this.lastAnimationTime = duration;
                invalidate();
            }
        }

        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            int measuredHeight = (getMeasuredHeight() / 2) - (AndroidUtilities.dp(28.0f) / 2);
            this.deleteProgressRect.set((float) (getMeasuredWidth() - AndroidUtilities.dp(49.0f)), (float) measuredHeight, (float) (getMeasuredWidth() - AndroidUtilities.dp(21.0f)), (float) (measuredHeight + AndroidUtilities.dp(28.0f)));
        }
    }

    private void animateTo(float f, float f2, float f3, boolean z) {
        animateTo(f, f2, f3, z, Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
    }

    private void animateTo(float f, float f2, float f3, boolean z, int i) {
        if (this.scale != f || this.translationX != f2 || this.translationY != f3) {
            this.zoomAnimation = z;
            this.animateToScale = f;
            this.animateToX = f2;
            this.animateToY = f3;
            this.animationStartTime = System.currentTimeMillis();
            this.imageMoveAnimation = new AnimatorSet();
            this.imageMoveAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(this, "animationValue", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
            this.imageMoveAnimation.setInterpolator(this.interpolator);
            this.imageMoveAnimation.setDuration((long) i);
            this.imageMoveAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    SecretMediaViewer.this.imageMoveAnimation = null;
                    SecretMediaViewer.this.containerView.invalidate();
                }
            });
            this.imageMoveAnimation.start();
        }
    }

    private void checkMinMax(boolean z) {
        float f = this.translationX;
        float f2 = this.translationY;
        updateMinMax(this.scale);
        if (this.translationX < this.minX) {
            f = this.minX;
        } else if (this.translationX > this.maxX) {
            f = this.maxX;
        }
        if (this.translationY < this.minY) {
            f2 = this.minY;
        } else if (this.translationY > this.maxY) {
            f2 = this.maxY;
        }
        animateTo(this.scale, f, f2, z);
    }

    private boolean checkPhotoAnimation() {
        if (this.photoAnimationInProgress != 0 && Math.abs(this.photoTransitionAnimationStartTime - System.currentTimeMillis()) >= 500) {
            if (this.photoAnimationEndRunnable != null) {
                this.photoAnimationEndRunnable.run();
                this.photoAnimationEndRunnable = null;
            }
            this.photoAnimationInProgress = 0;
        }
        return this.photoAnimationInProgress != 0;
    }

    private int getContainerViewHeight() {
        return this.containerView.getHeight();
    }

    private int getContainerViewWidth() {
        return this.containerView.getWidth();
    }

    public static SecretMediaViewer getInstance() {
        SecretMediaViewer secretMediaViewer = Instance;
        if (secretMediaViewer == null) {
            synchronized (PhotoViewer.class) {
                secretMediaViewer = Instance;
                if (secretMediaViewer == null) {
                    secretMediaViewer = new SecretMediaViewer();
                    Instance = secretMediaViewer;
                }
            }
        }
        return secretMediaViewer;
    }

    private void onDraw(Canvas canvas) {
        if (this.isPhotoVisible) {
            float f;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            Object obj;
            int bitmapWidth;
            int bitmapHeight;
            long currentTimeMillis;
            long j;
            float f8 = -1.0f;
            if (this.imageMoveAnimation != null) {
                if (!this.scroller.isFinished()) {
                    this.scroller.abortAnimation();
                }
                if (this.useOvershootForScale) {
                    if (this.animationValue < 0.9f) {
                        f = this.animationValue / 0.9f;
                        f2 = this.scale + (((this.animateToScale * 1.02f) - this.scale) * f);
                    } else {
                        f = 1.0f;
                        f2 = this.animateToScale + ((this.animateToScale * 0.01999998f) * (1.0f - ((this.animationValue - 0.9f) / 0.100000024f)));
                    }
                    f3 = this.translationY + ((this.animateToY - this.translationY) * f);
                    f4 = this.translationX + ((this.animateToX - this.translationX) * f);
                    f5 = ((this.animateToClipTop - this.clipTop) * f) + this.clipTop;
                    f6 = this.clipBottom + ((this.animateToClipBottom - this.clipBottom) * f);
                    f = (f * (this.animateToClipHorizontal - this.clipHorizontal)) + this.clipHorizontal;
                    float f9 = f6;
                    f6 = f5;
                    f5 = f2;
                    f2 = f9;
                } else {
                    f5 = this.scale + ((this.animateToScale - this.scale) * this.animationValue);
                    f3 = this.translationY + ((this.animateToY - this.translationY) * this.animationValue);
                    f4 = this.translationX + ((this.animateToX - this.translationX) * this.animationValue);
                    f6 = this.clipTop + ((this.animateToClipTop - this.clipTop) * this.animationValue);
                    f2 = ((this.animateToClipBottom - this.clipBottom) * this.animationValue) + this.clipBottom;
                    f = this.clipHorizontal + ((this.animateToClipHorizontal - this.clipHorizontal) * this.animationValue);
                }
                if (this.animateToScale == 1.0f && this.scale == 1.0f && this.translationX == BitmapDescriptorFactory.HUE_RED) {
                    f8 = f3;
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
                f5 = this.scale;
                f3 = this.translationY;
                f4 = this.translationX;
                f6 = this.clipTop;
                f2 = this.clipBottom;
                f = this.clipHorizontal;
                if (!this.moving) {
                    f8 = this.translationY;
                }
            }
            if (this.photoAnimationInProgress != 3) {
                if (this.scale != 1.0f || f8 == -1.0f || this.zoomAnimation) {
                    this.photoBackgroundDrawable.setAlpha(255);
                } else {
                    float containerViewHeight = ((float) getContainerViewHeight()) / 4.0f;
                    this.photoBackgroundDrawable.setAlpha((int) Math.max(127.0f, (1.0f - (Math.min(Math.abs(f8), containerViewHeight) / containerViewHeight)) * 255.0f));
                }
                if (!this.zoomAnimation && f4 > this.maxX) {
                    f4 = Math.min(1.0f, (f4 - this.maxX) / ((float) canvas.getWidth()));
                    f8 = 0.3f * f4;
                    f4 = 1.0f - f4;
                    f7 = this.maxX;
                    obj = (this.aspectRatioFrameLayout == null && this.aspectRatioFrameLayout.getVisibility() == 0) ? 1 : null;
                    canvas.save();
                    f8 = f5 - f8;
                    canvas.translate(((float) (getContainerViewWidth() / 2)) + f7, f3 + ((float) (getContainerViewHeight() / 2)));
                    canvas.scale(f8, f8);
                    bitmapWidth = this.centerImage.getBitmapWidth();
                    bitmapHeight = this.centerImage.getBitmapHeight();
                    if (obj != null && this.textureUploaded && Math.abs((((float) bitmapWidth) / ((float) bitmapHeight)) - (((float) this.videoTextureView.getMeasuredWidth()) / ((float) this.videoTextureView.getMeasuredHeight()))) > 0.01f) {
                        bitmapWidth = this.videoTextureView.getMeasuredWidth();
                        bitmapHeight = this.videoTextureView.getMeasuredHeight();
                    }
                    f7 = Math.min(((float) getContainerViewHeight()) / ((float) bitmapHeight), ((float) getContainerViewWidth()) / ((float) bitmapWidth));
                    bitmapWidth = (int) (((float) bitmapWidth) * f7);
                    bitmapHeight = (int) (((float) bitmapHeight) * f7);
                    canvas.clipRect(((float) ((-bitmapWidth) / 2)) + (f / f8), (f6 / f8) + ((float) ((-bitmapHeight) / 2)), ((float) (bitmapWidth / 2)) - (f / f8), ((float) (bitmapHeight / 2)) - (f2 / f8));
                    if (!(obj != null && this.textureUploaded && this.videoCrossfadeStarted && this.videoCrossfadeAlpha == 1.0f)) {
                        this.centerImage.setAlpha(f4);
                        this.centerImage.setImageCoords((-bitmapWidth) / 2, (-bitmapHeight) / 2, bitmapWidth, bitmapHeight);
                        this.centerImage.draw(canvas);
                    }
                    if (obj != null) {
                        if (!this.videoCrossfadeStarted && this.textureUploaded) {
                            this.videoCrossfadeStarted = true;
                            this.videoCrossfadeAlpha = BitmapDescriptorFactory.HUE_RED;
                            this.videoCrossfadeAlphaLastTime = System.currentTimeMillis();
                        }
                        canvas.translate((float) ((-bitmapWidth) / 2), (float) ((-bitmapHeight) / 2));
                        this.videoTextureView.setAlpha(this.videoCrossfadeAlpha * f4);
                        this.aspectRatioFrameLayout.draw(canvas);
                        if (this.videoCrossfadeStarted && this.videoCrossfadeAlpha < 1.0f) {
                            currentTimeMillis = System.currentTimeMillis();
                            j = currentTimeMillis - this.videoCrossfadeAlphaLastTime;
                            this.videoCrossfadeAlphaLastTime = currentTimeMillis;
                            this.videoCrossfadeAlpha += ((float) j) / 200.0f;
                            this.containerView.invalidate();
                            if (this.videoCrossfadeAlpha > 1.0f) {
                                this.videoCrossfadeAlpha = 1.0f;
                            }
                        }
                    }
                    canvas.restore();
                }
            }
            f8 = BitmapDescriptorFactory.HUE_RED;
            f7 = f4;
            f4 = 1.0f;
            if (this.aspectRatioFrameLayout == null) {
            }
            canvas.save();
            f8 = f5 - f8;
            canvas.translate(((float) (getContainerViewWidth() / 2)) + f7, f3 + ((float) (getContainerViewHeight() / 2)));
            canvas.scale(f8, f8);
            bitmapWidth = this.centerImage.getBitmapWidth();
            bitmapHeight = this.centerImage.getBitmapHeight();
            bitmapWidth = this.videoTextureView.getMeasuredWidth();
            bitmapHeight = this.videoTextureView.getMeasuredHeight();
            f7 = Math.min(((float) getContainerViewHeight()) / ((float) bitmapHeight), ((float) getContainerViewWidth()) / ((float) bitmapWidth));
            bitmapWidth = (int) (((float) bitmapWidth) * f7);
            bitmapHeight = (int) (((float) bitmapHeight) * f7);
            canvas.clipRect(((float) ((-bitmapWidth) / 2)) + (f / f8), (f6 / f8) + ((float) ((-bitmapHeight) / 2)), ((float) (bitmapWidth / 2)) - (f / f8), ((float) (bitmapHeight / 2)) - (f2 / f8));
            this.centerImage.setAlpha(f4);
            this.centerImage.setImageCoords((-bitmapWidth) / 2, (-bitmapHeight) / 2, bitmapWidth, bitmapHeight);
            this.centerImage.draw(canvas);
            if (obj != null) {
                this.videoCrossfadeStarted = true;
                this.videoCrossfadeAlpha = BitmapDescriptorFactory.HUE_RED;
                this.videoCrossfadeAlphaLastTime = System.currentTimeMillis();
                canvas.translate((float) ((-bitmapWidth) / 2), (float) ((-bitmapHeight) / 2));
                this.videoTextureView.setAlpha(this.videoCrossfadeAlpha * f4);
                this.aspectRatioFrameLayout.draw(canvas);
                currentTimeMillis = System.currentTimeMillis();
                j = currentTimeMillis - this.videoCrossfadeAlphaLastTime;
                this.videoCrossfadeAlphaLastTime = currentTimeMillis;
                this.videoCrossfadeAlpha += ((float) j) / 200.0f;
                this.containerView.invalidate();
                if (this.videoCrossfadeAlpha > 1.0f) {
                    this.videoCrossfadeAlpha = 1.0f;
                }
            }
            canvas.restore();
        }
    }

    private void onPhotoClosed(PlaceProviderObject placeProviderObject) {
        this.isVisible = false;
        this.currentProvider = null;
        this.disableShowCheck = false;
        releasePlayer();
        ArrayList arrayList = new ArrayList();
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                SecretMediaViewer.this.centerImage.setImageBitmap((Bitmap) null);
                try {
                    if (SecretMediaViewer.this.windowView.getParent() != null) {
                        ((WindowManager) SecretMediaViewer.this.parentActivity.getSystemService("window")).removeView(SecretMediaViewer.this.windowView);
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                SecretMediaViewer.this.isPhotoVisible = false;
            }
        }, 50);
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
            this.videoCrossfadeAlpha = BitmapDescriptorFactory.HUE_RED;
            textureView.setAlpha(BitmapDescriptorFactory.HUE_RED);
            if (this.videoPlayer == null) {
                this.videoPlayer = new VideoPlayer();
                this.videoPlayer.setTextureView(this.videoTextureView);
                this.videoPlayer.setDelegate(new C51591());
            }
            this.videoPlayer.preparePlayer(Uri.fromFile(file), "other");
            this.videoPlayer.setPlayWhenReady(true);
        }
    }

    private boolean processTouchEvent(MotionEvent motionEvent) {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (this.photoAnimationInProgress != 0 || this.animationStartTime != 0) {
            return false;
        }
        if (motionEvent.getPointerCount() == 1 && this.gestureDetector.onTouchEvent(motionEvent) && this.doubleTap) {
            this.doubleTap = false;
            this.moving = false;
            this.zooming = false;
            checkMinMax(false);
            return true;
        }
        float y;
        if (motionEvent.getActionMasked() == 0 || motionEvent.getActionMasked() == 5) {
            this.discardTap = false;
            if (!this.scroller.isFinished()) {
                this.scroller.abortAnimation();
            }
            if (!this.draggingDown) {
                if (motionEvent.getPointerCount() == 2) {
                    this.pinchStartDistance = (float) Math.hypot((double) (motionEvent.getX(1) - motionEvent.getX(0)), (double) (motionEvent.getY(1) - motionEvent.getY(0)));
                    this.pinchStartScale = this.scale;
                    this.pinchCenterX = (motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f;
                    this.pinchCenterY = (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f;
                    this.pinchStartX = this.translationX;
                    this.pinchStartY = this.translationY;
                    this.zooming = true;
                    this.moving = false;
                    if (this.velocityTracker != null) {
                        this.velocityTracker.clear();
                    }
                } else if (motionEvent.getPointerCount() == 1) {
                    this.moveStartX = motionEvent.getX();
                    y = motionEvent.getY();
                    this.moveStartY = y;
                    this.dragY = y;
                    this.draggingDown = false;
                    this.canDragDown = true;
                    if (this.velocityTracker != null) {
                        this.velocityTracker.clear();
                    }
                }
            }
        } else if (motionEvent.getActionMasked() == 2) {
            if (motionEvent.getPointerCount() == 2 && !this.draggingDown && this.zooming) {
                this.discardTap = true;
                this.scale = (((float) Math.hypot((double) (motionEvent.getX(1) - motionEvent.getX(0)), (double) (motionEvent.getY(1) - motionEvent.getY(0)))) / this.pinchStartDistance) * this.pinchStartScale;
                this.translationX = (this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - (((this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - this.pinchStartX) * (this.scale / this.pinchStartScale));
                this.translationY = (this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - (((this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - this.pinchStartY) * (this.scale / this.pinchStartScale));
                updateMinMax(this.scale);
                this.containerView.invalidate();
            } else if (motionEvent.getPointerCount() == 1) {
                if (this.velocityTracker != null) {
                    this.velocityTracker.addMovement(motionEvent);
                }
                y = Math.abs(motionEvent.getX() - this.moveStartX);
                float abs = Math.abs(motionEvent.getY() - this.dragY);
                if (y > ((float) AndroidUtilities.dp(3.0f)) || abs > ((float) AndroidUtilities.dp(3.0f))) {
                    this.discardTap = true;
                }
                if (this.canDragDown && !this.draggingDown && this.scale == 1.0f && abs >= ((float) AndroidUtilities.dp(30.0f)) && abs / 2.0f > y) {
                    this.draggingDown = true;
                    this.moving = false;
                    this.dragY = motionEvent.getY();
                    if (this.isActionBarVisible) {
                        toggleActionBar(false, true);
                    }
                    return true;
                } else if (this.draggingDown) {
                    this.translationY = motionEvent.getY() - this.dragY;
                    this.containerView.invalidate();
                } else if (this.invalidCoords || this.animationStartTime != 0) {
                    this.invalidCoords = false;
                    this.moveStartX = motionEvent.getX();
                    this.moveStartY = motionEvent.getY();
                } else {
                    abs = this.moveStartX - motionEvent.getX();
                    y = this.moveStartY - motionEvent.getY();
                    if (this.moving || ((this.scale == 1.0f && Math.abs(y) + ((float) AndroidUtilities.dp(12.0f)) < Math.abs(abs)) || this.scale != 1.0f)) {
                        if (!this.moving) {
                            this.moving = true;
                            this.canDragDown = false;
                            y = BitmapDescriptorFactory.HUE_RED;
                            abs = BitmapDescriptorFactory.HUE_RED;
                        }
                        this.moveStartX = motionEvent.getX();
                        this.moveStartY = motionEvent.getY();
                        updateMinMax(this.scale);
                        if (this.translationX < this.minX || this.translationX > this.maxX) {
                            abs /= 3.0f;
                        }
                        if (this.maxY != BitmapDescriptorFactory.HUE_RED || this.minY != BitmapDescriptorFactory.HUE_RED) {
                            if (this.translationY < this.minY || this.translationY > this.maxY) {
                                f = y / 3.0f;
                            }
                            f = y;
                        } else if (this.translationY - y < this.minY) {
                            this.translationY = this.minY;
                        } else {
                            if (this.translationY - y > this.maxY) {
                                this.translationY = this.maxY;
                            }
                            f = y;
                        }
                        this.translationX -= abs;
                        if (this.scale != 1.0f) {
                            this.translationY -= f;
                        }
                        this.containerView.invalidate();
                    }
                }
            }
        } else if (motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 6) {
            if (this.zooming) {
                this.invalidCoords = true;
                if (this.scale < 1.0f) {
                    updateMinMax(1.0f);
                    animateTo(1.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, true);
                } else if (this.scale > 3.0f) {
                    y = (this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - (((this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - this.pinchStartX) * (3.0f / this.pinchStartScale));
                    f = (this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - (((this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - this.pinchStartY) * (3.0f / this.pinchStartScale));
                    updateMinMax(3.0f);
                    if (y < this.minX) {
                        y = this.minX;
                    } else if (y > this.maxX) {
                        y = this.maxX;
                    }
                    if (f < this.minY) {
                        f = this.minY;
                    } else if (f > this.maxY) {
                        f = this.maxY;
                    }
                    animateTo(3.0f, y, f, true);
                } else {
                    checkMinMax(true);
                }
                this.zooming = false;
            } else if (this.draggingDown) {
                if (Math.abs(this.dragY - motionEvent.getY()) > ((float) getContainerViewHeight()) / 6.0f) {
                    closePhoto(true, false);
                } else {
                    animateTo(1.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, false);
                }
                this.draggingDown = false;
            } else if (this.moving) {
                y = this.translationX;
                f = this.translationY;
                updateMinMax(this.scale);
                this.moving = false;
                this.canDragDown = true;
                if (this.velocityTracker != null && this.scale == 1.0f) {
                    this.velocityTracker.computeCurrentVelocity(1000);
                }
                if (this.translationX < this.minX) {
                    y = this.minX;
                } else if (this.translationX > this.maxX) {
                    y = this.maxX;
                }
                if (this.translationY < this.minY) {
                    f = this.minY;
                } else if (this.translationY > this.maxY) {
                    f = this.maxY;
                }
                animateTo(this.scale, y, f, false);
            }
        }
        return false;
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
        } catch (Throwable e) {
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

    private boolean scaleToFill() {
        return false;
    }

    private void toggleActionBar(boolean z, boolean z2) {
        float f = 1.0f;
        if (z) {
            this.actionBar.setVisibility(0);
        }
        this.actionBar.setEnabled(z);
        this.isActionBarVisible = z;
        if (z2) {
            Collection arrayList = new ArrayList();
            ActionBar actionBar = this.actionBar;
            String str = "alpha";
            float[] fArr = new float[1];
            if (!z) {
                f = BitmapDescriptorFactory.HUE_RED;
            }
            fArr[0] = f;
            arrayList.add(ObjectAnimator.ofFloat(actionBar, str, fArr));
            this.currentActionBarAnimation = new AnimatorSet();
            this.currentActionBarAnimation.playTogether(arrayList);
            if (!z) {
                this.currentActionBarAnimation.addListener(new C51679());
            }
            this.currentActionBarAnimation.setDuration(200);
            this.currentActionBarAnimation.start();
            return;
        }
        ActionBar actionBar2 = this.actionBar;
        if (!z) {
            f = BitmapDescriptorFactory.HUE_RED;
        }
        actionBar2.setAlpha(f);
        if (!z) {
            this.actionBar.setVisibility(8);
        }
    }

    private void updateMinMax(float f) {
        int imageWidth = ((int) ((((float) this.centerImage.getImageWidth()) * f) - ((float) getContainerViewWidth()))) / 2;
        int imageHeight = ((int) ((((float) this.centerImage.getImageHeight()) * f) - ((float) getContainerViewHeight()))) / 2;
        if (imageWidth > 0) {
            this.minX = (float) (-imageWidth);
            this.maxX = (float) imageWidth;
        } else {
            this.maxX = BitmapDescriptorFactory.HUE_RED;
            this.minX = BitmapDescriptorFactory.HUE_RED;
        }
        if (imageHeight > 0) {
            this.minY = (float) (-imageHeight);
            this.maxY = (float) imageHeight;
            return;
        }
        this.maxY = BitmapDescriptorFactory.HUE_RED;
        this.minY = BitmapDescriptorFactory.HUE_RED;
    }

    public void closePhoto(boolean z, boolean z2) {
        if (this.parentActivity != null && this.isPhotoVisible && !checkPhotoAnimation()) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesDeleted);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateMessageMedia);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didCreatedNewDeleteTask);
            this.isActionBarVisible = false;
            if (this.velocityTracker != null) {
                this.velocityTracker.recycle();
                this.velocityTracker = null;
            }
            this.closeTime = System.currentTimeMillis();
            final PlaceProviderObject placeForPhoto = (this.currentProvider == null || (this.currentMessageObject.messageOwner.media.photo instanceof TLRPC$TL_photoEmpty) || (this.currentMessageObject.messageOwner.media.document instanceof TLRPC$TL_documentEmpty)) ? null : this.currentProvider.getPlaceForPhoto(this.currentMessageObject, null, 0);
            if (this.videoPlayer != null) {
                this.videoPlayer.pause();
            }
            AnimatorSet animatorSet;
            Animator[] animatorArr;
            if (z) {
                this.photoAnimationInProgress = 3;
                this.containerView.invalidate();
                this.imageMoveAnimation = new AnimatorSet();
                int i;
                if (placeForPhoto == null || placeForPhoto.imageReceiver.getThumbBitmap() == null || z2) {
                    i = (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + AndroidUtilities.displaySize.y;
                    this.animateToY = this.translationY >= BitmapDescriptorFactory.HUE_RED ? (float) i : (float) (-i);
                } else {
                    placeForPhoto.imageReceiver.setVisible(false, true);
                    Rect drawRegion = placeForPhoto.imageReceiver.getDrawRegion();
                    float f = (float) (drawRegion.right - drawRegion.left);
                    float f2 = (float) (drawRegion.bottom - drawRegion.top);
                    int i2 = AndroidUtilities.displaySize.x;
                    i = (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + AndroidUtilities.displaySize.y;
                    this.animateToScale = Math.max(f / ((float) i2), f2 / ((float) i));
                    this.animateToX = ((f / 2.0f) + ((float) (placeForPhoto.viewX + drawRegion.left))) - ((float) (i2 / 2));
                    this.animateToY = (((float) (placeForPhoto.viewY + drawRegion.top)) + (f2 / 2.0f)) - ((float) (i / 2));
                    this.animateToClipHorizontal = (float) Math.abs(drawRegion.left - placeForPhoto.imageReceiver.getImageX());
                    int abs = Math.abs(drawRegion.top - placeForPhoto.imageReceiver.getImageY());
                    int[] iArr = new int[2];
                    placeForPhoto.parentView.getLocationInWindow(iArr);
                    this.animateToClipTop = (float) (((iArr[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight)) - (placeForPhoto.viewY + drawRegion.top)) + placeForPhoto.clipTopAddition);
                    if (this.animateToClipTop < BitmapDescriptorFactory.HUE_RED) {
                        this.animateToClipTop = BitmapDescriptorFactory.HUE_RED;
                    }
                    this.animateToClipBottom = (float) (((((int) f2) + (placeForPhoto.viewY + drawRegion.top)) - ((placeForPhoto.parentView.getHeight() + iArr[1]) - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight))) + placeForPhoto.clipBottomAddition);
                    if (this.animateToClipBottom < BitmapDescriptorFactory.HUE_RED) {
                        this.animateToClipBottom = BitmapDescriptorFactory.HUE_RED;
                    }
                    this.animationStartTime = System.currentTimeMillis();
                    this.animateToClipBottom = Math.max(this.animateToClipBottom, (float) abs);
                    this.animateToClipTop = Math.max(this.animateToClipTop, (float) abs);
                    this.zoomAnimation = true;
                }
                if (this.isVideo) {
                    this.videoCrossfadeStarted = false;
                    this.textureUploaded = false;
                    animatorSet = this.imageMoveAnimation;
                    animatorArr = new Animator[5];
                    animatorArr[0] = ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0});
                    animatorArr[1] = ObjectAnimator.ofFloat(this, "animationValue", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
                    animatorArr[2] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorArr[3] = ObjectAnimator.ofFloat(this.secretDeleteTimer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorArr[4] = ObjectAnimator.ofFloat(this, "videoCrossfadeAlpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorSet.playTogether(animatorArr);
                } else {
                    this.centerImage.setManualAlphaAnimator(true);
                    animatorSet = this.imageMoveAnimation;
                    animatorArr = new Animator[5];
                    animatorArr[0] = ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0});
                    animatorArr[1] = ObjectAnimator.ofFloat(this, "animationValue", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
                    animatorArr[2] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorArr[3] = ObjectAnimator.ofFloat(this.secretDeleteTimer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorArr[4] = ObjectAnimator.ofFloat(this.centerImage, "currentAlpha", new float[]{BitmapDescriptorFactory.HUE_RED});
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
                        SecretMediaViewer.this.onPhotoClosed(placeForPhoto);
                    }
                };
                this.imageMoveAnimation.setInterpolator(new DecelerateInterpolator());
                this.imageMoveAnimation.setDuration(250);
                this.imageMoveAnimation.addListener(new AnimatorListenerAdapter() {

                    /* renamed from: org.telegram.ui.SecretMediaViewer$11$1 */
                    class C51581 implements Runnable {
                        C51581() {
                        }

                        public void run() {
                            if (SecretMediaViewer.this.photoAnimationEndRunnable != null) {
                                SecretMediaViewer.this.photoAnimationEndRunnable.run();
                                SecretMediaViewer.this.photoAnimationEndRunnable = null;
                            }
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (placeForPhoto != null) {
                            placeForPhoto.imageReceiver.setVisible(true, true);
                        }
                        SecretMediaViewer.this.isVisible = false;
                        AndroidUtilities.runOnUIThread(new C51581());
                    }
                });
                this.photoTransitionAnimationStartTime = System.currentTimeMillis();
                if (VERSION.SDK_INT >= 18) {
                    this.containerView.setLayerType(2, null);
                }
                this.imageMoveAnimation.start();
                return;
            }
            animatorSet = new AnimatorSet();
            animatorArr = new Animator[4];
            animatorArr[0] = ObjectAnimator.ofFloat(this.containerView, "scaleX", new float[]{0.9f});
            animatorArr[1] = ObjectAnimator.ofFloat(this.containerView, "scaleY", new float[]{0.9f});
            animatorArr[2] = ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0});
            animatorArr[3] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorSet.playTogether(animatorArr);
            this.photoAnimationInProgress = 2;
            this.photoAnimationEndRunnable = new Runnable() {
                public void run() {
                    if (SecretMediaViewer.this.containerView != null) {
                        if (VERSION.SDK_INT >= 18) {
                            SecretMediaViewer.this.containerView.setLayerType(0, null);
                        }
                        SecretMediaViewer.this.containerView.setVisibility(4);
                        SecretMediaViewer.this.photoAnimationInProgress = 0;
                        SecretMediaViewer.this.onPhotoClosed(placeForPhoto);
                        SecretMediaViewer.this.containerView.setScaleX(1.0f);
                        SecretMediaViewer.this.containerView.setScaleY(1.0f);
                    }
                }
            };
            animatorSet.setDuration(200);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
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
            animatorSet.start();
        }
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
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
        Instance = null;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.messagesDeleted) {
            if (this.currentMessageObject == null || ((Integer) objArr[1]).intValue() != 0 || !((ArrayList) objArr[0]).contains(Integer.valueOf(this.currentMessageObject.getId()))) {
                return;
            }
            if (!this.isVideo || this.videoWatchedOneTime) {
                closePhoto(true, true);
            } else {
                this.closeVideoAfterWatch = true;
            }
        } else if (i == NotificationCenter.didCreatedNewDeleteTask) {
            if (this.currentMessageObject != null && this.secretDeleteTimer != null) {
                SparseArray sparseArray = (SparseArray) objArr[0];
                for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                    int keyAt = sparseArray.keyAt(i2);
                    ArrayList arrayList = (ArrayList) sparseArray.get(keyAt);
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        long longValue = ((Long) arrayList.get(i3)).longValue();
                        if (i3 == 0) {
                            int i4 = (int) (longValue >> 32);
                            if (i4 < 0) {
                                i4 = 0;
                            }
                            if (i4 != this.currentChannelId) {
                                return;
                            }
                        }
                        if (((long) this.currentMessageObject.getId()) == longValue) {
                            this.currentMessageObject.messageOwner.destroyTime = keyAt;
                            this.secretDeleteTimer.invalidate();
                            return;
                        }
                    }
                }
            }
        } else if (i == NotificationCenter.updateMessageMedia) {
            if (this.currentMessageObject.getId() != ((Message) objArr[0]).id) {
                return;
            }
            if (!this.isVideo || this.videoWatchedOneTime) {
                closePhoto(true, true);
            } else {
                this.closeVideoAfterWatch = true;
            }
        }
    }

    public float getAnimationValue() {
        return this.animationValue;
    }

    public long getCloseTime() {
        return this.closeTime;
    }

    public MessageObject getCurrentMessageObject() {
        return this.currentMessageObject;
    }

    public long getOpenTime() {
        return this.openTime;
    }

    public float getVideoCrossfadeAlpha() {
        return this.videoCrossfadeAlpha;
    }

    public boolean isShowingImage(MessageObject messageObject) {
        return (!this.isVisible || this.disableShowCheck || messageObject == null || this.currentMessageObject == null || this.currentMessageObject.getId() != messageObject.getId()) ? false : true;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        if ((this.scale == 1.0f && (this.translationY != BitmapDescriptorFactory.HUE_RED || this.translationX != BitmapDescriptorFactory.HUE_RED)) || this.animationStartTime != 0 || this.photoAnimationInProgress != 0) {
            return false;
        }
        if (this.scale == 1.0f) {
            float x = (motionEvent.getX() - ((float) (getContainerViewWidth() / 2))) - (((motionEvent.getX() - ((float) (getContainerViewWidth() / 2))) - this.translationX) * (3.0f / this.scale));
            float y = (motionEvent.getY() - ((float) (getContainerViewHeight() / 2))) - (((motionEvent.getY() - ((float) (getContainerViewHeight() / 2))) - this.translationY) * (3.0f / this.scale));
            updateMinMax(3.0f);
            if (x < this.minX) {
                x = this.minX;
            } else if (x > this.maxX) {
                x = this.maxX;
            }
            if (y < this.minY) {
                y = this.minY;
            } else if (y > this.maxY) {
                y = this.maxY;
            }
            animateTo(3.0f, x, y, true);
        } else {
            animateTo(1.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, true);
        }
        this.doubleTap = true;
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.scale != 1.0f) {
            this.scroller.abortAnimation();
            this.scroller.fling(Math.round(this.translationX), Math.round(this.translationY), Math.round(f), Math.round(f2), (int) this.minX, (int) this.maxX, (int) this.minY, (int) this.maxY);
            this.containerView.postInvalidate();
        }
        return false;
    }

    public void onLongPress(MotionEvent motionEvent) {
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
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

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public void openMedia(MessageObject messageObject, PhotoViewerProvider photoViewerProvider) {
        if (this.parentActivity != null && messageObject != null && messageObject.isSecretPhoto() && photoViewerProvider != null) {
            final PlaceProviderObject placeForPhoto = photoViewerProvider.getPlaceForPhoto(messageObject, null, 0);
            if (placeForPhoto != null) {
                this.currentProvider = photoViewerProvider;
                this.openTime = System.currentTimeMillis();
                this.closeTime = 0;
                this.isActionBarVisible = true;
                this.isPhotoVisible = true;
                this.draggingDown = false;
                if (this.aspectRatioFrameLayout != null) {
                    this.aspectRatioFrameLayout.setVisibility(4);
                }
                releasePlayer();
                this.pinchStartDistance = BitmapDescriptorFactory.HUE_RED;
                this.pinchStartScale = 1.0f;
                this.pinchCenterX = BitmapDescriptorFactory.HUE_RED;
                this.pinchCenterY = BitmapDescriptorFactory.HUE_RED;
                this.pinchStartX = BitmapDescriptorFactory.HUE_RED;
                this.pinchStartY = BitmapDescriptorFactory.HUE_RED;
                this.moveStartX = BitmapDescriptorFactory.HUE_RED;
                this.moveStartY = BitmapDescriptorFactory.HUE_RED;
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
                Rect drawRegion = placeForPhoto.imageReceiver.getDrawRegion();
                float f = (float) (drawRegion.right - drawRegion.left);
                float f2 = (float) (drawRegion.bottom - drawRegion.top);
                int i = AndroidUtilities.displaySize.x;
                int i2 = (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + AndroidUtilities.displaySize.y;
                this.scale = Math.max(f / ((float) i), f2 / ((float) i2));
                this.translationX = ((f / 2.0f) + ((float) (placeForPhoto.viewX + drawRegion.left))) - ((float) (i / 2));
                this.translationY = (((float) (placeForPhoto.viewY + drawRegion.top)) + (f2 / 2.0f)) - ((float) (i2 / 2));
                this.clipHorizontal = (float) Math.abs(drawRegion.left - placeForPhoto.imageReceiver.getImageX());
                int abs = Math.abs(drawRegion.top - placeForPhoto.imageReceiver.getImageY());
                int[] iArr = new int[2];
                placeForPhoto.parentView.getLocationInWindow(iArr);
                this.clipTop = (float) (((iArr[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight)) - (placeForPhoto.viewY + drawRegion.top)) + placeForPhoto.clipTopAddition);
                if (this.clipTop < BitmapDescriptorFactory.HUE_RED) {
                    this.clipTop = BitmapDescriptorFactory.HUE_RED;
                }
                this.clipBottom = (float) (((((int) f2) + (placeForPhoto.viewY + drawRegion.top)) - ((placeForPhoto.parentView.getHeight() + iArr[1]) - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight))) + placeForPhoto.clipBottomAddition);
                if (this.clipBottom < BitmapDescriptorFactory.HUE_RED) {
                    this.clipBottom = BitmapDescriptorFactory.HUE_RED;
                }
                this.clipTop = Math.max(this.clipTop, (float) abs);
                this.clipBottom = Math.max(this.clipBottom, (float) abs);
                this.animationStartTime = System.currentTimeMillis();
                this.animateToX = BitmapDescriptorFactory.HUE_RED;
                this.animateToY = BitmapDescriptorFactory.HUE_RED;
                this.animateToClipBottom = BitmapDescriptorFactory.HUE_RED;
                this.animateToClipHorizontal = BitmapDescriptorFactory.HUE_RED;
                this.animateToClipTop = BitmapDescriptorFactory.HUE_RED;
                this.animateToScale = 1.0f;
                this.zoomAnimation = true;
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesDeleted);
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateMessageMedia);
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.didCreatedNewDeleteTask);
                this.currentChannelId = messageObject.messageOwner.to_id != null ? messageObject.messageOwner.to_id.channel_id : 0;
                toggleActionBar(true, false);
                this.currentMessageObject = messageObject;
                Document document = messageObject.getDocument();
                Bitmap thumbBitmap = placeForPhoto.imageReceiver.getThumbBitmap();
                if (document != null) {
                    this.actionBar.setTitle(LocaleController.getString("DisappearingVideo", R.string.DisappearingVideo));
                    File file = new File(messageObject.messageOwner.attachPath);
                    if (file.exists()) {
                        preparePlayer(file);
                    } else {
                        File pathToMessage = FileLoader.getPathToMessage(messageObject.messageOwner);
                        file = new File(pathToMessage.getAbsolutePath() + ".enc");
                        if (!file.exists()) {
                            file = pathToMessage;
                        }
                        preparePlayer(file);
                    }
                    this.isVideo = true;
                    this.centerImage.setImage(null, null, thumbBitmap != null ? new BitmapDrawable(thumbBitmap) : null, -1, null, 2);
                    if (((long) (messageObject.getDuration() * 1000)) > (((long) messageObject.messageOwner.destroyTime) * 1000) - (System.currentTimeMillis() + ((long) (ConnectionsManager.getInstance().getTimeDifference() * 1000)))) {
                        this.secretDeleteTimer.setDestroyTime(-1, -1, true);
                    } else {
                        this.secretDeleteTimer.setDestroyTime(((long) messageObject.messageOwner.destroyTime) * 1000, (long) messageObject.messageOwner.ttl, false);
                    }
                } else {
                    this.actionBar.setTitle(LocaleController.getString("DisappearingPhoto", R.string.DisappearingPhoto));
                    this.centerImage.setImage(FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize()).location, null, thumbBitmap != null ? new BitmapDrawable(thumbBitmap) : null, -1, null, 2);
                    this.secretDeleteTimer.setDestroyTime(((long) messageObject.messageOwner.destroyTime) * 1000, (long) messageObject.messageOwner.ttl, false);
                }
                try {
                    if (this.windowView.getParent() != null) {
                        ((WindowManager) this.parentActivity.getSystemService("window")).removeView(this.windowView);
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                ((WindowManager) this.parentActivity.getSystemService("window")).addView(this.windowView, this.windowLayoutParams);
                this.secretDeleteTimer.invalidate();
                this.isVisible = true;
                this.imageMoveAnimation = new AnimatorSet();
                this.imageMoveAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this.secretDeleteTimer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0, 255}), ObjectAnimator.ofFloat(this.secretDeleteTimer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this, "animationValue", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
                this.photoAnimationInProgress = 3;
                this.photoAnimationEndRunnable = new C51646();
                this.imageMoveAnimation.setDuration(250);
                this.imageMoveAnimation.addListener(new C51657());
                this.photoTransitionAnimationStartTime = System.currentTimeMillis();
                if (VERSION.SDK_INT >= 18) {
                    this.containerView.setLayerType(2, null);
                }
                this.imageMoveAnimation.setInterpolator(new DecelerateInterpolator());
                this.photoBackgroundDrawable.frame = 0;
                this.photoBackgroundDrawable.drawRunnable = new Runnable() {
                    public void run() {
                        SecretMediaViewer.this.disableShowCheck = false;
                        placeForPhoto.imageReceiver.setVisible(false, true);
                    }
                };
                this.imageMoveAnimation.start();
            }
        }
    }

    public void setAnimationValue(float f) {
        this.animationValue = f;
        this.containerView.invalidate();
    }

    public void setParentActivity(Activity activity) {
        if (this.parentActivity != activity) {
            this.parentActivity = activity;
            this.scroller = new Scroller(activity);
            this.windowView = new FrameLayout(activity) {
                protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                    int systemWindowInsetLeft = (VERSION.SDK_INT < 21 || SecretMediaViewer.this.lastInsets == null) ? 0 : ((WindowInsets) SecretMediaViewer.this.lastInsets).getSystemWindowInsetLeft() + 0;
                    SecretMediaViewer.this.containerView.layout(systemWindowInsetLeft, 0, SecretMediaViewer.this.containerView.getMeasuredWidth() + systemWindowInsetLeft, SecretMediaViewer.this.containerView.getMeasuredHeight());
                    if (z) {
                        if (SecretMediaViewer.this.imageMoveAnimation == null) {
                            SecretMediaViewer.this.scale = 1.0f;
                            SecretMediaViewer.this.translationX = BitmapDescriptorFactory.HUE_RED;
                            SecretMediaViewer.this.translationY = BitmapDescriptorFactory.HUE_RED;
                        }
                        SecretMediaViewer.this.updateMinMax(SecretMediaViewer.this.scale);
                    }
                }

                protected void onMeasure(int i, int i2) {
                    int size = MeasureSpec.getSize(i);
                    int size2 = MeasureSpec.getSize(i2);
                    if (VERSION.SDK_INT >= 21 && SecretMediaViewer.this.lastInsets != null) {
                        WindowInsets windowInsets = (WindowInsets) SecretMediaViewer.this.lastInsets;
                        if (AndroidUtilities.incorrectDisplaySizeFix) {
                            if (size2 > AndroidUtilities.displaySize.y) {
                                size2 = AndroidUtilities.displaySize.y;
                            }
                            size2 += AndroidUtilities.statusBarHeight;
                        }
                        size2 -= windowInsets.getSystemWindowInsetBottom();
                        size -= windowInsets.getSystemWindowInsetRight();
                    } else if (size2 > AndroidUtilities.displaySize.y) {
                        size2 = AndroidUtilities.displaySize.y;
                    }
                    setMeasuredDimension(size, size2);
                    if (VERSION.SDK_INT >= 21 && SecretMediaViewer.this.lastInsets != null) {
                        size -= ((WindowInsets) SecretMediaViewer.this.lastInsets).getSystemWindowInsetLeft();
                    }
                    SecretMediaViewer.this.containerView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(size2, 1073741824));
                }
            };
            this.windowView.setBackgroundDrawable(this.photoBackgroundDrawable);
            this.windowView.setFocusable(true);
            this.windowView.setFocusableInTouchMode(true);
            this.containerView = new FrameLayoutDrawer(activity) {
                protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                    super.onLayout(z, i, i2, i3, i4);
                    if (SecretMediaViewer.this.secretDeleteTimer != null) {
                        int currentActionBarHeight = (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + ((ActionBar.getCurrentActionBarHeight() - SecretMediaViewer.this.secretDeleteTimer.getMeasuredHeight()) / 2);
                        SecretMediaViewer.this.secretDeleteTimer.layout(SecretMediaViewer.this.secretDeleteTimer.getLeft(), currentActionBarHeight, SecretMediaViewer.this.secretDeleteTimer.getRight(), SecretMediaViewer.this.secretDeleteTimer.getMeasuredHeight() + currentActionBarHeight);
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
                this.containerView.setOnApplyWindowInsetsListener(new C51624());
                this.containerView.setSystemUiVisibility(1280);
            }
            this.gestureDetector = new GestureDetector(this.containerView.getContext(), this);
            this.gestureDetector.setOnDoubleTapListener(this);
            this.actionBar = new ActionBar(activity);
            this.actionBar.setTitleColor(-1);
            this.actionBar.setSubtitleColor(-1);
            this.actionBar.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
            this.actionBar.setOccupyStatusBar(VERSION.SDK_INT >= 21);
            this.actionBar.setItemsBackgroundColor(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR, false);
            this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
            this.actionBar.setTitleRightMargin(AndroidUtilities.dp(70.0f));
            this.containerView.addView(this.actionBar, LayoutHelper.createFrame(-1, -2.0f));
            this.actionBar.setActionBarMenuOnItemClick(new C51635());
            this.secretDeleteTimer = new SecretDeleteTimer(this, activity);
            this.containerView.addView(this.secretDeleteTimer, LayoutHelper.createFrame(119, 48.0f, 53, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
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

    public void setVideoCrossfadeAlpha(float f) {
        this.videoCrossfadeAlpha = f;
        this.containerView.invalidate();
    }
}
