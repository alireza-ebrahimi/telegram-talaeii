package org.telegram.ui.ActionBar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.ui.Components.LayoutHelper;

public class ActionBarLayout extends FrameLayout {
    private static Drawable headerShadowDrawable;
    private static Drawable layerShadowDrawable;
    private static Paint scrimPaint;
    private AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
    protected boolean animationInProgress;
    private float animationProgress = BitmapDescriptorFactory.HUE_RED;
    private Runnable animationRunnable;
    private View backgroundView;
    private boolean beginTrackingSent;
    private LinearLayoutContainer containerView;
    private LinearLayoutContainer containerViewBack;
    private ActionBar currentActionBar;
    private AnimatorSet currentAnimation;
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(1.5f);
    private Runnable delayedOpenAnimationRunnable;
    private ActionBarLayoutDelegate delegate = null;
    private DrawerLayoutContainer drawerLayoutContainer;
    public ArrayList<BaseFragment> fragmentsStack = null;
    private boolean inActionMode;
    private boolean inSelectMode;
    public float innerTranslationX;
    private long lastFrameTime;
    private boolean maybeStartTracking;
    private Runnable onCloseAnimationEndRunnable;
    private Runnable onOpenAnimationEndRunnable;
    private Runnable overlayAction;
    protected Activity parentActivity = null;
    private boolean rebuildAfterAnimation;
    private boolean rebuildLastAfterAnimation;
    private boolean removeActionBarExtraHeight;
    private boolean showLastAfterAnimation;
    private boolean showShadow = true;
    protected boolean startedTracking;
    private int startedTrackingPointerId;
    private int startedTrackingX;
    private int startedTrackingY;
    private String subtitleOverlayText;
    private String titleOverlayText;
    private boolean transitionAnimationInProgress;
    private long transitionAnimationStartTime;
    private boolean useAlphaAnimations;
    private VelocityTracker velocityTracker;
    private Runnable waitingForKeyboardCloseRunnable;

    /* renamed from: org.telegram.ui.ActionBar.ActionBarLayout$4 */
    class C38004 extends AnimatorListenerAdapter {
        C38004() {
        }

        public void onAnimationEnd(Animator animator) {
            ActionBarLayout.this.onAnimationEndCheck(false);
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarLayout$6 */
    class C38026 implements Runnable {
        C38026() {
        }

        public void run() {
            ActionBarLayout.this.onAnimationEndCheck(false);
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarLayout$7 */
    class C38037 implements Runnable {
        C38037() {
        }

        public void run() {
            if (ActionBarLayout.this.waitingForKeyboardCloseRunnable == this) {
                ActionBarLayout.this.waitingForKeyboardCloseRunnable = null;
                ActionBarLayout.this.startLayoutAnimation(true, true);
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarLayout$8 */
    class C38048 implements Runnable {
        C38048() {
        }

        public void run() {
            if (ActionBarLayout.this.delayedOpenAnimationRunnable == this) {
                ActionBarLayout.this.delayedOpenAnimationRunnable = null;
                ActionBarLayout.this.startLayoutAnimation(true, true);
            }
        }
    }

    public interface ActionBarLayoutDelegate {
        boolean needAddFragmentToStack(BaseFragment baseFragment, ActionBarLayout actionBarLayout);

        boolean needCloseLastFragment(ActionBarLayout actionBarLayout);

        boolean needPresentFragment(BaseFragment baseFragment, boolean z, boolean z2, ActionBarLayout actionBarLayout);

        boolean onPreIme();

        void onRebuildAllFragments(ActionBarLayout actionBarLayout);
    }

    public class LinearLayoutContainer extends LinearLayout {
        private boolean isKeyboardVisible;
        private Rect rect = new Rect();

        public LinearLayoutContainer(Context context) {
            super(context);
            setOrientation(1);
        }

        protected boolean drawChild(Canvas canvas, View view, long j) {
            if (view instanceof ActionBar) {
                return super.drawChild(canvas, view, j);
            }
            int i;
            boolean drawChild;
            int childCount = getChildCount();
            for (i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (childAt != view && (childAt instanceof ActionBar) && childAt.getVisibility() == 0) {
                    if (((ActionBar) childAt).getCastShadows()) {
                        i = childAt.getMeasuredHeight();
                        drawChild = super.drawChild(canvas, view, j);
                        if (!(i == 0 || ActionBarLayout.headerShadowDrawable == null || !ActionBarLayout.this.isShowShadow())) {
                            ActionBarLayout.headerShadowDrawable.setBounds(0, i, getMeasuredWidth(), ActionBarLayout.headerShadowDrawable.getIntrinsicHeight() + i);
                            ActionBarLayout.headerShadowDrawable.draw(canvas);
                        }
                        return drawChild;
                    }
                    i = 0;
                    drawChild = super.drawChild(canvas, view, j);
                    ActionBarLayout.headerShadowDrawable.setBounds(0, i, getMeasuredWidth(), ActionBarLayout.headerShadowDrawable.getIntrinsicHeight() + i);
                    ActionBarLayout.headerShadowDrawable.draw(canvas);
                    return drawChild;
                }
            }
            i = 0;
            drawChild = super.drawChild(canvas, view, j);
            ActionBarLayout.headerShadowDrawable.setBounds(0, i, getMeasuredWidth(), ActionBarLayout.headerShadowDrawable.getIntrinsicHeight() + i);
            ActionBarLayout.headerShadowDrawable.draw(canvas);
            return drawChild;
        }

        public boolean hasOverlappingRendering() {
            return false;
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            boolean z2 = false;
            super.onLayout(z, i, i2, i3, i4);
            View rootView = getRootView();
            getWindowVisibleDisplayFrame(this.rect);
            if (((rootView.getHeight() - (this.rect.top != 0 ? AndroidUtilities.statusBarHeight : 0)) - AndroidUtilities.getViewInset(rootView)) - (this.rect.bottom - this.rect.top) > 0) {
                z2 = true;
            }
            this.isKeyboardVisible = z2;
            if (ActionBarLayout.this.waitingForKeyboardCloseRunnable != null && !ActionBarLayout.this.containerView.isKeyboardVisible && !ActionBarLayout.this.containerViewBack.isKeyboardVisible) {
                AndroidUtilities.cancelRunOnUIThread(ActionBarLayout.this.waitingForKeyboardCloseRunnable);
                ActionBarLayout.this.waitingForKeyboardCloseRunnable.run();
                ActionBarLayout.this.waitingForKeyboardCloseRunnable = null;
            }
        }
    }

    public ActionBarLayout(Context context) {
        super(context);
        this.parentActivity = (Activity) context;
        if (layerShadowDrawable == null) {
            layerShadowDrawable = getResources().getDrawable(R.drawable.layer_shadow);
            headerShadowDrawable = getResources().getDrawable(R.drawable.header_shadow).mutate();
            scrimPaint = new Paint();
        }
    }

    private void checkNeedRebuild() {
        if (this.rebuildAfterAnimation) {
            rebuildAllFragmentViews(this.rebuildLastAfterAnimation, this.showLastAfterAnimation);
            this.rebuildAfterAnimation = false;
        }
    }

    private void closeLastFragmentInternalRemoveOld(BaseFragment baseFragment) {
        baseFragment.onPause();
        baseFragment.onFragmentDestroy();
        baseFragment.setParentLayout(null);
        this.fragmentsStack.remove(baseFragment);
        this.containerViewBack.setVisibility(8);
        bringChildToFront(this.containerView);
    }

    private void onAnimationEndCheck(boolean z) {
        onCloseAnimationEnd();
        onOpenAnimationEnd();
        if (this.waitingForKeyboardCloseRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.waitingForKeyboardCloseRunnable);
            this.waitingForKeyboardCloseRunnable = null;
        }
        if (this.currentAnimation != null) {
            if (z) {
                this.currentAnimation.cancel();
            }
            this.currentAnimation = null;
        }
        if (this.animationRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.animationRunnable);
            this.animationRunnable = null;
        }
        setAlpha(1.0f);
        this.containerView.setAlpha(1.0f);
        this.containerView.setScaleX(1.0f);
        this.containerView.setScaleY(1.0f);
        this.containerViewBack.setAlpha(1.0f);
        this.containerViewBack.setScaleX(1.0f);
        this.containerViewBack.setScaleY(1.0f);
    }

    private void onCloseAnimationEnd() {
        if (this.transitionAnimationInProgress && this.onCloseAnimationEndRunnable != null) {
            this.transitionAnimationInProgress = false;
            this.transitionAnimationStartTime = 0;
            this.onCloseAnimationEndRunnable.run();
            this.onCloseAnimationEndRunnable = null;
            checkNeedRebuild();
        }
    }

    private void onOpenAnimationEnd() {
        if (this.transitionAnimationInProgress && this.onOpenAnimationEndRunnable != null) {
            this.transitionAnimationInProgress = false;
            this.transitionAnimationStartTime = 0;
            this.onOpenAnimationEndRunnable.run();
            this.onOpenAnimationEndRunnable = null;
            checkNeedRebuild();
        }
    }

    private void onSlideAnimationEnd(boolean z) {
        BaseFragment baseFragment;
        if (z) {
            ViewGroup viewGroup;
            baseFragment = (BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 2);
            baseFragment.onPause();
            if (baseFragment.fragmentView != null) {
                viewGroup = (ViewGroup) baseFragment.fragmentView.getParent();
                if (viewGroup != null) {
                    baseFragment.onRemoveFromParent();
                    viewGroup.removeView(baseFragment.fragmentView);
                }
            }
            if (baseFragment.actionBar != null && baseFragment.actionBar.getAddToContainer()) {
                viewGroup = (ViewGroup) baseFragment.actionBar.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(baseFragment.actionBar);
                }
            }
        } else {
            baseFragment = (BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1);
            baseFragment.onPause();
            baseFragment.onFragmentDestroy();
            baseFragment.setParentLayout(null);
            this.fragmentsStack.remove(this.fragmentsStack.size() - 1);
            LinearLayoutContainer linearLayoutContainer = this.containerView;
            this.containerView = this.containerViewBack;
            this.containerViewBack = linearLayoutContainer;
            bringChildToFront(this.containerView);
            baseFragment = (BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1);
            this.currentActionBar = baseFragment.actionBar;
            baseFragment.onResume();
            baseFragment.onBecomeFullyVisible();
        }
        this.containerViewBack.setVisibility(8);
        this.startedTracking = false;
        this.animationInProgress = false;
        this.containerView.setTranslationX(BitmapDescriptorFactory.HUE_RED);
        this.containerViewBack.setTranslationX(BitmapDescriptorFactory.HUE_RED);
        setInnerTranslationX(BitmapDescriptorFactory.HUE_RED);
    }

    private void prepareForMoving(MotionEvent motionEvent) {
        this.maybeStartTracking = false;
        this.startedTracking = true;
        this.startedTrackingX = (int) motionEvent.getX();
        this.containerViewBack.setVisibility(0);
        this.beginTrackingSent = false;
        BaseFragment baseFragment = (BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 2);
        View view = baseFragment.fragmentView;
        View createView = view == null ? baseFragment.createView(this.parentActivity) : view;
        ViewGroup viewGroup = (ViewGroup) createView.getParent();
        if (viewGroup != null) {
            baseFragment.onRemoveFromParent();
            viewGroup.removeView(createView);
        }
        if (baseFragment.actionBar != null && baseFragment.actionBar.getAddToContainer()) {
            viewGroup = (ViewGroup) baseFragment.actionBar.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(baseFragment.actionBar);
            }
            if (this.removeActionBarExtraHeight) {
                baseFragment.actionBar.setOccupyStatusBar(false);
            }
            this.containerViewBack.addView(baseFragment.actionBar);
            baseFragment.actionBar.setTitleOverlayText(this.titleOverlayText, this.subtitleOverlayText, this.overlayAction);
        }
        this.containerViewBack.addView(createView);
        LayoutParams layoutParams = createView.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        createView.setLayoutParams(layoutParams);
        if (!baseFragment.hasOwnBackground && createView.getBackground() == null) {
            createView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        }
        baseFragment.onResume();
    }

    private void presentFragmentInternalRemoveOld(boolean z, BaseFragment baseFragment) {
        if (baseFragment != null) {
            baseFragment.onPause();
            if (z) {
                baseFragment.onFragmentDestroy();
                baseFragment.setParentLayout(null);
                this.fragmentsStack.remove(baseFragment);
            } else {
                ViewGroup viewGroup;
                if (baseFragment.fragmentView != null) {
                    viewGroup = (ViewGroup) baseFragment.fragmentView.getParent();
                    if (viewGroup != null) {
                        baseFragment.onRemoveFromParent();
                        viewGroup.removeView(baseFragment.fragmentView);
                    }
                }
                if (baseFragment.actionBar != null && baseFragment.actionBar.getAddToContainer()) {
                    viewGroup = (ViewGroup) baseFragment.actionBar.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(baseFragment.actionBar);
                    }
                }
            }
            this.containerViewBack.setVisibility(8);
        }
    }

    private void removeFragmentFromStackInternal(BaseFragment baseFragment) {
        baseFragment.onPause();
        baseFragment.onFragmentDestroy();
        baseFragment.setParentLayout(null);
        this.fragmentsStack.remove(baseFragment);
    }

    private void startLayoutAnimation(final boolean z, final boolean z2) {
        if (z2) {
            this.animationProgress = BitmapDescriptorFactory.HUE_RED;
            this.lastFrameTime = System.nanoTime() / C3446C.MICROS_PER_SECOND;
        }
        Runnable c37982 = new Runnable() {
            public void run() {
                long j = 18;
                if (ActionBarLayout.this.animationRunnable == this) {
                    ActionBarLayout.this.animationRunnable = null;
                    if (z2) {
                        ActionBarLayout.this.transitionAnimationStartTime = System.currentTimeMillis();
                    }
                    long nanoTime = System.nanoTime() / C3446C.MICROS_PER_SECOND;
                    long access$700 = nanoTime - ActionBarLayout.this.lastFrameTime;
                    if (access$700 <= 18) {
                        j = access$700;
                    }
                    ActionBarLayout.this.lastFrameTime = nanoTime;
                    ActionBarLayout.this.animationProgress = (((float) j) / 150.0f) + ActionBarLayout.this.animationProgress;
                    if (ActionBarLayout.this.animationProgress > 1.0f) {
                        ActionBarLayout.this.animationProgress = 1.0f;
                    }
                    float interpolation = ActionBarLayout.this.decelerateInterpolator.getInterpolation(ActionBarLayout.this.animationProgress);
                    if (z) {
                        ActionBarLayout.this.containerView.setAlpha(interpolation);
                        ActionBarLayout.this.containerView.setTranslationX((1.0f - interpolation) * ((float) AndroidUtilities.dp(48.0f)));
                    } else {
                        ActionBarLayout.this.containerViewBack.setAlpha(1.0f - interpolation);
                        ActionBarLayout.this.containerViewBack.setTranslationX(interpolation * ((float) AndroidUtilities.dp(48.0f)));
                    }
                    if (ActionBarLayout.this.animationProgress < 1.0f) {
                        ActionBarLayout.this.startLayoutAnimation(z, false);
                    } else {
                        ActionBarLayout.this.onAnimationEndCheck(false);
                    }
                }
            }
        };
        this.animationRunnable = c37982;
        AndroidUtilities.runOnUIThread(c37982);
    }

    public boolean addFragmentToStack(BaseFragment baseFragment) {
        return addFragmentToStack(baseFragment, -1);
    }

    public boolean addFragmentToStack(BaseFragment baseFragment, int i) {
        if ((this.delegate != null && !this.delegate.needAddFragmentToStack(baseFragment, this)) || !baseFragment.onFragmentCreate()) {
            return false;
        }
        baseFragment.setParentLayout(this);
        if (i == -1) {
            if (!this.fragmentsStack.isEmpty()) {
                ViewGroup viewGroup;
                BaseFragment baseFragment2 = (BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1);
                baseFragment2.onPause();
                if (baseFragment2.actionBar != null && baseFragment2.actionBar.getAddToContainer()) {
                    viewGroup = (ViewGroup) baseFragment2.actionBar.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(baseFragment2.actionBar);
                    }
                }
                if (baseFragment2.fragmentView != null) {
                    viewGroup = (ViewGroup) baseFragment2.fragmentView.getParent();
                    if (viewGroup != null) {
                        baseFragment2.onRemoveFromParent();
                        viewGroup.removeView(baseFragment2.fragmentView);
                    }
                }
            }
            this.fragmentsStack.add(baseFragment);
        } else {
            this.fragmentsStack.add(i, baseFragment);
        }
        return true;
    }

    public boolean checkTransitionAnimation() {
        if (this.transitionAnimationInProgress && this.transitionAnimationStartTime < System.currentTimeMillis() - 1500) {
            onAnimationEndCheck(true);
        }
        return this.transitionAnimationInProgress;
    }

    public void closeLastFragment(boolean z) {
        if (isInSelectMode()) {
            MessagesController.getInstance().unSelectAllDialogs();
        }
        setInSelectMode(false);
        if ((this.delegate == null || this.delegate.needCloseLastFragment(this)) && !checkTransitionAnimation() && !this.fragmentsStack.isEmpty()) {
            if (this.parentActivity.getCurrentFocus() != null) {
                AndroidUtilities.hideKeyboard(this.parentActivity.getCurrentFocus());
            }
            setInnerTranslationX(BitmapDescriptorFactory.HUE_RED);
            boolean z2 = z && this.parentActivity.getSharedPreferences("mainconfig", 0).getBoolean("view_animations", true);
            final BaseFragment baseFragment = (BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1);
            final BaseFragment baseFragment2 = this.fragmentsStack.size() > 1 ? (BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 2) : null;
            if (baseFragment2 != null) {
                ViewGroup viewGroup;
                LinearLayoutContainer linearLayoutContainer = this.containerView;
                this.containerView = this.containerViewBack;
                this.containerViewBack = linearLayoutContainer;
                this.containerView.setVisibility(0);
                baseFragment2.setParentLayout(this);
                View view = baseFragment2.fragmentView;
                if (view == null) {
                    view = baseFragment2.createView(this.parentActivity);
                } else {
                    viewGroup = (ViewGroup) view.getParent();
                    if (viewGroup != null) {
                        baseFragment2.onRemoveFromParent();
                        viewGroup.removeView(view);
                    }
                }
                if (baseFragment2.actionBar != null && baseFragment2.actionBar.getAddToContainer()) {
                    if (this.removeActionBarExtraHeight) {
                        baseFragment2.actionBar.setOccupyStatusBar(false);
                    }
                    viewGroup = (ViewGroup) baseFragment2.actionBar.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(baseFragment2.actionBar);
                    }
                    this.containerView.addView(baseFragment2.actionBar);
                    baseFragment2.actionBar.setTitleOverlayText(this.titleOverlayText, this.subtitleOverlayText, this.overlayAction);
                }
                this.containerView.addView(view);
                LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = -1;
                view.setLayoutParams(layoutParams);
                baseFragment2.onTransitionAnimationStart(true, true);
                baseFragment.onTransitionAnimationStart(false, false);
                baseFragment2.onResume();
                this.currentActionBar = baseFragment2.actionBar;
                if (!baseFragment2.hasOwnBackground && view.getBackground() == null) {
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                }
                if (!z2) {
                    closeLastFragmentInternalRemoveOld(baseFragment);
                }
                if (z2) {
                    this.transitionAnimationStartTime = System.currentTimeMillis();
                    this.transitionAnimationInProgress = true;
                    this.onCloseAnimationEndRunnable = new Runnable() {
                        public void run() {
                            ActionBarLayout.this.closeLastFragmentInternalRemoveOld(baseFragment);
                            ActionBarLayout.this.containerViewBack.setTranslationX(BitmapDescriptorFactory.HUE_RED);
                            baseFragment.onTransitionAnimationEnd(false, false);
                            baseFragment2.onTransitionAnimationEnd(true, true);
                            baseFragment2.onBecomeFullyVisible();
                        }
                    };
                    AnimatorSet onCustomTransitionAnimation = baseFragment.onCustomTransitionAnimation(false, new Runnable() {
                        public void run() {
                            ActionBarLayout.this.onAnimationEndCheck(false);
                        }
                    });
                    if (onCustomTransitionAnimation != null) {
                        this.currentAnimation = onCustomTransitionAnimation;
                        return;
                    } else if (this.containerView.isKeyboardVisible || this.containerViewBack.isKeyboardVisible) {
                        this.waitingForKeyboardCloseRunnable = new Runnable() {
                            public void run() {
                                if (ActionBarLayout.this.waitingForKeyboardCloseRunnable == this) {
                                    ActionBarLayout.this.waitingForKeyboardCloseRunnable = null;
                                    ActionBarLayout.this.startLayoutAnimation(false, true);
                                }
                            }
                        };
                        AndroidUtilities.runOnUIThread(this.waitingForKeyboardCloseRunnable, 200);
                        return;
                    } else {
                        startLayoutAnimation(false, true);
                        return;
                    }
                }
                baseFragment.onTransitionAnimationEnd(false, false);
                baseFragment2.onTransitionAnimationEnd(true, true);
                baseFragment2.onBecomeFullyVisible();
            } else if (this.useAlphaAnimations) {
                this.transitionAnimationStartTime = System.currentTimeMillis();
                this.transitionAnimationInProgress = true;
                this.onCloseAnimationEndRunnable = new Runnable() {
                    public void run() {
                        ActionBarLayout.this.removeFragmentFromStackInternal(baseFragment);
                        ActionBarLayout.this.setVisibility(8);
                        if (ActionBarLayout.this.backgroundView != null) {
                            ActionBarLayout.this.backgroundView.setVisibility(8);
                        }
                        if (ActionBarLayout.this.drawerLayoutContainer != null) {
                            ActionBarLayout.this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                        }
                    }
                };
                Collection arrayList = new ArrayList();
                arrayList.add(ObjectAnimator.ofFloat(this, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}));
                if (this.backgroundView != null) {
                    arrayList.add(ObjectAnimator.ofFloat(this.backgroundView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}));
                }
                this.currentAnimation = new AnimatorSet();
                this.currentAnimation.playTogether(arrayList);
                this.currentAnimation.setInterpolator(this.accelerateDecelerateInterpolator);
                this.currentAnimation.setDuration(200);
                this.currentAnimation.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        ActionBarLayout.this.onAnimationEndCheck(false);
                    }

                    public void onAnimationStart(Animator animator) {
                        ActionBarLayout.this.transitionAnimationStartTime = System.currentTimeMillis();
                    }
                });
                this.currentAnimation.start();
            } else {
                removeFragmentFromStackInternal(baseFragment);
                setVisibility(8);
                if (this.backgroundView != null) {
                    this.backgroundView.setVisibility(8);
                }
            }
        }
    }

    public void dismissDialogs() {
        if (!this.fragmentsStack.isEmpty()) {
            ((BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1)).dismissCurrentDialig();
        }
    }

    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        return (keyEvent != null && keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 1) ? (this.delegate != null && this.delegate.onPreIme()) || super.dispatchKeyEventPreIme(keyEvent) : super.dispatchKeyEventPreIme(keyEvent);
    }

    protected boolean drawChild(Canvas canvas, View view, long j) {
        int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
        int paddingRight = getPaddingRight() + ((int) this.innerTranslationX);
        int paddingLeft = getPaddingLeft();
        int paddingLeft2 = getPaddingLeft() + width;
        if (view == this.containerViewBack) {
            paddingLeft2 = paddingRight;
        } else if (view == this.containerView) {
            paddingLeft = paddingRight;
        }
        int save = canvas.save();
        if (!this.transitionAnimationInProgress) {
            canvas.clipRect(paddingLeft, 0, paddingLeft2, getHeight());
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(save);
        if (paddingRight != 0) {
            if (view == this.containerView) {
                float max = Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(((float) (width - paddingRight)) / ((float) AndroidUtilities.dp(20.0f)), 1.0f));
                layerShadowDrawable.setBounds(paddingRight - layerShadowDrawable.getIntrinsicWidth(), view.getTop(), paddingRight, view.getBottom());
                layerShadowDrawable.setAlpha((int) (max * 255.0f));
                layerShadowDrawable.draw(canvas);
            } else if (view == this.containerViewBack) {
                float min = Math.min(0.8f, ((float) (width - paddingRight)) / ((float) width));
                if (min < BitmapDescriptorFactory.HUE_RED) {
                    min = BitmapDescriptorFactory.HUE_RED;
                }
                scrimPaint.setColor(((int) (min * 153.0f)) << 24);
                canvas.drawRect((float) paddingLeft, BitmapDescriptorFactory.HUE_RED, (float) paddingLeft2, (float) getHeight(), scrimPaint);
            }
        }
        return drawChild;
    }

    public void drawHeaderShadow(Canvas canvas, int i) {
        if (headerShadowDrawable != null) {
            headerShadowDrawable.setBounds(0, i, getMeasuredWidth(), headerShadowDrawable.getIntrinsicHeight() + i);
            headerShadowDrawable.draw(canvas);
        }
    }

    public boolean extendActionMode(Menu menu) {
        return !this.fragmentsStack.isEmpty() && ((BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1)).extendActionMode(menu);
    }

    public DrawerLayoutContainer getDrawerLayoutContainer() {
        return this.drawerLayoutContainer;
    }

    public float getInnerTranslationX() {
        return this.innerTranslationX;
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public void init(ArrayList<BaseFragment> arrayList) {
        this.fragmentsStack = arrayList;
        this.containerViewBack = new LinearLayoutContainer(this.parentActivity);
        addView(this.containerViewBack);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.containerViewBack.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.gravity = 51;
        this.containerViewBack.setLayoutParams(layoutParams);
        this.containerView = new LinearLayoutContainer(this.parentActivity);
        addView(this.containerView);
        layoutParams = (FrameLayout.LayoutParams) this.containerView.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.gravity = 51;
        this.containerView.setLayoutParams(layoutParams);
        Iterator it = this.fragmentsStack.iterator();
        while (it.hasNext()) {
            ((BaseFragment) it.next()).setParentLayout(this);
        }
    }

    public boolean isInSelectMode() {
        return this.inSelectMode;
    }

    public boolean isShowShadow() {
        return this.showShadow;
    }

    public void onActionModeFinished(Object obj) {
        if (this.currentActionBar != null) {
            this.currentActionBar.setVisibility(0);
        }
        this.inActionMode = false;
    }

    public void onActionModeStarted(Object obj) {
        if (this.currentActionBar != null) {
            this.currentActionBar.setVisibility(8);
        }
        this.inActionMode = true;
    }

    public void onBackPressed() {
        if (!this.startedTracking && !checkTransitionAnimation() && !this.fragmentsStack.isEmpty()) {
            if (this.currentActionBar != null && this.currentActionBar.isSearchFieldVisible) {
                this.currentActionBar.closeSearchField();
            } else if (((BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1)).onBackPressed() && !this.fragmentsStack.isEmpty()) {
                closeLastFragment(true);
            }
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (!this.fragmentsStack.isEmpty()) {
            BaseFragment baseFragment = (BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1);
            baseFragment.onConfigurationChanged(configuration);
            if (baseFragment.visibleDialog instanceof BottomSheet) {
                ((BottomSheet) baseFragment.visibleDialog).onConfigurationChanged(configuration);
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.animationInProgress || checkTransitionAnimation() || onTouchEvent(motionEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (!(i != 82 || checkTransitionAnimation() || this.startedTracking || this.currentActionBar == null)) {
            this.currentActionBar.onMenuButtonPressed();
        }
        return super.onKeyUp(i, keyEvent);
    }

    public void onLowMemory() {
        Iterator it = this.fragmentsStack.iterator();
        while (it.hasNext()) {
            ((BaseFragment) it.next()).onLowMemory();
        }
    }

    public void onPause() {
        if (!this.fragmentsStack.isEmpty()) {
            ((BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1)).onPause();
        }
    }

    public void onResume() {
        if (this.transitionAnimationInProgress) {
            if (this.currentAnimation != null) {
                this.currentAnimation.cancel();
                this.currentAnimation = null;
            }
            if (this.onCloseAnimationEndRunnable != null) {
                onCloseAnimationEnd();
            } else if (this.onOpenAnimationEndRunnable != null) {
                onOpenAnimationEnd();
            }
        }
        if (!this.fragmentsStack.isEmpty()) {
            ((BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1)).onResume();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (checkTransitionAnimation() || this.inActionMode || this.animationInProgress) {
            return false;
        }
        if (this.fragmentsStack.size() > 1) {
            if (motionEvent == null || motionEvent.getAction() != 0 || this.startedTracking || this.maybeStartTracking) {
                if (motionEvent != null && motionEvent.getAction() == 2 && motionEvent.getPointerId(0) == this.startedTrackingPointerId) {
                    if (this.velocityTracker == null) {
                        this.velocityTracker = VelocityTracker.obtain();
                    }
                    int max = Math.max(0, (int) (motionEvent.getX() - ((float) this.startedTrackingX)));
                    int abs = Math.abs(((int) motionEvent.getY()) - this.startedTrackingY);
                    this.velocityTracker.addMovement(motionEvent);
                    if (this.maybeStartTracking && !this.startedTracking && ((float) max) >= AndroidUtilities.getPixelsInCM(0.4f, true) && Math.abs(max) / 3 > abs) {
                        prepareForMoving(motionEvent);
                    } else if (this.startedTracking) {
                        if (!this.beginTrackingSent) {
                            if (this.parentActivity.getCurrentFocus() != null) {
                                AndroidUtilities.hideKeyboard(this.parentActivity.getCurrentFocus());
                            }
                            ((BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1)).onBeginSlide();
                            this.beginTrackingSent = true;
                        }
                        this.containerView.setTranslationX((float) max);
                        setInnerTranslationX((float) max);
                    }
                } else if (motionEvent != null && motionEvent.getPointerId(0) == this.startedTrackingPointerId && (motionEvent.getAction() == 3 || motionEvent.getAction() == 1 || motionEvent.getAction() == 6)) {
                    float xVelocity;
                    float yVelocity;
                    if (this.velocityTracker == null) {
                        this.velocityTracker = VelocityTracker.obtain();
                    }
                    this.velocityTracker.computeCurrentVelocity(1000);
                    if (!this.startedTracking && ((BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1)).swipeBackEnabled) {
                        xVelocity = this.velocityTracker.getXVelocity();
                        yVelocity = this.velocityTracker.getYVelocity();
                        if (xVelocity >= 3500.0f && xVelocity > Math.abs(yVelocity)) {
                            prepareForMoving(motionEvent);
                            if (!this.beginTrackingSent) {
                                if (((Activity) getContext()).getCurrentFocus() != null) {
                                    AndroidUtilities.hideKeyboard(((Activity) getContext()).getCurrentFocus());
                                }
                                this.beginTrackingSent = true;
                            }
                        }
                    }
                    if (this.startedTracking) {
                        xVelocity = this.containerView.getX();
                        AnimatorSet animatorSet = new AnimatorSet();
                        yVelocity = this.velocityTracker.getXVelocity();
                        final boolean z = xVelocity < ((float) this.containerView.getMeasuredWidth()) / 3.0f && (yVelocity < 3500.0f || yVelocity < this.velocityTracker.getYVelocity());
                        Animator[] animatorArr;
                        if (z) {
                            animatorArr = new Animator[2];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.containerView, "translationX", new float[]{BitmapDescriptorFactory.HUE_RED});
                            animatorArr[1] = ObjectAnimator.ofFloat(this, "innerTranslationX", new float[]{BitmapDescriptorFactory.HUE_RED});
                            animatorSet.playTogether(animatorArr);
                        } else {
                            xVelocity = ((float) this.containerView.getMeasuredWidth()) - xVelocity;
                            animatorArr = new Animator[2];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.containerView, "translationX", new float[]{(float) this.containerView.getMeasuredWidth()});
                            animatorArr[1] = ObjectAnimator.ofFloat(this, "innerTranslationX", new float[]{(float) this.containerView.getMeasuredWidth()});
                            animatorSet.playTogether(animatorArr);
                        }
                        animatorSet.setDuration((long) Math.max((int) (xVelocity * (200.0f / ((float) this.containerView.getMeasuredWidth()))), 50));
                        animatorSet.addListener(new AnimatorListenerAdapter() {
                            public void onAnimationEnd(Animator animator) {
                                ActionBarLayout.this.onSlideAnimationEnd(z);
                            }
                        });
                        animatorSet.start();
                        this.animationInProgress = true;
                    } else {
                        this.maybeStartTracking = false;
                        this.startedTracking = false;
                    }
                    if (this.velocityTracker != null) {
                        this.velocityTracker.recycle();
                        this.velocityTracker = null;
                    }
                } else if (motionEvent == null) {
                    this.maybeStartTracking = false;
                    this.startedTracking = false;
                    if (this.velocityTracker != null) {
                        this.velocityTracker.recycle();
                        this.velocityTracker = null;
                    }
                }
            } else if (!((BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1)).swipeBackEnabled) {
                return false;
            } else {
                this.startedTrackingPointerId = motionEvent.getPointerId(0);
                this.maybeStartTracking = true;
                this.startedTrackingX = (int) motionEvent.getX();
                this.startedTrackingY = (int) motionEvent.getY();
                if (this.velocityTracker != null) {
                    this.velocityTracker.clear();
                }
            }
        }
        return this.startedTracking;
    }

    public boolean presentFragment(BaseFragment baseFragment) {
        return presentFragment(baseFragment, false, false, true);
    }

    public boolean presentFragment(BaseFragment baseFragment, boolean z) {
        return presentFragment(baseFragment, z, false, true);
    }

    public boolean presentFragment(final BaseFragment baseFragment, final boolean z, boolean z2, boolean z3) {
        try {
            if (checkTransitionAnimation() || ((this.delegate != null && z3 && !this.delegate.needPresentFragment(baseFragment, z, z2, this)) || !baseFragment.onFragmentCreate())) {
                return false;
            }
            ViewGroup viewGroup;
            if (this.parentActivity.getCurrentFocus() != null) {
                AndroidUtilities.hideKeyboard(this.parentActivity.getCurrentFocus());
            }
            Object obj = (z2 || !this.parentActivity.getSharedPreferences("mainconfig", 0).getBoolean("view_animations", true)) ? null : 1;
            final BaseFragment baseFragment2 = !this.fragmentsStack.isEmpty() ? (BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1) : null;
            baseFragment.setParentLayout(this);
            View view = baseFragment.fragmentView;
            if (view == null) {
                view = baseFragment.createView(this.parentActivity);
            } else {
                viewGroup = (ViewGroup) view.getParent();
                if (viewGroup != null) {
                    baseFragment.onRemoveFromParent();
                    viewGroup.removeView(view);
                }
            }
            if (baseFragment.actionBar != null && baseFragment.actionBar.getAddToContainer()) {
                if (this.removeActionBarExtraHeight) {
                    baseFragment.actionBar.setOccupyStatusBar(false);
                }
                viewGroup = (ViewGroup) baseFragment.actionBar.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(baseFragment.actionBar);
                }
                this.containerViewBack.addView(baseFragment.actionBar);
                baseFragment.actionBar.setTitleOverlayText(this.titleOverlayText, this.subtitleOverlayText, this.overlayAction);
            }
            this.containerViewBack.addView(view);
            LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            view.setLayoutParams(layoutParams);
            this.fragmentsStack.add(baseFragment);
            baseFragment.onResume();
            this.currentActionBar = baseFragment.actionBar;
            if (!baseFragment.hasOwnBackground && view.getBackground() == null) {
                view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            }
            LinearLayoutContainer linearLayoutContainer = this.containerView;
            this.containerView = this.containerViewBack;
            this.containerViewBack = linearLayoutContainer;
            this.containerView.setVisibility(0);
            setInnerTranslationX(BitmapDescriptorFactory.HUE_RED);
            bringChildToFront(this.containerView);
            if (obj == null) {
                presentFragmentInternalRemoveOld(z, baseFragment2);
                if (this.backgroundView != null) {
                    this.backgroundView.setVisibility(0);
                }
            }
            if (obj == null) {
                if (this.backgroundView != null) {
                    this.backgroundView.setAlpha(1.0f);
                    this.backgroundView.setVisibility(0);
                }
                baseFragment.onTransitionAnimationStart(true, false);
                baseFragment.onTransitionAnimationEnd(true, false);
                baseFragment.onBecomeFullyVisible();
            } else if (this.useAlphaAnimations && this.fragmentsStack.size() == 1) {
                presentFragmentInternalRemoveOld(z, baseFragment2);
                this.transitionAnimationStartTime = System.currentTimeMillis();
                this.transitionAnimationInProgress = true;
                this.onOpenAnimationEndRunnable = new Runnable() {
                    public void run() {
                        baseFragment.onTransitionAnimationEnd(true, false);
                        baseFragment.onBecomeFullyVisible();
                    }
                };
                Collection arrayList = new ArrayList();
                arrayList.add(ObjectAnimator.ofFloat(this, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}));
                if (this.backgroundView != null) {
                    this.backgroundView.setVisibility(0);
                    arrayList.add(ObjectAnimator.ofFloat(this.backgroundView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}));
                }
                baseFragment.onTransitionAnimationStart(true, false);
                this.currentAnimation = new AnimatorSet();
                this.currentAnimation.playTogether(arrayList);
                this.currentAnimation.setInterpolator(this.accelerateDecelerateInterpolator);
                this.currentAnimation.setDuration(200);
                this.currentAnimation.addListener(new C38004());
                this.currentAnimation.start();
            } else {
                this.transitionAnimationStartTime = System.currentTimeMillis();
                this.transitionAnimationInProgress = true;
                this.onOpenAnimationEndRunnable = new Runnable() {
                    public void run() {
                        ActionBarLayout.this.presentFragmentInternalRemoveOld(z, baseFragment2);
                        baseFragment.onTransitionAnimationEnd(true, false);
                        baseFragment.onBecomeFullyVisible();
                        ActionBarLayout.this.containerView.setTranslationX(BitmapDescriptorFactory.HUE_RED);
                    }
                };
                baseFragment.onTransitionAnimationStart(true, false);
                AnimatorSet onCustomTransitionAnimation = baseFragment.onCustomTransitionAnimation(true, new C38026());
                if (onCustomTransitionAnimation == null) {
                    this.containerView.setAlpha(BitmapDescriptorFactory.HUE_RED);
                    this.containerView.setTranslationX(48.0f);
                    if (this.containerView.isKeyboardVisible || this.containerViewBack.isKeyboardVisible) {
                        this.waitingForKeyboardCloseRunnable = new C38037();
                        AndroidUtilities.runOnUIThread(this.waitingForKeyboardCloseRunnable, 200);
                    } else if (baseFragment.needDelayOpenAnimation()) {
                        this.delayedOpenAnimationRunnable = new C38048();
                        AndroidUtilities.runOnUIThread(this.delayedOpenAnimationRunnable, 200);
                    } else {
                        startLayoutAnimation(true, true);
                    }
                } else {
                    this.containerView.setAlpha(1.0f);
                    this.containerView.setTranslationX(BitmapDescriptorFactory.HUE_RED);
                    this.currentAnimation = onCustomTransitionAnimation;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void rebuildAllFragmentViews(boolean z, boolean z2) {
        if (this.transitionAnimationInProgress) {
            this.rebuildAfterAnimation = true;
            this.rebuildLastAfterAnimation = z;
            this.showLastAfterAnimation = z2;
            return;
        }
        int i = 0;
        while (true) {
            if (i >= this.fragmentsStack.size() - (z ? 0 : 1)) {
                break;
            }
            ((BaseFragment) this.fragmentsStack.get(i)).clearViews();
            ((BaseFragment) this.fragmentsStack.get(i)).setParentLayout(this);
            i++;
        }
        if (this.delegate != null) {
            this.delegate.onRebuildAllFragments(this);
        }
        if (z2) {
            showLastFragment();
        }
    }

    public void removeAllFragments() {
        while (this.fragmentsStack.size() > 0) {
            removeFragmentFromStackInternal((BaseFragment) this.fragmentsStack.get(0));
        }
    }

    public void removeFragmentFromStack(BaseFragment baseFragment) {
        if (this.useAlphaAnimations && this.fragmentsStack.size() == 1 && AndroidUtilities.isTablet()) {
            closeLastFragment(true);
        } else {
            removeFragmentFromStackInternal(baseFragment);
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        onTouchEvent(null);
        super.requestDisallowInterceptTouchEvent(z);
    }

    public void resumeDelayedFragmentAnimation() {
        if (this.delayedOpenAnimationRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.delayedOpenAnimationRunnable);
            this.delayedOpenAnimationRunnable.run();
            this.delayedOpenAnimationRunnable = null;
        }
    }

    public void setBackgroundView(View view) {
        this.backgroundView = view;
    }

    public void setDelegate(ActionBarLayoutDelegate actionBarLayoutDelegate) {
        this.delegate = actionBarLayoutDelegate;
    }

    public void setDrawerLayoutContainer(DrawerLayoutContainer drawerLayoutContainer) {
        this.drawerLayoutContainer = drawerLayoutContainer;
    }

    public void setInSelectMode(boolean z) {
        this.inSelectMode = z;
    }

    public void setInnerTranslationX(float f) {
        this.innerTranslationX = f;
        invalidate();
    }

    public void setRemoveActionBarExtraHeight(boolean z) {
        this.removeActionBarExtraHeight = z;
    }

    public void setShowShadow(boolean z) {
        this.showShadow = z;
    }

    public void setTitleOverlayText(String str, String str2, Runnable runnable) {
        this.titleOverlayText = str;
        this.subtitleOverlayText = str2;
        this.overlayAction = runnable;
        for (int i = 0; i < this.fragmentsStack.size(); i++) {
            BaseFragment baseFragment = (BaseFragment) this.fragmentsStack.get(i);
            if (baseFragment.actionBar != null) {
                baseFragment.actionBar.setTitleOverlayText(this.titleOverlayText, this.subtitleOverlayText, runnable);
            }
        }
    }

    public void setUseAlphaAnimations(boolean z) {
        this.useAlphaAnimations = z;
    }

    public void showLastFragment() {
        if (!this.fragmentsStack.isEmpty()) {
            BaseFragment baseFragment;
            ViewGroup viewGroup;
            for (int i = 0; i < this.fragmentsStack.size() - 1; i++) {
                baseFragment = (BaseFragment) this.fragmentsStack.get(i);
                if (baseFragment.actionBar != null && baseFragment.actionBar.getAddToContainer()) {
                    viewGroup = (ViewGroup) baseFragment.actionBar.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(baseFragment.actionBar);
                    }
                }
                if (baseFragment.fragmentView != null) {
                    viewGroup = (ViewGroup) baseFragment.fragmentView.getParent();
                    if (viewGroup != null) {
                        baseFragment.onPause();
                        baseFragment.onRemoveFromParent();
                        viewGroup.removeView(baseFragment.fragmentView);
                    }
                }
            }
            baseFragment = (BaseFragment) this.fragmentsStack.get(this.fragmentsStack.size() - 1);
            baseFragment.setParentLayout(this);
            View view = baseFragment.fragmentView;
            if (view == null) {
                view = baseFragment.createView(this.parentActivity);
            } else {
                viewGroup = (ViewGroup) view.getParent();
                if (viewGroup != null) {
                    baseFragment.onRemoveFromParent();
                    viewGroup.removeView(view);
                }
            }
            if (baseFragment.actionBar != null && baseFragment.actionBar.getAddToContainer()) {
                if (this.removeActionBarExtraHeight) {
                    baseFragment.actionBar.setOccupyStatusBar(false);
                }
                viewGroup = (ViewGroup) baseFragment.actionBar.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(baseFragment.actionBar);
                }
                this.containerView.addView(baseFragment.actionBar);
                baseFragment.actionBar.setTitleOverlayText(this.titleOverlayText, this.subtitleOverlayText, this.overlayAction);
            }
            this.containerView.addView(view, LayoutHelper.createLinear(-1, -1));
            baseFragment.onResume();
            this.currentActionBar = baseFragment.actionBar;
            if (!baseFragment.hasOwnBackground && view.getBackground() == null) {
                view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            }
        }
    }

    public void startActivityForResult(Intent intent, int i) {
        if (this.parentActivity != null) {
            if (this.transitionAnimationInProgress) {
                if (this.currentAnimation != null) {
                    this.currentAnimation.cancel();
                    this.currentAnimation = null;
                }
                if (this.onCloseAnimationEndRunnable != null) {
                    onCloseAnimationEnd();
                } else if (this.onOpenAnimationEndRunnable != null) {
                    onOpenAnimationEnd();
                }
                this.containerView.invalidate();
                if (intent != null) {
                    this.parentActivity.startActivityForResult(intent, i);
                }
            } else if (intent != null) {
                this.parentActivity.startActivityForResult(intent, i);
            }
        }
    }
}
