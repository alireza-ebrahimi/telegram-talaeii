package org.telegram.ui.ActionBar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.Components.LayoutHelper;

public class BottomSheet extends Dialog {
    protected static int backgroundPaddingLeft;
    protected static int backgroundPaddingTop;
    private AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
    private boolean allowCustomAnimation = true;
    private boolean allowDrawContent = true;
    private boolean allowNestedScroll = true;
    private boolean applyBottomPadding = true;
    private boolean applyTopPadding = true;
    protected ColorDrawable backDrawable = new ColorDrawable(-16777216);
    protected ContainerView container;
    protected ViewGroup containerView;
    protected AnimatorSet currentSheetAnimation;
    private View customView;
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private BottomSheetDelegateInterface delegate;
    private boolean dismissed;
    private boolean focusable;
    protected boolean fullWidth;
    private int[] itemIcons;
    private ArrayList<BottomSheetCell> itemViews = new ArrayList();
    private CharSequence[] items;
    private WindowInsets lastInsets;
    private int layoutCount;
    private OnClickListener onClickListener;
    private Drawable shadowDrawable;
    private boolean showWithoutAnimation;
    private Runnable startAnimationRunnable;
    private int tag;
    private CharSequence title;
    private int touchSlop;
    private boolean useFastDismiss;
    private boolean useHardwareLayer = true;

    protected class ContainerView extends FrameLayout implements NestedScrollingParent {
        private AnimatorSet currentAnimation = null;
        private boolean maybeStartTracking = false;
        private NestedScrollingParentHelper nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        private boolean startedTracking = false;
        private int startedTrackingPointerId = -1;
        private int startedTrackingX;
        private int startedTrackingY;
        private VelocityTracker velocityTracker = null;

        /* renamed from: org.telegram.ui.ActionBar.BottomSheet$ContainerView$1 */
        class C20041 extends AnimatorListenerAdapter {
            C20041() {
            }

            public void onAnimationEnd(Animator animation) {
                if (ContainerView.this.currentAnimation != null && ContainerView.this.currentAnimation.equals(animation)) {
                    ContainerView.this.currentAnimation = null;
                }
            }
        }

        public ContainerView(Context context) {
            super(context);
        }

        public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
            return !BottomSheet.this.dismissed && BottomSheet.this.allowNestedScroll && nestedScrollAxes == 2 && !BottomSheet.this.canDismissWithSwipe();
        }

        public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
            this.nestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
            if (!BottomSheet.this.dismissed && BottomSheet.this.allowNestedScroll) {
                cancelCurrentAnimation();
            }
        }

        public void onStopNestedScroll(View target) {
            this.nestedScrollingParentHelper.onStopNestedScroll(target);
            if (!BottomSheet.this.dismissed && BottomSheet.this.allowNestedScroll) {
                float currentTranslation = BottomSheet.this.containerView.getTranslationY();
                checkDismiss(0.0f, 0.0f);
            }
        }

        public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
            if (!BottomSheet.this.dismissed && BottomSheet.this.allowNestedScroll) {
                cancelCurrentAnimation();
                if (dyUnconsumed != 0) {
                    float currentTranslation = BottomSheet.this.containerView.getTranslationY() - ((float) dyUnconsumed);
                    if (currentTranslation < 0.0f) {
                        currentTranslation = 0.0f;
                    }
                    BottomSheet.this.containerView.setTranslationY(currentTranslation);
                }
            }
        }

        public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
            if (!BottomSheet.this.dismissed && BottomSheet.this.allowNestedScroll) {
                cancelCurrentAnimation();
                float currentTranslation = BottomSheet.this.containerView.getTranslationY();
                if (currentTranslation > 0.0f && dy > 0) {
                    currentTranslation -= (float) dy;
                    consumed[1] = dy;
                    if (currentTranslation < 0.0f) {
                        currentTranslation = 0.0f;
                        consumed[1] = (int) (((float) consumed[1]) + 0.0f);
                    }
                    BottomSheet.this.containerView.setTranslationY(currentTranslation);
                }
            }
        }

        public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
            return false;
        }

        public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
            return false;
        }

        public int getNestedScrollAxes() {
            return this.nestedScrollingParentHelper.getNestedScrollAxes();
        }

        private void checkDismiss(float velX, float velY) {
            boolean backAnimation;
            float translationY = BottomSheet.this.containerView.getTranslationY();
            if ((translationY >= AndroidUtilities.getPixelsInCM(0.8f, false) || (velY >= 3500.0f && Math.abs(velY) >= Math.abs(velX))) && (velY >= 0.0f || Math.abs(velY) < 3500.0f)) {
                backAnimation = false;
            } else {
                backAnimation = true;
            }
            if (backAnimation) {
                this.currentAnimation = new AnimatorSet();
                AnimatorSet animatorSet = this.currentAnimation;
                Animator[] animatorArr = new Animator[1];
                animatorArr[0] = ObjectAnimator.ofFloat(BottomSheet.this.containerView, "translationY", new float[]{0.0f});
                animatorSet.playTogether(animatorArr);
                this.currentAnimation.setDuration((long) ((int) ((translationY / AndroidUtilities.getPixelsInCM(0.8f, false)) * 150.0f)));
                this.currentAnimation.setInterpolator(new DecelerateInterpolator());
                this.currentAnimation.addListener(new C20041());
                this.currentAnimation.start();
                return;
            }
            boolean allowOld = BottomSheet.this.allowCustomAnimation;
            BottomSheet.this.allowCustomAnimation = false;
            BottomSheet.this.useFastDismiss = true;
            BottomSheet.this.dismiss();
            BottomSheet.this.allowCustomAnimation = allowOld;
        }

        private void cancelCurrentAnimation() {
            if (this.currentAnimation != null) {
                this.currentAnimation.cancel();
                this.currentAnimation = null;
            }
        }

        public boolean onTouchEvent(MotionEvent ev) {
            if (BottomSheet.this.dismissed) {
                return false;
            }
            if (BottomSheet.this.onContainerTouchEvent(ev)) {
                return true;
            }
            if (BottomSheet.this.canDismissWithTouchOutside() && ev != null && ((ev.getAction() == 0 || ev.getAction() == 2) && ((!this.startedTracking && !this.maybeStartTracking) || ev.getPointerCount() == 1))) {
                this.startedTrackingX = (int) ev.getX();
                this.startedTrackingY = (int) ev.getY();
                if (this.startedTrackingY < BottomSheet.this.containerView.getTop() || this.startedTrackingX < BottomSheet.this.containerView.getLeft() || this.startedTrackingX > BottomSheet.this.containerView.getRight()) {
                    BottomSheet.this.dismiss();
                    return true;
                }
                this.startedTrackingPointerId = ev.getPointerId(0);
                this.maybeStartTracking = true;
                cancelCurrentAnimation();
                if (this.velocityTracker != null) {
                    this.velocityTracker.clear();
                }
            } else if (ev != null && ev.getAction() == 2 && ev.getPointerId(0) == this.startedTrackingPointerId) {
                if (this.velocityTracker == null) {
                    this.velocityTracker = VelocityTracker.obtain();
                }
                float dx = (float) Math.abs((int) (ev.getX() - ((float) this.startedTrackingX)));
                float dy = (float) (((int) ev.getY()) - this.startedTrackingY);
                this.velocityTracker.addMovement(ev);
                if (this.maybeStartTracking && !this.startedTracking && dy > 0.0f && dy / 3.0f > Math.abs(dx) && Math.abs(dy) >= ((float) BottomSheet.this.touchSlop)) {
                    this.startedTrackingY = (int) ev.getY();
                    this.maybeStartTracking = false;
                    this.startedTracking = true;
                    requestDisallowInterceptTouchEvent(true);
                } else if (this.startedTracking) {
                    translationY = BottomSheet.this.containerView.getTranslationY() + dy;
                    if (translationY < 0.0f) {
                        translationY = 0.0f;
                    }
                    BottomSheet.this.containerView.setTranslationY(translationY);
                    this.startedTrackingY = (int) ev.getY();
                }
            } else if (ev == null || (ev != null && ev.getPointerId(0) == this.startedTrackingPointerId && (ev.getAction() == 3 || ev.getAction() == 1 || ev.getAction() == 6))) {
                if (this.velocityTracker == null) {
                    this.velocityTracker = VelocityTracker.obtain();
                }
                this.velocityTracker.computeCurrentVelocity(1000);
                translationY = BottomSheet.this.containerView.getTranslationY();
                if (this.startedTracking || translationY != 0.0f) {
                    checkDismiss(this.velocityTracker.getXVelocity(), this.velocityTracker.getYVelocity());
                    this.startedTracking = false;
                } else {
                    this.maybeStartTracking = false;
                    this.startedTracking = false;
                }
                if (this.velocityTracker != null) {
                    this.velocityTracker.recycle();
                    this.velocityTracker = null;
                }
                this.startedTrackingPointerId = -1;
            }
            if (this.startedTracking || !BottomSheet.this.canDismissWithSwipe()) {
                return true;
            }
            return false;
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            boolean isPortrait;
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            if (BottomSheet.this.lastInsets != null && VERSION.SDK_INT >= 21) {
                height -= BottomSheet.this.lastInsets.getSystemWindowInsetBottom();
            }
            setMeasuredDimension(width, height);
            if (BottomSheet.this.lastInsets != null && VERSION.SDK_INT >= 21) {
                width -= BottomSheet.this.lastInsets.getSystemWindowInsetRight() + BottomSheet.this.lastInsets.getSystemWindowInsetLeft();
            }
            if (width < height) {
                isPortrait = true;
            } else {
                isPortrait = false;
            }
            if (BottomSheet.this.containerView != null) {
                if (BottomSheet.this.fullWidth) {
                    BottomSheet.this.containerView.measure(MeasureSpec.makeMeasureSpec((BottomSheet.backgroundPaddingLeft * 2) + width, 1073741824), MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE));
                } else {
                    int widthSpec;
                    if (AndroidUtilities.isTablet()) {
                        widthSpec = MeasureSpec.makeMeasureSpec(((int) (((float) Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y)) * 0.8f)) + (BottomSheet.backgroundPaddingLeft * 2), 1073741824);
                    } else {
                        widthSpec = MeasureSpec.makeMeasureSpec(isPortrait ? (BottomSheet.backgroundPaddingLeft * 2) + width : ((int) Math.max(((float) width) * 0.8f, (float) Math.min(AndroidUtilities.dp(480.0f), width))) + (BottomSheet.backgroundPaddingLeft * 2), 1073741824);
                    }
                    BottomSheet.this.containerView.measure(widthSpec, MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE));
                }
            }
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (!(child.getVisibility() == 8 || child == BottomSheet.this.containerView || BottomSheet.this.onCustomMeasure(child, width, height))) {
                    measureChildWithMargins(child, MeasureSpec.makeMeasureSpec(width, 1073741824), 0, MeasureSpec.makeMeasureSpec(height, 1073741824), 0);
                }
            }
        }

        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            BottomSheet.this.layoutCount = BottomSheet.this.layoutCount - 1;
            if (BottomSheet.this.containerView != null) {
                if (BottomSheet.this.lastInsets != null && VERSION.SDK_INT >= 21) {
                    left += BottomSheet.this.lastInsets.getSystemWindowInsetLeft();
                    right -= BottomSheet.this.lastInsets.getSystemWindowInsetRight();
                }
                int t = (bottom - top) - BottomSheet.this.containerView.getMeasuredHeight();
                int l = ((right - left) - BottomSheet.this.containerView.getMeasuredWidth()) / 2;
                if (BottomSheet.this.lastInsets != null && VERSION.SDK_INT >= 21) {
                    l += BottomSheet.this.lastInsets.getSystemWindowInsetLeft();
                }
                BottomSheet.this.containerView.layout(l, t, BottomSheet.this.containerView.getMeasuredWidth() + l, BottomSheet.this.containerView.getMeasuredHeight() + t);
            }
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                if (!(child.getVisibility() == 8 || child == BottomSheet.this.containerView || BottomSheet.this.onCustomLayout(child, left, top, right, bottom))) {
                    int childLeft;
                    int childTop;
                    LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    int width = child.getMeasuredWidth();
                    int height = child.getMeasuredHeight();
                    int gravity = lp.gravity;
                    if (gravity == -1) {
                        gravity = 51;
                    }
                    int verticalGravity = gravity & 112;
                    switch ((gravity & 7) & 7) {
                        case 1:
                            childLeft = ((((right - left) - width) / 2) + lp.leftMargin) - lp.rightMargin;
                            break;
                        case 5:
                            childLeft = (right - width) - lp.rightMargin;
                            break;
                        default:
                            childLeft = lp.leftMargin;
                            break;
                    }
                    switch (verticalGravity) {
                        case 16:
                            childTop = ((((bottom - top) - height) / 2) + lp.topMargin) - lp.bottomMargin;
                            break;
                        case 48:
                            childTop = lp.topMargin;
                            break;
                        case 80:
                            childTop = ((bottom - top) - height) - lp.bottomMargin;
                            break;
                        default:
                            childTop = lp.topMargin;
                            break;
                    }
                    if (BottomSheet.this.lastInsets != null && VERSION.SDK_INT >= 21) {
                        childLeft += BottomSheet.this.lastInsets.getSystemWindowInsetLeft();
                    }
                    child.layout(childLeft, childTop, childLeft + width, childTop + height);
                }
            }
            if (BottomSheet.this.layoutCount == 0 && BottomSheet.this.startAnimationRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(BottomSheet.this.startAnimationRunnable);
                BottomSheet.this.startAnimationRunnable.run();
                BottomSheet.this.startAnimationRunnable = null;
            }
        }

        public boolean onInterceptTouchEvent(MotionEvent event) {
            if (BottomSheet.this.canDismissWithSwipe()) {
                return onTouchEvent(event);
            }
            return super.onInterceptTouchEvent(event);
        }

        public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            if (this.maybeStartTracking && !this.startedTracking) {
                onTouchEvent(null);
            }
            super.requestDisallowInterceptTouchEvent(disallowIntercept);
        }

        public boolean hasOverlappingRendering() {
            return false;
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            BottomSheet.this.onContainerDraw(canvas);
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.BottomSheet$2 */
    class C19942 implements OnApplyWindowInsetsListener {
        C19942() {
        }

        @SuppressLint({"NewApi"})
        public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
            BottomSheet.this.lastInsets = insets;
            v.requestLayout();
            return insets.consumeSystemWindowInsets();
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.BottomSheet$4 */
    class C19964 implements OnTouchListener {
        C19964() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.BottomSheet$5 */
    class C19975 implements View.OnClickListener {
        C19975() {
        }

        public void onClick(View v) {
            BottomSheet.this.dismissWithButtonClick(((Integer) v.getTag()).intValue());
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.BottomSheet$6 */
    class C19986 implements Runnable {
        C19986() {
        }

        public void run() {
            if (BottomSheet.this.startAnimationRunnable == this && !BottomSheet.this.dismissed) {
                BottomSheet.this.startAnimationRunnable = null;
                BottomSheet.this.startOpenAnimation();
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.BottomSheet$7 */
    class C19997 extends AnimatorListenerAdapter {
        C19997() {
        }

        public void onAnimationEnd(Animator animation) {
            if (BottomSheet.this.currentSheetAnimation != null && BottomSheet.this.currentSheetAnimation.equals(animation)) {
                BottomSheet.this.currentSheetAnimation = null;
                if (BottomSheet.this.delegate != null) {
                    BottomSheet.this.delegate.onOpenAnimationEnd();
                }
                if (BottomSheet.this.useHardwareLayer) {
                    BottomSheet.this.container.setLayerType(0, null);
                }
            }
        }

        public void onAnimationCancel(Animator animation) {
            if (BottomSheet.this.currentSheetAnimation != null && BottomSheet.this.currentSheetAnimation.equals(animation)) {
                BottomSheet.this.currentSheetAnimation = null;
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.BottomSheet$9 */
    class C20039 extends AnimatorListenerAdapter {

        /* renamed from: org.telegram.ui.ActionBar.BottomSheet$9$1 */
        class C20021 implements Runnable {
            C20021() {
            }

            public void run() {
                try {
                    BottomSheet.this.dismissInternal();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        }

        C20039() {
        }

        public void onAnimationEnd(Animator animation) {
            if (BottomSheet.this.currentSheetAnimation != null && BottomSheet.this.currentSheetAnimation.equals(animation)) {
                BottomSheet.this.currentSheetAnimation = null;
                AndroidUtilities.runOnUIThread(new C20021());
            }
        }

        public void onAnimationCancel(Animator animation) {
            if (BottomSheet.this.currentSheetAnimation != null && BottomSheet.this.currentSheetAnimation.equals(animation)) {
                BottomSheet.this.currentSheetAnimation = null;
            }
        }
    }

    public static class BottomSheetCell extends FrameLayout {
        private ImageView imageView;
        private TextView textView;

        public BottomSheetCell(Context context, int type) {
            int i = 3;
            super(context);
            setBackgroundDrawable(Theme.getSelectorDrawable(false));
            setPadding(AndroidUtilities.dp(16.0f), 0, AndroidUtilities.dp(16.0f), 0);
            this.imageView = new ImageView(context);
            this.imageView.setScaleType(ScaleType.CENTER);
            this.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogIcon), Mode.MULTIPLY));
            addView(this.imageView, LayoutHelper.createFrame(24, 24, (LocaleController.isRTL ? 5 : 3) | 16));
            this.textView = new TextView(context);
            this.textView.setLines(1);
            this.textView.setSingleLine(true);
            this.textView.setGravity(1);
            this.textView.setEllipsize(TruncateAt.END);
            if (type == 0) {
                this.textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                this.textView.setTextSize(1, 16.0f);
                View view = this.textView;
                if (LocaleController.isRTL) {
                    i = 5;
                }
                addView(view, LayoutHelper.createFrame(-2, -2, i | 16));
            } else if (type == 1) {
                this.textView.setGravity(17);
                this.textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                this.textView.setTextSize(1, 14.0f);
                this.textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                addView(this.textView, LayoutHelper.createFrame(-1, -1.0f));
            }
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
        }

        public void setTextColor(int color) {
            this.textView.setTextColor(color);
        }

        public void setGravity(int gravity) {
            this.textView.setGravity(gravity);
        }

        public void setTextAndIcon(CharSequence text, int icon) {
            this.textView.setText(text);
            if (icon != 0) {
                this.imageView.setImageResource(icon);
                this.imageView.setVisibility(0);
                this.textView.setPadding(LocaleController.isRTL ? 0 : AndroidUtilities.dp(56.0f), 0, LocaleController.isRTL ? AndroidUtilities.dp(56.0f) : 0, 0);
                return;
            }
            this.imageView.setVisibility(4);
            this.textView.setPadding(0, 0, 0, 0);
        }
    }

    public interface BottomSheetDelegateInterface {
        boolean canDismiss();

        void onOpenAnimationEnd();

        void onOpenAnimationStart();
    }

    public static class BottomSheetDelegate implements BottomSheetDelegateInterface {
        public void onOpenAnimationStart() {
        }

        public void onOpenAnimationEnd() {
        }

        public boolean canDismiss() {
            return true;
        }
    }

    public static class Builder {
        private BottomSheet bottomSheet;

        public Builder(Context context) {
            this.bottomSheet = new BottomSheet(context, false);
        }

        public Builder(Context context, boolean needFocus) {
            this.bottomSheet = new BottomSheet(context, needFocus);
        }

        public Builder setItems(CharSequence[] items, OnClickListener onClickListener) {
            this.bottomSheet.items = items;
            this.bottomSheet.onClickListener = onClickListener;
            return this;
        }

        public Builder setItems(CharSequence[] items, int[] icons, OnClickListener onClickListener) {
            this.bottomSheet.items = items;
            this.bottomSheet.itemIcons = icons;
            this.bottomSheet.onClickListener = onClickListener;
            return this;
        }

        public Builder setCustomView(View view) {
            this.bottomSheet.customView = view;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.bottomSheet.title = title;
            return this;
        }

        public BottomSheet create() {
            return this.bottomSheet;
        }

        public BottomSheet show() {
            this.bottomSheet.show();
            return this.bottomSheet;
        }

        public Builder setTag(int tag) {
            this.bottomSheet.tag = tag;
            return this;
        }

        public Builder setUseHardwareLayer(boolean value) {
            this.bottomSheet.useHardwareLayer = value;
            return this;
        }

        public Builder setDelegate(BottomSheetDelegate delegate) {
            this.bottomSheet.setDelegate(delegate);
            return this;
        }

        public Builder setApplyTopPadding(boolean value) {
            this.bottomSheet.applyTopPadding = value;
            return this;
        }

        public Builder setApplyBottomPadding(boolean value) {
            this.bottomSheet.applyBottomPadding = value;
            return this;
        }

        public BottomSheet setUseFullWidth(boolean value) {
            this.bottomSheet.fullWidth = value;
            return this.bottomSheet;
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void setAllowNestedScroll(boolean value) {
        this.allowNestedScroll = value;
        if (!this.allowNestedScroll) {
            this.containerView.setTranslationY(0.0f);
        }
    }

    public BottomSheet(Context context, boolean needFocus) {
        super(context, R.style.TransparentDialog);
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(-2147417856);
        }
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        Rect padding = new Rect();
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogBackground), Mode.MULTIPLY));
        this.shadowDrawable.getPadding(padding);
        backgroundPaddingLeft = padding.left;
        backgroundPaddingTop = padding.top;
        this.container = new ContainerView(getContext()) {
            public boolean drawChild(Canvas canvas, View child, long drawingTime) {
                try {
                    return BottomSheet.this.allowDrawContent && super.drawChild(canvas, child, drawingTime);
                } catch (Exception e) {
                    FileLog.e(e);
                    return true;
                }
            }
        };
        this.container.setBackgroundDrawable(this.backDrawable);
        this.focusable = needFocus;
        if (VERSION.SDK_INT >= 21) {
            this.container.setFitsSystemWindows(true);
            this.container.setOnApplyWindowInsetsListener(new C19942());
            this.container.setSystemUiVisibility(1280);
        }
        this.backDrawable.setAlpha(0);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setWindowAnimations(R.style.DialogNoAnimation);
        setContentView(this.container, new ViewGroup.LayoutParams(-1, -1));
        if (this.containerView == null) {
            int dp;
            this.containerView = new FrameLayout(getContext()) {
                public boolean hasOverlappingRendering() {
                    return false;
                }

                public void setTranslationY(float translationY) {
                    super.setTranslationY(translationY);
                    BottomSheet.this.onContainerTranslationYChanged(translationY);
                }
            };
            this.containerView.setBackgroundDrawable(this.shadowDrawable);
            ViewGroup viewGroup = this.containerView;
            int i = backgroundPaddingLeft;
            int dp2 = ((this.applyTopPadding ? AndroidUtilities.dp(8.0f) : 0) + backgroundPaddingTop) - 1;
            int i2 = backgroundPaddingLeft;
            if (this.applyBottomPadding) {
                dp = AndroidUtilities.dp(8.0f);
            } else {
                dp = 0;
            }
            viewGroup.setPadding(i, dp2, i2, dp);
        }
        if (VERSION.SDK_INT >= 21) {
            this.containerView.setFitsSystemWindows(true);
        }
        this.containerView.setVisibility(4);
        this.container.addView(this.containerView, 0, LayoutHelper.createFrame(-1, -2, 80));
        int topOffset = 0;
        if (this.title != null) {
            TextView titleView = new TextView(getContext());
            titleView.setLines(1);
            titleView.setSingleLine(true);
            titleView.setText(this.title);
            titleView.setTextColor(Theme.getColor(Theme.key_dialogTextGray2));
            titleView.setTextSize(1, 16.0f);
            titleView.setEllipsize(TruncateAt.MIDDLE);
            titleView.setPadding(AndroidUtilities.dp(16.0f), 0, AndroidUtilities.dp(16.0f), AndroidUtilities.dp(8.0f));
            titleView.setGravity(16);
            this.containerView.addView(titleView, LayoutHelper.createFrame(-1, 48.0f));
            titleView.setOnTouchListener(new C19964());
            topOffset = 0 + 48;
        }
        if (this.customView != null) {
            if (this.customView.getParent() != null) {
                ((ViewGroup) this.customView.getParent()).removeView(this.customView);
            }
            this.containerView.addView(this.customView, LayoutHelper.createFrame(-1, -2.0f, 51, 0.0f, (float) topOffset, 0.0f, 0.0f));
        } else if (this.items != null) {
            int a = 0;
            while (a < this.items.length) {
                if (this.items[a] != null) {
                    BottomSheetCell cell = new BottomSheetCell(getContext(), 0);
                    cell.setTextAndIcon(this.items[a], this.itemIcons != null ? this.itemIcons[a] : 0);
                    this.containerView.addView(cell, LayoutHelper.createFrame(-1, 48.0f, 51, 0.0f, (float) topOffset, 0.0f, 0.0f));
                    topOffset += 48;
                    cell.setTag(Integer.valueOf(a));
                    cell.setOnClickListener(new C19975());
                    this.itemViews.add(cell);
                }
                a++;
            }
        }
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = -1;
        params.gravity = 51;
        params.dimAmount = 0.0f;
        params.flags &= -3;
        if (!this.focusable) {
            params.flags |= 131072;
        }
        params.height = -1;
        window.setAttributes(params);
    }

    public void setShowWithoutAnimation(boolean value) {
        this.showWithoutAnimation = value;
    }

    public void setBackgroundColor(int color) {
        this.shadowDrawable.setColorFilter(color, Mode.MULTIPLY);
    }

    public void show() {
        super.show();
        if (this.focusable) {
            getWindow().setSoftInputMode(16);
        }
        this.dismissed = false;
        cancelSheetAnimation();
        this.containerView.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.displaySize.x + (backgroundPaddingLeft * 2), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.displaySize.y, Integer.MIN_VALUE));
        if (this.showWithoutAnimation) {
            this.backDrawable.setAlpha(51);
            this.containerView.setTranslationY(0.0f);
            return;
        }
        this.backDrawable.setAlpha(0);
        if (VERSION.SDK_INT >= 18) {
            this.layoutCount = 2;
            this.containerView.setTranslationY((float) this.containerView.getMeasuredHeight());
            Runnable c19986 = new C19986();
            this.startAnimationRunnable = c19986;
            AndroidUtilities.runOnUIThread(c19986, 150);
            return;
        }
        startOpenAnimation();
    }

    public void setAllowDrawContent(boolean value) {
        if (this.allowDrawContent != value) {
            this.allowDrawContent = value;
            this.container.setBackgroundDrawable(this.allowDrawContent ? this.backDrawable : null);
            this.container.invalidate();
        }
    }

    protected boolean canDismissWithSwipe() {
        return true;
    }

    protected boolean onContainerTouchEvent(MotionEvent event) {
        return false;
    }

    public void setCustomView(View view) {
        this.customView = view;
    }

    public void setTitle(CharSequence value) {
        this.title = value;
    }

    public void setApplyTopPadding(boolean value) {
        this.applyTopPadding = value;
    }

    public void setApplyBottomPadding(boolean value) {
        this.applyBottomPadding = value;
    }

    protected boolean onCustomMeasure(View view, int width, int height) {
        return false;
    }

    protected boolean onCustomLayout(View view, int left, int top, int right, int bottom) {
        return false;
    }

    protected boolean canDismissWithTouchOutside() {
        return true;
    }

    protected void onContainerTranslationYChanged(float translationY) {
    }

    private void cancelSheetAnimation() {
        if (this.currentSheetAnimation != null) {
            this.currentSheetAnimation.cancel();
            this.currentSheetAnimation = null;
        }
    }

    private void startOpenAnimation() {
        if (!this.dismissed) {
            this.containerView.setVisibility(0);
            if (!onCustomOpenAnimation()) {
                if (VERSION.SDK_INT >= 20 && this.useHardwareLayer) {
                    this.container.setLayerType(2, null);
                }
                this.containerView.setTranslationY((float) this.containerView.getMeasuredHeight());
                AnimatorSet animatorSet = new AnimatorSet();
                r1 = new Animator[2];
                r1[0] = ObjectAnimator.ofFloat(this.containerView, "translationY", new float[]{0.0f});
                r1[1] = ObjectAnimator.ofInt(this.backDrawable, "alpha", new int[]{51});
                animatorSet.playTogether(r1);
                animatorSet.setDuration(200);
                animatorSet.setStartDelay(20);
                animatorSet.setInterpolator(new DecelerateInterpolator());
                animatorSet.addListener(new C19997());
                animatorSet.start();
                this.currentSheetAnimation = animatorSet;
            }
        }
    }

    public void setDelegate(BottomSheetDelegateInterface bottomSheetDelegate) {
        this.delegate = bottomSheetDelegate;
    }

    public FrameLayout getContainer() {
        return this.container;
    }

    public ViewGroup getSheetContainer() {
        return this.containerView;
    }

    public int getTag() {
        return this.tag;
    }

    public void setItemText(int item, CharSequence text) {
        if (item >= 0 && item < this.itemViews.size()) {
            ((BottomSheetCell) this.itemViews.get(item)).textView.setText(text);
        }
    }

    public boolean isDismissed() {
        return this.dismissed;
    }

    public void dismissWithButtonClick(final int item) {
        if (!this.dismissed) {
            this.dismissed = true;
            cancelSheetAnimation();
            AnimatorSet animatorSet = new AnimatorSet();
            r1 = new Animator[2];
            r1[0] = ObjectAnimator.ofFloat(this.containerView, "translationY", new float[]{(float) (this.containerView.getMeasuredHeight() + AndroidUtilities.dp(10.0f))});
            r1[1] = ObjectAnimator.ofInt(this.backDrawable, "alpha", new int[]{0});
            animatorSet.playTogether(r1);
            animatorSet.setDuration(180);
            animatorSet.setInterpolator(new AccelerateInterpolator());
            animatorSet.addListener(new AnimatorListenerAdapter() {

                /* renamed from: org.telegram.ui.ActionBar.BottomSheet$8$1 */
                class C20001 implements Runnable {
                    C20001() {
                    }

                    public void run() {
                        try {
                            super.dismiss();
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                }

                public void onAnimationEnd(Animator animation) {
                    if (BottomSheet.this.currentSheetAnimation != null && BottomSheet.this.currentSheetAnimation.equals(animation)) {
                        BottomSheet.this.currentSheetAnimation = null;
                        if (BottomSheet.this.onClickListener != null) {
                            BottomSheet.this.onClickListener.onClick(BottomSheet.this, item);
                        }
                        AndroidUtilities.runOnUIThread(new C20001());
                    }
                }

                public void onAnimationCancel(Animator animation) {
                    if (BottomSheet.this.currentSheetAnimation != null && BottomSheet.this.currentSheetAnimation.equals(animation)) {
                        BottomSheet.this.currentSheetAnimation = null;
                    }
                }
            });
            animatorSet.start();
            this.currentSheetAnimation = animatorSet;
        }
    }

    public void dismiss() {
        if ((this.delegate == null || this.delegate.canDismiss()) && !this.dismissed) {
            this.dismissed = true;
            cancelSheetAnimation();
            if (!this.allowCustomAnimation || !onCustomCloseAnimation()) {
                AnimatorSet animatorSet = new AnimatorSet();
                r2 = new Animator[2];
                r2[0] = ObjectAnimator.ofFloat(this.containerView, "translationY", new float[]{(float) (this.containerView.getMeasuredHeight() + AndroidUtilities.dp(10.0f))});
                r2[1] = ObjectAnimator.ofInt(this.backDrawable, "alpha", new int[]{0});
                animatorSet.playTogether(r2);
                if (this.useFastDismiss) {
                    int height = this.containerView.getMeasuredHeight();
                    animatorSet.setDuration((long) Math.max(60, (int) ((180.0f * (((float) height) - this.containerView.getTranslationY())) / ((float) height))));
                    this.useFastDismiss = false;
                } else {
                    animatorSet.setDuration(180);
                }
                animatorSet.setInterpolator(new AccelerateInterpolator());
                animatorSet.addListener(new C20039());
                animatorSet.start();
                this.currentSheetAnimation = animatorSet;
            }
        }
    }

    public void dismissInternal() {
        try {
            super.dismiss();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    protected boolean onCustomCloseAnimation() {
        return false;
    }

    protected boolean onCustomOpenAnimation() {
        return false;
    }

    protected int getLeftInset() {
        if (this.lastInsets == null || VERSION.SDK_INT < 21) {
            return 0;
        }
        return this.lastInsets.getSystemWindowInsetLeft();
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public void onContainerDraw(Canvas canvas) {
    }
}
