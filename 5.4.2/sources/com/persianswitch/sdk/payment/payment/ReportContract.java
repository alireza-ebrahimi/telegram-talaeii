package com.persianswitch.sdk.payment.payment;

import android.os.Bundle;
import com.persianswitch.sdk.payment.StateRecoverable;
import com.persianswitch.sdk.payment.model.PaymentProfile;

public interface ReportContract {

    public interface ActionListener extends com.persianswitch.sdk.base.BaseContract.ActionListener, StateRecoverable {
        /* renamed from: a */
        void mo3367a();

        /* renamed from: b */
        void mo3368b();
    }

    public interface View extends com.persianswitch.sdk.base.BaseContract.View<ActionListener> {
        /* renamed from: a */
        void mo3363a(long j);

        /* renamed from: a */
        void mo3364a(Bundle bundle);

        /* renamed from: a */
        void mo3365a(PaymentProfile paymentProfile);
    }
}
