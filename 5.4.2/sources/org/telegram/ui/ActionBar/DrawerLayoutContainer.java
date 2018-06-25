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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    class C38431 implements OnApplyWindowInsetsListener {
        C38431() {
        }

        @SuppressLint({"NewApi"})
        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            DrawerLayoutContainer drawerLayoutContainer = (DrawerLayoutContainer) view;
            AndroidUtilities.statusBarHeight = windowInsets.getSystemWindowInsetTop();
            DrawerLayoutContainer.this.lastInsets = windowInsets;
            boolean z = windowInsets.getSystemWindowInsetTop() <= 0 && DrawerLayoutContainer.this.getBackground() == null;
            drawerLayoutContainer.setWillNotDraw(z);
            drawerLayoutContainer.requestLayout();
            return windowInsets.consumeSystemWindowInsets();
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.DrawerLayoutContainer$2 */
    class C38442 extends AnimatorListenerAdapter {
        C38442() {
        }

        public void onAnimationEnd(Animator animator) {
            DrawerLayoutContainer.this.onDrawerAnimationEnd(true);
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.DrawerLayoutContainer$3 */
    class C38453 extends AnimatorListenerAdapter {
        C38453() {
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
            setOnApplyWindowInsetsListener(new C38431());
            setSystemUiVisibility(1280);
        }
        this.shadowLeft = getResources().getDrawable(R.drawable.menu_shadow);
    }

    @SuppressLint({"NewApi"})
    private void applyMarginInsets(MarginLayoutParams marginLayoutParams, Object obj, int i, boolean z) {
        int i2 = 0;
        WindowInsets windowInsets = (WindowInsets) obj;
        if (i == 3) {
            windowInsets = windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), 0, windowInsets.getSystemWindowInsetBottom());
        } else if (i == 5) {
            windowInsets = windowInsets.replaceSystemWindowInsets(0, windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
        }
        marginLayoutParams.leftMargin = windowInsets.getSystemWindowInsetLeft();
        if (!z) {
            i2 = windowInsets.getSystemWindowInsetTop();
        }
        marginLayoutParams.topMargin = i2;
        marginLayoutParams.rightMargin = windowInsets.getSystemWindowInsetRight();
        marginLayoutParams.bottomMargin = windowInsets.getSystemWindowInsetBottom();
    }

    @SuppressLint({"NewApi"})
    private void dispatchChildInsets(View view, Object obj, int i) {
        WindowInsets windowInsets = (WindowInsets) obj;
        if (i == 3) {
            windowInsets = windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), 0, windowInsets.getSystemWindowInsetBottom());
        } else if (i == 5) {
            windowInsets = windowInsets.replaceSystemWindowInsets(0, windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
        }
        view.dispatchApplyWindowInsets(windowInsets);
    }

    private float getScrimOpacity() {
        return this.scrimOpacity;
    }

    private int getTopInset(Object obj) {
        return (VERSION.SDK_INT < 21 || obj == null) ? 0 : ((WindowInsets) obj).getSystemWindowInsetTop();
    }

    private void onDrawerAnimationEnd(boolean z) {
        this.startedTracking = false;
        this.currentAnimation = null;
        this.drawerOpened = z;
        if (!z && (this.drawerLayout instanceof ListView)) {
            ((ListView) this.drawerLayout).setSelectionFromTop(0, 0);
        }
    }

    private void prepareForDrawerOpen(MotionEvent motionEvent) {
        this.maybeStartTracking = false;
        this.startedTracking = true;
        if (motionEvent != null) {
            this.startedTrackingX = (int) motionEvent.getX();
        }
        this.beginTrackingSent = false;
    }

    private void setScrimOpacity(float f) {
        this.scrimOpacity = f;
        invalidate();
    }

    public void cancelCurrentAnimation() {
        if (this.currentAnimation != null) {
            this.currentAnimation.cancel();
            this.currentAnimation = null;
        }
    }

    public void closeDrawer(boolean z) {
        cancelCurrentAnimation();
        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(this, "drawerPosition", new float[]{BitmapDescriptorFactory.HUE_RED});
        animatorSet.playTogether(animatorArr);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        if (z) {
            animatorSet.setDuration((long) Math.max((int) ((200.0f / ((float) this.drawerLayout.getMeasuredWidth())) * this.drawerPosition), 50));
        } else {
            animatorSet.setDuration(300);
        }
        animatorSet.addListener(new C38453());
        animatorSet.start();
    }

    protected boolean drawChild(Canvas canvas, View view, long j) {
        if (!this.allowDrawContent) {
            return false;
        }
        int height = getHeight();
        Object obj = view != this.drawerLayout ? 1 : null;
        int i = 0;
        int i2 = 0;
        int width = getWidth();
        int save = canvas.save();
        if (obj != null) {
            int childCount = getChildCount();
            int i3 = 0;
            while (i3 < childCount) {
                View childAt = getChildAt(i3);
                int i4 = (childAt.getVisibility() != 0 || childAt == this.drawerLayout) ? i : i3;
                if (childAt != view && childAt.getVisibility() == 0 && childAt == this.drawerLayout && childAt.getHeight() >= height) {
                    i = childAt.getRight();
                    if (i > i2) {
                        i2 = i;
                    }
                }
                i3++;
                i = i4;
            }
            if (i2 != 0) {
                canvas.clipRect(i2, 0, width, getHeight());
            }
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(save);
        if (this.scrimOpacity <= BitmapDescriptorFactory.HUE_RED || obj == null) {
            if (this.shadowLeft != null) {
                float max = Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(this.drawerPosition / ((float) AndroidUtilities.dp(20.0f)), 1.0f));
                if (max != BitmapDescriptorFactory.HUE_RED) {
                    this.shadowLeft.setBounds((int) this.drawerPosition, view.getTop(), ((int) this.drawerPosition) + this.shadowLeft.getIntrinsicWidth(), view.getBottom());
                    this.shadowLeft.setAlpha((int) (max * 255.0f));
                    this.shadowLeft.draw(canvas);
                }
            }
        } else if (indexOfChild(view) == i) {
            this.scrimPaint.setColor(((int) (153.0f * this.scrimOpacity)) << 24);
            canvas.drawRect((float) i2, BitmapDescriptorFactory.HUE_RED, (float) width, (float) getHeight(), this.scrimPaint);
        }
        return drawChild;
    }

    public View getDrawerLayout() {
        return this.drawerLayout;
    }

    public float getDrawerPosition() {
        return this.drawerPosition;
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isDrawerOpened() {
        return this.drawerOpened;
    }

    public void moveDrawerByX(float f) {
        setDrawerPosition(this.drawerPosition + f);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.parentActionBarLayout.checkTransitionAnimation() || onTouchEvent(motionEvent);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.inLayout = true;
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                try {
                    if (this.drawerLayout != childAt) {
                        childAt.layout(layoutParams.leftMargin, layoutParams.topMargin + getPaddingTop(), layoutParams.leftMargin + childAt.getMeasuredWidth(), (layoutParams.topMargin + childAt.getMeasuredHeight()) + getPaddingTop());
                    } else {
                        childAt.layout(-childAt.getMeasuredWidth(), layoutParams.topMargin + getPaddingTop(), 0, (layoutParams.topMargin + childAt.getMeasuredHeight()) + getPaddingTop());
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        }
        this.inLayout = false;
    }

    @SuppressLint({"NewApi"})
    protected void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        setMeasuredDimension(size, size2);
        if (VERSION.SDK_INT < 21) {
            this.inLayout = true;
            if (size2 == AndroidUtilities.displaySize.y + AndroidUtilities.statusBarHeight) {
                if (getLayoutParams() instanceof MarginLayoutParams) {
                    setPadding(0, AndroidUtilities.statusBarHeight, 0, 0);
                }
                size2 = AndroidUtilities.displaySize.y;
            } else if (getLayoutParams() instanceof MarginLayoutParams) {
                setPadding(0, 0, 0, 0);
            }
            this.inLayout = false;
        }
        int i3 = size2;
        Object obj = (this.lastInsets == null || VERSION.SDK_INT < 21) ? null : 1;
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (obj != null) {
                    if (childAt.getFitsSystemWindows()) {
                        dispatchChildInsets(childAt, this.lastInsets, layoutParams.gravity);
                    } else if (childAt.getTag() == null) {
                        applyMarginInsets(layoutParams, this.lastInsets, layoutParams.gravity, VERSION.SDK_INT >= 21);
                    }
                }
                if (this.drawerLayout != childAt) {
                    childAt.measure(MeasureSpec.makeMeasureSpec((size - layoutParams.leftMargin) - layoutParams.rightMargin, 1073741824), MeasureSpec.makeMeasureSpec((i3 - layoutParams.topMargin) - layoutParams.bottomMargin, 1073741824));
                } else {
                    childAt.setPadding(0, 0, 0, 0);
                    childAt.measure(getChildMeasureSpec(i, (this.minDrawerMargin + layoutParams.leftMargin) + layoutParams.rightMargin, layoutParams.width), getChildMeasureSpec(i2, layoutParams.topMargin + layoutParams.bottomMargin, layoutParams.height));
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = true;
        if (this.parentActionBarLayout.checkTransitionAnimation()) {
            return false;
        }
        if (!this.drawerOpened || motionEvent == null || motionEvent.getX() <= this.drawerPosition || this.startedTracking) {
            if (this.allowOpenDrawer && this.parentActionBarLayout.fragmentsStack.size() == 1) {
                if (motionEvent != null && ((motionEvent.getAction() == 0 || motionEvent.getAction() == 2) && !this.startedTracking && !this.maybeStartTracking)) {
                    this.startedTrackingPointerId = motionEvent.getPointerId(0);
                    this.maybeStartTracking = true;
                    this.startedTrackingX = (int) motionEvent.getX();
                    this.startedTrackingY = (int) motionEvent.getY();
                    cancelCurrentAnimation();
                    if (this.velocityTracker != null) {
                        this.velocityTracker.clear();
                    }
                } else if (motionEvent != null && motionEvent.getAction() == 2 && motionEvent.getPointerId(0) == this.startedTrackingPointerId) {
                    if (this.velocityTracker == null) {
                        this.velocityTracker = VelocityTracker.obtain();
                    }
                    float x = (float) ((int) (motionEvent.getX() - ((float) this.startedTrackingX)));
                    float abs = (float) Math.abs(((int) motionEvent.getY()) - this.startedTrackingY);
                    this.velocityTracker.addMovement(motionEvent);
                    if (this.maybeStartTracking && !this.startedTracking && ((x > BitmapDescriptorFactory.HUE_RED && x / 3.0f > Math.abs(abs) && Math.abs(x) >= AndroidUtilities.getPixelsInCM(0.2f, true)) || (x < BitmapDescriptorFactory.HUE_RED && Math.abs(x) >= Math.abs(abs) && Math.abs(x) >= AndroidUtilities.getPixelsInCM(0.4f, true)))) {
                        prepareForDrawerOpen(motionEvent);
                        this.startedTrackingX = (int) motionEvent.getX();
                        requestDisallowInterceptTouchEvent(true);
                    } else if (this.startedTracking) {
                        if (!this.beginTrackingSent) {
                            if (((Activity) getContext()).getCurrentFocus() != null) {
                                AndroidUtilities.hideKeyboard(((Activity) getContext()).getCurrentFocus());
                            }
                            this.beginTrackingSent = true;
                        }
                        moveDrawerByX(x);
                        this.startedTrackingX = (int) motionEvent.getX();
                    }
                } else if (motionEvent == null || (motionEvent != null && motionEvent.getPointerId(0) == this.startedTrackingPointerId && (motionEvent.getAction() == 3 || motionEvent.getAction() == 1 || motionEvent.getAction() == 6))) {
                    if (this.velocityTracker == null) {
                        this.velocityTracker = VelocityTracker.obtain();
                    }
                    this.velocityTracker.computeCurrentVelocity(1000);
                    if (this.startedTracking || !(this.drawerPosition == BitmapDescriptorFactory.HUE_RED || this.drawerPosition == ((float) this.drawerLayout.getMeasuredWidth()))) {
                        float xVelocity = this.velocityTracker.getXVelocity();
                        boolean z2 = (this.drawerPosition < ((float) this.drawerLayout.getMeasuredWidth()) / 2.0f && (xVelocity < 3500.0f || Math.abs(xVelocity) < Math.abs(this.velocityTracker.getYVelocity()))) || (xVelocity < BitmapDescriptorFactory.HUE_RED && Math.abs(xVelocity) >= 3500.0f);
                        if (z2) {
                            if (!this.drawerOpened || Math.abs(xVelocity) < 3500.0f) {
                                z = false;
                            }
                            closeDrawer(z);
                        } else {
                            z2 = !this.drawerOpened && Math.abs(xVelocity) >= 3500.0f;
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
        } else if (motionEvent.getAction() != 1) {
            return true;
        } else {
            closeDrawer(false);
            return true;
        }
    }

    public void openDrawer(boolean z) {
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
            if (z) {
                animatorSet.setDuration((long) Math.max((int) ((200.0f / ((float) this.drawerLayout.getMeasuredWidth())) * (((float) this.drawerLayout.getMeasuredWidth()) - this.drawerPosition)), 50));
            } else {
                animatorSet.setDuration(300);
            }
            animatorSet.addListener(new C38442());
            animatorSet.start();
            this.currentAnimation = animatorSet;
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (this.maybeStartTracking && !this.startedTracking) {
            onTouchEvent(null);
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    public void requestLayout() {
        if (!this.inLayout) {
            super.requestLayout();
        }
    }

    public void setAllowDrawContent(boolean z) {
        if (this.allowDrawContent != z) {
            this.allowDrawContent = z;
            invalidate();
        }
    }

    public void setAllowOpenDrawer(boolean z, boolean z2) {
        this.allowOpenDrawer = z;
        if (!this.allowOpenDrawer && this.drawerPosition != BitmapDescriptorFactory.HUE_RED) {
            if (z2) {
                closeDrawer(true);
                return;
            }
            setDrawerPosition(BitmapDescriptorFactory.HUE_RED);
            onDrawerAnimationEnd(false);
        }
    }

    public void setDrawerLayout(ViewGroup viewGroup) {
        this.drawerLayout = viewGroup;
        addView(this.drawerLayout);
        if (VERSION.SDK_INT >= 21) {
            this.drawerLayout.setFitsSystemWindows(true);
        }
    }

    public void setDrawerPosition(float f) {
        this.drawerPosition = f;
        if (this.drawerPosition > ((float) this.drawerLayout.getMeasuredWidth())) {
            this.drawerPosition = (float) this.drawerLayout.getMeasuredWidth();
        } else if (this.drawerPosition < BitmapDescriptorFactory.HUE_RED) {
            this.drawerPosition = BitmapDescriptorFactory.HUE_RED;
        }
        this.drawerLayout.setTranslationX(this.drawerPosition);
        int i = this.drawerPosition > BitmapDescriptorFactory.HUE_RED ? 0 : 8;
        if (this.drawerLayout.getVisibility() != i) {
            this.drawerLayout.setVisibility(i);
        }
        setScrimOpacity(this.drawerPosition / ((float) this.drawerLayout.getMeasuredWidth()));
    }

    public void setParentActionBarLayout(ActionBarLayout actionBarLayout) {
        this.parentActionBarLayout = actionBarLayout;
    }
}
