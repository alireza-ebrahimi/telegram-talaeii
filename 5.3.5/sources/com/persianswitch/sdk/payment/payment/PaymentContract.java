package com.persianswitch.sdk.payment.payment;

import android.os.Bundle;
import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.payment.StateRecoverable;
import com.persianswitch.sdk.payment.model.PaymentProfile;
import com.persianswitch.sdk.payment.model.UserCard;

interface PaymentContract {

    public interface ActionListener extends com.persianswitch.sdk.base.BaseContract.ActionListener, StateRecoverable {
        void cancelPayment();

        void cancelTimer();

        void checkForCVV2();

        void checkPaymentTimeout();

        void doMerchantInquiry();

        void doPayment();

        void onMerchantErrorDialogNegativeClicked();

        void onRequestPayment(Bundle bundle);

        void onRequestTrustCode();

        void onRootErrorDialogNegativeClicked();

        void onValidateNumberNegativeClicked();

        void proceedRootWarningDialog();

        void setMobileNumberValidated();

        void setPaymentInProgress(boolean z);

        void showSecurityHints();

        void startTimeoutTimer();

        void terminatePaymentDeuTimeout();
    }

    public interface View extends com.persianswitch.sdk.base.BaseContract.View<ActionListener>, ISuggestionUpdate {
        void dismissProgress();

        String getCard();

        String getCvv2();

        String getExpMonth();

        String getExpYear();

        String getPin2();

        UserCard getSelectedSuggestedCard();

        boolean isExpirationEditedByUser();

        void onUpdateTimer(long j);

        void setCardError(String str, boolean z);

        void setCvv2Error(String str, boolean z);

        void setExpMonthError(String str, boolean z);

        void setExpYearError(String str, boolean z);

        void setPaymentResult(Bundle bundle);

        void setPin2Error(String str, boolean z);

        void showCVV2(boolean z);

        void showInquiryErrorDialog(String str, boolean z);

        void showIsRootDisableDialog();

        void showIsRootWarningDialog();

        void showMerchantInfo(PaymentProfile paymentProfile);

        void showPaymentErrorDialog(String str, PaymentProfile paymentProfile);

        void showProgress();

        void showReportFragment(PaymentProfile paymentProfile);

        void showTimeoutError();

        void showTrustButton(boolean z);

        void showTrustCodeErrorDialog(WSStatus wSStatus, String str, WSResponse wSResponse);

        void showTrustDialog(Bundle bundle);

        void showValidateNumberDialog();
    }
}
