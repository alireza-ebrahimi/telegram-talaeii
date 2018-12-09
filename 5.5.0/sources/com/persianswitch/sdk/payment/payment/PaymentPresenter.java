package com.persianswitch.sdk.payment.payment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.google.android.gms.common.internal.ImagesContract;
import com.persianswitch.sdk.C2262R;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.jsevaluator.JsEvaluator;
import com.persianswitch.sdk.base.jsevaluator.interfaces.JsCallback;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.utils.RootUtils;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.StatusCode;
import com.persianswitch.sdk.base.webservice.WebService;
import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.base.webservice.data.WSTranRequest;
import com.persianswitch.sdk.base.webservice.data.WSTranResponse;
import com.persianswitch.sdk.payment.SDKSetting;
import com.persianswitch.sdk.payment.managers.CardManager;
import com.persianswitch.sdk.payment.managers.SDKResultManager;
import com.persianswitch.sdk.payment.managers.ScheduledTaskManager;
import com.persianswitch.sdk.payment.managers.SyncManager;
import com.persianswitch.sdk.payment.model.Bank;
import com.persianswitch.sdk.payment.model.CVV2Status;
import com.persianswitch.sdk.payment.model.CardPin;
import com.persianswitch.sdk.payment.model.ClientConfig;
import com.persianswitch.sdk.payment.model.ClientConfig.RootConfigTypes;
import com.persianswitch.sdk.payment.model.Cvv2JsonParameter;
import com.persianswitch.sdk.payment.model.HostDataRequestField;
import com.persianswitch.sdk.payment.model.HostDataResponseField;
import com.persianswitch.sdk.payment.model.PaymentProfile;
import com.persianswitch.sdk.payment.model.SyncType;
import com.persianswitch.sdk.payment.model.SyncableData;
import com.persianswitch.sdk.payment.model.TransactionStatus;
import com.persianswitch.sdk.payment.model.UserCard;
import com.persianswitch.sdk.payment.model.UserCard.CardProtocolConverter;
import com.persianswitch.sdk.payment.model.req.AbsRequest;
import com.persianswitch.sdk.payment.model.req.InquiryMerchantRequest;
import com.persianswitch.sdk.payment.model.req.InquiryTransactionStatusRequest;
import com.persianswitch.sdk.payment.model.req.PaymentRequest;
import com.persianswitch.sdk.payment.model.req.TrustCodeRequest;
import com.persianswitch.sdk.payment.model.res.InquiryMerchantResponse;
import com.persianswitch.sdk.payment.model.res.TrustResponse;
import com.persianswitch.sdk.payment.payment.PaymentContract.ActionListener;
import com.persianswitch.sdk.payment.payment.PaymentContract.View;
import com.persianswitch.sdk.payment.repo.SyncRepo;
import com.persianswitch.sdk.payment.webservice.SDKWebServiceCallback;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

class PaymentPresenter implements ActionListener {
    /* renamed from: a */
    private final View f7693a;
    /* renamed from: b */
    private long f7694b;
    /* renamed from: c */
    private CountDownTimer f7695c;
    /* renamed from: d */
    private boolean f7696d;
    /* renamed from: e */
    private boolean f7697e;
    /* renamed from: f */
    private CardManager f7698f = new CardManager(m11538q());
    /* renamed from: g */
    private PaymentProfile f7699g;
    /* renamed from: h */
    private CVV2Status f7700h;
    /* renamed from: i */
    private String f7701i;
    /* renamed from: j */
    private String f7702j;
    /* renamed from: k */
    private String f7703k;
    /* renamed from: l */
    private long f7704l;
    /* renamed from: m */
    private boolean f7705m;
    /* renamed from: n */
    private boolean f7706n;
    /* renamed from: o */
    private boolean f7707o;
    /* renamed from: p */
    private String f7708p;
    /* renamed from: q */
    private String f7709q;
    /* renamed from: r */
    private String f7710r;

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentPresenter$1 */
    class C23101 extends SDKWebServiceCallback<WSResponse> {
        /* renamed from: b */
        final /* synthetic */ PaymentPresenter f7681b;

        C23101(PaymentPresenter paymentPresenter) {
            this.f7681b = paymentPresenter;
        }

        /* renamed from: a */
        public void mo3276a() {
            this.f7681b.m11559n().m11424b();
        }

        /* renamed from: a */
        public void mo3277a(WSStatus wSStatus, String str, WSResponse wSResponse) {
            this.f7681b.m11518a(wSStatus, str, wSResponse);
        }

        /* renamed from: a */
        public void mo3278a(String str, WSResponse wSResponse) {
            this.f7681b.m11520a(wSResponse);
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentPresenter$3 */
    class C23123 implements JsCallback {
        /* renamed from: a */
        final /* synthetic */ PaymentPresenter f7687a;

        C23123(PaymentPresenter paymentPresenter) {
            this.f7687a = paymentPresenter;
        }

        /* renamed from: a */
        public void mo3345a(String str) {
            try {
                boolean parseBoolean = Boolean.parseBoolean(str);
                this.f7687a.f7700h = parseBoolean ? CVV2Status.CVV2_REQUIRED : CVV2Status.CVV2_NOT_REQUIRED_STATUS;
                this.f7687a.m11559n().mo3327b(parseBoolean);
            } catch (Exception e) {
                SDKLog.m10661c("PaymentPresenter", "error while invoke javascript for calculate cvv2/expiration status !", new Object[0]);
            }
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentPresenter$4 */
    class C23134 extends SDKWebServiceCallback<WSResponse> {
        /* renamed from: b */
        final /* synthetic */ PaymentPresenter f7688b;

        C23134(PaymentPresenter paymentPresenter) {
            this.f7688b = paymentPresenter;
        }

        /* renamed from: a */
        public void mo3276a() {
            this.f7688b.m11559n().m11424b();
        }

        /* renamed from: a */
        public void mo3277a(WSStatus wSStatus, String str, WSResponse wSResponse) {
            this.f7688b.m11559n().mo3317a(wSStatus, str, wSResponse);
        }

        /* renamed from: a */
        public void mo3278a(String str, WSResponse wSResponse) {
            try {
                TrustResponse trustResponse = new TrustResponse(this.f7688b.m11538q(), wSResponse);
                Bundle bundle = new Bundle();
                bundle.putString("code", trustResponse.m11383b());
                bundle.putString("ussdDial", trustResponse.m11381a());
                bundle.putString(ImagesContract.URL, trustResponse.m11384c());
                bundle.putBoolean("ussd_available", trustResponse.m11386e());
                bundle.putBoolean("web_available", trustResponse.m11387f());
                bundle.putString("desc", trustResponse.m11385d());
                this.f7688b.m11559n().mo3323b(bundle);
            } catch (JsonParseException e) {
                this.f7688b.m11559n().mo3317a(WSStatus.PARSE_ERROR, this.f7688b.m11538q().getString(C2262R.string.asanpardakht_message_error_bad_response), wSResponse);
            }
        }

        /* renamed from: b */
        public void mo3280b() {
            this.f7688b.m11559n().m11429c();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentPresenter$5 */
    class C23145 extends SDKWebServiceCallback<WSResponse> {
        /* renamed from: b */
        final /* synthetic */ PaymentPresenter f7689b;

        C23145(PaymentPresenter paymentPresenter) {
            this.f7689b = paymentPresenter;
        }

        /* renamed from: a */
        public void mo3276a() {
        }

        /* renamed from: a */
        public void mo3277a(WSStatus wSStatus, String str, WSResponse wSResponse) {
            this.f7689b.m11541t();
        }

        /* renamed from: a */
        public void mo3278a(String str, WSResponse wSResponse) {
            this.f7689b.m11531b(wSResponse);
        }

        /* renamed from: b */
        public void mo3280b() {
            this.f7689b.m11559n().m11429c();
        }
    }

    PaymentPresenter(View view) {
        this.f7693a = view;
    }

    /* renamed from: a */
    private TransactionStatus m11516a(int i) {
        return i == 0 ? TransactionStatus.SUCCESS : (i == 25 || i == StatusCode.TRANSACTION_NOT_FOUND.m10851a()) ? TransactionStatus.FAIL : StatusCode.m10848a(i).m10852b() ? TransactionStatus.UNKNOWN : TransactionStatus.FAIL;
    }

    /* renamed from: a */
    private void m11518a(WSStatus wSStatus, String str, WSResponse wSResponse) {
        m11559n().m11429c();
        this.f7699g.m11164a(1002);
        if (TransactionStatus.m11229a(wSStatus, wSResponse)) {
            this.f7705m = false;
        } else if (wSResponse.m10945f() != null) {
            HostDataResponseField a = HostDataResponseField.m11155a(wSResponse.m10945f().toString());
            if (a != null) {
                if (a.m11160c() > 0) {
                    this.f7699g.m11164a(a.m11160c());
                    this.f7705m = false;
                } else {
                    this.f7705m = true;
                }
                this.f7699g.m11169a(a.m11158a());
                this.f7699g.m11173b(a.m11159b());
            }
        }
        m11559n().mo3335f(str, this.f7705m);
    }

    /* renamed from: a */
    private void m11519a(WSStatus wSStatus, String str, WSTranResponse wSTranResponse, UserCard userCard, boolean z) {
        int i = 0;
        if (TransactionStatus.m11229a(wSStatus, wSTranResponse)) {
            this.f7699g.m11167a(TransactionStatus.UNKNOWN);
            this.f7699g.m11164a(1001);
            if (!(wSTranResponse == null || wSTranResponse.m10945f() == null)) {
                HostDataResponseField a = HostDataResponseField.m11155a(wSTranResponse.m10945f().toString());
                if (a != null && a.m11160c() > 0) {
                    this.f7699g.m11169a(a.m11159b());
                    this.f7699g.m11173b(a.m11159b());
                    this.f7699g.m11164a(a.m11160c());
                }
            }
            SDKLog.m10657a("PaymentPresenter", "try to inquiry the unknown transaction state", new Object[0]);
            m11540s();
            return;
        }
        this.f7698f.m11077a(z, userCard, wSTranResponse, m11559n());
        this.f7699g.m11165a(0);
        this.f7699g.m11172b(wSTranResponse.m10944e());
        this.f7699g.m11177d(wSTranResponse.m10940a(m11538q()));
        this.f7699g.m11179e(wSTranResponse.m10964j());
        this.f7699g.m11164a(1001);
        this.f7699g.m11167a(TransactionStatus.FAIL);
        if (wSTranResponse.m10945f() != null) {
            HostDataResponseField a2 = HostDataResponseField.m11155a(wSTranResponse.m10945f().toString());
            if (a2 != null) {
                if (a2.m11160c() > 0) {
                    this.f7699g.m11164a(a2.m11160c());
                    i = 1;
                }
                this.f7699g.m11169a(a2.m11158a());
                this.f7699g.m11173b(a2.m11159b());
            }
        }
        m11559n().m11429c();
        if (i != 0) {
            m11559n().mo3325b(this.f7699g);
            return;
        }
        if (wSTranResponse.m10942c().m10851a() == StatusCode.NEED_SEND_CVV2.m10851a() || wSTranResponse.m10942c().m10851a() == StatusCode.NEED_SEND_CVV2_GLOBAL.m10851a()) {
            this.f7700h = CVV2Status.CVV2_REQUIRED;
            m11559n().mo3327b(true);
        }
        m11559n().mo3320a(str, this.f7699g);
    }

    /* renamed from: a */
    private void m11520a(WSResponse wSResponse) {
        try {
            InquiryMerchantResponse inquiryMerchantResponse = new InquiryMerchantResponse(m11538q(), wSResponse);
            this.f7699g.m11195m(inquiryMerchantResponse.m11366a());
            this.f7699g.m11166a(inquiryMerchantResponse.m11368b());
            this.f7699g.m11168a(inquiryMerchantResponse.m11369c());
            this.f7699g.m11181f(inquiryMerchantResponse.m11370d());
            this.f7699g.m11183g(inquiryMerchantResponse.m11371e());
            this.f7699g.m11185h(inquiryMerchantResponse.m11372f());
            this.f7699g.m11177d(inquiryMerchantResponse.m11373g());
            this.f7699g.m11189j(inquiryMerchantResponse.m11376j());
            this.f7699g.m11191k(inquiryMerchantResponse.m11377k());
            this.f7699g.m11193l(inquiryMerchantResponse.m11378l());
            this.f7699g.m11170a(StringUtils.m10805a("1", inquiryMerchantResponse.m11379m()));
            if (StringUtils.m10805a("1", inquiryMerchantResponse.m11374h())) {
                this.f7699g.m11187i(inquiryMerchantResponse.m11375i());
            } else {
                m11559n().mo3335f(m11538q().getString(C2262R.string.asanpardakht_error_inquiry_amount), this.f7705m);
            }
            String c = LanguageManager.m10669a(m11538q()).m10678c();
            if (inquiryMerchantResponse.m11380n() != null) {
                new SyncManager(m11538q()).m11106a(inquiryMerchantResponse.m11380n().toString(), c);
            } else {
                SDKLog.m10661c("PaymentPresenter", "sync data is null", new Object[0]);
            }
            m11559n().mo3319a(this.f7699g);
            m11559n().mo3329c(ClientConfig.m11126a(m11538q()).m11135a());
            m11559n().m11429c();
            m11560o();
            m11561p();
            mo3358i();
        } catch (JsonParseException e) {
            m11518a(WSStatus.PARSE_ERROR, m11538q().getString(C2262R.string.asanpardakht_message_error_bad_response), wSResponse);
        }
    }

    /* renamed from: a */
    private void m11521a(WSTranResponse wSTranResponse, boolean z, UserCard userCard) {
        this.f7698f.m11077a(z, userCard, wSTranResponse, m11559n());
        HostDataResponseField a = wSTranResponse.m10945f() != null ? HostDataResponseField.m11155a(wSTranResponse.m10945f().toString()) : null;
        if (m11527a(a)) {
            this.f7699g.m11164a(0);
            this.f7699g.m11167a(TransactionStatus.SUCCESS);
            this.f7699g.m11169a(a.m11158a());
            this.f7699g.m11173b(a.m11159b());
            this.f7699g.m11172b(wSTranResponse.m10944e());
            this.f7699g.m11177d(wSTranResponse.m10943d());
            this.f7699g.m11179e(wSTranResponse.m10964j());
            m11559n().m11429c();
            m11559n().mo3325b(this.f7699g);
            return;
        }
        m11519a(WSStatus.SERVER_INTERNAL_ERROR, TtmlNode.ANONYMOUS_REGION_ID, null, userCard, z);
    }

    /* renamed from: a */
    private void m11522a(AbsRequest absRequest) {
        absRequest.m11287a(BaseSetting.m10470g(m11538q()));
        absRequest.m11288a(this.f7708p);
        absRequest.m11290b(this.f7709q);
        absRequest.m11293c(this.f7710r);
    }

    /* renamed from: a */
    private boolean m11527a(HostDataResponseField hostDataResponseField) {
        return (hostDataResponseField == null || StringUtils.m10803a(hostDataResponseField.m11158a()) || StringUtils.m10803a(hostDataResponseField.m11159b())) ? false : true;
    }

    /* renamed from: b */
    private void m11531b(WSResponse wSResponse) {
        int i = 0;
        int parseInt = Integer.parseInt(wSResponse.m10947h()[0]);
        TransactionStatus a = m11516a(parseInt);
        String str = wSResponse.m10947h()[1];
        SDKLog.m10657a("PaymentPresenter", "inquiry transactions successful with status : %s", a);
        if (StringUtils.m10803a(str)) {
            str = StatusCode.m10850a(m11538q(), parseInt);
        }
        String str2 = wSResponse.m10947h()[2];
        String str3 = wSResponse.m10947h()[4];
        this.f7699g.m11167a(a);
        this.f7699g.m11177d(str);
        this.f7699g.m11179e(str2);
        if (!StringUtils.m10803a(str3)) {
            HostDataResponseField a2 = HostDataResponseField.m11155a(str3);
            if (a2 != null) {
                parseInt = a2.m11160c();
                this.f7699g.m11169a(a2.m11158a());
                this.f7699g.m11173b(a2.m11159b());
                switch (a) {
                    case SUCCESS:
                        this.f7699g.m11164a(0);
                        i = 1;
                        break;
                    case FAIL:
                        if (parseInt <= 0) {
                            this.f7699g.m11164a(1001);
                            break;
                        }
                        this.f7699g.m11164a(parseInt);
                        i = 1;
                        break;
                    case UNKNOWN:
                        if (parseInt <= 0) {
                            parseInt = 1001;
                        }
                        this.f7699g.m11164a(parseInt);
                        i = 1;
                        break;
                }
                if (i == 0) {
                    m11559n().mo3325b(this.f7699g);
                } else {
                    m11559n().mo3320a(str, this.f7699g);
                }
            }
        }
        parseInt = 0;
        switch (a) {
            case SUCCESS:
                this.f7699g.m11164a(0);
                i = 1;
                break;
            case FAIL:
                if (parseInt <= 0) {
                    this.f7699g.m11164a(parseInt);
                    i = 1;
                    break;
                }
                this.f7699g.m11164a(1001);
                break;
            case UNKNOWN:
                if (parseInt <= 0) {
                    parseInt = 1001;
                }
                this.f7699g.m11164a(parseInt);
                i = 1;
                break;
        }
        if (i == 0) {
            m11559n().mo3320a(str, this.f7699g);
        } else {
            m11559n().mo3325b(this.f7699g);
        }
    }

    /* renamed from: b */
    private boolean m11534b(boolean z) {
        if (z && m11559n().mo3332e().equals(TtmlNode.ANONYMOUS_REGION_ID)) {
            m11559n().mo3321a(m11538q().getString(C2262R.string.asanpardakht_input_error_is_empty), true);
            return false;
        } else if (z && m11559n().mo3332e().length() < 16) {
            m11559n().mo3321a(m11538q().getString(C2262R.string.asanpardakht_input_error_card_length), true);
            return false;
        } else if (m11559n().mo3336g().equals(TtmlNode.ANONYMOUS_REGION_ID)) {
            m11559n().mo3326b(m11538q().getString(C2262R.string.asanpardakht_input_error_is_empty), true);
            return false;
        } else if (m11559n().mo3336g().length() < 5) {
            m11559n().mo3326b(m11538q().getString(C2262R.string.asanpardakht_input_error_pin_length), true);
            return false;
        } else {
            if (this.f7700h == CVV2Status.CVV2_REQUIRED) {
                if (m11559n().mo3337h().equals(TtmlNode.ANONYMOUS_REGION_ID)) {
                    m11559n().mo3328c(m11538q().getString(C2262R.string.asanpardakht_input_error_is_empty), true);
                    return false;
                } else if (m11559n().mo3337h().length() < 3) {
                    m11559n().mo3328c(m11538q().getString(C2262R.string.asanpardakht_input_error_cvv2_length), true);
                    return false;
                } else if (m11559n().mo3338i().equals(TtmlNode.ANONYMOUS_REGION_ID)) {
                    m11559n().mo3331d(m11538q().getString(C2262R.string.asanpardakht_input_error_is_empty), true);
                    return false;
                } else if (m11559n().mo3338i().length() < 2) {
                    m11559n().mo3331d(m11538q().getString(C2262R.string.asanpardakht_input_month_length), true);
                    return false;
                } else {
                    int parseInt = Integer.parseInt(m11559n().mo3338i());
                    if (parseInt < 1 || parseInt > 12) {
                        m11559n().mo3331d(m11538q().getString(C2262R.string.asanpardakht_input_month_range), true);
                        return false;
                    } else if (m11559n().mo3339j().equals(TtmlNode.ANONYMOUS_REGION_ID)) {
                        m11559n().mo3333e(m11538q().getString(C2262R.string.asanpardakht_input_error_is_empty), true);
                        return false;
                    } else if (m11559n().mo3339j().length() < 2) {
                        m11559n().mo3333e(m11538q().getString(C2262R.string.asanpardakht_input_year_length), true);
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /* renamed from: d */
    private void m11537d(Bundle bundle) {
        this.f7701i = StringUtils.m10799a(bundle.getString("host_card_no", TtmlNode.ANONYMOUS_REGION_ID));
        this.f7708p = bundle.getString("protocol_version");
        this.f7709q = bundle.getString("host_data", TtmlNode.ANONYMOUS_REGION_ID);
        this.f7710r = bundle.getString("host_data_sign", TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: q */
    private Context m11538q() {
        return m11559n().mo3228a();
    }

    /* renamed from: r */
    private void m11539r() {
        if (ClientConfig.m11126a(m11538q()).m11143i() && !SDKSetting.m11043c(m11538q())) {
            m11559n().mo3344o();
        }
    }

    /* renamed from: s */
    private void m11540s() {
        long j = 0;
        AbsRequest inquiryTransactionStatusRequest = new InquiryTransactionStatusRequest();
        m11522a(inquiryTransactionStatusRequest);
        inquiryTransactionStatusRequest.m11309e(this.f7702j);
        inquiryTransactionStatusRequest.m11311f(this.f7703k);
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(this.f7704l);
        inquiryTransactionStatusRequest.m11312g(new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(instance.getTime()));
        long b = ClientConfig.m11126a(m11538q()).m11136b() - Math.abs(System.currentTimeMillis() - this.f7704l);
        if (b >= 0) {
            SDKLog.m10657a("PaymentPresenter", "inquiry waited for %d milli seconds. config = %d", Long.valueOf(b), Long.valueOf(r6));
            j = b;
        }
        inquiryTransactionStatusRequest.m11291c(m11538q()).m10863a(m11538q(), j, new C23145(this));
    }

    /* renamed from: t */
    private void m11541t() {
        SDKLog.m10657a("PaymentPresenter", "error while inquiry transaction status", new Object[0]);
        m11559n().mo3325b(this.f7699g);
    }

    /* renamed from: a */
    public void mo3346a() {
        if (this.f7695c != null) {
            this.f7695c.cancel();
        }
    }

    /* renamed from: a */
    public void mo3347a(Bundle bundle) {
        bundle.putLong("payment_started_time", this.f7694b);
        bundle.putParcelable("payment_profile", this.f7699g);
        bundle.putString("host_card_no", this.f7701i);
        bundle.putBoolean("payment_launched", this.f7707o);
        bundle.putString("tran_id", this.f7702j);
        bundle.putString("op_code", this.f7703k);
        bundle.putLong("send_payment_time_millis", this.f7704l);
        bundle.putString("sdk_protocol_ver", this.f7708p);
        bundle.putString("req_host_data", this.f7709q);
        bundle.putString("req_host_data_sign", this.f7710r);
    }

    /* renamed from: a */
    public void mo3348a(boolean z) {
        this.f7697e = z;
    }

    /* renamed from: b */
    public void mo3349b() {
        this.f7705m = true;
        this.f7699g = new PaymentProfile();
        this.f7699g.m11175c(this.f7701i);
        String c = LanguageManager.m10669a(m11538q()).m10678c();
        AbsRequest inquiryMerchantRequest = new InquiryMerchantRequest();
        m11522a(inquiryMerchantRequest);
        inquiryMerchantRequest.m11305a(new SyncManager(m11538q()).m11105a(c));
        inquiryMerchantRequest.m11291c(m11559n().mo3228a()).m10865b(m11538q(), new C23101(this));
    }

    /* renamed from: b */
    public void mo3350b(Bundle bundle) {
        SDKLog.m10660b("PaymentPresenter", "onRecoverInstanceState Called ?" + String.valueOf(bundle != null), new Object[0]);
        if (bundle != null) {
            this.f7706n = true;
            this.f7694b = bundle.getLong("payment_started_time", System.currentTimeMillis());
            this.f7699g = (PaymentProfile) bundle.getParcelable("payment_profile");
            this.f7701i = bundle.getString("host_card_no");
            this.f7707o = bundle.getBoolean("payment_launched");
            this.f7702j = bundle.getString("tran_id");
            this.f7703k = bundle.getString("op_code");
            this.f7704l = bundle.getLong("send_payment_time_millis");
            this.f7708p = bundle.getString("sdk_protocol_ver");
            this.f7709q = bundle.getString("req_host_data");
            this.f7710r = bundle.getString("req_host_data_sign");
            return;
        }
        this.f7694b = System.currentTimeMillis();
    }

    /* renamed from: c */
    public void mo3351c() {
        mo3348a(true);
        if (m11534b(m11559n().mo3334f() == null)) {
            UserCard userCard;
            Context q = m11538q();
            AbsRequest paymentRequest = new PaymentRequest();
            m11522a(paymentRequest);
            paymentRequest.mo3303d(this.f7699g.m11200r());
            paymentRequest.m11315e(this.f7699g.m11190k());
            paymentRequest.m11317f(this.f7699g.m11197o());
            paymentRequest.m11318g(this.f7699g.m11198p());
            paymentRequest.m11319h(this.f7699g.m11199q());
            WSTranRequest wSTranRequest = (WSTranRequest) paymentRequest.mo3304a(q).mo3300a();
            Long d = StringUtils.m10808d(this.f7699g.m11196n());
            if (d != null) {
                wSTranRequest.m10954c(d.longValue());
            }
            String a = StringUtils.m10799a(m11559n().mo3332e());
            if (m11559n().mo3334f() == null) {
                UserCard userCard2 = new UserCard(a);
                userCard2.m11269b(true);
                userCard = userCard2;
            } else {
                userCard = m11559n().mo3334f();
            }
            boolean a2 = ScheduledTaskManager.f7399a.mo3298a(q, System.currentTimeMillis());
            userCard.m11271c(a2);
            this.f7699g.m11168a(userCard);
            wSTranRequest.m10918a(new HostDataRequestField(this.f7709q, this.f7710r, this.f7708p, AbsRequest.m11282b(q)).m11152a());
            wSTranRequest.m10958i(new CardPin(m11559n().mo3336g(), m11559n().mo3337h(), m11559n().mo3338i(), m11559n().mo3339j(), m11559n().mo3340k()).toString());
            wSTranRequest.m10956h(new CardProtocolConverter(q).m11233a(userCard));
            final WSTranRequest wSTranRequest2 = wSTranRequest;
            final Context context = q;
            final boolean z = a2;
            final UserCard userCard3 = userCard;
            WebService.m10861b(wSTranRequest).m10865b(q, new SDKWebServiceCallback<WSTranResponse>(this) {
                /* renamed from: f */
                final /* synthetic */ PaymentPresenter f7686f;

                /* renamed from: a */
                public void mo3276a() {
                    SDKLog.m10657a("PaymentPresenter", "payment launched.", new Object[0]);
                    this.f7686f.f7702j = String.valueOf(wSTranRequest2.m10912a());
                    this.f7686f.f7703k = String.valueOf(wSTranRequest2.m10925c());
                    this.f7686f.f7704l = System.currentTimeMillis();
                    this.f7686f.f7707o = true;
                    this.f7686f.m11559n().m11424b();
                }

                /* renamed from: a */
                public void m11499a(WSStatus wSStatus, String str, WSTranResponse wSTranResponse) {
                    this.f7686f.m11519a(wSStatus, str, wSTranResponse, userCard3, z);
                }

                /* renamed from: a */
                public void m11501a(String str, WSTranResponse wSTranResponse) {
                    this.f7686f.m11521a(wSTranResponse, z, userCard3);
                }

                /* renamed from: b */
                public void mo3280b() {
                    this.f7686f.f7699g.m11165a(SDKResultManager.m11084a(context, wSTranRequest2).longValue());
                }
            });
        }
    }

    /* renamed from: c */
    public void mo3352c(Bundle bundle) {
        if (this.f7706n && this.f7699g != null && this.f7707o) {
            m11559n().mo3319a(this.f7699g);
            m11559n().mo3329c(ClientConfig.m11126a(m11538q()).m11135a());
            m11559n().m11424b();
            m11540s();
            return;
        }
        m11537d(bundle);
        mo3349b();
    }

    /* renamed from: d */
    public void mo3353d() {
        m11559n().mo3315a(SDKResultManager.m11090d(m11538q()));
    }

    /* renamed from: e */
    public void mo3354e() {
        if (this.f7705m) {
            mo3353d();
        } else {
            m11559n().mo3315a(SDKResultManager.m11083a(m11538q(), this.f7699g));
        }
        this.f7705m = true;
    }

    /* renamed from: f */
    public void mo3355f() {
        m11559n().mo3315a(SDKResultManager.m11088c(m11538q()));
        this.f7705m = true;
    }

    /* renamed from: g */
    public void mo3356g() {
        if (this.f7696d && !this.f7697e) {
            m11559n().mo3341l();
        }
    }

    /* renamed from: h */
    public void mo3357h() {
        m11559n().mo3315a(SDKResultManager.m11092e(m11538q()));
    }

    /* renamed from: i */
    public void mo3358i() {
        String c = LanguageManager.m10669a(m11538q()).m10678c();
        long a = m11559n().mo3334f() == null ? Bank.m11115a(StringUtils.m10799a(m11559n().mo3332e())).m11116a() : m11559n().mo3334f().m11275f().longValue();
        SyncableData a2 = new SyncRepo(m11538q()).m11591a(SyncType.f7532a, c);
        Cvv2JsonParameter cvv2JsonParameter = new Cvv2JsonParameter();
        cvv2JsonParameter.f7492a = BaseSetting.m10470g(m11538q()) + TtmlNode.ANONYMOUS_REGION_ID;
        cvv2JsonParameter.f7493b = this.f7699g.m11176d() == CVV2Status.CVV2_REQUIRED ? "1" : "0";
        cvv2JsonParameter.f7494c = "209";
        cvv2JsonParameter.f7495d = this.f7699g.m11196n();
        cvv2JsonParameter.f7496e = a + TtmlNode.ANONYMOUS_REGION_ID;
        cvv2JsonParameter.f7497f = this.f7699g.m11190k();
        cvv2JsonParameter.f7498g = TtmlNode.ANONYMOUS_REGION_ID;
        cvv2JsonParameter.f7499h = BaseSetting.m10471h(m11538q());
        if (a2 != null && !StringUtils.m10803a(a2.m11228f())) {
            new JsEvaluator(m11538q()).m10643a(a2.m11228f(), new C23123(this), "isInternetChanel", cvv2JsonParameter.m11145a());
        }
    }

    /* renamed from: j */
    public void mo3359j() {
        AbsRequest trustCodeRequest = new TrustCodeRequest();
        m11522a(trustCodeRequest);
        trustCodeRequest.m11291c(m11538q()).m10865b(m11538q(), new C23134(this));
    }

    /* renamed from: k */
    public void mo3360k() {
        SDKSetting.m11039a(m11538q(), true);
        if (!SDKSetting.m11043c(m11538q())) {
            m11559n().mo3344o();
        }
    }

    /* renamed from: l */
    public void mo3361l() {
        SDKSetting.m11041b(m11538q(), true);
    }

    /* renamed from: m */
    public void mo3362m() {
        this.f7699g.m11167a(TransactionStatus.FAIL);
        this.f7699g.m11164a(2022);
        m11559n().mo3315a(SDKResultManager.m11083a(m11538q(), this.f7699g));
    }

    /* renamed from: n */
    public View m11559n() {
        return this.f7693a;
    }

    /* renamed from: o */
    public void m11560o() {
        if (RootUtils.m10764a() && ClientConfig.m11126a(m11538q()).m11141g() == RootConfigTypes.EnableWithWarning) {
            if (SDKSetting.m11042b(m11538q())) {
                m11539r();
            } else {
                m11559n().mo3342m();
            }
        } else if (RootUtils.m10764a() && ClientConfig.m11126a(m11538q()).m11141g() == RootConfigTypes.Disable) {
            SDKSetting.m11039a(m11538q(), false);
            m11559n().mo3343n();
        } else {
            SDKSetting.m11039a(m11538q(), false);
            m11539r();
        }
    }

    /* renamed from: p */
    public void m11561p() {
        mo3346a();
        long d = ClientConfig.m11126a(m11538q()).m11138d() * 1000;
        final long j = d;
        this.f7695c = new CountDownTimer(this, d, 1000) {
            /* renamed from: b */
            final /* synthetic */ PaymentPresenter f7691b;

            /* renamed from: a */
            private void m11512a() {
                long currentTimeMillis = System.currentTimeMillis() - this.f7691b.f7694b;
                if (currentTimeMillis >= j) {
                    cancel();
                    this.f7691b.f7696d = true;
                    this.f7691b.mo3356g();
                    return;
                }
                this.f7691b.m11559n().mo3314a(j - currentTimeMillis);
            }

            public void onFinish() {
                m11512a();
            }

            public void onTick(long j) {
                m11512a();
            }
        };
        this.f7695c.start();
    }
}
