package com.persianswitch.sdk.payment.payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.aa;
import com.persianswitch.sdk.C2262R;
import com.persianswitch.sdk.api.Response;
import com.persianswitch.sdk.base.BaseActivity;
import com.persianswitch.sdk.base.BaseDialogFragment;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.preference.SqliteSecurePreference;
import com.persianswitch.sdk.base.security.DecryptionException;
import com.persianswitch.sdk.payment.SDKConfig;
import com.persianswitch.sdk.payment.SDKSetting;
import com.persianswitch.sdk.payment.managers.SDKResultManager;
import com.persianswitch.sdk.payment.managers.ScheduledTaskManager;
import com.persianswitch.sdk.payment.managers.ServiceManager;
import com.persianswitch.sdk.payment.managers.ToastManager;

public final class PaymentActivity extends BaseActivity {
    /* renamed from: n */
    private boolean f7657n;
    /* renamed from: o */
    private boolean f7658o;

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentActivity$1 */
    class C23031 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ PaymentActivity f7656a;

        C23031(PaymentActivity paymentActivity) {
            this.f7656a = paymentActivity;
        }

        public void run() {
            this.f7656a.f7658o = false;
        }
    }

    /* renamed from: b */
    private void m11401b(Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtra(Response.f6990a, bundle);
        setResult(-1, intent);
        finish();
    }

    /* renamed from: a */
    public void m11402a(Bundle bundle) {
        aa a = m1547e().mo284a();
        Fragment reportFragment = new ReportFragment();
        reportFragment.setArguments(bundle);
        a.mo245a(C2262R.id.lyt_fragment, reportFragment);
        a.mo249b();
        this.f7657n = true;
    }

    public void onBackPressed() {
        if (this.f7657n) {
            ToastManager.m11108a(this, getString(C2262R.string.asanpardakht_text_payment_process_must_be_finalize));
        } else if (this.f7658o) {
            m11401b(SDKResultManager.m11090d(getApplicationContext()));
        } else {
            this.f7658o = true;
            new Handler().postDelayed(new C23031(this), 2000);
            ToastManager.m11108a(this, getString(C2262R.string.asanpardakht_text_press_back_again));
        }
    }

    protected void onCreate(Bundle bundle) {
        String string = getIntent().getBundleExtra(ServiceManager.f7403a).getString("host_security_token", TtmlNode.ANONYMOUS_REGION_ID);
        BaseSetting.m10457a(string);
        SDKSetting.m11040a(string);
        try {
            SqliteSecurePreference.m10704b(BaseSetting.m10452a((Context) this));
            SqliteSecurePreference.m10704b(SDKSetting.m11037a((Context) this));
        } catch (DecryptionException e) {
            if (!e.m10718a()) {
                Bundle a = SDKResultManager.m11082a(getApplicationContext());
                super.onCreate(bundle);
                SDKResultManager.m11085a((Activity) this, a);
                return;
            }
        }
        setTheme(new SDKConfig().m11031a(BaseSetting.m10470g(this)).mo3267a());
        super.onCreate(bundle);
        setContentView(C2262R.layout.asanpardakht_activity_payment);
        if (bundle != null) {
            BaseDialogFragment.m10441a(m1547e());
        }
        if (bundle != null) {
            return;
        }
        if (SDKResultManager.m11093f(this)) {
            aa a2 = m1547e().mo284a();
            a2.mo245a(C2262R.id.lyt_fragment, new PaymentFragment());
            a2.mo244a();
            ScheduledTaskManager.m11098a(getApplicationContext()).m11099a();
            return;
        }
        SDKResultManager.m11085a((Activity) this, SDKResultManager.m11086b(getApplicationContext()));
    }
}
