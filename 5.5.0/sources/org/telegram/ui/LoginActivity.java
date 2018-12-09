package org.telegram.ui;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p151g.C2820e;
import org.telegram.customization.util.C2872c;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.TL_account_deleteAccount;
import org.telegram.tgnet.TLRPC.TL_account_getPassword;
import org.telegram.tgnet.TLRPC.TL_account_password;
import org.telegram.tgnet.TLRPC.TL_auth_authorization;
import org.telegram.tgnet.TLRPC.TL_auth_cancelCode;
import org.telegram.tgnet.TLRPC.TL_auth_checkPassword;
import org.telegram.tgnet.TLRPC.TL_auth_codeTypeCall;
import org.telegram.tgnet.TLRPC.TL_auth_codeTypeFlashCall;
import org.telegram.tgnet.TLRPC.TL_auth_codeTypeSms;
import org.telegram.tgnet.TLRPC.TL_auth_passwordRecovery;
import org.telegram.tgnet.TLRPC.TL_auth_recoverPassword;
import org.telegram.tgnet.TLRPC.TL_auth_requestPasswordRecovery;
import org.telegram.tgnet.TLRPC.TL_auth_resendCode;
import org.telegram.tgnet.TLRPC.TL_auth_sendCode;
import org.telegram.tgnet.TLRPC.TL_auth_sentCode;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeApp;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeCall;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeFlashCall;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeSms;
import org.telegram.tgnet.TLRPC.TL_auth_signIn;
import org.telegram.tgnet.TLRPC.TL_auth_signUp;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.HintEditText;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.SlideView;
import org.telegram.ui.CountrySelectActivity.CountrySelectActivityDelegate;
import utils.p178a.C3791b;
import utils.view.FarsiTextView;

@SuppressLint({"HardwareIds"})
public class LoginActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int done_button = 1;
    boolean canContinueInNumberInsertion = true;
    private boolean checkPermissions = true;
    private boolean checkShowPermissions = true;
    private int currentViewNum;
    ProgressDialog dialogLoading;
    private View doneButton;
    private Dialog permissionsDialog;
    private ArrayList<String> permissionsItems = new ArrayList();
    private Dialog permissionsShowDialog;
    private ArrayList<String> permissionsShowItems = new ArrayList();
    private AlertDialog progressDialog;
    private SlideView[] views = new SlideView[9];

    /* renamed from: org.telegram.ui.LoginActivity$1 */
    class C48711 extends ActionBarMenuOnItemClick {
        C48711() {
        }

        public void onItemClick(int i) {
            if (i == 1) {
                if (LoginActivity.this.canContinueInNumberInsertion) {
                    LoginActivity.this.views[LoginActivity.this.currentViewNum].onNextPressed();
                    return;
                }
                Builder builder = new Builder(LoginActivity.this.getParentActivity());
                builder.setTitle(LocaleController.getString("RetryProxyHint", R.string.RetryProxyHint));
                builder.setMessage(LocaleController.getString("RetryProxyAlert", R.string.RetryProxyAlert));
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                LoginActivity.this.showDialog(builder.create());
            } else if (i == -1) {
                LoginActivity.this.onBackPressed();
            }
        }
    }

    /* renamed from: org.telegram.ui.LoginActivity$2 */
    class C48722 implements C2497d {
        C48722() {
        }

        public void onResult(Object obj, int i) {
        }
    }

    public class LoginActivityPasswordView extends SlideView {
        private TextView cancelButton;
        private EditTextBoldCursor codeField;
        private TextView confirmTextView;
        private Bundle currentParams;
        private byte[] current_salt;
        private String email_unconfirmed_pattern;
        private boolean has_recovery;
        private String hint;
        private boolean nextPressed;
        private String phoneCode;
        private String phoneHash;
        private String requestPhone;
        private TextView resetAccountButton;
        private TextView resetAccountText;

        /* renamed from: org.telegram.ui.LoginActivity$LoginActivityPasswordView$4 */
        class C48874 implements RequestDelegate {
            C48874() {
            }

            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        LoginActivity.this.needHideProgress();
                        LoginActivityPasswordView.this.nextPressed = false;
                        if (tLRPC$TL_error == null) {
                            TL_auth_authorization tL_auth_authorization = (TL_auth_authorization) tLObject;
                            ConnectionsManager.getInstance().setUserId(tL_auth_authorization.user.id);
                            UserConfig.clearConfig();
                            MessagesController.getInstance().cleanup();
                            UserConfig.setCurrentUser(tL_auth_authorization.user);
                            UserConfig.saveConfig(true);
                            MessagesStorage.getInstance().cleanup(true);
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(tL_auth_authorization.user);
                            MessagesStorage.getInstance().putUsersAndChats(arrayList, null, true, true);
                            MessagesController.getInstance().putUser(tL_auth_authorization.user, false);
                            ContactsController.getInstance().checkAppAccount();
                            MessagesController.getInstance().getBlockedUsers(true);
                            ConnectionsManager.getInstance().updateDcSettings();
                            LoginActivity.this.needFinishActivity();
                        } else if (tLRPC$TL_error.text.equals("PASSWORD_HASH_INVALID")) {
                            LoginActivityPasswordView.this.onPasscodeError(true);
                        } else if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                            int intValue = Utilities.parseInt(tLRPC$TL_error.text).intValue();
                            String formatPluralString = intValue < 60 ? LocaleController.formatPluralString("Seconds", intValue) : LocaleController.formatPluralString("Minutes", intValue / 60);
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{formatPluralString}));
                        } else {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), tLRPC$TL_error.text);
                        }
                    }
                });
            }
        }

        public LoginActivityPasswordView(Context context) {
            super(context);
            setOrientation(1);
            this.confirmTextView = new TextView(context);
            this.confirmTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.confirmTextView.setTextSize(1, 14.0f);
            this.confirmTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            this.confirmTextView.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            this.confirmTextView.setText(LocaleController.getString("LoginPasswordText", R.string.LoginPasswordText));
            addView(this.confirmTextView, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3));
            this.codeField = new EditTextBoldCursor(context);
            this.codeField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.codeField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.codeField.setCursorSize(AndroidUtilities.dp(20.0f));
            this.codeField.setCursorWidth(1.5f);
            this.codeField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.codeField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
            this.codeField.setHint(LocaleController.getString("LoginPassword", R.string.LoginPassword));
            this.codeField.setImeOptions(268435461);
            this.codeField.setTextSize(1, 18.0f);
            this.codeField.setMaxLines(1);
            this.codeField.setPadding(0, 0, 0, 0);
            this.codeField.setInputType(129);
            this.codeField.setTransformationMethod(PasswordTransformationMethod.getInstance());
            this.codeField.setTypeface(Typeface.DEFAULT);
            this.codeField.setGravity(LocaleController.isRTL ? 5 : 3);
            addView(this.codeField, LayoutHelper.createLinear(-1, 36, 1, 0, 20, 0, 0));
            this.codeField.setOnEditorActionListener(new OnEditorActionListener(LoginActivity.this) {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 5) {
                        return false;
                    }
                    LoginActivityPasswordView.this.onNextPressed();
                    return true;
                }
            });
            this.cancelButton = new TextView(context);
            this.cancelButton.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
            this.cancelButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            this.cancelButton.setText(LocaleController.getString("ForgotPassword", R.string.ForgotPassword));
            this.cancelButton.setTextSize(1, 14.0f);
            this.cancelButton.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            this.cancelButton.setPadding(0, AndroidUtilities.dp(14.0f), 0, 0);
            addView(this.cancelButton, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48));
            this.cancelButton.setOnClickListener(new OnClickListener(LoginActivity.this) {

                /* renamed from: org.telegram.ui.LoginActivity$LoginActivityPasswordView$2$1 */
                class C48801 implements RequestDelegate {
                    C48801() {
                    }

                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                LoginActivity.this.needHideProgress();
                                if (tLRPC$TL_error == null) {
                                    final TL_auth_passwordRecovery tL_auth_passwordRecovery = (TL_auth_passwordRecovery) tLObject;
                                    Builder builder = new Builder(LoginActivity.this.getParentActivity());
                                    builder.setMessage(LocaleController.formatString("RestoreEmailSent", R.string.RestoreEmailSent, new Object[]{tL_auth_passwordRecovery.email_pattern}));
                                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("email_unconfirmed_pattern", tL_auth_passwordRecovery.email_pattern);
                                            LoginActivity.this.setPage(7, true, bundle, false);
                                        }
                                    });
                                    Dialog showDialog = LoginActivity.this.showDialog(builder.create());
                                    if (showDialog != null) {
                                        showDialog.setCanceledOnTouchOutside(false);
                                        showDialog.setCancelable(false);
                                    }
                                } else if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                                    int intValue = Utilities.parseInt(tLRPC$TL_error.text).intValue();
                                    String formatPluralString = intValue < 60 ? LocaleController.formatPluralString("Seconds", intValue) : LocaleController.formatPluralString("Minutes", intValue / 60);
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{formatPluralString}));
                                } else {
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), tLRPC$TL_error.text);
                                }
                            }
                        });
                    }
                }

                public void onClick(View view) {
                    if (LoginActivityPasswordView.this.has_recovery) {
                        LoginActivity.this.needShowProgress(0);
                        ConnectionsManager.getInstance().sendRequest(new TL_auth_requestPasswordRecovery(), new C48801(), 10);
                        return;
                    }
                    LoginActivityPasswordView.this.resetAccountText.setVisibility(0);
                    LoginActivityPasswordView.this.resetAccountButton.setVisibility(0);
                    AndroidUtilities.hideKeyboard(LoginActivityPasswordView.this.codeField);
                    LoginActivity.this.needShowAlert(LocaleController.getString("RestorePasswordNoEmailTitle", R.string.RestorePasswordNoEmailTitle), LocaleController.getString("RestorePasswordNoEmailText", R.string.RestorePasswordNoEmailText));
                }
            });
            this.resetAccountButton = new TextView(context);
            this.resetAccountButton.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
            this.resetAccountButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText6));
            this.resetAccountButton.setVisibility(8);
            this.resetAccountButton.setText(LocaleController.getString("ResetMyAccount", R.string.ResetMyAccount));
            this.resetAccountButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.resetAccountButton.setTextSize(1, 14.0f);
            this.resetAccountButton.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            this.resetAccountButton.setPadding(0, AndroidUtilities.dp(14.0f), 0, 0);
            addView(this.resetAccountButton, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 0, 34, 0, 0));
            this.resetAccountButton.setOnClickListener(new OnClickListener(LoginActivity.this) {

                /* renamed from: org.telegram.ui.LoginActivity$LoginActivityPasswordView$3$1 */
                class C48841 implements DialogInterface.OnClickListener {

                    /* renamed from: org.telegram.ui.LoginActivity$LoginActivityPasswordView$3$1$1 */
                    class C48831 implements RequestDelegate {
                        C48831() {
                        }

                        public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    LoginActivity.this.needHideProgress();
                                    Bundle bundle;
                                    if (tLRPC$TL_error == null) {
                                        bundle = new Bundle();
                                        bundle.putString("phoneFormated", LoginActivityPasswordView.this.requestPhone);
                                        bundle.putString("phoneHash", LoginActivityPasswordView.this.phoneHash);
                                        bundle.putString("code", LoginActivityPasswordView.this.phoneCode);
                                        LoginActivity.this.setPage(5, true, bundle, false);
                                    } else if (tLRPC$TL_error.text.equals("2FA_RECENT_CONFIRM")) {
                                        LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("ResetAccountCancelledAlert", R.string.ResetAccountCancelledAlert));
                                    } else if (tLRPC$TL_error.text.startsWith("2FA_CONFIRM_WAIT_")) {
                                        bundle = new Bundle();
                                        bundle.putString("phoneFormated", LoginActivityPasswordView.this.requestPhone);
                                        bundle.putString("phoneHash", LoginActivityPasswordView.this.phoneHash);
                                        bundle.putString("code", LoginActivityPasswordView.this.phoneCode);
                                        bundle.putInt("startTime", ConnectionsManager.getInstance().getCurrentTime());
                                        bundle.putInt("waitTime", Utilities.parseInt(tLRPC$TL_error.text.replace("2FA_CONFIRM_WAIT_", TtmlNode.ANONYMOUS_REGION_ID)).intValue());
                                        LoginActivity.this.setPage(8, true, bundle, false);
                                    } else {
                                        LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), tLRPC$TL_error.text);
                                    }
                                }
                            });
                        }
                    }

                    C48841() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivity.this.needShowProgress(0);
                        TLObject tL_account_deleteAccount = new TL_account_deleteAccount();
                        tL_account_deleteAccount.reason = "Forgot password";
                        ConnectionsManager.getInstance().sendRequest(tL_account_deleteAccount, new C48831(), 10);
                    }
                }

                public void onClick(View view) {
                    Builder builder = new Builder(LoginActivity.this.getParentActivity());
                    builder.setMessage(LocaleController.getString("ResetMyAccountWarningText", R.string.ResetMyAccountWarningText));
                    builder.setTitle(LocaleController.getString("ResetMyAccountWarning", R.string.ResetMyAccountWarning));
                    builder.setPositiveButton(LocaleController.getString("ResetMyAccountWarningReset", R.string.ResetMyAccountWarningReset), new C48841());
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    LoginActivity.this.showDialog(builder.create());
                }
            });
            this.resetAccountText = new TextView(context);
            this.resetAccountText.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
            this.resetAccountText.setVisibility(8);
            this.resetAccountText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.resetAccountText.setText(LocaleController.getString("ResetMyAccountText", R.string.ResetMyAccountText));
            this.resetAccountText.setTextSize(1, 14.0f);
            this.resetAccountText.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            addView(this.resetAccountText, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 0, 7, 0, 14));
        }

        private void onPasscodeError(boolean z) {
            if (LoginActivity.this.getParentActivity() != null) {
                Vibrator vibrator = (Vibrator) LoginActivity.this.getParentActivity().getSystemService("vibrator");
                if (vibrator != null) {
                    vibrator.vibrate(200);
                }
                if (z) {
                    this.codeField.setText(TtmlNode.ANONYMOUS_REGION_ID);
                }
                AndroidUtilities.shakeView(this.confirmTextView, 2.0f, 0);
            }
        }

        public String getHeaderName() {
            return LocaleController.getString("LoginPassword", R.string.LoginPassword);
        }

        public boolean needBackButton() {
            return true;
        }

        public void onBackPressed() {
            this.currentParams = null;
        }

        public void onCancelPressed() {
            this.nextPressed = false;
        }

        public void onNextPressed() {
            if (!this.nextPressed) {
                String obj = this.codeField.getText().toString();
                if (obj.length() == 0) {
                    onPasscodeError(false);
                    return;
                }
                this.nextPressed = true;
                Object obj2 = null;
                try {
                    obj2 = obj.getBytes(C3446C.UTF8_NAME);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                LoginActivity.this.needShowProgress(0);
                Object obj3 = new byte[((this.current_salt.length * 2) + obj2.length)];
                System.arraycopy(this.current_salt, 0, obj3, 0, this.current_salt.length);
                System.arraycopy(obj2, 0, obj3, this.current_salt.length, obj2.length);
                System.arraycopy(this.current_salt, 0, obj3, obj3.length - this.current_salt.length, this.current_salt.length);
                TLObject tL_auth_checkPassword = new TL_auth_checkPassword();
                tL_auth_checkPassword.password_hash = Utilities.computeSHA256(obj3, 0, obj3.length);
                ConnectionsManager.getInstance().sendRequest(tL_auth_checkPassword, new C48874(), 10);
            }
        }

        public void onShow() {
            super.onShow();
            if (this.codeField != null) {
                this.codeField.requestFocus();
                this.codeField.setSelection(this.codeField.length());
                AndroidUtilities.showKeyboard(this.codeField);
            }
        }

        public void restoreStateParams(Bundle bundle) {
            this.currentParams = bundle.getBundle("passview_params");
            if (this.currentParams != null) {
                setParams(this.currentParams, true);
            }
            CharSequence string = bundle.getString("passview_code");
            if (string != null) {
                this.codeField.setText(string);
            }
        }

        public void saveStateParams(Bundle bundle) {
            String obj = this.codeField.getText().toString();
            if (obj.length() != 0) {
                bundle.putString("passview_code", obj);
            }
            if (this.currentParams != null) {
                bundle.putBundle("passview_params", this.currentParams);
            }
        }

        public void setParams(Bundle bundle, boolean z) {
            boolean z2 = true;
            if (bundle != null) {
                if (bundle.isEmpty()) {
                    this.resetAccountButton.setVisibility(0);
                    this.resetAccountText.setVisibility(0);
                    AndroidUtilities.hideKeyboard(this.codeField);
                    return;
                }
                this.resetAccountButton.setVisibility(8);
                this.resetAccountText.setVisibility(8);
                this.codeField.setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.currentParams = bundle;
                this.current_salt = Utilities.hexToBytes(this.currentParams.getString("current_salt"));
                this.hint = this.currentParams.getString("hint");
                if (this.currentParams.getInt("has_recovery") != 1) {
                    z2 = false;
                }
                this.has_recovery = z2;
                this.email_unconfirmed_pattern = this.currentParams.getString("email_unconfirmed_pattern");
                this.requestPhone = bundle.getString("phoneFormated");
                this.phoneHash = bundle.getString("phoneHash");
                this.phoneCode = bundle.getString("code");
                if (this.hint == null || this.hint.length() <= 0) {
                    this.codeField.setHint(LocaleController.getString("LoginPassword", R.string.LoginPassword));
                } else {
                    this.codeField.setHint(this.hint);
                }
            }
        }
    }

    public class LoginActivityRecoverView extends SlideView {
        private TextView cancelButton;
        private EditTextBoldCursor codeField;
        private TextView confirmTextView;
        private Bundle currentParams;
        private String email_unconfirmed_pattern;
        private boolean nextPressed;
        final /* synthetic */ LoginActivity this$0;

        /* renamed from: org.telegram.ui.LoginActivity$LoginActivityRecoverView$3 */
        class C48923 implements RequestDelegate {
            C48923() {
            }

            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        LoginActivityRecoverView.this.this$0.needHideProgress();
                        LoginActivityRecoverView.this.nextPressed = false;
                        if (tLRPC$TL_error == null) {
                            TL_auth_authorization tL_auth_authorization = (TL_auth_authorization) tLObject;
                            ConnectionsManager.getInstance().setUserId(tL_auth_authorization.user.id);
                            UserConfig.clearConfig();
                            MessagesController.getInstance().cleanup();
                            UserConfig.setCurrentUser(tL_auth_authorization.user);
                            UserConfig.saveConfig(true);
                            MessagesStorage.getInstance().cleanup(true);
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(tL_auth_authorization.user);
                            MessagesStorage.getInstance().putUsersAndChats(arrayList, null, true, true);
                            MessagesController.getInstance().putUser(tL_auth_authorization.user, false);
                            ContactsController.getInstance().checkAppAccount();
                            MessagesController.getInstance().getBlockedUsers(true);
                            ConnectionsManager.getInstance().updateDcSettings();
                            LoginActivityRecoverView.this.this$0.needFinishActivity();
                        } else if (tLRPC$TL_error.text.startsWith("CODE_INVALID")) {
                            LoginActivityRecoverView.this.onPasscodeError(true);
                        } else if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                            int intValue = Utilities.parseInt(tLRPC$TL_error.text).intValue();
                            String formatPluralString = intValue < 60 ? LocaleController.formatPluralString("Seconds", intValue) : LocaleController.formatPluralString("Minutes", intValue / 60);
                            LoginActivityRecoverView.this.this$0.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{formatPluralString}));
                        } else {
                            LoginActivityRecoverView.this.this$0.needShowAlert(LocaleController.getString("AppName", R.string.AppName), tLRPC$TL_error.text);
                        }
                    }
                });
            }
        }

        public LoginActivityRecoverView(final LoginActivity loginActivity, Context context) {
            int i = 5;
            this.this$0 = loginActivity;
            super(context);
            setOrientation(1);
            this.confirmTextView = new TextView(context);
            this.confirmTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.confirmTextView.setTextSize(1, 14.0f);
            this.confirmTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            this.confirmTextView.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            this.confirmTextView.setText(LocaleController.getString("RestoreEmailSentInfo", R.string.RestoreEmailSentInfo));
            addView(this.confirmTextView, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3));
            this.codeField = new EditTextBoldCursor(context);
            this.codeField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.codeField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.codeField.setCursorSize(AndroidUtilities.dp(20.0f));
            this.codeField.setCursorWidth(1.5f);
            this.codeField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.codeField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
            this.codeField.setHint(LocaleController.getString("PasswordCode", R.string.PasswordCode));
            this.codeField.setImeOptions(268435461);
            this.codeField.setTextSize(1, 18.0f);
            this.codeField.setMaxLines(1);
            this.codeField.setPadding(0, 0, 0, 0);
            this.codeField.setInputType(3);
            this.codeField.setTransformationMethod(PasswordTransformationMethod.getInstance());
            this.codeField.setTypeface(Typeface.DEFAULT);
            this.codeField.setGravity(LocaleController.isRTL ? 5 : 3);
            addView(this.codeField, LayoutHelper.createLinear(-1, 36, 1, 0, 20, 0, 0));
            this.codeField.setOnEditorActionListener(new OnEditorActionListener() {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 5) {
                        return false;
                    }
                    LoginActivityRecoverView.this.onNextPressed();
                    return true;
                }
            });
            this.cancelButton = new TextView(context);
            this.cancelButton.setGravity((LocaleController.isRTL ? 5 : 3) | 80);
            this.cancelButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            this.cancelButton.setTextSize(1, 14.0f);
            this.cancelButton.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            this.cancelButton.setPadding(0, AndroidUtilities.dp(14.0f), 0, 0);
            View view = this.cancelButton;
            if (!LocaleController.isRTL) {
                i = 3;
            }
            addView(view, LayoutHelper.createLinear(-2, -2, i | 80, 0, 0, 0, 14));
            this.cancelButton.setOnClickListener(new OnClickListener() {

                /* renamed from: org.telegram.ui.LoginActivity$LoginActivityRecoverView$2$1 */
                class C48891 implements DialogInterface.OnClickListener {
                    C48891() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivityRecoverView.this.this$0.setPage(6, true, new Bundle(), true);
                    }
                }

                public void onClick(View view) {
                    Builder builder = new Builder(LoginActivityRecoverView.this.this$0.getParentActivity());
                    builder.setMessage(LocaleController.getString("RestoreEmailTroubleText", R.string.RestoreEmailTroubleText));
                    builder.setTitle(LocaleController.getString("RestorePasswordNoEmailTitle", R.string.RestorePasswordNoEmailTitle));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C48891());
                    Dialog showDialog = LoginActivityRecoverView.this.this$0.showDialog(builder.create());
                    if (showDialog != null) {
                        showDialog.setCanceledOnTouchOutside(false);
                        showDialog.setCancelable(false);
                    }
                }
            });
        }

        private void onPasscodeError(boolean z) {
            if (this.this$0.getParentActivity() != null) {
                Vibrator vibrator = (Vibrator) this.this$0.getParentActivity().getSystemService("vibrator");
                if (vibrator != null) {
                    vibrator.vibrate(200);
                }
                if (z) {
                    this.codeField.setText(TtmlNode.ANONYMOUS_REGION_ID);
                }
                AndroidUtilities.shakeView(this.confirmTextView, 2.0f, 0);
            }
        }

        public String getHeaderName() {
            return LocaleController.getString("LoginPassword", R.string.LoginPassword);
        }

        public boolean needBackButton() {
            return true;
        }

        public void onBackPressed() {
            this.currentParams = null;
        }

        public void onCancelPressed() {
            this.nextPressed = false;
        }

        public void onNextPressed() {
            if (!this.nextPressed) {
                if (this.codeField.getText().toString().length() == 0) {
                    onPasscodeError(false);
                    return;
                }
                this.nextPressed = true;
                String obj = this.codeField.getText().toString();
                if (obj.length() == 0) {
                    onPasscodeError(false);
                    return;
                }
                this.this$0.needShowProgress(0);
                TLObject tL_auth_recoverPassword = new TL_auth_recoverPassword();
                tL_auth_recoverPassword.code = obj;
                ConnectionsManager.getInstance().sendRequest(tL_auth_recoverPassword, new C48923(), 10);
            }
        }

        public void onShow() {
            super.onShow();
            if (this.codeField != null) {
                this.codeField.requestFocus();
                this.codeField.setSelection(this.codeField.length());
            }
        }

        public void restoreStateParams(Bundle bundle) {
            this.currentParams = bundle.getBundle("recoveryview_params");
            if (this.currentParams != null) {
                setParams(this.currentParams, true);
            }
            CharSequence string = bundle.getString("recoveryview_code");
            if (string != null) {
                this.codeField.setText(string);
            }
        }

        public void saveStateParams(Bundle bundle) {
            String obj = this.codeField.getText().toString();
            if (!(obj == null || obj.length() == 0)) {
                bundle.putString("recoveryview_code", obj);
            }
            if (this.currentParams != null) {
                bundle.putBundle("recoveryview_params", this.currentParams);
            }
        }

        public void setParams(Bundle bundle, boolean z) {
            if (bundle != null) {
                this.codeField.setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.currentParams = bundle;
                this.email_unconfirmed_pattern = this.currentParams.getString("email_unconfirmed_pattern");
                this.cancelButton.setText(LocaleController.formatString("RestoreEmailTrouble", R.string.RestoreEmailTrouble, new Object[]{this.email_unconfirmed_pattern}));
                AndroidUtilities.showKeyboard(this.codeField);
                this.codeField.requestFocus();
            }
        }
    }

    public class LoginActivityRegisterView extends SlideView {
        private Bundle currentParams;
        private EditTextBoldCursor firstNameField;
        private EditTextBoldCursor lastNameField;
        private boolean nextPressed = false;
        private String phoneCode;
        private String phoneHash;
        private String requestPhone;
        private TextView textView;
        private TextView wrongNumber;

        /* renamed from: org.telegram.ui.LoginActivity$LoginActivityRegisterView$4 */
        class C48984 implements RequestDelegate {
            C48984() {
            }

            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        LoginActivityRegisterView.this.nextPressed = false;
                        LoginActivity.this.needHideProgress();
                        if (tLRPC$TL_error == null) {
                            TL_auth_authorization tL_auth_authorization = (TL_auth_authorization) tLObject;
                            ConnectionsManager.getInstance().setUserId(tL_auth_authorization.user.id);
                            UserConfig.clearConfig();
                            MessagesController.getInstance().cleanup();
                            UserConfig.setCurrentUser(tL_auth_authorization.user);
                            UserConfig.saveConfig(true);
                            MessagesStorage.getInstance().cleanup(true);
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(tL_auth_authorization.user);
                            MessagesStorage.getInstance().putUsersAndChats(arrayList, null, true, true);
                            MessagesController.getInstance().putUser(tL_auth_authorization.user, false);
                            ContactsController.getInstance().checkAppAccount();
                            MessagesController.getInstance().getBlockedUsers(true);
                            ConnectionsManager.getInstance().updateDcSettings();
                            LoginActivity.this.needFinishActivity();
                        } else if (tLRPC$TL_error.text.contains("PHONE_NUMBER_INVALID")) {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidPhoneNumber", R.string.InvalidPhoneNumber));
                        } else if (tLRPC$TL_error.text.contains("PHONE_CODE_EMPTY") || tLRPC$TL_error.text.contains("PHONE_CODE_INVALID")) {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidCode", R.string.InvalidCode));
                        } else if (tLRPC$TL_error.text.contains("PHONE_CODE_EXPIRED")) {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("CodeExpired", R.string.CodeExpired));
                        } else if (tLRPC$TL_error.text.contains("FIRSTNAME_INVALID")) {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidFirstName", R.string.InvalidFirstName));
                        } else if (tLRPC$TL_error.text.contains("LASTNAME_INVALID")) {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidLastName", R.string.InvalidLastName));
                        } else {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), tLRPC$TL_error.text);
                        }
                    }
                });
            }
        }

        public LoginActivityRegisterView(Context context) {
            super(context);
            setOrientation(1);
            this.textView = new TextView(context);
            this.textView.setText(LocaleController.getString("RegisterText", R.string.RegisterText));
            this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.textView.setGravity(LocaleController.isRTL ? 5 : 3);
            this.textView.setTextSize(1, 14.0f);
            addView(this.textView, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3, 0, 8, 0, 0));
            this.firstNameField = new EditTextBoldCursor(context);
            this.firstNameField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.firstNameField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.firstNameField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
            this.firstNameField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.firstNameField.setCursorSize(AndroidUtilities.dp(20.0f));
            this.firstNameField.setCursorWidth(1.5f);
            this.firstNameField.setHint(LocaleController.getString("FirstName", R.string.FirstName));
            this.firstNameField.setImeOptions(268435461);
            this.firstNameField.setTextSize(1, 18.0f);
            this.firstNameField.setMaxLines(1);
            this.firstNameField.setInputType(MessagesController.UPDATE_MASK_CHANNEL);
            addView(this.firstNameField, LayoutHelper.createLinear(-1, 36, BitmapDescriptorFactory.HUE_RED, 26.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.firstNameField.setOnEditorActionListener(new OnEditorActionListener(LoginActivity.this) {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 5) {
                        return false;
                    }
                    LoginActivityRegisterView.this.lastNameField.requestFocus();
                    return true;
                }
            });
            this.lastNameField = new EditTextBoldCursor(context);
            this.lastNameField.setHint(LocaleController.getString("LastName", R.string.LastName));
            this.lastNameField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.lastNameField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.lastNameField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
            this.lastNameField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.lastNameField.setCursorSize(AndroidUtilities.dp(20.0f));
            this.lastNameField.setCursorWidth(1.5f);
            this.lastNameField.setImeOptions(268435462);
            this.lastNameField.setTextSize(1, 18.0f);
            this.lastNameField.setMaxLines(1);
            this.lastNameField.setInputType(MessagesController.UPDATE_MASK_CHANNEL);
            addView(this.lastNameField, LayoutHelper.createLinear(-1, 36, BitmapDescriptorFactory.HUE_RED, 10.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.lastNameField.setOnEditorActionListener(new OnEditorActionListener(LoginActivity.this) {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 6 && i != 5) {
                        return false;
                    }
                    LoginActivityRegisterView.this.onNextPressed();
                    return true;
                }
            });
            View linearLayout = new LinearLayout(context);
            linearLayout.setGravity(80);
            addView(linearLayout, LayoutHelper.createLinear(-1, -1));
            this.wrongNumber = new TextView(context);
            this.wrongNumber.setText(LocaleController.getString("CancelRegistration", R.string.CancelRegistration));
            this.wrongNumber.setGravity((LocaleController.isRTL ? 5 : 3) | 1);
            this.wrongNumber.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            this.wrongNumber.setTextSize(1, 14.0f);
            this.wrongNumber.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            this.wrongNumber.setPadding(0, AndroidUtilities.dp(24.0f), 0, 0);
            linearLayout.addView(this.wrongNumber, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 80, 0, 0, 0, 10));
            this.wrongNumber.setOnClickListener(new OnClickListener(LoginActivity.this) {

                /* renamed from: org.telegram.ui.LoginActivity$LoginActivityRegisterView$3$1 */
                class C48951 implements DialogInterface.OnClickListener {
                    C48951() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivityRegisterView.this.onBackPressed();
                        LoginActivity.this.setPage(0, true, null, true);
                    }
                }

                public void onClick(View view) {
                    Builder builder = new Builder(LoginActivity.this.getParentActivity());
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setMessage(LocaleController.getString("AreYouSureRegistration", R.string.AreYouSureRegistration));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C48951());
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    LoginActivity.this.showDialog(builder.create());
                }
            });
        }

        public String getHeaderName() {
            return LocaleController.getString("YourName", R.string.YourName);
        }

        public void onBackPressed() {
            this.currentParams = null;
        }

        public void onCancelPressed() {
            this.nextPressed = false;
        }

        public void onNextPressed() {
            if (!this.nextPressed) {
                this.nextPressed = true;
                TLObject tL_auth_signUp = new TL_auth_signUp();
                tL_auth_signUp.phone_code = this.phoneCode;
                tL_auth_signUp.phone_code_hash = this.phoneHash;
                tL_auth_signUp.phone_number = this.requestPhone;
                tL_auth_signUp.first_name = this.firstNameField.getText().toString();
                tL_auth_signUp.last_name = this.lastNameField.getText().toString();
                LoginActivity.this.needShowProgress(0);
                ConnectionsManager.getInstance().sendRequest(tL_auth_signUp, new C48984(), 10);
            }
        }

        public void onShow() {
            super.onShow();
            if (this.firstNameField != null) {
                this.firstNameField.requestFocus();
                this.firstNameField.setSelection(this.firstNameField.length());
            }
        }

        public void restoreStateParams(Bundle bundle) {
            this.currentParams = bundle.getBundle("registerview_params");
            if (this.currentParams != null) {
                setParams(this.currentParams, true);
            }
            CharSequence string = bundle.getString("registerview_first");
            if (string != null) {
                this.firstNameField.setText(string);
            }
            string = bundle.getString("registerview_last");
            if (string != null) {
                this.lastNameField.setText(string);
            }
        }

        public void saveStateParams(Bundle bundle) {
            String obj = this.firstNameField.getText().toString();
            if (obj.length() != 0) {
                bundle.putString("registerview_first", obj);
            }
            obj = this.lastNameField.getText().toString();
            if (obj.length() != 0) {
                bundle.putString("registerview_last", obj);
            }
            if (this.currentParams != null) {
                bundle.putBundle("registerview_params", this.currentParams);
            }
        }

        public void setParams(Bundle bundle, boolean z) {
            if (bundle != null) {
                this.firstNameField.setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.lastNameField.setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.requestPhone = bundle.getString("phoneFormated");
                this.phoneHash = bundle.getString("phoneHash");
                this.phoneCode = bundle.getString("code");
                this.currentParams = bundle;
            }
        }
    }

    public class LoginActivityResetWaitView extends SlideView {
        private TextView confirmTextView;
        private Bundle currentParams;
        private String phoneCode;
        private String phoneHash;
        private String requestPhone;
        private TextView resetAccountButton;
        private TextView resetAccountText;
        private TextView resetAccountTime;
        private int startTime;
        final /* synthetic */ LoginActivity this$0;
        private Runnable timeRunnable;
        private int waitTime;

        /* renamed from: org.telegram.ui.LoginActivity$LoginActivityResetWaitView$2 */
        class C49032 implements Runnable {
            C49032() {
            }

            public void run() {
                if (LoginActivityResetWaitView.this.timeRunnable == this) {
                    LoginActivityResetWaitView.this.updateTimeText();
                    AndroidUtilities.runOnUIThread(LoginActivityResetWaitView.this.timeRunnable, 1000);
                }
            }
        }

        public LoginActivityResetWaitView(final LoginActivity loginActivity, Context context) {
            int i = 5;
            this.this$0 = loginActivity;
            super(context);
            setOrientation(1);
            this.confirmTextView = new TextView(context);
            this.confirmTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.confirmTextView.setTextSize(1, 14.0f);
            this.confirmTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            this.confirmTextView.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            addView(this.confirmTextView, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3));
            this.resetAccountText = new TextView(context);
            this.resetAccountText.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
            this.resetAccountText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.resetAccountText.setText(LocaleController.getString("ResetAccountStatus", R.string.ResetAccountStatus));
            this.resetAccountText.setTextSize(1, 14.0f);
            this.resetAccountText.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            addView(this.resetAccountText, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 0, 24, 0, 0));
            this.resetAccountTime = new TextView(context);
            this.resetAccountTime.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
            this.resetAccountTime.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.resetAccountTime.setTextSize(1, 14.0f);
            this.resetAccountTime.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            addView(this.resetAccountTime, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 0, 2, 0, 0));
            this.resetAccountButton = new TextView(context);
            this.resetAccountButton.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
            this.resetAccountButton.setText(LocaleController.getString("ResetAccountButton", R.string.ResetAccountButton));
            this.resetAccountButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.resetAccountButton.setTextSize(1, 14.0f);
            this.resetAccountButton.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            this.resetAccountButton.setPadding(0, AndroidUtilities.dp(14.0f), 0, 0);
            View view = this.resetAccountButton;
            if (!LocaleController.isRTL) {
                i = 3;
            }
            addView(view, LayoutHelper.createLinear(-2, -2, i | 48, 0, 7, 0, 0));
            this.resetAccountButton.setOnClickListener(new OnClickListener() {

                /* renamed from: org.telegram.ui.LoginActivity$LoginActivityResetWaitView$1$1 */
                class C49011 implements DialogInterface.OnClickListener {

                    /* renamed from: org.telegram.ui.LoginActivity$LoginActivityResetWaitView$1$1$1 */
                    class C49001 implements RequestDelegate {
                        C49001() {
                        }

                        public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    LoginActivityResetWaitView.this.this$0.needHideProgress();
                                    if (tLRPC$TL_error == null) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("phoneFormated", LoginActivityResetWaitView.this.requestPhone);
                                        bundle.putString("phoneHash", LoginActivityResetWaitView.this.phoneHash);
                                        bundle.putString("code", LoginActivityResetWaitView.this.phoneCode);
                                        LoginActivityResetWaitView.this.this$0.setPage(5, true, bundle, false);
                                    } else if (tLRPC$TL_error.text.equals("2FA_RECENT_CONFIRM")) {
                                        LoginActivityResetWaitView.this.this$0.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("ResetAccountCancelledAlert", R.string.ResetAccountCancelledAlert));
                                    } else {
                                        LoginActivityResetWaitView.this.this$0.needShowAlert(LocaleController.getString("AppName", R.string.AppName), tLRPC$TL_error.text);
                                    }
                                }
                            });
                        }
                    }

                    C49011() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivityResetWaitView.this.this$0.needShowProgress(0);
                        TLObject tL_account_deleteAccount = new TL_account_deleteAccount();
                        tL_account_deleteAccount.reason = "Forgot password";
                        ConnectionsManager.getInstance().sendRequest(tL_account_deleteAccount, new C49001(), 10);
                    }
                }

                public void onClick(View view) {
                    if (Math.abs(ConnectionsManager.getInstance().getCurrentTime() - LoginActivityResetWaitView.this.startTime) >= LoginActivityResetWaitView.this.waitTime) {
                        Builder builder = new Builder(LoginActivityResetWaitView.this.this$0.getParentActivity());
                        builder.setMessage(LocaleController.getString("ResetMyAccountWarningText", R.string.ResetMyAccountWarningText));
                        builder.setTitle(LocaleController.getString("ResetMyAccountWarning", R.string.ResetMyAccountWarning));
                        builder.setPositiveButton(LocaleController.getString("ResetMyAccountWarningReset", R.string.ResetMyAccountWarningReset), new C49011());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        LoginActivityResetWaitView.this.this$0.showDialog(builder.create());
                    }
                }
            });
        }

        private void updateTimeText() {
            int max = Math.max(0, this.waitTime - (ConnectionsManager.getInstance().getCurrentTime() - this.startTime));
            int i = max / 86400;
            int i2 = (max - (i * 86400)) / 3600;
            int i3 = ((max - (i * 86400)) - (i2 * 3600)) / 60;
            int i4 = max % 60;
            if (i != 0) {
                this.resetAccountTime.setText(AndroidUtilities.replaceTags(LocaleController.formatPluralString("DaysBold", i) + " " + LocaleController.formatPluralString("HoursBold", i2) + " " + LocaleController.formatPluralString("MinutesBold", i3)));
            } else {
                this.resetAccountTime.setText(AndroidUtilities.replaceTags(LocaleController.formatPluralString("HoursBold", i2) + " " + LocaleController.formatPluralString("MinutesBold", i3) + " " + LocaleController.formatPluralString("SecondsBold", i4)));
            }
            if (max > 0) {
                this.resetAccountButton.setTag(Theme.key_windowBackgroundWhiteGrayText6);
                this.resetAccountButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
                return;
            }
            this.resetAccountButton.setTag(Theme.key_windowBackgroundWhiteRedText6);
            this.resetAccountButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText6));
        }

        public String getHeaderName() {
            return LocaleController.getString("ResetAccount", R.string.ResetAccount);
        }

        public boolean needBackButton() {
            return true;
        }

        public void onBackPressed() {
            AndroidUtilities.cancelRunOnUIThread(this.timeRunnable);
            this.timeRunnable = null;
            this.currentParams = null;
        }

        public void restoreStateParams(Bundle bundle) {
            this.currentParams = bundle.getBundle("resetview_params");
            if (this.currentParams != null) {
                setParams(this.currentParams, true);
            }
        }

        public void saveStateParams(Bundle bundle) {
            if (this.currentParams != null) {
                bundle.putBundle("resetview_params", this.currentParams);
            }
        }

        public void setParams(Bundle bundle, boolean z) {
            if (bundle != null) {
                this.currentParams = bundle;
                this.requestPhone = bundle.getString("phoneFormated");
                this.phoneHash = bundle.getString("phoneHash");
                this.phoneCode = bundle.getString("code");
                this.startTime = bundle.getInt("startTime");
                this.waitTime = bundle.getInt("waitTime");
                this.confirmTextView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("ResetAccountInfo", R.string.ResetAccountInfo, new Object[]{C2488b.a().e("+" + this.requestPhone)})));
                updateTimeText();
                this.timeRunnable = new C49032();
                AndroidUtilities.runOnUIThread(this.timeRunnable, 1000);
            }
        }
    }

    public class LoginActivitySmsView extends SlideView implements NotificationCenterDelegate {
        private String catchedPhone;
        private EditTextBoldCursor codeField;
        private volatile int codeTime = DefaultLoadControl.DEFAULT_MIN_BUFFER_MS;
        private Timer codeTimer;
        private TextView confirmTextView;
        private Bundle currentParams;
        private int currentType;
        private String emailPhone;
        private boolean ignoreOnTextChange;
        private boolean isRestored;
        private double lastCodeTime;
        private double lastCurrentTime;
        private String lastError = TtmlNode.ANONYMOUS_REGION_ID;
        private int length;
        private boolean nextPressed;
        private int nextType;
        private int openTime;
        private String pattern = "*";
        private String phone;
        private String phoneHash;
        private TextView problemText;
        private ProgressView progressView;
        private String requestPhone;
        private volatile int time = 60000;
        private TextView timeText;
        private Timer timeTimer;
        private int timeout;
        private final Object timerSync = new Object();
        private boolean waitingForEvent;
        private TextView wrongNumber;

        /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$6 */
        class C49126 extends TimerTask {

            /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$6$1 */
            class C49111 implements Runnable {
                C49111() {
                }

                public void run() {
                    if (LoginActivitySmsView.this.codeTime <= 1000) {
                        LoginActivitySmsView.this.problemText.setVisibility(0);
                        LoginActivitySmsView.this.destroyCodeTimer();
                    }
                }
            }

            C49126() {
            }

            public void run() {
                double currentTimeMillis = (double) System.currentTimeMillis();
                LoginActivitySmsView.this.codeTime = (int) (((double) LoginActivitySmsView.this.codeTime) - (currentTimeMillis - LoginActivitySmsView.this.lastCodeTime));
                LoginActivitySmsView.this.lastCodeTime = currentTimeMillis;
                AndroidUtilities.runOnUIThread(new C49111());
            }
        }

        /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$7 */
        class C49167 extends TimerTask {

            /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$7$1 */
            class C49151 implements Runnable {

                /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$7$1$1 */
                class C49141 implements RequestDelegate {
                    C49141() {
                    }

                    public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error != null && tLRPC$TL_error.text != null) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    LoginActivitySmsView.this.lastError = tLRPC$TL_error.text;
                                }
                            });
                        }
                    }
                }

                C49151() {
                }

                public void run() {
                    if (LoginActivitySmsView.this.time >= 1000) {
                        int access$4400 = (LoginActivitySmsView.this.time / 1000) - (((LoginActivitySmsView.this.time / 1000) / 60) * 60);
                        if (LoginActivitySmsView.this.nextType == 4 || LoginActivitySmsView.this.nextType == 3) {
                            LoginActivitySmsView.this.timeText.setText(LocaleController.formatString("CallText", R.string.CallText, new Object[]{Integer.valueOf(r0), Integer.valueOf(access$4400)}));
                        } else if (LoginActivitySmsView.this.nextType == 2) {
                            LoginActivitySmsView.this.timeText.setText(LocaleController.formatString("SmsText", R.string.SmsText, new Object[]{Integer.valueOf(r0), Integer.valueOf(access$4400)}));
                        }
                        if (LoginActivitySmsView.this.progressView != null) {
                            LoginActivitySmsView.this.progressView.setProgress(1.0f - (((float) LoginActivitySmsView.this.time) / ((float) LoginActivitySmsView.this.timeout)));
                            return;
                        }
                        return;
                    }
                    if (LoginActivitySmsView.this.progressView != null) {
                        LoginActivitySmsView.this.progressView.setProgress(1.0f);
                    }
                    LoginActivitySmsView.this.destroyTimer();
                    if (LoginActivitySmsView.this.currentType == 3) {
                        AndroidUtilities.setWaitingForCall(false);
                        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceiveCall);
                        LoginActivitySmsView.this.waitingForEvent = false;
                        LoginActivitySmsView.this.destroyCodeTimer();
                        LoginActivitySmsView.this.resendCode();
                    } else if (LoginActivitySmsView.this.currentType != 2) {
                    } else {
                        if (LoginActivitySmsView.this.nextType == 4) {
                            LoginActivitySmsView.this.timeText.setText(LocaleController.getString("Calling", R.string.Calling));
                            LoginActivitySmsView.this.createCodeTimer();
                            TLObject tL_auth_resendCode = new TL_auth_resendCode();
                            tL_auth_resendCode.phone_number = LoginActivitySmsView.this.requestPhone;
                            tL_auth_resendCode.phone_code_hash = LoginActivitySmsView.this.phoneHash;
                            ConnectionsManager.getInstance().sendRequest(tL_auth_resendCode, new C49141(), 10);
                        } else if (LoginActivitySmsView.this.nextType == 3) {
                            AndroidUtilities.setWaitingForSms(false);
                            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceiveSmsCode);
                            LoginActivitySmsView.this.waitingForEvent = false;
                            LoginActivitySmsView.this.destroyCodeTimer();
                            LoginActivitySmsView.this.resendCode();
                        }
                    }
                }
            }

            C49167() {
            }

            public void run() {
                if (LoginActivitySmsView.this.timeTimer != null) {
                    double currentTimeMillis = (double) System.currentTimeMillis();
                    LoginActivitySmsView.this.time = (int) (((double) LoginActivitySmsView.this.time) - (currentTimeMillis - LoginActivitySmsView.this.lastCurrentTime));
                    LoginActivitySmsView.this.lastCurrentTime = currentTimeMillis;
                    AndroidUtilities.runOnUIThread(new C49151());
                }
            }
        }

        public LoginActivitySmsView(Context context, int i) {
            View frameLayout;
            View imageView;
            super(context);
            this.currentType = i;
            setOrientation(1);
            this.confirmTextView = new TextView(context);
            this.confirmTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.confirmTextView.setTextSize(1, 14.0f);
            this.confirmTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            this.confirmTextView.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            if (this.currentType == 3) {
                frameLayout = new FrameLayout(context);
                imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.phone_activate);
                if (LocaleController.isRTL) {
                    frameLayout.addView(imageView, LayoutHelper.createFrame(64, 76.0f, 19, 2.0f, 2.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                    frameLayout.addView(this.confirmTextView, LayoutHelper.createFrame(-1, -2.0f, LocaleController.isRTL ? 5 : 3, 82.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                } else {
                    frameLayout.addView(this.confirmTextView, LayoutHelper.createFrame(-1, -2.0f, LocaleController.isRTL ? 5 : 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 82.0f, BitmapDescriptorFactory.HUE_RED));
                    frameLayout.addView(imageView, LayoutHelper.createFrame(64, 76.0f, 21, BitmapDescriptorFactory.HUE_RED, 2.0f, BitmapDescriptorFactory.HUE_RED, 2.0f));
                }
                addView(frameLayout, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3));
            } else {
                addView(this.confirmTextView, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3));
            }
            this.codeField = new EditTextBoldCursor(context);
            this.codeField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.codeField.setHint(LocaleController.getString("Code", R.string.Code));
            this.codeField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.codeField.setCursorSize(AndroidUtilities.dp(20.0f));
            this.codeField.setCursorWidth(1.5f);
            this.codeField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.codeField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
            this.codeField.setImeOptions(268435461);
            this.codeField.setTextSize(1, 18.0f);
            this.codeField.setInputType(3);
            this.codeField.setMaxLines(1);
            this.codeField.setPadding(0, 0, 0, 0);
            addView(this.codeField, LayoutHelper.createLinear(-1, 36, 1, 0, 20, 0, 0));
            this.codeField.addTextChangedListener(new TextWatcher(LoginActivity.this) {
                public void afterTextChanged(Editable editable) {
                    if (!LoginActivitySmsView.this.ignoreOnTextChange && LoginActivitySmsView.this.length != 0 && LoginActivitySmsView.this.codeField.length() == LoginActivitySmsView.this.length) {
                        LoginActivitySmsView.this.onNextPressed();
                    }
                }

                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }
            });
            this.codeField.setOnEditorActionListener(new OnEditorActionListener(LoginActivity.this) {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 5) {
                        return false;
                    }
                    LoginActivitySmsView.this.onNextPressed();
                    return true;
                }
            });
            if (this.currentType == 3) {
                this.codeField.setEnabled(false);
                this.codeField.setInputType(0);
                this.codeField.setVisibility(8);
            }
            this.timeText = new TextView(context);
            this.timeText.setTextSize(1, 14.0f);
            this.timeText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.timeText.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            this.timeText.setGravity(LocaleController.isRTL ? 5 : 3);
            addView(this.timeText, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3, 0, 30, 0, 0));
            if (this.currentType == 3) {
                this.progressView = new ProgressView(context);
                addView(this.progressView, LayoutHelper.createLinear(-1, 3, BitmapDescriptorFactory.HUE_RED, 12.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            }
            this.problemText = new TextView(context);
            this.problemText.setText(LocaleController.getString("DidNotGetTheCode", R.string.DidNotGetTheCode));
            this.problemText.setGravity(LocaleController.isRTL ? 5 : 3);
            this.problemText.setTextSize(1, 14.0f);
            this.problemText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            this.problemText.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            this.problemText.setPadding(0, AndroidUtilities.dp(2.0f), 0, AndroidUtilities.dp(12.0f));
            addView(this.problemText, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3, 0, 20, 0, 0));
            this.problemText.setOnClickListener(new OnClickListener(LoginActivity.this) {
                public void onClick(View view) {
                    if (!LoginActivitySmsView.this.nextPressed) {
                        if (LoginActivitySmsView.this.nextType == 0 || LoginActivitySmsView.this.nextType == 4) {
                            try {
                                PackageInfo packageInfo = ApplicationLoader.applicationContext.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
                                String format = String.format(Locale.US, "%s (%d)", new Object[]{packageInfo.versionName, Integer.valueOf(packageInfo.versionCode)});
                                Intent intent = new Intent("android.intent.action.SEND");
                                intent.setType("message/rfc822");
                                intent.putExtra("android.intent.extra.EMAIL", new String[]{"sms@stel.com"});
                                intent.putExtra("android.intent.extra.SUBJECT", "Android registration/login issue " + format + " " + LoginActivitySmsView.this.emailPhone);
                                intent.putExtra("android.intent.extra.TEXT", "Phone: " + LoginActivitySmsView.this.requestPhone + "\nApp version: " + format + "\nOS version: SDK " + VERSION.SDK_INT + "\nDevice Name: " + Build.MANUFACTURER + Build.MODEL + "\nLocale: " + Locale.getDefault() + "\nError: " + LoginActivitySmsView.this.lastError);
                                LoginActivitySmsView.this.getContext().startActivity(Intent.createChooser(intent, "Send email..."));
                                return;
                            } catch (Exception e) {
                                LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("NoMailInstalled", R.string.NoMailInstalled));
                                return;
                            }
                        }
                        LoginActivitySmsView.this.resendCode();
                    }
                }
            });
            frameLayout = new LinearLayout(context);
            frameLayout.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
            addView(frameLayout, LayoutHelper.createLinear(-1, -1, LocaleController.isRTL ? 5 : 3));
            imageView = new TextView(context);
            imageView.setGravity((LocaleController.isRTL ? 5 : 3) | 1);
            imageView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            imageView.setTextSize(1, 14.0f);
            imageView.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            imageView.setPadding(0, AndroidUtilities.dp(24.0f), 0, 0);
            frameLayout.addView(imageView, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 80, 0, 0, 0, 10));
            imageView.setText(LocaleController.getString("WrongNumber", R.string.WrongNumber));
            imageView.setOnClickListener(new OnClickListener(LoginActivity.this) {

                /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$4$1 */
                class C49071 implements RequestDelegate {
                    C49071() {
                    }

                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    }
                }

                public void onClick(View view) {
                    TLObject tL_auth_cancelCode = new TL_auth_cancelCode();
                    tL_auth_cancelCode.phone_number = LoginActivitySmsView.this.requestPhone;
                    tL_auth_cancelCode.phone_code_hash = LoginActivitySmsView.this.phoneHash;
                    ConnectionsManager.getInstance().sendRequest(tL_auth_cancelCode, new C49071(), 10);
                    LoginActivitySmsView.this.onBackPressed();
                    LoginActivity.this.setPage(0, true, null, true);
                }
            });
        }

        private void createCodeTimer() {
            if (this.codeTimer == null) {
                this.codeTime = DefaultLoadControl.DEFAULT_MIN_BUFFER_MS;
                this.codeTimer = new Timer();
                this.lastCodeTime = (double) System.currentTimeMillis();
                this.codeTimer.schedule(new C49126(), 0, 1000);
            }
        }

        private void createTimer() {
            if (this.timeTimer == null) {
                this.timeTimer = new Timer();
                this.timeTimer.schedule(new C49167(), 0, 1000);
            }
        }

        private void destroyCodeTimer() {
            try {
                synchronized (this.timerSync) {
                    if (this.codeTimer != null) {
                        this.codeTimer.cancel();
                        this.codeTimer = null;
                    }
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }

        private void destroyTimer() {
            try {
                synchronized (this.timerSync) {
                    if (this.timeTimer != null) {
                        this.timeTimer.cancel();
                        this.timeTimer = null;
                    }
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }

        private void resendCode() {
            final Bundle bundle = new Bundle();
            bundle.putString("phone", this.phone);
            bundle.putString("ephone", this.emailPhone);
            bundle.putString("phoneFormated", this.requestPhone);
            this.nextPressed = true;
            TLObject tL_auth_resendCode = new TL_auth_resendCode();
            tL_auth_resendCode.phone_number = this.requestPhone;
            tL_auth_resendCode.phone_code_hash = this.phoneHash;
            ConnectionsManager.getInstance().sendRequest(tL_auth_resendCode, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            LoginActivitySmsView.this.nextPressed = false;
                            if (tLRPC$TL_error == null) {
                                LoginActivity.this.fillNextCodeParams(bundle, (TL_auth_sentCode) tLObject);
                            } else if (tLRPC$TL_error.text != null) {
                                if (tLRPC$TL_error.text.contains("PHONE_NUMBER_INVALID")) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidPhoneNumber", R.string.InvalidPhoneNumber));
                                } else if (tLRPC$TL_error.text.contains("PHONE_CODE_EMPTY") || tLRPC$TL_error.text.contains("PHONE_CODE_INVALID")) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidCode", R.string.InvalidCode));
                                } else if (tLRPC$TL_error.text.contains("PHONE_CODE_EXPIRED")) {
                                    LoginActivitySmsView.this.onBackPressed();
                                    LoginActivity.this.setPage(0, true, null, true);
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("CodeExpired", R.string.CodeExpired));
                                } else if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("FloodWait", R.string.FloodWait));
                                } else if (tLRPC$TL_error.code != C3446C.PRIORITY_DOWNLOAD) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + "\n" + tLRPC$TL_error.text);
                                }
                            }
                            LoginActivity.this.needHideProgress();
                        }
                    });
                }
            }, 10);
            LoginActivity.this.needShowProgress(0);
        }

        public void didReceivedNotification(int i, Object... objArr) {
            if (this.waitingForEvent && this.codeField != null) {
                if (i == NotificationCenter.didReceiveSmsCode) {
                    this.ignoreOnTextChange = true;
                    this.codeField.setText(TtmlNode.ANONYMOUS_REGION_ID + objArr[0]);
                    this.ignoreOnTextChange = false;
                    onNextPressed();
                } else if (i == NotificationCenter.didReceiveCall) {
                    CharSequence charSequence = TtmlNode.ANONYMOUS_REGION_ID + objArr[0];
                    if (AndroidUtilities.checkPhonePattern(this.pattern, charSequence)) {
                        if (!this.pattern.equals("*")) {
                            this.catchedPhone = charSequence;
                            AndroidUtilities.endIncomingCall();
                            AndroidUtilities.removeLoginPhoneCall(charSequence, true);
                        }
                        this.ignoreOnTextChange = true;
                        this.codeField.setText(charSequence);
                        this.ignoreOnTextChange = false;
                        onNextPressed();
                    }
                }
            }
        }

        public String getHeaderName() {
            return LocaleController.getString("YourCode", R.string.YourCode);
        }

        public void onBackPressed() {
            destroyTimer();
            destroyCodeTimer();
            this.currentParams = null;
            if (this.currentType == 2) {
                AndroidUtilities.setWaitingForSms(false);
                NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceiveSmsCode);
            } else if (this.currentType == 3) {
                AndroidUtilities.setWaitingForCall(false);
                NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceiveCall);
            }
            this.waitingForEvent = false;
        }

        public void onCancelPressed() {
            this.nextPressed = false;
        }

        public void onDestroyActivity() {
            super.onDestroyActivity();
            if (this.currentType == 2) {
                AndroidUtilities.setWaitingForSms(false);
                NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceiveSmsCode);
            } else if (this.currentType == 3) {
                AndroidUtilities.setWaitingForCall(false);
                NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceiveCall);
            }
            this.waitingForEvent = false;
            destroyTimer();
            destroyCodeTimer();
        }

        public void onNextPressed() {
            if (!this.nextPressed) {
                this.nextPressed = true;
                if (this.currentType == 2) {
                    AndroidUtilities.setWaitingForSms(false);
                    NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceiveSmsCode);
                } else if (this.currentType == 3) {
                    AndroidUtilities.setWaitingForCall(false);
                    NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceiveCall);
                }
                this.waitingForEvent = false;
                final String obj = this.codeField.getText().toString();
                final TLObject tL_auth_signIn = new TL_auth_signIn();
                tL_auth_signIn.phone_number = this.requestPhone;
                tL_auth_signIn.phone_code = obj;
                tL_auth_signIn.phone_code_hash = this.phoneHash;
                destroyTimer();
                ConnectionsManager.getInstance().sendRequest(tL_auth_signIn, new RequestDelegate() {
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {

                            /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$8$1$1 */
                            class C49181 implements RequestDelegate {
                                C49181() {
                                }

                                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                                    AndroidUtilities.runOnUIThread(new Runnable() {
                                        public void run() {
                                            LoginActivity.this.needHideProgress();
                                            if (tLRPC$TL_error == null) {
                                                TL_account_password tL_account_password = (TL_account_password) tLObject;
                                                Bundle bundle = new Bundle();
                                                bundle.putString("current_salt", Utilities.bytesToHex(tL_account_password.current_salt));
                                                bundle.putString("hint", tL_account_password.hint);
                                                bundle.putString("email_unconfirmed_pattern", tL_account_password.email_unconfirmed_pattern);
                                                bundle.putString("phoneFormated", LoginActivitySmsView.this.requestPhone);
                                                bundle.putString("phoneHash", LoginActivitySmsView.this.phoneHash);
                                                bundle.putString("code", tL_auth_signIn.phone_code);
                                                bundle.putInt("has_recovery", tL_account_password.has_recovery ? 1 : 0);
                                                LoginActivity.this.setPage(6, true, bundle, false);
                                                return;
                                            }
                                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), tLRPC$TL_error.text);
                                        }
                                    });
                                }
                            }

                            public void run() {
                                boolean z;
                                LoginActivitySmsView.this.nextPressed = false;
                                if (tLRPC$TL_error == null) {
                                    LoginActivity.this.needHideProgress();
                                    TL_auth_authorization tL_auth_authorization = (TL_auth_authorization) tLObject;
                                    ConnectionsManager.getInstance().setUserId(tL_auth_authorization.user.id);
                                    LoginActivitySmsView.this.destroyTimer();
                                    LoginActivitySmsView.this.destroyCodeTimer();
                                    UserConfig.clearConfig();
                                    MessagesController.getInstance().cleanup();
                                    UserConfig.setCurrentUser(tL_auth_authorization.user);
                                    UserConfig.saveConfig(true);
                                    MessagesStorage.getInstance().cleanup(true);
                                    ArrayList arrayList = new ArrayList();
                                    arrayList.add(tL_auth_authorization.user);
                                    MessagesStorage.getInstance().putUsersAndChats(arrayList, null, true, true);
                                    MessagesController.getInstance().putUser(tL_auth_authorization.user, false);
                                    ContactsController.getInstance().checkAppAccount();
                                    MessagesController.getInstance().getBlockedUsers(true);
                                    ConnectionsManager.getInstance().updateDcSettings();
                                    LoginActivity.this.needFinishActivity();
                                    z = true;
                                } else {
                                    LoginActivitySmsView.this.lastError = tLRPC$TL_error.text;
                                    if (tLRPC$TL_error.text.contains("PHONE_NUMBER_UNOCCUPIED")) {
                                        LoginActivity.this.needHideProgress();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("phoneFormated", LoginActivitySmsView.this.requestPhone);
                                        bundle.putString("phoneHash", LoginActivitySmsView.this.phoneHash);
                                        bundle.putString("code", tL_auth_signIn.phone_code);
                                        LoginActivity.this.setPage(5, true, bundle, false);
                                        LoginActivitySmsView.this.destroyTimer();
                                        LoginActivitySmsView.this.destroyCodeTimer();
                                        z = true;
                                    } else if (tLRPC$TL_error.text.contains("SESSION_PASSWORD_NEEDED")) {
                                        ConnectionsManager.getInstance().sendRequest(new TL_account_getPassword(), new C49181(), 10);
                                        LoginActivitySmsView.this.destroyTimer();
                                        LoginActivitySmsView.this.destroyCodeTimer();
                                        z = true;
                                    } else {
                                        LoginActivity.this.needHideProgress();
                                        if ((LoginActivitySmsView.this.currentType == 3 && (LoginActivitySmsView.this.nextType == 4 || LoginActivitySmsView.this.nextType == 2)) || (LoginActivitySmsView.this.currentType == 2 && (LoginActivitySmsView.this.nextType == 4 || LoginActivitySmsView.this.nextType == 3))) {
                                            LoginActivitySmsView.this.createTimer();
                                        }
                                        if (LoginActivitySmsView.this.currentType == 2) {
                                            AndroidUtilities.setWaitingForSms(true);
                                            NotificationCenter.getInstance().addObserver(LoginActivitySmsView.this, NotificationCenter.didReceiveSmsCode);
                                        } else if (LoginActivitySmsView.this.currentType == 3) {
                                            AndroidUtilities.setWaitingForCall(true);
                                            NotificationCenter.getInstance().addObserver(LoginActivitySmsView.this, NotificationCenter.didReceiveCall);
                                        }
                                        LoginActivitySmsView.this.waitingForEvent = true;
                                        if (LoginActivitySmsView.this.currentType != 3) {
                                            if (tLRPC$TL_error.text.contains("PHONE_NUMBER_INVALID")) {
                                                LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidPhoneNumber", R.string.InvalidPhoneNumber));
                                                z = false;
                                            } else if (tLRPC$TL_error.text.contains("PHONE_CODE_EMPTY") || tLRPC$TL_error.text.contains("PHONE_CODE_INVALID")) {
                                                LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidCode", R.string.InvalidCode));
                                                z = false;
                                            } else if (tLRPC$TL_error.text.contains("PHONE_CODE_EXPIRED")) {
                                                LoginActivitySmsView.this.onBackPressed();
                                                LoginActivity.this.setPage(0, true, null, true);
                                                LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("CodeExpired", R.string.CodeExpired));
                                                z = false;
                                            } else if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                                                LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("FloodWait", R.string.FloodWait));
                                                z = false;
                                            } else {
                                                LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + "\n" + tLRPC$TL_error.text);
                                            }
                                        }
                                        z = false;
                                    }
                                }
                                if (z && LoginActivitySmsView.this.currentType == 3) {
                                    AndroidUtilities.endIncomingCall();
                                    AndroidUtilities.removeLoginPhoneCall(obj, true);
                                }
                            }
                        });
                    }
                }, 10);
                LoginActivity.this.needShowProgress(0);
            }
        }

        public void onShow() {
            super.onShow();
            if (this.codeField != null && this.currentType != 3) {
                this.codeField.requestFocus();
                this.codeField.setSelection(this.codeField.length());
            }
        }

        public void restoreStateParams(Bundle bundle) {
            this.currentParams = bundle.getBundle("smsview_params_" + this.currentType);
            if (this.currentParams != null) {
                setParams(this.currentParams, true);
            }
            String string = bundle.getString("catchedPhone");
            if (string != null) {
                this.catchedPhone = string;
            }
            CharSequence string2 = bundle.getString("smsview_code_" + this.currentType);
            if (string2 != null) {
                this.codeField.setText(string2);
            }
            int i = bundle.getInt("time");
            if (i != 0) {
                this.time = i;
            }
            i = bundle.getInt("open");
            if (i != 0) {
                this.openTime = i;
            }
        }

        public void saveStateParams(Bundle bundle) {
            String obj = this.codeField.getText().toString();
            if (obj.length() != 0) {
                bundle.putString("smsview_code_" + this.currentType, obj);
            }
            if (this.catchedPhone != null) {
                bundle.putString("catchedPhone", this.catchedPhone);
            }
            if (this.currentParams != null) {
                bundle.putBundle("smsview_params_" + this.currentType, this.currentParams);
            }
            if (this.time != 0) {
                bundle.putInt("time", this.time);
            }
            if (this.openTime != 0) {
                bundle.putInt("open", this.openTime);
            }
        }

        public void setParams(Bundle bundle, boolean z) {
            int i = 0;
            if (bundle != null) {
                this.isRestored = z;
                this.codeField.setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.waitingForEvent = true;
                if (this.currentType == 2) {
                    AndroidUtilities.setWaitingForSms(true);
                    NotificationCenter.getInstance().addObserver(this, NotificationCenter.didReceiveSmsCode);
                } else if (this.currentType == 3) {
                    AndroidUtilities.setWaitingForCall(true);
                    NotificationCenter.getInstance().addObserver(this, NotificationCenter.didReceiveCall);
                }
                this.currentParams = bundle;
                this.phone = bundle.getString("phone");
                this.emailPhone = bundle.getString("ephone");
                this.requestPhone = bundle.getString("phoneFormated");
                this.phoneHash = bundle.getString("phoneHash");
                int i2 = bundle.getInt("timeout");
                this.time = i2;
                this.timeout = i2;
                this.openTime = (int) (System.currentTimeMillis() / 1000);
                this.nextType = bundle.getInt("nextType");
                this.pattern = bundle.getString("pattern");
                this.length = bundle.getInt("length");
                if (this.length != 0) {
                    this.codeField.setFilters(new InputFilter[]{new LengthFilter(this.length)});
                } else {
                    this.codeField.setFilters(new InputFilter[0]);
                }
                if (this.progressView != null) {
                    this.progressView.setVisibility(this.nextType != 0 ? 0 : 8);
                }
                if (this.phone != null) {
                    String e = C2488b.a().e(this.phone);
                    CharSequence charSequence = TtmlNode.ANONYMOUS_REGION_ID;
                    if (this.currentType == 1) {
                        charSequence = AndroidUtilities.replaceTags(LocaleController.getString("SentAppCode", R.string.SentAppCode));
                    } else if (this.currentType == 2) {
                        charSequence = AndroidUtilities.replaceTags(LocaleController.formatString("SentSmsCode", R.string.SentSmsCode, new Object[]{e}));
                    } else if (this.currentType == 3) {
                        charSequence = AndroidUtilities.replaceTags(LocaleController.formatString("SentCallCode", R.string.SentCallCode, new Object[]{e}));
                    } else if (this.currentType == 4) {
                        charSequence = AndroidUtilities.replaceTags(LocaleController.formatString("SentCallOnly", R.string.SentCallOnly, new Object[]{e}));
                    }
                    this.confirmTextView.setText(charSequence);
                    if (this.currentType != 3) {
                        AndroidUtilities.showKeyboard(this.codeField);
                        this.codeField.requestFocus();
                    } else {
                        AndroidUtilities.hideKeyboard(this.codeField);
                    }
                    destroyTimer();
                    destroyCodeTimer();
                    this.lastCurrentTime = (double) System.currentTimeMillis();
                    if (this.currentType == 1) {
                        this.problemText.setVisibility(0);
                        this.problemText.performClick();
                        this.timeText.setVisibility(8);
                    } else if (this.currentType == 3 && (this.nextType == 4 || this.nextType == 2)) {
                        this.problemText.setVisibility(8);
                        this.timeText.setVisibility(0);
                        if (this.nextType == 4) {
                            this.timeText.setText(LocaleController.formatString("CallText", R.string.CallText, new Object[]{Integer.valueOf(1), Integer.valueOf(0)}));
                        } else if (this.nextType == 2) {
                            this.timeText.setText(LocaleController.formatString("SmsText", R.string.SmsText, new Object[]{Integer.valueOf(1), Integer.valueOf(0)}));
                        }
                        charSequence = this.isRestored ? AndroidUtilities.obtainLoginPhoneCall(this.pattern) : null;
                        if (charSequence != null) {
                            this.ignoreOnTextChange = true;
                            this.codeField.setText(charSequence);
                            this.ignoreOnTextChange = false;
                            onNextPressed();
                        } else if (this.catchedPhone != null) {
                            this.ignoreOnTextChange = true;
                            this.codeField.setText(this.catchedPhone);
                            this.ignoreOnTextChange = false;
                            onNextPressed();
                        } else {
                            createTimer();
                        }
                    } else if (this.currentType == 2 && (this.nextType == 4 || this.nextType == 3)) {
                        this.timeText.setVisibility(0);
                        this.timeText.setText(LocaleController.formatString("CallText", R.string.CallText, new Object[]{Integer.valueOf(2), Integer.valueOf(0)}));
                        TextView textView = this.problemText;
                        if (this.time >= 1000) {
                            i = 8;
                        }
                        textView.setVisibility(i);
                        createTimer();
                    } else {
                        this.timeText.setVisibility(8);
                        this.problemText.setVisibility(8);
                        createCodeTimer();
                    }
                }
            }
        }
    }

    public class PhoneView extends SlideView implements OnItemSelectedListener {
        TextView btnRetry;
        private EditTextBoldCursor codeField;
        private HashMap<String, String> codesMap = new HashMap();
        private ArrayList<String> countriesArray = new ArrayList();
        private HashMap<String, String> countriesMap = new HashMap();
        private TextView countryButton;
        private int countryState = 0;
        private boolean ignoreOnPhoneChange = false;
        private boolean ignoreOnTextChange = false;
        private boolean ignoreSelection = false;
        private boolean nextPressed = false;
        private HintEditText phoneField;
        private HashMap<String, String> phoneFormatMap = new HashMap();
        private TextView textView;
        private TextView textView2;
        private TextView textView3;
        private TextView textView3Bold;
        private TextView textView4;
        private View view;

        public PhoneView(Context context) {
            super(context);
            setOrientation(1);
            this.countryButton = new TextView(context);
            this.countryButton.setTextSize(1, 18.0f);
            this.countryButton.setPadding(AndroidUtilities.dp(12.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(12.0f), 0);
            this.countryButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.countryButton.setMaxLines(1);
            this.countryButton.setSingleLine(true);
            this.countryButton.setEllipsize(TruncateAt.END);
            this.countryButton.setGravity((LocaleController.isRTL ? 5 : 3) | 1);
            this.countryButton.setBackgroundResource(R.drawable.spinner_states);
            addView(this.countryButton, LayoutHelper.createLinear(-1, 36, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 14.0f));
            this.countryButton.setOnClickListener(new OnClickListener(LoginActivity.this) {

                /* renamed from: org.telegram.ui.LoginActivity$PhoneView$1$1 */
                class C49221 implements CountrySelectActivityDelegate {

                    /* renamed from: org.telegram.ui.LoginActivity$PhoneView$1$1$1 */
                    class C49211 implements Runnable {
                        C49211() {
                        }

                        public void run() {
                            AndroidUtilities.showKeyboard(PhoneView.this.phoneField);
                        }
                    }

                    C49221() {
                    }

                    public void didSelectCountry(String str, String str2) {
                        PhoneView.this.selectCountry(str);
                        AndroidUtilities.runOnUIThread(new C49211(), 300);
                        PhoneView.this.phoneField.requestFocus();
                        PhoneView.this.phoneField.setSelection(PhoneView.this.phoneField.length());
                    }
                }

                public void onClick(View view) {
                    BaseFragment countrySelectActivity = new CountrySelectActivity(true);
                    countrySelectActivity.setCountrySelectActivityDelegate(new C49221());
                    LoginActivity.this.presentFragment(countrySelectActivity);
                }
            });
            this.view = new View(context);
            this.view.setPadding(AndroidUtilities.dp(12.0f), 0, AndroidUtilities.dp(12.0f), 0);
            this.view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayLine));
            addView(this.view, LayoutHelper.createLinear(-1, 1, 4.0f, -17.5f, 4.0f, BitmapDescriptorFactory.HUE_RED));
            View linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(0);
            addView(linearLayout, LayoutHelper.createLinear(-1, -2, BitmapDescriptorFactory.HUE_RED, 20.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.textView = new TextView(context);
            this.textView.setText("+");
            this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.textView.setTextSize(1, 18.0f);
            linearLayout.addView(this.textView, LayoutHelper.createLinear(-2, -2));
            this.codeField = new EditTextBoldCursor(context);
            this.codeField.setInputType(3);
            this.codeField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.codeField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
            this.codeField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.codeField.setCursorSize(AndroidUtilities.dp(20.0f));
            this.codeField.setCursorWidth(1.5f);
            this.codeField.setPadding(AndroidUtilities.dp(10.0f), 0, 0, 0);
            this.codeField.setTextSize(1, 18.0f);
            this.codeField.setMaxLines(1);
            this.codeField.setGravity(19);
            this.codeField.setImeOptions(268435461);
            this.codeField.setFilters(new InputFilter[]{new LengthFilter(5)});
            linearLayout.addView(this.codeField, LayoutHelper.createLinear(55, 36, -9.0f, BitmapDescriptorFactory.HUE_RED, 16.0f, BitmapDescriptorFactory.HUE_RED));
            this.codeField.addTextChangedListener(new TextWatcher(LoginActivity.this) {
                public void afterTextChanged(Editable editable) {
                    String str = null;
                    if (!PhoneView.this.ignoreOnTextChange) {
                        PhoneView.this.ignoreOnTextChange = true;
                        String b = C2488b.b(PhoneView.this.codeField.getText().toString());
                        PhoneView.this.codeField.setText(b);
                        if (b.length() == 0) {
                            PhoneView.this.countryButton.setText(LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
                            PhoneView.this.phoneField.setHintText(null);
                            PhoneView.this.countryState = 1;
                        } else {
                            String str2;
                            Object obj;
                            boolean z;
                            CharSequence charSequence;
                            if (b.length() > 4) {
                                String substring;
                                boolean z2;
                                for (int i = 4; i >= 1; i--) {
                                    substring = b.substring(0, i);
                                    if (((String) PhoneView.this.codesMap.get(substring)) != null) {
                                        str2 = b.substring(i, b.length()) + PhoneView.this.phoneField.getText().toString();
                                        PhoneView.this.codeField.setText(substring);
                                        z2 = true;
                                        break;
                                    }
                                }
                                str2 = null;
                                substring = b;
                                z2 = false;
                                if (!z2) {
                                    str2 = substring.substring(1, substring.length()) + PhoneView.this.phoneField.getText().toString();
                                    EditTextBoldCursor access$900 = PhoneView.this.codeField;
                                    substring = substring.substring(0, 1);
                                    access$900.setText(substring);
                                }
                                obj = substring;
                                z = z2;
                                charSequence = str2;
                            } else {
                                z = false;
                                String str3 = b;
                                charSequence = null;
                            }
                            str2 = (String) PhoneView.this.codesMap.get(obj);
                            if (str2 != null) {
                                int indexOf = PhoneView.this.countriesArray.indexOf(str2);
                                if (indexOf != -1) {
                                    PhoneView.this.ignoreSelection = true;
                                    PhoneView.this.countryButton.setText((CharSequence) PhoneView.this.countriesArray.get(indexOf));
                                    str2 = (String) PhoneView.this.phoneFormatMap.get(obj);
                                    HintEditText access$700 = PhoneView.this.phoneField;
                                    if (str2 != null) {
                                        str = str2.replace('X', '–');
                                    }
                                    access$700.setHintText(str);
                                    PhoneView.this.countryState = 0;
                                } else {
                                    PhoneView.this.countryButton.setText(LocaleController.getString("WrongCountry", R.string.WrongCountry));
                                    PhoneView.this.phoneField.setHintText(null);
                                    PhoneView.this.countryState = 2;
                                }
                            } else {
                                PhoneView.this.countryButton.setText(LocaleController.getString("WrongCountry", R.string.WrongCountry));
                                PhoneView.this.phoneField.setHintText(null);
                                PhoneView.this.countryState = 2;
                            }
                            if (!z) {
                                PhoneView.this.codeField.setSelection(PhoneView.this.codeField.getText().length());
                            }
                            if (charSequence != null) {
                                PhoneView.this.phoneField.requestFocus();
                                PhoneView.this.phoneField.setText(charSequence);
                                PhoneView.this.phoneField.setSelection(PhoneView.this.phoneField.length());
                            }
                        }
                        PhoneView.this.ignoreOnTextChange = false;
                    }
                }

                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }
            });
            this.codeField.setOnEditorActionListener(new OnEditorActionListener(LoginActivity.this) {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 5) {
                        return false;
                    }
                    PhoneView.this.phoneField.requestFocus();
                    PhoneView.this.phoneField.setSelection(PhoneView.this.phoneField.length());
                    return true;
                }
            });
            this.phoneField = new HintEditText(context);
            this.phoneField.setInputType(3);
            this.phoneField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.phoneField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.phoneField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
            this.phoneField.setPadding(0, 0, 0, 0);
            this.phoneField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.phoneField.setCursorSize(AndroidUtilities.dp(20.0f));
            this.phoneField.setCursorWidth(1.5f);
            this.phoneField.setTextSize(1, 18.0f);
            this.phoneField.setMaxLines(1);
            this.phoneField.setGravity(19);
            this.phoneField.setImeOptions(268435461);
            linearLayout.addView(this.phoneField, LayoutHelper.createFrame(-1, 36.0f));
            this.phoneField.addTextChangedListener(new TextWatcher(LoginActivity.this) {
                private int actionPosition;
                private int characterAction = -1;

                public void afterTextChanged(Editable editable) {
                    if (!PhoneView.this.ignoreOnPhoneChange) {
                        int selectionStart = PhoneView.this.phoneField.getSelectionStart();
                        String str = "0123456789";
                        String obj = PhoneView.this.phoneField.getText().toString();
                        if (this.characterAction == 3) {
                            obj = obj.substring(0, this.actionPosition) + obj.substring(this.actionPosition + 1, obj.length());
                            selectionStart--;
                        }
                        CharSequence stringBuilder = new StringBuilder(obj.length());
                        for (int i = 0; i < obj.length(); i++) {
                            Object substring = obj.substring(i, i + 1);
                            if (str.contains(substring)) {
                                stringBuilder.append(substring);
                            }
                        }
                        PhoneView.this.ignoreOnPhoneChange = true;
                        String hintText = PhoneView.this.phoneField.getHintText();
                        if (hintText != null) {
                            int i2 = 0;
                            while (i2 < stringBuilder.length()) {
                                if (i2 < hintText.length()) {
                                    if (hintText.charAt(i2) == ' ') {
                                        stringBuilder.insert(i2, ' ');
                                        i2++;
                                        if (!(selectionStart != i2 || this.characterAction == 2 || this.characterAction == 3)) {
                                            selectionStart++;
                                        }
                                    }
                                    i2++;
                                } else {
                                    stringBuilder.insert(i2, ' ');
                                    if (!(selectionStart != i2 + 1 || this.characterAction == 2 || this.characterAction == 3)) {
                                        selectionStart++;
                                    }
                                }
                            }
                        }
                        PhoneView.this.phoneField.setText(stringBuilder);
                        if (selectionStart >= 0) {
                            HintEditText access$700 = PhoneView.this.phoneField;
                            if (selectionStart > PhoneView.this.phoneField.length()) {
                                selectionStart = PhoneView.this.phoneField.length();
                            }
                            access$700.setSelection(selectionStart);
                        }
                        PhoneView.this.phoneField.onTextChange();
                        PhoneView.this.ignoreOnPhoneChange = false;
                    }
                }

                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    if (i2 == 0 && i3 == 1) {
                        this.characterAction = 1;
                    } else if (i2 != 1 || i3 != 0) {
                        this.characterAction = -1;
                    } else if (charSequence.charAt(i) != ' ' || i <= 0) {
                        this.characterAction = 2;
                    } else {
                        this.characterAction = 3;
                        this.actionPosition = i - 1;
                    }
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }
            });
            this.phoneField.setOnEditorActionListener(new OnEditorActionListener(LoginActivity.this) {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 5) {
                        return false;
                    }
                    PhoneView.this.onNextPressed();
                    return true;
                }
            });
            this.textView2 = new TextView(context);
            this.textView2.setText(LocaleController.getString("StartText", R.string.StartText));
            this.textView2.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.textView2.setTextSize(1, 14.0f);
            this.textView2.setGravity(LocaleController.isRTL ? 5 : 3);
            this.textView2.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            addView(this.textView2, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3, 0, 28, 0, 10));
            if (C3791b.am(ApplicationLoader.applicationContext) > 0) {
                this.textView3Bold = new TextView(context);
                this.textView3Bold.setText(LocaleController.getString("RetryProxyHint", R.string.RetryProxyHint));
                this.textView3Bold.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
                this.textView3Bold.setTextSize(1, 16.0f);
                this.textView3Bold.setTypeface(FarsiTextView.m14170a(ApplicationLoader.applicationContext), 1);
                this.textView3Bold.setGravity(17);
                this.textView3Bold.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
                addView(this.textView3Bold, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3, 0, 28, 0, 10));
                this.textView3 = new TextView(context);
                this.textView3.setText(LocaleController.getString("RetryProxy", R.string.RetryProxy));
                this.textView3.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
                this.textView3.setTextSize(1, 14.0f);
                this.textView3.setGravity(17);
                this.textView3.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
                addView(this.textView3, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3, 0, 0, 0, 10));
                this.btnRetry = new FarsiTextView(context);
                this.btnRetry.setText(LocaleController.getString("ConnectAgain", R.string.ConnectAgain));
                this.btnRetry.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
                this.btnRetry.setTextSize(1, 14.0f);
                this.btnRetry.setGravity(17);
                this.btnRetry.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
                this.btnRetry.setPadding(30, 15, 30, 15);
                this.btnRetry.setBackgroundResource(R.drawable.regbtn_states);
                this.btnRetry.setTextColor(getResources().getColor(R.color.white));
                addView(this.btnRetry, LayoutHelper.createLinear(-2, -2, 17, 0, 28, 0, 0));
                this.btnRetry.setOnClickListener(new OnClickListener(LoginActivity.this) {

                    /* renamed from: org.telegram.ui.LoginActivity$PhoneView$6$1 */
                    class C49281 implements Runnable {
                        C49281() {
                        }

                        public void run() {
                            if (LoginActivity.this.getParentActivity() != null) {
                                Toast.makeText(LoginActivity.this.getParentActivity(), LocaleController.getString("AppWillRestart", R.string.AppWillRestart), 0).show();
                            }
                        }
                    }

                    /* renamed from: org.telegram.ui.LoginActivity$PhoneView$6$2 */
                    class C49292 implements Runnable {
                        C49292() {
                        }

                        public void run() {
                            C2872c.a();
                        }
                    }

                    public void onClick(View view) {
                        PhoneView.this.btnRetry.setClickable(false);
                        LoginActivity.this.showLoadingDialog(PhoneView.this.getContext(), LocaleController.getString("MyAppName", R.string.MyAppName), LocaleController.getString("wait", R.string.wait));
                        C2820e.a(ApplicationLoader.applicationContext, true);
                        PhoneView.this.btnRetry.setClickable(true);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.proxySettingsChanged, new Object[0]);
                        LoginActivity.this.dialogLoading.dismiss();
                        C2872c.f9483a = true;
                        AndroidUtilities.runOnUIThread(new C49281());
                        if (C2872c.f9483a) {
                            new Handler().postDelayed(new C49292(), 2000);
                        }
                    }
                });
                switch (ConnectionsManager.getInstance().getConnectionState()) {
                    case 3:
                    case 5:
                        this.textView3.setVisibility(8);
                        this.textView3Bold.setVisibility(8);
                        this.btnRetry.setVisibility(8);
                        break;
                }
            }
            HashMap hashMap = new HashMap();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResources().getAssets().open("countries.txt")));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        String[] split = readLine.split(";");
                        this.countriesArray.add(0, split[2]);
                        this.countriesMap.put(split[2], split[0]);
                        this.codesMap.put(split[0], split[2]);
                        if (split.length > 3) {
                            this.phoneFormatMap.put(split[0], split[3]);
                        }
                        hashMap.put(split[1], split[2]);
                    } else {
                        Object toUpperCase;
                        String str;
                        bufferedReader.close();
                        Collections.sort(this.countriesArray, new Comparator<String>(LoginActivity.this) {
                            public int compare(String str, String str2) {
                                return str.compareTo(str2);
                            }
                        });
                        try {
                            TelephonyManager telephonyManager = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
                            if (telephonyManager != null) {
                                toUpperCase = telephonyManager.getSimCountryIso().toUpperCase();
                                if (toUpperCase != null) {
                                    str = (String) hashMap.get(toUpperCase);
                                    if (!(str == null || this.countriesArray.indexOf(str) == -1)) {
                                        this.codeField.setText((CharSequence) this.countriesMap.get(str));
                                        this.countryState = 0;
                                    }
                                }
                                if (this.codeField.length() == 0) {
                                    this.countryButton.setText(LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
                                    this.phoneField.setHintText(null);
                                    this.countryState = 1;
                                }
                                if (this.codeField.length() == 0) {
                                    this.phoneField.requestFocus();
                                    this.phoneField.setSelection(this.phoneField.length());
                                    return;
                                }
                                this.codeField.requestFocus();
                                return;
                            }
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                        toUpperCase = null;
                        if (toUpperCase != null) {
                            str = (String) hashMap.get(toUpperCase);
                            this.codeField.setText((CharSequence) this.countriesMap.get(str));
                            this.countryState = 0;
                        }
                        if (this.codeField.length() == 0) {
                            this.countryButton.setText(LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
                            this.phoneField.setHintText(null);
                            this.countryState = 1;
                        }
                        if (this.codeField.length() == 0) {
                            this.codeField.requestFocus();
                            return;
                        }
                        this.phoneField.requestFocus();
                        this.phoneField.setSelection(this.phoneField.length());
                        return;
                    }
                }
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
        }

        public void fillNumber() {
            int i = 4;
            try {
                TelephonyManager telephonyManager = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
                if (telephonyManager.getSimState() != 1 && telephonyManager.getPhoneType() != 0) {
                    Object obj;
                    Object obj2;
                    if (VERSION.SDK_INT >= 23) {
                        obj = LoginActivity.this.getParentActivity().checkSelfPermission("android.permission.READ_PHONE_STATE") == 0 ? 1 : null;
                        obj2 = LoginActivity.this.getParentActivity().checkSelfPermission("android.permission.RECEIVE_SMS") == 0 ? 1 : null;
                        if (LoginActivity.this.checkShowPermissions && obj == null && obj2 == null) {
                            LoginActivity.this.permissionsShowItems.clear();
                            if (obj == null) {
                                LoginActivity.this.permissionsShowItems.add("android.permission.READ_PHONE_STATE");
                            }
                            if (obj2 == null) {
                                LoginActivity.this.permissionsShowItems.add("android.permission.RECEIVE_SMS");
                                if (VERSION.SDK_INT >= 23) {
                                    LoginActivity.this.permissionsShowItems.add("android.permission.READ_SMS");
                                }
                            }
                            if (!LoginActivity.this.permissionsShowItems.isEmpty()) {
                                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                                if (sharedPreferences.getBoolean("firstloginshow", true) || LoginActivity.this.getParentActivity().shouldShowRequestPermissionRationale("android.permission.READ_PHONE_STATE") || LoginActivity.this.getParentActivity().shouldShowRequestPermissionRationale("android.permission.RECEIVE_SMS")) {
                                    sharedPreferences.edit().putBoolean("firstloginshow", false).commit();
                                    Builder builder = new Builder(LoginActivity.this.getParentActivity());
                                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                    builder.setMessage(LocaleController.getString("AllowFillNumber", R.string.AllowFillNumber));
                                    LoginActivity.this.permissionsShowDialog = LoginActivity.this.showDialog(builder.create());
                                    return;
                                }
                                LoginActivity.this.getParentActivity().requestPermissions((String[]) LoginActivity.this.permissionsShowItems.toArray(new String[LoginActivity.this.permissionsShowItems.size()]), 7);
                                return;
                            }
                            return;
                        }
                    }
                    obj2 = 1;
                    obj = 1;
                    if (obj != null || r5 != null) {
                        String b = C2488b.b(telephonyManager.getLine1Number());
                        if (!TextUtils.isEmpty(b)) {
                            CharSequence substring;
                            if (b.length() > 4) {
                                Object obj3;
                                while (i >= 1) {
                                    CharSequence substring2 = b.substring(0, i);
                                    if (((String) this.codesMap.get(substring2)) != null) {
                                        substring = b.substring(i, b.length());
                                        this.codeField.setText(substring2);
                                        obj3 = 1;
                                        break;
                                    }
                                    i--;
                                }
                                substring = null;
                                obj3 = null;
                                if (obj3 == null) {
                                    substring = b.substring(1, b.length());
                                    this.codeField.setText(b.substring(0, 1));
                                }
                            } else {
                                substring = null;
                            }
                            if (substring != null) {
                                this.phoneField.requestFocus();
                                this.phoneField.setText(substring);
                                this.phoneField.setSelection(this.phoneField.length());
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }

        public String getHeaderName() {
            return LocaleController.getString("YourPhone", R.string.YourPhone);
        }

        public void onCancelPressed() {
            this.nextPressed = false;
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            if (this.ignoreSelection) {
                this.ignoreSelection = false;
                return;
            }
            this.ignoreOnTextChange = true;
            this.codeField.setText((CharSequence) this.countriesMap.get((String) this.countriesArray.get(i)));
            this.ignoreOnTextChange = false;
        }

        public void onNextPressed() {
            if (LoginActivity.this.getParentActivity() != null && !this.nextPressed) {
                boolean z;
                boolean z2;
                TelephonyManager telephonyManager = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
                boolean z3 = (telephonyManager.getSimState() == 1 || telephonyManager.getPhoneType() == 0) ? false : true;
                if (VERSION.SDK_INT < 23 || !z3) {
                    z = true;
                } else {
                    z = LoginActivity.this.getParentActivity().checkSelfPermission("android.permission.READ_PHONE_STATE") == 0;
                    z2 = LoginActivity.this.getParentActivity().checkSelfPermission("android.permission.RECEIVE_SMS") == 0;
                    boolean z4 = LoginActivity.this.getParentActivity().checkSelfPermission("android.permission.CALL_PHONE") == 0;
                    if (LoginActivity.this.checkPermissions) {
                        LoginActivity.this.permissionsItems.clear();
                        if (!z) {
                            LoginActivity.this.permissionsItems.add("android.permission.READ_PHONE_STATE");
                        }
                        if (!z2) {
                            LoginActivity.this.permissionsItems.add("android.permission.RECEIVE_SMS");
                            if (VERSION.SDK_INT >= 23) {
                                LoginActivity.this.permissionsItems.add("android.permission.READ_SMS");
                            }
                        }
                        if (!z4) {
                            LoginActivity.this.permissionsItems.add("android.permission.CALL_PHONE");
                            LoginActivity.this.permissionsItems.add("android.permission.WRITE_CALL_LOG");
                            LoginActivity.this.permissionsItems.add("android.permission.READ_CALL_LOG");
                        }
                        if (!LoginActivity.this.permissionsItems.isEmpty()) {
                            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                            if (!z4 && z) {
                                LoginActivity.this.getParentActivity().requestPermissions((String[]) LoginActivity.this.permissionsItems.toArray(new String[LoginActivity.this.permissionsItems.size()]), 6);
                                z2 = true;
                            } else if (sharedPreferences.getBoolean("firstlogin", true) || LoginActivity.this.getParentActivity().shouldShowRequestPermissionRationale("android.permission.READ_PHONE_STATE") || LoginActivity.this.getParentActivity().shouldShowRequestPermissionRationale("android.permission.RECEIVE_SMS")) {
                                sharedPreferences.edit().putBoolean("firstlogin", false).commit();
                                Builder builder = new Builder(LoginActivity.this.getParentActivity());
                                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                if (LoginActivity.this.permissionsItems.size() >= 2) {
                                    builder.setMessage(LocaleController.getString("AllowReadCallAndSms", R.string.AllowReadCallAndSms));
                                } else if (z2) {
                                    builder.setMessage(LocaleController.getString("AllowReadCall", R.string.AllowReadCall));
                                } else {
                                    builder.setMessage(LocaleController.getString("AllowReadSms", R.string.AllowReadSms));
                                }
                                LoginActivity.this.permissionsDialog = LoginActivity.this.showDialog(builder.create());
                                z2 = true;
                            } else {
                                try {
                                    LoginActivity.this.getParentActivity().requestPermissions((String[]) LoginActivity.this.permissionsItems.toArray(new String[LoginActivity.this.permissionsItems.size()]), 6);
                                    z2 = true;
                                } catch (Exception e) {
                                    z2 = false;
                                }
                            }
                            if (z2) {
                                return;
                            }
                        }
                    }
                }
                if (this.countryState == 1) {
                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
                } else if (this.countryState == 2 && !BuildVars.DEBUG_VERSION && !this.codeField.getText().toString().equals("999")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("WrongCountry", R.string.WrongCountry));
                } else if (this.codeField.length() == 0) {
                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidPhoneNumber", R.string.InvalidPhoneNumber));
                } else {
                    ConnectionsManager.getInstance().cleanup();
                    final TLObject tL_auth_sendCode = new TL_auth_sendCode();
                    String b = C2488b.b(TtmlNode.ANONYMOUS_REGION_ID + this.codeField.getText() + this.phoneField.getText());
                    tL_auth_sendCode.api_hash = C3791b.Q(ApplicationLoader.applicationContext);
                    tL_auth_sendCode.api_id = C3791b.P(ApplicationLoader.applicationContext);
                    tL_auth_sendCode.phone_number = b;
                    z2 = z3 && z;
                    tL_auth_sendCode.allow_flashcall = z2;
                    if (tL_auth_sendCode.allow_flashcall) {
                        try {
                            Object line1Number = telephonyManager.getLine1Number();
                            if (TextUtils.isEmpty(line1Number)) {
                                tL_auth_sendCode.current_number = false;
                            } else {
                                boolean z5 = b.contains(line1Number) || line1Number.contains(b);
                                tL_auth_sendCode.current_number = z5;
                                if (!tL_auth_sendCode.current_number) {
                                    tL_auth_sendCode.allow_flashcall = false;
                                }
                            }
                        } catch (Throwable e2) {
                            tL_auth_sendCode.allow_flashcall = false;
                            FileLog.e(e2);
                        }
                    }
                    final Bundle bundle = new Bundle();
                    bundle.putString("phone", "+" + this.codeField.getText() + this.phoneField.getText());
                    try {
                        bundle.putString("ephone", "+" + C2488b.b(this.codeField.getText().toString()) + " " + C2488b.b(this.phoneField.getText().toString()));
                    } catch (Throwable e22) {
                        FileLog.e(e22);
                        bundle.putString("ephone", "+" + b);
                    }
                    bundle.putString("phoneFormated", b);
                    this.nextPressed = true;
                    LoginActivity.this.needShowProgress(ConnectionsManager.getInstance().sendRequest(tL_auth_sendCode, new RequestDelegate() {
                        public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    PhoneView.this.nextPressed = false;
                                    if (tLRPC$TL_error == null) {
                                        LoginActivity.this.fillNextCodeParams(bundle, (TL_auth_sentCode) tLObject);
                                    } else if (tLRPC$TL_error.text != null) {
                                        if (tLRPC$TL_error.text.contains("PHONE_NUMBER_INVALID")) {
                                            LoginActivity.this.needShowInvalidAlert(tL_auth_sendCode.phone_number, false);
                                        } else if (tLRPC$TL_error.text.contains("PHONE_NUMBER_FLOOD")) {
                                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("PhoneNumberFlood", R.string.PhoneNumberFlood));
                                        } else if (tLRPC$TL_error.text.contains("PHONE_NUMBER_BANNED")) {
                                            LoginActivity.this.needShowInvalidAlert(tL_auth_sendCode.phone_number, true);
                                        } else if (tLRPC$TL_error.text.contains("PHONE_CODE_EMPTY") || tLRPC$TL_error.text.contains("PHONE_CODE_INVALID")) {
                                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidCode", R.string.InvalidCode));
                                        } else if (tLRPC$TL_error.text.contains("PHONE_CODE_EXPIRED")) {
                                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("CodeExpired", R.string.CodeExpired));
                                        } else if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("FloodWait", R.string.FloodWait));
                                        } else if (tLRPC$TL_error.code != C3446C.PRIORITY_DOWNLOAD) {
                                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), tLRPC$TL_error.text);
                                        }
                                    }
                                    LoginActivity.this.needHideProgress();
                                }
                            });
                        }
                    }, 27));
                }
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }

        public void onShow() {
            super.onShow();
            if (this.phoneField != null) {
                if (this.codeField.length() != 0) {
                    AndroidUtilities.showKeyboard(this.phoneField);
                    this.phoneField.requestFocus();
                    this.phoneField.setSelection(this.phoneField.length());
                } else {
                    AndroidUtilities.showKeyboard(this.codeField);
                    this.codeField.requestFocus();
                }
            }
            fillNumber();
        }

        public void restoreStateParams(Bundle bundle) {
            CharSequence string = bundle.getString("phoneview_code");
            if (string != null) {
                this.codeField.setText(string);
            }
            string = bundle.getString("phoneview_phone");
            if (string != null) {
                this.phoneField.setText(string);
            }
        }

        public void saveStateParams(Bundle bundle) {
            String obj = this.codeField.getText().toString();
            if (obj.length() != 0) {
                bundle.putString("phoneview_code", obj);
            }
            obj = this.phoneField.getText().toString();
            if (obj.length() != 0) {
                bundle.putString("phoneview_phone", obj);
            }
        }

        public void selectCountry(String str) {
            if (this.countriesArray.indexOf(str) != -1) {
                this.ignoreOnTextChange = true;
                String str2 = (String) this.countriesMap.get(str);
                this.codeField.setText(str2);
                this.countryButton.setText(str);
                str2 = (String) this.phoneFormatMap.get(str2);
                this.phoneField.setHintText(str2 != null ? str2.replace('X', '–') : null);
                this.countryState = 0;
                this.ignoreOnTextChange = false;
            }
        }
    }

    private class ProgressView extends View {
        private Paint paint = new Paint();
        private Paint paint2 = new Paint();
        private float progress;

        public ProgressView(Context context) {
            super(context);
            this.paint.setColor(Theme.getColor(Theme.key_login_progressInner));
            this.paint2.setColor(Theme.getColor(Theme.key_login_progressOuter));
        }

        protected void onDraw(Canvas canvas) {
            int measuredWidth = (int) (((float) getMeasuredWidth()) * this.progress);
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) measuredWidth, (float) getMeasuredHeight(), this.paint2);
            canvas.drawRect((float) measuredWidth, BitmapDescriptorFactory.HUE_RED, (float) getMeasuredWidth(), (float) getMeasuredHeight(), this.paint);
        }

        public void setProgress(float f) {
            this.progress = f;
            invalidate();
        }
    }

    private void clearCurrentState() {
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("logininfo2", 0).edit();
        edit.clear();
        edit.commit();
    }

    private void fillNextCodeParams(Bundle bundle, TL_auth_sentCode tL_auth_sentCode) {
        bundle.putString("phoneHash", tL_auth_sentCode.phone_code_hash);
        if (tL_auth_sentCode.next_type instanceof TL_auth_codeTypeCall) {
            bundle.putInt("nextType", 4);
        } else if (tL_auth_sentCode.next_type instanceof TL_auth_codeTypeFlashCall) {
            bundle.putInt("nextType", 3);
        } else if (tL_auth_sentCode.next_type instanceof TL_auth_codeTypeSms) {
            bundle.putInt("nextType", 2);
        }
        if (tL_auth_sentCode.type instanceof TL_auth_sentCodeTypeApp) {
            bundle.putInt(Param.TYPE, 1);
            bundle.putInt("length", tL_auth_sentCode.type.length);
            setPage(1, true, bundle, false);
            return;
        }
        if (tL_auth_sentCode.timeout == 0) {
            tL_auth_sentCode.timeout = 60;
        }
        bundle.putInt("timeout", tL_auth_sentCode.timeout * 1000);
        if (tL_auth_sentCode.type instanceof TL_auth_sentCodeTypeCall) {
            bundle.putInt(Param.TYPE, 4);
            bundle.putInt("length", tL_auth_sentCode.type.length);
            setPage(4, true, bundle, false);
        } else if (tL_auth_sentCode.type instanceof TL_auth_sentCodeTypeFlashCall) {
            bundle.putInt(Param.TYPE, 3);
            bundle.putString("pattern", tL_auth_sentCode.type.pattern);
            setPage(3, true, bundle, false);
        } else if (tL_auth_sentCode.type instanceof TL_auth_sentCodeTypeSms) {
            bundle.putInt(Param.TYPE, 2);
            bundle.putInt("length", tL_auth_sentCode.type.length);
            setPage(2, true, bundle, false);
        }
    }

    private Bundle loadCurrentState() {
        try {
            Bundle bundle = new Bundle();
            for (Entry entry : ApplicationLoader.applicationContext.getSharedPreferences("logininfo2", 0).getAll().entrySet()) {
                String str = (String) entry.getKey();
                Object value = entry.getValue();
                String[] split = str.split("_\\|_");
                if (split.length == 1) {
                    if (value instanceof String) {
                        bundle.putString(str, (String) value);
                    } else if (value instanceof Integer) {
                        bundle.putInt(str, ((Integer) value).intValue());
                    }
                } else if (split.length == 2) {
                    Bundle bundle2 = bundle.getBundle(split[0]);
                    if (bundle2 == null) {
                        bundle2 = new Bundle();
                        bundle.putBundle(split[0], bundle2);
                    }
                    if (value instanceof String) {
                        bundle2.putString(split[1], (String) value);
                    } else if (value instanceof Integer) {
                        bundle2.putInt(split[1], ((Integer) value).intValue());
                    }
                }
            }
            return bundle;
        } catch (Throwable e) {
            FileLog.e(e);
            return null;
        }
    }

    private void needFinishActivity() {
        clearCurrentState();
        presentFragment(new DialogsActivity(null), true);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
    }

    private void needShowAlert(String str, String str2) {
        if (str2 != null && getParentActivity() != null) {
            Builder builder = new Builder(getParentActivity());
            builder.setTitle(str);
            builder.setMessage(str2);
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            showDialog(builder.create());
        }
    }

    private void needShowInvalidAlert(final String str, final boolean z) {
        if (getParentActivity() != null) {
            Builder builder = new Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            if (z) {
                builder.setMessage(LocaleController.getString("BannedPhoneNumber", R.string.BannedPhoneNumber));
            } else {
                builder.setMessage(LocaleController.getString("InvalidPhoneNumber", R.string.InvalidPhoneNumber));
            }
            builder.setNeutralButton(LocaleController.getString("BotHelp", R.string.BotHelp), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        PackageInfo packageInfo = ApplicationLoader.applicationContext.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
                        String format = String.format(Locale.US, "%s (%d)", new Object[]{packageInfo.versionName, Integer.valueOf(packageInfo.versionCode)});
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.setType("message/rfc822");
                        intent.putExtra("android.intent.extra.EMAIL", new String[]{"login@stel.com"});
                        if (z) {
                            intent.putExtra("android.intent.extra.SUBJECT", "Banned phone number: " + str);
                            intent.putExtra("android.intent.extra.TEXT", "I'm trying to use my mobile phone number: " + str + "\nBut Telegram says it's banned. Please help.\n\nApp version: " + format + "\nOS version: SDK " + VERSION.SDK_INT + "\nDevice Name: " + Build.MANUFACTURER + Build.MODEL + "\nLocale: " + Locale.getDefault());
                        } else {
                            intent.putExtra("android.intent.extra.SUBJECT", "Invalid phone number: " + str);
                            intent.putExtra("android.intent.extra.TEXT", "I'm trying to use my mobile phone number: " + str + "\nBut Telegram says it's invalid. Please help.\n\nApp version: " + format + "\nOS version: SDK " + VERSION.SDK_INT + "\nDevice Name: " + Build.MANUFACTURER + Build.MODEL + "\nLocale: " + Locale.getDefault());
                        }
                        LoginActivity.this.getParentActivity().startActivity(Intent.createChooser(intent, "Send email..."));
                    } catch (Exception e) {
                        LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("NoMailInstalled", R.string.NoMailInstalled));
                    }
                }
            });
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            showDialog(builder.create());
        }
    }

    private void needShowProgress(final int i) {
        if (getParentActivity() != null && !getParentActivity().isFinishing() && this.progressDialog == null) {
            Builder builder = new Builder(getParentActivity(), 1);
            builder.setMessage(LocaleController.getString("Loading", R.string.Loading));
            if (i != 0) {
                builder.setPositiveButton(LocaleController.getString("Cancel", R.string.Cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivity.this.views[LoginActivity.this.currentViewNum].onCancelPressed();
                        ConnectionsManager.getInstance().cancelRequest(i, true);
                        LoginActivity.this.progressDialog = null;
                    }
                });
            }
            this.progressDialog = builder.show();
            this.progressDialog.setCanceledOnTouchOutside(false);
            this.progressDialog.setCancelable(false);
        }
    }

    private void putBundleToEditor(Bundle bundle, Editor editor, String str) {
        for (String str2 : bundle.keySet()) {
            Object obj = bundle.get(str2);
            if (obj instanceof String) {
                if (str != null) {
                    editor.putString(str + "_|_" + str2, (String) obj);
                } else {
                    editor.putString(str2, (String) obj);
                }
            } else if (obj instanceof Integer) {
                if (str != null) {
                    editor.putInt(str + "_|_" + str2, ((Integer) obj).intValue());
                } else {
                    editor.putInt(str2, ((Integer) obj).intValue());
                }
            } else if (obj instanceof Bundle) {
                putBundleToEditor((Bundle) obj, editor, str2);
            }
        }
    }

    private void showLoadingDialog(Context context, String str, String str2) {
        this.dialogLoading = new ProgressDialog(getParentActivity());
        this.dialogLoading.setTitle(str);
        this.dialogLoading.setMessage(str2);
        this.dialogLoading.show();
    }

    public View createView(Context context) {
        int i;
        this.actionBar.setTitle(LocaleController.getString("AppName", R.string.AppName));
        this.actionBar.setActionBarMenuOnItemClick(new C48711());
        ActionBarMenu createMenu = this.actionBar.createMenu();
        this.actionBar.setAllowOverlayTitle(true);
        this.doneButton = createMenu.addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.fragmentView = new ScrollView(context);
        ScrollView scrollView = (ScrollView) this.fragmentView;
        scrollView.setFillViewport(true);
        View frameLayout = new FrameLayout(context);
        scrollView.addView(frameLayout, LayoutHelper.createScroll(-1, -2, 51));
        this.views[0] = new PhoneView(context);
        this.views[1] = new LoginActivitySmsView(context, 1);
        this.views[2] = new LoginActivitySmsView(context, 2);
        this.views[3] = new LoginActivitySmsView(context, 3);
        this.views[4] = new LoginActivitySmsView(context, 4);
        this.views[5] = new LoginActivityRegisterView(context);
        this.views[6] = new LoginActivityPasswordView(context);
        this.views[7] = new LoginActivityRecoverView(this, context);
        this.views[8] = new LoginActivityResetWaitView(this, context);
        int i2 = 0;
        while (i2 < this.views.length) {
            this.views[i2].setVisibility(i2 == 0 ? 0 : 8);
            frameLayout.addView(this.views[i2], LayoutHelper.createFrame(-1, i2 == 0 ? -2.0f : -1.0f, 51, AndroidUtilities.isTablet() ? 26.0f : 18.0f, 30.0f, AndroidUtilities.isTablet() ? 26.0f : 18.0f, BitmapDescriptorFactory.HUE_RED));
            i2++;
        }
        Bundle loadCurrentState = loadCurrentState();
        if (loadCurrentState != null) {
            this.currentViewNum = loadCurrentState.getInt("currentViewNum", 0);
            if (this.currentViewNum >= 1 && this.currentViewNum <= 4) {
                i = loadCurrentState.getInt("open");
                if (i != 0 && Math.abs((System.currentTimeMillis() / 1000) - ((long) i)) >= 86400) {
                    this.currentViewNum = 0;
                    loadCurrentState = null;
                    clearCurrentState();
                }
            }
        }
        this.actionBar.setTitle(this.views[this.currentViewNum].getHeaderName());
        i = 0;
        while (i < this.views.length) {
            if (loadCurrentState != null) {
                if (i < 1 || i > 4) {
                    this.views[i].restoreStateParams(loadCurrentState);
                } else if (i == this.currentViewNum) {
                    this.views[i].restoreStateParams(loadCurrentState);
                }
            }
            if (this.currentViewNum == i) {
                this.actionBar.setBackButtonImage(this.views[i].needBackButton() ? R.drawable.ic_ab_back : 0);
                this.views[i].setVisibility(0);
                this.views[i].onShow();
                if (i == 3 || i == 8) {
                    this.doneButton.setVisibility(8);
                }
            } else {
                this.views[i].setVisibility(8);
            }
            i++;
        }
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didUpdatedConnectionState);
        C2818c.a(getParentActivity(), new C48722()).d();
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        try {
            if (i == NotificationCenter.didUpdatedConnectionState) {
                int connectionState = ConnectionsManager.getInstance().getConnectionState();
                View view = ((PhoneView) this.views[0]).btnRetry;
                View access$000 = ((PhoneView) this.views[0]).textView3;
                View access$100 = ((PhoneView) this.views[0]).textView3Bold;
                switch (connectionState) {
                    case 3:
                    case 5:
                        view.setVisibility(8);
                        access$000.setVisibility(8);
                        access$100.setVisibility(8);
                        this.canContinueInNumberInsertion = true;
                        return;
                    default:
                        view.setVisibility(0);
                        access$000.setVisibility(0);
                        access$100.setVisibility(0);
                        this.canContinueInNumberInsertion = false;
                        return;
                }
            }
        } catch (Exception e) {
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        PhoneView phoneView = (PhoneView) this.views[0];
        LoginActivitySmsView loginActivitySmsView = (LoginActivitySmsView) this.views[1];
        LoginActivitySmsView loginActivitySmsView2 = (LoginActivitySmsView) this.views[2];
        LoginActivitySmsView loginActivitySmsView3 = (LoginActivitySmsView) this.views[3];
        LoginActivitySmsView loginActivitySmsView4 = (LoginActivitySmsView) this.views[4];
        LoginActivityRegisterView loginActivityRegisterView = (LoginActivityRegisterView) this.views[5];
        LoginActivityPasswordView loginActivityPasswordView = (LoginActivityPasswordView) this.views[6];
        LoginActivityRecoverView loginActivityRecoverView = (LoginActivityRecoverView) this.views[7];
        LoginActivityResetWaitView loginActivityResetWaitView = (LoginActivityResetWaitView) this.views[8];
        r18 = new ThemeDescription[86];
        r18[54] = new ThemeDescription(loginActivitySmsView.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        r18[55] = new ThemeDescription(loginActivitySmsView.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        r18[56] = new ThemeDescription(loginActivitySmsView2.confirmTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r18[57] = new ThemeDescription(loginActivitySmsView2.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r18[58] = new ThemeDescription(loginActivitySmsView2.codeField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r18[59] = new ThemeDescription(loginActivitySmsView2.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        r18[60] = new ThemeDescription(loginActivitySmsView2.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        r18[61] = new ThemeDescription(loginActivitySmsView2.timeText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r18[62] = new ThemeDescription(loginActivitySmsView2.problemText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r18[63] = new ThemeDescription(loginActivitySmsView2.wrongNumber, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r18[64] = new ThemeDescription(loginActivitySmsView2.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        r18[65] = new ThemeDescription(loginActivitySmsView2.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        r18[66] = new ThemeDescription(loginActivitySmsView3.confirmTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r18[67] = new ThemeDescription(loginActivitySmsView3.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r18[68] = new ThemeDescription(loginActivitySmsView3.codeField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r18[69] = new ThemeDescription(loginActivitySmsView3.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        r18[70] = new ThemeDescription(loginActivitySmsView3.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        r18[71] = new ThemeDescription(loginActivitySmsView3.timeText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r18[72] = new ThemeDescription(loginActivitySmsView3.problemText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r18[73] = new ThemeDescription(loginActivitySmsView3.wrongNumber, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r18[74] = new ThemeDescription(loginActivitySmsView3.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        r18[75] = new ThemeDescription(loginActivitySmsView3.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        r18[76] = new ThemeDescription(loginActivitySmsView4.confirmTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r18[77] = new ThemeDescription(loginActivitySmsView4.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r18[78] = new ThemeDescription(loginActivitySmsView4.codeField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r18[79] = new ThemeDescription(loginActivitySmsView4.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        r18[80] = new ThemeDescription(loginActivitySmsView4.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        r18[81] = new ThemeDescription(loginActivitySmsView4.timeText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r18[82] = new ThemeDescription(loginActivitySmsView4.problemText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r18[83] = new ThemeDescription(loginActivitySmsView4.wrongNumber, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r18[84] = new ThemeDescription(loginActivitySmsView4.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        r18[85] = new ThemeDescription(loginActivitySmsView4.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        return r18;
    }

    public void needHideProgress() {
        if (this.progressDialog != null) {
            try {
                this.progressDialog.dismiss();
            } catch (Throwable e) {
                FileLog.e(e);
            }
            this.progressDialog = null;
        }
    }

    public boolean onBackPressed() {
        int i = 0;
        if (this.currentViewNum == 0) {
            while (i < this.views.length) {
                if (this.views[i] != null) {
                    this.views[i].onDestroyActivity();
                }
                i++;
            }
            clearCurrentState();
            return true;
        } else if (this.currentViewNum == 6) {
            this.views[this.currentViewNum].onBackPressed();
            setPage(0, true, null, true);
            return false;
        } else if (this.currentViewNum != 7 && this.currentViewNum != 8) {
            return false;
        } else {
            this.views[this.currentViewNum].onBackPressed();
            setPage(6, true, null, true);
            return false;
        }
    }

    protected void onDialogDismiss(Dialog dialog) {
        if (VERSION.SDK_INT < 23) {
            return;
        }
        if (dialog == this.permissionsDialog && !this.permissionsItems.isEmpty() && getParentActivity() != null) {
            try {
                getParentActivity().requestPermissions((String[]) this.permissionsItems.toArray(new String[this.permissionsItems.size()]), 6);
            } catch (Exception e) {
            }
        } else if (dialog == this.permissionsShowDialog && !this.permissionsShowItems.isEmpty() && getParentActivity() != null) {
            try {
                getParentActivity().requestPermissions((String[]) this.permissionsShowItems.toArray(new String[this.permissionsShowItems.size()]), 7);
            } catch (Exception e2) {
            }
        }
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        for (int i = 0; i < this.views.length; i++) {
            if (this.views[i] != null) {
                this.views[i].onDestroyActivity();
            }
        }
        if (this.progressDialog != null) {
            try {
                this.progressDialog.dismiss();
            } catch (Throwable e) {
                FileLog.e(e);
            }
            this.progressDialog = null;
        }
    }

    public void onPause() {
        super.onPause();
        AndroidUtilities.removeAdjustResize(getParentActivity(), this.classGuid);
    }

    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        if (i == 6) {
            this.checkPermissions = false;
            if (this.currentViewNum == 0) {
                this.views[this.currentViewNum].onNextPressed();
            }
        } else if (i == 7) {
            this.checkShowPermissions = false;
            if (this.currentViewNum == 0) {
                ((PhoneView) this.views[this.currentViewNum]).fillNumber();
            }
        }
    }

    public void onResume() {
        super.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
        try {
            if (this.currentViewNum >= 1 && this.currentViewNum <= 4 && (this.views[this.currentViewNum] instanceof LoginActivitySmsView)) {
                int access$400 = ((LoginActivitySmsView) this.views[this.currentViewNum]).openTime;
                if (access$400 != 0 && Math.abs((System.currentTimeMillis() / 1000) - ((long) access$400)) >= 86400) {
                    this.views[this.currentViewNum].onBackPressed();
                    setPage(0, false, null, true);
                }
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    public void saveSelfArgs(Bundle bundle) {
        try {
            Bundle bundle2 = new Bundle();
            bundle2.putInt("currentViewNum", this.currentViewNum);
            for (int i = 0; i <= this.currentViewNum; i++) {
                SlideView slideView = this.views[i];
                if (slideView != null) {
                    slideView.saveStateParams(bundle2);
                }
            }
            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("logininfo2", 0).edit();
            edit.clear();
            putBundleToEditor(bundle2, edit, null);
            edit.commit();
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    public void setPage(int i, boolean z, Bundle bundle, boolean z2) {
        int i2 = R.drawable.ic_ab_back;
        if (i == 3 || i == 8) {
            this.doneButton.setVisibility(8);
        } else {
            if (i == 0) {
                this.checkPermissions = true;
                this.checkShowPermissions = true;
            }
            this.doneButton.setVisibility(0);
        }
        if (z) {
            final SlideView slideView = this.views[this.currentViewNum];
            final SlideView slideView2 = this.views[i];
            this.currentViewNum = i;
            ActionBar actionBar = this.actionBar;
            if (!slideView2.needBackButton()) {
                i2 = 0;
            }
            actionBar.setBackButtonImage(i2);
            slideView2.setParams(bundle, false);
            this.actionBar.setTitle(slideView2.getHeaderName());
            slideView2.onShow();
            slideView2.setX(z2 ? (float) (-AndroidUtilities.displaySize.x) : (float) AndroidUtilities.displaySize.x);
            slideView.animate().setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new AnimatorListener() {
                public void onAnimationCancel(Animator animator) {
                }

                @SuppressLint({"NewApi"})
                public void onAnimationEnd(Animator animator) {
                    slideView.setVisibility(8);
                    slideView.setX(BitmapDescriptorFactory.HUE_RED);
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                }
            }).setDuration(300).translationX(z2 ? (float) AndroidUtilities.displaySize.x : (float) (-AndroidUtilities.displaySize.x)).start();
            slideView2.animate().setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new AnimatorListener() {
                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                    slideView2.setVisibility(0);
                }
            }).setDuration(300).translationX(BitmapDescriptorFactory.HUE_RED).start();
            return;
        }
        ActionBar actionBar2 = this.actionBar;
        if (!this.views[i].needBackButton()) {
            i2 = 0;
        }
        actionBar2.setBackButtonImage(i2);
        this.views[this.currentViewNum].setVisibility(8);
        this.currentViewNum = i;
        this.views[i].setParams(bundle, false);
        this.views[i].setVisibility(0);
        this.actionBar.setTitle(this.views[i].getHeaderName());
        this.views[i].onShow();
    }
}
