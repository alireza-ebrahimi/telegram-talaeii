package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Vibrator;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_userFull;
import org.telegram.tgnet.TLRPC.TL_account_updateProfile;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;

public class ChangeBioActivity extends BaseFragment {
    private static final int done_button = 1;
    private TextView checkTextView;
    private View doneButton;
    private EditTextBoldCursor firstNameField;
    private TextView helpTextView;

    /* renamed from: org.telegram.ui.ChangeBioActivity$1 */
    class C40301 extends ActionBarMenuOnItemClick {
        C40301() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                ChangeBioActivity.this.finishFragment();
            } else if (i == 1) {
                ChangeBioActivity.this.saveName();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChangeBioActivity$2 */
    class C40312 implements OnTouchListener {
        C40312() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ChangeBioActivity$4 */
    class C40334 implements OnEditorActionListener {
        C40334() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 6 || ChangeBioActivity.this.doneButton == null) {
                return false;
            }
            ChangeBioActivity.this.doneButton.performClick();
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ChangeBioActivity$5 */
    class C40345 implements TextWatcher {
        C40345() {
        }

        public void afterTextChanged(Editable editable) {
            ChangeBioActivity.this.checkTextView.setText(TtmlNode.ANONYMOUS_REGION_ID + (70 - ChangeBioActivity.this.firstNameField.length()));
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    private void saveName() {
        final TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(UserConfig.getClientUserId());
        if (getParentActivity() != null && userFull != null) {
            String str = userFull.about;
            if (str == null) {
                str = TtmlNode.ANONYMOUS_REGION_ID;
            }
            final String replace = this.firstNameField.getText().toString().replace("\n", TtmlNode.ANONYMOUS_REGION_ID);
            if (str.equals(replace)) {
                finishFragment();
                return;
            }
            final AlertDialog alertDialog = new AlertDialog(getParentActivity(), 1);
            alertDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            final TLObject tL_account_updateProfile = new TL_account_updateProfile();
            tL_account_updateProfile.about = replace;
            tL_account_updateProfile.flags |= 4;
            final int sendRequest = ConnectionsManager.getInstance().sendRequest(tL_account_updateProfile, new RequestDelegate() {
                public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        final User user = (User) tLObject;
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                try {
                                    alertDialog.dismiss();
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                                userFull.about = replace;
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.userInfoDidLoaded, new Object[]{Integer.valueOf(user.id), userFull});
                                ChangeBioActivity.this.finishFragment();
                            }
                        });
                        return;
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            try {
                                alertDialog.dismiss();
                            } catch (Throwable e) {
                                FileLog.e(e);
                            }
                            AlertsCreator.processError(tLRPC$TL_error, ChangeBioActivity.this, tL_account_updateProfile, new Object[0]);
                        }
                    });
                }
            }, 2);
            ConnectionsManager.getInstance().bindRequestToGuid(sendRequest, this.classGuid);
            alertDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ConnectionsManager.getInstance().cancelRequest(sendRequest, true);
                    try {
                        dialogInterface.dismiss();
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                }
            });
            alertDialog.show();
        }
    }

    public View createView(Context context) {
        float f = 24.0f;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("UserBio", R.string.UserBio));
        this.actionBar.setActionBarMenuOnItemClick(new C40301());
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.fragmentView = new LinearLayout(context);
        LinearLayout linearLayout = (LinearLayout) this.fragmentView;
        linearLayout.setOrientation(1);
        this.fragmentView.setOnTouchListener(new C40312());
        View frameLayout = new FrameLayout(context);
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 24.0f, 24.0f, 20.0f, BitmapDescriptorFactory.HUE_RED));
        this.firstNameField = new EditTextBoldCursor(context);
        this.firstNameField.setTextSize(1, 18.0f);
        this.firstNameField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.firstNameField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.firstNameField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
        this.firstNameField.setMaxLines(4);
        EditTextBoldCursor editTextBoldCursor = this.firstNameField;
        int dp = AndroidUtilities.dp(LocaleController.isRTL ? 24.0f : BitmapDescriptorFactory.HUE_RED);
        if (LocaleController.isRTL) {
            f = BitmapDescriptorFactory.HUE_RED;
        }
        editTextBoldCursor.setPadding(dp, 0, AndroidUtilities.dp(f), AndroidUtilities.dp(6.0f));
        this.firstNameField.setGravity(LocaleController.isRTL ? 5 : 3);
        this.firstNameField.setImeOptions(ErrorDialogData.BINDER_CRASH);
        this.firstNameField.setInputType(147457);
        this.firstNameField.setImeOptions(6);
        this.firstNameField.setFilters(new InputFilter[]{new LengthFilter(70) {
            public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                if (charSequence == null || TextUtils.indexOf(charSequence, '\n') == -1) {
                    CharSequence filter = super.filter(charSequence, i, i2, spanned, i3, i4);
                    if (!(filter == null || charSequence == null || filter.length() == charSequence.length())) {
                        Vibrator vibrator = (Vibrator) ChangeBioActivity.this.getParentActivity().getSystemService("vibrator");
                        if (vibrator != null) {
                            vibrator.vibrate(200);
                        }
                        AndroidUtilities.shakeView(ChangeBioActivity.this.checkTextView, 2.0f, 0);
                    }
                    return filter;
                }
                ChangeBioActivity.this.doneButton.performClick();
                return TtmlNode.ANONYMOUS_REGION_ID;
            }
        }});
        this.firstNameField.setMinHeight(AndroidUtilities.dp(36.0f));
        this.firstNameField.setHint(LocaleController.getString("UserBio", R.string.UserBio));
        this.firstNameField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.firstNameField.setCursorSize(AndroidUtilities.dp(20.0f));
        this.firstNameField.setCursorWidth(1.5f);
        this.firstNameField.setOnEditorActionListener(new C40334());
        this.firstNameField.addTextChangedListener(new C40345());
        frameLayout.addView(this.firstNameField, LayoutHelper.createFrame(-1, -2.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 4.0f, BitmapDescriptorFactory.HUE_RED));
        this.checkTextView = new TextView(context);
        this.checkTextView.setTextSize(1, 15.0f);
        this.checkTextView.setText("70");
        this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText4));
        frameLayout.addView(this.checkTextView, LayoutHelper.createFrame(-2, -2.0f, LocaleController.isRTL ? 3 : 5, BitmapDescriptorFactory.HUE_RED, 4.0f, 4.0f, BitmapDescriptorFactory.HUE_RED));
        this.helpTextView = new TextView(context);
        this.helpTextView.setTextSize(1, 15.0f);
        this.helpTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText8));
        this.helpTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        this.helpTextView.setText(AndroidUtilities.replaceTags(LocaleController.getString("UserBioInfo", R.string.UserBioInfo)));
        linearLayout.addView(this.helpTextView, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3, 24, 10, 24, 0));
        TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(UserConfig.getClientUserId());
        if (!(userFull == null || userFull.about == null)) {
            this.firstNameField.setText(userFull.about);
            this.firstNameField.setSelection(this.firstNameField.length());
        }
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[11];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[5] = new ThemeDescription(this.firstNameField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[6] = new ThemeDescription(this.firstNameField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        themeDescriptionArr[7] = new ThemeDescription(this.firstNameField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        themeDescriptionArr[8] = new ThemeDescription(this.firstNameField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        themeDescriptionArr[9] = new ThemeDescription(this.helpTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText8);
        themeDescriptionArr[10] = new ThemeDescription(this.checkTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
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
            this.firstNameField.requestFocus();
            AndroidUtilities.showKeyboard(this.firstNameField);
        }
    }
}
