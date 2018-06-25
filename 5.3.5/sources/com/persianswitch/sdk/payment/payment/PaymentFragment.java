package com.persianswitch.sdk.payment.payment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.api.Response;
import com.persianswitch.sdk.base.BaseMVPFragment;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.manager.FontManager;
import com.persianswitch.sdk.base.style.PersonalizedConfig;
import com.persianswitch.sdk.base.utils.Spanny;
import com.persianswitch.sdk.base.utils.strings.StringFormatter;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.DownloadLogoTask;
import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.base.widgets.edittext.ApLabelAutoComplete;
import com.persianswitch.sdk.base.widgets.edittext.ApLabelCardExpire;
import com.persianswitch.sdk.base.widgets.edittext.ApLabelEditText;
import com.persianswitch.sdk.base.widgets.listener.ScrollGlobalLayoutListener;
import com.persianswitch.sdk.payment.SDKConfig;
import com.persianswitch.sdk.payment.common.CommonDialog;
import com.persianswitch.sdk.payment.common.CommonDialog.CommonDialogInterface;
import com.persianswitch.sdk.payment.common.CommonDialog.CommonDialogParam;
import com.persianswitch.sdk.payment.managers.ServiceManager;
import com.persianswitch.sdk.payment.managers.suggestion.InputSuggestionManager;
import com.persianswitch.sdk.payment.managers.suggestion.SuggestionListener;
import com.persianswitch.sdk.payment.model.ClientConfig;
import com.persianswitch.sdk.payment.model.PaymentProfile;
import com.persianswitch.sdk.payment.model.UserCard;
import com.persianswitch.sdk.payment.payment.PaymentContract.ActionListener;
import com.persianswitch.sdk.payment.payment.PaymentContract.View;
import com.persianswitch.sdk.payment.utils.CardTextWatcher;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PaymentFragment extends BaseMVPFragment<ActionListener> implements View, CommonDialogInterface {
    public static final String MERCHANT_ERROR_DIALOG = "inquiry_error";
    public static final String PAYMENT_ERROR_DIALOG = "payment_error";
    private static final String ROOT_DISABLE_DIALOG = "root_disable";
    private static final String ROOT_VALIDATE_NUMBER = "validate_number";
    private static final String ROOT_WARNING_DIALOG = "root_warning";
    private static final String TIMEOUT_ERROR_DIALOG = "timeout_error";
    public static final String TRUST_CODE_ERROR_DIALOG = "trust_error";
    private ActionListener mActionListener;
    private Button mBtnCancel;
    private Button mBtnPay;
    private android.view.View mBtnTrust;
    private ApLabelAutoComplete mEdtCardNo;
    private ApLabelEditText mEdtCvv2;
    private ApLabelCardExpire mEdtExpirationDate;
    private ApLabelEditText mEdtPin2;
    private ImageView mImgMerchantLogo;
    private android.view.View mLytMerchantInfo;
    private UserCard mSelectedCard;
    private final SuggestionListener<UserCard> mSuggestionListener = new C08121();
    private TextView mTxtAmount;
    private TextView mTxtMerchantName;
    private TextView mTxtPaymentId;

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentFragment$1 */
    class C08121 implements SuggestionListener<UserCard> {
        C08121() {
        }

        public void onSelect(UserCard entity) {
            PaymentFragment.this.mSelectedCard = entity;
            PaymentFragment.this.mEdtPin2.setText("");
            PaymentFragment.this.mEdtCvv2.setText("");
            PaymentFragment.this.mEdtExpirationDate.getMonthEditText().setText("");
            PaymentFragment.this.mEdtExpirationDate.getYearEditText().setText("");
            if (entity.isExpirySaved()) {
                PaymentFragment.this.mEdtExpirationDate.getMonthEditText().setText("11");
                PaymentFragment.this.mEdtExpirationDate.getYearEditText().setText("11");
                PaymentFragment.this.mEdtExpirationDate.setFieldEdited(false);
            } else {
                PaymentFragment.this.mEdtExpirationDate.getMonthEditText().getText().clear();
                PaymentFragment.this.mEdtExpirationDate.getYearEditText().getText().clear();
                PaymentFragment.this.mEdtExpirationDate.setFieldEdited(true);
            }
            PaymentFragment.this.getPresenter().checkForCVV2();
            PaymentFragment.this.requestNextField();
        }

        public void onClear() {
            PaymentFragment.this.mSelectedCard = null;
            PaymentFragment.this.mEdtPin2.setText("");
            PaymentFragment.this.mEdtCvv2.setText("");
            PaymentFragment.this.mEdtExpirationDate.getYearEditText().setText("");
            PaymentFragment.this.mEdtExpirationDate.getMonthEditText().setText("");
            PaymentFragment.this.getPresenter().checkForCVV2();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentFragment$2 */
    class C08132 implements OnClickListener {
        C08132() {
        }

        public void onClick(android.view.View view) {
            PaymentFragment.this.onShaparakClicked();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentFragment$3 */
    class C08143 implements OnClickListener {
        C08143() {
        }

        public void onClick(android.view.View view) {
            PaymentFragment.this.onTrustSdkClicked();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentFragment$5 */
    class C08165 implements OnClickListener {
        C08165() {
        }

        public void onClick(android.view.View v) {
            PaymentFragment.this.getPresenter().doPayment();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentFragment$6 */
    class C08176 implements OnClickListener {
        C08176() {
        }

        public void onClick(android.view.View v) {
            PaymentFragment.this.getPresenter().cancelPayment();
        }
    }

    private void requestNextField() {
        if (StringUtils.isEmpty(this.mEdtCardNo.getText().toString())) {
            this.mEdtCardNo.getInnerInput().requestFocus();
        } else if (StringUtils.isEmpty(this.mEdtPin2.getText().toString())) {
            this.mEdtPin2.getInnerInput().requestFocus();
        } else if (StringUtils.isEmpty(this.mEdtCvv2.getText().toString())) {
            this.mEdtCvv2.getInnerInput().requestFocus();
        } else if (StringUtils.isEmpty(this.mEdtExpirationDate.getMonthEditText().getText().toString())) {
            this.mEdtExpirationDate.getMonthEditText().requestFocus();
        } else if (StringUtils.isEmpty(this.mEdtExpirationDate.getYearEditText().getText().toString())) {
            this.mEdtExpirationDate.getYearEditText().requestFocus();
        } else if (getView() != null) {
            this.mBtnPay.requestFocus();
            ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mBtnPay.getWindowToken(), 0);
        }
    }

    public int getLayoutResourceId() {
        return new SDKConfig().getPersonalizedConfig(BaseSetting.getHostId(getApplicationContext())).getPaymentLayout();
    }

    public void onFragmentCreated(android.view.View view, Bundle savedInstanceState) {
        this.mLytMerchantInfo = view.findViewById(C0770R.id.lyt_merchant_info);
        this.mImgMerchantLogo = (ImageView) view.findViewById(C0770R.id.img_merchant_logo);
        this.mTxtMerchantName = (TextView) view.findViewById(C0770R.id.txt_merchant_name);
        this.mTxtPaymentId = (TextView) view.findViewById(C0770R.id.txt_payment_id);
        this.mTxtAmount = (TextView) view.findViewById(C0770R.id.txt_amount);
        this.mEdtCardNo = (ApLabelAutoComplete) view.findViewById(C0770R.id.edt_card_no);
        this.mEdtPin2 = (ApLabelEditText) view.findViewById(C0770R.id.edt_pin2);
        this.mEdtCvv2 = (ApLabelEditText) view.findViewById(C0770R.id.edt_cvv2);
        this.mEdtExpirationDate = (ApLabelCardExpire) view.findViewById(C0770R.id.edt_expiration_date);
        this.mBtnPay = (Button) view.findViewById(C0770R.id.btn_pay);
        this.mBtnCancel = (Button) view.findViewById(C0770R.id.btn_cancel);
        view.findViewById(C0770R.id.img_shaparak).setOnClickListener(new C08132());
        this.mBtnTrust = view.findViewById(C0770R.id.lyt_trust_sdk);
        this.mBtnTrust.setOnClickListener(new C08143());
        showTrustButton(ClientConfig.getInstance(getContext()).isTrustAvailable());
        ScrollView scrollView = (ScrollView) view.findViewById(C0770R.id.scroll_view);
        ScrollGlobalLayoutListener.init(scrollView);
        FontManager.overrideFonts(scrollView);
        this.mEdtCardNo.getInnerInput().addTextChangedListener(new CardTextWatcher(this.mEdtCardNo) {
            public void onBankIdReady(long bankId) {
                PaymentFragment.this.getPresenter().checkForCVV2();
            }
        });
        this.mLytMerchantInfo.setVisibility(4);
        this.mActionListener = new PaymentPresenter(this);
        this.mActionListener.onRecoverInstanceState(savedInstanceState);
        this.mBtnPay.setOnClickListener(new C08165());
        this.mBtnCancel.setOnClickListener(new C08176());
        showCVV2(false);
        getPresenter().onRequestPayment(getActivity().getIntent().getBundleExtra(ServiceManager.PAYMENT_PAGE_DATA_BUNDLE_KEY));
    }

    public void showTrustButton(boolean showButton) {
        this.mBtnTrust.setVisibility(showButton ? 0 : 4);
    }

    private void onShaparakClicked() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://www.shaparak.ir"));
        startActivity(intent);
    }

    private void onTrustSdkClicked() {
        getPresenter().onRequestTrustCode();
    }

    public ActionListener getPresenter() {
        return this.mActionListener;
    }

    public void onSaveInstanceState(Bundle outState) {
        this.mActionListener.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    public String getCard() {
        return this.mEdtCardNo.getText().toString();
    }

    public UserCard getSelectedSuggestedCard() {
        return this.mSelectedCard;
    }

    public void setCardError(String message, boolean focus) {
        this.mEdtCardNo.getInnerInput().setError(message);
        this.mEdtCardNo.requestFocus();
    }

    public String getPin2() {
        return this.mEdtPin2.getText().toString();
    }

    public void setPin2Error(String message, boolean focus) {
        this.mEdtPin2.getInnerInput().setError(message);
        this.mEdtPin2.requestFocus();
    }

    public String getCvv2() {
        return this.mEdtCvv2.getText().toString();
    }

    public void setCvv2Error(String message, boolean focus) {
        this.mEdtCvv2.getInnerInput().setError(message);
        this.mEdtCvv2.requestFocus();
    }

    public String getExpMonth() {
        return this.mEdtExpirationDate.getMonthEditText().getText().toString();
    }

    public void setExpMonthError(String message, boolean focus) {
        this.mEdtExpirationDate.getMonthEditText().setError(message);
        this.mEdtExpirationDate.getMonthEditText().requestFocus();
    }

    public String getExpYear() {
        return this.mEdtExpirationDate.getYearEditText().getText().toString();
    }

    public void setExpYearError(String message, boolean focus) {
        this.mEdtExpirationDate.getYearEditText().setError(message);
        this.mEdtExpirationDate.getYearEditText().requestFocus();
    }

    public void showMerchantInfo(PaymentProfile paymentProfile) {
        PersonalizedConfig personalizedConfig = new SDKConfig().getPersonalizedConfig(BaseSetting.getHostId(getContext()));
        if (isAdded() && paymentProfile != null) {
            int highlightColor = ContextCompat.getColor(getActivity(), personalizedConfig.getColorConfig().getAmountHighlightColorRes());
            this.mTxtMerchantName.setText(new Spanny().append(paymentProfile.getMerchantName()).append("\t" + getString(C0770R.string.asanpardakht_param_merchant_code, paymentProfile.getMerchantCode()), new RelativeSizeSpan(0.7f)));
            if (personalizedConfig.needLoadLogo()) {
                if (StringUtils.isEmpty(paymentProfile.getMerchantLogoURL())) {
                    this.mImgMerchantLogo.setVisibility(8);
                } else {
                    DownloadLogoTask.loadInImageView(new SDKConfig(), paymentProfile.getMerchantLogoURL(), this.mImgMerchantLogo);
                }
            }
            if (!StringUtils.isEmpty(paymentProfile.getPaymentId())) {
                this.mTxtPaymentId.setText(getString(C0770R.string.asanpardakht_payment_id) + " : " + paymentProfile.getPaymentId());
                if (paymentProfile.getPaymentId().length() > 20) {
                    LayoutParams params = this.mLytMerchantInfo.getLayoutParams();
                    params.height = (int) TypedValue.applyDimension(1, 100.0f, getResources().getDisplayMetrics());
                    this.mLytMerchantInfo.setLayoutParams(params);
                    this.mTxtMerchantName.setTextSize(2, 16.0f);
                }
                this.mTxtPaymentId.setVisibility(0);
            }
            if (!StringUtils.isEmpty(paymentProfile.getAmount())) {
                this.mTxtAmount.setText(new Spanny().append(getString(C0770R.string.asanpardakht_amount)).append((CharSequence) ": \t\t").append(StringFormatter.formatPrice(getActivity(), paymentProfile.getAmount()), new ForegroundColorSpan(highlightColor)));
            }
            this.mLytMerchantInfo.setVisibility(0);
            this.mTxtAmount.setVisibility(0);
            UserCard defaultCard = null;
            if (StringUtils.between(paymentProfile.getHostSuggestCardNo(), 16, 19)) {
                defaultCard = new UserCard(paymentProfile.getHostSuggestCardNo());
                defaultCard.setRegisterCard(true);
            } else if (paymentProfile.getCard() != null) {
                defaultCard = paymentProfile.getCard();
            }
            InputSuggestionManager.suggestCard(this.mEdtCardNo, defaultCard, this.mSuggestionListener);
        }
    }

    public void showReportFragment(PaymentProfile paymentProfile) {
        if (isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("tran_data", paymentProfile);
            ((PaymentActivity) getActivity()).showReportFragment(bundle);
            getPresenter().cancelTimer();
            dismissProgress();
        }
    }

    public void showInquiryErrorDialog(String message, boolean canContinueOnMerchantError) {
        if (isAdded()) {
            CommonDialogParam dialogParam = new CommonDialogParam();
            dialogParam.title = getString(C0770R.string.asanpardakht_text_error_dialog_title);
            dialogParam.message = message;
            if (canContinueOnMerchantError) {
                dialogParam.positiveTextButton = getString(C0770R.string.asanpardakht_action_retry);
                dialogParam.negativeTextButton = getString(C0770R.string.asanpardakht_action_cancel);
            } else {
                dialogParam.negativeTextButton = getString(C0770R.string.asanpardakht_action_return_to_parent);
            }
            CommonDialog dialog = CommonDialog.getInstance(dialogParam);
            dialog.setTargetFragment(this, 0);
            dialog.setCancelable(false);
            dialog.showAllowStateLoss(getActivity().getSupportFragmentManager(), MERCHANT_ERROR_DIALOG);
        }
    }

    public void setPaymentResult(Bundle result) {
        if (isAdded()) {
            Intent intent = new Intent();
            intent.putExtra(Response.BUNDLE_KEY, result);
            getActivity().setResult(-1, intent);
            getPresenter().cancelTimer();
            getActivity().finish();
        }
    }

    public boolean isExpirationEditedByUser() {
        return this.mEdtExpirationDate.isFieldEdited();
    }

    public void showTimeoutError() {
        if (isAdded()) {
            CommonDialogParam dialogParam = new CommonDialogParam();
            dialogParam.title = getString(C0770R.string.asanpardakht_text_error_dialog_title);
            dialogParam.message = getString(C0770R.string.asanpardakht_message_sdk_status_timeout);
            dialogParam.negativeTextButton = getString(C0770R.string.asanpardakht_action_ok);
            Fragment dialog = CommonDialog.getInstance(dialogParam);
            dialog.setTargetFragment(this, 0);
            dialog.setCancelable(false);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.add(dialog, TIMEOUT_ERROR_DIALOG);
            ft.commitAllowingStateLoss();
        }
    }

    public void showPaymentErrorDialog(String errorMessage, PaymentProfile paymentProfile) {
        if (isAdded()) {
            this.mEdtPin2.setText("");
            this.mEdtCvv2.setText("");
            UserCard selectedCard = getSelectedSuggestedCard();
            if (!(selectedCard == null || selectedCard.isExpirySaved())) {
                this.mEdtExpirationDate.getMonthEditText().setText("");
                this.mEdtExpirationDate.getYearEditText().setText("");
            }
            CommonDialogParam dialogParam = new CommonDialogParam();
            dialogParam.title = getString(C0770R.string.asanpardakht_text_error_dialog_title);
            dialogParam.message = errorMessage;
            dialogParam.negativeTextButton = getString(C0770R.string.asanpardakht_action_ok);
            CommonDialog dialog = CommonDialog.getInstance(dialogParam);
            dialog.setTargetFragment(this, 0);
            dialog.setCancelable(false);
            dialog.showAllowStateLoss(getActivity().getSupportFragmentManager(), PAYMENT_ERROR_DIALOG);
        }
    }

    public void onResume() {
        super.onResume();
        getPresenter().checkPaymentTimeout();
    }

    public void onUpdateTimer(long millis) {
        if (isAdded()) {
            long second = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
            String formatedTime = String.format(Locale.US, "(%2d:%02d)", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(millis)), Long.valueOf(second)});
            this.mBtnCancel.setText(getString(C0770R.string.asanpardakht_action_param_cancel, formatedTime));
        }
    }

    public void showCVV2(boolean show) {
        if (show) {
            this.mEdtPin2.getInnerInput().setImeOptions(5);
            this.mEdtCvv2.setVisibility(0);
            this.mEdtExpirationDate.setVisibility(0);
            return;
        }
        this.mEdtPin2.setImeOptions(6);
        this.mEdtCvv2.setVisibility(8);
        this.mEdtExpirationDate.setVisibility(8);
    }

    public void updateCardSuggestion(boolean keepCard) {
        InputSuggestionManager.updateSuggestCards(this.mEdtCardNo, !keepCard, this.mSuggestionListener);
    }

    public void showValidateNumberDialog() {
        CommonDialogParam dialogParam = new CommonDialogParam();
        dialogParam.title = getString(C0770R.string.asanpardakht_text_warning_dialog_title);
        dialogParam.message = getString(C0770R.string.asanpardakht_info_validate_number_param, BaseSetting.getMobileNumber(getContext()));
        dialogParam.negativeTextButton = getString(C0770R.string.asanpardakht_action_return_to_parent);
        dialogParam.positiveTextButton = getString(C0770R.string.asanpardakht_action_continue);
        CommonDialog dialog = CommonDialog.getInstance(dialogParam);
        dialog.setTargetFragment(this, 0);
        dialog.setCancelable(false);
        dialog.showAllowStateLoss(getActivity().getSupportFragmentManager(), ROOT_VALIDATE_NUMBER);
    }

    public void showIsRootDisableDialog() {
        CommonDialogParam dialogParam = new CommonDialogParam();
        dialogParam.title = getString(C0770R.string.asanpardakht_text_error_dialog_title);
        dialogParam.message = ClientConfig.getInstance(getContext()).getRootConfigMessage();
        dialogParam.negativeTextButton = getString(C0770R.string.asanpardakht_action_return_to_parent);
        CommonDialog dialog = CommonDialog.getInstance(dialogParam);
        dialog.setTargetFragment(this, 0);
        dialog.setCancelable(false);
        dialog.showAllowStateLoss(getActivity().getSupportFragmentManager(), ROOT_DISABLE_DIALOG);
    }

    public void showIsRootWarningDialog() {
        CommonDialogParam dialogParam = new CommonDialogParam();
        dialogParam.title = getString(C0770R.string.asanpardakht_text_warning_dialog_title);
        dialogParam.message = ClientConfig.getInstance(getContext()).getRootConfigMessage();
        dialogParam.negativeTextButton = getString(C0770R.string.asanpardakht_action_continue);
        dialogParam.positiveTextButton = getString(C0770R.string.asanpardakht_action_return_to_parent);
        CommonDialog dialog = CommonDialog.getInstance(dialogParam);
        dialog.setTargetFragment(this, 0);
        dialog.setCancelable(false);
        dialog.showAllowStateLoss(getActivity().getSupportFragmentManager(), ROOT_WARNING_DIALOG);
    }

    public void showTrustDialog(Bundle trustDialogArgument) {
        VerificationSDKDialog trustDialog = new VerificationSDKDialog();
        trustDialog.setArguments(trustDialogArgument);
        trustDialog.showAllowStateLoss(getActivity().getSupportFragmentManager(), "auth-dialog");
    }

    public void showTrustCodeErrorDialog(WSStatus parseError, String errorMsg, WSResponse response) {
        CommonDialogParam dialogParam = new CommonDialogParam();
        dialogParam.title = getString(C0770R.string.asanpardakht_text_error_dialog_title);
        dialogParam.message = errorMsg;
        dialogParam.negativeTextButton = getString(C0770R.string.asanpardakht_action_ok);
        dialogParam.positiveTextButton = getString(C0770R.string.asanpardakht_action_retry);
        CommonDialog dialog = CommonDialog.getInstance(dialogParam);
        dialog.setTargetFragment(this, 0);
        dialog.setCancelable(false);
        dialog.showAllowStateLoss(getActivity().getSupportFragmentManager(), TRUST_CODE_ERROR_DIALOG);
    }

    public void onCommonDialogNegativeClicked(CommonDialog self) {
        if (isAdded()) {
            String tag = self.getTag();
            boolean z = true;
            switch (tag.hashCode()) {
                case -424356545:
                    if (tag.equals(ROOT_WARNING_DIALOG)) {
                        z = true;
                        break;
                    }
                    break;
                case 122505355:
                    if (tag.equals(ROOT_DISABLE_DIALOG)) {
                        z = true;
                        break;
                    }
                    break;
                case 253459090:
                    if (tag.equals(ROOT_VALIDATE_NUMBER)) {
                        z = true;
                        break;
                    }
                    break;
                case 1000410512:
                    if (tag.equals(MERCHANT_ERROR_DIALOG)) {
                        z = true;
                        break;
                    }
                    break;
                case 1308053857:
                    if (tag.equals(TRUST_CODE_ERROR_DIALOG)) {
                        z = true;
                        break;
                    }
                    break;
                case 1700738474:
                    if (tag.equals(TIMEOUT_ERROR_DIALOG)) {
                        z = false;
                        break;
                    }
                    break;
                case 1760905871:
                    if (tag.equals(PAYMENT_ERROR_DIALOG)) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    getPresenter().terminatePaymentDeuTimeout();
                    break;
                case true:
                    getPresenter().setPaymentInProgress(false);
                    getPresenter().checkPaymentTimeout();
                    break;
                case true:
                    getPresenter().onMerchantErrorDialogNegativeClicked();
                    break;
                case true:
                    getPresenter().proceedRootWarningDialog();
                    break;
                case true:
                    getPresenter().onRootErrorDialogNegativeClicked();
                    break;
                case true:
                    getPresenter().onValidateNumberNegativeClicked();
                    break;
            }
            self.dismissAllowingStateLoss();
        }
    }

    public void onCommonDialogPositiveClicked(CommonDialog self) {
        if (isAdded()) {
            self.dismissAllowingStateLoss();
            String tag = self.getTag();
            Object obj = -1;
            switch (tag.hashCode()) {
                case -424356545:
                    if (tag.equals(ROOT_WARNING_DIALOG)) {
                        obj = 3;
                        break;
                    }
                    break;
                case 253459090:
                    if (tag.equals(ROOT_VALIDATE_NUMBER)) {
                        obj = 4;
                        break;
                    }
                    break;
                case 1000410512:
                    if (tag.equals(MERCHANT_ERROR_DIALOG)) {
                        obj = 1;
                        break;
                    }
                    break;
                case 1308053857:
                    if (tag.equals(TRUST_CODE_ERROR_DIALOG)) {
                        obj = 2;
                        break;
                    }
                    break;
                case 1760905871:
                    if (tag.equals(PAYMENT_ERROR_DIALOG)) {
                        obj = null;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    return;
                case 1:
                    getPresenter().doMerchantInquiry();
                    return;
                case 2:
                    getPresenter().onRequestTrustCode();
                    return;
                case 3:
                    getPresenter().onRootErrorDialogNegativeClicked();
                    return;
                case 4:
                    getPresenter().setMobileNumberValidated();
                    return;
                default:
                    return;
            }
        }
    }
}
