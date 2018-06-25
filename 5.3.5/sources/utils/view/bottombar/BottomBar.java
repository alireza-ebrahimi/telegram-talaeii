package utils.view.bottombar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.XmlRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.messenger.C0906R;
import org.telegram.ui.ActionBar.Theme;
import utils.view.bottombar.BottomBarTab.Config;
import utils.view.bottombar.BottomBarTab.Config.Builder;

public class BottomBar extends LinearLayout implements OnClickListener, OnLongClickListener {
    private static final int BEHAVIOR_DRAW_UNDER_NAV = 4;
    private static final int BEHAVIOR_NONE = 0;
    private static final int BEHAVIOR_SHIFTING = 1;
    private static final int BEHAVIOR_SHY = 2;
    private static final float DEFAULT_INACTIVE_SHIFTING_TAB_ALPHA = 0.6f;
    private static final String STATE_CURRENT_SELECTED_TAB = "STATE_CURRENT_SELECTED_TAB";
    private int activeShiftingItemWidth;
    private float activeTabAlpha;
    private int activeTabColor;
    private View backgroundOverlay;
    private int badgeBackgroundColor;
    private BatchTabPropertyApplier batchPropertyApplier;
    private int behaviors;
    private int currentBackgroundColor;
    private int currentTabPosition;
    private BottomBarTab[] currentTabs;
    private int defaultBackgroundColor = Theme.getColor(Theme.key_actionBarDefault);
    private boolean ignoreTabReselectionListener;
    private int inActiveShiftingItemWidth;
    private float inActiveTabAlpha;
    private int inActiveTabColor;
    private boolean isComingFromRestoredState;
    private boolean isTabletMode;
    private int maxFixedItemWidth;
    private boolean navBarAccountedHeightCalculated;
    private OnTabReselectListener onTabReselectListener;
    private OnTabSelectListener onTabSelectListener;
    private ViewGroup outerContainer;
    private int primaryColor;
    private int screenWidth;
    private View shadowView;
    private boolean showShadow;
    private boolean shyHeightAlreadyCalculated;
    private ViewGroup tabContainer;
    private int tabXmlResource;
    private int tenDp;
    private int titleTextAppearance;
    private Typeface titleTypeFace;

    /* renamed from: utils.view.bottombar.BottomBar$1 */
    class C34821 implements TabPropertyUpdater {
        C34821() {
        }

        public void update(BottomBarTab tab) {
            tab.setInActiveAlpha(BottomBar.this.inActiveTabAlpha);
        }
    }

    /* renamed from: utils.view.bottombar.BottomBar$2 */
    class C34832 implements TabPropertyUpdater {
        C34832() {
        }

        public void update(BottomBarTab tab) {
            tab.setActiveAlpha(BottomBar.this.activeTabAlpha);
        }
    }

    /* renamed from: utils.view.bottombar.BottomBar$3 */
    class C34843 implements TabPropertyUpdater {
        C34843() {
        }

        public void update(BottomBarTab tab) {
            tab.setInActiveColor(BottomBar.this.inActiveTabColor);
        }
    }

    /* renamed from: utils.view.bottombar.BottomBar$4 */
    class C34854 implements TabPropertyUpdater {
        C34854() {
        }

        public void update(BottomBarTab tab) {
            tab.setActiveColor(BottomBar.this.activeTabColor);
        }
    }

    /* renamed from: utils.view.bottombar.BottomBar$5 */
    class C34865 implements TabPropertyUpdater {
        C34865() {
        }

        public void update(BottomBarTab tab) {
            tab.setBadgeBackgroundColor(BottomBar.this.badgeBackgroundColor);
        }
    }

    /* renamed from: utils.view.bottombar.BottomBar$6 */
    class C34876 implements TabPropertyUpdater {
        C34876() {
        }

        public void update(BottomBarTab tab) {
            tab.setTitleTextAppearance(BottomBar.this.titleTextAppearance);
        }
    }

    /* renamed from: utils.view.bottombar.BottomBar$7 */
    class C34887 implements TabPropertyUpdater {
        C34887() {
        }

        public void update(BottomBarTab tab) {
            tab.setTitleTypeface(BottomBar.this.titleTypeFace);
        }
    }

    public BottomBar(Context context) {
        super(context);
        init(context, null);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.batchPropertyApplier = new BatchTabPropertyApplier(this);
        populateAttributes(context, attrs);
        initializeViews();
        determineInitialBackgroundColor();
        if (this.tabXmlResource != 0) {
            setItems(this.tabXmlResource);
        }
    }

    private void populateAttributes(Context context, AttributeSet attrs) {
        int defaultActiveColor = -1;
        float f = 1.0f;
        this.primaryColor = Theme.getColor(Theme.key_actionBarDefault);
        this.screenWidth = MiscUtils.getScreenWidth(getContext());
        this.tenDp = MiscUtils.dpToPixel(getContext(), 10.0f);
        this.maxFixedItemWidth = MiscUtils.dpToPixel(getContext(), 168.0f);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, C0906R.styleable.BottomBar, 0, 0);
        try {
            int defaultInActiveColor;
            this.tabXmlResource = ta.getResourceId(0, 0);
            this.isTabletMode = ta.getBoolean(1, false);
            this.behaviors = ta.getInteger(2, 0);
            if (isShiftingMode()) {
                f = DEFAULT_INACTIVE_SHIFTING_TAB_ALPHA;
            }
            this.inActiveTabAlpha = ta.getFloat(3, f);
            this.activeTabAlpha = ta.getFloat(4, 1.0f);
            if (isShiftingMode()) {
                defaultInActiveColor = -1;
            } else {
                defaultInActiveColor = ContextCompat.getColor(context, R.color.bb_inActiveBottomBarItemColor);
            }
            if (!isShiftingMode()) {
                defaultActiveColor = this.primaryColor;
            }
            this.inActiveTabColor = ta.getColor(5, defaultInActiveColor);
            this.activeTabColor = ta.getColor(6, defaultActiveColor);
            this.badgeBackgroundColor = ta.getColor(7, SupportMenu.CATEGORY_MASK);
            this.titleTextAppearance = ta.getResourceId(8, 0);
            this.titleTypeFace = getTypeFaceFromAsset(ta.getString(9));
            this.showShadow = ta.getBoolean(10, true);
        } finally {
            ta.recycle();
        }
    }

    private boolean isShiftingMode() {
        return !this.isTabletMode && hasBehavior(1);
    }

    private boolean drawUnderNav() {
        return !this.isTabletMode && hasBehavior(4) && NavbarUtils.shouldDrawBehindNavbar(getContext());
    }

    private boolean isShy() {
        return !this.isTabletMode && hasBehavior(2);
    }

    private boolean hasBehavior(int behavior) {
        return (this.behaviors | behavior) == this.behaviors;
    }

    private Typeface getTypeFaceFromAsset(String fontPath) {
        if (fontPath != null) {
            return Typeface.createFromAsset(getContext().getAssets(), fontPath);
        }
        return null;
    }

    private void initializeViews() {
        int width;
        int height;
        if (this.isTabletMode) {
            width = -2;
        } else {
            width = -1;
        }
        if (this.isTabletMode) {
            height = -1;
        } else {
            height = -2;
        }
        LayoutParams params = new LayoutParams(width, height);
        setLayoutParams(params);
        setOrientation(this.isTabletMode ? 0 : 1);
        ViewCompat.setElevation(this, (float) MiscUtils.dpToPixel(getContext(), 8.0f));
        View rootView = inflate(getContext(), this.isTabletMode ? R.layout.bb_bottom_bar_item_container_tablet : R.layout.bb_bottom_bar_item_container, this);
        rootView.setLayoutParams(params);
        this.backgroundOverlay = rootView.findViewById(R.id.bb_bottom_bar_background_overlay);
        this.outerContainer = (ViewGroup) rootView.findViewById(R.id.bb_bottom_bar_outer_container);
        this.tabContainer = (ViewGroup) rootView.findViewById(R.id.bb_bottom_bar_item_container);
        this.shadowView = rootView.findViewById(R.id.bb_bottom_bar_shadow);
        if (!this.showShadow) {
            this.shadowView.setVisibility(8);
        }
    }

    private void determineInitialBackgroundColor() {
        boolean userHasDefinedBackgroundColor;
        if (isShiftingMode()) {
            this.defaultBackgroundColor = this.primaryColor;
        }
        Drawable userDefinedBackground = getBackground();
        if (userDefinedBackground == null || !(userDefinedBackground instanceof ColorDrawable)) {
            userHasDefinedBackgroundColor = false;
        } else {
            userHasDefinedBackgroundColor = true;
        }
        if (userHasDefinedBackgroundColor) {
            this.defaultBackgroundColor = ((ColorDrawable) userDefinedBackground).getColor();
            setBackgroundColor(0);
        }
    }

    public void setItems(@XmlRes int xmlRes) {
        setItems(xmlRes, null);
    }

    public void setItems(@XmlRes int xmlRes, Config defaultTabConfig) {
        if (xmlRes == 0) {
            throw new RuntimeException("No items specified for the BottomBar!");
        }
        if (defaultTabConfig == null) {
            defaultTabConfig = getTabConfig();
        }
        updateItems(new TabParser(getContext(), defaultTabConfig, xmlRes).getTabs());
    }

    private Config getTabConfig() {
        return new Builder().inActiveTabAlpha(this.inActiveTabAlpha).activeTabAlpha(this.activeTabAlpha).inActiveTabColor(this.inActiveTabColor).activeTabColor(this.activeTabColor).barColorWhenSelected(this.defaultBackgroundColor).badgeBackgroundColor(this.badgeBackgroundColor).titleTextAppearance(this.titleTextAppearance).titleTypeFace(this.titleTypeFace).build();
    }

    private void updateItems(List<BottomBarTab> bottomBarItems) {
        this.tabContainer.removeAllViews();
        int index = 0;
        int biggestWidth = 0;
        BottomBarTab[] viewsToAdd = new BottomBarTab[bottomBarItems.size()];
        for (BottomBarTab bottomBarTab : bottomBarItems) {
            Type type;
            if (isShiftingMode()) {
                type = Type.SHIFTING;
            } else if (this.isTabletMode) {
                type = Type.TABLET;
            } else {
                type = Type.FIXED;
            }
            bottomBarTab.setType(type);
            bottomBarTab.prepareLayout();
            if (index == this.currentTabPosition) {
                bottomBarTab.select(true);
                handleBackgroundColorChange(bottomBarTab, false);
            } else {
                bottomBarTab.deselect(false);
            }
            if (this.isTabletMode) {
                this.tabContainer.addView(bottomBarTab);
            } else {
                if (bottomBarTab.getWidth() > biggestWidth) {
                    biggestWidth = bottomBarTab.getWidth();
                }
                viewsToAdd[index] = bottomBarTab;
            }
            bottomBarTab.setOnClickListener(this);
            bottomBarTab.setOnLongClickListener(this);
            index++;
        }
        this.currentTabs = viewsToAdd;
        if (!this.isTabletMode) {
            resizeTabsToCorrectSizes(viewsToAdd);
        }
    }

    private void resizeTabsToCorrectSizes(BottomBarTab[] tabsToAdd) {
        int viewWidth = MiscUtils.pixelToDp(getContext(), getWidth());
        if (viewWidth <= 0 || viewWidth > this.screenWidth) {
            viewWidth = this.screenWidth;
        }
        int proposedItemWidth = Math.min(MiscUtils.dpToPixel(getContext(), (float) (viewWidth / tabsToAdd.length)), this.maxFixedItemWidth);
        this.inActiveShiftingItemWidth = (int) (((double) proposedItemWidth) * 0.9d);
        this.activeShiftingItemWidth = (int) (((double) proposedItemWidth) + (((double) proposedItemWidth) * (((double) tabsToAdd.length) * 0.1d)));
        int height = Math.round(getContext().getResources().getDimension(R.dimen.bb_height));
        for (BottomBarTab tabView : tabsToAdd) {
            ViewGroup.LayoutParams params = tabView.getLayoutParams();
            params.height = height;
            if (!isShiftingMode()) {
                params.width = proposedItemWidth;
            } else if (tabView.isActive()) {
                params.width = this.activeShiftingItemWidth;
            } else {
                params.width = this.inActiveShiftingItemWidth;
            }
            if (tabView.getParent() == null) {
                this.tabContainer.addView(tabView);
            }
            tabView.requestLayout();
        }
    }

    public void setOnTabSelectListener(@Nullable OnTabSelectListener listener) {
        setOnTabSelectListener(listener, true);
    }

    public OnTabSelectListener getOnTabSelectListener() {
        return this.onTabSelectListener;
    }

    public void setOnTabSelectListener(@Nullable OnTabSelectListener listener, boolean shouldFireInitially) {
        this.onTabSelectListener = listener;
        if (shouldFireInitially && listener != null && getTabCount() > 0) {
            listener.onTabSelected(getCurrentTabId());
        }
    }

    public void setOnTabReselectListener(@Nullable OnTabReselectListener listener) {
        this.onTabReselectListener = listener;
    }

    public void setDefaultTab(@IdRes int defaultTabId) {
        setDefaultTabPosition(findPositionForTabWithId(defaultTabId));
    }

    public void setDefaultTabPosition(int defaultTabPosition) {
        if (!this.isComingFromRestoredState) {
            selectTabAtPosition(defaultTabPosition);
        }
    }

    public void selectTabWithId(@IdRes int tabResId) {
        selectTabAtPosition(findPositionForTabWithId(tabResId));
    }

    public void selectTabAtPosition(int position) {
        selectTabAtPosition(position, false);
    }

    public void selectTabAtPosition(int position, boolean animate) {
        if (position > getTabCount() - 1 || position < 0) {
            throw new IndexOutOfBoundsException("Can't select tab at position " + position + ". This BottomBar has no items at that position.");
        }
        BottomBarTab oldTab = getCurrentTab();
        BottomBarTab newTab = getTabAtPosition(position);
        oldTab.deselect(animate);
        newTab.select(animate);
        updateSelectedTab(position);
        shiftingMagic(oldTab, newTab, animate);
        handleBackgroundColorChange(newTab, animate);
    }

    public int getTabCount() {
        return this.tabContainer.getChildCount();
    }

    public BottomBarTab getCurrentTab() {
        return getTabAtPosition(getCurrentTabPosition());
    }

    public BottomBarTab getTabAtPosition(int position) {
        View child = this.tabContainer.getChildAt(position);
        if (child instanceof BadgeContainer) {
            return findTabInLayout((BadgeContainer) child);
        }
        return (BottomBarTab) child;
    }

    @IdRes
    public int getCurrentTabId() {
        return getCurrentTab().getId();
    }

    public int getCurrentTabPosition() {
        return this.currentTabPosition;
    }

    public int findPositionForTabWithId(@IdRes int tabId) {
        return getTabWithId(tabId).getIndexInTabContainer();
    }

    public BottomBarTab getTabWithId(@IdRes int tabId) {
        return (BottomBarTab) this.tabContainer.findViewById(tabId);
    }

    public void setInActiveTabAlpha(float alpha) {
        this.inActiveTabAlpha = alpha;
        this.batchPropertyApplier.applyToAllTabs(new C34821());
    }

    public void setActiveTabAlpha(float alpha) {
        this.activeTabAlpha = alpha;
        this.batchPropertyApplier.applyToAllTabs(new C34832());
    }

    public void setInActiveTabColor(@ColorInt int color) {
        this.inActiveTabColor = color;
        this.batchPropertyApplier.applyToAllTabs(new C34843());
    }

    public void setActiveTabColor(@ColorInt int color) {
        this.activeTabColor = color;
        this.batchPropertyApplier.applyToAllTabs(new C34854());
    }

    public void setBadgeBackgroundColor(@ColorInt int color) {
        this.badgeBackgroundColor = color;
        this.batchPropertyApplier.applyToAllTabs(new C34865());
    }

    public void setTabTitleTextAppearance(int textAppearance) {
        this.titleTextAppearance = textAppearance;
        this.batchPropertyApplier.applyToAllTabs(new C34876());
    }

    public void setTabTitleTypeface(String fontPath) {
        setTabTitleTypeface(getTypeFaceFromAsset(fontPath));
    }

    public void setTabTitleTypeface(Typeface typeface) {
        this.titleTypeFace = typeface;
        this.batchPropertyApplier.applyToAllTabs(new C34887());
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            if (!this.isTabletMode) {
                resizeTabsToCorrectSizes(this.currentTabs);
            }
            updateTitleBottomPadding();
            if (isShy()) {
                initializeShyBehavior();
            }
            if (drawUnderNav()) {
                resizeForDrawingUnderNavbar();
            }
        }
    }

    private void updateTitleBottomPadding() {
        int tabCount = getTabCount();
        if (this.tabContainer != null && tabCount != 0 && isShiftingMode()) {
            for (int i = 0; i < tabCount; i++) {
                TextView title = getTabAtPosition(i).getTitleView();
                if (title != null) {
                    int missingPadding = this.tenDp - (title.getHeight() - title.getBaseline());
                    if (missingPadding > 0) {
                        title.setPadding(title.getPaddingLeft(), title.getPaddingTop(), title.getPaddingRight(), title.getPaddingBottom() + missingPadding);
                    }
                }
            }
        }
    }

    private void initializeShyBehavior() {
        ViewParent parent = getParent();
        boolean hasAbusiveParent = parent != null && (parent instanceof CoordinatorLayout);
        if (!hasAbusiveParent) {
            throw new RuntimeException("In order to have shy behavior, the BottomBar must be a direct child of a CoordinatorLayout.");
        } else if (!this.shyHeightAlreadyCalculated) {
            int height = getHeight();
            if (height != 0) {
                updateShyHeight(height);
                this.shyHeightAlreadyCalculated = true;
            }
        }
    }

    private void updateShyHeight(int height) {
        ((CoordinatorLayout.LayoutParams) getLayoutParams()).setBehavior(new BottomNavigationBehavior(height, 0, false));
    }

    private void resizeForDrawingUnderNavbar() {
        if (VERSION.SDK_INT >= 19) {
            int currentHeight = getHeight();
            if (currentHeight != 0 && !this.navBarAccountedHeightCalculated) {
                this.navBarAccountedHeightCalculated = true;
                this.tabContainer.getLayoutParams().height = currentHeight;
                int finalHeight = currentHeight + NavbarUtils.getNavbarHeight(getContext());
                getLayoutParams().height = finalHeight;
                if (isShy()) {
                    updateShyHeight(finalHeight);
                }
            }
        }
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = saveState();
        bundle.putParcelable("superstate", super.onSaveInstanceState());
        return bundle;
    }

    @VisibleForTesting
    Bundle saveState() {
        Bundle outState = new Bundle();
        outState.putInt(STATE_CURRENT_SELECTED_TAB, this.currentTabPosition);
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
        if (savedInstanceState != null) {
            this.isComingFromRestoredState = true;
            this.ignoreTabReselectionListener = true;
            selectTabAtPosition(savedInstanceState.getInt(STATE_CURRENT_SELECTED_TAB, this.currentTabPosition), false);
        }
    }

    public void onClick(View v) {
        handleClick(v);
    }

    public boolean onLongClick(View v) {
        return handleLongClick(v);
    }

    private BottomBarTab findTabInLayout(ViewGroup child) {
        for (int i = 0; i < child.getChildCount(); i++) {
            View candidate = child.getChildAt(i);
            if (candidate instanceof BottomBarTab) {
                return (BottomBarTab) candidate;
            }
        }
        return null;
    }

    private void handleClick(View v) {
        BottomBarTab oldTab = getCurrentTab();
        BottomBarTab newTab = (BottomBarTab) v;
        oldTab.deselect(true);
        newTab.select(true);
        shiftingMagic(oldTab, newTab, true);
        handleBackgroundColorChange(newTab, true);
        updateSelectedTab(newTab.getIndexInTabContainer());
    }

    private boolean handleLongClick(View v) {
        if (v instanceof BottomBarTab) {
            BottomBarTab longClickedTab = (BottomBarTab) v;
            if ((isShiftingMode() || this.isTabletMode) && !longClickedTab.isActive()) {
                Toast.makeText(getContext(), longClickedTab.getTitle(), 0).show();
            }
        }
        return true;
    }

    private void updateSelectedTab(int newPosition) {
        int newTabId = getTabAtPosition(newPosition).getId();
        if (newPosition != this.currentTabPosition) {
            if (this.onTabSelectListener != null) {
                this.onTabSelectListener.onTabSelected(newTabId);
            }
        } else if (!(this.onTabReselectListener == null || this.ignoreTabReselectionListener)) {
            this.onTabReselectListener.onTabReSelected(newTabId);
        }
        this.currentTabPosition = newPosition;
        if (this.ignoreTabReselectionListener) {
            this.ignoreTabReselectionListener = false;
        }
    }

    private void shiftingMagic(BottomBarTab oldTab, BottomBarTab newTab, boolean animate) {
        if (isShiftingMode()) {
            oldTab.updateWidth((float) this.inActiveShiftingItemWidth, animate);
            newTab.updateWidth((float) this.activeShiftingItemWidth, animate);
        }
    }

    private void handleBackgroundColorChange(BottomBarTab tab, boolean animate) {
        int newColor = tab.getBarColorWhenSelected();
        if (this.currentBackgroundColor != newColor) {
            if (animate) {
                View clickedView = tab;
                if (tab.hasActiveBadge()) {
                    clickedView = tab.getOuterView();
                }
                animateBGColorChange(clickedView, newColor);
                this.currentBackgroundColor = newColor;
                return;
            }
            this.outerContainer.setBackgroundColor(newColor);
        }
    }

    private void animateBGColorChange(View clickedView, int newColor) {
        prepareForBackgroundColorAnimation(newColor);
        if (VERSION.SDK_INT < 21) {
            backgroundCrossfadeAnimation(newColor);
        } else if (this.outerContainer.isAttachedToWindow()) {
            backgroundCircularRevealAnimation(clickedView, newColor);
        }
    }

    private void prepareForBackgroundColorAnimation(int newColor) {
        this.outerContainer.clearAnimation();
        this.backgroundOverlay.clearAnimation();
        this.backgroundOverlay.setBackgroundColor(newColor);
        this.backgroundOverlay.setVisibility(0);
    }

    @TargetApi(21)
    private void backgroundCircularRevealAnimation(View clickedView, final int newColor) {
        Animator animator = ViewAnimationUtils.createCircularReveal(this.backgroundOverlay, (int) (ViewCompat.getX(clickedView) + ((float) (clickedView.getMeasuredWidth() / 2))), (this.isTabletMode ? (int) ViewCompat.getY(clickedView) : 0) + (clickedView.getMeasuredHeight() / 2), (float) 0, (float) (this.isTabletMode ? this.outerContainer.getHeight() : this.outerContainer.getWidth()));
        if (this.isTabletMode) {
            animator.setDuration(500);
        }
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                onEnd();
            }

            public void onAnimationCancel(Animator animation) {
                onEnd();
            }

            private void onEnd() {
                BottomBar.this.outerContainer.setBackgroundColor(newColor);
                BottomBar.this.backgroundOverlay.setVisibility(4);
                ViewCompat.setAlpha(BottomBar.this.backgroundOverlay, 1.0f);
            }
        });
        animator.start();
    }

    private void backgroundCrossfadeAnimation(final int newColor) {
        ViewCompat.setAlpha(this.backgroundOverlay, 0.0f);
        ViewCompat.animate(this.backgroundOverlay).alpha(1.0f).setListener(new ViewPropertyAnimatorListenerAdapter() {
            public void onAnimationEnd(View view) {
                onEnd();
            }

            public void onAnimationCancel(View view) {
                onEnd();
            }

            private void onEnd() {
                BottomBar.this.outerContainer.setBackgroundColor(newColor);
                BottomBar.this.backgroundOverlay.setVisibility(4);
                ViewCompat.setAlpha(BottomBar.this.backgroundOverlay, 1.0f);
            }
        }).start();
    }

    private void toggleShyVisibility(boolean visible) {
        BottomNavigationBehavior<BottomBar> from = BottomNavigationBehavior.from(this);
        if (from != null) {
            from.setHidden(this, visible);
        }
    }
}
