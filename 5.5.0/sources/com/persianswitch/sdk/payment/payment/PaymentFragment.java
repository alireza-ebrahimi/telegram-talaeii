package com.persianswitch.sdk.payment.payment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.aa;
import android.support.v4.content.C0235a;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.persianswitch.sdk.C2262R;
import com.persianswitch.sdk.api.Response;
import com.persianswitch.sdk.base.BaseMVPFragment;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.manager.FontManager;
import com.persianswitch.sdk.base.style.PersonalizedConfig;
import com.persianswitch.sdk.base.utils.Spanny;
import com.persianswitch.sdk.base.utils.strings.StringFormatter;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.DownloadLogoTask;
import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.base.widgets.edittext.ApLabelAutoComplete;
import com.persianswitch.sdk.base.widgets.edittext.ApLabelCardExpire;
import com.persianswitch.sdk.base.widgets.edittext.ApLabelEditText;
import com.persianswitch.sdk.base.widgets.listener.ScrollGlobalLayoutListener;
import com.persianswitch.sdk.payment.SDKConfig;
import com.persianswitch.sdk.payment.common.CommonDialog;
import com.persianswitch.sdk.payment.common.CommonDialog.CommonDialogInterface;
import com.persianswitch.sdk.payment.common.CommonDialog.CommonDialogParam;
import com.persianswitch.sdk.payment.managers.ServiceManager;
import com.persianswitch.sdk.payment.managers.suggestion.InputSuggestionManager;
import com.persianswitch.sdk.payment.managers.suggestion.SuggestionListener;
import com.persianswitch.sdk.payment.model.ClientConfig;
import com.persianswitch.sdk.payment.model.PaymentProfile;
import com.persianswitch.sdk.payment.model.UserCard;
import com.persianswitch.sdk.payment.payment.PaymentContract.ActionListener;
import com.persianswitch.sdk.payment.payment.PaymentContract.View;
import com.persianswitch.sdk.payment.utils.CardTextWatcher;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PaymentFragment extends BaseMVPFragment<ActionListener> implements CommonDialogInterface, View {
    /* renamed from: a */
    private ActionListener f7666a;
    /* renamed from: b */
    private android.view.View f7667b;
    /* renamed from: c */
    private ImageView f7668c;
    /* renamed from: d */
    private TextView f7669d;
    /* renamed from: e */
    private TextView f7670e;
    /* renamed from: f */
    private TextView f7671f;
    /* renamed from: g */
    private ApLabelAutoComplete f7672g;
    /* renamed from: h */
    private ApLabelEditText f7673h;
    /* renamed from: i */
    private ApLabelEditText f7674i;
    /* renamed from: j */
    private ApLabelCardExpire f7675j;
    /* renamed from: k */
    private Button f7676k;
    /* renamed from: l */
    private Button f7677l;
    /* renamed from: m */
    private android.view.View f7678m;
    /* renamed from: n */
    private UserCard f7679n;
    /* renamed from: o */
    private final SuggestionListener<UserCard> f7680o = new C23041(this);

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentFragment$1 */
    class C23041 implements SuggestionListener<UserCard> {
        /* renamed from: a */
        final /* synthetic */ PaymentFragment f7659a;

        C23041(PaymentFragment paymentFragment) {
            this.f7659a = paymentFragment;
        }

        /* renamed from: a */
        public void mo3311a() {
            this.f7659a.f7679n = null;
            this.f7659a.f7673h.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.f7659a.f7674i.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.f7659a.f7675j.getYearEditText().setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.f7659a.f7675j.getMonthEditText().setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.f7659a.m11493p().mo3358i();
        }

        /* renamed from: a */
        public void m11447a(UserCard userCard) {
            this.f7659a.f7679n = userCard;
            this.f7659a.f7673h.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.f7659a.f7674i.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.f7659a.f7675j.getMonthEditText().setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.f7659a.f7675j.getYearEditText().setText(TtmlNode.ANONYMOUS_REGION_ID);
            if (userCard.m11277h()) {
                this.f7659a.f7675j.getMonthEditText().setText("11");
                this.f7659a.f7675j.getYearEditText().setText("11");
                this.f7659a.f7675j.setFieldEdited(false);
            } else {
                this.f7659a.f7675j.getMonthEditText().getText().clear();
                this.f7659a.f7675j.getYearEditText().getText().clear();
                this.f7659a.f7675j.setFieldEdited(true);
            }
            this.f7659a.m11493p().mo3358i();
            this.f7659a.m11459q();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentFragment$2 */
    class C23052 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ PaymentFragment f7660a;

        C23052(PaymentFragment paymentFragment) {
            this.f7660a = paymentFragment;
        }

        public void onClick(android.view.View view) {
            this.f7660a.m11460r();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentFragment$3 */
    class C23063 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ PaymentFragment f7661a;

        C23063(PaymentFragment paymentFragment) {
            this.f7661a = paymentFragment;
        }

        public void onClick(android.view.View view) {
            this.f7661a.m11461s();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentFragment$5 */
    class C23085 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ PaymentFragment f7664a;

        C23085(PaymentFragment paymentFragment) {
            this.f7664a = paymentFragment;
        }

        public void onClick(android.view.View view) {
            this.f7664a.m11493p().mo3351c();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentFragment$6 */
    class C23096 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ PaymentFragment f7665a;

        C23096(PaymentFragment paymentFragment) {
            this.f7665a = paymentFragment;
        }

        public void onClick(android.view.View view) {
            this.f7665a.m11493p().mo3353d();
        }
    }

    /* renamed from: q */
    private void m11459q() {
        if (StringUtils.m10803a(this.f7672g.getText().toString())) {
            this.f7672g.getInnerInput().requestFocus();
        } else if (StringUtils.m10803a(this.f7673h.getText().toString())) {
            this.f7673h.getInnerInput().requestFocus();
        } else if (StringUtils.m10803a(this.f7674i.getText().toString())) {
            this.f7674i.getInnerInput().requestFocus();
        } else if (StringUtils.m10803a(this.f7675j.getMonthEditText().getText().toString())) {
            this.f7675j.getMonthEditText().requestFocus();
        } else if (StringUtils.m10803a(this.f7675j.getYearEditText().getText().toString())) {
            this.f7675j.getYearEditText().requestFocus();
        } else if (getView() != null) {
            this.f7676k.requestFocus();
            ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.f7676k.getWindowToken(), 0);
        }
    }

    /* renamed from: r */
    private void m11460r() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://www.shaparak.ir"));
        startActivity(intent);
    }

    /* renamed from: s */
    private void m11461s() {
        m11493p().mo3359j();
    }

    /* renamed from: a */
    public void mo3314a(long j) {
        if (isAdded()) {
            long toSeconds = TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j));
            String format = String.format(Locale.US, "(%2d:%02d)", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j)), Long.valueOf(toSeconds)});
            this.f7677l.setText(getString(C2262R.string.asanpardakht_action_param_cancel, format));
        }
    }

    /* renamed from: a */
    public void mo3315a(Bundle bundle) {
        if (isAdded()) {
            Intent intent = new Intent();
            intent.putExtra(Response.f6990a, bundle);
            getActivity().setResult(-1, intent);
            m11493p().mo3346a();
            getActivity().finish();
        }
    }

    /* renamed from: a */
    public void mo3316a(android.view.View view, Bundle bundle) {
        this.f7667b = view.findViewById(C2262R.id.lyt_merchant_info);
        this.f7668c = (ImageView) view.findViewById(C2262R.id.img_merchant_logo);
        this.f7669d = (TextView) view.findViewById(C2262R.id.txt_merchant_name);
        this.f7670e = (TextView) view.findViewById(C2262R.id.txt_payment_id);
        this.f7671f = (TextView) view.findViewById(C2262R.id.txt_amount);
        this.f7672g = (ApLabelAutoComplete) view.findViewById(C2262R.id.edt_card_no);
        this.f7673h = (ApLabelEditText) view.findViewById(C2262R.id.edt_pin2);
        this.f7674i = (ApLabelEditText) view.findViewById(C2262R.id.edt_cvv2);
        this.f7675j = (ApLabelCardExpire) view.findViewById(C2262R.id.edt_expiration_date);
        this.f7676k = (Button) view.findViewById(C2262R.id.btn_pay);
        this.f7677l = (Button) view.findViewById(C2262R.id.btn_cancel);
        view.findViewById(C2262R.id.img_shaparak).setOnClickListener(new C23052(this));
        this.f7678m = view.findViewById(C2262R.id.lyt_trust_sdk);
        this.f7678m.setOnClickListener(new C23063(this));
        mo3329c(ClientConfig.m11126a(getContext()).m11135a());
        android.view.View view2 = (ScrollView) view.findViewById(C2262R.id.scroll_view);
        ScrollGlobalLayoutListener.m11028a(view2);
        FontManager.m10664a(view2);
        this.f7672g.getInnerInput().addTextChangedListener(new CardTextWatcher(this, this.f7672g) {
            /* renamed from: a */
            final /* synthetic */ PaymentFragment f7663a;

            /* renamed from: a */
            public void mo3313a(long j) {
                this.f7663a.m11493p().mo3358i();
            }
        });
        this.f7667b.setVisibility(4);
        this.f7666a = new PaymentPresenter(this);
        this.f7666a.mo3350b(bundle);
        this.f7676k.setOnClickListener(new C23085(this));
        this.f7677l.setOnClickListener(new C23096(this));
        mo3327b(false);
        m11493p().mo3352c(getActivity().getIntent().getBundleExtra(ServiceManager.f7403a));
    }

    /* renamed from: a */
    public void mo3317a(WSStatus wSStatus, String str, WSResponse wSResponse) {
        CommonDialogParam commonDialogParam = new CommonDialogParam();
        commonDialogParam.f7380a = getString(C2262R.string.asanpardakht_text_error_dialog_title);
        commonDialogParam.f7381b = str;
        commonDialogParam.f7383d = getString(C2262R.string.asanpardakht_action_ok);
        commonDialogParam.f7382c = getString(C2262R.string.asanpardakht_action_retry);
        CommonDialog a = CommonDialog.m11052a(commonDialogParam);
        a.setTargetFragment(this, 0);
        a.setCancelable(false);
        a.m10444a(getActivity().m1547e(), "trust_error");
    }

    /* renamed from: a */
    public void mo3318a(CommonDialog commonDialog) {
        if (isAdded()) {
            commonDialog.dismissAllowingStateLoss();
            String tag = commonDialog.getTag();
            Object obj = -1;
            switch (tag.hashCode()) {
                case -424356545:
                    if (tag.equals("root_warning")) {
                        obj = 3;
                        break;
                    }
                    break;
                case 253459090:
                    if (tag.equals("validate_number")) {
                        obj = 4;
                        break;
                    }
                    break;
                case 1000410512:
                    if (tag.equals("inquiry_error")) {
                        obj = 1;
                        break;
                    }
                    break;
                case 1308053857:
                    if (tag.equals("trust_error")) {
                        obj = 2;
                        break;
                    }
                    break;
                case 1760905871:
                    if (tag.equals("payment_error")) {
                        obj = null;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    return;
                case 1:
                    m11493p().mo3349b();
                    return;
                case 2:
                    m11493p().mo3359j();
                    return;
                case 3:
                    m11493p().mo3355f();
                    return;
                case 4:
                    m11493p().mo3361l();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: a */
    public void mo3319a(PaymentProfile paymentProfile) {
        PersonalizedConfig a = new SDKConfig().m11031a(BaseSetting.m10470g(getContext()));
        if (isAdded() && paymentProfile != null) {
            int c = C0235a.m1075c(getActivity(), a.mo3273e().mo3262a());
            this.f7669d.setText(new Spanny().m10769a(paymentProfile.m11192l()).m10770a("\t" + getString(C2262R.string.asanpardakht_param_merchant_code, paymentProfile.m11190k()), new RelativeSizeSpan(0.7f)));
            if (a.mo3272d()) {
                if (StringUtils.m10803a(paymentProfile.m11194m())) {
                    this.f7668c.setVisibility(8);
                } else {
                    DownloadLogoTask.m10816a(new SDKConfig(), paymentProfile.m11194m(), this.f7668c);
                }
            }
            if (!StringUtils.m10803a(paymentProfile.m11197o())) {
                this.f7670e.setText(getString(C2262R.string.asanpardakht_payment_id) + " : " + paymentProfile.m11197o());
                if (paymentProfile.m11197o().length() > 20) {
                    LayoutParams layoutParams = this.f7667b.getLayoutParams();
                    layoutParams.height = (int) TypedValue.applyDimension(1, 100.0f, getResources().getDisplayMetrics());
                    this.f7667b.setLayoutParams(layoutParams);
                    this.f7669d.setTextSize(2, 16.0f);
                }
                this.f7670e.setVisibility(0);
            }
            if (!StringUtils.m10803a(paymentProfile.m11196n())) {
                this.f7671f.setText(new Spanny().m10769a(getString(C2262R.string.asanpardakht_amount)).m10769a(": \t\t").m10770a(StringFormatter.m10796a(getActivity(), paymentProfile.m11196n()), new ForegroundColorSpan(c)));
            }
            this.f7667b.setVisibility(0);
            this.f7671f.setVisibility(0);
            UserCard userCard = null;
            if (StringUtils.m10804a(paymentProfile.m11178e(), 16, 19)) {
                userCard = new UserCard(paymentProfile.m11178e());
                userCard.m11269b(true);
            } else if (paymentProfile.m11180f() != null) {
                userCard = paymentProfile.m11180f();
            }
            InputSuggestionManager.m11110a(this.f7672g, userCard, this.f7680o);
        }
    }

    /* renamed from: a */
    public void mo3320a(String str, PaymentProfile paymentProfile) {
        if (isAdded()) {
            this.f7673h.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.f7674i.setText(TtmlNode.ANONYMOUS_REGION_ID);
            UserCard f = mo3334f();
            if (!(f == null || f.m11277h())) {
                this.f7675j.getMonthEditText().setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.f7675j.getYearEditText().setText(TtmlNode.ANONYMOUS_REGION_ID);
            }
            CommonDialogParam commonDialogParam = new CommonDialogParam();
            commonDialogParam.f7380a = getString(C2262R.string.asanpardakht_text_error_dialog_title);
            commonDialogParam.f7381b = str;
            commonDialogParam.f7383d = getString(C2262R.string.asanpardakht_action_ok);
            CommonDialog a = CommonDialog.m11052a(commonDialogParam);
            a.setTargetFragment(this, 0);
            a.setCancelable(false);
            a.m10444a(getActivity().m1547e(), "payment_error");
        }
    }

    /* renamed from: a */
    public void mo3321a(String str, boolean z) {
        this.f7672g.getInnerInput().setError(str);
        this.f7672g.requestFocus();
    }

    /* renamed from: a */
    public void mo3322a(boolean z) {
        InputSuggestionManager.m11111a(this.f7672g, !z, this.f7680o);
    }

    /* renamed from: b */
    public void mo3323b(Bundle bundle) {
        VerificationSDKDialog verificationSDKDialog = new VerificationSDKDialog();
        verificationSDKDialog.setArguments(bundle);
        verificationSDKDialog.m10444a(getActivity().m1547e(), "auth-dialog");
    }

    /* renamed from: b */
    public void mo3324b(CommonDialog commonDialog) {
        if (isAdded()) {
            String tag = commonDialog.getTag();
            boolean z = true;
            switch (tag.hashCode()) {
                case -424356545:
                    if (tag.equals("root_warning")) {
                        z = true;
                        break;
                    }
                    break;
                case 122505355:
                    if (tag.equals("root_disable")) {
                        z = true;
                        break;
                    }
                    break;
                case 253459090:
                    if (tag.equals("validate_number")) {
                        z = true;
                        break;
                    }
                    break;
                case 1000410512:
                    if (tag.equals("inquiry_error")) {
                        z = true;
                        break;
                    }
                    break;
                case 1308053857:
                    if (tag.equals("trust_error")) {
                        z = true;
                        break;
                    }
                    break;
                case 1700738474:
                    if (tag.equals("timeout_error")) {
                        z = false;
                        break;
                    }
                    break;
                case 1760905871:
                    if (tag.equals("payment_error")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    m11493p().mo3357h();
                    break;
                case true:
                    m11493p().mo3348a(false);
                    m11493p().mo3356g();
                    break;
                case true:
                    m11493p().mo3354e();
                    break;
                case true:
                    m11493p().mo3360k();
                    break;
                case true:
                    m11493p().mo3355f();
                    break;
                case true:
                    m11493p().mo3362m();
                    break;
            }
            commonDialog.dismissAllowingStateLoss();
        }
    }

    /* renamed from: b */
    public void mo3325b(PaymentProfile paymentProfile) {
        if (isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("tran_data", paymentProfile);
            ((PaymentActivity) getActivity()).m11402a(bundle);
            m11493p().mo3346a();
            m10450c();
        }
    }

    /* renamed from: b */
    public void mo3326b(String str, boolean z) {
        this.f7673h.getInnerInput().setError(str);
        this.f7673h.requestFocus();
    }

    /* renamed from: b */
    public void mo3327b(boolean z) {
        if (z) {
            this.f7673h.getInnerInput().setImeOptions(5);
            this.f7674i.setVisibility(0);
            this.f7675j.setVisibility(0);
            return;
        }
        this.f7673h.setImeOptions(6);
        this.f7674i.setVisibility(8);
        this.f7675j.setVisibility(8);
    }

    /* renamed from: c */
    public void mo3328c(String str, boolean z) {
        this.f7674i.getInnerInput().setError(str);
        this.f7674i.requestFocus();
    }

    /* renamed from: c */
    public void mo3329c(boolean z) {
        this.f7678m.setVisibility(z ? 0 : 4);
    }

    /* renamed from: d */
    public int mo3330d() {
        return new SDKConfig().m11031a(BaseSetting.m10470g(mo3228a())).mo3269b();
    }

    /* renamed from: d */
    public void mo3331d(String str, boolean z) {
        this.f7675j.getMonthEditText().setError(str);
        this.f7675j.getMonthEditText().requestFocus();
    }

    /* renamed from: e */
    public String mo3332e() {
        return this.f7672g.getText().toString();
    }

    /* renamed from: e */
    public void mo3333e(String str, boolean z) {
        this.f7675j.getYearEditText().setError(str);
        this.f7675j.getYearEditText().requestFocus();
    }

    /* renamed from: f */
    public UserCard mo3334f() {
        return this.f7679n;
    }

    /* renamed from: f */
    public void mo3335f(String str, boolean z) {
        if (isAdded()) {
            CommonDialogParam commonDialogParam = new CommonDialogParam();
            commonDialogParam.f7380a = getString(C2262R.string.asanpardakht_text_error_dialog_title);
            commonDialogParam.f7381b = str;
            if (z) {
                commonDialogParam.f7382c = getString(C2262R.string.asanpardakht_action_retry);
                commonDialogParam.f7383d = getString(C2262R.string.asanpardakht_action_cancel);
            } else {
                commonDialogParam.f7383d = getString(C2262R.string.asanpardakht_action_return_to_parent);
            }
            CommonDialog a = CommonDialog.m11052a(commonDialogParam);
            a.setTargetFragment(this, 0);
            a.setCancelable(false);
            a.m10444a(getActivity().m1547e(), "inquiry_error");
        }
    }

    /* renamed from: g */
    public String mo3336g() {
        return this.f7673h.getText().toString();
    }

    /* renamed from: h */
    public String mo3337h() {
        return this.f7674i.getText().toString();
    }

    /* renamed from: i */
    public String mo3338i() {
        return this.f7675j.getMonthEditText().getText().toString();
    }

    /* renamed from: j */
    public String mo3339j() {
        return this.f7675j.getYearEditText().getText().toString();
    }

    /* renamed from: k */
    public boolean mo3340k() {
        return this.f7675j.m11015c();
    }

    /* renamed from: l */
    public void mo3341l() {
        if (isAdded()) {
            CommonDialogParam commonDialogParam = new CommonDialogParam();
            commonDialogParam.f7380a = getString(C2262R.string.asanpardakht_text_error_dialog_title);
            commonDialogParam.f7381b = getString(C2262R.string.asanpardakht_message_sdk_status_timeout);
            commonDialogParam.f7383d = getString(C2262R.string.asanpardakht_action_ok);
            Fragment a = CommonDialog.m11052a(commonDialogParam);
            a.setTargetFragment(this, 0);
            a.setCancelable(false);
            aa a2 = getActivity().m1547e().mo284a();
            a2.mo247a(a, "timeout_error");
            a2.mo249b();
        }
    }

    /* renamed from: m */
    public void mo3342m() {
        CommonDialogParam commonDialogParam = new CommonDialogParam();
        commonDialogParam.f7380a = getString(C2262R.string.asanpardakht_text_warning_dialog_title);
        commonDialogParam.f7381b = ClientConfig.m11126a(getContext()).m11142h();
        commonDialogParam.f7383d = getString(C2262R.string.asanpardakht_action_continue);
        commonDialogParam.f7382c = getString(C2262R.string.asanpardakht_action_return_to_parent);
        CommonDialog a = CommonDialog.m11052a(commonDialogParam);
        a.setTargetFragment(this, 0);
        a.setCancelable(false);
        a.m10444a(getActivity().m1547e(), "root_warning");
    }

    /* renamed from: n */
    public void mo3343n() {
        CommonDialogParam commonDialogParam = new CommonDialogParam();
        commonDialogParam.f7380a = getString(C2262R.string.asanpardakht_text_error_dialog_title);
        commonDialogParam.f7381b = ClientConfig.m11126a(getContext()).m11142h();
        commonDialogParam.f7383d = getString(C2262R.string.asanpardakht_action_return_to_parent);
        CommonDialog a = CommonDialog.m11052a(commonDialogParam);
        a.setTargetFragment(this, 0);
        a.setCancelable(false);
        a.m10444a(getActivity().m1547e(), "root_disable");
    }

    /* renamed from: o */
    public void mo3344o() {
        CommonDialogParam commonDialogParam = new CommonDialogParam();
        commonDialogParam.f7380a = getString(C2262R.string.asanpardakht_text_warning_dialog_title);
        commonDialogParam.f7381b = getString(C2262R.string.asanpardakht_info_validate_number_param, BaseSetting.m10471h(getContext()));
        commonDialogParam.f7383d = getString(C2262R.string.asanpardakht_action_return_to_parent);
        commonDialogParam.f7382c = getString(C2262R.string.asanpardakht_action_continue);
        CommonDialog a = CommonDialog.m11052a(commonDialogParam);
        a.setTargetFragment(this, 0);
        a.setCancelable(false);
        a.m10444a(getActivity().m1547e(), "validate_number");
    }

    public void onResume() {
        super.onResume();
        m11493p().mo3356g();
    }

    public void onSaveInstanceState(Bundle bundle) {
        this.f7666a.mo3347a(bundle);
        super.onSaveInstanceState(bundle);
    }

    /* renamed from: p */
    public ActionListener m11493p() {
        return this.f7666a;
    }
}
