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
import android.view.ViewPropertyAnimator;
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
import com.coremedia.iso.boxes.TrackReferenceTypeBox;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.persianswitch.sdk.base.log.LogCollector;
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
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Internet.SLSProxyHelper;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.customization.util.AppUtilities;
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
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_account_deleteAccount;
import org.telegram.tgnet.TLRPC$TL_account_getPassword;
import org.telegram.tgnet.TLRPC$TL_account_password;
import org.telegram.tgnet.TLRPC$TL_auth_authorization;
import org.telegram.tgnet.TLRPC$TL_auth_cancelCode;
import org.telegram.tgnet.TLRPC$TL_auth_checkPassword;
import org.telegram.tgnet.TLRPC$TL_auth_codeTypeCall;
import org.telegram.tgnet.TLRPC$TL_auth_codeTypeFlashCall;
import org.telegram.tgnet.TLRPC$TL_auth_codeTypeSms;
import org.telegram.tgnet.TLRPC$TL_auth_passwordRecovery;
import org.telegram.tgnet.TLRPC$TL_auth_recoverPassword;
import org.telegram.tgnet.TLRPC$TL_auth_requestPasswordRecovery;
import org.telegram.tgnet.TLRPC$TL_auth_resendCode;
import org.telegram.tgnet.TLRPC$TL_auth_sendCode;
import org.telegram.tgnet.TLRPC$TL_auth_sentCode;
import org.telegram.tgnet.TLRPC$TL_auth_sentCodeTypeApp;
import org.telegram.tgnet.TLRPC$TL_auth_sentCodeTypeCall;
import org.telegram.tgnet.TLRPC$TL_auth_sentCodeTypeFlashCall;
import org.telegram.tgnet.TLRPC$TL_auth_sentCodeTypeSms;
import org.telegram.tgnet.TLRPC$TL_auth_signIn;
import org.telegram.tgnet.TLRPC$TL_auth_signUp;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.User;
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
import utils.app.AppPreferences;
import utils.view.Constants;
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
    class C30321 extends ActionBarMenuOnItemClick {
        C30321() {
        }

        public void onItemClick(int id) {
            if (id == 1) {
                if (LoginActivity.this.canContinueInNumberInsertion) {
                    LoginActivity.this.views[LoginActivity.this.currentViewNum].onNextPressed();
                    return;
                }
                Builder builder = new Builder(LoginActivity.this.getParentActivity());
                builder.setTitle(LocaleController.getString("RetryProxyHint", R.string.RetryProxyHint));
                builder.setMessage(LocaleController.getString("RetryProxyAlert", R.string.RetryProxyAlert));
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                LoginActivity.this.showDialog(builder.create());
            } else if (id == -1) {
                LoginActivity.this.onBackPressed();
            }
        }
    }

    /* renamed from: org.telegram.ui.LoginActivity$2 */
    class C30332 implements IResponseReceiver {
        C30332() {
        }

        public void onResult(Object object, int StatusCode) {
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
        class C30484 implements RequestDelegate {
            C30484() {
            }

            public void run(final TLObject response, final TLRPC$TL_error error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        LoginActivity.this.needHideProgress();
                        LoginActivityPasswordView.this.nextPressed = false;
                        if (error == null) {
                            TLRPC$TL_auth_authorization res = response;
                            ConnectionsManager.getInstance().setUserId(res.user.id);
                            UserConfig.clearConfig();
                            MessagesController.getInstance().cleanup();
                            UserConfig.setCurrentUser(res.user);
                            UserConfig.saveConfig(true);
                            MessagesStorage.getInstance().cleanup(true);
                            ArrayList<User> users = new ArrayList();
                            users.add(res.user);
                            MessagesStorage.getInstance().putUsersAndChats(users, null, true, true);
                            MessagesController.getInstance().putUser(res.user, false);
                            ContactsController.getInstance().checkAppAccount();
                            MessagesController.getInstance().getBlockedUsers(true);
                            ConnectionsManager.getInstance().updateDcSettings();
                            LoginActivity.this.needFinishActivity();
                        } else if (error.text.equals("PASSWORD_HASH_INVALID")) {
                            LoginActivityPasswordView.this.onPasscodeError(true);
                        } else if (error.text.startsWith("FLOOD_WAIT")) {
                            String timeString;
                            int time = Utilities.parseInt(error.text).intValue();
                            if (time < 60) {
                                timeString = LocaleController.formatPluralString("Seconds", time);
                            } else {
                                timeString = LocaleController.formatPluralString("Minutes", time / 60);
                            }
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{timeString}));
                        } else {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), error.text);
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
            this.codeField.setInputType(TsExtractor.TS_STREAM_TYPE_AC3);
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
                class C30411 implements RequestDelegate {
                    C30411() {
                    }

                    public void run(final TLObject response, final TLRPC$TL_error error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                LoginActivity.this.needHideProgress();
                                if (error == null) {
                                    final TLRPC$TL_auth_passwordRecovery res = response;
                                    Builder builder = new Builder(LoginActivity.this.getParentActivity());
                                    builder.setMessage(LocaleController.formatString("RestoreEmailSent", R.string.RestoreEmailSent, new Object[]{res.email_pattern}));
                                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("email_unconfirmed_pattern", res.email_pattern);
                                            LoginActivity.this.setPage(7, true, bundle, false);
                                        }
                                    });
                                    Dialog dialog = LoginActivity.this.showDialog(builder.create());
                                    if (dialog != null) {
                                        dialog.setCanceledOnTouchOutside(false);
                                        dialog.setCancelable(false);
                                    }
                                } else if (error.text.startsWith("FLOOD_WAIT")) {
                                    String timeString;
                                    int time = Utilities.parseInt(error.text).intValue();
                                    if (time < 60) {
                                        timeString = LocaleController.formatPluralString("Seconds", time);
                                    } else {
                                        timeString = LocaleController.formatPluralString("Minutes", time / 60);
                                    }
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{timeString}));
                                } else {
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), error.text);
                                }
                            }
                        });
                    }
                }

                public void onClick(View view) {
                    if (LoginActivityPasswordView.this.has_recovery) {
                        LoginActivity.this.needShowProgress(0);
                        ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_auth_requestPasswordRecovery(), new C30411(), 10);
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
                class C30451 implements DialogInterface.OnClickListener {

                    /* renamed from: org.telegram.ui.LoginActivity$LoginActivityPasswordView$3$1$1 */
                    class C30441 implements RequestDelegate {
                        C30441() {
                        }

                        public void run(TLObject response, final TLRPC$TL_error error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    LoginActivity.this.needHideProgress();
                                    Bundle params;
                                    if (error == null) {
                                        params = new Bundle();
                                        params.putString("phoneFormated", LoginActivityPasswordView.this.requestPhone);
                                        params.putString("phoneHash", LoginActivityPasswordView.this.phoneHash);
                                        params.putString("code", LoginActivityPasswordView.this.phoneCode);
                                        LoginActivity.this.setPage(5, true, params, false);
                                    } else if (error.text.equals("2FA_RECENT_CONFIRM")) {
                                        LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("ResetAccountCancelledAlert", R.string.ResetAccountCancelledAlert));
                                    } else if (error.text.startsWith("2FA_CONFIRM_WAIT_")) {
                                        params = new Bundle();
                                        params.putString("phoneFormated", LoginActivityPasswordView.this.requestPhone);
                                        params.putString("phoneHash", LoginActivityPasswordView.this.phoneHash);
                                        params.putString("code", LoginActivityPasswordView.this.phoneCode);
                                        params.putInt("startTime", ConnectionsManager.getInstance().getCurrentTime());
                                        params.putInt("waitTime", Utilities.parseInt(error.text.replace("2FA_CONFIRM_WAIT_", "")).intValue());
                                        LoginActivity.this.setPage(8, true, params, false);
                                    } else {
                                        LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), error.text);
                                    }
                                }
                            });
                        }
                    }

                    C30451() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivity.this.needShowProgress(0);
                        TLRPC$TL_account_deleteAccount req = new TLRPC$TL_account_deleteAccount();
                        req.reason = "Forgot password";
                        ConnectionsManager.getInstance().sendRequest(req, new C30441(), 10);
                    }
                }

                public void onClick(View view) {
                    Builder builder = new Builder(LoginActivity.this.getParentActivity());
                    builder.setMessage(LocaleController.getString("ResetMyAccountWarningText", R.string.ResetMyAccountWarningText));
                    builder.setTitle(LocaleController.getString("ResetMyAccountWarning", R.string.ResetMyAccountWarning));
                    builder.setPositiveButton(LocaleController.getString("ResetMyAccountWarningReset", R.string.ResetMyAccountWarningReset), new C30451());
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

        public String getHeaderName() {
            return LocaleController.getString("LoginPassword", R.string.LoginPassword);
        }

        public void onCancelPressed() {
            this.nextPressed = false;
        }

        public void setParams(Bundle params, boolean restore) {
            boolean z = true;
            if (params != null) {
                if (params.isEmpty()) {
                    this.resetAccountButton.setVisibility(0);
                    this.resetAccountText.setVisibility(0);
                    AndroidUtilities.hideKeyboard(this.codeField);
                    return;
                }
                this.resetAccountButton.setVisibility(8);
                this.resetAccountText.setVisibility(8);
                this.codeField.setText("");
                this.currentParams = params;
                this.current_salt = Utilities.hexToBytes(this.currentParams.getString("current_salt"));
                this.hint = this.currentParams.getString(TrackReferenceTypeBox.TYPE1);
                if (this.currentParams.getInt("has_recovery") != 1) {
                    z = false;
                }
                this.has_recovery = z;
                this.email_unconfirmed_pattern = this.currentParams.getString("email_unconfirmed_pattern");
                this.requestPhone = params.getString("phoneFormated");
                this.phoneHash = params.getString("phoneHash");
                this.phoneCode = params.getString("code");
                if (this.hint == null || this.hint.length() <= 0) {
                    this.codeField.setHint(LocaleController.getString("LoginPassword", R.string.LoginPassword));
                } else {
                    this.codeField.setHint(this.hint);
                }
            }
        }

        private void onPasscodeError(boolean clear) {
            if (LoginActivity.this.getParentActivity() != null) {
                Vibrator v = (Vibrator) LoginActivity.this.getParentActivity().getSystemService("vibrator");
                if (v != null) {
                    v.vibrate(200);
                }
                if (clear) {
                    this.codeField.setText("");
                }
                AndroidUtilities.shakeView(this.confirmTextView, 2.0f, 0);
            }
        }

        public void onNextPressed() {
            if (!this.nextPressed) {
                String oldPassword = this.codeField.getText().toString();
                if (oldPassword.length() == 0) {
                    onPasscodeError(false);
                    return;
                }
                this.nextPressed = true;
                byte[] oldPasswordBytes = null;
                try {
                    oldPasswordBytes = oldPassword.getBytes("UTF-8");
                } catch (Exception e) {
                    FileLog.e(e);
                }
                LoginActivity.this.needShowProgress(0);
                byte[] hash = new byte[((this.current_salt.length * 2) + oldPasswordBytes.length)];
                System.arraycopy(this.current_salt, 0, hash, 0, this.current_salt.length);
                System.arraycopy(oldPasswordBytes, 0, hash, this.current_salt.length, oldPasswordBytes.length);
                System.arraycopy(this.current_salt, 0, hash, hash.length - this.current_salt.length, this.current_salt.length);
                TLRPC$TL_auth_checkPassword req = new TLRPC$TL_auth_checkPassword();
                req.password_hash = Utilities.computeSHA256(hash, 0, hash.length);
                ConnectionsManager.getInstance().sendRequest(req, new C30484(), 10);
            }
        }

        public boolean needBackButton() {
            return true;
        }

        public void onBackPressed() {
            this.currentParams = null;
        }

        public void onShow() {
            super.onShow();
            if (this.codeField != null) {
                this.codeField.requestFocus();
                this.codeField.setSelection(this.codeField.length());
                AndroidUtilities.showKeyboard(this.codeField);
            }
        }

        public void saveStateParams(Bundle bundle) {
            String code = this.codeField.getText().toString();
            if (code.length() != 0) {
                bundle.putString("passview_code", code);
            }
            if (this.currentParams != null) {
                bundle.putBundle("passview_params", this.currentParams);
            }
        }

        public void restoreStateParams(Bundle bundle) {
            this.currentParams = bundle.getBundle("passview_params");
            if (this.currentParams != null) {
                setParams(this.currentParams, true);
            }
            String code = bundle.getString("passview_code");
            if (code != null) {
                this.codeField.setText(code);
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
        class C30533 implements RequestDelegate {
            C30533() {
            }

            public void run(final TLObject response, final TLRPC$TL_error error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        LoginActivityRecoverView.this.this$0.needHideProgress();
                        LoginActivityRecoverView.this.nextPressed = false;
                        if (error == null) {
                            TLRPC$TL_auth_authorization res = response;
                            ConnectionsManager.getInstance().setUserId(res.user.id);
                            UserConfig.clearConfig();
                            MessagesController.getInstance().cleanup();
                            UserConfig.setCurrentUser(res.user);
                            UserConfig.saveConfig(true);
                            MessagesStorage.getInstance().cleanup(true);
                            ArrayList<User> users = new ArrayList();
                            users.add(res.user);
                            MessagesStorage.getInstance().putUsersAndChats(users, null, true, true);
                            MessagesController.getInstance().putUser(res.user, false);
                            ContactsController.getInstance().checkAppAccount();
                            MessagesController.getInstance().getBlockedUsers(true);
                            ConnectionsManager.getInstance().updateDcSettings();
                            LoginActivityRecoverView.this.this$0.needFinishActivity();
                        } else if (error.text.startsWith("CODE_INVALID")) {
                            LoginActivityRecoverView.this.onPasscodeError(true);
                        } else if (error.text.startsWith("FLOOD_WAIT")) {
                            String timeString;
                            int time = Utilities.parseInt(error.text).intValue();
                            if (time < 60) {
                                timeString = LocaleController.formatPluralString("Seconds", time);
                            } else {
                                timeString = LocaleController.formatPluralString("Minutes", time / 60);
                            }
                            LoginActivityRecoverView.this.this$0.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{timeString}));
                        } else {
                            LoginActivityRecoverView.this.this$0.needShowAlert(LocaleController.getString("AppName", R.string.AppName), error.text);
                        }
                    }
                });
            }
        }

        public LoginActivityRecoverView(final LoginActivity this$0, Context context) {
            int i;
            int i2 = 5;
            this.this$0 = this$0;
            super(context);
            setOrientation(1);
            this.confirmTextView = new TextView(context);
            this.confirmTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.confirmTextView.setTextSize(1, 14.0f);
            this.confirmTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            this.confirmTextView.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            this.confirmTextView.setText(LocaleController.getString("RestoreEmailSentInfo", R.string.RestoreEmailSentInfo));
            View view = this.confirmTextView;
            if (LocaleController.isRTL) {
                i = 5;
            } else {
                i = 3;
            }
            addView(view, LayoutHelper.createLinear(-2, -2, i));
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
            View view2 = this.cancelButton;
            if (!LocaleController.isRTL) {
                i2 = 3;
            }
            addView(view2, LayoutHelper.createLinear(-2, -2, i2 | 80, 0, 0, 0, 14));
            this.cancelButton.setOnClickListener(new OnClickListener() {

                /* renamed from: org.telegram.ui.LoginActivity$LoginActivityRecoverView$2$1 */
                class C30501 implements DialogInterface.OnClickListener {
                    C30501() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivityRecoverView.this.this$0.setPage(6, true, new Bundle(), true);
                    }
                }

                public void onClick(View view) {
                    Builder builder = new Builder(LoginActivityRecoverView.this.this$0.getParentActivity());
                    builder.setMessage(LocaleController.getString("RestoreEmailTroubleText", R.string.RestoreEmailTroubleText));
                    builder.setTitle(LocaleController.getString("RestorePasswordNoEmailTitle", R.string.RestorePasswordNoEmailTitle));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C30501());
                    Dialog dialog = LoginActivityRecoverView.this.this$0.showDialog(builder.create());
                    if (dialog != null) {
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);
                    }
                }
            });
        }

        public boolean needBackButton() {
            return true;
        }

        public void onCancelPressed() {
            this.nextPressed = false;
        }

        public String getHeaderName() {
            return LocaleController.getString("LoginPassword", R.string.LoginPassword);
        }

        public void setParams(Bundle params, boolean restore) {
            if (params != null) {
                this.codeField.setText("");
                this.currentParams = params;
                this.email_unconfirmed_pattern = this.currentParams.getString("email_unconfirmed_pattern");
                this.cancelButton.setText(LocaleController.formatString("RestoreEmailTrouble", R.string.RestoreEmailTrouble, new Object[]{this.email_unconfirmed_pattern}));
                AndroidUtilities.showKeyboard(this.codeField);
                this.codeField.requestFocus();
            }
        }

        private void onPasscodeError(boolean clear) {
            if (this.this$0.getParentActivity() != null) {
                Vibrator v = (Vibrator) this.this$0.getParentActivity().getSystemService("vibrator");
                if (v != null) {
                    v.vibrate(200);
                }
                if (clear) {
                    this.codeField.setText("");
                }
                AndroidUtilities.shakeView(this.confirmTextView, 2.0f, 0);
            }
        }

        public void onNextPressed() {
            if (!this.nextPressed) {
                if (this.codeField.getText().toString().length() == 0) {
                    onPasscodeError(false);
                    return;
                }
                this.nextPressed = true;
                String code = this.codeField.getText().toString();
                if (code.length() == 0) {
                    onPasscodeError(false);
                    return;
                }
                this.this$0.needShowProgress(0);
                TLRPC$TL_auth_recoverPassword req = new TLRPC$TL_auth_recoverPassword();
                req.code = code;
                ConnectionsManager.getInstance().sendRequest(req, new C30533(), 10);
            }
        }

        public void onBackPressed() {
            this.currentParams = null;
        }

        public void onShow() {
            super.onShow();
            if (this.codeField != null) {
                this.codeField.requestFocus();
                this.codeField.setSelection(this.codeField.length());
            }
        }

        public void saveStateParams(Bundle bundle) {
            String code = this.codeField.getText().toString();
            if (!(code == null || code.length() == 0)) {
                bundle.putString("recoveryview_code", code);
            }
            if (this.currentParams != null) {
                bundle.putBundle("recoveryview_params", this.currentParams);
            }
        }

        public void restoreStateParams(Bundle bundle) {
            this.currentParams = bundle.getBundle("recoveryview_params");
            if (this.currentParams != null) {
                setParams(this.currentParams, true);
            }
            String code = bundle.getString("recoveryview_code");
            if (code != null) {
                this.codeField.setText(code);
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
        class C30594 implements RequestDelegate {
            C30594() {
            }

            public void run(final TLObject response, final TLRPC$TL_error error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        LoginActivityRegisterView.this.nextPressed = false;
                        LoginActivity.this.needHideProgress();
                        if (error == null) {
                            TLRPC$TL_auth_authorization res = response;
                            ConnectionsManager.getInstance().setUserId(res.user.id);
                            UserConfig.clearConfig();
                            MessagesController.getInstance().cleanup();
                            UserConfig.setCurrentUser(res.user);
                            UserConfig.saveConfig(true);
                            MessagesStorage.getInstance().cleanup(true);
                            ArrayList<User> users = new ArrayList();
                            users.add(res.user);
                            MessagesStorage.getInstance().putUsersAndChats(users, null, true, true);
                            MessagesController.getInstance().putUser(res.user, false);
                            ContactsController.getInstance().checkAppAccount();
                            MessagesController.getInstance().getBlockedUsers(true);
                            ConnectionsManager.getInstance().updateDcSettings();
                            LoginActivity.this.needFinishActivity();
                        } else if (error.text.contains("PHONE_NUMBER_INVALID")) {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidPhoneNumber", R.string.InvalidPhoneNumber));
                        } else if (error.text.contains("PHONE_CODE_EMPTY") || error.text.contains("PHONE_CODE_INVALID")) {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidCode", R.string.InvalidCode));
                        } else if (error.text.contains("PHONE_CODE_EXPIRED")) {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("CodeExpired", R.string.CodeExpired));
                        } else if (error.text.contains("FIRSTNAME_INVALID")) {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidFirstName", R.string.InvalidFirstName));
                        } else if (error.text.contains("LASTNAME_INVALID")) {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidLastName", R.string.InvalidLastName));
                        } else {
                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), error.text);
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
            this.firstNameField.setHint(LocaleController.getString(Constants.MONO_USER_MANAGEMENT_FIRST_NAME, R.string.FirstName));
            this.firstNameField.setImeOptions(268435461);
            this.firstNameField.setTextSize(1, 18.0f);
            this.firstNameField.setMaxLines(1);
            this.firstNameField.setInputType(8192);
            addView(this.firstNameField, LayoutHelper.createLinear(-1, 36, 0.0f, 26.0f, 0.0f, 0.0f));
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
            this.lastNameField.setHint(LocaleController.getString(Constants.MONO_USER_MANAGEMENT_LAST_NAME, R.string.LastName));
            this.lastNameField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.lastNameField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.lastNameField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
            this.lastNameField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.lastNameField.setCursorSize(AndroidUtilities.dp(20.0f));
            this.lastNameField.setCursorWidth(1.5f);
            this.lastNameField.setImeOptions(268435462);
            this.lastNameField.setTextSize(1, 18.0f);
            this.lastNameField.setMaxLines(1);
            this.lastNameField.setInputType(8192);
            addView(this.lastNameField, LayoutHelper.createLinear(-1, 36, 0.0f, 10.0f, 0.0f, 0.0f));
            this.lastNameField.setOnEditorActionListener(new OnEditorActionListener(LoginActivity.this) {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 6 && i != 5) {
                        return false;
                    }
                    LoginActivityRegisterView.this.onNextPressed();
                    return true;
                }
            });
            LinearLayout linearLayout = new LinearLayout(context);
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
                class C30561 implements DialogInterface.OnClickListener {
                    C30561() {
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
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C30561());
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    LoginActivity.this.showDialog(builder.create());
                }
            });
        }

        public void onBackPressed() {
            this.currentParams = null;
        }

        public String getHeaderName() {
            return LocaleController.getString("YourName", R.string.YourName);
        }

        public void onCancelPressed() {
            this.nextPressed = false;
        }

        public void onShow() {
            super.onShow();
            if (this.firstNameField != null) {
                this.firstNameField.requestFocus();
                this.firstNameField.setSelection(this.firstNameField.length());
            }
        }

        public void setParams(Bundle params, boolean restore) {
            if (params != null) {
                this.firstNameField.setText("");
                this.lastNameField.setText("");
                this.requestPhone = params.getString("phoneFormated");
                this.phoneHash = params.getString("phoneHash");
                this.phoneCode = params.getString("code");
                this.currentParams = params;
            }
        }

        public void onNextPressed() {
            if (!this.nextPressed) {
                this.nextPressed = true;
                TLRPC$TL_auth_signUp req = new TLRPC$TL_auth_signUp();
                req.phone_code = this.phoneCode;
                req.phone_code_hash = this.phoneHash;
                req.phone_number = this.requestPhone;
                req.first_name = this.firstNameField.getText().toString();
                req.last_name = this.lastNameField.getText().toString();
                LoginActivity.this.needShowProgress(0);
                ConnectionsManager.getInstance().sendRequest(req, new C30594(), 10);
            }
        }

        public void saveStateParams(Bundle bundle) {
            String first = this.firstNameField.getText().toString();
            if (first.length() != 0) {
                bundle.putString("registerview_first", first);
            }
            String last = this.lastNameField.getText().toString();
            if (last.length() != 0) {
                bundle.putString("registerview_last", last);
            }
            if (this.currentParams != null) {
                bundle.putBundle("registerview_params", this.currentParams);
            }
        }

        public void restoreStateParams(Bundle bundle) {
            this.currentParams = bundle.getBundle("registerview_params");
            if (this.currentParams != null) {
                setParams(this.currentParams, true);
            }
            String first = bundle.getString("registerview_first");
            if (first != null) {
                this.firstNameField.setText(first);
            }
            String last = bundle.getString("registerview_last");
            if (last != null) {
                this.lastNameField.setText(last);
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
        class C30642 implements Runnable {
            C30642() {
            }

            public void run() {
                if (LoginActivityResetWaitView.this.timeRunnable == this) {
                    LoginActivityResetWaitView.this.updateTimeText();
                    AndroidUtilities.runOnUIThread(LoginActivityResetWaitView.this.timeRunnable, 1000);
                }
            }
        }

        public LoginActivityResetWaitView(final LoginActivity this$0, Context context) {
            int i;
            int i2 = 5;
            this.this$0 = this$0;
            super(context);
            setOrientation(1);
            this.confirmTextView = new TextView(context);
            this.confirmTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.confirmTextView.setTextSize(1, 14.0f);
            this.confirmTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            this.confirmTextView.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            View view = this.confirmTextView;
            if (LocaleController.isRTL) {
                i = 5;
            } else {
                i = 3;
            }
            addView(view, LayoutHelper.createLinear(-2, -2, i));
            this.resetAccountText = new TextView(context);
            TextView textView = this.resetAccountText;
            if (LocaleController.isRTL) {
                i = 5;
            } else {
                i = 3;
            }
            textView.setGravity(i | 48);
            this.resetAccountText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.resetAccountText.setText(LocaleController.getString("ResetAccountStatus", R.string.ResetAccountStatus));
            this.resetAccountText.setTextSize(1, 14.0f);
            this.resetAccountText.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            View view2 = this.resetAccountText;
            if (LocaleController.isRTL) {
                i = 5;
            } else {
                i = 3;
            }
            addView(view2, LayoutHelper.createLinear(-2, -2, i | 48, 0, 24, 0, 0));
            this.resetAccountTime = new TextView(context);
            textView = this.resetAccountTime;
            if (LocaleController.isRTL) {
                i = 5;
            } else {
                i = 3;
            }
            textView.setGravity(i | 48);
            this.resetAccountTime.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.resetAccountTime.setTextSize(1, 14.0f);
            this.resetAccountTime.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            view2 = this.resetAccountTime;
            if (LocaleController.isRTL) {
                i = 5;
            } else {
                i = 3;
            }
            addView(view2, LayoutHelper.createLinear(-2, -2, i | 48, 0, 2, 0, 0));
            this.resetAccountButton = new TextView(context);
            textView = this.resetAccountButton;
            if (LocaleController.isRTL) {
                i = 5;
            } else {
                i = 3;
            }
            textView.setGravity(i | 48);
            this.resetAccountButton.setText(LocaleController.getString("ResetAccountButton", R.string.ResetAccountButton));
            this.resetAccountButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.resetAccountButton.setTextSize(1, 14.0f);
            this.resetAccountButton.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            this.resetAccountButton.setPadding(0, AndroidUtilities.dp(14.0f), 0, 0);
            view2 = this.resetAccountButton;
            if (!LocaleController.isRTL) {
                i2 = 3;
            }
            addView(view2, LayoutHelper.createLinear(-2, -2, i2 | 48, 0, 7, 0, 0));
            this.resetAccountButton.setOnClickListener(new OnClickListener() {

                /* renamed from: org.telegram.ui.LoginActivity$LoginActivityResetWaitView$1$1 */
                class C30621 implements DialogInterface.OnClickListener {

                    /* renamed from: org.telegram.ui.LoginActivity$LoginActivityResetWaitView$1$1$1 */
                    class C30611 implements RequestDelegate {
                        C30611() {
                        }

                        public void run(TLObject response, final TLRPC$TL_error error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    LoginActivityResetWaitView.this.this$0.needHideProgress();
                                    if (error == null) {
                                        Bundle params = new Bundle();
                                        params.putString("phoneFormated", LoginActivityResetWaitView.this.requestPhone);
                                        params.putString("phoneHash", LoginActivityResetWaitView.this.phoneHash);
                                        params.putString("code", LoginActivityResetWaitView.this.phoneCode);
                                        LoginActivityResetWaitView.this.this$0.setPage(5, true, params, false);
                                    } else if (error.text.equals("2FA_RECENT_CONFIRM")) {
                                        LoginActivityResetWaitView.this.this$0.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("ResetAccountCancelledAlert", R.string.ResetAccountCancelledAlert));
                                    } else {
                                        LoginActivityResetWaitView.this.this$0.needShowAlert(LocaleController.getString("AppName", R.string.AppName), error.text);
                                    }
                                }
                            });
                        }
                    }

                    C30621() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivityResetWaitView.this.this$0.needShowProgress(0);
                        TLRPC$TL_account_deleteAccount req = new TLRPC$TL_account_deleteAccount();
                        req.reason = "Forgot password";
                        ConnectionsManager.getInstance().sendRequest(req, new C30611(), 10);
                    }
                }

                public void onClick(View view) {
                    if (Math.abs(ConnectionsManager.getInstance().getCurrentTime() - LoginActivityResetWaitView.this.startTime) >= LoginActivityResetWaitView.this.waitTime) {
                        Builder builder = new Builder(LoginActivityResetWaitView.this.this$0.getParentActivity());
                        builder.setMessage(LocaleController.getString("ResetMyAccountWarningText", R.string.ResetMyAccountWarningText));
                        builder.setTitle(LocaleController.getString("ResetMyAccountWarning", R.string.ResetMyAccountWarning));
                        builder.setPositiveButton(LocaleController.getString("ResetMyAccountWarningReset", R.string.ResetMyAccountWarningReset), new C30621());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        LoginActivityResetWaitView.this.this$0.showDialog(builder.create());
                    }
                }
            });
        }

        public String getHeaderName() {
            return LocaleController.getString("ResetAccount", R.string.ResetAccount);
        }

        private void updateTimeText() {
            int timeLeft = Math.max(0, this.waitTime - (ConnectionsManager.getInstance().getCurrentTime() - this.startTime));
            int days = timeLeft / 86400;
            int hours = (timeLeft - (days * 86400)) / 3600;
            int minutes = ((timeLeft - (days * 86400)) - (hours * 3600)) / 60;
            int seconds = timeLeft % 60;
            if (days != 0) {
                this.resetAccountTime.setText(AndroidUtilities.replaceTags(LocaleController.formatPluralString("DaysBold", days) + " " + LocaleController.formatPluralString("HoursBold", hours) + " " + LocaleController.formatPluralString("MinutesBold", minutes)));
            } else {
                this.resetAccountTime.setText(AndroidUtilities.replaceTags(LocaleController.formatPluralString("HoursBold", hours) + " " + LocaleController.formatPluralString("MinutesBold", minutes) + " " + LocaleController.formatPluralString("SecondsBold", seconds)));
            }
            if (timeLeft > 0) {
                this.resetAccountButton.setTag(Theme.key_windowBackgroundWhiteGrayText6);
                this.resetAccountButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
                return;
            }
            this.resetAccountButton.setTag(Theme.key_windowBackgroundWhiteRedText6);
            this.resetAccountButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText6));
        }

        public void setParams(Bundle params, boolean restore) {
            if (params != null) {
                this.currentParams = params;
                this.requestPhone = params.getString("phoneFormated");
                this.phoneHash = params.getString("phoneHash");
                this.phoneCode = params.getString("code");
                this.startTime = params.getInt("startTime");
                this.waitTime = params.getInt("waitTime");
                this.confirmTextView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("ResetAccountInfo", R.string.ResetAccountInfo, new Object[]{PhoneFormat.getInstance().format("+" + this.requestPhone)})));
                updateTimeText();
                this.timeRunnable = new C30642();
                AndroidUtilities.runOnUIThread(this.timeRunnable, 1000);
            }
        }

        public boolean needBackButton() {
            return true;
        }

        public void onBackPressed() {
            AndroidUtilities.cancelRunOnUIThread(this.timeRunnable);
            this.timeRunnable = null;
            this.currentParams = null;
        }

        public void saveStateParams(Bundle bundle) {
            if (this.currentParams != null) {
                bundle.putBundle("resetview_params", this.currentParams);
            }
        }

        public void restoreStateParams(Bundle bundle) {
            this.currentParams = bundle.getBundle("resetview_params");
            if (this.currentParams != null) {
                setParams(this.currentParams, true);
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
        private String lastError = "";
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
        class C30736 extends TimerTask {

            /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$6$1 */
            class C30721 implements Runnable {
                C30721() {
                }

                public void run() {
                    if (LoginActivitySmsView.this.codeTime <= 1000) {
                        LoginActivitySmsView.this.problemText.setVisibility(0);
                        LoginActivitySmsView.this.destroyCodeTimer();
                    }
                }
            }

            C30736() {
            }

            public void run() {
                double currentTime = (double) System.currentTimeMillis();
                LoginActivitySmsView.this.codeTime = (int) (((double) LoginActivitySmsView.this.codeTime) - (currentTime - LoginActivitySmsView.this.lastCodeTime));
                LoginActivitySmsView.this.lastCodeTime = currentTime;
                AndroidUtilities.runOnUIThread(new C30721());
            }
        }

        /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$7 */
        class C30777 extends TimerTask {

            /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$7$1 */
            class C30761 implements Runnable {

                /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$7$1$1 */
                class C30751 implements RequestDelegate {
                    C30751() {
                    }

                    public void run(TLObject response, final TLRPC$TL_error error) {
                        if (error != null && error.text != null) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    LoginActivitySmsView.this.lastError = error.text;
                                }
                            });
                        }
                    }
                }

                C30761() {
                }

                public void run() {
                    if (LoginActivitySmsView.this.time >= 1000) {
                        int seconds = (LoginActivitySmsView.this.time / 1000) - (((LoginActivitySmsView.this.time / 1000) / 60) * 60);
                        if (LoginActivitySmsView.this.nextType == 4 || LoginActivitySmsView.this.nextType == 3) {
                            LoginActivitySmsView.this.timeText.setText(LocaleController.formatString("CallText", R.string.CallText, new Object[]{Integer.valueOf(minutes), Integer.valueOf(seconds)}));
                        } else if (LoginActivitySmsView.this.nextType == 2) {
                            LoginActivitySmsView.this.timeText.setText(LocaleController.formatString("SmsText", R.string.SmsText, new Object[]{Integer.valueOf(minutes), Integer.valueOf(seconds)}));
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
                            TLRPC$TL_auth_resendCode req = new TLRPC$TL_auth_resendCode();
                            req.phone_number = LoginActivitySmsView.this.requestPhone;
                            req.phone_code_hash = LoginActivitySmsView.this.phoneHash;
                            ConnectionsManager.getInstance().sendRequest(req, new C30751(), 10);
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

            C30777() {
            }

            public void run() {
                if (LoginActivitySmsView.this.timeTimer != null) {
                    double currentTime = (double) System.currentTimeMillis();
                    LoginActivitySmsView.this.time = (int) (((double) LoginActivitySmsView.this.time) - (currentTime - LoginActivitySmsView.this.lastCurrentTime));
                    LoginActivitySmsView.this.lastCurrentTime = currentTime;
                    AndroidUtilities.runOnUIThread(new C30761());
                }
            }
        }

        public LoginActivitySmsView(Context context, int type) {
            super(context);
            this.currentType = type;
            setOrientation(1);
            this.confirmTextView = new TextView(context);
            this.confirmTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.confirmTextView.setTextSize(1, 14.0f);
            this.confirmTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            this.confirmTextView.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            if (this.currentType == 3) {
                FrameLayout frameLayout = new FrameLayout(context);
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.phone_activate);
                if (LocaleController.isRTL) {
                    frameLayout.addView(imageView, LayoutHelper.createFrame(64, 76.0f, 19, 2.0f, 2.0f, 0.0f, 0.0f));
                    frameLayout.addView(this.confirmTextView, LayoutHelper.createFrame(-1, -2.0f, LocaleController.isRTL ? 5 : 3, 82.0f, 0.0f, 0.0f, 0.0f));
                } else {
                    frameLayout.addView(this.confirmTextView, LayoutHelper.createFrame(-1, -2.0f, LocaleController.isRTL ? 5 : 3, 0.0f, 0.0f, 82.0f, 0.0f));
                    frameLayout.addView(imageView, LayoutHelper.createFrame(64, 76.0f, 21, 0.0f, 2.0f, 0.0f, 2.0f));
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
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                public void afterTextChanged(Editable s) {
                    if (!LoginActivitySmsView.this.ignoreOnTextChange && LoginActivitySmsView.this.length != 0 && LoginActivitySmsView.this.codeField.length() == LoginActivitySmsView.this.length) {
                        LoginActivitySmsView.this.onNextPressed();
                    }
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
                addView(this.progressView, LayoutHelper.createLinear(-1, 3, 0.0f, 12.0f, 0.0f, 0.0f));
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
                public void onClick(View v) {
                    if (!LoginActivitySmsView.this.nextPressed) {
                        if (LoginActivitySmsView.this.nextType == 0 || LoginActivitySmsView.this.nextType == 4) {
                            try {
                                PackageInfo pInfo = ApplicationLoader.applicationContext.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
                                String version = String.format(Locale.US, "%s (%d)", new Object[]{pInfo.versionName, Integer.valueOf(pInfo.versionCode)});
                                Intent mailer = new Intent("android.intent.action.SEND");
                                mailer.setType("message/rfc822");
                                mailer.putExtra("android.intent.extra.EMAIL", new String[]{"sms@stel.com"});
                                mailer.putExtra("android.intent.extra.SUBJECT", "Android registration/login issue " + version + " " + LoginActivitySmsView.this.emailPhone);
                                mailer.putExtra("android.intent.extra.TEXT", "Phone: " + LoginActivitySmsView.this.requestPhone + "\nApp version: " + version + "\nOS version: SDK " + VERSION.SDK_INT + "\nDevice Name: " + Build.MANUFACTURER + Build.MODEL + "\nLocale: " + Locale.getDefault() + "\nError: " + LoginActivitySmsView.this.lastError);
                                LoginActivitySmsView.this.getContext().startActivity(Intent.createChooser(mailer, "Send email..."));
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
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
            addView(linearLayout, LayoutHelper.createLinear(-1, -1, LocaleController.isRTL ? 5 : 3));
            TextView wrongNumber = new TextView(context);
            wrongNumber.setGravity((LocaleController.isRTL ? 5 : 3) | 1);
            wrongNumber.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            wrongNumber.setTextSize(1, 14.0f);
            wrongNumber.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            wrongNumber.setPadding(0, AndroidUtilities.dp(24.0f), 0, 0);
            linearLayout.addView(wrongNumber, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 80, 0, 0, 0, 10));
            wrongNumber.setText(LocaleController.getString("WrongNumber", R.string.WrongNumber));
            wrongNumber.setOnClickListener(new OnClickListener(LoginActivity.this) {

                /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$4$1 */
                class C30681 implements RequestDelegate {
                    C30681() {
                    }

                    public void run(TLObject response, TLRPC$TL_error error) {
                    }
                }

                public void onClick(View view) {
                    TLRPC$TL_auth_cancelCode req = new TLRPC$TL_auth_cancelCode();
                    req.phone_number = LoginActivitySmsView.this.requestPhone;
                    req.phone_code_hash = LoginActivitySmsView.this.phoneHash;
                    ConnectionsManager.getInstance().sendRequest(req, new C30681(), 10);
                    LoginActivitySmsView.this.onBackPressed();
                    LoginActivity.this.setPage(0, true, null, true);
                }
            });
        }

        public void onCancelPressed() {
            this.nextPressed = false;
        }

        private void resendCode() {
            final Bundle params = new Bundle();
            params.putString("phone", this.phone);
            params.putString("ephone", this.emailPhone);
            params.putString("phoneFormated", this.requestPhone);
            this.nextPressed = true;
            TLRPC$TL_auth_resendCode req = new TLRPC$TL_auth_resendCode();
            req.phone_number = this.requestPhone;
            req.phone_code_hash = this.phoneHash;
            int reqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, final TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            LoginActivitySmsView.this.nextPressed = false;
                            if (error == null) {
                                LoginActivity.this.fillNextCodeParams(params, (TLRPC$TL_auth_sentCode) response);
                            } else if (error.text != null) {
                                if (error.text.contains("PHONE_NUMBER_INVALID")) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidPhoneNumber", R.string.InvalidPhoneNumber));
                                } else if (error.text.contains("PHONE_CODE_EMPTY") || error.text.contains("PHONE_CODE_INVALID")) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidCode", R.string.InvalidCode));
                                } else if (error.text.contains("PHONE_CODE_EXPIRED")) {
                                    LoginActivitySmsView.this.onBackPressed();
                                    LoginActivity.this.setPage(0, true, null, true);
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("CodeExpired", R.string.CodeExpired));
                                } else if (error.text.startsWith("FLOOD_WAIT")) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("FloodWait", R.string.FloodWait));
                                } else if (error.code != -1000) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + LogCollector.LINE_SEPARATOR + error.text);
                                }
                            }
                            LoginActivity.this.needHideProgress();
                        }
                    });
                }
            }, 10);
            LoginActivity.this.needShowProgress(0);
        }

        public String getHeaderName() {
            return LocaleController.getString("YourCode", R.string.YourCode);
        }

        public void setParams(Bundle params, boolean restore) {
            if (params != null) {
                this.isRestored = restore;
                this.codeField.setText("");
                this.waitingForEvent = true;
                if (this.currentType == 2) {
                    AndroidUtilities.setWaitingForSms(true);
                    NotificationCenter.getInstance().addObserver(this, NotificationCenter.didReceiveSmsCode);
                } else if (this.currentType == 3) {
                    AndroidUtilities.setWaitingForCall(true);
                    NotificationCenter.getInstance().addObserver(this, NotificationCenter.didReceiveCall);
                }
                this.currentParams = params;
                this.phone = params.getString("phone");
                this.emailPhone = params.getString("ephone");
                this.requestPhone = params.getString("phoneFormated");
                this.phoneHash = params.getString("phoneHash");
                int i = params.getInt("timeout");
                this.time = i;
                this.timeout = i;
                this.openTime = (int) (System.currentTimeMillis() / 1000);
                this.nextType = params.getInt("nextType");
                this.pattern = params.getString("pattern");
                this.length = params.getInt("length");
                if (this.length != 0) {
                    this.codeField.setFilters(new InputFilter[]{new LengthFilter(this.length)});
                } else {
                    this.codeField.setFilters(new InputFilter[0]);
                }
                if (this.progressView != null) {
                    this.progressView.setVisibility(this.nextType != 0 ? 0 : 8);
                }
                if (this.phone != null) {
                    String number = PhoneFormat.getInstance().format(this.phone);
                    CharSequence str = "";
                    if (this.currentType == 1) {
                        str = AndroidUtilities.replaceTags(LocaleController.getString("SentAppCode", R.string.SentAppCode));
                    } else if (this.currentType == 2) {
                        str = AndroidUtilities.replaceTags(LocaleController.formatString("SentSmsCode", R.string.SentSmsCode, new Object[]{number}));
                    } else if (this.currentType == 3) {
                        str = AndroidUtilities.replaceTags(LocaleController.formatString("SentCallCode", R.string.SentCallCode, new Object[]{number}));
                    } else if (this.currentType == 4) {
                        str = AndroidUtilities.replaceTags(LocaleController.formatString("SentCallOnly", R.string.SentCallOnly, new Object[]{number}));
                    }
                    this.confirmTextView.setText(str);
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
                        String callLogNumber = this.isRestored ? AndroidUtilities.obtainLoginPhoneCall(this.pattern) : null;
                        if (callLogNumber != null) {
                            this.ignoreOnTextChange = true;
                            this.codeField.setText(callLogNumber);
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
                        this.problemText.setVisibility(this.time < 1000 ? 0 : 8);
                        createTimer();
                    } else {
                        this.timeText.setVisibility(8);
                        this.problemText.setVisibility(8);
                        createCodeTimer();
                    }
                }
            }
        }

        private void createCodeTimer() {
            if (this.codeTimer == null) {
                this.codeTime = DefaultLoadControl.DEFAULT_MIN_BUFFER_MS;
                this.codeTimer = new Timer();
                this.lastCodeTime = (double) System.currentTimeMillis();
                this.codeTimer.schedule(new C30736(), 0, 1000);
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
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        private void createTimer() {
            if (this.timeTimer == null) {
                this.timeTimer = new Timer();
                this.timeTimer.schedule(new C30777(), 0, 1000);
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
            } catch (Exception e) {
                FileLog.e(e);
            }
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
                final String code = this.codeField.getText().toString();
                final TLRPC$TL_auth_signIn req = new TLRPC$TL_auth_signIn();
                req.phone_number = this.requestPhone;
                req.phone_code = code;
                req.phone_code_hash = this.phoneHash;
                destroyTimer();
                int reqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                    public void run(final TLObject response, final TLRPC$TL_error error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {

                            /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$8$1$1 */
                            class C30791 implements RequestDelegate {
                                C30791() {
                                }

                                public void run(final TLObject response, final TLRPC$TL_error error) {
                                    AndroidUtilities.runOnUIThread(new Runnable() {
                                        public void run() {
                                            LoginActivity.this.needHideProgress();
                                            if (error == null) {
                                                int i;
                                                TLRPC$TL_account_password password = response;
                                                Bundle bundle = new Bundle();
                                                bundle.putString("current_salt", Utilities.bytesToHex(password.current_salt));
                                                bundle.putString(TrackReferenceTypeBox.TYPE1, password.hint);
                                                bundle.putString("email_unconfirmed_pattern", password.email_unconfirmed_pattern);
                                                bundle.putString("phoneFormated", LoginActivitySmsView.this.requestPhone);
                                                bundle.putString("phoneHash", LoginActivitySmsView.this.phoneHash);
                                                bundle.putString("code", req.phone_code);
                                                String str = "has_recovery";
                                                if (password.has_recovery) {
                                                    i = 1;
                                                } else {
                                                    i = 0;
                                                }
                                                bundle.putInt(str, i);
                                                LoginActivity.this.setPage(6, true, bundle, false);
                                                return;
                                            }
                                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), error.text);
                                        }
                                    });
                                }
                            }

                            public void run() {
                                LoginActivitySmsView.this.nextPressed = false;
                                boolean ok = false;
                                if (error == null) {
                                    ok = true;
                                    LoginActivity.this.needHideProgress();
                                    TLRPC$TL_auth_authorization res = response;
                                    ConnectionsManager.getInstance().setUserId(res.user.id);
                                    LoginActivitySmsView.this.destroyTimer();
                                    LoginActivitySmsView.this.destroyCodeTimer();
                                    UserConfig.clearConfig();
                                    MessagesController.getInstance().cleanup();
                                    UserConfig.setCurrentUser(res.user);
                                    UserConfig.saveConfig(true);
                                    MessagesStorage.getInstance().cleanup(true);
                                    ArrayList<User> users = new ArrayList();
                                    users.add(res.user);
                                    MessagesStorage.getInstance().putUsersAndChats(users, null, true, true);
                                    MessagesController.getInstance().putUser(res.user, false);
                                    ContactsController.getInstance().checkAppAccount();
                                    MessagesController.getInstance().getBlockedUsers(true);
                                    ConnectionsManager.getInstance().updateDcSettings();
                                    LoginActivity.this.needFinishActivity();
                                } else {
                                    LoginActivitySmsView.this.lastError = error.text;
                                    if (error.text.contains("PHONE_NUMBER_UNOCCUPIED")) {
                                        ok = true;
                                        LoginActivity.this.needHideProgress();
                                        Bundle params = new Bundle();
                                        params.putString("phoneFormated", LoginActivitySmsView.this.requestPhone);
                                        params.putString("phoneHash", LoginActivitySmsView.this.phoneHash);
                                        params.putString("code", req.phone_code);
                                        LoginActivity.this.setPage(5, true, params, false);
                                        LoginActivitySmsView.this.destroyTimer();
                                        LoginActivitySmsView.this.destroyCodeTimer();
                                    } else if (error.text.contains("SESSION_PASSWORD_NEEDED")) {
                                        ok = true;
                                        ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_account_getPassword(), new C30791(), 10);
                                        LoginActivitySmsView.this.destroyTimer();
                                        LoginActivitySmsView.this.destroyCodeTimer();
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
                                            if (error.text.contains("PHONE_NUMBER_INVALID")) {
                                                LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidPhoneNumber", R.string.InvalidPhoneNumber));
                                            } else if (error.text.contains("PHONE_CODE_EMPTY") || error.text.contains("PHONE_CODE_INVALID")) {
                                                LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidCode", R.string.InvalidCode));
                                            } else if (error.text.contains("PHONE_CODE_EXPIRED")) {
                                                LoginActivitySmsView.this.onBackPressed();
                                                LoginActivity.this.setPage(0, true, null, true);
                                                LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("CodeExpired", R.string.CodeExpired));
                                            } else if (error.text.startsWith("FLOOD_WAIT")) {
                                                LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("FloodWait", R.string.FloodWait));
                                            } else {
                                                LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + LogCollector.LINE_SEPARATOR + error.text);
                                            }
                                        }
                                    }
                                }
                                if (ok && LoginActivitySmsView.this.currentType == 3) {
                                    AndroidUtilities.endIncomingCall();
                                    AndroidUtilities.removeLoginPhoneCall(code, true);
                                }
                            }
                        });
                    }
                }, 10);
                LoginActivity.this.needShowProgress(0);
            }
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

        public void onShow() {
            super.onShow();
            if (this.codeField != null && this.currentType != 3) {
                this.codeField.requestFocus();
                this.codeField.setSelection(this.codeField.length());
            }
        }

        public void didReceivedNotification(int id, Object... args) {
            if (this.waitingForEvent && this.codeField != null) {
                if (id == NotificationCenter.didReceiveSmsCode) {
                    this.ignoreOnTextChange = true;
                    this.codeField.setText("" + args[0]);
                    this.ignoreOnTextChange = false;
                    onNextPressed();
                } else if (id == NotificationCenter.didReceiveCall) {
                    String num = "" + args[0];
                    if (AndroidUtilities.checkPhonePattern(this.pattern, num)) {
                        if (!this.pattern.equals("*")) {
                            this.catchedPhone = num;
                            AndroidUtilities.endIncomingCall();
                            AndroidUtilities.removeLoginPhoneCall(num, true);
                        }
                        this.ignoreOnTextChange = true;
                        this.codeField.setText(num);
                        this.ignoreOnTextChange = false;
                        onNextPressed();
                    }
                }
            }
        }

        public void saveStateParams(Bundle bundle) {
            String code = this.codeField.getText().toString();
            if (code.length() != 0) {
                bundle.putString("smsview_code_" + this.currentType, code);
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

        public void restoreStateParams(Bundle bundle) {
            this.currentParams = bundle.getBundle("smsview_params_" + this.currentType);
            if (this.currentParams != null) {
                setParams(this.currentParams, true);
            }
            String catched = bundle.getString("catchedPhone");
            if (catched != null) {
                this.catchedPhone = catched;
            }
            String code = bundle.getString("smsview_code_" + this.currentType);
            if (code != null) {
                this.codeField.setText(code);
            }
            int t = bundle.getInt("time");
            if (t != 0) {
                this.time = t;
            }
            int t2 = bundle.getInt("open");
            if (t2 != 0) {
                this.openTime = t2;
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
            addView(this.countryButton, LayoutHelper.createLinear(-1, 36, 0.0f, 0.0f, 0.0f, 14.0f));
            final LoginActivity loginActivity = LoginActivity.this;
            this.countryButton.setOnClickListener(new OnClickListener() {

                /* renamed from: org.telegram.ui.LoginActivity$PhoneView$1$1 */
                class C30831 implements CountrySelectActivityDelegate {

                    /* renamed from: org.telegram.ui.LoginActivity$PhoneView$1$1$1 */
                    class C30821 implements Runnable {
                        C30821() {
                        }

                        public void run() {
                            AndroidUtilities.showKeyboard(PhoneView.this.phoneField);
                        }
                    }

                    C30831() {
                    }

                    public void didSelectCountry(String name, String shortName) {
                        PhoneView.this.selectCountry(name);
                        AndroidUtilities.runOnUIThread(new C30821(), 300);
                        PhoneView.this.phoneField.requestFocus();
                        PhoneView.this.phoneField.setSelection(PhoneView.this.phoneField.length());
                    }
                }

                public void onClick(View view) {
                    CountrySelectActivity fragment = new CountrySelectActivity(true);
                    fragment.setCountrySelectActivityDelegate(new C30831());
                    LoginActivity.this.presentFragment(fragment);
                }
            });
            this.view = new View(context);
            this.view.setPadding(AndroidUtilities.dp(12.0f), 0, AndroidUtilities.dp(12.0f), 0);
            this.view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayLine));
            addView(this.view, LayoutHelper.createLinear(-1, 1, 4.0f, -17.5f, 4.0f, 0.0f));
            View linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(0);
            addView(linearLayout, LayoutHelper.createLinear(-1, -2, 0.0f, 20.0f, 0.0f, 0.0f));
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
            linearLayout.addView(this.codeField, LayoutHelper.createLinear(55, 36, -9.0f, 0.0f, 16.0f, 0.0f));
            final LoginActivity loginActivity2 = LoginActivity.this;
            this.codeField.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void afterTextChanged(Editable editable) {
                    if (!PhoneView.this.ignoreOnTextChange) {
                        PhoneView.this.ignoreOnTextChange = true;
                        String text = PhoneFormat.stripExceptNumbers(PhoneView.this.codeField.getText().toString());
                        PhoneView.this.codeField.setText(text);
                        if (text.length() == 0) {
                            PhoneView.this.countryButton.setText(LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
                            PhoneView.this.phoneField.setHintText(null);
                            PhoneView.this.countryState = 1;
                        } else {
                            boolean ok = false;
                            String textToSet = null;
                            if (text.length() > 4) {
                                for (int a = 4; a >= 1; a--) {
                                    String sub = text.substring(0, a);
                                    if (((String) PhoneView.this.codesMap.get(sub)) != null) {
                                        ok = true;
                                        textToSet = text.substring(a, text.length()) + PhoneView.this.phoneField.getText().toString();
                                        text = sub;
                                        PhoneView.this.codeField.setText(sub);
                                        break;
                                    }
                                }
                                if (!ok) {
                                    textToSet = text.substring(1, text.length()) + PhoneView.this.phoneField.getText().toString();
                                    EditTextBoldCursor access$900 = PhoneView.this.codeField;
                                    text = text.substring(0, 1);
                                    access$900.setText(text);
                                }
                            }
                            String country = (String) PhoneView.this.codesMap.get(text);
                            if (country != null) {
                                int index = PhoneView.this.countriesArray.indexOf(country);
                                if (index != -1) {
                                    PhoneView.this.ignoreSelection = true;
                                    PhoneView.this.countryButton.setText((CharSequence) PhoneView.this.countriesArray.get(index));
                                    String hint = (String) PhoneView.this.phoneFormatMap.get(text);
                                    PhoneView.this.phoneField.setHintText(hint != null ? hint.replace('X', '–') : null);
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
                            if (!ok) {
                                PhoneView.this.codeField.setSelection(PhoneView.this.codeField.getText().length());
                            }
                            if (textToSet != null) {
                                PhoneView.this.phoneField.requestFocus();
                                PhoneView.this.phoneField.setText(textToSet);
                                PhoneView.this.phoneField.setSelection(PhoneView.this.phoneField.length());
                            }
                        }
                        PhoneView.this.ignoreOnTextChange = false;
                    }
                }
            });
            final LoginActivity loginActivity22 = LoginActivity.this;
            this.codeField.setOnEditorActionListener(new OnEditorActionListener() {
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
            final LoginActivity loginActivity222 = LoginActivity.this;
            this.phoneField.addTextChangedListener(new TextWatcher() {
                private int actionPosition;
                private int characterAction = -1;

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (count == 0 && after == 1) {
                        this.characterAction = 1;
                    } else if (count != 1 || after != 0) {
                        this.characterAction = -1;
                    } else if (s.charAt(start) != ' ' || start <= 0) {
                        this.characterAction = 2;
                    } else {
                        this.characterAction = 3;
                        this.actionPosition = start - 1;
                    }
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                public void afterTextChanged(Editable s) {
                    if (!PhoneView.this.ignoreOnPhoneChange) {
                        int a;
                        int start = PhoneView.this.phoneField.getSelectionStart();
                        String phoneChars = "0123456789";
                        String str = PhoneView.this.phoneField.getText().toString();
                        if (this.characterAction == 3) {
                            str = str.substring(0, this.actionPosition) + str.substring(this.actionPosition + 1, str.length());
                            start--;
                        }
                        StringBuilder builder = new StringBuilder(str.length());
                        for (a = 0; a < str.length(); a++) {
                            String ch = str.substring(a, a + 1);
                            if (phoneChars.contains(ch)) {
                                builder.append(ch);
                            }
                        }
                        PhoneView.this.ignoreOnPhoneChange = true;
                        String hint = PhoneView.this.phoneField.getHintText();
                        if (hint != null) {
                            a = 0;
                            while (a < builder.length()) {
                                if (a < hint.length()) {
                                    if (hint.charAt(a) == ' ') {
                                        builder.insert(a, ' ');
                                        a++;
                                        if (!(start != a || this.characterAction == 2 || this.characterAction == 3)) {
                                            start++;
                                        }
                                    }
                                    a++;
                                } else {
                                    builder.insert(a, ' ');
                                    if (!(start != a + 1 || this.characterAction == 2 || this.characterAction == 3)) {
                                        start++;
                                    }
                                }
                            }
                        }
                        PhoneView.this.phoneField.setText(builder);
                        if (start >= 0) {
                            HintEditText access$700 = PhoneView.this.phoneField;
                            if (start > PhoneView.this.phoneField.length()) {
                                start = PhoneView.this.phoneField.length();
                            }
                            access$700.setSelection(start);
                        }
                        PhoneView.this.phoneField.onTextChange();
                        PhoneView.this.ignoreOnPhoneChange = false;
                    }
                }
            });
            final LoginActivity loginActivity2222 = LoginActivity.this;
            this.phoneField.setOnEditorActionListener(new OnEditorActionListener() {
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
            if (AppPreferences.getProxyEnable(ApplicationLoader.applicationContext) > 0) {
                this.textView3Bold = new TextView(context);
                this.textView3Bold.setText(LocaleController.getString("RetryProxyHint", R.string.RetryProxyHint));
                this.textView3Bold.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
                this.textView3Bold.setTextSize(1, 16.0f);
                this.textView3Bold.setTypeface(FarsiTextView.getTypeface(ApplicationLoader.applicationContext), 1);
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
                final LoginActivity loginActivity22222 = LoginActivity.this;
                this.btnRetry.setOnClickListener(new OnClickListener() {

                    /* renamed from: org.telegram.ui.LoginActivity$PhoneView$6$1 */
                    class C30891 implements Runnable {
                        C30891() {
                        }

                        public void run() {
                            if (LoginActivity.this.getParentActivity() != null) {
                                Toast.makeText(LoginActivity.this.getParentActivity(), LocaleController.getString("AppWillRestart", R.string.AppWillRestart), 0).show();
                            }
                        }
                    }

                    /* renamed from: org.telegram.ui.LoginActivity$PhoneView$6$2 */
                    class C30902 implements Runnable {
                        C30902() {
                        }

                        public void run() {
                            AppUtilities.restartApp();
                        }
                    }

                    public void onClick(View view) {
                        PhoneView.this.btnRetry.setClickable(false);
                        LoginActivity.this.showLoadingDialog(PhoneView.this.getContext(), LocaleController.getString("MyAppName", R.string.MyAppName), LocaleController.getString("wait", R.string.wait));
                        SLSProxyHelper.getProxyServer(ApplicationLoader.applicationContext, true);
                        PhoneView.this.btnRetry.setClickable(true);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.proxySettingsChanged, new Object[0]);
                        LoginActivity.this.dialogLoading.dismiss();
                        AppUtilities.needRestart = true;
                        AndroidUtilities.runOnUIThread(new C30891());
                        if (AppUtilities.needRestart) {
                            new Handler().postDelayed(new C30902(), FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
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
            HashMap<String, String> languageMap = new HashMap();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResources().getAssets().open("countries.txt")));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        String[] args = line.split(";");
                        this.countriesArray.add(0, args[2]);
                        this.countriesMap.put(args[2], args[0]);
                        this.codesMap.put(args[0], args[2]);
                        if (args.length > 3) {
                            this.phoneFormatMap.put(args[0], args[3]);
                        }
                        languageMap.put(args[1], args[2]);
                    } else {
                        bufferedReader.close();
                        final LoginActivity loginActivity222222 = LoginActivity.this;
                        Collections.sort(this.countriesArray, new Comparator<String>() {
                            public int compare(String lhs, String rhs) {
                                return lhs.compareTo(rhs);
                            }
                        });
                        String country = null;
                        try {
                            TelephonyManager telephonyManager = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
                            if (telephonyManager != null) {
                                country = telephonyManager.getSimCountryIso().toUpperCase();
                            }
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                        if (country != null) {
                            String countryName = (String) languageMap.get(country);
                            if (!(countryName == null || this.countriesArray.indexOf(countryName) == -1)) {
                                this.codeField.setText((CharSequence) this.countriesMap.get(countryName));
                                this.countryState = 0;
                            }
                        }
                        if (this.codeField.length() == 0) {
                            this.countryButton.setText(LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
                            this.phoneField.setHintText(null);
                            this.countryState = 1;
                        }
                        if (this.codeField.length() != 0) {
                            this.phoneField.requestFocus();
                            this.phoneField.setSelection(this.phoneField.length());
                            return;
                        }
                        this.codeField.requestFocus();
                        return;
                    }
                }
            } catch (Exception e2) {
                FileLog.e(e2);
            }
        }

        public void selectCountry(String name) {
            if (this.countriesArray.indexOf(name) != -1) {
                this.ignoreOnTextChange = true;
                String code = (String) this.countriesMap.get(name);
                this.codeField.setText(code);
                this.countryButton.setText(name);
                String hint = (String) this.phoneFormatMap.get(code);
                this.phoneField.setHintText(hint != null ? hint.replace('X', '–') : null);
                this.countryState = 0;
                this.ignoreOnTextChange = false;
            }
        }

        public void onCancelPressed() {
            this.nextPressed = false;
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (this.ignoreSelection) {
                this.ignoreSelection = false;
                return;
            }
            this.ignoreOnTextChange = true;
            this.codeField.setText((CharSequence) this.countriesMap.get((String) this.countriesArray.get(i)));
            this.ignoreOnTextChange = false;
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }

        public void onNextPressed() {
            if (LoginActivity.this.getParentActivity() != null && !this.nextPressed) {
                TelephonyManager tm = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
                boolean simcardAvailable = (tm.getSimState() == 1 || tm.getPhoneType() == 0) ? false : true;
                boolean allowCall = true;
                if (VERSION.SDK_INT >= 23 && simcardAvailable) {
                    allowCall = LoginActivity.this.getParentActivity().checkSelfPermission("android.permission.READ_PHONE_STATE") == 0;
                    boolean allowSms = LoginActivity.this.getParentActivity().checkSelfPermission("android.permission.RECEIVE_SMS") == 0;
                    boolean allowCancelCall = LoginActivity.this.getParentActivity().checkSelfPermission("android.permission.CALL_PHONE") == 0;
                    if (LoginActivity.this.checkPermissions) {
                        LoginActivity.this.permissionsItems.clear();
                        if (!allowCall) {
                            LoginActivity.this.permissionsItems.add("android.permission.READ_PHONE_STATE");
                        }
                        if (!allowSms) {
                            LoginActivity.this.permissionsItems.add("android.permission.RECEIVE_SMS");
                            if (VERSION.SDK_INT >= 23) {
                                LoginActivity.this.permissionsItems.add("android.permission.READ_SMS");
                            }
                        }
                        if (!allowCancelCall) {
                            LoginActivity.this.permissionsItems.add("android.permission.CALL_PHONE");
                            LoginActivity.this.permissionsItems.add("android.permission.WRITE_CALL_LOG");
                            LoginActivity.this.permissionsItems.add("android.permission.READ_CALL_LOG");
                        }
                        boolean ok = true;
                        if (!LoginActivity.this.permissionsItems.isEmpty()) {
                            SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                            if (!allowCancelCall && allowCall) {
                                LoginActivity.this.getParentActivity().requestPermissions((String[]) LoginActivity.this.permissionsItems.toArray(new String[LoginActivity.this.permissionsItems.size()]), 6);
                            } else if (preferences.getBoolean("firstlogin", true) || LoginActivity.this.getParentActivity().shouldShowRequestPermissionRationale("android.permission.READ_PHONE_STATE") || LoginActivity.this.getParentActivity().shouldShowRequestPermissionRationale("android.permission.RECEIVE_SMS")) {
                                preferences.edit().putBoolean("firstlogin", false).commit();
                                Builder builder = new Builder(LoginActivity.this.getParentActivity());
                                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                if (LoginActivity.this.permissionsItems.size() >= 2) {
                                    builder.setMessage(LocaleController.getString("AllowReadCallAndSms", R.string.AllowReadCallAndSms));
                                } else if (allowSms) {
                                    builder.setMessage(LocaleController.getString("AllowReadCall", R.string.AllowReadCall));
                                } else {
                                    builder.setMessage(LocaleController.getString("AllowReadSms", R.string.AllowReadSms));
                                }
                                LoginActivity.this.permissionsDialog = LoginActivity.this.showDialog(builder.create());
                            } else {
                                try {
                                    LoginActivity.this.getParentActivity().requestPermissions((String[]) LoginActivity.this.permissionsItems.toArray(new String[LoginActivity.this.permissionsItems.size()]), 6);
                                } catch (Exception e) {
                                    ok = false;
                                }
                            }
                            if (ok) {
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
                    final TLRPC$TL_auth_sendCode req = new TLRPC$TL_auth_sendCode();
                    String phone = PhoneFormat.stripExceptNumbers("" + this.codeField.getText() + this.phoneField.getText());
                    req.api_hash = AppPreferences.getTelegramHashId(ApplicationLoader.applicationContext);
                    req.api_id = AppPreferences.getTelegramAppId(ApplicationLoader.applicationContext);
                    req.phone_number = phone;
                    boolean z = simcardAvailable && allowCall;
                    req.allow_flashcall = z;
                    if (req.allow_flashcall) {
                        try {
                            String number = tm.getLine1Number();
                            if (TextUtils.isEmpty(number)) {
                                req.current_number = false;
                            } else {
                                z = phone.contains(number) || number.contains(phone);
                                req.current_number = z;
                                if (!req.current_number) {
                                    req.allow_flashcall = false;
                                }
                            }
                        } catch (Exception e2) {
                            req.allow_flashcall = false;
                            FileLog.e(e2);
                        }
                    }
                    final Bundle params = new Bundle();
                    params.putString("phone", "+" + this.codeField.getText() + this.phoneField.getText());
                    try {
                        params.putString("ephone", "+" + PhoneFormat.stripExceptNumbers(this.codeField.getText().toString()) + " " + PhoneFormat.stripExceptNumbers(this.phoneField.getText().toString()));
                    } catch (Exception e22) {
                        FileLog.e(e22);
                        params.putString("ephone", "+" + phone);
                    }
                    params.putString("phoneFormated", phone);
                    this.nextPressed = true;
                    LoginActivity.this.needShowProgress(ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                        public void run(final TLObject response, final TLRPC$TL_error error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    PhoneView.this.nextPressed = false;
                                    if (error == null) {
                                        LoginActivity.this.fillNextCodeParams(params, (TLRPC$TL_auth_sentCode) response);
                                    } else if (error.text != null) {
                                        if (error.text.contains("PHONE_NUMBER_INVALID")) {
                                            LoginActivity.this.needShowInvalidAlert(req.phone_number, false);
                                        } else if (error.text.contains("PHONE_NUMBER_FLOOD")) {
                                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("PhoneNumberFlood", R.string.PhoneNumberFlood));
                                        } else if (error.text.contains("PHONE_NUMBER_BANNED")) {
                                            LoginActivity.this.needShowInvalidAlert(req.phone_number, true);
                                        } else if (error.text.contains("PHONE_CODE_EMPTY") || error.text.contains("PHONE_CODE_INVALID")) {
                                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("InvalidCode", R.string.InvalidCode));
                                        } else if (error.text.contains("PHONE_CODE_EXPIRED")) {
                                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("CodeExpired", R.string.CodeExpired));
                                        } else if (error.text.startsWith("FLOOD_WAIT")) {
                                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("FloodWait", R.string.FloodWait));
                                        } else if (error.code != -1000) {
                                            LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), error.text);
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

        public void fillNumber() {
            try {
                TelephonyManager tm = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
                if (tm.getSimState() != 1 && tm.getPhoneType() != 0) {
                    boolean allowCall = true;
                    boolean allowSms = true;
                    if (VERSION.SDK_INT >= 23) {
                        allowCall = LoginActivity.this.getParentActivity().checkSelfPermission("android.permission.READ_PHONE_STATE") == 0;
                        allowSms = LoginActivity.this.getParentActivity().checkSelfPermission("android.permission.RECEIVE_SMS") == 0;
                        if (!(!LoginActivity.this.checkShowPermissions || allowCall || allowSms)) {
                            LoginActivity.this.permissionsShowItems.clear();
                            if (!allowCall) {
                                LoginActivity.this.permissionsShowItems.add("android.permission.READ_PHONE_STATE");
                            }
                            if (!allowSms) {
                                LoginActivity.this.permissionsShowItems.add("android.permission.RECEIVE_SMS");
                                if (VERSION.SDK_INT >= 23) {
                                    LoginActivity.this.permissionsShowItems.add("android.permission.READ_SMS");
                                }
                            }
                            if (!LoginActivity.this.permissionsShowItems.isEmpty()) {
                                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                                if (preferences.getBoolean("firstloginshow", true) || LoginActivity.this.getParentActivity().shouldShowRequestPermissionRationale("android.permission.READ_PHONE_STATE") || LoginActivity.this.getParentActivity().shouldShowRequestPermissionRationale("android.permission.RECEIVE_SMS")) {
                                    preferences.edit().putBoolean("firstloginshow", false).commit();
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
                    if (allowCall || allowSms) {
                        String number = PhoneFormat.stripExceptNumbers(tm.getLine1Number());
                        String textToSet = null;
                        boolean ok = false;
                        if (!TextUtils.isEmpty(number)) {
                            if (number.length() > 4) {
                                for (int a = 4; a >= 1; a--) {
                                    String sub = number.substring(0, a);
                                    if (((String) this.codesMap.get(sub)) != null) {
                                        ok = true;
                                        textToSet = number.substring(a, number.length());
                                        this.codeField.setText(sub);
                                        break;
                                    }
                                }
                                if (!ok) {
                                    textToSet = number.substring(1, number.length());
                                    this.codeField.setText(number.substring(0, 1));
                                }
                            }
                            if (textToSet != null) {
                                this.phoneField.requestFocus();
                                this.phoneField.setText(textToSet);
                                this.phoneField.setSelection(this.phoneField.length());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
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

        public String getHeaderName() {
            return LocaleController.getString("YourPhone", R.string.YourPhone);
        }

        public void saveStateParams(Bundle bundle) {
            String code = this.codeField.getText().toString();
            if (code.length() != 0) {
                bundle.putString("phoneview_code", code);
            }
            String phone = this.phoneField.getText().toString();
            if (phone.length() != 0) {
                bundle.putString("phoneview_phone", phone);
            }
        }

        public void restoreStateParams(Bundle bundle) {
            String code = bundle.getString("phoneview_code");
            if (code != null) {
                this.codeField.setText(code);
            }
            String phone = bundle.getString("phoneview_phone");
            if (phone != null) {
                this.phoneField.setText(phone);
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

        public void setProgress(float value) {
            this.progress = value;
            invalidate();
        }

        protected void onDraw(Canvas canvas) {
            int start = (int) (((float) getMeasuredWidth()) * this.progress);
            canvas.drawRect(0.0f, 0.0f, (float) start, (float) getMeasuredHeight(), this.paint2);
            canvas.drawRect((float) start, 0.0f, (float) getMeasuredWidth(), (float) getMeasuredHeight(), this.paint);
        }
    }

    public void didReceivedNotification(int id, Object... args) {
        try {
            if (id == NotificationCenter.didUpdatedConnectionState) {
                int state = ConnectionsManager.getInstance().getConnectionState();
                View retryBtn = ((PhoneView) this.views[0]).btnRetry;
                View retryTxt = ((PhoneView) this.views[0]).textView3;
                View retryTxtBold = ((PhoneView) this.views[0]).textView3Bold;
                switch (state) {
                    case 3:
                    case 5:
                        retryBtn.setVisibility(8);
                        retryTxt.setVisibility(8);
                        retryTxtBold.setVisibility(8);
                        this.canContinueInNumberInsertion = true;
                        return;
                    default:
                        retryBtn.setVisibility(0);
                        retryTxt.setVisibility(0);
                        retryTxtBold.setVisibility(0);
                        this.canContinueInNumberInsertion = false;
                        return;
                }
            }
        } catch (Exception e) {
        }
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        for (int a = 0; a < this.views.length; a++) {
            if (this.views[a] != null) {
                this.views[a].onDestroyActivity();
            }
        }
        if (this.progressDialog != null) {
            try {
                this.progressDialog.dismiss();
            } catch (Exception e) {
                FileLog.e(e);
            }
            this.progressDialog = null;
        }
    }

    public View createView(Context context) {
        this.actionBar.setTitle(LocaleController.getString("AppName", R.string.AppName));
        this.actionBar.setActionBarMenuOnItemClick(new C30321());
        ActionBarMenu menu = this.actionBar.createMenu();
        this.actionBar.setAllowOverlayTitle(true);
        this.doneButton = menu.addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.fragmentView = new ScrollView(context);
        ScrollView scrollView = this.fragmentView;
        scrollView.setFillViewport(true);
        FrameLayout frameLayout = new FrameLayout(context);
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
        int a = 0;
        while (a < this.views.length) {
            this.views[a].setVisibility(a == 0 ? 0 : 8);
            frameLayout.addView(this.views[a], LayoutHelper.createFrame(-1, a == 0 ? -2.0f : -1.0f, 51, AndroidUtilities.isTablet() ? 26.0f : 18.0f, 30.0f, AndroidUtilities.isTablet() ? 26.0f : 18.0f, 0.0f));
            a++;
        }
        Bundle savedInstanceState = loadCurrentState();
        if (savedInstanceState != null) {
            this.currentViewNum = savedInstanceState.getInt("currentViewNum", 0);
            if (this.currentViewNum >= 1 && this.currentViewNum <= 4) {
                int time = savedInstanceState.getInt("open");
                if (time != 0 && Math.abs((System.currentTimeMillis() / 1000) - ((long) time)) >= 86400) {
                    this.currentViewNum = 0;
                    savedInstanceState = null;
                    clearCurrentState();
                }
            }
        }
        this.actionBar.setTitle(this.views[this.currentViewNum].getHeaderName());
        a = 0;
        while (a < this.views.length) {
            if (savedInstanceState != null) {
                if (a < 1 || a > 4) {
                    this.views[a].restoreStateParams(savedInstanceState);
                } else if (a == this.currentViewNum) {
                    this.views[a].restoreStateParams(savedInstanceState);
                }
            }
            if (this.currentViewNum == a) {
                this.actionBar.setBackButtonImage(this.views[a].needBackButton() ? R.drawable.ic_ab_back : 0);
                this.views[a].setVisibility(0);
                this.views[a].onShow();
                if (a == 3 || a == 8) {
                    this.doneButton.setVisibility(8);
                }
            } else {
                this.views[a].setVisibility(8);
            }
            a++;
        }
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didUpdatedConnectionState);
        HandleRequest.getNew(getParentActivity(), new C30332()).getSelfUpdateAndSetting();
        return this.fragmentView;
    }

    public void onPause() {
        super.onPause();
        AndroidUtilities.removeAdjustResize(getParentActivity(), this.classGuid);
    }

    public void onResume() {
        super.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
        try {
            if (this.currentViewNum >= 1 && this.currentViewNum <= 4 && (this.views[this.currentViewNum] instanceof LoginActivitySmsView)) {
                int time = ((LoginActivitySmsView) this.views[this.currentViewNum]).openTime;
                if (time != 0 && Math.abs((System.currentTimeMillis() / 1000) - ((long) time)) >= 86400) {
                    this.views[this.currentViewNum].onBackPressed();
                    setPage(0, false, null, true);
                }
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public void onRequestPermissionsResultFragment(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 6) {
            this.checkPermissions = false;
            if (this.currentViewNum == 0) {
                this.views[this.currentViewNum].onNextPressed();
            }
        } else if (requestCode == 7) {
            this.checkShowPermissions = false;
            if (this.currentViewNum == 0) {
                ((PhoneView) this.views[this.currentViewNum]).fillNumber();
            }
        }
    }

    private Bundle loadCurrentState() {
        try {
            Bundle bundle = new Bundle();
            for (Entry<String, ?> entry : ApplicationLoader.applicationContext.getSharedPreferences("logininfo2", 0).getAll().entrySet()) {
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                String[] args = key.split("_\\|_");
                if (args.length == 1) {
                    if (value instanceof String) {
                        bundle.putString(key, (String) value);
                    } else if (value instanceof Integer) {
                        bundle.putInt(key, ((Integer) value).intValue());
                    }
                } else if (args.length == 2) {
                    Bundle inner = bundle.getBundle(args[0]);
                    if (inner == null) {
                        inner = new Bundle();
                        bundle.putBundle(args[0], inner);
                    }
                    if (value instanceof String) {
                        inner.putString(args[1], (String) value);
                    } else if (value instanceof Integer) {
                        inner.putInt(args[1], ((Integer) value).intValue());
                    }
                }
            }
            return bundle;
        } catch (Exception e) {
            FileLog.e(e);
            return null;
        }
    }

    private void clearCurrentState() {
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("logininfo2", 0).edit();
        editor.clear();
        editor.commit();
    }

    private void putBundleToEditor(Bundle bundle, Editor editor, String prefix) {
        for (String key : bundle.keySet()) {
            Object obj = bundle.get(key);
            if (obj instanceof String) {
                if (prefix != null) {
                    editor.putString(prefix + "_|_" + key, (String) obj);
                } else {
                    editor.putString(key, (String) obj);
                }
            } else if (obj instanceof Integer) {
                if (prefix != null) {
                    editor.putInt(prefix + "_|_" + key, ((Integer) obj).intValue());
                } else {
                    editor.putInt(key, ((Integer) obj).intValue());
                }
            } else if (obj instanceof Bundle) {
                putBundleToEditor((Bundle) obj, editor, key);
            }
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

    public boolean onBackPressed() {
        if (this.currentViewNum == 0) {
            for (int a = 0; a < this.views.length; a++) {
                if (this.views[a] != null) {
                    this.views[a].onDestroyActivity();
                }
            }
            clearCurrentState();
            return true;
        }
        if (this.currentViewNum == 6) {
            this.views[this.currentViewNum].onBackPressed();
            setPage(0, true, null, true);
        } else if (this.currentViewNum == 7 || this.currentViewNum == 8) {
            this.views[this.currentViewNum].onBackPressed();
            setPage(6, true, null, true);
        }
        return false;
    }

    private void needShowAlert(String title, String text) {
        if (text != null && getParentActivity() != null) {
            Builder builder = new Builder(getParentActivity());
            builder.setTitle(title);
            builder.setMessage(text);
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            showDialog(builder.create());
        }
    }

    private void needShowInvalidAlert(final String phoneNumber, final boolean banned) {
        if (getParentActivity() != null) {
            Builder builder = new Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            if (banned) {
                builder.setMessage(LocaleController.getString("BannedPhoneNumber", R.string.BannedPhoneNumber));
            } else {
                builder.setMessage(LocaleController.getString("InvalidPhoneNumber", R.string.InvalidPhoneNumber));
            }
            builder.setNeutralButton(LocaleController.getString("BotHelp", R.string.BotHelp), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        PackageInfo pInfo = ApplicationLoader.applicationContext.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
                        String version = String.format(Locale.US, "%s (%d)", new Object[]{pInfo.versionName, Integer.valueOf(pInfo.versionCode)});
                        Intent mailer = new Intent("android.intent.action.SEND");
                        mailer.setType("message/rfc822");
                        mailer.putExtra("android.intent.extra.EMAIL", new String[]{"login@stel.com"});
                        if (banned) {
                            mailer.putExtra("android.intent.extra.SUBJECT", "Banned phone number: " + phoneNumber);
                            mailer.putExtra("android.intent.extra.TEXT", "I'm trying to use my mobile phone number: " + phoneNumber + "\nBut Telegram says it's banned. Please help.\n\nApp version: " + version + "\nOS version: SDK " + VERSION.SDK_INT + "\nDevice Name: " + Build.MANUFACTURER + Build.MODEL + "\nLocale: " + Locale.getDefault());
                        } else {
                            mailer.putExtra("android.intent.extra.SUBJECT", "Invalid phone number: " + phoneNumber);
                            mailer.putExtra("android.intent.extra.TEXT", "I'm trying to use my mobile phone number: " + phoneNumber + "\nBut Telegram says it's invalid. Please help.\n\nApp version: " + version + "\nOS version: SDK " + VERSION.SDK_INT + "\nDevice Name: " + Build.MANUFACTURER + Build.MODEL + "\nLocale: " + Locale.getDefault());
                        }
                        LoginActivity.this.getParentActivity().startActivity(Intent.createChooser(mailer, "Send email..."));
                    } catch (Exception e) {
                        LoginActivity.this.needShowAlert(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("NoMailInstalled", R.string.NoMailInstalled));
                    }
                }
            });
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            showDialog(builder.create());
        }
    }

    private void needShowProgress(final int reqiestId) {
        if (getParentActivity() != null && !getParentActivity().isFinishing() && this.progressDialog == null) {
            Builder builder = new Builder(getParentActivity(), 1);
            builder.setMessage(LocaleController.getString("Loading", R.string.Loading));
            if (reqiestId != 0) {
                builder.setPositiveButton(LocaleController.getString("Cancel", R.string.Cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LoginActivity.this.views[LoginActivity.this.currentViewNum].onCancelPressed();
                        ConnectionsManager.getInstance().cancelRequest(reqiestId, true);
                        LoginActivity.this.progressDialog = null;
                    }
                });
            }
            this.progressDialog = builder.show();
            this.progressDialog.setCanceledOnTouchOutside(false);
            this.progressDialog.setCancelable(false);
        }
    }

    public void needHideProgress() {
        if (this.progressDialog != null) {
            try {
                this.progressDialog.dismiss();
            } catch (Exception e) {
                FileLog.e(e);
            }
            this.progressDialog = null;
        }
    }

    public void setPage(int page, boolean animated, Bundle params, boolean back) {
        int i = R.drawable.ic_ab_back;
        if (page == 3 || page == 8) {
            this.doneButton.setVisibility(8);
        } else {
            if (page == 0) {
                this.checkPermissions = true;
                this.checkShowPermissions = true;
            }
            this.doneButton.setVisibility(0);
        }
        if (animated) {
            float f;
            final SlideView outView = this.views[this.currentViewNum];
            final SlideView newView = this.views[page];
            this.currentViewNum = page;
            ActionBar actionBar = this.actionBar;
            if (!newView.needBackButton()) {
                i = 0;
            }
            actionBar.setBackButtonImage(i);
            newView.setParams(params, false);
            this.actionBar.setTitle(newView.getHeaderName());
            newView.onShow();
            if (back) {
                f = (float) (-AndroidUtilities.displaySize.x);
            } else {
                f = (float) AndroidUtilities.displaySize.x;
            }
            newView.setX(f);
            ViewPropertyAnimator duration = outView.animate().setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                }

                @SuppressLint({"NewApi"})
                public void onAnimationEnd(Animator animator) {
                    outView.setVisibility(8);
                    outView.setX(0.0f);
                }

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }
            }).setDuration(300);
            if (back) {
                f = (float) AndroidUtilities.displaySize.x;
            } else {
                f = (float) (-AndroidUtilities.displaySize.x);
            }
            duration.translationX(f).start();
            newView.animate().setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                    newView.setVisibility(0);
                }

                public void onAnimationEnd(Animator animator) {
                }

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }
            }).setDuration(300).translationX(0.0f).start();
            return;
        }
        actionBar = this.actionBar;
        if (!this.views[page].needBackButton()) {
            i = 0;
        }
        actionBar.setBackButtonImage(i);
        this.views[this.currentViewNum].setVisibility(8);
        this.currentViewNum = page;
        this.views[page].setParams(params, false);
        this.views[page].setVisibility(0);
        this.actionBar.setTitle(this.views[page].getHeaderName());
        this.views[page].onShow();
    }

    public void saveSelfArgs(Bundle outState) {
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("currentViewNum", this.currentViewNum);
            for (int a = 0; a <= this.currentViewNum; a++) {
                SlideView v = this.views[a];
                if (v != null) {
                    v.saveStateParams(bundle);
                }
            }
            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("logininfo2", 0).edit();
            editor.clear();
            putBundleToEditor(bundle, editor, null);
            editor.commit();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    private void needFinishActivity() {
        clearCurrentState();
        presentFragment(new DialogsActivity(null), true);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
    }

    private void fillNextCodeParams(Bundle params, TLRPC$TL_auth_sentCode res) {
        params.putString("phoneHash", res.phone_code_hash);
        if (res.next_type instanceof TLRPC$TL_auth_codeTypeCall) {
            params.putInt("nextType", 4);
        } else if (res.next_type instanceof TLRPC$TL_auth_codeTypeFlashCall) {
            params.putInt("nextType", 3);
        } else if (res.next_type instanceof TLRPC$TL_auth_codeTypeSms) {
            params.putInt("nextType", 2);
        }
        if (res.type instanceof TLRPC$TL_auth_sentCodeTypeApp) {
            params.putInt(Param.TYPE, 1);
            params.putInt("length", res.type.length);
            setPage(1, true, params, false);
            return;
        }
        if (res.timeout == 0) {
            res.timeout = 60;
        }
        params.putInt("timeout", res.timeout * 1000);
        if (res.type instanceof TLRPC$TL_auth_sentCodeTypeCall) {
            params.putInt(Param.TYPE, 4);
            params.putInt("length", res.type.length);
            setPage(4, true, params, false);
        } else if (res.type instanceof TLRPC$TL_auth_sentCodeTypeFlashCall) {
            params.putInt(Param.TYPE, 3);
            params.putString("pattern", res.type.pattern);
            setPage(3, true, params, false);
        } else if (res.type instanceof TLRPC$TL_auth_sentCodeTypeSms) {
            params.putInt(Param.TYPE, 2);
            params.putInt("length", res.type.length);
            setPage(2, true, params, false);
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        PhoneView phoneView = this.views[0];
        LoginActivitySmsView smsView1 = this.views[1];
        LoginActivitySmsView smsView2 = this.views[2];
        LoginActivitySmsView smsView3 = this.views[3];
        LoginActivitySmsView smsView4 = this.views[4];
        LoginActivityRegisterView registerView = this.views[5];
        LoginActivityPasswordView passwordView = this.views[6];
        LoginActivityRecoverView recoverView = this.views[7];
        LoginActivityResetWaitView waitView = this.views[8];
        r19 = new ThemeDescription[86];
        r19[54] = new ThemeDescription(smsView1.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        r19[55] = new ThemeDescription(smsView1.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        r19[56] = new ThemeDescription(smsView2.confirmTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r19[57] = new ThemeDescription(smsView2.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r19[58] = new ThemeDescription(smsView2.codeField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r19[59] = new ThemeDescription(smsView2.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        r19[60] = new ThemeDescription(smsView2.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        r19[61] = new ThemeDescription(smsView2.timeText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r19[62] = new ThemeDescription(smsView2.problemText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r19[63] = new ThemeDescription(smsView2.wrongNumber, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r19[64] = new ThemeDescription(smsView2.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        r19[65] = new ThemeDescription(smsView2.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        r19[66] = new ThemeDescription(smsView3.confirmTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r19[67] = new ThemeDescription(smsView3.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r19[68] = new ThemeDescription(smsView3.codeField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r19[69] = new ThemeDescription(smsView3.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        r19[70] = new ThemeDescription(smsView3.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        r19[71] = new ThemeDescription(smsView3.timeText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r19[72] = new ThemeDescription(smsView3.problemText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r19[73] = new ThemeDescription(smsView3.wrongNumber, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r19[74] = new ThemeDescription(smsView3.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        r19[75] = new ThemeDescription(smsView3.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        r19[76] = new ThemeDescription(smsView4.confirmTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r19[77] = new ThemeDescription(smsView4.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r19[78] = new ThemeDescription(smsView4.codeField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r19[79] = new ThemeDescription(smsView4.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        r19[80] = new ThemeDescription(smsView4.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        r19[81] = new ThemeDescription(smsView4.timeText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r19[82] = new ThemeDescription(smsView4.problemText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r19[83] = new ThemeDescription(smsView4.wrongNumber, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r19[84] = new ThemeDescription(smsView4.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        r19[85] = new ThemeDescription(smsView4.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        return r19;
    }

    private void showLoadingDialog(Context context, String title, String message) {
        this.dialogLoading = new ProgressDialog(getParentActivity());
        this.dialogLoading.setTitle(title);
        this.dialogLoading.setMessage(message);
        this.dialogLoading.show();
    }
}
