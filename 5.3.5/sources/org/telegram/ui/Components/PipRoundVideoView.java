package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.Bitmaps;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.exoplayer2.ui.AspectRatioFrameLayout;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.Theme;

public class PipRoundVideoView implements NotificationCenterDelegate {
    @SuppressLint({"StaticFieldLeak"})
    private static PipRoundVideoView instance;
    private AspectRatioFrameLayout aspectRatioFrameLayout;
    private Bitmap bitmap;
    private DecelerateInterpolator decelerateInterpolator;
    private AnimatorSet hideShowAnimation;
    private ImageView imageView;
    private Runnable onCloseRunnable;
    private Activity parentActivity;
    private SharedPreferences preferences;
    private RectF rect = new RectF();
    private TextureView textureView;
    private int videoHeight;
    private int videoWidth;
    private LayoutParams windowLayoutParams;
    private WindowManager windowManager;
    private FrameLayout windowView;

    /* renamed from: org.telegram.ui.Components.PipRoundVideoView$3 */
    class C27123 extends ViewOutlineProvider {
        C27123() {
        }

        @TargetApi(21)
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, AndroidUtilities.dp(120.0f), AndroidUtilities.dp(120.0f));
        }
    }

    /* renamed from: org.telegram.ui.Components.PipRoundVideoView$5 */
    class C27145 extends AnimatorListenerAdapter {
        C27145() {
        }

        public void onAnimationEnd(Animator animation) {
            if (animation.equals(PipRoundVideoView.this.hideShowAnimation)) {
                PipRoundVideoView.this.hideShowAnimation = null;
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.PipRoundVideoView$7 */
    class C27167 extends AnimatorListenerAdapter {
        C27167() {
        }

        public void onAnimationEnd(Animator animation) {
            PipRoundVideoView.this.close(false);
            if (PipRoundVideoView.this.onCloseRunnable != null) {
                PipRoundVideoView.this.onCloseRunnable.run();
            }
        }
    }

    public void show(Activity activity, Runnable closeRunnable) {
        if (activity != null) {
            instance = this;
            this.onCloseRunnable = closeRunnable;
            this.windowView = new FrameLayout(activity) {
                private boolean dragging;
                private boolean startDragging;
                private float startX;
                private float startY;

                public boolean onInterceptTouchEvent(MotionEvent event) {
                    if (event.getAction() == 0) {
                        this.startX = event.getRawX();
                        this.startY = event.getRawY();
                        this.startDragging = true;
                    }
                    return true;
                }

                public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                    super.requestDisallowInterceptTouchEvent(disallowIntercept);
                }

                public boolean onTouchEvent(MotionEvent event) {
                    if (!this.startDragging && !this.dragging) {
                        return false;
                    }
                    float x = event.getRawX();
                    float y = event.getRawY();
                    if (event.getAction() == 2) {
                        float dx = x - this.startX;
                        float dy = y - this.startY;
                        if (this.startDragging) {
                            if (Math.abs(dx) >= AndroidUtilities.getPixelsInCM(0.3f, true) || Math.abs(dy) >= AndroidUtilities.getPixelsInCM(0.3f, false)) {
                                this.dragging = true;
                                this.startDragging = false;
                            }
                        } else if (this.dragging) {
                            LayoutParams access$000 = PipRoundVideoView.this.windowLayoutParams;
                            access$000.x = (int) (((float) access$000.x) + dx);
                            access$000 = PipRoundVideoView.this.windowLayoutParams;
                            access$000.y = (int) (((float) access$000.y) + dy);
                            int maxDiff = PipRoundVideoView.this.videoWidth / 2;
                            if (PipRoundVideoView.this.windowLayoutParams.x < (-maxDiff)) {
                                PipRoundVideoView.this.windowLayoutParams.x = -maxDiff;
                            } else if (PipRoundVideoView.this.windowLayoutParams.x > (AndroidUtilities.displaySize.x - PipRoundVideoView.this.windowLayoutParams.width) + maxDiff) {
                                PipRoundVideoView.this.windowLayoutParams.x = (AndroidUtilities.displaySize.x - PipRoundVideoView.this.windowLayoutParams.width) + maxDiff;
                            }
                            float alpha = 1.0f;
                            if (PipRoundVideoView.this.windowLayoutParams.x < 0) {
                                alpha = 1.0f + ((((float) PipRoundVideoView.this.windowLayoutParams.x) / ((float) maxDiff)) * 0.5f);
                            } else if (PipRoundVideoView.this.windowLayoutParams.x > AndroidUtilities.displaySize.x - PipRoundVideoView.this.windowLayoutParams.width) {
                                alpha = 1.0f - ((((float) ((PipRoundVideoView.this.windowLayoutParams.x - AndroidUtilities.displaySize.x) + PipRoundVideoView.this.windowLayoutParams.width)) / ((float) maxDiff)) * 0.5f);
                            }
                            if (PipRoundVideoView.this.windowView.getAlpha() != alpha) {
                                PipRoundVideoView.this.windowView.setAlpha(alpha);
                            }
                            if (PipRoundVideoView.this.windowLayoutParams.y < (-null)) {
                                PipRoundVideoView.this.windowLayoutParams.y = -null;
                            } else if (PipRoundVideoView.this.windowLayoutParams.y > (AndroidUtilities.displaySize.y - PipRoundVideoView.this.windowLayoutParams.height) + 0) {
                                PipRoundVideoView.this.windowLayoutParams.y = (AndroidUtilities.displaySize.y - PipRoundVideoView.this.windowLayoutParams.height) + 0;
                            }
                            PipRoundVideoView.this.windowManager.updateViewLayout(PipRoundVideoView.this.windowView, PipRoundVideoView.this.windowLayoutParams);
                            this.startX = x;
                            this.startY = y;
                        }
                    } else if (event.getAction() == 1) {
                        if (this.startDragging && !this.dragging) {
                            MessageObject messageObject = MediaController.getInstance().getPlayingMessageObject();
                            if (messageObject != null) {
                                if (MediaController.getInstance().isMessagePaused()) {
                                    MediaController.getInstance().playMessage(messageObject);
                                } else {
                                    MediaController.getInstance().pauseMessage(messageObject);
                                }
                            }
                        }
                        this.dragging = false;
                        this.startDragging = false;
                        PipRoundVideoView.this.animateToBoundsMaybe();
                    }
                    return true;
                }

                protected void onDraw(Canvas canvas) {
                    if (Theme.chat_roundVideoShadow != null) {
                        Theme.chat_roundVideoShadow.setAlpha((int) (getAlpha() * 255.0f));
                        Theme.chat_roundVideoShadow.setBounds(AndroidUtilities.dp(1.0f), AndroidUtilities.dp(2.0f), AndroidUtilities.dp(125.0f), AndroidUtilities.dp(125.0f));
                        Theme.chat_roundVideoShadow.draw(canvas);
                    }
                }
            };
            this.windowView.setWillNotDraw(false);
            this.videoWidth = AndroidUtilities.dp(126.0f);
            this.videoHeight = AndroidUtilities.dp(126.0f);
            if (VERSION.SDK_INT >= 21) {
                this.aspectRatioFrameLayout = new AspectRatioFrameLayout(activity) {
                    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
                        boolean result = super.drawChild(canvas, child, drawingTime);
                        if (child == PipRoundVideoView.this.textureView) {
                            MessageObject currentMessageObject = MediaController.getInstance().getPlayingMessageObject();
                            if (currentMessageObject != null) {
                                PipRoundVideoView.this.rect.set(AndroidUtilities.dpf2(1.5f), AndroidUtilities.dpf2(1.5f), ((float) getMeasuredWidth()) - AndroidUtilities.dpf2(1.5f), ((float) getMeasuredHeight()) - AndroidUtilities.dpf2(1.5f));
                                canvas.drawArc(PipRoundVideoView.this.rect, -90.0f, currentMessageObject.audioProgress * 360.0f, false, Theme.chat_radialProgressPaint);
                            }
                        }
                        return result;
                    }
                };
                this.aspectRatioFrameLayout.setOutlineProvider(new C27123());
                this.aspectRatioFrameLayout.setClipToOutline(true);
            } else {
                final Paint aspectPaint = new Paint(1);
                aspectPaint.setColor(-16777216);
                aspectPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
                this.aspectRatioFrameLayout = new AspectRatioFrameLayout(activity) {
                    private Path aspectPath = new Path();

                    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
                        super.onSizeChanged(w, h, oldw, oldh);
                        this.aspectPath.reset();
                        this.aspectPath.addCircle((float) (w / 2), (float) (h / 2), (float) (w / 2), Direction.CW);
                        this.aspectPath.toggleInverseFillType();
                    }

                    protected void dispatchDraw(Canvas canvas) {
                        super.dispatchDraw(canvas);
                        canvas.drawPath(this.aspectPath, aspectPaint);
                    }

                    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
                        boolean result;
                        try {
                            result = super.drawChild(canvas, child, drawingTime);
                        } catch (Throwable th) {
                            result = false;
                        }
                        if (child == PipRoundVideoView.this.textureView) {
                            MessageObject currentMessageObject = MediaController.getInstance().getPlayingMessageObject();
                            if (currentMessageObject != null) {
                                PipRoundVideoView.this.rect.set(AndroidUtilities.dpf2(1.5f), AndroidUtilities.dpf2(1.5f), ((float) getMeasuredWidth()) - AndroidUtilities.dpf2(1.5f), ((float) getMeasuredHeight()) - AndroidUtilities.dpf2(1.5f));
                                canvas.drawArc(PipRoundVideoView.this.rect, -90.0f, currentMessageObject.audioProgress * 360.0f, false, Theme.chat_radialProgressPaint);
                            }
                        }
                        return result;
                    }
                };
                this.aspectRatioFrameLayout.setLayerType(2, null);
            }
            this.aspectRatioFrameLayout.setAspectRatio(1.0f, 0);
            this.windowView.addView(this.aspectRatioFrameLayout, LayoutHelper.createFrame(120, 120.0f, 51, 3.0f, 3.0f, 0.0f, 0.0f));
            this.windowView.setAlpha(1.0f);
            this.windowView.setScaleX(0.8f);
            this.windowView.setScaleY(0.8f);
            this.textureView = new TextureView(activity);
            this.aspectRatioFrameLayout.addView(this.textureView, LayoutHelper.createFrame(-1, -1.0f));
            this.imageView = new ImageView(activity);
            this.aspectRatioFrameLayout.addView(this.imageView, LayoutHelper.createFrame(-1, -1.0f));
            this.imageView.setVisibility(4);
            this.windowManager = (WindowManager) activity.getSystemService("window");
            this.preferences = ApplicationLoader.applicationContext.getSharedPreferences("pipconfig", 0);
            int sidex = this.preferences.getInt("sidex", 1);
            int sidey = this.preferences.getInt("sidey", 0);
            float px = this.preferences.getFloat("px", 0.0f);
            float py = this.preferences.getFloat("py", 0.0f);
            try {
                this.windowLayoutParams = new LayoutParams();
                this.windowLayoutParams.width = this.videoWidth;
                this.windowLayoutParams.height = this.videoHeight;
                this.windowLayoutParams.x = getSideCoord(true, sidex, px, this.videoWidth);
                this.windowLayoutParams.y = getSideCoord(false, sidey, py, this.videoHeight);
                this.windowLayoutParams.format = -3;
                this.windowLayoutParams.gravity = 51;
                this.windowLayoutParams.type = 99;
                this.windowLayoutParams.flags = 16777736;
                this.windowManager.addView(this.windowView, this.windowLayoutParams);
                this.parentActivity = activity;
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
                runShowHideAnimation(true);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    private static int getSideCoord(boolean isX, int side, float p, int sideSize) {
        int total;
        int result;
        if (isX) {
            total = AndroidUtilities.displaySize.x - sideSize;
        } else {
            total = (AndroidUtilities.displaySize.y - sideSize) - ActionBar.getCurrentActionBarHeight();
        }
        if (side == 0) {
            result = AndroidUtilities.dp(10.0f);
        } else if (side == 1) {
            result = total - AndroidUtilities.dp(10.0f);
        } else {
            result = Math.round(((float) (total - AndroidUtilities.dp(20.0f))) * p) + AndroidUtilities.dp(10.0f);
        }
        if (isX) {
            return result;
        }
        return result + ActionBar.getCurrentActionBarHeight();
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.messagePlayingProgressDidChanged && this.aspectRatioFrameLayout != null) {
            this.aspectRatioFrameLayout.invalidate();
        }
    }

    public TextureView getTextureView() {
        return this.textureView;
    }

    public void close(boolean animated) {
        if (!animated) {
            if (this.bitmap != null) {
                this.imageView.setImageDrawable(null);
                this.bitmap.recycle();
                this.bitmap = null;
            }
            try {
                this.windowManager.removeView(this.windowView);
            } catch (Exception e) {
            }
            if (instance == this) {
                instance = null;
            }
            this.parentActivity = null;
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        } else if (this.textureView != null && this.textureView.getParent() != null) {
            if (this.textureView.getWidth() > 0 && this.textureView.getHeight() > 0) {
                this.bitmap = Bitmaps.createBitmap(this.textureView.getWidth(), this.textureView.getHeight(), Config.ARGB_8888);
            }
            try {
                this.textureView.getBitmap(this.bitmap);
            } catch (Throwable th) {
                this.bitmap = null;
            }
            this.imageView.setImageBitmap(this.bitmap);
            try {
                this.aspectRatioFrameLayout.removeView(this.textureView);
            } catch (Exception e2) {
            }
            this.imageView.setVisibility(0);
            runShowHideAnimation(false);
        }
    }

    public void onConfigurationChanged() {
        int sidex = this.preferences.getInt("sidex", 1);
        int sidey = this.preferences.getInt("sidey", 0);
        float px = this.preferences.getFloat("px", 0.0f);
        float py = this.preferences.getFloat("py", 0.0f);
        this.windowLayoutParams.x = getSideCoord(true, sidex, px, this.videoWidth);
        this.windowLayoutParams.y = getSideCoord(false, sidey, py, this.videoHeight);
        this.windowManager.updateViewLayout(this.windowView, this.windowLayoutParams);
    }

    public void showTemporary(boolean show) {
        float f;
        float f2 = 1.0f;
        if (this.hideShowAnimation != null) {
            this.hideShowAnimation.cancel();
        }
        this.hideShowAnimation = new AnimatorSet();
        AnimatorSet animatorSet = this.hideShowAnimation;
        Animator[] animatorArr = new Animator[3];
        FrameLayout frameLayout = this.windowView;
        String str = "alpha";
        float[] fArr = new float[1];
        fArr[0] = show ? 1.0f : 0.0f;
        animatorArr[0] = ObjectAnimator.ofFloat(frameLayout, str, fArr);
        frameLayout = this.windowView;
        str = "scaleX";
        fArr = new float[1];
        if (show) {
            f = 1.0f;
        } else {
            f = 0.8f;
        }
        fArr[0] = f;
        animatorArr[1] = ObjectAnimator.ofFloat(frameLayout, str, fArr);
        frameLayout = this.windowView;
        str = "scaleY";
        fArr = new float[1];
        if (!show) {
            f2 = 0.8f;
        }
        fArr[0] = f2;
        animatorArr[2] = ObjectAnimator.ofFloat(frameLayout, str, fArr);
        animatorSet.playTogether(animatorArr);
        this.hideShowAnimation.setDuration(150);
        if (this.decelerateInterpolator == null) {
            this.decelerateInterpolator = new DecelerateInterpolator();
        }
        this.hideShowAnimation.addListener(new C27145());
        this.hideShowAnimation.setInterpolator(this.decelerateInterpolator);
        this.hideShowAnimation.start();
    }

    private void runShowHideAnimation(final boolean show) {
        float f;
        float f2 = 1.0f;
        if (this.hideShowAnimation != null) {
            this.hideShowAnimation.cancel();
        }
        this.hideShowAnimation = new AnimatorSet();
        AnimatorSet animatorSet = this.hideShowAnimation;
        Animator[] animatorArr = new Animator[3];
        FrameLayout frameLayout = this.windowView;
        String str = "alpha";
        float[] fArr = new float[1];
        fArr[0] = show ? 1.0f : 0.0f;
        animatorArr[0] = ObjectAnimator.ofFloat(frameLayout, str, fArr);
        frameLayout = this.windowView;
        str = "scaleX";
        fArr = new float[1];
        if (show) {
            f = 1.0f;
        } else {
            f = 0.8f;
        }
        fArr[0] = f;
        animatorArr[1] = ObjectAnimator.ofFloat(frameLayout, str, fArr);
        frameLayout = this.windowView;
        str = "scaleY";
        fArr = new float[1];
        if (!show) {
            f2 = 0.8f;
        }
        fArr[0] = f2;
        animatorArr[2] = ObjectAnimator.ofFloat(frameLayout, str, fArr);
        animatorSet.playTogether(animatorArr);
        this.hideShowAnimation.setDuration(150);
        if (this.decelerateInterpolator == null) {
            this.decelerateInterpolator = new DecelerateInterpolator();
        }
        this.hideShowAnimation.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (animation.equals(PipRoundVideoView.this.hideShowAnimation)) {
                    if (!show) {
                        PipRoundVideoView.this.close(false);
                    }
                    PipRoundVideoView.this.hideShowAnimation = null;
                }
            }

            public void onAnimationCancel(Animator animation) {
                if (animation.equals(PipRoundVideoView.this.hideShowAnimation)) {
                    PipRoundVideoView.this.hideShowAnimation = null;
                }
            }
        });
        this.hideShowAnimation.setInterpolator(this.decelerateInterpolator);
        this.hideShowAnimation.start();
    }

    private void animateToBoundsMaybe() {
        int startX = getSideCoord(true, 0, 0.0f, this.videoWidth);
        int endX = getSideCoord(true, 1, 0.0f, this.videoWidth);
        int startY = getSideCoord(false, 0, 0.0f, this.videoHeight);
        int endY = getSideCoord(false, 1, 0.0f, this.videoHeight);
        ArrayList<Animator> animators = null;
        Editor editor = this.preferences.edit();
        int maxDiff = AndroidUtilities.dp(20.0f);
        boolean slideOut = false;
        if (Math.abs(startX - this.windowLayoutParams.x) <= maxDiff || (this.windowLayoutParams.x < 0 && this.windowLayoutParams.x > (-this.videoWidth) / 4)) {
            if (null == null) {
                animators = new ArrayList();
            }
            editor.putInt("sidex", 0);
            if (this.windowView.getAlpha() != 1.0f) {
                animators.add(ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{1.0f}));
            }
            animators.add(ObjectAnimator.ofInt(this, "x", new int[]{startX}));
        } else if (Math.abs(endX - this.windowLayoutParams.x) <= maxDiff || (this.windowLayoutParams.x > AndroidUtilities.displaySize.x - this.videoWidth && this.windowLayoutParams.x < AndroidUtilities.displaySize.x - ((this.videoWidth / 4) * 3))) {
            if (null == null) {
                animators = new ArrayList();
            }
            editor.putInt("sidex", 1);
            if (this.windowView.getAlpha() != 1.0f) {
                animators.add(ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{1.0f}));
            }
            animators.add(ObjectAnimator.ofInt(this, "x", new int[]{endX}));
        } else if (this.windowView.getAlpha() != 1.0f) {
            if (null == null) {
                animators = new ArrayList();
            }
            if (this.windowLayoutParams.x < 0) {
                animators.add(ObjectAnimator.ofInt(this, "x", new int[]{-this.videoWidth}));
            } else {
                animators.add(ObjectAnimator.ofInt(this, "x", new int[]{AndroidUtilities.displaySize.x}));
            }
            slideOut = true;
        } else {
            editor.putFloat("px", ((float) (this.windowLayoutParams.x - startX)) / ((float) (endX - startX)));
            editor.putInt("sidex", 2);
        }
        if (!slideOut) {
            if (Math.abs(startY - this.windowLayoutParams.y) <= maxDiff || this.windowLayoutParams.y <= ActionBar.getCurrentActionBarHeight()) {
                if (animators == null) {
                    animators = new ArrayList();
                }
                editor.putInt("sidey", 0);
                animators.add(ObjectAnimator.ofInt(this, "y", new int[]{startY}));
            } else if (Math.abs(endY - this.windowLayoutParams.y) <= maxDiff) {
                if (animators == null) {
                    animators = new ArrayList();
                }
                editor.putInt("sidey", 1);
                animators.add(ObjectAnimator.ofInt(this, "y", new int[]{endY}));
            } else {
                editor.putFloat("py", ((float) (this.windowLayoutParams.y - startY)) / ((float) (endY - startY)));
                editor.putInt("sidey", 2);
            }
            editor.commit();
        }
        if (animators != null) {
            if (this.decelerateInterpolator == null) {
                this.decelerateInterpolator = new DecelerateInterpolator();
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(this.decelerateInterpolator);
            animatorSet.setDuration(150);
            if (slideOut) {
                animators.add(ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{0.0f}));
                animatorSet.addListener(new C27167());
            }
            animatorSet.playTogether(animators);
            animatorSet.start();
        }
    }

    public int getX() {
        return this.windowLayoutParams.x;
    }

    public int getY() {
        return this.windowLayoutParams.y;
    }

    public void setX(int value) {
        this.windowLayoutParams.x = value;
        try {
            this.windowManager.updateViewLayout(this.windowView, this.windowLayoutParams);
        } catch (Exception e) {
        }
    }

    public void setY(int value) {
        this.windowLayoutParams.y = value;
        try {
            this.windowManager.updateViewLayout(this.windowView, this.windowLayoutParams);
        } catch (Exception e) {
        }
    }

    public static PipRoundVideoView getInstance() {
        return instance;
    }
}
