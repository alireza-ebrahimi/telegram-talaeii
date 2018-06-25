package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
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
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
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
import com.persianswitch.sdk.base.log.LogCollector;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.exception.APIConnectionException;
import com.stripe.android.exception.APIException;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.net.StripeApiHandler;
import com.stripe.android.net.TokenParser;
import com.thin.downloadmanager.BuildConfig;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import org.ir.talaeii.R;
import org.json.JSONException;
import org.telegram.PhoneFormat.PhoneFormat;
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
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_account_getPassword;
import org.telegram.tgnet.TLRPC$TL_account_getTmpPassword;
import org.telegram.tgnet.TLRPC$TL_account_noPassword;
import org.telegram.tgnet.TLRPC$TL_account_password;
import org.telegram.tgnet.TLRPC$TL_account_passwordInputSettings;
import org.telegram.tgnet.TLRPC$TL_account_tmpPassword;
import org.telegram.tgnet.TLRPC$TL_account_updatePasswordSettings;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
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
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
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
    class C31551 extends ActionBarMenuOnItemClick {
        C31551() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                if (!PaymentFormActivity.this.donePressed) {
                    PaymentFormActivity.this.finishFragment();
                }
            } else if (id == 1 && !PaymentFormActivity.this.donePressed) {
                if (PaymentFormActivity.this.currentStep != 3) {
                    AndroidUtilities.hideKeyboard(PaymentFormActivity.this.getParentActivity().getCurrentFocus());
                }
                if (PaymentFormActivity.this.currentStep == 0) {
                    PaymentFormActivity.this.setDonePressed(true);
                    PaymentFormActivity.this.sendForm();
                } else if (PaymentFormActivity.this.currentStep == 1) {
                    for (int a = 0; a < PaymentFormActivity.this.radioCells.length; a++) {
                        if (PaymentFormActivity.this.radioCells[a].isChecked()) {
                            PaymentFormActivity.this.shippingOption = (TLRPC$TL_shippingOption) PaymentFormActivity.this.requestedInfo.shipping_options.get(a);
                            break;
                        }
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
    class C31612 implements Comparator<String> {
        C31612() {
        }

        public int compare(String lhs, String rhs) {
            return lhs.compareTo(rhs);
        }
    }

    /* renamed from: org.telegram.ui.PaymentFormActivity$3 */
    class C31753 implements OnTouchListener {

        /* renamed from: org.telegram.ui.PaymentFormActivity$3$1 */
        class C31621 implements CountrySelectActivityDelegate {
            C31621() {
            }

            public void didSelectCountry(String name, String shortName) {
                PaymentFormActivity.this.inputFields[4].setText(name);
                PaymentFormActivity.this.countryName = shortName;
            }
        }

        C31753() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (PaymentFormActivity.this.getParentActivity() == null) {
                return false;
            }
            if (event.getAction() == 1) {
                CountrySelectActivity fragment = new CountrySelectActivity(false);
                fragment.setCountrySelectActivityDelegate(new C31621());
                PaymentFormActivity.this.presentFragment(fragment);
            }
            return true;
        }
    }

    /* renamed from: org.telegram.ui.PaymentFormActivity$4 */
    class C31764 implements TextWatcher {
        C31764() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void afterTextChanged(Editable editable) {
            if (!PaymentFormActivity.this.ignoreOnTextChange) {
                PaymentFormActivity.this.ignoreOnTextChange = true;
                String text = PhoneFormat.stripExceptNumbers(PaymentFormActivity.this.inputFields[8].getText().toString());
                PaymentFormActivity.this.inputFields[8].setText(text);
                HintEditText phoneField = PaymentFormActivity.this.inputFields[9];
                if (text.length() == 0) {
                    phoneField.setHintText(null);
                    phoneField.setHint(LocaleController.getString("PaymentShippingPhoneNumber", R.string.PaymentShippingPhoneNumber));
                } else {
                    boolean ok = false;
                    String textToSet = null;
                    if (text.length() > 4) {
                        for (int a = 4; a >= 1; a--) {
                            String sub = text.substring(0, a);
                            if (((String) PaymentFormActivity.this.codesMap.get(sub)) != null) {
                                ok = true;
                                textToSet = text.substring(a, text.length()) + PaymentFormActivity.this.inputFields[9].getText().toString();
                                text = sub;
                                PaymentFormActivity.this.inputFields[8].setText(sub);
                                break;
                            }
                        }
                        if (!ok) {
                            textToSet = text.substring(1, text.length()) + PaymentFormActivity.this.inputFields[9].getText().toString();
                            EditTextBoldCursor editTextBoldCursor = PaymentFormActivity.this.inputFields[8];
                            text = text.substring(0, 1);
                            editTextBoldCursor.setText(text);
                        }
                    }
                    String country = (String) PaymentFormActivity.this.codesMap.get(text);
                    boolean set = false;
                    if (!(country == null || PaymentFormActivity.this.countriesArray.indexOf(country) == -1)) {
                        String hint = (String) PaymentFormActivity.this.phoneFormatMap.get(text);
                        if (hint != null) {
                            set = true;
                            phoneField.setHintText(hint.replace('X', '–'));
                            phoneField.setHint(null);
                        }
                    }
                    if (!set) {
                        phoneField.setHintText(null);
                        phoneField.setHint(LocaleController.getString("PaymentShippingPhoneNumber", R.string.PaymentShippingPhoneNumber));
                    }
                    if (!ok) {
                        PaymentFormActivity.this.inputFields[8].setSelection(PaymentFormActivity.this.inputFields[8].getText().length());
                    }
                    if (textToSet != null) {
                        phoneField.requestFocus();
                        phoneField.setText(textToSet);
                        phoneField.setSelection(phoneField.length());
                    }
                }
                PaymentFormActivity.this.ignoreOnTextChange = false;
            }
        }
    }

    /* renamed from: org.telegram.ui.PaymentFormActivity$5 */
    class C31775 implements TextWatcher {
        private int actionPosition;
        private int characterAction = -1;

        C31775() {
        }

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
            if (!PaymentFormActivity.this.ignoreOnPhoneChange) {
                int a;
                HintEditText phoneField = PaymentFormActivity.this.inputFields[9];
                int start = phoneField.getSelectionStart();
                String phoneChars = "0123456789";
                String str = phoneField.getText().toString();
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
                PaymentFormActivity.this.ignoreOnPhoneChange = true;
                String hint = phoneField.getHintText();
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
                phoneField.setText(builder);
                if (start >= 0) {
                    if (start > phoneField.length()) {
                        start = phoneField.length();
                    }
                    phoneField.setSelection(start);
                }
                phoneField.onTextChange();
                PaymentFormActivity.this.ignoreOnPhoneChange = false;
            }
        }
    }

    /* renamed from: org.telegram.ui.PaymentFormActivity$6 */
    class C31786 implements OnEditorActionListener {
        C31786() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == 5) {
                int num = ((Integer) textView.getTag()).intValue();
                while (num + 1 < PaymentFormActivity.this.inputFields.length) {
                    num++;
                    if (num != 4 && ((View) PaymentFormActivity.this.inputFields[num].getParent()).getVisibility() == 0) {
                        PaymentFormActivity.this.inputFields[num].requestFocus();
                        break;
                    }
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
    class C31797 implements OnClickListener {
        C31797() {
        }

        public void onClick(View v) {
            PaymentFormActivity.this.saveShippingInfo = !PaymentFormActivity.this.saveShippingInfo;
            PaymentFormActivity.this.checkCell1.setChecked(PaymentFormActivity.this.saveShippingInfo);
        }
    }

    /* renamed from: org.telegram.ui.PaymentFormActivity$9 */
    class C31819 extends WebViewClient {
        C31819() {
        }

        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            PaymentFormActivity.this.webviewLoading = false;
            PaymentFormActivity.this.showEditDoneProgress(true, false);
            PaymentFormActivity.this.updateSavePaymentField();
        }
    }

    private static class LinkMovementMethodMy extends LinkMovementMethod {
        private LinkMovementMethodMy() {
        }

        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            try {
                boolean result = super.onTouchEvent(widget, buffer, event);
                if (event.getAction() != 1 && event.getAction() != 3) {
                    return result;
                }
                Selection.removeSelection(buffer);
                return result;
            } catch (Exception e) {
                FileLog.e(e);
                return false;
            }
        }
    }

    public class LinkSpan extends ClickableSpan {
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }

        public void onClick(View widget) {
            PaymentFormActivity.this.presentFragment(new TwoStepVerificationActivity(0));
        }
    }

    public PaymentFormActivity(MessageObject message, TLRPC$TL_payments_paymentReceipt receipt) {
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
        this.paymentForm.bot_id = receipt.bot_id;
        this.paymentForm.invoice = receipt.invoice;
        this.paymentForm.provider_id = receipt.provider_id;
        this.paymentForm.users = receipt.users;
        this.shippingOption = receipt.shipping;
        this.messageObject = message;
        this.botUser = MessagesController.getInstance().getUser(Integer.valueOf(receipt.bot_id));
        if (this.botUser != null) {
            this.currentBotName = this.botUser.first_name;
        } else {
            this.currentBotName = "";
        }
        this.currentItemName = message.messageOwner.media.title;
        if (receipt.info != null) {
            this.validateRequest = new TLRPC$TL_payments_validateRequestedInfo();
            this.validateRequest.info = receipt.info;
        }
        this.cardName = receipt.credentials_title;
    }

    public PaymentFormActivity(TLRPC$TL_payments_paymentForm form, MessageObject message) {
        int step;
        this.countriesArray = new ArrayList();
        this.countriesMap = new HashMap();
        this.codesMap = new HashMap();
        this.phoneFormatMap = new HashMap();
        this.headerCell = new HeaderCell[3];
        this.dividers = new ArrayList();
        this.sectionCell = new ShadowSectionCell[3];
        this.bottomCell = new TextInfoPrivacyCell[3];
        this.detailSettingsCell = new TextDetailSettingsCell[7];
        if (form.invoice.shipping_address_requested || form.invoice.email_requested || form.invoice.name_requested || form.invoice.phone_requested) {
            step = 0;
        } else if (form.saved_credentials != null) {
            if (UserConfig.tmpPassword != null && UserConfig.tmpPassword.valid_until < ConnectionsManager.getInstance().getCurrentTime() + 60) {
                UserConfig.tmpPassword = null;
                UserConfig.saveConfig(false);
            }
            if (UserConfig.tmpPassword != null) {
                step = 4;
            } else {
                step = 3;
            }
        } else {
            step = 2;
        }
        init(form, message, step, null, null, null, null, null, false, null);
    }

    private PaymentFormActivity(TLRPC$TL_payments_paymentForm form, MessageObject message, int step, TLRPC$TL_payments_validatedRequestedInfo validatedRequestedInfo, TLRPC$TL_shippingOption shipping, String tokenJson, String card, TLRPC$TL_payments_validateRequestedInfo request, boolean saveCard, TLRPC$TL_inputPaymentCredentialsAndroidPay androidPay) {
        this.countriesArray = new ArrayList();
        this.countriesMap = new HashMap();
        this.codesMap = new HashMap();
        this.phoneFormatMap = new HashMap();
        this.headerCell = new HeaderCell[3];
        this.dividers = new ArrayList();
        this.sectionCell = new ShadowSectionCell[3];
        this.bottomCell = new TextInfoPrivacyCell[3];
        this.detailSettingsCell = new TextDetailSettingsCell[7];
        init(form, message, step, validatedRequestedInfo, shipping, tokenJson, card, request, saveCard, androidPay);
    }

    private void setCurrentPassword(TLRPC$account_Password password) {
        if (!(password instanceof TLRPC$TL_account_password)) {
            this.currentPassword = password;
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

    private void init(TLRPC$TL_payments_paymentForm form, MessageObject message, int step, TLRPC$TL_payments_validatedRequestedInfo validatedRequestedInfo, TLRPC$TL_shippingOption shipping, String tokenJson, String card, TLRPC$TL_payments_validateRequestedInfo request, boolean saveCard, TLRPC$TL_inputPaymentCredentialsAndroidPay androidPay) {
        boolean z = true;
        this.currentStep = step;
        this.paymentJson = tokenJson;
        this.androidPayCredentials = androidPay;
        this.requestedInfo = validatedRequestedInfo;
        this.paymentForm = form;
        this.shippingOption = shipping;
        this.messageObject = message;
        this.saveCardInfo = saveCard;
        this.isWebView = !"stripe".equals(this.paymentForm.native_provider);
        this.botUser = MessagesController.getInstance().getUser(Integer.valueOf(form.bot_id));
        if (this.botUser != null) {
            this.currentBotName = this.botUser.first_name;
        } else {
            this.currentBotName = "";
        }
        this.currentItemName = message.messageOwner.media.title;
        this.validateRequest = request;
        this.saveShippingInfo = true;
        if (saveCard) {
            this.saveCardInfo = saveCard;
        } else {
            if (this.paymentForm.saved_credentials == null) {
                z = false;
            }
            this.saveCardInfo = z;
        }
        if (card != null) {
            this.cardName = card;
        } else if (form.saved_credentials != null) {
            this.cardName = form.saved_credentials.title;
        }
    }

    public void onResume() {
        super.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
        if (VERSION.SDK_INT >= 23) {
            try {
                if ((this.currentStep == 2 || this.currentStep == 6) && !this.paymentForm.invoice.test) {
                    getParentActivity().getWindow().setFlags(8192, 8192);
                } else if (UserConfig.passcodeHash.length() == 0 || UserConfig.allowScreenCapture) {
                    getParentActivity().getWindow().clearFlags(8192);
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
        if (this.googleApiClient != null) {
            this.googleApiClient.connect();
        }
    }

    public void onPause() {
        if (this.googleApiClient != null) {
            this.googleApiClient.disconnect();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.annotation.SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    public android.view.View createView(android.content.Context r48) {
        /*
        r47 = this;
        r0 = r47;
        r4 = r0.currentStep;
        if (r4 != 0) goto L_0x041c;
    L_0x0006:
        r0 = r47;
        r4 = r0.actionBar;
        r5 = "PaymentShippingInfo";
        r6 = 2131232113; // 0x7f080571 float:1.8080326E38 double:1.0529685703E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setTitle(r5);
    L_0x0017:
        r0 = r47;
        r4 = r0.actionBar;
        r5 = 2130837865; // 0x7f020169 float:1.7280696E38 double:1.052773786E-314;
        r4.setBackButtonImage(r5);
        r0 = r47;
        r4 = r0.actionBar;
        r5 = 1;
        r4.setAllowOverlayTitle(r5);
        r0 = r47;
        r4 = r0.actionBar;
        r5 = new org.telegram.ui.PaymentFormActivity$1;
        r0 = r47;
        r5.<init>();
        r4.setActionBarMenuOnItemClick(r5);
        r0 = r47;
        r4 = r0.actionBar;
        r32 = r4.createMenu();
        r0 = r47;
        r4 = r0.currentStep;
        if (r4 == 0) goto L_0x0068;
    L_0x0045:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 1;
        if (r4 == r5) goto L_0x0068;
    L_0x004c:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 2;
        if (r4 == r5) goto L_0x0068;
    L_0x0053:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 3;
        if (r4 == r5) goto L_0x0068;
    L_0x005a:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 4;
        if (r4 == r5) goto L_0x0068;
    L_0x0061:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 6;
        if (r4 != r5) goto L_0x00a2;
    L_0x0068:
        r4 = 1;
        r5 = 2130837912; // 0x7f020198 float:1.7280791E38 double:1.052773809E-314;
        r6 = 1113587712; // 0x42600000 float:56.0 double:5.50185432E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r0 = r32;
        r4 = r0.addItemWithWidth(r4, r5, r6);
        r0 = r47;
        r0.doneItem = r4;
        r4 = new org.telegram.ui.Components.ContextProgressView;
        r5 = 1;
        r0 = r48;
        r4.<init>(r0, r5);
        r0 = r47;
        r0.progressView = r4;
        r0 = r47;
        r4 = r0.doneItem;
        r0 = r47;
        r5 = r0.progressView;
        r6 = -1;
        r7 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r6 = org.telegram.ui.Components.LayoutHelper.createFrame(r6, r7);
        r4.addView(r5, r6);
        r0 = r47;
        r4 = r0.progressView;
        r5 = 4;
        r4.setVisibility(r5);
    L_0x00a2:
        r4 = new android.widget.FrameLayout;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.fragmentView = r4;
        r0 = r47;
        r0 = r0.fragmentView;
        r26 = r0;
        r26 = (android.widget.FrameLayout) r26;
        r0 = r47;
        r4 = r0.fragmentView;
        r5 = "windowBackgroundGray";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r4 = new android.widget.ScrollView;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.scrollView = r4;
        r0 = r47;
        r4 = r0.scrollView;
        r5 = 1;
        r4.setFillViewport(r5);
        r0 = r47;
        r4 = r0.scrollView;
        r5 = "actionBarDefault";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        org.telegram.messenger.AndroidUtilities.setScrollViewEdgeEffectColor(r4, r5);
        r0 = r47;
        r0 = r0.scrollView;
        r45 = r0;
        r4 = -1;
        r5 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r6 = 51;
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r0 = r47;
        r10 = r0.currentStep;
        r46 = 4;
        r0 = r46;
        if (r10 != r0) goto L_0x051a;
    L_0x00fc:
        r10 = 1111490560; // 0x42400000 float:48.0 double:5.491493014E-315;
    L_0x00fe:
        r4 = org.telegram.ui.Components.LayoutHelper.createFrame(r4, r5, r6, r7, r8, r9, r10);
        r0 = r26;
        r1 = r45;
        r0.addView(r1, r4);
        r4 = new android.widget.LinearLayout;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.linearLayout2 = r4;
        r0 = r47;
        r4 = r0.linearLayout2;
        r5 = 1;
        r4.setOrientation(r5);
        r0 = r47;
        r4 = r0.scrollView;
        r0 = r47;
        r5 = r0.linearLayout2;
        r6 = new android.widget.FrameLayout$LayoutParams;
        r7 = -1;
        r8 = -2;
        r6.<init>(r7, r8);
        r4.addView(r5, r6);
        r0 = r47;
        r4 = r0.currentStep;
        if (r4 != 0) goto L_0x0cce;
    L_0x0134:
        r30 = new java.util.HashMap;
        r30.<init>();
        r22 = new java.util.HashMap;
        r22.<init>();
        r38 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x01b5 }
        r4 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x01b5 }
        r5 = r48.getResources();	 Catch:{ Exception -> 0x01b5 }
        r5 = r5.getAssets();	 Catch:{ Exception -> 0x01b5 }
        r6 = "countries.txt";
        r5 = r5.open(r6);	 Catch:{ Exception -> 0x01b5 }
        r4.<init>(r5);	 Catch:{ Exception -> 0x01b5 }
        r0 = r38;
        r0.<init>(r4);	 Catch:{ Exception -> 0x01b5 }
    L_0x0159:
        r31 = r38.readLine();	 Catch:{ Exception -> 0x01b5 }
        if (r31 == 0) goto L_0x051d;
    L_0x015f:
        r4 = ";";
        r0 = r31;
        r15 = r0.split(r4);	 Catch:{ Exception -> 0x01b5 }
        r0 = r47;
        r4 = r0.countriesArray;	 Catch:{ Exception -> 0x01b5 }
        r5 = 0;
        r6 = 2;
        r6 = r15[r6];	 Catch:{ Exception -> 0x01b5 }
        r4.add(r5, r6);	 Catch:{ Exception -> 0x01b5 }
        r0 = r47;
        r4 = r0.countriesMap;	 Catch:{ Exception -> 0x01b5 }
        r5 = 2;
        r5 = r15[r5];	 Catch:{ Exception -> 0x01b5 }
        r6 = 0;
        r6 = r15[r6];	 Catch:{ Exception -> 0x01b5 }
        r4.put(r5, r6);	 Catch:{ Exception -> 0x01b5 }
        r0 = r47;
        r4 = r0.codesMap;	 Catch:{ Exception -> 0x01b5 }
        r5 = 0;
        r5 = r15[r5];	 Catch:{ Exception -> 0x01b5 }
        r6 = 2;
        r6 = r15[r6];	 Catch:{ Exception -> 0x01b5 }
        r4.put(r5, r6);	 Catch:{ Exception -> 0x01b5 }
        r4 = 1;
        r4 = r15[r4];	 Catch:{ Exception -> 0x01b5 }
        r5 = 2;
        r5 = r15[r5];	 Catch:{ Exception -> 0x01b5 }
        r0 = r22;
        r0.put(r4, r5);	 Catch:{ Exception -> 0x01b5 }
        r4 = r15.length;	 Catch:{ Exception -> 0x01b5 }
        r5 = 3;
        if (r4 <= r5) goto L_0x01a9;
    L_0x019c:
        r0 = r47;
        r4 = r0.phoneFormatMap;	 Catch:{ Exception -> 0x01b5 }
        r5 = 0;
        r5 = r15[r5];	 Catch:{ Exception -> 0x01b5 }
        r6 = 3;
        r6 = r15[r6];	 Catch:{ Exception -> 0x01b5 }
        r4.put(r5, r6);	 Catch:{ Exception -> 0x01b5 }
    L_0x01a9:
        r4 = 1;
        r4 = r15[r4];	 Catch:{ Exception -> 0x01b5 }
        r5 = 2;
        r5 = r15[r5];	 Catch:{ Exception -> 0x01b5 }
        r0 = r30;
        r0.put(r4, r5);	 Catch:{ Exception -> 0x01b5 }
        goto L_0x0159;
    L_0x01b5:
        r25 = move-exception;
        org.telegram.messenger.FileLog.e(r25);
    L_0x01b9:
        r0 = r47;
        r4 = r0.countriesArray;
        r5 = new org.telegram.ui.PaymentFormActivity$2;
        r0 = r47;
        r5.<init>();
        java.util.Collections.sort(r4, r5);
        r4 = 10;
        r4 = new org.telegram.ui.Components.EditTextBoldCursor[r4];
        r0 = r47;
        r0.inputFields = r4;
        r11 = 0;
    L_0x01d0:
        r4 = 10;
        if (r11 >= r4) goto L_0x0a49;
    L_0x01d4:
        if (r11 != 0) goto L_0x0522;
    L_0x01d6:
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.HeaderCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = "PaymentShippingAddress";
        r6 = 2131232107; // 0x7f08056b float:1.8080314E38 double:1.0529685674E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setText(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.headerCell;
        r6 = 0;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
    L_0x021d:
        r4 = 8;
        if (r11 != r4) goto L_0x0590;
    L_0x0221:
        r18 = new android.widget.LinearLayout;
        r0 = r18;
        r1 = r48;
        r0.<init>(r1);
        r4 = r18;
        r4 = (android.widget.LinearLayout) r4;
        r5 = 0;
        r4.setOrientation(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r5 = -1;
        r6 = 48;
        r5 = org.telegram.ui.Components.LayoutHelper.createLinear(r5, r6);
        r0 = r18;
        r4.addView(r0, r5);
        r4 = "windowBackgroundWhite";
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r0 = r18;
        r0.setBackgroundColor(r4);
    L_0x024e:
        r4 = 9;
        if (r11 != r4) goto L_0x062e;
    L_0x0252:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = new org.telegram.ui.Components.HintEditText;
        r0 = r48;
        r5.<init>(r0);
        r4[r11] = r5;
    L_0x025f:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = java.lang.Integer.valueOf(r11);
        r4.setTag(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1;
        r6 = 1098907648; // 0x41800000 float:16.0 double:5.42932517E-315;
        r4.setTextSize(r5, r6);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "windowBackgroundWhiteHintText";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setHintTextColor(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "windowBackgroundWhiteBlackText";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setTextColor(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 0;
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "windowBackgroundWhiteBlackText";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setCursorColor(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4.setCursorSize(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1069547520; // 0x3fc00000 float:1.5 double:5.28426686E-315;
        r4.setCursorWidth(r5);
        r4 = 4;
        if (r11 != r4) goto L_0x02e9;
    L_0x02cf:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = new org.telegram.ui.PaymentFormActivity$3;
        r0 = r47;
        r5.<init>();
        r4.setOnTouchListener(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 0;
        r4.setInputType(r5);
    L_0x02e9:
        r4 = 9;
        if (r11 == r4) goto L_0x02f1;
    L_0x02ed:
        r4 = 8;
        if (r11 != r4) goto L_0x063d;
    L_0x02f1:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 3;
        r4.setInputType(r5);
    L_0x02fb:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 268435461; // 0x10000005 float:2.5243564E-29 double:1.326247394E-315;
        r4.setImeOptions(r5);
        switch(r11) {
            case 0: goto L_0x06c9;
            case 1: goto L_0x0703;
            case 2: goto L_0x073d;
            case 3: goto L_0x0777;
            case 4: goto L_0x07b1;
            case 5: goto L_0x080c;
            case 6: goto L_0x0659;
            case 7: goto L_0x0691;
            default: goto L_0x030a;
        };
    L_0x030a:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r0 = r47;
        r5 = r0.inputFields;
        r5 = r5[r11];
        r5 = r5.length();
        r4.setSelection(r5);
        r4 = 8;
        if (r11 != r4) goto L_0x0846;
    L_0x0321:
        r4 = new android.widget.TextView;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.textView = r4;
        r0 = r47;
        r4 = r0.textView;
        r5 = "+";
        r4.setText(r5);
        r0 = r47;
        r4 = r0.textView;
        r5 = "windowBackgroundWhiteBlackText";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setTextColor(r5);
        r0 = r47;
        r4 = r0.textView;
        r5 = 1;
        r6 = 1098907648; // 0x41800000 float:16.0 double:5.42932517E-315;
        r4.setTextSize(r5, r6);
        r0 = r47;
        r10 = r0.textView;
        r4 = -2;
        r5 = -2;
        r6 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r7 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r8 = 0;
        r9 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r4 = org.telegram.ui.Components.LayoutHelper.createLinear(r4, r5, r6, r7, r8, r9);
        r0 = r18;
        r0.addView(r10, r4);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r4.setPadding(r5, r6, r7, r8);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 19;
        r4.setGravity(r5);
        r4 = 1;
        r0 = new android.text.InputFilter[r4];
        r28 = r0;
        r4 = 0;
        r5 = new android.text.InputFilter$LengthFilter;
        r6 = 5;
        r5.<init>(r6);
        r28[r4] = r5;
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r0 = r28;
        r4.setFilters(r0);
        r0 = r47;
        r4 = r0.inputFields;
        r10 = r4[r11];
        r4 = 55;
        r5 = -2;
        r6 = 0;
        r7 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r8 = 1098907648; // 0x41800000 float:16.0 double:5.42932517E-315;
        r9 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r4 = org.telegram.ui.Components.LayoutHelper.createLinear(r4, r5, r6, r7, r8, r9);
        r0 = r18;
        r0.addView(r10, r4);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = new org.telegram.ui.PaymentFormActivity$4;
        r0 = r47;
        r5.<init>();
        r4.addTextChangedListener(r5);
    L_0x03c3:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = new org.telegram.ui.PaymentFormActivity$6;
        r0 = r47;
        r5.<init>();
        r4.setOnEditorActionListener(r5);
        r4 = 9;
        if (r11 != r4) goto L_0x09da;
    L_0x03d7:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.email_to_provider;
        if (r4 != 0) goto L_0x03eb;
    L_0x03e1:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.phone_to_provider;
        if (r4 == 0) goto L_0x0a25;
    L_0x03eb:
        r37 = 0;
        r17 = 0;
    L_0x03ef:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.users;
        r4 = r4.size();
        r0 = r17;
        if (r0 >= r4) goto L_0x08ce;
    L_0x03fd:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.users;
        r0 = r17;
        r43 = r4.get(r0);
        r43 = (org.telegram.tgnet.TLRPC.User) r43;
        r0 = r43;
        r4 = r0.id;
        r0 = r47;
        r5 = r0.paymentForm;
        r5 = r5.provider_id;
        if (r4 != r5) goto L_0x0419;
    L_0x0417:
        r37 = r43;
    L_0x0419:
        r17 = r17 + 1;
        goto L_0x03ef;
    L_0x041c:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 1;
        if (r4 != r5) goto L_0x0436;
    L_0x0423:
        r0 = r47;
        r4 = r0.actionBar;
        r5 = "PaymentShippingMethod";
        r6 = 2131232114; // 0x7f080572 float:1.8080328E38 double:1.052968571E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setTitle(r5);
        goto L_0x0017;
    L_0x0436:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 2;
        if (r4 != r5) goto L_0x0450;
    L_0x043d:
        r0 = r47;
        r4 = r0.actionBar;
        r5 = "PaymentCardInfo";
        r6 = 2131232083; // 0x7f080553 float:1.8080265E38 double:1.0529685555E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setTitle(r5);
        goto L_0x0017;
    L_0x0450:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 3;
        if (r4 != r5) goto L_0x046a;
    L_0x0457:
        r0 = r47;
        r4 = r0.actionBar;
        r5 = "PaymentCardInfo";
        r6 = 2131232083; // 0x7f080553 float:1.8080265E38 double:1.0529685555E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setTitle(r5);
        goto L_0x0017;
    L_0x046a:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 4;
        if (r4 != r5) goto L_0x04b5;
    L_0x0471:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.test;
        if (r4 == 0) goto L_0x04a2;
    L_0x047b:
        r0 = r47;
        r4 = r0.actionBar;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "Test ";
        r5 = r5.append(r6);
        r6 = "PaymentCheckout";
        r7 = 2131232091; // 0x7f08055b float:1.8080281E38 double:1.0529685595E-314;
        r6 = org.telegram.messenger.LocaleController.getString(r6, r7);
        r5 = r5.append(r6);
        r5 = r5.toString();
        r4.setTitle(r5);
        goto L_0x0017;
    L_0x04a2:
        r0 = r47;
        r4 = r0.actionBar;
        r5 = "PaymentCheckout";
        r6 = 2131232091; // 0x7f08055b float:1.8080281E38 double:1.0529685595E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setTitle(r5);
        goto L_0x0017;
    L_0x04b5:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 5;
        if (r4 != r5) goto L_0x0500;
    L_0x04bc:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.test;
        if (r4 == 0) goto L_0x04ed;
    L_0x04c6:
        r0 = r47;
        r4 = r0.actionBar;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "Test ";
        r5 = r5.append(r6);
        r6 = "PaymentReceipt";
        r7 = 2131232106; // 0x7f08056a float:1.8080312E38 double:1.052968567E-314;
        r6 = org.telegram.messenger.LocaleController.getString(r6, r7);
        r5 = r5.append(r6);
        r5 = r5.toString();
        r4.setTitle(r5);
        goto L_0x0017;
    L_0x04ed:
        r0 = r47;
        r4 = r0.actionBar;
        r5 = "PaymentReceipt";
        r6 = 2131232106; // 0x7f08056a float:1.8080312E38 double:1.052968567E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setTitle(r5);
        goto L_0x0017;
    L_0x0500:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 6;
        if (r4 != r5) goto L_0x0017;
    L_0x0507:
        r0 = r47;
        r4 = r0.actionBar;
        r5 = "PaymentPassword";
        r6 = 2131232914; // 0x7f080892 float:1.808195E38 double:1.052968966E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setTitle(r5);
        goto L_0x0017;
    L_0x051a:
        r10 = 0;
        goto L_0x00fe;
    L_0x051d:
        r38.close();	 Catch:{ Exception -> 0x01b5 }
        goto L_0x01b9;
    L_0x0522:
        r4 = 6;
        if (r11 != r4) goto L_0x021d;
    L_0x0525:
        r0 = r47;
        r4 = r0.sectionCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.ShadowSectionCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.sectionCell;
        r6 = 0;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 1;
        r6 = new org.telegram.ui.Cells.HeaderCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = "PaymentShippingReceiver";
        r6 = 2131232117; // 0x7f080575 float:1.8080334E38 double:1.0529685723E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setText(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.headerCell;
        r6 = 1;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        goto L_0x021d;
    L_0x0590:
        r4 = 9;
        if (r11 != r4) goto L_0x05a4;
    L_0x0594:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 8;
        r4 = r4[r5];
        r18 = r4.getParent();
        r18 = (android.view.ViewGroup) r18;
        goto L_0x024e;
    L_0x05a4:
        r18 = new android.widget.FrameLayout;
        r0 = r18;
        r1 = r48;
        r0.<init>(r1);
        r0 = r47;
        r4 = r0.linearLayout2;
        r5 = -1;
        r6 = 48;
        r5 = org.telegram.ui.Components.LayoutHelper.createLinear(r5, r6);
        r0 = r18;
        r4.addView(r0, r5);
        r4 = "windowBackgroundWhite";
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r0 = r18;
        r0.setBackgroundColor(r4);
        r4 = 5;
        if (r11 == r4) goto L_0x0613;
    L_0x05cc:
        r4 = 9;
        if (r11 == r4) goto L_0x0613;
    L_0x05d0:
        r13 = 1;
    L_0x05d1:
        if (r13 == 0) goto L_0x05e1;
    L_0x05d3:
        r4 = 7;
        if (r11 != r4) goto L_0x0615;
    L_0x05d6:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.phone_requested;
        if (r4 != 0) goto L_0x0615;
    L_0x05e0:
        r13 = 0;
    L_0x05e1:
        if (r13 == 0) goto L_0x024e;
    L_0x05e3:
        r24 = new android.view.View;
        r0 = r24;
        r1 = r48;
        r0.<init>(r1);
        r0 = r47;
        r4 = r0.dividers;
        r0 = r24;
        r4.add(r0);
        r4 = "divider";
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r0 = r24;
        r0.setBackgroundColor(r4);
        r4 = new android.widget.FrameLayout$LayoutParams;
        r5 = -1;
        r6 = 1;
        r7 = 83;
        r4.<init>(r5, r6, r7);
        r0 = r18;
        r1 = r24;
        r0.addView(r1, r4);
        goto L_0x024e;
    L_0x0613:
        r13 = 0;
        goto L_0x05d1;
    L_0x0615:
        r4 = 6;
        if (r11 != r4) goto L_0x05e1;
    L_0x0618:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.phone_requested;
        if (r4 != 0) goto L_0x05e1;
    L_0x0622:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.email_requested;
        if (r4 != 0) goto L_0x05e1;
    L_0x062c:
        r13 = 0;
        goto L_0x05e1;
    L_0x062e:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = new org.telegram.ui.Components.EditTextBoldCursor;
        r0 = r48;
        r5.<init>(r0);
        r4[r11] = r5;
        goto L_0x025f;
    L_0x063d:
        r4 = 7;
        if (r11 != r4) goto L_0x064c;
    L_0x0640:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1;
        r4.setInputType(r5);
        goto L_0x02fb;
    L_0x064c:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 16385; // 0x4001 float:2.296E-41 double:8.0953E-320;
        r4.setInputType(r5);
        goto L_0x02fb;
    L_0x0659:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentShippingName";
        r6 = 2131232115; // 0x7f080573 float:1.808033E38 double:1.0529685713E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        if (r4 == 0) goto L_0x030a;
    L_0x0674:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        r4 = r4.name;
        if (r4 == 0) goto L_0x030a;
    L_0x067e:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r0 = r47;
        r5 = r0.paymentForm;
        r5 = r5.saved_info;
        r5 = r5.name;
        r4.setText(r5);
        goto L_0x030a;
    L_0x0691:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentShippingEmailPlaceholder";
        r6 = 2131232112; // 0x7f080570 float:1.8080324E38 double:1.05296857E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        if (r4 == 0) goto L_0x030a;
    L_0x06ac:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        r4 = r4.email;
        if (r4 == 0) goto L_0x030a;
    L_0x06b6:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r0 = r47;
        r5 = r0.paymentForm;
        r5 = r5.saved_info;
        r5 = r5.email;
        r4.setText(r5);
        goto L_0x030a;
    L_0x06c9:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentShippingAddress1Placeholder";
        r6 = 2131232108; // 0x7f08056c float:1.8080316E38 double:1.052968568E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        if (r4 == 0) goto L_0x030a;
    L_0x06e4:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        r4 = r4.shipping_address;
        if (r4 == 0) goto L_0x030a;
    L_0x06ee:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r0 = r47;
        r5 = r0.paymentForm;
        r5 = r5.saved_info;
        r5 = r5.shipping_address;
        r5 = r5.street_line1;
        r4.setText(r5);
        goto L_0x030a;
    L_0x0703:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentShippingAddress2Placeholder";
        r6 = 2131232109; // 0x7f08056d float:1.8080318E38 double:1.0529685684E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        if (r4 == 0) goto L_0x030a;
    L_0x071e:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        r4 = r4.shipping_address;
        if (r4 == 0) goto L_0x030a;
    L_0x0728:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r0 = r47;
        r5 = r0.paymentForm;
        r5 = r5.saved_info;
        r5 = r5.shipping_address;
        r5 = r5.street_line2;
        r4.setText(r5);
        goto L_0x030a;
    L_0x073d:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentShippingCityPlaceholder";
        r6 = 2131232110; // 0x7f08056e float:1.808032E38 double:1.052968569E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        if (r4 == 0) goto L_0x030a;
    L_0x0758:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        r4 = r4.shipping_address;
        if (r4 == 0) goto L_0x030a;
    L_0x0762:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r0 = r47;
        r5 = r0.paymentForm;
        r5 = r5.saved_info;
        r5 = r5.shipping_address;
        r5 = r5.city;
        r4.setText(r5);
        goto L_0x030a;
    L_0x0777:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentShippingStatePlaceholder";
        r6 = 2131232120; // 0x7f080578 float:1.808034E38 double:1.052968574E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        if (r4 == 0) goto L_0x030a;
    L_0x0792:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        r4 = r4.shipping_address;
        if (r4 == 0) goto L_0x030a;
    L_0x079c:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r0 = r47;
        r5 = r0.paymentForm;
        r5 = r5.saved_info;
        r5 = r5.shipping_address;
        r5 = r5.state;
        r4.setText(r5);
        goto L_0x030a;
    L_0x07b1:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentShippingCountry";
        r6 = 2131232111; // 0x7f08056f float:1.8080322E38 double:1.0529685694E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        if (r4 == 0) goto L_0x030a;
    L_0x07cc:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        r4 = r4.shipping_address;
        if (r4 == 0) goto L_0x030a;
    L_0x07d6:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        r4 = r4.shipping_address;
        r4 = r4.country_iso2;
        r0 = r22;
        r44 = r0.get(r4);
        r44 = (java.lang.String) r44;
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        r4 = r4.shipping_address;
        r4 = r4.country_iso2;
        r0 = r47;
        r0.countryName = r4;
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        if (r44 == 0) goto L_0x0805;
    L_0x07fe:
        r0 = r44;
        r4.setText(r0);
        goto L_0x030a;
    L_0x0805:
        r0 = r47;
        r0 = r0.countryName;
        r44 = r0;
        goto L_0x07fe;
    L_0x080c:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentShippingZipPlaceholder";
        r6 = 2131232121; // 0x7f080579 float:1.8080342E38 double:1.0529685743E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        if (r4 == 0) goto L_0x030a;
    L_0x0827:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        r4 = r4.shipping_address;
        if (r4 == 0) goto L_0x030a;
    L_0x0831:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r0 = r47;
        r5 = r0.paymentForm;
        r5 = r5.saved_info;
        r5 = r5.shipping_address;
        r5 = r5.post_code;
        r4.setText(r5);
        goto L_0x030a;
    L_0x0846:
        r4 = 9;
        if (r11 != r4) goto L_0x088c;
    L_0x084a:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r4.setPadding(r5, r6, r7, r8);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 19;
        r4.setGravity(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r10 = r4[r11];
        r4 = -1;
        r5 = -2;
        r6 = 0;
        r7 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r8 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r9 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r4 = org.telegram.ui.Components.LayoutHelper.createLinear(r4, r5, r6, r7, r8, r9);
        r0 = r18;
        r0.addView(r10, r4);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = new org.telegram.ui.PaymentFormActivity$5;
        r0 = r47;
        r5.<init>();
        r4.addTextChangedListener(r5);
        goto L_0x03c3;
    L_0x088c:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);
        r4.setPadding(r5, r6, r7, r8);
        r0 = r47;
        r4 = r0.inputFields;
        r5 = r4[r11];
        r4 = org.telegram.messenger.LocaleController.isRTL;
        if (r4 == 0) goto L_0x08cc;
    L_0x08a8:
        r4 = 5;
    L_0x08a9:
        r5.setGravity(r4);
        r0 = r47;
        r4 = r0.inputFields;
        r45 = r4[r11];
        r4 = -1;
        r5 = -1073741824; // 0xffffffffc0000000 float:-2.0 double:NaN;
        r6 = 51;
        r7 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r8 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r9 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r10 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r4 = org.telegram.ui.Components.LayoutHelper.createFrame(r4, r5, r6, r7, r8, r9, r10);
        r0 = r18;
        r1 = r45;
        r0.addView(r1, r4);
        goto L_0x03c3;
    L_0x08cc:
        r4 = 3;
        goto L_0x08a9;
    L_0x08ce:
        if (r37 == 0) goto L_0x09de;
    L_0x08d0:
        r0 = r37;
        r4 = r0.first_name;
        r0 = r37;
        r5 = r0.last_name;
        r36 = org.telegram.messenger.ContactsController.formatName(r4, r5);
    L_0x08dc:
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 1;
        r6 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = 2130837849; // 0x7f020159 float:1.7280664E38 double:1.052773778E-314;
        r6 = "windowBackgroundGrayShadow";
        r0 = r48;
        r5 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r0, r5, r6);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.bottomCell;
        r6 = 1;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.email_to_provider;
        if (r4 == 0) goto L_0x09e3;
    L_0x091e:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.phone_to_provider;
        if (r4 == 0) goto L_0x09e3;
    L_0x0928:
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = "PaymentPhoneEmailToProvider";
        r6 = 2131232922; // 0x7f08089a float:1.8081967E38 double:1.05296897E-314;
        r7 = 1;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r7[r8] = r36;
        r5 = org.telegram.messenger.LocaleController.formatString(r5, r6, r7);
        r4.setText(r5);
    L_0x0942:
        r4 = new org.telegram.ui.Cells.TextCheckCell;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.checkCell1 = r4;
        r0 = r47;
        r4 = r0.checkCell1;
        r5 = 1;
        r5 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r5);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.checkCell1;
        r5 = "PaymentShippingSave";
        r6 = 2131232118; // 0x7f080576 float:1.8080336E38 double:1.052968573E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r0 = r47;
        r6 = r0.saveShippingInfo;
        r7 = 0;
        r4.setTextAndCheck(r5, r6, r7);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.checkCell1;
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r0 = r47;
        r4 = r0.checkCell1;
        r5 = new org.telegram.ui.PaymentFormActivity$7;
        r0 = r47;
        r5.<init>();
        r4.setOnClickListener(r5);
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = 2130837849; // 0x7f020159 float:1.7280664E38 double:1.052773778E-314;
        r6 = "windowBackgroundGrayShadow";
        r0 = r48;
        r5 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r0, r5, r6);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = "PaymentShippingSaveInfo";
        r6 = 2131232119; // 0x7f080577 float:1.8080338E38 double:1.0529685733E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setText(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.bottomCell;
        r6 = 0;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
    L_0x09da:
        r11 = r11 + 1;
        goto L_0x01d0;
    L_0x09de:
        r36 = "";
        goto L_0x08dc;
    L_0x09e3:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.email_to_provider;
        if (r4 == 0) goto L_0x0a09;
    L_0x09ed:
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = "PaymentEmailToProvider";
        r6 = 2131232922; // 0x7f08089a float:1.8081967E38 double:1.05296897E-314;
        r7 = 1;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r7[r8] = r36;
        r5 = org.telegram.messenger.LocaleController.formatString(r5, r6, r7);
        r4.setText(r5);
        goto L_0x0942;
    L_0x0a09:
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = "PaymentPhoneToProvider";
        r6 = 2131232922; // 0x7f08089a float:1.8081967E38 double:1.05296897E-314;
        r7 = 1;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r7[r8] = r36;
        r5 = org.telegram.messenger.LocaleController.formatString(r5, r6, r7);
        r4.setText(r5);
        goto L_0x0942;
    L_0x0a25:
        r0 = r47;
        r4 = r0.sectionCell;
        r5 = 1;
        r6 = new org.telegram.ui.Cells.ShadowSectionCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.sectionCell;
        r6 = 1;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        goto L_0x0942;
    L_0x0a49:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.name_requested;
        if (r4 != 0) goto L_0x0a65;
    L_0x0a53:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 6;
        r4 = r4[r5];
        r4 = r4.getParent();
        r4 = (android.view.ViewGroup) r4;
        r5 = 8;
        r4.setVisibility(r5);
    L_0x0a65:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.phone_requested;
        if (r4 != 0) goto L_0x0a82;
    L_0x0a6f:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 8;
        r4 = r4[r5];
        r4 = r4.getParent();
        r4 = (android.view.ViewGroup) r4;
        r5 = 8;
        r4.setVisibility(r5);
    L_0x0a82:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.email_requested;
        if (r4 != 0) goto L_0x0a9e;
    L_0x0a8c:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 7;
        r4 = r4[r5];
        r4 = r4.getParent();
        r4 = (android.view.ViewGroup) r4;
        r5 = 8;
        r4.setVisibility(r5);
    L_0x0a9e:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.phone_requested;
        if (r4 == 0) goto L_0x0c40;
    L_0x0aa8:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 9;
        r4 = r4[r5];
        r5 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r4.setImeOptions(r5);
    L_0x0ab6:
        r0 = r47;
        r4 = r0.sectionCell;
        r5 = 1;
        r4 = r4[r5];
        if (r4 == 0) goto L_0x0c85;
    L_0x0abf:
        r0 = r47;
        r4 = r0.sectionCell;
        r5 = 1;
        r5 = r4[r5];
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.name_requested;
        if (r4 != 0) goto L_0x0ae4;
    L_0x0ad0:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.phone_requested;
        if (r4 != 0) goto L_0x0ae4;
    L_0x0ada:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.email_requested;
        if (r4 == 0) goto L_0x0c81;
    L_0x0ae4:
        r4 = 0;
    L_0x0ae5:
        r5.setVisibility(r4);
    L_0x0ae8:
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 1;
        r5 = r4[r5];
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.name_requested;
        if (r4 != 0) goto L_0x0b0d;
    L_0x0af9:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.phone_requested;
        if (r4 != 0) goto L_0x0b0d;
    L_0x0b03:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.email_requested;
        if (r4 == 0) goto L_0x0cbc;
    L_0x0b0d:
        r4 = 0;
    L_0x0b0e:
        r5.setVisibility(r4);
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.shipping_address_requested;
        if (r4 != 0) goto L_0x0b9f;
    L_0x0b1b:
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = 8;
        r4.setVisibility(r5);
        r0 = r47;
        r4 = r0.sectionCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = 8;
        r4.setVisibility(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 0;
        r4 = r4[r5];
        r4 = r4.getParent();
        r4 = (android.view.ViewGroup) r4;
        r5 = 8;
        r4.setVisibility(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 1;
        r4 = r4[r5];
        r4 = r4.getParent();
        r4 = (android.view.ViewGroup) r4;
        r5 = 8;
        r4.setVisibility(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 2;
        r4 = r4[r5];
        r4 = r4.getParent();
        r4 = (android.view.ViewGroup) r4;
        r5 = 8;
        r4.setVisibility(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 3;
        r4 = r4[r5];
        r4 = r4.getParent();
        r4 = (android.view.ViewGroup) r4;
        r5 = 8;
        r4.setVisibility(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 4;
        r4 = r4[r5];
        r4 = r4.getParent();
        r4 = (android.view.ViewGroup) r4;
        r5 = 8;
        r4.setVisibility(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 5;
        r4 = r4[r5];
        r4 = r4.getParent();
        r4 = (android.view.ViewGroup) r4;
        r5 = 8;
        r4.setVisibility(r5);
    L_0x0b9f:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        if (r4 == 0) goto L_0x0cc0;
    L_0x0ba7:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        r4 = r4.phone;
        r4 = android.text.TextUtils.isEmpty(r4);
        if (r4 != 0) goto L_0x0cc0;
    L_0x0bb5:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        r4 = r4.phone;
        r0 = r47;
        r0.fillNumber(r4);
    L_0x0bc2:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 8;
        r4 = r4[r5];
        r4 = r4.length();
        if (r4 != 0) goto L_0x0c3b;
    L_0x0bd0:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.phone_requested;
        if (r4 == 0) goto L_0x0c3b;
    L_0x0bda:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        if (r4 == 0) goto L_0x0bf0;
    L_0x0be2:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.saved_info;
        r4 = r4.phone;
        r4 = android.text.TextUtils.isEmpty(r4);
        if (r4 == 0) goto L_0x0c3b;
    L_0x0bf0:
        r21 = 0;
        r4 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0cc8 }
        r5 = "phone";
        r41 = r4.getSystemService(r5);	 Catch:{ Exception -> 0x0cc8 }
        r41 = (android.telephony.TelephonyManager) r41;	 Catch:{ Exception -> 0x0cc8 }
        if (r41 == 0) goto L_0x0c07;
    L_0x0bff:
        r4 = r41.getSimCountryIso();	 Catch:{ Exception -> 0x0cc8 }
        r21 = r4.toUpperCase();	 Catch:{ Exception -> 0x0cc8 }
    L_0x0c07:
        if (r21 == 0) goto L_0x0c3b;
    L_0x0c09:
        r0 = r30;
        r1 = r21;
        r23 = r0.get(r1);
        r23 = (java.lang.String) r23;
        if (r23 == 0) goto L_0x0c3b;
    L_0x0c15:
        r0 = r47;
        r4 = r0.countriesArray;
        r0 = r23;
        r27 = r4.indexOf(r0);
        r4 = -1;
        r0 = r27;
        if (r0 == r4) goto L_0x0c3b;
    L_0x0c24:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 8;
        r5 = r4[r5];
        r0 = r47;
        r4 = r0.countriesMap;
        r0 = r23;
        r4 = r4.get(r0);
        r4 = (java.lang.CharSequence) r4;
        r5.setText(r4);
    L_0x0c3b:
        r0 = r47;
        r4 = r0.fragmentView;
        return r4;
    L_0x0c40:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.email_requested;
        if (r4 == 0) goto L_0x0c59;
    L_0x0c4a:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 7;
        r4 = r4[r5];
        r5 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r4.setImeOptions(r5);
        goto L_0x0ab6;
    L_0x0c59:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.name_requested;
        if (r4 == 0) goto L_0x0c72;
    L_0x0c63:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 6;
        r4 = r4[r5];
        r5 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r4.setImeOptions(r5);
        goto L_0x0ab6;
    L_0x0c72:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 5;
        r4 = r4[r5];
        r5 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r4.setImeOptions(r5);
        goto L_0x0ab6;
    L_0x0c81:
        r4 = 8;
        goto L_0x0ae5;
    L_0x0c85:
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 1;
        r4 = r4[r5];
        if (r4 == 0) goto L_0x0ae8;
    L_0x0c8e:
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 1;
        r5 = r4[r5];
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.name_requested;
        if (r4 != 0) goto L_0x0cb3;
    L_0x0c9f:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.phone_requested;
        if (r4 != 0) goto L_0x0cb3;
    L_0x0ca9:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.email_requested;
        if (r4 == 0) goto L_0x0cb9;
    L_0x0cb3:
        r4 = 0;
    L_0x0cb4:
        r5.setVisibility(r4);
        goto L_0x0ae8;
    L_0x0cb9:
        r4 = 8;
        goto L_0x0cb4;
    L_0x0cbc:
        r4 = 8;
        goto L_0x0b0e;
    L_0x0cc0:
        r4 = 0;
        r0 = r47;
        r0.fillNumber(r4);
        goto L_0x0bc2;
    L_0x0cc8:
        r25 = move-exception;
        org.telegram.messenger.FileLog.e(r25);
        goto L_0x0c07;
    L_0x0cce:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 2;
        if (r4 != r5) goto L_0x13ef;
    L_0x0cd5:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.native_params;
        if (r4 == 0) goto L_0x0d1c;
    L_0x0cdd:
        r29 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0eb8 }
        r0 = r47;
        r4 = r0.paymentForm;	 Catch:{ Exception -> 0x0eb8 }
        r4 = r4.native_params;	 Catch:{ Exception -> 0x0eb8 }
        r4 = r4.data;	 Catch:{ Exception -> 0x0eb8 }
        r0 = r29;
        r0.<init>(r4);	 Catch:{ Exception -> 0x0eb8 }
        r4 = "android_pay_public_key";
        r0 = r29;
        r14 = r0.getString(r4);	 Catch:{ Exception -> 0x0eb0 }
        r4 = android.text.TextUtils.isEmpty(r14);	 Catch:{ Exception -> 0x0eb0 }
        if (r4 != 0) goto L_0x0cff;
    L_0x0cfb:
        r0 = r47;
        r0.androidPayPublicKey = r14;	 Catch:{ Exception -> 0x0eb0 }
    L_0x0cff:
        r4 = "android_pay_bgcolor";
        r0 = r29;
        r4 = r0.getInt(r4);	 Catch:{ Exception -> 0x0ebe }
        r5 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r4 = r4 | r5;
        r0 = r47;
        r0.androidPayBackgroundColor = r4;	 Catch:{ Exception -> 0x0ebe }
    L_0x0d0f:
        r4 = "android_pay_inverse";
        r0 = r29;
        r4 = r0.getBoolean(r4);	 Catch:{ Exception -> 0x0ec6 }
        r0 = r47;
        r0.androidPayBlackTheme = r4;	 Catch:{ Exception -> 0x0ec6 }
    L_0x0d1c:
        r0 = r47;
        r4 = r0.isWebView;
        if (r4 == 0) goto L_0x0ece;
    L_0x0d22:
        r0 = r47;
        r4 = r0.androidPayPublicKey;
        if (r4 == 0) goto L_0x0d2b;
    L_0x0d28:
        r47.initAndroidPay(r48);
    L_0x0d2b:
        r4 = new android.widget.FrameLayout;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.androidPayContainer = r4;
        r0 = r47;
        r4 = r0.androidPayContainer;
        r5 = 4000; // 0xfa0 float:5.605E-42 double:1.9763E-320;
        r4.setId(r5);
        r0 = r47;
        r4 = r0.androidPayContainer;
        r5 = 1;
        r5 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r5);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.androidPayContainer;
        r5 = 8;
        r4.setVisibility(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.androidPayContainer;
        r6 = -1;
        r7 = 48;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r4 = 1;
        r0 = r47;
        r0.webviewLoading = r4;
        r4 = 1;
        r5 = 1;
        r0 = r47;
        r0.showEditDoneProgress(r4, r5);
        r0 = r47;
        r4 = r0.progressView;
        r5 = 0;
        r4.setVisibility(r5);
        r0 = r47;
        r4 = r0.doneItem;
        r5 = 0;
        r4.setEnabled(r5);
        r0 = r47;
        r4 = r0.doneItem;
        r4 = r4.getImageView();
        r5 = 4;
        r4.setVisibility(r5);
        r4 = new org.telegram.ui.PaymentFormActivity$8;
        r0 = r47;
        r1 = r48;
        r4.<init>(r1);
        r0 = r47;
        r0.webView = r4;
        r0 = r47;
        r4 = r0.webView;
        r4 = r4.getSettings();
        r5 = 1;
        r4.setJavaScriptEnabled(r5);
        r0 = r47;
        r4 = r0.webView;
        r4 = r4.getSettings();
        r5 = 1;
        r4.setDomStorageEnabled(r5);
        r4 = android.os.Build.VERSION.SDK_INT;
        r5 = 21;
        if (r4 < r5) goto L_0x0dd3;
    L_0x0db9:
        r0 = r47;
        r4 = r0.webView;
        r4 = r4.getSettings();
        r5 = 0;
        r4.setMixedContentMode(r5);
        r19 = android.webkit.CookieManager.getInstance();
        r0 = r47;
        r4 = r0.webView;
        r5 = 1;
        r0 = r19;
        r0.setAcceptThirdPartyCookies(r4, r5);
    L_0x0dd3:
        r0 = r47;
        r4 = r0.webView;
        r5 = new org.telegram.ui.PaymentFormActivity$TelegramWebviewProxy;
        r6 = 0;
        r0 = r47;
        r5.<init>(r0, r6);
        r6 = "TelegramWebviewProxy";
        r4.addJavascriptInterface(r5, r6);
        r0 = r47;
        r4 = r0.webView;
        r5 = new org.telegram.ui.PaymentFormActivity$9;
        r0 = r47;
        r5.<init>();
        r4.setWebViewClient(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.webView;
        r6 = -1;
        r7 = -1073741824; // 0xffffffffc0000000 float:-2.0 double:NaN;
        r6 = org.telegram.ui.Components.LayoutHelper.createFrame(r6, r7);
        r4.addView(r5, r6);
        r0 = r47;
        r4 = r0.sectionCell;
        r5 = 2;
        r6 = new org.telegram.ui.Cells.ShadowSectionCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.sectionCell;
        r6 = 2;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r4 = new org.telegram.ui.Cells.TextCheckCell;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.checkCell1 = r4;
        r0 = r47;
        r4 = r0.checkCell1;
        r5 = 1;
        r5 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r5);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.checkCell1;
        r5 = "PaymentCardSavePaymentInformation";
        r6 = 2131232087; // 0x7f080557 float:1.8080273E38 double:1.0529685575E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r0 = r47;
        r6 = r0.saveCardInfo;
        r7 = 0;
        r4.setTextAndCheck(r5, r6, r7);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.checkCell1;
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r0 = r47;
        r4 = r0.checkCell1;
        r5 = new org.telegram.ui.PaymentFormActivity$10;
        r0 = r47;
        r5.<init>();
        r4.setOnClickListener(r5);
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = 2130837849; // 0x7f020159 float:1.7280664E38 double:1.052773778E-314;
        r6 = "windowBackgroundGrayShadow";
        r0 = r48;
        r5 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r0, r5, r6);
        r4.setBackgroundDrawable(r5);
        r47.updateSavePaymentField();
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.bottomCell;
        r6 = 0;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        goto L_0x0c3b;
    L_0x0eb0:
        r25 = move-exception;
        r4 = 0;
        r0 = r47;
        r0.androidPayPublicKey = r4;	 Catch:{ Exception -> 0x0eb8 }
        goto L_0x0cff;
    L_0x0eb8:
        r25 = move-exception;
        org.telegram.messenger.FileLog.e(r25);
        goto L_0x0d1c;
    L_0x0ebe:
        r25 = move-exception;
        r4 = -1;
        r0 = r47;
        r0.androidPayBackgroundColor = r4;	 Catch:{ Exception -> 0x0eb8 }
        goto L_0x0d0f;
    L_0x0ec6:
        r25 = move-exception;
        r4 = 0;
        r0 = r47;
        r0.androidPayBlackTheme = r4;	 Catch:{ Exception -> 0x0eb8 }
        goto L_0x0d1c;
    L_0x0ece:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.native_params;
        if (r4 == 0) goto L_0x0f19;
    L_0x0ed6:
        r29 = new org.json.JSONObject;	 Catch:{ Exception -> 0x1152 }
        r0 = r47;
        r4 = r0.paymentForm;	 Catch:{ Exception -> 0x1152 }
        r4 = r4.native_params;	 Catch:{ Exception -> 0x1152 }
        r4 = r4.data;	 Catch:{ Exception -> 0x1152 }
        r0 = r29;
        r0.<init>(r4);	 Catch:{ Exception -> 0x1152 }
        r4 = "need_country";
        r0 = r29;
        r4 = r0.getBoolean(r4);	 Catch:{ Exception -> 0x114a }
        r0 = r47;
        r0.need_card_country = r4;	 Catch:{ Exception -> 0x114a }
    L_0x0ef2:
        r4 = "need_zip";
        r0 = r29;
        r4 = r0.getBoolean(r4);	 Catch:{ Exception -> 0x1158 }
        r0 = r47;
        r0.need_card_postcode = r4;	 Catch:{ Exception -> 0x1158 }
    L_0x0eff:
        r4 = "need_cardholder_name";
        r0 = r29;
        r4 = r0.getBoolean(r4);	 Catch:{ Exception -> 0x1160 }
        r0 = r47;
        r0.need_card_name = r4;	 Catch:{ Exception -> 0x1160 }
    L_0x0f0c:
        r4 = "publishable_key";
        r0 = r29;
        r4 = r0.getString(r4);	 Catch:{ Exception -> 0x1168 }
        r0 = r47;
        r0.stripeApiKey = r4;	 Catch:{ Exception -> 0x1168 }
    L_0x0f19:
        r47.initAndroidPay(r48);
        r4 = 6;
        r4 = new org.telegram.ui.Components.EditTextBoldCursor[r4];
        r0 = r47;
        r0.inputFields = r4;
        r11 = 0;
    L_0x0f24:
        r4 = 6;
        if (r11 >= r4) goto L_0x13a7;
    L_0x0f27:
        if (r11 != 0) goto L_0x1172;
    L_0x0f29:
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.HeaderCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = "PaymentCardTitle";
        r6 = 2131232090; // 0x7f08055a float:1.808028E38 double:1.052968559E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setText(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.headerCell;
        r6 = 0;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
    L_0x0f70:
        r4 = 3;
        if (r11 == r4) goto L_0x11be;
    L_0x0f73:
        r4 = 5;
        if (r11 == r4) goto L_0x11be;
    L_0x0f76:
        r4 = 4;
        if (r11 != r4) goto L_0x0f7f;
    L_0x0f79:
        r0 = r47;
        r4 = r0.need_card_postcode;
        if (r4 == 0) goto L_0x11be;
    L_0x0f7f:
        r13 = 1;
    L_0x0f80:
        r18 = new android.widget.FrameLayout;
        r0 = r18;
        r1 = r48;
        r0.<init>(r1);
        r0 = r47;
        r4 = r0.linearLayout2;
        r5 = -1;
        r6 = 48;
        r5 = org.telegram.ui.Components.LayoutHelper.createLinear(r5, r6);
        r0 = r18;
        r4.addView(r0, r5);
        r4 = "windowBackgroundWhite";
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r0 = r18;
        r0.setBackgroundColor(r4);
        r33 = 0;
        r0 = r47;
        r4 = r0.inputFields;
        r5 = new org.telegram.ui.Components.EditTextBoldCursor;
        r0 = r48;
        r5.<init>(r0);
        r4[r11] = r5;
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = java.lang.Integer.valueOf(r11);
        r4.setTag(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1;
        r6 = 1098907648; // 0x41800000 float:16.0 double:5.42932517E-315;
        r4.setTextSize(r5, r6);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "windowBackgroundWhiteHintText";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setHintTextColor(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "windowBackgroundWhiteBlackText";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setTextColor(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 0;
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "windowBackgroundWhiteBlackText";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setCursorColor(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4.setCursorSize(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1069547520; // 0x3fc00000 float:1.5 double:5.28426686E-315;
        r4.setCursorWidth(r5);
        r4 = 3;
        if (r11 != r4) goto L_0x11c1;
    L_0x1024:
        r4 = 1;
        r0 = new android.text.InputFilter[r4];
        r28 = r0;
        r4 = 0;
        r5 = new android.text.InputFilter$LengthFilter;
        r6 = 3;
        r5.<init>(r6);
        r28[r4] = r5;
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r0 = r28;
        r4.setFilters(r0);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 130; // 0x82 float:1.82E-43 double:6.4E-322;
        r4.setInputType(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = android.graphics.Typeface.DEFAULT;
        r4.setTypeface(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = android.text.method.PasswordTransformationMethod.getInstance();
        r4.setTransformationMethod(r5);
    L_0x1060:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 268435461; // 0x10000005 float:2.5243564E-29 double:1.326247394E-315;
        r4.setImeOptions(r5);
        switch(r11) {
            case 0: goto L_0x121b;
            case 1: goto L_0x1245;
            case 2: goto L_0x125a;
            case 3: goto L_0x1230;
            case 4: goto L_0x1284;
            case 5: goto L_0x126f;
            default: goto L_0x106f;
        };
    L_0x106f:
        if (r11 != 0) goto L_0x1299;
    L_0x1071:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = new org.telegram.ui.PaymentFormActivity$12;
        r0 = r47;
        r5.<init>();
        r4.addTextChangedListener(r5);
    L_0x1081:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);
        r4.setPadding(r5, r6, r7, r8);
        r0 = r47;
        r4 = r0.inputFields;
        r5 = r4[r11];
        r4 = org.telegram.messenger.LocaleController.isRTL;
        if (r4 == 0) goto L_0x12ae;
    L_0x109d:
        r4 = 5;
    L_0x109e:
        r5.setGravity(r4);
        r0 = r47;
        r4 = r0.inputFields;
        r45 = r4[r11];
        r4 = -1;
        r5 = -1073741824; // 0xffffffffc0000000 float:-2.0 double:NaN;
        r6 = 51;
        r7 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r8 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r9 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r10 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r4 = org.telegram.ui.Components.LayoutHelper.createFrame(r4, r5, r6, r7, r8, r9, r10);
        r0 = r18;
        r1 = r45;
        r0.addView(r1, r4);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = new org.telegram.ui.PaymentFormActivity$14;
        r0 = r47;
        r5.<init>();
        r4.setOnEditorActionListener(r5);
        r4 = 3;
        if (r11 != r4) goto L_0x12b1;
    L_0x10d2:
        r0 = r47;
        r4 = r0.sectionCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.ShadowSectionCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.sectionCell;
        r6 = 0;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
    L_0x10f4:
        if (r13 == 0) goto L_0x1124;
    L_0x10f6:
        r24 = new android.view.View;
        r0 = r24;
        r1 = r48;
        r0.<init>(r1);
        r0 = r47;
        r4 = r0.dividers;
        r0 = r24;
        r4.add(r0);
        r4 = "divider";
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r0 = r24;
        r0.setBackgroundColor(r4);
        r4 = new android.widget.FrameLayout$LayoutParams;
        r5 = -1;
        r6 = 1;
        r7 = 83;
        r4.<init>(r5, r6, r7);
        r0 = r18;
        r1 = r24;
        r0.addView(r1, r4);
    L_0x1124:
        r4 = 4;
        if (r11 != r4) goto L_0x112d;
    L_0x1127:
        r0 = r47;
        r4 = r0.need_card_country;
        if (r4 == 0) goto L_0x113f;
    L_0x112d:
        r4 = 5;
        if (r11 != r4) goto L_0x1136;
    L_0x1130:
        r0 = r47;
        r4 = r0.need_card_postcode;
        if (r4 == 0) goto L_0x113f;
    L_0x1136:
        r4 = 2;
        if (r11 != r4) goto L_0x1146;
    L_0x1139:
        r0 = r47;
        r4 = r0.need_card_name;
        if (r4 != 0) goto L_0x1146;
    L_0x113f:
        r4 = 8;
        r0 = r18;
        r0.setVisibility(r4);
    L_0x1146:
        r11 = r11 + 1;
        goto L_0x0f24;
    L_0x114a:
        r25 = move-exception;
        r4 = 0;
        r0 = r47;
        r0.need_card_country = r4;	 Catch:{ Exception -> 0x1152 }
        goto L_0x0ef2;
    L_0x1152:
        r25 = move-exception;
        org.telegram.messenger.FileLog.e(r25);
        goto L_0x0f19;
    L_0x1158:
        r25 = move-exception;
        r4 = 0;
        r0 = r47;
        r0.need_card_postcode = r4;	 Catch:{ Exception -> 0x1152 }
        goto L_0x0eff;
    L_0x1160:
        r25 = move-exception;
        r4 = 0;
        r0 = r47;
        r0.need_card_name = r4;	 Catch:{ Exception -> 0x1152 }
        goto L_0x0f0c;
    L_0x1168:
        r25 = move-exception;
        r4 = "";
        r0 = r47;
        r0.stripeApiKey = r4;	 Catch:{ Exception -> 0x1152 }
        goto L_0x0f19;
    L_0x1172:
        r4 = 4;
        if (r11 != r4) goto L_0x0f70;
    L_0x1175:
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 1;
        r6 = new org.telegram.ui.Cells.HeaderCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = "PaymentBillingAddress";
        r6 = 2131232080; // 0x7f080550 float:1.808026E38 double:1.052968554E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setText(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.headerCell;
        r6 = 1;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        goto L_0x0f70;
    L_0x11be:
        r13 = 0;
        goto L_0x0f80;
    L_0x11c1:
        if (r11 != 0) goto L_0x11cf;
    L_0x11c3:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 2;
        r4.setInputType(r5);
        goto L_0x1060;
    L_0x11cf:
        r4 = 4;
        if (r11 != r4) goto L_0x11ee;
    L_0x11d2:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = new org.telegram.ui.PaymentFormActivity$11;
        r0 = r47;
        r5.<init>();
        r4.setOnTouchListener(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 0;
        r4.setInputType(r5);
        goto L_0x1060;
    L_0x11ee:
        r4 = 1;
        if (r11 != r4) goto L_0x11fe;
    L_0x11f1:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 16386; // 0x4002 float:2.2962E-41 double:8.096E-320;
        r4.setInputType(r5);
        goto L_0x1060;
    L_0x11fe:
        r4 = 2;
        if (r11 != r4) goto L_0x120e;
    L_0x1201:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 4097; // 0x1001 float:5.741E-42 double:2.024E-320;
        r4.setInputType(r5);
        goto L_0x1060;
    L_0x120e:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 16385; // 0x4001 float:2.296E-41 double:8.0953E-320;
        r4.setInputType(r5);
        goto L_0x1060;
    L_0x121b:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentCardNumber";
        r6 = 2131232086; // 0x7f080556 float:1.8080271E38 double:1.052968557E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        goto L_0x106f;
    L_0x1230:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentCardCvv";
        r6 = 2131232081; // 0x7f080551 float:1.8080261E38 double:1.0529685545E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        goto L_0x106f;
    L_0x1245:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentCardExpireDate";
        r6 = 2131232082; // 0x7f080552 float:1.8080263E38 double:1.052968555E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        goto L_0x106f;
    L_0x125a:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentCardName";
        r6 = 2131232084; // 0x7f080554 float:1.8080267E38 double:1.052968556E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        goto L_0x106f;
    L_0x126f:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentShippingZipPlaceholder";
        r6 = 2131232121; // 0x7f080579 float:1.8080342E38 double:1.0529685743E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        goto L_0x106f;
    L_0x1284:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentShippingCountry";
        r6 = 2131232111; // 0x7f08056f float:1.8080322E38 double:1.0529685694E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        goto L_0x106f;
    L_0x1299:
        r4 = 1;
        if (r11 != r4) goto L_0x1081;
    L_0x129c:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = new org.telegram.ui.PaymentFormActivity$13;
        r0 = r47;
        r5.<init>();
        r4.addTextChangedListener(r5);
        goto L_0x1081;
    L_0x12ae:
        r4 = 3;
        goto L_0x109e;
    L_0x12b1:
        r4 = 5;
        if (r11 != r4) goto L_0x135f;
    L_0x12b4:
        r0 = r47;
        r4 = r0.sectionCell;
        r5 = 2;
        r6 = new org.telegram.ui.Cells.ShadowSectionCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.sectionCell;
        r6 = 2;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r4 = new org.telegram.ui.Cells.TextCheckCell;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.checkCell1 = r4;
        r0 = r47;
        r4 = r0.checkCell1;
        r5 = 1;
        r5 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r5);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.checkCell1;
        r5 = "PaymentCardSavePaymentInformation";
        r6 = 2131232087; // 0x7f080557 float:1.8080273E38 double:1.0529685575E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r0 = r47;
        r6 = r0.saveCardInfo;
        r7 = 0;
        r4.setTextAndCheck(r5, r6, r7);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.checkCell1;
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r0 = r47;
        r4 = r0.checkCell1;
        r5 = new org.telegram.ui.PaymentFormActivity$15;
        r0 = r47;
        r5.<init>();
        r4.setOnClickListener(r5);
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = 2130837849; // 0x7f020159 float:1.7280664E38 double:1.052773778E-314;
        r6 = "windowBackgroundGrayShadow";
        r0 = r48;
        r5 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r0, r5, r6);
        r4.setBackgroundDrawable(r5);
        r47.updateSavePaymentField();
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.bottomCell;
        r6 = 0;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        goto L_0x10f4;
    L_0x135f:
        if (r11 != 0) goto L_0x10f4;
    L_0x1361:
        r4 = new android.widget.FrameLayout;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.androidPayContainer = r4;
        r0 = r47;
        r4 = r0.androidPayContainer;
        r5 = 4000; // 0xfa0 float:5.605E-42 double:1.9763E-320;
        r4.setId(r5);
        r0 = r47;
        r4 = r0.androidPayContainer;
        r5 = 1;
        r5 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r5);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.androidPayContainer;
        r5 = 8;
        r4.setVisibility(r5);
        r0 = r47;
        r0 = r0.androidPayContainer;
        r45 = r0;
        r4 = -2;
        r5 = -1073741824; // 0xffffffffc0000000 float:-2.0 double:NaN;
        r6 = 21;
        r7 = 0;
        r8 = 0;
        r9 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r10 = 0;
        r4 = org.telegram.ui.Components.LayoutHelper.createFrame(r4, r5, r6, r7, r8, r9, r10);
        r0 = r18;
        r1 = r45;
        r0.addView(r1, r4);
        goto L_0x10f4;
    L_0x13a7:
        r0 = r47;
        r4 = r0.need_card_country;
        if (r4 != 0) goto L_0x13cb;
    L_0x13ad:
        r0 = r47;
        r4 = r0.need_card_postcode;
        if (r4 != 0) goto L_0x13cb;
    L_0x13b3:
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = 8;
        r4.setVisibility(r5);
        r0 = r47;
        r4 = r0.sectionCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = 8;
        r4.setVisibility(r5);
    L_0x13cb:
        r0 = r47;
        r4 = r0.need_card_postcode;
        if (r4 == 0) goto L_0x13e0;
    L_0x13d1:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 5;
        r4 = r4[r5];
        r5 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r4.setImeOptions(r5);
        goto L_0x0c3b;
    L_0x13e0:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = 3;
        r4 = r4[r5];
        r5 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r4.setImeOptions(r5);
        goto L_0x0c3b;
    L_0x13ef:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 1;
        if (r4 != r5) goto L_0x14d1;
    L_0x13f6:
        r0 = r47;
        r4 = r0.requestedInfo;
        r4 = r4.shipping_options;
        r20 = r4.size();
        r0 = r20;
        r4 = new org.telegram.ui.Cells.RadioCell[r0];
        r0 = r47;
        r0.radioCells = r4;
        r11 = 0;
    L_0x1409:
        r0 = r20;
        if (r11 >= r0) goto L_0x1497;
    L_0x140d:
        r0 = r47;
        r4 = r0.requestedInfo;
        r4 = r4.shipping_options;
        r40 = r4.get(r11);
        r40 = (org.telegram.tgnet.TLRPC$TL_shippingOption) r40;
        r0 = r47;
        r4 = r0.radioCells;
        r5 = new org.telegram.ui.Cells.RadioCell;
        r0 = r48;
        r5.<init>(r0);
        r4[r11] = r5;
        r0 = r47;
        r4 = r0.radioCells;
        r4 = r4[r11];
        r5 = java.lang.Integer.valueOf(r11);
        r4.setTag(r5);
        r0 = r47;
        r4 = r0.radioCells;
        r4 = r4[r11];
        r5 = 1;
        r5 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r5);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.radioCells;
        r6 = r4[r11];
        r4 = "%s - %s";
        r5 = 2;
        r5 = new java.lang.Object[r5];
        r7 = 0;
        r0 = r40;
        r8 = r0.prices;
        r0 = r47;
        r8 = r0.getTotalPriceString(r8);
        r5[r7] = r8;
        r7 = 1;
        r0 = r40;
        r8 = r0.title;
        r5[r7] = r8;
        r7 = java.lang.String.format(r4, r5);
        if (r11 != 0) goto L_0x1492;
    L_0x1467:
        r4 = 1;
        r5 = r4;
    L_0x1469:
        r4 = r20 + -1;
        if (r11 == r4) goto L_0x1495;
    L_0x146d:
        r4 = 1;
    L_0x146e:
        r6.setText(r7, r5, r4);
        r0 = r47;
        r4 = r0.radioCells;
        r4 = r4[r11];
        r5 = new org.telegram.ui.PaymentFormActivity$16;
        r0 = r47;
        r5.<init>();
        r4.setOnClickListener(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.radioCells;
        r5 = r5[r11];
        r4.addView(r5);
        r11 = r11 + 1;
        goto L_0x1409;
    L_0x1492:
        r4 = 0;
        r5 = r4;
        goto L_0x1469;
    L_0x1495:
        r4 = 0;
        goto L_0x146e;
    L_0x1497:
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = 2130837849; // 0x7f020159 float:1.7280664E38 double:1.052773778E-314;
        r6 = "windowBackgroundGrayShadow";
        r0 = r48;
        r5 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r0, r5, r6);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.bottomCell;
        r6 = 0;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        goto L_0x0c3b;
    L_0x14d1:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 3;
        if (r4 != r5) goto L_0x17d2;
    L_0x14d8:
        r4 = 2;
        r4 = new org.telegram.ui.Components.EditTextBoldCursor[r4];
        r0 = r47;
        r0.inputFields = r4;
        r11 = 0;
    L_0x14e0:
        r4 = 2;
        if (r11 >= r4) goto L_0x0c3b;
    L_0x14e3:
        if (r11 != 0) goto L_0x152c;
    L_0x14e5:
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.HeaderCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = "PaymentCardTitle";
        r6 = 2131232090; // 0x7f08055a float:1.808028E38 double:1.052968559E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setText(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.headerCell;
        r6 = 0;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
    L_0x152c:
        r18 = new android.widget.FrameLayout;
        r0 = r18;
        r1 = r48;
        r0.<init>(r1);
        r0 = r47;
        r4 = r0.linearLayout2;
        r5 = -1;
        r6 = 48;
        r5 = org.telegram.ui.Components.LayoutHelper.createLinear(r5, r6);
        r0 = r18;
        r4.addView(r0, r5);
        r4 = "windowBackgroundWhite";
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r0 = r18;
        r0.setBackgroundColor(r4);
        r4 = 1;
        if (r11 == r4) goto L_0x1769;
    L_0x1554:
        r13 = 1;
    L_0x1555:
        if (r13 == 0) goto L_0x1565;
    L_0x1557:
        r4 = 7;
        if (r11 != r4) goto L_0x176c;
    L_0x155a:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.phone_requested;
        if (r4 != 0) goto L_0x176c;
    L_0x1564:
        r13 = 0;
    L_0x1565:
        if (r13 == 0) goto L_0x1595;
    L_0x1567:
        r24 = new android.view.View;
        r0 = r24;
        r1 = r48;
        r0.<init>(r1);
        r0 = r47;
        r4 = r0.dividers;
        r0 = r24;
        r4.add(r0);
        r4 = "divider";
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r0 = r24;
        r0.setBackgroundColor(r4);
        r4 = new android.widget.FrameLayout$LayoutParams;
        r5 = -1;
        r6 = 1;
        r7 = 83;
        r4.<init>(r5, r6, r7);
        r0 = r18;
        r1 = r24;
        r0.addView(r1, r4);
    L_0x1595:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = new org.telegram.ui.Components.EditTextBoldCursor;
        r0 = r48;
        r5.<init>(r0);
        r4[r11] = r5;
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = java.lang.Integer.valueOf(r11);
        r4.setTag(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1;
        r6 = 1098907648; // 0x41800000 float:16.0 double:5.42932517E-315;
        r4.setTextSize(r5, r6);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "windowBackgroundWhiteHintText";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setHintTextColor(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "windowBackgroundWhiteBlackText";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setTextColor(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 0;
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "windowBackgroundWhiteBlackText";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setCursorColor(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4.setCursorSize(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1069547520; // 0x3fc00000 float:1.5 double:5.28426686E-315;
        r4.setCursorWidth(r5);
        if (r11 != 0) goto L_0x1786;
    L_0x1611:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = new org.telegram.ui.PaymentFormActivity$17;
        r0 = r47;
        r5.<init>();
        r4.setOnTouchListener(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 0;
        r4.setInputType(r5);
    L_0x162b:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r4.setImeOptions(r5);
        switch(r11) {
            case 0: goto L_0x179e;
            case 1: goto L_0x17b1;
            default: goto L_0x163a;
        };
    L_0x163a:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);
        r4.setPadding(r5, r6, r7, r8);
        r0 = r47;
        r4 = r0.inputFields;
        r5 = r4[r11];
        r4 = org.telegram.messenger.LocaleController.isRTL;
        if (r4 == 0) goto L_0x17cf;
    L_0x1656:
        r4 = 5;
    L_0x1657:
        r5.setGravity(r4);
        r0 = r47;
        r4 = r0.inputFields;
        r45 = r4[r11];
        r4 = -1;
        r5 = -1073741824; // 0xffffffffc0000000 float:-2.0 double:NaN;
        r6 = 51;
        r7 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r8 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r9 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r10 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r4 = org.telegram.ui.Components.LayoutHelper.createFrame(r4, r5, r6, r7, r8, r9, r10);
        r0 = r18;
        r1 = r45;
        r0.addView(r1, r4);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = new org.telegram.ui.PaymentFormActivity$18;
        r0 = r47;
        r5.<init>();
        r4.setOnEditorActionListener(r5);
        r4 = 1;
        if (r11 != r4) goto L_0x1765;
    L_0x168b:
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = "PaymentConfirmationMessage";
        r6 = 2131232099; // 0x7f080563 float:1.8080298E38 double:1.0529685634E-314;
        r7 = 1;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r0 = r47;
        r9 = r0.paymentForm;
        r9 = r9.saved_credentials;
        r9 = r9.title;
        r7[r8] = r9;
        r5 = org.telegram.messenger.LocaleController.formatString(r5, r6, r7);
        r4.setText(r5);
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = 2130837848; // 0x7f020158 float:1.7280662E38 double:1.0527737776E-314;
        r6 = "windowBackgroundGrayShadow";
        r0 = r48;
        r5 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r0, r5, r6);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.bottomCell;
        r6 = 0;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r4 = new org.telegram.ui.Cells.TextSettingsCell;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.settingsCell1 = r4;
        r0 = r47;
        r4 = r0.settingsCell1;
        r5 = 1;
        r5 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r5);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.settingsCell1;
        r5 = "PaymentConfirmationNewCard";
        r6 = 2131232100; // 0x7f080564 float:1.80803E38 double:1.052968564E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r6 = 0;
        r4.setText(r5, r6);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.settingsCell1;
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r0 = r47;
        r4 = r0.settingsCell1;
        r5 = new org.telegram.ui.PaymentFormActivity$19;
        r0 = r47;
        r5.<init>();
        r4.setOnClickListener(r5);
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 1;
        r6 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = 2130837849; // 0x7f020159 float:1.7280664E38 double:1.052773778E-314;
        r6 = "windowBackgroundGrayShadow";
        r0 = r48;
        r5 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r0, r5, r6);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.bottomCell;
        r6 = 1;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
    L_0x1765:
        r11 = r11 + 1;
        goto L_0x14e0;
    L_0x1769:
        r13 = 0;
        goto L_0x1555;
    L_0x176c:
        r4 = 6;
        if (r11 != r4) goto L_0x1565;
    L_0x176f:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.phone_requested;
        if (r4 != 0) goto L_0x1565;
    L_0x1779:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.email_requested;
        if (r4 != 0) goto L_0x1565;
    L_0x1783:
        r13 = 0;
        goto L_0x1565;
    L_0x1786:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 129; // 0x81 float:1.81E-43 double:6.37E-322;
        r4.setInputType(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = android.graphics.Typeface.DEFAULT;
        r4.setTypeface(r5);
        goto L_0x162b;
    L_0x179e:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r0 = r47;
        r5 = r0.paymentForm;
        r5 = r5.saved_credentials;
        r5 = r5.title;
        r4.setText(r5);
        goto L_0x163a;
    L_0x17b1:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "LoginPassword";
        r6 = 2131231765; // 0x7f080415 float:1.807962E38 double:1.0529683984E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r4.requestFocus();
        goto L_0x163a;
    L_0x17cf:
        r4 = 3;
        goto L_0x1657;
    L_0x17d2:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 4;
        if (r4 == r5) goto L_0x17e0;
    L_0x17d9:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 5;
        if (r4 != r5) goto L_0x1da6;
    L_0x17e0:
        r4 = new org.telegram.ui.Cells.PaymentInfoCell;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.paymentInfoCell = r4;
        r0 = r47;
        r4 = r0.paymentInfoCell;
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r0 = r47;
        r5 = r0.paymentInfoCell;
        r0 = r47;
        r4 = r0.messageObject;
        r4 = r4.messageOwner;
        r4 = r4.media;
        r4 = (org.telegram.tgnet.TLRPC$TL_messageMediaInvoice) r4;
        r0 = r47;
        r6 = r0.currentBotName;
        r5.setInvoice(r4, r6);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.paymentInfoCell;
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r0 = r47;
        r4 = r0.sectionCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.ShadowSectionCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.sectionCell;
        r6 = 0;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r16 = new java.util.ArrayList;
        r16.<init>();
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.invoice;
        r4 = r4.prices;
        r0 = r16;
        r0.addAll(r4);
        r0 = r47;
        r4 = r0.shippingOption;
        if (r4 == 0) goto L_0x1864;
    L_0x1859:
        r0 = r47;
        r4 = r0.shippingOption;
        r4 = r4.prices;
        r0 = r16;
        r0.addAll(r4);
    L_0x1864:
        r0 = r47;
        r1 = r16;
        r42 = r0.getTotalPriceString(r1);
        r11 = 0;
    L_0x186d:
        r4 = r16.size();
        if (r11 >= r4) goto L_0x18ba;
    L_0x1873:
        r0 = r16;
        r34 = r0.get(r11);
        r34 = (org.telegram.tgnet.TLRPC$TL_labeledPrice) r34;
        r35 = new org.telegram.ui.Cells.TextPriceCell;
        r0 = r35;
        r1 = r48;
        r0.<init>(r1);
        r4 = "windowBackgroundWhite";
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r0 = r35;
        r0.setBackgroundColor(r4);
        r0 = r34;
        r4 = r0.label;
        r5 = org.telegram.messenger.LocaleController.getInstance();
        r0 = r34;
        r6 = r0.amount;
        r0 = r47;
        r8 = r0.paymentForm;
        r8 = r8.invoice;
        r8 = r8.currency;
        r5 = r5.formatCurrencyString(r6, r8);
        r6 = 0;
        r0 = r35;
        r0.setTextAndValue(r4, r5, r6);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r35;
        r4.addView(r0);
        r11 = r11 + 1;
        goto L_0x186d;
    L_0x18ba:
        r35 = new org.telegram.ui.Cells.TextPriceCell;
        r0 = r35;
        r1 = r48;
        r0.<init>(r1);
        r4 = "windowBackgroundWhite";
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r0 = r35;
        r0.setBackgroundColor(r4);
        r4 = "PaymentTransactionTotal";
        r5 = 2131232127; // 0x7f08057f float:1.8080354E38 double:1.0529685773E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r5 = 1;
        r0 = r35;
        r1 = r42;
        r0.setTextAndValue(r4, r1, r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r35;
        r4.addView(r0);
        r24 = new android.view.View;
        r0 = r24;
        r1 = r48;
        r0.<init>(r1);
        r0 = r47;
        r4 = r0.dividers;
        r0 = r24;
        r4.add(r0);
        r4 = "divider";
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r0 = r24;
        r0.setBackgroundColor(r4);
        r0 = r47;
        r4 = r0.linearLayout2;
        r5 = new android.widget.FrameLayout$LayoutParams;
        r6 = -1;
        r7 = 1;
        r8 = 83;
        r5.<init>(r6, r7, r8);
        r0 = r24;
        r4.addView(r0, r5);
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = 1;
        r5 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r5);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 0;
        r4 = r4[r5];
        r0 = r47;
        r5 = r0.cardName;
        r6 = "PaymentCheckoutMethod";
        r7 = 2131232093; // 0x7f08055d float:1.8080286E38 double:1.0529685605E-314;
        r6 = org.telegram.messenger.LocaleController.getString(r6, r7);
        r7 = 1;
        r4.setTextAndValue(r5, r6, r7);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.detailSettingsCell;
        r6 = 0;
        r5 = r5[r6];
        r4.addView(r5);
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 4;
        if (r4 != r5) goto L_0x1976;
    L_0x1965:
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = new org.telegram.ui.PaymentFormActivity$20;
        r0 = r47;
        r5.<init>();
        r4.setOnClickListener(r5);
    L_0x1976:
        r37 = 0;
        r11 = 0;
    L_0x1979:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.users;
        r4 = r4.size();
        if (r11 >= r4) goto L_0x19a2;
    L_0x1985:
        r0 = r47;
        r4 = r0.paymentForm;
        r4 = r4.users;
        r43 = r4.get(r11);
        r43 = (org.telegram.tgnet.TLRPC.User) r43;
        r0 = r43;
        r4 = r0.id;
        r0 = r47;
        r5 = r0.paymentForm;
        r5 = r5.provider_id;
        if (r4 != r5) goto L_0x199f;
    L_0x199d:
        r37 = r43;
    L_0x199f:
        r11 = r11 + 1;
        goto L_0x1979;
    L_0x19a2:
        if (r37 == 0) goto L_0x1da1;
    L_0x19a4:
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 1;
        r6 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = 1;
        r5 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r5);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 1;
        r4 = r4[r5];
        r0 = r37;
        r5 = r0.first_name;
        r0 = r37;
        r6 = r0.last_name;
        r36 = org.telegram.messenger.ContactsController.formatName(r5, r6);
        r5 = "PaymentCheckoutProvider";
        r6 = 2131232097; // 0x7f080561 float:1.8080294E38 double:1.0529685624E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r6 = 1;
        r0 = r36;
        r4.setTextAndValue(r0, r5, r6);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.detailSettingsCell;
        r6 = 1;
        r5 = r5[r6];
        r4.addView(r5);
    L_0x19f2:
        r0 = r47;
        r4 = r0.validateRequest;
        if (r4 == 0) goto L_0x1bee;
    L_0x19f8:
        r0 = r47;
        r4 = r0.validateRequest;
        r4 = r4.info;
        r4 = r4.shipping_address;
        if (r4 == 0) goto L_0x1a9c;
    L_0x1a02:
        r4 = "%s %s, %s, %s, %s, %s";
        r5 = 6;
        r5 = new java.lang.Object[r5];
        r6 = 0;
        r0 = r47;
        r7 = r0.validateRequest;
        r7 = r7.info;
        r7 = r7.shipping_address;
        r7 = r7.street_line1;
        r5[r6] = r7;
        r6 = 1;
        r0 = r47;
        r7 = r0.validateRequest;
        r7 = r7.info;
        r7 = r7.shipping_address;
        r7 = r7.street_line2;
        r5[r6] = r7;
        r6 = 2;
        r0 = r47;
        r7 = r0.validateRequest;
        r7 = r7.info;
        r7 = r7.shipping_address;
        r7 = r7.city;
        r5[r6] = r7;
        r6 = 3;
        r0 = r47;
        r7 = r0.validateRequest;
        r7 = r7.info;
        r7 = r7.shipping_address;
        r7 = r7.state;
        r5[r6] = r7;
        r6 = 4;
        r0 = r47;
        r7 = r0.validateRequest;
        r7 = r7.info;
        r7 = r7.shipping_address;
        r7 = r7.country_iso2;
        r5[r6] = r7;
        r6 = 5;
        r0 = r47;
        r7 = r0.validateRequest;
        r7 = r7.info;
        r7 = r7.shipping_address;
        r7 = r7.post_code;
        r5[r6] = r7;
        r12 = java.lang.String.format(r4, r5);
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 2;
        r6 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 2;
        r4 = r4[r5];
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 2;
        r4 = r4[r5];
        r5 = "PaymentShippingAddress";
        r6 = 2131232107; // 0x7f08056b float:1.8080314E38 double:1.0529685674E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r6 = 1;
        r4.setTextAndValue(r12, r5, r6);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.detailSettingsCell;
        r6 = 2;
        r5 = r5[r6];
        r4.addView(r5);
    L_0x1a9c:
        r0 = r47;
        r4 = r0.validateRequest;
        r4 = r4.info;
        r4 = r4.name;
        if (r4 == 0) goto L_0x1af0;
    L_0x1aa6:
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 3;
        r6 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 3;
        r4 = r4[r5];
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 3;
        r4 = r4[r5];
        r0 = r47;
        r5 = r0.validateRequest;
        r5 = r5.info;
        r5 = r5.name;
        r6 = "PaymentCheckoutName";
        r7 = 2131232094; // 0x7f08055e float:1.8080288E38 double:1.052968561E-314;
        r6 = org.telegram.messenger.LocaleController.getString(r6, r7);
        r7 = 1;
        r4.setTextAndValue(r5, r6, r7);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.detailSettingsCell;
        r6 = 3;
        r5 = r5[r6];
        r4.addView(r5);
    L_0x1af0:
        r0 = r47;
        r4 = r0.validateRequest;
        r4 = r4.info;
        r4 = r4.phone;
        if (r4 == 0) goto L_0x1b4c;
    L_0x1afa:
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 4;
        r6 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 4;
        r4 = r4[r5];
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 4;
        r4 = r4[r5];
        r5 = org.telegram.PhoneFormat.PhoneFormat.getInstance();
        r0 = r47;
        r6 = r0.validateRequest;
        r6 = r6.info;
        r6 = r6.phone;
        r5 = r5.format(r6);
        r6 = "PaymentCheckoutPhoneNumber";
        r7 = 2131232096; // 0x7f080560 float:1.8080292E38 double:1.052968562E-314;
        r6 = org.telegram.messenger.LocaleController.getString(r6, r7);
        r7 = 1;
        r4.setTextAndValue(r5, r6, r7);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.detailSettingsCell;
        r6 = 4;
        r5 = r5[r6];
        r4.addView(r5);
    L_0x1b4c:
        r0 = r47;
        r4 = r0.validateRequest;
        r4 = r4.info;
        r4 = r4.email;
        if (r4 == 0) goto L_0x1ba0;
    L_0x1b56:
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 5;
        r6 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 5;
        r4 = r4[r5];
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 5;
        r4 = r4[r5];
        r0 = r47;
        r5 = r0.validateRequest;
        r5 = r5.info;
        r5 = r5.email;
        r6 = "PaymentCheckoutEmail";
        r7 = 2131232092; // 0x7f08055c float:1.8080283E38 double:1.05296856E-314;
        r6 = org.telegram.messenger.LocaleController.getString(r6, r7);
        r7 = 1;
        r4.setTextAndValue(r5, r6, r7);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.detailSettingsCell;
        r6 = 5;
        r5 = r5[r6];
        r4.addView(r5);
    L_0x1ba0:
        r0 = r47;
        r4 = r0.shippingOption;
        if (r4 == 0) goto L_0x1bee;
    L_0x1ba6:
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 6;
        r6 = new org.telegram.ui.Cells.TextDetailSettingsCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 6;
        r4 = r4[r5];
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r0 = r47;
        r4 = r0.detailSettingsCell;
        r5 = 6;
        r4 = r4[r5];
        r0 = r47;
        r5 = r0.shippingOption;
        r5 = r5.title;
        r6 = "PaymentCheckoutShippingMethod";
        r7 = 2131232098; // 0x7f080562 float:1.8080296E38 double:1.052968563E-314;
        r6 = org.telegram.messenger.LocaleController.getString(r6, r7);
        r7 = 0;
        r4.setTextAndValue(r5, r6, r7);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.detailSettingsCell;
        r6 = 6;
        r5 = r5[r6];
        r4.addView(r5);
    L_0x1bee:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 4;
        if (r4 != r5) goto L_0x1d67;
    L_0x1bf5:
        r4 = new android.widget.FrameLayout;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.bottomLayout = r4;
        r0 = r47;
        r4 = r0.bottomLayout;
        r5 = 1;
        r5 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r5);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.bottomLayout;
        r5 = -1;
        r6 = 48;
        r7 = 80;
        r5 = org.telegram.ui.Components.LayoutHelper.createFrame(r5, r6, r7);
        r0 = r26;
        r0.addView(r4, r5);
        r0 = r47;
        r4 = r0.bottomLayout;
        r5 = new org.telegram.ui.PaymentFormActivity$21;
        r0 = r47;
        r1 = r36;
        r2 = r42;
        r5.<init>(r1, r2);
        r4.setOnClickListener(r5);
        r4 = new android.widget.TextView;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.payTextView = r4;
        r0 = r47;
        r4 = r0.payTextView;
        r5 = "windowBackgroundWhiteBlueText6";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setTextColor(r5);
        r0 = r47;
        r4 = r0.payTextView;
        r5 = "PaymentCheckoutPay";
        r6 = 2131232095; // 0x7f08055f float:1.808029E38 double:1.0529685615E-314;
        r7 = 1;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r7[r8] = r42;
        r5 = org.telegram.messenger.LocaleController.formatString(r5, r6, r7);
        r4.setText(r5);
        r0 = r47;
        r4 = r0.payTextView;
        r5 = 1;
        r6 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r4.setTextSize(r5, r6);
        r0 = r47;
        r4 = r0.payTextView;
        r5 = 17;
        r4.setGravity(r5);
        r0 = r47;
        r4 = r0.payTextView;
        r5 = "fonts/rmedium.ttf";
        r5 = org.telegram.messenger.AndroidUtilities.getTypeface(r5);
        r4.setTypeface(r5);
        r0 = r47;
        r4 = r0.bottomLayout;
        r0 = r47;
        r5 = r0.payTextView;
        r6 = -1;
        r7 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r6 = org.telegram.ui.Components.LayoutHelper.createFrame(r6, r7);
        r4.addView(r5, r6);
        r4 = new org.telegram.ui.Components.ContextProgressView;
        r5 = 0;
        r0 = r48;
        r4.<init>(r0, r5);
        r0 = r47;
        r0.progressViewButton = r4;
        r0 = r47;
        r4 = r0.progressViewButton;
        r5 = 4;
        r4.setVisibility(r5);
        r0 = r47;
        r4 = r0.bottomLayout;
        r0 = r47;
        r5 = r0.progressViewButton;
        r6 = -1;
        r7 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r6 = org.telegram.ui.Components.LayoutHelper.createFrame(r6, r7);
        r4.addView(r5, r6);
        r39 = new android.view.View;
        r0 = r39;
        r1 = r48;
        r0.<init>(r1);
        r4 = 2130837860; // 0x7f020164 float:1.7280686E38 double:1.0527737835E-314;
        r0 = r39;
        r0.setBackgroundResource(r4);
        r4 = -1;
        r5 = 1077936128; // 0x40400000 float:3.0 double:5.325712093E-315;
        r6 = 83;
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r10 = 1111490560; // 0x42400000 float:48.0 double:5.491493014E-315;
        r4 = org.telegram.ui.Components.LayoutHelper.createFrame(r4, r5, r6, r7, r8, r9, r10);
        r0 = r26;
        r1 = r39;
        r0.addView(r1, r4);
        r0 = r47;
        r4 = r0.doneItem;
        r5 = 0;
        r4.setEnabled(r5);
        r0 = r47;
        r4 = r0.doneItem;
        r4 = r4.getImageView();
        r5 = 4;
        r4.setVisibility(r5);
        r4 = new org.telegram.ui.PaymentFormActivity$22;
        r0 = r47;
        r1 = r48;
        r4.<init>(r1);
        r0 = r47;
        r0.webView = r4;
        r0 = r47;
        r4 = r0.webView;
        r5 = -1;
        r4.setBackgroundColor(r5);
        r0 = r47;
        r4 = r0.webView;
        r4 = r4.getSettings();
        r5 = 1;
        r4.setJavaScriptEnabled(r5);
        r0 = r47;
        r4 = r0.webView;
        r4 = r4.getSettings();
        r5 = 1;
        r4.setDomStorageEnabled(r5);
        r4 = android.os.Build.VERSION.SDK_INT;
        r5 = 21;
        if (r4 < r5) goto L_0x1d40;
    L_0x1d26:
        r0 = r47;
        r4 = r0.webView;
        r4 = r4.getSettings();
        r5 = 0;
        r4.setMixedContentMode(r5);
        r19 = android.webkit.CookieManager.getInstance();
        r0 = r47;
        r4 = r0.webView;
        r5 = 1;
        r0 = r19;
        r0.setAcceptThirdPartyCookies(r4, r5);
    L_0x1d40:
        r0 = r47;
        r4 = r0.webView;
        r5 = new org.telegram.ui.PaymentFormActivity$23;
        r0 = r47;
        r5.<init>();
        r4.setWebViewClient(r5);
        r0 = r47;
        r4 = r0.webView;
        r5 = -1;
        r6 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r5 = org.telegram.ui.Components.LayoutHelper.createFrame(r5, r6);
        r0 = r26;
        r0.addView(r4, r5);
        r0 = r47;
        r4 = r0.webView;
        r5 = 8;
        r4.setVisibility(r5);
    L_0x1d67:
        r0 = r47;
        r4 = r0.sectionCell;
        r5 = 1;
        r6 = new org.telegram.ui.Cells.ShadowSectionCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.sectionCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = 2130837849; // 0x7f020159 float:1.7280664E38 double:1.052773778E-314;
        r6 = "windowBackgroundGrayShadow";
        r0 = r48;
        r5 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r0, r5, r6);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.sectionCell;
        r6 = 1;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        goto L_0x0c3b;
    L_0x1da1:
        r36 = "";
        goto L_0x19f2;
    L_0x1da6:
        r0 = r47;
        r4 = r0.currentStep;
        r5 = 6;
        if (r4 != r5) goto L_0x0c3b;
    L_0x1dad:
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 2;
        r6 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 2;
        r4 = r4[r5];
        r5 = 2130837848; // 0x7f020158 float:1.7280662E38 double:1.0527737776E-314;
        r6 = "windowBackgroundGrayShadow";
        r0 = r48;
        r5 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r0, r5, r6);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.bottomCell;
        r6 = 2;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r4 = new org.telegram.ui.Cells.TextSettingsCell;
        r0 = r48;
        r4.<init>(r0);
        r0 = r47;
        r0.settingsCell1 = r4;
        r0 = r47;
        r4 = r0.settingsCell1;
        r5 = 1;
        r5 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r5);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.settingsCell1;
        r5 = "windowBackgroundWhiteRedText3";
        r4.setTag(r5);
        r0 = r47;
        r4 = r0.settingsCell1;
        r5 = "windowBackgroundWhiteRedText3";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setTextColor(r5);
        r0 = r47;
        r4 = r0.settingsCell1;
        r5 = "AbortPassword";
        r6 = 2131230773; // 0x7f080035 float:1.8077608E38 double:1.0529679083E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r6 = 0;
        r4.setText(r5, r6);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.settingsCell1;
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        r0 = r47;
        r4 = r0.settingsCell1;
        r5 = new org.telegram.ui.PaymentFormActivity$24;
        r0 = r47;
        r5.<init>();
        r4.setOnClickListener(r5);
        r4 = 3;
        r4 = new org.telegram.ui.Components.EditTextBoldCursor[r4];
        r0 = r47;
        r0.inputFields = r4;
        r11 = 0;
    L_0x1e4d:
        r4 = 3;
        if (r11 >= r4) goto L_0x2129;
    L_0x1e50:
        if (r11 != 0) goto L_0x2033;
    L_0x1e52:
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.HeaderCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = "PaymentPasswordTitle";
        r6 = 2131232921; // 0x7f080899 float:1.8081965E38 double:1.0529689696E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setText(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.headerCell;
        r6 = 0;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
    L_0x1e99:
        r18 = new android.widget.FrameLayout;
        r0 = r18;
        r1 = r48;
        r0.<init>(r1);
        r0 = r47;
        r4 = r0.linearLayout2;
        r5 = -1;
        r6 = 48;
        r5 = org.telegram.ui.Components.LayoutHelper.createLinear(r5, r6);
        r0 = r18;
        r4.addView(r0, r5);
        r4 = "windowBackgroundWhite";
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r0 = r18;
        r0.setBackgroundColor(r4);
        if (r11 != 0) goto L_0x1eee;
    L_0x1ec0:
        r24 = new android.view.View;
        r0 = r24;
        r1 = r48;
        r0.<init>(r1);
        r0 = r47;
        r4 = r0.dividers;
        r0 = r24;
        r4.add(r0);
        r4 = "divider";
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r0 = r24;
        r0.setBackgroundColor(r4);
        r4 = new android.widget.FrameLayout$LayoutParams;
        r5 = -1;
        r6 = 1;
        r7 = 83;
        r4.<init>(r5, r6, r7);
        r0 = r18;
        r1 = r24;
        r0.addView(r1, r4);
    L_0x1eee:
        r0 = r47;
        r4 = r0.inputFields;
        r5 = new org.telegram.ui.Components.EditTextBoldCursor;
        r0 = r48;
        r5.<init>(r0);
        r4[r11] = r5;
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = java.lang.Integer.valueOf(r11);
        r4.setTag(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1;
        r6 = 1098907648; // 0x41800000 float:16.0 double:5.42932517E-315;
        r4.setTextSize(r5, r6);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "windowBackgroundWhiteHintText";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setHintTextColor(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "windowBackgroundWhiteBlackText";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setTextColor(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 0;
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "windowBackgroundWhiteBlackText";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setCursorColor(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4.setCursorSize(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 1069547520; // 0x3fc00000 float:1.5 double:5.28426686E-315;
        r4.setCursorWidth(r5);
        if (r11 == 0) goto L_0x1f6d;
    L_0x1f6a:
        r4 = 1;
        if (r11 != r4) goto L_0x207f;
    L_0x1f6d:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 129; // 0x81 float:1.81E-43 double:6.37E-322;
        r4.setInputType(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = android.graphics.Typeface.DEFAULT;
        r4.setTypeface(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 268435461; // 0x10000005 float:2.5243564E-29 double:1.326247394E-315;
        r4.setImeOptions(r5);
    L_0x1f8f:
        switch(r11) {
            case 0: goto L_0x208d;
            case 1: goto L_0x20ab;
            case 2: goto L_0x20c0;
            default: goto L_0x1f92;
        };
    L_0x1f92:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);
        r4.setPadding(r5, r6, r7, r8);
        r0 = r47;
        r4 = r0.inputFields;
        r5 = r4[r11];
        r4 = org.telegram.messenger.LocaleController.isRTL;
        if (r4 == 0) goto L_0x20d5;
    L_0x1fae:
        r4 = 5;
    L_0x1faf:
        r5.setGravity(r4);
        r0 = r47;
        r4 = r0.inputFields;
        r45 = r4[r11];
        r4 = -1;
        r5 = -1073741824; // 0xffffffffc0000000 float:-2.0 double:NaN;
        r6 = 51;
        r7 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r8 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r9 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r10 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r4 = org.telegram.ui.Components.LayoutHelper.createFrame(r4, r5, r6, r7, r8, r9, r10);
        r0 = r18;
        r1 = r45;
        r0.addView(r1, r4);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = new org.telegram.ui.PaymentFormActivity$25;
        r0 = r47;
        r5.<init>();
        r4.setOnEditorActionListener(r5);
        r4 = 1;
        if (r11 != r4) goto L_0x20d8;
    L_0x1fe3:
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r6 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = "PaymentPasswordInfo";
        r6 = 2131232919; // 0x7f080897 float:1.808196E38 double:1.0529689686E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setText(r5);
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 0;
        r4 = r4[r5];
        r5 = 2130837848; // 0x7f020158 float:1.7280662E38 double:1.0527737776E-314;
        r6 = "windowBackgroundGrayShadow";
        r0 = r48;
        r5 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r0, r5, r6);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.bottomCell;
        r6 = 0;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
    L_0x202f:
        r11 = r11 + 1;
        goto L_0x1e4d;
    L_0x2033:
        r4 = 2;
        if (r11 != r4) goto L_0x1e99;
    L_0x2036:
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 1;
        r6 = new org.telegram.ui.Cells.HeaderCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = "windowBackgroundWhite";
        r5 = org.telegram.ui.ActionBar.Theme.getColor(r5);
        r4.setBackgroundColor(r5);
        r0 = r47;
        r4 = r0.headerCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = "PaymentPasswordEmailTitle";
        r6 = 2131232917; // 0x7f080895 float:1.8081957E38 double:1.0529689676E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setText(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.headerCell;
        r6 = 1;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        goto L_0x1e99;
    L_0x207f:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = 268435462; // 0x10000006 float:2.5243567E-29 double:1.3262474E-315;
        r4.setImeOptions(r5);
        goto L_0x1f8f;
    L_0x208d:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentPasswordEnter";
        r6 = 2131232918; // 0x7f080896 float:1.8081959E38 double:1.052968968E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r4.requestFocus();
        goto L_0x1f92;
    L_0x20ab:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentPasswordReEnter";
        r6 = 2131232920; // 0x7f080898 float:1.8081963E38 double:1.052968969E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        goto L_0x1f92;
    L_0x20c0:
        r0 = r47;
        r4 = r0.inputFields;
        r4 = r4[r11];
        r5 = "PaymentPasswordEmail";
        r6 = 2131232915; // 0x7f080893 float:1.8081953E38 double:1.0529689666E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setHint(r5);
        goto L_0x1f92;
    L_0x20d5:
        r4 = 3;
        goto L_0x1faf;
    L_0x20d8:
        r4 = 2;
        if (r11 != r4) goto L_0x202f;
    L_0x20db:
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 1;
        r6 = new org.telegram.ui.Cells.TextInfoPrivacyCell;
        r0 = r48;
        r6.<init>(r0);
        r4[r5] = r6;
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = "PaymentPasswordEmailInfo";
        r6 = 2131232916; // 0x7f080894 float:1.8081955E38 double:1.052968967E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r4.setText(r5);
        r0 = r47;
        r4 = r0.bottomCell;
        r5 = 1;
        r4 = r4[r5];
        r5 = 2130837849; // 0x7f020159 float:1.7280664E38 double:1.052773778E-314;
        r6 = "windowBackgroundGrayShadow";
        r0 = r48;
        r5 = org.telegram.ui.ActionBar.Theme.getThemedDrawable(r0, r5, r6);
        r4.setBackgroundDrawable(r5);
        r0 = r47;
        r4 = r0.linearLayout2;
        r0 = r47;
        r5 = r0.bottomCell;
        r6 = 1;
        r5 = r5[r6];
        r6 = -1;
        r7 = -2;
        r6 = org.telegram.ui.Components.LayoutHelper.createLinear(r6, r7);
        r4.addView(r5, r6);
        goto L_0x202f;
    L_0x2129:
        r47.updatePasswordFields();
        goto L_0x0c3b;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.PaymentFormActivity.createView(android.content.Context):android.view.View");
    }

    private void updatePasswordFields() {
        if (this.currentStep == 6 && this.bottomCell[2] != null) {
            int a;
            if (this.currentPassword == null) {
                this.doneItem.setVisibility(0);
                showEditDoneProgress(true, true);
                this.bottomCell[2].setVisibility(8);
                this.settingsCell1.setVisibility(8);
                this.headerCell[0].setVisibility(8);
                this.headerCell[1].setVisibility(8);
                this.bottomCell[0].setVisibility(8);
                for (a = 0; a < 3; a++) {
                    ((View) this.inputFields[a].getParent()).setVisibility(8);
                }
                for (a = 0; a < this.dividers.size(); a++) {
                    ((View) this.dividers.get(a)).setVisibility(8);
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
                this.bottomCell[1].setText("");
                this.headerCell[0].setVisibility(8);
                this.headerCell[1].setVisibility(8);
                this.bottomCell[0].setVisibility(8);
                for (a = 0; a < 3; a++) {
                    ((View) this.inputFields[a].getParent()).setVisibility(8);
                }
                for (a = 0; a < this.dividers.size(); a++) {
                    ((View) this.dividers.get(a)).setVisibility(8);
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
            for (a = 0; a < 3; a++) {
                ((View) this.inputFields[a].getParent()).setVisibility(0);
            }
            for (a = 0; a < this.dividers.size(); a++) {
                ((View) this.dividers.get(a)).setVisibility(0);
            }
        }
    }

    private void loadPasswordInfo() {
        if (!this.loadingPasswordInfo) {
            this.loadingPasswordInfo = true;
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_account_getPassword(), new RequestDelegate() {
                public void run(final TLObject response, final TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {

                        /* renamed from: org.telegram.ui.PaymentFormActivity$26$1$1 */
                        class C31591 implements Runnable {
                            C31591() {
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
                            if (error == null) {
                                PaymentFormActivity.this.currentPassword = (TLRPC$account_Password) response;
                                if (PaymentFormActivity.this.paymentForm != null && (PaymentFormActivity.this.currentPassword instanceof TLRPC$TL_account_password)) {
                                    PaymentFormActivity.this.paymentForm.password_missing = false;
                                    PaymentFormActivity.this.paymentForm.can_save_credentials = true;
                                    PaymentFormActivity.this.updateSavePaymentField();
                                }
                                byte[] salt = new byte[(PaymentFormActivity.this.currentPassword.new_salt.length + 8)];
                                Utilities.random.nextBytes(salt);
                                System.arraycopy(PaymentFormActivity.this.currentPassword.new_salt, 0, salt, 0, PaymentFormActivity.this.currentPassword.new_salt.length);
                                PaymentFormActivity.this.currentPassword.new_salt = salt;
                                if (PaymentFormActivity.this.passwordFragment != null) {
                                    PaymentFormActivity.this.passwordFragment.setCurrentPassword(PaymentFormActivity.this.currentPassword);
                                }
                            }
                            if ((response instanceof TLRPC$TL_account_noPassword) && PaymentFormActivity.this.shortPollRunnable == null) {
                                PaymentFormActivity.this.shortPollRunnable = new C31591();
                                AndroidUtilities.runOnUIThread(PaymentFormActivity.this.shortPollRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                            }
                        }
                    });
                }
            }, 10);
        }
    }

    private void showAlertWithText(String title, String text) {
        Builder builder = new Builder(getParentActivity());
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
        builder.setTitle(title);
        builder.setMessage(text);
        showDialog(builder.create());
    }

    private void showPayAlert(String totalPrice) {
        Builder builder = new Builder(getParentActivity());
        builder.setTitle(LocaleController.getString("PaymentTransactionReview", R.string.PaymentTransactionReview));
        builder.setMessage(LocaleController.formatString("PaymentTransactionMessage", R.string.PaymentTransactionMessage, new Object[]{totalPrice, this.currentBotName, this.currentItemName}));
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                PaymentFormActivity.this.setDonePressed(true);
                PaymentFormActivity.this.sendData();
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        showDialog(builder.create());
    }

    private void initAndroidPay(Context context) {
        if (VERSION.SDK_INT >= 19) {
            this.googleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(new ConnectionCallbacks() {
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

    private String getTotalPriceString(ArrayList<TLRPC$TL_labeledPrice> prices) {
        long amount = 0;
        for (int a = 0; a < prices.size(); a++) {
            amount += ((TLRPC$TL_labeledPrice) prices.get(a)).amount;
        }
        return LocaleController.getInstance().formatCurrencyString(amount, this.paymentForm.invoice.currency);
    }

    private String getTotalPriceDecimalString(ArrayList<TLRPC$TL_labeledPrice> prices) {
        long amount = 0;
        for (int a = 0; a < prices.size(); a++) {
            amount += ((TLRPC$TL_labeledPrice) prices.get(a)).amount;
        }
        return LocaleController.getInstance().formatCurrencyDecimalString(amount, this.paymentForm.invoice.currency, false);
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
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
        try {
            if ((this.currentStep == 2 || this.currentStep == 6) && VERSION.SDK_INT >= 23 && (UserConfig.passcodeHash.length() == 0 || UserConfig.allowScreenCapture)) {
                getParentActivity().getWindow().clearFlags(8192);
            }
        } catch (Throwable e2) {
            FileLog.e(e2);
        }
        super.onFragmentDestroy();
        this.canceled = true;
    }

    protected void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        if (isOpen && !backward) {
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

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.didSetTwoStepPassword) {
            this.paymentForm.password_missing = false;
            this.paymentForm.can_save_credentials = true;
            updateSavePaymentField();
        } else if (id == NotificationCenter.didRemovedTwoStepPassword) {
            this.paymentForm.password_missing = true;
            this.paymentForm.can_save_credentials = false;
            updateSavePaymentField();
        } else if (id == NotificationCenter.paymentFinished) {
            removeSelfFromStack();
        }
    }

    private void showAndroidPay() {
        if (getParentActivity() != null && this.androidPayContainer != null) {
            WalletFragmentStyle walletFragmentStyle;
            PaymentMethodTokenizationParameters parameters;
            WalletFragmentOptions.Builder optionsBuilder = WalletFragmentOptions.newBuilder();
            optionsBuilder.setEnvironment(this.paymentForm.invoice.test ? 3 : 1);
            optionsBuilder.setMode(1);
            if (this.androidPayPublicKey != null) {
                this.androidPayContainer.setBackgroundColor(this.androidPayBackgroundColor);
                walletFragmentStyle = new WalletFragmentStyle().setBuyButtonText(5).setBuyButtonAppearance(this.androidPayBlackTheme ? 6 : 4).setBuyButtonWidth(-1);
            } else {
                walletFragmentStyle = new WalletFragmentStyle().setBuyButtonText(6).setBuyButtonAppearance(6).setBuyButtonWidth(-2);
            }
            optionsBuilder.setFragmentStyle(walletFragmentStyle);
            WalletFragment walletFragment = WalletFragment.newInstance(optionsBuilder.build());
            FragmentTransaction fragmentTransaction = getParentActivity().getFragmentManager().beginTransaction();
            fragmentTransaction.replace(4000, walletFragment);
            fragmentTransaction.commit();
            ArrayList<TLRPC$TL_labeledPrice> arrayList = new ArrayList();
            arrayList.addAll(this.paymentForm.invoice.prices);
            if (this.shippingOption != null) {
                arrayList.addAll(this.shippingOption.prices);
            }
            this.totalPriceDecimal = getTotalPriceDecimalString(arrayList);
            if (this.androidPayPublicKey != null) {
                parameters = PaymentMethodTokenizationParameters.newBuilder().setPaymentMethodTokenizationType(2).addParameter("publicKey", this.androidPayPublicKey).build();
            } else {
                parameters = PaymentMethodTokenizationParameters.newBuilder().setPaymentMethodTokenizationType(1).addParameter("gateway", "stripe").addParameter("stripe:publishableKey", this.stripeApiKey).addParameter("stripe:version", StripeApiHandler.VERSION).build();
            }
            walletFragment.initialize(WalletFragmentInitParams.newBuilder().setMaskedWalletRequest(MaskedWalletRequest.newBuilder().setPaymentMethodTokenizationParameters(parameters).setEstimatedTotalPrice(this.totalPriceDecimal).setCurrencyCode(this.paymentForm.invoice.currency).build()).setMaskedWalletRequestCode(1000).build());
            this.androidPayContainer.setVisibility(0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.androidPayContainer, "alpha", new float[]{0.0f, 1.0f})});
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.setDuration(180);
            animatorSet.start();
        }
    }

    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000) {
            if (resultCode == -1) {
                showEditDoneProgress(true, true);
                setDonePressed(true);
                MaskedWallet maskedWallet = (MaskedWallet) data.getParcelableExtra(WalletConstants.EXTRA_MASKED_WALLET);
                Cart.Builder cardBuilder = Cart.newBuilder().setCurrencyCode(this.paymentForm.invoice.currency).setTotalPrice(this.totalPriceDecimal);
                ArrayList<TLRPC$TL_labeledPrice> arrayList = new ArrayList();
                arrayList.addAll(this.paymentForm.invoice.prices);
                if (this.shippingOption != null) {
                    arrayList.addAll(this.shippingOption.prices);
                }
                for (int a = 0; a < arrayList.size(); a++) {
                    TLRPC$TL_labeledPrice price = (TLRPC$TL_labeledPrice) arrayList.get(a);
                    String amount = LocaleController.getInstance().formatCurrencyDecimalString(price.amount, this.paymentForm.invoice.currency, false);
                    cardBuilder.addLineItem(LineItem.newBuilder().setCurrencyCode(this.paymentForm.invoice.currency).setQuantity(BuildConfig.VERSION_NAME).setDescription(price.label).setTotalPrice(amount).setUnitPrice(amount).build());
                }
                Wallet.Payments.loadFullWallet(this.googleApiClient, FullWalletRequest.newBuilder().setCart(cardBuilder.build()).setGoogleTransactionId(maskedWallet.getGoogleTransactionId()).build(), 1001);
                return;
            }
            showEditDoneProgress(true, false);
            setDonePressed(false);
        } else if (requestCode != 1001) {
        } else {
            if (resultCode == -1) {
                FullWallet fullWallet = (FullWallet) data.getParcelableExtra(WalletConstants.EXTRA_FULL_WALLET);
                String tokenJSON = fullWallet.getPaymentMethodToken().getToken();
                try {
                    if (this.androidPayPublicKey != null) {
                        this.androidPayCredentials = new TLRPC$TL_inputPaymentCredentialsAndroidPay();
                        this.androidPayCredentials.payment_token = new TLRPC$TL_dataJSON();
                        this.androidPayCredentials.payment_token.data = tokenJSON;
                        String[] descriptions = fullWallet.getPaymentDescriptions();
                        if (descriptions.length > 0) {
                            this.cardName = descriptions[0];
                        } else {
                            this.cardName = "Android Pay";
                        }
                    } else {
                        this.paymentJson = String.format(Locale.US, "{\"type\":\"%1$s\", \"id\":\"%2$s\"}", new Object[]{token.getType(), TokenParser.parseToken(tokenJSON).getId()});
                        Card card = token.getCard();
                        this.cardName = card.getType() + " *" + card.getLast4();
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

    private void goToNextStep() {
        int nextStep;
        if (this.currentStep == 0) {
            if (this.paymentForm.invoice.flexible) {
                nextStep = 1;
            } else if (this.paymentForm.saved_credentials != null) {
                if (UserConfig.tmpPassword != null && UserConfig.tmpPassword.valid_until < ConnectionsManager.getInstance().getCurrentTime() + 60) {
                    UserConfig.tmpPassword = null;
                    UserConfig.saveConfig(false);
                }
                if (UserConfig.tmpPassword != null) {
                    nextStep = 4;
                } else {
                    nextStep = 3;
                }
            } else {
                nextStep = 2;
            }
            presentFragment(new PaymentFormActivity(this.paymentForm, this.messageObject, nextStep, this.requestedInfo, null, null, this.cardName, this.validateRequest, this.saveCardInfo, this.androidPayCredentials), this.isWebView);
        } else if (this.currentStep == 1) {
            if (this.paymentForm.saved_credentials != null) {
                if (UserConfig.tmpPassword != null && UserConfig.tmpPassword.valid_until < ConnectionsManager.getInstance().getCurrentTime() + 60) {
                    UserConfig.tmpPassword = null;
                    UserConfig.saveConfig(false);
                }
                if (UserConfig.tmpPassword != null) {
                    nextStep = 4;
                } else {
                    nextStep = 3;
                }
            } else {
                nextStep = 2;
            }
            presentFragment(new PaymentFormActivity(this.paymentForm, this.messageObject, nextStep, this.requestedInfo, this.shippingOption, null, this.cardName, this.validateRequest, this.saveCardInfo, this.androidPayCredentials), this.isWebView);
        } else if (this.currentStep == 2) {
            if (this.paymentForm.password_missing && this.saveCardInfo) {
                this.passwordFragment = new PaymentFormActivity(this.paymentForm, this.messageObject, 6, this.requestedInfo, this.shippingOption, this.paymentJson, this.cardName, this.validateRequest, this.saveCardInfo, this.androidPayCredentials);
                this.passwordFragment.setCurrentPassword(this.currentPassword);
                this.passwordFragment.setDelegate(new PaymentFormActivityDelegate() {
                    public boolean didSelectNewCard(String tokenJson, String card, boolean saveCard, TLRPC$TL_inputPaymentCredentialsAndroidPay androidPay) {
                        if (PaymentFormActivity.this.delegate != null) {
                            PaymentFormActivity.this.delegate.didSelectNewCard(tokenJson, card, saveCard, androidPay);
                        }
                        if (PaymentFormActivity.this.isWebView) {
                            PaymentFormActivity.this.removeSelfFromStack();
                        }
                        return PaymentFormActivity.this.delegate != null;
                    }

                    public void onFragmentDestroyed() {
                        PaymentFormActivity.this.passwordFragment = null;
                    }

                    public void currentPasswordUpdated(TLRPC$account_Password password) {
                        PaymentFormActivity.this.currentPassword = password;
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
            if (this.passwordOk) {
                nextStep = 4;
            } else {
                nextStep = 2;
            }
            presentFragment(new PaymentFormActivity(this.paymentForm, this.messageObject, nextStep, this.requestedInfo, this.shippingOption, this.paymentJson, this.cardName, this.validateRequest, this.saveCardInfo, this.androidPayCredentials), !this.passwordOk);
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

    private void updateSavePaymentField() {
        if (this.bottomCell[0] != null && this.sectionCell[2] != null) {
            if ((this.paymentForm.password_missing || this.paymentForm.can_save_credentials) && (this.webView == null || !(this.webView == null || this.webviewLoading))) {
                SpannableStringBuilder text = new SpannableStringBuilder(LocaleController.getString("PaymentCardSavePaymentInformationInfoLine1", R.string.PaymentCardSavePaymentInformationInfoLine1));
                if (this.paymentForm.password_missing) {
                    loadPasswordInfo();
                    text.append(LogCollector.LINE_SEPARATOR);
                    int len = text.length();
                    String str2 = LocaleController.getString("PaymentCardSavePaymentInformationInfoLine2", R.string.PaymentCardSavePaymentInformationInfoLine2);
                    int index1 = str2.indexOf(42);
                    int index2 = str2.lastIndexOf(42);
                    text.append(str2);
                    if (!(index1 == -1 || index2 == -1)) {
                        index1 += len;
                        index2 += len;
                        this.bottomCell[0].getTextView().setMovementMethod(new LinkMovementMethodMy());
                        text.replace(index2, index2 + 1, "");
                        text.replace(index1, index1 + 1, "");
                        text.setSpan(new LinkSpan(), index1, index2 - 1, 33);
                    }
                }
                this.checkCell1.setEnabled(true);
                this.bottomCell[0].setText(text);
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

    @SuppressLint({"HardwareIds"})
    public void fillNumber(String number) {
        try {
            TelephonyManager tm = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
            boolean allowCall = true;
            boolean allowSms = true;
            if (number != null || (tm.getSimState() != 1 && tm.getPhoneType() != 0)) {
                if (VERSION.SDK_INT >= 23) {
                    if (getParentActivity().checkSelfPermission("android.permission.READ_PHONE_STATE") == 0) {
                        allowCall = true;
                    } else {
                        allowCall = false;
                    }
                    if (getParentActivity().checkSelfPermission("android.permission.RECEIVE_SMS") == 0) {
                        allowSms = true;
                    } else {
                        allowSms = false;
                    }
                }
                if (number != null || allowCall || allowSms) {
                    if (number == null) {
                        number = PhoneFormat.stripExceptNumbers(tm.getLine1Number());
                    }
                    String textToSet = null;
                    boolean ok = false;
                    if (!TextUtils.isEmpty(number)) {
                        if (number.length() > 4) {
                            for (int a = 4; a >= 1; a--) {
                                String sub = number.substring(0, a);
                                if (((String) this.codesMap.get(sub)) != null) {
                                    ok = true;
                                    textToSet = number.substring(a, number.length());
                                    this.inputFields[8].setText(sub);
                                    break;
                                }
                            }
                            if (!ok) {
                                textToSet = number.substring(1, number.length());
                                this.inputFields[8].setText(number.substring(0, 1));
                            }
                        }
                        if (textToSet != null) {
                            this.inputFields[9].setText(textToSet);
                            this.inputFields[9].setSelection(this.inputFields[9].length());
                        }
                    }
                }
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    private void sendSavePassword(final boolean clear) {
        String email;
        TLRPC$TL_account_updatePasswordSettings req = new TLRPC$TL_account_updatePasswordSettings();
        if (clear) {
            this.doneItem.setVisibility(0);
            email = null;
            req.new_settings = new TLRPC$TL_account_passwordInputSettings();
            req.new_settings.flags = 2;
            req.new_settings.email = "";
            req.current_password_hash = new byte[0];
        } else {
            String firstPassword = this.inputFields[0].getText().toString();
            if (TextUtils.isEmpty(firstPassword)) {
                shakeField(0);
                return;
            } else if (firstPassword.equals(this.inputFields[1].getText().toString())) {
                email = this.inputFields[2].getText().toString();
                if (email.length() < 3) {
                    shakeField(2);
                    return;
                }
                int dot = email.lastIndexOf(46);
                int dog = email.lastIndexOf(64);
                if (dot < 0 || dog < 0 || dot < dog) {
                    shakeField(2);
                    return;
                }
                req.current_password_hash = new byte[0];
                req.new_settings = new TLRPC$TL_account_passwordInputSettings();
                byte[] newPasswordBytes = null;
                try {
                    newPasswordBytes = firstPassword.getBytes("UTF-8");
                } catch (Exception e) {
                    FileLog.e(e);
                }
                byte[] new_salt = this.currentPassword.new_salt;
                byte[] hash = new byte[((new_salt.length * 2) + newPasswordBytes.length)];
                System.arraycopy(new_salt, 0, hash, 0, new_salt.length);
                System.arraycopy(newPasswordBytes, 0, hash, new_salt.length, newPasswordBytes.length);
                System.arraycopy(new_salt, 0, hash, hash.length - new_salt.length, new_salt.length);
                TLRPC$TL_account_passwordInputSettings tLRPC$TL_account_passwordInputSettings = req.new_settings;
                tLRPC$TL_account_passwordInputSettings.flags |= 1;
                req.new_settings.hint = "";
                req.new_settings.new_password_hash = Utilities.computeSHA256(hash, 0, hash.length);
                req.new_settings.new_salt = new_salt;
                if (email.length() > 0) {
                    tLRPC$TL_account_passwordInputSettings = req.new_settings;
                    tLRPC$TL_account_passwordInputSettings.flags |= 2;
                    req.new_settings.email = email.trim();
                }
            } else {
                try {
                    Toast.makeText(getParentActivity(), LocaleController.getString("PasswordDoNotMatch", R.string.PasswordDoNotMatch), 0).show();
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
                shakeField(1);
                return;
            }
        }
        showEditDoneProgress(true, true);
        ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
            public void run(final TLObject response, final TLRPC$TL_error error) {
                AndroidUtilities.runOnUIThread(new Runnable() {

                    /* renamed from: org.telegram.ui.PaymentFormActivity$32$1$1 */
                    class C31631 implements DialogInterface.OnClickListener {
                        C31631() {
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            PaymentFormActivity.this.waitingForEmail = true;
                            PaymentFormActivity.this.currentPassword.email_unconfirmed_pattern = email;
                            PaymentFormActivity.this.updatePasswordFields();
                        }
                    }

                    public void run() {
                        PaymentFormActivity.this.showEditDoneProgress(true, false);
                        if (clear) {
                            PaymentFormActivity.this.currentPassword = new TLRPC$TL_account_noPassword();
                            PaymentFormActivity.this.delegate.currentPasswordUpdated(PaymentFormActivity.this.currentPassword);
                            PaymentFormActivity.this.finishFragment();
                        } else if (error == null && (response instanceof TLRPC$TL_boolTrue)) {
                            if (PaymentFormActivity.this.getParentActivity() != null) {
                                PaymentFormActivity.this.goToNextStep();
                            }
                        } else if (error == null) {
                        } else {
                            if (error.text.equals("EMAIL_UNCONFIRMED")) {
                                Builder builder = new Builder(PaymentFormActivity.this.getParentActivity());
                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C31631());
                                builder.setMessage(LocaleController.getString("YourEmailAlmostThereText", R.string.YourEmailAlmostThereText));
                                builder.setTitle(LocaleController.getString("YourEmailAlmostThere", R.string.YourEmailAlmostThere));
                                Dialog dialog = PaymentFormActivity.this.showDialog(builder.create());
                                if (dialog != null) {
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setCancelable(false);
                                }
                            } else if (error.text.equals("EMAIL_INVALID")) {
                                PaymentFormActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("PasswordEmailInvalid", R.string.PasswordEmailInvalid));
                            } else if (error.text.startsWith("FLOOD_WAIT")) {
                                String timeString;
                                int time = Utilities.parseInt(error.text).intValue();
                                if (time < 60) {
                                    timeString = LocaleController.formatPluralString("Seconds", time);
                                } else {
                                    timeString = LocaleController.formatPluralString("Minutes", time / 60);
                                }
                                PaymentFormActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{timeString}));
                            } else {
                                PaymentFormActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), error.text);
                            }
                        }
                    }
                });
            }
        }, 10);
    }

    private boolean sendCardData() {
        Integer month;
        Integer year;
        String[] args = this.inputFields[1].getText().toString().split("/");
        if (args.length == 2) {
            month = Utilities.parseInt(args[0]);
            year = Utilities.parseInt(args[1]);
        } else {
            month = null;
            year = null;
        }
        Card card = new Card(this.inputFields[0].getText().toString(), month, year, this.inputFields[3].getText().toString(), this.inputFields[2].getText().toString(), null, null, null, null, this.inputFields[5].getText().toString(), this.inputFields[4].getText().toString(), null);
        this.cardName = card.getType() + " *" + card.getLast4();
        if (!card.validateNumber()) {
            shakeField(0);
            return false;
        } else if (!card.validateExpMonth() || !card.validateExpYear() || !card.validateExpiryDate()) {
            shakeField(1);
            return false;
        } else if (this.need_card_name && this.inputFields[2].length() == 0) {
            shakeField(2);
            return false;
        } else if (!card.validateCVC()) {
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
                new Stripe(this.stripeApiKey).createToken(card, new TokenCallback() {

                    /* renamed from: org.telegram.ui.PaymentFormActivity$33$1 */
                    class C31651 implements Runnable {
                        C31651() {
                        }

                        public void run() {
                            PaymentFormActivity.this.goToNextStep();
                            PaymentFormActivity.this.showEditDoneProgress(true, false);
                            PaymentFormActivity.this.setDonePressed(false);
                        }
                    }

                    public void onSuccess(Token token) {
                        if (!PaymentFormActivity.this.canceled) {
                            PaymentFormActivity.this.paymentJson = String.format(Locale.US, "{\"type\":\"%1$s\", \"id\":\"%2$s\"}", new Object[]{token.getType(), token.getId()});
                            AndroidUtilities.runOnUIThread(new C31651());
                        }
                    }

                    public void onError(Exception error) {
                        if (!PaymentFormActivity.this.canceled) {
                            PaymentFormActivity.this.showEditDoneProgress(true, false);
                            PaymentFormActivity.this.setDonePressed(false);
                            if ((error instanceof APIConnectionException) || (error instanceof APIException)) {
                                AlertsCreator.showSimpleToast(PaymentFormActivity.this, LocaleController.getString("PaymentConnectionFailed", R.string.PaymentConnectionFailed));
                            } else {
                                AlertsCreator.showSimpleToast(PaymentFormActivity.this, error.getMessage());
                            }
                        }
                    }
                });
            } catch (Exception e) {
                FileLog.e(e);
            }
            return true;
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
                this.validateRequest.info.shipping_address.country_iso2 = this.countryName != null ? this.countryName : "";
                this.validateRequest.info.shipping_address.post_code = this.inputFields[5].getText().toString();
                tLRPC$TL_paymentRequestedInfo = this.validateRequest.info;
                tLRPC$TL_paymentRequestedInfo.flags |= 8;
            }
            final TLObject req = this.validateRequest;
            ConnectionsManager.getInstance().sendRequest(this.validateRequest, new RequestDelegate() {
                public void run(final TLObject response, final TLRPC$TL_error error) {
                    if (response instanceof TLRPC$TL_payments_validatedRequestedInfo) {
                        AndroidUtilities.runOnUIThread(new Runnable() {

                            /* renamed from: org.telegram.ui.PaymentFormActivity$34$1$1 */
                            class C31661 implements RequestDelegate {
                                C31661() {
                                }

                                public void run(TLObject response, TLRPC$TL_error error) {
                                }
                            }

                            public void run() {
                                PaymentFormActivity.this.requestedInfo = (TLRPC$TL_payments_validatedRequestedInfo) response;
                                if (!(PaymentFormActivity.this.paymentForm.saved_info == null || PaymentFormActivity.this.saveShippingInfo)) {
                                    TLRPC$TL_payments_clearSavedInfo req = new TLRPC$TL_payments_clearSavedInfo();
                                    req.info = true;
                                    ConnectionsManager.getInstance().sendRequest(req, new C31661());
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
                                if (error != null) {
                                    String str = error.text;
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
                                            AlertsCreator.processError(error, PaymentFormActivity.this, req, new Object[0]);
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

    private TLRPC$TL_paymentRequestedInfo getRequestInfo() {
        TLRPC$TL_paymentRequestedInfo info = new TLRPC$TL_paymentRequestedInfo();
        if (this.paymentForm.invoice.name_requested) {
            info.name = this.inputFields[6].getText().toString();
            info.flags |= 1;
        }
        if (this.paymentForm.invoice.phone_requested) {
            info.phone = "+" + this.inputFields[8].getText().toString() + this.inputFields[9].getText().toString();
            info.flags |= 2;
        }
        if (this.paymentForm.invoice.email_requested) {
            info.email = this.inputFields[7].getText().toString().trim();
            info.flags |= 4;
        }
        if (this.paymentForm.invoice.shipping_address_requested) {
            info.shipping_address = new TLRPC$TL_postAddress();
            info.shipping_address.street_line1 = this.inputFields[0].getText().toString();
            info.shipping_address.street_line2 = this.inputFields[1].getText().toString();
            info.shipping_address.city = this.inputFields[2].getText().toString();
            info.shipping_address.state = this.inputFields[3].getText().toString();
            info.shipping_address.country_iso2 = this.countryName != null ? this.countryName : "";
            info.shipping_address.post_code = this.inputFields[5].getText().toString();
            info.flags |= 8;
        }
        return info;
    }

    private void sendData() {
        if (!this.canceled) {
            showEditDoneProgress(false, true);
            final TLRPC$TL_payments_sendPaymentForm req = new TLRPC$TL_payments_sendPaymentForm();
            req.msg_id = this.messageObject.getId();
            if (UserConfig.tmpPassword != null && this.paymentForm.saved_credentials != null) {
                req.credentials = new TLRPC$TL_inputPaymentCredentialsSaved();
                req.credentials.id = this.paymentForm.saved_credentials.id;
                req.credentials.tmp_password = UserConfig.tmpPassword.tmp_password;
            } else if (this.androidPayCredentials != null) {
                req.credentials = this.androidPayCredentials;
            } else {
                req.credentials = new TLRPC$TL_inputPaymentCredentials();
                req.credentials.save = this.saveCardInfo;
                req.credentials.data = new TLRPC$TL_dataJSON();
                req.credentials.data.data = this.paymentJson;
            }
            if (!(this.requestedInfo == null || this.requestedInfo.id == null)) {
                req.requested_info_id = this.requestedInfo.id;
                req.flags |= 1;
            }
            if (this.shippingOption != null) {
                req.shipping_option_id = this.shippingOption.id;
                req.flags |= 2;
            }
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {

                /* renamed from: org.telegram.ui.PaymentFormActivity$35$1 */
                class C31691 implements Runnable {
                    C31691() {
                    }

                    public void run() {
                        PaymentFormActivity.this.goToNextStep();
                    }
                }

                public void run(final TLObject response, final TLRPC$TL_error error) {
                    if (response == null) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                AlertsCreator.processError(error, PaymentFormActivity.this, req, new Object[0]);
                                PaymentFormActivity.this.setDonePressed(false);
                                PaymentFormActivity.this.showEditDoneProgress(false, false);
                            }
                        });
                    } else if (response instanceof TLRPC$TL_payments_paymentResult) {
                        MessagesController.getInstance().processUpdates(((TLRPC$TL_payments_paymentResult) response).updates, false);
                        AndroidUtilities.runOnUIThread(new C31691());
                    } else if (response instanceof TLRPC$TL_payments_paymentVerficationNeeded) {
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
                                PaymentFormActivity.this.webView.loadUrl(((TLRPC$TL_payments_paymentVerficationNeeded) response).url);
                            }
                        });
                    }
                }
            }, 2);
        }
    }

    private void shakeField(int field) {
        Vibrator v = (Vibrator) getParentActivity().getSystemService("vibrator");
        if (v != null) {
            v.vibrate(200);
        }
        AndroidUtilities.shakeView(this.inputFields[field], 2.0f, 0);
    }

    private void setDonePressed(boolean value) {
        boolean z;
        boolean z2 = true;
        this.donePressed = value;
        this.swipeBackEnabled = !value;
        View backButton = this.actionBar.getBackButton();
        if (this.donePressed) {
            z = false;
        } else {
            z = true;
        }
        backButton.setEnabled(z);
        if (this.detailSettingsCell[0] != null) {
            TextDetailSettingsCell textDetailSettingsCell = this.detailSettingsCell[0];
            if (this.donePressed) {
                z2 = false;
            }
            textDetailSettingsCell.setEnabled(z2);
        }
    }

    private void checkPassword() {
        if (UserConfig.tmpPassword != null && UserConfig.tmpPassword.valid_until < ConnectionsManager.getInstance().getCurrentTime() + 60) {
            UserConfig.tmpPassword = null;
            UserConfig.saveConfig(false);
        }
        if (UserConfig.tmpPassword != null) {
            sendData();
        } else if (this.inputFields[1].length() == 0) {
            Vibrator v = (Vibrator) ApplicationLoader.applicationContext.getSystemService("vibrator");
            if (v != null) {
                v.vibrate(200);
            }
            AndroidUtilities.shakeView(this.inputFields[1], 2.0f, 0);
        } else {
            final String password = this.inputFields[1].getText().toString();
            showEditDoneProgress(true, true);
            setDonePressed(true);
            final TLRPC$TL_account_getPassword req = new TLRPC$TL_account_getPassword();
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, final TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (error != null) {
                                AlertsCreator.processError(error, PaymentFormActivity.this, req, new Object[0]);
                                PaymentFormActivity.this.showEditDoneProgress(true, false);
                                PaymentFormActivity.this.setDonePressed(false);
                            } else if (response instanceof TLRPC$TL_account_noPassword) {
                                PaymentFormActivity.this.passwordOk = false;
                                PaymentFormActivity.this.goToNextStep();
                            } else {
                                TLRPC$TL_account_password currentPassword = response;
                                byte[] passwordBytes = null;
                                try {
                                    passwordBytes = password.getBytes("UTF-8");
                                } catch (Exception e) {
                                    FileLog.e(e);
                                }
                                byte[] hash = new byte[((currentPassword.current_salt.length * 2) + passwordBytes.length)];
                                System.arraycopy(currentPassword.current_salt, 0, hash, 0, currentPassword.current_salt.length);
                                System.arraycopy(passwordBytes, 0, hash, currentPassword.current_salt.length, passwordBytes.length);
                                System.arraycopy(currentPassword.current_salt, 0, hash, hash.length - currentPassword.current_salt.length, currentPassword.current_salt.length);
                                final TLRPC$TL_account_getTmpPassword req = new TLRPC$TL_account_getTmpPassword();
                                req.password_hash = Utilities.computeSHA256(hash, 0, hash.length);
                                req.period = 1800;
                                ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                                    public void run(final TLObject response, final TLRPC$TL_error error) {
                                        AndroidUtilities.runOnUIThread(new Runnable() {
                                            public void run() {
                                                PaymentFormActivity.this.showEditDoneProgress(true, false);
                                                PaymentFormActivity.this.setDonePressed(false);
                                                if (response != null) {
                                                    PaymentFormActivity.this.passwordOk = true;
                                                    UserConfig.tmpPassword = (TLRPC$TL_account_tmpPassword) response;
                                                    UserConfig.saveConfig(false);
                                                    PaymentFormActivity.this.goToNextStep();
                                                } else if (error.text.equals("PASSWORD_HASH_INVALID")) {
                                                    Vibrator v = (Vibrator) ApplicationLoader.applicationContext.getSystemService("vibrator");
                                                    if (v != null) {
                                                        v.vibrate(200);
                                                    }
                                                    AndroidUtilities.shakeView(PaymentFormActivity.this.inputFields[1], 2.0f, 0);
                                                    PaymentFormActivity.this.inputFields[1].setText("");
                                                } else {
                                                    AlertsCreator.processError(error, PaymentFormActivity.this, req, new Object[0]);
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

    private void showEditDoneProgress(boolean animateDoneItem, final boolean show) {
        if (this.doneItemAnimation != null) {
            this.doneItemAnimation.cancel();
        }
        AnimatorSet animatorSet;
        Animator[] animatorArr;
        if (animateDoneItem && this.doneItem != null) {
            this.doneItemAnimation = new AnimatorSet();
            if (show) {
                this.progressView.setVisibility(0);
                this.doneItem.setEnabled(false);
                animatorSet = this.doneItemAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "alpha", new float[]{0.0f});
                animatorArr[3] = ObjectAnimator.ofFloat(this.progressView, "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.progressView, "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.progressView, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            } else if (this.webView != null) {
                animatorSet = this.doneItemAnimation;
                animatorArr = new Animator[3];
                animatorArr[0] = ObjectAnimator.ofFloat(this.progressView, "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.progressView, "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.progressView, "alpha", new float[]{0.0f});
                animatorSet.playTogether(animatorArr);
            } else {
                this.doneItem.getImageView().setVisibility(0);
                this.doneItem.setEnabled(true);
                animatorSet = this.doneItemAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.progressView, "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.progressView, "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.progressView, "alpha", new float[]{0.0f});
                animatorArr[3] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            }
            this.doneItemAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    if (PaymentFormActivity.this.doneItemAnimation != null && PaymentFormActivity.this.doneItemAnimation.equals(animation)) {
                        if (show) {
                            PaymentFormActivity.this.doneItem.getImageView().setVisibility(4);
                        } else {
                            PaymentFormActivity.this.progressView.setVisibility(4);
                        }
                    }
                }

                public void onAnimationCancel(Animator animation) {
                    if (PaymentFormActivity.this.doneItemAnimation != null && PaymentFormActivity.this.doneItemAnimation.equals(animation)) {
                        PaymentFormActivity.this.doneItemAnimation = null;
                    }
                }
            });
            this.doneItemAnimation.setDuration(150);
            this.doneItemAnimation.start();
        } else if (this.payTextView != null) {
            this.doneItemAnimation = new AnimatorSet();
            if (show) {
                this.progressViewButton.setVisibility(0);
                this.bottomLayout.setEnabled(false);
                animatorSet = this.doneItemAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.payTextView, "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.payTextView, "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.payTextView, "alpha", new float[]{0.0f});
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
                animatorArr[2] = ObjectAnimator.ofFloat(this.progressViewButton, "alpha", new float[]{0.0f});
                animatorArr[3] = ObjectAnimator.ofFloat(this.payTextView, "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.payTextView, "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.payTextView, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            }
            this.doneItemAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    if (PaymentFormActivity.this.doneItemAnimation != null && PaymentFormActivity.this.doneItemAnimation.equals(animation)) {
                        if (show) {
                            PaymentFormActivity.this.payTextView.setVisibility(4);
                        } else {
                            PaymentFormActivity.this.progressViewButton.setVisibility(4);
                        }
                    }
                }

                public void onAnimationCancel(Animator animation) {
                    if (PaymentFormActivity.this.doneItemAnimation != null && PaymentFormActivity.this.doneItemAnimation.equals(animation)) {
                        PaymentFormActivity.this.doneItemAnimation = null;
                    }
                }
            });
            this.doneItemAnimation.setDuration(150);
            this.doneItemAnimation.start();
        }
    }

    public boolean onBackPressed() {
        return !this.donePressed;
    }

    public ThemeDescription[] getThemeDescriptions() {
        int a;
        ArrayList<ThemeDescription> arrayList = new ArrayList();
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
            for (a = 0; a < this.inputFields.length; a++) {
                arrayList.add(new ThemeDescription((View) this.inputFields[a].getParent(), ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
                arrayList.add(new ThemeDescription(this.inputFields[a], ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
                arrayList.add(new ThemeDescription(this.inputFields[a], ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText));
            }
        } else {
            arrayList.add(new ThemeDescription(null, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
            arrayList.add(new ThemeDescription(null, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText));
        }
        if (this.radioCells != null) {
            for (a = 0; a < this.radioCells.length; a++) {
                arrayList.add(new ThemeDescription(this.radioCells[a], ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_windowBackgroundWhite));
                arrayList.add(new ThemeDescription(this.radioCells[a], ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_listSelector));
                arrayList.add(new ThemeDescription(this.radioCells[a], 0, new Class[]{RadioCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
                arrayList.add(new ThemeDescription(this.radioCells[a], ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground));
                arrayList.add(new ThemeDescription(this.radioCells[a], ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked));
            }
        } else {
            arrayList.add(new ThemeDescription(null, 0, new Class[]{RadioCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
            arrayList.add(new ThemeDescription(null, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground));
            arrayList.add(new ThemeDescription(null, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked));
        }
        for (a = 0; a < this.headerCell.length; a++) {
            arrayList.add(new ThemeDescription(this.headerCell[a], ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
            arrayList.add(new ThemeDescription(this.headerCell[a], 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader));
        }
        for (View themeDescription : this.sectionCell) {
            arrayList.add(new ThemeDescription(themeDescription, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow));
        }
        for (a = 0; a < this.bottomCell.length; a++) {
            arrayList.add(new ThemeDescription(this.bottomCell[a], ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow));
            arrayList.add(new ThemeDescription(this.bottomCell[a], 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4));
            arrayList.add(new ThemeDescription(this.bottomCell[a], ThemeDescription.FLAG_LINKCOLOR, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteLinkText));
        }
        for (a = 0; a < this.dividers.size(); a++) {
            arrayList.add(new ThemeDescription((View) this.dividers.get(a), ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_divider));
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
        for (a = 1; a < this.detailSettingsCell.length; a++) {
            arrayList.add(new ThemeDescription(this.detailSettingsCell[a], ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
            arrayList.add(new ThemeDescription(this.detailSettingsCell[a], 0, new Class[]{TextDetailSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
            arrayList.add(new ThemeDescription(this.detailSettingsCell[a], 0, new Class[]{TextDetailSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2));
        }
        arrayList.add(new ThemeDescription(this.paymentInfoCell, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.paymentInfoCell, 0, new Class[]{PaymentInfoCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.paymentInfoCell, 0, new Class[]{PaymentInfoCell.class}, new String[]{"detailTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.paymentInfoCell, 0, new Class[]{PaymentInfoCell.class}, new String[]{"detailExTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2));
        arrayList.add(new ThemeDescription(this.bottomLayout, ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.bottomLayout, ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_listSelector));
        return (ThemeDescription[]) arrayList.toArray(new ThemeDescription[arrayList.size()]);
    }
}
