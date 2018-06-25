package org.telegram.ui.ActionBar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowInsets;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;

public class DrawerLayoutContainer extends FrameLayout {
    private static final int MIN_DRAWER_MARGIN = 64;
    private boolean allowDrawContent = true;
    private boolean allowOpenDrawer;
    private boolean beginTrackingSent;
    private AnimatorSet currentAnimation;
    private ViewGroup drawerLayout;
    private boolean drawerOpened;
    private float drawerPosition;
    private boolean inLayout;
    private Object lastInsets;
    private boolean maybeStartTracking;
    private int minDrawerMargin = ((int) ((64.0f * AndroidUtilities.density) + 0.5f));
    private int paddingTop;
    private ActionBarLayout parentActionBarLayout;
    private float scrimOpacity;
    private Paint scrimPaint = new Paint();
    private Drawable shadowLeft;
    private boolean startedTracking;
    private int startedTrackingPointerId;
    private int startedTrackingX;
    private int startedTrackingY;
    private VelocityTracker velocityTracker;

    /* renamed from: org.telegram.ui.ActionBar.DrawerLayoutContainer$1 */
    class C20051 implements OnApplyWindowInsetsListener {
        C20051() {
        }

        @SuppressLint({"NewApi"})
        public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
            DrawerLayoutContainer drawerLayout = (DrawerLayoutContainer) v;
            AndroidUtilities.statusBarHeight = insets.getSystemWindowInsetTop();
            DrawerLayoutContainer.this.lastInsets = insets;
            boolean z = insets.getSystemWindowInsetTop() <= 0 && DrawerLayoutContainer.this.getBackground() == null;
            drawerLayout.setWillNotDraw(z);
            drawerLayout.requestLayout();
            return insets.consumeSystemWindowInsets();
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.DrawerLayoutContainer$2 */
    class C20062 extends AnimatorListenerAdapter {
        C20062() {
        }

        public void onAnimationEnd(Animator animator) {
            DrawerLayoutContainer.this.onDrawerAnimationEnd(true);
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.DrawerLayoutContainer$3 */
    class C20073 extends AnimatorListenerAdapter {
        C20073() {
        }

        public void onAnimationEnd(Animator animator) {
            DrawerLayoutContainer.this.onDrawerAnimationEnd(false);
        }
    }

    public DrawerLayoutContainer(Context context) {
        super(context);
        setDescendantFocusability(262144);
        setFocusableInTouchMode(true);
        if (VERSION.SDK_INT >= 21) {
            setFitsSystemWindows(true);
            setOnApplyWindowInsetsListener(new C20051());
            setSystemUiVisibility(1280);
        }
        this.shadowLeft = getResources().getDrawable(R.drawable.menu_shadow);
    }

    @SuppressLint({"NewApi"})
    private void dispatchChildInsets(View child, Object insets, int drawerGravity) {
        WindowInsets wi = (WindowInsets) insets;
        if (drawerGravity == 3) {
            wi = wi.replaceSystemWindowInsets(wi.getSystemWindowInsetLeft(), wi.getSystemWindowInsetTop(), 0, wi.getSystemWindowInsetBottom());
        } else if (drawerGravity == 5) {
            wi = wi.replaceSystemWindowInsets(0, wi.getSystemWindowInsetTop(), wi.getSystemWindowInsetRight(), wi.getSystemWindowInsetBottom());
        }
        child.dispatchApplyWindowInsets(wi);
    }

    @SuppressLint({"NewApi"})
    private void applyMarginInsets(MarginLayoutParams lp, Object insets, int drawerGravity, boolean topOnly) {
        int i = 0;
        WindowInsets wi = (WindowInsets) insets;
        if (drawerGravity == 3) {
            wi = wi.replaceSystemWindowInsets(wi.getSystemWindowInsetLeft(), wi.getSystemWindowInsetTop(), 0, wi.getSystemWindowInsetBottom());
        } else if (drawerGravity == 5) {
            wi = wi.replaceSystemWindowInsets(0, wi.getSystemWindowInsetTop(), wi.getSystemWindowInsetRight(), wi.getSystemWindowInsetBottom());
        }
        lp.leftMargin = wi.getSystemWindowInsetLeft();
        if (!topOnly) {
            i = wi.getSystemWindowInsetTop();
        }
        lp.topMargin = i;
        lp.rightMargin = wi.getSystemWindowInsetRight();
        lp.bottomMargin = wi.getSystemWindowInsetBottom();
    }

    private int getTopInset(Object insets) {
        if (VERSION.SDK_INT < 21 || insets == null) {
            return 0;
        }
        return ((WindowInsets) insets).getSystemWindowInsetTop();
    }

    public void setDrawerLayout(ViewGroup layout) {
        this.drawerLayout = layout;
        addView(this.drawerLayout);
        if (VERSION.SDK_INT >= 21) {
            this.drawerLayout.setFitsSystemWindows(true);
        }
    }

    public void moveDrawerByX(float dx) {
        setDrawerPosition(this.drawerPosition + dx);
    }

    public void setDrawerPosition(float value) {
        this.drawerPosition = value;
        if (this.drawerPosition > ((float) this.drawerLayout.getMeasuredWidth())) {
            this.drawerPosition = (float) this.drawerLayout.getMeasuredWidth();
        } else if (this.drawerPosition < 0.0f) {
            this.drawerPosition = 0.0f;
        }
        this.drawerLayout.setTranslationX(this.drawerPosition);
        int newVisibility = this.drawerPosition > 0.0f ? 0 : 8;
        if (this.drawerLayout.getVisibility() != newVisibility) {
            this.drawerLayout.setVisibility(newVisibility);
        }
        setScrimOpacity(this.drawerPosition / ((float) this.drawerLayout.getMeasuredWidth()));
    }

    public float getDrawerPosition() {
        return this.drawerPosition;
    }

    public void cancelCurrentAnimation() {
        if (this.currentAnimation != null) {
            this.currentAnimation.cancel();
            this.currentAnimation = null;
        }
    }

    public void openDrawer(boolean fast) {
        if (this.allowOpenDrawer) {
            if (!(!AndroidUtilities.isTablet() || this.parentActionBarLayout == null || this.parentActionBarLayout.parentActivity == null)) {
                AndroidUtilities.hideKeyboard(this.parentActionBarLayout.parentActivity.getCurrentFocus());
            }
            cancelCurrentAnimation();
            AnimatorSet animatorSet = new AnimatorSet();
            Animator[] animatorArr = new Animator[1];
            animatorArr[0] = ObjectAnimator.ofFloat(this, "drawerPosition", new float[]{(float) this.drawerLayout.getMeasuredWidth()});
            animatorSet.playTogether(animatorArr);
            animatorSet.setInterpolator(new DecelerateInterpolator());
            if (fast) {
                animatorSet.setDuration((long) Math.max((int) ((200.0f / ((float) this.drawerLayout.getMeasuredWidth())) * (((float) this.drawerLayout.getMeasuredWidth()) - this.drawerPosition)), 50));
            } else {
                animatorSet.setDuration(300);
            }
            animatorSet.addListener(new C20062());
            animatorSet.start();
            this.currentAnimation = animatorSet;
        }
    }

    public void closeDrawer(boolean fast) {
        cancelCurrentAnimation();
        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(this, "drawerPosition", new float[]{0.0f});
        animatorSet.playTogether(animatorArr);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        if (fast) {
            animatorSet.setDuration((long) Math.max((int) ((200.0f / ((float) this.drawerLayout.getMeasuredWidth())) * this.drawerPosition), 50));
        } else {
            animatorSet.setDuration(300);
        }
        animatorSet.addListener(new C20073());
        animatorSet.start();
    }

    private void onDrawerAnimationEnd(boolean opened) {
        this.startedTracking = false;
        this.currentAnimation = null;
        this.drawerOpened = opened;
        if (!opened && (this.drawerLayout instanceof ListView)) {
            ((ListView) this.drawerLayout).setSelectionFromTop(0, 0);
        }
    }

    private void setScrimOpacity(float value) {
        this.scrimOpacity = value;
        invalidate();
    }

    private float getScrimOpacity() {
        return this.scrimOpacity;
    }

    public View getDrawerLayout() {
        return this.drawerLayout;
    }

    public void setParentActionBarLayout(ActionBarLayout layout) {
        this.parentActionBarLayout = layout;
    }

    public void setAllowOpenDrawer(boolean value, boolean animated) {
        this.allowOpenDrawer = value;
        if (!this.allowOpenDrawer && this.drawerPosition != 0.0f) {
            if (animated) {
                closeDrawer(true);
                return;
            }
            setDrawerPosition(0.0f);
            onDrawerAnimationEnd(false);
        }
    }

    private void prepareForDrawerOpen(MotionEvent ev) {
        this.maybeStartTracking = false;
        this.startedTracking = true;
        if (ev != null) {
            this.startedTrackingX = (int) ev.getX();
        }
        this.beginTrackingSent = false;
    }

    public boolean isDrawerOpened() {
        return this.drawerOpened;
    }

    public void setAllowDrawContent(boolean value) {
        if (this.allowDrawContent != value) {
            this.allowDrawContent = value;
            invalidate();
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        boolean z = true;
        if (this.parentActionBarLayout.checkTransitionAnimation()) {
            return false;
        }
        if (!this.drawerOpened || ev == null || ev.getX() <= this.drawerPosition || this.startedTracking) {
            if (this.allowOpenDrawer && this.parentActionBarLayout.fragmentsStack.size() == 1) {
                if (ev != null && ((ev.getAction() == 0 || ev.getAction() == 2) && !this.startedTracking && !this.maybeStartTracking)) {
                    this.startedTrackingPointerId = ev.getPointerId(0);
                    this.maybeStartTracking = true;
                    this.startedTrackingX = (int) ev.getX();
                    this.startedTrackingY = (int) ev.getY();
                    cancelCurrentAnimation();
                    if (this.velocityTracker != null) {
                        this.velocityTracker.clear();
                    }
                } else if (ev != null && ev.getAction() == 2 && ev.getPointerId(0) == this.startedTrackingPointerId) {
                    if (this.velocityTracker == null) {
                        this.velocityTracker = VelocityTracker.obtain();
                    }
                    float dx = (float) ((int) (ev.getX() - ((float) this.startedTrackingX)));
                    float dy = (float) Math.abs(((int) ev.getY()) - this.startedTrackingY);
                    this.velocityTracker.addMovement(ev);
                    if (this.maybeStartTracking && !this.startedTracking && ((dx > 0.0f && dx / 3.0f > Math.abs(dy) && Math.abs(dx) >= AndroidUtilities.getPixelsInCM(0.2f, true)) || (dx < 0.0f && Math.abs(dx) >= Math.abs(dy) && Math.abs(dx) >= AndroidUtilities.getPixelsInCM(0.4f, true)))) {
                        prepareForDrawerOpen(ev);
                        this.startedTrackingX = (int) ev.getX();
                        requestDisallowInterceptTouchEvent(true);
                    } else if (this.startedTracking) {
                        if (!this.beginTrackingSent) {
                            if (((Activity) getContext()).getCurrentFocus() != null) {
                                AndroidUtilities.hideKeyboard(((Activity) getContext()).getCurrentFocus());
                            }
                            this.beginTrackingSent = true;
                        }
                        moveDrawerByX(dx);
                        this.startedTrackingX = (int) ev.getX();
                    }
                } else if (ev == null || (ev != null && ev.getPointerId(0) == this.startedTrackingPointerId && (ev.getAction() == 3 || ev.getAction() == 1 || ev.getAction() == 6))) {
                    if (this.velocityTracker == null) {
                        this.velocityTracker = VelocityTracker.obtain();
                    }
                    this.velocityTracker.computeCurrentVelocity(1000);
                    if (this.startedTracking || !(this.drawerPosition == 0.0f || this.drawerPosition == ((float) this.drawerLayout.getMeasuredWidth()))) {
                        boolean backAnimation;
                        float velX = this.velocityTracker.getXVelocity();
                        float velY = this.velocityTracker.getYVelocity();
                        if ((this.drawerPosition >= ((float) this.drawerLayout.getMeasuredWidth()) / 2.0f || (velX >= 3500.0f && Math.abs(velX) >= Math.abs(velY))) && (velX >= 0.0f || Math.abs(velX) < 3500.0f)) {
                            backAnimation = false;
                        } else {
                            backAnimation = true;
                        }
                        if (backAnimation) {
                            if (!this.drawerOpened || Math.abs(velX) < 3500.0f) {
                                z = false;
                            }
                            closeDrawer(z);
                        } else {
                            boolean z2;
                            if (this.drawerOpened || Math.abs(velX) < 3500.0f) {
                                z2 = false;
                            } else {
                                z2 = true;
                            }
                            openDrawer(z2);
                        }
                    }
                    this.startedTracking = false;
                    this.maybeStartTracking = false;
                    if (this.velocityTracker != null) {
                        this.velocityTracker.recycle();
                        this.velocityTracker = null;
                    }
                }
            }
            return this.startedTracking;
        } else if (ev.getAction() != 1) {
            return true;
        } else {
            closeDrawer(false);
            return true;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.parentActionBarLayout.checkTransitionAnimation() || onTouchEvent(ev);
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (this.maybeStartTracking && !this.startedTracking) {
            onTouchEvent(null);
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.inLayout = true;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                try {
                    if (this.drawerLayout != child) {
                        child.layout(lp.leftMargin, lp.topMargin + getPaddingTop(), lp.leftMargin + child.getMeasuredWidth(), (lp.topMargin + child.getMeasuredHeight()) + getPaddingTop());
                    } else {
                        child.layout(-child.getMeasuredWidth(), lp.topMargin + getPaddingTop(), 0, (lp.topMargin + child.getMeasuredHeight()) + getPaddingTop());
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        }
        this.inLayout = false;
    }

    public void requestLayout() {
        if (!this.inLayout) {
            super.requestLayout();
        }
    }

    @SuppressLint({"NewApi"})
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
        if (VERSION.SDK_INT < 21) {
            this.inLayout = true;
            if (heightSize == AndroidUtilities.displaySize.y + AndroidUtilities.statusBarHeight) {
                if (getLayoutParams() instanceof MarginLayoutParams) {
                    setPadding(0, AndroidUtilities.statusBarHeight, 0, 0);
                }
                heightSize = AndroidUtilities.displaySize.y;
            } else if (getLayoutParams() instanceof MarginLayoutParams) {
                setPadding(0, 0, 0, 0);
            }
            this.inLayout = false;
        }
        boolean applyInsets = this.lastInsets != null && VERSION.SDK_INT >= 21;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (applyInsets) {
                    if (child.getFitsSystemWindows()) {
                        dispatchChildInsets(child, this.lastInsets, lp.gravity);
                    } else if (child.getTag() == null) {
                        applyMarginInsets(lp, this.lastInsets, lp.gravity, VERSION.SDK_INT >= 21);
                    }
                }
                if (this.drawerLayout != child) {
                    child.measure(MeasureSpec.makeMeasureSpec((widthSize - lp.leftMargin) - lp.rightMargin, 1073741824), MeasureSpec.makeMeasureSpec((heightSize - lp.topMargin) - lp.bottomMargin, 1073741824));
                } else {
                    child.setPadding(0, 0, 0, 0);
                    child.measure(getChildMeasureSpec(widthMeasureSpec, (this.minDrawerMargin + lp.leftMargin) + lp.rightMargin, lp.width), getChildMeasureSpec(heightMeasureSpec, lp.topMargin + lp.bottomMargin, lp.height));
                }
            }
        }
    }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (!this.allowDrawContent) {
            return false;
        }
        int height = getHeight();
        boolean drawingContent = child != this.drawerLayout;
        int lastVisibleChild = 0;
        int clipLeft = 0;
        int clipRight = getWidth();
        int restoreCount = canvas.save();
        if (drawingContent) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View v = getChildAt(i);
                if (v.getVisibility() == 0 && v != this.drawerLayout) {
                    lastVisibleChild = i;
                }
                if (v != child && v.getVisibility() == 0 && v == this.drawerLayout && v.getHeight() >= height) {
                    int vright = v.getRight();
                    if (vright > clipLeft) {
                        clipLeft = vright;
                    }
                }
            }
            if (clipLeft != 0) {
                canvas.clipRect(clipLeft, 0, clipRight, getHeight());
            }
        }
        boolean result = super.drawChild(canvas, child, drawingTime);
        canvas.restoreToCount(restoreCount);
        if (this.scrimOpacity <= 0.0f || !drawingContent) {
            if (this.shadowLeft == null) {
                return result;
            }
            float alpha = Math.max(0.0f, Math.min(this.drawerPosition / ((float) AndroidUtilities.dp(20.0f)), 1.0f));
            if (alpha == 0.0f) {
                return result;
            }
            this.shadowLeft.setBounds((int) this.drawerPosition, child.getTop(), ((int) this.drawerPosition) + this.shadowLeft.getIntrinsicWidth(), child.getBottom());
            this.shadowLeft.setAlpha((int) (255.0f * alpha));
            this.shadowLeft.draw(canvas);
            return result;
        } else if (indexOfChild(child) != lastVisibleChild) {
            return result;
        } else {
            this.scrimPaint.setColor(((int) (153.0f * this.scrimOpacity)) << 24);
            canvas.drawRect((float) clipLeft, 0.0f, (float) clipRight, (float) getHeight(), this.scrimPaint);
            return result;
        }
    }

    public boolean hasOverlappingRendering() {
        return false;
    }
}
