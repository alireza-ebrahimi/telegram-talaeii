package org.telegram.ui.ActionBar;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import java.lang.reflect.Field;
import java.util.HashMap;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.ui.Components.LayoutHelper;

public class ActionBarPopupWindow extends PopupWindow {
    private static final OnScrollChangedListener NOP = new C19781();
    private static final boolean allowAnimation;
    private static DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private static final Field superListenerField;
    private boolean animationEnabled = allowAnimation;
    private OnScrollChangedListener mSuperScrollListener;
    private ViewTreeObserver mViewTreeObserver;
    private AnimatorSet windowAnimatorSet;

    public interface OnDispatchKeyEventListener {
        void onDispatchKeyEvent(KeyEvent keyEvent);
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarPopupWindow$1 */
    static class C19781 implements OnScrollChangedListener {
        C19781() {
        }

        public void onScrollChanged() {
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarPopupWindow$2 */
    class C19792 implements AnimatorListener {
        C19792() {
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            ActionBarPopupWindow.this.windowAnimatorSet = null;
        }

        public void onAnimationCancel(Animator animation) {
            onAnimationEnd(animation);
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarPopupWindow$3 */
    class C19803 implements AnimatorListener {
        C19803() {
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            ActionBarPopupWindow.this.windowAnimatorSet = null;
            ActionBarPopupWindow.this.setFocusable(false);
            try {
                super.dismiss();
            } catch (Exception e) {
            }
            ActionBarPopupWindow.this.unregisterListener();
        }

        public void onAnimationCancel(Animator animation) {
            onAnimationEnd(animation);
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    public static class ActionBarPopupWindowLayout extends FrameLayout {
        private boolean animationEnabled = ActionBarPopupWindow.allowAnimation;
        private int backAlpha = 255;
        private float backScaleX = 1.0f;
        private float backScaleY = 1.0f;
        protected Drawable backgroundDrawable = getResources().getDrawable(R.drawable.popup_fixed).mutate();
        private int lastStartedChild = 0;
        protected LinearLayout linearLayout;
        private OnDispatchKeyEventListener mOnDispatchKeyEventListener;
        private HashMap<View, Integer> positions = new HashMap();
        private ScrollView scrollView;
        private boolean showedFromBotton;

        public ActionBarPopupWindowLayout(Context context) {
            super(context);
            this.backgroundDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_actionBarDefaultSubmenuBackground), Mode.MULTIPLY));
            setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f));
            setWillNotDraw(false);
            try {
                this.scrollView = new ScrollView(context);
                this.scrollView.setVerticalScrollBarEnabled(false);
                addView(this.scrollView, LayoutHelper.createFrame(-2, -2.0f));
            } catch (Throwable e) {
                FileLog.e(e);
            }
            this.linearLayout = new LinearLayout(context);
            this.linearLayout.setOrientation(1);
            if (this.scrollView != null) {
                this.scrollView.addView(this.linearLayout, new LayoutParams(-2, -2));
            } else {
                addView(this.linearLayout, LayoutHelper.createFrame(-2, -2.0f));
            }
        }

        public void setShowedFromBotton(boolean value) {
            this.showedFromBotton = value;
        }

        public void setDispatchKeyEventListener(OnDispatchKeyEventListener listener) {
            this.mOnDispatchKeyEventListener = listener;
        }

        public void setBackAlpha(int value) {
            this.backAlpha = value;
        }

        public int getBackAlpha() {
            return this.backAlpha;
        }

        public void setBackScaleX(float value) {
            this.backScaleX = value;
            invalidate();
        }

        public void setBackScaleY(float value) {
            this.backScaleY = value;
            if (this.animationEnabled) {
                int a;
                int count = getItemsCount();
                int visibleCount = 0;
                for (a = 0; a < count; a++) {
                    visibleCount += getItemAt(a).getVisibility() == 0 ? 1 : 0;
                }
                int height = getMeasuredHeight() - AndroidUtilities.dp(16.0f);
                View child;
                Integer position;
                if (this.showedFromBotton) {
                    for (a = this.lastStartedChild; a >= 0; a--) {
                        child = getItemAt(a);
                        if (child.getVisibility() == 0) {
                            position = (Integer) this.positions.get(child);
                            if (position != null && ((float) (height - ((position.intValue() * AndroidUtilities.dp(48.0f)) + AndroidUtilities.dp(32.0f)))) > ((float) height) * value) {
                                break;
                            }
                            this.lastStartedChild = a - 1;
                            startChildAnimation(child);
                        }
                    }
                } else {
                    for (a = this.lastStartedChild; a < count; a++) {
                        child = getItemAt(a);
                        if (child.getVisibility() == 0) {
                            position = (Integer) this.positions.get(child);
                            if (position != null && ((float) (((position.intValue() + 1) * AndroidUtilities.dp(48.0f)) - AndroidUtilities.dp(24.0f))) > ((float) height) * value) {
                                break;
                            }
                            this.lastStartedChild = a + 1;
                            startChildAnimation(child);
                        }
                    }
                }
            }
            invalidate();
        }

        public void setBackgroundDrawable(Drawable drawable) {
            this.backgroundDrawable = drawable;
        }

        private void startChildAnimation(View child) {
            if (this.animationEnabled) {
                AnimatorSet animatorSet = new AnimatorSet();
                Animator[] animatorArr = new Animator[2];
                animatorArr[0] = ObjectAnimator.ofFloat(child, "alpha", new float[]{0.0f, 1.0f});
                String str = "translationY";
                float[] fArr = new float[2];
                fArr[0] = (float) AndroidUtilities.dp(this.showedFromBotton ? 6.0f : -6.0f);
                fArr[1] = 0.0f;
                animatorArr[1] = ObjectAnimator.ofFloat(child, str, fArr);
                animatorSet.playTogether(animatorArr);
                animatorSet.setDuration(180);
                animatorSet.setInterpolator(ActionBarPopupWindow.decelerateInterpolator);
                animatorSet.start();
            }
        }

        public void setAnimationEnabled(boolean value) {
            this.animationEnabled = value;
        }

        public void addView(View child) {
            this.linearLayout.addView(child);
        }

        public void removeInnerViews() {
            this.linearLayout.removeAllViews();
        }

        public float getBackScaleX() {
            return this.backScaleX;
        }

        public float getBackScaleY() {
            return this.backScaleY;
        }

        public boolean dispatchKeyEvent(KeyEvent event) {
            if (this.mOnDispatchKeyEventListener != null) {
                this.mOnDispatchKeyEventListener.onDispatchKeyEvent(event);
            }
            return super.dispatchKeyEvent(event);
        }

        protected void onDraw(Canvas canvas) {
            if (this.backgroundDrawable != null) {
                this.backgroundDrawable.setAlpha(this.backAlpha);
                int height = getMeasuredHeight();
                if (this.showedFromBotton) {
                    this.backgroundDrawable.setBounds(0, (int) (((float) getMeasuredHeight()) * (1.0f - this.backScaleY)), (int) (((float) getMeasuredWidth()) * this.backScaleX), getMeasuredHeight());
                } else {
                    this.backgroundDrawable.setBounds(0, 0, (int) (((float) getMeasuredWidth()) * this.backScaleX), (int) (((float) getMeasuredHeight()) * this.backScaleY));
                }
                this.backgroundDrawable.draw(canvas);
            }
        }

        public int getItemsCount() {
            return this.linearLayout.getChildCount();
        }

        public View getItemAt(int index) {
            return this.linearLayout.getChildAt(index);
        }

        public void scrollToTop() {
            if (this.scrollView != null) {
                this.scrollView.scrollTo(0, 0);
            }
        }
    }

    static {
        boolean z = true;
        if (VERSION.SDK_INT < 18) {
            z = false;
        }
        allowAnimation = z;
        Field field = null;
        try {
            field = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
        }
        superListenerField = field;
    }

    public ActionBarPopupWindow() {
        init();
    }

    public ActionBarPopupWindow(Context context) {
        super(context);
        init();
    }

    public ActionBarPopupWindow(int width, int height) {
        super(width, height);
        init();
    }

    public ActionBarPopupWindow(View contentView) {
        super(contentView);
        init();
    }

    public ActionBarPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        init();
    }

    public ActionBarPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
        init();
    }

    public void setAnimationEnabled(boolean value) {
        this.animationEnabled = value;
    }

    private void init() {
        if (superListenerField != null) {
            try {
                this.mSuperScrollListener = (OnScrollChangedListener) superListenerField.get(this);
                superListenerField.set(this, NOP);
            } catch (Exception e) {
                this.mSuperScrollListener = null;
            }
        }
    }

    private void unregisterListener() {
        if (this.mSuperScrollListener != null && this.mViewTreeObserver != null) {
            if (this.mViewTreeObserver.isAlive()) {
                this.mViewTreeObserver.removeOnScrollChangedListener(this.mSuperScrollListener);
            }
            this.mViewTreeObserver = null;
        }
    }

    private void registerListener(View anchor) {
        if (this.mSuperScrollListener != null) {
            ViewTreeObserver vto = anchor.getWindowToken() != null ? anchor.getViewTreeObserver() : null;
            if (vto != this.mViewTreeObserver) {
                if (this.mViewTreeObserver != null && this.mViewTreeObserver.isAlive()) {
                    this.mViewTreeObserver.removeOnScrollChangedListener(this.mSuperScrollListener);
                }
                this.mViewTreeObserver = vto;
                if (vto != null) {
                    vto.addOnScrollChangedListener(this.mSuperScrollListener);
                }
            }
        }
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        try {
            super.showAsDropDown(anchor, xoff, yoff);
            registerListener(anchor);
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public void startAnimation() {
        if (this.animationEnabled && this.windowAnimatorSet == null) {
            ActionBarPopupWindowLayout content = (ActionBarPopupWindowLayout) getContentView();
            content.setTranslationY(0.0f);
            content.setAlpha(1.0f);
            content.setPivotX((float) content.getMeasuredWidth());
            content.setPivotY(0.0f);
            int count = content.getItemsCount();
            content.positions.clear();
            int visibleCount = 0;
            for (int a = 0; a < count; a++) {
                View child = content.getItemAt(a);
                if (child.getVisibility() == 0) {
                    content.positions.put(child, Integer.valueOf(visibleCount));
                    child.setAlpha(0.0f);
                    visibleCount++;
                }
            }
            if (content.showedFromBotton) {
                content.lastStartedChild = count - 1;
            } else {
                content.lastStartedChild = 0;
            }
            this.windowAnimatorSet = new AnimatorSet();
            this.windowAnimatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(content, "backScaleY", new float[]{0.0f, 1.0f}), ObjectAnimator.ofInt(content, "backAlpha", new int[]{0, 255})});
            this.windowAnimatorSet.setDuration((long) ((visibleCount * 16) + 150));
            this.windowAnimatorSet.addListener(new C19792());
            this.windowAnimatorSet.start();
        }
    }

    public void update(View anchor, int xoff, int yoff, int width, int height) {
        super.update(anchor, xoff, yoff, width, height);
        registerListener(anchor);
    }

    public void update(View anchor, int width, int height) {
        super.update(anchor, width, height);
        registerListener(anchor);
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        unregisterListener();
    }

    public void dismiss() {
        dismiss(true);
    }

    public void dismiss(boolean animated) {
        setFocusable(false);
        if (this.animationEnabled && animated) {
            if (this.windowAnimatorSet != null) {
                this.windowAnimatorSet.cancel();
            }
            ActionBarPopupWindowLayout content = (ActionBarPopupWindowLayout) getContentView();
            this.windowAnimatorSet = new AnimatorSet();
            AnimatorSet animatorSet = this.windowAnimatorSet;
            Animator[] animatorArr = new Animator[2];
            String str = "translationY";
            float[] fArr = new float[1];
            fArr[0] = (float) AndroidUtilities.dp(content.showedFromBotton ? 5.0f : -5.0f);
            animatorArr[0] = ObjectAnimator.ofFloat(content, str, fArr);
            animatorArr[1] = ObjectAnimator.ofFloat(content, "alpha", new float[]{0.0f});
            animatorSet.playTogether(animatorArr);
            this.windowAnimatorSet.setDuration(150);
            this.windowAnimatorSet.addListener(new C19803());
            this.windowAnimatorSet.start();
            return;
        }
        try {
            super.dismiss();
        } catch (Exception e) {
        }
        unregisterListener();
    }
}
