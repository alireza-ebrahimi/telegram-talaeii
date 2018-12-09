package com.persianswitch.sdk.payment.payment;

import android.os.Bundle;
import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.payment.StateRecoverable;
import com.persianswitch.sdk.payment.model.PaymentProfile;
import com.persianswitch.sdk.payment.model.UserCard;

interface PaymentContract {

    public interface ActionListener extends com.persianswitch.sdk.base.BaseContract.ActionListener, StateRecoverable {
        /* renamed from: a */
        void mo3346a();

        /* renamed from: a */
        void mo3348a(boolean z);

        /* renamed from: b */
        void mo3349b();

        /* renamed from: c */
        void mo3351c();

        /* renamed from: c */
        void mo3352c(Bundle bundle);

        /* renamed from: d */
        void mo3353d();

        /* renamed from: e */
        void mo3354e();

        /* renamed from: f */
        void mo3355f();

        /* renamed from: g */
        void mo3356g();

        /* renamed from: h */
        void mo3357h();

        /* renamed from: i */
        void mo3358i();

        /* renamed from: j */
        void mo3359j();

        /* renamed from: k */
        void mo3360k();

        /* renamed from: l */
        void mo3361l();

        /* renamed from: m */
        void mo3362m();
    }

    public interface View extends com.persianswitch.sdk.base.BaseContract.View<ActionListener>, ISuggestionUpdate {
        /* renamed from: a */
        void mo3314a(long j);

        /* renamed from: a */
        void mo3315a(Bundle bundle);

        /* renamed from: a */
        void mo3317a(WSStatus wSStatus, String str, WSResponse wSResponse);

        /* renamed from: a */
        void mo3319a(PaymentProfile paymentProfile);

        /* renamed from: a */
        void mo3320a(String str, PaymentProfile paymentProfile);

        /* renamed from: a */
        void mo3321a(String str, boolean z);

        /* renamed from: b */
        void m11424b();

        /* renamed from: b */
        void mo3323b(Bundle bundle);

        /* renamed from: b */
        void mo3325b(PaymentProfile paymentProfile);

        /* renamed from: b */
        void mo3326b(String str, boolean z);

        /* renamed from: b */
        void mo3327b(boolean z);

        /* renamed from: c */
        void m11429c();

        /* renamed from: c */
        void mo3328c(String str, boolean z);

        /* renamed from: c */
        void mo3329c(boolean z);

        /* renamed from: d */
        void mo3331d(String str, boolean z);

        /* renamed from: e */
        String mo3332e();

        /* renamed from: e */
        void mo3333e(String str, boolean z);

        /* renamed from: f */
        UserCard mo3334f();

        /* renamed from: f */
        void mo3335f(String str, boolean z);

        /* renamed from: g */
        String mo3336g();

        /* renamed from: h */
        String mo3337h();

        /* renamed from: i */
        String mo3338i();

        /* renamed from: j */
        String mo3339j();

        /* renamed from: k */
        boolean mo3340k();

        /* renamed from: l */
        void mo3341l();

        /* renamed from: m */
        void mo3342m();

        /* renamed from: n */
        void mo3343n();

        /* renamed from: o */
        void mo3344o();
    }
}
