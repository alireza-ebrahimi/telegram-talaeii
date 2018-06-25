package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.C0016R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView.ItemView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

@RestrictTo({Scope.LIBRARY_GROUP})
public class BottomNavigationItemView extends FrameLayout implements ItemView {
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    public static final int INVALID_ITEM_POSITION = -1;
    private final int mDefaultMargin;
    private ImageView mIcon;
    private ColorStateList mIconTint;
    private MenuItemImpl mItemData;
    private int mItemPosition;
    private final TextView mLargeLabel;
    private final float mScaleDownFactor;
    private final float mScaleUpFactor;
    private final int mShiftAmount;
    private boolean mShiftingMode;
    private final TextView mSmallLabel;

    public BottomNavigationItemView(@NonNull Context context) {
        this(context, null);
    }

    public BottomNavigationItemView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mItemPosition = -1;
        Resources res = getResources();
        int inactiveLabelSize = res.getDimensionPixelSize(C0016R.dimen.design_bottom_navigation_text_size);
        int activeLabelSize = res.getDimensionPixelSize(C0016R.dimen.design_bottom_navigation_active_text_size);
        this.mDefaultMargin = res.getDimensionPixelSize(C0016R.dimen.design_bottom_navigation_margin);
        this.mShiftAmount = inactiveLabelSize - activeLabelSize;
        this.mScaleUpFactor = (((float) activeLabelSize) * 1.0f) / ((float) inactiveLabelSize);
        this.mScaleDownFactor = (((float) inactiveLabelSize) * 1.0f) / ((float) activeLabelSize);
        LayoutInflater.from(context).inflate(C0016R.layout.design_bottom_navigation_item, this, true);
        setBackgroundResource(C0016R.drawable.design_bottom_navigation_item_background);
        this.mIcon = (ImageView) findViewById(C0016R.id.icon);
        this.mSmallLabel = (TextView) findViewById(C0016R.id.smallLabel);
        this.mLargeLabel = (TextView) findViewById(C0016R.id.largeLabel);
    }

    public void initialize(MenuItemImpl itemData, int menuType) {
        this.mItemData = itemData;
        setCheckable(itemData.isCheckable());
        setChecked(itemData.isChecked());
        setEnabled(itemData.isEnabled());
        setIcon(itemData.getIcon());
        setTitle(itemData.getTitle());
        setId(itemData.getItemId());
    }

    public void setItemPosition(int position) {
        this.mItemPosition = position;
    }

    public int getItemPosition() {
        return this.mItemPosition;
    }

    public void setShiftingMode(boolean enabled) {
        this.mShiftingMode = enabled;
    }

    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    public void setTitle(CharSequence title) {
        this.mSmallLabel.setText(title);
        this.mLargeLabel.setText(title);
        setContentDescription(title);
    }

    public void setCheckable(boolean checkable) {
        refreshDrawableState();
    }

    public void setChecked(boolean checked) {
        ViewCompat.setPivotX(this.mLargeLabel, (float) (this.mLargeLabel.getWidth() / 2));
        ViewCompat.setPivotY(this.mLargeLabel, (float) this.mLargeLabel.getBaseline());
        ViewCompat.setPivotX(this.mSmallLabel, (float) (this.mSmallLabel.getWidth() / 2));
        ViewCompat.setPivotY(this.mSmallLabel, (float) this.mSmallLabel.getBaseline());
        LayoutParams iconParams;
        if (this.mShiftingMode) {
            if (checked) {
                iconParams = (LayoutParams) this.mIcon.getLayoutParams();
                iconParams.gravity = 49;
                iconParams.topMargin = this.mDefaultMargin;
                this.mIcon.setLayoutParams(iconParams);
                this.mLargeLabel.setVisibility(0);
                ViewCompat.setScaleX(this.mLargeLabel, 1.0f);
                ViewCompat.setScaleY(this.mLargeLabel, 1.0f);
            } else {
                iconParams = (LayoutParams) this.mIcon.getLayoutParams();
                iconParams.gravity = 17;
                iconParams.topMargin = this.mDefaultMargin;
                this.mIcon.setLayoutParams(iconParams);
                this.mLargeLabel.setVisibility(4);
                ViewCompat.setScaleX(this.mLargeLabel, 0.5f);
                ViewCompat.setScaleY(this.mLargeLabel, 0.5f);
            }
            this.mSmallLabel.setVisibility(4);
        } else if (checked) {
            iconParams = (LayoutParams) this.mIcon.getLayoutParams();
            iconParams.gravity = 49;
            iconParams.topMargin = this.mDefaultMargin + this.mShiftAmount;
            this.mIcon.setLayoutParams(iconParams);
            this.mLargeLabel.setVisibility(0);
            this.mSmallLabel.setVisibility(4);
            ViewCompat.setScaleX(this.mLargeLabel, 1.0f);
            ViewCompat.setScaleY(this.mLargeLabel, 1.0f);
            ViewCompat.setScaleX(this.mSmallLabel, this.mScaleUpFactor);
            ViewCompat.setScaleY(this.mSmallLabel, this.mScaleUpFactor);
        } else {
            iconParams = (LayoutParams) this.mIcon.getLayoutParams();
            iconParams.gravity = 49;
            iconParams.topMargin = this.mDefaultMargin;
            this.mIcon.setLayoutParams(iconParams);
            this.mLargeLabel.setVisibility(4);
            this.mSmallLabel.setVisibility(0);
            ViewCompat.setScaleX(this.mLargeLabel, this.mScaleDownFactor);
            ViewCompat.setScaleY(this.mLargeLabel, this.mScaleDownFactor);
            ViewCompat.setScaleX(this.mSmallLabel, 1.0f);
            ViewCompat.setScaleY(this.mSmallLabel, 1.0f);
        }
        refreshDrawableState();
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.mSmallLabel.setEnabled(enabled);
        this.mLargeLabel.setEnabled(enabled);
        this.mIcon.setEnabled(enabled);
        if (enabled) {
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), 1002));
        } else {
            ViewCompat.setPointerIcon(this, null);
        }
    }

    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (this.mItemData != null && this.mItemData.isCheckable() && this.mItemData.isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    public void setShortcut(boolean showShortcut, char shortcutKey) {
    }

    public void setIcon(Drawable icon) {
        if (icon != null) {
            ConstantState state = icon.getConstantState();
            if (state != null) {
                icon = state.newDrawable();
            }
            icon = DrawableCompat.wrap(icon).mutate();
            DrawableCompat.setTintList(icon, this.mIconTint);
        }
        this.mIcon.setImageDrawable(icon);
    }

    public boolean prefersCondensedTitle() {
        return false;
    }

    public boolean showsIcon() {
        return true;
    }

    public void setIconTintList(ColorStateList tint) {
        this.mIconTint = tint;
        if (this.mItemData != null) {
            setIcon(this.mItemData.getIcon());
        }
    }

    public void setTextColor(ColorStateList color) {
        this.mSmallLabel.setTextColor(color);
        this.mLargeLabel.setTextColor(color);
    }

    public void setItemBackground(int background) {
        Drawable backgroundDrawable;
        if (background == 0) {
            backgroundDrawable = null;
        } else {
            backgroundDrawable = ContextCompat.getDrawable(getContext(), background);
        }
        ViewCompat.setBackground(this, backgroundDrawable);
    }
}
