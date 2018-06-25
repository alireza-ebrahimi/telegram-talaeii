package com.persianswitch.sdk.payment.payment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.api.Request.General;
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
    static final String TRANSACTION_DATA_KEY = "tran_data";
    private boolean isLegalToBack;
    private boolean isOnReportFragment;

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentActivity$1 */
    class C08111 implements Runnable {
        C08111() {
        }

        public void run() {
            PaymentActivity.this.isLegalToBack = false;
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        String secureToken = getIntent().getBundleExtra(ServiceManager.PAYMENT_PAGE_DATA_BUNDLE_KEY).getString(General.SECURE_TOKEN, "");
        BaseSetting.initSecurePreference(secureToken);
        SDKSetting.initSecurePreference(secureToken);
        try {
            SqliteSecurePreference.checkEncryption(BaseSetting.getSecurePreferences(this));
            SqliteSecurePreference.checkEncryption(SDKSetting.getSecurePreferences(this));
        } catch (DecryptionException e) {
            if (!e.isNoData()) {
                Bundle bundle = SDKResultManager.onDecryptionError(getApplicationContext());
                super.onCreate(savedInstanceState);
                SDKResultManager.finishActivityWithResult(this, bundle);
                return;
            }
        }
        setTheme(new SDKConfig().getPersonalizedConfig(BaseSetting.getHostId(this)).getTheme());
        super.onCreate(savedInstanceState);
        setContentView(C0770R.layout.asanpardakht_activity_payment);
        if (savedInstanceState != null) {
            BaseDialogFragment.dismissAllDialogs(getSupportFragmentManager());
        }
        if (savedInstanceState != null) {
            return;
        }
        if (SDKResultManager.isUserRegistered(this)) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(C0770R.id.lyt_fragment, new PaymentFragment());
            fragmentTransaction.commit();
            ScheduledTaskManager.getInstance(getApplicationContext()).checkTasks();
            return;
        }
        SDKResultManager.finishActivityWithResult(this, SDKResultManager.onUserNotRegistered(getApplicationContext()));
    }

    public void onBackPressed() {
        if (this.isOnReportFragment) {
            ToastManager.showSharedToast(this, getString(C0770R.string.asanpardakht_text_payment_process_must_be_finalize));
        } else if (this.isLegalToBack) {
            setPaymentResult(SDKResultManager.onPaymentCanceled(getApplicationContext()));
        } else {
            this.isLegalToBack = true;
            new Handler().postDelayed(new C08111(), FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
            ToastManager.showSharedToast(this, getString(C0770R.string.asanpardakht_text_press_back_again));
        }
    }

    private void setPaymentResult(Bundle result) {
        Intent intent = new Intent();
        intent.putExtra(Response.BUNDLE_KEY, result);
        setResult(-1, intent);
        finish();
    }

    public void showReportFragment(Bundle bundle) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ReportFragment reportFragment = new ReportFragment();
        reportFragment.setArguments(bundle);
        fragmentTransaction.replace(C0770R.id.lyt_fragment, reportFragment);
        fragmentTransaction.commitAllowingStateLoss();
        this.isOnReportFragment = true;
    }
}
