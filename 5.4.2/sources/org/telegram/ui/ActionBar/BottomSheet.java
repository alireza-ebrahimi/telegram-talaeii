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
import android.support.v4.view.C0107x;
import android.support.v4.view.C0662y;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    protected ColorDrawable backDrawable = new ColorDrawable(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
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

    protected class ContainerView extends FrameLayout implements C0107x {
        private AnimatorSet currentAnimation = null;
        private boolean maybeStartTracking = false;
        private C0662y nestedScrollingParentHelper = new C0662y(this);
        private boolean startedTracking = false;
        private int startedTrackingPointerId = -1;
        private int startedTrackingX;
        private int startedTrackingY;
        private VelocityTracker velocityTracker = null;

        /* renamed from: org.telegram.ui.ActionBar.BottomSheet$ContainerView$1 */
        class C38421 extends AnimatorListenerAdapter {
            C38421() {
            }

            public void onAnimationEnd(Animator animator) {
                if (ContainerView.this.currentAnimation != null && ContainerView.this.currentAnimation.equals(animator)) {
                    ContainerView.this.currentAnimation = null;
                }
            }
        }

        public ContainerView(Context context) {
            super(context);
        }

        private void cancelCurrentAnimation() {
            if (this.currentAnimation != null) {
                this.currentAnimation.cancel();
                this.currentAnimation = null;
            }
        }

        private void checkDismiss(float f, float f2) {
            float translationY = BottomSheet.this.containerView.getTranslationY();
            boolean z = (translationY < AndroidUtilities.getPixelsInCM(0.8f, false) && (f2 < 3500.0f || Math.abs(f2) < Math.abs(f))) || (f2 < BitmapDescriptorFactory.HUE_RED && Math.abs(f2) >= 3500.0f);
            if (z) {
                this.currentAnimation = new AnimatorSet();
                AnimatorSet animatorSet = this.currentAnimation;
                Animator[] animatorArr = new Animator[1];
                animatorArr[0] = ObjectAnimator.ofFloat(BottomSheet.this.containerView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorSet.playTogether(animatorArr);
                this.currentAnimation.setDuration((long) ((int) (150.0f * (translationY / AndroidUtilities.getPixelsInCM(0.8f, false)))));
                this.currentAnimation.setInterpolator(new DecelerateInterpolator());
                this.currentAnimation.addListener(new C38421());
                this.currentAnimation.start();
                return;
            }
            z = BottomSheet.this.allowCustomAnimation;
            BottomSheet.this.allowCustomAnimation = false;
            BottomSheet.this.useFastDismiss = true;
            BottomSheet.this.dismiss();
            BottomSheet.this.allowCustomAnimation = z;
        }

        public int getNestedScrollAxes() {
            return this.nestedScrollingParentHelper.a();
        }

        public boolean hasOverlappingRendering() {
            return false;
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            BottomSheet.this.onContainerDraw(canvas);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return BottomSheet.this.canDismissWithSwipe() ? onTouchEvent(motionEvent) : super.onInterceptTouchEvent(motionEvent);
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int measuredWidth;
            BottomSheet.this.layoutCount = BottomSheet.this.layoutCount - 1;
            if (BottomSheet.this.containerView != null) {
                if (BottomSheet.this.lastInsets != null && VERSION.SDK_INT >= 21) {
                    i += BottomSheet.this.lastInsets.getSystemWindowInsetLeft();
                    i3 -= BottomSheet.this.lastInsets.getSystemWindowInsetRight();
                }
                int measuredHeight = (i4 - i2) - BottomSheet.this.containerView.getMeasuredHeight();
                measuredWidth = ((i3 - i) - BottomSheet.this.containerView.getMeasuredWidth()) / 2;
                if (BottomSheet.this.lastInsets != null && VERSION.SDK_INT >= 21) {
                    measuredWidth += BottomSheet.this.lastInsets.getSystemWindowInsetLeft();
                }
                BottomSheet.this.containerView.layout(measuredWidth, measuredHeight, BottomSheet.this.containerView.getMeasuredWidth() + measuredWidth, BottomSheet.this.containerView.getMeasuredHeight() + measuredHeight);
            }
            int i5 = i3;
            int i6 = i;
            int childCount = getChildCount();
            for (int i7 = 0; i7 < childCount; i7++) {
                View childAt = getChildAt(i7);
                if (!(childAt.getVisibility() == 8 || childAt == BottomSheet.this.containerView || BottomSheet.this.onCustomLayout(childAt, i6, i2, i5, i4))) {
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    int measuredWidth2 = childAt.getMeasuredWidth();
                    int measuredHeight2 = childAt.getMeasuredHeight();
                    int i8 = layoutParams.gravity;
                    if (i8 == -1) {
                        i8 = 51;
                    }
                    int i9 = i8 & 112;
                    switch ((i8 & 7) & 7) {
                        case 1:
                            i8 = ((((i5 - i6) - measuredWidth2) / 2) + layoutParams.leftMargin) - layoutParams.rightMargin;
                            break;
                        case 5:
                            i8 = (i5 - measuredWidth2) - layoutParams.rightMargin;
                            break;
                        default:
                            i8 = layoutParams.leftMargin;
                            break;
                    }
                    switch (i9) {
                        case 16:
                            measuredWidth = ((((i4 - i2) - measuredHeight2) / 2) + layoutParams.topMargin) - layoutParams.bottomMargin;
                            break;
                        case 48:
                            measuredWidth = layoutParams.topMargin;
                            break;
                        case 80:
                            measuredWidth = ((i4 - i2) - measuredHeight2) - layoutParams.bottomMargin;
                            break;
                        default:
                            measuredWidth = layoutParams.topMargin;
                            break;
                    }
                    if (BottomSheet.this.lastInsets != null && VERSION.SDK_INT >= 21) {
                        i8 += BottomSheet.this.lastInsets.getSystemWindowInsetLeft();
                    }
                    childAt.layout(i8, measuredWidth, measuredWidth2 + i8, measuredHeight2 + measuredWidth);
                }
            }
            if (BottomSheet.this.layoutCount == 0 && BottomSheet.this.startAnimationRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(BottomSheet.this.startAnimationRunnable);
                BottomSheet.this.startAnimationRunnable.run();
                BottomSheet.this.startAnimationRunnable = null;
            }
        }

        protected void onMeasure(int i, int i2) {
            int size = MeasureSpec.getSize(i);
            int size2 = MeasureSpec.getSize(i2);
            int systemWindowInsetBottom = (BottomSheet.this.lastInsets == null || VERSION.SDK_INT < 21) ? size2 : size2 - BottomSheet.this.lastInsets.getSystemWindowInsetBottom();
            setMeasuredDimension(size, systemWindowInsetBottom);
            int systemWindowInsetRight = (BottomSheet.this.lastInsets == null || VERSION.SDK_INT < 21) ? size : size - (BottomSheet.this.lastInsets.getSystemWindowInsetRight() + BottomSheet.this.lastInsets.getSystemWindowInsetLeft());
            size2 = systemWindowInsetRight < systemWindowInsetBottom ? 1 : 0;
            if (BottomSheet.this.containerView != null) {
                if (BottomSheet.this.fullWidth) {
                    BottomSheet.this.containerView.measure(MeasureSpec.makeMeasureSpec((BottomSheet.backgroundPaddingLeft * 2) + systemWindowInsetRight, 1073741824), MeasureSpec.makeMeasureSpec(systemWindowInsetBottom, Integer.MIN_VALUE));
                } else {
                    if (AndroidUtilities.isTablet()) {
                        size2 = MeasureSpec.makeMeasureSpec(((int) (((float) Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y)) * 0.8f)) + (BottomSheet.backgroundPaddingLeft * 2), 1073741824);
                    } else {
                        size2 = MeasureSpec.makeMeasureSpec(size2 != 0 ? (BottomSheet.backgroundPaddingLeft * 2) + systemWindowInsetRight : ((int) Math.max(((float) systemWindowInsetRight) * 0.8f, (float) Math.min(AndroidUtilities.dp(480.0f), systemWindowInsetRight))) + (BottomSheet.backgroundPaddingLeft * 2), 1073741824);
                    }
                    BottomSheet.this.containerView.measure(size2, MeasureSpec.makeMeasureSpec(systemWindowInsetBottom, Integer.MIN_VALUE));
                }
            }
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (!(childAt.getVisibility() == 8 || childAt == BottomSheet.this.containerView || BottomSheet.this.onCustomMeasure(childAt, systemWindowInsetRight, systemWindowInsetBottom))) {
                    measureChildWithMargins(childAt, MeasureSpec.makeMeasureSpec(systemWindowInsetRight, 1073741824), 0, MeasureSpec.makeMeasureSpec(systemWindowInsetBottom, 1073741824), 0);
                }
            }
        }

        public boolean onNestedFling(View view, float f, float f2, boolean z) {
            return false;
        }

        public boolean onNestedPreFling(View view, float f, float f2) {
            return false;
        }

        public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
            float f = BitmapDescriptorFactory.HUE_RED;
            if (!BottomSheet.this.dismissed && BottomSheet.this.allowNestedScroll) {
                cancelCurrentAnimation();
                float translationY = BottomSheet.this.containerView.getTranslationY();
                if (translationY > BitmapDescriptorFactory.HUE_RED && i2 > 0) {
                    translationY -= (float) i2;
                    iArr[1] = i2;
                    if (translationY < BitmapDescriptorFactory.HUE_RED) {
                        iArr[1] = (int) (((float) iArr[1]) + BitmapDescriptorFactory.HUE_RED);
                    } else {
                        f = translationY;
                    }
                    BottomSheet.this.containerView.setTranslationY(f);
                }
            }
        }

        public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
            float f = BitmapDescriptorFactory.HUE_RED;
            if (!BottomSheet.this.dismissed && BottomSheet.this.allowNestedScroll) {
                cancelCurrentAnimation();
                if (i4 != 0) {
                    float translationY = BottomSheet.this.containerView.getTranslationY() - ((float) i4);
                    if (translationY >= BitmapDescriptorFactory.HUE_RED) {
                        f = translationY;
                    }
                    BottomSheet.this.containerView.setTranslationY(f);
                }
            }
        }

        public void onNestedScrollAccepted(View view, View view2, int i) {
            this.nestedScrollingParentHelper.a(view, view2, i);
            if (!BottomSheet.this.dismissed && BottomSheet.this.allowNestedScroll) {
                cancelCurrentAnimation();
            }
        }

        public boolean onStartNestedScroll(View view, View view2, int i) {
            return !BottomSheet.this.dismissed && BottomSheet.this.allowNestedScroll && i == 2 && !BottomSheet.this.canDismissWithSwipe();
        }

        public void onStopNestedScroll(View view) {
            this.nestedScrollingParentHelper.a(view);
            if (!BottomSheet.this.dismissed && BottomSheet.this.allowNestedScroll) {
                BottomSheet.this.containerView.getTranslationY();
                checkDismiss(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
            }
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            float f = BitmapDescriptorFactory.HUE_RED;
            if (BottomSheet.this.dismissed) {
                return false;
            }
            if (BottomSheet.this.onContainerTouchEvent(motionEvent)) {
                return true;
            }
            if (BottomSheet.this.canDismissWithTouchOutside() && motionEvent != null && ((motionEvent.getAction() == 0 || motionEvent.getAction() == 2) && ((!this.startedTracking && !this.maybeStartTracking) || motionEvent.getPointerCount() == 1))) {
                this.startedTrackingX = (int) motionEvent.getX();
                this.startedTrackingY = (int) motionEvent.getY();
                if (this.startedTrackingY < BottomSheet.this.containerView.getTop() || this.startedTrackingX < BottomSheet.this.containerView.getLeft() || this.startedTrackingX > BottomSheet.this.containerView.getRight()) {
                    BottomSheet.this.dismiss();
                    return true;
                }
                this.startedTrackingPointerId = motionEvent.getPointerId(0);
                this.maybeStartTracking = true;
                cancelCurrentAnimation();
                if (this.velocityTracker != null) {
                    this.velocityTracker.clear();
                }
            } else if (motionEvent != null && motionEvent.getAction() == 2 && motionEvent.getPointerId(0) == this.startedTrackingPointerId) {
                if (this.velocityTracker == null) {
                    this.velocityTracker = VelocityTracker.obtain();
                }
                r1 = (float) Math.abs((int) (motionEvent.getX() - ((float) this.startedTrackingX)));
                float y = (float) (((int) motionEvent.getY()) - this.startedTrackingY);
                this.velocityTracker.addMovement(motionEvent);
                if (this.maybeStartTracking && !this.startedTracking && y > BitmapDescriptorFactory.HUE_RED && y / 3.0f > Math.abs(r1) && Math.abs(y) >= ((float) BottomSheet.this.touchSlop)) {
                    this.startedTrackingY = (int) motionEvent.getY();
                    this.maybeStartTracking = false;
                    this.startedTracking = true;
                    requestDisallowInterceptTouchEvent(true);
                } else if (this.startedTracking) {
                    r1 = BottomSheet.this.containerView.getTranslationY() + y;
                    if (r1 >= BitmapDescriptorFactory.HUE_RED) {
                        f = r1;
                    }
                    BottomSheet.this.containerView.setTranslationY(f);
                    this.startedTrackingY = (int) motionEvent.getY();
                }
            } else if (motionEvent == null || (motionEvent != null && motionEvent.getPointerId(0) == this.startedTrackingPointerId && (motionEvent.getAction() == 3 || motionEvent.getAction() == 1 || motionEvent.getAction() == 6))) {
                if (this.velocityTracker == null) {
                    this.velocityTracker = VelocityTracker.obtain();
                }
                this.velocityTracker.computeCurrentVelocity(1000);
                r1 = BottomSheet.this.containerView.getTranslationY();
                if (this.startedTracking || r1 != BitmapDescriptorFactory.HUE_RED) {
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
            boolean z = this.startedTracking || !BottomSheet.this.canDismissWithSwipe();
            return z;
        }

        public void requestDisallowInterceptTouchEvent(boolean z) {
            if (this.maybeStartTracking && !this.startedTracking) {
                onTouchEvent(null);
            }
            super.requestDisallowInterceptTouchEvent(z);
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.BottomSheet$2 */
    class C38322 implements OnApplyWindowInsetsListener {
        C38322() {
        }

        @SuppressLint({"NewApi"})
        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            BottomSheet.this.lastInsets = windowInsets;
            view.requestLayout();
            return windowInsets.consumeSystemWindowInsets();
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.BottomSheet$4 */
    class C38344 implements OnTouchListener {
        C38344() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.BottomSheet$5 */
    class C38355 implements View.OnClickListener {
        C38355() {
        }

        public void onClick(View view) {
            BottomSheet.this.dismissWithButtonClick(((Integer) view.getTag()).intValue());
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.BottomSheet$6 */
    class C38366 implements Runnable {
        C38366() {
        }

        public void run() {
            if (BottomSheet.this.startAnimationRunnable == this && !BottomSheet.this.dismissed) {
                BottomSheet.this.startAnimationRunnable = null;
                BottomSheet.this.startOpenAnimation();
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.BottomSheet$7 */
    class C38377 extends AnimatorListenerAdapter {
        C38377() {
        }

        public void onAnimationCancel(Animator animator) {
            if (BottomSheet.this.currentSheetAnimation != null && BottomSheet.this.currentSheetAnimation.equals(animator)) {
                BottomSheet.this.currentSheetAnimation = null;
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (BottomSheet.this.currentSheetAnimation != null && BottomSheet.this.currentSheetAnimation.equals(animator)) {
                BottomSheet.this.currentSheetAnimation = null;
                if (BottomSheet.this.delegate != null) {
                    BottomSheet.this.delegate.onOpenAnimationEnd();
                }
                if (BottomSheet.this.useHardwareLayer) {
                    BottomSheet.this.container.setLayerType(0, null);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.BottomSheet$9 */
    class C38419 extends AnimatorListenerAdapter {

        /* renamed from: org.telegram.ui.ActionBar.BottomSheet$9$1 */
        class C38401 implements Runnable {
            C38401() {
            }

            public void run() {
                try {
                    BottomSheet.this.dismissInternal();
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        }

        C38419() {
        }

        public void onAnimationCancel(Animator animator) {
            if (BottomSheet.this.currentSheetAnimation != null && BottomSheet.this.currentSheetAnimation.equals(animator)) {
                BottomSheet.this.currentSheetAnimation = null;
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (BottomSheet.this.currentSheetAnimation != null && BottomSheet.this.currentSheetAnimation.equals(animator)) {
                BottomSheet.this.currentSheetAnimation = null;
                AndroidUtilities.runOnUIThread(new C38401());
            }
        }
    }

    public static class BottomSheetCell extends FrameLayout {
        private ImageView imageView;
        private TextView textView;

        public BottomSheetCell(Context context, int i) {
            int i2 = 3;
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
            if (i == 0) {
                this.textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                this.textView.setTextSize(1, 16.0f);
                View view = this.textView;
                if (LocaleController.isRTL) {
                    i2 = 5;
                }
                addView(view, LayoutHelper.createFrame(-2, -2, i2 | 16));
            } else if (i == 1) {
                this.textView.setGravity(17);
                this.textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                this.textView.setTextSize(1, 14.0f);
                this.textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                addView(this.textView, LayoutHelper.createFrame(-1, -1.0f));
            }
        }

        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
        }

        public void setGravity(int i) {
            this.textView.setGravity(i);
        }

        public void setTextAndIcon(CharSequence charSequence, int i) {
            this.textView.setText(charSequence);
            if (i != 0) {
                this.imageView.setImageResource(i);
                this.imageView.setVisibility(0);
                this.textView.setPadding(LocaleController.isRTL ? 0 : AndroidUtilities.dp(56.0f), 0, LocaleController.isRTL ? AndroidUtilities.dp(56.0f) : 0, 0);
                return;
            }
            this.imageView.setVisibility(4);
            this.textView.setPadding(0, 0, 0, 0);
        }

        public void setTextColor(int i) {
            this.textView.setTextColor(i);
        }
    }

    public interface BottomSheetDelegateInterface {
        boolean canDismiss();

        void onOpenAnimationEnd();

        void onOpenAnimationStart();
    }

    public static class BottomSheetDelegate implements BottomSheetDelegateInterface {
        public boolean canDismiss() {
            return true;
        }

        public void onOpenAnimationEnd() {
        }

        public void onOpenAnimationStart() {
        }
    }

    public static class Builder {
        private BottomSheet bottomSheet;

        public Builder(Context context) {
            this.bottomSheet = new BottomSheet(context, false);
        }

        public Builder(Context context, boolean z) {
            this.bottomSheet = new BottomSheet(context, z);
        }

        public BottomSheet create() {
            return this.bottomSheet;
        }

        public Builder setApplyBottomPadding(boolean z) {
            this.bottomSheet.applyBottomPadding = z;
            return this;
        }

        public Builder setApplyTopPadding(boolean z) {
            this.bottomSheet.applyTopPadding = z;
            return this;
        }

        public Builder setCustomView(View view) {
            this.bottomSheet.customView = view;
            return this;
        }

        public Builder setDelegate(BottomSheetDelegate bottomSheetDelegate) {
            this.bottomSheet.setDelegate(bottomSheetDelegate);
            return this;
        }

        public Builder setItems(CharSequence[] charSequenceArr, OnClickListener onClickListener) {
            this.bottomSheet.items = charSequenceArr;
            this.bottomSheet.onClickListener = onClickListener;
            return this;
        }

        public Builder setItems(CharSequence[] charSequenceArr, int[] iArr, OnClickListener onClickListener) {
            this.bottomSheet.items = charSequenceArr;
            this.bottomSheet.itemIcons = iArr;
            this.bottomSheet.onClickListener = onClickListener;
            return this;
        }

        public Builder setTag(int i) {
            this.bottomSheet.tag = i;
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.bottomSheet.title = charSequence;
            return this;
        }

        public BottomSheet setUseFullWidth(boolean z) {
            this.bottomSheet.fullWidth = z;
            return this.bottomSheet;
        }

        public Builder setUseHardwareLayer(boolean z) {
            this.bottomSheet.useHardwareLayer = z;
            return this;
        }

        public BottomSheet show() {
            this.bottomSheet.show();
            return this.bottomSheet;
        }
    }

    public BottomSheet(Context context, boolean z) {
        super(context, R.style.TransparentDialog);
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(-2147417856);
        }
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        Rect rect = new Rect();
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogBackground), Mode.MULTIPLY));
        this.shadowDrawable.getPadding(rect);
        backgroundPaddingLeft = rect.left;
        backgroundPaddingTop = rect.top;
        this.container = new ContainerView(getContext()) {
            public boolean drawChild(Canvas canvas, View view, long j) {
                try {
                    return BottomSheet.this.allowDrawContent && super.drawChild(canvas, view, j);
                } catch (Throwable e) {
                    FileLog.e(e);
                    return true;
                }
            }
        };
        this.container.setBackgroundDrawable(this.backDrawable);
        this.focusable = z;
        if (VERSION.SDK_INT >= 21) {
            this.container.setFitsSystemWindows(true);
            this.container.setOnApplyWindowInsetsListener(new C38322());
            this.container.setSystemUiVisibility(1280);
        }
        this.backDrawable.setAlpha(0);
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
                r1[0] = ObjectAnimator.ofFloat(this.containerView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
                r1[1] = ObjectAnimator.ofInt(this.backDrawable, "alpha", new int[]{51});
                animatorSet.playTogether(r1);
                animatorSet.setDuration(200);
                animatorSet.setStartDelay(20);
                animatorSet.setInterpolator(new DecelerateInterpolator());
                animatorSet.addListener(new C38377());
                animatorSet.start();
                this.currentSheetAnimation = animatorSet;
            }
        }
    }

    protected boolean canDismissWithSwipe() {
        return true;
    }

    protected boolean canDismissWithTouchOutside() {
        return true;
    }

    public void dismiss() {
        if ((this.delegate == null || this.delegate.canDismiss()) && !this.dismissed) {
            this.dismissed = true;
            cancelSheetAnimation();
            if (!this.allowCustomAnimation || !onCustomCloseAnimation()) {
                AnimatorSet animatorSet = new AnimatorSet();
                r1 = new Animator[2];
                r1[0] = ObjectAnimator.ofFloat(this.containerView, "translationY", new float[]{(float) (this.containerView.getMeasuredHeight() + AndroidUtilities.dp(10.0f))});
                r1[1] = ObjectAnimator.ofInt(this.backDrawable, "alpha", new int[]{0});
                animatorSet.playTogether(r1);
                if (this.useFastDismiss) {
                    int measuredHeight = this.containerView.getMeasuredHeight();
                    animatorSet.setDuration((long) Math.max(60, (int) ((180.0f * (((float) measuredHeight) - this.containerView.getTranslationY())) / ((float) measuredHeight))));
                    this.useFastDismiss = false;
                } else {
                    animatorSet.setDuration(180);
                }
                animatorSet.setInterpolator(new AccelerateInterpolator());
                animatorSet.addListener(new C38419());
                animatorSet.start();
                this.currentSheetAnimation = animatorSet;
            }
        }
    }

    public void dismissInternal() {
        try {
            super.dismiss();
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    public void dismissWithButtonClick(final int i) {
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
                class C38381 implements Runnable {
                    C38381() {
                    }

                    public void run() {
                        try {
                            super.dismiss();
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                }

                public void onAnimationCancel(Animator animator) {
                    if (BottomSheet.this.currentSheetAnimation != null && BottomSheet.this.currentSheetAnimation.equals(animator)) {
                        BottomSheet.this.currentSheetAnimation = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (BottomSheet.this.currentSheetAnimation != null && BottomSheet.this.currentSheetAnimation.equals(animator)) {
                        BottomSheet.this.currentSheetAnimation = null;
                        if (BottomSheet.this.onClickListener != null) {
                            BottomSheet.this.onClickListener.onClick(BottomSheet.this, i);
                        }
                        AndroidUtilities.runOnUIThread(new C38381());
                    }
                }
            });
            animatorSet.start();
            this.currentSheetAnimation = animatorSet;
        }
    }

    public FrameLayout getContainer() {
        return this.container;
    }

    protected int getLeftInset() {
        return (this.lastInsets == null || VERSION.SDK_INT < 21) ? 0 : this.lastInsets.getSystemWindowInsetLeft();
    }

    public ViewGroup getSheetContainer() {
        return this.containerView;
    }

    public int getTag() {
        return this.tag;
    }

    public boolean isDismissed() {
        return this.dismissed;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onContainerDraw(Canvas canvas) {
    }

    protected boolean onContainerTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    protected void onContainerTranslationYChanged(float f) {
    }

    protected void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        Window window = getWindow();
        window.setWindowAnimations(R.style.DialogNoAnimation);
        setContentView(this.container, new ViewGroup.LayoutParams(-1, -1));
        if (this.containerView == null) {
            this.containerView = new FrameLayout(getContext()) {
                public boolean hasOverlappingRendering() {
                    return false;
                }

                public void setTranslationY(float f) {
                    super.setTranslationY(f);
                    BottomSheet.this.onContainerTranslationYChanged(f);
                }
            };
            this.containerView.setBackgroundDrawable(this.shadowDrawable);
            this.containerView.setPadding(backgroundPaddingLeft, ((this.applyTopPadding ? AndroidUtilities.dp(8.0f) : 0) + backgroundPaddingTop) - 1, backgroundPaddingLeft, this.applyBottomPadding ? AndroidUtilities.dp(8.0f) : 0);
        }
        if (VERSION.SDK_INT >= 21) {
            this.containerView.setFitsSystemWindows(true);
        }
        this.containerView.setVisibility(4);
        this.container.addView(this.containerView, 0, LayoutHelper.createFrame(-1, -2, 80));
        if (this.title != null) {
            View textView = new TextView(getContext());
            textView.setLines(1);
            textView.setSingleLine(true);
            textView.setText(this.title);
            textView.setTextColor(Theme.getColor(Theme.key_dialogTextGray2));
            textView.setTextSize(1, 16.0f);
            textView.setEllipsize(TruncateAt.MIDDLE);
            textView.setPadding(AndroidUtilities.dp(16.0f), 0, AndroidUtilities.dp(16.0f), AndroidUtilities.dp(8.0f));
            textView.setGravity(16);
            this.containerView.addView(textView, LayoutHelper.createFrame(-1, 48.0f));
            textView.setOnTouchListener(new C38344());
            i = 48;
        } else {
            i = 0;
        }
        if (this.customView != null) {
            if (this.customView.getParent() != null) {
                ((ViewGroup) this.customView.getParent()).removeView(this.customView);
            }
            this.containerView.addView(this.customView, LayoutHelper.createFrame(-1, -2.0f, 51, BitmapDescriptorFactory.HUE_RED, (float) i, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        } else if (this.items != null) {
            int i2 = 0;
            int i3 = i;
            while (i2 < this.items.length) {
                if (this.items[i2] == null) {
                    i = i3;
                } else {
                    View bottomSheetCell = new BottomSheetCell(getContext(), 0);
                    bottomSheetCell.setTextAndIcon(this.items[i2], this.itemIcons != null ? this.itemIcons[i2] : 0);
                    this.containerView.addView(bottomSheetCell, LayoutHelper.createFrame(-1, 48.0f, 51, BitmapDescriptorFactory.HUE_RED, (float) i3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                    i = i3 + 48;
                    bottomSheetCell.setTag(Integer.valueOf(i2));
                    bottomSheetCell.setOnClickListener(new C38355());
                    this.itemViews.add(bottomSheetCell);
                }
                i2++;
                i3 = i;
            }
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = -1;
        attributes.gravity = 51;
        attributes.dimAmount = BitmapDescriptorFactory.HUE_RED;
        attributes.flags &= -3;
        if (!this.focusable) {
            attributes.flags |= 131072;
        }
        attributes.height = -1;
        window.setAttributes(attributes);
    }

    protected boolean onCustomCloseAnimation() {
        return false;
    }

    protected boolean onCustomLayout(View view, int i, int i2, int i3, int i4) {
        return false;
    }

    protected boolean onCustomMeasure(View view, int i, int i2) {
        return false;
    }

    protected boolean onCustomOpenAnimation() {
        return false;
    }

    public void setAllowDrawContent(boolean z) {
        if (this.allowDrawContent != z) {
            this.allowDrawContent = z;
            this.container.setBackgroundDrawable(this.allowDrawContent ? this.backDrawable : null);
            this.container.invalidate();
        }
    }

    public void setAllowNestedScroll(boolean z) {
        this.allowNestedScroll = z;
        if (!this.allowNestedScroll) {
            this.containerView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        }
    }

    public void setApplyBottomPadding(boolean z) {
        this.applyBottomPadding = z;
    }

    public void setApplyTopPadding(boolean z) {
        this.applyTopPadding = z;
    }

    public void setBackgroundColor(int i) {
        this.shadowDrawable.setColorFilter(i, Mode.MULTIPLY);
    }

    public void setCustomView(View view) {
        this.customView = view;
    }

    public void setDelegate(BottomSheetDelegateInterface bottomSheetDelegateInterface) {
        this.delegate = bottomSheetDelegateInterface;
    }

    public void setItemText(int i, CharSequence charSequence) {
        if (i >= 0 && i < this.itemViews.size()) {
            ((BottomSheetCell) this.itemViews.get(i)).textView.setText(charSequence);
        }
    }

    public void setShowWithoutAnimation(boolean z) {
        this.showWithoutAnimation = z;
    }

    public void setTitle(CharSequence charSequence) {
        this.title = charSequence;
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
            this.containerView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
            return;
        }
        this.backDrawable.setAlpha(0);
        if (VERSION.SDK_INT >= 18) {
            this.layoutCount = 2;
            this.containerView.setTranslationY((float) this.containerView.getMeasuredHeight());
            Runnable c38366 = new C38366();
            this.startAnimationRunnable = c38366;
            AndroidUtilities.runOnUIThread(c38366, 150);
            return;
        }
        startOpenAnimation();
    }
}
