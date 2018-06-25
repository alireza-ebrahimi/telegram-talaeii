package com.persianswitch.sdk.payment.payment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.base.BaseMVPFragment;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.manager.FontManager;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.style.PersonalizedConfig;
import com.persianswitch.sdk.base.utils.DateFormatUtils;
import com.persianswitch.sdk.base.utils.DrawableUtils;
import com.persianswitch.sdk.base.utils.ScreenshotUtils;
import com.persianswitch.sdk.base.utils.Spanny;
import com.persianswitch.sdk.base.utils.strings.StringFormatter;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.widgets.APKeyValueView;
import com.persianswitch.sdk.payment.SDKConfig;
import com.persianswitch.sdk.payment.managers.SDKResultManager;
import com.persianswitch.sdk.payment.managers.ToastManager;
import com.persianswitch.sdk.payment.model.ClientConfig;
import com.persianswitch.sdk.payment.model.PaymentProfile;
import com.persianswitch.sdk.payment.model.TransactionStatus;
import com.persianswitch.sdk.payment.model.UserCard;
import com.persianswitch.sdk.payment.payment.ReportContract.ActionListener;
import com.persianswitch.sdk.payment.payment.ReportContract.View;
import java.util.Date;

public class ReportFragment extends BaseMVPFragment<ActionListener> implements View {
    public static final int COUNTDOWN = 30;
    public static final int STORAGE_WRITE_PERMISSION_REQUESTCODE = 1;
    private static final String TAG = "ReportFragment";
    private ActionListener mActionListener;
    private AppCompatButton mBtnReturn;
    private android.view.View mBtnScreenshot;
    private ImageView mImgStatus;
    private LinearLayout mLytContent;
    private android.view.View mLytReport;
    private android.view.View mLytStatus;
    private PaymentProfile mPaymentProfile;
    private android.view.View mSeparatorLine;
    private TextView mTxtDescription;
    private TextView mTxtStatus;
    private TextView mTxtTransactionInfo;

    /* renamed from: com.persianswitch.sdk.payment.payment.ReportFragment$1 */
    class C08251 implements OnClickListener {
        C08251() {
        }

        public void onClick(android.view.View v) {
            ReportFragment.this.grantCapturePermission();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.ReportFragment$2 */
    class C08262 implements OnClickListener {
        C08262() {
        }

        public void onClick(android.view.View v) {
            ReportFragment.this.onReturnToParent();
        }
    }

    protected int getLayoutResourceId() {
        return new SDKConfig().getPersonalizedConfig(BaseSetting.getHostId(getApplicationContext())).getReportLayout();
    }

    protected void onFragmentCreated(android.view.View view, Bundle savedInstanceState) {
        this.mLytReport = view.findViewById(C0770R.id.lyt_report);
        this.mLytStatus = view.findViewById(C0770R.id.lyt_status);
        this.mImgStatus = (ImageView) view.findViewById(C0770R.id.img_status_icon);
        this.mTxtStatus = (TextView) view.findViewById(C0770R.id.txt_status_title);
        this.mTxtTransactionInfo = (TextView) view.findViewById(C0770R.id.txt_transaction_status_info);
        this.mSeparatorLine = view.findViewById(C0770R.id.lyt_separator_line);
        this.mTxtDescription = (TextView) view.findViewById(C0770R.id.txt_description);
        this.mLytContent = (LinearLayout) view.findViewById(C0770R.id.lyt_content);
        this.mBtnScreenshot = view.findViewById(C0770R.id.btn_screenshot);
        this.mBtnScreenshot.setOnClickListener(new C08251());
        this.mBtnReturn = (AppCompatButton) view.findViewById(C0770R.id.btn_return_parent);
        this.mBtnReturn.setOnClickListener(new C08262());
        android.view.View viewProtectorToolbar = view.findViewById(C0770R.id.lyt_trust_sdk);
        if (viewProtectorToolbar != null) {
            viewProtectorToolbar.setVisibility(8);
        }
        FontManager.overrideFonts(view);
        this.mActionListener = new ReportPresenter(this, getArguments());
        this.mActionListener.onRecoverInstanceState(savedInstanceState);
        this.mActionListener.startTimeoutTimer();
    }

    public void updateCountdown(long seconds) {
        if (!isAdded()) {
            return;
        }
        if (this.mPaymentProfile == null || this.mPaymentProfile.getStatusType() != TransactionStatus.SUCCESS) {
            this.mBtnReturn.setText(getString(C0770R.string.asanpardakht_action_param_return_parent, Long.valueOf(seconds)));
            return;
        }
        this.mBtnReturn.setText(getString(C0770R.string.asanpardakht_action_param_complete_payment_process, Long.valueOf(seconds)));
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    ToastManager.showSharedToast(getContext(), getString(C0770R.string.asanpardakht_deny_permission));
                    return;
                } else {
                    saveScreenCapture();
                    return;
                }
            default:
                return;
        }
    }

    private void grantCapturePermission() {
        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
    }

    private void saveScreenCapture() {
        this.mBtnScreenshot.setEnabled(false);
        if (ScreenshotUtils.saveScreenshot(getView(), ScreenshotUtils.buildScreenshotPath(getContext()), true)) {
            this.mBtnScreenshot.setVisibility(8);
            Toast.makeText(getContext(), getString(C0770R.string.asanpardakht_info_saved_in_gallery), 0).show();
            return;
        }
        this.mBtnScreenshot.setEnabled(true);
    }

    public void buildReport(PaymentProfile paymentProfile) {
        if (paymentProfile != null) {
            this.mPaymentProfile = paymentProfile;
            updateTransactionStatus();
            int highlightColor = ContextCompat.getColor(getContext(), new SDKConfig().getPersonalizedConfig(BaseSetting.getHostId(getApplicationContext())).getColorConfig().getReportAmountHighlightColorRes());
            UserCard card = paymentProfile.getCard();
            if (card != null) {
                String cardNo = card.getCardDisplayName();
                int bankLogo = card.getLogoResource();
                this.mLytContent.addView(new APKeyValueView(getContext(), getString(C0770R.string.asanpardakht_card_no), cardNo, bankLogo));
            }
            String amount = paymentProfile.getAmount();
            if (!StringUtils.isEmpty(amount)) {
                CharSequence amountValue = new Spanny().append(StringFormatter.formatPrice(getActivity(), amount), new ForegroundColorSpan(highlightColor), new RelativeSizeSpan(1.1f));
                this.mLytContent.addView(new APKeyValueView(getContext(), getString(C0770R.string.asanpardakht_amount), amountValue));
            }
            CharSequence merchantName = paymentProfile.getMerchantName();
            if (!StringUtils.isEmpty(merchantName)) {
                Spanny merchantNameCode = new Spanny();
                merchantNameCode.append(merchantName);
                if (!StringUtils.isEmpty(paymentProfile.getMerchantCode())) {
                    merchantNameCode.append((CharSequence) "\t" + getString(C0770R.string.asanpardakht_param_merchant_code, paymentProfile.getMerchantCode()), (Object) new RelativeSizeSpan(0.7f));
                }
                this.mLytContent.addView(new APKeyValueView(getContext(), getString(C0770R.string.asanpardakht_merchant), merchantNameCode));
            }
            String paymentId = paymentProfile.getPaymentId();
            if (!StringUtils.isEmpty(paymentId)) {
                this.mLytContent.addView(new APKeyValueView(getContext(), getString(C0770R.string.asanpardakht_payment_id), paymentId));
            }
            String distributerMobileNumber = paymentProfile.getDistributorMobileNumber();
            if (!StringUtils.isEmpty(distributerMobileNumber)) {
                this.mLytContent.addView(new APKeyValueView(getContext(), getString(C0770R.string.asanpardakht_distributer_mobile), distributerMobileNumber));
            }
            String referenceNo = paymentProfile.getReferenceNumber();
            if (!StringUtils.isEmpty(referenceNo)) {
                this.mLytContent.addView(new APKeyValueView(getContext(), getString(C0770R.string.asanpardakht_reference_no), referenceNo));
            }
            int point = paymentProfile.getPoint();
            if (point > 0) {
                this.mLytContent.addView(new APKeyValueView(getContext(), getString(C0770R.string.asanpardakht_score), String.valueOf(point)));
            }
            String dateStr = DateFormatUtils.longDateTime(new Date(), LanguageManager.getInstance(getContext()).isPersian());
            this.mLytContent.addView(new APKeyValueView(getContext(), getString(C0770R.string.asanpardakht_date), dateStr));
            if (StringUtils.isEmpty(paymentProfile.getServerMessage())) {
                this.mSeparatorLine.setVisibility(8);
                this.mTxtDescription.setVisibility(8);
            } else {
                this.mSeparatorLine.setVisibility(0);
                this.mTxtDescription.setVisibility(0);
                this.mTxtDescription.setText(paymentProfile.getServerMessage());
            }
            FontManager.overrideFonts(this.mLytContent);
        }
    }

    private void updateTransactionStatus() {
        if (this.mPaymentProfile != null) {
            PersonalizedConfig personalizedConfig = new SDKConfig().getPersonalizedConfig(BaseSetting.getHostId(getApplicationContext()));
            if (this.mPaymentProfile.getStatusType() == TransactionStatus.SUCCESS) {
                int clSuccess = ContextCompat.getColor(getContext(), personalizedConfig.getColorConfig().getSuccessColor());
                DrawableUtils.setBackground(this.mLytStatus, personalizedConfig.prepareReportStatusBG(getContext(), clSuccess), false);
                DrawableUtils.setBackground(this.mLytReport, personalizedConfig.prepareReportBG(getContext(), clSuccess), false);
                this.mImgStatus.setImageResource(C0770R.drawable.asanpardakht_ic_success);
                this.mTxtStatus.setText(getString(C0770R.string.asanpardakht_info_transaction_successful));
                this.mTxtTransactionInfo.setVisibility(8);
            } else if (this.mPaymentProfile.isUnknown()) {
                int clUnknown = ContextCompat.getColor(getContext(), personalizedConfig.getColorConfig().getUnknownColor());
                DrawableUtils.setBackground(this.mLytStatus, personalizedConfig.prepareReportStatusBG(getContext(), clUnknown), false);
                DrawableUtils.setBackground(this.mLytReport, personalizedConfig.prepareReportBG(getContext(), clUnknown), false);
                this.mImgStatus.setImageResource(C0770R.drawable.asanpardakht_ic_warning);
                this.mTxtStatus.setText(getString(C0770R.string.asanpardakht_info_transaction_unknown));
                this.mTxtTransactionInfo.setVisibility(8);
            } else {
                int clFailed = ContextCompat.getColor(getContext(), personalizedConfig.getColorConfig().getFailedColor());
                DrawableUtils.setBackground(this.mLytStatus, personalizedConfig.prepareReportStatusBG(getContext(), clFailed), false);
                DrawableUtils.setBackground(this.mLytReport, personalizedConfig.prepareReportBG(getContext(), clFailed), false);
                this.mImgStatus.setImageResource(C0770R.drawable.asanpardakht_ic_warning);
                this.mTxtStatus.setText(getString(C0770R.string.asanpardakht_info_transaction_failed));
                if (this.mPaymentProfile.getStatusType() == TransactionStatus.UNKNOWN) {
                    String unknownMessage = ClientConfig.getInstance(getContext()).getUnknownMessage();
                    if (unknownMessage == null) {
                        unknownMessage = getString(C0770R.string.asanpardakht_info_transaction_unknown_more_info);
                    }
                    this.mTxtTransactionInfo.setText(unknownMessage);
                    this.mTxtTransactionInfo.setVisibility(0);
                } else {
                    this.mTxtTransactionInfo.setVisibility(8);
                }
            }
            updateCountdown(30);
        }
    }

    private void onReturnToParent() {
        getPresenter().buildPaymentResult();
    }

    public void onResult(Bundle result) {
        SDKResultManager.finishActivityWithResult(getActivity(), result);
    }

    public ActionListener getPresenter() {
        return this.mActionListener;
    }

    public void onSaveInstanceState(Bundle outState) {
        this.mActionListener.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
