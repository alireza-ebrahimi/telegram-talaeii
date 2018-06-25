package com.persianswitch.sdk.payment.payment;

import android.os.Bundle;
import android.os.CountDownTimer;
import com.persianswitch.sdk.payment.managers.SDKResultManager;
import com.persianswitch.sdk.payment.model.ClientConfig;
import com.persianswitch.sdk.payment.model.PaymentProfile;
import com.persianswitch.sdk.payment.payment.ReportContract.ActionListener;
import com.persianswitch.sdk.payment.payment.ReportContract.View;
import java.util.concurrent.TimeUnit;

public final class ReportPresenter implements ActionListener {
    private CountDownTimer mCountDownTimer;
    private final PaymentProfile mPaymentProfile;
    private long mRemainTimeoutMillis;
    private final View mView;

    public ReportPresenter(View view, Bundle arguments) {
        this.mView = view;
        this.mPaymentProfile = (PaymentProfile) arguments.getParcelable("tran_data");
        getView().buildReport(this.mPaymentProfile);
    }

    public View getView() {
        return this.mView;
    }

    public void buildPaymentResult() {
        if (this.mPaymentProfile != null) {
            getView().onResult(SDKResultManager.onPaymentResult(getView().getApplicationContext(), this.mPaymentProfile));
        }
        this.mCountDownTimer.cancel();
    }

    public void startTimeoutTimer() {
        long intervalMillis = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS);
        if (this.mCountDownTimer != null) {
            this.mCountDownTimer.cancel();
        }
        this.mCountDownTimer = new CountDownTimer(this.mRemainTimeoutMillis, intervalMillis) {
            public void onTick(long millisUntilFinished) {
                ReportPresenter.this.getView().updateCountdown(TimeUnit.SECONDS.convert(millisUntilFinished, TimeUnit.MILLISECONDS));
            }

            public void onFinish() {
                ReportPresenter.this.buildPaymentResult();
            }
        }.start();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("remain_timeout_millis", this.mRemainTimeoutMillis);
    }

    public void onRecoverInstanceState(Bundle savedState) {
        long timeoutDurationMillis = ClientConfig.getInstance(getView().getApplicationContext()).getReportTimeout() * 1000;
        if (savedState != null) {
            this.mRemainTimeoutMillis = savedState.getLong("remain_timeout_millis", timeoutDurationMillis);
        } else {
            this.mRemainTimeoutMillis = timeoutDurationMillis;
        }
    }
}
