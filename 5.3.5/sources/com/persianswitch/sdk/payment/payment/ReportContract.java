package com.persianswitch.sdk.payment.payment;

import android.os.Bundle;
import com.persianswitch.sdk.payment.StateRecoverable;
import com.persianswitch.sdk.payment.model.PaymentProfile;

public interface ReportContract {

    public interface ActionListener extends com.persianswitch.sdk.base.BaseContract.ActionListener, StateRecoverable {
        void buildPaymentResult();

        void startTimeoutTimer();
    }

    public interface View extends com.persianswitch.sdk.base.BaseContract.View<ActionListener> {
        void buildReport(PaymentProfile paymentProfile);

        void onResult(Bundle bundle);

        void updateCountdown(long j);
    }
}
