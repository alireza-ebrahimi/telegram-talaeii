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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Collection;
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

    /* renamed from: org.telegram.ui.ActionBar.ActionBar$1 */
    class C37931 implements OnClickListener {
        C37931() {
        }

        public void onClick(View view) {
            if (ActionBar.this.titleActionRunnable != null) {
                ActionBar.this.titleActionRunnable.run();
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBar$2 */
    class C37942 implements OnClickListener {
        C37942() {
        }

        public void onClick(View view) {
            if (!ActionBar.this.actionModeVisible && ActionBar.this.isSearchFieldVisible) {
                ActionBar.this.closeSearchField();
            } else if (ActionBar.this.actionBarMenuOnItemClick != null) {
                ActionBar.this.actionBarMenuOnItemClick.onItemClick(-1);
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBar$3 */
    class C37953 extends AnimatorListenerAdapter {
        C37953() {
        }

        public void onAnimationCancel(Animator animator) {
            if (ActionBar.this.actionModeAnimation != null && ActionBar.this.actionModeAnimation.equals(animator)) {
                ActionBar.this.actionModeAnimation = null;
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (ActionBar.this.actionModeAnimation != null && ActionBar.this.actionModeAnimation.equals(animator)) {
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

        public void onAnimationStart(Animator animator) {
            ActionBar.this.actionMode.setVisibility(0);
            if (ActionBar.this.occupyStatusBar && ActionBar.this.actionModeTop != null) {
                ActionBar.this.actionModeTop.setVisibility(0);
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBar$4 */
    class C37964 extends AnimatorListenerAdapter {
        C37964() {
        }

        public void onAnimationCancel(Animator animator) {
            if (ActionBar.this.actionModeAnimation != null && ActionBar.this.actionModeAnimation.equals(animator)) {
                ActionBar.this.actionModeAnimation = null;
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (ActionBar.this.actionModeAnimation != null && ActionBar.this.actionModeAnimation.equals(animator)) {
                ActionBar.this.actionModeAnimation = null;
                ActionBar.this.actionMode.setVisibility(4);
                if (ActionBar.this.occupyStatusBar && ActionBar.this.actionModeTop != null) {
                    ActionBar.this.actionModeTop.setVisibility(4);
                }
            }
        }
    }

    public static class ActionBarMenuOnItemClick {
        public boolean canOpenMenu() {
            return true;
        }

        public void onItemClick(int i) {
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
        setOnClickListener(new C37931());
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
            this.backButtonImageView.setOnClickListener(new C37942());
        }
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

    private void createTitleTextView() {
        if (this.titleTextView == null) {
            this.titleTextView = new SimpleTextView(getContext());
            this.titleTextView.setGravity(3);
            this.titleTextView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultTitle));
            this.titleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            addView(this.titleTextView, 0, LayoutHelper.createFrame(-2, -2, 51));
        }
    }

    public static int getCurrentActionBarHeight() {
        return AndroidUtilities.isTablet() ? AndroidUtilities.dp(64.0f) : AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y ? AndroidUtilities.dp(48.0f) : AndroidUtilities.dp(56.0f);
    }

    public void closeSearchField() {
        closeSearchField(true);
    }

    public void closeSearchField(boolean z) {
        if (this.isSearchFieldVisible && this.menu != null) {
            this.menu.closeSearchField(z);
        }
    }

    public ActionBarMenu createActionMode() {
        if (this.actionMode != null) {
            return this.actionMode;
        }
        this.actionMode = new ActionBarMenu(getContext(), this);
        this.actionMode.isActionMode = true;
        this.actionMode.setBackgroundColor(Theme.getColor(Theme.key_actionBarActionModeDefault));
        addView(this.actionMode, indexOfChild(this.backButtonImageView));
        this.actionMode.setPadding(0, this.occupyStatusBar ? AndroidUtilities.statusBarHeight : 0, 0, 0);
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

    public ActionBarMenu createMenu() {
        if (this.menu != null) {
            return this.menu;
        }
        this.menu = new ActionBarMenu(getContext(), this);
        addView(this.menu, 0, LayoutHelper.createFrame(-2, -1, 5));
        return this.menu;
    }

    public ActionBarMenuOnItemClick getActionBarMenuOnItemClick() {
        return this.actionBarMenuOnItemClick;
    }

    public boolean getAddToContainer() {
        return this.addToContainer;
    }

    public View getBackButton() {
        return this.backButtonImageView;
    }

    public boolean getCastShadows() {
        return this.castShadows;
    }

    public boolean getOccupyStatusBar() {
        return this.occupyStatusBar;
    }

    public String getSubtitle() {
        return this.subtitleTextView == null ? null : this.subtitleTextView.getText().toString();
    }

    public SimpleTextView getSubtitleTextView() {
        return this.subtitleTextView;
    }

    public String getTitle() {
        return this.titleTextView == null ? null : this.titleTextView.getText().toString();
    }

    public SimpleTextView getTitleTextView() {
        return this.titleTextView;
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public void hideActionMode() {
        if (this.actionMode != null && this.actionModeVisible) {
            this.actionModeVisible = false;
            Collection arrayList = new ArrayList();
            arrayList.add(ObjectAnimator.ofFloat(this.actionMode, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
            if (this.occupyStatusBar && this.actionModeTop != null) {
                arrayList.add(ObjectAnimator.ofFloat(this.actionModeTop, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
            }
            if (this.actionModeAnimation != null) {
                this.actionModeAnimation.cancel();
            }
            this.actionModeAnimation = new AnimatorSet();
            this.actionModeAnimation.playTogether(arrayList);
            this.actionModeAnimation.setDuration(200);
            this.actionModeAnimation.addListener(new C37964());
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
                    ((BackDrawable) drawable).setRotation(BitmapDescriptorFactory.HUE_RED, true);
                }
                this.backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(this.itemsBackgroundColor));
            }
        }
    }

    public boolean isActionModeShowed() {
        return this.actionMode != null && this.actionModeVisible;
    }

    public boolean isSearchFieldVisible() {
        return this.isSearchFieldVisible;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int dp;
        int dp2;
        int currentActionBarHeight;
        int i5 = this.occupyStatusBar ? AndroidUtilities.statusBarHeight : 0;
        if (this.backButtonImageView == null || this.backButtonImageView.getVisibility() == 8) {
            dp = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 26.0f : 18.0f);
        } else {
            this.backButtonImageView.layout(0, i5, this.backButtonImageView.getMeasuredWidth(), this.backButtonImageView.getMeasuredHeight() + i5);
            dp = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 80.0f : 72.0f);
        }
        if (!(this.menu == null || this.menu.getVisibility() == 8)) {
            if (this.isSearchFieldVisible) {
                dp2 = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 74.0f : 66.0f);
            } else {
                dp2 = (i3 - i) - this.menu.getMeasuredWidth();
            }
            this.menu.layout(dp2, i5, this.menu.getMeasuredWidth() + dp2, this.menu.getMeasuredHeight() + i5);
        }
        if (!(this.titleTextView == null || this.titleTextView.getVisibility() == 8)) {
            if (this.subtitleTextView == null || this.subtitleTextView.getVisibility() == 8) {
                dp2 = (getCurrentActionBarHeight() - this.titleTextView.getTextHeight()) / 2;
            } else {
                currentActionBarHeight = ((getCurrentActionBarHeight() / 2) - this.titleTextView.getTextHeight()) / 2;
                float f = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 3.0f : 2.0f;
                dp2 = AndroidUtilities.dp(f) + currentActionBarHeight;
            }
            this.titleTextView.layout(dp, i5 + dp2, this.titleTextView.getMeasuredWidth() + dp, (dp2 + i5) + this.titleTextView.getTextHeight());
        }
        if (!(this.subtitleTextView == null || this.subtitleTextView.getVisibility() == 8)) {
            currentActionBarHeight = (((getCurrentActionBarHeight() / 2) - this.subtitleTextView.getTextHeight()) / 2) + (getCurrentActionBarHeight() / 2);
            f = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 1.0f : 1.0f;
            dp2 = currentActionBarHeight - AndroidUtilities.dp(f);
            this.subtitleTextView.layout(dp, i5 + dp2, this.subtitleTextView.getMeasuredWidth() + dp, (i5 + dp2) + this.subtitleTextView.getTextHeight());
        }
        currentActionBarHeight = getChildCount();
        for (dp2 = 0; dp2 < currentActionBarHeight; dp2++) {
            View childAt = getChildAt(dp2);
            if (!(childAt.getVisibility() == 8 || childAt == this.titleTextView || childAt == this.subtitleTextView || childAt == this.menu || childAt == this.backButtonImageView)) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                dp = layoutParams.gravity;
                if (dp == -1) {
                    dp = 51;
                }
                int i6 = dp & 112;
                switch ((dp & 7) & 7) {
                    case 1:
                        dp = ((((i3 - i) - measuredWidth) / 2) + layoutParams.leftMargin) - layoutParams.rightMargin;
                        break;
                    case 5:
                        dp = (i3 - measuredWidth) - layoutParams.rightMargin;
                        break;
                    default:
                        dp = layoutParams.leftMargin;
                        break;
                }
                switch (i6) {
                    case 16:
                        i5 = ((((i4 - i2) - measuredHeight) / 2) + layoutParams.topMargin) - layoutParams.bottomMargin;
                        break;
                    case 48:
                        i5 = layoutParams.topMargin;
                        break;
                    case 80:
                        i5 = ((i4 - i2) - measuredHeight) - layoutParams.bottomMargin;
                        break;
                    default:
                        i5 = layoutParams.topMargin;
                        break;
                }
                childAt.layout(dp, i5, measuredWidth + dp, measuredHeight + i5);
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        int dp;
        int size = MeasureSpec.getSize(i);
        MeasureSpec.getSize(i2);
        int currentActionBarHeight = getCurrentActionBarHeight();
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(currentActionBarHeight, 1073741824);
        setMeasuredDimension(size, ((this.occupyStatusBar ? AndroidUtilities.statusBarHeight : 0) + currentActionBarHeight) + this.extraHeight);
        if (this.backButtonImageView == null || this.backButtonImageView.getVisibility() == 8) {
            dp = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 26.0f : 18.0f);
        } else {
            this.backButtonImageView.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(54.0f), 1073741824), makeMeasureSpec);
            dp = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 80.0f : 72.0f);
        }
        if (!(this.menu == null || this.menu.getVisibility() == 8)) {
            if (this.isSearchFieldVisible) {
                currentActionBarHeight = MeasureSpec.makeMeasureSpec(size - AndroidUtilities.dp(AndroidUtilities.isTablet() ? 74.0f : 66.0f), 1073741824);
            } else {
                currentActionBarHeight = MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
            }
            this.menu.measure(currentActionBarHeight, makeMeasureSpec);
        }
        if (!((this.titleTextView == null || this.titleTextView.getVisibility() == 8) && (this.subtitleTextView == null || this.subtitleTextView.getVisibility() == 8))) {
            SimpleTextView simpleTextView;
            currentActionBarHeight = (((size - (this.menu != null ? this.menu.getMeasuredWidth() : 0)) - AndroidUtilities.dp(16.0f)) - dp) - this.titleRightMargin;
            if (!(this.titleTextView == null || this.titleTextView.getVisibility() == 8)) {
                if (this.actionBarFontSize > 2) {
                    simpleTextView = this.titleTextView;
                    dp = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? this.actionBarFontSize : this.actionBarFontSize - 2;
                    simpleTextView.setTextSize(dp);
                } else {
                    simpleTextView = this.titleTextView;
                    dp = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 18 : 16;
                    simpleTextView.setTextSize(dp);
                }
                this.titleTextView.measure(MeasureSpec.makeMeasureSpec(currentActionBarHeight, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24.0f), Integer.MIN_VALUE));
            }
            if (!(this.subtitleTextView == null || this.subtitleTextView.getVisibility() == 8)) {
                simpleTextView = this.subtitleTextView;
                dp = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 14 : 12;
                simpleTextView.setTextSize(dp);
                this.subtitleTextView.measure(MeasureSpec.makeMeasureSpec(currentActionBarHeight, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), Integer.MIN_VALUE));
            }
        }
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (!(childAt.getVisibility() == 8 || childAt == this.titleTextView || childAt == this.subtitleTextView || childAt == this.menu || childAt == this.backButtonImageView)) {
                measureChildWithMargins(childAt, i, 0, MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824), 0);
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

    protected void onSearchFieldVisibilityChanged(boolean z) {
        int i = 4;
        this.isSearchFieldVisible = z;
        if (this.titleTextView != null) {
            this.titleTextView.setVisibility(z ? 4 : 0);
        }
        if (this.subtitleTextView != null) {
            SimpleTextView simpleTextView = this.subtitleTextView;
            if (!z) {
                i = 0;
            }
            simpleTextView.setVisibility(i);
        }
        Drawable drawable = this.backButtonImageView.getDrawable();
        if (drawable != null && (drawable instanceof MenuDrawable)) {
            ((MenuDrawable) drawable).setRotation(z ? 1.0f : BitmapDescriptorFactory.HUE_RED, true);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent) || this.interceptTouches;
    }

    public void openSearchField(String str) {
        if (this.menu != null && str != null) {
            this.menu.openSearchField(!this.isSearchFieldVisible, str);
        }
    }

    public void setActionBarMenuOnItemClick(ActionBarMenuOnItemClick actionBarMenuOnItemClick) {
        this.actionBarMenuOnItemClick = actionBarMenuOnItemClick;
    }

    public void setActionModeColor(int i) {
        if (this.actionMode != null) {
            this.actionMode.setBackgroundColor(i);
        }
    }

    public void setActionModeTopColor(int i) {
        if (this.actionModeTop != null) {
            this.actionModeTop.setBackgroundColor(i);
        }
    }

    public void setAddToContainer(boolean z) {
        this.addToContainer = z;
    }

    public void setAllowOverlayTitle(boolean z) {
        this.allowOverlayTitle = z;
    }

    public void setBackButtonDrawable(Drawable drawable) {
        if (this.backButtonImageView == null) {
            createBackButtonImage();
        }
        this.backButtonImageView.setVisibility(drawable == null ? 8 : 0);
        this.backButtonImageView.setImageDrawable(drawable);
        if (drawable instanceof BackDrawable) {
            BackDrawable backDrawable = (BackDrawable) drawable;
            backDrawable.setRotation(isActionModeShowed() ? 1.0f : BitmapDescriptorFactory.HUE_RED, false);
            backDrawable.setRotatedColor(this.itemsActionModeColor);
            backDrawable.setColor(this.itemsColor);
        }
    }

    public void setBackButtonImage(int i) {
        if (this.backButtonImageView == null) {
            createBackButtonImage();
        }
        this.backButtonImageView.setVisibility(i == 0 ? 8 : 0);
        this.backButtonImageView.setImageResource(i);
    }

    public void setCastShadows(boolean z) {
        this.castShadows = z;
    }

    public void setExtraHeight(int i) {
        this.extraHeight = i;
    }

    public void setInterceptTouches(boolean z) {
        this.interceptTouches = z;
    }

    public void setItemsBackgroundColor(int i, boolean z) {
        if (z) {
            this.itemsActionModeBackgroundColor = i;
            if (this.actionModeVisible && this.backButtonImageView != null) {
                this.backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(this.itemsActionModeBackgroundColor));
            }
            if (this.actionMode != null) {
                this.actionMode.updateItemsBackgroundColor();
                return;
            }
            return;
        }
        this.itemsBackgroundColor = i;
        if (this.backButtonImageView != null) {
            this.backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(this.itemsBackgroundColor));
        }
        if (this.menu != null) {
            this.menu.updateItemsBackgroundColor();
        }
    }

    public void setItemsColor(int i, boolean z) {
        Drawable drawable;
        if (z) {
            this.itemsActionModeColor = i;
            if (this.actionMode != null) {
                this.actionMode.updateItemsColor();
            }
            if (this.backButtonImageView != null) {
                drawable = this.backButtonImageView.getDrawable();
                if (drawable instanceof BackDrawable) {
                    ((BackDrawable) drawable).setRotatedColor(i);
                    return;
                }
                return;
            }
            return;
        }
        this.itemsColor = i;
        if (!(this.backButtonImageView == null || this.itemsColor == 0)) {
            this.backButtonImageView.setColorFilter(new PorterDuffColorFilter(this.itemsColor, Mode.MULTIPLY));
            drawable = this.backButtonImageView.getDrawable();
            if (drawable instanceof BackDrawable) {
                ((BackDrawable) drawable).setColor(i);
            }
        }
        if (this.menu != null) {
            this.menu.updateItemsColor();
        }
    }

    public void setOccupyStatusBar(boolean z) {
        this.occupyStatusBar = z;
        if (this.actionMode != null) {
            this.actionMode.setPadding(0, this.occupyStatusBar ? AndroidUtilities.statusBarHeight : 0, 0, 0);
        }
    }

    public void setPopupBackgroundColor(int i) {
        if (this.menu != null) {
            this.menu.redrawPopup(i);
        }
    }

    public void setPopupItemsColor(int i) {
        if (this.menu != null) {
            this.menu.setPopupItemsColor(i);
        }
    }

    public void setSearchTextColor(int i, boolean z) {
        if (this.menu != null) {
            this.menu.setSearchTextColor(i, z);
        }
    }

    public void setSubtitle(CharSequence charSequence) {
        if (charSequence != null && this.subtitleTextView == null) {
            createSubtitleTextView();
        }
        if (this.subtitleTextView != null) {
            this.lastSubtitle = charSequence;
            SimpleTextView simpleTextView = this.subtitleTextView;
            int i = (TextUtils.isEmpty(charSequence) || this.isSearchFieldVisible) ? 8 : 0;
            simpleTextView.setVisibility(i);
            this.subtitleTextView.setText(charSequence);
        }
    }

    public void setSubtitleColor(int i) {
        if (this.subtitleTextView == null) {
            createSubtitleTextView();
        }
        this.subtitleTextView.setTextColor(i);
    }

    public void setTitle(CharSequence charSequence) {
        if (charSequence != null && this.titleTextView == null) {
            createTitleTextView();
        }
        if (this.titleTextView != null) {
            this.lastTitle = charSequence;
            SimpleTextView simpleTextView = this.titleTextView;
            int i = (charSequence == null || this.isSearchFieldVisible) ? 4 : 0;
            simpleTextView.setVisibility(i);
            this.titleTextView.setText(charSequence);
        }
    }

    public void setTitleColor(int i) {
        if (this.titleTextView == null) {
            createTitleTextView();
        }
        this.titleTextView.setTextColor(i);
    }

    public void setTitleOverlayText(String str, String str2, Runnable runnable) {
        int i = 0;
        if (this.allowOverlayTitle && this.parentFragment.parentLayout != null) {
            CharSequence charSequence;
            CharSequence charSequence2;
            if (str == null) {
                charSequence = this.lastTitle;
            }
            if (charSequence != null && this.titleTextView == null) {
                createTitleTextView();
            }
            if (this.titleTextView != null) {
                SimpleTextView simpleTextView = this.titleTextView;
                int i2 = (charSequence == null || this.isSearchFieldVisible) ? 4 : 0;
                simpleTextView.setVisibility(i2);
                this.titleTextView.setText(charSequence);
            }
            if (str2 == null) {
                charSequence2 = this.lastSubtitle;
            }
            if (charSequence2 != null && this.subtitleTextView == null) {
                createSubtitleTextView();
            }
            if (this.subtitleTextView != null) {
                SimpleTextView simpleTextView2 = this.subtitleTextView;
                if (TextUtils.isEmpty(charSequence2) || this.isSearchFieldVisible) {
                    i = 8;
                }
                simpleTextView2.setVisibility(i);
                this.subtitleTextView.setText(charSequence2);
            }
            this.titleActionRunnable = runnable;
        }
    }

    public void setTitleRightMargin(int i) {
        this.titleRightMargin = i;
    }

    public void showActionMode() {
        if (this.actionMode != null && !this.actionModeVisible) {
            this.actionModeVisible = true;
            Collection arrayList = new ArrayList();
            arrayList.add(ObjectAnimator.ofFloat(this.actionMode, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}));
            if (this.occupyStatusBar && this.actionModeTop != null) {
                arrayList.add(ObjectAnimator.ofFloat(this.actionModeTop, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}));
            }
            if (this.actionModeAnimation != null) {
                this.actionModeAnimation.cancel();
            }
            this.actionModeAnimation = new AnimatorSet();
            this.actionModeAnimation.playTogether(arrayList);
            this.actionModeAnimation.setDuration(200);
            this.actionModeAnimation.addListener(new C37953());
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
}
