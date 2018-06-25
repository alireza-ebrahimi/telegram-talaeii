package com.persianswitch.sdk.payment.managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.persianswitch.sdk.C2262R;
import com.persianswitch.sdk.api.Response;
import com.persianswitch.sdk.api.Response.General;
import com.persianswitch.sdk.api.Response.Payment;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.StatusCode;
import com.persianswitch.sdk.base.webservice.data.WSTranRequest;
import com.persianswitch.sdk.payment.model.PaymentProfile;
import com.persianswitch.sdk.payment.model.TransactionStatus;

public final class SDKResultManager {
    /* renamed from: a */
    private static Bundle m11081a(int i, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt(General.f6983a, i);
        bundle.putString(General.f6984b, str);
        return bundle;
    }

    /* renamed from: a */
    public static Bundle m11082a(Context context) {
        return m11081a(2023, context.getString(C2262R.string.asanpardakht_message_error_2023));
    }

    /* renamed from: a */
    public static Bundle m11083a(Context context, PaymentProfile paymentProfile) {
        return paymentProfile.m11188j() == TransactionStatus.SUCCESS ? m11087b(context, paymentProfile) : paymentProfile.m11201s() ? m11091d(context, paymentProfile) : m11089c(context, paymentProfile);
    }

    /* renamed from: a */
    public static Long m11084a(Context context, WSTranRequest wSTranRequest) {
        return Long.valueOf((BaseSetting.m10461c(context) * 100000000) + wSTranRequest.m10912a());
    }

    /* renamed from: a */
    public static void m11085a(Activity activity, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtra(Response.f6990a, bundle);
        activity.setResult(-1, intent);
        activity.finish();
    }

    /* renamed from: b */
    public static Bundle m11086b(Context context) {
        return m11081a(1102, context.getString(C2262R.string.asanpardakht_message_sdk_status_register_need));
    }

    /* renamed from: b */
    private static Bundle m11087b(Context context, PaymentProfile paymentProfile) {
        Bundle a = m11081a(paymentProfile.m11174c(), context.getString(C2262R.string.asanpardakht_message_sdk_status_successful));
        if (paymentProfile.m11163a() != null) {
            a.putString(General.f6985c, paymentProfile.m11163a());
        }
        if (paymentProfile.m11171b() != null) {
            a.putString(General.f6986d, paymentProfile.m11171b());
        }
        if (paymentProfile.m11202t().longValue() > 0) {
            a.putLong(Payment.f6987a, paymentProfile.m11202t().longValue());
        }
        return a;
    }

    /* renamed from: c */
    public static Bundle m11088c(Context context) {
        int a = StatusCode.SECURITY_REQUIREMENT_NOT_PASSED.m10851a();
        return m11081a(a, StatusCode.m10850a(context, a));
    }

    /* renamed from: c */
    private static Bundle m11089c(Context context, PaymentProfile paymentProfile) {
        Bundle a = m11081a(paymentProfile.m11174c(), context.getString(C2262R.string.asanpardakht_message_sdk_status_failed));
        if (paymentProfile.m11163a() != null) {
            a.putString(General.f6985c, paymentProfile.m11163a());
        }
        if (paymentProfile.m11171b() != null) {
            a.putString(General.f6986d, paymentProfile.m11171b());
        }
        if (paymentProfile.m11202t().longValue() > 0) {
            a.putLong(Payment.f6987a, paymentProfile.m11202t().longValue());
        }
        return a;
    }

    /* renamed from: d */
    public static Bundle m11090d(Context context) {
        return m11081a(2020, context.getString(C2262R.string.asanpardakht_message_sdk_status_canceled));
    }

    /* renamed from: d */
    private static Bundle m11091d(Context context, PaymentProfile paymentProfile) {
        Bundle a = m11081a(paymentProfile.m11174c(), context.getString(C2262R.string.asanpardakht_message_sdk_status_unknown));
        if (paymentProfile.m11163a() != null) {
            a.putString(General.f6985c, paymentProfile.m11163a());
        }
        if (paymentProfile.m11171b() != null) {
            a.putString(General.f6986d, paymentProfile.m11171b());
        }
        if (paymentProfile.m11202t().longValue() > 0) {
            a.putLong(Payment.f6987a, paymentProfile.m11202t().longValue());
        }
        return a;
    }

    /* renamed from: e */
    public static Bundle m11092e(Context context) {
        return m11081a(2021, context.getString(C2262R.string.asanpardakht_message_sdk_status_timeout));
    }

    /* renamed from: f */
    public static boolean m11093f(Context context) {
        return BaseSetting.m10461c(context) > 0 && !StringUtils.m10803a(BaseSetting.m10471h(context));
    }
}
