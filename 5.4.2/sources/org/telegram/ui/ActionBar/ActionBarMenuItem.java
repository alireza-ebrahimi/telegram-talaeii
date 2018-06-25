package org.telegram.ui.ActionBar;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.reflect.Method;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.ui.ActionBar.ActionBarPopupWindow.ActionBarPopupWindowLayout;
import org.telegram.ui.ActionBar.ActionBarPopupWindow.OnDispatchKeyEventListener;
import org.telegram.ui.Components.CloseProgressDrawable2;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;

public class ActionBarMenuItem extends FrameLayout {
    private static Method layoutInScreenMethod;
    private int additionalOffset;
    private boolean allowCloseAnimation = true;
    private boolean animationEnabled = true;
    private ImageView clearButton;
    private ActionBarMenuItemDelegate delegate;
    protected ImageView iconView;
    private boolean ignoreOnTextChange;
    private boolean isSearchField;
    private boolean layoutInScreen;
    private ActionBarMenuItemSearchListener listener;
    private int[] location;
    private int menuHeight = AndroidUtilities.dp(16.0f);
    protected boolean overrideMenuClick;
    private ActionBarMenu parentMenu;
    private ActionBarPopupWindowLayout popupLayout;
    private ActionBarPopupWindow popupWindow;
    private boolean processedPopupClick;
    private CloseProgressDrawable2 progressDrawable;
    private Rect rect;
    private FrameLayout searchContainer;
    private EditTextBoldCursor searchField;
    private TextView searchFieldCaption;
    private View selectedMenuView;
    private Runnable showMenuRunnable;
    private int subMenuOpenSide;

    /* renamed from: org.telegram.ui.ActionBar.ActionBarMenuItem$1 */
    class C38071 implements Runnable {
        C38071() {
        }

        public void run() {
            if (ActionBarMenuItem.this.getParent() != null) {
                ActionBarMenuItem.this.getParent().requestDisallowInterceptTouchEvent(true);
            }
            ActionBarMenuItem.this.toggleSubMenu();
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarMenuItem$2 */
    class C38082 implements OnTouchListener {
        C38082() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getActionMasked() == 0 && ActionBarMenuItem.this.popupWindow != null && ActionBarMenuItem.this.popupWindow.isShowing()) {
                view.getHitRect(ActionBarMenuItem.this.rect);
                if (!ActionBarMenuItem.this.rect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    ActionBarMenuItem.this.popupWindow.dismiss();
                }
            }
            return false;
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarMenuItem$3 */
    class C38093 implements OnDispatchKeyEventListener {
        C38093() {
        }

        public void onDispatchKeyEvent(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == 4 && keyEvent.getRepeatCount() == 0 && ActionBarMenuItem.this.popupWindow != null && ActionBarMenuItem.this.popupWindow.isShowing()) {
                ActionBarMenuItem.this.popupWindow.dismiss();
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarMenuItem$4 */
    class C38104 implements OnClickListener {
        C38104() {
        }

        public void onClick(View view) {
            if (ActionBarMenuItem.this.popupWindow != null && ActionBarMenuItem.this.popupWindow.isShowing()) {
                if (!ActionBarMenuItem.this.processedPopupClick) {
                    ActionBarMenuItem.this.processedPopupClick = true;
                    ActionBarMenuItem.this.popupWindow.dismiss(ActionBarMenuItem.this.allowCloseAnimation);
                } else {
                    return;
                }
            }
            if (ActionBarMenuItem.this.parentMenu != null) {
                ActionBarMenuItem.this.parentMenu.onItemClick(((Integer) view.getTag()).intValue());
            } else if (ActionBarMenuItem.this.delegate != null) {
                ActionBarMenuItem.this.delegate.onItemClick(((Integer) view.getTag()).intValue());
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarMenuItem$5 */
    class C38115 implements OnKeyListener {
        C38115() {
        }

        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (i != 82 || keyEvent.getRepeatCount() != 0 || keyEvent.getAction() != 1 || ActionBarMenuItem.this.popupWindow == null || !ActionBarMenuItem.this.popupWindow.isShowing()) {
                return false;
            }
            ActionBarMenuItem.this.popupWindow.dismiss();
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarMenuItem$8 */
    class C38148 implements Callback {
        C38148() {
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        public void onDestroyActionMode(ActionMode actionMode) {
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarMenuItem$9 */
    class C38159 implements OnEditorActionListener {
        C38159() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (keyEvent != null && ((keyEvent.getAction() == 1 && keyEvent.getKeyCode() == 84) || (keyEvent.getAction() == 0 && keyEvent.getKeyCode() == 66))) {
                AndroidUtilities.hideKeyboard(ActionBarMenuItem.this.searchField);
                if (ActionBarMenuItem.this.listener != null) {
                    ActionBarMenuItem.this.listener.onSearchPressed(ActionBarMenuItem.this.searchField);
                }
            }
            return false;
        }
    }

    public interface ActionBarMenuItemDelegate {
        void onItemClick(int i);
    }

    public static class ActionBarMenuItemSearchListener {
        public boolean canCollapseSearch() {
            return true;
        }

        public void onCaptionCleared() {
        }

        public void onSearchCollapse() {
        }

        public void onSearchExpand() {
        }

        public void onSearchPressed(EditText editText) {
        }

        public void onTextChanged(EditText editText) {
        }
    }

    public ActionBarMenuItem(Context context, ActionBarMenu actionBarMenu, int i, int i2) {
        super(context);
        if (i != 0) {
            setBackgroundDrawable(Theme.createSelectorDrawable(i));
        }
        this.parentMenu = actionBarMenu;
        this.iconView = new ImageView(context);
        this.iconView.setScaleType(ScaleType.CENTER);
        addView(this.iconView, LayoutHelper.createFrame(-1, -1.0f));
        if (i2 != 0) {
            this.iconView.setColorFilter(new PorterDuffColorFilter(i2, Mode.MULTIPLY));
        }
    }

    private void createPopupLayout() {
        if (this.popupLayout == null) {
            this.rect = new Rect();
            this.location = new int[2];
            this.popupLayout = new ActionBarPopupWindowLayout(getContext());
            this.popupLayout.setOnTouchListener(new C38082());
            this.popupLayout.setDispatchKeyEventListener(new C38093());
        }
    }

    private void updateOrShowPopup(boolean z, boolean z2) {
        int top;
        if (this.parentMenu != null) {
            top = (-this.parentMenu.parentActionBar.getMeasuredHeight()) + this.parentMenu.getTop();
        } else {
            float scaleY = getScaleY();
            top = (-((int) ((((float) getMeasuredHeight()) * scaleY) - (getTranslationY() / scaleY)))) + this.additionalOffset;
        }
        if (z) {
            this.popupLayout.scrollToTop();
        }
        View view;
        if (this.parentMenu != null) {
            view = this.parentMenu.parentActionBar;
            if (this.subMenuOpenSide == 0) {
                if (z) {
                    this.popupWindow.showAsDropDown(view, (((getLeft() + this.parentMenu.getLeft()) + getMeasuredWidth()) - this.popupLayout.getMeasuredWidth()) + ((int) getTranslationX()), top);
                }
                if (z2) {
                    this.popupWindow.update(view, (((getLeft() + this.parentMenu.getLeft()) + getMeasuredWidth()) - this.popupLayout.getMeasuredWidth()) + ((int) getTranslationX()), top, -1, -1);
                    return;
                }
                return;
            }
            if (z) {
                this.popupWindow.showAsDropDown(view, (getLeft() - AndroidUtilities.dp(8.0f)) + ((int) getTranslationX()), top);
            }
            if (z2) {
                this.popupWindow.update(view, (getLeft() - AndroidUtilities.dp(8.0f)) + ((int) getTranslationX()), top, -1, -1);
            }
        } else if (this.subMenuOpenSide != 0) {
            if (z) {
                this.popupWindow.showAsDropDown(this, -AndroidUtilities.dp(8.0f), top);
            }
            if (z2) {
                this.popupWindow.update(this, -AndroidUtilities.dp(8.0f), top, -1, -1);
            }
        } else if (getParent() != null) {
            view = (View) getParent();
            if (z) {
                this.popupWindow.showAsDropDown(view, (getLeft() + getMeasuredWidth()) - this.popupLayout.getMeasuredWidth(), top);
            }
            if (z2) {
                this.popupWindow.update(view, (getLeft() + getMeasuredWidth()) - this.popupLayout.getMeasuredWidth(), top, -1, -1);
            }
        }
    }

    public TextView addSubItem(int i, String str) {
        createPopupLayout();
        View textView = new TextView(getContext());
        textView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
        textView.setBackgroundDrawable(Theme.getSelectorDrawable(false));
        if (LocaleController.isRTL) {
            textView.setGravity(21);
        } else {
            textView.setGravity(16);
        }
        textView.setPadding(AndroidUtilities.dp(16.0f), 0, AndroidUtilities.dp(16.0f), 0);
        textView.setTextSize(1, 16.0f);
        textView.setMinWidth(AndroidUtilities.dp(196.0f));
        textView.setTag(Integer.valueOf(i));
        textView.setText(str);
        this.popupLayout.addView(textView);
        LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
        if (LocaleController.isRTL) {
            layoutParams.gravity = 5;
        }
        layoutParams.width = -1;
        layoutParams.height = AndroidUtilities.dp(48.0f);
        textView.setLayoutParams(layoutParams);
        textView.setOnClickListener(new C38104());
        this.menuHeight = layoutParams.height + this.menuHeight;
        return textView;
    }

    public void addSubItem(View view, int i, int i2) {
        createPopupLayout();
        this.popupLayout.addView(view, new LayoutParams(i, i2));
    }

    public void clearSearchText() {
        if (this.searchField != null) {
            this.searchField.setText(TtmlNode.ANONYMOUS_REGION_ID);
        }
    }

    public void closeSubMenu() {
        if (this.popupWindow != null && this.popupWindow.isShowing()) {
            this.popupWindow.dismiss();
        }
    }

    public ImageView getImageView() {
        return this.iconView;
    }

    public TextView getItemById(int i) {
        return getpopupLayout() != null ? (TextView) getpopupLayout().findViewWithTag(Integer.valueOf(i)) : null;
    }

    public EditTextBoldCursor getSearchField() {
        return this.searchField;
    }

    public View getpopupLayout() {
        return this.popupLayout != null ? this.popupLayout : null;
    }

    public boolean hasSubMenu() {
        return this.popupLayout != null;
    }

    public void hideSubItem(int i) {
        View findViewWithTag = this.popupLayout.findViewWithTag(Integer.valueOf(i));
        if (findViewWithTag != null) {
            findViewWithTag.setVisibility(8);
        }
    }

    public boolean isSearchField() {
        return this.isSearchField;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.popupWindow != null && this.popupWindow.isShowing()) {
            updateOrShowPopup(false, true);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            if (hasSubMenu() && (this.popupWindow == null || !(this.popupWindow == null || this.popupWindow.isShowing()))) {
                this.showMenuRunnable = new C38071();
                AndroidUtilities.runOnUIThread(this.showMenuRunnable, 200);
            }
        } else if (motionEvent.getActionMasked() == 2) {
            if (!hasSubMenu() || (this.popupWindow != null && (this.popupWindow == null || this.popupWindow.isShowing()))) {
                if (this.popupWindow != null && this.popupWindow.isShowing()) {
                    getLocationOnScreen(this.location);
                    float x = motionEvent.getX() + ((float) this.location[0]);
                    float y = motionEvent.getY() + ((float) this.location[1]);
                    this.popupLayout.getLocationOnScreen(this.location);
                    float f = x - ((float) this.location[0]);
                    float f2 = y - ((float) this.location[1]);
                    this.selectedMenuView = null;
                    for (int i = 0; i < this.popupLayout.getItemsCount(); i++) {
                        View itemAt = this.popupLayout.getItemAt(i);
                        itemAt.getHitRect(this.rect);
                        if (((Integer) itemAt.getTag()).intValue() < 100) {
                            if (this.rect.contains((int) f, (int) f2)) {
                                itemAt.setPressed(true);
                                itemAt.setSelected(true);
                                if (VERSION.SDK_INT >= 21) {
                                    if (VERSION.SDK_INT == 21) {
                                        itemAt.getBackground().setVisible(true, false);
                                    }
                                    itemAt.drawableHotspotChanged(f, f2 - ((float) itemAt.getTop()));
                                }
                                this.selectedMenuView = itemAt;
                            } else {
                                itemAt.setPressed(false);
                                itemAt.setSelected(false);
                                if (VERSION.SDK_INT == 21) {
                                    itemAt.getBackground().setVisible(false, false);
                                }
                            }
                        }
                    }
                }
            } else if (motionEvent.getY() > ((float) getHeight())) {
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                toggleSubMenu();
                return true;
            }
        } else if (this.popupWindow != null && this.popupWindow.isShowing() && motionEvent.getActionMasked() == 1) {
            if (this.selectedMenuView != null) {
                this.selectedMenuView.setSelected(false);
                if (this.parentMenu != null) {
                    this.parentMenu.onItemClick(((Integer) this.selectedMenuView.getTag()).intValue());
                } else if (this.delegate != null) {
                    this.delegate.onItemClick(((Integer) this.selectedMenuView.getTag()).intValue());
                }
                this.popupWindow.dismiss(this.allowCloseAnimation);
            } else {
                this.popupWindow.dismiss();
            }
        } else if (this.selectedMenuView != null) {
            this.selectedMenuView.setSelected(false);
            this.selectedMenuView = null;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void openSearch(boolean z) {
        if (this.searchContainer != null && this.searchContainer.getVisibility() != 0 && this.parentMenu != null) {
            this.parentMenu.parentActionBar.onSearchFieldVisibilityChanged(toggleSearch(z));
        }
    }

    public void redrawPopup(int i) {
        if (this.popupLayout != null) {
            this.popupLayout.backgroundDrawable.setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
            this.popupLayout.invalidate();
        }
    }

    public ActionBarMenuItem setActionBarMenuItemSearchListener(ActionBarMenuItemSearchListener actionBarMenuItemSearchListener) {
        this.listener = actionBarMenuItemSearchListener;
        return this;
    }

    public void setAdditionalOffset(int i) {
        this.additionalOffset = i;
    }

    public ActionBarMenuItem setAllowCloseAnimation(boolean z) {
        this.allowCloseAnimation = z;
        return this;
    }

    public void setDelegate(ActionBarMenuItemDelegate actionBarMenuItemDelegate) {
        this.delegate = actionBarMenuItemDelegate;
    }

    public void setIcon(int i) {
        this.iconView.setImageResource(i);
    }

    public void setIcon(Drawable drawable) {
        this.iconView.setImageDrawable(drawable);
    }

    public void setIconColor(int i) {
        this.iconView.setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
        if (this.clearButton != null) {
            this.clearButton.setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
        }
    }

    public void setIgnoreOnTextChange() {
        this.ignoreOnTextChange = true;
    }

    public ActionBarMenuItem setIsSearchField(boolean z) {
        if (this.parentMenu != null) {
            if (z && this.searchContainer == null) {
                this.searchContainer = new FrameLayout(getContext()) {
                    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                        int i5 = 0;
                        super.onLayout(z, i, i2, i3, i4);
                        if (!LocaleController.isRTL && ActionBarMenuItem.this.searchFieldCaption.getVisibility() == 0) {
                            i5 = ActionBarMenuItem.this.searchFieldCaption.getMeasuredWidth() + AndroidUtilities.dp(4.0f);
                        }
                        ActionBarMenuItem.this.searchField.layout(i5, ActionBarMenuItem.this.searchField.getTop(), ActionBarMenuItem.this.searchField.getMeasuredWidth() + i5, ActionBarMenuItem.this.searchField.getBottom());
                    }

                    protected void onMeasure(int i, int i2) {
                        int measuredWidth;
                        measureChildWithMargins(ActionBarMenuItem.this.clearButton, i, 0, i2, 0);
                        if (ActionBarMenuItem.this.searchFieldCaption.getVisibility() == 0) {
                            measureChildWithMargins(ActionBarMenuItem.this.searchFieldCaption, i, MeasureSpec.getSize(i) / 2, i2, 0);
                            measuredWidth = ActionBarMenuItem.this.searchFieldCaption.getMeasuredWidth() + AndroidUtilities.dp(4.0f);
                        } else {
                            measuredWidth = 0;
                        }
                        measureChildWithMargins(ActionBarMenuItem.this.searchField, i, measuredWidth, i2, 0);
                        MeasureSpec.getSize(i);
                        MeasureSpec.getSize(i2);
                        setMeasuredDimension(MeasureSpec.getSize(i), MeasureSpec.getSize(i2));
                    }
                };
                this.parentMenu.addView(this.searchContainer, 0, LayoutHelper.createLinear(0, -1, 1.0f, 6, 0, 0, 0));
                this.searchContainer.setVisibility(8);
                this.searchFieldCaption = new TextView(getContext());
                this.searchFieldCaption.setTextSize(1, 18.0f);
                this.searchFieldCaption.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSearch));
                this.searchFieldCaption.setSingleLine(true);
                this.searchFieldCaption.setEllipsize(TruncateAt.END);
                this.searchFieldCaption.setVisibility(8);
                this.searchFieldCaption.setGravity(LocaleController.isRTL ? 5 : 3);
                this.searchField = new EditTextBoldCursor(getContext()) {
                    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
                        return super.dispatchKeyEvent(keyEvent);
                    }

                    public boolean onKeyDown(int i, KeyEvent keyEvent) {
                        if (i != 67 || ActionBarMenuItem.this.searchField.length() != 0 || ActionBarMenuItem.this.searchFieldCaption.getVisibility() != 0 || ActionBarMenuItem.this.searchFieldCaption.length() <= 0) {
                            return super.onKeyDown(i, keyEvent);
                        }
                        ActionBarMenuItem.this.clearButton.callOnClick();
                        return true;
                    }
                };
                this.searchField.setCursorWidth(1.5f);
                this.searchField.setCursorColor(-1);
                this.searchField.setTextSize(1, 18.0f);
                this.searchField.setHintTextColor(Theme.getColor(Theme.key_actionBarDefaultSearchPlaceholder));
                this.searchField.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSearch));
                this.searchField.setSingleLine(true);
                this.searchField.setBackgroundResource(0);
                this.searchField.setPadding(0, 0, 0, 0);
                this.searchField.setInputType(this.searchField.getInputType() | 524288);
                if (VERSION.SDK_INT < 23) {
                    this.searchField.setCustomSelectionActionModeCallback(new C38148());
                }
                this.searchField.setOnEditorActionListener(new C38159());
                this.searchField.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable editable) {
                    }

                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        if (ActionBarMenuItem.this.ignoreOnTextChange) {
                            ActionBarMenuItem.this.ignoreOnTextChange = false;
                            return;
                        }
                        if (ActionBarMenuItem.this.listener != null) {
                            ActionBarMenuItem.this.listener.onTextChanged(ActionBarMenuItem.this.searchField);
                        }
                        if (ActionBarMenuItem.this.clearButton == null) {
                        }
                    }
                });
                this.searchField.setImeOptions(33554435);
                this.searchField.setTextIsSelectable(false);
                if (LocaleController.isRTL) {
                    this.searchContainer.addView(this.searchField, LayoutHelper.createFrame(-1, 36.0f, 16, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED));
                    this.searchContainer.addView(this.searchFieldCaption, LayoutHelper.createFrame(-2, 36.0f, 21, BitmapDescriptorFactory.HUE_RED, 5.5f, 48.0f, BitmapDescriptorFactory.HUE_RED));
                } else {
                    this.searchContainer.addView(this.searchFieldCaption, LayoutHelper.createFrame(-2, 36.0f, 19, BitmapDescriptorFactory.HUE_RED, 5.5f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                    this.searchContainer.addView(this.searchField, LayoutHelper.createFrame(-1, 36.0f, 16, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED));
                }
                this.clearButton = new ImageView(getContext());
                ImageView imageView = this.clearButton;
                Drawable closeProgressDrawable2 = new CloseProgressDrawable2();
                this.progressDrawable = closeProgressDrawable2;
                imageView.setImageDrawable(closeProgressDrawable2);
                this.clearButton.setColorFilter(new PorterDuffColorFilter(this.parentMenu.parentActionBar.itemsColor, Mode.MULTIPLY));
                this.clearButton.setScaleType(ScaleType.CENTER);
                this.clearButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (ActionBarMenuItem.this.searchField.length() != 0) {
                            ActionBarMenuItem.this.searchField.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        } else if (ActionBarMenuItem.this.searchFieldCaption != null && ActionBarMenuItem.this.searchFieldCaption.getVisibility() == 0) {
                            ActionBarMenuItem.this.searchFieldCaption.setVisibility(8);
                            if (ActionBarMenuItem.this.listener != null) {
                                ActionBarMenuItem.this.listener.onCaptionCleared();
                            }
                        }
                        ActionBarMenuItem.this.searchField.requestFocus();
                        AndroidUtilities.showKeyboard(ActionBarMenuItem.this.searchField);
                    }
                });
                this.searchContainer.addView(this.clearButton, LayoutHelper.createFrame(48, -1, 21));
            }
            this.isSearchField = z;
        }
        return this;
    }

    public void setLayoutInScreen(boolean z) {
        this.layoutInScreen = z;
    }

    public ActionBarMenuItem setOverrideMenuClick(boolean z) {
        this.overrideMenuClick = z;
        return this;
    }

    public void setPopupAnimationEnabled(boolean z) {
        if (this.popupWindow != null) {
            this.popupWindow.setAnimationEnabled(z);
        }
        this.animationEnabled = z;
    }

    public void setPopupItemsColor(int i) {
        if (this.popupLayout != null) {
            int childCount = this.popupLayout.linearLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = this.popupLayout.linearLayout.getChildAt(i2);
                if (childAt instanceof TextView) {
                    ((TextView) childAt).setTextColor(i);
                }
            }
        }
    }

    public void setSearchFieldCaption(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.searchFieldCaption.setVisibility(8);
        } else {
            this.searchFieldCaption.setVisibility(0);
            this.searchFieldCaption.setText(charSequence);
        }
        if (this.clearButton == null) {
        }
    }

    public void setShowSearchProgress(boolean z) {
        if (this.progressDrawable != null) {
            if (z) {
                this.progressDrawable.startAnimation();
            } else {
                this.progressDrawable.stopAnimation();
            }
        }
    }

    public void setSubMenuOpenSide(int i) {
        this.subMenuOpenSide = i;
    }

    public void showSubItem(int i) {
        View findViewWithTag = this.popupLayout.findViewWithTag(Integer.valueOf(i));
        if (findViewWithTag != null) {
            findViewWithTag.setVisibility(0);
        }
    }

    public boolean toggleSearch(boolean z) {
        if (this.searchContainer == null) {
            return false;
        }
        if (this.searchContainer.getVisibility() != 0) {
            this.searchContainer.setVisibility(0);
            setVisibility(8);
            this.searchField.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.searchField.requestFocus();
            if (z) {
                AndroidUtilities.showKeyboard(this.searchField);
            }
            if (this.listener != null) {
                this.listener.onSearchExpand();
            }
            return true;
        } else if (this.listener != null && (this.listener == null || !this.listener.canCollapseSearch())) {
            return false;
        } else {
            this.searchContainer.setVisibility(8);
            this.searchField.clearFocus();
            setVisibility(0);
            if (z) {
                AndroidUtilities.hideKeyboard(this.searchField);
            }
            if (this.listener == null) {
                return false;
            }
            this.listener.onSearchCollapse();
            return false;
        }
    }

    public void toggleSubMenu() {
        if (this.popupLayout != null) {
            if (this.showMenuRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(this.showMenuRunnable);
                this.showMenuRunnable = null;
            }
            if (this.popupWindow == null || !this.popupWindow.isShowing()) {
                if (this.popupWindow == null) {
                    this.popupWindow = new ActionBarPopupWindow(this.popupLayout, -2, -2);
                    if (!this.animationEnabled || VERSION.SDK_INT < 19) {
                        this.popupWindow.setAnimationStyle(R.style.PopupAnimation);
                    } else {
                        this.popupWindow.setAnimationStyle(0);
                    }
                    if (!this.animationEnabled) {
                        this.popupWindow.setAnimationEnabled(this.animationEnabled);
                    }
                    this.popupWindow.setOutsideTouchable(true);
                    this.popupWindow.setClippingEnabled(true);
                    if (this.layoutInScreen) {
                        try {
                            if (layoutInScreenMethod == null) {
                                layoutInScreenMethod = PopupWindow.class.getDeclaredMethod("setLayoutInScreenEnabled", new Class[]{Boolean.TYPE});
                                layoutInScreenMethod.setAccessible(true);
                            }
                            layoutInScreenMethod.invoke(this.popupWindow, new Object[]{Boolean.valueOf(true)});
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                    this.popupWindow.setInputMethodMode(2);
                    this.popupWindow.setSoftInputMode(0);
                    this.popupLayout.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), Integer.MIN_VALUE));
                    this.popupWindow.getContentView().setFocusableInTouchMode(true);
                    this.popupWindow.getContentView().setOnKeyListener(new C38115());
                }
                this.processedPopupClick = false;
                this.popupWindow.setFocusable(true);
                if (this.popupLayout.getMeasuredWidth() == 0) {
                    updateOrShowPopup(true, true);
                } else {
                    updateOrShowPopup(true, false);
                }
                this.popupWindow.startAnimation();
                return;
            }
            this.popupWindow.dismiss();
        }
    }
}
