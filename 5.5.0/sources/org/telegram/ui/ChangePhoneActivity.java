package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.TL_account_changePhone;
import org.telegram.tgnet.TLRPC.TL_account_sendChangePhoneCode;
import org.telegram.tgnet.TLRPC.TL_auth_cancelCode;
import org.telegram.tgnet.TLRPC.TL_auth_codeTypeCall;
import org.telegram.tgnet.TLRPC.TL_auth_codeTypeFlashCall;
import org.telegram.tgnet.TLRPC.TL_auth_codeTypeSms;
import org.telegram.tgnet.TLRPC.TL_auth_resendCode;
import org.telegram.tgnet.TLRPC.TL_auth_sentCode;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeApp;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeCall;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeFlashCall;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeSms;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.HintEditText;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.SlideView;
import org.telegram.ui.CountrySelectActivity.CountrySelectActivityDelegate;

public class ChangePhoneActivity extends BaseFragment {
    private static final int done_button = 1;
    private boolean checkPermissions = true;
    private int currentViewNum = 0;
    private View doneButton;
    private Dialog permissionsDialog;
    private ArrayList<String> permissionsItems = new ArrayList();
    private AlertDialog progressDialog;
    private SlideView[] views = new SlideView[5];

    /* renamed from: org.telegram.ui.ChangePhoneActivity$1 */
    class C40551 extends ActionBarMenuOnItemClick {
        C40551() {
        }

        public void onItemClick(int i) {
            if (i == 1) {
                ChangePhoneActivity.this.views[ChangePhoneActivity.this.currentViewNum].onNextPressed();
            } else if (i == -1) {
                ChangePhoneActivity.this.finishFragment();
            }
        }
    }

    public class LoginActivitySmsView extends SlideView implements NotificationCenterDelegate {
        private EditTextBoldCursor codeField;
        private volatile int codeTime = DefaultLoadControl.DEFAULT_MIN_BUFFER_MS;
        private Timer codeTimer;
        private TextView confirmTextView;
        private Bundle currentParams;
        private int currentType;
        private String emailPhone;
        private boolean ignoreOnTextChange;
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

        /* renamed from: org.telegram.ui.ChangePhoneActivity$LoginActivitySmsView$6 */
        class C40656 extends TimerTask {

            /* renamed from: org.telegram.ui.ChangePhoneActivity$LoginActivitySmsView$6$1 */
            class C40641 implements Runnable {
                C40641() {
                }

                public void run() {
                    if (LoginActivitySmsView.this.codeTime <= 1000) {
                        LoginActivitySmsView.this.problemText.setVisibility(0);
                        LoginActivitySmsView.this.destroyCodeTimer();
                    }
                }
            }

            C40656() {
            }

            public void run() {
                double currentTimeMillis = (double) System.currentTimeMillis();
                LoginActivitySmsView.this.codeTime = (int) (((double) LoginActivitySmsView.this.codeTime) - (currentTimeMillis - LoginActivitySmsView.this.lastCodeTime));
                LoginActivitySmsView.this.lastCodeTime = currentTimeMillis;
                AndroidUtilities.runOnUIThread(new C40641());
            }
        }

        /* renamed from: org.telegram.ui.ChangePhoneActivity$LoginActivitySmsView$7 */
        class C40697 extends TimerTask {

            /* renamed from: org.telegram.ui.ChangePhoneActivity$LoginActivitySmsView$7$1 */
            class C40681 implements Runnable {

                /* renamed from: org.telegram.ui.ChangePhoneActivity$LoginActivitySmsView$7$1$1 */
                class C40671 implements RequestDelegate {
                    C40671() {
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

                C40681() {
                }

                public void run() {
                    if (LoginActivitySmsView.this.time >= 1000) {
                        int access$3300 = (LoginActivitySmsView.this.time / 1000) - (((LoginActivitySmsView.this.time / 1000) / 60) * 60);
                        if (LoginActivitySmsView.this.nextType == 4 || LoginActivitySmsView.this.nextType == 3) {
                            LoginActivitySmsView.this.timeText.setText(LocaleController.formatString("CallText", R.string.CallText, new Object[]{Integer.valueOf(r0), Integer.valueOf(access$3300)}));
                        } else if (LoginActivitySmsView.this.nextType == 2) {
                            LoginActivitySmsView.this.timeText.setText(LocaleController.formatString("SmsText", R.string.SmsText, new Object[]{Integer.valueOf(r0), Integer.valueOf(access$3300)}));
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
                            ConnectionsManager.getInstance().sendRequest(tL_auth_resendCode, new C40671(), 2);
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

            C40697() {
            }

            public void run() {
                if (LoginActivitySmsView.this.timeTimer != null) {
                    double currentTimeMillis = (double) System.currentTimeMillis();
                    LoginActivitySmsView.this.time = (int) (((double) LoginActivitySmsView.this.time) - (currentTimeMillis - LoginActivitySmsView.this.lastCurrentTime));
                    LoginActivitySmsView.this.lastCurrentTime = currentTimeMillis;
                    AndroidUtilities.runOnUIThread(new C40681());
                }
            }
        }

        public LoginActivitySmsView(Context context, int i) {
            View frameLayout;
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
                View imageView = new ImageView(context);
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
            this.codeField.addTextChangedListener(new TextWatcher(ChangePhoneActivity.this) {
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
            this.codeField.setOnEditorActionListener(new OnEditorActionListener(ChangePhoneActivity.this) {
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
            this.problemText.setOnClickListener(new OnClickListener(ChangePhoneActivity.this) {
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
                                AlertsCreator.showSimpleAlert(ChangePhoneActivity.this, LocaleController.getString("NoMailInstalled", R.string.NoMailInstalled));
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
            this.wrongNumber = new TextView(context);
            this.wrongNumber.setGravity((LocaleController.isRTL ? 5 : 3) | 1);
            this.wrongNumber.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            this.wrongNumber.setTextSize(1, 14.0f);
            this.wrongNumber.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            this.wrongNumber.setPadding(0, AndroidUtilities.dp(24.0f), 0, 0);
            frameLayout.addView(this.wrongNumber, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 80, 0, 0, 0, 10));
            this.wrongNumber.setText(LocaleController.getString("WrongNumber", R.string.WrongNumber));
            this.wrongNumber.setOnClickListener(new OnClickListener(ChangePhoneActivity.this) {

                /* renamed from: org.telegram.ui.ChangePhoneActivity$LoginActivitySmsView$4$1 */
                class C40601 implements RequestDelegate {
                    C40601() {
                    }

                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    }
                }

                public void onClick(View view) {
                    TLObject tL_auth_cancelCode = new TL_auth_cancelCode();
                    tL_auth_cancelCode.phone_number = LoginActivitySmsView.this.requestPhone;
                    tL_auth_cancelCode.phone_code_hash = LoginActivitySmsView.this.phoneHash;
                    ConnectionsManager.getInstance().sendRequest(tL_auth_cancelCode, new C40601(), 2);
                    LoginActivitySmsView.this.onBackPressed();
                    ChangePhoneActivity.this.setPage(0, true, null, true);
                }
            });
        }

        private void createCodeTimer() {
            if (this.codeTimer == null) {
                this.codeTime = DefaultLoadControl.DEFAULT_MIN_BUFFER_MS;
                this.codeTimer = new Timer();
                this.lastCodeTime = (double) System.currentTimeMillis();
                this.codeTimer.schedule(new C40656(), 0, 1000);
            }
        }

        private void createTimer() {
            if (this.timeTimer == null) {
                this.timeTimer = new Timer();
                this.timeTimer.schedule(new C40697(), 0, 1000);
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
            ChangePhoneActivity.this.needShowProgress();
            final TLObject tL_auth_resendCode = new TL_auth_resendCode();
            tL_auth_resendCode.phone_number = this.requestPhone;
            tL_auth_resendCode.phone_code_hash = this.phoneHash;
            ConnectionsManager.getInstance().sendRequest(tL_auth_resendCode, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            LoginActivitySmsView.this.nextPressed = false;
                            if (tLRPC$TL_error == null) {
                                ChangePhoneActivity.this.fillNextCodeParams(bundle, (TL_auth_sentCode) tLObject);
                            } else {
                                AlertsCreator.processError(tLRPC$TL_error, ChangePhoneActivity.this, tL_auth_resendCode, new Object[0]);
                                if (tLRPC$TL_error.text.contains("PHONE_CODE_EXPIRED")) {
                                    LoginActivitySmsView.this.onBackPressed();
                                    ChangePhoneActivity.this.setPage(0, true, null, true);
                                }
                            }
                            ChangePhoneActivity.this.needHideProgress();
                        }
                    });
                }
            }, 2);
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
                final TLObject tL_account_changePhone = new TL_account_changePhone();
                tL_account_changePhone.phone_number = this.requestPhone;
                tL_account_changePhone.phone_code = this.codeField.getText().toString();
                tL_account_changePhone.phone_code_hash = this.phoneHash;
                destroyTimer();
                ChangePhoneActivity.this.needShowProgress();
                ConnectionsManager.getInstance().sendRequest(tL_account_changePhone, new RequestDelegate() {
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                ChangePhoneActivity.this.needHideProgress();
                                LoginActivitySmsView.this.nextPressed = false;
                                if (tLRPC$TL_error == null) {
                                    User user = (User) tLObject;
                                    LoginActivitySmsView.this.destroyTimer();
                                    LoginActivitySmsView.this.destroyCodeTimer();
                                    UserConfig.setCurrentUser(user);
                                    UserConfig.saveConfig(true);
                                    ArrayList arrayList = new ArrayList();
                                    arrayList.add(user);
                                    MessagesStorage.getInstance().putUsersAndChats(arrayList, null, true, true);
                                    MessagesController.getInstance().putUser(user, false);
                                    ChangePhoneActivity.this.finishFragment();
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
                                    return;
                                }
                                LoginActivitySmsView.this.lastError = tLRPC$TL_error.text;
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
                                    AlertsCreator.processError(tLRPC$TL_error, ChangePhoneActivity.this, tL_account_changePhone, new Object[0]);
                                }
                            }
                        });
                    }
                }, 2);
            }
        }

        public void onShow() {
            super.onShow();
            if (this.codeField != null) {
                this.codeField.requestFocus();
                this.codeField.setSelection(this.codeField.length());
            }
        }

        public void setParams(Bundle bundle, boolean z) {
            int i = 0;
            if (bundle != null) {
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
                        this.timeText.setVisibility(8);
                    } else if (this.currentType == 3 && (this.nextType == 4 || this.nextType == 2)) {
                        this.problemText.setVisibility(8);
                        this.timeText.setVisibility(0);
                        if (this.nextType == 4) {
                            this.timeText.setText(LocaleController.formatString("CallText", R.string.CallText, new Object[]{Integer.valueOf(1), Integer.valueOf(0)}));
                        } else if (this.nextType == 2) {
                            this.timeText.setText(LocaleController.formatString("SmsText", R.string.SmsText, new Object[]{Integer.valueOf(1), Integer.valueOf(0)}));
                        }
                        createTimer();
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
        private View view;

        public PhoneView(Context context) {
            Object toUpperCase;
            String str;
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
            this.countryButton.setOnClickListener(new OnClickListener(ChangePhoneActivity.this) {

                /* renamed from: org.telegram.ui.ChangePhoneActivity$PhoneView$1$1 */
                class C40731 implements CountrySelectActivityDelegate {

                    /* renamed from: org.telegram.ui.ChangePhoneActivity$PhoneView$1$1$1 */
                    class C40721 implements Runnable {
                        C40721() {
                        }

                        public void run() {
                            AndroidUtilities.showKeyboard(PhoneView.this.phoneField);
                        }
                    }

                    C40731() {
                    }

                    public void didSelectCountry(String str, String str2) {
                        PhoneView.this.selectCountry(str);
                        AndroidUtilities.runOnUIThread(new C40721(), 300);
                        PhoneView.this.phoneField.requestFocus();
                        PhoneView.this.phoneField.setSelection(PhoneView.this.phoneField.length());
                    }
                }

                public void onClick(View view) {
                    BaseFragment countrySelectActivity = new CountrySelectActivity(true);
                    countrySelectActivity.setCountrySelectActivityDelegate(new C40731());
                    ChangePhoneActivity.this.presentFragment(countrySelectActivity);
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
            this.codeField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.codeField.setCursorSize(AndroidUtilities.dp(20.0f));
            this.codeField.setCursorWidth(1.5f);
            this.codeField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
            this.codeField.setPadding(AndroidUtilities.dp(10.0f), 0, 0, 0);
            this.codeField.setTextSize(1, 18.0f);
            this.codeField.setMaxLines(1);
            this.codeField.setGravity(19);
            this.codeField.setImeOptions(268435461);
            this.codeField.setFilters(new InputFilter[]{new LengthFilter(5)});
            linearLayout.addView(this.codeField, LayoutHelper.createLinear(55, 36, -9.0f, BitmapDescriptorFactory.HUE_RED, 16.0f, BitmapDescriptorFactory.HUE_RED));
            this.codeField.addTextChangedListener(new TextWatcher(ChangePhoneActivity.this) {
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
                                PhoneView.this.ignoreOnTextChange = true;
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
                                    PhoneView.this.ignoreOnTextChange = true;
                                    str2 = substring.substring(1, substring.length()) + PhoneView.this.phoneField.getText().toString();
                                    EditTextBoldCursor access$400 = PhoneView.this.codeField;
                                    substring = substring.substring(0, 1);
                                    access$400.setText(substring);
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
                                    HintEditText access$200 = PhoneView.this.phoneField;
                                    if (str2 != null) {
                                        str = str2.replace('X', '–');
                                    }
                                    access$200.setHintText(str);
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
            this.codeField.setOnEditorActionListener(new OnEditorActionListener(ChangePhoneActivity.this) {
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
            this.phoneField.addTextChangedListener(new TextWatcher(ChangePhoneActivity.this) {
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
                            HintEditText access$200 = PhoneView.this.phoneField;
                            if (selectionStart > PhoneView.this.phoneField.length()) {
                                selectionStart = PhoneView.this.phoneField.length();
                            }
                            access$200.setSelection(selectionStart);
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
            this.phoneField.setOnEditorActionListener(new OnEditorActionListener(ChangePhoneActivity.this) {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 5) {
                        return false;
                    }
                    PhoneView.this.onNextPressed();
                    return true;
                }
            });
            this.textView2 = new TextView(context);
            this.textView2.setText(LocaleController.getString("ChangePhoneHelp", R.string.ChangePhoneHelp));
            this.textView2.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.textView2.setTextSize(1, 14.0f);
            this.textView2.setGravity(LocaleController.isRTL ? 5 : 3);
            this.textView2.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            addView(this.textView2, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3, 0, 28, 0, 10));
            HashMap hashMap = new HashMap();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResources().getAssets().open("countries.txt")));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    String[] split = readLine.split(";");
                    this.countriesArray.add(0, split[2]);
                    this.countriesMap.put(split[2], split[0]);
                    this.codesMap.put(split[0], split[2]);
                    if (split.length > 3) {
                        this.phoneFormatMap.put(split[0], split[3]);
                    }
                    hashMap.put(split[1], split[2]);
                }
                bufferedReader.close();
            } catch (Throwable e) {
                FileLog.e(e);
            }
            Collections.sort(this.countriesArray, new Comparator<String>(ChangePhoneActivity.this) {
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
                        AndroidUtilities.showKeyboard(this.phoneField);
                        this.phoneField.requestFocus();
                        this.phoneField.setSelection(this.phoneField.length());
                    }
                    AndroidUtilities.showKeyboard(this.codeField);
                    this.codeField.requestFocus();
                    return;
                }
            } catch (Throwable e2) {
                FileLog.e(e2);
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
                AndroidUtilities.showKeyboard(this.codeField);
                this.codeField.requestFocus();
                return;
            }
            AndroidUtilities.showKeyboard(this.phoneField);
            this.phoneField.requestFocus();
            this.phoneField.setSelection(this.phoneField.length());
        }

        public String getHeaderName() {
            return LocaleController.getString("ChangePhoneNewNumber", R.string.ChangePhoneNewNumber);
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
            if (ChangePhoneActivity.this.getParentActivity() != null && !this.nextPressed) {
                boolean z;
                TelephonyManager telephonyManager = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
                boolean z2 = (telephonyManager.getSimState() == 1 || telephonyManager.getPhoneType() == 0) ? false : true;
                if (VERSION.SDK_INT < 23 || !z2) {
                    z = true;
                } else {
                    z = ChangePhoneActivity.this.getParentActivity().checkSelfPermission("android.permission.READ_PHONE_STATE") == 0;
                    boolean z3 = ChangePhoneActivity.this.getParentActivity().checkSelfPermission("android.permission.RECEIVE_SMS") == 0;
                    if (ChangePhoneActivity.this.checkPermissions) {
                        ChangePhoneActivity.this.permissionsItems.clear();
                        if (!z) {
                            ChangePhoneActivity.this.permissionsItems.add("android.permission.READ_PHONE_STATE");
                        }
                        if (!z3) {
                            ChangePhoneActivity.this.permissionsItems.add("android.permission.RECEIVE_SMS");
                            if (VERSION.SDK_INT >= 23) {
                                ChangePhoneActivity.this.permissionsItems.add("android.permission.READ_SMS");
                            }
                        }
                        if (!ChangePhoneActivity.this.permissionsItems.isEmpty()) {
                            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                            if (sharedPreferences.getBoolean("firstlogin", true) || ChangePhoneActivity.this.getParentActivity().shouldShowRequestPermissionRationale("android.permission.READ_PHONE_STATE") || ChangePhoneActivity.this.getParentActivity().shouldShowRequestPermissionRationale("android.permission.RECEIVE_SMS")) {
                                sharedPreferences.edit().putBoolean("firstlogin", false).commit();
                                Builder builder = new Builder(ChangePhoneActivity.this.getParentActivity());
                                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                if (ChangePhoneActivity.this.permissionsItems.size() == 2) {
                                    builder.setMessage(LocaleController.getString("AllowReadCallAndSms", R.string.AllowReadCallAndSms));
                                } else if (z3) {
                                    builder.setMessage(LocaleController.getString("AllowReadCall", R.string.AllowReadCall));
                                } else {
                                    builder.setMessage(LocaleController.getString("AllowReadSms", R.string.AllowReadSms));
                                }
                                ChangePhoneActivity.this.permissionsDialog = ChangePhoneActivity.this.showDialog(builder.create());
                                return;
                            }
                            ChangePhoneActivity.this.getParentActivity().requestPermissions((String[]) ChangePhoneActivity.this.permissionsItems.toArray(new String[ChangePhoneActivity.this.permissionsItems.size()]), 6);
                            return;
                        }
                    }
                }
                if (this.countryState == 1) {
                    AlertsCreator.showSimpleAlert(ChangePhoneActivity.this, LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
                } else if (this.countryState == 2 && !BuildVars.DEBUG_VERSION) {
                    AlertsCreator.showSimpleAlert(ChangePhoneActivity.this, LocaleController.getString("WrongCountry", R.string.WrongCountry));
                } else if (this.codeField.length() == 0) {
                    AlertsCreator.showSimpleAlert(ChangePhoneActivity.this, LocaleController.getString("InvalidPhoneNumber", R.string.InvalidPhoneNumber));
                } else {
                    final TLObject tL_account_sendChangePhoneCode = new TL_account_sendChangePhoneCode();
                    String b = C2488b.b(TtmlNode.ANONYMOUS_REGION_ID + this.codeField.getText() + this.phoneField.getText());
                    tL_account_sendChangePhoneCode.phone_number = b;
                    z2 = z2 && z;
                    tL_account_sendChangePhoneCode.allow_flashcall = z2;
                    if (tL_account_sendChangePhoneCode.allow_flashcall) {
                        try {
                            Object line1Number = telephonyManager.getLine1Number();
                            if (TextUtils.isEmpty(line1Number)) {
                                tL_account_sendChangePhoneCode.current_number = false;
                            } else {
                                boolean z4 = b.contains(line1Number) || line1Number.contains(b);
                                tL_account_sendChangePhoneCode.current_number = z4;
                                if (!tL_account_sendChangePhoneCode.current_number) {
                                    tL_account_sendChangePhoneCode.allow_flashcall = false;
                                }
                            }
                        } catch (Throwable e) {
                            tL_account_sendChangePhoneCode.allow_flashcall = false;
                            FileLog.e(e);
                        }
                    }
                    final Bundle bundle = new Bundle();
                    bundle.putString("phone", "+" + this.codeField.getText() + this.phoneField.getText());
                    try {
                        bundle.putString("ephone", "+" + C2488b.b(this.codeField.getText().toString()) + " " + C2488b.b(this.phoneField.getText().toString()));
                    } catch (Throwable e2) {
                        FileLog.e(e2);
                        bundle.putString("ephone", "+" + b);
                    }
                    bundle.putString("phoneFormated", b);
                    this.nextPressed = true;
                    ChangePhoneActivity.this.needShowProgress();
                    ConnectionsManager.getInstance().sendRequest(tL_account_sendChangePhoneCode, new RequestDelegate() {
                        public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    PhoneView.this.nextPressed = false;
                                    if (tLRPC$TL_error == null) {
                                        ChangePhoneActivity.this.fillNextCodeParams(bundle, (TL_auth_sentCode) tLObject);
                                    } else {
                                        AlertsCreator.processError(tLRPC$TL_error, ChangePhoneActivity.this, tL_account_sendChangePhoneCode, bundle.getString("phone"));
                                    }
                                    ChangePhoneActivity.this.needHideProgress();
                                }
                            });
                        }
                    }, 2);
                }
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }

        public void onShow() {
            super.onShow();
            if (this.phoneField == null) {
                return;
            }
            if (this.codeField.length() != 0) {
                AndroidUtilities.showKeyboard(this.phoneField);
                this.phoneField.requestFocus();
                this.phoneField.setSelection(this.phoneField.length());
                return;
            }
            AndroidUtilities.showKeyboard(this.codeField);
            this.codeField.requestFocus();
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

    public View createView(Context context) {
        this.actionBar.setTitle(LocaleController.getString("AppName", R.string.AppName));
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setActionBarMenuOnItemClick(new C40551());
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
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
        int i = 0;
        while (i < this.views.length) {
            this.views[i].setVisibility(i == 0 ? 0 : 8);
            frameLayout.addView(this.views[i], LayoutHelper.createFrame(-1, i == 0 ? -2.0f : -1.0f, 51, AndroidUtilities.isTablet() ? 26.0f : 18.0f, 30.0f, AndroidUtilities.isTablet() ? 26.0f : 18.0f, BitmapDescriptorFactory.HUE_RED));
            i++;
        }
        this.actionBar.setTitle(this.views[0].getHeaderName());
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        PhoneView phoneView = (PhoneView) this.views[0];
        LoginActivitySmsView loginActivitySmsView = (LoginActivitySmsView) this.views[1];
        LoginActivitySmsView loginActivitySmsView2 = (LoginActivitySmsView) this.views[2];
        LoginActivitySmsView loginActivitySmsView3 = (LoginActivitySmsView) this.views[3];
        LoginActivitySmsView loginActivitySmsView4 = (LoginActivitySmsView) this.views[4];
        r13 = new ThemeDescription[57];
        r13[25] = new ThemeDescription(loginActivitySmsView.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        r13[26] = new ThemeDescription(loginActivitySmsView.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        r13[27] = new ThemeDescription(loginActivitySmsView2.confirmTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r13[28] = new ThemeDescription(loginActivitySmsView2.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r13[29] = new ThemeDescription(loginActivitySmsView2.codeField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r13[30] = new ThemeDescription(loginActivitySmsView2.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        r13[31] = new ThemeDescription(loginActivitySmsView2.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        r13[32] = new ThemeDescription(loginActivitySmsView2.timeText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r13[33] = new ThemeDescription(loginActivitySmsView2.problemText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r13[34] = new ThemeDescription(loginActivitySmsView2.wrongNumber, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r13[35] = new ThemeDescription(loginActivitySmsView2.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        r13[36] = new ThemeDescription(loginActivitySmsView2.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        r13[37] = new ThemeDescription(loginActivitySmsView3.confirmTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r13[38] = new ThemeDescription(loginActivitySmsView3.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r13[39] = new ThemeDescription(loginActivitySmsView3.codeField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r13[40] = new ThemeDescription(loginActivitySmsView3.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        r13[41] = new ThemeDescription(loginActivitySmsView3.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        r13[42] = new ThemeDescription(loginActivitySmsView3.timeText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r13[43] = new ThemeDescription(loginActivitySmsView3.problemText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r13[44] = new ThemeDescription(loginActivitySmsView3.wrongNumber, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r13[45] = new ThemeDescription(loginActivitySmsView3.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        r13[46] = new ThemeDescription(loginActivitySmsView3.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        r13[47] = new ThemeDescription(loginActivitySmsView4.confirmTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r13[48] = new ThemeDescription(loginActivitySmsView4.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r13[49] = new ThemeDescription(loginActivitySmsView4.codeField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r13[50] = new ThemeDescription(loginActivitySmsView.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        r13[51] = new ThemeDescription(loginActivitySmsView4.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        r13[52] = new ThemeDescription(loginActivitySmsView4.timeText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r13[53] = new ThemeDescription(loginActivitySmsView4.problemText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r13[54] = new ThemeDescription(loginActivitySmsView4.wrongNumber, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r13[55] = new ThemeDescription(loginActivitySmsView4.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        r13[56] = new ThemeDescription(loginActivitySmsView4.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        return r13;
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

    public void needShowProgress() {
        if (getParentActivity() != null && !getParentActivity().isFinishing() && this.progressDialog == null) {
            this.progressDialog = new AlertDialog(getParentActivity(), 1);
            this.progressDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
            this.progressDialog.setCanceledOnTouchOutside(false);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
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
            return true;
        }
        this.views[this.currentViewNum].onBackPressed();
        setPage(0, true, null, true);
        return false;
    }

    protected void onDialogDismiss(Dialog dialog) {
        if (VERSION.SDK_INT >= 23 && dialog == this.permissionsDialog && !this.permissionsItems.isEmpty()) {
            getParentActivity().requestPermissions((String[]) this.permissionsItems.toArray(new String[this.permissionsItems.size()]), 6);
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
        AndroidUtilities.removeAdjustResize(getParentActivity(), this.classGuid);
    }

    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        if (i == 6) {
            this.checkPermissions = false;
            if (this.currentViewNum == 0) {
                this.views[this.currentViewNum].onNextPressed();
            }
        }
    }

    public void onResume() {
        super.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
    }

    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z) {
            this.views[this.currentViewNum].onShow();
        }
    }

    public void setPage(int i, boolean z, Bundle bundle, boolean z2) {
        if (i == 3) {
            this.doneButton.setVisibility(8);
        } else {
            if (i == 0) {
                this.checkPermissions = true;
            }
            this.doneButton.setVisibility(0);
        }
        final SlideView slideView = this.views[this.currentViewNum];
        final SlideView slideView2 = this.views[i];
        this.currentViewNum = i;
        slideView2.setParams(bundle, false);
        this.actionBar.setTitle(slideView2.getHeaderName());
        slideView2.onShow();
        slideView2.setX(z2 ? (float) (-AndroidUtilities.displaySize.x) : (float) AndroidUtilities.displaySize.x);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(300);
        Animator[] animatorArr = new Animator[2];
        String str = "translationX";
        float[] fArr = new float[1];
        fArr[0] = z2 ? (float) AndroidUtilities.displaySize.x : (float) (-AndroidUtilities.displaySize.x);
        animatorArr[0] = ObjectAnimator.ofFloat(slideView, str, fArr);
        animatorArr[1] = ObjectAnimator.ofFloat(slideView2, "translationX", new float[]{BitmapDescriptorFactory.HUE_RED});
        animatorSet.playTogether(animatorArr);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                slideView.setVisibility(8);
                slideView.setX(BitmapDescriptorFactory.HUE_RED);
            }

            public void onAnimationStart(Animator animator) {
                slideView2.setVisibility(0);
            }
        });
        animatorSet.start();
    }
}
