package org.telegram.customization.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.p098a.C1768f;
import com.google.p098a.p103c.C1748a;
import java.util.ArrayList;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Payment.Bank;
import org.telegram.customization.Model.Payment.HostRequestData;
import org.telegram.customization.Model.Payment.User;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2757a;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p152f.C2506b;
import org.telegram.customization.p167h.C2824a;
import org.telegram.customization.util.C2882h;
import org.telegram.customization.util.C2886j;
import org.telegram.customization.util.C2887k;
import org.telegram.customization.util.MaterialSpinner;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.ui.ActionBar.Theme;
import utils.p178a.C3791b;
import utils.view.FarsiButton;
import utils.view.FarsiEditText;
import utils.view.ToastUtil;

public class PaymentRegisterActivitySls extends Activity implements OnClickListener, C2497d {
    /* renamed from: a */
    static ProgressDialog f8408a;
    /* renamed from: b */
    FarsiEditText f8409b;
    /* renamed from: c */
    FarsiEditText f8410c;
    /* renamed from: d */
    FarsiEditText f8411d;
    /* renamed from: e */
    FarsiEditText f8412e;
    /* renamed from: f */
    FarsiEditText f8413f;
    /* renamed from: g */
    FarsiEditText f8414g;
    /* renamed from: h */
    FarsiEditText f8415h;
    /* renamed from: i */
    FarsiEditText f8416i;
    /* renamed from: j */
    FarsiEditText f8417j;
    /* renamed from: k */
    FarsiButton f8418k;
    /* renamed from: l */
    MaterialSpinner f8419l;
    /* renamed from: m */
    C2512a f8420m;
    /* renamed from: n */
    ArrayList<Bank> f8421n = new ArrayList();
    /* renamed from: o */
    Toolbar f8422o;
    /* renamed from: p */
    TextInputLayout f8423p;

    /* renamed from: org.telegram.customization.Activities.PaymentRegisterActivitySls$1 */
    class C25081 implements C2506b {
        /* renamed from: a */
        final /* synthetic */ PaymentRegisterActivitySls f8402a;

        C25081(PaymentRegisterActivitySls paymentRegisterActivitySls) {
            this.f8402a = paymentRegisterActivitySls;
        }

        /* renamed from: a */
        public void mo3416a() {
            C3791b.m13937a(true);
        }

        /* renamed from: b */
        public void mo3417b() {
            C3791b.m13937a(false);
        }
    }

    /* renamed from: org.telegram.customization.Activities.PaymentRegisterActivitySls$2 */
    class C25092 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ PaymentRegisterActivitySls f8403a;

        C25092(PaymentRegisterActivitySls paymentRegisterActivitySls) {
            this.f8403a = paymentRegisterActivitySls;
        }

        public void onClick(View view) {
            this.f8403a.onBackPressed();
        }
    }

    /* renamed from: org.telegram.customization.Activities.PaymentRegisterActivitySls$3 */
    class C25103 extends C1748a<ArrayList<Bank>> {
        /* renamed from: d */
        final /* synthetic */ PaymentRegisterActivitySls f8404d;

        C25103(PaymentRegisterActivitySls paymentRegisterActivitySls) {
            this.f8404d = paymentRegisterActivitySls;
        }
    }

    /* renamed from: org.telegram.customization.Activities.PaymentRegisterActivitySls$a */
    public class C2512a extends ArrayAdapter<Bank> {
        /* renamed from: a */
        List<Bank> f8406a = new ArrayList();
        /* renamed from: b */
        final /* synthetic */ PaymentRegisterActivitySls f8407b;

        public C2512a(PaymentRegisterActivitySls paymentRegisterActivitySls, Context context, int i, List<Bank> list) {
            this.f8407b = paymentRegisterActivitySls;
            super(context, i, list);
            this.f8406a = list;
        }

        /* renamed from: a */
        public View m12253a(int i, View view, ViewGroup viewGroup) {
            View inflate = this.f8407b.getLayoutInflater().inflate(R.layout.multi_select_spinner_item, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.ftv_color_name)).setText(((Bank) this.f8406a.get(i)).getName());
            return inflate;
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return m12253a(i, view, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return m12253a(i, view, viewGroup);
        }
    }

    /* renamed from: a */
    private void m12254a(Context context, String str, String str2) {
        f8408a = new ProgressDialog(this);
        f8408a.setTitle(str);
        f8408a.setMessage(str2);
        f8408a.show();
    }

    /* renamed from: a */
    public static void m12255a(final C2506b c2506b) {
        C2818c.m13087a(ApplicationLoader.applicationContext, new C2497d() {
            public void onResult(Object obj, int i) {
                switch (i) {
                    case 27:
                        obj = (HostRequestData) obj;
                        Log.d("APPPPP:", new C1768f().m8395a(obj));
                        Bundle bundle = new Bundle();
                        bundle.putString("host_security_token", C3791b.ar(ApplicationLoader.applicationContext));
                        bundle.putInt("host_id", 1152);
                        bundle.putString("protocol_version", "1.8.0");
                        bundle.putString("host_data", obj.getHostRequest());
                        bundle.putString("host_data_sign", obj.getHostRequestSign());
                        String a = C2882h.m13368a(UserConfig.getCurrentUser().phone);
                        Log.d("alireza", "alireza phone no" + a);
                        bundle.putString("client_mobile_no", a);
                        new C2824a(bundle, c2506b).m13160a(ApplicationLoader.applicationContext);
                        return;
                    default:
                        return;
                }
            }
        }).m13140i();
    }

    /* renamed from: a */
    public void m12256a() {
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(Theme.getColor(Theme.key_actionBarDefault));
        }
        this.f8422o = (Toolbar) findViewById(R.id.toolbar);
        this.f8422o.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        findViewById(R.id.iv_back).setOnClickListener(new C25092(this));
        this.f8423p = (TextInputLayout) findViewById(R.id.til_national_code);
        this.f8409b = (FarsiEditText) findViewById(R.id.fet_first_name);
        this.f8410c = (FarsiEditText) findViewById(R.id.fet_last_name);
        this.f8411d = (FarsiEditText) findViewById(R.id.fet_password);
        this.f8412e = (FarsiEditText) findViewById(R.id.fet_password_conf);
        this.f8413f = (FarsiEditText) findViewById(R.id.fet_desc);
        this.f8414g = (FarsiEditText) findViewById(R.id.fet_address);
        this.f8415h = (FarsiEditText) findViewById(R.id.fet_national_code);
        this.f8416i = (FarsiEditText) findViewById(R.id.fet_card_no);
        this.f8417j = (FarsiEditText) findViewById(R.id.fet_shaba);
        this.f8419l = (MaterialSpinner) findViewById(R.id.sp_activity_group);
        this.f8418k = (FarsiButton) findViewById(R.id.ftv_register);
        this.f8418k.setOnClickListener(this);
        this.f8409b.setText(UserConfig.getCurrentUser().first_name);
        this.f8410c.setText(UserConfig.getCurrentUser().last_name);
        this.f8421n = (ArrayList) new C1768f().m8393a(C2886j.m13401a(ApplicationLoader.applicationContext, "banks.txt"), new C25103(this).m8360b());
        this.f8420m = new C2512a(this, ApplicationLoader.applicationContext, R.layout.multi_select_spinner_item, this.f8421n);
        this.f8419l.setAdapter(this.f8420m);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ftv_register:
                if (TextUtils.isEmpty(this.f8409b.getText().toString())) {
                    ToastUtil.a(ApplicationLoader.applicationContext, "نام خود را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.f8410c.getText().toString())) {
                    ToastUtil.a(ApplicationLoader.applicationContext, "نام خود خانوادگی را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.f8411d.getText().toString())) {
                    ToastUtil.a(ApplicationLoader.applicationContext, "رمزعبور خود را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.f8412e.getText().toString())) {
                    ToastUtil.a(ApplicationLoader.applicationContext, "تاییده رمزعبور خود را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.f8413f.getText().toString())) {
                    ToastUtil.a(ApplicationLoader.applicationContext, "توضیخات خود را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.f8414g.getText().toString())) {
                    ToastUtil.a(ApplicationLoader.applicationContext, "آدرس خود را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.f8415h.getText().toString())) {
                    ToastUtil.a(ApplicationLoader.applicationContext, "کد ملی خود را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.f8416i.getText().toString())) {
                    ToastUtil.a(ApplicationLoader.applicationContext, "شماره کارت خود را وارد کنید").show();
                    return;
                } else if (!C2887k.m13403a(this.f8417j.getText().toString())) {
                    ToastUtil.a(ApplicationLoader.applicationContext, "شماره شبا خود را وارد کنید").show();
                    return;
                } else if (!C2887k.m13402a(this.f8415h, this.f8423p, R.string.error_national_code, this)) {
                    return;
                } else {
                    if (this.f8411d.getText().toString().contentEquals(this.f8412e.getText().toString())) {
                        User user = new User();
                        user.setUsername(UserConfig.getCurrentUser().username);
                        user.setFirstName(this.f8409b.getText().toString());
                        user.setLastName(this.f8410c.getText().toString());
                        user.setPassword(this.f8411d.getText().toString());
                        user.setDescription(this.f8413f.getText().toString());
                        user.setAddress(this.f8414g.getText().toString());
                        user.setNationalCode(this.f8415h.getText().toString());
                        user.setCardNo(this.f8416i.getText().toString());
                        user.setShaba(this.f8417j.getText().toString());
                        user.setBankId(((Bank) this.f8421n.get(this.f8419l.getSelectedItemPosition())).getId());
                        user.setMobileNumber(Long.valueOf(Long.parseLong(C2882h.m13368a(UserConfig.getCurrentUser().phone))));
                        C2818c.m13087a(ApplicationLoader.applicationContext, (C2497d) this).m13121a(user);
                        m12254a(ApplicationLoader.applicationContext, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), "لطفا منتظر بمانید");
                        return;
                    }
                    ToastUtil.a(ApplicationLoader.applicationContext, "رمز عبور وارد شده یکسان نیست").show();
                    return;
                }
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_register_payment);
        m12256a();
        m12255a(new C25081(this));
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case -28:
                if (f8408a != null) {
                    f8408a.dismiss();
                    return;
                }
                return;
            case 28:
                Log.d("alireza", "alireza user registerd in payment");
                C2757a c2757a = (C2757a) obj;
                if (c2757a.m12805b() == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                    ToastUtil.a(ApplicationLoader.applicationContext, "ثبت نام با موفقیت انجام شد").show();
                    finish();
                    return;
                }
                ToastUtil.a(getApplicationContext(), c2757a.m12806c()).show();
                if (f8408a != null) {
                    f8408a.dismiss();
                    return;
                }
                return;
            default:
                return;
        }
    }
}
