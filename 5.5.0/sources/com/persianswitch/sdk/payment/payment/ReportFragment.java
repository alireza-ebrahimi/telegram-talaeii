package com.persianswitch.sdk.payment.payment;

import android.os.Bundle;
import android.support.v4.content.C0235a;
import android.support.v7.widget.AppCompatButton;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.persianswitch.sdk.C2262R;
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
    /* renamed from: a */
    private android.view.View f7713a;
    /* renamed from: b */
    private android.view.View f7714b;
    /* renamed from: c */
    private android.view.View f7715c;
    /* renamed from: d */
    private ImageView f7716d;
    /* renamed from: e */
    private TextView f7717e;
    /* renamed from: f */
    private TextView f7718f;
    /* renamed from: g */
    private TextView f7719g;
    /* renamed from: h */
    private LinearLayout f7720h;
    /* renamed from: i */
    private AppCompatButton f7721i;
    /* renamed from: j */
    private android.view.View f7722j;
    /* renamed from: k */
    private ActionListener f7723k;
    /* renamed from: l */
    private PaymentProfile f7724l;

    /* renamed from: com.persianswitch.sdk.payment.payment.ReportFragment$1 */
    class C23171 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ ReportFragment f7711a;

        C23171(ReportFragment reportFragment) {
            this.f7711a = reportFragment;
        }

        public void onClick(android.view.View view) {
            this.f7711a.m11569f();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.ReportFragment$2 */
    class C23182 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ ReportFragment f7712a;

        C23182(ReportFragment reportFragment) {
            this.f7712a = reportFragment;
        }

        public void onClick(android.view.View view) {
            this.f7712a.m11572i();
        }
    }

    /* renamed from: f */
    private void m11569f() {
        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
    }

    /* renamed from: g */
    private void m11570g() {
        this.f7722j.setEnabled(false);
        if (ScreenshotUtils.m10767a(getView(), ScreenshotUtils.m10766a(getContext()), true)) {
            this.f7722j.setVisibility(8);
            Toast.makeText(getContext(), getString(C2262R.string.asanpardakht_info_saved_in_gallery), 0).show();
            return;
        }
        this.f7722j.setEnabled(true);
    }

    /* renamed from: h */
    private void m11571h() {
        if (this.f7724l != null) {
            PersonalizedConfig a = new SDKConfig().m11031a(BaseSetting.m10470g(mo3228a()));
            int c;
            if (this.f7724l.m11188j() == TransactionStatus.SUCCESS) {
                c = C0235a.m1075c(getContext(), a.mo3273e().mo3264c());
                DrawableUtils.m10760a(this.f7714b, a.mo3268a(getContext(), c), false);
                DrawableUtils.m10760a(this.f7713a, a.mo3270b(getContext(), c), false);
                this.f7716d.setImageResource(C2262R.drawable.asanpardakht_ic_success);
                this.f7717e.setText(getString(C2262R.string.asanpardakht_info_transaction_successful));
                this.f7719g.setVisibility(8);
            } else if (this.f7724l.m11201s()) {
                c = C0235a.m1075c(getContext(), a.mo3273e().mo3265d());
                DrawableUtils.m10760a(this.f7714b, a.mo3268a(getContext(), c), false);
                DrawableUtils.m10760a(this.f7713a, a.mo3270b(getContext(), c), false);
                this.f7716d.setImageResource(C2262R.drawable.asanpardakht_ic_warning);
                this.f7717e.setText(getString(C2262R.string.asanpardakht_info_transaction_unknown));
                this.f7719g.setVisibility(8);
            } else {
                c = C0235a.m1075c(getContext(), a.mo3273e().mo3266e());
                DrawableUtils.m10760a(this.f7714b, a.mo3268a(getContext(), c), false);
                DrawableUtils.m10760a(this.f7713a, a.mo3270b(getContext(), c), false);
                this.f7716d.setImageResource(C2262R.drawable.asanpardakht_ic_warning);
                this.f7717e.setText(getString(C2262R.string.asanpardakht_info_transaction_failed));
                if (this.f7724l.m11188j() == TransactionStatus.UNKNOWN) {
                    CharSequence c2 = ClientConfig.m11126a(getContext()).m11137c();
                    if (c2 == null) {
                        c2 = getString(C2262R.string.asanpardakht_info_transaction_unknown_more_info);
                    }
                    this.f7719g.setText(c2);
                    this.f7719g.setVisibility(0);
                } else {
                    this.f7719g.setVisibility(8);
                }
            }
            mo3363a(30);
        }
    }

    /* renamed from: i */
    private void m11572i() {
        m11578e().mo3367a();
    }

    /* renamed from: a */
    public void mo3363a(long j) {
        if (!isAdded()) {
            return;
        }
        if (this.f7724l == null || this.f7724l.m11188j() != TransactionStatus.SUCCESS) {
            this.f7721i.setText(getString(C2262R.string.asanpardakht_action_param_return_parent, Long.valueOf(j)));
            return;
        }
        this.f7721i.setText(getString(C2262R.string.asanpardakht_action_param_complete_payment_process, Long.valueOf(j)));
    }

    /* renamed from: a */
    public void mo3364a(Bundle bundle) {
        SDKResultManager.m11085a(getActivity(), bundle);
    }

    /* renamed from: a */
    protected void mo3316a(android.view.View view, Bundle bundle) {
        this.f7713a = view.findViewById(C2262R.id.lyt_report);
        this.f7714b = view.findViewById(C2262R.id.lyt_status);
        this.f7716d = (ImageView) view.findViewById(C2262R.id.img_status_icon);
        this.f7717e = (TextView) view.findViewById(C2262R.id.txt_status_title);
        this.f7719g = (TextView) view.findViewById(C2262R.id.txt_transaction_status_info);
        this.f7715c = view.findViewById(C2262R.id.lyt_separator_line);
        this.f7718f = (TextView) view.findViewById(C2262R.id.txt_description);
        this.f7720h = (LinearLayout) view.findViewById(C2262R.id.lyt_content);
        this.f7722j = view.findViewById(C2262R.id.btn_screenshot);
        this.f7722j.setOnClickListener(new C23171(this));
        this.f7721i = (AppCompatButton) view.findViewById(C2262R.id.btn_return_parent);
        this.f7721i.setOnClickListener(new C23182(this));
        android.view.View findViewById = view.findViewById(C2262R.id.lyt_trust_sdk);
        if (findViewById != null) {
            findViewById.setVisibility(8);
        }
        FontManager.m10664a(view);
        this.f7723k = new ReportPresenter(this, getArguments());
        this.f7723k.mo3350b(bundle);
        this.f7723k.mo3368b();
    }

    /* renamed from: a */
    public void mo3365a(PaymentProfile paymentProfile) {
        if (paymentProfile != null) {
            this.f7724l = paymentProfile;
            m11571h();
            int c = C0235a.m1075c(getContext(), new SDKConfig().m11031a(BaseSetting.m10470g(mo3228a())).mo3273e().mo3263b());
            UserCard f = paymentProfile.m11180f();
            if (f != null) {
                this.f7720h.addView(new APKeyValueView(getContext(), getString(C2262R.string.asanpardakht_card_no), f.m11272d(), f.m11274e()));
            }
            String n = paymentProfile.m11196n();
            if (!StringUtils.m10803a(n)) {
                this.f7720h.addView(new APKeyValueView(getContext(), getString(C2262R.string.asanpardakht_amount), new Spanny().m10771a(StringFormatter.m10796a(getActivity(), n), new ForegroundColorSpan(c), new RelativeSizeSpan(1.1f))));
            }
            String l = paymentProfile.m11192l();
            if (!StringUtils.m10803a(l)) {
                CharSequence spanny = new Spanny();
                spanny.m10769a(l);
                if (!StringUtils.m10803a(paymentProfile.m11190k())) {
                    spanny.m10770a("\t" + getString(C2262R.string.asanpardakht_param_merchant_code, paymentProfile.m11190k()), new RelativeSizeSpan(0.7f));
                }
                this.f7720h.addView(new APKeyValueView(getContext(), getString(C2262R.string.asanpardakht_merchant), spanny));
            }
            l = paymentProfile.m11197o();
            if (!StringUtils.m10803a(l)) {
                this.f7720h.addView(new APKeyValueView(getContext(), getString(C2262R.string.asanpardakht_payment_id), l));
            }
            l = paymentProfile.m11198p();
            if (!StringUtils.m10803a(l)) {
                this.f7720h.addView(new APKeyValueView(getContext(), getString(C2262R.string.asanpardakht_distributer_mobile), l));
            }
            l = paymentProfile.m11184h();
            if (!StringUtils.m10803a(l)) {
                this.f7720h.addView(new APKeyValueView(getContext(), getString(C2262R.string.asanpardakht_reference_no), l));
            }
            c = paymentProfile.m11186i();
            if (c > 0) {
                this.f7720h.addView(new APKeyValueView(getContext(), getString(C2262R.string.asanpardakht_score), String.valueOf(c)));
            }
            this.f7720h.addView(new APKeyValueView(getContext(), getString(C2262R.string.asanpardakht_date), DateFormatUtils.m10759a(new Date(), LanguageManager.m10669a(getContext()).m10677b())));
            if (StringUtils.m10803a(paymentProfile.m11182g())) {
                this.f7715c.setVisibility(8);
                this.f7718f.setVisibility(8);
            } else {
                this.f7715c.setVisibility(0);
                this.f7718f.setVisibility(0);
                this.f7718f.setText(paymentProfile.m11182g());
            }
            FontManager.m10664a(this.f7720h);
        }
    }

    /* renamed from: d */
    protected int mo3330d() {
        return new SDKConfig().m11031a(BaseSetting.m10470g(mo3228a())).mo3271c();
    }

    /* renamed from: e */
    public ActionListener m11578e() {
        return this.f7723k;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        switch (i) {
            case 1:
                if (iArr.length <= 0 || iArr[0] != 0) {
                    ToastManager.m11108a(getContext(), getString(C2262R.string.asanpardakht_deny_permission));
                    return;
                } else {
                    m11570g();
                    return;
                }
            default:
                return;
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        this.f7723k.mo3347a(bundle);
        super.onSaveInstanceState(bundle);
    }
}
