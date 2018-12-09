package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Collection;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.exoplayer2.ui.AspectRatioFrameLayout;
import org.telegram.ui.ActionBar.ActionBar;

public class PipVideoView {
    private View controlsView;
    private DecelerateInterpolator decelerateInterpolator;
    private Activity parentActivity;
    private EmbedBottomSheet parentSheet;
    private SharedPreferences preferences;
    private int videoHeight;
    private int videoWidth;
    private LayoutParams windowLayoutParams;
    private WindowManager windowManager;
    private FrameLayout windowView;

    /* renamed from: org.telegram.ui.Components.PipVideoView$2 */
    class C45562 extends AnimatorListenerAdapter {
        C45562() {
        }

        public void onAnimationEnd(Animator animator) {
            if (PipVideoView.this.parentSheet != null) {
                PipVideoView.this.parentSheet.destroy();
            }
        }
    }

    private class MiniControlsView extends FrameLayout {
        private AnimatorSet currentAnimation;
        private Runnable hideRunnable = new C45571();
        private ImageView inlineButton;
        private boolean isVisible = true;

        /* renamed from: org.telegram.ui.Components.PipVideoView$MiniControlsView$1 */
        class C45571 implements Runnable {
            C45571() {
            }

            public void run() {
                MiniControlsView.this.show(false, true);
            }
        }

        /* renamed from: org.telegram.ui.Components.PipVideoView$MiniControlsView$3 */
        class C45593 extends AnimatorListenerAdapter {
            C45593() {
            }

            public void onAnimationEnd(Animator animator) {
                MiniControlsView.this.currentAnimation = null;
            }
        }

        /* renamed from: org.telegram.ui.Components.PipVideoView$MiniControlsView$4 */
        class C45604 extends AnimatorListenerAdapter {
            C45604() {
            }

            public void onAnimationEnd(Animator animator) {
                MiniControlsView.this.currentAnimation = null;
            }
        }

        public MiniControlsView(Context context) {
            super(context);
            setWillNotDraw(false);
            this.inlineButton = new ImageView(context);
            this.inlineButton.setScaleType(ScaleType.CENTER);
            this.inlineButton.setImageResource(R.drawable.ic_outinline);
            addView(this.inlineButton, LayoutHelper.createFrame(56, 48, 53));
            this.inlineButton.setOnClickListener(new OnClickListener(PipVideoView.this) {
                public void onClick(View view) {
                    if (PipVideoView.this.parentSheet != null) {
                        PipVideoView.this.parentSheet.exitFromPip();
                    }
                }
            });
        }

        private void checkNeedHide() {
            AndroidUtilities.cancelRunOnUIThread(this.hideRunnable);
            if (this.isVisible) {
                AndroidUtilities.runOnUIThread(this.hideRunnable, 3000);
            }
        }

        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            checkNeedHide();
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                if (this.isVisible) {
                    checkNeedHide();
                } else {
                    show(true, true);
                    return true;
                }
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        public void requestDisallowInterceptTouchEvent(boolean z) {
            super.requestDisallowInterceptTouchEvent(z);
            checkNeedHide();
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
                        this.currentAnimation.addListener(new C45593());
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
                    this.currentAnimation.addListener(new C45604());
                    this.currentAnimation.start();
                } else {
                    setAlpha(BitmapDescriptorFactory.HUE_RED);
                }
                checkNeedHide();
            }
        }
    }

    private void animateToBoundsMaybe() {
        boolean z;
        int sideCoord = getSideCoord(true, 0, BitmapDescriptorFactory.HUE_RED, this.videoWidth);
        int sideCoord2 = getSideCoord(true, 1, BitmapDescriptorFactory.HUE_RED, this.videoWidth);
        int sideCoord3 = getSideCoord(false, 0, BitmapDescriptorFactory.HUE_RED, this.videoHeight);
        int sideCoord4 = getSideCoord(false, 1, BitmapDescriptorFactory.HUE_RED, this.videoHeight);
        Collection collection = null;
        Editor edit = this.preferences.edit();
        int dp = AndroidUtilities.dp(20.0f);
        if (Math.abs(sideCoord - this.windowLayoutParams.x) <= dp || (this.windowLayoutParams.x < 0 && this.windowLayoutParams.x > (-this.videoWidth) / 4)) {
            if (null == null) {
                collection = new ArrayList();
            }
            edit.putInt("sidex", 0);
            if (this.windowView.getAlpha() != 1.0f) {
                collection.add(ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{1.0f}));
            }
            collection.add(ObjectAnimator.ofInt(this, "x", new int[]{sideCoord}));
            z = false;
        } else if (Math.abs(sideCoord2 - this.windowLayoutParams.x) <= dp || (this.windowLayoutParams.x > AndroidUtilities.displaySize.x - this.videoWidth && this.windowLayoutParams.x < AndroidUtilities.displaySize.x - ((this.videoWidth / 4) * 3))) {
            if (null == null) {
                collection = new ArrayList();
            }
            edit.putInt("sidex", 1);
            if (this.windowView.getAlpha() != 1.0f) {
                collection.add(ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{1.0f}));
            }
            collection.add(ObjectAnimator.ofInt(this, "x", new int[]{sideCoord2}));
            z = false;
        } else if (this.windowView.getAlpha() != 1.0f) {
            if (null == null) {
                collection = new ArrayList();
            }
            if (this.windowLayoutParams.x < 0) {
                collection.add(ObjectAnimator.ofInt(this, "x", new int[]{-this.videoWidth}));
            } else {
                collection.add(ObjectAnimator.ofInt(this, "x", new int[]{AndroidUtilities.displaySize.x}));
            }
            z = true;
        } else {
            edit.putFloat("px", ((float) (this.windowLayoutParams.x - sideCoord)) / ((float) (sideCoord2 - sideCoord)));
            edit.putInt("sidex", 2);
            z = false;
        }
        if (!z) {
            if (Math.abs(sideCoord3 - this.windowLayoutParams.y) <= dp || this.windowLayoutParams.y <= ActionBar.getCurrentActionBarHeight()) {
                if (collection == null) {
                    collection = new ArrayList();
                }
                edit.putInt("sidey", 0);
                collection.add(ObjectAnimator.ofInt(this, "y", new int[]{sideCoord3}));
            } else if (Math.abs(sideCoord4 - this.windowLayoutParams.y) <= dp) {
                if (collection == null) {
                    collection = new ArrayList();
                }
                edit.putInt("sidey", 1);
                collection.add(ObjectAnimator.ofInt(this, "y", new int[]{sideCoord4}));
            } else {
                edit.putFloat("py", ((float) (this.windowLayoutParams.y - sideCoord3)) / ((float) (sideCoord4 - sideCoord3)));
                edit.putInt("sidey", 2);
            }
            edit.commit();
        }
        if (collection != null) {
            if (this.decelerateInterpolator == null) {
                this.decelerateInterpolator = new DecelerateInterpolator();
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(this.decelerateInterpolator);
            animatorSet.setDuration(150);
            if (z) {
                collection.add(ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                animatorSet.addListener(new C45562());
            }
            animatorSet.playTogether(collection);
            animatorSet.start();
        }
    }

    public static Rect getPipRect(float f) {
        int dp;
        int i;
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("pipconfig", 0);
        int i2 = sharedPreferences.getInt("sidex", 1);
        int i3 = sharedPreferences.getInt("sidey", 0);
        float f2 = sharedPreferences.getFloat("px", BitmapDescriptorFactory.HUE_RED);
        float f3 = sharedPreferences.getFloat("py", BitmapDescriptorFactory.HUE_RED);
        if (f > 1.0f) {
            dp = AndroidUtilities.dp(192.0f);
            i = (int) (((float) dp) / f);
        } else {
            i = AndroidUtilities.dp(192.0f);
            dp = (int) (((float) i) * f);
        }
        return new Rect((float) getSideCoord(true, i2, f2, dp), (float) getSideCoord(false, i3, f3, i), (float) dp, (float) i);
    }

    private static int getSideCoord(boolean z, int i, float f, int i2) {
        int currentActionBarHeight = z ? AndroidUtilities.displaySize.x - i2 : (AndroidUtilities.displaySize.y - i2) - ActionBar.getCurrentActionBarHeight();
        currentActionBarHeight = i == 0 ? AndroidUtilities.dp(10.0f) : i == 1 ? currentActionBarHeight - AndroidUtilities.dp(10.0f) : Math.round(((float) (currentActionBarHeight - AndroidUtilities.dp(20.0f))) * f) + AndroidUtilities.dp(10.0f);
        return !z ? currentActionBarHeight + ActionBar.getCurrentActionBarHeight() : currentActionBarHeight;
    }

    public void close() {
        try {
            this.windowManager.removeView(this.windowView);
        } catch (Exception e) {
        }
        this.parentSheet = null;
        this.parentActivity = null;
    }

    public int getX() {
        return this.windowLayoutParams.x;
    }

    public int getY() {
        return this.windowLayoutParams.y;
    }

    public void onConfigurationChanged() {
        int i = this.preferences.getInt("sidex", 1);
        int i2 = this.preferences.getInt("sidey", 0);
        float f = this.preferences.getFloat("px", BitmapDescriptorFactory.HUE_RED);
        float f2 = this.preferences.getFloat("py", BitmapDescriptorFactory.HUE_RED);
        this.windowLayoutParams.x = getSideCoord(true, i, f, this.videoWidth);
        this.windowLayoutParams.y = getSideCoord(false, i2, f2, this.videoHeight);
        this.windowManager.updateViewLayout(this.windowView, this.windowLayoutParams);
    }

    public void setX(int i) {
        this.windowLayoutParams.x = i;
        this.windowManager.updateViewLayout(this.windowView, this.windowLayoutParams);
    }

    public void setY(int i) {
        this.windowLayoutParams.y = i;
        this.windowManager.updateViewLayout(this.windowView, this.windowLayoutParams);
    }

    public TextureView show(Activity activity, EmbedBottomSheet embedBottomSheet, View view, float f, int i, WebView webView) {
        View view2;
        this.windowView = new FrameLayout(activity) {
            private boolean dragging;
            private float startX;
            private float startY;

            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                float rawX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();
                if (motionEvent.getAction() == 0) {
                    this.startX = rawX;
                    this.startY = rawY;
                } else if (motionEvent.getAction() == 2 && !this.dragging && (Math.abs(this.startX - rawX) >= AndroidUtilities.getPixelsInCM(0.3f, true) || Math.abs(this.startY - rawY) >= AndroidUtilities.getPixelsInCM(0.3f, false))) {
                    this.dragging = true;
                    this.startX = rawX;
                    this.startY = rawY;
                    if (PipVideoView.this.controlsView != null) {
                        ((ViewParent) PipVideoView.this.controlsView).requestDisallowInterceptTouchEvent(true);
                    }
                    return true;
                }
                return super.onInterceptTouchEvent(motionEvent);
            }

            public boolean onTouchEvent(MotionEvent motionEvent) {
                float f = 1.0f;
                if (!this.dragging) {
                    return false;
                }
                float rawX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();
                if (motionEvent.getAction() == 2) {
                    float f2 = rawX - this.startX;
                    float f3 = rawY - this.startY;
                    LayoutParams access$300 = PipVideoView.this.windowLayoutParams;
                    access$300.x = (int) (f2 + ((float) access$300.x));
                    LayoutParams access$3002 = PipVideoView.this.windowLayoutParams;
                    access$3002.y = (int) (f3 + ((float) access$3002.y));
                    int access$400 = PipVideoView.this.videoWidth / 2;
                    if (PipVideoView.this.windowLayoutParams.x < (-access$400)) {
                        PipVideoView.this.windowLayoutParams.x = -access$400;
                    } else if (PipVideoView.this.windowLayoutParams.x > (AndroidUtilities.displaySize.x - PipVideoView.this.windowLayoutParams.width) + access$400) {
                        PipVideoView.this.windowLayoutParams.x = (AndroidUtilities.displaySize.x - PipVideoView.this.windowLayoutParams.width) + access$400;
                    }
                    if (PipVideoView.this.windowLayoutParams.x < 0) {
                        f = 1.0f + ((((float) PipVideoView.this.windowLayoutParams.x) / ((float) access$400)) * 0.5f);
                    } else if (PipVideoView.this.windowLayoutParams.x > AndroidUtilities.displaySize.x - PipVideoView.this.windowLayoutParams.width) {
                        f = 1.0f - ((((float) ((PipVideoView.this.windowLayoutParams.x - AndroidUtilities.displaySize.x) + PipVideoView.this.windowLayoutParams.width)) / ((float) access$400)) * 0.5f);
                    }
                    if (PipVideoView.this.windowView.getAlpha() != f) {
                        PipVideoView.this.windowView.setAlpha(f);
                    }
                    if (PipVideoView.this.windowLayoutParams.y < (-null)) {
                        PipVideoView.this.windowLayoutParams.y = -null;
                    } else if (PipVideoView.this.windowLayoutParams.y > (AndroidUtilities.displaySize.y - PipVideoView.this.windowLayoutParams.height) + 0) {
                        PipVideoView.this.windowLayoutParams.y = 0 + (AndroidUtilities.displaySize.y - PipVideoView.this.windowLayoutParams.height);
                    }
                    PipVideoView.this.windowManager.updateViewLayout(PipVideoView.this.windowView, PipVideoView.this.windowLayoutParams);
                    this.startX = rawX;
                    this.startY = rawY;
                } else if (motionEvent.getAction() == 1) {
                    this.dragging = false;
                    PipVideoView.this.animateToBoundsMaybe();
                }
                return true;
            }

            public void requestDisallowInterceptTouchEvent(boolean z) {
                super.requestDisallowInterceptTouchEvent(z);
            }
        };
        if (f > 1.0f) {
            this.videoWidth = AndroidUtilities.dp(192.0f);
            this.videoHeight = (int) (((float) this.videoWidth) / f);
        } else {
            this.videoHeight = AndroidUtilities.dp(192.0f);
            this.videoWidth = (int) (((float) this.videoHeight) * f);
        }
        View aspectRatioFrameLayout = new AspectRatioFrameLayout(activity);
        aspectRatioFrameLayout.setAspectRatio(f, i);
        this.windowView.addView(aspectRatioFrameLayout, LayoutHelper.createFrame(-1, -1, 17));
        if (webView != null) {
            ViewGroup viewGroup = (ViewGroup) webView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(webView);
            }
            aspectRatioFrameLayout.addView(webView, LayoutHelper.createFrame(-1, -1.0f));
            view2 = null;
        } else {
            view2 = new TextureView(activity);
            aspectRatioFrameLayout.addView(view2, LayoutHelper.createFrame(-1, -1.0f));
        }
        if (view == null) {
            this.controlsView = new MiniControlsView(activity);
        } else {
            this.controlsView = view;
        }
        this.windowView.addView(this.controlsView, LayoutHelper.createFrame(-1, -1.0f));
        this.windowManager = (WindowManager) ApplicationLoader.applicationContext.getSystemService("window");
        this.preferences = ApplicationLoader.applicationContext.getSharedPreferences("pipconfig", 0);
        int i2 = this.preferences.getInt("sidex", 1);
        int i3 = this.preferences.getInt("sidey", 0);
        float f2 = this.preferences.getFloat("px", BitmapDescriptorFactory.HUE_RED);
        float f3 = this.preferences.getFloat("py", BitmapDescriptorFactory.HUE_RED);
        try {
            this.windowLayoutParams = new LayoutParams();
            this.windowLayoutParams.width = this.videoWidth;
            this.windowLayoutParams.height = this.videoHeight;
            this.windowLayoutParams.x = getSideCoord(true, i2, f2, this.videoWidth);
            this.windowLayoutParams.y = getSideCoord(false, i3, f3, this.videoHeight);
            this.windowLayoutParams.format = -3;
            this.windowLayoutParams.gravity = 51;
            if (VERSION.SDK_INT >= 26) {
                this.windowLayoutParams.type = 2038;
            } else {
                this.windowLayoutParams.type = 2003;
            }
            this.windowLayoutParams.flags = 16777736;
            this.windowManager.addView(this.windowView, this.windowLayoutParams);
            this.parentSheet = embedBottomSheet;
            this.parentActivity = activity;
            return view2;
        } catch (Throwable e) {
            FileLog.e(e);
            return null;
        }
    }
}
