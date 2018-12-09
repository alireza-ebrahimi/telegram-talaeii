package org.telegram.ui;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.TL_account_updateProfile;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;

public class ChangeNameActivity extends BaseFragment {
    private static final int done_button = 1;
    private View doneButton;
    private EditTextBoldCursor firstNameField;
    private View headerLabelView;
    private EditTextBoldCursor lastNameField;

    /* renamed from: org.telegram.ui.ChangeNameActivity$1 */
    class C40491 extends ActionBarMenuOnItemClick {
        C40491() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                ChangeNameActivity.this.finishFragment();
            } else if (i == 1 && ChangeNameActivity.this.firstNameField.getText().length() != 0) {
                ChangeNameActivity.this.saveName();
                ChangeNameActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChangeNameActivity$2 */
    class C40502 implements OnTouchListener {
        C40502() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ChangeNameActivity$3 */
    class C40513 implements OnEditorActionListener {
        C40513() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 5) {
                return false;
            }
            ChangeNameActivity.this.lastNameField.requestFocus();
            ChangeNameActivity.this.lastNameField.setSelection(ChangeNameActivity.this.lastNameField.length());
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ChangeNameActivity$4 */
    class C40524 implements OnEditorActionListener {
        C40524() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 6) {
                return false;
            }
            ChangeNameActivity.this.doneButton.performClick();
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ChangeNameActivity$5 */
    class C40535 implements RequestDelegate {
        C40535() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        }
    }

    /* renamed from: org.telegram.ui.ChangeNameActivity$6 */
    class C40546 implements Runnable {
        C40546() {
        }

        public void run() {
            if (ChangeNameActivity.this.firstNameField != null) {
                ChangeNameActivity.this.firstNameField.requestFocus();
                AndroidUtilities.showKeyboard(ChangeNameActivity.this.firstNameField);
            }
        }
    }

    private void saveName() {
        User currentUser = UserConfig.getCurrentUser();
        if (currentUser != null && this.lastNameField.getText() != null && this.firstNameField.getText() != null) {
            String obj = this.firstNameField.getText().toString();
            String obj2 = this.lastNameField.getText().toString();
            if (currentUser.first_name == null || !currentUser.first_name.equals(obj) || currentUser.last_name == null || !currentUser.last_name.equals(obj2)) {
                TLObject tL_account_updateProfile = new TL_account_updateProfile();
                tL_account_updateProfile.flags = 3;
                tL_account_updateProfile.first_name = obj;
                currentUser.first_name = obj;
                tL_account_updateProfile.last_name = obj2;
                currentUser.last_name = obj2;
                currentUser = MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId()));
                if (currentUser != null) {
                    currentUser.first_name = tL_account_updateProfile.first_name;
                    currentUser.last_name = tL_account_updateProfile.last_name;
                }
                UserConfig.saveConfig(true);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(1)});
                ConnectionsManager.getInstance().sendRequest(tL_account_updateProfile, new C40535());
            }
        }
    }

    public View createView(Context context) {
        int i = 5;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("EditName", R.string.EditName));
        this.actionBar.setActionBarMenuOnItemClick(new C40491());
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        User user = MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId()));
        User currentUser = user == null ? UserConfig.getCurrentUser() : user;
        View linearLayout = new LinearLayout(context);
        this.fragmentView = linearLayout;
        this.fragmentView.setLayoutParams(new LayoutParams(-1, -1));
        ((LinearLayout) this.fragmentView).setOrientation(1);
        this.fragmentView.setOnTouchListener(new C40502());
        this.firstNameField = new EditTextBoldCursor(context);
        this.firstNameField.setTextSize(1, 18.0f);
        this.firstNameField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.firstNameField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.firstNameField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
        this.firstNameField.setMaxLines(1);
        this.firstNameField.setLines(1);
        this.firstNameField.setSingleLine(true);
        this.firstNameField.setGravity(LocaleController.isRTL ? 5 : 3);
        this.firstNameField.setInputType(49152);
        this.firstNameField.setImeOptions(5);
        this.firstNameField.setHint(LocaleController.getString("FirstName", R.string.FirstName));
        this.firstNameField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.firstNameField.setCursorSize(AndroidUtilities.dp(20.0f));
        this.firstNameField.setCursorWidth(1.5f);
        linearLayout.addView(this.firstNameField, LayoutHelper.createLinear(-1, 36, 24.0f, 24.0f, 24.0f, BitmapDescriptorFactory.HUE_RED));
        this.firstNameField.setOnEditorActionListener(new C40513());
        this.lastNameField = new EditTextBoldCursor(context);
        this.lastNameField.setTextSize(1, 18.0f);
        this.lastNameField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.lastNameField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.lastNameField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
        this.lastNameField.setMaxLines(1);
        this.lastNameField.setLines(1);
        this.lastNameField.setSingleLine(true);
        EditTextBoldCursor editTextBoldCursor = this.lastNameField;
        if (!LocaleController.isRTL) {
            i = 3;
        }
        editTextBoldCursor.setGravity(i);
        this.lastNameField.setInputType(49152);
        this.lastNameField.setImeOptions(6);
        this.lastNameField.setHint(LocaleController.getString("LastName", R.string.LastName));
        this.lastNameField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.lastNameField.setCursorSize(AndroidUtilities.dp(20.0f));
        this.lastNameField.setCursorWidth(1.5f);
        linearLayout.addView(this.lastNameField, LayoutHelper.createLinear(-1, 36, 24.0f, 16.0f, 24.0f, BitmapDescriptorFactory.HUE_RED));
        this.lastNameField.setOnEditorActionListener(new C40524());
        if (currentUser != null) {
            this.firstNameField.setText(currentUser.first_name);
            this.firstNameField.setSelection(this.firstNameField.length());
            this.lastNameField.setText(currentUser.last_name);
        }
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[13];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[5] = new ThemeDescription(this.firstNameField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[6] = new ThemeDescription(this.firstNameField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        themeDescriptionArr[7] = new ThemeDescription(this.firstNameField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        themeDescriptionArr[8] = new ThemeDescription(this.firstNameField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        themeDescriptionArr[9] = new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[10] = new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        themeDescriptionArr[11] = new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        themeDescriptionArr[12] = new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        return themeDescriptionArr;
    }

    public void onResume() {
        super.onResume();
        if (!ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean("view_animations", true)) {
            this.firstNameField.requestFocus();
            AndroidUtilities.showKeyboard(this.firstNameField);
        }
    }

    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z) {
            AndroidUtilities.runOnUIThread(new C40546(), 100);
        }
    }
}
