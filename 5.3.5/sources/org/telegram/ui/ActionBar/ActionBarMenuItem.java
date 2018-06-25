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
import java.lang.reflect.Method;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
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

    public static class ActionBarMenuItemSearchListener {
        public void onSearchExpand() {
        }

        public boolean canCollapseSearch() {
            return true;
        }

        public void onSearchCollapse() {
        }

        public void onTextChanged(EditText editText) {
        }

        public void onSearchPressed(EditText editText) {
        }

        public void onCaptionCleared() {
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarMenuItem$1 */
    class C19691 implements Runnable {
        C19691() {
        }

        public void run() {
            if (ActionBarMenuItem.this.getParent() != null) {
                ActionBarMenuItem.this.getParent().requestDisallowInterceptTouchEvent(true);
            }
            ActionBarMenuItem.this.toggleSubMenu();
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarMenuItem$2 */
    class C19702 implements OnTouchListener {
        C19702() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() == 0 && ActionBarMenuItem.this.popupWindow != null && ActionBarMenuItem.this.popupWindow.isShowing()) {
                v.getHitRect(ActionBarMenuItem.this.rect);
                if (!ActionBarMenuItem.this.rect.contains((int) event.getX(), (int) event.getY())) {
                    ActionBarMenuItem.this.popupWindow.dismiss();
                }
            }
            return false;
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarMenuItem$3 */
    class C19713 implements OnDispatchKeyEventListener {
        C19713() {
        }

        public void onDispatchKeyEvent(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == 4 && keyEvent.getRepeatCount() == 0 && ActionBarMenuItem.this.popupWindow != null && ActionBarMenuItem.this.popupWindow.isShowing()) {
                ActionBarMenuItem.this.popupWindow.dismiss();
            }
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarMenuItem$4 */
    class C19724 implements OnClickListener {
        C19724() {
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
    class C19735 implements OnKeyListener {
        C19735() {
        }

        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode != 82 || event.getRepeatCount() != 0 || event.getAction() != 1 || ActionBarMenuItem.this.popupWindow == null || !ActionBarMenuItem.this.popupWindow.isShowing()) {
                return false;
            }
            ActionBarMenuItem.this.popupWindow.dismiss();
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarMenuItem$8 */
    class C19768 implements Callback {
        C19768() {
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }
    }

    /* renamed from: org.telegram.ui.ActionBar.ActionBarMenuItem$9 */
    class C19779 implements OnEditorActionListener {
        C19779() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (event != null && ((event.getAction() == 1 && event.getKeyCode() == 84) || (event.getAction() == 0 && event.getKeyCode() == 66))) {
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

    public ActionBarMenuItem(Context context, ActionBarMenu menu, int backgroundColor, int iconColor) {
        super(context);
        if (backgroundColor != 0) {
            setBackgroundDrawable(Theme.createSelectorDrawable(backgroundColor));
        }
        this.parentMenu = menu;
        this.iconView = new ImageView(context);
        this.iconView.setScaleType(ScaleType.CENTER);
        addView(this.iconView, LayoutHelper.createFrame(-1, -1.0f));
        if (iconColor != 0) {
            this.iconView.setColorFilter(new PorterDuffColorFilter(iconColor, Mode.MULTIPLY));
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == 0) {
            if (hasSubMenu() && (this.popupWindow == null || !(this.popupWindow == null || this.popupWindow.isShowing()))) {
                this.showMenuRunnable = new C19691();
                AndroidUtilities.runOnUIThread(this.showMenuRunnable, 200);
            }
        } else if (event.getActionMasked() == 2) {
            if (!hasSubMenu() || (this.popupWindow != null && (this.popupWindow == null || this.popupWindow.isShowing()))) {
                if (this.popupWindow != null && this.popupWindow.isShowing()) {
                    getLocationOnScreen(this.location);
                    float x = event.getX() + ((float) this.location[0]);
                    float y = event.getY() + ((float) this.location[1]);
                    this.popupLayout.getLocationOnScreen(this.location);
                    x -= (float) this.location[0];
                    y -= (float) this.location[1];
                    this.selectedMenuView = null;
                    for (int a = 0; a < this.popupLayout.getItemsCount(); a++) {
                        View child = this.popupLayout.getItemAt(a);
                        child.getHitRect(this.rect);
                        if (((Integer) child.getTag()).intValue() < 100) {
                            if (this.rect.contains((int) x, (int) y)) {
                                child.setPressed(true);
                                child.setSelected(true);
                                if (VERSION.SDK_INT >= 21) {
                                    if (VERSION.SDK_INT == 21) {
                                        child.getBackground().setVisible(true, false);
                                    }
                                    child.drawableHotspotChanged(x, y - ((float) child.getTop()));
                                }
                                this.selectedMenuView = child;
                            } else {
                                child.setPressed(false);
                                child.setSelected(false);
                                if (VERSION.SDK_INT == 21) {
                                    child.getBackground().setVisible(false, false);
                                }
                            }
                        }
                    }
                }
            } else if (event.getY() > ((float) getHeight())) {
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                toggleSubMenu();
                return true;
            }
        } else if (this.popupWindow != null && this.popupWindow.isShowing() && event.getActionMasked() == 1) {
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
        return super.onTouchEvent(event);
    }

    public void setDelegate(ActionBarMenuItemDelegate delegate) {
        this.delegate = delegate;
    }

    public void setIconColor(int color) {
        this.iconView.setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
        if (this.clearButton != null) {
            this.clearButton.setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
        }
    }

    public void setSubMenuOpenSide(int side) {
        this.subMenuOpenSide = side;
    }

    public void setLayoutInScreen(boolean value) {
        this.layoutInScreen = value;
    }

    private void createPopupLayout() {
        if (this.popupLayout == null) {
            this.rect = new Rect();
            this.location = new int[2];
            this.popupLayout = new ActionBarPopupWindowLayout(getContext());
            this.popupLayout.setOnTouchListener(new C19702());
            this.popupLayout.setDispatchKeyEventListener(new C19713());
        }
    }

    public void addSubItem(View view, int width, int height) {
        createPopupLayout();
        this.popupLayout.addView(view, new LayoutParams(width, height));
    }

    public TextView addSubItem(int id, String text) {
        createPopupLayout();
        TextView textView = new TextView(getContext());
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
        textView.setTag(Integer.valueOf(id));
        textView.setText(text);
        this.popupLayout.addView(textView);
        LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
        if (LocaleController.isRTL) {
            layoutParams.gravity = 5;
        }
        layoutParams.width = -1;
        layoutParams.height = AndroidUtilities.dp(48.0f);
        textView.setLayoutParams(layoutParams);
        textView.setOnClickListener(new C19724());
        this.menuHeight += layoutParams.height;
        return textView;
    }

    public void redrawPopup(int color) {
        if (this.popupLayout != null) {
            this.popupLayout.backgroundDrawable.setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
            this.popupLayout.invalidate();
        }
    }

    public void setPopupItemsColor(int color) {
        if (this.popupLayout != null) {
            int count = this.popupLayout.linearLayout.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = this.popupLayout.linearLayout.getChildAt(a);
                if (child instanceof TextView) {
                    ((TextView) child).setTextColor(color);
                }
            }
        }
    }

    public boolean hasSubMenu() {
        return this.popupLayout != null;
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
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                    this.popupWindow.setInputMethodMode(2);
                    this.popupWindow.setSoftInputMode(0);
                    this.popupLayout.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), Integer.MIN_VALUE));
                    this.popupWindow.getContentView().setFocusableInTouchMode(true);
                    this.popupWindow.getContentView().setOnKeyListener(new C19735());
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

    public void openSearch(boolean openKeyboard) {
        if (this.searchContainer != null && this.searchContainer.getVisibility() != 0 && this.parentMenu != null) {
            this.parentMenu.parentActionBar.onSearchFieldVisibilityChanged(toggleSearch(openKeyboard));
        }
    }

    public boolean toggleSearch(boolean openKeyboard) {
        if (this.searchContainer == null) {
            return false;
        }
        if (this.searchContainer.getVisibility() != 0) {
            this.searchContainer.setVisibility(0);
            setVisibility(8);
            this.searchField.setText("");
            this.searchField.requestFocus();
            if (openKeyboard) {
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
            if (openKeyboard) {
                AndroidUtilities.hideKeyboard(this.searchField);
            }
            if (this.listener == null) {
                return false;
            }
            this.listener.onSearchCollapse();
            return false;
        }
    }

    public void closeSubMenu() {
        if (this.popupWindow != null && this.popupWindow.isShowing()) {
            this.popupWindow.dismiss();
        }
    }

    public void setIcon(int resId) {
        this.iconView.setImageResource(resId);
    }

    public void setIcon(Drawable drawable) {
        this.iconView.setImageDrawable(drawable);
    }

    public ImageView getImageView() {
        return this.iconView;
    }

    public EditTextBoldCursor getSearchField() {
        return this.searchField;
    }

    public ActionBarMenuItem setOverrideMenuClick(boolean value) {
        this.overrideMenuClick = value;
        return this;
    }

    public ActionBarMenuItem setIsSearchField(boolean value) {
        if (this.parentMenu != null) {
            if (value && this.searchContainer == null) {
                this.searchContainer = new FrameLayout(getContext()) {
                    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                        int width;
                        measureChildWithMargins(ActionBarMenuItem.this.clearButton, widthMeasureSpec, 0, heightMeasureSpec, 0);
                        if (ActionBarMenuItem.this.searchFieldCaption.getVisibility() == 0) {
                            measureChildWithMargins(ActionBarMenuItem.this.searchFieldCaption, widthMeasureSpec, MeasureSpec.getSize(widthMeasureSpec) / 2, heightMeasureSpec, 0);
                            width = ActionBarMenuItem.this.searchFieldCaption.getMeasuredWidth() + AndroidUtilities.dp(4.0f);
                        } else {
                            width = 0;
                        }
                        measureChildWithMargins(ActionBarMenuItem.this.searchField, widthMeasureSpec, width, heightMeasureSpec, 0);
                        int w = MeasureSpec.getSize(widthMeasureSpec);
                        int h = MeasureSpec.getSize(heightMeasureSpec);
                        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
                    }

                    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                        int x;
                        super.onLayout(changed, left, top, right, bottom);
                        if (LocaleController.isRTL) {
                            x = 0;
                        } else if (ActionBarMenuItem.this.searchFieldCaption.getVisibility() == 0) {
                            x = ActionBarMenuItem.this.searchFieldCaption.getMeasuredWidth() + AndroidUtilities.dp(4.0f);
                        } else {
                            x = 0;
                        }
                        ActionBarMenuItem.this.searchField.layout(x, ActionBarMenuItem.this.searchField.getTop(), ActionBarMenuItem.this.searchField.getMeasuredWidth() + x, ActionBarMenuItem.this.searchField.getBottom());
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
                    public boolean onKeyDown(int keyCode, KeyEvent event) {
                        if (keyCode != 67 || ActionBarMenuItem.this.searchField.length() != 0 || ActionBarMenuItem.this.searchFieldCaption.getVisibility() != 0 || ActionBarMenuItem.this.searchFieldCaption.length() <= 0) {
                            return super.onKeyDown(keyCode, event);
                        }
                        ActionBarMenuItem.this.clearButton.callOnClick();
                        return true;
                    }

                    public boolean dispatchKeyEvent(KeyEvent event) {
                        return super.dispatchKeyEvent(event);
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
                    this.searchField.setCustomSelectionActionModeCallback(new C19768());
                }
                this.searchField.setOnEditorActionListener(new C19779());
                this.searchField.addTextChangedListener(new TextWatcher() {
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
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

                    public void afterTextChanged(Editable s) {
                    }
                });
                this.searchField.setImeOptions(33554435);
                this.searchField.setTextIsSelectable(false);
                if (LocaleController.isRTL) {
                    this.searchContainer.addView(this.searchField, LayoutHelper.createFrame(-1, 36.0f, 16, 0.0f, 0.0f, 48.0f, 0.0f));
                    this.searchContainer.addView(this.searchFieldCaption, LayoutHelper.createFrame(-2, 36.0f, 21, 0.0f, 5.5f, 48.0f, 0.0f));
                } else {
                    this.searchContainer.addView(this.searchFieldCaption, LayoutHelper.createFrame(-2, 36.0f, 19, 0.0f, 5.5f, 0.0f, 0.0f));
                    this.searchContainer.addView(this.searchField, LayoutHelper.createFrame(-1, 36.0f, 16, 0.0f, 0.0f, 48.0f, 0.0f));
                }
                this.clearButton = new ImageView(getContext());
                ImageView imageView = this.clearButton;
                Drawable closeProgressDrawable2 = new CloseProgressDrawable2();
                this.progressDrawable = closeProgressDrawable2;
                imageView.setImageDrawable(closeProgressDrawable2);
                this.clearButton.setColorFilter(new PorterDuffColorFilter(this.parentMenu.parentActionBar.itemsColor, Mode.MULTIPLY));
                this.clearButton.setScaleType(ScaleType.CENTER);
                this.clearButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (ActionBarMenuItem.this.searchField.length() != 0) {
                            ActionBarMenuItem.this.searchField.setText("");
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
            this.isSearchField = value;
        }
        return this;
    }

    public void setShowSearchProgress(boolean show) {
        if (this.progressDrawable != null) {
            if (show) {
                this.progressDrawable.startAnimation();
            } else {
                this.progressDrawable.stopAnimation();
            }
        }
    }

    public void setSearchFieldCaption(CharSequence caption) {
        if (TextUtils.isEmpty(caption)) {
            this.searchFieldCaption.setVisibility(8);
        } else {
            this.searchFieldCaption.setVisibility(0);
            this.searchFieldCaption.setText(caption);
        }
        if (this.clearButton == null) {
        }
    }

    public void setIgnoreOnTextChange() {
        this.ignoreOnTextChange = true;
    }

    public boolean isSearchField() {
        return this.isSearchField;
    }

    public void clearSearchText() {
        if (this.searchField != null) {
            this.searchField.setText("");
        }
    }

    public ActionBarMenuItem setActionBarMenuItemSearchListener(ActionBarMenuItemSearchListener listener) {
        this.listener = listener;
        return this;
    }

    public ActionBarMenuItem setAllowCloseAnimation(boolean value) {
        this.allowCloseAnimation = value;
        return this;
    }

    public void setPopupAnimationEnabled(boolean value) {
        if (this.popupWindow != null) {
            this.popupWindow.setAnimationEnabled(value);
        }
        this.animationEnabled = value;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.popupWindow != null && this.popupWindow.isShowing()) {
            updateOrShowPopup(false, true);
        }
    }

    public void setAdditionalOffset(int value) {
        this.additionalOffset = value;
    }

    private void updateOrShowPopup(boolean show, boolean update) {
        int offsetY;
        if (this.parentMenu != null) {
            offsetY = (-this.parentMenu.parentActionBar.getMeasuredHeight()) + this.parentMenu.getTop();
        } else {
            float scaleY = getScaleY();
            offsetY = (-((int) ((((float) getMeasuredHeight()) * scaleY) - (getTranslationY() / scaleY)))) + this.additionalOffset;
        }
        if (show) {
            this.popupLayout.scrollToTop();
        }
        View parent;
        if (this.parentMenu != null) {
            parent = this.parentMenu.parentActionBar;
            if (this.subMenuOpenSide == 0) {
                if (show) {
                    this.popupWindow.showAsDropDown(parent, (((getLeft() + this.parentMenu.getLeft()) + getMeasuredWidth()) - this.popupLayout.getMeasuredWidth()) + ((int) getTranslationX()), offsetY);
                }
                if (update) {
                    this.popupWindow.update(parent, (((getLeft() + this.parentMenu.getLeft()) + getMeasuredWidth()) - this.popupLayout.getMeasuredWidth()) + ((int) getTranslationX()), offsetY, -1, -1);
                    return;
                }
                return;
            }
            if (show) {
                this.popupWindow.showAsDropDown(parent, (getLeft() - AndroidUtilities.dp(8.0f)) + ((int) getTranslationX()), offsetY);
            }
            if (update) {
                this.popupWindow.update(parent, (getLeft() - AndroidUtilities.dp(8.0f)) + ((int) getTranslationX()), offsetY, -1, -1);
            }
        } else if (this.subMenuOpenSide != 0) {
            if (show) {
                this.popupWindow.showAsDropDown(this, -AndroidUtilities.dp(8.0f), offsetY);
            }
            if (update) {
                this.popupWindow.update(this, -AndroidUtilities.dp(8.0f), offsetY, -1, -1);
            }
        } else if (getParent() != null) {
            parent = (View) getParent();
            if (show) {
                this.popupWindow.showAsDropDown(parent, (getLeft() + getMeasuredWidth()) - this.popupLayout.getMeasuredWidth(), offsetY);
            }
            if (update) {
                this.popupWindow.update(parent, (getLeft() + getMeasuredWidth()) - this.popupLayout.getMeasuredWidth(), offsetY, -1, -1);
            }
        }
    }

    public void hideSubItem(int id) {
        View view = this.popupLayout.findViewWithTag(Integer.valueOf(id));
        if (view != null) {
            view.setVisibility(8);
        }
    }

    public void showSubItem(int id) {
        View view = this.popupLayout.findViewWithTag(Integer.valueOf(id));
        if (view != null) {
            view.setVisibility(0);
        }
    }

    public TextView getItemById(int id) {
        if (getpopupLayout() != null) {
            return (TextView) getpopupLayout().findViewWithTag(Integer.valueOf(id));
        }
        return null;
    }

    public View getpopupLayout() {
        if (this.popupLayout != null) {
            return this.popupLayout;
        }
        return null;
    }
}
