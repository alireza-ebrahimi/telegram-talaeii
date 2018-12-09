package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.TL_account_confirmPhone;
import org.telegram.tgnet.TLRPC.TL_account_sendConfirmPhoneCode;
import org.telegram.tgnet.TLRPC.TL_auth_codeTypeCall;
import org.telegram.tgnet.TLRPC.TL_auth_codeTypeFlashCall;
import org.telegram.tgnet.TLRPC.TL_auth_codeTypeSms;
import org.telegram.tgnet.TLRPC.TL_auth_resendCode;
import org.telegram.tgnet.TLRPC.TL_auth_sentCode;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeApp;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeCall;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeFlashCall;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeSms;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RadialProgressView;
import org.telegram.ui.Components.SlideView;

public class CancelAccountDeletionActivity extends BaseFragment {
    private static final int done_button = 1;
    private boolean checkPermissions = false;
    private int currentViewNum = 0;
    private View doneButton;
    private Dialog errorDialog;
    private String hash;
    private Dialog permissionsDialog;
    private ArrayList<String> permissionsItems = new ArrayList();
    private String phone;
    private AlertDialog progressDialog;
    private SlideView[] views = new SlideView[5];

    /* renamed from: org.telegram.ui.CancelAccountDeletionActivity$1 */
    class C39781 extends ActionBarMenuOnItemClick {
        C39781() {
        }

        public void onItemClick(int i) {
            if (i == 1) {
                CancelAccountDeletionActivity.this.views[CancelAccountDeletionActivity.this.currentViewNum].onNextPressed();
            } else if (i == -1) {
                CancelAccountDeletionActivity.this.finishFragment();
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
        private volatile int time = 60000;
        private TextView timeText;
        private Timer timeTimer;
        private int timeout;
        private final Object timerSync = new Object();
        private boolean waitingForEvent;

        /* renamed from: org.telegram.ui.CancelAccountDeletionActivity$LoginActivitySmsView$5 */
        class C39865 extends TimerTask {

            /* renamed from: org.telegram.ui.CancelAccountDeletionActivity$LoginActivitySmsView$5$1 */
            class C39851 implements Runnable {
                C39851() {
                }

                public void run() {
                    if (LoginActivitySmsView.this.codeTime <= 1000) {
                        LoginActivitySmsView.this.problemText.setVisibility(0);
                        LoginActivitySmsView.this.destroyCodeTimer();
                    }
                }
            }

            C39865() {
            }

            public void run() {
                double currentTimeMillis = (double) System.currentTimeMillis();
                LoginActivitySmsView.this.codeTime = (int) (((double) LoginActivitySmsView.this.codeTime) - (currentTimeMillis - LoginActivitySmsView.this.lastCodeTime));
                LoginActivitySmsView.this.lastCodeTime = currentTimeMillis;
                AndroidUtilities.runOnUIThread(new C39851());
            }
        }

        /* renamed from: org.telegram.ui.CancelAccountDeletionActivity$LoginActivitySmsView$6 */
        class C39906 extends TimerTask {

            /* renamed from: org.telegram.ui.CancelAccountDeletionActivity$LoginActivitySmsView$6$1 */
            class C39891 implements Runnable {

                /* renamed from: org.telegram.ui.CancelAccountDeletionActivity$LoginActivitySmsView$6$1$1 */
                class C39881 implements RequestDelegate {
                    C39881() {
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

                C39891() {
                }

                public void run() {
                    if (LoginActivitySmsView.this.time >= 1000) {
                        int access$2100 = (LoginActivitySmsView.this.time / 1000) - (((LoginActivitySmsView.this.time / 1000) / 60) * 60);
                        if (LoginActivitySmsView.this.nextType == 4 || LoginActivitySmsView.this.nextType == 3) {
                            LoginActivitySmsView.this.timeText.setText(LocaleController.formatString("CallText", R.string.CallText, new Object[]{Integer.valueOf(r0), Integer.valueOf(access$2100)}));
                        } else if (LoginActivitySmsView.this.nextType == 2) {
                            LoginActivitySmsView.this.timeText.setText(LocaleController.formatString("SmsText", R.string.SmsText, new Object[]{Integer.valueOf(r0), Integer.valueOf(access$2100)}));
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
                            tL_auth_resendCode.phone_number = LoginActivitySmsView.this.phone;
                            tL_auth_resendCode.phone_code_hash = LoginActivitySmsView.this.phoneHash;
                            ConnectionsManager.getInstance().sendRequest(tL_auth_resendCode, new C39881(), 2);
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

            C39906() {
            }

            public void run() {
                if (LoginActivitySmsView.this.timeTimer != null) {
                    double currentTimeMillis = (double) System.currentTimeMillis();
                    LoginActivitySmsView.this.time = (int) (((double) LoginActivitySmsView.this.time) - (currentTimeMillis - LoginActivitySmsView.this.lastCurrentTime));
                    LoginActivitySmsView.this.lastCurrentTime = currentTimeMillis;
                    AndroidUtilities.runOnUIThread(new C39891());
                }
            }
        }

        public LoginActivitySmsView(Context context, int i) {
            super(context);
            this.currentType = i;
            setOrientation(1);
            this.confirmTextView = new TextView(context);
            this.confirmTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.confirmTextView.setTextSize(1, 14.0f);
            this.confirmTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            this.confirmTextView.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
            if (this.currentType == 3) {
                View frameLayout = new FrameLayout(context);
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
            this.codeField.setCursorWidth(1.5f);
            this.codeField.setCursorSize(AndroidUtilities.dp(20.0f));
            this.codeField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.codeField.setImeOptions(268435461);
            this.codeField.setTextSize(1, 18.0f);
            this.codeField.setInputType(3);
            this.codeField.setMaxLines(1);
            this.codeField.setPadding(0, 0, 0, 0);
            this.codeField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
            addView(this.codeField, LayoutHelper.createLinear(-1, 36, 1, 0, 20, 0, 0));
            this.codeField.addTextChangedListener(new TextWatcher(CancelAccountDeletionActivity.this) {
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
            this.codeField.setOnEditorActionListener(new OnEditorActionListener(CancelAccountDeletionActivity.this) {
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
            this.problemText.setOnClickListener(new OnClickListener(CancelAccountDeletionActivity.this) {
                public void onClick(View view) {
                    if (!LoginActivitySmsView.this.nextPressed) {
                        if (LoginActivitySmsView.this.nextType == 0 || LoginActivitySmsView.this.nextType == 4) {
                            try {
                                PackageInfo packageInfo = ApplicationLoader.applicationContext.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
                                String format = String.format(Locale.US, "%s (%d)", new Object[]{packageInfo.versionName, Integer.valueOf(packageInfo.versionCode)});
                                Intent intent = new Intent("android.intent.action.SEND");
                                intent.setType("message/rfc822");
                                intent.putExtra("android.intent.extra.EMAIL", new String[]{"sms@stel.com"});
                                intent.putExtra("android.intent.extra.SUBJECT", "Android cancel account deletion issue " + format + " " + LoginActivitySmsView.this.phone);
                                intent.putExtra("android.intent.extra.TEXT", "Phone: " + LoginActivitySmsView.this.phone + "\nApp version: " + format + "\nOS version: SDK " + VERSION.SDK_INT + "\nDevice Name: " + Build.MANUFACTURER + Build.MODEL + "\nLocale: " + Locale.getDefault() + "\nError: " + LoginActivitySmsView.this.lastError);
                                LoginActivitySmsView.this.getContext().startActivity(Intent.createChooser(intent, "Send email..."));
                                return;
                            } catch (Exception e) {
                                AlertsCreator.showSimpleAlert(CancelAccountDeletionActivity.this, LocaleController.getString("NoMailInstalled", R.string.NoMailInstalled));
                                return;
                            }
                        }
                        LoginActivitySmsView.this.resendCode();
                    }
                }
            });
        }

        private void createCodeTimer() {
            if (this.codeTimer == null) {
                this.codeTime = DefaultLoadControl.DEFAULT_MIN_BUFFER_MS;
                this.codeTimer = new Timer();
                this.lastCodeTime = (double) System.currentTimeMillis();
                this.codeTimer.schedule(new C39865(), 0, 1000);
            }
        }

        private void createTimer() {
            if (this.timeTimer == null) {
                this.timeTimer = new Timer();
                this.timeTimer.schedule(new C39906(), 0, 1000);
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
            this.nextPressed = true;
            CancelAccountDeletionActivity.this.needShowProgress();
            final TLObject tL_auth_resendCode = new TL_auth_resendCode();
            tL_auth_resendCode.phone_number = this.phone;
            tL_auth_resendCode.phone_code_hash = this.phoneHash;
            ConnectionsManager.getInstance().sendRequest(tL_auth_resendCode, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            LoginActivitySmsView.this.nextPressed = false;
                            if (tLRPC$TL_error == null) {
                                CancelAccountDeletionActivity.this.fillNextCodeParams(bundle, (TL_auth_sentCode) tLObject);
                            } else {
                                AlertsCreator.processError(tLRPC$TL_error, CancelAccountDeletionActivity.this, tL_auth_resendCode, new Object[0]);
                            }
                            CancelAccountDeletionActivity.this.needHideProgress();
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
            return LocaleController.getString("CancelAccountReset", R.string.CancelAccountReset);
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
                final TLObject tL_account_confirmPhone = new TL_account_confirmPhone();
                tL_account_confirmPhone.phone_code = this.codeField.getText().toString();
                tL_account_confirmPhone.phone_code_hash = this.phoneHash;
                destroyTimer();
                CancelAccountDeletionActivity.this.needShowProgress();
                ConnectionsManager.getInstance().sendRequest(tL_account_confirmPhone, new RequestDelegate() {
                    public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                CancelAccountDeletionActivity.this.needHideProgress();
                                LoginActivitySmsView.this.nextPressed = false;
                                if (tLRPC$TL_error == null) {
                                    CancelAccountDeletionActivity.this.errorDialog = AlertsCreator.showSimpleAlert(CancelAccountDeletionActivity.this, LocaleController.formatString("CancelLinkSuccess", R.string.CancelLinkSuccess, new Object[]{C2488b.a().e("+" + LoginActivitySmsView.this.phone)}));
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
                                    AlertsCreator.processError(tLRPC$TL_error, CancelAccountDeletionActivity.this, tL_account_confirmPhone, new Object[0]);
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
                    this.confirmTextView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("CancelAccountResetInfo", R.string.CancelAccountResetInfo, new Object[]{C2488b.a().e("+" + e)})));
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

    public class PhoneView extends SlideView {
        private boolean nextPressed = false;
        private RadialProgressView progressBar;

        public PhoneView(Context context) {
            super(context);
            setOrientation(1);
            View frameLayout = new FrameLayout(context);
            addView(frameLayout, LayoutHelper.createLinear(-1, Callback.DEFAULT_DRAG_ANIMATION_DURATION));
            this.progressBar = new RadialProgressView(context);
            frameLayout.addView(this.progressBar, LayoutHelper.createFrame(-2, -2, 17));
        }

        public String getHeaderName() {
            return LocaleController.getString("CancelAccountReset", R.string.CancelAccountReset);
        }

        public void onNextPressed() {
            if (CancelAccountDeletionActivity.this.getParentActivity() != null && !this.nextPressed) {
                final TLObject tL_account_sendConfirmPhoneCode;
                TelephonyManager telephonyManager = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
                boolean z = (telephonyManager.getSimState() == 1 || telephonyManager.getPhoneType() == 0) ? false : true;
                if (VERSION.SDK_INT < 23 || z) {
                    tL_account_sendConfirmPhoneCode = new TL_account_sendConfirmPhoneCode();
                    tL_account_sendConfirmPhoneCode.allow_flashcall = false;
                    tL_account_sendConfirmPhoneCode.hash = CancelAccountDeletionActivity.this.hash;
                } else {
                    tL_account_sendConfirmPhoneCode = new TL_account_sendConfirmPhoneCode();
                    tL_account_sendConfirmPhoneCode.allow_flashcall = false;
                    tL_account_sendConfirmPhoneCode.hash = CancelAccountDeletionActivity.this.hash;
                }
                if (tL_account_sendConfirmPhoneCode.allow_flashcall) {
                    try {
                        Object line1Number = telephonyManager.getLine1Number();
                        if (TextUtils.isEmpty(line1Number)) {
                            tL_account_sendConfirmPhoneCode.current_number = false;
                        } else {
                            boolean z2 = CancelAccountDeletionActivity.this.phone.contains(line1Number) || line1Number.contains(CancelAccountDeletionActivity.this.phone);
                            tL_account_sendConfirmPhoneCode.current_number = z2;
                            if (!tL_account_sendConfirmPhoneCode.current_number) {
                                tL_account_sendConfirmPhoneCode.allow_flashcall = false;
                            }
                        }
                    } catch (Throwable e) {
                        tL_account_sendConfirmPhoneCode.allow_flashcall = false;
                        FileLog.e(e);
                    }
                }
                final Bundle bundle = new Bundle();
                bundle.putString("phone", CancelAccountDeletionActivity.this.phone);
                this.nextPressed = true;
                ConnectionsManager.getInstance().sendRequest(tL_account_sendConfirmPhoneCode, new RequestDelegate() {
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                PhoneView.this.nextPressed = false;
                                if (tLRPC$TL_error == null) {
                                    CancelAccountDeletionActivity.this.fillNextCodeParams(bundle, (TL_auth_sentCode) tLObject);
                                } else {
                                    CancelAccountDeletionActivity.this.errorDialog = AlertsCreator.processError(tLRPC$TL_error, CancelAccountDeletionActivity.this, tL_account_sendConfirmPhoneCode, new Object[0]);
                                }
                            }
                        });
                    }
                }, 2);
            }
        }

        public void onShow() {
            super.onShow();
            onNextPressed();
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

    public CancelAccountDeletionActivity(Bundle bundle) {
        super(bundle);
        this.hash = bundle.getString("hash");
        this.phone = bundle.getString("phone");
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
        this.actionBar.setActionBarMenuOnItemClick(new C39781());
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.doneButton.setVisibility(8);
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
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[43];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[6] = new ThemeDescription(phoneView.progressBar, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle);
        themeDescriptionArr[7] = new ThemeDescription(loginActivitySmsView.confirmTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        themeDescriptionArr[8] = new ThemeDescription(loginActivitySmsView.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[9] = new ThemeDescription(loginActivitySmsView.codeField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        themeDescriptionArr[10] = new ThemeDescription(loginActivitySmsView.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        themeDescriptionArr[11] = new ThemeDescription(loginActivitySmsView.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        themeDescriptionArr[12] = new ThemeDescription(loginActivitySmsView.timeText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        themeDescriptionArr[13] = new ThemeDescription(loginActivitySmsView.problemText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        themeDescriptionArr[14] = new ThemeDescription(loginActivitySmsView.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        themeDescriptionArr[15] = new ThemeDescription(loginActivitySmsView.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        themeDescriptionArr[16] = new ThemeDescription(loginActivitySmsView2.confirmTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        themeDescriptionArr[17] = new ThemeDescription(loginActivitySmsView2.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[18] = new ThemeDescription(loginActivitySmsView2.codeField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        themeDescriptionArr[19] = new ThemeDescription(loginActivitySmsView2.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        themeDescriptionArr[20] = new ThemeDescription(loginActivitySmsView2.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        themeDescriptionArr[21] = new ThemeDescription(loginActivitySmsView2.timeText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        themeDescriptionArr[22] = new ThemeDescription(loginActivitySmsView2.problemText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        themeDescriptionArr[23] = new ThemeDescription(loginActivitySmsView2.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        themeDescriptionArr[24] = new ThemeDescription(loginActivitySmsView2.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        themeDescriptionArr[25] = new ThemeDescription(loginActivitySmsView3.confirmTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        themeDescriptionArr[26] = new ThemeDescription(loginActivitySmsView3.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[27] = new ThemeDescription(loginActivitySmsView3.codeField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        themeDescriptionArr[28] = new ThemeDescription(loginActivitySmsView3.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        themeDescriptionArr[29] = new ThemeDescription(loginActivitySmsView3.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        themeDescriptionArr[30] = new ThemeDescription(loginActivitySmsView3.timeText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        themeDescriptionArr[31] = new ThemeDescription(loginActivitySmsView3.problemText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        themeDescriptionArr[32] = new ThemeDescription(loginActivitySmsView3.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        themeDescriptionArr[33] = new ThemeDescription(loginActivitySmsView3.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        themeDescriptionArr[34] = new ThemeDescription(loginActivitySmsView4.confirmTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        themeDescriptionArr[35] = new ThemeDescription(loginActivitySmsView4.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[36] = new ThemeDescription(loginActivitySmsView4.codeField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        themeDescriptionArr[37] = new ThemeDescription(loginActivitySmsView4.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        themeDescriptionArr[38] = new ThemeDescription(loginActivitySmsView4.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        themeDescriptionArr[39] = new ThemeDescription(loginActivitySmsView4.timeText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        themeDescriptionArr[40] = new ThemeDescription(loginActivitySmsView4.problemText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        themeDescriptionArr[41] = new ThemeDescription(loginActivitySmsView4.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressInner);
        themeDescriptionArr[42] = new ThemeDescription(loginActivitySmsView4.progressView, 0, new Class[]{ProgressView.class}, new String[]{"paint"}, null, null, null, Theme.key_login_progressOuter);
        return themeDescriptionArr;
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
        for (int i = 0; i < this.views.length; i++) {
            if (this.views[i] != null) {
                this.views[i].onDestroyActivity();
            }
        }
        return true;
    }

    protected void onDialogDismiss(Dialog dialog) {
        if (VERSION.SDK_INT >= 23 && dialog == this.permissionsDialog && !this.permissionsItems.isEmpty()) {
            getParentActivity().requestPermissions((String[]) this.permissionsItems.toArray(new String[this.permissionsItems.size()]), 6);
        }
        if (dialog == this.errorDialog) {
            finishFragment();
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
        if (i != 3 && i != 0) {
            this.doneButton.setVisibility(0);
        } else if (i == 0) {
            this.doneButton.setVisibility(8);
        } else {
            this.doneButton.setVisibility(8);
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
