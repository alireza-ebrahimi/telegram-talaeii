package org.telegram.customization.Activities;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.C0768c;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.p098a.C1768f;
import com.persianswitch.sdk.api.IPaymentService;
import com.persianswitch.sdk.api.IPaymentService.Stub;
import com.persianswitch.sdk.api.PaymentService;
import com.persianswitch.sdk.api.Response;
import com.persianswitch.sdk.api.Response.General;
import com.persianswitch.sdk.api.Response.Payment;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Payment.HostRequestData;
import org.telegram.customization.Model.Payment.HostResponseData;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2757a;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p152f.C2506b;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.ui.ActionBar.Theme;
import utils.p178a.C3791b;
import utils.view.ToastUtil;

public class PaymentConfirmActivity extends C0768c implements OnClickListener, C2497d {
    /* renamed from: A */
    private IPaymentService f8387A;
    /* renamed from: B */
    private final ServiceConnection f8388B = new C25041(this);
    /* renamed from: n */
    String f8389n = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: o */
    ProgressBar f8390o;
    /* renamed from: p */
    TextView f8391p;
    /* renamed from: q */
    TextView f8392q;
    /* renamed from: r */
    View f8393r;
    /* renamed from: s */
    View f8394s;
    /* renamed from: t */
    View f8395t;
    /* renamed from: u */
    TextView f8396u;
    /* renamed from: v */
    Button f8397v;
    /* renamed from: w */
    ScrollView f8398w;
    /* renamed from: x */
    HostRequestData f8399x;
    /* renamed from: y */
    Toolbar f8400y;
    /* renamed from: z */
    ProgressDialog f8401z;

    /* renamed from: org.telegram.customization.Activities.PaymentConfirmActivity$1 */
    class C25041 implements ServiceConnection {
        /* renamed from: a */
        final /* synthetic */ PaymentConfirmActivity f8384a;

        C25041(PaymentConfirmActivity paymentConfirmActivity) {
            this.f8384a = paymentConfirmActivity;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("alireza", "sdk service connected");
            this.f8384a.f8387A = Stub.m10439a(iBinder);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("alireza", "sdk service  Dissconnected");
            this.f8384a.f8387A = null;
        }
    }

    /* renamed from: org.telegram.customization.Activities.PaymentConfirmActivity$2 */
    class C25052 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ PaymentConfirmActivity f8385a;

        C25052(PaymentConfirmActivity paymentConfirmActivity) {
            this.f8385a = paymentConfirmActivity;
        }

        public void onClick(View view) {
            this.f8385a.onBackPressed();
        }
    }

    /* renamed from: org.telegram.customization.Activities.PaymentConfirmActivity$3 */
    class C25073 implements C2506b {
        /* renamed from: a */
        final /* synthetic */ PaymentConfirmActivity f8386a;

        C25073(PaymentConfirmActivity paymentConfirmActivity) {
            this.f8386a = paymentConfirmActivity;
        }

        /* renamed from: a */
        public void mo3416a() {
            this.f8386a.m12249n();
            C3791b.m13937a(true);
        }

        /* renamed from: b */
        public void mo3417b() {
            C3791b.m13937a(false);
            this.f8386a.m12249n();
            this.f8386a.finish();
        }
    }

    /* renamed from: a */
    private void m12243a(Context context, String str, String str2) {
        if (this.f8401z == null) {
            this.f8401z = new ProgressDialog(this);
            this.f8401z.setTitle(str);
            this.f8401z.setMessage(str2);
            this.f8401z.setCancelable(false);
        }
        this.f8401z.show();
    }

    /* renamed from: c */
    private void m12245c(int i) {
        C2818c.m13087a(getApplicationContext(), (C2497d) this).m13116a(this.f8389n, String.valueOf(i));
    }

    /* renamed from: k */
    private void m12246k() {
        C2818c.m13087a(ApplicationLoader.applicationContext, (C2497d) this).m13146m(this.f8389n);
        m12243a(ApplicationLoader.applicationContext, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), "لطفا منتظر بمانید");
    }

    /* renamed from: l */
    private void m12247l() {
        if (C3791b.m13972e()) {
            m12249n();
        } else {
            PaymentRegisterActivitySls.m12255a(new C25073(this));
        }
    }

    /* renamed from: m */
    private void m12248m() {
        bindService(new Intent(this, PaymentService.class), this.f8388B, 1);
    }

    /* renamed from: n */
    private void m12249n() {
        try {
            if (this.f8401z != null) {
                this.f8401z.dismiss();
            }
        } catch (Exception e) {
        }
    }

    /* renamed from: j */
    public void m12250j() {
        if (this.f8387A != null && this.f8399x != null) {
            try {
                Bundle bundle = new Bundle();
                bundle.putString("protocol_version", "1.8.0");
                bundle.putString("host_security_token", C3791b.ar(ApplicationLoader.applicationContext));
                bundle.putLong("host_tran_id", this.f8399x.getTranId());
                bundle.putString("host_data", this.f8399x.getHostRequest());
                bundle.putString("host_data_sign", this.f8399x.getHostRequestSign());
                try {
                    startIntentSenderForResult(((PendingIntent) this.f8387A.mo3226c(bundle).getParcelable("payment_intent")).getIntentSender(), 100, null, 0, 0, 0);
                } catch (SendIntentException e) {
                }
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i == 100 && i2 == -1 && intent != null) {
            Bundle bundleExtra = intent.getBundleExtra(Response.f6990a);
            if (bundleExtra != null) {
                int i3 = bundleExtra.getInt(General.f6983a);
                String string = bundleExtra.getString(General.f6985c);
                String string2 = bundleExtra.getString(General.f6986d);
                switch (i3) {
                    case 0:
                        Long.valueOf(bundleExtra.getLong(Payment.f6987a));
                        HostResponseData hostResponseData = new HostResponseData();
                        hostResponseData.setHresp(string);
                        hostResponseData.setHsign(string2);
                        Log.e("alireza", "alireza" + new C1768f().m8395a(this.f8399x));
                        C2818c.m13087a(getApplicationContext(), (C2497d) this).m13120a(hostResponseData);
                        m12243a(ApplicationLoader.applicationContext, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), "لطفا منتظر بمانید");
                        Toast.makeText(this, "Transaction Successful in SDK", 0).show();
                        return;
                    case 1001:
                    case 1201:
                        Long.valueOf(bundleExtra.getLong(Payment.f6987a));
                        Toast.makeText(this, "Transaction Unknown", 0).show();
                        m12245c(i3);
                        return;
                    case 1002:
                        m12245c(i3);
                        Toast.makeText(this, "Transaction Failed", 0).show();
                        return;
                    case 1100:
                        Toast.makeText(this, "Invalid Host Data, check your host data request ", 0).show();
                        m12245c(i3);
                        return;
                    case 1102:
                        m12243a(ApplicationLoader.applicationContext, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), "لطفا منتظر بمانید");
                        m12247l();
                        Toast.makeText(this, "First Register User", 0).show();
                        m12245c(i3);
                        return;
                    case 1105:
                        Toast.makeText(this, "You must update your sdk", 0).show();
                        m12245c(i3);
                        return;
                    case 2020:
                        Toast.makeText(this, "Transaction Canceled By User", 0).show();
                        m12245c(i3);
                        return;
                    case 2021:
                        Toast.makeText(this, "Transaction Canceled By SDK(due to timeout)", 0).show();
                        m12245c(i3);
                        return;
                    case 2022:
                        Toast.makeText(this, "User don't confirm registration data (such as mobile no)", 0).show();
                        m12245c(i3);
                        return;
                    case 2023:
                        Toast.makeText(this, "Don't change secret key until register again", 0).show();
                        m12245c(i3);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                m12243a(ApplicationLoader.applicationContext, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), "لطفا منتظر بمانید");
                C2818c.m13087a(ApplicationLoader.applicationContext, (C2497d) this).m13147n(this.f8389n);
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_confirm_payment);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(Theme.getColor(Theme.key_actionBarDefault));
        }
        this.f8400y = (Toolbar) findViewById(R.id.toolbar);
        this.f8400y.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        findViewById(R.id.iv_back).setOnClickListener(new C25052(this));
        this.f8389n = getIntent().getStringExtra("EXTRA_PAYMENT_ID");
        this.f8390o = (ProgressBar) findViewById(R.id.pb_loading);
        this.f8391p = (TextView) findViewById(R.id.tv_date);
        this.f8393r = findViewById(R.id.item_id);
        this.f8394s = findViewById(R.id.item_issue_tracking);
        this.f8395t = findViewById(R.id.item_desc);
        this.f8396u = (TextView) findViewById(R.id.tv_amount);
        this.f8397v = (Button) findViewById(R.id.btn_submit);
        this.f8392q = (TextView) findViewById(R.id.tv_desc);
        this.f8397v.setOnClickListener(this);
        this.f8398w = (ScrollView) findViewById(R.id.scroll_view);
        m12246k();
        this.f8390o.setVisibility(0);
    }

    public void onPause() {
        super.onPause();
        Log.e("LEE", "onPause");
        unbindService(this.f8388B);
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case -35:
                m12249n();
                findViewById(R.id.pb_loading).setVisibility(8);
                ToastUtil.a(getApplicationContext(), "خطا در دریافت اطلاعات").show();
                Log.e("alireza", "alireza payment Nok  ");
                finish();
                return;
            case -31:
                m12249n();
                findViewById(R.id.pb_loading).setVisibility(8);
                ToastUtil.a(getApplicationContext(), "خطا در دریافت اطلاعات").show();
                Log.e("alireza", "alireza payment Nok  ");
                finish();
                return;
            case -30:
                m12249n();
                ToastUtil.a(getApplicationContext(), "پرداخت نا موفق بود").show();
                return;
            case 30:
                m12249n();
                C2757a c2757a = (C2757a) obj;
                if (c2757a.m12805b() == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                    ToastUtil.a(getApplicationContext(), "پرداخت با موفقیت انجام شد").show();
                } else {
                    ToastUtil.a(getApplicationContext(), c2757a.m12806c()).show();
                    if (this.f8401z != null) {
                        this.f8401z.dismiss();
                    }
                }
                try {
                    String c = c2757a.m12806c();
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.paymentSuccessMessage, c);
                } catch (Exception e) {
                }
                finish();
                return;
            case 31:
                m12249n();
                findViewById(R.id.pb_loading).setVisibility(8);
                this.f8399x = (HostRequestData) obj;
                m12250j();
                return;
            case 35:
                findViewById(R.id.pb_loading).setVisibility(8);
                this.f8399x = (HostRequestData) obj;
                this.f8396u.setText(this.f8399x.getAmount() + TtmlNode.ANONYMOUS_REGION_ID);
                this.f8391p.setText(this.f8399x.getDate());
                this.f8392q.setText(this.f8399x.getDescription());
                m12247l();
                return;
            default:
                return;
        }
    }

    protected void onResume() {
        super.onResume();
        Log.e("LEE", "onResume");
        m12248m();
    }
}
