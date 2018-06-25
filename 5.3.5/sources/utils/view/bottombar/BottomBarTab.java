package utils.view.bottombar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.ir.talaeii.R;

public class BottomBarTab extends LinearLayout {
    private static final float ACTIVE_TITLE_SCALE = 1.0f;
    private static final long ANIMATION_DURATION = 150;
    private static final float INACTIVE_FIXED_TITLE_SCALE = 0.86f;
    @VisibleForTesting
    static final String STATE_BADGE_COUNT = "STATE_BADGE_COUNT_FOR_TAB_";
    private float activeAlpha;
    private int activeColor;
    @VisibleForTesting
    BottomBarBadge badge;
    private int badgeBackgroundColor;
    private int barColorWhenSelected;
    private final int eightDps;
    private int iconResId;
    private AppCompatImageView iconView;
    private float inActiveAlpha;
    private int inActiveColor;
    private int indexInContainer;
    private boolean isActive;
    private final int sixDps;
    private final int sixteenDps;
    private String title;
    private int titleTextAppearanceResId;
    private Typeface titleTypeFace;
    private TextView titleView;
    private Type type = Type.FIXED;

    /* renamed from: utils.view.bottombar.BottomBarTab$1 */
    class C34921 implements AnimatorUpdateListener {
        C34921() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            BottomBarTab.this.setColors(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
    }

    /* renamed from: utils.view.bottombar.BottomBarTab$2 */
    class C34932 implements AnimatorUpdateListener {
        C34932() {
        }

        public void onAnimationUpdate(ValueAnimator animator) {
            LayoutParams params = BottomBarTab.this.getLayoutParams();
            if (params != null) {
                params.width = Math.round(((Float) animator.getAnimatedValue()).floatValue());
                BottomBarTab.this.setLayoutParams(params);
            }
        }
    }

    /* renamed from: utils.view.bottombar.BottomBarTab$3 */
    class C34943 extends AnimatorListenerAdapter {
        C34943() {
        }

        public void onAnimationEnd(Animator animation) {
            if (!BottomBarTab.this.isActive && BottomBarTab.this.badge != null) {
                BottomBarTab.this.badge.adjustPositionAndSize(BottomBarTab.this);
                BottomBarTab.this.badge.show();
            }
        }
    }

    /* renamed from: utils.view.bottombar.BottomBarTab$4 */
    class C34954 implements AnimatorUpdateListener {
        C34954() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            BottomBarTab.this.iconView.setPadding(BottomBarTab.this.iconView.getPaddingLeft(), ((Integer) animation.getAnimatedValue()).intValue(), BottomBarTab.this.iconView.getPaddingRight(), BottomBarTab.this.iconView.getPaddingBottom());
        }
    }

    public static class Config {
        private final float activeTabAlpha;
        private final int activeTabColor;
        private final int badgeBackgroundColor;
        private final int barColorWhenSelected;
        private final float inActiveTabAlpha;
        private final int inActiveTabColor;
        private final int titleTextAppearance;
        private final Typeface titleTypeFace;

        public static class Builder {
            private float activeTabAlpha;
            private int activeTabColor;
            private int badgeBackgroundColor;
            private int barColorWhenSelected;
            private float inActiveTabAlpha;
            private int inActiveTabColor;
            private int titleTextAppearance;
            private Typeface titleTypeFace;

            public Builder inActiveTabAlpha(float alpha) {
                this.inActiveTabAlpha = alpha;
                return this;
            }

            public Builder activeTabAlpha(float alpha) {
                this.activeTabAlpha = alpha;
                return this;
            }

            public Builder inActiveTabColor(@ColorInt int color) {
                this.inActiveTabColor = color;
                return this;
            }

            public Builder activeTabColor(@ColorInt int color) {
                this.activeTabColor = color;
                return this;
            }

            public Builder barColorWhenSelected(@ColorInt int color) {
                this.barColorWhenSelected = color;
                return this;
            }

            public Builder badgeBackgroundColor(@ColorInt int color) {
                this.badgeBackgroundColor = color;
                return this;
            }

            public Builder titleTextAppearance(int titleTextAppearance) {
                this.titleTextAppearance = titleTextAppearance;
                return this;
            }

            public Builder titleTypeFace(Typeface titleTypeFace) {
                this.titleTypeFace = titleTypeFace;
                return this;
            }

            public Config build() {
                return new Config();
            }
        }

        private Config(Builder builder) {
            this.inActiveTabAlpha = builder.inActiveTabAlpha;
            this.activeTabAlpha = builder.activeTabAlpha;
            this.inActiveTabColor = builder.inActiveTabColor;
            this.activeTabColor = builder.activeTabColor;
            this.barColorWhenSelected = builder.barColorWhenSelected;
            this.badgeBackgroundColor = builder.badgeBackgroundColor;
            this.titleTextAppearance = builder.titleTextAppearance;
            this.titleTypeFace = builder.titleTypeFace;
        }
    }

    enum Type {
        FIXED,
        SHIFTING,
        TABLET
    }

    BottomBarTab(Context context) {
        super(context);
        this.sixDps = MiscUtils.dpToPixel(context, 6.0f);
        this.eightDps = MiscUtils.dpToPixel(context, 8.0f);
        this.sixteenDps = MiscUtils.dpToPixel(context, 16.0f);
    }

    void setConfig(Config config) {
        setInActiveAlpha(config.inActiveTabAlpha);
        setActiveAlpha(config.activeTabAlpha);
        setInActiveColor(config.inActiveTabColor);
        setActiveColor(config.activeTabColor);
        setBarColorWhenSelected(config.barColorWhenSelected);
        setBadgeBackgroundColor(config.badgeBackgroundColor);
        setTitleTextAppearance(config.titleTextAppearance);
        setTitleTypeface(config.titleTypeFace);
    }

    void prepareLayout() {
        inflate(getContext(), getLayoutResource(), this);
        setOrientation(1);
        setGravity(17);
        setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.iconView = (AppCompatImageView) findViewById(R.id.bb_bottom_bar_icon);
        this.iconView.setImageResource(this.iconResId);
        if (this.type != Type.TABLET) {
            this.titleView = (TextView) findViewById(R.id.bb_bottom_bar_title);
            updateTitle();
        }
        updateCustomTextAppearance();
        updateCustomTypeface();
    }

    @VisibleForTesting
    int getLayoutResource() {
        switch (this.type) {
            case FIXED:
                return R.layout.bb_bottom_bar_item_fixed;
            case SHIFTING:
                return R.layout.bb_bottom_bar_item_shifting;
            case TABLET:
                return R.layout.bb_bottom_bar_item_fixed_tablet;
            default:
                throw new RuntimeException("Unknown BottomBarTab type.");
        }
    }

    private void updateTitle() {
        if (this.titleView != null) {
            this.titleView.setText(this.title);
        }
    }

    private void updateCustomTextAppearance() {
        if (this.titleView != null && this.titleTextAppearanceResId != 0) {
            if (VERSION.SDK_INT >= 23) {
                this.titleView.setTextAppearance(this.titleTextAppearanceResId);
            } else {
                this.titleView.setTextAppearance(getContext(), this.titleTextAppearanceResId);
            }
            this.titleView.setTag(Integer.valueOf(this.titleTextAppearanceResId));
        }
    }

    private void updateCustomTypeface() {
        if (this.titleTypeFace != null && this.titleView != null) {
            this.titleView.setTypeface(this.titleTypeFace);
        }
    }

    Type getType() {
        return this.type;
    }

    void setType(Type type) {
        this.type = type;
    }

    public ViewGroup getOuterView() {
        return (ViewGroup) getParent();
    }

    AppCompatImageView getIconView() {
        return this.iconView;
    }

    int getIconResId() {
        return this.iconResId;
    }

    void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    TextView getTitleView() {
        return this.titleView;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
        updateTitle();
    }

    public float getInActiveAlpha() {
        return this.inActiveAlpha;
    }

    public void setInActiveAlpha(float inActiveAlpha) {
        this.inActiveAlpha = inActiveAlpha;
        if (!this.isActive) {
            setAlphas(inActiveAlpha);
        }
    }

    public float getActiveAlpha() {
        return this.activeAlpha;
    }

    public void setActiveAlpha(float activeAlpha) {
        this.activeAlpha = activeAlpha;
        if (this.isActive) {
            setAlphas(activeAlpha);
        }
    }

    public int getInActiveColor() {
        return this.inActiveColor;
    }

    public void setInActiveColor(int inActiveColor) {
        this.inActiveColor = inActiveColor;
        if (!this.isActive) {
            setColors(inActiveColor);
        }
    }

    public int getActiveColor() {
        return this.activeColor;
    }

    public void setActiveColor(int activeIconColor) {
        this.activeColor = activeIconColor;
        if (this.isActive) {
            setColors(this.activeColor);
        }
    }

    public int getBarColorWhenSelected() {
        return this.barColorWhenSelected;
    }

    public void setBarColorWhenSelected(int barColorWhenSelected) {
        this.barColorWhenSelected = barColorWhenSelected;
    }

    public int getBadgeBackgroundColor() {
        return this.badgeBackgroundColor;
    }

    public void setBadgeBackgroundColor(int badgeBackgroundColor) {
        this.badgeBackgroundColor = badgeBackgroundColor;
        if (this.badge != null) {
            this.badge.setColoredCircleBackground(badgeBackgroundColor);
        }
    }

    int getCurrentDisplayedIconColor() {
        if (this.iconView.getTag() instanceof Integer) {
            return ((Integer) this.iconView.getTag()).intValue();
        }
        return 0;
    }

    int getCurrentDisplayedTitleColor() {
        if (this.titleView != null) {
            return this.titleView.getCurrentTextColor();
        }
        return 0;
    }

    int getCurrentDisplayedTextAppearance() {
        Object tag = this.titleView.getTag();
        if (this.titleView == null || !(tag instanceof Integer)) {
            return 0;
        }
        return ((Integer) this.titleView.getTag()).intValue();
    }

    public void setBadgeCount(int count) {
        if (count > 0) {
            if (this.badge == null) {
                this.badge = new BottomBarBadge(getContext());
                this.badge.attachToTab(this, this.badgeBackgroundColor);
            }
            this.badge.setCount(count);
        } else if (this.badge != null) {
            this.badge.removeFromTab(this);
            this.badge = null;
        }
    }

    public void removeBadge() {
        setBadgeCount(0);
    }

    boolean isActive() {
        return this.isActive;
    }

    boolean hasActiveBadge() {
        return this.badge != null;
    }

    int getIndexInTabContainer() {
        return this.indexInContainer;
    }

    void setIndexInContainer(int indexInContainer) {
        this.indexInContainer = indexInContainer;
    }

    void setIconTint(int tint) {
        this.iconView.setColorFilter(tint);
    }

    void setTitleTextAppearance(int resId) {
        this.titleTextAppearanceResId = resId;
        updateCustomTextAppearance();
    }

    public int getTitleTextAppearance() {
        return this.titleTextAppearanceResId;
    }

    public void setTitleTypeface(Typeface typeface) {
        this.titleTypeFace = typeface;
        updateCustomTypeface();
    }

    public Typeface getTitleTypeFace() {
        return this.titleTypeFace;
    }

    void select(boolean animate) {
        this.isActive = true;
        if (animate) {
            setTopPaddingAnimated(this.iconView.getPaddingTop(), this.sixDps);
            animateIcon(this.activeAlpha);
            animateTitle(1.0f, this.activeAlpha);
            animateColors(this.inActiveColor, this.activeColor);
        } else {
            setTitleScale(1.0f);
            setTopPadding(this.sixDps);
            setColors(this.activeColor);
            setAlphas(this.activeAlpha);
        }
        if (this.badge != null) {
            this.badge.hide();
        }
    }

    void deselect(boolean animate) {
        boolean isShifting = false;
        this.isActive = false;
        if (this.type == Type.SHIFTING) {
            isShifting = true;
        }
        float scale = isShifting ? 0.0f : INACTIVE_FIXED_TITLE_SCALE;
        int iconPaddingTop = isShifting ? this.sixteenDps : this.eightDps;
        if (animate) {
            setTopPaddingAnimated(this.iconView.getPaddingTop(), iconPaddingTop);
            animateTitle(scale, this.inActiveAlpha);
            animateIcon(this.inActiveAlpha);
            animateColors(this.activeColor, this.inActiveColor);
        } else {
            setTitleScale(scale);
            setTopPadding(iconPaddingTop);
            setColors(this.inActiveColor);
            setAlphas(this.inActiveAlpha);
        }
        if (!isShifting && this.badge != null) {
            this.badge.show();
        }
    }

    private void animateColors(int previousColor, int color) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(new int[]{previousColor, color});
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new C34921());
        anim.setDuration(ANIMATION_DURATION);
        anim.start();
    }

    private void setColors(int color) {
        if (this.iconView != null) {
            this.iconView.setColorFilter(color);
            this.iconView.setTag(Integer.valueOf(color));
        }
        if (this.titleView != null) {
            this.titleView.setTextColor(color);
        }
    }

    private void setAlphas(float alpha) {
        if (this.iconView != null) {
            ViewCompat.setAlpha(this.iconView, alpha);
        }
        if (this.titleView != null) {
            ViewCompat.setAlpha(this.titleView, alpha);
        }
    }

    void updateWidth(float endWidth, boolean animated) {
        if (animated) {
            float start = (float) getWidth();
            ValueAnimator animator = ValueAnimator.ofFloat(new float[]{start, endWidth});
            animator.setDuration(ANIMATION_DURATION);
            animator.addUpdateListener(new C34932());
            animator.addListener(new C34943());
            animator.start();
            return;
        }
        getLayoutParams().width = (int) endWidth;
        if (!this.isActive && this.badge != null) {
            this.badge.adjustPositionAndSize(this);
            this.badge.show();
        }
    }

    private void updateBadgePosition() {
        if (this.badge != null) {
            this.badge.adjustPositionAndSize(this);
        }
    }

    private void setTopPaddingAnimated(int start, int end) {
        if (this.type != Type.TABLET) {
            ValueAnimator paddingAnimator = ValueAnimator.ofInt(new int[]{start, end});
            paddingAnimator.addUpdateListener(new C34954());
            paddingAnimator.setDuration(ANIMATION_DURATION);
            paddingAnimator.start();
        }
    }

    private void animateTitle(float finalScale, float finalAlpha) {
        if (this.type != Type.TABLET) {
            ViewPropertyAnimatorCompat titleAnimator = ViewCompat.animate(this.titleView).setDuration(ANIMATION_DURATION).scaleX(finalScale).scaleY(finalScale);
            titleAnimator.alpha(finalAlpha);
            titleAnimator.start();
        }
    }

    private void animateIcon(float finalAlpha) {
        ViewCompat.animate(this.iconView).setDuration(ANIMATION_DURATION).alpha(finalAlpha).start();
    }

    private void setTopPadding(int topPadding) {
        if (this.type != Type.TABLET) {
            this.iconView.setPadding(this.iconView.getPaddingLeft(), topPadding, this.iconView.getPaddingRight(), this.iconView.getPaddingBottom());
        }
    }

    private void setTitleScale(float scale) {
        if (this.type != Type.TABLET) {
            ViewCompat.setScaleX(this.titleView, scale);
            ViewCompat.setScaleY(this.titleView, scale);
        }
    }

    public Parcelable onSaveInstanceState() {
        if (this.badge == null) {
            return super.onSaveInstanceState();
        }
        Bundle bundle = saveState();
        bundle.putParcelable("superstate", super.onSaveInstanceState());
        return bundle;
    }

    @VisibleForTesting
    Bundle saveState() {
        Bundle outState = new Bundle();
        outState.putInt(STATE_BADGE_COUNT + getIndexInTabContainer(), this.badge.getCount());
        return outState;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            restoreState(bundle);
            state = bundle.getParcelable("superstate");
        }
        super.onRestoreInstanceState(state);
    }

    @VisibleForTesting
    void restoreState(Bundle savedInstanceState) {
        setBadgeCount(savedInstanceState.getInt(STATE_BADGE_COUNT + getIndexInTabContainer()));
    }
}
