package utils.view.bottombar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import org.ir.talaeii.R;

class BottomBarBadge extends TextView {
    private int count;
    private boolean isVisible = false;

    BottomBarBadge(Context context) {
        super(context);
    }

    void setCount(int count) {
        this.count = count;
        setText(String.valueOf(count));
    }

    int getCount() {
        return this.count;
    }

    void show() {
        this.isVisible = true;
        ViewCompat.animate(this).setDuration(150).alpha(1.0f).scaleX(1.0f).scaleY(1.0f).start();
    }

    void hide() {
        this.isVisible = false;
        ViewCompat.animate(this).setDuration(150).alpha(0.0f).scaleX(0.0f).scaleY(0.0f).start();
    }

    boolean isVisible() {
        return this.isVisible;
    }

    void attachToTab(BottomBarTab tab, int backgroundColor) {
        setLayoutParams(new LayoutParams(-2, -2));
        setGravity(17);
        MiscUtils.setTextAppearance(this, R.style.BB_BottomBarBadge_Text);
        setColoredCircleBackground(backgroundColor);
        wrapTabAndBadgeInSameContainer(tab);
    }

    void setColoredCircleBackground(int circleColor) {
        int innerPadding = MiscUtils.dpToPixel(getContext(), 1.0f);
        ShapeDrawable backgroundCircle = BadgeCircle.make(innerPadding * 3, circleColor);
        setPadding(innerPadding, innerPadding, innerPadding, innerPadding);
        setBackgroundCompat(backgroundCircle);
    }

    private void wrapTabAndBadgeInSameContainer(final BottomBarTab tab) {
        ViewGroup tabContainer = (ViewGroup) tab.getParent();
        tabContainer.removeView(tab);
        final BadgeContainer badgeContainer = new BadgeContainer(getContext());
        badgeContainer.setLayoutParams(new LayoutParams(-2, -2));
        badgeContainer.addView(tab);
        badgeContainer.addView(this);
        tabContainer.addView(badgeContainer, tab.getIndexInTabContainer());
        badgeContainer.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                badgeContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                BottomBarBadge.this.adjustPositionAndSize(tab);
            }
        });
    }

    void removeFromTab(BottomBarTab tab) {
        BadgeContainer badgeAndTabContainer = (BadgeContainer) getParent();
        ViewGroup originalTabContainer = (ViewGroup) badgeAndTabContainer.getParent();
        badgeAndTabContainer.removeView(tab);
        originalTabContainer.removeView(badgeAndTabContainer);
        originalTabContainer.addView(tab, tab.getIndexInTabContainer());
    }

    void adjustPositionAndSize(BottomBarTab tab) {
        AppCompatImageView iconView = tab.getIconView();
        LayoutParams params = getLayoutParams();
        int size = Math.max(getWidth(), getHeight());
        setX(iconView.getX() + ((float) (((double) iconView.getWidth()) / 1.25d)));
        setTranslationY(10.0f);
        if (params.width != size || params.height != size) {
            params.width = size;
            params.height = size;
            setLayoutParams(params);
        }
    }

    private void setBackgroundCompat(Drawable background) {
        if (VERSION.SDK_INT >= 16) {
            setBackground(background);
        } else {
            setBackgroundDrawable(background);
        }
    }
}
