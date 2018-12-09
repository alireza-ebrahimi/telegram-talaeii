package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.LineItem;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.Wallet.WalletOptions;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.WalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;
import com.p111h.p112a.C1976a;
import com.p111h.p112a.C1979b;
import com.p111h.p112a.p113a.C1969a;
import com.p111h.p112a.p113a.C1970b;
import com.p111h.p112a.p114b.C1977a;
import com.p111h.p112a.p114b.C1978b;
import com.p111h.p112a.p115c.C1990g;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import org.ir.talaeii.R;
import org.json.JSONException;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_dataJSON;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputPaymentCredentials;
import org.telegram.tgnet.TLRPC$TL_inputPaymentCredentialsAndroidPay;
import org.telegram.tgnet.TLRPC$TL_inputPaymentCredentialsSaved;
import org.telegram.tgnet.TLRPC$TL_labeledPrice;
import org.telegram.tgnet.TLRPC$TL_paymentRequestedInfo;
import org.telegram.tgnet.TLRPC$TL_payments_clearSavedInfo;
import org.telegram.tgnet.TLRPC$TL_payments_paymentForm;
import org.telegram.tgnet.TLRPC$TL_payments_paymentReceipt;
import org.telegram.tgnet.TLRPC$TL_payments_paymentResult;
import org.telegram.tgnet.TLRPC$TL_payments_paymentVerficationNeeded;
import org.telegram.tgnet.TLRPC$TL_payments_sendPaymentForm;
import org.telegram.tgnet.TLRPC$TL_payments_validateRequestedInfo;
import org.telegram.tgnet.TLRPC$TL_payments_validatedRequestedInfo;
import org.telegram.tgnet.TLRPC$TL_postAddress;
import org.telegram.tgnet.TLRPC$TL_shippingOption;
import org.telegram.tgnet.TLRPC$account_Password;
import org.telegram.tgnet.TLRPC.TL_account_getPassword;
import org.telegram.tgnet.TLRPC.TL_account_getTmpPassword;
import org.telegram.tgnet.TLRPC.TL_account_noPassword;
import org.telegram.tgnet.TLRPC.TL_account_password;
import org.telegram.tgnet.TLRPC.TL_account_passwordInputSettings;
import org.telegram.tgnet.TLRPC.TL_account_tmpPassword;
import org.telegram.tgnet.TLRPC.TL_account_updatePasswordSettings;
import org.telegram.tgnet.TLRPC.TL_boolTrue;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.PaymentInfoCell;
import org.telegram.ui.Cells.RadioCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextDetailSettingsCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextPriceCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.ContextProgressView;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.HintEditText;
import org.telegram.ui.CountrySelectActivity.CountrySelectActivityDelegate;

public class PaymentFormActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int FIELDS_COUNT_ADDRESS = 10;
    private static final int FIELDS_COUNT_CARD = 6;
    private static final int FIELDS_COUNT_PASSWORD = 3;
    private static final int FIELDS_COUNT_SAVEDCARD = 2;
    private static final int FIELD_CARD = 0;
    private static final int FIELD_CARDNAME = 2;
    private static final int FIELD_CARD_COUNTRY = 4;
    private static final int FIELD_CARD_POSTCODE = 5;
    private static final int FIELD_CITY = 2;
    private static final int FIELD_COUNTRY = 4;
    private static final int FIELD_CVV = 3;
    private static final int FIELD_EMAIL = 7;
    private static final int FIELD_ENTERPASSWORD = 0;
    private static final int FIELD_ENTERPASSWORDEMAIL = 2;
    private static final int FIELD_EXPIRE_DATE = 1;
    private static final int FIELD_NAME = 6;
    private static final int FIELD_PHONE = 9;
    private static final int FIELD_PHONECODE = 8;
    private static final int FIELD_POSTCODE = 5;
    private static final int FIELD_REENTERPASSWORD = 1;
    private static final int FIELD_SAVEDCARD = 0;
    private static final int FIELD_SAVEDPASSWORD = 1;
    private static final int FIELD_STATE = 3;
    private static final int FIELD_STREET1 = 0;
    private static final int FIELD_STREET2 = 1;
    private static final int LOAD_FULL_WALLET_REQUEST_CODE = 1001;
    private static final int LOAD_MASKED_WALLET_REQUEST_CODE = 1000;
    private static final int done_button = 1;
    private static final int fragment_container_id = 4000;
    private int androidPayBackgroundColor;
    private boolean androidPayBlackTheme;
    private FrameLayout androidPayContainer;
    private TLRPC$TL_inputPaymentCredentialsAndroidPay androidPayCredentials;
    private String androidPayPublicKey;
    private User botUser;
    private TextInfoPrivacyCell[] bottomCell;
    private FrameLayout bottomLayout;
    private boolean canceled;
    private String cardName;
    private TextCheckCell checkCell1;
    private HashMap<String, String> codesMap;
    private ArrayList<String> countriesArray;
    private HashMap<String, String> countriesMap;
    private String countryName;
    private String currentBotName;
    private String currentItemName;
    private TLRPC$account_Password currentPassword;
    private int currentStep;
    private PaymentFormActivityDelegate delegate;
    private TextDetailSettingsCell[] detailSettingsCell;
    private ArrayList<View> dividers;
    private ActionBarMenuItem doneItem;
    private AnimatorSet doneItemAnimation;
    private boolean donePressed;
    private GoogleApiClient googleApiClient;
    private HeaderCell[] headerCell;
    private boolean ignoreOnCardChange;
    private boolean ignoreOnPhoneChange;
    private boolean ignoreOnTextChange;
    private EditTextBoldCursor[] inputFields;
    private boolean isWebView;
    private LinearLayout linearLayout2;
    private boolean loadingPasswordInfo;
    private MessageObject messageObject;
    private boolean need_card_country;
    private boolean need_card_name;
    private boolean need_card_postcode;
    private PaymentFormActivity passwordFragment;
    private boolean passwordOk;
    private TextView payTextView;
    private TLRPC$TL_payments_paymentForm paymentForm;
    private PaymentInfoCell paymentInfoCell;
    private String paymentJson;
    private HashMap<String, String> phoneFormatMap;
    private ContextProgressView progressView;
    private ContextProgressView progressViewButton;
    private RadioCell[] radioCells;
    private TLRPC$TL_payments_validatedRequestedInfo requestedInfo;
    private boolean saveCardInfo;
    private boolean saveShippingInfo;
    private ScrollView scrollView;
    private ShadowSectionCell[] sectionCell;
    private TextSettingsCell settingsCell1;
    private TLRPC$TL_shippingOption shippingOption;
    private Runnable shortPollRunnable;
    private String stripeApiKey;
    private TextView textView;
    private String totalPriceDecimal;
    private TLRPC$TL_payments_validateRequestedInfo validateRequest;
    private boolean waitingForEmail;
    private WebView webView;
    private boolean webviewLoading;

    /* renamed from: org.telegram.ui.PaymentFormActivity$1 */
    class C49941 extends ActionBarMenuOnItemClick {
        C49941() {
        }

        public void onItemClick(int i) {
            int i2 = 0;
            if (i == -1) {
                if (!PaymentFormActivity.this.donePressed) {
                    PaymentFormActivity.this.finishFragment();
                }
            } else if (i == 1 && !PaymentFormActivity.this.donePressed) {
                if (PaymentFormActivity.this.currentStep != 3) {
                    AndroidUtilities.hideKeyboard(PaymentFormActivity.this.getParentActivity().getCurrentFocus());
                }
                if (PaymentFormActivity.this.currentStep == 0) {
                    PaymentFormActivity.this.setDonePressed(true);
                    PaymentFormActivity.this.sendForm();
                } else if (PaymentFormActivity.this.currentStep == 1) {
                    while (i2 < PaymentFormActivity.this.radioCells.length) {
                        if (PaymentFormActivity.this.radioCells[i2].isChecked()) {
                            PaymentFormActivity.this.shippingOption = (TLRPC$TL_shippingOption) PaymentFormActivity.this.requestedInfo.shipping_options.get(i2);
                            break;
                        }
                        i2++;
                    }
                    PaymentFormActivity.this.goToNextStep();
                } else if (PaymentFormActivity.this.currentStep == 2) {
                    PaymentFormActivity.this.sendCardData();
                } else if (PaymentFormActivity.this.currentStep == 3) {
                    PaymentFormActivity.this.checkPassword();
                } else if (PaymentFormActivity.this.currentStep == 6) {
                    PaymentFormActivity.this.sendSavePassword(false);
                }
            }
        }
    }

    private interface PaymentFormActivityDelegate {
        void currentPasswordUpdated(TLRPC$account_Password tLRPC$account_Password);

        boolean didSelectNewCard(String str, String str2, boolean z, TLRPC$TL_inputPaymentCredentialsAndroidPay tLRPC$TL_inputPaymentCredentialsAndroidPay);

        void onFragmentDestroyed();
    }

    /* renamed from: org.telegram.ui.PaymentFormActivity$2 */
    class C50002 implements Comparator<String> {
        C50002() {
        }

        public int compare(String str, String str2) {
            return str.compareTo(str2);
        }
    }

    /* renamed from: org.telegram.ui.PaymentFormActivity$3 */
    class C50143 implements OnTouchListener {

        /* renamed from: org.telegram.ui.PaymentFormActivity$3$1 */
        class C50011 implements CountrySelectActivityDelegate {
            C50011() {
            }

            public void didSelectCountry(String str, String str2) {
                PaymentFormActivity.this.inputFields[4].setText(str);
                PaymentFormActivity.this.countryName = str2;
            }
        }

        C50143() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (PaymentFormActivity.this.getParentActivity() == null) {
                return false;
            }
            if (motionEvent.getAction() == 1) {
                BaseFragment countrySelectActivity = new CountrySelectActivity(false);
                countrySelectActivity.setCountrySelectActivityDelegate(new C50011());
                PaymentFormActivity.this.presentFragment(countrySelectActivity);
            }
            return true;
        }
    }

    /* renamed from: org.telegram.ui.PaymentFormActivity$4 */
    class C50154 implements TextWatcher {
        C50154() {
        }

        public void afterTextChanged(Editable editable) {
            if (!PaymentFormActivity.this.ignoreOnTextChange) {
                PaymentFormActivity.this.ignoreOnTextChange = true;
                String b = C2488b.b(PaymentFormActivity.this.inputFields[8].getText().toString());
                PaymentFormActivity.this.inputFields[8].setText(b);
                HintEditText hintEditText = (HintEditText) PaymentFormActivity.this.inputFields[9];
                if (b.length() == 0) {
                    hintEditText.setHintText(null);
                    hintEditText.setHint(LocaleController.getString("PaymentShippingPhoneNumber", R.string.PaymentShippingPhoneNumber));
                } else {
                    String str;
                    Object obj;
                    boolean z;
                    CharSequence charSequence;
                    boolean z2;
                    if (b.length() > 4) {
                        String substring;
                        boolean z3;
                        for (int i = 4; i >= 1; i--) {
                            substring = b.substring(0, i);
                            if (((String) PaymentFormActivity.this.codesMap.get(substring)) != null) {
                                str = b.substring(i, b.length()) + PaymentFormActivity.this.inputFields[9].getText().toString();
                                PaymentFormActivity.this.inputFields[8].setText(substring);
                                z3 = true;
                                break;
                            }
                        }
                        str = null;
                        substring = b;
                        z3 = false;
                        if (!z3) {
                            str = substring.substring(1, substring.length()) + PaymentFormActivity.this.inputFields[9].getText().toString();
                            EditTextBoldCursor editTextBoldCursor = PaymentFormActivity.this.inputFields[8];
                            substring = substring.substring(0, 1);
                            editTextBoldCursor.setText(substring);
                        }
                        obj = substring;
                        z = z3;
                        charSequence = str;
                    } else {
                        z = false;
                        String str2 = b;
                        charSequence = null;
                    }
                    str = (String) PaymentFormActivity.this.codesMap.get(obj);
                    if (!(str == null || PaymentFormActivity.this.countriesArray.indexOf(str) == -1)) {
                        str = (String) PaymentFormActivity.this.phoneFormatMap.get(obj);
                        if (str != null) {
                            hintEditText.setHintText(str.replace('X', '–'));
                            hintEditText.setHint(null);
                            z2 = true;
                            if (!z2) {
                                hintEditText.setHintText(null);
                                hintEditText.setHint(LocaleController.getString("PaymentShippingPhoneNumber", R.string.PaymentShippingPhoneNumber));
                            }
                            if (!z) {
                                PaymentFormActivity.this.inputFields[8].setSelection(PaymentFormActivity.this.inputFields[8].getText().length());
                            }
                            if (charSequence != null) {
                                hintEditText.requestFocus();
                                hintEditText.setText(charSequence);
                                hintEditText.setSelection(hintEditText.length());
                            }
                        }
                    }
                    z2 = false;
                    if (z2) {
                        hintEditText.setHintText(null);
                        hintEditText.setHint(LocaleController.getString("PaymentShippingPhoneNumber", R.string.PaymentShippingPhoneNumber));
                    }
                    if (z) {
                        PaymentFormActivity.this.inputFields[8].setSelection(PaymentFormActivity.this.inputFields[8].getText().length());
                    }
                    if (charSequence != null) {
                        hintEditText.requestFocus();
                        hintEditText.setText(charSequence);
                        hintEditText.setSelection(hintEditText.length());
                    }
                }
                PaymentFormActivity.this.ignoreOnTextChange = false;
            }
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.PaymentFormActivity$5 */
    class C50165 implements TextWatcher {
        private int actionPosition;
        private int characterAction = -1;

        C50165() {
        }

        public void afterTextChanged(Editable editable) {
            if (!PaymentFormActivity.this.ignoreOnPhoneChange) {
                HintEditText hintEditText = (HintEditText) PaymentFormActivity.this.inputFields[9];
                int selectionStart = hintEditText.getSelectionStart();
                String str = "0123456789";
                String obj = hintEditText.getText().toString();
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
                PaymentFormActivity.this.ignoreOnPhoneChange = true;
                String hintText = hintEditText.getHintText();
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
                hintEditText.setText(stringBuilder);
                if (selectionStart >= 0) {
                    if (selectionStart > hintEditText.length()) {
                        selectionStart = hintEditText.length();
                    }
                    hintEditText.setSelection(selectionStart);
                }
                hintEditText.onTextChange();
                PaymentFormActivity.this.ignoreOnPhoneChange = false;
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
    }

    /* renamed from: org.telegram.ui.PaymentFormActivity$6 */
    class C50176 implements OnEditorActionListener {
        C50176() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == 5) {
                int intValue = ((Integer) textView.getTag()).intValue();
                while (intValue + 1 < PaymentFormActivity.this.inputFields.length) {
                    int i2 = intValue + 1;
                    if (i2 != 4 && ((View) PaymentFormActivity.this.inputFields[i2].getParent()).getVisibility() == 0) {
                        PaymentFormActivity.this.inputFields[i2].requestFocus();
                        break;
                    }
                    intValue = i2;
                }
                return true;
            } else if (i != 6) {
                return false;
            } else {
                PaymentFormActivity.this.doneItem.performClick();
                return true;
            }
        }
    }

    /* renamed from: org.telegram.ui.PaymentFormActivity$7 */
    class C50187 implements OnClickListener {
        C50187() {
        }

        public void onClick(View view) {
            PaymentFormActivity.this.saveShippingInfo = !PaymentFormActivity.this.saveShippingInfo;
            PaymentFormActivity.this.checkCell1.setChecked(PaymentFormActivity.this.saveShippingInfo);
        }
    }

    /* renamed from: org.telegram.ui.PaymentFormActivity$9 */
    class C50209 extends WebViewClient {
        C50209() {
        }

        public void onLoadResource(WebView webView, String str) {
            super.onLoadResource(webView, str);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            PaymentFormActivity.this.webviewLoading = false;
            PaymentFormActivity.this.showEditDoneProgress(true, false);
            PaymentFormActivity.this.updateSavePaymentField();
        }
    }

    private static class LinkMovementMethodMy extends LinkMovementMethod {
        private LinkMovementMethodMy() {
        }

        public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            try {
                boolean onTouchEvent = super.onTouchEvent(textView, spannable, motionEvent);
                if (motionEvent.getAction() != 1 && motionEvent.getAction() != 3) {
                    return onTouchEvent;
                }
                Selection.removeSelection(spannable);
                return onTouchEvent;
            } catch (Throwable e) {
                FileLog.e(e);
                return false;
            }
        }
    }

    public class LinkSpan extends ClickableSpan {
        public void onClick(View view) {
            PaymentFormActivity.this.presentFragment(new TwoStepVerificationActivity(0));
        }

        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setUnderlineText(false);
        }
    }

    public PaymentFormActivity(MessageObject messageObject, TLRPC$TL_payments_paymentReceipt tLRPC$TL_payments_paymentReceipt) {
        this.countriesArray = new ArrayList();
        this.countriesMap = new HashMap();
        this.codesMap = new HashMap();
        this.phoneFormatMap = new HashMap();
        this.headerCell = new HeaderCell[3];
        this.dividers = new ArrayList();
        this.sectionCell = new ShadowSectionCell[3];
        this.bottomCell = new TextInfoPrivacyCell[3];
        this.detailSettingsCell = new TextDetailSettingsCell[7];
        this.currentStep = 5;
        this.paymentForm = new TLRPC$TL_payments_paymentForm();
        this.paymentForm.bot_id = tLRPC$TL_payments_paymentReceipt.bot_id;
        this.paymentForm.invoice = tLRPC$TL_payments_paymentReceipt.invoice;
        this.paymentForm.provider_id = tLRPC$TL_payments_paymentReceipt.provider_id;
        this.paymentForm.users = tLRPC$TL_payments_paymentReceipt.users;
        this.shippingOption = tLRPC$TL_payments_paymentReceipt.shipping;
        this.messageObject = messageObject;
        this.botUser = MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_payments_paymentReceipt.bot_id));
        if (this.botUser != null) {
            this.currentBotName = this.botUser.first_name;
        } else {
            this.currentBotName = TtmlNode.ANONYMOUS_REGION_ID;
        }
        this.currentItemName = messageObject.messageOwner.media.title;
        if (tLRPC$TL_payments_paymentReceipt.info != null) {
            this.validateRequest = new TLRPC$TL_payments_validateRequestedInfo();
            this.validateRequest.info = tLRPC$TL_payments_paymentReceipt.info;
        }
        this.cardName = tLRPC$TL_payments_paymentReceipt.credentials_title;
    }

    public PaymentFormActivity(TLRPC$TL_payments_paymentForm tLRPC$TL_payments_paymentForm, MessageObject messageObject) {
        int i = 3;
        this.countriesArray = new ArrayList();
        this.countriesMap = new HashMap();
        this.codesMap = new HashMap();
        this.phoneFormatMap = new HashMap();
        this.headerCell = new HeaderCell[3];
        this.dividers = new ArrayList();
        this.sectionCell = new ShadowSectionCell[3];
        this.bottomCell = new TextInfoPrivacyCell[3];
        this.detailSettingsCell = new TextDetailSettingsCell[7];
        if (tLRPC$TL_payments_paymentForm.invoice.shipping_address_requested || tLRPC$TL_payments_paymentForm.invoice.email_requested || tLRPC$TL_payments_paymentForm.invoice.name_requested || tLRPC$TL_payments_paymentForm.invoice.phone_requested) {
            i = 0;
        } else if (tLRPC$TL_payments_paymentForm.saved_credentials != null) {
            if (UserConfig.tmpPassword != null && UserConfig.tmpPassword.valid_until < ConnectionsManager.getInstance().getCurrentTime() + 60) {
                UserConfig.tmpPassword = null;
                UserConfig.saveConfig(false);
            }
            if (UserConfig.tmpPassword != null) {
                i = 4;
            }
        } else {
            i = 2;
        }
        init(tLRPC$TL_payments_paymentForm, messageObject, i, null, null, null, null, null, false, null);
    }

    private PaymentFormActivity(TLRPC$TL_payments_paymentForm tLRPC$TL_payments_paymentForm, MessageObject messageObject, int i, TLRPC$TL_payments_validatedRequestedInfo tLRPC$TL_payments_validatedRequestedInfo, TLRPC$TL_shippingOption tLRPC$TL_shippingOption, String str, String str2, TLRPC$TL_payments_validateRequestedInfo tLRPC$TL_payments_validateRequestedInfo, boolean z, TLRPC$TL_inputPaymentCredentialsAndroidPay tLRPC$TL_inputPaymentCredentialsAndroidPay) {
        this.countriesArray = new ArrayList();
        this.countriesMap = new HashMap();
        this.codesMap = new HashMap();
        this.phoneFormatMap = new HashMap();
        this.headerCell = new HeaderCell[3];
        this.dividers = new ArrayList();
        this.sectionCell = new ShadowSectionCell[3];
        this.bottomCell = new TextInfoPrivacyCell[3];
        this.detailSettingsCell = new TextDetailSettingsCell[7];
        init(tLRPC$TL_payments_paymentForm, messageObject, i, tLRPC$TL_payments_validatedRequestedInfo, tLRPC$TL_shippingOption, str, str2, tLRPC$TL_payments_validateRequestedInfo, z, tLRPC$TL_inputPaymentCredentialsAndroidPay);
    }

    private void checkPassword() {
        if (UserConfig.tmpPassword != null && UserConfig.tmpPassword.valid_until < ConnectionsManager.getInstance().getCurrentTime() + 60) {
            UserConfig.tmpPassword = null;
            UserConfig.saveConfig(false);
        }
        if (UserConfig.tmpPassword != null) {
            sendData();
        } else if (this.inputFields[1].length() == 0) {
            Vibrator vibrator = (Vibrator) ApplicationLoader.applicationContext.getSystemService("vibrator");
            if (vibrator != null) {
                vibrator.vibrate(200);
            }
            AndroidUtilities.shakeView(this.inputFields[1], 2.0f, 0);
        } else {
            final String obj = this.inputFields[1].getText().toString();
            showEditDoneProgress(true, true);
            setDonePressed(true);
            final TLObject tL_account_getPassword = new TL_account_getPassword();
            ConnectionsManager.getInstance().sendRequest(tL_account_getPassword, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLRPC$TL_error != null) {
                                AlertsCreator.processError(tLRPC$TL_error, PaymentFormActivity.this, tL_account_getPassword, new Object[0]);
                                PaymentFormActivity.this.showEditDoneProgress(true, false);
                                PaymentFormActivity.this.setDonePressed(false);
                            } else if (tLObject instanceof TL_account_noPassword) {
                                PaymentFormActivity.this.passwordOk = false;
                                PaymentFormActivity.this.goToNextStep();
                            } else {
                                TL_account_password tL_account_password = (TL_account_password) tLObject;
                                Object obj = null;
                                try {
                                    obj = obj.getBytes(C3446C.UTF8_NAME);
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                                Object obj2 = new byte[((tL_account_password.current_salt.length * 2) + obj.length)];
                                System.arraycopy(tL_account_password.current_salt, 0, obj2, 0, tL_account_password.current_salt.length);
                                System.arraycopy(obj, 0, obj2, tL_account_password.current_salt.length, obj.length);
                                System.arraycopy(tL_account_password.current_salt, 0, obj2, obj2.length - tL_account_password.current_salt.length, tL_account_password.current_salt.length);
                                final TLObject tL_account_getTmpPassword = new TL_account_getTmpPassword();
                                tL_account_getTmpPassword.password_hash = Utilities.computeSHA256(obj2, 0, obj2.length);
                                tL_account_getTmpPassword.period = 1800;
                                ConnectionsManager.getInstance().sendRequest(tL_account_getTmpPassword, new RequestDelegate() {
                                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                                        AndroidUtilities.runOnUIThread(new Runnable() {
                                            public void run() {
                                                PaymentFormActivity.this.showEditDoneProgress(true, false);
                                                PaymentFormActivity.this.setDonePressed(false);
                                                if (tLObject != null) {
                                                    PaymentFormActivity.this.passwordOk = true;
                                                    UserConfig.tmpPassword = (TL_account_tmpPassword) tLObject;
                                                    UserConfig.saveConfig(false);
                                                    PaymentFormActivity.this.goToNextStep();
                                                } else if (tLRPC$TL_error.text.equals("PASSWORD_HASH_INVALID")) {
                                                    Vibrator vibrator = (Vibrator) ApplicationLoader.applicationContext.getSystemService("vibrator");
                                                    if (vibrator != null) {
                                                        vibrator.vibrate(200);
                                                    }
                                                    AndroidUtilities.shakeView(PaymentFormActivity.this.inputFields[1], 2.0f, 0);
                                                    PaymentFormActivity.this.inputFields[1].setText(TtmlNode.ANONYMOUS_REGION_ID);
                                                } else {
                                                    AlertsCreator.processError(tLRPC$TL_error, PaymentFormActivity.this, tL_account_getTmpPassword, new Object[0]);
                                                }
                                            }
                                        });
                                    }
                                }, 2);
                            }
                        }
                    });
                }
            }, 2);
        }
    }

    private TLRPC$TL_paymentRequestedInfo getRequestInfo() {
        TLRPC$TL_paymentRequestedInfo tLRPC$TL_paymentRequestedInfo = new TLRPC$TL_paymentRequestedInfo();
        if (this.paymentForm.invoice.name_requested) {
            tLRPC$TL_paymentRequestedInfo.name = this.inputFields[6].getText().toString();
            tLRPC$TL_paymentRequestedInfo.flags |= 1;
        }
        if (this.paymentForm.invoice.phone_requested) {
            tLRPC$TL_paymentRequestedInfo.phone = "+" + this.inputFields[8].getText().toString() + this.inputFields[9].getText().toString();
            tLRPC$TL_paymentRequestedInfo.flags |= 2;
        }
        if (this.paymentForm.invoice.email_requested) {
            tLRPC$TL_paymentRequestedInfo.email = this.inputFields[7].getText().toString().trim();
            tLRPC$TL_paymentRequestedInfo.flags |= 4;
        }
        if (this.paymentForm.invoice.shipping_address_requested) {
            tLRPC$TL_paymentRequestedInfo.shipping_address = new TLRPC$TL_postAddress();
            tLRPC$TL_paymentRequestedInfo.shipping_address.street_line1 = this.inputFields[0].getText().toString();
            tLRPC$TL_paymentRequestedInfo.shipping_address.street_line2 = this.inputFields[1].getText().toString();
            tLRPC$TL_paymentRequestedInfo.shipping_address.city = this.inputFields[2].getText().toString();
            tLRPC$TL_paymentRequestedInfo.shipping_address.state = this.inputFields[3].getText().toString();
            tLRPC$TL_paymentRequestedInfo.shipping_address.country_iso2 = this.countryName != null ? this.countryName : TtmlNode.ANONYMOUS_REGION_ID;
            tLRPC$TL_paymentRequestedInfo.shipping_address.post_code = this.inputFields[5].getText().toString();
            tLRPC$TL_paymentRequestedInfo.flags |= 8;
        }
        return tLRPC$TL_paymentRequestedInfo;
    }

    private String getTotalPriceDecimalString(ArrayList<TLRPC$TL_labeledPrice> arrayList) {
        long j = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            j += ((TLRPC$TL_labeledPrice) arrayList.get(i)).amount;
        }
        return LocaleController.getInstance().formatCurrencyDecimalString(j, this.paymentForm.invoice.currency, false);
    }

    private String getTotalPriceString(ArrayList<TLRPC$TL_labeledPrice> arrayList) {
        long j = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            j += ((TLRPC$TL_labeledPrice) arrayList.get(i)).amount;
        }
        return LocaleController.getInstance().formatCurrencyString(j, this.paymentForm.invoice.currency);
    }

    private void goToNextStep() {
        int i;
        if (this.currentStep == 0) {
            if (this.paymentForm.invoice.flexible) {
                i = 1;
            } else if (this.paymentForm.saved_credentials != null) {
                if (UserConfig.tmpPassword != null && UserConfig.tmpPassword.valid_until < ConnectionsManager.getInstance().getCurrentTime() + 60) {
                    UserConfig.tmpPassword = null;
                    UserConfig.saveConfig(false);
                }
                i = UserConfig.tmpPassword != null ? 4 : 3;
            } else {
                i = 2;
            }
            presentFragment(new PaymentFormActivity(this.paymentForm, this.messageObject, i, this.requestedInfo, null, null, this.cardName, this.validateRequest, this.saveCardInfo, this.androidPayCredentials), this.isWebView);
        } else if (this.currentStep == 1) {
            if (this.paymentForm.saved_credentials != null) {
                if (UserConfig.tmpPassword != null && UserConfig.tmpPassword.valid_until < ConnectionsManager.getInstance().getCurrentTime() + 60) {
                    UserConfig.tmpPassword = null;
                    UserConfig.saveConfig(false);
                }
                i = UserConfig.tmpPassword != null ? 4 : 3;
            } else {
                i = 2;
            }
            presentFragment(new PaymentFormActivity(this.paymentForm, this.messageObject, i, this.requestedInfo, this.shippingOption, null, this.cardName, this.validateRequest, this.saveCardInfo, this.androidPayCredentials), this.isWebView);
        } else if (this.currentStep == 2) {
            if (this.paymentForm.password_missing && this.saveCardInfo) {
                this.passwordFragment = new PaymentFormActivity(this.paymentForm, this.messageObject, 6, this.requestedInfo, this.shippingOption, this.paymentJson, this.cardName, this.validateRequest, this.saveCardInfo, this.androidPayCredentials);
                this.passwordFragment.setCurrentPassword(this.currentPassword);
                this.passwordFragment.setDelegate(new PaymentFormActivityDelegate() {
                    public void currentPasswordUpdated(TLRPC$account_Password tLRPC$account_Password) {
                        PaymentFormActivity.this.currentPassword = tLRPC$account_Password;
                    }

                    public boolean didSelectNewCard(String str, String str2, boolean z, TLRPC$TL_inputPaymentCredentialsAndroidPay tLRPC$TL_inputPaymentCredentialsAndroidPay) {
                        if (PaymentFormActivity.this.delegate != null) {
                            PaymentFormActivity.this.delegate.didSelectNewCard(str, str2, z, tLRPC$TL_inputPaymentCredentialsAndroidPay);
                        }
                        if (PaymentFormActivity.this.isWebView) {
                            PaymentFormActivity.this.removeSelfFromStack();
                        }
                        return PaymentFormActivity.this.delegate != null;
                    }

                    public void onFragmentDestroyed() {
                        PaymentFormActivity.this.passwordFragment = null;
                    }
                });
                presentFragment(this.passwordFragment, this.isWebView);
            } else if (this.delegate != null) {
                this.delegate.didSelectNewCard(this.paymentJson, this.cardName, this.saveCardInfo, this.androidPayCredentials);
                finishFragment();
            } else {
                presentFragment(new PaymentFormActivity(this.paymentForm, this.messageObject, 4, this.requestedInfo, this.shippingOption, this.paymentJson, this.cardName, this.validateRequest, this.saveCardInfo, this.androidPayCredentials), this.isWebView);
            }
        } else if (this.currentStep == 3) {
            presentFragment(new PaymentFormActivity(this.paymentForm, this.messageObject, this.passwordOk ? 4 : 2, this.requestedInfo, this.shippingOption, this.paymentJson, this.cardName, this.validateRequest, this.saveCardInfo, this.androidPayCredentials), !this.passwordOk);
        } else if (this.currentStep == 4) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.paymentFinished, new Object[0]);
            finishFragment();
        } else if (this.currentStep != 6) {
        } else {
            if (this.delegate.didSelectNewCard(this.paymentJson, this.cardName, this.saveCardInfo, this.androidPayCredentials)) {
                finishFragment();
            } else {
                presentFragment(new PaymentFormActivity(this.paymentForm, this.messageObject, 4, this.requestedInfo, this.shippingOption, this.paymentJson, this.cardName, this.validateRequest, this.saveCardInfo, this.androidPayCredentials), true);
            }
        }
    }

    private void init(TLRPC$TL_payments_paymentForm tLRPC$TL_payments_paymentForm, MessageObject messageObject, int i, TLRPC$TL_payments_validatedRequestedInfo tLRPC$TL_payments_validatedRequestedInfo, TLRPC$TL_shippingOption tLRPC$TL_shippingOption, String str, String str2, TLRPC$TL_payments_validateRequestedInfo tLRPC$TL_payments_validateRequestedInfo, boolean z, TLRPC$TL_inputPaymentCredentialsAndroidPay tLRPC$TL_inputPaymentCredentialsAndroidPay) {
        boolean z2 = true;
        this.currentStep = i;
        this.paymentJson = str;
        this.androidPayCredentials = tLRPC$TL_inputPaymentCredentialsAndroidPay;
        this.requestedInfo = tLRPC$TL_payments_validatedRequestedInfo;
        this.paymentForm = tLRPC$TL_payments_paymentForm;
        this.shippingOption = tLRPC$TL_shippingOption;
        this.messageObject = messageObject;
        this.saveCardInfo = z;
        this.isWebView = !"stripe".equals(this.paymentForm.native_provider);
        this.botUser = MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_payments_paymentForm.bot_id));
        if (this.botUser != null) {
            this.currentBotName = this.botUser.first_name;
        } else {
            this.currentBotName = TtmlNode.ANONYMOUS_REGION_ID;
        }
        this.currentItemName = messageObject.messageOwner.media.title;
        this.validateRequest = tLRPC$TL_payments_validateRequestedInfo;
        this.saveShippingInfo = true;
        if (z) {
            this.saveCardInfo = z;
        } else {
            if (this.paymentForm.saved_credentials == null) {
                z2 = false;
            }
            this.saveCardInfo = z2;
        }
        if (str2 != null) {
            this.cardName = str2;
        } else if (tLRPC$TL_payments_paymentForm.saved_credentials != null) {
            this.cardName = tLRPC$TL_payments_paymentForm.saved_credentials.title;
        }
    }

    private void initAndroidPay(Context context) {
        if (VERSION.SDK_INT >= 19) {
            this.googleApiClient = new Builder(context).addConnectionCallbacks(new ConnectionCallbacks() {
                public void onConnected(Bundle bundle) {
                }

                public void onConnectionSuspended(int i) {
                }
            }).addOnConnectionFailedListener(new OnConnectionFailedListener() {
                public void onConnectionFailed(ConnectionResult connectionResult) {
                }
            }).addApi(Wallet.API, new WalletOptions.Builder().setEnvironment(this.paymentForm.invoice.test ? 3 : 1).setTheme(1).build()).build();
            Wallet.Payments.isReadyToPay(this.googleApiClient).setResultCallback(new ResultCallback<BooleanResult>() {
                public void onResult(BooleanResult booleanResult) {
                    if (booleanResult.getStatus().isSuccess() && booleanResult.getValue()) {
                        PaymentFormActivity.this.showAndroidPay();
                    }
                }
            });
            this.googleApiClient.connect();
        }
    }

    private void loadPasswordInfo() {
        if (!this.loadingPasswordInfo) {
            this.loadingPasswordInfo = true;
            ConnectionsManager.getInstance().sendRequest(new TL_account_getPassword(), new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {

                        /* renamed from: org.telegram.ui.PaymentFormActivity$26$1$1 */
                        class C49981 implements Runnable {
                            C49981() {
                            }

                            public void run() {
                                if (PaymentFormActivity.this.shortPollRunnable != null) {
                                    PaymentFormActivity.this.loadPasswordInfo();
                                    PaymentFormActivity.this.shortPollRunnable = null;
                                }
                            }
                        }

                        public void run() {
                            PaymentFormActivity.this.loadingPasswordInfo = false;
                            if (tLRPC$TL_error == null) {
                                PaymentFormActivity.this.currentPassword = (TLRPC$account_Password) tLObject;
                                if (PaymentFormActivity.this.paymentForm != null && (PaymentFormActivity.this.currentPassword instanceof TL_account_password)) {
                                    PaymentFormActivity.this.paymentForm.password_missing = false;
                                    PaymentFormActivity.this.paymentForm.can_save_credentials = true;
                                    PaymentFormActivity.this.updateSavePaymentField();
                                }
                                Object obj = new byte[(PaymentFormActivity.this.currentPassword.new_salt.length + 8)];
                                Utilities.random.nextBytes(obj);
                                System.arraycopy(PaymentFormActivity.this.currentPassword.new_salt, 0, obj, 0, PaymentFormActivity.this.currentPassword.new_salt.length);
                                PaymentFormActivity.this.currentPassword.new_salt = obj;
                                if (PaymentFormActivity.this.passwordFragment != null) {
                                    PaymentFormActivity.this.passwordFragment.setCurrentPassword(PaymentFormActivity.this.currentPassword);
                                }
                            }
                            if ((tLObject instanceof TL_account_noPassword) && PaymentFormActivity.this.shortPollRunnable == null) {
                                PaymentFormActivity.this.shortPollRunnable = new C49981();
                                AndroidUtilities.runOnUIThread(PaymentFormActivity.this.shortPollRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                            }
                        }
                    });
                }
            }, 10);
        }
    }

    private boolean sendCardData() {
        Integer parseInt;
        Integer parseInt2;
        String[] split = this.inputFields[1].getText().toString().split("/");
        if (split.length == 2) {
            parseInt = Utilities.parseInt(split[0]);
            parseInt2 = Utilities.parseInt(split[1]);
        } else {
            parseInt = null;
            parseInt2 = null;
        }
        C1977a c1977a = new C1977a(this.inputFields[0].getText().toString(), parseInt, parseInt2, this.inputFields[3].getText().toString(), this.inputFields[2].getText().toString(), null, null, null, null, this.inputFields[5].getText().toString(), this.inputFields[4].getText().toString(), null);
        this.cardName = c1977a.s() + " *" + c1977a.r();
        if (!c1977a.a()) {
            shakeField(0);
            return false;
        } else if (!c1977a.d() || !c1977a.e() || !c1977a.b()) {
            shakeField(1);
            return false;
        } else if (this.need_card_name && this.inputFields[2].length() == 0) {
            shakeField(2);
            return false;
        } else if (!c1977a.c()) {
            shakeField(3);
            return false;
        } else if (this.need_card_country && this.inputFields[4].length() == 0) {
            shakeField(4);
            return false;
        } else if (this.need_card_postcode && this.inputFields[5].length() == 0) {
            shakeField(5);
            return false;
        } else {
            showEditDoneProgress(true, true);
            try {
                new C1976a(this.stripeApiKey).a(c1977a, new C1979b() {

                    /* renamed from: org.telegram.ui.PaymentFormActivity$33$1 */
                    class C50041 implements Runnable {
                        C50041() {
                        }

                        public void run() {
                            PaymentFormActivity.this.goToNextStep();
                            PaymentFormActivity.this.showEditDoneProgress(true, false);
                            PaymentFormActivity.this.setDonePressed(false);
                        }
                    }

                    public void onError(Exception exception) {
                        if (!PaymentFormActivity.this.canceled) {
                            PaymentFormActivity.this.showEditDoneProgress(true, false);
                            PaymentFormActivity.this.setDonePressed(false);
                            if ((exception instanceof C1969a) || (exception instanceof C1970b)) {
                                AlertsCreator.showSimpleToast(PaymentFormActivity.this, LocaleController.getString("PaymentConnectionFailed", R.string.PaymentConnectionFailed));
                            } else {
                                AlertsCreator.showSimpleToast(PaymentFormActivity.this, exception.getMessage());
                            }
                        }
                    }

                    public void onSuccess(C1978b c1978b) {
                        if (!PaymentFormActivity.this.canceled) {
                            PaymentFormActivity.this.paymentJson = String.format(Locale.US, "{\"type\":\"%1$s\", \"id\":\"%2$s\"}", new Object[]{c1978b.b(), c1978b.a()});
                            AndroidUtilities.runOnUIThread(new C50041());
                        }
                    }
                });
            } catch (Throwable e) {
                FileLog.e(e);
            }
            return true;
        }
    }

    private void sendData() {
        if (!this.canceled) {
            showEditDoneProgress(false, true);
            final TLObject tLRPC$TL_payments_sendPaymentForm = new TLRPC$TL_payments_sendPaymentForm();
            tLRPC$TL_payments_sendPaymentForm.msg_id = this.messageObject.getId();
            if (UserConfig.tmpPassword != null && this.paymentForm.saved_credentials != null) {
                tLRPC$TL_payments_sendPaymentForm.credentials = new TLRPC$TL_inputPaymentCredentialsSaved();
                tLRPC$TL_payments_sendPaymentForm.credentials.id = this.paymentForm.saved_credentials.id;
                tLRPC$TL_payments_sendPaymentForm.credentials.tmp_password = UserConfig.tmpPassword.tmp_password;
            } else if (this.androidPayCredentials != null) {
                tLRPC$TL_payments_sendPaymentForm.credentials = this.androidPayCredentials;
            } else {
                tLRPC$TL_payments_sendPaymentForm.credentials = new TLRPC$TL_inputPaymentCredentials();
                tLRPC$TL_payments_sendPaymentForm.credentials.save = this.saveCardInfo;
                tLRPC$TL_payments_sendPaymentForm.credentials.data = new TLRPC$TL_dataJSON();
                tLRPC$TL_payments_sendPaymentForm.credentials.data.data = this.paymentJson;
            }
            if (!(this.requestedInfo == null || this.requestedInfo.id == null)) {
                tLRPC$TL_payments_sendPaymentForm.requested_info_id = this.requestedInfo.id;
                tLRPC$TL_payments_sendPaymentForm.flags |= 1;
            }
            if (this.shippingOption != null) {
                tLRPC$TL_payments_sendPaymentForm.shipping_option_id = this.shippingOption.id;
                tLRPC$TL_payments_sendPaymentForm.flags |= 2;
            }
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_payments_sendPaymentForm, new RequestDelegate() {

                /* renamed from: org.telegram.ui.PaymentFormActivity$35$1 */
                class C50081 implements Runnable {
                    C50081() {
                    }

                    public void run() {
                        PaymentFormActivity.this.goToNextStep();
                    }
                }

                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    if (tLObject == null) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                AlertsCreator.processError(tLRPC$TL_error, PaymentFormActivity.this, tLRPC$TL_payments_sendPaymentForm, new Object[0]);
                                PaymentFormActivity.this.setDonePressed(false);
                                PaymentFormActivity.this.showEditDoneProgress(false, false);
                            }
                        });
                    } else if (tLObject instanceof TLRPC$TL_payments_paymentResult) {
                        MessagesController.getInstance().processUpdates(((TLRPC$TL_payments_paymentResult) tLObject).updates, false);
                        AndroidUtilities.runOnUIThread(new C50081());
                    } else if (tLObject instanceof TLRPC$TL_payments_paymentVerficationNeeded) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.paymentFinished, new Object[0]);
                                PaymentFormActivity.this.setDonePressed(false);
                                PaymentFormActivity.this.webView.setVisibility(0);
                                PaymentFormActivity.this.webviewLoading = true;
                                PaymentFormActivity.this.showEditDoneProgress(true, true);
                                PaymentFormActivity.this.progressView.setVisibility(0);
                                PaymentFormActivity.this.doneItem.setEnabled(false);
                                PaymentFormActivity.this.doneItem.getImageView().setVisibility(4);
                                PaymentFormActivity.this.webView.loadUrl(((TLRPC$TL_payments_paymentVerficationNeeded) tLObject).url);
                            }
                        });
                    }
                }
            }, 2);
        }
    }

    private void sendForm() {
        if (!this.canceled) {
            TLRPC$TL_paymentRequestedInfo tLRPC$TL_paymentRequestedInfo;
            showEditDoneProgress(true, true);
            this.validateRequest = new TLRPC$TL_payments_validateRequestedInfo();
            this.validateRequest.save = this.saveShippingInfo;
            this.validateRequest.msg_id = this.messageObject.getId();
            this.validateRequest.info = new TLRPC$TL_paymentRequestedInfo();
            if (this.paymentForm.invoice.name_requested) {
                this.validateRequest.info.name = this.inputFields[6].getText().toString();
                tLRPC$TL_paymentRequestedInfo = this.validateRequest.info;
                tLRPC$TL_paymentRequestedInfo.flags |= 1;
            }
            if (this.paymentForm.invoice.phone_requested) {
                this.validateRequest.info.phone = "+" + this.inputFields[8].getText().toString() + this.inputFields[9].getText().toString();
                tLRPC$TL_paymentRequestedInfo = this.validateRequest.info;
                tLRPC$TL_paymentRequestedInfo.flags |= 2;
            }
            if (this.paymentForm.invoice.email_requested) {
                this.validateRequest.info.email = this.inputFields[7].getText().toString().trim();
                tLRPC$TL_paymentRequestedInfo = this.validateRequest.info;
                tLRPC$TL_paymentRequestedInfo.flags |= 4;
            }
            if (this.paymentForm.invoice.shipping_address_requested) {
                this.validateRequest.info.shipping_address = new TLRPC$TL_postAddress();
                this.validateRequest.info.shipping_address.street_line1 = this.inputFields[0].getText().toString();
                this.validateRequest.info.shipping_address.street_line2 = this.inputFields[1].getText().toString();
                this.validateRequest.info.shipping_address.city = this.inputFields[2].getText().toString();
                this.validateRequest.info.shipping_address.state = this.inputFields[3].getText().toString();
                this.validateRequest.info.shipping_address.country_iso2 = this.countryName != null ? this.countryName : TtmlNode.ANONYMOUS_REGION_ID;
                this.validateRequest.info.shipping_address.post_code = this.inputFields[5].getText().toString();
                tLRPC$TL_paymentRequestedInfo = this.validateRequest.info;
                tLRPC$TL_paymentRequestedInfo.flags |= 8;
            }
            final TLObject tLObject = this.validateRequest;
            ConnectionsManager.getInstance().sendRequest(this.validateRequest, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    if (tLObject instanceof TLRPC$TL_payments_validatedRequestedInfo) {
                        AndroidUtilities.runOnUIThread(new Runnable() {

                            /* renamed from: org.telegram.ui.PaymentFormActivity$34$1$1 */
                            class C50051 implements RequestDelegate {
                                C50051() {
                                }

                                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                }
                            }

                            public void run() {
                                PaymentFormActivity.this.requestedInfo = (TLRPC$TL_payments_validatedRequestedInfo) tLObject;
                                if (!(PaymentFormActivity.this.paymentForm.saved_info == null || PaymentFormActivity.this.saveShippingInfo)) {
                                    TLObject tLRPC$TL_payments_clearSavedInfo = new TLRPC$TL_payments_clearSavedInfo();
                                    tLRPC$TL_payments_clearSavedInfo.info = true;
                                    ConnectionsManager.getInstance().sendRequest(tLRPC$TL_payments_clearSavedInfo, new C50051());
                                }
                                PaymentFormActivity.this.goToNextStep();
                                PaymentFormActivity.this.setDonePressed(false);
                                PaymentFormActivity.this.showEditDoneProgress(true, false);
                            }
                        });
                    } else {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                PaymentFormActivity.this.setDonePressed(false);
                                PaymentFormActivity.this.showEditDoneProgress(true, false);
                                if (tLRPC$TL_error != null) {
                                    String str = tLRPC$TL_error.text;
                                    boolean z = true;
                                    switch (str.hashCode()) {
                                        case -2092780146:
                                            if (str.equals("ADDRESS_CITY_INVALID")) {
                                                z = true;
                                                break;
                                            }
                                            break;
                                        case -1623547228:
                                            if (str.equals("ADDRESS_STREET_LINE1_INVALID")) {
                                                z = true;
                                                break;
                                            }
                                            break;
                                        case -1224177757:
                                            if (str.equals("ADDRESS_COUNTRY_INVALID")) {
                                                z = true;
                                                break;
                                            }
                                            break;
                                        case -1031752045:
                                            if (str.equals("REQ_INFO_NAME_INVALID")) {
                                                z = false;
                                                break;
                                            }
                                            break;
                                        case -274035920:
                                            if (str.equals("ADDRESS_POSTCODE_INVALID")) {
                                                z = true;
                                                break;
                                            }
                                            break;
                                        case 417441502:
                                            if (str.equals("ADDRESS_STATE_INVALID")) {
                                                z = true;
                                                break;
                                            }
                                            break;
                                        case 708423542:
                                            if (str.equals("REQ_INFO_PHONE_INVALID")) {
                                                z = true;
                                                break;
                                            }
                                            break;
                                        case 863965605:
                                            if (str.equals("ADDRESS_STREET_LINE2_INVALID")) {
                                                z = true;
                                                break;
                                            }
                                            break;
                                        case 889106340:
                                            if (str.equals("REQ_INFO_EMAIL_INVALID")) {
                                                z = true;
                                                break;
                                            }
                                            break;
                                    }
                                    switch (z) {
                                        case false:
                                            PaymentFormActivity.this.shakeField(6);
                                            return;
                                        case true:
                                            PaymentFormActivity.this.shakeField(9);
                                            return;
                                        case true:
                                            PaymentFormActivity.this.shakeField(7);
                                            return;
                                        case true:
                                            PaymentFormActivity.this.shakeField(4);
                                            return;
                                        case true:
                                            PaymentFormActivity.this.shakeField(2);
                                            return;
                                        case true:
                                            PaymentFormActivity.this.shakeField(5);
                                            return;
                                        case true:
                                            PaymentFormActivity.this.shakeField(3);
                                            return;
                                        case true:
                                            PaymentFormActivity.this.shakeField(0);
                                            return;
                                        case true:
                                            PaymentFormActivity.this.shakeField(1);
                                            return;
                                        default:
                                            AlertsCreator.processError(tLRPC$TL_error, PaymentFormActivity.this, tLObject, new Object[0]);
                                            return;
                                    }
                                }
                            }
                        });
                    }
                }
            }, 2);
        }
    }

    private void sendSavePassword(final boolean z) {
        String str;
        Object obj = null;
        TLObject tL_account_updatePasswordSettings = new TL_account_updatePasswordSettings();
        if (z) {
            this.doneItem.setVisibility(0);
            tL_account_updatePasswordSettings.new_settings = new TL_account_passwordInputSettings();
            tL_account_updatePasswordSettings.new_settings.flags = 2;
            tL_account_updatePasswordSettings.new_settings.email = TtmlNode.ANONYMOUS_REGION_ID;
            tL_account_updatePasswordSettings.current_password_hash = new byte[0];
        } else {
            String obj2 = this.inputFields[0].getText().toString();
            if (TextUtils.isEmpty(obj2)) {
                shakeField(0);
                return;
            } else if (obj2.equals(this.inputFields[1].getText().toString())) {
                String obj3 = this.inputFields[2].getText().toString();
                if (obj3.length() < 3) {
                    shakeField(2);
                    return;
                }
                int lastIndexOf = obj3.lastIndexOf(46);
                int lastIndexOf2 = obj3.lastIndexOf(64);
                if (lastIndexOf < 0 || lastIndexOf2 < 0 || lastIndexOf < lastIndexOf2) {
                    shakeField(2);
                    return;
                }
                tL_account_updatePasswordSettings.current_password_hash = new byte[0];
                tL_account_updatePasswordSettings.new_settings = new TL_account_passwordInputSettings();
                try {
                    obj = obj2.getBytes(C3446C.UTF8_NAME);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                Object obj4 = this.currentPassword.new_salt;
                Object obj5 = new byte[((obj4.length * 2) + obj.length)];
                System.arraycopy(obj4, 0, obj5, 0, obj4.length);
                System.arraycopy(obj, 0, obj5, obj4.length, obj.length);
                System.arraycopy(obj4, 0, obj5, obj5.length - obj4.length, obj4.length);
                TL_account_passwordInputSettings tL_account_passwordInputSettings = tL_account_updatePasswordSettings.new_settings;
                tL_account_passwordInputSettings.flags |= 1;
                tL_account_updatePasswordSettings.new_settings.hint = TtmlNode.ANONYMOUS_REGION_ID;
                tL_account_updatePasswordSettings.new_settings.new_password_hash = Utilities.computeSHA256(obj5, 0, obj5.length);
                tL_account_updatePasswordSettings.new_settings.new_salt = obj4;
                if (obj3.length() > 0) {
                    tL_account_passwordInputSettings = tL_account_updatePasswordSettings.new_settings;
                    tL_account_passwordInputSettings.flags |= 2;
                    tL_account_updatePasswordSettings.new_settings.email = obj3.trim();
                }
                str = obj3;
            } else {
                try {
                    Toast.makeText(getParentActivity(), LocaleController.getString("PasswordDoNotMatch", R.string.PasswordDoNotMatch), 0).show();
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
                shakeField(1);
                return;
            }
        }
        showEditDoneProgress(true, true);
        ConnectionsManager.getInstance().sendRequest(tL_account_updatePasswordSettings, new RequestDelegate() {
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {

                    /* renamed from: org.telegram.ui.PaymentFormActivity$32$1$1 */
                    class C50021 implements DialogInterface.OnClickListener {
                        C50021() {
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            PaymentFormActivity.this.waitingForEmail = true;
                            PaymentFormActivity.this.currentPassword.email_unconfirmed_pattern = str;
                            PaymentFormActivity.this.updatePasswordFields();
                        }
                    }

                    public void run() {
                        PaymentFormActivity.this.showEditDoneProgress(true, false);
                        if (z) {
                            PaymentFormActivity.this.currentPassword = new TL_account_noPassword();
                            PaymentFormActivity.this.delegate.currentPasswordUpdated(PaymentFormActivity.this.currentPassword);
                            PaymentFormActivity.this.finishFragment();
                        } else if (tLRPC$TL_error == null && (tLObject instanceof TL_boolTrue)) {
                            if (PaymentFormActivity.this.getParentActivity() != null) {
                                PaymentFormActivity.this.goToNextStep();
                            }
                        } else if (tLRPC$TL_error == null) {
                        } else {
                            if (tLRPC$TL_error.text.equals("EMAIL_UNCONFIRMED")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentFormActivity.this.getParentActivity());
                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C50021());
                                builder.setMessage(LocaleController.getString("YourEmailAlmostThereText", R.string.YourEmailAlmostThereText));
                                builder.setTitle(LocaleController.getString("YourEmailAlmostThere", R.string.YourEmailAlmostThere));
                                Dialog showDialog = PaymentFormActivity.this.showDialog(builder.create());
                                if (showDialog != null) {
                                    showDialog.setCanceledOnTouchOutside(false);
                                    showDialog.setCancelable(false);
                                }
                            } else if (tLRPC$TL_error.text.equals("EMAIL_INVALID")) {
                                PaymentFormActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("PasswordEmailInvalid", R.string.PasswordEmailInvalid));
                            } else if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                                int intValue = Utilities.parseInt(tLRPC$TL_error.text).intValue();
                                String formatPluralString = intValue < 60 ? LocaleController.formatPluralString("Seconds", intValue) : LocaleController.formatPluralString("Minutes", intValue / 60);
                                PaymentFormActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{formatPluralString}));
                            } else {
                                PaymentFormActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), tLRPC$TL_error.text);
                            }
                        }
                    }
                });
            }
        }, 10);
    }

    private void setCurrentPassword(TLRPC$account_Password tLRPC$account_Password) {
        if (!(tLRPC$account_Password instanceof TL_account_password)) {
            this.currentPassword = tLRPC$account_Password;
            if (this.currentPassword != null) {
                this.waitingForEmail = this.currentPassword.email_unconfirmed_pattern.length() > 0;
            }
            updatePasswordFields();
        } else if (getParentActivity() != null) {
            goToNextStep();
        }
    }

    private void setDelegate(PaymentFormActivityDelegate paymentFormActivityDelegate) {
        this.delegate = paymentFormActivityDelegate;
    }

    private void setDonePressed(boolean z) {
        boolean z2 = true;
        this.donePressed = z;
        this.swipeBackEnabled = !z;
        this.actionBar.getBackButton().setEnabled(!this.donePressed);
        if (this.detailSettingsCell[0] != null) {
            TextDetailSettingsCell textDetailSettingsCell = this.detailSettingsCell[0];
            if (this.donePressed) {
                z2 = false;
            }
            textDetailSettingsCell.setEnabled(z2);
        }
    }

    private void shakeField(int i) {
        Vibrator vibrator = (Vibrator) getParentActivity().getSystemService("vibrator");
        if (vibrator != null) {
            vibrator.vibrate(200);
        }
        AndroidUtilities.shakeView(this.inputFields[i], 2.0f, 0);
    }

    private void showAlertWithText(String str, String str2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
        builder.setTitle(str);
        builder.setMessage(str2);
        showDialog(builder.create());
    }

    private void showAndroidPay() {
        if (getParentActivity() != null && this.androidPayContainer != null) {
            WalletFragmentStyle buyButtonWidth;
            WalletFragmentOptions.Builder newBuilder = WalletFragmentOptions.newBuilder();
            newBuilder.setEnvironment(this.paymentForm.invoice.test ? 3 : 1);
            newBuilder.setMode(1);
            if (this.androidPayPublicKey != null) {
                this.androidPayContainer.setBackgroundColor(this.androidPayBackgroundColor);
                buyButtonWidth = new WalletFragmentStyle().setBuyButtonText(5).setBuyButtonAppearance(this.androidPayBlackTheme ? 6 : 4).setBuyButtonWidth(-1);
            } else {
                buyButtonWidth = new WalletFragmentStyle().setBuyButtonText(6).setBuyButtonAppearance(6).setBuyButtonWidth(-2);
            }
            newBuilder.setFragmentStyle(buyButtonWidth);
            Fragment newInstance = WalletFragment.newInstance(newBuilder.build());
            FragmentTransaction beginTransaction = getParentActivity().getFragmentManager().beginTransaction();
            beginTransaction.replace(4000, newInstance);
            beginTransaction.commit();
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.paymentForm.invoice.prices);
            if (this.shippingOption != null) {
                arrayList.addAll(this.shippingOption.prices);
            }
            this.totalPriceDecimal = getTotalPriceDecimalString(arrayList);
            newInstance.initialize(WalletFragmentInitParams.newBuilder().setMaskedWalletRequest(MaskedWalletRequest.newBuilder().setPaymentMethodTokenizationParameters(this.androidPayPublicKey != null ? PaymentMethodTokenizationParameters.newBuilder().setPaymentMethodTokenizationType(2).addParameter("publicKey", this.androidPayPublicKey).build() : PaymentMethodTokenizationParameters.newBuilder().setPaymentMethodTokenizationType(1).addParameter("gateway", "stripe").addParameter("stripe:publishableKey", this.stripeApiKey).addParameter("stripe:version", "3.5.0").build()).setEstimatedTotalPrice(this.totalPriceDecimal).setCurrencyCode(this.paymentForm.invoice.currency).build()).setMaskedWalletRequestCode(1000).build());
            this.androidPayContainer.setVisibility(0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.androidPayContainer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.setDuration(180);
            animatorSet.start();
        }
    }

    private void showEditDoneProgress(boolean z, final boolean z2) {
        if (this.doneItemAnimation != null) {
            this.doneItemAnimation.cancel();
        }
        AnimatorSet animatorSet;
        Animator[] animatorArr;
        if (z && this.doneItem != null) {
            this.doneItemAnimation = new AnimatorSet();
            if (z2) {
                this.progressView.setVisibility(0);
                this.doneItem.setEnabled(false);
                animatorSet = this.doneItemAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[3] = ObjectAnimator.ofFloat(this.progressView, "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.progressView, "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.progressView, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            } else if (this.webView != null) {
                animatorSet = this.doneItemAnimation;
                animatorArr = new Animator[3];
                animatorArr[0] = ObjectAnimator.ofFloat(this.progressView, "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.progressView, "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.progressView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorSet.playTogether(animatorArr);
            } else {
                this.doneItem.getImageView().setVisibility(0);
                this.doneItem.setEnabled(true);
                animatorSet = this.doneItemAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.progressView, "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.progressView, "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.progressView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[3] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            }
            this.doneItemAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    if (PaymentFormActivity.this.doneItemAnimation != null && PaymentFormActivity.this.doneItemAnimation.equals(animator)) {
                        PaymentFormActivity.this.doneItemAnimation = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (PaymentFormActivity.this.doneItemAnimation != null && PaymentFormActivity.this.doneItemAnimation.equals(animator)) {
                        if (z2) {
                            PaymentFormActivity.this.doneItem.getImageView().setVisibility(4);
                        } else {
                            PaymentFormActivity.this.progressView.setVisibility(4);
                        }
                    }
                }
            });
            this.doneItemAnimation.setDuration(150);
            this.doneItemAnimation.start();
        } else if (this.payTextView != null) {
            this.doneItemAnimation = new AnimatorSet();
            if (z2) {
                this.progressViewButton.setVisibility(0);
                this.bottomLayout.setEnabled(false);
                animatorSet = this.doneItemAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.payTextView, "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.payTextView, "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.payTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[3] = ObjectAnimator.ofFloat(this.progressViewButton, "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.progressViewButton, "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.progressViewButton, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            } else {
                this.payTextView.setVisibility(0);
                this.bottomLayout.setEnabled(true);
                animatorSet = this.doneItemAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.progressViewButton, "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.progressViewButton, "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.progressViewButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[3] = ObjectAnimator.ofFloat(this.payTextView, "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.payTextView, "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.payTextView, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            }
            this.doneItemAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    if (PaymentFormActivity.this.doneItemAnimation != null && PaymentFormActivity.this.doneItemAnimation.equals(animator)) {
                        PaymentFormActivity.this.doneItemAnimation = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (PaymentFormActivity.this.doneItemAnimation != null && PaymentFormActivity.this.doneItemAnimation.equals(animator)) {
                        if (z2) {
                            PaymentFormActivity.this.payTextView.setVisibility(4);
                        } else {
                            PaymentFormActivity.this.progressViewButton.setVisibility(4);
                        }
                    }
                }
            });
            this.doneItemAnimation.setDuration(150);
            this.doneItemAnimation.start();
        }
    }

    private void showPayAlert(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        builder.setTitle(LocaleController.getString("PaymentTransactionReview", R.string.PaymentTransactionReview));
        builder.setMessage(LocaleController.formatString("PaymentTransactionMessage", R.string.PaymentTransactionMessage, new Object[]{str, this.currentBotName, this.currentItemName}));
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                PaymentFormActivity.this.setDonePressed(true);
                PaymentFormActivity.this.sendData();
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        showDialog(builder.create());
    }

    private void updatePasswordFields() {
        int i = 0;
        if (this.currentStep == 6 && this.bottomCell[2] != null) {
            int i2;
            if (this.currentPassword == null) {
                this.doneItem.setVisibility(0);
                showEditDoneProgress(true, true);
                this.bottomCell[2].setVisibility(8);
                this.settingsCell1.setVisibility(8);
                this.headerCell[0].setVisibility(8);
                this.headerCell[1].setVisibility(8);
                this.bottomCell[0].setVisibility(8);
                for (i2 = 0; i2 < 3; i2++) {
                    ((View) this.inputFields[i2].getParent()).setVisibility(8);
                }
                while (i < this.dividers.size()) {
                    ((View) this.dividers.get(i)).setVisibility(8);
                    i++;
                }
                return;
            }
            showEditDoneProgress(true, false);
            if (this.waitingForEmail) {
                if (getParentActivity() != null) {
                    AndroidUtilities.hideKeyboard(getParentActivity().getCurrentFocus());
                }
                this.doneItem.setVisibility(8);
                this.bottomCell[2].setText(LocaleController.formatString("EmailPasswordConfirmText", R.string.EmailPasswordConfirmText, new Object[]{this.currentPassword.email_unconfirmed_pattern}));
                this.bottomCell[2].setVisibility(0);
                this.settingsCell1.setVisibility(0);
                this.bottomCell[1].setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.headerCell[0].setVisibility(8);
                this.headerCell[1].setVisibility(8);
                this.bottomCell[0].setVisibility(8);
                for (i2 = 0; i2 < 3; i2++) {
                    ((View) this.inputFields[i2].getParent()).setVisibility(8);
                }
                while (i < this.dividers.size()) {
                    ((View) this.dividers.get(i)).setVisibility(8);
                    i++;
                }
                return;
            }
            this.doneItem.setVisibility(0);
            this.bottomCell[2].setVisibility(8);
            this.settingsCell1.setVisibility(8);
            this.bottomCell[1].setText(LocaleController.getString("PaymentPasswordEmailInfo", R.string.PaymentPasswordEmailInfo));
            this.headerCell[0].setVisibility(0);
            this.headerCell[1].setVisibility(0);
            this.bottomCell[0].setVisibility(0);
            for (i2 = 0; i2 < 3; i2++) {
                ((View) this.inputFields[i2].getParent()).setVisibility(0);
            }
            for (i2 = 0; i2 < this.dividers.size(); i2++) {
                ((View) this.dividers.get(i2)).setVisibility(0);
            }
        }
    }

    private void updateSavePaymentField() {
        if (this.bottomCell[0] != null && this.sectionCell[2] != null) {
            if ((this.paymentForm.password_missing || this.paymentForm.can_save_credentials) && (this.webView == null || !(this.webView == null || this.webviewLoading))) {
                CharSequence spannableStringBuilder = new SpannableStringBuilder(LocaleController.getString("PaymentCardSavePaymentInformationInfoLine1", R.string.PaymentCardSavePaymentInformationInfoLine1));
                if (this.paymentForm.password_missing) {
                    loadPasswordInfo();
                    spannableStringBuilder.append("\n");
                    int length = spannableStringBuilder.length();
                    CharSequence string = LocaleController.getString("PaymentCardSavePaymentInformationInfoLine2", R.string.PaymentCardSavePaymentInformationInfoLine2);
                    int indexOf = string.indexOf(42);
                    int lastIndexOf = string.lastIndexOf(42);
                    spannableStringBuilder.append(string);
                    if (!(indexOf == -1 || lastIndexOf == -1)) {
                        int i = indexOf + length;
                        length += lastIndexOf;
                        this.bottomCell[0].getTextView().setMovementMethod(new LinkMovementMethodMy());
                        spannableStringBuilder.replace(length, length + 1, TtmlNode.ANONYMOUS_REGION_ID);
                        spannableStringBuilder.replace(i, i + 1, TtmlNode.ANONYMOUS_REGION_ID);
                        spannableStringBuilder.setSpan(new LinkSpan(), i, length - 1, 33);
                    }
                }
                this.checkCell1.setEnabled(true);
                this.bottomCell[0].setText(spannableStringBuilder);
                this.checkCell1.setVisibility(0);
                this.bottomCell[0].setVisibility(0);
                this.sectionCell[2].setBackgroundDrawable(Theme.getThemedDrawable(this.sectionCell[2].getContext(), R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                return;
            }
            this.checkCell1.setVisibility(8);
            this.bottomCell[0].setVisibility(8);
            this.sectionCell[2].setBackgroundDrawable(Theme.getThemedDrawable(this.sectionCell[2].getContext(), R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.annotation.SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    public android.view.View createView(android.content.Context r13) {
        /*
        r12 = this;
        r0 = r12.currentStep;
        if (r0 != 0) goto L_0x0348;
    L_0x0004:
        r0 = r12.actionBar;
        r1 = "PaymentShippingInfo";
        r2 = 2131232114; // 0x7f080572 float:1.8080328E38 double:1.052968571E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setTitle(r1);
    L_0x0013:
        r0 = r12.actionBar;
        r1 = 2130837866; // 0x7f02016a float:1.7280698E38 double:1.0527737864E-314;
        r0.setBackButtonImage(r1);
        r0 = r12.actionBar;
        r1 = 1;
        r0.setAllowOverlayTitle(r1);
        r0 = r12.actionBar;
        r1 = new org.telegram.ui.PaymentFormActivity$1;
        r1.<init>();
        r0.setActionBarMenuOnItemClick(r1);
        r0 = r12.actionBar;
        r0 = r0.createMenu();
        r1 = r12.currentStep;
        if (r1 == 0) goto L_0x004e;
    L_0x0035:
        r1 = r12.currentStep;
        r2 = 1;
        if (r1 == r2) goto L_0x004e;
    L_0x003a:
        r1 = r12.currentStep;
        r2 = 2;
        if (r1 == r2) goto L_0x004e;
    L_0x003f:
        r1 = r12.currentStep;
        r2 = 3;
        if (r1 == r2) goto L_0x004e;
    L_0x0044:
        r1 = r12.currentStep;
        r2 = 4;
        if (r1 == r2) goto L_0x004e;
    L_0x0049:
        r1 = r12.currentStep;
        r2 = 6;
        if (r1 != r2) goto L_0x007a;
    L_0x004e:
        r1 = 1;
        r2 = 2130837914; // 0x7f02019a float:1.7280796E38 double:1.05277381E-314;
        r3 = 1113587712; // 0x42600000 float:56.0 double:5.50185432E-315;
        r3 = org.telegram.messenger.AndroidUtilities.dp(r3);
        r0 = r0.addItemWithWidth(r1, r2, r3);
        r12.doneItem = r0;
        r0 = new org.telegram.ui.Components.ContextProgressView;
        r1 = 1;
        r0.<init>(r13, r1);
        r12.progressView = r0;
        r0 = r12.doneItem;
        r1 = r12.progressView;
        r2 = -1;
        r3 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r2 = org.telegram.ui.Components.LayoutHelper.createFrame(r2, r3);
        r0.addView(r1, r2);
        r0 = r12.progressView;
        r1 = 4;
        r0.setVisibility(r1);
    L_0x007a:
        r0 = new android.widget.FrameLayout;
        r0.<init>(r13);
        r12.fragmentView = r0;
        r0 = r12.fragmentView;
        r7 = r0;
        r7 = (android.widget.FrameLayout) r7;
        r0 = r12.fragmentView;
        r1 = "windowBackgroundGray";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r0 = new android.widget.ScrollView;
        r0.<init>(r13);
        r12.scrollView = r0;
        r0 = r12.scrollView;
        r1 = 1;
        r0.setFillViewport(r1);
        r0 = r12.scrollView;
        r1 = "actionBarDefault";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        org.telegram.messenger.AndroidUtilities.setScrollViewEdgeEffectColor(r0, r1);
        r8 = r12.scrollView;
        r0 = -1;
        r1 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r2 = 51;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = r12.currentStep;
        r9 = 4;
        if (r6 != r9) goto L_0x0426;
    L_0x00ba:
        r6 = 1111490560; // 0x42400000 float:48.0 double:5.491493014E-315;
    L_0x00bc:
        r0 = org.telegram.ui.Components.LayoutHelper.createFrame(r0, r1, r2, r3, r4, r5, r6);
        r7.addView(r8, r0);
        r0 = new android.widget.LinearLayout;
        r0.<init>(r13);
        r12.linearLayout2 = r0;
        r0 = r12.linearLayout2;
        r1 = 1;
        r0.setOrientation(r1);
        r0 = r12.scrollView;
        r1 = r12.linearLayout2;
        r2 = new android.widget.FrameLayout$LayoutParams;
        r3 = -1;
        r4 = -2;
        r2.<init>(r3, r4);
        r0.addView(r1, r2);
        r0 = r12.currentStep;
        if (r0 != 0) goto L_0x0a7f;
    L_0x00e2:
        r9 = new java.util.HashMap;
        r9.<init>();
        r10 = new java.util.HashMap;
        r10.<init>();
        r0 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x0153 }
        r1 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x0153 }
        r2 = r13.getResources();	 Catch:{ Exception -> 0x0153 }
        r2 = r2.getAssets();	 Catch:{ Exception -> 0x0153 }
        r3 = "countries.txt";
        r2 = r2.open(r3);	 Catch:{ Exception -> 0x0153 }
        r1.<init>(r2);	 Catch:{ Exception -> 0x0153 }
        r0.<init>(r1);	 Catch:{ Exception -> 0x0153 }
    L_0x0105:
        r1 = r0.readLine();	 Catch:{ Exception -> 0x0153 }
        if (r1 == 0) goto L_0x0429;
    L_0x010b:
        r2 = ";";
        r1 = r1.split(r2);	 Catch:{ Exception -> 0x0153 }
        r2 = r12.countriesArray;	 Catch:{ Exception -> 0x0153 }
        r3 = 0;
        r4 = 2;
        r4 = r1[r4];	 Catch:{ Exception -> 0x0153 }
        r2.add(r3, r4);	 Catch:{ Exception -> 0x0153 }
        r2 = r12.countriesMap;	 Catch:{ Exception -> 0x0153 }
        r3 = 2;
        r3 = r1[r3];	 Catch:{ Exception -> 0x0153 }
        r4 = 0;
        r4 = r1[r4];	 Catch:{ Exception -> 0x0153 }
        r2.put(r3, r4);	 Catch:{ Exception -> 0x0153 }
        r2 = r12.codesMap;	 Catch:{ Exception -> 0x0153 }
        r3 = 0;
        r3 = r1[r3];	 Catch:{ Exception -> 0x0153 }
        r4 = 2;
        r4 = r1[r4];	 Catch:{ Exception -> 0x0153 }
        r2.put(r3, r4);	 Catch:{ Exception -> 0x0153 }
        r2 = 1;
        r2 = r1[r2];	 Catch:{ Exception -> 0x0153 }
        r3 = 2;
        r3 = r1[r3];	 Catch:{ Exception -> 0x0153 }
        r10.put(r2, r3);	 Catch:{ Exception -> 0x0153 }
        r2 = r1.length;	 Catch:{ Exception -> 0x0153 }
        r3 = 3;
        if (r2 <= r3) goto L_0x0149;
    L_0x013e:
        r2 = r12.phoneFormatMap;	 Catch:{ Exception -> 0x0153 }
        r3 = 0;
        r3 = r1[r3];	 Catch:{ Exception -> 0x0153 }
        r4 = 3;
        r4 = r1[r4];	 Catch:{ Exception -> 0x0153 }
        r2.put(r3, r4);	 Catch:{ Exception -> 0x0153 }
    L_0x0149:
        r2 = 1;
        r2 = r1[r2];	 Catch:{ Exception -> 0x0153 }
        r3 = 2;
        r1 = r1[r3];	 Catch:{ Exception -> 0x0153 }
        r9.put(r2, r1);	 Catch:{ Exception -> 0x0153 }
        goto L_0x0105;
    L_0x0153:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
    L_0x0157:
        r0 = r12.countriesArray;
        r1 = new org.telegram.ui.PaymentFormActivity$2;
        r1.<init>();
        java.util.Collections.sort(r0, r1);
        r0 = 10;
        r0 = new org.telegram.ui.Components.EditTextBoldCursor[r0];
        r12.inputFields = r0;
        r0 = 0;
        r8 = r0;
    L_0x0169:
        r0 = 10;
        if (r8 >= r0) goto L_0x0866;
    L_0x016d:
        if (r8 != 0) goto L_0x042e;
    L_0x016f:
        r0 = r12.headerCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.HeaderCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.headerCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = "windowBackgroundWhite";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r0 = r12.headerCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = "PaymentShippingAddress";
        r2 = 2131232108; // 0x7f08056c float:1.8080316E38 double:1.052968568E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setText(r1);
        r0 = r12.linearLayout2;
        r1 = r12.headerCell;
        r2 = 0;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
    L_0x01aa:
        r0 = 8;
        if (r8 != r0) goto L_0x0488;
    L_0x01ae:
        r1 = new android.widget.LinearLayout;
        r1.<init>(r13);
        r0 = r1;
        r0 = (android.widget.LinearLayout) r0;
        r2 = 0;
        r0.setOrientation(r2);
        r0 = r12.linearLayout2;
        r2 = -1;
        r3 = 48;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = "windowBackgroundWhite";
        r0 = org.telegram.ui.ActionBar.Theme.getColor(r0);
        r1.setBackgroundColor(r0);
        r7 = r1;
    L_0x01d1:
        r0 = 9;
        if (r8 != r0) goto L_0x0508;
    L_0x01d5:
        r0 = r12.inputFields;
        r1 = new org.telegram.ui.Components.HintEditText;
        r1.<init>(r13);
        r0[r8] = r1;
    L_0x01de:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = java.lang.Integer.valueOf(r8);
        r0.setTag(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 1;
        r2 = 1098907648; // 0x41800000 float:16.0 double:5.42932517E-315;
        r0.setTextSize(r1, r2);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "windowBackgroundWhiteHintText";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setHintTextColor(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "windowBackgroundWhiteBlackText";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setTextColor(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 0;
        r0.setBackgroundDrawable(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "windowBackgroundWhiteBlackText";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setCursorColor(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r1 = org.telegram.messenger.AndroidUtilities.dp(r1);
        r0.setCursorSize(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 1069547520; // 0x3fc00000 float:1.5 double:5.28426686E-315;
        r0.setCursorWidth(r1);
        r0 = 4;
        if (r8 != r0) goto L_0x0252;
    L_0x023e:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = new org.telegram.ui.PaymentFormActivity$3;
        r1.<init>();
        r0.setOnTouchListener(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 0;
        r0.setInputType(r1);
    L_0x0252:
        r0 = 9;
        if (r8 == r0) goto L_0x025a;
    L_0x0256:
        r0 = 8;
        if (r8 != r0) goto L_0x0513;
    L_0x025a:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 3;
        r0.setInputType(r1);
    L_0x0262:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 268435461; // 0x10000005 float:2.5243564E-29 double:1.326247394E-315;
        r0.setImeOptions(r1);
        switch(r8) {
            case 0: goto L_0x0587;
            case 1: goto L_0x05b7;
            case 2: goto L_0x05e7;
            case 3: goto L_0x0617;
            case 4: goto L_0x0647;
            case 5: goto L_0x068c;
            case 6: goto L_0x052b;
            case 7: goto L_0x0559;
            default: goto L_0x026f;
        };
    L_0x026f:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = r12.inputFields;
        r1 = r1[r8];
        r1 = r1.length();
        r0.setSelection(r1);
        r0 = 8;
        if (r8 != r0) goto L_0x06bc;
    L_0x0282:
        r0 = new android.widget.TextView;
        r0.<init>(r13);
        r12.textView = r0;
        r0 = r12.textView;
        r1 = "+";
        r0.setText(r1);
        r0 = r12.textView;
        r1 = "windowBackgroundWhiteBlackText";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setTextColor(r1);
        r0 = r12.textView;
        r1 = 1;
        r2 = 1098907648; // 0x41800000 float:16.0 double:5.42932517E-315;
        r0.setTextSize(r1, r2);
        r6 = r12.textView;
        r0 = -2;
        r1 = -2;
        r2 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r3 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r4 = 0;
        r5 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r0 = org.telegram.ui.Components.LayoutHelper.createLinear(r0, r1, r2, r3, r4, r5);
        r7.addView(r6, r0);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r1 = org.telegram.messenger.AndroidUtilities.dp(r1);
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r0.setPadding(r1, r2, r3, r4);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 19;
        r0.setGravity(r1);
        r0 = 1;
        r0 = new android.text.InputFilter[r0];
        r1 = 0;
        r2 = new android.text.InputFilter$LengthFilter;
        r3 = 5;
        r2.<init>(r3);
        r0[r1] = r2;
        r1 = r12.inputFields;
        r1 = r1[r8];
        r1.setFilters(r0);
        r0 = r12.inputFields;
        r6 = r0[r8];
        r0 = 55;
        r1 = -2;
        r2 = 0;
        r3 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r4 = 1098907648; // 0x41800000 float:16.0 double:5.42932517E-315;
        r5 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r0 = org.telegram.ui.Components.LayoutHelper.createLinear(r0, r1, r2, r3, r4, r5);
        r7.addView(r6, r0);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = new org.telegram.ui.PaymentFormActivity$4;
        r1.<init>();
        r0.addTextChangedListener(r1);
    L_0x0304:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = new org.telegram.ui.PaymentFormActivity$6;
        r1.<init>();
        r0.setOnEditorActionListener(r1);
        r0 = 9;
        if (r8 != r0) goto L_0x0804;
    L_0x0314:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.email_to_provider;
        if (r0 != 0) goto L_0x0324;
    L_0x031c:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.phone_to_provider;
        if (r0 == 0) goto L_0x084a;
    L_0x0324:
        r1 = 0;
        r0 = 0;
        r2 = r0;
    L_0x0327:
        r0 = r12.paymentForm;
        r0 = r0.users;
        r0 = r0.size();
        if (r2 >= r0) goto L_0x072e;
    L_0x0331:
        r0 = r12.paymentForm;
        r0 = r0.users;
        r0 = r0.get(r2);
        r0 = (org.telegram.tgnet.TLRPC.User) r0;
        r3 = r0.id;
        r4 = r12.paymentForm;
        r4 = r4.provider_id;
        if (r3 != r4) goto L_0x1acd;
    L_0x0343:
        r1 = r2 + 1;
        r2 = r1;
        r1 = r0;
        goto L_0x0327;
    L_0x0348:
        r0 = r12.currentStep;
        r1 = 1;
        if (r0 != r1) goto L_0x035e;
    L_0x034d:
        r0 = r12.actionBar;
        r1 = "PaymentShippingMethod";
        r2 = 2131232115; // 0x7f080573 float:1.808033E38 double:1.0529685713E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setTitle(r1);
        goto L_0x0013;
    L_0x035e:
        r0 = r12.currentStep;
        r1 = 2;
        if (r0 != r1) goto L_0x0374;
    L_0x0363:
        r0 = r12.actionBar;
        r1 = "PaymentCardInfo";
        r2 = 2131232084; // 0x7f080554 float:1.8080267E38 double:1.052968556E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setTitle(r1);
        goto L_0x0013;
    L_0x0374:
        r0 = r12.currentStep;
        r1 = 3;
        if (r0 != r1) goto L_0x038a;
    L_0x0379:
        r0 = r12.actionBar;
        r1 = "PaymentCardInfo";
        r2 = 2131232084; // 0x7f080554 float:1.8080267E38 double:1.052968556E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setTitle(r1);
        goto L_0x0013;
    L_0x038a:
        r0 = r12.currentStep;
        r1 = 4;
        if (r0 != r1) goto L_0x03cd;
    L_0x038f:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.test;
        if (r0 == 0) goto L_0x03bc;
    L_0x0397:
        r0 = r12.actionBar;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Test ";
        r1 = r1.append(r2);
        r2 = "PaymentCheckout";
        r3 = 2131232092; // 0x7f08055c float:1.8080283E38 double:1.05296856E-314;
        r2 = org.telegram.messenger.LocaleController.getString(r2, r3);
        r1 = r1.append(r2);
        r1 = r1.toString();
        r0.setTitle(r1);
        goto L_0x0013;
    L_0x03bc:
        r0 = r12.actionBar;
        r1 = "PaymentCheckout";
        r2 = 2131232092; // 0x7f08055c float:1.8080283E38 double:1.05296856E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setTitle(r1);
        goto L_0x0013;
    L_0x03cd:
        r0 = r12.currentStep;
        r1 = 5;
        if (r0 != r1) goto L_0x0410;
    L_0x03d2:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.test;
        if (r0 == 0) goto L_0x03ff;
    L_0x03da:
        r0 = r12.actionBar;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Test ";
        r1 = r1.append(r2);
        r2 = "PaymentReceipt";
        r3 = 2131232107; // 0x7f08056b float:1.8080314E38 double:1.0529685674E-314;
        r2 = org.telegram.messenger.LocaleController.getString(r2, r3);
        r1 = r1.append(r2);
        r1 = r1.toString();
        r0.setTitle(r1);
        goto L_0x0013;
    L_0x03ff:
        r0 = r12.actionBar;
        r1 = "PaymentReceipt";
        r2 = 2131232107; // 0x7f08056b float:1.8080314E38 double:1.0529685674E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setTitle(r1);
        goto L_0x0013;
    L_0x0410:
        r0 = r12.currentStep;
        r1 = 6;
        if (r0 != r1) goto L_0x0013;
    L_0x0415:
        r0 = r12.actionBar;
        r1 = "PaymentPassword";
        r2 = 2131232915; // 0x7f080893 float:1.8081953E38 double:1.0529689666E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setTitle(r1);
        goto L_0x0013;
    L_0x0426:
        r6 = 0;
        goto L_0x00bc;
    L_0x0429:
        r0.close();	 Catch:{ Exception -> 0x0153 }
        goto L_0x0157;
    L_0x042e:
        r0 = 6;
        if (r8 != r0) goto L_0x01aa;
    L_0x0431:
        r0 = r12.sectionCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.ShadowSectionCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.linearLayout2;
        r1 = r12.sectionCell;
        r2 = 0;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = r12.headerCell;
        r1 = 1;
        r2 = new org.telegram.ui.Cells.HeaderCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.headerCell;
        r1 = 1;
        r0 = r0[r1];
        r1 = "windowBackgroundWhite";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r0 = r12.headerCell;
        r1 = 1;
        r0 = r0[r1];
        r1 = "PaymentShippingReceiver";
        r2 = 2131232118; // 0x7f080576 float:1.8080336E38 double:1.052968573E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setText(r1);
        r0 = r12.linearLayout2;
        r1 = r12.headerCell;
        r2 = 1;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        goto L_0x01aa;
    L_0x0488:
        r0 = 9;
        if (r8 != r0) goto L_0x049b;
    L_0x048c:
        r0 = r12.inputFields;
        r1 = 8;
        r0 = r0[r1];
        r0 = r0.getParent();
        r0 = (android.view.ViewGroup) r0;
        r7 = r0;
        goto L_0x01d1;
    L_0x049b:
        r1 = new android.widget.FrameLayout;
        r1.<init>(r13);
        r0 = r12.linearLayout2;
        r2 = -1;
        r3 = 48;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = "windowBackgroundWhite";
        r0 = org.telegram.ui.ActionBar.Theme.getColor(r0);
        r1.setBackgroundColor(r0);
        r0 = 5;
        if (r8 == r0) goto L_0x04f1;
    L_0x04b9:
        r0 = 9;
        if (r8 == r0) goto L_0x04f1;
    L_0x04bd:
        r0 = 1;
    L_0x04be:
        if (r0 == 0) goto L_0x04cc;
    L_0x04c0:
        r2 = 7;
        if (r8 != r2) goto L_0x04f3;
    L_0x04c3:
        r2 = r12.paymentForm;
        r2 = r2.invoice;
        r2 = r2.phone_requested;
        if (r2 != 0) goto L_0x04f3;
    L_0x04cb:
        r0 = 0;
    L_0x04cc:
        if (r0 == 0) goto L_0x04ee;
    L_0x04ce:
        r0 = new android.view.View;
        r0.<init>(r13);
        r2 = r12.dividers;
        r2.add(r0);
        r2 = "divider";
        r2 = org.telegram.ui.ActionBar.Theme.getColor(r2);
        r0.setBackgroundColor(r2);
        r2 = new android.widget.FrameLayout$LayoutParams;
        r3 = -1;
        r4 = 1;
        r5 = 83;
        r2.<init>(r3, r4, r5);
        r1.addView(r0, r2);
    L_0x04ee:
        r7 = r1;
        goto L_0x01d1;
    L_0x04f1:
        r0 = 0;
        goto L_0x04be;
    L_0x04f3:
        r2 = 6;
        if (r8 != r2) goto L_0x04cc;
    L_0x04f6:
        r2 = r12.paymentForm;
        r2 = r2.invoice;
        r2 = r2.phone_requested;
        if (r2 != 0) goto L_0x04cc;
    L_0x04fe:
        r2 = r12.paymentForm;
        r2 = r2.invoice;
        r2 = r2.email_requested;
        if (r2 != 0) goto L_0x04cc;
    L_0x0506:
        r0 = 0;
        goto L_0x04cc;
    L_0x0508:
        r0 = r12.inputFields;
        r1 = new org.telegram.ui.Components.EditTextBoldCursor;
        r1.<init>(r13);
        r0[r8] = r1;
        goto L_0x01de;
    L_0x0513:
        r0 = 7;
        if (r8 != r0) goto L_0x0520;
    L_0x0516:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 1;
        r0.setInputType(r1);
        goto L_0x0262;
    L_0x0520:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 16385; // 0x4001 float:2.296E-41 double:8.0953E-320;
        r0.setInputType(r1);
        goto L_0x0262;
    L_0x052b:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentShippingName";
        r2 = 2131232116; // 0x7f080574 float:1.8080332E38 double:1.052968572E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        if (r0 == 0) goto L_0x026f;
    L_0x0542:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        r0 = r0.name;
        if (r0 == 0) goto L_0x026f;
    L_0x054a:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = r12.paymentForm;
        r1 = r1.saved_info;
        r1 = r1.name;
        r0.setText(r1);
        goto L_0x026f;
    L_0x0559:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentShippingEmailPlaceholder";
        r2 = 2131232113; // 0x7f080571 float:1.8080326E38 double:1.0529685703E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        if (r0 == 0) goto L_0x026f;
    L_0x0570:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        r0 = r0.email;
        if (r0 == 0) goto L_0x026f;
    L_0x0578:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = r12.paymentForm;
        r1 = r1.saved_info;
        r1 = r1.email;
        r0.setText(r1);
        goto L_0x026f;
    L_0x0587:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentShippingAddress1Placeholder";
        r2 = 2131232109; // 0x7f08056d float:1.8080318E38 double:1.0529685684E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        if (r0 == 0) goto L_0x026f;
    L_0x059e:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        r0 = r0.shipping_address;
        if (r0 == 0) goto L_0x026f;
    L_0x05a6:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = r12.paymentForm;
        r1 = r1.saved_info;
        r1 = r1.shipping_address;
        r1 = r1.street_line1;
        r0.setText(r1);
        goto L_0x026f;
    L_0x05b7:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentShippingAddress2Placeholder";
        r2 = 2131232110; // 0x7f08056e float:1.808032E38 double:1.052968569E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        if (r0 == 0) goto L_0x026f;
    L_0x05ce:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        r0 = r0.shipping_address;
        if (r0 == 0) goto L_0x026f;
    L_0x05d6:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = r12.paymentForm;
        r1 = r1.saved_info;
        r1 = r1.shipping_address;
        r1 = r1.street_line2;
        r0.setText(r1);
        goto L_0x026f;
    L_0x05e7:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentShippingCityPlaceholder";
        r2 = 2131232111; // 0x7f08056f float:1.8080322E38 double:1.0529685694E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        if (r0 == 0) goto L_0x026f;
    L_0x05fe:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        r0 = r0.shipping_address;
        if (r0 == 0) goto L_0x026f;
    L_0x0606:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = r12.paymentForm;
        r1 = r1.saved_info;
        r1 = r1.shipping_address;
        r1 = r1.city;
        r0.setText(r1);
        goto L_0x026f;
    L_0x0617:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentShippingStatePlaceholder";
        r2 = 2131232121; // 0x7f080579 float:1.8080342E38 double:1.0529685743E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        if (r0 == 0) goto L_0x026f;
    L_0x062e:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        r0 = r0.shipping_address;
        if (r0 == 0) goto L_0x026f;
    L_0x0636:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = r12.paymentForm;
        r1 = r1.saved_info;
        r1 = r1.shipping_address;
        r1 = r1.state;
        r0.setText(r1);
        goto L_0x026f;
    L_0x0647:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentShippingCountry";
        r2 = 2131232112; // 0x7f080570 float:1.8080324E38 double:1.05296857E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        if (r0 == 0) goto L_0x026f;
    L_0x065e:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        r0 = r0.shipping_address;
        if (r0 == 0) goto L_0x026f;
    L_0x0666:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        r0 = r0.shipping_address;
        r0 = r0.country_iso2;
        r0 = r10.get(r0);
        r0 = (java.lang.String) r0;
        r1 = r12.paymentForm;
        r1 = r1.saved_info;
        r1 = r1.shipping_address;
        r1 = r1.country_iso2;
        r12.countryName = r1;
        r1 = r12.inputFields;
        r1 = r1[r8];
        if (r0 == 0) goto L_0x0689;
    L_0x0684:
        r1.setText(r0);
        goto L_0x026f;
    L_0x0689:
        r0 = r12.countryName;
        goto L_0x0684;
    L_0x068c:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentShippingZipPlaceholder";
        r2 = 2131232122; // 0x7f08057a float:1.8080344E38 double:1.052968575E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        if (r0 == 0) goto L_0x026f;
    L_0x06a3:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        r0 = r0.shipping_address;
        if (r0 == 0) goto L_0x026f;
    L_0x06ab:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = r12.paymentForm;
        r1 = r1.saved_info;
        r1 = r1.shipping_address;
        r1 = r1.post_code;
        r0.setText(r1);
        goto L_0x026f;
    L_0x06bc:
        r0 = 9;
        if (r8 != r0) goto L_0x06f6;
    L_0x06c0:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r0.setPadding(r1, r2, r3, r4);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 19;
        r0.setGravity(r1);
        r0 = r12.inputFields;
        r6 = r0[r8];
        r0 = -1;
        r1 = -2;
        r2 = 0;
        r3 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r4 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r5 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r0 = org.telegram.ui.Components.LayoutHelper.createLinear(r0, r1, r2, r3, r4, r5);
        r7.addView(r6, r0);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = new org.telegram.ui.PaymentFormActivity$5;
        r1.<init>();
        r0.addTextChangedListener(r1);
        goto L_0x0304;
    L_0x06f6:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0.setPadding(r1, r2, r3, r4);
        r0 = r12.inputFields;
        r1 = r0[r8];
        r0 = org.telegram.messenger.LocaleController.isRTL;
        if (r0 == 0) goto L_0x072c;
    L_0x070e:
        r0 = 5;
    L_0x070f:
        r1.setGravity(r0);
        r0 = r12.inputFields;
        r11 = r0[r8];
        r0 = -1;
        r1 = -1073741824; // 0xffffffffc0000000 float:-2.0 double:NaN;
        r2 = 51;
        r3 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r4 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r5 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r6 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r0 = org.telegram.ui.Components.LayoutHelper.createFrame(r0, r1, r2, r3, r4, r5, r6);
        r7.addView(r11, r0);
        goto L_0x0304;
    L_0x072c:
        r0 = 3;
        goto L_0x070f;
    L_0x072e:
        if (r1 == 0) goto L_0x0809;
    L_0x0730:
        r0 = r1.first_name;
        r1 = r1.last_name;
        r0 = org.telegram.messenger.ContactsController.formatName(r0, r1);
    L_0x0738:
        r1 = r12.bottomCell;
        r2 = 1;
        r3 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r3.<init>(r13);
        r1[r2] = r3;
        r1 = r12.bottomCell;
        r2 = 1;
        r1 = r1[r2];
        r2 = 2130837850; // 0x7f02015a float:1.7280666E38 double:1.0527737785E-314;
        r3 = "windowBackgroundGrayShadow";
        r2 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r13, r2, r3);
        r1.setBackgroundDrawable(r2);
        r1 = r12.linearLayout2;
        r2 = r12.bottomCell;
        r3 = 1;
        r2 = r2[r3];
        r3 = -1;
        r4 = -2;
        r3 = org.telegram.ui.Components.LayoutHelper.createLinear(r3, r4);
        r1.addView(r2, r3);
        r1 = r12.paymentForm;
        r1 = r1.invoice;
        r1 = r1.email_to_provider;
        if (r1 == 0) goto L_0x080e;
    L_0x076c:
        r1 = r12.paymentForm;
        r1 = r1.invoice;
        r1 = r1.phone_to_provider;
        if (r1 == 0) goto L_0x080e;
    L_0x0774:
        r1 = r12.bottomCell;
        r2 = 1;
        r1 = r1[r2];
        r2 = "PaymentPhoneEmailToProvider";
        r3 = 2131232923; // 0x7f08089b float:1.8081969E38 double:1.0529689705E-314;
        r4 = 1;
        r4 = new java.lang.Object[r4];
        r5 = 0;
        r4[r5] = r0;
        r0 = org.telegram.messenger.LocaleController.formatString(r2, r3, r4);
        r1.setText(r0);
    L_0x078c:
        r0 = new org.telegram.ui.Cells.TextCheckCell;
        r0.<init>(r13);
        r12.checkCell1 = r0;
        r0 = r12.checkCell1;
        r1 = 1;
        r1 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r1);
        r0.setBackgroundDrawable(r1);
        r0 = r12.checkCell1;
        r1 = "PaymentShippingSave";
        r2 = 2131232119; // 0x7f080577 float:1.8080338E38 double:1.0529685733E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r2 = r12.saveShippingInfo;
        r3 = 0;
        r0.setTextAndCheck(r1, r2, r3);
        r0 = r12.linearLayout2;
        r1 = r12.checkCell1;
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = r12.checkCell1;
        r1 = new org.telegram.ui.PaymentFormActivity$7;
        r1.<init>();
        r0.setOnClickListener(r1);
        r0 = r12.bottomCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.bottomCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = 2130837850; // 0x7f02015a float:1.7280666E38 double:1.0527737785E-314;
        r2 = "windowBackgroundGrayShadow";
        r1 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r13, r1, r2);
        r0.setBackgroundDrawable(r1);
        r0 = r12.bottomCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = "PaymentShippingSaveInfo";
        r2 = 2131232120; // 0x7f080578 float:1.808034E38 double:1.052968574E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setText(r1);
        r0 = r12.linearLayout2;
        r1 = r12.bottomCell;
        r2 = 0;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
    L_0x0804:
        r0 = r8 + 1;
        r8 = r0;
        goto L_0x0169;
    L_0x0809:
        r0 = "";
        goto L_0x0738;
    L_0x080e:
        r1 = r12.paymentForm;
        r1 = r1.invoice;
        r1 = r1.email_to_provider;
        if (r1 == 0) goto L_0x0830;
    L_0x0816:
        r1 = r12.bottomCell;
        r2 = 1;
        r1 = r1[r2];
        r2 = "PaymentEmailToProvider";
        r3 = 2131232923; // 0x7f08089b float:1.8081969E38 double:1.0529689705E-314;
        r4 = 1;
        r4 = new java.lang.Object[r4];
        r5 = 0;
        r4[r5] = r0;
        r0 = org.telegram.messenger.LocaleController.formatString(r2, r3, r4);
        r1.setText(r0);
        goto L_0x078c;
    L_0x0830:
        r1 = r12.bottomCell;
        r2 = 1;
        r1 = r1[r2];
        r2 = "PaymentPhoneToProvider";
        r3 = 2131232923; // 0x7f08089b float:1.8081969E38 double:1.0529689705E-314;
        r4 = 1;
        r4 = new java.lang.Object[r4];
        r5 = 0;
        r4[r5] = r0;
        r0 = org.telegram.messenger.LocaleController.formatString(r2, r3, r4);
        r1.setText(r0);
        goto L_0x078c;
    L_0x084a:
        r0 = r12.sectionCell;
        r1 = 1;
        r2 = new org.telegram.ui.Cells.ShadowSectionCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.linearLayout2;
        r1 = r12.sectionCell;
        r2 = 1;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        goto L_0x078c;
    L_0x0866:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.name_requested;
        if (r0 != 0) goto L_0x087e;
    L_0x086e:
        r0 = r12.inputFields;
        r1 = 6;
        r0 = r0[r1];
        r0 = r0.getParent();
        r0 = (android.view.ViewGroup) r0;
        r1 = 8;
        r0.setVisibility(r1);
    L_0x087e:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.phone_requested;
        if (r0 != 0) goto L_0x0897;
    L_0x0886:
        r0 = r12.inputFields;
        r1 = 8;
        r0 = r0[r1];
        r0 = r0.getParent();
        r0 = (android.view.ViewGroup) r0;
        r1 = 8;
        r0.setVisibility(r1);
    L_0x0897:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.email_requested;
        if (r0 != 0) goto L_0x08af;
    L_0x089f:
        r0 = r12.inputFields;
        r1 = 7;
        r0 = r0[r1];
        r0 = r0.getParent();
        r0 = (android.view.ViewGroup) r0;
        r1 = 8;
        r0.setVisibility(r1);
    L_0x08af:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.phone_requested;
        if (r0 == 0) goto L_0x0a06;
    L_0x08b7:
        r0 = r12.inputFields;
        r1 = 9;
        r0 = r0[r1];
        r1 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r0.setImeOptions(r1);
    L_0x08c3:
        r0 = r12.sectionCell;
        r1 = 1;
        r0 = r0[r1];
        if (r0 == 0) goto L_0x0a41;
    L_0x08ca:
        r0 = r12.sectionCell;
        r1 = 1;
        r1 = r0[r1];
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.name_requested;
        if (r0 != 0) goto L_0x08e7;
    L_0x08d7:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.phone_requested;
        if (r0 != 0) goto L_0x08e7;
    L_0x08df:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.email_requested;
        if (r0 == 0) goto L_0x0a3d;
    L_0x08e7:
        r0 = 0;
    L_0x08e8:
        r1.setVisibility(r0);
    L_0x08eb:
        r0 = r12.headerCell;
        r1 = 1;
        r1 = r0[r1];
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.name_requested;
        if (r0 != 0) goto L_0x0908;
    L_0x08f8:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.phone_requested;
        if (r0 != 0) goto L_0x0908;
    L_0x0900:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.email_requested;
        if (r0 == 0) goto L_0x0a6e;
    L_0x0908:
        r0 = 0;
    L_0x0909:
        r1.setVisibility(r0);
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.shipping_address_requested;
        if (r0 != 0) goto L_0x0988;
    L_0x0914:
        r0 = r12.headerCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = 8;
        r0.setVisibility(r1);
        r0 = r12.sectionCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = 8;
        r0.setVisibility(r1);
        r0 = r12.inputFields;
        r1 = 0;
        r0 = r0[r1];
        r0 = r0.getParent();
        r0 = (android.view.ViewGroup) r0;
        r1 = 8;
        r0.setVisibility(r1);
        r0 = r12.inputFields;
        r1 = 1;
        r0 = r0[r1];
        r0 = r0.getParent();
        r0 = (android.view.ViewGroup) r0;
        r1 = 8;
        r0.setVisibility(r1);
        r0 = r12.inputFields;
        r1 = 2;
        r0 = r0[r1];
        r0 = r0.getParent();
        r0 = (android.view.ViewGroup) r0;
        r1 = 8;
        r0.setVisibility(r1);
        r0 = r12.inputFields;
        r1 = 3;
        r0 = r0[r1];
        r0 = r0.getParent();
        r0 = (android.view.ViewGroup) r0;
        r1 = 8;
        r0.setVisibility(r1);
        r0 = r12.inputFields;
        r1 = 4;
        r0 = r0[r1];
        r0 = r0.getParent();
        r0 = (android.view.ViewGroup) r0;
        r1 = 8;
        r0.setVisibility(r1);
        r0 = r12.inputFields;
        r1 = 5;
        r0 = r0[r1];
        r0 = r0.getParent();
        r0 = (android.view.ViewGroup) r0;
        r1 = 8;
        r0.setVisibility(r1);
    L_0x0988:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        if (r0 == 0) goto L_0x0a72;
    L_0x098e:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        r0 = r0.phone;
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 != 0) goto L_0x0a72;
    L_0x099a:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        r0 = r0.phone;
        r12.fillNumber(r0);
    L_0x09a3:
        r0 = r12.inputFields;
        r1 = 8;
        r0 = r0[r1];
        r0 = r0.length();
        if (r0 != 0) goto L_0x0a03;
    L_0x09af:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.phone_requested;
        if (r0 == 0) goto L_0x0a03;
    L_0x09b7:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        if (r0 == 0) goto L_0x09c9;
    L_0x09bd:
        r0 = r12.paymentForm;
        r0 = r0.saved_info;
        r0 = r0.phone;
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 == 0) goto L_0x0a03;
    L_0x09c9:
        r1 = 0;
        r0 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0a78 }
        r2 = "phone";
        r0 = r0.getSystemService(r2);	 Catch:{ Exception -> 0x0a78 }
        r0 = (android.telephony.TelephonyManager) r0;	 Catch:{ Exception -> 0x0a78 }
        if (r0 == 0) goto L_0x0a7c;
    L_0x09d7:
        r0 = r0.getSimCountryIso();	 Catch:{ Exception -> 0x0a78 }
        r0 = r0.toUpperCase();	 Catch:{ Exception -> 0x0a78 }
    L_0x09df:
        if (r0 == 0) goto L_0x0a03;
    L_0x09e1:
        r0 = r9.get(r0);
        r0 = (java.lang.String) r0;
        if (r0 == 0) goto L_0x0a03;
    L_0x09e9:
        r1 = r12.countriesArray;
        r1 = r1.indexOf(r0);
        r2 = -1;
        if (r1 == r2) goto L_0x0a03;
    L_0x09f2:
        r1 = r12.inputFields;
        r2 = 8;
        r1 = r1[r2];
        r2 = r12.countriesMap;
        r0 = r2.get(r0);
        r0 = (java.lang.CharSequence) r0;
        r1.setText(r0);
    L_0x0a03:
        r0 = r12.fragmentView;
        return r0;
    L_0x0a06:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.email_requested;
        if (r0 == 0) goto L_0x0a1b;
    L_0x0a0e:
        r0 = r12.inputFields;
        r1 = 7;
        r0 = r0[r1];
        r1 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r0.setImeOptions(r1);
        goto L_0x08c3;
    L_0x0a1b:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.name_requested;
        if (r0 == 0) goto L_0x0a30;
    L_0x0a23:
        r0 = r12.inputFields;
        r1 = 6;
        r0 = r0[r1];
        r1 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r0.setImeOptions(r1);
        goto L_0x08c3;
    L_0x0a30:
        r0 = r12.inputFields;
        r1 = 5;
        r0 = r0[r1];
        r1 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r0.setImeOptions(r1);
        goto L_0x08c3;
    L_0x0a3d:
        r0 = 8;
        goto L_0x08e8;
    L_0x0a41:
        r0 = r12.bottomCell;
        r1 = 1;
        r0 = r0[r1];
        if (r0 == 0) goto L_0x08eb;
    L_0x0a48:
        r0 = r12.bottomCell;
        r1 = 1;
        r1 = r0[r1];
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.name_requested;
        if (r0 != 0) goto L_0x0a65;
    L_0x0a55:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.phone_requested;
        if (r0 != 0) goto L_0x0a65;
    L_0x0a5d:
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.email_requested;
        if (r0 == 0) goto L_0x0a6b;
    L_0x0a65:
        r0 = 0;
    L_0x0a66:
        r1.setVisibility(r0);
        goto L_0x08eb;
    L_0x0a6b:
        r0 = 8;
        goto L_0x0a66;
    L_0x0a6e:
        r0 = 8;
        goto L_0x0909;
    L_0x0a72:
        r0 = 0;
        r12.fillNumber(r0);
        goto L_0x09a3;
    L_0x0a78:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
    L_0x0a7c:
        r0 = r1;
        goto L_0x09df;
    L_0x0a7f:
        r0 = r12.currentStep;
        r1 = 2;
        if (r0 != r1) goto L_0x1030;
    L_0x0a84:
        r0 = r12.paymentForm;
        r0 = r0.native_params;
        if (r0 == 0) goto L_0x0ab9;
    L_0x0a8a:
        r0 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0bf5 }
        r1 = r12.paymentForm;	 Catch:{ Exception -> 0x0bf5 }
        r1 = r1.native_params;	 Catch:{ Exception -> 0x0bf5 }
        r1 = r1.data;	 Catch:{ Exception -> 0x0bf5 }
        r0.<init>(r1);	 Catch:{ Exception -> 0x0bf5 }
        r1 = "android_pay_public_key";
        r1 = r0.getString(r1);	 Catch:{ Exception -> 0x0bef }
        r2 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Exception -> 0x0bef }
        if (r2 != 0) goto L_0x0aa4;
    L_0x0aa2:
        r12.androidPayPublicKey = r1;	 Catch:{ Exception -> 0x0bef }
    L_0x0aa4:
        r1 = "android_pay_bgcolor";
        r1 = r0.getInt(r1);	 Catch:{ Exception -> 0x0bfb }
        r2 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r1 = r1 | r2;
        r12.androidPayBackgroundColor = r1;	 Catch:{ Exception -> 0x0bfb }
    L_0x0ab0:
        r1 = "android_pay_inverse";
        r0 = r0.getBoolean(r1);	 Catch:{ Exception -> 0x0c01 }
        r12.androidPayBlackTheme = r0;	 Catch:{ Exception -> 0x0c01 }
    L_0x0ab9:
        r0 = r12.isWebView;
        if (r0 == 0) goto L_0x0c07;
    L_0x0abd:
        r0 = r12.androidPayPublicKey;
        if (r0 == 0) goto L_0x0ac4;
    L_0x0ac1:
        r12.initAndroidPay(r13);
    L_0x0ac4:
        r0 = new android.widget.FrameLayout;
        r0.<init>(r13);
        r12.androidPayContainer = r0;
        r0 = r12.androidPayContainer;
        r1 = 4000; // 0xfa0 float:5.605E-42 double:1.9763E-320;
        r0.setId(r1);
        r0 = r12.androidPayContainer;
        r1 = 1;
        r1 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r1);
        r0.setBackgroundDrawable(r1);
        r0 = r12.androidPayContainer;
        r1 = 8;
        r0.setVisibility(r1);
        r0 = r12.linearLayout2;
        r1 = r12.androidPayContainer;
        r2 = -1;
        r3 = 48;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = 1;
        r12.webviewLoading = r0;
        r0 = 1;
        r1 = 1;
        r12.showEditDoneProgress(r0, r1);
        r0 = r12.progressView;
        r1 = 0;
        r0.setVisibility(r1);
        r0 = r12.doneItem;
        r1 = 0;
        r0.setEnabled(r1);
        r0 = r12.doneItem;
        r0 = r0.getImageView();
        r1 = 4;
        r0.setVisibility(r1);
        r0 = new org.telegram.ui.PaymentFormActivity$8;
        r0.<init>(r13);
        r12.webView = r0;
        r0 = r12.webView;
        r0 = r0.getSettings();
        r1 = 1;
        r0.setJavaScriptEnabled(r1);
        r0 = r12.webView;
        r0 = r0.getSettings();
        r1 = 1;
        r0.setDomStorageEnabled(r1);
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 21;
        if (r0 < r1) goto L_0x0b44;
    L_0x0b30:
        r0 = r12.webView;
        r0 = r0.getSettings();
        r1 = 0;
        r0.setMixedContentMode(r1);
        r0 = android.webkit.CookieManager.getInstance();
        r1 = r12.webView;
        r2 = 1;
        r0.setAcceptThirdPartyCookies(r1, r2);
    L_0x0b44:
        r0 = r12.webView;
        r1 = new org.telegram.ui.PaymentFormActivity$TelegramWebviewProxy;
        r2 = 0;
        r1.<init>(r12, r2);
        r2 = "TelegramWebviewProxy";
        r0.addJavascriptInterface(r1, r2);
        r0 = r12.webView;
        r1 = new org.telegram.ui.PaymentFormActivity$9;
        r1.<init>();
        r0.setWebViewClient(r1);
        r0 = r12.linearLayout2;
        r1 = r12.webView;
        r2 = -1;
        r3 = -1073741824; // 0xffffffffc0000000 float:-2.0 double:NaN;
        r2 = org.telegram.ui.Components.LayoutHelper.createFrame(r2, r3);
        r0.addView(r1, r2);
        r0 = r12.sectionCell;
        r1 = 2;
        r2 = new org.telegram.ui.Cells.ShadowSectionCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.linearLayout2;
        r1 = r12.sectionCell;
        r2 = 2;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = new org.telegram.ui.Cells.TextCheckCell;
        r0.<init>(r13);
        r12.checkCell1 = r0;
        r0 = r12.checkCell1;
        r1 = 1;
        r1 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r1);
        r0.setBackgroundDrawable(r1);
        r0 = r12.checkCell1;
        r1 = "PaymentCardSavePaymentInformation";
        r2 = 2131232088; // 0x7f080558 float:1.8080275E38 double:1.052968558E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r2 = r12.saveCardInfo;
        r3 = 0;
        r0.setTextAndCheck(r1, r2, r3);
        r0 = r12.linearLayout2;
        r1 = r12.checkCell1;
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = r12.checkCell1;
        r1 = new org.telegram.ui.PaymentFormActivity$10;
        r1.<init>();
        r0.setOnClickListener(r1);
        r0 = r12.bottomCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.bottomCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = 2130837850; // 0x7f02015a float:1.7280666E38 double:1.0527737785E-314;
        r2 = "windowBackgroundGrayShadow";
        r1 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r13, r1, r2);
        r0.setBackgroundDrawable(r1);
        r12.updateSavePaymentField();
        r0 = r12.linearLayout2;
        r1 = r12.bottomCell;
        r2 = 0;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        goto L_0x0a03;
    L_0x0bef:
        r1 = move-exception;
        r1 = 0;
        r12.androidPayPublicKey = r1;	 Catch:{ Exception -> 0x0bf5 }
        goto L_0x0aa4;
    L_0x0bf5:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
        goto L_0x0ab9;
    L_0x0bfb:
        r1 = move-exception;
        r1 = -1;
        r12.androidPayBackgroundColor = r1;	 Catch:{ Exception -> 0x0bf5 }
        goto L_0x0ab0;
    L_0x0c01:
        r0 = move-exception;
        r0 = 0;
        r12.androidPayBlackTheme = r0;	 Catch:{ Exception -> 0x0bf5 }
        goto L_0x0ab9;
    L_0x0c07:
        r0 = r12.paymentForm;
        r0 = r0.native_params;
        if (r0 == 0) goto L_0x0c3c;
    L_0x0c0d:
        r0 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0e08 }
        r1 = r12.paymentForm;	 Catch:{ Exception -> 0x0e08 }
        r1 = r1.native_params;	 Catch:{ Exception -> 0x0e08 }
        r1 = r1.data;	 Catch:{ Exception -> 0x0e08 }
        r0.<init>(r1);	 Catch:{ Exception -> 0x0e08 }
        r1 = "need_country";
        r1 = r0.getBoolean(r1);	 Catch:{ Exception -> 0x0e02 }
        r12.need_card_country = r1;	 Catch:{ Exception -> 0x0e02 }
    L_0x0c21:
        r1 = "need_zip";
        r1 = r0.getBoolean(r1);	 Catch:{ Exception -> 0x0e0e }
        r12.need_card_postcode = r1;	 Catch:{ Exception -> 0x0e0e }
    L_0x0c2a:
        r1 = "need_cardholder_name";
        r1 = r0.getBoolean(r1);	 Catch:{ Exception -> 0x0e14 }
        r12.need_card_name = r1;	 Catch:{ Exception -> 0x0e14 }
    L_0x0c33:
        r1 = "publishable_key";
        r0 = r0.getString(r1);	 Catch:{ Exception -> 0x0e1a }
        r12.stripeApiKey = r0;	 Catch:{ Exception -> 0x0e1a }
    L_0x0c3c:
        r12.initAndroidPay(r13);
        r0 = 6;
        r0 = new org.telegram.ui.Components.EditTextBoldCursor[r0];
        r12.inputFields = r0;
        r0 = 0;
        r8 = r0;
    L_0x0c46:
        r0 = 6;
        if (r8 >= r0) goto L_0x0ff6;
    L_0x0c49:
        if (r8 != 0) goto L_0x0e22;
    L_0x0c4b:
        r0 = r12.headerCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.HeaderCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.headerCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = "windowBackgroundWhite";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r0 = r12.headerCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = "PaymentCardTitle";
        r2 = 2131232091; // 0x7f08055b float:1.8080281E38 double:1.0529685595E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setText(r1);
        r0 = r12.linearLayout2;
        r1 = r12.headerCell;
        r2 = 0;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
    L_0x0c86:
        r0 = 3;
        if (r8 == r0) goto L_0x0e62;
    L_0x0c89:
        r0 = 5;
        if (r8 == r0) goto L_0x0e62;
    L_0x0c8c:
        r0 = 4;
        if (r8 != r0) goto L_0x0c93;
    L_0x0c8f:
        r0 = r12.need_card_postcode;
        if (r0 == 0) goto L_0x0e62;
    L_0x0c93:
        r0 = 1;
        r7 = r0;
    L_0x0c95:
        r9 = new android.widget.FrameLayout;
        r9.<init>(r13);
        r0 = r12.linearLayout2;
        r1 = -1;
        r2 = 48;
        r1 = org.telegram.ui.Components.LayoutHelper.createLinear(r1, r2);
        r0.addView(r9, r1);
        r0 = "windowBackgroundWhite";
        r0 = org.telegram.ui.ActionBar.Theme.getColor(r0);
        r9.setBackgroundColor(r0);
        r0 = r12.inputFields;
        r1 = new org.telegram.ui.Components.EditTextBoldCursor;
        r1.<init>(r13);
        r0[r8] = r1;
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = java.lang.Integer.valueOf(r8);
        r0.setTag(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 1;
        r2 = 1098907648; // 0x41800000 float:16.0 double:5.42932517E-315;
        r0.setTextSize(r1, r2);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "windowBackgroundWhiteHintText";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setHintTextColor(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "windowBackgroundWhiteBlackText";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setTextColor(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 0;
        r0.setBackgroundDrawable(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "windowBackgroundWhiteBlackText";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setCursorColor(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r1 = org.telegram.messenger.AndroidUtilities.dp(r1);
        r0.setCursorSize(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 1069547520; // 0x3fc00000 float:1.5 double:5.28426686E-315;
        r0.setCursorWidth(r1);
        r0 = 3;
        if (r8 != r0) goto L_0x0e66;
    L_0x0d19:
        r0 = 1;
        r0 = new android.text.InputFilter[r0];
        r1 = 0;
        r2 = new android.text.InputFilter$LengthFilter;
        r3 = 3;
        r2.<init>(r3);
        r0[r1] = r2;
        r1 = r12.inputFields;
        r1 = r1[r8];
        r1.setFilters(r0);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 130; // 0x82 float:1.82E-43 double:6.4E-322;
        r0.setInputType(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = android.graphics.Typeface.DEFAULT;
        r0.setTypeface(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = android.text.method.PasswordTransformationMethod.getInstance();
        r0.setTransformationMethod(r1);
    L_0x0d49:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 268435461; // 0x10000005 float:2.5243564E-29 double:1.326247394E-315;
        r0.setImeOptions(r1);
        switch(r8) {
            case 0: goto L_0x0eb2;
            case 1: goto L_0x0ed8;
            case 2: goto L_0x0eeb;
            case 3: goto L_0x0ec5;
            case 4: goto L_0x0f11;
            case 5: goto L_0x0efe;
            default: goto L_0x0d56;
        };
    L_0x0d56:
        if (r8 != 0) goto L_0x0f24;
    L_0x0d58:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = new org.telegram.ui.PaymentFormActivity$12;
        r1.<init>();
        r0.addTextChangedListener(r1);
    L_0x0d64:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0.setPadding(r1, r2, r3, r4);
        r0 = r12.inputFields;
        r1 = r0[r8];
        r0 = org.telegram.messenger.LocaleController.isRTL;
        if (r0 == 0) goto L_0x0f35;
    L_0x0d7c:
        r0 = 5;
    L_0x0d7d:
        r1.setGravity(r0);
        r0 = r12.inputFields;
        r10 = r0[r8];
        r0 = -1;
        r1 = -1073741824; // 0xffffffffc0000000 float:-2.0 double:NaN;
        r2 = 51;
        r3 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r4 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r5 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r6 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r0 = org.telegram.ui.Components.LayoutHelper.createFrame(r0, r1, r2, r3, r4, r5, r6);
        r9.addView(r10, r0);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = new org.telegram.ui.PaymentFormActivity$14;
        r1.<init>();
        r0.setOnEditorActionListener(r1);
        r0 = 3;
        if (r8 != r0) goto L_0x0f38;
    L_0x0da7:
        r0 = r12.sectionCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.ShadowSectionCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.linearLayout2;
        r1 = r12.sectionCell;
        r2 = 0;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
    L_0x0dc1:
        if (r7 == 0) goto L_0x0de3;
    L_0x0dc3:
        r0 = new android.view.View;
        r0.<init>(r13);
        r1 = r12.dividers;
        r1.add(r0);
        r1 = "divider";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r1 = new android.widget.FrameLayout$LayoutParams;
        r2 = -1;
        r3 = 1;
        r4 = 83;
        r1.<init>(r2, r3, r4);
        r9.addView(r0, r1);
    L_0x0de3:
        r0 = 4;
        if (r8 != r0) goto L_0x0dea;
    L_0x0de6:
        r0 = r12.need_card_country;
        if (r0 == 0) goto L_0x0df8;
    L_0x0dea:
        r0 = 5;
        if (r8 != r0) goto L_0x0df1;
    L_0x0ded:
        r0 = r12.need_card_postcode;
        if (r0 == 0) goto L_0x0df8;
    L_0x0df1:
        r0 = 2;
        if (r8 != r0) goto L_0x0dfd;
    L_0x0df4:
        r0 = r12.need_card_name;
        if (r0 != 0) goto L_0x0dfd;
    L_0x0df8:
        r0 = 8;
        r9.setVisibility(r0);
    L_0x0dfd:
        r0 = r8 + 1;
        r8 = r0;
        goto L_0x0c46;
    L_0x0e02:
        r1 = move-exception;
        r1 = 0;
        r12.need_card_country = r1;	 Catch:{ Exception -> 0x0e08 }
        goto L_0x0c21;
    L_0x0e08:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
        goto L_0x0c3c;
    L_0x0e0e:
        r1 = move-exception;
        r1 = 0;
        r12.need_card_postcode = r1;	 Catch:{ Exception -> 0x0e08 }
        goto L_0x0c2a;
    L_0x0e14:
        r1 = move-exception;
        r1 = 0;
        r12.need_card_name = r1;	 Catch:{ Exception -> 0x0e08 }
        goto L_0x0c33;
    L_0x0e1a:
        r0 = move-exception;
        r0 = "";
        r12.stripeApiKey = r0;	 Catch:{ Exception -> 0x0e08 }
        goto L_0x0c3c;
    L_0x0e22:
        r0 = 4;
        if (r8 != r0) goto L_0x0c86;
    L_0x0e25:
        r0 = r12.headerCell;
        r1 = 1;
        r2 = new org.telegram.ui.Cells.HeaderCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.headerCell;
        r1 = 1;
        r0 = r0[r1];
        r1 = "windowBackgroundWhite";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r0 = r12.headerCell;
        r1 = 1;
        r0 = r0[r1];
        r1 = "PaymentBillingAddress";
        r2 = 2131232081; // 0x7f080551 float:1.8080261E38 double:1.0529685545E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setText(r1);
        r0 = r12.linearLayout2;
        r1 = r12.headerCell;
        r2 = 1;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        goto L_0x0c86;
    L_0x0e62:
        r0 = 0;
        r7 = r0;
        goto L_0x0c95;
    L_0x0e66:
        if (r8 != 0) goto L_0x0e72;
    L_0x0e68:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 2;
        r0.setInputType(r1);
        goto L_0x0d49;
    L_0x0e72:
        r0 = 4;
        if (r8 != r0) goto L_0x0e8b;
    L_0x0e75:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = new org.telegram.ui.PaymentFormActivity$11;
        r1.<init>();
        r0.setOnTouchListener(r1);
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 0;
        r0.setInputType(r1);
        goto L_0x0d49;
    L_0x0e8b:
        r0 = 1;
        if (r8 != r0) goto L_0x0e99;
    L_0x0e8e:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 16386; // 0x4002 float:2.2962E-41 double:8.096E-320;
        r0.setInputType(r1);
        goto L_0x0d49;
    L_0x0e99:
        r0 = 2;
        if (r8 != r0) goto L_0x0ea7;
    L_0x0e9c:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 4097; // 0x1001 float:5.741E-42 double:2.024E-320;
        r0.setInputType(r1);
        goto L_0x0d49;
    L_0x0ea7:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = 16385; // 0x4001 float:2.296E-41 double:8.0953E-320;
        r0.setInputType(r1);
        goto L_0x0d49;
    L_0x0eb2:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentCardNumber";
        r2 = 2131232087; // 0x7f080557 float:1.8080273E38 double:1.0529685575E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        goto L_0x0d56;
    L_0x0ec5:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentCardCvv";
        r2 = 2131232082; // 0x7f080552 float:1.8080263E38 double:1.052968555E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        goto L_0x0d56;
    L_0x0ed8:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentCardExpireDate";
        r2 = 2131232083; // 0x7f080553 float:1.8080265E38 double:1.0529685555E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        goto L_0x0d56;
    L_0x0eeb:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentCardName";
        r2 = 2131232085; // 0x7f080555 float:1.808027E38 double:1.0529685565E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        goto L_0x0d56;
    L_0x0efe:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentShippingZipPlaceholder";
        r2 = 2131232122; // 0x7f08057a float:1.8080344E38 double:1.052968575E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        goto L_0x0d56;
    L_0x0f11:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = "PaymentShippingCountry";
        r2 = 2131232112; // 0x7f080570 float:1.8080324E38 double:1.05296857E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        goto L_0x0d56;
    L_0x0f24:
        r0 = 1;
        if (r8 != r0) goto L_0x0d64;
    L_0x0f27:
        r0 = r12.inputFields;
        r0 = r0[r8];
        r1 = new org.telegram.ui.PaymentFormActivity$13;
        r1.<init>();
        r0.addTextChangedListener(r1);
        goto L_0x0d64;
    L_0x0f35:
        r0 = 3;
        goto L_0x0d7d;
    L_0x0f38:
        r0 = 5;
        if (r8 != r0) goto L_0x0fc0;
    L_0x0f3b:
        r0 = r12.sectionCell;
        r1 = 2;
        r2 = new org.telegram.ui.Cells.ShadowSectionCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.linearLayout2;
        r1 = r12.sectionCell;
        r2 = 2;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = new org.telegram.ui.Cells.TextCheckCell;
        r0.<init>(r13);
        r12.checkCell1 = r0;
        r0 = r12.checkCell1;
        r1 = 1;
        r1 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r1);
        r0.setBackgroundDrawable(r1);
        r0 = r12.checkCell1;
        r1 = "PaymentCardSavePaymentInformation";
        r2 = 2131232088; // 0x7f080558 float:1.8080275E38 double:1.052968558E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r2 = r12.saveCardInfo;
        r3 = 0;
        r0.setTextAndCheck(r1, r2, r3);
        r0 = r12.linearLayout2;
        r1 = r12.checkCell1;
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = r12.checkCell1;
        r1 = new org.telegram.ui.PaymentFormActivity$15;
        r1.<init>();
        r0.setOnClickListener(r1);
        r0 = r12.bottomCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.bottomCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = 2130837850; // 0x7f02015a float:1.7280666E38 double:1.0527737785E-314;
        r2 = "windowBackgroundGrayShadow";
        r1 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r13, r1, r2);
        r0.setBackgroundDrawable(r1);
        r12.updateSavePaymentField();
        r0 = r12.linearLayout2;
        r1 = r12.bottomCell;
        r2 = 0;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        goto L_0x0dc1;
    L_0x0fc0:
        if (r8 != 0) goto L_0x0dc1;
    L_0x0fc2:
        r0 = new android.widget.FrameLayout;
        r0.<init>(r13);
        r12.androidPayContainer = r0;
        r0 = r12.androidPayContainer;
        r1 = 4000; // 0xfa0 float:5.605E-42 double:1.9763E-320;
        r0.setId(r1);
        r0 = r12.androidPayContainer;
        r1 = 1;
        r1 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r1);
        r0.setBackgroundDrawable(r1);
        r0 = r12.androidPayContainer;
        r1 = 8;
        r0.setVisibility(r1);
        r10 = r12.androidPayContainer;
        r0 = -2;
        r1 = -1073741824; // 0xffffffffc0000000 float:-2.0 double:NaN;
        r2 = 21;
        r3 = 0;
        r4 = 0;
        r5 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r6 = 0;
        r0 = org.telegram.ui.Components.LayoutHelper.createFrame(r0, r1, r2, r3, r4, r5, r6);
        r9.addView(r10, r0);
        goto L_0x0dc1;
    L_0x0ff6:
        r0 = r12.need_card_country;
        if (r0 != 0) goto L_0x1012;
    L_0x0ffa:
        r0 = r12.need_card_postcode;
        if (r0 != 0) goto L_0x1012;
    L_0x0ffe:
        r0 = r12.headerCell;
        r1 = 1;
        r0 = r0[r1];
        r1 = 8;
        r0.setVisibility(r1);
        r0 = r12.sectionCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = 8;
        r0.setVisibility(r1);
    L_0x1012:
        r0 = r12.need_card_postcode;
        if (r0 == 0) goto L_0x1023;
    L_0x1016:
        r0 = r12.inputFields;
        r1 = 5;
        r0 = r0[r1];
        r1 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r0.setImeOptions(r1);
        goto L_0x0a03;
    L_0x1023:
        r0 = r12.inputFields;
        r1 = 3;
        r0 = r0[r1];
        r1 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r0.setImeOptions(r1);
        goto L_0x0a03;
    L_0x1030:
        r0 = r12.currentStep;
        r1 = 1;
        if (r0 != r1) goto L_0x10e3;
    L_0x1035:
        r0 = r12.requestedInfo;
        r0 = r0.shipping_options;
        r3 = r0.size();
        r0 = new org.telegram.ui.Cells.RadioCell[r3];
        r12.radioCells = r0;
        r0 = 0;
        r2 = r0;
    L_0x1043:
        if (r2 >= r3) goto L_0x10b5;
    L_0x1045:
        r0 = r12.requestedInfo;
        r0 = r0.shipping_options;
        r0 = r0.get(r2);
        r0 = (org.telegram.tgnet.TLRPC$TL_shippingOption) r0;
        r1 = r12.radioCells;
        r4 = new org.telegram.ui.Cells.RadioCell;
        r4.<init>(r13);
        r1[r2] = r4;
        r1 = r12.radioCells;
        r1 = r1[r2];
        r4 = java.lang.Integer.valueOf(r2);
        r1.setTag(r4);
        r1 = r12.radioCells;
        r1 = r1[r2];
        r4 = 1;
        r4 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r4);
        r1.setBackgroundDrawable(r4);
        r1 = r12.radioCells;
        r4 = r1[r2];
        r1 = "%s - %s";
        r5 = 2;
        r5 = new java.lang.Object[r5];
        r6 = 0;
        r7 = r0.prices;
        r7 = r12.getTotalPriceString(r7);
        r5[r6] = r7;
        r6 = 1;
        r0 = r0.title;
        r5[r6] = r0;
        r5 = java.lang.String.format(r1, r5);
        if (r2 != 0) goto L_0x10b0;
    L_0x108d:
        r0 = 1;
        r1 = r0;
    L_0x108f:
        r0 = r3 + -1;
        if (r2 == r0) goto L_0x10b3;
    L_0x1093:
        r0 = 1;
    L_0x1094:
        r4.setText(r5, r1, r0);
        r0 = r12.radioCells;
        r0 = r0[r2];
        r1 = new org.telegram.ui.PaymentFormActivity$16;
        r1.<init>();
        r0.setOnClickListener(r1);
        r0 = r12.linearLayout2;
        r1 = r12.radioCells;
        r1 = r1[r2];
        r0.addView(r1);
        r0 = r2 + 1;
        r2 = r0;
        goto L_0x1043;
    L_0x10b0:
        r0 = 0;
        r1 = r0;
        goto L_0x108f;
    L_0x10b3:
        r0 = 0;
        goto L_0x1094;
    L_0x10b5:
        r0 = r12.bottomCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.bottomCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = 2130837850; // 0x7f02015a float:1.7280666E38 double:1.0527737785E-314;
        r2 = "windowBackgroundGrayShadow";
        r1 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r13, r1, r2);
        r0.setBackgroundDrawable(r1);
        r0 = r12.linearLayout2;
        r1 = r12.bottomCell;
        r2 = 0;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        goto L_0x0a03;
    L_0x10e3:
        r0 = r12.currentStep;
        r1 = 3;
        if (r0 != r1) goto L_0x1356;
    L_0x10e8:
        r0 = 2;
        r0 = new org.telegram.ui.Components.EditTextBoldCursor[r0];
        r12.inputFields = r0;
        r0 = 0;
        r7 = r0;
    L_0x10ef:
        r0 = 2;
        if (r7 >= r0) goto L_0x0a03;
    L_0x10f2:
        if (r7 != 0) goto L_0x112f;
    L_0x10f4:
        r0 = r12.headerCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.HeaderCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.headerCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = "windowBackgroundWhite";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r0 = r12.headerCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = "PaymentCardTitle";
        r2 = 2131232091; // 0x7f08055b float:1.8080281E38 double:1.0529685595E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setText(r1);
        r0 = r12.linearLayout2;
        r1 = r12.headerCell;
        r2 = 0;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
    L_0x112f:
        r8 = new android.widget.FrameLayout;
        r8.<init>(r13);
        r0 = r12.linearLayout2;
        r1 = -1;
        r2 = 48;
        r1 = org.telegram.ui.Components.LayoutHelper.createLinear(r1, r2);
        r0.addView(r8, r1);
        r0 = "windowBackgroundWhite";
        r0 = org.telegram.ui.ActionBar.Theme.getColor(r0);
        r8.setBackgroundColor(r0);
        r0 = 1;
        if (r7 == r0) goto L_0x12fd;
    L_0x114d:
        r0 = 1;
    L_0x114e:
        if (r0 == 0) goto L_0x115c;
    L_0x1150:
        r1 = 7;
        if (r7 != r1) goto L_0x1300;
    L_0x1153:
        r1 = r12.paymentForm;
        r1 = r1.invoice;
        r1 = r1.phone_requested;
        if (r1 != 0) goto L_0x1300;
    L_0x115b:
        r0 = 0;
    L_0x115c:
        if (r0 == 0) goto L_0x117e;
    L_0x115e:
        r0 = new android.view.View;
        r0.<init>(r13);
        r1 = r12.dividers;
        r1.add(r0);
        r1 = "divider";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r1 = new android.widget.FrameLayout$LayoutParams;
        r2 = -1;
        r3 = 1;
        r4 = 83;
        r1.<init>(r2, r3, r4);
        r8.addView(r0, r1);
    L_0x117e:
        r0 = r12.inputFields;
        r1 = new org.telegram.ui.Components.EditTextBoldCursor;
        r1.<init>(r13);
        r0[r7] = r1;
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = java.lang.Integer.valueOf(r7);
        r0.setTag(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 1;
        r2 = 1098907648; // 0x41800000 float:16.0 double:5.42932517E-315;
        r0.setTextSize(r1, r2);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = "windowBackgroundWhiteHintText";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setHintTextColor(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = "windowBackgroundWhiteBlackText";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setTextColor(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 0;
        r0.setBackgroundDrawable(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = "windowBackgroundWhiteBlackText";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setCursorColor(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r1 = org.telegram.messenger.AndroidUtilities.dp(r1);
        r0.setCursorSize(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 1069547520; // 0x3fc00000 float:1.5 double:5.28426686E-315;
        r0.setCursorWidth(r1);
        if (r7 != 0) goto L_0x1316;
    L_0x11e6:
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = new org.telegram.ui.PaymentFormActivity$17;
        r1.<init>();
        r0.setOnTouchListener(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 0;
        r0.setInputType(r1);
    L_0x11fa:
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r0.setImeOptions(r1);
        switch(r7) {
            case 0: goto L_0x132a;
            case 1: goto L_0x1339;
            default: goto L_0x1207;
        };
    L_0x1207:
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0.setPadding(r1, r2, r3, r4);
        r0 = r12.inputFields;
        r1 = r0[r7];
        r0 = org.telegram.messenger.LocaleController.isRTL;
        if (r0 == 0) goto L_0x1353;
    L_0x121f:
        r0 = 5;
    L_0x1220:
        r1.setGravity(r0);
        r0 = r12.inputFields;
        r9 = r0[r7];
        r0 = -1;
        r1 = -1073741824; // 0xffffffffc0000000 float:-2.0 double:NaN;
        r2 = 51;
        r3 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r4 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r5 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r6 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r0 = org.telegram.ui.Components.LayoutHelper.createFrame(r0, r1, r2, r3, r4, r5, r6);
        r8.addView(r9, r0);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = new org.telegram.ui.PaymentFormActivity$18;
        r1.<init>();
        r0.setOnEditorActionListener(r1);
        r0 = 1;
        if (r7 != r0) goto L_0x12f8;
    L_0x124a:
        r0 = r12.bottomCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.bottomCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = "PaymentConfirmationMessage";
        r2 = 2131232100; // 0x7f080564 float:1.80803E38 double:1.052968564E-314;
        r3 = 1;
        r3 = new java.lang.Object[r3];
        r4 = 0;
        r5 = r12.paymentForm;
        r5 = r5.saved_credentials;
        r5 = r5.title;
        r3[r4] = r5;
        r1 = org.telegram.messenger.LocaleController.formatString(r1, r2, r3);
        r0.setText(r1);
        r0 = r12.bottomCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = 2130837849; // 0x7f020159 float:1.7280664E38 double:1.052773778E-314;
        r2 = "windowBackgroundGrayShadow";
        r1 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r13, r1, r2);
        r0.setBackgroundDrawable(r1);
        r0 = r12.linearLayout2;
        r1 = r12.bottomCell;
        r2 = 0;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = new org.telegram.ui.Cells.TextSettingsCell;
        r0.<init>(r13);
        r12.settingsCell1 = r0;
        r0 = r12.settingsCell1;
        r1 = 1;
        r1 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r1);
        r0.setBackgroundDrawable(r1);
        r0 = r12.settingsCell1;
        r1 = "PaymentConfirmationNewCard";
        r2 = 2131232101; // 0x7f080565 float:1.8080302E38 double:1.0529685644E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r2 = 0;
        r0.setText(r1, r2);
        r0 = r12.linearLayout2;
        r1 = r12.settingsCell1;
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = r12.settingsCell1;
        r1 = new org.telegram.ui.PaymentFormActivity$19;
        r1.<init>();
        r0.setOnClickListener(r1);
        r0 = r12.bottomCell;
        r1 = 1;
        r2 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.bottomCell;
        r1 = 1;
        r0 = r0[r1];
        r1 = 2130837850; // 0x7f02015a float:1.7280666E38 double:1.0527737785E-314;
        r2 = "windowBackgroundGrayShadow";
        r1 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r13, r1, r2);
        r0.setBackgroundDrawable(r1);
        r0 = r12.linearLayout2;
        r1 = r12.bottomCell;
        r2 = 1;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
    L_0x12f8:
        r0 = r7 + 1;
        r7 = r0;
        goto L_0x10ef;
    L_0x12fd:
        r0 = 0;
        goto L_0x114e;
    L_0x1300:
        r1 = 6;
        if (r7 != r1) goto L_0x115c;
    L_0x1303:
        r1 = r12.paymentForm;
        r1 = r1.invoice;
        r1 = r1.phone_requested;
        if (r1 != 0) goto L_0x115c;
    L_0x130b:
        r1 = r12.paymentForm;
        r1 = r1.invoice;
        r1 = r1.email_requested;
        if (r1 != 0) goto L_0x115c;
    L_0x1313:
        r0 = 0;
        goto L_0x115c;
    L_0x1316:
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 129; // 0x81 float:1.81E-43 double:6.37E-322;
        r0.setInputType(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = android.graphics.Typeface.DEFAULT;
        r0.setTypeface(r1);
        goto L_0x11fa;
    L_0x132a:
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = r12.paymentForm;
        r1 = r1.saved_credentials;
        r1 = r1.title;
        r0.setText(r1);
        goto L_0x1207;
    L_0x1339:
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = "LoginPassword";
        r2 = 2131231766; // 0x7f080416 float:1.8079622E38 double:1.052968399E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r0.requestFocus();
        goto L_0x1207;
    L_0x1353:
        r0 = 3;
        goto L_0x1220;
    L_0x1356:
        r0 = r12.currentStep;
        r1 = 4;
        if (r0 == r1) goto L_0x1360;
    L_0x135b:
        r0 = r12.currentStep;
        r1 = 5;
        if (r0 != r1) goto L_0x17e2;
    L_0x1360:
        r0 = new org.telegram.ui.Cells.PaymentInfoCell;
        r0.<init>(r13);
        r12.paymentInfoCell = r0;
        r0 = r12.paymentInfoCell;
        r1 = "windowBackgroundWhite";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r1 = r12.paymentInfoCell;
        r0 = r12.messageObject;
        r0 = r0.messageOwner;
        r0 = r0.media;
        r0 = (org.telegram.tgnet.TLRPC$TL_messageMediaInvoice) r0;
        r2 = r12.currentBotName;
        r1.setInvoice(r0, r2);
        r0 = r12.linearLayout2;
        r1 = r12.paymentInfoCell;
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = r12.sectionCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.ShadowSectionCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.linearLayout2;
        r1 = r12.sectionCell;
        r2 = 0;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r2 = new java.util.ArrayList;
        r2.<init>();
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.prices;
        r2.addAll(r0);
        r0 = r12.shippingOption;
        if (r0 == 0) goto L_0x13c2;
    L_0x13bb:
        r0 = r12.shippingOption;
        r0 = r0.prices;
        r2.addAll(r0);
    L_0x13c2:
        r3 = r12.getTotalPriceString(r2);
        r0 = 0;
        r1 = r0;
    L_0x13c8:
        r0 = r2.size();
        if (r1 >= r0) goto L_0x1402;
    L_0x13ce:
        r0 = r2.get(r1);
        r0 = (org.telegram.tgnet.TLRPC$TL_labeledPrice) r0;
        r4 = new org.telegram.ui.Cells.TextPriceCell;
        r4.<init>(r13);
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r5 = r0.label;
        r6 = org.telegram.messenger.LocaleController.getInstance();
        r8 = r0.amount;
        r0 = r12.paymentForm;
        r0 = r0.invoice;
        r0 = r0.currency;
        r0 = r6.formatCurrencyString(r8, r0);
        r6 = 0;
        r4.setTextAndValue(r5, r0, r6);
        r0 = r12.linearLayout2;
        r0.addView(r4);
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x13c8;
    L_0x1402:
        r0 = new org.telegram.ui.Cells.TextPriceCell;
        r0.<init>(r13);
        r1 = "windowBackgroundWhite";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r1 = "PaymentTransactionTotal";
        r2 = 2131232128; // 0x7f080580 float:1.8080357E38 double:1.052968578E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r2 = 1;
        r0.setTextAndValue(r1, r3, r2);
        r1 = r12.linearLayout2;
        r1.addView(r0);
        r0 = new android.view.View;
        r0.<init>(r13);
        r1 = r12.dividers;
        r1.add(r0);
        r1 = "divider";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r1 = r12.linearLayout2;
        r2 = new android.widget.FrameLayout$LayoutParams;
        r4 = -1;
        r5 = 1;
        r6 = 83;
        r2.<init>(r4, r5, r6);
        r1.addView(r0, r2);
        r0 = r12.detailSettingsCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.detailSettingsCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = 1;
        r1 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r1);
        r0.setBackgroundDrawable(r1);
        r0 = r12.detailSettingsCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = r12.cardName;
        r2 = "PaymentCheckoutMethod";
        r4 = 2131232094; // 0x7f08055e float:1.8080288E38 double:1.052968561E-314;
        r2 = org.telegram.messenger.LocaleController.getString(r2, r4);
        r4 = 1;
        r0.setTextAndValue(r1, r2, r4);
        r0 = r12.linearLayout2;
        r1 = r12.detailSettingsCell;
        r2 = 0;
        r1 = r1[r2];
        r0.addView(r1);
        r0 = r12.currentStep;
        r1 = 4;
        if (r0 != r1) goto L_0x148e;
    L_0x1481:
        r0 = r12.detailSettingsCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = new org.telegram.ui.PaymentFormActivity$20;
        r1.<init>();
        r0.setOnClickListener(r1);
    L_0x148e:
        r1 = 0;
        r0 = 0;
        r2 = r0;
    L_0x1491:
        r0 = r12.paymentForm;
        r0 = r0.users;
        r0 = r0.size();
        if (r2 >= r0) goto L_0x14b2;
    L_0x149b:
        r0 = r12.paymentForm;
        r0 = r0.users;
        r0 = r0.get(r2);
        r0 = (org.telegram.tgnet.TLRPC.User) r0;
        r4 = r0.id;
        r5 = r12.paymentForm;
        r5 = r5.provider_id;
        if (r4 != r5) goto L_0x1aca;
    L_0x14ad:
        r1 = r2 + 1;
        r2 = r1;
        r1 = r0;
        goto L_0x1491;
    L_0x14b2:
        if (r1 == 0) goto L_0x17dd;
    L_0x14b4:
        r0 = r12.detailSettingsCell;
        r2 = 1;
        r4 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r4.<init>(r13);
        r0[r2] = r4;
        r0 = r12.detailSettingsCell;
        r2 = 1;
        r0 = r0[r2];
        r2 = 1;
        r2 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r2);
        r0.setBackgroundDrawable(r2);
        r0 = r12.detailSettingsCell;
        r2 = 1;
        r2 = r0[r2];
        r0 = r1.first_name;
        r1 = r1.last_name;
        r0 = org.telegram.messenger.ContactsController.formatName(r0, r1);
        r1 = "PaymentCheckoutProvider";
        r4 = 2131232098; // 0x7f080562 float:1.8080296E38 double:1.052968563E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r4);
        r4 = 1;
        r2.setTextAndValue(r0, r1, r4);
        r1 = r12.linearLayout2;
        r2 = r12.detailSettingsCell;
        r4 = 1;
        r2 = r2[r4];
        r1.addView(r2);
    L_0x14f0:
        r1 = r12.validateRequest;
        if (r1 == 0) goto L_0x1690;
    L_0x14f4:
        r1 = r12.validateRequest;
        r1 = r1.info;
        r1 = r1.shipping_address;
        if (r1 == 0) goto L_0x157e;
    L_0x14fc:
        r1 = "%s %s, %s, %s, %s, %s";
        r2 = 6;
        r2 = new java.lang.Object[r2];
        r4 = 0;
        r5 = r12.validateRequest;
        r5 = r5.info;
        r5 = r5.shipping_address;
        r5 = r5.street_line1;
        r2[r4] = r5;
        r4 = 1;
        r5 = r12.validateRequest;
        r5 = r5.info;
        r5 = r5.shipping_address;
        r5 = r5.street_line2;
        r2[r4] = r5;
        r4 = 2;
        r5 = r12.validateRequest;
        r5 = r5.info;
        r5 = r5.shipping_address;
        r5 = r5.city;
        r2[r4] = r5;
        r4 = 3;
        r5 = r12.validateRequest;
        r5 = r5.info;
        r5 = r5.shipping_address;
        r5 = r5.state;
        r2[r4] = r5;
        r4 = 4;
        r5 = r12.validateRequest;
        r5 = r5.info;
        r5 = r5.shipping_address;
        r5 = r5.country_iso2;
        r2[r4] = r5;
        r4 = 5;
        r5 = r12.validateRequest;
        r5 = r5.info;
        r5 = r5.shipping_address;
        r5 = r5.post_code;
        r2[r4] = r5;
        r1 = java.lang.String.format(r1, r2);
        r2 = r12.detailSettingsCell;
        r4 = 2;
        r5 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r5.<init>(r13);
        r2[r4] = r5;
        r2 = r12.detailSettingsCell;
        r4 = 2;
        r2 = r2[r4];
        r4 = "windowBackgroundWhite";
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r2.setBackgroundColor(r4);
        r2 = r12.detailSettingsCell;
        r4 = 2;
        r2 = r2[r4];
        r4 = "PaymentShippingAddress";
        r5 = 2131232108; // 0x7f08056c float:1.8080316E38 double:1.052968568E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r5 = 1;
        r2.setTextAndValue(r1, r4, r5);
        r1 = r12.linearLayout2;
        r2 = r12.detailSettingsCell;
        r4 = 2;
        r2 = r2[r4];
        r1.addView(r2);
    L_0x157e:
        r1 = r12.validateRequest;
        r1 = r1.info;
        r1 = r1.name;
        if (r1 == 0) goto L_0x15c2;
    L_0x1586:
        r1 = r12.detailSettingsCell;
        r2 = 3;
        r4 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r4.<init>(r13);
        r1[r2] = r4;
        r1 = r12.detailSettingsCell;
        r2 = 3;
        r1 = r1[r2];
        r2 = "windowBackgroundWhite";
        r2 = org.telegram.ui.ActionBar.Theme.getColor(r2);
        r1.setBackgroundColor(r2);
        r1 = r12.detailSettingsCell;
        r2 = 3;
        r1 = r1[r2];
        r2 = r12.validateRequest;
        r2 = r2.info;
        r2 = r2.name;
        r4 = "PaymentCheckoutName";
        r5 = 2131232095; // 0x7f08055f float:1.808029E38 double:1.0529685615E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r5 = 1;
        r1.setTextAndValue(r2, r4, r5);
        r1 = r12.linearLayout2;
        r2 = r12.detailSettingsCell;
        r4 = 3;
        r2 = r2[r4];
        r1.addView(r2);
    L_0x15c2:
        r1 = r12.validateRequest;
        r1 = r1.info;
        r1 = r1.phone;
        if (r1 == 0) goto L_0x160e;
    L_0x15ca:
        r1 = r12.detailSettingsCell;
        r2 = 4;
        r4 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r4.<init>(r13);
        r1[r2] = r4;
        r1 = r12.detailSettingsCell;
        r2 = 4;
        r1 = r1[r2];
        r2 = "windowBackgroundWhite";
        r2 = org.telegram.ui.ActionBar.Theme.getColor(r2);
        r1.setBackgroundColor(r2);
        r1 = r12.detailSettingsCell;
        r2 = 4;
        r1 = r1[r2];
        r2 = org.telegram.p149a.C2488b.a();
        r4 = r12.validateRequest;
        r4 = r4.info;
        r4 = r4.phone;
        r2 = r2.e(r4);
        r4 = "PaymentCheckoutPhoneNumber";
        r5 = 2131232097; // 0x7f080561 float:1.8080294E38 double:1.0529685624E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r5 = 1;
        r1.setTextAndValue(r2, r4, r5);
        r1 = r12.linearLayout2;
        r2 = r12.detailSettingsCell;
        r4 = 4;
        r2 = r2[r4];
        r1.addView(r2);
    L_0x160e:
        r1 = r12.validateRequest;
        r1 = r1.info;
        r1 = r1.email;
        if (r1 == 0) goto L_0x1652;
    L_0x1616:
        r1 = r12.detailSettingsCell;
        r2 = 5;
        r4 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r4.<init>(r13);
        r1[r2] = r4;
        r1 = r12.detailSettingsCell;
        r2 = 5;
        r1 = r1[r2];
        r2 = "windowBackgroundWhite";
        r2 = org.telegram.ui.ActionBar.Theme.getColor(r2);
        r1.setBackgroundColor(r2);
        r1 = r12.detailSettingsCell;
        r2 = 5;
        r1 = r1[r2];
        r2 = r12.validateRequest;
        r2 = r2.info;
        r2 = r2.email;
        r4 = "PaymentCheckoutEmail";
        r5 = 2131232093; // 0x7f08055d float:1.8080286E38 double:1.0529685605E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r5 = 1;
        r1.setTextAndValue(r2, r4, r5);
        r1 = r12.linearLayout2;
        r2 = r12.detailSettingsCell;
        r4 = 5;
        r2 = r2[r4];
        r1.addView(r2);
    L_0x1652:
        r1 = r12.shippingOption;
        if (r1 == 0) goto L_0x1690;
    L_0x1656:
        r1 = r12.detailSettingsCell;
        r2 = 6;
        r4 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r4.<init>(r13);
        r1[r2] = r4;
        r1 = r12.detailSettingsCell;
        r2 = 6;
        r1 = r1[r2];
        r2 = "windowBackgroundWhite";
        r2 = org.telegram.ui.ActionBar.Theme.getColor(r2);
        r1.setBackgroundColor(r2);
        r1 = r12.detailSettingsCell;
        r2 = 6;
        r1 = r1[r2];
        r2 = r12.shippingOption;
        r2 = r2.title;
        r4 = "PaymentCheckoutShippingMethod";
        r5 = 2131232099; // 0x7f080563 float:1.8080298E38 double:1.0529685634E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r5 = 0;
        r1.setTextAndValue(r2, r4, r5);
        r1 = r12.linearLayout2;
        r2 = r12.detailSettingsCell;
        r4 = 6;
        r2 = r2[r4];
        r1.addView(r2);
    L_0x1690:
        r1 = r12.currentStep;
        r2 = 4;
        if (r1 != r2) goto L_0x17af;
    L_0x1695:
        r1 = new android.widget.FrameLayout;
        r1.<init>(r13);
        r12.bottomLayout = r1;
        r1 = r12.bottomLayout;
        r2 = 1;
        r2 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r2);
        r1.setBackgroundDrawable(r2);
        r1 = r12.bottomLayout;
        r2 = -1;
        r4 = 48;
        r5 = 80;
        r2 = org.telegram.ui.Components.LayoutHelper.createFrame(r2, r4, r5);
        r7.addView(r1, r2);
        r1 = r12.bottomLayout;
        r2 = new org.telegram.ui.PaymentFormActivity$21;
        r2.<init>(r0, r3);
        r1.setOnClickListener(r2);
        r0 = new android.widget.TextView;
        r0.<init>(r13);
        r12.payTextView = r0;
        r0 = r12.payTextView;
        r1 = "windowBackgroundWhiteBlueText6";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setTextColor(r1);
        r0 = r12.payTextView;
        r1 = "PaymentCheckoutPay";
        r2 = 2131232096; // 0x7f080560 float:1.8080292E38 double:1.052968562E-314;
        r4 = 1;
        r4 = new java.lang.Object[r4];
        r5 = 0;
        r4[r5] = r3;
        r1 = org.telegram.messenger.LocaleController.formatString(r1, r2, r4);
        r0.setText(r1);
        r0 = r12.payTextView;
        r1 = 1;
        r2 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r0.setTextSize(r1, r2);
        r0 = r12.payTextView;
        r1 = 17;
        r0.setGravity(r1);
        r0 = r12.payTextView;
        r1 = "fonts/rmedium.ttf";
        r1 = org.telegram.messenger.AndroidUtilities.getTypeface(r1);
        r0.setTypeface(r1);
        r0 = r12.bottomLayout;
        r1 = r12.payTextView;
        r2 = -1;
        r3 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r2 = org.telegram.ui.Components.LayoutHelper.createFrame(r2, r3);
        r0.addView(r1, r2);
        r0 = new org.telegram.ui.Components.ContextProgressView;
        r1 = 0;
        r0.<init>(r13, r1);
        r12.progressViewButton = r0;
        r0 = r12.progressViewButton;
        r1 = 4;
        r0.setVisibility(r1);
        r0 = r12.bottomLayout;
        r1 = r12.progressViewButton;
        r2 = -1;
        r3 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r2 = org.telegram.ui.Components.LayoutHelper.createFrame(r2, r3);
        r0.addView(r1, r2);
        r8 = new android.view.View;
        r8.<init>(r13);
        r0 = 2130837861; // 0x7f020165 float:1.7280688E38 double:1.052773784E-314;
        r8.setBackgroundResource(r0);
        r0 = -1;
        r1 = 1077936128; // 0x40400000 float:3.0 double:5.325712093E-315;
        r2 = 83;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 1111490560; // 0x42400000 float:48.0 double:5.491493014E-315;
        r0 = org.telegram.ui.Components.LayoutHelper.createFrame(r0, r1, r2, r3, r4, r5, r6);
        r7.addView(r8, r0);
        r0 = r12.doneItem;
        r1 = 0;
        r0.setEnabled(r1);
        r0 = r12.doneItem;
        r0 = r0.getImageView();
        r1 = 4;
        r0.setVisibility(r1);
        r0 = new org.telegram.ui.PaymentFormActivity$22;
        r0.<init>(r13);
        r12.webView = r0;
        r0 = r12.webView;
        r1 = -1;
        r0.setBackgroundColor(r1);
        r0 = r12.webView;
        r0 = r0.getSettings();
        r1 = 1;
        r0.setJavaScriptEnabled(r1);
        r0 = r12.webView;
        r0 = r0.getSettings();
        r1 = 1;
        r0.setDomStorageEnabled(r1);
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 21;
        if (r0 < r1) goto L_0x1792;
    L_0x177e:
        r0 = r12.webView;
        r0 = r0.getSettings();
        r1 = 0;
        r0.setMixedContentMode(r1);
        r0 = android.webkit.CookieManager.getInstance();
        r1 = r12.webView;
        r2 = 1;
        r0.setAcceptThirdPartyCookies(r1, r2);
    L_0x1792:
        r0 = r12.webView;
        r1 = new org.telegram.ui.PaymentFormActivity$23;
        r1.<init>();
        r0.setWebViewClient(r1);
        r0 = r12.webView;
        r1 = -1;
        r2 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r1 = org.telegram.ui.Components.LayoutHelper.createFrame(r1, r2);
        r7.addView(r0, r1);
        r0 = r12.webView;
        r1 = 8;
        r0.setVisibility(r1);
    L_0x17af:
        r0 = r12.sectionCell;
        r1 = 1;
        r2 = new org.telegram.ui.Cells.ShadowSectionCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.sectionCell;
        r1 = 1;
        r0 = r0[r1];
        r1 = 2130837850; // 0x7f02015a float:1.7280666E38 double:1.0527737785E-314;
        r2 = "windowBackgroundGrayShadow";
        r1 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r13, r1, r2);
        r0.setBackgroundDrawable(r1);
        r0 = r12.linearLayout2;
        r1 = r12.sectionCell;
        r2 = 1;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        goto L_0x0a03;
    L_0x17dd:
        r0 = "";
        goto L_0x14f0;
    L_0x17e2:
        r0 = r12.currentStep;
        r1 = 6;
        if (r0 != r1) goto L_0x0a03;
    L_0x17e7:
        r0 = r12.bottomCell;
        r1 = 2;
        r2 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.bottomCell;
        r1 = 2;
        r0 = r0[r1];
        r1 = 2130837849; // 0x7f020159 float:1.7280664E38 double:1.052773778E-314;
        r2 = "windowBackgroundGrayShadow";
        r1 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r13, r1, r2);
        r0.setBackgroundDrawable(r1);
        r0 = r12.linearLayout2;
        r1 = r12.bottomCell;
        r2 = 2;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = new org.telegram.ui.Cells.TextSettingsCell;
        r0.<init>(r13);
        r12.settingsCell1 = r0;
        r0 = r12.settingsCell1;
        r1 = 1;
        r1 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r1);
        r0.setBackgroundDrawable(r1);
        r0 = r12.settingsCell1;
        r1 = "windowBackgroundWhiteRedText3";
        r0.setTag(r1);
        r0 = r12.settingsCell1;
        r1 = "windowBackgroundWhiteRedText3";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setTextColor(r1);
        r0 = r12.settingsCell1;
        r1 = "AbortPassword";
        r2 = 2131230774; // 0x7f080036 float:1.807761E38 double:1.052967909E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r2 = 0;
        r0.setText(r1, r2);
        r0 = r12.linearLayout2;
        r1 = r12.settingsCell1;
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        r0 = r12.settingsCell1;
        r1 = new org.telegram.ui.PaymentFormActivity$24;
        r1.<init>();
        r0.setOnClickListener(r1);
        r0 = 3;
        r0 = new org.telegram.ui.Components.EditTextBoldCursor[r0];
        r12.inputFields = r0;
        r0 = 0;
        r7 = r0;
    L_0x1866:
        r0 = 3;
        if (r7 >= r0) goto L_0x1ac5;
    L_0x1869:
        if (r7 != 0) goto L_0x19f3;
    L_0x186b:
        r0 = r12.headerCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.HeaderCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.headerCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = "windowBackgroundWhite";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r0 = r12.headerCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = "PaymentPasswordTitle";
        r2 = 2131232922; // 0x7f08089a float:1.8081967E38 double:1.05296897E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setText(r1);
        r0 = r12.linearLayout2;
        r1 = r12.headerCell;
        r2 = 0;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
    L_0x18a6:
        r8 = new android.widget.FrameLayout;
        r8.<init>(r13);
        r0 = r12.linearLayout2;
        r1 = -1;
        r2 = 48;
        r1 = org.telegram.ui.Components.LayoutHelper.createLinear(r1, r2);
        r0.addView(r8, r1);
        r0 = "windowBackgroundWhite";
        r0 = org.telegram.ui.ActionBar.Theme.getColor(r0);
        r8.setBackgroundColor(r0);
        if (r7 != 0) goto L_0x18e3;
    L_0x18c3:
        r0 = new android.view.View;
        r0.<init>(r13);
        r1 = r12.dividers;
        r1.add(r0);
        r1 = "divider";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r1 = new android.widget.FrameLayout$LayoutParams;
        r2 = -1;
        r3 = 1;
        r4 = 83;
        r1.<init>(r2, r3, r4);
        r8.addView(r0, r1);
    L_0x18e3:
        r0 = r12.inputFields;
        r1 = new org.telegram.ui.Components.EditTextBoldCursor;
        r1.<init>(r13);
        r0[r7] = r1;
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = java.lang.Integer.valueOf(r7);
        r0.setTag(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 1;
        r2 = 1098907648; // 0x41800000 float:16.0 double:5.42932517E-315;
        r0.setTextSize(r1, r2);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = "windowBackgroundWhiteHintText";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setHintTextColor(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = "windowBackgroundWhiteBlackText";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setTextColor(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 0;
        r0.setBackgroundDrawable(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = "windowBackgroundWhiteBlackText";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setCursorColor(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r1 = org.telegram.messenger.AndroidUtilities.dp(r1);
        r0.setCursorSize(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 1069547520; // 0x3fc00000 float:1.5 double:5.28426686E-315;
        r0.setCursorWidth(r1);
        if (r7 == 0) goto L_0x194e;
    L_0x194b:
        r0 = 1;
        if (r7 != r0) goto L_0x1a33;
    L_0x194e:
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 129; // 0x81 float:1.81E-43 double:6.37E-322;
        r0.setInputType(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = android.graphics.Typeface.DEFAULT;
        r0.setTypeface(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 268435461; // 0x10000005 float:2.5243564E-29 double:1.326247394E-315;
        r0.setImeOptions(r1);
    L_0x196a:
        switch(r7) {
            case 0: goto L_0x1a3f;
            case 1: goto L_0x1a59;
            case 2: goto L_0x1a6c;
            default: goto L_0x196d;
        };
    L_0x196d:
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0.setPadding(r1, r2, r3, r4);
        r0 = r12.inputFields;
        r1 = r0[r7];
        r0 = org.telegram.messenger.LocaleController.isRTL;
        if (r0 == 0) goto L_0x1a7f;
    L_0x1985:
        r0 = 5;
    L_0x1986:
        r1.setGravity(r0);
        r0 = r12.inputFields;
        r9 = r0[r7];
        r0 = -1;
        r1 = -1073741824; // 0xffffffffc0000000 float:-2.0 double:NaN;
        r2 = 51;
        r3 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r4 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r5 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r6 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r0 = org.telegram.ui.Components.LayoutHelper.createFrame(r0, r1, r2, r3, r4, r5, r6);
        r8.addView(r9, r0);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = new org.telegram.ui.PaymentFormActivity$25;
        r1.<init>();
        r0.setOnEditorActionListener(r1);
        r0 = 1;
        if (r7 != r0) goto L_0x1a82;
    L_0x19b0:
        r0 = r12.bottomCell;
        r1 = 0;
        r2 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.bottomCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = "PaymentPasswordInfo";
        r2 = 2131232920; // 0x7f080898 float:1.8081963E38 double:1.052968969E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setText(r1);
        r0 = r12.bottomCell;
        r1 = 0;
        r0 = r0[r1];
        r1 = 2130837849; // 0x7f020159 float:1.7280664E38 double:1.052773778E-314;
        r2 = "windowBackgroundGrayShadow";
        r1 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r13, r1, r2);
        r0.setBackgroundDrawable(r1);
        r0 = r12.linearLayout2;
        r1 = r12.bottomCell;
        r2 = 0;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
    L_0x19ee:
        r0 = r7 + 1;
        r7 = r0;
        goto L_0x1866;
    L_0x19f3:
        r0 = 2;
        if (r7 != r0) goto L_0x18a6;
    L_0x19f6:
        r0 = r12.headerCell;
        r1 = 1;
        r2 = new org.telegram.ui.Cells.HeaderCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.headerCell;
        r1 = 1;
        r0 = r0[r1];
        r1 = "windowBackgroundWhite";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r0 = r12.headerCell;
        r1 = 1;
        r0 = r0[r1];
        r1 = "PaymentPasswordEmailTitle";
        r2 = 2131232918; // 0x7f080896 float:1.8081959E38 double:1.052968968E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setText(r1);
        r0 = r12.linearLayout2;
        r1 = r12.headerCell;
        r2 = 1;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        goto L_0x18a6;
    L_0x1a33:
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r0.setImeOptions(r1);
        goto L_0x196a;
    L_0x1a3f:
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = "PaymentPasswordEnter";
        r2 = 2131232919; // 0x7f080897 float:1.808196E38 double:1.0529689686E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        r0 = r12.inputFields;
        r0 = r0[r7];
        r0.requestFocus();
        goto L_0x196d;
    L_0x1a59:
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = "PaymentPasswordReEnter";
        r2 = 2131232921; // 0x7f080899 float:1.8081965E38 double:1.0529689696E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        goto L_0x196d;
    L_0x1a6c:
        r0 = r12.inputFields;
        r0 = r0[r7];
        r1 = "PaymentPasswordEmail";
        r2 = 2131232916; // 0x7f080894 float:1.8081955E38 double:1.052968967E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setHint(r1);
        goto L_0x196d;
    L_0x1a7f:
        r0 = 3;
        goto L_0x1986;
    L_0x1a82:
        r0 = 2;
        if (r7 != r0) goto L_0x19ee;
    L_0x1a85:
        r0 = r12.bottomCell;
        r1 = 1;
        r2 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r2.<init>(r13);
        r0[r1] = r2;
        r0 = r12.bottomCell;
        r1 = 1;
        r0 = r0[r1];
        r1 = "PaymentPasswordEmailInfo";
        r2 = 2131232917; // 0x7f080895 float:1.8081957E38 double:1.0529689676E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setText(r1);
        r0 = r12.bottomCell;
        r1 = 1;
        r0 = r0[r1];
        r1 = 2130837850; // 0x7f02015a float:1.7280666E38 double:1.0527737785E-314;
        r2 = "windowBackgroundGrayShadow";
        r1 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r13, r1, r2);
        r0.setBackgroundDrawable(r1);
        r0 = r12.linearLayout2;
        r1 = r12.bottomCell;
        r2 = 1;
        r1 = r1[r2];
        r2 = -1;
        r3 = -2;
        r2 = org.telegram.ui.Components.LayoutHelper.createLinear(r2, r3);
        r0.addView(r1, r2);
        goto L_0x19ee;
    L_0x1ac5:
        r12.updatePasswordFields();
        goto L_0x0a03;
    L_0x1aca:
        r0 = r1;
        goto L_0x14ad;
    L_0x1acd:
        r0 = r1;
        goto L_0x0343;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.PaymentFormActivity.createView(android.content.Context):android.view.View");
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.didSetTwoStepPassword) {
            this.paymentForm.password_missing = false;
            this.paymentForm.can_save_credentials = true;
            updateSavePaymentField();
        } else if (i == NotificationCenter.didRemovedTwoStepPassword) {
            this.paymentForm.password_missing = true;
            this.paymentForm.can_save_credentials = false;
            updateSavePaymentField();
        } else if (i == NotificationCenter.paymentFinished) {
            removeSelfFromStack();
        }
    }

    @SuppressLint({"HardwareIds"})
    public void fillNumber(String str) {
        int i = 4;
        Object obj = 1;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
            if (str != null || (telephonyManager.getSimState() != 1 && telephonyManager.getPhoneType() != 0)) {
                if (VERSION.SDK_INT >= 23) {
                    Object obj2 = getParentActivity().checkSelfPermission("android.permission.READ_PHONE_STATE") == 0 ? 1 : null;
                    Object obj3 = getParentActivity().checkSelfPermission("android.permission.RECEIVE_SMS") == 0 ? 1 : null;
                } else {
                    int i2 = 1;
                    int i3 = 1;
                }
                if (str != null || r5 != null || r1 != null) {
                    if (str == null) {
                        str = C2488b.b(telephonyManager.getLine1Number());
                    }
                    if (!TextUtils.isEmpty(str)) {
                        CharSequence substring;
                        if (str.length() > 4) {
                            while (i >= 1) {
                                CharSequence substring2 = str.substring(0, i);
                                if (((String) this.codesMap.get(substring2)) != null) {
                                    substring = str.substring(i, str.length());
                                    this.inputFields[8].setText(substring2);
                                    break;
                                }
                                i--;
                            }
                            obj = null;
                            substring = null;
                            if (obj == null) {
                                substring = str.substring(1, str.length());
                                this.inputFields[8].setText(str.substring(0, 1));
                            }
                        } else {
                            substring = null;
                        }
                        if (substring != null) {
                            this.inputFields[9].setText(substring);
                            this.inputFields[9].setSelection(this.inputFields[9].length());
                        }
                    }
                }
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        int i;
        int i2;
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault));
        arrayList.add(new ThemeDescription(this.scrollView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCH, null, null, null, null, Theme.key_actionBarDefaultSearch));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCHPLACEHOLDER, null, null, null, null, Theme.key_actionBarDefaultSearchPlaceholder));
        arrayList.add(new ThemeDescription(this.linearLayout2, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        arrayList.add(new ThemeDescription(this.progressView, 0, null, null, null, null, Theme.key_contextProgressInner2));
        arrayList.add(new ThemeDescription(this.progressView, 0, null, null, null, null, Theme.key_contextProgressOuter2));
        arrayList.add(new ThemeDescription(this.progressViewButton, 0, null, null, null, null, Theme.key_contextProgressInner2));
        arrayList.add(new ThemeDescription(this.progressViewButton, 0, null, null, null, null, Theme.key_contextProgressOuter2));
        if (this.inputFields != null) {
            for (i = 0; i < this.inputFields.length; i++) {
                arrayList.add(new ThemeDescription((View) this.inputFields[i].getParent(), ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
                arrayList.add(new ThemeDescription(this.inputFields[i], ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
                arrayList.add(new ThemeDescription(this.inputFields[i], ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText));
            }
        } else {
            arrayList.add(new ThemeDescription(null, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
            arrayList.add(new ThemeDescription(null, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText));
        }
        if (this.radioCells != null) {
            for (i2 = 0; i2 < this.radioCells.length; i2++) {
                arrayList.add(new ThemeDescription(this.radioCells[i2], ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_windowBackgroundWhite));
                arrayList.add(new ThemeDescription(this.radioCells[i2], ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_listSelector));
                arrayList.add(new ThemeDescription(this.radioCells[i2], 0, new Class[]{RadioCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
                arrayList.add(new ThemeDescription(this.radioCells[i2], ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground));
                arrayList.add(new ThemeDescription(this.radioCells[i2], ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked));
            }
        } else {
            arrayList.add(new ThemeDescription(null, 0, new Class[]{RadioCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
            arrayList.add(new ThemeDescription(null, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground));
            arrayList.add(new ThemeDescription(null, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked));
        }
        for (i2 = 0; i2 < this.headerCell.length; i2++) {
            arrayList.add(new ThemeDescription(this.headerCell[i2], ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
            arrayList.add(new ThemeDescription(this.headerCell[i2], 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader));
        }
        for (View themeDescription : this.sectionCell) {
            arrayList.add(new ThemeDescription(themeDescription, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow));
        }
        for (i2 = 0; i2 < this.bottomCell.length; i2++) {
            arrayList.add(new ThemeDescription(this.bottomCell[i2], ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow));
            arrayList.add(new ThemeDescription(this.bottomCell[i2], 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4));
            arrayList.add(new ThemeDescription(this.bottomCell[i2], ThemeDescription.FLAG_LINKCOLOR, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteLinkText));
        }
        for (i = 0; i < this.dividers.size(); i++) {
            arrayList.add(new ThemeDescription((View) this.dividers.get(i), ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_divider));
        }
        arrayList.add(new ThemeDescription(this.textView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.checkCell1, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.checkCell1, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumb));
        arrayList.add(new ThemeDescription(this.checkCell1, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack));
        arrayList.add(new ThemeDescription(this.checkCell1, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumbChecked));
        arrayList.add(new ThemeDescription(this.checkCell1, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked));
        arrayList.add(new ThemeDescription(this.checkCell1, ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.checkCell1, ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_listSelector));
        arrayList.add(new ThemeDescription(this.settingsCell1, ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.settingsCell1, ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_listSelector));
        arrayList.add(new ThemeDescription(this.settingsCell1, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.payTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText6));
        arrayList.add(new ThemeDescription(this.linearLayout2, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextPriceCell.class}, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.linearLayout2, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextPriceCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.linearLayout2, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextPriceCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.linearLayout2, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextPriceCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2));
        arrayList.add(new ThemeDescription(this.linearLayout2, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextPriceCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2));
        arrayList.add(new ThemeDescription(this.detailSettingsCell[0], ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.detailSettingsCell[0], ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_listSelector));
        for (i2 = 1; i2 < this.detailSettingsCell.length; i2++) {
            arrayList.add(new ThemeDescription(this.detailSettingsCell[i2], ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
            arrayList.add(new ThemeDescription(this.detailSettingsCell[i2], 0, new Class[]{TextDetailSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
            arrayList.add(new ThemeDescription(this.detailSettingsCell[i2], 0, new Class[]{TextDetailSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2));
        }
        arrayList.add(new ThemeDescription(this.paymentInfoCell, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.paymentInfoCell, 0, new Class[]{PaymentInfoCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.paymentInfoCell, 0, new Class[]{PaymentInfoCell.class}, new String[]{"detailTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.paymentInfoCell, 0, new Class[]{PaymentInfoCell.class}, new String[]{"detailExTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2));
        arrayList.add(new ThemeDescription(this.bottomLayout, ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.bottomLayout, ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_listSelector));
        return (ThemeDescription[]) arrayList.toArray(new ThemeDescription[arrayList.size()]);
    }

    public void onActivityResultFragment(int i, int i2, Intent intent) {
        if (i == 1000) {
            if (i2 == -1) {
                showEditDoneProgress(true, true);
                setDonePressed(true);
                MaskedWallet maskedWallet = (MaskedWallet) intent.getParcelableExtra(WalletConstants.EXTRA_MASKED_WALLET);
                Cart.Builder totalPrice = Cart.newBuilder().setCurrencyCode(this.paymentForm.invoice.currency).setTotalPrice(this.totalPriceDecimal);
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(this.paymentForm.invoice.prices);
                if (this.shippingOption != null) {
                    arrayList.addAll(this.shippingOption.prices);
                }
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    TLRPC$TL_labeledPrice tLRPC$TL_labeledPrice = (TLRPC$TL_labeledPrice) arrayList.get(i3);
                    String formatCurrencyDecimalString = LocaleController.getInstance().formatCurrencyDecimalString(tLRPC$TL_labeledPrice.amount, this.paymentForm.invoice.currency, false);
                    totalPrice.addLineItem(LineItem.newBuilder().setCurrencyCode(this.paymentForm.invoice.currency).setQuantity("1").setDescription(tLRPC$TL_labeledPrice.label).setTotalPrice(formatCurrencyDecimalString).setUnitPrice(formatCurrencyDecimalString).build());
                }
                Wallet.Payments.loadFullWallet(this.googleApiClient, FullWalletRequest.newBuilder().setCart(totalPrice.build()).setGoogleTransactionId(maskedWallet.getGoogleTransactionId()).build(), LOAD_FULL_WALLET_REQUEST_CODE);
                return;
            }
            showEditDoneProgress(true, false);
            setDonePressed(false);
        } else if (i != LOAD_FULL_WALLET_REQUEST_CODE) {
        } else {
            if (i2 == -1) {
                FullWallet fullWallet = (FullWallet) intent.getParcelableExtra(WalletConstants.EXTRA_FULL_WALLET);
                String token = fullWallet.getPaymentMethodToken().getToken();
                try {
                    if (this.androidPayPublicKey != null) {
                        this.androidPayCredentials = new TLRPC$TL_inputPaymentCredentialsAndroidPay();
                        this.androidPayCredentials.payment_token = new TLRPC$TL_dataJSON();
                        this.androidPayCredentials.payment_token.data = token;
                        String[] paymentDescriptions = fullWallet.getPaymentDescriptions();
                        if (paymentDescriptions.length > 0) {
                            this.cardName = paymentDescriptions[0];
                        } else {
                            this.cardName = "Android Pay";
                        }
                    } else {
                        this.paymentJson = String.format(Locale.US, "{\"type\":\"%1$s\", \"id\":\"%2$s\"}", new Object[]{r0.b(), C1990g.a(token).a()});
                        C1977a c = r0.c();
                        this.cardName = c.s() + " *" + c.r();
                    }
                    goToNextStep();
                    showEditDoneProgress(true, false);
                    setDonePressed(false);
                    return;
                } catch (JSONException e) {
                    showEditDoneProgress(true, false);
                    setDonePressed(false);
                    return;
                }
            }
            showEditDoneProgress(true, false);
            setDonePressed(false);
        }
    }

    public boolean onBackPressed() {
        return !this.donePressed;
    }

    public boolean onFragmentCreate() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didSetTwoStepPassword);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didRemovedTwoStepPassword);
        if (this.currentStep != 4) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.paymentFinished);
        }
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        if (this.delegate != null) {
            this.delegate.onFragmentDestroyed();
        }
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didSetTwoStepPassword);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didRemovedTwoStepPassword);
        if (this.currentStep != 4) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.paymentFinished);
        }
        if (this.webView != null) {
            try {
                ViewParent parent = this.webView.getParent();
                if (parent != null) {
                    ((FrameLayout) parent).removeView(this.webView);
                }
                this.webView.stopLoading();
                this.webView.loadUrl("about:blank");
                this.webView.destroy();
                this.webView = null;
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
        try {
            if ((this.currentStep == 2 || this.currentStep == 6) && VERSION.SDK_INT >= 23 && (UserConfig.passcodeHash.length() == 0 || UserConfig.allowScreenCapture)) {
                getParentActivity().getWindow().clearFlags(MessagesController.UPDATE_MASK_CHANNEL);
            }
        } catch (Throwable e2) {
            FileLog.e(e2);
        }
        super.onFragmentDestroy();
        this.canceled = true;
    }

    public void onPause() {
        if (this.googleApiClient != null) {
            this.googleApiClient.disconnect();
        }
    }

    public void onResume() {
        super.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
        if (VERSION.SDK_INT >= 23) {
            try {
                if ((this.currentStep == 2 || this.currentStep == 6) && !this.paymentForm.invoice.test) {
                    getParentActivity().getWindow().setFlags(MessagesController.UPDATE_MASK_CHANNEL, MessagesController.UPDATE_MASK_CHANNEL);
                } else if (UserConfig.passcodeHash.length() == 0 || UserConfig.allowScreenCapture) {
                    getParentActivity().getWindow().clearFlags(MessagesController.UPDATE_MASK_CHANNEL);
                }
            } catch (Throwable th) {
                FileLog.e(th);
            }
        }
        if (this.googleApiClient != null) {
            this.googleApiClient.connect();
        }
    }

    protected void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z && !z2) {
            if (this.webView != null) {
                if (this.currentStep != 4) {
                    this.webView.loadUrl(this.paymentForm.url);
                }
            } else if (this.currentStep == 2) {
                this.inputFields[0].requestFocus();
                AndroidUtilities.showKeyboard(this.inputFields[0]);
            } else if (this.currentStep == 3) {
                this.inputFields[1].requestFocus();
                AndroidUtilities.showKeyboard(this.inputFields[1]);
            } else if (this.currentStep == 6 && !this.waitingForEmail) {
                this.inputFields[0].requestFocus();
                AndroidUtilities.showKeyboard(this.inputFields[0]);
            }
        }
    }
}
