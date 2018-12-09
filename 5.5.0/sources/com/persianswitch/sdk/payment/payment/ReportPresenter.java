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
    /* renamed from: a */
    private final View f7726a;
    /* renamed from: b */
    private final PaymentProfile f7727b;
    /* renamed from: c */
    private long f7728c;
    /* renamed from: d */
    private CountDownTimer f7729d;

    public ReportPresenter(View view, Bundle bundle) {
        this.f7726a = view;
        this.f7727b = (PaymentProfile) bundle.getParcelable("tran_data");
        m11583c().mo3365a(this.f7727b);
    }

    /* renamed from: a */
    public void mo3367a() {
        if (this.f7727b != null) {
            m11583c().mo3364a(SDKResultManager.m11083a(m11583c().mo3228a(), this.f7727b));
        }
        this.f7729d.cancel();
    }

    /* renamed from: a */
    public void mo3347a(Bundle bundle) {
        bundle.putLong("remain_timeout_millis", this.f7728c);
    }

    /* renamed from: b */
    public void mo3368b() {
        long convert = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS);
        if (this.f7729d != null) {
            this.f7729d.cancel();
        }
        this.f7729d = new CountDownTimer(this, this.f7728c, convert) {
            /* renamed from: a */
            final /* synthetic */ ReportPresenter f7725a;

            public void onFinish() {
                this.f7725a.mo3367a();
            }

            public void onTick(long j) {
                this.f7725a.m11583c().mo3363a(TimeUnit.SECONDS.convert(j, TimeUnit.MILLISECONDS));
            }
        }.start();
    }

    /* renamed from: b */
    public void mo3350b(Bundle bundle) {
        long e = ClientConfig.m11126a(m11583c().mo3228a()).m11139e() * 1000;
        if (bundle != null) {
            this.f7728c = bundle.getLong("remain_timeout_millis", e);
        } else {
            this.f7728c = e;
        }
    }

    /* renamed from: c */
    public View m11583c() {
        return this.f7726a;
    }
}
