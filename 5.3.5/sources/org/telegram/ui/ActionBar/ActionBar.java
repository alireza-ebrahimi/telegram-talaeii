package org.telegram.ui.ActionBar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.Components.LayoutHelper;

public class ActionBar extends FrameLayout {
    public int actionBarFontSize;
    public ActionBarMenuOnItemClick actionBarMenuOnItemClick;
    private ActionBarMenu actionMode;
    private AnimatorSet actionModeAnimation;
    private View actionModeTop;
    private boolean actionModeVisible;
    private boolean addToContainer;
    private boolean allowOverlayTitle;
    private ImageView backButtonImageView;
    private boolean castShadows;
    private int extraHeight;
    private boolean interceptTouches;
    private boolean isBackOverlayVisible;
    protected boolean isSearchFieldVisible;
    protected int itemsActionModeBackgroundColor;
    protected int itemsActionModeColor;
    protected int itemsBackgroundColor;
    protected int itemsColor;
    private CharSequence lastSubtitle;
    private CharSequence lastTitle;
    private ActionBarMenu menu;
    private boolean occupyStatusBar;
    protected BaseFragment parentFragment;
    public String searchTabTitle;
    private SimpleTextView subtitleTextView;
    private Runnable titleActionRunnable;
    private int titleRightMargin;
    private SimpleTextView titleTextView;

    public static class ActionBarMenuOnItemClick {
        public void onItemClick(int id) {
        }

        public boolean canOpenMenu() {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBar$1 */
    class C19551 implements OnClickListener {
        C19551() {
        }

        public void onClick(View v) {
            if (ActionBar.this.titleActionRunnable != null) {
                ActionBar.this.titleActionRunnable.run();
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBar$2 */
    class C19562 implements OnClickListener {
        C19562() {
        }

        public void onClick(View v) {
            if (!ActionBar.this.actionModeVisible && ActionBar.this.isSearchFieldVisible) {
                ActionBar.this.closeSearchField();
            } else if (ActionBar.this.actionBarMenuOnItemClick != null) {
                ActionBar.this.actionBarMenuOnItemClick.onItemClick(-1);
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBar$3 */
    class C19573 extends AnimatorListenerAdapter {
        C19573() {
        }

        public void onAnimationStart(Animator animation) {
            ActionBar.this.actionMode.setVisibility(0);
            if (ActionBar.this.occupyStatusBar && ActionBar.this.actionModeTop != null) {
                ActionBar.this.actionModeTop.setVisibility(0);
            }
        }

        public void onAnimationEnd(Animator animation) {
            if (ActionBar.this.actionModeAnimation != null && ActionBar.this.actionModeAnimation.equals(animation)) {
                ActionBar.this.actionModeAnimation = null;
                if (ActionBar.this.titleTextView != null) {
                    ActionBar.this.titleTextView.setVisibility(4);
                }
                if (ActionBar.this.subtitleTextView != null) {
                    ActionBar.this.subtitleTextView.setVisibility(4);
                }
                if (ActionBar.this.menu != null) {
                    ActionBar.this.menu.setVisibility(4);
                }
            }
        }

        public void onAnimationCancel(Animator animation) {
            if (ActionBar.this.actionModeAnimation != null && ActionBar.this.actionModeAnimation.equals(animation)) {
                ActionBar.this.actionModeAnimation = null;
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBar$4 */
    class C19584 extends AnimatorListenerAdapter {
        C19584() {
        }

        public void onAnimationEnd(Animator animation) {
            if (ActionBar.this.actionModeAnimation != null && ActionBar.this.actionModeAnimation.equals(animation)) {
                ActionBar.this.actionModeAnimation = null;
                ActionBar.this.actionMode.setVisibility(4);
                if (ActionBar.this.occupyStatusBar && ActionBar.this.actionModeTop != null) {
                    ActionBar.this.actionModeTop.setVisibility(4);
                }
            }
        }

        public void onAnimationCancel(Animator animation) {
            if (ActionBar.this.actionModeAnimation != null && ActionBar.this.actionModeAnimation.equals(animation)) {
                ActionBar.this.actionModeAnimation = null;
            }
        }
    }

    public ActionBar(Context context) {
        super(context);
        this.occupyStatusBar = VERSION.SDK_INT >= 21;
        this.addToContainer = true;
        this.interceptTouches = true;
        this.castShadows = true;
        this.actionBarFontSize = -1;
        this.searchTabTitle = "جستجو";
        setOnClickListener(new C19551());
    }

    private void createBackButtonImage() {
        if (this.backButtonImageView == null) {
            this.backButtonImageView = new ImageView(getContext());
            this.backButtonImageView.setScaleType(ScaleType.CENTER);
            this.backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(this.itemsBackgroundColor));
            if (this.itemsColor != 0) {
                this.backButtonImageView.setColorFilter(new PorterDuffColorFilter(this.itemsColor, Mode.MULTIPLY));
            }
            this.backButtonImageView.setPadding(AndroidUtilities.dp(1.0f), 0, 0, 0);
            addView(this.backButtonImageView, LayoutHelper.createFrame(54, 54, 51));
            this.backButtonImageView.setOnClickListener(new C19562());
        }
    }

    public void setBackButtonDrawable(Drawable drawable) {
        if (this.backButtonImageView == null) {
            createBackButtonImage();
        }
        this.backButtonImageView.setVisibility(drawable == null ? 8 : 0);
        this.backButtonImageView.setImageDrawable(drawable);
        if (drawable instanceof BackDrawable) {
            BackDrawable backDrawable = (BackDrawable) drawable;
            backDrawable.setRotation(isActionModeShowed() ? 1.0f : 0.0f, false);
            backDrawable.setRotatedColor(this.itemsActionModeColor);
            backDrawable.setColor(this.itemsColor);
        }
    }

    public void setBackButtonImage(int resource) {
        if (this.backButtonImageView == null) {
            createBackButtonImage();
        }
        this.backButtonImageView.setVisibility(resource == 0 ? 8 : 0);
        this.backButtonImageView.setImageResource(resource);
    }

    private void createSubtitleTextView() {
        if (this.subtitleTextView == null) {
            this.subtitleTextView = new SimpleTextView(getContext());
            this.subtitleTextView.setGravity(3);
            this.subtitleTextView.setVisibility(8);
            this.subtitleTextView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubtitle));
            addView(this.subtitleTextView, 0, LayoutHelper.createFrame(-2, -2, 51));
        }
    }

    public void setAddToContainer(boolean value) {
        this.addToContainer = value;
    }

    public boolean getAddToContainer() {
        return this.addToContainer;
    }

    public void setSubtitle(CharSequence value) {
        if (value != null && this.subtitleTextView == null) {
            createSubtitleTextView();
        }
        if (this.subtitleTextView != null) {
            this.lastSubtitle = value;
            SimpleTextView simpleTextView = this.subtitleTextView;
            int i = (TextUtils.isEmpty(value) || this.isSearchFieldVisible) ? 8 : 0;
            simpleTextView.setVisibility(i);
            this.subtitleTextView.setText(value);
        }
    }

    private void createTitleTextView() {
        if (this.titleTextView == null) {
            this.titleTextView = new SimpleTextView(getContext());
            this.titleTextView.setGravity(3);
            this.titleTextView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultTitle));
            this.titleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            addView(this.titleTextView, 0, LayoutHelper.createFrame(-2, -2, 51));
        }
    }

    public void setTitleRightMargin(int value) {
        this.titleRightMargin = value;
    }

    public void setTitle(CharSequence value) {
        if (value != null && this.titleTextView == null) {
            createTitleTextView();
        }
        if (this.titleTextView != null) {
            this.lastTitle = value;
            SimpleTextView simpleTextView = this.titleTextView;
            int i = (value == null || this.isSearchFieldVisible) ? 4 : 0;
            simpleTextView.setVisibility(i);
            this.titleTextView.setText(value);
        }
    }

    public void setTitleColor(int color) {
        if (this.titleTextView == null) {
            createTitleTextView();
        }
        this.titleTextView.setTextColor(color);
    }

    public void setSubtitleColor(int color) {
        if (this.subtitleTextView == null) {
            createSubtitleTextView();
        }
        this.subtitleTextView.setTextColor(color);
    }

    public void setPopupItemsColor(int color) {
        if (this.menu != null) {
            this.menu.setPopupItemsColor(color);
        }
    }

    public void setPopupBackgroundColor(int color) {
        if (this.menu != null) {
            this.menu.redrawPopup(color);
        }
    }

    public SimpleTextView getSubtitleTextView() {
        return this.subtitleTextView;
    }

    public SimpleTextView getTitleTextView() {
        return this.titleTextView;
    }

    public String getTitle() {
        if (this.titleTextView == null) {
            return null;
        }
        return this.titleTextView.getText().toString();
    }

    public String getSubtitle() {
        if (this.subtitleTextView == null) {
            return null;
        }
        return this.subtitleTextView.getText().toString();
    }

    public ActionBarMenu createMenu() {
        if (this.menu != null) {
            return this.menu;
        }
        this.menu = new ActionBarMenu(getContext(), this);
        addView(this.menu, 0, LayoutHelper.createFrame(-2, -1, 5));
        return this.menu;
    }

    public void setActionBarMenuOnItemClick(ActionBarMenuOnItemClick listener) {
        this.actionBarMenuOnItemClick = listener;
    }

    public ActionBarMenuOnItemClick getActionBarMenuOnItemClick() {
        return this.actionBarMenuOnItemClick;
    }

    public View getBackButton() {
        return this.backButtonImageView;
    }

    public ActionBarMenu createActionMode() {
        if (this.actionMode != null) {
            return this.actionMode;
        }
        int i;
        this.actionMode = new ActionBarMenu(getContext(), this);
        this.actionMode.isActionMode = true;
        this.actionMode.setBackgroundColor(Theme.getColor(Theme.key_actionBarActionModeDefault));
        addView(this.actionMode, indexOfChild(this.backButtonImageView));
        ActionBarMenu actionBarMenu = this.actionMode;
        if (this.occupyStatusBar) {
            i = AndroidUtilities.statusBarHeight;
        } else {
            i = 0;
        }
        actionBarMenu.setPadding(0, i, 0, 0);
        LayoutParams layoutParams = (LayoutParams) this.actionMode.getLayoutParams();
        layoutParams.height = -1;
        layoutParams.width = -1;
        layoutParams.gravity = 5;
        this.actionMode.setLayoutParams(layoutParams);
        this.actionMode.setVisibility(4);
        if (this.occupyStatusBar && this.actionModeTop == null) {
            this.actionModeTop = new View(getContext());
            this.actionModeTop.setBackgroundColor(Theme.getColor(Theme.key_actionBarActionModeDefaultTop));
            addView(this.actionModeTop);
            layoutParams = (LayoutParams) this.actionModeTop.getLayoutParams();
            layoutParams.height = AndroidUtilities.statusBarHeight;
            layoutParams.width = -1;
            layoutParams.gravity = 51;
            this.actionModeTop.setLayoutParams(layoutParams);
            this.actionModeTop.setVisibility(4);
        }
        return this.actionMode;
    }

    public void showActionMode() {
        if (this.actionMode != null && !this.actionModeVisible) {
            this.actionModeVisible = true;
            ArrayList<Animator> animators = new ArrayList();
            animators.add(ObjectAnimator.ofFloat(this.actionMode, "alpha", new float[]{0.0f, 1.0f}));
            if (this.occupyStatusBar && this.actionModeTop != null) {
                animators.add(ObjectAnimator.ofFloat(this.actionModeTop, "alpha", new float[]{0.0f, 1.0f}));
            }
            if (this.actionModeAnimation != null) {
                this.actionModeAnimation.cancel();
            }
            this.actionModeAnimation = new AnimatorSet();
            this.actionModeAnimation.playTogether(animators);
            this.actionModeAnimation.setDuration(200);
            this.actionModeAnimation.addListener(new C19573());
            this.actionModeAnimation.start();
            if (this.backButtonImageView != null) {
                Drawable drawable = this.backButtonImageView.getDrawable();
                if (drawable instanceof BackDrawable) {
                    ((BackDrawable) drawable).setRotation(1.0f, true);
                }
                this.backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(this.itemsActionModeBackgroundColor));
            }
        }
    }

    public void hideActionMode() {
        if (this.actionMode != null && this.actionModeVisible) {
            this.actionModeVisible = false;
            ArrayList<Animator> animators = new ArrayList();
            animators.add(ObjectAnimator.ofFloat(this.actionMode, "alpha", new float[]{0.0f}));
            if (this.occupyStatusBar && this.actionModeTop != null) {
                animators.add(ObjectAnimator.ofFloat(this.actionModeTop, "alpha", new float[]{0.0f}));
            }
            if (this.actionModeAnimation != null) {
                this.actionModeAnimation.cancel();
            }
            this.actionModeAnimation = new AnimatorSet();
            this.actionModeAnimation.playTogether(animators);
            this.actionModeAnimation.setDuration(200);
            this.actionModeAnimation.addListener(new C19584());
            this.actionModeAnimation.start();
            if (this.titleTextView != null) {
                this.titleTextView.setVisibility(0);
            }
            if (this.subtitleTextView != null) {
                this.subtitleTextView.setVisibility(0);
            }
            if (this.menu != null) {
                this.menu.setVisibility(0);
            }
            if (this.backButtonImageView != null) {
                Drawable drawable = this.backButtonImageView.getDrawable();
                if (drawable instanceof BackDrawable) {
                    ((BackDrawable) drawable).setRotation(0.0f, true);
                }
                this.backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(this.itemsBackgroundColor));
            }
        }
    }

    public void showActionModeTop() {
        if (this.occupyStatusBar && this.actionModeTop == null) {
            this.actionModeTop = new View(getContext());
            this.actionModeTop.setBackgroundColor(Theme.getColor(Theme.key_actionBarActionModeDefaultTop));
            addView(this.actionModeTop);
            LayoutParams layoutParams = (LayoutParams) this.actionModeTop.getLayoutParams();
            layoutParams.height = AndroidUtilities.statusBarHeight;
            layoutParams.width = -1;
            layoutParams.gravity = 51;
            this.actionModeTop.setLayoutParams(layoutParams);
        }
    }

    public void setActionModeTopColor(int color) {
        if (this.actionModeTop != null) {
            this.actionModeTop.setBackgroundColor(color);
        }
    }

    public void setSearchTextColor(int color, boolean placeholder) {
        if (this.menu != null) {
            this.menu.setSearchTextColor(color, placeholder);
        }
    }

    public void setActionModeColor(int color) {
        if (this.actionMode != null) {
            this.actionMode.setBackgroundColor(color);
        }
    }

    public boolean isActionModeShowed() {
        return this.actionMode != null && this.actionModeVisible;
    }

    protected void onSearchFieldVisibilityChanged(boolean visible) {
        int i = 4;
        this.isSearchFieldVisible = visible;
        if (this.titleTextView != null) {
            this.titleTextView.setVisibility(visible ? 4 : 0);
        }
        if (this.subtitleTextView != null) {
            SimpleTextView simpleTextView = this.subtitleTextView;
            if (!visible) {
                i = 0;
            }
            simpleTextView.setVisibility(i);
        }
        Drawable drawable = this.backButtonImageView.getDrawable();
        if (drawable != null && (drawable instanceof MenuDrawable)) {
            ((MenuDrawable) drawable).setRotation(visible ? 1.0f : 0.0f, true);
        }
    }

    public void setInterceptTouches(boolean value) {
        this.interceptTouches = value;
    }

    public void setExtraHeight(int value) {
        this.extraHeight = value;
    }

    public void closeSearchField() {
        closeSearchField(true);
    }

    public void closeSearchField(boolean closeKeyboard) {
        if (this.isSearchFieldVisible && this.menu != null) {
            this.menu.closeSearchField(closeKeyboard);
        }
    }

    public void openSearchField(String text) {
        if (this.menu != null && text != null) {
            this.menu.openSearchField(!this.isSearchFieldVisible, text);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int textLeft;
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int actionBarHeight = getCurrentActionBarHeight();
        int actionBarHeightSpec = MeasureSpec.makeMeasureSpec(actionBarHeight, 1073741824);
        setMeasuredDimension(width, ((this.occupyStatusBar ? AndroidUtilities.statusBarHeight : 0) + actionBarHeight) + this.extraHeight);
        if (this.backButtonImageView == null || this.backButtonImageView.getVisibility() == 8) {
            textLeft = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 26.0f : 18.0f);
        } else {
            this.backButtonImageView.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(54.0f), 1073741824), actionBarHeightSpec);
            textLeft = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 80.0f : 72.0f);
        }
        if (!(this.menu == null || this.menu.getVisibility() == 8)) {
            int menuWidth;
            if (this.isSearchFieldVisible) {
                menuWidth = MeasureSpec.makeMeasureSpec(width - AndroidUtilities.dp(AndroidUtilities.isTablet() ? 74.0f : 66.0f), 1073741824);
            } else {
                menuWidth = MeasureSpec.makeMeasureSpec(width, Integer.MIN_VALUE);
            }
            this.menu.measure(menuWidth, actionBarHeightSpec);
        }
        if (!((this.titleTextView == null || this.titleTextView.getVisibility() == 8) && (this.subtitleTextView == null || this.subtitleTextView.getVisibility() == 8))) {
            SimpleTextView simpleTextView;
            int i;
            int availableWidth = (((width - (this.menu != null ? this.menu.getMeasuredWidth() : 0)) - AndroidUtilities.dp(16.0f)) - textLeft) - this.titleRightMargin;
            if (!(this.titleTextView == null || this.titleTextView.getVisibility() == 8)) {
                if (this.actionBarFontSize > 2) {
                    simpleTextView = this.titleTextView;
                    i = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? this.actionBarFontSize : this.actionBarFontSize - 2;
                    simpleTextView.setTextSize(i);
                } else {
                    simpleTextView = this.titleTextView;
                    i = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 18 : 16;
                    simpleTextView.setTextSize(i);
                }
                this.titleTextView.measure(MeasureSpec.makeMeasureSpec(availableWidth, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24.0f), Integer.MIN_VALUE));
            }
            if (!(this.subtitleTextView == null || this.subtitleTextView.getVisibility() == 8)) {
                simpleTextView = this.subtitleTextView;
                i = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 14 : 12;
                simpleTextView.setTextSize(i);
                this.subtitleTextView.measure(MeasureSpec.makeMeasureSpec(availableWidth, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), Integer.MIN_VALUE));
            }
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View child = getChildAt(i2);
            if (!(child.getVisibility() == 8 || child == this.titleTextView || child == this.subtitleTextView || child == this.menu || child == this.backButtonImageView)) {
                measureChildWithMargins(child, widthMeasureSpec, 0, MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824), 0);
            }
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int textLeft;
        int textTop;
        int additionalTop = this.occupyStatusBar ? AndroidUtilities.statusBarHeight : 0;
        if (this.backButtonImageView == null || this.backButtonImageView.getVisibility() == 8) {
            textLeft = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 26.0f : 18.0f);
        } else {
            this.backButtonImageView.layout(0, additionalTop, this.backButtonImageView.getMeasuredWidth(), this.backButtonImageView.getMeasuredHeight() + additionalTop);
            textLeft = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 80.0f : 72.0f);
        }
        if (!(this.menu == null || this.menu.getVisibility() == 8)) {
            int menuLeft;
            if (this.isSearchFieldVisible) {
                menuLeft = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 74.0f : 66.0f);
            } else {
                menuLeft = (right - left) - this.menu.getMeasuredWidth();
            }
            this.menu.layout(menuLeft, additionalTop, this.menu.getMeasuredWidth() + menuLeft, this.menu.getMeasuredHeight() + additionalTop);
        }
        if (!(this.titleTextView == null || this.titleTextView.getVisibility() == 8)) {
            if (this.subtitleTextView == null || this.subtitleTextView.getVisibility() == 8) {
                textTop = (getCurrentActionBarHeight() - this.titleTextView.getTextHeight()) / 2;
            } else {
                int currentActionBarHeight = ((getCurrentActionBarHeight() / 2) - this.titleTextView.getTextHeight()) / 2;
                float f = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 3.0f : 2.0f;
                textTop = currentActionBarHeight + AndroidUtilities.dp(f);
            }
            this.titleTextView.layout(textLeft, additionalTop + textTop, this.titleTextView.getMeasuredWidth() + textLeft, (additionalTop + textTop) + this.titleTextView.getTextHeight());
        }
        if (!(this.subtitleTextView == null || this.subtitleTextView.getVisibility() == 8)) {
            currentActionBarHeight = (((getCurrentActionBarHeight() / 2) - this.subtitleTextView.getTextHeight()) / 2) + (getCurrentActionBarHeight() / 2);
            f = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 1.0f : 1.0f;
            textTop = currentActionBarHeight - AndroidUtilities.dp(f);
            this.subtitleTextView.layout(textLeft, additionalTop + textTop, this.subtitleTextView.getMeasuredWidth() + textLeft, (additionalTop + textTop) + this.subtitleTextView.getTextHeight());
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (!(child.getVisibility() == 8 || child == this.titleTextView || child == this.subtitleTextView || child == this.menu || child == this.backButtonImageView)) {
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
                child.layout(childLeft, childTop, childLeft + width, childTop + height);
            }
        }
    }

    public void onMenuButtonPressed() {
        if (this.menu != null) {
            this.menu.onMenuButtonPressed();
        }
    }

    protected void onPause() {
        if (this.menu != null) {
            this.menu.hideAllPopupMenus();
        }
    }

    public void setAllowOverlayTitle(boolean value) {
        this.allowOverlayTitle = value;
    }

    public void setTitleOverlayText(String title, String subtitle, Runnable action) {
        int i = 0;
        if (this.allowOverlayTitle && this.parentFragment.parentLayout != null) {
            CharSequence textToSet = title != null ? title : this.lastTitle;
            if (textToSet != null && this.titleTextView == null) {
                createTitleTextView();
            }
            if (this.titleTextView != null) {
                SimpleTextView simpleTextView = this.titleTextView;
                int i2 = (textToSet == null || this.isSearchFieldVisible) ? 4 : 0;
                simpleTextView.setVisibility(i2);
                this.titleTextView.setText(textToSet);
            }
            textToSet = subtitle != null ? subtitle : this.lastSubtitle;
            if (textToSet != null && this.subtitleTextView == null) {
                createSubtitleTextView();
            }
            if (this.subtitleTextView != null) {
                SimpleTextView simpleTextView2 = this.subtitleTextView;
                if (TextUtils.isEmpty(textToSet) || this.isSearchFieldVisible) {
                    i = 8;
                }
                simpleTextView2.setVisibility(i);
                this.subtitleTextView.setText(textToSet);
            }
            this.titleActionRunnable = action;
        }
    }

    public boolean isSearchFieldVisible() {
        return this.isSearchFieldVisible;
    }

    public void setOccupyStatusBar(boolean value) {
        this.occupyStatusBar = value;
        if (this.actionMode != null) {
            int i;
            ActionBarMenu actionBarMenu = this.actionMode;
            if (this.occupyStatusBar) {
                i = AndroidUtilities.statusBarHeight;
            } else {
                i = 0;
            }
            actionBarMenu.setPadding(0, i, 0, 0);
        }
    }

    public boolean getOccupyStatusBar() {
        return this.occupyStatusBar;
    }

    public void setItemsBackgroundColor(int color, boolean isActionMode) {
        if (isActionMode) {
            this.itemsActionModeBackgroundColor = color;
            if (this.actionModeVisible && this.backButtonImageView != null) {
                this.backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(this.itemsActionModeBackgroundColor));
            }
            if (this.actionMode != null) {
                this.actionMode.updateItemsBackgroundColor();
                return;
            }
            return;
        }
        this.itemsBackgroundColor = color;
        if (this.backButtonImageView != null) {
            this.backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(this.itemsBackgroundColor));
        }
        if (this.menu != null) {
            this.menu.updateItemsBackgroundColor();
        }
    }

    public void setItemsColor(int color, boolean isActionMode) {
        Drawable drawable;
        if (isActionMode) {
            this.itemsActionModeColor = color;
            if (this.actionMode != null) {
                this.actionMode.updateItemsColor();
            }
            if (this.backButtonImageView != null) {
                drawable = this.backButtonImageView.getDrawable();
                if (drawable instanceof BackDrawable) {
                    ((BackDrawable) drawable).setRotatedColor(color);
                    return;
                }
                return;
            }
            return;
        }
        this.itemsColor = color;
        if (!(this.backButtonImageView == null || this.itemsColor == 0)) {
            this.backButtonImageView.setColorFilter(new PorterDuffColorFilter(this.itemsColor, Mode.MULTIPLY));
            drawable = this.backButtonImageView.getDrawable();
            if (drawable instanceof BackDrawable) {
                ((BackDrawable) drawable).setColor(color);
            }
        }
        if (this.menu != null) {
            this.menu.updateItemsColor();
        }
    }

    public void setCastShadows(boolean value) {
        this.castShadows = value;
    }

    public boolean getCastShadows() {
        return this.castShadows;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event) || this.interceptTouches;
    }

    public static int getCurrentActionBarHeight() {
        if (AndroidUtilities.isTablet()) {
            return AndroidUtilities.dp(64.0f);
        }
        if (AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y) {
            return AndroidUtilities.dp(48.0f);
        }
        return AndroidUtilities.dp(56.0f);
    }

    public boolean hasOverlappingRendering() {
        return false;
    }
}
