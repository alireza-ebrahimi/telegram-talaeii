package com.persianswitch.sdk.payment.managers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.persianswitch.sdk.api.IPaymentService.Stub;
import com.persianswitch.sdk.api.Response.General;
import com.persianswitch.sdk.api.Response.Register;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.preference.SqliteSecurePreference;
import com.persianswitch.sdk.base.security.DeviceInfo;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.ResultPack;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.payment.SDKConfig;
import com.persianswitch.sdk.payment.SDKSetting;
import com.persianswitch.sdk.payment.model.HostDataResponseField;
import com.persianswitch.sdk.payment.model.TransactionStatus;
import com.persianswitch.sdk.payment.model.req.ReVerificationRequest;
import com.persianswitch.sdk.payment.model.req.RegisterRequest;
import com.persianswitch.sdk.payment.payment.PaymentActivity;
import com.persianswitch.sdk.payment.webservice.SDKSyncWebServiceCallback;

public class ServiceManager extends Stub {
    /* renamed from: a */
    public static String f7403a = "dataBundle";
    /* renamed from: b */
    private final Context f7404b;

    public ServiceManager(Context context) {
        this.f7404b = context;
    }

    /* renamed from: d */
    private void m11100d(Bundle bundle) {
        String string = bundle.getString("host_security_token", TtmlNode.ANONYMOUS_REGION_ID);
        BaseSetting.m10457a(string);
        SDKSetting.m11040a(string);
    }

    /* renamed from: a */
    public void mo3224a(Bundle bundle) {
        SDKConfig.m11030a(bundle);
        if ("fa".equalsIgnoreCase(bundle.getString("language"))) {
            LanguageManager.m10669a(this.f7404b).m10676a("fa");
        } else {
            LanguageManager.m10669a(this.f7404b).m10676a("en");
        }
    }

    /* renamed from: b */
    public Bundle mo3225b(Bundle bundle) {
        m11100d(bundle);
        boolean l = BaseSetting.m10475l(this.f7404b);
        if (!l) {
            DataManager.m11080a(this.f7404b);
            m11100d(bundle);
            SqliteSecurePreference.m10703a(BaseSetting.m10452a(this.f7404b));
            SqliteSecurePreference.m10703a(SDKSetting.m11037a(this.f7404b));
        }
        String string = bundle.getString("client_mobile_no", TtmlNode.ANONYMOUS_REGION_ID);
        String string2 = bundle.getString("client_imei", TtmlNode.ANONYMOUS_REGION_ID);
        String string3 = bundle.getString("client_wifi_mac", TtmlNode.ANONYMOUS_REGION_ID);
        BaseSetting.m10455a(this.f7404b, string2);
        BaseSetting.m10460b(this.f7404b, string3);
        Bundle bundle2 = new Bundle();
        RegisterRequest reVerificationRequest = l ? new ReVerificationRequest() : new RegisterRequest();
        reVerificationRequest.m11327e(string);
        reVerificationRequest.m11340l(bundle.getString("activation_id"));
        reVerificationRequest.m11342m(bundle.getString("activation_code"));
        reVerificationRequest.m11337k(DeviceInfo.m10725c(this.f7404b));
        reVerificationRequest.m11332h(string2);
        reVerificationRequest.m11334i(string3);
        reVerificationRequest.m11287a(bundle.getInt("host_id"));
        reVerificationRequest.m11288a(bundle.getString("protocol_version"));
        reVerificationRequest.m11290b(bundle.getString("host_data"));
        reVerificationRequest.m11293c(bundle.getString("host_data_sign"));
        reVerificationRequest.m11325b(DeviceInfo.m10719a(this.f7404b, reVerificationRequest.m11331h()));
        reVerificationRequest.m11329f(VERSION.RELEASE);
        reVerificationRequest.m11324a(DeviceInfo.m10723a(this.f7404b));
        reVerificationRequest.m11330g(DeviceInfo.m10720a());
        reVerificationRequest.m11336j(DeviceInfo.m10724b(this.f7404b));
        ResultPack a = reVerificationRequest.m11294d(this.f7404b).m10870a(this.f7404b, new SDKSyncWebServiceCallback());
        WSResponse b = a.m10847b();
        if (a.m10846a() == TransactionStatus.SUCCESS) {
            String[] h = b.m10947h();
            Long d = StringUtils.m10808d(h[0]);
            String str = h[1];
            string = h[2];
            if (d != null) {
                BaseSetting.m10454a(this.f7404b, d.longValue());
            }
            BaseSetting.m10463c(this.f7404b, string);
            BaseSetting.m10453a(this.f7404b, reVerificationRequest.m11299g());
            BaseSetting.m10465d(this.f7404b, reVerificationRequest.m11331h());
            bundle2.putInt(General.f6983a, 0);
            bundle2.putString(General.f6985c, b.m10945f().toString());
            try {
                CardManager cardManager = new CardManager(this.f7404b);
                cardManager.m11076a(cardManager.m11075a());
            } catch (Exception e) {
                SDKLog.m10661c("ServiceManager", "error in sync cards in register", new Object[0]);
            }
        } else {
            bundle2.putInt(General.f6983a, 1001);
            if (!(b == null || b.m10946g() == null || !b.m10946g().has("locktime"))) {
                try {
                    bundle2.putInt(Register.f6988a, b.m10946g().getInt("locktime"));
                } catch (Throwable e2) {
                    SDKLog.m10659b("ServiceManager", "error while read lock_time from extra data", e2, new Object[0]);
                }
            }
            if (b == null || b.m10945f() == null) {
                bundle2.putString(General.f6985c, TtmlNode.ANONYMOUS_REGION_ID);
                bundle2.putString(General.f6986d, TtmlNode.ANONYMOUS_REGION_ID);
            } else {
                HostDataResponseField a2 = HostDataResponseField.m11155a(b.m10945f().toString());
                if (a2 != null) {
                    if (a2.m11160c() > 0) {
                        bundle2.putInt(General.f6983a, a2.m11160c());
                    }
                    bundle2.putString(General.f6985c, a2.m11158a());
                    bundle2.putString(General.f6986d, a2.m11159b());
                }
            }
        }
        return bundle2;
    }

    /* renamed from: c */
    public Bundle mo3226c(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        Intent intent = new Intent(this.f7404b, PaymentActivity.class);
        intent.putExtra(f7403a, bundle);
        bundle2.putParcelable("payment_intent", PendingIntent.getActivity(this.f7404b, 0, intent, 134217728));
        return bundle2;
    }
}
